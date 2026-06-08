package com.phishing.emailbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phishing.emailbackend.entity.ScannedEmail;

@Repository
public interface ScannedEmailRepository extends JpaRepository<ScannedEmail, Long> {

    // 👉 Extra custom methods later (optional)
    // Example:
    // List<ScannedEmail> findByResult(String result);

}
