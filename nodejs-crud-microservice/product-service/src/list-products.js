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

    try {
        const response = await dispatch()
        return buildResponse(201, response)

    } catch (error) {
        console.error('ERROR', error);
        return error.statusCode && error.body
                ? error
                : buildResponse(500, {error: error.toString()})
    }
}

async function dispatch() {
    return await listProducts()
}

async function listProducts(lastEvaluatedKey) {
    const params = {
        TableName: PRODUCT_TABLE
    }
    
    if (lastEvaluatedKey) {
        params.ExclusiveStartKey = lastEvaluatedKey
    }
    
    const res = await dynamoDb.scan(params).promise()
    
    const products = (res.Count && res.Items) 
        ? res.Items.map(item => ({ id: item.productId,
                                   name: item.name,
                                   description: item.description,
                                   price: item.price }))
        : []
        
    return products.concat(res.LastEvaluatedKey ? listProducts(res.LastEvaluatedKey) : [])
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