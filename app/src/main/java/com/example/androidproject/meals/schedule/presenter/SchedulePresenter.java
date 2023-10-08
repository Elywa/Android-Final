package com.example.androidproject.meals.schedule.presenter;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;


import com.example.androidproject.data.dto.Day;
import com.example.androidproject.data.dto.Meal;
import com.example.androidproject.data.dto.table.Breakfast;
import com.example.androidproject.data.dto.table.Dinner;
import com.example.androidproject.data.dto.table.Favourite;
import com.example.androidproject.data.dto.table.Launch;
import com.example.androidproject.data.repository.RepositoryInterface;
import com.example.androidproject.meals.schedule.view.ScheduleViewInterface;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SchedulePresenter implements  SchedulePresenterInterface, Parcelable {

    private final RepositoryInterface repository;

    private List<Breakfast> presenterBreakfasts ;
    private List<Launch> presenterLaunches ;
    private List<Dinner> presenterDinners ;
    private List<Favourite> presenterFavourites ;

    private ScheduleViewInterface viewInterface ;

    private Day selectedDay ;

    public Day getSelectedDay() {
        return selectedDay;
    }

    public void setSelectedDay(Day selectedDay) {
        this.selectedDay = selectedDay;
    }

    public List<Breakfast> getPresenterBreakfasts() {
        return presenterBreakfasts;
    }



    public List<Launch> getPresenterLaunches() {
        return presenterLaunches;
    }



    public List<Dinner> getPresenterDinners() {
        return presenterDinners;
    }



    public List<Favourite> getPresenterFavourites() {
        return presenterFavourites;
    }


    private  Day[] days;
    public SchedulePresenter(RepositoryInterface repository,ScheduleViewInterface viewInterface)
    {
        this.repository = repository;
        this.viewInterface = viewInterface;
        presenterBreakfasts = new ArrayList<>();
        presenterLaunches = new ArrayList<>();
        presenterDinners = new ArrayList<>();
        presenterFavourites = new ArrayList<>();


    }





    public Day[] getWeekDays()
    {
        if (days == null){
            days = new Day[7];
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
            for (int i = 0; i < 7; i++)
            {
                Day day = new Day();
                String dayName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                day.setDayName(dayName);
                day.setDayNumber(String.valueOf(dayOfMonth));
                if (selectedDay != null && day.getDayNumber().equals(selectedDay.getDayNumber())){

                    day.setSelected(true);
                }
                days[i] = day;
                calendar.add(Calendar.DAY_OF_MONTH, 1);

            }

            if (selectedDay == null){
                days[0].setSelected(true);
                selectedDay = days[0];
            }
        }
        return this.days;
    }


    protected SchedulePresenter(Parcel in) {
        repository = in.readParcelable(RepositoryInterface.class.getClassLoader());
        this.presenterBreakfasts = in.createTypedArrayList(Breakfast.CREATOR);
        this.presenterLaunches = in.createTypedArrayList(Launch.CREATOR);
        this.presenterDinners = in.createTypedArrayList(Dinner.CREATOR);
        this.presenterFavourites = in.createTypedArrayList(Favourite.CREATOR);
        days = in.createTypedArray(Day.CREATOR);
        selectedDay = in.readParcelable(Day.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(presenterBreakfasts);
        dest.writeTypedList(presenterLaunches);
        dest.writeTypedList(presenterDinners);
        dest.writeTypedList(presenterFavourites);
        dest.writeTypedArray(days, flags);
        dest.writeParcelable(selectedDay,flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SchedulePresenter> CREATOR = new Creator<SchedulePresenter>() {
        @Override
        public SchedulePresenter createFromParcel(Parcel in) {
            return new SchedulePresenter(in);
        }

        @Override
        public SchedulePresenter[] newArray(int size) {
            return new SchedulePresenter[size];
        }
    };

    public String getCurrentDay() {

       Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
      String day  = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        return day;
    }

    public void getAllBreakfastMeals() {

        repository.getAllBreakfastMeals().debounce(500, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Breakfast>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<Breakfast> breakfasts) {


                if (!breakfasts.isEmpty())
                {
                    Log.d("breakfast",breakfasts.size()+"");
                    presenterBreakfasts.clear();
                    presenterBreakfasts.addAll(breakfasts);
                    viewInterface.onBreakfastsSuccessCallback();
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


    public void getAllLaunchMeals() {


        repository.getAllLaunchMeals().debounce(500, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Launch>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<Launch> launches) {


                if (!launches.isEmpty())
                {
                    presenterLaunches.clear();
                    presenterLaunches.addAll(launches);
                    viewInterface.onLaunchesSuccessCallback();
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


    public void getAllDinnerMeals() {

        repository.getAllDinnerMeals().debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Dinner>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<Dinner> dinners) {


                if (!dinners.isEmpty())
                {
                    presenterDinners.clear();
                    presenterDinners.addAll(dinners);
                    viewInterface.onDinnersSuccessCallback();
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






}
