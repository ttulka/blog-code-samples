const AWS = require('aws-sdk')

const PRODUCT_TABLE = process.env.PRODUCT_TABLE

const dynamoDb = new AWS.DynamoDB.DocumentClient({apiVersion: '2012-08-10'})

exports.handler = async function(event) {
    if (!event || !event.httpMethod) {
        return buildResponse(400, {error: 'Wrong request format.'})
    }
    if (event.httpMethod !== 'GET') {
        return buildResponse(405, {error: 'Wrong request method - only GET supported.'})
    }
    if (!event.pathParameters || !event.pathParameters.id) {
        return buildResponse(400, {error: 'Wrong request - parameter product ID must be set.'})
    }

    try {
        const response = await dispatch(event.pathParameters.id)
        if (response) {
            return buildResponse(200, response)
        } else {
            return buildResponse(404, {error: 'Product was not found.'})
        }
        
    } catch (ex) {
        console.error('ERROR', JSON.stringify(ex))
    }
}

async function dispatch(id) {
    return getProduct(id)
}

async function getProduct(id) {
    const params = {
        TableName: PRODUCT_TABLE,
        Key: { productId: id }
    }
    const res = await dynamoDb.get(params).promise()
    
    return (res.Item) 
        ? { id: res.Item.productId, name: res.Item.name, description: res.Item.description, price: res.Item.price }
        : null
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