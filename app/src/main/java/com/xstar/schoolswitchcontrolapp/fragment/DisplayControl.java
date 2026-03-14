package com.xstar.schoolswitchcontrolapp.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xstar.schoolswitchcontrolapp.R;
import com.xstar.schoolswitchcontrolapp.viewmodel.DisplayControlViewModel;

public class DisplayControl extends Fragment {

    private DisplayControlViewModel mViewModel;

    public static DisplayControl newInstance() {
        return new DisplayControl();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_display_control, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DisplayControlViewModel.class);
        // TODO: Use the ViewModel
    }

}