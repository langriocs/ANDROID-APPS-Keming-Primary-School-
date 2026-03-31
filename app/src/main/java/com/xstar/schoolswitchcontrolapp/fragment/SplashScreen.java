package com.xstar.schoolswitchcontrolapp.fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xstar.schoolswitchcontrolapp.R;
import com.xstar.schoolswitchcontrolapp.viewmodel.SplashScreenViewModel;

public class SplashScreen extends Fragment {

    private SplashScreenViewModel mViewModel;

    public static SplashScreen newInstance() {
        return new SplashScreen();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SplashScreenViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Set click listener on the entire view (splash screen)
        view.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_splashScreen_to_displayControl);
        });

        // Also set click listener on the button specifically, as it might intercept clicks
        Button btnPressStart = view.findViewById(R.id.btn_press_start);
        btnPressStart.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_splashScreen_to_displayControl);
        });
    }
}
