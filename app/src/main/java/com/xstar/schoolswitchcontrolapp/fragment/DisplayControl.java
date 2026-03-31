package com.xstar.schoolswitchcontrolapp.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;

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
    private CountDownTimer warmupTimer;


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

        // Warmup UI components
        View layoutWarmup = view.findViewById(R.id.layoutWarmup);
        TextView txtWarmupCountdown = view.findViewById(R.id.txtWarmupCountdown);

        // Video Source Buttons
        Button btnWallHDMI = view.findViewById(R.id.btnWallHDMI);
        Button btnProjector = view.findViewById(R.id.btnProjector);
        Button btnWireless1 = view.findViewById(R.id.btnWireless1);
        Button btnTVLeft = view.findViewById(R.id.btnTVLeft);
        Button btnWireless2 = view.findViewById(R.id.btnWireless2);
        Button btnTVRight = view.findViewById(R.id.btnTVRight);

        // Ensure connections are active
        mViewModel.connectSwitcher(AppConstant.SWITCHER_IP, AppConstant.SWITCHER_PORT);
        mViewModel.connectProjector(AppConstant.PROJECTOR_IP, AppConstant.PROJECTOR_PORT);
        mViewModel.connectLeftTV(AppConstant.LEFT_TV_IP, AppConstant.LEFT_PORT);
        mViewModel.connectRightTV(AppConstant.RIGHT_TV_IP, AppConstant.RIGHT_PORT);

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

        // Initialize system logic
        mViewModel.getIsSystemInitialized().observe(getViewLifecycleOwner(), isInitialized -> {
            if (!isInitialized) {
                startWarmup(layoutWarmup, txtWarmupCountdown);
            } else {
                layoutWarmup.setVisibility(View.GONE);
            }
        });
    }

    private void startWarmup(View layoutWarmup, TextView txtWarmupCountdown) {
        layoutWarmup.setVisibility(View.VISIBLE);
        
        mViewModel.sendHexToProjector(AppConstant.PROJECTOR_ON, true);
        mViewModel.sendHexLeftTV(AppConstant.TV_ON, true);
        mViewModel.sendHexRightTV(AppConstant.TV_ON, true);

        if (warmupTimer != null) {
            warmupTimer.cancel();
        }

        warmupTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtWarmupCountdown.setText((millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                setSource(AppConstant.WALL_HDMI);
                mViewModel.sendHexToProjector(AppConstant.PROJECTOR_SET_SOURCE_HDMI_1, true);
                mViewModel.sendHexRightTV(AppConstant.TV_SET_SOURCE_HDMI_1, true);
                mViewModel.sendHexLeftTV(AppConstant.TV_SET_SOURCE_HDMI_1, true);
                
                mViewModel.setSystemInitialized(true);
                layoutWarmup.setVisibility(View.GONE);
            }
        }.start();
    }

    private void showShutdownDialog() {
        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_custom_dialog);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnConfirm = dialog.findViewById(R.id.btn_confirm);

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        btnConfirm.setOnClickListener(v -> {
            performShutdown();
            dialog.dismiss();
            Navigation.findNavController(requireView()).navigate(R.id.action_displayControl_to_splashScreen);
        });

        dialog.show();
    }

    private void performShutdown() {
        if (warmupTimer != null) {
            warmupTimer.cancel();
        }
        
        mViewModel.setSystemInitialized(false);

        mViewModel.sendHexToProjector(AppConstant.PROJECTOR_OFF, false);
        mViewModel.sendHexLeftTV(AppConstant.TV_OFF, false);
        mViewModel.sendHexRightTV(AppConstant.TV_OFF, false);
        setAudio(AppConstant.AUDIO_OUTPUT);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (warmupTimer != null) {
            warmupTimer.cancel();
        }
    }
}
