package com.alvosenet.alvoid;

/**
 * Created by brucezeng on 11/21/2016.
 */

public class ConfigurationManager {
    private static String startTime = null;
    private static String endTime = null;

    public static String getEndTime() {
        return endTime;
    }

    public static void setEndTime(String endTime) {
        ConfigurationManager.endTime = endTime;
    }

    public static String getStartTime() {
        return startTime;
    }

    public static void setStartTime(String startTime) {
        ConfigurationManager.startTime = startTime;
    }







}
