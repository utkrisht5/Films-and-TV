package com.example.filmsandtv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {
    private RecyclerView rv;
    private ProgressBar bar;
    EditText search;
    ArrayList<movie_info> movieInfoArrayList;
    movieAdapter adapter;
    ImageButton go;
    LinearLayout linearLayout;
    static movie_viewModel model;
    ProgressBar progressBar;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        rv = findViewById(R.id.rv);
        search = findViewById(R.id.search_here);
        linearLayout = findViewById(R.id.linearLayout);
        text = findViewById(R.id.title);
        model = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(movie_viewModel.class);
        go = findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                progressBar.setVisibility(View.VISIBLE);
                if (search.getText().toString().isEmpty()) {
                    search.setError("Please enter search query");
                    return;
                }
                getMoviesInfo(search.getText().toString());
            }
        });
    }

    private void getMoviesInfo(String query) {
        movieInfoArrayList = new ArrayList<>();
        for (int i = 0; i < query.length(); i++) {
            if (query.codePointAt(i) == 32) {
                char[] chars = query.toCharArray();
                chars[i] = '+';
                query = String.valueOf(chars);
            }
        }
        String url = "https://api.themoviedb.org/3/search/multi?api_key=216a1a11469ad220b5b12f0009090e70&language=en-US&page=1&include_adult=false&query=" + query;
        JsonObjectRequest movieObjrequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                progressBar.setVisibility(View.GONE);
                text.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);
                try {
                    JSONArray search = response.getJSONArray("results");
                    for (int i = 0; i < search.length(); i++) {
                        JSONObject a = search.getJSONObject(i);
                        String name, year, type, imageUrl;
                        if (a.has("name")) name = a.getString("name");
                        else if (a.has("title")) name = a.getString("title");
                        else if (a.has("original_title")) name = a.getString("original_title");
                        else name = a.getString("original_name");
                        if (a.has("first_air_date")) year = a.getString("first_air_date");
                        else year = a.getString("release_date");
                        type = a.getString("media_type");
                        imageUrl = a.getString("poster_path");
                        String id = a.getString("id");
                        movie_info Movie = new movie_info(name, year, type, imageUrl, id);
                        movieInfoArrayList.add(Movie);
                        adapter = new movieAdapter(movieInfoArrayList, SearchResultsActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchResultsActivity.this, RecyclerView.VERTICAL, false);
                        rv.setLayoutManager(linearLayoutManager);
                        rv.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    // displaying a toast message when we get any error from API
                    Toast.makeText(SearchResultsActivity.this, "No Data Found" + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // also displaying error message in toast.
                Toast.makeText(SearchResultsActivity.this, "Error found is " + error, Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(movieObjrequest);
    }

    }
