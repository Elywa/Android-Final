package com.example.androidproject.main_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.androidproject.NavGraphDirections;
import com.example.androidproject.R;
import com.example.androidproject.data.local.LocalDataSourceImp;
import com.example.androidproject.data.remote.RemoteDataSourceImpl;
import com.example.androidproject.data.repository.Repository;
import com.example.androidproject.databinding.ActivityMainBinding;
import com.example.androidproject.meals.mainmealsfragment.presenter.MealsPresenter;
import com.example.androidproject.utils.NetworkConnectivityObserver;
import com.example.androidproject.utils.NetworkStatus;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
    public ActivityMainBinding binding;

    public NetworkConnectivityObserver connectivityObserver ;
    private Observable<Object> fragmentObservable;

    private NavHostFragment navHostFragment;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        mAuth = FirebaseAuth.getInstance();
        connectivityObserver  = new NetworkConnectivityObserver(getApplicationContext());
        setupNavigation();
        currentFragmentObservable();
        connectivityObserve();
        FirebaseApp.getInstance();

    }


    private void connectivityObserve()
    {

        Observable.combineLatest(connectivityObserver.Observe().distinctUntilChanged(),
                        fragmentObservable.distinctUntilChanged(), (networkStatus, fragmentStatus) -> networkStatus)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Object status) {

                        NetworkStatus networkStatus = (NetworkStatus)status;
                        switch (networkStatus){
                            case Lost:
                            case Unavailable: {
                                binding.bottomNavigationView.setVisibility(View.GONE);
                                if (mAuth.getCurrentUser() != null) {
                                    navHostFragment.getNavController().clearBackStack(R.id.scheduleFragment);
                                  // navHostFragment.getNavController().navigate(NavGraphDirections.);
                                }else{
                                    navHostFragment.getNavController().clearBackStack(R.id.loginFragment);
                                   navHostFragment.getNavController().navigate(NavGraphDirections.actionToLoginFragment());
                                }
                                break;
                            }

                            case Available:{
                                if (mAuth.getCurrentUser() != null)
                                {

                                    binding.bottomNavigationView.setVisibility(View.VISIBLE);
                                    showBottomNavigation(fragmentsToHideBottomNavigation());
                                    break;
                                }
                            }


                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private void showBottomNavigation(Boolean show)
    {
        Log.d("showBottom",show.toString());
        if (show) binding.bottomNavigationView.setVisibility(View.GONE);
    }

    private boolean fragmentsToHideBottomNavigation(){
        int currentDestination = navHostFragment.getNavController().getCurrentDestination().getId();
        return  currentDestination == R.id.splashFragment||currentDestination == R.id.loginFragment
                || currentDestination  == R.id.signUp;
    }
    private void setupNavigation()
    {
        navHostFragment =  ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment));
        NavigationUI.setupWithNavController(binding.bottomNavigationView,navHostFragment.getNavController());
        binding.bottomNavigationView.findViewById(R.id.loginFragment).setOnClickListener(view1 -> {
            mAuth.signOut();

            binding.bottomNavigationView.setVisibility(View.GONE);
            navHostFragment.getNavController().navigate(NavGraphDirections.actionToLoginFragment());
        });
    }
    private void currentFragmentObservable()
    {
        fragmentObservable =  Observable.create(emitter -> {
            navHostFragment.getNavController().addOnDestinationChangedListener((navController, navDestination, bundle) -> {
                int currentDestination = navHostFragment.getNavController().getCurrentDestination().getId();
                emitter.onNext( currentDestination== R.id.splashFragment
                        ||currentDestination == R.id.loginFragment
                        ||currentDestination == R.id.signUp
                );
            });
        }).observeOn(AndroidSchedulers.mainThread());

    }



}