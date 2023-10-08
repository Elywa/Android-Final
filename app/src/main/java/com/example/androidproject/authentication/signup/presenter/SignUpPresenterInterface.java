package com.example.androidproject.authentication.signup.presenter;

public interface SignUpPresenterInterface {

    public boolean isValidEmail(String email);
    public boolean isValidPassword(String password);

    public boolean isValidUsername(String username);


}
