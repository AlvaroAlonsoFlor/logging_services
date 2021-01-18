const { LOG_LEVELS, log } = require("./index");
const { createServer, Response } = require("miragejs");
require("node-fetch");

beforeAll(() => {
  const server = createServer({
    routes() {
      this.post("http://localhost:8080/log-data", (schema, request) => {
        let attrs = JSON.parse(request.requestBody);
        if (!(attrs.clientId && attrs.logLevel && attrs.message)) {
          return new Response(500, {}, { error: "test message" });
        }
      });
    },
  });
  server.logging = false;
});

test("It should return a successful response when called with the right parameters", async () => {
  const response = await log("http://localhost:8080/log-data", {
    clientId: "test123",
    level: LOG_LEVELS.info,
    message: "This is a test message",
  });
  expect(response.ok).toBeTruthy();
  expect(response.status).toBe(201);
});

test("It should throw an error if required keys are not the right type", async () => {
  const malformedParams = async () => {
    await log("http://localhost:8080/log-data", {
      malformedKey: "test123",
    })
  }

  return expect(malformedParams()).rejects.toThrowError(new TypeError("clientId, level and message must be strings"));

});
