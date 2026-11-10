package com.logdetector;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

public class AlertEngine {

    private static final String REPORT_PATH = "reports/threat_report.txt";

    public void printAlerts(List<String> alerts, ThreatClassifier.ThreatLevel level, String recommendation) {
        System.out.println("\n========================================");
        System.out.println("   🛡️  THREAT DETECTION REPORT");
        System.out.println("========================================");
        System.out.println("🕐 Scan Time : " + LocalDateTime.now());
        System.out.println("🔎 Anomalies Found : " + alerts.size());
        System.out.println("⚡ Threat Level : " + level);
        System.out.println("----------------------------------------");

        if (alerts.isEmpty()) {
            System.out.println("✅ No anomalies detected. System is clean.");
        } else {
            for (String alert : alerts) {
                System.out.println(alert);
            }
        }

        System.out.println("----------------------------------------");
        System.out.println("💡 Recommendation: " + recommendation);
        System.out.println("========================================\n");
    }

    public void saveReport(List<String> alerts, ThreatClassifier.ThreatLevel level, String recommendation) {
        try {
            new File("reports").mkdirs();
            BufferedWriter writer = new BufferedWriter(new FileWriter(REPORT_PATH));
            writer.write("THREAT DETECTION REPORT\n");
            writer.write("Generated: " + LocalDateTime.now() + "\n");
            writer.write("Threat Level: " + level + "\n");
            writer.write("----------------------------------------\n");
            for (String alert : alerts) {
                writer.write(alert + "\n");
            }
            writer.write("----------------------------------------\n");
            writer.write("Recommendation: " + recommendation + "\n");
            writer.close();
            System.out.println("📄 Report saved to: " + REPORT_PATH);
        } catch (IOException e) {
            System.err.println("Could not save report: " + e.getMessage());
        }
    }
}