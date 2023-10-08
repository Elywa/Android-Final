package com.example.androidproject.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class Day implements Parcelable {

    private String dayName;
    private String dayNumber;
    private boolean selected = false;

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(String dayNumber) {
        this.dayNumber = dayNumber;
    }

    public Day()
    {

    }

    protected Day(Parcel in) {
        dayName = in.readString();
        dayNumber = in.readString();
        selected = in.readByte() != 0;
    }

    public static final Creator<Day> CREATOR = new Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel in) {
            return new Day(in);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dayName);
        dest.writeString(dayNumber);
        dest.writeByte((byte)(selected ? 1 : 0));
    }
}
