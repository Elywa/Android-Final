package com.example.androidproject.data.dto.category;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MealCategory implements Parcelable {
    @SerializedName("strCategory")
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public MealCategory()
    {

    }
    public static final Creator<MealCategory> CREATOR = new Creator<MealCategory>() {
        @Override
        public MealCategory createFromParcel(Parcel in) {
            return new MealCategory(in);
        }

        @Override
        public MealCategory[] newArray(int size) {
            return new MealCategory[size];
        }
    };


    protected MealCategory(Parcel in) {
        category = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
