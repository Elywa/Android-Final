package com.example.androidproject.meals.mainmealsfragment.view;

import android.widget.ImageView;

import com.example.androidproject.data.dto.Meal;


public interface OnMealClickListener {

    public  void onMealClicked(Meal meal, ImageView transitionView);
}
