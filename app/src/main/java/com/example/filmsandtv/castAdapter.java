package com.example.filmsandtv;

import android.content.Context;
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

public class castAdapter extends RecyclerView.Adapter<castAdapter.viewHolder>{
    private List<cast> casts;
    private Context mContext;

    public castAdapter(Context mContext, ArrayList<cast> casts){
        this.mContext = mContext;
        this.casts = casts;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        cast member = casts.get(position);
        if(member.getUrl().length()!=0) {
            Glide.with(mContext).load("https://image.tmdb.org/t/p/w1280" + member.getUrl()).into(holder.pictures);
        }else{
            holder.pictures.setVisibility(View.GONE);
            holder.cast_name.setVisibility(View.GONE);
        }
        holder.cast_name.setText(member.getName());
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView pictures;
        TextView cast_name;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            pictures = itemView.findViewById(R.id.pictures);
            cast_name = itemView.findViewById(R.id.cast_name);
        }
    }


}
