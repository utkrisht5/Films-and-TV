package com.example.filmsandtv;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class movie_repository {

    private movie_dao mDao;
    private movie_database db;
    private LiveData<List<movie_info>> mList;

    public movie_repository(Application application){
        db = movie_database.getInstance(application);
        mDao = db.mDao();
        mList = mDao.getAllMovies();
    }

    LiveData<List<movie_info>> getAllMovies(){
        return mList;
    }

    public void insert(movie_info movie){
        movie_database.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDao.insert(movie);
            }
        });
    }

    public void delete(movie_info movie){
        movie_database.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDao.delete(movie);
            }
        });
    }

}
