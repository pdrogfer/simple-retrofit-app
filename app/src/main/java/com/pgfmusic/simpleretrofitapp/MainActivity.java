package com.pgfmusic.simpleretrofitapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * Esta app muestra un uso simple de Retrofit para extraer datos de un servidor que provee json,
 * parsear a objeto Movie, extraer información de él (url del poster) y mostrarla en la UI
 *
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Callback<Movie> {
    private static final String TAG = "MainActivity";

    Button btnSearch;
    EditText searchInput;
    ImageView poster;
    private MovieService movieService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch = (Button) findViewById(R.id.buttonSearch);
        searchInput = (EditText) findViewById(R.id.editTextField);
        poster = (ImageView) findViewById(R.id.imageViewPoster);

        btnSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Retrofit retrofitInstance = new Retrofit
                .Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieService = retrofitInstance.create(MovieService.class);

        Call<Movie> movieCall = movieService.searchMovie(searchInput.getText().toString());
        movieCall.enqueue(this);
    }

    @Override
    public void onResponse(Call<Movie> call, Response<Movie> response) {
        Toast.makeText(this, response.body().getActors(), Toast.LENGTH_LONG).show();
        Log.i(TAG, "onResponse: " + response.body().getPoster());

        Picasso.with(this).load(response.body().getPoster()).into(poster);
    }

    @Override
    public void onFailure(Call<Movie> call, Throwable t) {
        Toast.makeText(this, "Error getting movie info" + t.toString(), Toast.LENGTH_SHORT).show();
    }
}
