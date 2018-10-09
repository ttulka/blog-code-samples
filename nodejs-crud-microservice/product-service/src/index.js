const list = require('./list-products').list
const get = require('./get-product').get
const create = require('./create-product').create
const update = require('./update-product').update
const remove = require('./remove-product').remove

exports.handler = async function(event) {
    if (!event.httpMethod) {
        return buildResponse(400, {error: 'Wrong request format - no method specified.'})
    }
    
    try {
        const request = {
            resource: event.resource,
            method: event.httpMethod,
            body: event.body,
            parameters: event.pathParameters,
        }

        return await dispatch(request)

    } catch (error) {
        console.error('ERROR', error);
        return error.statusCode && error.body
            ? error
            : buildResponse(500, {error: error.toString()})
    }
}

async function dispatch(request) {
    if (request.resource === '/') {  
      
        switch (request.method) {
            case 'GET':
                return await dispatchList(request)
            case 'POST':
                return await dispatchCreate(request)
            default:
                return buildResponse(405, {error: 'Wrong request method - only GET and POST are supported for this resource.'}) 
        }
    }
    else if (request.resource === '/{id}') {
                                                                                                                          
        if (!request.parameters || !request.parameters.id) {
            return buildResponse(404, {error: 'Wrong request - product ID must be set as a parameter.'})
        }
        
        switch (request.method) {
            case 'GET':
                return await dispatchGet(request)
            case 'PUT':
                return await dispatchUpdate(request)
            case 'DELETE':
                return await dispatchRemove(request)
            default:
                return buildResponse(405, {error: 'Wrong request method - only GET and POST are supported for this resource.'}) 
        }
    } else {
        return buildResponse(404, {error: 'Wrong request - resource does not exist.'})
    }
}

async function dispatchList(request) {
    const productsList = await list()
    return buildResponse(200, productsList)
}

async function dispatchCreate(request) {
    if (!request.body) {
        return buildResponse(400, {error: 'Wrong request - body payload must be set.'})
    }
    
    if (!request.body.name || !request.body.description || !request.body.price) {
        return buildResponse(400, {error: 'Wrong request - body payload must contain attributes [name, description, price].'})
    }
    
    if (isNaN(request.body.price)) {
        return buildResponse(400, {error: 'Wrong request - attribute [price] must a number.'})
    }
    
    const newProduct = await create(request.body)
    return buildResponse(201, newProduct)
}

async function dispatchGet(request) {
    const product = await get(request.parameters.id)
    return buildResponse(200, product)
}

async function dispatchUpdate(request) {
    if (!request.body) {
        return buildResponse(400, {error: 'Wrong request - body payload must be set.'})
    }
    
    if (!request.body.name || !request.body.description || !request.body.price) {
        return buildResponse(400, {error: 'Wrong request - body payload must contain attributes [name, description, price].'})
    }
    
    if (isNaN(request.body.price)) {
        return buildResponse(400, {error: 'Wrong request - attribute [price] must a number.'})
    }
    
    const productUpdated = await update(request.parameters.id, request.body)
    if (productUpdated) {
        return buildResponse(200)
    } else {
        return buildResponse(404, {error: 'Product was not found.'})
    }
}

async function dispatchRemove(request) {    
    const productRemoved = await remove(request.parameters.id)
    if (productRemoved) {
        return buildResponse(200)
    } else {
        return buildResponse(404, {error: 'Product was not found.'})
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