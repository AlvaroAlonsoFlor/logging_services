package com.github.alvaroalonsoflor.logwriter.service;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alvaroalonsoflor.logwriter.model.LogParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {LogWriterService.class}, properties = {"logging.level.root=DEBUG"})
public class LogWriterServiceTest {

    @Autowired
    private LogWriterService logWriterService;

    private LogParameters logParameters;

    private ListAppender<ILoggingEvent> listAppender;

    private final String DEFAULT_CLIENT_ID_MESSAGE_IN_LOGS = "Client id: testId";
    private final String DEFAULT_TEST_MESSAGE_IN_LOGS = "Message: Test message";
    private final String BODY_RECEIVED = "Body received: ";

    @BeforeEach
    public void setup() {
        logParameters = new LogParameters();
        logParameters.setMessage("Test message");
        logParameters.setClientId("testId");
        Logger logger = (Logger) LoggerFactory.getLogger(LogWriterService.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @Test
    public void shouldWriteInfoLogWithAMessageWhenPayloadIsCorrect() throws JsonProcessingException {
        logParameters.setLogLevel("info");
        final String parameters = new ObjectMapper().writeValueAsString(logParameters);
        logWriterService.writeWithLogLevels(parameters);
        assertEquals(BODY_RECEIVED + parameters, listAppender.list.get(0).getMessage());
        assertEquals(DEFAULT_CLIENT_ID_MESSAGE_IN_LOGS, listAppender.list.get(1).getMessage());
        assertEquals(DEFAULT_TEST_MESSAGE_IN_LOGS, listAppender.list.get(2).getMessage());
    }
    @Test
    public void shouldWriteWarningLogWithAMessageWhenPayloadIsCorrect() throws JsonProcessingException {
        logParameters.setLogLevel("warn");
        final String parameters = new ObjectMapper().writeValueAsString(logParameters);
        logWriterService.writeWithLogLevels(parameters);
        assertEquals(BODY_RECEIVED + parameters, listAppender.list.get(0).getMessage());
        assertEquals(DEFAULT_CLIENT_ID_MESSAGE_IN_LOGS, listAppender.list.get(1).getMessage());
        assertEquals(DEFAULT_TEST_MESSAGE_IN_LOGS, listAppender.list.get(2).getMessage());
    }

    @Test
    public void shouldWriteDebugLogWithAMessageWhenPayloadIsCorrect() throws JsonProcessingException {
        logParameters.setLogLevel("debug");
        final String parameters = new ObjectMapper().writeValueAsString(logParameters);
        logWriterService.writeWithLogLevels(parameters);
        assertEquals(BODY_RECEIVED + parameters, listAppender.list.get(0).getMessage());
        assertEquals(DEFAULT_CLIENT_ID_MESSAGE_IN_LOGS, listAppender.list.get(1).getMessage());
        assertEquals(DEFAULT_TEST_MESSAGE_IN_LOGS, listAppender.list.get(2).getMessage());
    }
    @Test
    public void shouldWriteErrorLogWithAMessageWhenPayloadIsCorrect() throws JsonProcessingException {
        logParameters.setLogLevel("error");
        final String parameters = new ObjectMapper().writeValueAsString(logParameters);
        logWriterService.writeWithLogLevels(parameters);
        assertEquals(BODY_RECEIVED + parameters, listAppender.list.get(0).getMessage());
        assertEquals(DEFAULT_CLIENT_ID_MESSAGE_IN_LOGS, listAppender.list.get(1).getMessage());
        assertEquals(DEFAULT_TEST_MESSAGE_IN_LOGS, listAppender.list.get(2).getMessage());
    }
    @Test
    public void shouldWriteJsonExceptionInLogs() throws JsonProcessingException {
        final String parameters = "thisIsNotValidJson";
        logWriterService.writeWithLogLevels(parameters);
        assertEquals(BODY_RECEIVED + parameters, listAppender.list.get(0).getMessage());
        assertTrue(listAppender.list.get(1).getMessage().contains("Unrecognized token 'thisIsNotValidJson'"));
    }
}
