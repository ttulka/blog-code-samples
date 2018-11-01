exports.times = function(money, multiplier) {
    return {amount: money.amount * multiplier, currency: money.currency}
}

exports.reduce = async function(money, currency, exchange) {
    const rate = exchange.rate(money.currency, currency)        
    return {amount: money.amount * rate, currency}
}

class ValidationError extends Error {
    constructor(type, ...params) {
        super(...params);
        this.type = 'validation';
    }
}