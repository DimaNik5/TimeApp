package com.example.time.animation;

import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import androidx.annotation.NonNull;

public class LocalAnimation {

    // анимация вращения вокруг оси за заданный период времени
    @NonNull
    public static Animation getRotAnim(int seconds, int size){
        // установка поворота и координат оси для анимации
        Animation an = new RotateAnimation(0.0f, 360.0f, size, size);
        // установка параметров
        an.setDuration(seconds * 1000L);               // время в мс
        an.setRepeatCount(-1);                // -1 = бесконечное повторение
        an.setRepeatMode(Animation.INFINITE);
        an.setInterpolator(new LinearInterpolator()); // равномерное вращение
        an.setFillAfter(true);
        return an;
    }

}
