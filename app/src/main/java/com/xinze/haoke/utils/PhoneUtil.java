package com.xinze.haoke.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtil {
    private static Pattern NUMBER_PATTERN = Pattern.compile("(^(13\\d|14[57]|15[^4,\\D]|17[13678]|18\\d)\\d{8}|170[^346,\\D]\\d{7})$");

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        // 验证手机号
        p = NUMBER_PATTERN;
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
}
