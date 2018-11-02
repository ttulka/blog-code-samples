describe('exchange test suite', function() {
    
    const exchange = require('../src/exchange')
  
    it('rate test', async function() {
        var rate
        rate = await exchange.rate('EUR', 'USD') 
        expect(rate).toBeDefined()
        
        rate = await exchange.rate('USD', 'EUR') 
        expect(rate).toBeDefined()
    })
    
    it('rate identity test', async function() {
        var rate
        rate = await exchange.rate('USD', 'USD') 
        expect(rate).toBe(1)
        
        rate = await exchange.rate('EUR', 'EUR') 
        expect(rate).toBe(1)
    })
})