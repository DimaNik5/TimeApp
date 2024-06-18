package com.example.time;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.time.animation.LocalAnimation;
import com.example.time.model.Command;
import com.example.time.user.User;

public class MainActivity extends AppCompatActivity {

    ImageView arm;
    TextView inputText;
    Button btnSign;
    Command com;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences("USER", Context.MODE_PRIVATE);
        loadDate();

        com = new Command("","");
        arm = findViewById(R.id.imgarm);
        arm.setAnimation(LocalAnimation.getRotAnim(12, 170));
        inputText = findViewById(R.id.textInput);
        btnSign = findViewById(R.id.btnSignin);
        btnSign.setOnClickListener(v -> SignIn());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void SignIn() {
        String text = inputText.getText().toString();
        text = text.replace(" ", "");
        if(text.isEmpty()){
            return;
        }
        if(!com.getLogin().equals(text)) {
            com = new Command("", text);
        }
        Intent intent;
        SharedPreferences.Editor editor = pref.edit();
        switch (User.getLevel(text, com)){
            case 0:
                intent = new Intent(this, AdminActivity.class);
                editor.putString("userId", User.SPEC_ADMIN);
                break;
            case 1:
                intent = new Intent(this, CommandsListActivity.class);
                editor.putString("userId", User.SPEC_WAY);
                break;
            case 2:
                intent = new Intent(this, GeneratorActivity.class);
                editor.putString("userId", User.SPEC_STORE);
                break;
            default:
                if(!com.getName().isEmpty()){
                    intent = new Intent(this, CommandActivity.class);
                    editor.putString("userId", User.SPEC_COMMAND);
                    editor.putString("logName", com.getLogin());
                    editor.putString("comName", com.getName());
                    editor.putBoolean("life", com.isLife());
                    editor.putInt("bonTime", com.AllBonTime());
                }
                else {
                    return;
                }
        }
        editor.apply();
        this.startActivity(intent);
        finish();
    }

    private void loadDate() {
        Intent intent;
        switch (pref.getString("userId", "")){
            case User.SPEC_ADMIN:
                intent = new Intent(this, AdminActivity.class);
                break;
            case User.SPEC_WAY:
                intent = new Intent(this, CommandsListActivity.class);
                break;
            case User.SPEC_STORE:
                intent = new Intent(this, GeneratorActivity.class);
                break;
            case User.SPEC_COMMAND:
                intent = new Intent(this, CommandActivity.class);
                break;
            default: return;
        }
        this.startActivity(intent);
        finish();
    }

}