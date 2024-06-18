package com.example.time.user;

import com.example.time.model.Action;

import java.util.ArrayList;

public class ComUser extends User{
    private static String loginLastCom = "";
    public ComUser(){
        specId = SPEC_COMMAND;
        actionList = new ArrayList<>();
        actionList.add(new Action(1, "Участники"));
        actionList.add(new Action(2, "Сгенерировать код"));
        actionList.add(new Action(3, "Ввести код"));
    }
    public static String getLoginLastCom(){
        return loginLastCom;
    }

    public static void setLoginLastCom(String login) {
        loginLastCom = login;
    }
}
