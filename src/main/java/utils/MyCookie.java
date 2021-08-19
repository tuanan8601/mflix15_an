package utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class MyCookie {
    public Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }
}