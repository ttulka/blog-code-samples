const CURRENT_SERVICE_VERSION = process.env.CURRENT_SERVICE_VERSION

exports.handler = async function(event) {
    return {
        isBase64Encoded: false,
        statusCode: 200,
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({version: CURRENT_SERVICE_VERSION})
    }    
}