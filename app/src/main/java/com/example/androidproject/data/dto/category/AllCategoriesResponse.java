package com.example.androidproject.data.dto.category;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllCategoriesResponse {




        @SerializedName("meals")
        private List<MealCategory> mealCategories;

        public List<MealCategory> getMealCategories() {
            return mealCategories;
        }

        public void setMealCategories(List<MealCategory> mealCategories) {
            this.mealCategories = mealCategories;
        }

}
