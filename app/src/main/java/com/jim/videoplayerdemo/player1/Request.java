package com.jim.videoplayerdemo.player1;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jim on 2018/4/26.
 */

public class Request {

//    private final String MATCHER_URL= Pattern.compile("GET");

    private String requestUrl;

    public Request(String request){
        initRequestParam(request);
    }

    private void initRequestParam(String request){
        if (!TextUtils.isEmpty(request)){
            this.requestUrl=findUrl(request);
        }
    }

    private String findUrl(String request) {
        String url="";
        return url;
    }

}
