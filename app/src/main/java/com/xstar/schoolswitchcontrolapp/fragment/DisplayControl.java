package com.xstar.schoolswitchcontrolapp.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.xstar.schoolswitchcontrolapp.AppConstant;
import com.xstar.schoolswitchcontrolapp.R;
import com.xstar.schoolswitchcontrolapp.viewmodel.MainControlViewModel;

public class DisplayControl extends Fragment {

    private MainControlViewModel mViewModel;
    private int sourceDisplay = AppConstant.WALL_HDMI;


    public static DisplayControl newInstance() {
        return new DisplayControl();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Using requireActivity() to share the ViewModel across fragments
        mViewModel = new ViewModelProvider(requireActivity()).get(MainControlViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_display_control, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Status TextComponents
        TextView txtSwitchStatus = view.findViewById(R.id.txtSwitchStatus);

        // Video Source Buttons
        Button btnWallHDMI = view.findViewById(R.id.btnWallHDMI);
        Button btnProjector = view.findViewById(R.id.btnProjector);
        Button btnWireless1 = view.findViewById(R.id.btnWireless1);
        Button btnTVLeft = view.findViewById(R.id.btnTVLeft);
        Button btnWireless2 = view.findViewById(R.id.btnWireless2);
        Button btnTVRight = view.findViewById(R.id.btnTVRight);

        // Ensure connections are active
        mViewModel.connectSwitcher(AppConstant.SWITCHER_IP, AppConstant.SWITCHER_PORT);

        // Observe Connection Status
        mViewModel.getSwitcherConnected().observe(getViewLifecycleOwner(), isConnected -> {
            txtSwitchStatus.setText("Switcher Status: " + (isConnected ? "Connected" : "Not Connected"));
        });

        // Audio Panel Buttons
        Button btnWallHDMIAudio = view.findViewById(R.id.btnWallHDMIAudio);
        Button btnWireless1Audio = view.findViewById(R.id.btnWireless1Audio);
        Button btnWireless2Audio = view.findViewById(R.id.btnWireless2Audio);

        // Menu Panel Buttons
        Button btnAdvance = view.findViewById(R.id.btnAdvance);
        Button btnShutdown = view.findViewById(R.id.btnShutdown);

        // Click Listeners for Video Source
        btnWallHDMI.setOnClickListener(v -> setSource(AppConstant.WALL_HDMI));
        btnWireless1.setOnClickListener(v -> setSource(AppConstant.WIRELESS_1));
        btnWireless2.setOnClickListener(v -> setSource(AppConstant.WIRELESS_2));

        btnProjector.setOnClickListener(v -> setDisplay(AppConstant.PROJECTOR));
        btnTVLeft.setOnClickListener(v -> setDisplay(AppConstant.TV_1));
        btnTVRight.setOnClickListener(v -> setDisplay(AppConstant.TV_2));

        // Click Listeners for Audio
        btnWallHDMIAudio.setOnClickListener(v -> setAudio(AppConstant.WALL_HDMI));
        btnWireless1Audio.setOnClickListener(v -> setAudio(AppConstant.WIRELESS_1));
        btnWireless2Audio.setOnClickListener(v -> setAudio(AppConstant.WIRELESS_2));

        // Click Listeners for Menu
        btnAdvance.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_displayControl_to_advanceControl);
        });

        btnShutdown.setOnClickListener(v -> showShutdownDialog());
    }

    private void showShutdownDialog() {
        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_custom_dialog);

        // To make the corners of the background resource visible, 
        // the underlying window background must be set to transparent.
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnConfirm = dialog.findViewById(R.id.btn_confirm);

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        btnConfirm.setOnClickListener(v -> {
            performShutdown();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void performShutdown() {
        // Implementation for shutting down all devices
        mViewModel.sendToSwitcher("system standby!");
        mViewModel.sendToProjector("power off!");
        mViewModel.sendToLeftTV("power off!");
        mViewModel.sendToRightTV("power off!");
        mViewModel.sendToMeteorizeScreen("screen up!");
    }

    private void setDisplay(int outDisplay) {
        mViewModel.sendToSwitcher("s in " + sourceDisplay + " av out " + outDisplay + "!");
    }

    private void setAudio(int source) {
        mViewModel.sendToSwitcher("s in " + source + " av out " + AppConstant.AUDIO_OUTPUT + "!");
    }

    private void setSource(int source) {
        sourceDisplay = source;
    }
}