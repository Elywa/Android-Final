package com.example.androidproject;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.androidproject.databinding.FragmentSplashBinding;


import com.example.androidproject.main_activity.MainActivity;
import com.google.firebase.auth.FirebaseAuth;


public class SplashFragment extends Fragment {

    private FragmentSplashBinding binding;

    private NavController controller ;

    private FirebaseAuth mAuth ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSplashBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);

        mAuth = FirebaseAuth.getInstance();
      binding.lottieSplash.addAnimatorListener(new Animator.AnimatorListener() {
          @Override
          public void onAnimationStart(@NonNull Animator animator) {

          }

          @Override
          public void onAnimationEnd(@NonNull Animator animator) {
              if (((MainActivity)requireActivity()).connectivityObserver.networkStatus() && mAuth.getCurrentUser() != null){
                  //controller.navigate(NavGraphDirections.actionToScheduleFragment());
                  return;
              }
              controller.navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment());
          }

          @Override
          public void onAnimationCancel(@NonNull Animator animator) {

          }

          @Override
          public void onAnimationRepeat(@NonNull Animator animator) {

          }
      });
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onPause() {
        super.onPause();
        requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
    }
}