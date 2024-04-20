package com.cooba.constant;

public class Topic {
    public static String server(String ipAddress, int port) {
        return "server" + ":" + ipAddress + ":" + port;
    }

    public static String ALL = "all";
}
