describe('exchange integration test suite', function() {
    
    const AWS = require('aws-sdk')
    const lambda = new AWS.Lambda({apiVersion: '2015-03-31'})
    
    const EXCHANGE_LAMBDA = process.env.EXCHANGE_LAMBDA
  
    it('rate test', async function() {
        const params = {
            FunctionName: EXCHANGE_LAMBDA, 
            Payload: JSON.stringify({'EUR', 'USD'})
        }
        const res = await lambda.invoke(params).promise()
        
        expect(res).toBeDefined()
        expect(res.FunctionError).toBeUndefined()
        
        const payload = JSON.parse(res.Payload)
        
        expect(payload.rate).toBeDefined()
    })
    
    it('rate identity test', async function() {
        const params = {
            FunctionName: EXCHANGE_SERVICE, 
            Payload: JSON.stringify({'USD', 'USD'})
        }
        const res = await lambda.invoke(params).promise()
        
        expect(res).toBeDefined()
        expect(res.FunctionError).toBeUndefined()
        
        const payload = JSON.parse(res.Payload)
        
        expect(payload.rate).toBe(1)
    })
})