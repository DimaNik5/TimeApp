package com.example.time.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.time.R;
import com.example.time.model.Command;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    Context context;
    Command command;

    public PlayerAdapter(Context context, Command command) {
        this.context = context;
        this.command = command;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View player = LayoutInflater.from(context).inflate(R.layout.player, parent, false);
        return new PlayerAdapter.PlayerViewHolder(player);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        holder.textName.setText(command.getPlayers().get(position).getName());
        holder.textBonus.setText(String.format("%d", command.getPlayers().get(position).getBonusTime()));


    }


    @Override
    public int getItemCount() {
        return command.getPlayers().size();
    }

    public static final class PlayerViewHolder extends RecyclerView.ViewHolder{

        TextView textName, textBonus;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textContent);
            textBonus = itemView.findViewById(R.id.textBonus);
        }
    }
}
