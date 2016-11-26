package com.pgfmusic.simpleretrofitapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Callback<List<Movie>> {
    private static final String TAG = "MainActivity";

    Button btnSearch;
    EditText searchInput;
    ImageView poster;
    private MovieService movieService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        searchInput = (EditText) findViewById(R.id.editText);
        poster = (ImageView) findViewById(R.id.imageView);

        btnSearch.setOnClickListener(this);

        Retrofit retrofitInstance = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieService = retrofitInstance.create(MovieService.class);

    }

    @Override
    public void onClick(View view) {

        Retrofit retrofitInstance = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieService = retrofitInstance.create(MovieService.class);

        Call<List<Movie>> movieCall = movieService.searchMovie(searchInput.getText().toString());
        movieCall.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
        Toast.makeText(this, response.body().getActors(), Toast.LENGTH_LONG).show();
        Log.i(TAG, "onResponse: " + response.body().getPoster());

        Picasso.with(this).load(response.body().getPoster()).into(poster);
    }

    @Override
    public void onFailure(Call<List<Movie>> call, Throwable t) {
        Toast.makeText(this, "Error recuperando pelicula" + t.toString(), Toast.LENGTH_SHORT).show();
    }
}
