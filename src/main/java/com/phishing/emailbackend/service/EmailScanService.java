package com.phishing.emailbackend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import com.phishing.emailbackend.entity.ScannedEmail;
import com.phishing.emailbackend.repository.ScannedEmailRepository;

@Service
public class EmailScanService {

    @Autowired
    private ScannedEmailRepository scannedEmailRepository;

    public ScannedEmail saveScanResult(String senderEmail,
                                       String subject,
                                       String extractedLink,
                                       String result,
                                       Integer riskScore) {

        ScannedEmail email = new ScannedEmail();
        email.setSenderEmail(senderEmail);
        email.setSubject(subject);
        email.setExtractedLink(extractedLink);
        email.setResult(result);
        email.setRiskScore(riskScore);
        email.setScannedAt(LocalDateTime.now());

        return scannedEmailRepository.save(email);
    }

    public List<ScannedEmail> getAllScannedEmails() {
        return scannedEmailRepository.findAll();
    }

    /**
     * 🔥 ML आधारित phishing detection (Flask API call)
     */
    public String detectPhishing(String combinedText) {

        try {
            String url = "http://localhost:5000/predict";

            RestTemplate restTemplate = new RestTemplate();

            Map<String, String> body = new HashMap<>();
            body.put("text", combinedText); // IMPORTANT

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> request =
                    new HttpEntity<>(body, headers);

            ResponseEntity<Map> response =
                    restTemplate.postForEntity(url, request, Map.class);

            return response.getBody().get("result").toString();

        } catch (Exception e) {
            System.out.println("ML API Error: " + e.getMessage());
        }

        return "UNKNOWN";
    }

    public int calculateRiskScore(String result) {
        if ("PHISHING".equalsIgnoreCase(result)) {
            return 85;
        }
        if ("SAFE".equalsIgnoreCase(result)) {
            return 10;
        }
        return 0;
    }
}