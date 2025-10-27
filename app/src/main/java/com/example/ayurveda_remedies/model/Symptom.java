package com.example.ayurveda_remedies.model;

public class Symptom {
    public long timestamp;
    public String date;
    public String text;

    public Symptom(long timestamp, String date, String text) {
        this.timestamp = timestamp;
        this.date = date;
        this.text = text;
    }
}
