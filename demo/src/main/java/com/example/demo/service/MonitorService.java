package com.example.demo.service;
import com.example.demo.model.Monitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MonitorService {

    private final Map<String, Monitor> monitors = new ConcurrentHashMap<>();

    @Autowired
    private TimerService timerService;

    // ✅ Create Monitor
    public String createMonitor(Monitor monitor) {
        monitors.put(monitor.getId(), monitor);

        // Start timer
        timerService.startTimer(monitor.getId(), monitor.getTimeout());

        return "Monitor created successfully";
    }

    // ✅ Heartbeat (Reset Timer)
    public String heartbeat(String id) {
        com.example.demo.model.Monitor monitor = monitors.get(id);

        if (monitor == null) {
            throw new RuntimeException("Monitor not found");
        }

        // If paused → unpause automatically
        if (monitor.isPaused()) {
            monitor.setPaused(false);
        }

        timerService.resetTimer(id, monitor.getTimeout());

        return "Heartbeat received";
    }

    // ✅ Pause Monitor
    public String pauseMonitor(String id) {
        Monitor monitor = monitors.get(id);

        if (monitor == null) {
            throw new RuntimeException("Monitor not found");
        }

        monitor.setPaused(true);

        // Stop timer
        timerService.cancelTimer(id);

        return "Monitor paused";
    }

    // ✅ Get Monitor (Developer’s Choice Feature)
    public Monitor getMonitor(String id) {
        Monitor monitor = monitors.get(id);

        if (monitor == null) {
            throw new RuntimeException("Monitor not found");
        }

        return monitor;
    }

    // ✅ Optional: Delete Monitor (Extra feature)
    public String deleteMonitor(String id) {
        Monitor monitor = monitors.remove(id);

        if (monitor == null) {
            throw new RuntimeException("Monitor not found");
        }

        timerService.cancelTimer(id);

        return "Monitor deleted";
    }
}