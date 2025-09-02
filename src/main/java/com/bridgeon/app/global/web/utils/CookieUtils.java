package com.bridgeon.app.global.web.utils;

import org.springframework.http.ResponseCookie;

public class CookieUtils {

    public static String authCookie(String name, String value, long maxAgeSeconds, boolean httpOnly, boolean secure, String sameSite) {

        return ResponseCookie.from(name, value)
                .httpOnly(httpOnly)
                .secure(secure)
                .sameSite(sameSite)
                .path("/")
                .maxAge(maxAgeSeconds)
                .build()
                .toString();
    }


    public static String deleteCookie(String name, boolean secure, String sameSite) {
        return ResponseCookie.from(name, "")
                .httpOnly(true)
                .secure(secure)
                .sameSite(sameSite)
                .path("/")
                .maxAge(0)
                .build()
                .toString();
    }
}
