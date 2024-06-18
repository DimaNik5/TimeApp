package com.example.time.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.time.CommandsListActivity;
import com.example.time.GeneratorActivity;
import com.example.time.ModeCommandsListActivity;
import com.example.time.PlayersActivity;
import com.example.time.R;
import com.example.time.database.DataBase;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class LibActions {
    private static final int UTC = 4;
    private static final String SEE_COMMANDS = "Команды";
    private static final String MODE_COMMANDS = "Изменить команды";
    private static final String MODE_TIME = "Изменить время";
    private static final String GEN_CODE = "Сгенерировать код";
    private static final String INPUT_CODE = "Ввести код";
    private static final String PLAYERS = "Участники";
    private static List<Long> timeState;
    public static Context useContent(@NonNull String title, Context cur){
        try {
            switch (title) {
                case PLAYERS:
                    return PlayersActivity.class.newInstance();
                case INPUT_CODE:
                    showInputWindow(cur);
                    return cur;
                case GEN_CODE:
                    return GeneratorActivity.class.newInstance();
                case SEE_COMMANDS:
                    return CommandsListActivity.class.newInstance();
                case MODE_COMMANDS:
                    return ModeCommandsListActivity.class.newInstance();
                case MODE_TIME:
                    if(timeState == null){
                        timeState = new ArrayList<>();
                        timeState.add(0L); timeState.add(0L);
                    }
                    DataBase.getStatTime(timeState);
                    showTimeWindow(cur);
                    return cur;

                default: return cur;
            }
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    private static void showInputWindow(Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Введите код");

        LayoutInflater inflater = LayoutInflater.from(context);
        View createWindow = inflater.inflate(R.layout.create_command_window, null);
        dialog.setView(createWindow);

        final TextInputEditText name = createWindow.findViewById(R.id.nameCom);
        TextInputLayout namep = createWindow.findViewById(R.id.nameplace);
        TextInputLayout loginp = createWindow.findViewById(R.id.logplace);
        namep.setHint("Код");
        loginp.setVisibility(View.INVISIBLE);

        dialog.setNegativeButton("Отмена", (dialogInterface, which) -> dialogInterface.dismiss());

        dialog.setPositiveButton("Ввести", (dialogInterface, which) -> {
            String textName = name.getText().toString();
            textName = textName.replace(" ","");

            if(textName.isEmpty()){
                return;
            }

            DataBase.closeStore(textName);
        });

        dialog.show();
    }

    private static void showTimeWindow(Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Изменить время");

        LayoutInflater inflater = LayoutInflater.from(context);
        View createWindow = inflater.inflate(R.layout.create_command_window, null);
        dialog.setView(createWindow);

        float saTime = (float) ((timeState.get(0) / 1000) % (60 * 60 * 24)) / (60 * 60) + UTC;
        float haTime = (float) ((timeState.get(1) / 1000) % (60 * 60 * 24)) / (60 * 60);

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        final TextInputEditText timeStart = createWindow.findViewById(R.id.nameCom);
        timeStart.setText(df.format(saTime));
        final TextInputEditText haveTime = createWindow.findViewById(R.id.logCom);
        haveTime.setText(df.format(haTime));
        TextInputLayout namep = createWindow.findViewById(R.id.nameplace);
        namep.setHint("Время старта");
        TextInputLayout loginp = createWindow.findViewById(R.id.logplace);
        loginp.setHint("Начальное время");

        dialog.setNegativeButton("Отменить", (dialogInterface, which) -> dialogInterface.dismiss());

        dialog.setPositiveButton("Изменить", (dialogInterface, which) -> {
            String textTime = timeStart.getText().toString();
            textTime = textTime.replace(" ","");
            String textHave = haveTime.getText().toString();
            textHave = textHave.replace(" ","");

            if(textTime.isEmpty() || textHave.isEmpty()){
                return;
            }
            textTime += 'f';
            textHave += 'f';
            float timeS, timeH;
            try{
                timeS = Float.parseFloat(textTime);
                timeH = Float.parseFloat(textHave);
            }catch (Exception e) {
                return;
            }

            timeState.set(0, (long) ((timeS * 60 * 60 * 1000) + ((System.currentTimeMillis() / (60 * 60 * 1000 * 24)) * 60 * 60 * 1000 * 24) - (UTC * 60 * 60 * 1000)));
            timeState.set(1, (long) (timeH * 60 * 60 * 1000));

            DataBase.setStatTime(timeState);
        });

        dialog.show();
    }
}
