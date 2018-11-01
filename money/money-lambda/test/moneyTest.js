describe('money test suite', function() {
    
    const money = require('../src/money')
  
    it('multiplication test', function() {
        var result
        result = money.times({amount: 5, currency: 'USD'}, 2)
        expect(result).toBeDefined()        
        expect(result.amount).toBe(10)
        expect(result.currency).toBe('USD')
        
        result = money.times({amount: 10, currency: 'USD'}, 3)        
        expect(result).toBeDefined()        
        expect(result.amount).toBe(30)
        expect(result.currency).toBe('USD')
        
        result = money.times({amount: 5, currency: 'EUR'}, 2)
        expect(result).toBeDefined()        
        expect(result.amount).toBe(10)
        expect(result.currency).toBe('EUR')
        
        result = money.times({amount: 10, currency: 'EUR'}, 3)        
        expect(result).toBeDefined()        
        expect(result.amount).toBe(30)
        expect(result.currency).toBe('EUR')
    })
    
    it('reduction test', async function() {
        const fakeExchange = {
            rate: (origin, target) => {
                if (origin === target) return 1
                if (origin === 'EUR' && target === 'USD') return 1.5
                if (origin === 'USD' && target === 'EUR') return 0.5
                throw new Error('Unsupported currencies')
            }
        }
        const fiveDollars = {amount: 5, currency: 'USD'}
        const fiveEuros = {amount: 5, currency: 'EUR'}
        
        var result
        result = await money.reduce(fiveDollars, 'USD', fakeExchange)
        expect(result).toBeDefined()        
        expect(result.amount).toBe(5)
        expect(result.currency).toBe('USD')
        
        result = await money.reduce(fiveDollars, 'EUR', fakeExchange)
        expect(result).toBeDefined()        
        expect(result.amount).toBe(2.5)
        expect(result.currency).toBe('EUR')
        
        result = await money.reduce(fiveEuros, 'USD', fakeExchange)
        expect(result).toBeDefined()        
        expect(result.amount).toBe(7.5)
        expect(result.currency).toBe('USD')
    })
})