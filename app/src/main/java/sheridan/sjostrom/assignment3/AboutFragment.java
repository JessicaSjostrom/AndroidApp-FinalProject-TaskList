package sheridan.sjostrom.assignment3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AboutFragment extends DialogFragment {

    public AboutFragment() {
    }

    public AboutFragment newInstance() {return new AboutFragment();}

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Assignment 3");
        builder.setMessage("Jessica Sjostrom, Sheridan College 2019");

        builder.setPositiveButton("OK", null);
        return builder.create();
    }
}
