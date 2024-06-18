package com.example.time;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.time.adapter.ModePlayerAdapter;
import com.example.time.database.DataBase;
import com.example.time.model.Command;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class ModePlayersListActivity extends AppCompatActivity {

    RecyclerView playerRecycler;
    ModePlayerAdapter modePlayerAdapter;
    TextView btnAdd;
    ConstraintLayout root;
    Command command;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mode_players_list);

        command = new Command(getIntent().getStringExtra("comName"), getIntent().getStringExtra("logName"));
        setPlayerRecycler();
        DataBase.getListComDataFromDB(command, modePlayerAdapter);

        root = findViewById(R.id.main);
        btnAdd = findViewById(R.id.addPlayer);
        btnAdd.setOnClickListener(v -> showCreateWindow());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showCreateWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Добавить новго участника");

        LayoutInflater inflater = LayoutInflater.from(this);
        View createWindow = inflater.inflate(R.layout.create_command_window, null);
        dialog.setView(createWindow);

        final TextInputEditText name = createWindow.findViewById(R.id.nameCom);
        //final TextInputEditText login = createWindow.findViewById(R.id.logCom);
        TextInputLayout namep = createWindow.findViewById(R.id.nameplace);
        TextInputLayout loginp = createWindow.findViewById(R.id.logplace);
        namep.setHint("Имя");
        loginp.setVisibility(View.INVISIBLE);

        dialog.setNegativeButton("Отменить", (dialogInterface, which) -> dialogInterface.dismiss());

        dialog.setPositiveButton("Создать", (dialogInterface, which) -> {
            String text = name.getText().toString();
            if(text.isEmpty()){
                Snackbar.make(root,"Не все поля заполнены",Snackbar.LENGTH_SHORT).show();
                return;
            }

            command.addPlayer(text);
            DataBase.saveCom(command);
        });

        dialog.show();
    }

    private void setPlayerRecycler() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        playerRecycler = findViewById(R.id.playerRecycler);
        playerRecycler.setLayoutManager(layoutManager);

        modePlayerAdapter = new ModePlayerAdapter(this, command);
        playerRecycler.setAdapter(modePlayerAdapter);

    }


}