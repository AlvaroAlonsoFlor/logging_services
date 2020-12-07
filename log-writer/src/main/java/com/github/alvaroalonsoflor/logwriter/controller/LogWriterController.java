package com.github.alvaroalonsoflor.logwriter.controller;


import com.github.alvaroalonsoflor.logwriter.service.LogWriterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogWriterController {

    private final LogWriterService logWriterService;

    public LogWriterController(LogWriterService logWriterService) {
        this.logWriterService = logWriterService;
    }

    @PostMapping("/log-data")
    void writeIntoLogs(@RequestBody String body) {
        logWriterService.writeWithLogLevels(body);
    }
}
