package sheridan.sjostrom.assignment3;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static sheridan.sjostrom.assignment3.utilities.Constants.DATE_KEY;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    interface DateSetListener{
        void onDateSet(int year, int month, int dayOfMonth);
    }

    private DateSetListener mDateSetListener;

    public DatePickerFragment() {
        // Required empty public constructor
    }

    public static DatePickerFragment getInstance(Date date){
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(DATE_KEY, date);
        fragment.setArguments(arguments);
        return fragment;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker

        Date date;
        Bundle arguments = getArguments();
        if(arguments != null){
            date = (Date) arguments.getSerializable(DATE_KEY);
        }else{
            date = new Date();
        }

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of TimePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        if(mDateSetListener != null){
            mDateSetListener.onDateSet(year, month, day);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof DateSetListener){
            mDateSetListener = (DateSetListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mDateSetListener = null;
    }
}
