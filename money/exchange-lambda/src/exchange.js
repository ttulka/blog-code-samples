exports.rate = async function(origin, target) {
    // fake it
    if (origin !== 'EUR' && origin !== 'USD' && target !== 'EUR' && target !== 'USD') {
        throw new ValidationError('Currency must be either EUR or USD')
    }
    if (origin === target) {
        return 1;
    }
    return origin === 'EUR' ? 1.13 : 0.88
}

class ValidationError extends Error {
    constructor(type, ...params) {
        super(...params);
        this.type = 'validation';
    }
}