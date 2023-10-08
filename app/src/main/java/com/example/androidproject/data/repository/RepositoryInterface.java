package com.example.androidproject.data.repository;


import com.example.androidproject.data.dto.Meal;
import com.example.androidproject.data.dto.area.AreaResponse;
import com.example.androidproject.data.dto.category.AllCategoriesResponse;
import com.example.androidproject.data.dto.ingredients.IngredientsResponse;
import com.example.androidproject.data.dto.meal.MealsResponse;
import com.example.androidproject.data.dto.search.FilterMealResponse;
import com.example.androidproject.data.dto.table.Breakfast;
import com.example.androidproject.data.dto.table.Dinner;
import com.example.androidproject.data.dto.table.Favourite;
import com.example.androidproject.data.dto.table.Launch;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public interface RepositoryInterface {


    public Observable<MealsResponse>  getMealOfTheDay();



    public Completable insertMealToBreakfast(Meal meal);

    public Completable insertMealToLaunch(Meal meal);

    public Completable insertMealToDinner(Meal meal);

    public Completable insertMealToFavourite(Meal meal);

    public Observable<MealsResponse> searchByNameMealRequest(String prefix);
    public Observable<MealsResponse> getFullDetailsById(String id,String requester);
    public Observable<IngredientsResponse> getAllIngredients();
    public Observable<AllCategoriesResponse> getAllCategories();
    public Observable<AreaResponse> getAllCountries();
    public Observable<FilterMealResponse> filterMealsByCountry(String country);

    public Observable<FilterMealResponse> filterMealsByIngredient(String ingredients);

    public Observable<FilterMealResponse>  filterMealsByCategory(String category );
    public Observable<List<Favourite>> getAllFavouriteMeals();
    public Observable<List<Breakfast>> getAllBreakfastMeals();
    public Observable<List<Launch>> getAllLaunchMeals();
    public Observable<List<Dinner>> getAllDinnerMeals();

    public Completable deleteMealFromBreakfast(Meal meal);
    public Completable deleteMealFromLaunch(Meal meal);
    public Completable deleteMealFromDinner(Meal meal);
    public Completable deleteMealFromFavourite(Meal meal);

    public void clearAllTables();






}
