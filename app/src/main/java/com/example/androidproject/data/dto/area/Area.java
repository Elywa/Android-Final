package com.example.androidproject.data.dto.area;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Area implements Parcelable {
    @SerializedName("strArea")
    private String area;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }


    public Area()
    {

    }

    protected Area(Parcel in) {
        area = in.readString();
    }

    public static final Creator<Area> CREATOR = new Creator<Area>() {
        @Override
        public Area createFromParcel(Parcel in) {
            return new Area(in);
        }

        @Override
        public Area[] newArray(int size) {
            return new Area[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(area);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
