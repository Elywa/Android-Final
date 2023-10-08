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
import com.example.androidproject.data.local.LocalDataSource;
import com.example.androidproject.data.remote.RemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class Repository implements RepositoryInterface{

    private final RemoteDataSource remote;
    private final LocalDataSource local;
    private static Repository instance ;

    private Repository(RemoteDataSource remote, LocalDataSource local)
    {

        this.remote = remote;
        this.local = local;

    }


    public  static synchronized Repository getInstance(RemoteDataSource remote,LocalDataSource local)
    {

        if (instance == null)
        {

            instance = new Repository(remote,local);
        }

        return instance;
    }




    @Override
    public Observable<MealsResponse> getMealOfTheDay() {
       return remote.getMealOfTheDay();
    }


    @Override
    public Observable<MealsResponse> searchByNameMealRequest(String prefix) {
        return remote.searchByNameMealRequest(prefix);
    }



    @Override
    public Completable insertMealToBreakfast(Meal meal) {
        return local.insertMealToBreakfast(meal);
    }

    @Override
    public Completable insertMealToLaunch(Meal meal) {

        return  local.insertMealToLaunch(meal);
    }

    @Override
    public Completable insertMealToDinner(Meal meal) {

        return local.insertMealToDinner(meal);
    }

    @Override
    public Completable insertMealToFavourite(Meal meal) {

        return local.insertMealToFavourite(meal);
    }


    @Override
    public Observable<MealsResponse> getFullDetailsById(String id,String requester) {

        return  remote.getFullDetailsById(id,requester);
    }

    @Override
    public Observable<IngredientsResponse> getAllIngredients() {
        return remote.getAllIngredients();
    }
    @Override
    public void clearAllTables() {
        local.clearAllTables();
    }

    @Override
    public Observable<FilterMealResponse> filterMealsByCountry(String country) {

        return remote.filterMealsByCountry(country);
    }
    @Override
    public Observable<FilterMealResponse> filterMealsByIngredient(String ingredients ) {

        return  remote.filterMealsByIngredient(ingredients);
    }

    @Override
    public Observable<FilterMealResponse> filterMealsByCategory(String category) {

        return  remote.filterMealsByCategory(category);
    }

    @Override
    public Observable<AllCategoriesResponse> getAllCategories() {
        return  remote.getAllCategories();
    }
    @Override
    public Observable<AreaResponse> getAllCountries() {
        return remote.getAllCountries();
    }

    @Override
    public Observable<List<Favourite>> getAllFavouriteMeals() {
        return local.getAllFavouriteMeals();
    }

    @Override
    public Observable<List<Breakfast>> getAllBreakfastMeals() {
        return local.getAllBreakfastMeals();
    }

    @Override
    public Observable<List<Launch>> getAllLaunchMeals() {
        return local.getAllLaunchMeals();
    }

    @Override
    public Observable<List<Dinner>> getAllDinnerMeals() {
        return local.getAllDinnerMeals();
    }




    @Override
    public Completable deleteMealFromBreakfast(Meal meal) {

        return local.deleteMealFromBreakfast(meal);
    }

    @Override
    public Completable deleteMealFromLaunch(Meal meal) {

        return   local.deleteMealFromLaunch(meal);
    }

    @Override
    public Completable deleteMealFromDinner(Meal meal) {

        return local.deleteMealFromDinner(meal);
    }

    @Override
    public Completable deleteMealFromFavourite(Meal meal) {

        return local.deleteMealFromFavourite(meal);
    }

}
