# 🛡️ Intelligent Log Anomaly & Threat Detection System

An AI-powered system that analyzes server logs, detects security threats,
and provides a conversational chatbot interface for querying results.

## 🚀 Features
- Detects brute force attacks from repeated failed logins
- Identifies SQL injection attempts
- Flags critical system events
- Scores overall threat level (SAFE → CRITICAL)
- Saves detailed threat reports automatically
- Interactive AI chatbot to query findings

## 🧠 How It Works
1. Parses raw log files line by line
2. Runs multiple anomaly detection algorithms
3. Classifies threat level using weighted scoring
4. Generates a full threat report
5. Launches chatbot for conversational log analysis

## 🛠️ Tech Stack
- Java (Core engine)
- AIML (Chatbot conversation layer)
- Pattern Detection Algorithms (Anomaly detection)

## ▶️ How To Run
```bash
# Compile
javac -d java-backend/out java-backend/src/main/java/com/logdetector/*.java

# Run
java -cp java-backend/out com.logdetector.Main
```

## 📊 Sample Output
CRITICAL THREAT DETECTED

Brute Force Attack: IP 192.168.1.105 (5 attempts)
SQL Injection Attempt detected
Unauthorized access to /etc/passwd
Recommendation: Take system offline and investigate NOW!
