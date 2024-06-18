package com.example.time;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.time.adapter.ActionAdapter;
import com.example.time.model.Action;
import com.example.time.user.Admin;
import com.example.time.user.User;

import java.util.List;

public class AdminActivity extends AppCompatActivity {

    RecyclerView actionRecycler;
    ActionAdapter actionAdapter;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);

        user = new Admin();
        setActionRecycler(user.getActionList());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setActionRecycler(List<Action> actionList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        actionRecycler = findViewById(R.id.actionRecycler);
        actionRecycler.setLayoutManager(layoutManager);

        actionAdapter = new ActionAdapter(this, actionList);
        actionRecycler.setAdapter(actionAdapter);

    }

}