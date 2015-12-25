package com.pgfmusic.simpleretrofitapp;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by pedrogonzalezferrandez on 25/12/15.
 */
public interface CustomerService {

    @GET("query_customers_remote_json.php")

    Call<List<Customer>> getCustomer();
}
