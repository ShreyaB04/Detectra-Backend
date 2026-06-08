package com.phishing.emailbackend.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.phishing.emailbackend.service.EmailScanService;

@Component
@EnableScheduling
public class EmailScanScheduler {

    @Autowired
    private EmailScanService emailScanService;

    /**
     * Auto email scan scheduler
     * Runs every 5 minutes
     */
    //@Scheduled(fixedRate = 300000) // 5 minutes
    public void autoScanEmails() {

        // 🔹 Dummy data for auto scan (demo purpose)
        String senderEmail = "test.sender@gmail.com";
        String subject = "Account Verification Required";
        String link = "http://fake-bank-login.verify-user.com";

        String result = emailScanService.detectPhishing(link);
        int riskScore = emailScanService.calculateRiskScore(result);

        emailScanService.saveScanResult(
                senderEmail,
                subject,
                link,
                result,
                riskScore
        );

        System.out.println("Auto scan executed by scheduler");
    }
}
