package com.example.androidproject.meals.mainmealsfragment.view;


import com.example.androidproject.data.dto.Meal;

public interface MealOfTheDayCallback {

    public void onResultSuccessMealOfTheDayCallback(Meal meal);
    public void onResultFailureMealOfTheDayCallback(String error);
}
