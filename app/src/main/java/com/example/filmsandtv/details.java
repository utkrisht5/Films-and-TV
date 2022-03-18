package com.example.filmsandtv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class details extends AppCompatActivity {
    ImageView backdrop;
    String id, type, trailer_url, key;
    TextView movie_title, tagline, date, genres, overview;
    castAdapter Adapter;
    RecyclerView cast, crew, moreLikeThis;
    ArrayList<cast> cast_member, crew_member, recommend;
    Button trailer;
    TextView more;
    movie_viewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details2);
        backdrop = findViewById(R.id.imageView4);
        movie_title= findViewById(R.id.movie_title);
        tagline = findViewById(R.id.tagline);
        date = findViewById(R.id.date);
        genres = findViewById(R.id.genres);
        overview = findViewById(R.id.overview);
        cast = findViewById(R.id.cast_pictures);
        crew = findViewById(R.id.crew_pictures);
        trailer = findViewById(R.id.trailer);
        moreLikeThis = findViewById(R.id.recommend_pictures);
        more = findViewById(R.id.recommend);
        movieDetails();
    }


    public void movieDetails() {
        id = getIntent().getStringExtra("id");
        type = getIntent().getStringExtra("type");
        if(type == null){
            type = "movie";
        }
        cast_member = new ArrayList<>();
        crew_member = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/" + type + "/" + id + "?api_key=216a1a11469ad220b5b12f0009090e70&language=en-US&append_to_response=credits";
        JsonObjectRequest detailsObj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                        if(response.has("title")) movie_title.setText(response.getString("title"));
                        else if(response.has("name")) movie_title.setText(response.getString("name"));
                        else movie_title.setText(response.getString("original_title"));
                        if(response.has("tagline")) tagline.setText(response.getString("tagline"));
                        else tagline.setVisibility(View.GONE);
                        if(response.has("release_date")) date.setText(response.getString("release_date"));
                        else date.setVisibility(View.GONE);
                    if(response.has("genres")) {
                        JSONArray genre = response.getJSONArray("genres");
                        for (int i = 0; i < genre.length(); i++) {
                            JSONObject o = genre.getJSONObject(i);
                            genres.append(o.getString("name") + " ");
                        }
                    }
                    else genres.setVisibility(View.GONE);
                    if(response.has("overview")) {
                        if (response.getString("overview").length() > 200){
                            overview.setText(response.getString("overview").substring(0, 200) + "...");
                        }else{
                            overview.setText(response.getString("overview"));
                        }
                    }else overview.setVisibility(View.GONE);
                        Glide.with(details.this).load("https://image.tmdb.org/t/p/original" + response.getString("backdrop_path")).into(backdrop);
                        JSONObject credits = response.getJSONObject("credits");
                        JSONArray ca = credits.getJSONArray("cast");
                        for(int i=0; i<ca.length(); i++){
                            JSONObject m = ca.getJSONObject(i);
                            String url = m.getString("profile_path");
                            String name = m.getString("name");
                            String id = m.getString("id");
                            cast_member.add(new cast(url, name, id));
                        }
                        putData(cast, cast_member);
                    JSONArray cr = credits.getJSONArray("crew");
                    for(int i=0; i<cr.length(); i++){
                        JSONObject m = cr.getJSONObject(i);
                        String url = m.getString("profile_path");
                        String name = m.getString("name");
                        String id = m.getString("id");
                        crew_member.add(new cast(url, name, id));
                    }
                    putData(crew, crew_member);
                } catch (
                        JSONException e) {
                    e.printStackTrace();
                    // displaying a toast message when we get any error from API
                    Toast.makeText(details.this, "No Data Found" + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // also displaying error message in toast.
                Toast.makeText(details.this, "Error found is " + error, Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(detailsObj);

        String url1 = "https://api.themoviedb.org/3/" + type + "/" + id + "/recommendations?api_key=216a1a11469ad220b5b12f0009090e70&language=en-US&page=1";
        ArrayList<movie_info> Movies = new ArrayList<>();
        JsonObjectRequest trending = new JsonObjectRequest(Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray result = response.getJSONArray("results");
                    if(result.length() == 0) {
                        moreLikeThis.setVisibility(View.GONE);
                        more.setVisibility(View.GONE);
                    }
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject o = result.getJSONObject(i);
                        String poster = o.getString("poster_path");
                        String id = o.getString("id");
                        String type = o.getString("media_type");
                        movie_info m = new movie_info(poster, id, type);
                        Movies.add(m);
                    }
                    moreLikeThis.setLayoutManager(new LinearLayoutManager(details.this, RecyclerView.HORIZONTAL, false));
                    movie_adapter adapter = new movie_adapter(details.this, Movies);
                    moreLikeThis.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    // displaying a toast message when we get any error from API
                    Toast.makeText(details.this, "No Data Found" + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // also displaying error message in toast.
                Toast.makeText(details.this, "Error found is " + error, Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(details.this);
        queue.add(trending);

    }

    private void putData(RecyclerView grid, ArrayList<cast> casts) {
        grid.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        Adapter = new castAdapter(this, casts);
        grid.setAdapter(Adapter);
    }

    public void play(View v){
        Intent i = new Intent(this, trailer.class);
        id = getIntent().getStringExtra("id");
        type = getIntent().getStringExtra("type");
        if(type==null){
            type = "movie";
        }
        String url = "https://api.themoviedb.org/3/" + type + "/" + id + "?api_key=216a1a11469ad220b5b12f0009090e70&language=en-US&append_to_response=videos";
        JsonObjectRequest trailerRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject videos = response.getJSONObject("videos");
                    JSONArray results = videos.getJSONArray("results");
                    for(int i=0; i<results.length(); i++) {
                        JSONObject o = results.getJSONObject(i);
                        String s = o.getString("name");
                        String t = o.getString("type");
                        if (s.equals("Official Trailer")) {
                            key = o.getString("key");
                            break;
                        }
                        else if(t.equals("Trailer")){
                            key = o.getString("key");
                            break;
                        }
                    }
                    i.putExtra("key", key);
                    startActivity(i);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    // displaying a toast message when we get any error from API
                    Toast.makeText(details.this, "No Data Found" + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // also displaying error message in toast.
                Toast.makeText(details.this, "Error found is " + error, Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(trailerRequest);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.add_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        switch (id){
//            case R.id.add:{
//                String i = getIntent().getStringExtra("id");
//                String name = getIntent().getStringExtra("name");
//                System.out.print(name);
//                String year = getIntent().getStringExtra("year");
//                String type = getIntent().getStringExtra("type");
//                String url = getIntent().getStringExtra("url");
//                movie_info movie = new movie_info(name, year, type, url, i);
//                model = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(movie_viewModel.class);
//                model.insert(movie);
//                Toast.makeText(this, "Added to watchlist", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//            default: return super.onOptionsItemSelected(item);
//        }
//
    }
