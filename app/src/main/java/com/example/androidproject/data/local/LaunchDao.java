package com.example.androidproject.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidproject.data.dto.table.Launch;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;


@Dao
public interface LaunchDao {



        @Insert(onConflict = OnConflictStrategy.REPLACE)
        Completable  insert(Launch meal);

        @Update
        Completable update(Launch meal);

        @Delete
        Completable  delete(Launch meal);


        @Query("SELECT * FROM Launch")
        Observable<List<Launch>> getAllMeals();

}
