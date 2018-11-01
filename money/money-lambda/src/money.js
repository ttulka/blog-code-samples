exports.times = async function(money, multiplier) {
    return {amount: money.amount * multiplier, currency: money.currency}
}

exports.equals = async function(money1, money2) {
    return money1.amount === money2.amount 
        && money1.currency === money2.currency
}

class ValidationError extends Error {
    constructor(type, ...params) {
        super(...params);
        this.type = 'validation';
    }
}