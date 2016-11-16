package com.pgfmusic.simpleretrofitapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface MovieService {

    @GET("?")
    Call<List<Movie>> searchMovie(@Query("s") String title);
}
