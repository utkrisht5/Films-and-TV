package com.example.filmsandtv;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class movie_info {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "year")
    private String year;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "imageUrl")
    private String imageUrl;
    @ColumnInfo(name = "movieId")
    private String id;

    public movie_info(String name, String year, String type, String imageUrl, String id){
        this.name = name;
        this.year = year;
        this.type = type;
        this.imageUrl = imageUrl;
        this.id = id;
    }

    @Ignore
    public movie_info(String imageUrl, String id, String type){
        this.imageUrl = imageUrl;
        this.id = id;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }

    public String getType() {
        return type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setYear(String year){
        this.year = year;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }
}
