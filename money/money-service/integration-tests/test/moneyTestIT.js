describe('money integration test suite', function() {
    
    const AWS = require('aws-sdk')
    const lambda = new AWS.Lambda({apiVersion: '2015-03-31'})
    
    const MONEY_LAMBDA = process.env.MONEY_LAMBDA
  
    it('multiplication test', async function() {
        const apiGatewayEvent = {
            "body": "{\"money\":{\"amount\":5,\"currency\":\"USD\"}, \"multiplier\":2}",
            "resource": "/times",
            "httpMethod": "POST"
        }
        const params = {
            FunctionName: MONEY_LAMBDA, 
            Payload: JSON.stringify(apiGatewayEvent)
        }
        const res = await lambda.invoke(params).promise()
        
        expect(res).toBeDefined()
        expect(res.FunctionError).toBeUndefined()
        
        const payload = JSON.parse(res.Payload)
        
        expect(payload.statusCode).toBe(200)
        
        const body = JSON.parse(payload.body)
        
        expect(body.amount).toBe(10)
        expect(body.currency).toBe('USD')
    })
    
    it('reduction test', async function() {
        const apiGatewayEvent = {
            "body": "{\"money\":{\"amount\":5,\"currency\":\"USD\"}, \"currency\":\"EUR\"}",
            "resource": "/reduce",
            "httpMethod": "POST"
        }
        const params = {
            FunctionName: MONEY_LAMBDA, 
            Payload: JSON.stringify(apiGatewayEvent)
        }
        const res = await lambda.invoke(params).promise()
        
        expect(res).toBeDefined()
        expect(res.FunctionError).toBeUndefined()
        
        const payload = JSON.parse(res.Payload)
        
        expect(payload.statusCode).toBe(200)
        
        const body = JSON.parse(payload.body)
        
        expect(body.amount).toBeDefined()
        expect(body.currency).toBe('EUR')
    })
})