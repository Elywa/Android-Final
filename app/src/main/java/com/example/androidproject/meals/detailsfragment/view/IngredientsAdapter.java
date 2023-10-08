package com.example.androidproject.meals.detailsfragment.view;

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


import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>  {

    private List<String> ingredients = new ArrayList<>();
    private Context context;




    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {

       String ingredientName =  ingredients.get(position);
       if (ingredientName != null){
           holder.ingredientTextView.setText(ingredientName);
           Glide.with(context)
                   .load("https://www.themealdb.com/images/ingredients/"+ingredientName+".png")
                   .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                   .override(300, 200).downsample(DownsampleStrategy.CENTER_INSIDE)
                   .into(holder.ingredientImageView);
       }
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void setData(List<String> ingredients, Context context)
    {

        this.context = context;
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }




    static class IngredientsViewHolder extends  RecyclerView.ViewHolder{

        TextView ingredientTextView;
        ImageView ingredientImageView;

        public IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);

            ingredientImageView =  itemView.findViewById(R.id.ingredientImageView);
            ingredientTextView = itemView.findViewById(R.id.ingredientTextView);
        }
    }



}
