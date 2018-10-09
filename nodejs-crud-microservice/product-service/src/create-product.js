const AWS = require('aws-sdk')
const uuidv1 = require('uuid/v1')

const PRODUCT_TABLE = process.env.PRODUCT_TABLE
const EVENTS_TOPIC = process.env.EVENTS_TOPIC

const dynamoDb = new AWS.DynamoDB.DocumentClient({apiVersion: '2012-08-10'})
const sns = new AWS.SNS({apiVersion: '2010-03-31'})

exports.create = async function(payload) {
    try {
        const product = await createProduct(payload)
        
        await notifyProductCreated(product)
        
        return product
    } catch (ex) {
        console.error('ERROR', JSON.stringify(ex))
    }
}

async function createProduct(payload) {
    const id = uuidv1()
    const product = {
        id,
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