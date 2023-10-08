package com.example.androidproject.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.androidproject.data.dto.Meal;
import com.example.androidproject.data.dto.table.Breakfast;
import com.example.androidproject.data.dto.table.Dinner;
import com.example.androidproject.data.dto.table.Favourite;
import com.example.androidproject.data.dto.table.Launch;


@Database(entities = {Meal.class, Launch.class, Breakfast.class, Dinner.class, Favourite.class}, version = 1)
public abstract class MealsDatabase extends RoomDatabase {
    public abstract MealsDao MealsDao();
    public abstract BreakfastDao BreakfastDao();
    public abstract LaunchDao LaunchDao();
    public abstract DinnerDao DinnerDao();
    public abstract FavouriteDao FavouriteDao();

    private static MealsDatabase instance;

    public static synchronized MealsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MealsDatabase.class, "meals_database")
                    .build();
        }
        return instance;
    }
}