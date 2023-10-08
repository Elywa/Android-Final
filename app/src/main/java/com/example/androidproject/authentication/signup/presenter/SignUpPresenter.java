package com.example.androidproject.authentication.signup.presenter;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.regex.Pattern;

public class SignUpPresenter implements SignUpPresenterInterface, Parcelable {


    private String email;

    private String name;

    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SignUpPresenter()
    {

    }


    protected SignUpPresenter(Parcel in) {
        email = in.readString();
        name = in.readString();
        password = in.readString();
    }
    public static final Creator<SignUpPresenter> CREATOR = new Creator<SignUpPresenter>() {
        @Override
        public SignUpPresenter createFromParcel(Parcel in) {
            return new SignUpPresenter(in);
        }

        @Override
        public SignUpPresenter[] newArray(int size) {
            return new SignUpPresenter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(name);
        dest.writeString(password);
    }

    @Override
    public boolean isValidEmail(String email) {
        String emailRegex = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(email).matches();
    }

    @Override
    public boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%_^&+=])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        return pattern.matcher(password).matches();
    }

    @Override
    public boolean isValidUsername(String username) {
        return  username.length() > 8 ;
    }
}
