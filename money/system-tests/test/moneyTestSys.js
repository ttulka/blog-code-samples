describe('money system test suite', function() {
    
    const axios = require('axios')
    
    const FACADE_URL = process.env.FACADE_URL
  
    it('multiplication test', async function() {
        const params = {
            money: {
                amount: 5,
                currency: 'USD'
            }, 
            multiplier: 2
        }
        const res = await axios.post(FACADE_URL + '/times', params)
        
        expect(res).toBeDefined()
        expect(res.status).toBe(200)
        
        expect(res.data.amount).toBe(10)
        expect(res.data.currency).toBe('USD')
    })
    
    it('reduction test', async function() {
        const params = {
            money: {
                amount: 5,
                currency: 'USD'
            }, 
            currency: 'EUR'
        }
        const res = await axios.post(FACADE_URL + '/reduce', params)
                
        expect(res).toBeDefined()
        expect(res.status).toBe(200)
                
        expect(res.data.amount).toBeDefined()
        expect(res.data.currency).toBe('EUR')
    })
})