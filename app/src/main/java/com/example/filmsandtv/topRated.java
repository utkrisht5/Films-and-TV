package com.example.filmsandtv;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class topRated extends Fragment {
    private RecyclerView grid;

    public topRated() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        grid = view.findViewById(R.id.topRated);
        fetchMovies(grid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_rated, container, false);
    }

    private void fetchMovies(RecyclerView grid) {
        String url = "https://api.themoviedb.org/3/movie/top_rated?api_key=216a1a11469ad220b5b12f0009090e70&language=en-US";
        ArrayList<movie_info> Movies = new ArrayList<>();
        JsonObjectRequest trending = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray result = response.getJSONArray("results");
                    for(int i=0; i<result.length(); i++){
                        JSONObject o = result.getJSONObject(i);
                        String poster = o.getString("poster_path");
                        String id = o.getString("id");
                        movie_info m = new movie_info(poster, id, null);
                        Movies.add(m);
                    }
                    grid.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    movie_adapter Adapter = new movie_adapter(getContext(), Movies);
                    grid.setAdapter(Adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    // displaying a toast message when we get any error from API
                    Toast.makeText(getContext(), "No Data Found" + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // also displaying error message in toast.
                Toast.makeText(getContext(), "Error found is " + error, Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(trending);
    }

}