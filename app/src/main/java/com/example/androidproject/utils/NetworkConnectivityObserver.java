package com.example.androidproject.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.util.Log;

import androidx.annotation.NonNull;

import io.reactivex.rxjava3.core.Observable;

public class NetworkConnectivityObserver  implements ConnectivityObserver{

    private final ConnectivityManager connectivityManager ;

    NetworkRequest networkRequest = new NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build();






    public  NetworkConnectivityObserver(Context context)
    {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE) ;


    }
    @Override
    public Observable<Object> Observe() {
        return Observable.create(emitter -> {
            ConnectivityManager.NetworkCallback networkCallback =    new ConnectivityManager.NetworkCallback(){
                @Override
                public void onAvailable(@NonNull Network network) {
                    super.onAvailable(network);
                    emitter.onNext(NetworkStatus.Available);
                }

                @Override
                public void onLosing(@NonNull Network network, int maxMsToLive) {
                    super.onLosing(network, maxMsToLive);
                    emitter.onNext(NetworkStatus.Losing);
                }

                @Override
                public void onLost(@NonNull Network network) {
                    super.onLost(network);
                    emitter.onNext(NetworkStatus.Lost);
                }

                @Override
                public void onUnavailable() {
                    super.onUnavailable();
                    Log.d("network bottom","unavailable");
                    emitter.onNext(NetworkStatus.Unavailable);
                }


            };
            connectivityManager.registerNetworkCallback(networkRequest,networkCallback);
        });
    }

    @Override
    public Boolean networkStatus() {
        return connectivityManager.getActiveNetworkInfo() == null;
    }
}
