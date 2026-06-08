package com.phishing.emailbackend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.phishing.emailbackend.entity.ScannedEmail;
import com.phishing.emailbackend.service.EmailScanService;

@CrossOrigin(origins = "*")  // Allows frontend on another origin to connect
@RestController
@RequestMapping("/api")   
public class EmailScanController {

    @Autowired
    private EmailScanService emailScanService;

    /**
     * ✅ Health check API
     */
    @GetMapping("/health")
    public String healthCheck() {
        return "Backend is running successfully!";
    }

    /**
     * 🔥 Scan email using ML model (Flask API)
     * Accepts JSON with 'text' and 'url' fields
     */
    @PostMapping("/scan")
    public Map<String, Object> scanEmail(@RequestBody Map<String, String> payload) {

        // Extract text + url from JSON payload
        String text = payload.getOrDefault("text", "");
        String url = payload.getOrDefault("url", "");

        // Combine for ML input
        String fullText = text + " " + url;

        // Call ML model via service
        String mlPrediction = emailScanService.detectPhishing(fullText);

        // Map ML result to frontend-friendly label
        String result = mlPrediction.equalsIgnoreCase("fraud") ? "phishing" : "safe";

        // Optionally, calculate risk score (if needed)
        int riskScore = emailScanService.calculateRiskScore(mlPrediction);

        // Save to DB (optional, if you want to keep history)
        ScannedEmail savedEmail = emailScanService.saveScanResult(
                "N/A",      // senderEmail not required here
                text,
                url,
                result,
                riskScore
        );

        // Prepare response
        Map<String, Object> response = new HashMap<>();
        response.put("id", savedEmail.getId());
        response.put("text", savedEmail.getSubject());
        response.put("url", savedEmail.getExtractedLink());
        response.put("result", result);
        response.put("riskScore", riskScore);
        response.put("message", "Email scanned successfully");

        return response;
    }

    /**
     * 📜 Get all scanned emails (history)
     */
    @GetMapping("/history")
    public List<ScannedEmail> getScanHistory() {
        return emailScanService.getAllScannedEmails();
    }
}