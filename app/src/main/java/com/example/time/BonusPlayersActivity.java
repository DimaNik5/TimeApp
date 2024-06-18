package com.example.time;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.time.adapter.BonusPlayerAdapter;
import com.example.time.database.DataBase;
import com.example.time.model.Command;

public class BonusPlayersActivity extends AppCompatActivity {

    RecyclerView timePlayerRecycler;
    BonusPlayerAdapter bonusPlayerAdapter;

    Command command;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bonus_players);

        command = new Command(getIntent().getStringExtra("comName"), getIntent().getStringExtra("logName"));
        setPlayerRecycler();
        DataBase.getListPlayerDataFromDB(command, bonusPlayerAdapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setPlayerRecycler() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        timePlayerRecycler = findViewById(R.id.timePlayerRecycler);
        timePlayerRecycler.setLayoutManager(layoutManager);

        bonusPlayerAdapter = new BonusPlayerAdapter(this, command);
        timePlayerRecycler.setAdapter(bonusPlayerAdapter);

    }
}