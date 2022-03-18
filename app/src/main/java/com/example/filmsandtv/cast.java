package com.example.filmsandtv;

public class cast {
    String url;
    String name;

    public cast(String url, String name){
        this.url = url;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
