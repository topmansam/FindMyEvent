package com.example.findmyevent;

public class Image {
    private String url;
    private String ratio;
    private int width;
    private int height;
    private boolean fallback;
    // Getters and Setters
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getRatio() { return ratio; }
    public void setRatio(String ratio) { this.ratio = ratio; }
    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
    public boolean isFallback() { return fallback; }
    public void setFallback(boolean fallback) { this.fallback = fallback; }
}