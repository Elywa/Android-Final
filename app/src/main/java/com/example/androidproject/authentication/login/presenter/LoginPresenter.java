package com.example.androidproject.authentication.login.presenter;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.regex.Pattern;

public class LoginPresenter  implements  LoginPresenterInterface, Parcelable {


    private String email;
    private String password;



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


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginPresenter()
    {

    }

    protected LoginPresenter(Parcel in) {
        email = in.readString();
        password = in.readString();
    }

    public static final Creator<LoginPresenter> CREATOR = new Creator<LoginPresenter>() {
        @Override
        public LoginPresenter createFromParcel(Parcel in) {
            return new LoginPresenter(in);
        }

        @Override
        public LoginPresenter[] newArray(int size) {
            return new LoginPresenter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(password);
    }
}
