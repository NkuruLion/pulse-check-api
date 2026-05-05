package com.example.demo.controller;
import com.example.demo.service.TimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/monitors")
public class MonitorController {

    @Autowired
    private TimerService timerService;

    private final Map<String, com.example.demo.model.Monitor> monitors = new ConcurrentHashMap<>();

    @PostMapping
    public ResponseEntity<?> createMonitor(@RequestBody com.example.demo.model.Monitor monitor) {
        monitors.put(monitor.getId(), monitor);
        timerService.startTimer(monitor.getId(), monitor.getTimeout());
        return ResponseEntity.status(201).body("Monitor created");
    }

    @PostMapping("/{id}/heartbeat")
    public ResponseEntity<?> heartbeat(@PathVariable String id) {
        com.example.demo.model.Monitor monitor = monitors.get(id);

        if (monitor == null) {
            return ResponseEntity.status(404).body("Not Found");
        }

        if (monitor.isPaused()) {
            monitor.setPaused(false);
        }

        timerService.resetTimer(id, monitor.getTimeout());
        return ResponseEntity.ok("Heartbeat received");
    }

    @PostMapping("/{id}/pause")
    public ResponseEntity<?> pause(@PathVariable String id) {
        com.example.demo.model.Monitor monitor = monitors.get(id);

        if (monitor == null) {
            return ResponseEntity.status(404).body("Not Found");
        }

        monitor.setPaused(true);
        timerService.cancelTimer(id);

        return ResponseEntity.ok("Monitor paused");
    }
}