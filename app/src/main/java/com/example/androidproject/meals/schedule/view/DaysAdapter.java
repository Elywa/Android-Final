package com.example.androidproject.meals.schedule.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.androidproject.R;
import com.example.androidproject.data.dto.Day;

import java.util.ArrayList;
import java.util.List;


public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.DaysViewHolder> {

    private List<Day> days = new ArrayList<>();
    private OnDayListener listener;


    private int prevSelected = 0;


    @NonNull
    @Override
    public DaysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DaysViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.day_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DaysViewHolder holder, int position) {


        holder.dayTextView.setText(days.get(position).getDayName());
        holder.numberTextView.setText(days.get(position).getDayNumber());

        if (days.get(position).getSelected()) {
            holder.selectedSate();

        } else {
            holder.initState();
        }

        holder.dayCard.setOnClickListener(view -> {
            days.forEach(day -> {day.setSelected(false);});
            prevSelected = holder.getAdapterPosition();
            listener.onDayClicked(days.get(position));
           notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public void setDays(List<Day> days, Context context, OnDayListener listener) {
        Log.d("day", days.size() + "");
        this.listener = listener;
        this.days = days;
        notifyDataSetChanged();
    }


    static class DaysViewHolder extends RecyclerView.ViewHolder {


        TextView dayTextView, numberTextView;

        CardView dayCard;


        private int selectedDay = 0;

        public DaysViewHolder(@NonNull View itemView) {
            super(itemView);
            dayCard = itemView.findViewById(R.id.dayCard);
            dayTextView = itemView.findViewById(R.id.dayTextView);
            numberTextView = itemView.findViewById(R.id.numberTextView);


        }


        private void initState() {
            dayCard.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.gray));
            dayTextView.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.gray30));
            numberTextView.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.black));
        }


        private void selectedSate() {
            dayCard.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.green));
            dayTextView.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
            numberTextView.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
        }
    }
}
