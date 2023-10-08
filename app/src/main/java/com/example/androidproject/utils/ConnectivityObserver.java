package com.example.androidproject.utils;

import io.reactivex.rxjava3.core.Observable;

public interface ConnectivityObserver {

    Observable<Object> Observe();

    Boolean networkStatus();


}





