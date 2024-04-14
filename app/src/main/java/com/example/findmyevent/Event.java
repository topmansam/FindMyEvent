package com.example.findmyevent;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Event {
    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private String id;

    @SerializedName("url")
    private String url;

    @SerializedName("images")
    private List<Image> images;

    @SerializedName("locale")
    private String locale;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public List<Image> getImages() { return images; }
    public void setImages(List<Image> images) { this.images = images; }
    public String getLocale() { return locale; }
    public void setLocale(String locale) { this.locale = locale; }
}