package com.example.android.newsapp;

/**
 * Created by Jeet on 02-01-2017.
 */

public class supplyclass {
    private String headline;
    private String url;
    String brief;
private String urlart;

    public String getUrlart() {
        return urlart;
    }

    public supplyclass(String headline, String url, String urlart) {
        this.headline = headline;
        this.url = url;
this.urlart = urlart;
    }

    public supplyclass(String headline, String url, String urlart,String brief) {
        this.headline = headline;
        this.url = url;
        this.urlart = urlart;
        this.brief = brief;
    }

    public String getBrief() {
        return brief;
    }

    public supplyclass(String headline, String url) {
        this.headline = headline;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getHeadline() {
        return headline;
    }
}
