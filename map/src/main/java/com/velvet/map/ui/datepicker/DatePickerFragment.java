package com.velvet.map.ui.datepicker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.velvet.core.Values;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private String tag;
    private DateListener listener;

    public static DatePickerFragment newInstance(DateListener listener, String tag) {
        DatePickerFragment newFragment = new DatePickerFragment();
        Bundle bundle = new Bundle(1);
        bundle.putString(Values.DATE_PICKER_TAG, tag);
        newFragment.setArguments(bundle);
        newFragment.setListener(listener);
        return newFragment;
    }

    public void setListener(DateListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        tag = bundle.getString(Values.DATE_PICKER_TAG);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (listener != null) {
            listener.onDateSet(year, month, dayOfMonth, tag);
        }
    }
}
