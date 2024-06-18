package com.example.time;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.time.database.DataBase;
import com.example.time.model.Command;
import com.example.time.model.Generator;
import com.example.time.user.ComUser;
import com.example.time.user.StrUser;
import com.example.time.user.User;
import com.google.android.material.textfield.TextInputEditText;

public class GeneratorActivity extends AppCompatActivity {
    TextInputEditText timeInput;
    TextView btnGen, textCode;
    User user;
    Command command;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_generator);

        if(!ComUser.getLoginLastCom().isEmpty()){
            user = new ComUser();
            command = new Command("", ComUser.getLoginLastCom());
            DataBase.SignOnLogin(command);
        }
        else {
            user = new StrUser();
            command = new Command("","");
        }

        timeInput = findViewById(R.id.timeInput);
        textCode = findViewById(R.id.textCode);
        btnGen = findViewById(R.id.btnGen);
        btnGen.setOnClickListener(v -> generation());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void generation() {
        if(timeInput.getText().toString().isEmpty()){
            return;
        }
        int time;
        try{
            time = Integer.parseInt(String.valueOf(timeInput.getText()));
        }catch (Exception e){
            return;
        }
        textCode.setText(Generator.generateCode(time, user.getSpecId(), command.getName()));
    }
}