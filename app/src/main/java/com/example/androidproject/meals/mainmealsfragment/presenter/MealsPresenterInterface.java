package com.example.androidproject.meals.mainmealsfragment.presenter;



import com.example.androidproject.data.dto.Meal;

import io.reactivex.rxjava3.core.Completable;

public interface MealsPresenterInterface {


    public void mealOfTheDayRequest();

    public void searchByNameMealRequest(String prefix);

    public Completable insertMealToBreakfast(Meal meal);
    public  Completable insertMealToLaunch(Meal meal);
    public  Completable insertMealToDinner(Meal meal);
    public Completable insertMealToFavourite(Meal meal);


    public void clearAllTables();

}
