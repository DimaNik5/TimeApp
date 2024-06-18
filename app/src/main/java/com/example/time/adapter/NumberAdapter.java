package com.example.time.adapter;


import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.time.R;

import java.util.ArrayList;
import java.util.List;

public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.NumberViewHolder> {

    Context context;
    List<Integer> numbers;

    List<Integer> numbersNew;

    public NumberAdapter(Context context, List<Integer> numbersNew) {
        this.context = context;
        this.numbers = new ArrayList<>();
        numbers.add(0);numbers.add(0);
        numbers.add(0);numbers.add(0);
        numbers.add(0);numbers.add(0);
        this.numbersNew = numbersNew;
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View actionItems = LayoutInflater.from(context).inflate(R.layout.num_layout, parent, false);
        return new NumberViewHolder(actionItems);
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {
        if(position % 2 == 0){
            holder.img.setTranslationX(-4f);
        }

        if(!numbers.get(position).equals(numbersNew.get(position))) {
            switch (numbersNew.get(position)) {
                case 0:
                    holder.img.setBackgroundResource(R.drawable.anim_from1);
                    break;
                case 1:
                    holder.img.setBackgroundResource(R.drawable.anim_from2);
                    break;
                case 2:
                    holder.img.setBackgroundResource(R.drawable.anim_from3);
                    break;
                case 3:
                    holder.img.setBackgroundResource(R.drawable.anim_from4);
                    break;
                case 4:
                    holder.img.setBackgroundResource(R.drawable.anim_from5);
                    break;
                case 5:
                    holder.img.setBackgroundResource(R.drawable.anim_from6);
                    break;
                case 6:
                    holder.img.setBackgroundResource(R.drawable.anim_from7);
                    break;
                case 7:
                    holder.img.setBackgroundResource(R.drawable.anim_from8);
                    break;
                case 8:
                    holder.img.setBackgroundResource(R.drawable.anim_from9);
                    break;
                case 9:
                    holder.img.setBackgroundResource(R.drawable.anim_from0);
                    break;
            }
            numbers.set(position, numbersNew.get(position));
            AnimationDrawable frameAnimation = (AnimationDrawable) holder.img.getBackground();
            frameAnimation.start();
        }
        else{
            switch (numbers.get(position)){
                case 0:
                    holder.img.setBackgroundResource(R.drawable.anim_from0);
                    break;
                case 1:
                    holder.img.setBackgroundResource(R.drawable.anim_from1);
                    break;
                case 2:
                    holder.img.setBackgroundResource(R.drawable.anim_from2);
                    break;
                case 3:
                    holder.img.setBackgroundResource(R.drawable.anim_from3);
                    break;
                case 4:
                    holder.img.setBackgroundResource(R.drawable.anim_from4);
                    break;
                case 5:
                    holder.img.setBackgroundResource(R.drawable.anim_from5);
                    break;
                case 6:
                    holder.img.setBackgroundResource(R.drawable.anim_from6);
                    break;
                case 7:
                    holder.img.setBackgroundResource(R.drawable.anim_from7);
                    break;
                case 8:
                    holder.img.setBackgroundResource(R.drawable.anim_from8);
                    break;
                case 9:
                    holder.img.setBackgroundResource(R.drawable.anim_from9);
                    break;
            }

        }

    }

    @Override
    public int getItemCount() {
        return numbersNew.size();
    }

    public static final class NumberViewHolder extends RecyclerView.ViewHolder{
        ImageView img;

        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
        }
    }



}
