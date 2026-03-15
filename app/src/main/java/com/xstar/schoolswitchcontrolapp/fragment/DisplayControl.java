package com.xstar.schoolswitchcontrolapp.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xstar.schoolswitchcontrolapp.R;
import com.xstar.schoolswitchcontrolapp.viewmodel.DisplayControlViewModel;

public class DisplayControl extends Fragment {

    private DisplayControlViewModel mViewModel;

    public static DisplayControl newInstance() {
        return new DisplayControl();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DisplayControlViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_display_control, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Video Source Buttons
        Button btnWallHDMI = view.findViewById(R.id.btnWallHDMI);
        Button btnProjector = view.findViewById(R.id.btnProjector);
        Button btnWireless1 = view.findViewById(R.id.btnWireless1);
        Button btnTVLeft = view.findViewById(R.id.btnTVLeft);
        Button btnWireless2 = view.findViewById(R.id.btnWireless2);
        Button btnTVRight = view.findViewById(R.id.btnTVRight);

        // Audio Panel Buttons
        Button btnWallHDMIAudio = view.findViewById(R.id.btnWallHDMIAudio);
        Button btnWireless1Audio = view.findViewById(R.id.btnWireless1Audio);
        Button btnWireless2Audio = view.findViewById(R.id.btnWireless2Audio);

        // Menu Panel Buttons
        Button btnAdvance = view.findViewById(R.id.btnAdvance);
        Button btnShutdown = view.findViewById(R.id.btnShutdown);

        // Click Listeners for Video Source (Inlined)
        btnWallHDMI.setOnClickListener(v -> {
            // TODO: Implement video source switching logic for Wall HDMI

        });
        btnProjector.setOnClickListener(v -> {
            // TODO: Implement video source switching logic for Projector
        });
        btnWireless1.setOnClickListener(v -> {
            // TODO: Implement video source switching logic for Wireless 1
        });
        btnTVLeft.setOnClickListener(v -> {
            // TODO: Implement video source switching logic for TV Left
        });
        btnWireless2.setOnClickListener(v -> {
            // TODO: Implement video source switching logic for Wireless 2
        });
        btnTVRight.setOnClickListener(v -> {
            // TODO: Implement video source switching logic for TV Right
        });

        // Click Listeners for Audio (Inlined)
        btnWallHDMIAudio.setOnClickListener(v -> {
            // TODO: Implement audio switching logic for Wall HDMI Audio
        });
        btnWireless1Audio.setOnClickListener(v -> {
            // TODO: Implement audio switching logic for Wireless 1 Audio
        });
        btnWireless2Audio.setOnClickListener(v -> {
            // TODO: Implement audio switching logic for Wireless 2 Audio
        });

        // Click Listeners for Menu
        btnAdvance.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_displayControl_to_advanceControl);
        });

        btnShutdown.setOnClickListener(v -> {
            // TODO: Implement shutdown logic here
        });
    }
}