package com.logdetector;

import java.util.List;

public class ThreatClassifier {

    public enum ThreatLevel {
        SAFE, LOW, MEDIUM, HIGH, CRITICAL
    }

    public ThreatLevel classify(List<String> anomalies) {
        int score = 0;

        for (String anomaly : anomalies) {
            if (anomaly.contains("BRUTE FORCE"))       score += 40;
            if (anomaly.contains("SQL injection"))      score += 50;
            if (anomaly.contains("CRITICAL"))           score += 35;
            if (anomaly.contains("ERROR BURST"))        score += 20;
            if (anomaly.contains("SUSPICIOUS"))         score += 15;
            if (anomaly.contains("unauthorized"))       score += 25;
        }

        if (score == 0)   return ThreatLevel.SAFE;
        if (score < 20)   return ThreatLevel.LOW;
        if (score < 40)   return ThreatLevel.MEDIUM;
        if (score < 70)   return ThreatLevel.HIGH;
        return ThreatLevel.CRITICAL;
    }

    public String getRecommendation(ThreatLevel level) {
        switch (level) {
            case SAFE:     return "✅ System looks healthy. No action needed.";
            case LOW:      return "🟡 Minor issues found. Keep monitoring.";
            case MEDIUM:   return "🟠 Moderate threats detected. Review logs manually.";
            case HIGH:     return "🔴 High threat level! Investigate immediately.";
            case CRITICAL: return "🚨 CRITICAL THREAT! Take system offline and investigate NOW!";
            default:       return "Unknown threat level.";
        }
    }
}