package com.example.time;

import android.annotation.SuppressLint;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.example.time.adapter.NumberAdapter;
import com.example.time.database.DataBase;
import com.example.time.model.Command;
import com.example.time.model.Player;

import java.util.ArrayList;
import java.util.List;

public class Time {
    List<Integer> num;
    private List<Long> timeState;
    private int bonTime;
    private boolean lifeF;
    private NumberAdapter adapter;
    private Handler handler;

    private boolean minusSec(int pos){
        if(pos < num.size()){
            if(num.get(pos) > 0){
                num.set(pos, num.get(pos) - 1);
                return true;
            }
            if(minusSec(pos + 1)){
                num.set(pos, 9);
                return true;
            }
        }
        return false;
    }

    public void setAdapter(NumberAdapter numberAdapter) {
        this.adapter = numberAdapter;
    }

    public Time(boolean f){
        num = new ArrayList<>();
        timeState = new ArrayList<>();
        timeState.add(0L); // время старта
        timeState.add(0L); // начальное время включая бонусы
        lifeF = f;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void update(@NonNull Command com){
        if(lifeF) {
            DataBase.getStatTime(timeState);
            if(timeState.get(0) > 0) {
                bonTime = com.getStoreTime() * 60;
                for (Player p : com.getPlayers()) {
                    bonTime += p.getBonusTime() * 60;
                }
                updateNum();
                if(handler == null){
                    handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Do something after 1000ms
                            if(!minusSec(0)){
                                lifeF = false;
                                handler.removeCallbacks(this);
                            }
                            updateNum();
                            adapter.notifyDataSetChanged();
                            handler.postDelayed(this, 1000);
                        }
                    }, 0);
                }
            }
        }
        else {
            com.setLife(lifeF);
            DataBase.saveCom(com);
        }
    }

    public void delTimer(){
        handler.removeCallbacks(handler.obtainMessage().getCallback());
    }

    private void updateNum(){
        long haveTime = (timeState.get(1) - (System.currentTimeMillis() - timeState.get(0))) / 1000 + bonTime;
        if(haveTime <= 0) return;
        int hours = (int) (haveTime / (60 * 60));
        haveTime %= 60 * 60;
        int minuts = (int) (haveTime / 60);
        haveTime %= 60;
        num.set(0, (int) (haveTime % 10));
        num.set(1, (int) (haveTime / 10));
        num.set(2, (minuts % 10));
        num.set(3, (minuts / 10));
        num.set(4, (hours % 10));
        num.set(5, (hours / 10));
    }

    public long haveTimeCom(){
        return (timeState.get(1) - (System.currentTimeMillis() - timeState.get(0))) / 1000 + bonTime;
    }

    public void setNumbers(List<Integer> n) {
        num = n;
    }

    public long getStart(){
        return timeState.get(0);
    }
    public long getHave(){
        return timeState.get(1);
    }
    public void setStart(long t){
        timeState.set(0, t);
    }
    public void setHave(long t){
        timeState.set(1, t);
    }
}
