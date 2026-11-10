package com.logdetector;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class LogParser {

    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public List<LogEntry> parseFile(String filePath) {
        List<LogEntry> entries = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    LogEntry entry = parseLine(line);
                    if (entry != null) {
                        entries.add(entry);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading log file: " + e.getMessage());
        }

        System.out.println("✅ Parsed " + entries.size() + " log entries.");
        return entries;
    }

    private LogEntry parseLine(String line) {
        try {
            // Format: 2024-01-15 08:23:11 INFO message
            String[] parts = line.split(" ", 4);
            if (parts.length < 4) return null;

            String dateStr = parts[0] + " " + parts[1];
            LocalDateTime timestamp = LocalDateTime.parse(dateStr, FORMATTER);

            LogEntry.Severity severity = parseSeverity(parts[2]);
            String message = parts[3];

            return new LogEntry(timestamp, severity, message, line);

        } catch (Exception e) {
            System.err.println("⚠️ Could not parse line: " + line);
            return null;
        }
    }

    private LogEntry.Severity parseSeverity(String level) {
        switch (level.toUpperCase()) {
            case "INFO":     return LogEntry.Severity.INFO;
            case "WARNING":  return LogEntry.Severity.WARNING;
            case "ERROR":    return LogEntry.Severity.ERROR;
            case "CRITICAL": return LogEntry.Severity.CRITICAL;
            default:         return LogEntry.Severity.UNKNOWN;
        }
    }
}