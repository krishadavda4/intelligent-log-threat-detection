package com.logdetector;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("🚀 Intelligent Log Anomaly & Threat Detection System");
        System.out.println("=====================================================\n");

        // Step 1: Parse logs
        LogParser parser = new LogParser();
        List<LogEntry> entries = parser.parseFile("logs/sample_logs.txt");

        // Step 2: Detect anomalies
        AnomalyDetector detector = new AnomalyDetector();
        List<String> allAnomalies = new ArrayList<>();
        allAnomalies.addAll(detector.detectBruteForce(entries));
        allAnomalies.addAll(detector.detectErrorBurst(entries));
        allAnomalies.addAll(detector.detectCriticalEvents(entries));
        allAnomalies.addAll(detector.detectSuspiciousActivity(entries));

        // Step 3: Classify threat level
        ThreatClassifier classifier = new ThreatClassifier();
        ThreatClassifier.ThreatLevel level = classifier.classify(allAnomalies);
        String recommendation = classifier.getRecommendation(level);

        // Step 4: Print and save report
        AlertEngine engine = new AlertEngine();
        engine.printAlerts(allAnomalies, level, recommendation);
        engine.saveReport(allAnomalies, level, recommendation);

        // Step 5: Start chatbot
        ChatbotInterface chatbot = new ChatbotInterface(allAnomalies, level, entries);
        chatbot.start();
    }
}