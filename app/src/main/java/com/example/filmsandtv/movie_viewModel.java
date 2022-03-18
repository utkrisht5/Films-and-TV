package com.example.filmsandtv;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class movie_viewModel extends AndroidViewModel {

    private movie_repository mRepo;
    private LiveData<List<movie_info>> movies;

    public movie_viewModel(@NonNull Application application) {
        super(application);
        mRepo = new movie_repository(application);
        movies = mRepo.getAllMovies();
    }

    public void insert(movie_info movie){
        mRepo.insert(movie);
    }

    public LiveData<List<movie_info>> getAllMovies() {
        return movies;
    }

    public void delete(movie_info movie) {
        mRepo.delete(movie);
    }
}
