package com.example.time;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.time.user.Commands;
import com.example.time.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginActivity extends AppCompatActivity {

    Button btnSignin;
    TextView inputText;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference commands;
    RelativeLayout root;

    SharedPreferences pref = null;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        armAnim();

        btnSignin = findViewById(R.id.btnSignin);
        inputText = findViewById(R.id.iputText);

        root = findViewById(R.id.root_element);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        commands = db.getReference("Commands");

        btnSignin.setOnClickListener((v) -> { Singin(); });

        pref = getSharedPreferences(User.NAME_TABLE, Context.MODE_PRIVATE);

    }

    private void Singin() {
        if(!inputText.getText().toString().isEmpty()){
            inputText.setHintTextColor(Color.RED);
            return;
        }
        if(inputText.getText().toString().equals("admin")){
            Intent intent = new Intent(root.getContext(), AdminActivity.class);
            root.getContext().startActivity(intent);
        }
    }

    private void goToActivity(String spec){
        Intent intent;
        switch (spec){
            case User.SPEC_ADMIN:
                intent = new Intent(root.getContext(), AdminActivity.class);
                break;
            case User.SPEC_COMAND:
                intent = new Intent(root.getContext(), AdminActivity.class);
                break;
            case User.SPEC_WAY:
                intent = new Intent(root.getContext(), AdminActivity.class);
                break;
            case User.SPEC_STORE:
                intent = new Intent(root.getContext(), AdminActivity.class);
                break;
            default: return;
        }
        root.getContext().startActivity(intent);
    }

    private void saveData(){
        SharedPreferences.Editor editor = pref.edit();
        //editor.putInt("namePola", 5);
        editor.apply();
    }

    private boolean loadData(){
        String spec = pref.getString("specId", "");
        if(spec.isEmpty()){
            return false;
        }
        switch (spec){
            case User.SPEC_ADMIN:
                break;
            case User.SPEC_COMAND:
                user = new Commands();
                user.loadInfo(pref);
                break;
            case User.SPEC_WAY:
                break;
            case User.SPEC_STORE:
                break;
            default:
                return false;
        }
        return true;
    }

    private void armAnim(){
        // Locate view
        ImageView arm = (ImageView) findViewById(R.id.imgArm);
        // Create an animation instance
        Animation an = new RotateAnimation(0.0f, 360.0f, arm.getPivotX() + 170, arm.getPivotY() + 170);
        // Set the animation's parameters
        an.setDuration(10000);               // duration in ms
        an.setRepeatCount(-1);                // -1 = infinite repeated
        an.setRepeatMode(Animation.INFINITE);
        an.setInterpolator(new LinearInterpolator());
        an.setFillAfter(true);               // keep rotation after animation
        // Aply animation to image view
        arm.setAnimation(an);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveData();
    }
}