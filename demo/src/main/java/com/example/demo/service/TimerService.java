package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.*;

@Service
public class TimerService {

    private final Map<String, ScheduledFuture<?>> timers = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

    @Autowired
    private com.example.demo.service.AlertService alertService;

    public void startTimer(String id, int timeout) {
        cancelTimer(id);

        ScheduledFuture<?> future = scheduler.schedule(() -> {
            alertService.triggerAlert(id);
        }, timeout, TimeUnit.SECONDS);

        timers.put(id, future);
    }

    public void resetTimer(String id, int timeout) {
        startTimer(id, timeout);
    }

    public void cancelTimer(String id) {
        ScheduledFuture<?> existing = timers.get(id);
        if (existing != null) {
            existing.cancel(false);
        }
    }
}