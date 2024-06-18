package com.example.time.user;

import com.example.time.model.Action;

import java.util.ArrayList;

public class StrUser extends User{
    public StrUser(){
        specId = SPEC_STORE;
        actionList = new ArrayList<>();
        actionList.add(new Action(1, "Сгенерировать код"));
    }
}
