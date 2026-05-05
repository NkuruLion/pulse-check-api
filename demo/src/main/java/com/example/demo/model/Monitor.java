package com.example.demo.model;

import javax.annotation.processing.Generated;

public class Monitor {
    private String id;
    private int timeout;
    private String alertEmail;
    private boolean isPaused;
    private long expiryTime;

    // getters & setters


    public Monitor() {
    }

    public Monitor(String id, int timeout, String alertEmail, boolean isPaused, long expiryTime) {
        this.id = id;
        this.timeout = timeout;
        this.alertEmail = alertEmail;
        this.isPaused = isPaused;
        this.expiryTime = expiryTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getAlertEmail() {
        return alertEmail;
    }

    public void setAlertEmail(String alertEmail) {
        this.alertEmail = alertEmail;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(long expiryTime) {
        this.expiryTime = expiryTime;
    }
}