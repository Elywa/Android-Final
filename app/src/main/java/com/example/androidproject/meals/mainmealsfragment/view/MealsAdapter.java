package com.example.androidproject.meals.mainmealsfragment.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;

import com.example.androidproject.R;
import com.example.androidproject.data.dto.Meal;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealsViewHolder>  {

    private List<Meal> meals = new ArrayList<>();
    private Context context;
    private  MealsFragmentViewInterface listener;




    @NonNull
    @Override
    public MealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MealsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MealsViewHolder holder, int position) {

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

        holder.mealImage.setTransitionName("mealImage"+position);
        Glide.with(context)
                .load(meal.getStrMealThumb())
                .override(300, 200).downsample(DownsampleStrategy.CENTER_INSIDE)
                .into(holder.mealImage);

        holder.layout.setOnClickListener(view -> {

            listener.onMealClicked(meal,holder.mealImage);
        });


        holder.addImageView.setOnClickListener(view -> {
            listener.onMealAddClicked(meal);
        });

    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void setMeals(List<Meal> meals, Context context, MealsFragmentViewInterface listener)
    {
        this.listener = listener;
        this.context = context;
        this.meals = meals;
        Log.d("watcher",hashCode() +"adapter success");
        notifyDataSetChanged();
    }




    static class MealsViewHolder extends  RecyclerView.ViewHolder{

        TextView mealNameTextView,ingredientTextView;
        ImageView mealImage,addImageView;
        View layout;
        MaterialCardView cardView;

        public MealsViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;

            cardView = itemView.findViewById(R.id.itemCardView);
            mealNameTextView = itemView.findViewById(R.id.mealNameTextView);
            ingredientTextView = itemView.findViewById(R.id.ingredientsTextView);
            mealImage = itemView.findViewById(R.id.mealImage);
            addImageView = itemView.findViewById(R.id.plusMealImage);
        }
    }



}
