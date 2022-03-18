package com.example.filmsandtv;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.filmsandtv.movie_info;

import java.util.List;

@Dao
public interface movie_dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(movie_info movie);

    @Query("SELECT * FROM movies")
    LiveData<List<movie_info>> getAllMovies();

    @Delete
    void delete(movie_info movie);
}
