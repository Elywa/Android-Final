package com.example.androidproject.data.remote;


import com.example.androidproject.data.dto.area.AreaResponse;
import com.example.androidproject.data.dto.category.AllCategoriesResponse;
import com.example.androidproject.data.dto.ingredients.IngredientsResponse;
import com.example.androidproject.data.dto.meal.MealsResponse;
import com.example.androidproject.data.dto.search.FilterMealResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;

public interface RemoteDataSource {


    public  Observable<MealsResponse>  getMealOfTheDay();

    public Observable<MealsResponse> searchByNameMealRequest(String prefix);
    public  Observable<MealsResponse> getFullDetailsById(String id,String requester);
    public Observable<IngredientsResponse> getAllIngredients();
    public Observable<AreaResponse> getAllCountries();
    public Observable<AllCategoriesResponse> getAllCategories();
    public Observable<FilterMealResponse> filterMealsByCountry(String country);

    public Observable<FilterMealResponse> filterMealsByIngredient(String ingredients);

    public  Observable<FilterMealResponse>  filterMealsByCategory(String category);



}
