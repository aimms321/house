package com.project.house.common.result;

import com.google.common.base.Joiner;
import com.google.common.base.Utf8;
import com.google.common.collect.Maps;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by user on 2018-05-05.
 */
public class ResultMsg {
    public static String errorMsgKey = "errorMsg";
    public static String successMsgKey = "successMsg";
    private String errorMsg;
    private String successMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public static ResultMsg errorMsg(String errorMsg) {
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setErrorMsg(errorMsg);
        return resultMsg;
    }

    public static ResultMsg successMsg(String successMsg) {
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setSuccessMsg(successMsg);
        return resultMsg;
    }

    public Map<String, String> asMap() {
        HashMap<String, String> map = Maps.newHashMap();
        map.put(successMsgKey, successMsg);
        map.put(errorMsgKey, errorMsg);
        return map;
    }

    public String asUrlParams() {
        Map<String, String> map = this.asMap();
        Map<String, String> newMap = Maps.newHashMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                try {
                    newMap.put(entry.getKey(), URLEncoder.encode(entry.getValue(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return Joiner.on("&").withKeyValueSeparator("=").useForNull("").join(newMap);
    }
}
