package com.example.androidproject.meals.favourites.presenter;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.androidproject.data.dto.Day;
import com.example.androidproject.data.dto.table.Breakfast;
import com.example.androidproject.data.dto.table.Dinner;
import com.example.androidproject.data.dto.table.Favourite;
import com.example.androidproject.data.dto.table.Launch;
import com.example.androidproject.data.repository.RepositoryInterface;
import com.example.androidproject.meals.favourites.view.FavouriteViewInterface;
import com.example.androidproject.meals.schedule.presenter.SchedulePresenter;
import com.example.androidproject.meals.schedule.view.ScheduleViewInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavouritePresenter implements FavouritePresenterInterface , Parcelable {

    private final RepositoryInterface repository;

    private List<Favourite> presenterFavourites ;
    private Day selectedDay ;
    private FavouriteViewInterface viewInterface ;
    public Day getSelectedDay() {
        return selectedDay;
    }
    public void setSelectedDay(Day selectedDay) {
        this.selectedDay = selectedDay;
    }
    public List<Favourite> getPresenterFavourites() {
        return presenterFavourites;
    }
    private  Day[] days;
    public FavouritePresenter(RepositoryInterface repository, FavouriteViewInterface viewInterface)
    {
        this.repository = repository;
        this.viewInterface = viewInterface;
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

    protected FavouritePresenter(Parcel in) {
        repository = in.readParcelable(RepositoryInterface.class.getClassLoader());
        this.presenterFavourites = in.createTypedArrayList(Favourite.CREATOR);
        days = in.createTypedArray(Day.CREATOR);
        selectedDay = in.readParcelable(Day.class.getClassLoader());
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(presenterFavourites);
        dest.writeTypedArray(days, flags);
        dest.writeParcelable(selectedDay,flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FavouritePresenter> CREATOR = new Creator<FavouritePresenter>() {
        @Override
        public FavouritePresenter createFromParcel(Parcel in) {
            return new FavouritePresenter(in);
        }

        @Override
        public FavouritePresenter[] newArray(int size) {
            return new FavouritePresenter[size];
        }
    };

    public String getCurrentDay() {

        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
        String day  = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        return day;
    }

    public void getAllFavouriteMeals() {

        repository.getAllFavouriteMeals().debounce(500, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Favourite>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<Favourite> favourites) {


                if (!favourites.isEmpty())
                {
                    presenterFavourites.clear();
                    presenterFavourites.addAll(favourites);
                    viewInterface.onFavouritesSuccessCallback();

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
