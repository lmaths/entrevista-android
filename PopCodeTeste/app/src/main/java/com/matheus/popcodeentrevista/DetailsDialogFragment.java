package com.matheus.popcodeentrevista;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matheus.popcodeentrevista.models.Result;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsDialogFragment extends DialogFragment {

    public static DetailsDialogFragment detailsDialogFragment(Result result) {
        DetailsDialogFragment detailsDialogFragment = new DetailsDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("result", result);
        detailsDialogFragment.setArguments(bundle);
        return detailsDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_dialog, container, false);
        Bundle bundle = getArguments();
        Result result = (Result) bundle.get("result");
        return  view;
    }
}
