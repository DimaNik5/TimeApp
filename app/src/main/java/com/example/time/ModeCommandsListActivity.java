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

import com.example.time.adapter.ModeCommandAdapter;
import com.example.time.database.DataBase;
import com.example.time.model.Command;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class ModeCommandsListActivity extends AppCompatActivity {

    RecyclerView commandRecycler;
    ModeCommandAdapter modeCommandAdapter;
    TextView btnAdd;
    ConstraintLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mode_commands_list);

        List<Command> commandList = new ArrayList<>();
        setComRecycler(commandList);
        DataBase.getListComDataFromDB(commandList, modeCommandAdapter);

        root = findViewById(R.id.main);
        btnAdd = findViewById(R.id.addCommand);
        btnAdd.setOnClickListener(v -> showCreateWindow());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showCreateWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Создать новую команду");

        LayoutInflater inflater = LayoutInflater.from(this);
        View createWindow = inflater.inflate(R.layout.create_command_window, null);
        dialog.setView(createWindow);

        final TextInputEditText name = createWindow.findViewById(R.id.nameCom);
        final TextInputEditText login = createWindow.findViewById(R.id.logCom);

        dialog.setNegativeButton("Отменить", (dialogInterface, which) -> dialogInterface.dismiss());

        dialog.setPositiveButton("Создать", (dialogInterface, which) -> {
            String textName = name.getText().toString();
            textName = textName.replace(" ","");
            String textLog = login.getText().toString();
            textLog = textLog.replace(" ","");

            if(textName.isEmpty() || textLog.isEmpty()){
                Snackbar.make(root,"Не все поля заполнены",Snackbar.LENGTH_SHORT).show();
                return;
            }

            DataBase.saveCom(new Command(textName, textLog));
        });

        dialog.show();
    }

    private void setComRecycler(List<Command> commandList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        commandRecycler = findViewById(R.id.commandRecycler);
        commandRecycler.setLayoutManager(layoutManager);

        modeCommandAdapter = new ModeCommandAdapter(this, commandList);
        commandRecycler.setAdapter(modeCommandAdapter);

    }


}