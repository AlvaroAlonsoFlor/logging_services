package com.github.alvaroalonsoflor.logwriter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alvaroalonsoflor.logwriter.model.LogParameters;
import com.github.alvaroalonsoflor.logwriter.service.LogWriterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {LogWriterController.class, LogWriterService.class})
@AutoConfigureMockMvc
public class LogWriterControllerTest {


    @Autowired
    private LogWriterController logWriterController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    LogWriterService mockLogWriterService;

    @Test
    public void logWriterControllerShouldCallServiceSuccessfully() throws Exception {
        LogParameters logParameters = new LogParameters();
        logParameters.setClientId("testId");
        logParameters.setLogLevel("warning");
        logParameters.setMessage("test message");
        final String body = new ObjectMapper().writeValueAsString(logParameters);

        mockMvc.perform(post("/log-data").contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isOk());
        verify(mockLogWriterService).writeWithLogLevels(body);
    }
}
