package com.example.task1.smtp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailTemplateType {

    REGISTRATION("register"),
    PASSWORD("password");

    private final String templateName;

}
