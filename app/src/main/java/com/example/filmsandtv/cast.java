package com.example.filmsandtv;

public class cast {
    String url;
    String name;
    String id;

    public cast(String url, String name, String id){
        this.url = url;
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getId() { return id; }
}
