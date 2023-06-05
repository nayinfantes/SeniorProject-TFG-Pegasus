package com.example.app_pegasus.models;

public class CallLogItem {
    String number;
    String duration;
    String type;
    String date;

    public CallLogItem() {
    }

    public CallLogItem(String number, String duration, String type, String date) {
        this.number = number;
        this.duration = duration;
        this.type = type;
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
