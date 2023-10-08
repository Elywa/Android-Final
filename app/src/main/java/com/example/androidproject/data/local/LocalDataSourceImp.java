package com.example.androidproject.data.local;

import android.content.Context;


import com.example.androidproject.data.dto.Meal;
import com.example.androidproject.data.dto.table.Breakfast;
import com.example.androidproject.data.dto.table.Dinner;
import com.example.androidproject.data.dto.table.Favourite;
import com.example.androidproject.data.dto.table.Launch;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;


public class LocalDataSourceImp implements  LocalDataSource{

    private static  LocalDataSourceImp localDataSourceImp;
    private final MealsDatabase database;
    private final BreakfastDao breakfastDao ;
    private final LaunchDao launchDao ;
    private final DinnerDao dinnerDao ;
    private final FavouriteDao favouriteDao ;
    private LocalDataSourceImp(Context context)
    {
        database = MealsDatabase.getInstance(context);

        this.breakfastDao = database.BreakfastDao();
        this.launchDao = database.LaunchDao();
        this.dinnerDao = database.DinnerDao();
        this.favouriteDao = database.FavouriteDao();

    }


    public static synchronized LocalDataSourceImp getInstance(Context context)
    {

        if (localDataSourceImp == null)
        {
            localDataSourceImp = new LocalDataSourceImp(context);
        }

        return localDataSourceImp;
    }

    @Override
    public Completable insertMealToBreakfast(Meal meal) {
          return  breakfastDao.insert(meal.mealToBreakfastMapper());
    }

    @Override
    public Completable insertMealToLaunch(Meal meal) {
            return     launchDao.insert(meal.mealToLaunchMapper());
    }

    @Override
    public Completable insertMealToDinner(Meal meal) {

        return     dinnerDao.insert(meal.mealToDinnerMapper());

    }

    @Override
    public Completable  insertMealToFavourite(Meal meal) {
           return favouriteDao.insert(meal.mealToFavouriteMapper());
    }

    @Override
    public Observable<List<Favourite>> getAllFavouriteMeals() {
        return favouriteDao.getAllMeals();
    }

    @Override
    public Observable<List<Breakfast>> getAllBreakfastMeals() {
        return  breakfastDao.getAllMeals();
    }

    @Override
    public Observable<List<Launch>> getAllLaunchMeals() {
        return launchDao.getAllMeals();
    }

    @Override
    public Observable<List<Dinner>> getAllDinnerMeals() {
        return  dinnerDao.getAllMeals();
    }

    @Override
    public Completable deleteMealFromBreakfast(Meal meal) {


        return  breakfastDao.delete(meal.mealToBreakfastMapper());

    }

    @Override
    public Completable  deleteMealFromLaunch(Meal meal) {



        return  launchDao.delete(meal.mealToLaunchMapper());

    }

    @Override
    public Completable  deleteMealFromDinner(Meal meal) {

        return   dinnerDao.delete(meal.mealToDinnerMapper());

    }

    @Override
    public Completable  deleteMealFromFavourite(Meal meal) {

        return    favouriteDao.delete(meal.mealToFavouriteMapper());
    }
    @Override
    public void clearAllTables() {
        new Thread(() -> {
            database.clearAllTables();
        }).start();
    }
}
