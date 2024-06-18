package com.example.time.model;

public class Player {
    int id;
    String name;
    int bonusTime = 0;

    public Player() {
    }

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBonusTime() {
        return bonusTime;
    }

    public void addBonusTime(int addBonusTime) {
        this.bonusTime += addBonusTime;
    }
}
