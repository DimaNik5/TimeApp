package com.example.time;

import androidx.annotation.NonNull;
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

    // кнопка входа
    Button btnSignin;
    // поле ввода логина
    TextView inputText;
    // для работы с базой данных
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference commands;
    // начальное поле
    RelativeLayout root;
    // для хранения данных внутри памяти программы
    SharedPreferences pref = null;
    // объект для реализации возможностей пользователя
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        armAnim();

        btnSignin = findViewById(R.id.btnSignin);
        btnSignin.setOnClickListener((v) -> { Singin(); });
        inputText = findViewById(R.id.iputText);
        root = findViewById(R.id.root_element);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        commands = db.getReference("Commands");
        // выбор "области" во внутреней памяти программы
        pref = getSharedPreferences(User.NAME_TABLE, Context.MODE_PRIVATE);
    }

    // функция входа
    private void Singin() {
        // проверка на наличие ввода
        if(!inputText.getText().toString().isEmpty()){
            inputText.setHintTextColor(Color.RED);
            return;
        }
        if(inputText.getText().toString().equals("admin")){
            Intent intent = new Intent(root.getContext(), AdminActivity.class);
            root.getContext().startActivity(intent);
        }
    }

    // переход на другую страницу страницу по сохранёным данным
    private void goToActivity(@NonNull String spec){
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

    // загрузка данных из памяти программы и переход на соответсвенную страницу
    private void loadData(){
        String spec = pref.getString("specId", "");
        if(spec.isEmpty()){
            return;
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
                return;
        }
        goToActivity(spec);
    }

    // установка анимации для стрелки у часов
    private void armAnim(){
        // выбор картинки
        ImageView arm = (ImageView) findViewById(R.id.imgArm);
        // установка поворота и координат оси для анимации
        Animation an = new RotateAnimation(0.0f, 360.0f, arm.getPivotX() + 170, arm.getPivotY() + 170);
        // установка параметров
        an.setDuration(10000);               // время в мс
        an.setRepeatCount(-1);                // -1 = бесконечное повторение
        an.setRepeatMode(Animation.INFINITE);
        an.setInterpolator(new LinearInterpolator()); // равномерное вращение
        an.setFillAfter(true);               // возвращение к начальной позиции
        // установка анимации к элементу
        arm.setAnimation(an);
    }

    // сохраняет данные при выходе из приложения !!надо перенести в другие активити!!
    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveData();
    }
    private void saveData(){
        SharedPreferences.Editor editor = pref.edit();
        //editor.putInt("namePola", 5);
        editor.apply();
    }
}