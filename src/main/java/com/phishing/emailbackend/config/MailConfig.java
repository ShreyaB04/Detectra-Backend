package com.phishing.emailbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    /**
     * Mail configuration bean
     * (Test email / future integration)
     */
    @Bean
    public JavaMailSender javaMailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // 🔹 Dummy / placeholder configuration
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        // ⚠️ Credentials intentionally not hardcoded
        // mailSender.setUsername("test.email@gmail.com");
        // mailSender.setPassword("app-password");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");

        return mailSender;
    }
}
