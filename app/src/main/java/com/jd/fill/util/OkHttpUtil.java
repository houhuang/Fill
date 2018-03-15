package com.jd.fill.util;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by houhuang on 18/3/15.
 */
public class OkHttpUtil {
    public static void sendOkHttpRequest(String url, Callback callback)
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);

    }
}
