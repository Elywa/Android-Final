package com.example.androidproject.data.dto.ingredients;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientsResponse{

	@SerializedName("meals")
	private List<Ingredient> meals;

	public List<Ingredient> getMeals(){
		return meals;
	}
}