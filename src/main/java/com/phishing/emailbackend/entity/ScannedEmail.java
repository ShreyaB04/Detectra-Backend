package com.phishing.emailbackend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "scanned_emails")
public class ScannedEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_email")
    private String senderEmail;

    @Column(name = "email_subject")
    private String subject;

    @Column(name = "extracted_link", length = 1000)
    private String extractedLink;

    @Column(name = "scan_result")
    private String result;   // PHISHING / SAFE

    @Column(name = "risk_score")
    private Integer riskScore;

    @Column(name = "scanned_at")
    private LocalDateTime scannedAt;

    // ✅ Default constructor (JPA needs this)
    public ScannedEmail() {
    }

    // ✅ Parameterized constructor
    public ScannedEmail(String senderEmail, String subject, String extractedLink,
                        String result, Integer riskScore, LocalDateTime scannedAt) {
        this.senderEmail = senderEmail;
        this.subject = subject;
        this.extractedLink = extractedLink;
        this.result = result;
        this.riskScore = riskScore;
        this.scannedAt = scannedAt;
    }

    // ✅ Getters & Setters

    public Long getId() {
        return id;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getExtractedLink() {
        return extractedLink;
    }

    public void setExtractedLink(String extractedLink) {
        this.extractedLink = extractedLink;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }

    public LocalDateTime getScannedAt() {
        return scannedAt;
    }

    public void setScannedAt(LocalDateTime scannedAt) {
        this.scannedAt = scannedAt;
    }
}
