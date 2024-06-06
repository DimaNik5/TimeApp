package com.example.time.user;

import android.content.SharedPreferences;

public class User {
    // название "области"
    final public static String NAME_TABLE = "user";
    // специальные id для пользователей с разными уровнями доступа
    final public static String SPEC_COMAND = "com";
    final public static String SPEC_ADMIN = "adm";
    final public static String SPEC_WAY = "way";
    final public static String SPEC_STORE = "str";
    // специальный id для пользователя по определённому уровню доступа
    protected String specId;

    public String getSpecId() {
        return specId;
    }
    // создать сделку на время
    public void createTrade(){

    }
    // принять сделку
    public void closeTrade(){

    }
    // загружает информацию об ползователе из внутреней памяти программы
    public void loadInfo(SharedPreferences pref) {}
}
