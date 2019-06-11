package cn.zhangz.common.utils;


import cn.zhangz.common.config.constants.SecurityConstants;

import javax.servlet.http.Cookie;

public class TokenCookieUtil {

    public static Cookie deleteTokenCookie(){
        Cookie cookie = new Cookie(SecurityConstants.token_key, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        return cookie;
    }

    public static Cookie createTokenCookie(String value) {
        Cookie cookie = new Cookie(SecurityConstants.token_key, value);
        cookie.setPath("/");
        cookie.setMaxAge(43200);
        return cookie;
    }

    public static String getTokenFromCookie(Cookie[] cookies){
        if(null == cookies || 0 == cookies.length){
            return null;
        }
        Cookie cookie;
        for (int i = 0; i < cookies.length; i++) {
            cookie = cookies[i];
            if(SecurityConstants.token_key.equalsIgnoreCase(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }
}
