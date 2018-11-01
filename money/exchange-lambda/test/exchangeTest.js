describe("exchange test suite", function() {
    
    const exchange = require('../src/exchange')
  
    it("rate should be defined for EUR/USD exchange", async function() {
        const rate = await exchange.rate('EUR', 'USD') 
        expect(rate).toBeDefined();
    });
    
    it("rate should be defined for USD/EUR exchange", async function() {
        const rate = await exchange.rate('USD', 'EUR') 
        expect(rate).toBeDefined();
    });
    
    it("rate should be 1 for the same currency", async function() {
        const rate = await exchange.rate('USD', 'USD') 
        expect(rate).toBe(1);
    });
});