async function log(url, { clientId, level, message }) {
  if (!(validateStringKey(clientId) && validateStringKey(level) && validateStringKey(message))) {
    await Promise.reject(new TypeError("clientId, level and message must be strings"))
  }

  return await fetch(url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ message, logLevel: level, clientId }),
  });
}

const validateStringKey = (key) => {
  return typeof key === "string";
}

const LOG_LEVELS = {
  info: "info",
  warn: "warn",
  debug: "debug",
  error: "error",
};

module.exports = { LOG_LEVELS, log };
