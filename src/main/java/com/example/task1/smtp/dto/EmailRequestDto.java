package com.example.task1.smtp.dto;


import com.example.task1.smtp.EmailTemplateType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailRequestDto {
    private String to;
    private String subject;
    private String body;
    private EmailTemplateType templateType;
}