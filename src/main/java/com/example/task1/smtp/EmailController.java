package com.example.task1.smtp;

import com.example.task1.smtp.dto.EmailRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;


    @PostMapping("/send-html-email")
    public String sendHtmlEmail(@RequestBody EmailRequestDto emailRequest) {

        Context context = new Context();
        context.setVariable("message", emailRequest.getBody());

        emailService.sendEmailWithTemplateType(emailRequest.getTo(),
                emailRequest.getSubject(), emailRequest.getTemplateType(), context);

        return "HTML email sent successfully!";
    }
}