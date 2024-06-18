package com.example.time.model;

public class Store {
    String code;
    int time;

    public Store(String code, int time) {
        this.code = code;
        this.time = time;
    }

    public Store() {}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}

