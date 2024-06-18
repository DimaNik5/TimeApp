package com.example.time;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.time.adapter.PlayerAdapter;
import com.example.time.database.DataBase;
import com.example.time.model.Command;
import com.example.time.user.ComUser;

public class PlayersActivity extends AppCompatActivity {

    RecyclerView timePlayerRecycler;
    PlayerAdapter playerAdapter;

    Command command;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_players);

        command = new Command("", ComUser.getLoginLastCom());
        setPlayerRecycler();
        DataBase.getListPlayerDataFromDB(command, playerAdapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setPlayerRecycler() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        timePlayerRecycler = findViewById(R.id.playerRecycler);
        timePlayerRecycler.setLayoutManager(layoutManager);

        playerAdapter = new PlayerAdapter(this, command);
        timePlayerRecycler.setAdapter(playerAdapter);

    }
}