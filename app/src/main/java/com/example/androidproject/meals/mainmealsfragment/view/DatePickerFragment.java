package com.example.androidproject.meals.mainmealsfragment.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;


import com.example.androidproject.R;

import java.util.Calendar;


public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {



    private NavController controller;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        controller = NavHostFragment.findNavController(this);
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
    DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.DatePickerTheme, this, year, month, day);
    dialog.getDatePicker().setMinDate(calendar.getTime().getTime());
        calendar.add(Calendar.DAY_OF_MONTH, 6);
    dialog.getDatePicker().setMaxDate(calendar.getTime().getTime());
        return dialog;
    }



    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        controller.getPreviousBackStackEntry().getSavedStateHandle().set(getString(R.string.date),day);
    }
}