package com.snazzis.recom;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AjaxRequest {
    OkHttpClient client = new OkHttpClient();
    String apiUrl = "http://192.168.88.152:3000/";
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(apiUrl + url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(apiUrl + url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    String login(String accessToken,Number userId) throws IOException, JSONException {
        JSONObject obj = new JSONObject();

        obj.put("token",accessToken);
        obj.put("user_id",userId);

        String response = this.post("session",obj.toString());
        return response;
    }
}