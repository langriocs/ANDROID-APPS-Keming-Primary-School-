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

        // Ensure connections are active (will reuse existing ones if already connected)
        mViewModel.connectProjector(AppConstant.PROJECTOR_IP, AppConstant.PROJECTOR_PORT);
        mViewModel.connectLeftTV(AppConstant.LEFT_TV_IP, AppConstant.LEFT_PORT);
        mViewModel.connectRightTV(AppConstant.RIGHT_TV_IP, AppConstant.RIGHT_PORT);
        mViewModel.connectMeteorizeScreen(AppConstant.M_SCREEN_IP, AppConstant.M_SCREEN_PORT);

        // Observe Connection Status
        mViewModel.getProjectorConnected().observe(getViewLifecycleOwner(), isConnected -> {
            txtProjectorStatus.setText("Projector Status: " + (isConnected ? "Connected" : "Not Connected"));
        });

        mViewModel.getLeftTVConnected().observe(getViewLifecycleOwner(), isConnected -> {
            txtTV1Status.setText("TV 1 Status: " + (isConnected ? "Connected" : "Not Connected"));
        });

        mViewModel.getRightTVConnected().observe(getViewLifecycleOwner(), isConnected -> {
            txtTV2Status.setText("TV 2 Status: " + (isConnected ? "Connected" : "Not Connected"));
        });

        mViewModel.getMeteorizeScreenConnected().observe(getViewLifecycleOwner(), isConnected -> {
            txtMotorizeStatus.setText("Motorize Screen Status: " + (isConnected ? "Connected" : "Not Connected"));
        });

        Button btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_advanceControl_to_displayControl);
        });
    }
}