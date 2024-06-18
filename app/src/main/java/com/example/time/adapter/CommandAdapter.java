package com.example.time.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.time.BonusPlayersActivity;
import com.example.time.R;
import com.example.time.model.Command;

import java.util.List;

public class CommandAdapter extends RecyclerView.Adapter<CommandAdapter.CommandViewHolder> {

    protected Context context;
    protected List<Command> commands;

    public CommandAdapter(Context context, List<Command> commands) {
        this.context = context;
        this.commands = commands;
    }

    @NonNull
    @Override
    public CommandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View command = LayoutInflater.from(context).inflate(R.layout.content_item, parent, false);
        return new CommandAdapter.CommandViewHolder(command);
    }

    @Override
    public void onBindViewHolder(@NonNull CommandViewHolder holder, int position) {
        holder.textName.setText(commands.get(position).getName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BonusPlayersActivity.class);

            intent.putExtra("logName", commands.get(position).getLogin());
            intent.putExtra("comName", commands.get(position).getName());

            context.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return commands.size();
    }

    public static final class CommandViewHolder extends RecyclerView.ViewHolder{

        TextView textName;

        public CommandViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textContent);
        }
    }
}
