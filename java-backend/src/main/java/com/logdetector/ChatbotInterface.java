package com.logdetector;

import java.util.*;

public class ChatbotInterface {

    private List<String> anomalies;
    private ThreatClassifier.ThreatLevel threatLevel;
    private List<LogEntry> logEntries;

    public ChatbotInterface(List<String> anomalies,
                             ThreatClassifier.ThreatLevel threatLevel,
                             List<LogEntry> logEntries) {
        this.anomalies = anomalies;
        this.threatLevel = threatLevel;
        this.logEntries = logEntries;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n🤖 Log Analysis Chatbot Ready! Type 'exit' to quit.");
        System.out.println("💬 Ask me anything about your logs!\n");

        while (true) {
            System.out.print("You: ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("exit") || input.equals("quit")) {
                System.out.println("Bot: Goodbye! Stay secure. 🛡️");
                break;
            }

            System.out.println("Bot: " + getResponse(input) + "\n");
        }
    }

    private String getResponse(String input) {
        if (input.contains("threat") || input.contains("level")) {
            return "⚡ Current threat level is: " + threatLevel;
        }
        if (input.contains("how many") || input.contains("count") || input.contains("total")) {
            return "🔎 I found " + anomalies.size() + " anomalies in your logs.";
        }
        if (input.contains("brute force") || input.contains("brute")) {
            long count = anomalies.stream().filter(a -> a.contains("BRUTE FORCE")).count();
            return count > 0 ? "🚨 Yes! " + count + " brute force attack(s) detected!" 
                             : "✅ No brute force attacks detected.";
        }
        if (input.contains("sql") || input.contains("injection")) {
            long count = anomalies.stream().filter(a -> a.contains("SQL")).count();
            return count > 0 ? "🚨 SQL injection attempt detected in logs!" 
                             : "✅ No SQL injection attempts found.";
        }
        if (input.contains("critical")) {
            long count = anomalies.stream().filter(a -> a.contains("CRITICAL")).count();
            return count > 0 ? "🔴 " + count + " critical event(s) found!" 
                             : "✅ No critical events found.";
        }
        if (input.contains("error")) {
            long count = logEntries.stream()
                .filter(e -> e.getSeverity() == LogEntry.Severity.ERROR).count();
            return "⚠️ Found " + count + " ERROR level log entries.";
        }
        if (input.contains("safe") || input.contains("clean")) {
            return threatLevel == ThreatClassifier.ThreatLevel.SAFE
                ? "✅ Yes! Your system looks clean and safe."
                : "⚠️ No, threats were detected. Check the report!";
        }
        if (input.contains("show") || input.contains("list") || input.contains("all")) {
            if (anomalies.isEmpty()) return "✅ No anomalies found!";
            StringBuilder sb = new StringBuilder("Here are all anomalies:\n");
            for (String a : anomalies) sb.append("  → ").append(a).append("\n");
            return sb.toString();
        }
        if (input.contains("help")) {
            return "You can ask me:\n" +
                   "  → 'What is the threat level?'\n" +
                   "  → 'How many anomalies?'\n" +
                   "  → 'Any brute force attacks?'\n" +
                   "  → 'Any SQL injection?'\n" +
                   "  → 'Show all anomalies'\n" +
                   "  → 'Is the system safe?'";
        }
        return "🤔 I didn't understand that. Type 'help' to see what I can answer!";
    }
}