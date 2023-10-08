package com.example.androidproject.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.androidproject.data.dto.Meal;

import java.util.List;


@Dao
public interface MealsDao {



        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(Meal meal);

        @Update
        void update(Meal meal);

        @Delete
        void delete(Meal meal);

        @Query("SELECT * FROM Meal")
        LiveData<List<Meal>> getAllMeals();  //LiveData automatically notifies any registered observers

}
