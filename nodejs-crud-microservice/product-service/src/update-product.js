const AWS = require('aws-sdk')

const PRODUCT_TABLE = process.env.PRODUCT_TABLE
const EVENTS_TOPIC = process.env.EVENTS_TOPIC

const dynamoDb = new AWS.DynamoDB.DocumentClient({apiVersion: '2012-08-10'})
const sns = new AWS.SNS({apiVersion: '2010-03-31'})

exports.handler = async function(event) {
    if (!event || !event.httpMethod) {
        return buildResponse(400, {error: 'Wrong request format.'})
    }
    if (event.httpMethod !== 'PUT') {
        return buildResponse(405, {error: 'Wrong request method - only PUT supported.'})
    }
    if (!event.pathParameters || !event.pathParameters.id) {
        return buildResponse(400, {error: 'Wrong request - parameter product ID must be set.'})
    }
    if (!event.body) {
        return buildResponse(400, {error: 'Wrong request - body payload must be set.'})
    }
    
    const payload = JSON.parse(event.body)
    
    if (!payload.name || !payload.description || !payload.price) {
        return buildResponse(400, {error: 'Wrong request - body payload must contain attributes [name, description, price].'})
    }
    if (isNaN(payload.price)) {
        return buildResponse(400, {error: 'Wrong request - attribute [price] must a number.'})
    }

    try {
        const response = await dispatch(event.pathParameters.id, payload)
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

async function dispatch(id, payload) {
    const productUpdated = await updateProduct(id, payload)

    if (productUpdated) {
        await notifyProductUpdated(productUpdated)

        return true
    }
    return false
}

async function updateProduct(id, payload) {
    const product = {
        id,
        name: payload.name,
        description: payload.description,
        price: payload.price,
    }
    
    const params = {
        TableName: PRODUCT_TABLE,
        Key: { productId : id },
        AttributeUpdates: {
            name: {
                Value: payload.name,
                Action: 'PUT'
            },
            description: {
                Value: payload.description,
                Action: 'PUT'
            },
            price: {
                Value: payload.price,
                Action: 'PUT'
            }
        }
    }
    await dynamoDb.update(params).promise()
    
    return product
}

async function notifyProductUpdated(product) {
    const params = {
        TopicArn: EVENTS_TOPIC,
        Message: JSON.stringify({
            Records: [{
                eventSource: 'product-service',
                eventName: 'PRODUCT_UPDATED',
                eventVersion: '1.0',
                eventTime: new Date().toISOString(),
                payload: product
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