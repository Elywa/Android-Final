package com.example.androidproject.data.remote;


import com.example.androidproject.data.dto.area.AreaResponse;
import com.example.androidproject.data.dto.category.AllCategoriesResponse;
import com.example.androidproject.data.dto.ingredients.IngredientsResponse;
import com.example.androidproject.data.dto.meal.MealsResponse;
import com.example.androidproject.data.dto.search.FilterMealResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDataSourceImpl implements RemoteDataSource{


    private static ApiProvider retrofit = null;
    private static RemoteDataSourceImpl remoteDataSource;

    private RemoteDataSourceImpl()
    {

        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build().create(ApiProvider.class);
        }
    }

    public static synchronized  RemoteDataSourceImpl getInstance() {

        if (remoteDataSource == null)
        {
            remoteDataSource = new RemoteDataSourceImpl();
        }
      return remoteDataSource;
    }



    @Override
    public Observable<MealsResponse> searchByNameMealRequest(String prefix) {
        return retrofit.searchMealsByName(prefix);
    }

    @Override
    public Observable<MealsResponse> getFullDetailsById(String id, String requester) {
        return retrofit.getFullDetailsById(id);
    }

    @Override
    public Observable<IngredientsResponse> getAllIngredients() {
        return retrofit.getAllIngredients();
    }

    @Override
    public Observable<AreaResponse> getAllCountries() {
        return retrofit.getAllCountries();
    }

    @Override
    public Observable<AllCategoriesResponse> getAllCategories() {
        return retrofit.getAllCategories();
    }

    @Override
    public Observable<FilterMealResponse> filterMealsByCountry(String country) {
        return retrofit.filterMealsByCountry(country);
    }

    @Override
    public Observable<FilterMealResponse> filterMealsByIngredient(String ingredients) {
        return retrofit.filterMealsByIngredient(ingredients);
    }

    @Override
    public Observable<FilterMealResponse> filterMealsByCategory(String category) {
        return retrofit.filterMealsByCategory(category);
    }

    @Override
    public Observable<MealsResponse>  getMealOfTheDay() {
       return retrofit.getMealOfTheDay();
    }




}
