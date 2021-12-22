package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {
    // post request to post a data
    @POST("/")

    //post data
    Call<DataModel> createPost(@Body DataModel dataModal);
}
