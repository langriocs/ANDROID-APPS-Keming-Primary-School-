package com.xstar.schoolswitchcontrolapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.xstar.schoolswitchcontrolapp.libs.TCPClient;

public class MainControlViewModel extends ViewModel {

    private final MutableLiveData<Boolean> switcherConnected = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> projectorConnected = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> leftTVConnected = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> rightTVConnected = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> meteorizeScreenConnected = new MutableLiveData<>(false);

    private final MutableLiveData<Boolean> projectorPowerOn = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> leftTVPowerOn = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> rightTVPowerOn = new MutableLiveData<>(false);

    private final MutableLiveData<Boolean> isSystemInitialized = new MutableLiveData<>(false);

    private final TCPClient switcherClient;
    private final TCPClient projectorClient;
    private final TCPClient leftTVClient;
    private final TCPClient rightTVClient;
    private final TCPClient meteorizeScreenClient;

    public MainControlViewModel() {
        switcherClient = createClient(switcherConnected, "Switcher");
        projectorClient = createClient(projectorConnected, "Projector");
        leftTVClient = createClient(leftTVConnected, "LeftTV");
        rightTVClient = createClient(rightTVConnected, "RightTV");
        meteorizeScreenClient = createClient(meteorizeScreenConnected, "MeteorizeScreen");
    }

    private TCPClient createClient(MutableLiveData<Boolean> connectionState, String tag) {
        return new TCPClient(
            tag,
            message -> {
                Log.d(tag, "Received: " + message);
            },
            new TCPClient.OnConnectionStatusChanged() {
                @Override
                public void onConnected() {
                    connectionState.postValue(true);
                }

                @Override
                public void onDisconnected() {
                    connectionState.postValue(false);
                }
            }
        );
    }

    public void connectSwitcher(String ip, int port) { switcherClient.connect(ip, port); }
    public void connectProjector(String ip, int port) { projectorClient.connect(ip, port); }
    public void connectLeftTV(String ip, int port) { leftTVClient.connect(ip, port); }
    public void connectRightTV(String ip, int port) { rightTVClient.connect(ip, port); }
    public void connectMeteorizeScreen(String ip, int port) { meteorizeScreenClient.connect(ip, port); }

    public void sendToSwitcher(String msg) { switcherClient.sendMessage(msg); }
    
    public void sendToProjector(String msg, boolean powerOn) { 
        projectorClient.sendMessage(msg);
        projectorPowerOn.postValue(powerOn);
    }

    public void sendHexToProjector(String hex, boolean powerOn) {
        projectorClient.sendHex(hex);
        projectorPowerOn.postValue(powerOn);
    }
    
    public void sendToLeftTV(String msg, boolean powerOn) { 
        leftTVClient.sendMessage(msg); 
        leftTVPowerOn.postValue(powerOn);
    }

    public void sendHexLeftTV(String hex, boolean powerOn) {
        leftTVClient.sendHex(hex);
        leftTVPowerOn.postValue(powerOn);
    }
    
    public void sendToRightTV(String msg, boolean powerOn) { 
        rightTVClient.sendMessage(msg); 
        rightTVPowerOn.postValue(powerOn);
    }

    public void sendHexRightTV(String hex, boolean powerOn) {
        rightTVClient.sendHex(hex);
        rightTVPowerOn.postValue(powerOn);
    }
    
    public void sendToMeteorizeScreen(String msg) { meteorizeScreenClient.sendMessage(msg); }

    public LiveData<Boolean> getSwitcherConnected() { return switcherConnected; }
    public LiveData<Boolean> getProjectorConnected() { return projectorConnected; }
    public LiveData<Boolean> getLeftTVConnected() { return leftTVConnected; }
    public LiveData<Boolean> getRightTVConnected() { return rightTVConnected; }
    public LiveData<Boolean> getMeteorizeScreenConnected() { return meteorizeScreenConnected; }

    public LiveData<Boolean> getProjectorPowerOn() { return projectorPowerOn; }
    public LiveData<Boolean> getLeftTVPowerOn() { return leftTVPowerOn; }
    public LiveData<Boolean> getRightTVPowerOn() { return rightTVPowerOn; }

    public LiveData<Boolean> getIsSystemInitialized() { return isSystemInitialized; }
    public void setSystemInitialized(boolean initialized) { isSystemInitialized.postValue(initialized); }

    @Override
    protected void onCleared() {
        super.onCleared();
        switcherClient.stopClient();
        projectorClient.stopClient();
        leftTVClient.stopClient();
        rightTVClient.stopClient();
        meteorizeScreenClient.stopClient();
    }
}
