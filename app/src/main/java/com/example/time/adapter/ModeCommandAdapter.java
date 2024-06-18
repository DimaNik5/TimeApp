package com.example.time.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.time.ModePlayersListActivity;
import com.example.time.R;
import com.example.time.model.Command;

import java.util.List;

public class ModeCommandAdapter extends RecyclerView.Adapter<ModeCommandAdapter.ModeCommandViewHolder> {

    Context context;
    List<Command> commands;

    public ModeCommandAdapter(Context context, List<Command> commands) {
        this.context = context;
        this.commands = commands;
    }

    @NonNull
    @Override
    public ModeCommandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View command = LayoutInflater.from(context).inflate(R.layout.content_item, parent, false);
        return new ModeCommandAdapter.ModeCommandViewHolder(command);
    }

    @Override
    public void onBindViewHolder(@NonNull ModeCommandViewHolder holder, int position) {
        holder.textName.setText(commands.get(position).getName());
        holder.textName.setOnClickListener(v -> {
            Intent intent = new Intent(context, ModePlayersListActivity.class);
            intent.putExtra("comName", commands.get(position).getName());
            intent.putExtra("logName", commands.get(position).getLogin());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return commands.size();
    }

    public static final class ModeCommandViewHolder extends RecyclerView.ViewHolder{

        TextView textName;

        public ModeCommandViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textContent);
        }
    }
}
