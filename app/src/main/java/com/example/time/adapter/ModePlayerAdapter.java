package com.example.time.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.time.R;
import com.example.time.database.DataBase;
import com.example.time.model.Command;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class ModePlayerAdapter extends RecyclerView.Adapter<ModePlayerAdapter.ModePlayerViewHolder> {

    Context context;
    Command command;

    public ModePlayerAdapter(Context context, Command command) {
        this.context = context;
        this.command = command;
    }

    @NonNull
    @Override
    public ModePlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View player = LayoutInflater.from(context).inflate(R.layout.mode_comand_item, parent, false);
        return new ModePlayerAdapter.ModePlayerViewHolder(player);
    }

    @Override
    public void onBindViewHolder(@NonNull ModePlayerViewHolder holder, int position) {
        holder.textName.setText(command.getPlayers().get(position).getName());

        holder.imgEdit.setOnClickListener(v -> showCreateWindow(holder.textName.getText().toString()));
        holder.imgDelete.setOnClickListener(v -> delElement(holder.textName.getText().toString()));

    }


    @Override
    public int getItemCount() {
        return command.getPlayers().size();
    }

    public static final class ModePlayerViewHolder extends RecyclerView.ViewHolder{

        TextView textName;
        ImageView imgEdit, imgDelete;

        public ModePlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textContent);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }

    private void showCreateWindow(@NonNull String textName) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Изменить имя участника");
        dialog.setMessage(textName);

        LayoutInflater inflater = LayoutInflater.from(context);
        View createWindow = inflater.inflate(R.layout.create_command_window, null);
        dialog.setView(createWindow);

        final TextInputEditText name = createWindow.findViewById(R.id.nameCom);
        TextInputLayout namep = createWindow.findViewById(R.id.nameplace);
        TextInputLayout loginp = createWindow.findViewById(R.id.logplace);
        namep.setHint("Новое имя");
        loginp.setVisibility(View.INVISIBLE);

        dialog.setNegativeButton("Отменить", (dialogInterface, which) -> dialogInterface.dismiss());

        dialog.setPositiveButton("Изменить", (dialogInterface, which) -> {
            String text = name.getText().toString();
            text = text.trim();
            if(text.isEmpty()){
                return;
            }

            if(command.getPlayers().get(command.indexStringof(textName)).getId() == 0){
                command.setName(text);
                command.getPlayers().get(0).setName(text);
            }
            else {
                command.getPlayers().get(command.indexStringof(textName)).setName(text);
            }
            DataBase.saveCom(command);
        });

        dialog.show();
    }

    private void delElement(String textName) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Вы точно хотите удалить?");
        dialog.setMessage(textName);

        dialog.setNegativeButton("Нет", (dialogInterface, which) -> dialogInterface.dismiss());

        dialog.setPositiveButton("Да", (dialogInterface, which) -> {

            if(command.getPlayers().get(command.indexStringof(textName)).getId() == 0){
                DataBase.delCom(command.getLogin());
            }
            else {
                command.getPlayers().remove(command.indexStringof(textName));
                DataBase.saveCom(command);
            }
        });

        dialog.show();
    }
}
