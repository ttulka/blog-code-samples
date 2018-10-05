import {APIGatewayEvent} from 'aws-lambda';
import {Credentials, DynamoDB} from "aws-sdk";
import {
    ErrorResponse, InvocationRequest, PackageResult, PackageSearchResult, PackageStoredObject, RESTRequest, RESTResponse, Tag, UploadPayload,
    UploadResult
} from './types';
import {TaggingService} from "./taggingService";

const AWS = require('aws-sdk');
const uuidv1 = require('uuid/v1');

const PACKAGE_TABLE: string = process.env.PACKAGE_TABLE || '';
const S3_BUCKET: string = process.env.S3_BUCKET || '';
const KMS_KEY_ID: string = process.env.KMS_KEY_ID || '';
const TAGGING_LAMBDA: string = process.env.TAGGING_LAMBDA || '';

var taggingService: TaggingService = new TaggingService(TAGGING_LAMBDA);

var dynamoDb = new AWS.DynamoDB.DocumentClient({apiVersion: '2012-08-10'});
var s3;

export async function handler(event: any) {
    if (event.httpMethod) {
        return await handleAPIGatewayEvent(event as APIGatewayEvent);
    }
    else {
        return await handleInvocationRequest(event.payload as InvocationRequest);
    }
}

async function handleInvocationRequest(event: InvocationRequest) {
    if (!event.tenantId) {
        throw new Error("'tenantId' is missing in the payload.");
    }
    if (!event.payload.type) {
        throw new Error("'type' is missing in the payload.");
    }
    if (!event.payload.contentType) {
        throw new Error("'contentType' is missing in the payload.");
    }
    if (!event.payload.name) {
        throw new Error("'name' is missing in the payload.");
    }

    if (!s3) {
        s3 = new AWS.S3({apiVersion: '2006-03-01', signatureVersion: 'v4'});
    }

    return await upload(event.tenantId, event.payload) as UploadResult;
}

async function handleAPIGatewayEvent(event: APIGatewayEvent) {
    try {
        validateEnvVariables();

        const tenantId = getTenantId(event);
        const tenantIsolationRoleArn = getTenantIsolationRoleArn(event);

        validateTenantSpecifics(tenantId, tenantIsolationRoleArn);

        if (tenantIsolationRoleArn) {
            const tenantCredentials: Credentials = await getTenantCredentials(tenantIsolationRoleArn);

            //dynamoDb = new AWS.DynamoDB.DocumentClient({apiVersion: '2012-08-10', credentials: tenantCredentials});
            s3 = new AWS.S3({apiVersion: '2006-03-01', signatureVersion: 'v4', credentials: tenantCredentials});
        }

        const request: RESTRequest = {
            resource: event.resource,
            method: event.httpMethod,
            body: event.body,
            parameters: event.pathParameters,
        };

        return await dispatch(tenantId, request) as RESTResponse;

    } catch (error) {
        console.error('ERROR', error);
        return error.statusCode && error.body
            ? error
            : buildResponse(500, {error: error.toString()});
    }
}

function validateEnvVariables() {
    if (!PACKAGE_TABLE) {
        throw buildResponse(500, {error: 'PACKAGE_TABLE environment variable is not defined.'});
    }
    if (!S3_BUCKET) {
        throw buildResponse(500, {error: 'S3_BUCKET environment variable is not defined.'});
    }
    if (!KMS_KEY_ID) {
        throw buildResponse(500, {error: 'KMS_KEY_ID environment variable is not defined.'});
    }
    if (!TAGGING_LAMBDA) {
        throw buildResponse(500, {Error: 'TAGGING_LAMBDA environment variable is not defined.'});
    }
}

function validateTenantSpecifics(tenantId: string, tenantIsolationRoleArn: string) {
    if (!tenantId) {
        throw buildResponse(400, {error: 'tenantId is not defined.'});
    }
    if (!tenantIsolationRoleArn) {
        throw buildResponse(400, {error: 'tenantIsolationRoleArn is not defined.'});
    }
}

function getTenantId(event: APIGatewayEvent): string {
    return (event.requestContext && event.requestContext.authorizer)
        ? event.requestContext.authorizer.tenantId
        : undefined;
}

function getTenantIsolationRoleArn(event: APIGatewayEvent): string {
    return (event.requestContext && event.requestContext.authorizer)
        ? event.requestContext.authorizer.isolationRoleArn
        : undefined;
}

function getTenantCredentials(tenantIsolationRoleArn: string): Promise<Credentials> {
    return new Promise<Credentials>((response, reject) => {
        new AWS.CredentialProviderChain().resolve(function (err, masterCredentials) {
            if (err) {
                reject(new Error('No master credentials available'));
            } else {
                AWS.config.update({credentials: masterCredentials});

                const tenantCredentials = new AWS.TemporaryCredentials({RoleArn: tenantIsolationRoleArn});
                response(tenantCredentials);
            }
        });
    });
}

