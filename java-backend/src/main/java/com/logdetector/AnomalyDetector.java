package com.logdetector;

import java.util.*;

public class AnomalyDetector {

    // Detects repeated failures from same IP - Brute Force pattern
    public List<String> detectBruteForce(List<LogEntry> entries) {
        Map<String, Integer> ipFailCount = new HashMap<>();
        List<String> alerts = new ArrayList<>();

        for (LogEntry entry : entries) {
            if (entry.getMessage().contains("Failed login") && entry.getIpAddress() != null) {
                ipFailCount.merge(entry.getIpAddress(), 1, Integer::sum);
            }
        }

        for (Map.Entry<String, Integer> e : ipFailCount.entrySet()) {
            if (e.getValue() >= 3) {
                alerts.add("🚨 BRUTE FORCE DETECTED: IP " + e.getKey() +
                           " failed login " + e.getValue() + " times!");
            }
        }
        return alerts;
    }

    // Detects repeated ERROR bursts in short time
    public List<String> detectErrorBurst(List<LogEntry> entries) {
        List<String> alerts = new ArrayList<>();
        int errorCount = 0;

        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).getSeverity() == LogEntry.Severity.ERROR) {
                errorCount++;
                if (errorCount >= 3) {
                    alerts.add("🚨 ERROR BURST DETECTED: " + errorCount +
                               " consecutive errors around " + entries.get(i).getTimestamp());
                    errorCount = 0;
                }
            } else {
                errorCount = 0;
            }
        }
        return alerts;
    }

    // Detects critical system events
    public List<String> detectCriticalEvents(List<LogEntry> entries) {
        List<String> alerts = new ArrayList<>();

        for (LogEntry entry : entries) {
            if (entry.getSeverity() == LogEntry.Severity.CRITICAL) {
                alerts.add("🔴 CRITICAL EVENT: " + entry.getMessage() +
                           " at " + entry.getTimestamp());
            }
        }
        return alerts;
    }

    // Detects suspicious keywords in logs
    public List<String> detectSuspiciousActivity(List<LogEntry> entries) {
        List<String> alerts = new ArrayList<>();
        String[] suspiciousKeywords = {
            "SQL injection", "unauthorized", "passwd",
            "attack", "blocked", "exploit"
        };

        for (LogEntry entry : entries) {
            for (String keyword : suspiciousKeywords) {
                if (entry.getMessage().toLowerCase().contains(keyword.toLowerCase())) {
                    alerts.add("⚠️ SUSPICIOUS ACTIVITY: '" + keyword +
                               "' detected - " + entry.getMessage());
                    break;
                }
            }
        }
        return alerts;
    }
}