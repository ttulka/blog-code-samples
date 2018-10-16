describe("Sample Test", function () {

    const axios = require('axios')

    const serviceUrl = process.env.SERVICE_ENDPOINT
    const currentVersion = 'v1'     // value from the stack

    it("Should return the correct version", async function () {

        const response = await axios.get(serviceUrl)

        expect(response).not.toBeNull()
        expect(response.status).toEqual(200)
        expect(response.data.version).toEqual(currentVersion)
    })
})
