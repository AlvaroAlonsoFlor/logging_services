package com.github.alvaroalonsoflor.logwriter.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogParameters {
    private String message;
    private String logLevel;
    private String clientId;
}
