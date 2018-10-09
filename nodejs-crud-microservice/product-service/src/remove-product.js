const AWS = require('aws-sdk')

const PRODUCT_TABLE = process.env.PRODUCT_TABLE
const EVENTS_TOPIC = process.env.EVENTS_TOPIC

const dynamoDb = new AWS.DynamoDB.DocumentClient({apiVersion: '2012-08-10'})
const sns = new AWS.SNS({apiVersion: '2010-03-31'})

exports.remove = async function(id) {
    try {
        await removeProduct(id)
        
        await notifyProductRemoved(id)
        
        return true
        
    } catch (ex) {
        console.error('ERROR', JSON.stringify(ex))
        return false
    }
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