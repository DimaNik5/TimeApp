package com.example.time.database;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.example.time.CommandActivity;
import com.example.time.adapter.BonusPlayerAdapter;
import com.example.time.adapter.CommandAdapter;
import com.example.time.adapter.ModeCommandAdapter;
import com.example.time.adapter.ModePlayerAdapter;
import com.example.time.adapter.PlayerAdapter;
import com.example.time.model.Command;
import com.example.time.model.Store;
import com.example.time.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DataBase {
    private static final DatabaseReference DatabaseCom = FirebaseDatabase.getInstance().getReference("Commands");
    private static final DatabaseReference DatabaseTime = FirebaseDatabase.getInstance().getReference("Time");
    private static final DatabaseReference DatabaseStore = FirebaseDatabase.getInstance().getReference("Store");

    public static void saveCom(@NonNull Command com){
        DatabaseCom.child(com.getLogin()).setValue(com);
    }

    public static void delCom(@NonNull String log){
        DatabaseCom.child(log).setValue(null);
    }

    public static void getListComDataFromDB(List<Command> commands, CommandAdapter commandAdapter){
        ValueEventListener vListener = new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!commands.isEmpty()) commands.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Command com = ds.getValue(Command.class);
                    assert com != null;
                    commands.add(com);
                }
                commandAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        DatabaseCom.addValueEventListener(vListener);
    }

    public static void getListComDataFromDB(List<Command> commands, ModeCommandAdapter commandAdapter){
        ValueEventListener vListener = new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!commands.isEmpty()) commands.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Command com = ds.getValue(Command.class);
                    assert com != null;
                    commands.add(com);
                }
                commandAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        DatabaseCom.addValueEventListener(vListener);
    }

    public static void getListComDataFromDB(Command command, ModePlayerAdapter modePlayerAdapter){
        ValueEventListener vListener = new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!command.getPlayers().isEmpty()) command.getPlayers().clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Command com = ds.getValue(Command.class);
                    assert com != null;
                    if(com.getName().equals(command.getName())){
                        command.copyContent(com);
                    }
                }
                modePlayerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        DatabaseCom.addValueEventListener(vListener);
    }

    public static void getListPlayerDataFromDB(Command command, BonusPlayerAdapter bonusPlayerAdapter){
        ValueEventListener vListener = new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!command.getPlayers().isEmpty()) command.getPlayers().clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Command com = ds.getValue(Command.class);
                    assert com != null;
                    if(com.getName().equals(command.getName())){
                        command.copyContent(com);
                    }
                }
                bonusPlayerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        DatabaseCom.addValueEventListener(vListener);
    }

    public static void getListPlayerDataFromDB(Command command, PlayerAdapter playerAdapter){
        ValueEventListener vListener = new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!command.getPlayers().isEmpty()) command.getPlayers().clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Command com = ds.getValue(Command.class);
                    assert com != null;
                    if(com.getLogin().equals(command.getLogin())){
                        command.copyContent(com);
                        command.addPlayer("Торговля");
                        command.getPlayers().get(command.getPlayers().size() - 1).addBonusTime(command.getStoreTime());
                    }
                }
                playerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        DatabaseCom.addValueEventListener(vListener);
    }

    public static void SignOnLogin(Command command){
        DatabaseCom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    Command com = ds.getValue(Command.class);
                    assert com != null;
                    if(com.getLogin().equals(command.getLogin())){
                        command.copyContent(com);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void setHaveStartTime(long t){
        DatabaseTime.child("HaveStartTime").setValue(t);
    }

    public static void setStartTime(long t){
        DatabaseTime.child("startTime").setValue(t);
    }

    public static void setStatTime(@NonNull List<Long> time){
        setStartTime(time.get(0));
        setHaveStartTime(time.get(1));
    }

    public static void getStatTime(List<Long> time){
        DatabaseTime.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int c = 1;
                for(DataSnapshot ds : snapshot.getChildren()){
                    time.set(c--, ds.getValue(long.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void saveNewStore(@NonNull Store str){
        DatabaseStore.child(str.getCode()).setValue(str);
    }

    public static void delStore(@NonNull String code){
        DatabaseStore.child(code).setValue(null);
    }

    public static void closeStore(String code){
        DatabaseStore.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    Store str = ds.getValue(Store.class);
                    assert str != null;
                    if(str.getCode().equals(code)){
                        if(CommandActivity.getHaveTime() <= str.getTime()) return;
                        String[] parts = code.split("_");
                        if(parts[0].equals(User.SPEC_COMMAND)){
                            if(parts[1].equals(CommandActivity.getCom().getName())) return;
                            giveTimeCom(parts[1], str.getTime());
                        }
                        CommandActivity.getCom().takeTime(str.getTime());
                        saveCom(CommandActivity.getCom());
                        delStore(code);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private static void giveTimeCom(String name, int time) {
        DatabaseCom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    Command com = ds.getValue(Command.class);
                    assert com != null;
                    if(com.getName().equals(name)){
                        com.giveTime(time);
                        saveCom(com);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
