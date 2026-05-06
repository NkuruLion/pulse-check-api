 Device Monitor API

A lightweight heartbeat monitoring API built with Spring Boot.  
Each monitor has a timeout window, and if no heartbeat arrives before the timer expires, the system triggers an alert.

# Architecture Diagram (Flow Chart)

I attachache the flow chart file above

## Setup Instructions

### Prerequisites

- Java 17+
- Gradle (optional, wrapper included)

### Run Locally

1. Open a terminal in `demo/`
2. Start the application:

```bash
./gradlew bootRun
```

On Windows PowerShell:

```powershell
.\gradlew.bat bootRun
```

The service starts on `http://localhost:8080` by default.

### Run Tests

```bash
./gradlew test
```

On Windows PowerShell:

```powershell
.\gradlew.bat test
```

## API Documentation

Base URL: `http://localhost:8080`

### 1) Create Monitor

- **Method:** `POST`
- **Path:** `/monitors`
- **Description:** Creates a monitor and starts its timeout timer.

Request body:

```json
{
  "id": "device-1",
  "timeout": 15,
  "alertEmail": "ops@example.com",
  "paused": false,
  "expiryTime": 0
}
```

Success response:

- **Status:** `201 Created`
- **Body:** `Monitor created`

---

### 2) Send Heartbeat

- **Method:** `POST`
- **Path:** `/monitors/{id}/heartbeat`
- **Description:** Resets timeout timer for the given monitor.

Success response:

- **Status:** `200 OK`
- **Body:** `Heartbeat received`

Error response:

- **Status:** `404 Not Found`
- **Body:** `Not Found`

---

### 3) Pause Monitor

- **Method:** `POST`
- **Path:** `/monitors/{id}/pause`
- **Description:** Marks monitor as paused and cancels active timer.

Success response:

- **Status:** `200 OK`
- **Body:** `Monitor paused`

Error response:

- **Status:** `404 Not Found`
- **Body:** `Not Found`

---

### Alert Behavior

When a monitor timer expires without receiving a heartbeat, an alert is triggered by `AlertService` and printed to the console with timestamp data.

Example alert output:

```text
{ALERT=Device device-1 is down!, time=2026-05-06T10:00:00Z}
```

## The Developer's Choice: Added Feature Explanation

### Feature: Automatic Resume on Heartbeat

If a monitor is currently paused and a heartbeat is received on `/monitors/{id}/heartbeat`, the system automatically unpauses it and restarts its timeout timer.

### Why this feature matters

- Reduces operational friction: clients do not need a separate "resume" endpoint.
- Makes monitor recovery fast and intuitive: sending heartbeat is enough to restore normal tracking.
- Helps prevent missed monitoring windows after temporary maintenance pauses.

### Where it is implemented

- Pause state check and auto-resume logic in `MonitorController`.
- Timer reset behavior in `TimerService`.
