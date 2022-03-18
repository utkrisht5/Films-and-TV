package com.example.filmsandtv;

import android.content.Context;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class movieAdapter extends RecyclerView.Adapter<movieAdapter.movieViewHolder> {
    private List<movie_info> movieInfoArrayList;
    private Context mcontext;

    // creating constructor for array list and context.
    public movieAdapter(List<movie_info> movieInfoArrayList, Context mcontext) {
        this.movieInfoArrayList = movieInfoArrayList;
        this.mcontext = mcontext;
    }

    @NonNull

    public movieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout for item of recycler view item.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new movieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull movieViewHolder holder, int position) {
        movie_info movie = movieInfoArrayList.get(position);
        holder.name.setText(movie.getName());
        holder.year.setText("Release date : " + movie.getYear());
        String type = movie.getType();
        if(type.equals("tv")) holder.type.setText("Type : Series");
        else if(type.equals("movie")) holder.type.setText("Type : Movie");
        Glide.with(mcontext).load("https://image.tmdb.org/t/p/w1280" + movie.getImageUrl()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, details.class);
                intent.putExtra("name", movie.getName());
                intent.putExtra("type", movie.getType());
                intent.putExtra("id", movie.getId());
                mcontext.startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        // inside get item count method we
        // are returning the size of our array list.
        return movieInfoArrayList.size();
    }

    public void setMovies(List<movie_info> movieInfoArrayList){
        this.movieInfoArrayList = movieInfoArrayList;
        notifyDataSetChanged();
    }

    public movie_info getMovie(int adapterPosition) {
        return movieInfoArrayList.get(adapterPosition);
    }

    public class movieViewHolder extends RecyclerView.ViewHolder {

        ImageButton star;
        ImageButton delete;
        TextView name, year, type;
        ImageView imageView;
        public movieViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            year = itemView.findViewById(R.id.year);
            type = itemView.findViewById(R.id.type);
            imageView = itemView.findViewById(R.id.imageView);
            star = itemView.findViewById(R.id.star);
            delete = itemView.findViewById(R.id.delete);
            star.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.Q)
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    movie_info movie = movieInfoArrayList.get(position);
                    SearchResultsActivity.model.insert(movie);
                    Toast.makeText(mcontext, "Added to watchlist", Toast.LENGTH_SHORT).show();
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    movie_info movie = movieInfoArrayList.get(position);
                    SearchResultsActivity.model.delete(movie);
                    Toast.makeText(mcontext, "Removed from watchlist", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
