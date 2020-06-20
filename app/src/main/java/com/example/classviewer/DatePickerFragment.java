package com.example.classviewer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    int selectedYear;
    int selectedDay;
    int selectedMonth;

    public DatePickerFragment(int selectedYear, int selectedDay, int selectedMonth) {
        this.selectedYear = selectedYear;
        this.selectedDay = selectedDay;
        this.selectedMonth = selectedMonth;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        if (selectedDay != 0){
            c.set(this.selectedYear,this.selectedMonth,this.selectedDay);
        }
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(),this,year,month,day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        MainActivity activity = (MainActivity) getActivity();
        final Calendar c = Calendar.getInstance();
        c.set(year,month,dayOfMonth);
        c.set(year,month,dayOfMonth,8,0,0);
        Long t = c.getTimeInMillis() /1000;
        activity.getDateClasses(t,dayOfMonth,month,year);
    }
}
