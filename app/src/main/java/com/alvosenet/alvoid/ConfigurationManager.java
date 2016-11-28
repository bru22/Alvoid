package com.alvosenet.alvoid;

import android.content.Context;

import com.github.dubu.lockscreenusingservice.Lockscreen;

/**
 * Created by brucezeng on 11/21/2016.
 */

public class ConfigurationManager {
    private static String startTime = null;
    private static String endTime = null;
    private static Boolean isLockScreen = false;

    public static void loadConfiguration(Context context) {
        ConfigurationUtil.init(context);
        startTime = ConfigurationUtil.getString(Lockscreen.STARTTIME);
        endTime = ConfigurationUtil.getString(Lockscreen.ENDTIME);
        isLockScreen = ConfigurationUtil.getBoolean(Lockscreen.ISLOCK);
    }

    public static Boolean getIsLockScreen() {
        return isLockScreen;
    }

    public static void setIsLockScreen(Boolean isLockScreen) {
        ConfigurationManager.isLockScreen = isLockScreen;
        ConfigurationUtil.setBoolean(Lockscreen.ISLOCK, isLockScreen);
    }

    public static String getEndTime() {
        return endTime;
    }

    public static void setEndTime(String endTime) {
        ConfigurationManager.endTime = endTime;
        ConfigurationUtil.setString(Lockscreen.ENDTIME, endTime);
    }

    public static String getStartTime() {
        return startTime;
    }

    public static void setStartTime(String startTime) {
        ConfigurationManager.startTime = startTime;
        ConfigurationUtil.setString(Lockscreen.STARTTIME, startTime);
    }







}
