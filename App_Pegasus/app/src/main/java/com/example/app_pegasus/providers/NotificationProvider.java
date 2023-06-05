package com.example.app_pegasus.providers;

import com.example.app_pegasus.models.FCMBody;
import com.example.app_pegasus.models.FCMResponse;
import com.example.app_pegasus.retrofit.IFCMApi;
import com.example.app_pegasus.retrofit.RetrofitChildren;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationProvider {

    private String url = "https://fcm.googleapis.com";

    public NotificationProvider() {
    }

    public Call<FCMResponse> sendNotification(FCMBody body) {
        return RetrofitChildren.getChildrenObject(url).create(IFCMApi.class).send(body);
    }
}
