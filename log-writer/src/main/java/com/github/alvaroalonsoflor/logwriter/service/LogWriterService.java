package com.github.alvaroalonsoflor.logwriter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alvaroalonsoflor.logwriter.model.LogParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LogWriterService {

    Logger logger = LoggerFactory.getLogger(LogWriterService.class);
    String CLIENT_ID = "Client id: ";
    String MESSAGE = "Message: ";

    public void writeWithLogLevels(String body) {
        logger.debug("Body received: " + body);
        try {
            LogParameters logParameters = new ObjectMapper().readValue(body, LogParameters.class);
            switch (logParameters.getLogLevel()) {
                case "warn":
                    logger.warn(CLIENT_ID + logParameters.getClientId());
                    logger.warn(MESSAGE + logParameters.getMessage());
                    break;
                case "info":
                    logger.info(CLIENT_ID + logParameters.getClientId());
                    logger.info(MESSAGE + logParameters.getMessage());
                    break;
                case "error":
                    logger.error(CLIENT_ID + logParameters.getClientId());
                    logger.error(MESSAGE + logParameters.getMessage());
                    break;
                case "debug":
                    logger.debug(CLIENT_ID + logParameters.getClientId());
                    logger.debug(MESSAGE + logParameters.getMessage());
                    break;
            }
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }

    }
}
