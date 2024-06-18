package com.example.time.user;

import com.example.time.model.Action;

import java.util.ArrayList;

public class Admin extends User{

    public Admin() {
        specId = SPEC_ADMIN;
        actionList = new ArrayList<>();
        actionList.add(new Action(1, "Команды"));
        actionList.add(new Action(2, "Изменить команды"));
        actionList.add(new Action(3, "Изменить время"));
    }
}
