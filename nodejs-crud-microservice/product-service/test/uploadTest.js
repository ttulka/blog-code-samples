describe("Upload request test", function () {
    const rewire = require("rewire");
    const fs = require("fs");

    const PACKAGE_TABLE: string = 'package';
    const S3_BUCKET: string = 's3bucket';
    const KMS_KEY_ID: string = 'kmskey';
    const TAGGING_LAMBDA: string = 'tagging-service';

    setupEnvVariables();

    const dynamoDbMock = mockDynamoDb();
    const s3Mock = mockS3();
    const taggingServiceMock = mockTaggingService();

    const index = rewire(__dirname + '/../src/index.ts');

    index.__set__('dynamoDb', dynamoDbMock);
    index.__set__('s3', s3Mock);
    index.__set__('taggingService', taggingServiceMock);

    index.__set__('validateTenantSpecifics', () => true);

    var response: any;

    beforeAll(done => {
        spyOn(dynamoDbMock, 'put').and.callThrough();
        spyOn(s3Mock, 'getSignedUrl').and.callThrough();

        response = index.handler(loadEvent('upload-request'))
            .then(res => {
                response = res;
                done();
            })
            .catch(err => done.fail(err));
    })

    it("Response should be set.", function () {
        expect(response).toBeDefined();
    });

    it("Response status should be 200.", function () {
        expect(response.statusCode).toBe(200);
    });

    it("Response body should contain `uploadUrl`.", function () {
        expect(response.body).toBeDefined();
        expect(JSON.parse(response.body).uploadUrl).toBe('test-signed-url');
    });

    it("DynamoDB should be called.", function () {
        expect(dynamoDbMock.put).toHaveBeenCalled();
    });

    it("S3 should be called.", function () {
        expect(s3Mock.getSignedUrl).toHaveBeenCalled();
    });

    it("Tagging should be called.", function () {
        expect(taggingServiceMock.saveTagsForPackage).toHaveBeenCalled();
    });

    // /////////////////////////////////////////////////////////////////////////////////////////////////////

    function mockDynamoDb() {
        const mock: any = {};
        mock.put = function (params: any) {
            if (params && params.TableName === PACKAGE_TABLE) {
                return {
                    promise: () => Promise.resolve({})
                }
            }
            else {
                throw new Error('TEST ERROR: calling DynamoDB with wrong params: ' + JSON.stringify(params));
            }
        }
        return mock;
    }

    function mockS3() {
        const mock: any = {};
        mock.getSignedUrl = function (op: string, params: any, callback?: (err, url) => void): string | void {
            if (op === 'putObject' && params && params.Bucket === 's3bucket' && params.ContentType === 'test/type' && params.Key) {
                if (callback) {
                    callback(null, 'test-signed-url');
                } else {
                    return 'test-signed-url';
                }
            }
            else {
                throw new Error('TEST ERROR: calling S3 with wrong params: ' + JSON.stringify(params));
            }
        }
        return mock;
    }

    function mockTaggingService() {
        return jasmine.createSpyObj('taggingService', ['saveTagsForPackage'])
    }

    function loadEvent(name): object {
        const event = JSON.parse(fs.readFileSync(__dirname + `/resources/${name}.json`))
        event.requestContext.authorizer.isolationRoleArn = undefined;
        return event
    }

    function setupEnvVariables(): void {
        process.env.PACKAGE_TABLE = PACKAGE_TABLE;
        process.env.S3_BUCKET = S3_BUCKET;
        process.env.KMS_KEY_ID = KMS_KEY_ID;
        process.env.TAGGING_LAMBDA = TAGGING_LAMBDA;
    }
});