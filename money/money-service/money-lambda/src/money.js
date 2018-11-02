exports.times = function(money, multiplier) {
    return {amount: money.amount * multiplier, currency: money.currency}
}

exports.reduce = async function(money, currency, exchange) {
    const rate = await exchange.rate(money.currency, currency)        
    return {amount: money.amount * rate, currency}
}