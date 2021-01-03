async function log(url, { clientId, level, message }) {
  return await fetch(url, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ message, logLevel: level, clientId }),
  });
}

const LOG_LEVELS = {
  info: "info",
  warn: "warn",
  debug: "debug",
  error: "error",
};

module.exports = { LOG_LEVELS, log };
