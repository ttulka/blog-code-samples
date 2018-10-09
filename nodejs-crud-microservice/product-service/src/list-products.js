const AWS = require('aws-sdk')

const PRODUCT_TABLE = process.env.PRODUCT_TABLE

const dynamoDb = new AWS.DynamoDB.DocumentClient({apiVersion: '2012-08-10'})

exports.list = async function() {
    try {
        return listProducts()
        
    } catch (ex) {
        console.error('ERROR', JSON.stringify(ex))
    }
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
        ? res.Items.map(item => ({ id: item.productId, name: item.name, description: item.description, price: item.price }))
        : []
        
    return products.concat(res.LastEvaluatedKey ? listProducts(res.LastEvaluatedKey) : [])
}