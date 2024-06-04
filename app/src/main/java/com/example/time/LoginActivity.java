package com.example.time;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    }

    private void Singin() {
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
}