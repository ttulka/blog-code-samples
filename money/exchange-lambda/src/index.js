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
        const rate = await exchange.rate(event.payload.origin, event.payload.target)
    
        return {rate}
        
    } catch (e) {
        console.error(e)
        return {error: e.toString()}
    }
}