package com.pgfmusic.simpleretrofitapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface MovieService {

    @GET("?")
    Call<Movie> searchMovie(@Query("t") String title);
}
