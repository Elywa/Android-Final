package com.example.androidproject.meals.schedule.view;

import static androidx.recyclerview.widget.RecyclerView.Adapter;
import static androidx.recyclerview.widget.RecyclerView.GONE;
import static androidx.recyclerview.widget.RecyclerView.VISIBLE;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;

import com.example.androidproject.data.dto.Day;
import com.example.androidproject.data.dto.Meal;
import com.example.androidproject.data.dto.table.Breakfast;
import com.example.androidproject.data.dto.table.Dinner;
import com.example.androidproject.data.dto.table.Favourite;
import com.example.androidproject.data.dto.table.Launch;
import com.example.androidproject.data.local.LocalDataSourceImp;
import com.example.androidproject.data.remote.RemoteDataSourceImpl;
import com.example.androidproject.data.repository.Repository;
import com.example.androidproject.databinding.FragmentScheduleBinding;
import com.example.androidproject.meals.mainmealsfragment.view.OnMealClickListener;
import com.example.androidproject.meals.schedule.presenter.SchedulePresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;

import java.util.List;
import java.util.stream.Collectors;


public class ScheduleFragment extends Fragment implements OnDayListener, OnMealClickListener,ScheduleViewInterface {

    private FragmentScheduleBinding binding;
    private NavController controller;
    private SchedulePresenter presenter;

    private FirebaseAuth mAuth;

    private FirebaseFirestore firebaseFirestore;
    private final ScheduleMealsAdapter breakfastAdapter = new ScheduleMealsAdapter();
    private final ScheduleMealsAdapter launchAdapter = new ScheduleMealsAdapter();
    private final ScheduleMealsAdapter dinnerAdapter = new ScheduleMealsAdapter();

    private final  DaysAdapter daysAdapter = new DaysAdapter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentScheduleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        initPresenterAndSendRequests(savedInstanceState);



        setRecyclerView(binding.daysRecycler,daysAdapter);
        setRecyclerView(binding.breakFastRecycler,breakfastAdapter);
        setRecyclerView(binding.launchRecycler,launchAdapter);
        setRecyclerView(binding.dinnerRecycler,dinnerAdapter);

        setDaysAdapter();


    }

    @Override
    public void onStart() {
        super.onStart();
        enableInteraction();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(getString(R.string.presenter),presenter);
    }

    private void enableInteraction() {
        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    private void initPresenterAndSendRequests(Bundle savedInstanceState)
    {
        if (savedInstanceState != null)
        {
            presenter = savedInstanceState.getParcelable(getString(R.string.presenter));
            if (presenter != null){

            getDataFromPresenter(presenter.getSelectedDay().getDayNumber());

            }else{
                initPresenterAndRequests();
            }
        }else{

             initPresenterAndRequests();

        }
    }

    private void initPresenterAndRequests()
    {
        presenter = new SchedulePresenter(Repository.getInstance(RemoteDataSourceImpl.getInstance()
                , LocalDataSourceImp.getInstance(getContext())),this);
        presenter.getAllBreakfastMeals();
        presenter.getAllLaunchMeals();
        presenter.getAllDinnerMeals();

    }










    private void setRecyclerVisibility(View recyclerTitle ,View recycler, boolean visible)
    {

        if (visible){
            recyclerTitle.setVisibility(VISIBLE);
            recycler.setVisibility(VISIBLE);
        }else{
            recyclerTitle.setVisibility(GONE);
            recycler.setVisibility(GONE);

        }
    }

    private void setRecyclerView(RecyclerView recyclerView, Adapter adapter)
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void setDaysAdapter()
    {
        daysAdapter.setDays(Arrays.asList(presenter.getWeekDays()),getContext(),this);

    }

    private void getDataFromPresenter(String  numberOfDay)
    {
        List<Meal> breakfasts = presenter.getPresenterBreakfasts().stream().map(Breakfast::breakFastToMealMapper)
                .filter(meal ->
                        meal.getDay().equals(numberOfDay))
                .collect(Collectors.toList());


        List<Meal> launches = presenter.getPresenterLaunches().stream().map(Launch::launchToMealMapper)
                .filter(meal ->
                        meal.getDay().equals(numberOfDay))
                .collect(Collectors.toList());


        List<Meal> dinners = presenter.getPresenterDinners().stream().map(Dinner::dinnerToMealMapper)
                .filter(meal ->
                        meal.getDay().equals(numberOfDay)) .collect(Collectors.toList());


        List<Meal> favourites = presenter.getPresenterFavourites().stream().map(Favourite::favouriteToMealMapper).filter(meal ->
                        meal.getDay().equals(numberOfDay))
                .collect(Collectors.toList());


        setRecyclerVisibility(binding.breakFastTextView,binding.breakFastRecycler,!breakfasts.isEmpty());
        setRecyclerVisibility(binding.launchTextView,binding.launchRecycler,!launches.isEmpty());
        setRecyclerVisibility(binding.dinnerTextView,binding.dinnerRecycler,!dinners.isEmpty());


        breakfastAdapter.setMeals(breakfasts
                , getContext(),R.string.breakfast,ScheduleFragment.this);
        launchAdapter.setMeals(launches
                , getContext(),R.string.launch,ScheduleFragment.this);

        dinnerAdapter.setMeals(dinners, getContext(),R.string.dinner,ScheduleFragment.this
               );



        daysAdapter.notifyDataSetChanged();
        breakfastAdapter.notifyDataSetChanged();
        launchAdapter.notifyDataSetChanged();
        dinnerAdapter.notifyDataSetChanged();

    }


    @Override
    public void onDayClicked(Day day) {
          day.setSelected(true);
          presenter.setSelectedDay(day);
          getDataFromPresenter(day.getDayNumber());
    }

    @Override
    public void onMealClicked(Meal meal, ImageView transitionView) {
        FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                .addSharedElement(transitionView, transitionView.getTransitionName())
                .build();
        controller.navigate(ScheduleFragmentDirections.actionScheduleFragmentToMealDetailsFragment(transitionView.getTransitionName(),meal),extras);
    }


    @Override
    public void onBreakfastsSuccessCallback() {
        List<Meal> breakfasts = presenter.getPresenterBreakfasts().stream().map(Breakfast::breakFastToMealMapper)
                .filter(meal ->
                        meal.getDay().equals(presenter.getCurrentDay()))
                .collect(Collectors.toList());
            setRecyclerVisibility(binding.breakFastTextView,binding.breakFastRecycler,!breakfasts.isEmpty());
            breakfastAdapter.setMeals(breakfasts
                    , getContext(),R.string.breakfast,ScheduleFragment.this);

    }

    @Override
    public void onLaunchesSuccessCallback() {

        List<Meal> launches = presenter.getPresenterLaunches().stream().map(Launch::launchToMealMapper)
                .filter(meal ->
                        meal.getDay().equals(presenter.getCurrentDay()))
                .collect(Collectors.toList());

            setRecyclerVisibility(binding.launchTextView,binding.launchRecycler,!launches.isEmpty());
            launchAdapter.setMeals(launches
                    , getContext(),R.string.launch,ScheduleFragment.this);


    }

    @Override
    public void onDinnersSuccessCallback() {

        List<Meal> dinners = presenter.getPresenterDinners().stream().map(Dinner::dinnerToMealMapper)
                .filter(meal ->
                        meal.getDay().equals(presenter.getCurrentDay())) .collect(Collectors.toList());

            setRecyclerVisibility(binding.dinnerTextView,binding.dinnerRecycler,!dinners.isEmpty());
            dinnerAdapter.setMeals(dinners
                           , getContext(),R.string.dinner,ScheduleFragment.this
                   );



    }


}