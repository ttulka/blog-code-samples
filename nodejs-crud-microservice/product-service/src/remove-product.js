const AWS = require('aws-sdk')

const PRODUCT_TABLE = process.env.PRODUCT_TABLE
const EVENTS_TOPIC = process.env.EVENTS_TOPIC

const dynamoDb = new AWS.DynamoDB.DocumentClient({apiVersion: '2012-08-10'})
const sns = new AWS.SNS({apiVersion: '2010-03-31'})

exports.handler = async function(event) {
    if (!event || !event.httpMethod) {
        return buildResponse(400, {error: 'Wrong request format.'})
    }
    if (event.httpMethod !== 'DELETE') {
        return buildResponse(405, {error: 'Wrong request method - only DELETE supported.'})
    }
    if (!event.pathParameters || !event.pathParameters.id) {
        return buildResponse(400, {error: 'Wrong request - parameter product ID must be set.'})
    }

    try {
        const response = await dispatch(event.pathParameters.id)
        if (response) {
            return buildResponse(200)
        } else {
            return buildResponse(404, {error: 'Product was not found.'})
        }

    } catch (error) {
        console.error('ERROR', error);
        return error.statusCode && error.body
                ? error
                : buildResponse(500, {error: error.toString()})
    }
}

async function dispatch(id) {
    await removeProduct(id)

    await notifyProductRemoved(id)

    return true
}

async function removeProduct(id) {
    const params = {
        TableName: PRODUCT_TABLE,
        Key: { productId : id },
    }
    await dynamoDb.delete(params).promise()
}

async function notifyProductRemoved(id) {
    const params = {
        TopicArn: EVENTS_TOPIC,
        Message: JSON.stringify({
            Records: [{
                eventSource: 'product-service',
                eventName: 'PRODUCT_DELETED',
                eventVersion: '1.0',
                eventTime: new Date().toISOString(),
                payload: { id }
            }]
        })
    }
    await sns.publish(params).promise()
}

function buildResponse(statusCode, data = null) {
    return {
        isBase64Encoded: false,
        statusCode: statusCode,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }
}