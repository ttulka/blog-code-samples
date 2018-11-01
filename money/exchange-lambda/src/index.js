const exchange = require('./exchange')

exports.handler = async function(event) {
    if (!event || !event.payload) {
        return buildResponse(400, {error: 'Wrong request format.'})
    }

    if (!event.payload.origin) {
        return buildResponse(400, {error: '"origin" must be set.'})
    }
    if (!event.payload.target) {
        return buildResponse(400, {error: '"target" must be set.'})
    }
    
    try {
        const rate = exchange.rate(event.payload.origin, event.payload.target)
    
        return buildResponse(200, {rate})
        
    } catch (e) {
        console.error(e)
        return buildResponse(e.type === 'validation' ? 400 : 500, {error: e.toString()})
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