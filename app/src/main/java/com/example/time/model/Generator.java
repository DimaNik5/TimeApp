package com.example.time.model;

import androidx.annotation.NonNull;

import com.example.time.database.DataBase;
import com.example.time.user.User;

import java.util.Random;

public class Generator{
    private static Random random;
    private static StringBuilder code;
    @NonNull
    public static String generateCode(int minutes, @NonNull String specId, String comName){
        if(minutes <= 0) return "";
        delLastCode();
        code = new StringBuilder(specId + '_');
        if(specId.equals(User.SPEC_COMMAND)){
            code.append(comName).append('_');
        }
        if(random == null){
            random = new Random();
            random.setSeed(System.currentTimeMillis());
        }
        code.append(random.nextInt(1000));
        Store str = new Store(code.toString(), minutes);
        DataBase.saveNewStore(str);
        return code.toString();
    }

    private static void delLastCode(){
        if(code != null) {
            DataBase.delStore(code.toString());
            code = null;
        }
    }
    
}
