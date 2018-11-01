describe('money test suite', function() {
    
    const money = require('../src/money')
  
    it('USD multiplication test', async function() {
        var result = await money.times({amount: 5, currency: 'USD'}, 2)
        expect(result).toBeDefined()        
        expect(result.amount).toBe(10)
        expect(result.currency).toBe('USD')
        
        result = await money.times({amount: 10, currency: 'USD'}, 3)        
        expect(result).toBeDefined()        
        expect(result.amount).toBe(30)
        expect(result.currency).toBe('USD')
    });
    
    it('EUR multiplication test', async function() {
        var result = await money.times({amount: 5, currency: 'EUR'}, 2)
        expect(result).toBeDefined()        
        expect(result.amount).toBe(10)
        expect(result.currency).toBe('EUR')
        
        result = await money.times({amount: 10, currency: 'EUR'}, 3)        
        expect(result).toBeDefined()        
        expect(result.amount).toBe(30)
        expect(result.currency).toBe('EUR')
    });
    
    it('equality test', async function() {
        const fiveDollars = {amount: 5, currency: 'USD'}
        var result = await money.equals(fiveDollars, fiveDollars)
        expect(result).toBe(true)
        
        const tenDollars = {amount: 10, currency: 'USD'}
        var result = await money.equals(fiveDollars, tenDollars)
        expect(result).toBe(false)
        
        const fileEuros = {amount: 5, currency: 'EUR'}
        var result = await money.equals(fileEuros, fileEuros)
        expect(result).toBe(true)
        
        var result = await money.equals(fileEuros, fiveDollars)
        expect(result).toBe(false)
    });
});