package com.xinze.haoke.utils;

public class UrlUtils {

    public static String appendHttp(String url) {
        return "http://" + url;
    }

    public static String appendHttps(String url) {
        return "https://" + url;
    }
}
