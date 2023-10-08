package com.example.androidproject.meals.schedule.presenter;



import com.example.androidproject.data.dto.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

public interface SchedulePresenterInterface {

  void getAllBreakfastMeals();
   void  getAllLaunchMeals();
  void getAllDinnerMeals();



}
