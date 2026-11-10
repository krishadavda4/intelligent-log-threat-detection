package com.logdetector;

import java.time.LocalDateTime;

public class LogEntry {

    public enum Severity {
        INFO, WARNING, ERROR, CRITICAL, UNKNOWN
    }

    private LocalDateTime timestamp;
    private Severity severity;
    private String message;
    private String rawLine;
    private String ipAddress;

    public LogEntry(LocalDateTime timestamp, Severity severity, String message, String rawLine) {
        this.timestamp = timestamp;
        this.severity = severity;
        this.message = message;
        this.rawLine = rawLine;
        this.ipAddress = extractIP(message);
    }

    private String extractIP(String message) {
        if (message.contains("ip=")) {
            String[] parts = message.split("ip=");
            if (parts.length > 1) {
                return parts[1].split(" ")[0];
            }
        }
        return null;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public Severity getSeverity() { return severity; }
    public String getMessage() { return message; }
    public String getRawLine() { return rawLine; }
    public String getIpAddress() { return ipAddress; }

    @Override
    public String toString() {
        return "[" + severity + "] " + timestamp + " - " + message;
    }
}