const exchange = require('./exchange')

exports.handler = async function(event) {
    if (!event.origin) {
        throw new Error('"origin" must be set.')
    }
    if (!event.target) {
        throw new Error('"target" must be set.')
    }
    
    try {
        const rate = await exchange.rate(event.origin, event.target)
    
        return {rate}
        
    } catch (e) {
        console.error(e)
        return {error: e.toString()}
    }
}