const list = require('./list-products').handler
const get = require('./get-product').handler
const create = require('./create-product').handler
const update = require('./update-product').handler
const remove = require('./remove-product').handler

exports.handler = async function(event) {
    if (!event || !event.resource || !event.httpMethod) {
        return buildResponse(400, {error: 'Wrong request format.'})
    }

    if (event.resource === '/') {

        switch (event.httpMethod) {
            case 'GET':
                return await list(event)
            case 'POST':
                return await create(event)
            default:
                return buildResponse(405, {error: 'Wrong request method - only GET and POST are supported for this resource.'})
        }
    }
    else if (event.resource === '/{id}') {

        switch (event.httpMethod) {
            case 'GET':
                return await get(event)
            case 'PUT':
                return await update(event)
            case 'DELETE':
                return await remove(event)
            default:
                return buildResponse(405, {error: 'Wrong request method - only GET and POST are supported for this resource.'})
        }
    } else {
        return buildResponse(404, {error: 'Resource does not exist.'})
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