const AWS = require('aws-sdk')
const uuidv1 = require('uuid/v1')

const PRODUCT_TABLE = process.env.PRODUCT_TABLE
const EVENTS_TOPIC = process.env.EVENTS_TOPIC

const dynamoDb = new AWS.DynamoDB.DocumentClient({apiVersion: '2012-08-10'})
const sns = new AWS.SNS({apiVersion: '2010-03-31'})

exports.handler = async function(event) {
    if (!event || !event.httpMethod) {
        return buildResponse(400, {error: 'Wrong request format.'})
    }
    if (event.httpMethod !== 'POST') {
        return buildResponse(405, {error: 'Wrong request method - only POST supported.'})
    }
    if (!event.body) {
        return buildResponse(400, {error: 'Wrong request - body payload must be set.'})
    }
    if (!event.body.name || !event.body.description || !event.body.price) {
        return buildResponse(400, {error: 'Wrong request - body payload must contain attributes [name, description, price].'})
    }
    if (isNaN(event.body.price)) {
        return buildResponse(400, {error: 'Wrong request - attribute [price] must a number.'})
    }

    try {
        const response = await dispatch(event.body)
        return buildResponse(201, response)

    } catch (error) {
        console.error('ERROR', error);
        return error.statusCode && error.body
                ? error
                : buildResponse(500, {error: error.toString()})
    }
}

async function dispatch(payload) {
    const product = await createProduct(payload)

    await notifyProductCreated(product)

    return product
}

async function createProduct(payload) {
    const id = uuidv1()
    const product = {
        productId: id,
        name: payload.name,
        description: payload.description,
        price: payload.price,
    }
    
    const params = {
        TableName: PRODUCT_TABLE,
        Item: product
    }
    await dynamoDb.put(params).promise()
    
    return product
}

async function notifyProductCreated(product) {
    const params = {
        TopicArn: EVENTS_TOPIC,
        Message: JSON.stringify({
            Records: [{
                eventSource: 'product-service',
                eventName: 'PRODUCT_CREATED',
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