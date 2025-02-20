package com.trimly.helper;

import jakarta.servlet.http.HttpServletRequest;

public class AppConstants {

    public static final String BASE_URL  = "http://localhost:8080/";

    public static final String EMAIL_MATCHER_REGEX_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String URL_MATCHER_REGEX_PATTERN = "((https?|ftp):\\/\\/)?(?:\\S+(?::\\S*)?@)?(?:(?!10(?:\\.\\d{1,3}){3})(?!127(?:\\.\\d{1,3}){3})(?!169\\.254(?:\\.\\d{1,3}){2})(?!192\\.168(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\x{00a1}\\-\\x{ffff}0-9]+-?)*[a-z\\x{00a1}\\-\\x{ffff}0-9]+)(?:\\.(?:[a-z\\x{00a1}\\-\\x{ffff}0-9]+-?)*[a-z\\x{00a1}\\-\\x{ffff}0-9]+)*(?:\\.(?:[a-z\\x{00a1}\\-\\x{ffff}]{2,})))(?::\\d{2,5})?(?:\\/[^\\s]*)?(\\/)?";

    // Helper method to get client IP
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
