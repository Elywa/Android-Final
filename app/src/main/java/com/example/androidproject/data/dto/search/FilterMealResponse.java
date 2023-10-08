
package com.example.androidproject.data.dto.search;

import com.google.gson.annotations.SerializedName;

import java.util.List;



public class FilterMealResponse {

    @SerializedName("meals")
    private List<FilterMeal> mMeals;

    public List<FilterMeal> getMeals() {
        return mMeals;
    }

    public void setMeals(List<FilterMeal> meals) {
        mMeals = meals;
    }

}
