package com.example.time.user;

import androidx.annotation.NonNull;

import com.example.time.database.DataBase;
import com.example.time.model.Action;
import com.example.time.model.Command;

import java.util.List;

public class User {
    public final static String SPEC_ADMIN = "adm";
    public final static String SPEC_WAY = "way";
    public final static String SPEC_STORE = "str";
    public final static String SPEC_COMMAND = "com";
    private final static String PASS_ADMIN = "admin";
    private final static String PASS_WAY = "way";
    private final static String PASS_STORE = "str";
    //private final static String SPEC_COMMAND = "com";

    protected List<Action> actionList;

    public String getSpecId() {
        return specId;
    }

    protected String specId;

    public static int getLevel(@NonNull String password, Command com){
            switch (password) {
                case PASS_ADMIN:
                    return 0;
                case PASS_WAY:
                    return 1;
                case PASS_STORE:
                    return 2;
                default:
                    DataBase.SignOnLogin(com);
                    return 3;
            }

    }

    public List<Action> getActionList() {
        return actionList;
    }

}
