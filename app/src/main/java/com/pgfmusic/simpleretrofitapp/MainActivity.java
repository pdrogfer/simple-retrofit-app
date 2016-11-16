package com.pgfmusic.simpleretrofitapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * Esta app muestra un uso simple de Retrofit para extraer datos de un servidor(que provee json)
 * en formato array<Objeto>, en este caso customers (de la base de datos 'tnb')
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    Button btnSearch;
    EditText searchInput;
    TextView textViewResult;
    private MovieService movieService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        searchInput = (EditText) findViewById(R.id.editText);
        textViewResult = (TextView) findViewById(R.id.textViewResult);
        btnSearch.setOnClickListener(this);

        Retrofit retrofitInstance = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieService = retrofitInstance.create(MovieService.class);

    }

    @Override
    public void onClick(View view) {
        final Call<List<Movie>> movieCall = movieService.searchMovie(searchInput.getText().toString());
        movieCall.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                Log.i(TAG, "onResponse: " + response.body().);
                for (int i = 0; i < response.body().size(); i++) {
                    Movie tempMovie = response.body().get(i);
                    Log.i(TAG, "onResponse: " + tempMovie.getTitle() + ", " + tempMovie.getActors());

                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.toString());
            }
        });
    }

}
