package com.example.time.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Command {
    String name;
    List<Player> players;
    String login;
    int storeTime = 0;

    public boolean isLife() {
        return life;
    }

    public void setLife(boolean life) {
        this.life = life;
    }

    boolean life = true;

    public Command() {
        players = new ArrayList<>();
    }

    public Command(String name, String login)
    {
        this.login = login;
        this.name = name;
        players = new ArrayList<>();
        players.add(new Player(0, name));
    }

    public Command(String name, String login, List<Player> players) {
        this.login = login;
        this.name = name;
        this.players = players;
    }

    public int indexStringof(String name){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getName().equals(name)){
                return i;
            }
        }
        return -1;
    }

    public void copyContent(@NonNull Command other){
        life = other.life;
        name = other.name;
        players = other.players;
        login = other.login;
        storeTime = other.storeTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(String name) {
        this.players.add(new Player(players.size(), name));
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void takeTime(int time) {
        storeTime -= time;
    }

    public void giveTime(int time) {
        storeTime += time;
    }

    public int getStoreTime() {
        return storeTime;
    }

    public void setStoreTime(int storeTime) {
        this.storeTime = storeTime;
    }

    public int AllBonTime(){
        int res = storeTime;
        for(Player p : players){
            res += p.getBonusTime();
        }
        return res;
    }
}
