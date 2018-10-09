const AWS = require('aws-sdk')

const PRODUCT_TABLE = process.env.PRODUCT_TABLE
const EVENTS_TOPIC = process.env.EVENTS_TOPIC

const dynamoDb = new AWS.DynamoDB.DocumentClient({apiVersion: '2012-08-10'})
const sns = new AWS.SNS({apiVersion: '2010-03-31'})

exports.update = async function(id, payload) {
    try {
        const product = await updateProduct(id, payload)
        
        await notifyProductUpdated(product)
        
        return product
    
    } catch (ex) {
        console.error('ERROR', JSON.stringify(ex))
    }
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