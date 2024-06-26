package com.example.myapplication.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("eventInfo")
    Call<ApiResponse> getTrafficEvents(
            @Query("apiKey") String apiKey,
            @Query("type") String type,
            @Query("eventType") String eventType,
            @Query("getType") String getType
    );

}
