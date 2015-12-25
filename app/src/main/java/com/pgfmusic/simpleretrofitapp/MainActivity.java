package com.pgfmusic.simpleretrofitapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/*
 * Esta app muestra un uso simple de Retrofit para extraer datos de un servidor(que provee json)
 * en formato array<Objeto>, en este caso customers (de la base de datos 'tnb')
 */

public class MainActivity extends AppCompatActivity {

    TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = (TextView) findViewById(R.id.tv_result);

        // all this should be in another class, don't put logic in Activities, just GUI staff,
        // a cleaner approach for better testing
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://pgfmusic.com/php_course/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CustomerService customerService = retrofit.create(CustomerService.class);
        final Call<List<Customer>> customers = customerService.getCustomer();
        customers.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Response<List<Customer>> response) {
                String status = response.message();
                String result = response.body().toString();
                tvResult.setText(result);
            }

            @Override
            public void onFailure(Throwable t) {
                tvResult.setText(t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
