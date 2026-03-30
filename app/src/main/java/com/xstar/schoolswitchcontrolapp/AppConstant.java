package com.xstar.schoolswitchcontrolapp;

public class AppConstant {
    public static final String SWITCHER_IP = "192.168.1.10"; //"192.168.1.130"; //
    public static final int SWITCHER_PORT = 8000;

    public static final String PROJECTOR_IP = "192.168.1.15"; //"192.168.1.130"; //
    public static final int PROJECTOR_PORT = 23;

    public static final String LEFT_TV_IP = "192.168.1.13";
    public static final int LEFT_PORT = 1515;

    public static final String RIGHT_TV_IP = "192.168.1.14";
    public static final int RIGHT_PORT = 1515;

    public static final String M_SCREEN_IP = "192.168.1.16";
    public static final int M_SCREEN_PORT = 8887;

    public static final int WALL_HDMI = 1;
    public static final int WIRELESS_1 = 1;
    public static final int WIRELESS_2 = 2;

    public static final int PROJECTOR = 1;
    public static final int TV_1 = 2;
    public static final int TV_2 = 3;
    public static final int MOTORIZE_SCREEN = 4;

    public static final int AUDIO_OUTPUT = 4;
    public static final String TV_ON = "AA 11 00 01 01 13";
    public static final String TV_OFF = "AA 11 00 01 00 12";


    public static final String PROJECTOR_ON = "PWR1";
    public static final String PROJECTOR_OFF = "PWR0";
    public static final String PROJECTOR_SET_SOURCE_HDMI_1 = "SRC5";
    public static final String TV_SET_SOURCE_HDMI_1 = "AA 14 00 01 21 36";

}
