package com.example.androidproject.data.dto.meal;


import com.example.androidproject.data.dto.Meal;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealsResponse{

	@SerializedName("meals")
	private List<Meal> meals;

	public List<Meal> getMeals(){
		return meals;
	}
}