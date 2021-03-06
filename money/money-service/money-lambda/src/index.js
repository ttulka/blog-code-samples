const AWS = require('aws-sdk')
const lambda = new AWS.Lambda({apiVersion: '2015-03-31'})

const money = require('./money')

const EXCHANGE_SERVICE = process.env.EXCHANGE_SERVICE

exports.handler = async function(event) {
    if (!event.resource || !event.body || event.httpMethod !== 'POST') {
        return buildResponse(400, {error: 'Wrong request format.'})
    }
    
    try {
        switch (event.resource) {
            case '/times': return await dispatchTimes(JSON.parse(event.body)) 
            case '/reduce': return await dispatchReduce(JSON.parse(event.body))
            default: buildResponse(400, {error: 'Unknown resource.'})
        }        
    } catch (e) {
        console.error(e)
        return buildResponse(500, {error: e.toString()})
    }
}

async function dispatchTimes(params) {
    if (!params.money) {
        return buildResponse(400, {error: '"money" query parameter must be set.'})
    }
    if (!params.multiplier) {
        return buildResponse(400, {error: '"multiplier" query parameter must be set.'})
    }

    const result = money.times(params.money, params.multiplier)
        
    return buildResponse(200, result)
}

async function dispatchReduce(params) {
    if (!params.money) {
        return buildResponse(400, {error: '"money" query parameter must be set.'})
    }
    if (!params.currency) {
        return buildResponse(400, {error: '"currency" query parameter must be set.'})
    }

    const result = await money.reduce(params.money, params.currency, exchange())
        
    return buildResponse(200, result)
}

function exchange() {
    return {
        rate: async (origin, target) => {
            const params = {
                FunctionName: EXCHANGE_SERVICE, 
                Payload: JSON.stringify({origin, target})
            }
            const res = await lambda.invoke(params).promise()
            const payload = JSON.parse(res.Payload)
            
            if (res.FunctionError) throw new Error(res.Payload)
            
            return payload.rate
        }    
    }
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