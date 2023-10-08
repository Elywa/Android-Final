package com.example.androidproject.data.remote;



import com.example.androidproject.data.dto.area.AreaResponse;
import com.example.androidproject.data.dto.category.AllCategoriesResponse;
import com.example.androidproject.data.dto.ingredients.IngredientsResponse;
import com.example.androidproject.data.dto.meal.MealsResponse;
import com.example.androidproject.data.dto.search.FilterMealResponse;


import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiProvider {

    @GET("random.php")
    Observable<MealsResponse> getMealOfTheDay();

    @GET("lookup.php")
    Observable<MealsResponse> getFullDetailsById(@Query("i") String id);

    @GET("search.php")
    Observable<MealsResponse> searchMealsByName(@Query("s") String name);



    @GET("filter.php")
    Observable<FilterMealResponse> filterMealsByIngredient(@Query("i") String IngredientName);

    @GET("filter.php")
    Observable<FilterMealResponse> filterMealsByCategory(@Query("c") String categoryName);

    @GET("filter.php")
    Observable<FilterMealResponse> filterMealsByCountry(@Query("a") String countryName);
    @GET("list.php?c=list")
    Observable<AllCategoriesResponse> getAllCategories();


    @GET("list.php?a=list")
    Observable<AreaResponse> getAllCountries();

    @GET("list.php?i=list")
    Observable<IngredientsResponse> getAllIngredients();



}

