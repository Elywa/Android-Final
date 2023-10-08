package com.example.androidproject.meals.schedule.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.example.androidproject.R;
import com.example.androidproject.data.dto.Meal;
import com.example.androidproject.meals.mainmealsfragment.view.OnMealClickListener;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class ScheduleMealsAdapter extends RecyclerView.Adapter<ScheduleMealsAdapter.CalenderMealViewHolder>  {

    private List<Meal> meals = new ArrayList<>();

    private int recyclerType;

    private Context context;
    private OnMealClickListener mealClickListener;




    @NonNull
    @Override
    public CalenderMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CalenderMealViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CalenderMealViewHolder holder, int position) {


        holder.itemView.post(() -> {
         if(holder.itemView.getMeasuredWidth() < holder.itemView.getWidth() ){
           holder.layout.setMinimumWidth(holder.itemView.getWidth());
         }
        });


        Meal meal = meals.get(position);
        holder.mealNameTextView.setText(meal.getStrMeal());
        holder.ingredientTextView.setText(String.format("%s, %s"
                , meal.getStrIngredient1(), meal.getStrIngredient2()
                ,meal.getStrIngredient3(),meal.getStrIngredient4()
        ));

        holder.mealImage.setTransitionName("mealImage"+position+meal.getStrMeal());
        Glide.with(context)
                .load(meal.getStrMealThumb())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .override(300, 200).downsample(DownsampleStrategy.CENTER_INSIDE)
                .into(holder.mealImage);

        holder.layout.setOnClickListener(view -> {

            mealClickListener.onMealClicked(meal,holder.mealImage);
        });


    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void setMeals(List<Meal> meals, Context context, int recyclerType, OnMealClickListener mealClickListener)
    {
        this.mealClickListener = mealClickListener;

        this.recyclerType = recyclerType;
        this.context = context;
        this.meals = meals;
        notifyDataSetChanged();
    }




    static class CalenderMealViewHolder extends  RecyclerView.ViewHolder{

        TextView mealNameTextView,ingredientTextView;
        ImageView mealImage;
        View layout;
        MaterialCardView cardView;

        public CalenderMealViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            cardView = itemView.findViewById(R.id.itemCardView);
            mealNameTextView = itemView.findViewById(R.id.mealNameTextView);
            ingredientTextView = itemView.findViewById(R.id.ingredientsTextView);
            mealImage = itemView.findViewById(R.id.mealImage);
        }
    }



}
