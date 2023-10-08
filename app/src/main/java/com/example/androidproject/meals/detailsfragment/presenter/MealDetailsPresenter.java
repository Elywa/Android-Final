package com.example.androidproject.meals.detailsfragment.presenter;

import android.os.Parcel;
import android.os.Parcelable;


import com.example.androidproject.data.dto.Meal;
import com.example.androidproject.data.repository.RepositoryInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Completable;

public class MealDetailsPresenter implements  MealDetailsPresenterInterface, Parcelable {


    private final RepositoryInterface repository;



    private Meal mealToAdd;

    public Meal getMealToAdd() {
        return mealToAdd;
    }

    public void setMealToAdd(Meal mealToAdd) {
        this.mealToAdd = mealToAdd;
    }

    public MealDetailsPresenter(RepositoryInterface repository)
    {

        this.repository = repository;


    }


    public List<String> getIngredients(Meal meal)
    {
        List<String> ingredients = Arrays.stream(meal.getClass().getMethods()).filter(method -> method.getName().toLowerCase().contains("ingredient")).filter(method -> method.getName().toLowerCase().contains("get")).map(method -> {
            String ingredientName   = null;
            try {
                ingredientName = (String) method.invoke(meal);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return ingredientName;
        }).collect(Collectors.toList());
        ingredients.removeIf(String::isEmpty);
        return ingredients;
    }


    public String getYouTubeId (String youTubeUrl) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";// regular expression  designed to extract YouTube video IDs from various types of YouTube video URLs
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if(matcher.find()){
            return matcher.group();
        } else {
            return "error";
        }
    }


    protected MealDetailsPresenter(Parcel in) {
        repository = in.readParcelable(RepositoryInterface.class.getClassLoader());
        mealToAdd = in.readParcelable(Meal.class.getClassLoader());
    }

    public static final Creator<MealDetailsPresenter> CREATOR = new Creator<MealDetailsPresenter>() {
        @Override
        public MealDetailsPresenter createFromParcel(Parcel in) {
            return new MealDetailsPresenter(in);
        }

        @Override
        public MealDetailsPresenter[] newArray(int size) {
            return new MealDetailsPresenter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mealToAdd, flags);
    }


    @Override
    public Completable insertMealToBreakfast(Meal meal) {
      return   repository.insertMealToBreakfast(meal);
    }

    @Override
    public Completable insertMealToLaunch(Meal meal) {
      return   repository.insertMealToLaunch(meal);
    }

    @Override
    public Completable insertMealToDinner(Meal meal) {
       return repository.insertMealToDinner(meal);
    }

    @Override
    public Completable insertMealToFavourite(Meal meal) {
       return repository.insertMealToFavourite(meal);
    }
}
