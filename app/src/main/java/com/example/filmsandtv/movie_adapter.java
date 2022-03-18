package com.example.filmsandtv;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class movie_adapter extends RecyclerView.Adapter<movie_adapter.viewHolder>{
    private List<movie_info> movies;
    private Context mContext;

    public movie_adapter(Context mContext, ArrayList<movie_info> movies){
        this.mContext = mContext;
        this.movies = movies;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_poster, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        movie_info movie = movies.get(position);
        
        Glide.with(mContext).load("https://image.tmdb.org/t/p/w1280" + movie.getImageUrl()).into(holder.poster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, details.class);
                intent.putExtra("id", movie.getId());
                intent.putExtra("type", movie.getType());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView title;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.imageView3);
            title = itemView.findViewById(R.id.title);
        }
    }

    public void setMovieList(List<movie_info> movieList)
    {
        movies.clear();
        movies.addAll(movieList);
    }
}
