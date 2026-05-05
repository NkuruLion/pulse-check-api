package com.example.demo.service;


import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
public class AlertService {

    public void triggerAlert(String id) {
        System.out.println(Map.of(
                "ALERT", "Device " + id + " is down!",
                "time", Instant.now().toString()
        ));
    }
}