package com.example.time.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.time.R;
import com.example.time.model.Action;

import java.util.List;

public class ActionAdapter extends RecyclerView.Adapter<ActionAdapter.ActionViewHolder> {

    Context context;
    List<Action> actions;

    public ActionAdapter(Context context, List<Action> actions) {
        this.context = context;
        this.actions = actions;
    }

    @NonNull
    @Override
    public ActionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View actionItems = LayoutInflater.from(context).inflate(R.layout.content_item, parent, false);
        return new ActionViewHolder(actionItems);
    }

    @Override
    public void onBindViewHolder(@NonNull ActionViewHolder holder, int position) {
        holder.textAction.setText(actions.get(position).getTitle());

        holder.itemView.setOnClickListener(v -> {
            Context next = actions.get(position).useContent(context);
            if(!next.equals(context)){
                Intent intent = new Intent(context, next.getClass());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return actions.size();
    }

    public static final class ActionViewHolder extends RecyclerView.ViewHolder{

        TextView textAction;

        public ActionViewHolder(@NonNull View itemView) {
            super(itemView);
            textAction = itemView.findViewById(R.id.textContent);
        }
    }

}
