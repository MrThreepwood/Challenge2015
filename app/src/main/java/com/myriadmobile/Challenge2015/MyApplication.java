package com.myriadmobile.Challenge2015;

import android.app.Application;

import API.ChallengeAPI;
import retrofit.RestAdapter;

/**
 * Created by joshuaswoyer on 10/20/15.
 */
public class MyApplication extends Application {
    public static final String BASE_URL = "https://challenge2015.myriadapps.com/api/v1";
    private static ChallengeAPI apiInstance;
    private static RestAdapter restAdapter;

    @Override
    public void onCreate() {
        super.onCreate();
        restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(BASE_URL).build();
    }
    public ChallengeAPI getApiInstance() {
        if(apiInstance == null) {
            apiInstance = restAdapter.create(ChallengeAPI.class);
        }
        return apiInstance;
    }
}
