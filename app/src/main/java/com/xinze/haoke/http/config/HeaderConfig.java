package com.xinze.haoke.http.config;

import com.xinze.haoke.App;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置请求头
 *
 * @author lxf
 */
public class HeaderConfig {
    public static HashMap<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<>(2);
        if (App.mUser != null) {
            headers.put("sessionid", App.mUser.getSessionid());
            headers.put("userid", App.mUser.getId());
            return headers;
        }
        return null;
    }
}
