const AWS = require('aws-sdk')

const PRODUCT_TABLE = process.env.PRODUCT_TABLE

const dynamoDb = new AWS.DynamoDB.DocumentClient({apiVersion: '2012-08-10'})

exports.get = async function(id) {
    try {
        return getProduct(id)
        
    } catch (ex) {
        console.error('ERROR', JSON.stringify(ex))
    }
}

async function getProduct(id) {
    const params = {
        TableName: PRODUCT_TABLE,
        Key: { productId: id }
    }
    const res = await dynamoDb.get(params).promise()
    
    return (res.Item) 
        ? { id: res.Item.productId, name: res.Item.name, description: res.Item.description, price: res.Item.price }
        : []
}