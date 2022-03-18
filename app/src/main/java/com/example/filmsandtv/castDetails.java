package com.example.filmsandtv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class castDetails extends AppCompatActivity {
    ImageView photo;
    TextView name, dob, place, dod, job;
    Button bio;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_details);
        photo = findViewById(R.id.imageView2);
        name = findViewById(R.id.name);
        dob = findViewById(R.id.dob);
        place = findViewById(R.id.place);
        dod = findViewById(R.id.dod);
        job = findViewById(R.id.job);
        bio = findViewById(R.id.biography);
        personDetails();
    }

    public void personDetails() {
        id = getIntent().getStringExtra("id");
        String url = "https://api.themoviedb.org/3/person/" + id + "?api_key=216a1a11469ad220b5b12f0009090e70&language=en-US";
        JsonObjectRequest detailsObj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    name.setText(response.getString("name"));
                    if(response.getString("birthday") != "null")
                    dob.setText("Date of birth: " + response.getString("birthday"));
                    else dob.setVisibility(View.GONE);
                    if(response.getString("place_of_birth") != "null")
                    place.setText("Place of birth: " + response.getString("place_of_birth"));
                    else place.setVisibility(View.GONE);
                    if(response.getString("deathday") != "null"){
                        dod.setText("Date of death: " + response.getString("deathday"));
                    } else dod.setVisibility(View.GONE);
                    if(response.getString("known_for_department") != "null")
                    job.setText("Profession: " + response.getString("known_for_department"));
                    else job.setVisibility(View.GONE);
                    Glide.with(castDetails.this).load("https://image.tmdb.org/t/p/original" + response.getString("profile_path")).into(photo);
                    bio.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(castDetails.this, castBio.class);
                            String s = null;
                            try {
                                s = response.getString("biography");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            i.putExtra("bio", s);
                            startActivity(i);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    // displaying a toast message when we get any error from API
                    Toast.makeText(castDetails.this, "No Data Found" + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // also displaying error message in toast.
                Toast.makeText(castDetails.this, "Error found is " + error, Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(detailsObj);
    }


}