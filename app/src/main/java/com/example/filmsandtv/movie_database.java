package com.example.filmsandtv;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {movie_info.class}, version = 1, exportSchema = false)
public abstract class movie_database extends RoomDatabase {

    public abstract movie_dao mDao();
    public static movie_database INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static movie_database getInstance(Context context) {
        if(INSTANCE==null){
            synchronized (movie_database.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), movie_database.class, "moviedatabase").build();
                }
            }
        }
        return INSTANCE;
    }
}
