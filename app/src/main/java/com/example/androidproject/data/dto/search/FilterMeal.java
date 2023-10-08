
package com.example.androidproject.data.dto.search;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;



public class FilterMeal implements Parcelable {

    @SerializedName("idMeal")
    private String mIdMeal;
    @SerializedName("strMeal")
    private String mStrMeal;
    @SerializedName("strMealThumb")
    private String mStrMealThumb;

    public String getIdMeal() {
        return mIdMeal;
    }

    public void setIdMeal(String idMeal) {
        mIdMeal = idMeal;
    }

    public String getStrMeal() {
        return mStrMeal;
    }

    public void setStrMeal(String strMeal) {
        mStrMeal = strMeal;
    }

    public String getStrMealThumb() {
        return mStrMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        mStrMealThumb = strMealThumb;
    }


    protected FilterMeal(Parcel in) {
        mIdMeal = in.readString();
        mStrMeal = in.readString();
        mStrMealThumb = in.readString();
    }


    public static final Creator<FilterMeal> CREATOR = new Creator<FilterMeal>() {
        @Override
        public FilterMeal createFromParcel(Parcel in) {
            return new FilterMeal(in);
        }

        @Override
        public FilterMeal[] newArray(int size) {
            return new FilterMeal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mIdMeal);
        dest.writeString(mStrMeal);
        dest.writeString(mStrMealThumb);
    }

}
