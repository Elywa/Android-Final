package com.example.androidproject.meals.detailsfragment.presenter;



import com.example.androidproject.data.dto.Meal;

import io.reactivex.rxjava3.core.Completable;

public interface MealDetailsPresenterInterface {

    public Completable insertMealToBreakfast(Meal meal);
    public  Completable insertMealToLaunch(Meal meal);
    public  Completable insertMealToDinner(Meal meal);
    public Completable insertMealToFavourite(Meal meal);

    public Meal getMealToAdd();

    public void setMealToAdd(Meal meal);

}
