package com.ll;

import java.util.HashMap;
import java.util.Map;

public class Rq {
    private String actionCode;
    private Map<String, String> params;

    //삭제?id=1&name=ㅋㅋ
    public Rq(String cmd) {
        String[] cmdBits = cmd.split("\\?", 2);

        this.actionCode = cmdBits[0];

        params = new HashMap<>();

        if(cmdBits.length == 1) return;

        String[] paramsBits = cmdBits[1].split("&");

        for(String paramStr : paramsBits) {
            String[] paramStrBits = paramStr.split("=", 2);

            if(paramStrBits.length == 1) continue;

            params.put(paramStrBits[0], paramStrBits[1]);
        }
    }

    public String getActionCode() {
        return actionCode;
    }

    public String getParam(String key) {
        return params.get(key);
    }

    public long getLongParam(String key, long defaultValue) {
        try {
            return Long.parseLong(getParam(key));
        } catch (NumberFormatException e) {

        }
        return defaultValue;
    }
}
