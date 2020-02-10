package com.example.ad_team10.clients;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestService {
    private static final String URL = "http://192.168.1.106/Stationery/api/";
    private static Retrofit retrofit;
    private StationeryService apiService;

    public RestService()
    {
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        apiService = retrofit.create(StationeryService.class);
    }

    public StationeryService getService()
    {
        return apiService;
    }
}
