const money = require('./money')

exports.handler = async function(event) {
    if (!event) {
        return buildResponse(400, {error: 'Wrong request format.'})
    }
    
    try {
        return buildResponse(200, {})
        
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