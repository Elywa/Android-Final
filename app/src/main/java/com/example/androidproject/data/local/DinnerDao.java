package com.example.androidproject.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidproject.data.dto.table.Dinner;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;


@Dao
public interface DinnerDao {



        @Insert(onConflict = OnConflictStrategy.REPLACE)
        Completable insert(Dinner meal);

        @Update
        Completable  update(Dinner meal);

        @Delete
        Completable  delete(Dinner meal);

        @Query("SELECT * FROM Dinner")
        Observable<List<Dinner>> getAllMeals();


}
