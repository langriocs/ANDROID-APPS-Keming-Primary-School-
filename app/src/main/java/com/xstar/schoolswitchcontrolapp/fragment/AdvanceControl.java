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
import android.widget.TextView;

import com.xstar.schoolswitchcontrolapp.AppConstant;
import com.xstar.schoolswitchcontrolapp.R;
import com.xstar.schoolswitchcontrolapp.viewmodel.MainControlViewModel;

public class AdvanceControl extends Fragment {

    private MainControlViewModel mViewModel;

    public static AdvanceControl newInstance() {
        return new AdvanceControl();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Using requireActivity() to share the same ViewModel instance
        mViewModel = new ViewModelProvider(requireActivity()).get(MainControlViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_advance_control, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Status TextComponents
        TextView txtProjectorStatus = view.findViewById(R.id.txtProjectorStatus);
        TextView txtTV1Status = view.findViewById(R.id.txtTV1Status);
        TextView txtTV2Status = view.findViewById(R.id.txtTV2Status);
        TextView txtMotorizeStatus = view.findViewById(R.id.txtMotorizeStatus);

        // Display Control Buttons
        Button btnProjectorOn = view.findViewById(R.id.btnProjectorOn);
        Button btnProjectorOff = view.findViewById(R.id.btnProjectorOff);
        Button btnTVLeftOn = view.findViewById(R.id.btnTVLeft);
        Button btnTVLeftOff = view.findViewById(R.id.btnWireless1);
        Button btnTVRightOn = view.findViewById(R.id.btnTVRightOn);
        Button btnTVRightOff = view.findViewById(R.id.btnTVRightOff);

        // Screen Control Buttons
        Button btnScreenUp = view.findViewById(R.id.btnScreenUp);
        Button btnScreenDown = view.findViewById(R.id.btnScreenDown);

        // Back Button
        Button btnBack = view.findViewById(R.id.btnBack);

        // Ensure connections are active

        mViewModel.connectMeteorizeScreen(AppConstant.M_SCREEN_IP, AppConstant.M_SCREEN_PORT);

        // Observe Connection Status
        mViewModel.getProjectorConnected().observe(getViewLifecycleOwner(), isConnected -> {
            txtProjectorStatus.setText("Projector: " + (isConnected ? "Connected" : "Not Connected"));
        });

        mViewModel.getLeftTVConnected().observe(getViewLifecycleOwner(), isConnected -> {
            txtTV1Status.setText("TV 1: " + (isConnected ? "Connected" : "Not Connected"));
        });

        mViewModel.getRightTVConnected().observe(getViewLifecycleOwner(), isConnected -> {
            txtTV2Status.setText("TV 2: " + (isConnected ? "Connected" : "Not Connected"));
        });

        mViewModel.getMeteorizeScreenConnected().observe(getViewLifecycleOwner(), isConnected -> {
            txtMotorizeStatus.setText("Screen: " + (isConnected ? "Connected" : "Not Connected"));
        });

        // Observe Power States for Option Button behavior
        mViewModel.getProjectorPowerOn().observe(getViewLifecycleOwner(), isOn -> {
            btnProjectorOn.setEnabled(!isOn);
            btnProjectorOff.setEnabled(isOn);
        });

        mViewModel.getLeftTVPowerOn().observe(getViewLifecycleOwner(), isOn -> {
            btnTVLeftOn.setEnabled(!isOn);
            btnTVLeftOff.setEnabled(isOn);
        });

        mViewModel.getRightTVPowerOn().observe(getViewLifecycleOwner(), isOn -> {
            btnTVRightOn.setEnabled(!isOn);
            btnTVRightOff.setEnabled(isOn);
        });

        // Click Listeners for Projector
        btnProjectorOn.setOnClickListener(v -> mViewModel.sendHexToProjector(AppConstant.PROJECTOR_ON, true));
        btnProjectorOff.setOnClickListener(v -> mViewModel.sendHexToProjector(AppConstant.PROJECTOR_OFF, false));

        // Click Listeners for Left TV
        btnTVLeftOn.setOnClickListener(v -> mViewModel.sendHexLeftTV(AppConstant.TV_ON, true));
        btnTVLeftOff.setOnClickListener(v -> mViewModel.sendHexLeftTV(AppConstant.TV_OFF , false));

        // Click Listeners for Right TV
        btnTVRightOn.setOnClickListener(v -> mViewModel.sendHexRightTV(AppConstant.TV_ON, true));
        btnTVRightOff.setOnClickListener(v -> mViewModel.sendHexRightTV(AppConstant.TV_OFF, false));

        // Click Listeners for Screen
//        btnScreenUp.setOnClickListener(v -> mViewModel.sendToMeteorizeScreen("screen up!"));
//        btnScreenDown.setOnClickListener(v -> mViewModel.sendToMeteorizeScreen("screen down!"));

        // Click Listener for Back
        btnBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_advanceControl_to_displayControl);
        });
    }
}