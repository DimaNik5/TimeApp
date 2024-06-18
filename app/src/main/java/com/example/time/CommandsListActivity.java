package com.example.time;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.time.adapter.CommandAdapter;
import com.example.time.database.DataBase;
import com.example.time.model.Command;

import java.util.ArrayList;
import java.util.List;

public class CommandsListActivity extends AppCompatActivity {

    RecyclerView commandRecycler;
    CommandAdapter commandAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_commands_list);

        List<Command> commandList = new ArrayList<>();
        setComRecycler(commandList);
        DataBase.getListComDataFromDB(commandList, commandAdapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setComRecycler(List<Command> commandList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        commandRecycler = findViewById(R.id.commandRecycler);
        commandRecycler.setLayoutManager(layoutManager);

        commandAdapter = new CommandAdapter(this, commandList);
        commandRecycler.setAdapter(commandAdapter);

    }
}