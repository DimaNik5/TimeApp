package com.example.time;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.time.adapter.ActionAdapter;
import com.example.time.adapter.NumberAdapter;
import com.example.time.database.DataBase;
import com.example.time.model.Action;
import com.example.time.model.Command;
import com.example.time.user.ComUser;
import com.example.time.user.User;

import java.util.ArrayList;
import java.util.List;

public class CommandActivity extends AppCompatActivity {
    static Command command;
    RecyclerView timeRecycler;
    NumberAdapter numberAdapter;
    static Time time;
    ImageView reload;
    List<Integer> num;
    TextView comName;
    User user;
    RecyclerView actionRecycler;
    ActionAdapter actionAdapter;
    SharedPreferences prefCom;
    SharedPreferences prefTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_command);

        prefCom = getSharedPreferences("USER", Context.MODE_PRIVATE);
        prefTime = getSharedPreferences("TIME", Context.MODE_PRIVATE);

        num = new ArrayList<>();
        num.add(0); num.add(0);
        num.add(0); num.add(0);
        num.add(0); num.add(0);

        comName = findViewById(R.id.comName);


        command = new Command(prefCom.getString("comName", ""), prefCom.getString("logName", ""));
        command.setStoreTime(prefCom.getInt("bonTime", 0));
        time = new Time(prefCom.getBoolean("life", false));
        time.setStart(prefTime.getLong("Start", 0L));
        time.setHave(prefTime.getLong("Have", 0L));
        comName.setText(prefCom.getString("comName", ""));

        time.setNumbers(num);
        setTimeRecycler(num);
        time.setAdapter(numberAdapter);
        update();

        reload = findViewById(R.id.imgReload);
        reload.setOnClickListener(v -> update());

        user = new ComUser();
        setActionRecycler(user.getActionList());
        ComUser.setLoginLastCom(command.getLogin());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void update(){
        DataBase.SignOnLogin(command);
        time.update(command);
        if(time.getHave() != prefTime.getLong("Have", 0) || time.getStart() != prefTime.getLong("Start", 0)) {
            SharedPreferences.Editor editor = prefTime.edit();
            editor.putLong("Start", time.getStart());
            editor.putLong("Have", time.getHave());
            editor.apply();
        }
    }

    private void setTimeRecycler(List<Integer> timeList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true);

        timeRecycler = findViewById(R.id.numRecycler);
        timeRecycler.setLayoutManager(layoutManager);

        numberAdapter = new NumberAdapter(this, timeList);
        timeRecycler.setAdapter(numberAdapter);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        time.delTimer();
        SharedPreferences.Editor editor = prefCom.edit();
        editor.putString("userId", User.SPEC_COMMAND);
        editor.putString("logName", command.getLogin());
        editor.putString("comName", command.getName());
        editor.putBoolean("life", command.isLife());
        editor.putInt("bonTime", command.AllBonTime());
        editor.apply();
    }

    private void setActionRecycler(List<Action> actionList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        actionRecycler = findViewById(R.id.actionRecycler);
        actionRecycler.setLayoutManager(layoutManager);

        actionAdapter = new ActionAdapter(this, actionList);
        actionRecycler.setAdapter(actionAdapter);

    }

    public static Command getCom(){
        return command;
    }

    public static long getHaveTime(){
        return time.haveTimeCom();
    }


}