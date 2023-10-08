package com.example.androidproject.meals.favourites.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

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
import com.example.androidproject.databinding.FragmentFavouriteMealBinding;
import com.example.androidproject.databinding.FragmentScheduleBinding;
import com.example.androidproject.meals.favourites.presenter.FavouritePresenter;
import com.example.androidproject.meals.mainmealsfragment.view.OnMealClickListener;
import com.example.androidproject.meals.schedule.presenter.SchedulePresenter;
import com.example.androidproject.meals.schedule.view.DaysAdapter;
import com.example.androidproject.meals.schedule.view.OnDayListener;
import com.example.androidproject.meals.schedule.view.ScheduleFragment;
import com.example.androidproject.meals.schedule.view.ScheduleFragmentDirections;
import com.example.androidproject.meals.schedule.view.ScheduleMealsAdapter;
import com.example.androidproject.meals.schedule.view.ScheduleViewInterface;
import com.example.androidproject.utils.CustomFlexLayoutManager;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class FavouriteMealFragment extends Fragment implements OnDayListener, OnMealClickListener, FavouriteViewInterface {

    private FragmentFavouriteMealBinding binding;
    private NavController controller;
    private FavouritePresenter presenter;

    private FirebaseAuth mAuth;

    private FirebaseFirestore firebaseFirestore;
    private final FavouriteMealAdapter favouriteAdapter = new FavouriteMealAdapter();
    private final DaysAdapter daysAdapter = new DaysAdapter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFavouriteMealBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        setRecyclerView();
        initPresenterAndSendRequests(savedInstanceState);



        setRecyclerView(binding.daysRecycler,daysAdapter);

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
        presenter = new FavouritePresenter(Repository.getInstance(RemoteDataSourceImpl.getInstance()
                , LocalDataSourceImp.getInstance(getContext())),this);

        presenter.getAllFavouriteMeals();
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

    private void setRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter adapter)
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



        List<Meal> favourites = presenter.getPresenterFavourites().stream().map(Favourite::favouriteToMealMapper).filter(meal ->
                        meal.getDay().equals(numberOfDay))
                .collect(Collectors.toList());



        setRecyclerVisibility(binding.favouritesTextView,binding.favouritesRecycler,!favourites.isEmpty());

        favouriteAdapter.setMeals(favourites, getContext(), R.string.favourites,FavouriteMealFragment.this);

        daysAdapter.notifyDataSetChanged();

        favouriteAdapter.notifyDataSetChanged();
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
        controller.navigate(FavouriteMealFragmentDirections.actionFavouriteFragmentToMealDetailsFragment(transitionView.getTransitionName(),meal),extras);
    }
    @Override
    public void onFavouritesSuccessCallback() {
        List<Meal> favourites = presenter.getPresenterFavourites().stream().map(Favourite::favouriteToMealMapper).filter(meal ->
                        meal.getDay().equals(presenter.getCurrentDay()))
                .collect(Collectors.toList());
        setRecyclerVisibility(binding.favouritesTextView,binding.favouritesRecycler,!favourites.isEmpty());
        favouriteAdapter.setMeals(favourites
                , getContext(), R.string.favourites,FavouriteMealFragment.this);


    }
    private void setRecyclerView() {
        FlexboxLayoutManager layoutManager = new CustomFlexLayoutManager(getContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.SPACE_EVENLY);
        binding.favouritesRecycler.setLayoutManager(layoutManager);
        binding.favouritesRecycler.setAdapter(favouriteAdapter);
        binding.favouritesRecycler.setNestedScrollingEnabled(false);

    }



}