async function dispatch(tenantId: string, request: RESTRequest): Promise<RESTResponse> {
    if (request.method === 'GET') {
        if (request.parameters && request.parameters.id) {
            const packageId: string = request.parameters.id;
            const packageResult: PackageResult | undefined = await get(tenantId, packageId);

            if (packageResult) {
                return buildResponse(200, packageResult);
            } else {
                throw buildResponse(404, {error: "Cannot find ID " + packageId} as ErrorResponse);
            }
        } else {
            throw buildResponse(400, {error: "'id' parameter is missing."} as ErrorResponse);
        }
    }
    else if (request.method === 'POST') {
        if (request.body) {
            const payload: UploadPayload = JSON.parse(request.body) as UploadPayload;

            if (request.resource === '/search') {
                if (!payload.tags) {
                    throw buildResponse(400, {error: "'tags' is missing in the payload."} as ErrorResponse);
                }

                const packageResults: PackageSearchResult[] = await search(tenantId, payload.tags);
                return buildResponse(200, packageResults);

            } else {
                if (!payload.type) {
                    throw buildResponse(400, {error: "'type' is missing in the payload."} as ErrorResponse);
                }
                if (!payload.contentType) {
                    throw buildResponse(400, {error: "'contentType' is missing in the payload."} as ErrorResponse);
                }
                if (!payload.name) {
                    throw buildResponse(400, {error: "'name' is missing in the payload."} as ErrorResponse);
                }

                const uploadResult: UploadResult = await upload(tenantId, payload);
                return buildResponse(200, uploadResult);
            }
        } else {
            throw buildResponse(400, {error: "Invalid payload."} as ErrorResponse);
        }
    } else {
        throw buildResponse(400, {error: "Invalid method = '" + request.method + "'"} as ErrorResponse);
    }
}

async function get(tenantId: string, packageId: string): Promise<PackageResult | undefined> {
    const packageResult: PackageResult | undefined = await loadPackage(tenantId, packageId);

    if (packageResult) {
        packageResult.tags = await taggingService.loadTagsForPackage(tenantId, packageId);
    }
    return packageResult;
}

async function loadPackage(tenantId: string, packageId: string): Promise<PackageResult | undefined> {
    const params = {
        TableName: PACKAGE_TABLE,
        Key: {
            packageId, tenantId
        }
    } as DynamoDB.DocumentClient.GetItemInput;

    const result = await dynamoDb.get(params).promise();

    if (result.Item) {
        const downloadUrl = await buildDownloadUrl(result.Item.s3Bucket, result.Item.s3Key, KMS_KEY_ID);

        return {
            packageId: result.Item.packageId,
            tenantId: result.Item.tenantId,
            createdAt: result.Item.createdAt,
            type: result.Item.type,
            contentType: result.Item.contentType,
            name: result.Item.name,
            downloadUrl
        } as PackageResult;
    }
    return undefined;
}

async function search(tenantId: string, tags: Tag[]): Promise<PackageSearchResult[]> {
    const packageIds: string[] = await taggingService.searchPackageIdsByTags(tenantId, tags);

    return packageIds && packageIds.length
        ? packageIds.map(packageId => ({packageId}) as PackageSearchResult)
        : [];
}

async function upload(tenantId: string, payload: UploadPayload): Promise<UploadResult> {
    const packageObject: PackageStoredObject = await savePackage(tenantId, payload);

    if (payload.tags && payload.tags.length) {
        await taggingService.saveTagsForPackage(tenantId, packageObject.packageId, payload.tags);
    }

    const uploadUrl = await buildUploadUrl(packageObject.bucket, packageObject.key, payload.contentType, KMS_KEY_ID);

    return {
        packageId: packageObject.packageId,
        uploadUrl
    } as UploadResult;
}

async function savePackage(tenantId: string, payload: UploadPayload): Promise<PackageStoredObject> {
    const packageId: string = uuidv1();
    const createdAt = new Date().toISOString();

    const s3Bucket = S3_BUCKET;

    const keyHash = packageId.substr(0, 8);
    const s3Key = keyHash + "/" + tenantId + "/" + packageId;

    await dynamoDb.put({
        TableName: PACKAGE_TABLE,
        Item: {
            packageId,
            tenantId,
            s3Key,
            s3Bucket,
            type: payload.type,
            contentType: payload.contentType,
            name: payload.name,
            createdAt
        }
    }).promise()

    return {
        packageId,
        bucket: s3Bucket,
        key: s3Key
    } as PackageStoredObject
}

function buildUploadUrl(bucket, key, contentType, kmsKeyId): Promise<string> {
    return new Promise<string>(function (resolve, reject) {
        const params = {
            Bucket: bucket,
            Key: key,
            ContentType: contentType,
            //ServerSideEncryption: 'aws:kms',
            //SSEKMSKeyId: kmsKeyId,
            Expires: 24 * 60 * 60 // 1 day
        };
        s3.getSignedUrl('putObject', params, function (err, url) {
            if (err) reject(err);
            else resolve(url);
        });
    })
}

function buildDownloadUrl(bucket, key, kmsKeyId): Promise<string> {
    return new Promise<string>(function (resolve, reject) {
        const params = {
            Bucket: bucket,
            Key: key,
            //ServerSideEncryption: 'aws:kms',
            //SSEKMSKeyId: kmsKeyId,
            Expires: 24 * 60 * 60 // 1 day
        };
        s3.getSignedUrl('getObject', params, function (err, url) {
            if (err) reject(err);
            else resolve(url);
        });
    })
}

function buildResponse(statusCode: number, data: object): RESTResponse {
    return {
        isBase64Encoded: false,
        statusCode: statusCode,
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    } as RESTResponse;
}