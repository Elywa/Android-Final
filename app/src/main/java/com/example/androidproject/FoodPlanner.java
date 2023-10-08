package com.example.androidproject;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class FoodPlanner extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }

}
