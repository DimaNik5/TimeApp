package com.example.time.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.time.R;
import com.example.time.database.DataBase;
import com.example.time.model.Command;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class BonusPlayerAdapter extends RecyclerView.Adapter<BonusPlayerAdapter.BonusPlayerViewHolder> {

    Context context;
    Command command;

    public BonusPlayerAdapter(Context context, Command command) {
        this.context = context;
        this.command = command;
    }

    @NonNull
    @Override
    public BonusPlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View player = LayoutInflater.from(context).inflate(R.layout.bonus_item, parent, false);
        return new BonusPlayerAdapter.BonusPlayerViewHolder(player);
    }

    @Override
    public void onBindViewHolder(@NonNull BonusPlayerViewHolder holder, int position) {
        holder.textName.setText(command.getPlayers().get(position).getName());

        holder.addTime.setOnClickListener(v -> showAddWindow(holder.textName.getText().toString()));
        holder.mTime.setOnClickListener(v -> showMinusWindow(holder.textName.getText().toString()));

    }


    @Override
    public int getItemCount() {
        return command.getPlayers().size();
    }

    public static final class BonusPlayerViewHolder extends RecyclerView.ViewHolder{

        TextView textName, addTime, mTime;

        public BonusPlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textContent);
            addTime = itemView.findViewById(R.id.addTimebtn);
            mTime = itemView.findViewById(R.id.textBonus);
        }
    }

    private void showAddWindow(@NonNull String textName) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Введите бонусное время для");
        dialog.setMessage(textName);

        LayoutInflater inflater = LayoutInflater.from(context);
        View createWindow = inflater.inflate(R.layout.create_command_window, null);
        dialog.setView(createWindow);

        final TextInputEditText name = createWindow.findViewById(R.id.nameCom);
        TextInputLayout namep = createWindow.findViewById(R.id.nameplace);
        TextInputLayout loginp = createWindow.findViewById(R.id.logplace);
        namep.setHint("Время (> 0)");
        loginp.setVisibility(View.INVISIBLE);

        dialog.setNegativeButton("Отменить", (dialogInterface, which) -> dialogInterface.dismiss());

        dialog.setPositiveButton("Сохранить", (dialogInterface, which) -> {
            String text = name.getText().toString();
            text = text.replace(" ","");
            int bTime;
            try {
                bTime = Integer.parseInt(text);
            }
            catch (NumberFormatException exception){
                return;
            }

            command.getPlayers().get(command.indexStringof(textName)).addBonusTime(bTime);
            DataBase.saveCom(command);
        });

        dialog.show();
    }

    private void showMinusWindow(String textName) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Введите время штрафа для");
        dialog.setMessage(textName);

        LayoutInflater inflater = LayoutInflater.from(context);
        View createWindow = inflater.inflate(R.layout.create_command_window, null);
        dialog.setView(createWindow);

        final TextInputEditText name = createWindow.findViewById(R.id.nameCom);
        TextInputLayout namep = createWindow.findViewById(R.id.nameplace);
        TextInputLayout loginp = createWindow.findViewById(R.id.logplace);
        namep.setHint("Время (> 0)");
        loginp.setVisibility(View.INVISIBLE);

        dialog.setNegativeButton("Отменить", (dialogInterface, which) -> dialogInterface.dismiss());

        dialog.setPositiveButton("Сохранить", (dialogInterface, which) -> {
            String text = name.getText().toString();
            text = text.replace(" ","");
            int bTime;
            try {
                bTime = Integer.parseInt(text);
            }
            catch (NumberFormatException exception){
                return;
            }

            command.getPlayers().get(command.indexStringof(textName)).addBonusTime(-1 * bTime);
            DataBase.saveCom(command);
        });

        dialog.show();
    }
}
