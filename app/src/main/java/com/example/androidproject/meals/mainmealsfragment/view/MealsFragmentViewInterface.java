package com.example.androidproject.meals.mainmealsfragment.view;

import android.widget.ImageView;

import com.example.androidproject.data.dto.Meal;


public interface MealsFragmentViewInterface {
    public void onResultSuccessOneMealsCallback();
    public void onResultFailureOneMealCallback(String error);

    public void onSearchSuccessResult();




    void onMealAddClicked(Meal meal);

    public  void onMealClicked(Meal meal, ImageView transitionView);
}
