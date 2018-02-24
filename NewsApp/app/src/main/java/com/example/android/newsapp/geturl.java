package com.example.android.newsapp;

import android.content.Context;
import android.net.Uri;

/**
 * Created by Jeet on 05-01-2017.
 */

public class geturl  {

    static String arr[] =
            {
                    "entertainment-weekly",
                    "cnbc",
                    "the-new-york-times",
                    "techradar",
                    "bbc-sport",
                    "recode",
                    "new-scientist",
                    "espn-cric-info",
                    "google-news",
                    "mtv-news-uk",
                    "the-times-of-india"
            };


    static String newsnames[] =
            {
                    "Entertainment News",
                    "Buissness News",
                    "New York Times",
                    "Tech News",
                    "Sports News",
                    "Tech Geeks",
                    "Science",
                    "Cricket News",
                    "Google News",
                    "MTV",
                    "Times Of India"
            };


    String mainurl = "https://newsapi.org/v1/articles?";
    String searchurl = "https://newsapi.org/v2/everything?";

    public String getpreferences(int source,Context cxt)
    {


        Uri base = Uri.parse(mainurl);


        Uri.Builder bl = base.buildUpon();

        bl.appendQueryParameter("source", arr[source]);
        bl.appendQueryParameter("sortBy", "top");
        bl.appendQueryParameter("apiKey", cxt.getResources().getString(R.string.API_KEY));


        return bl.toString();
    }

    public String getsearchpreferences(String query,Context cxt)
    {


        Uri base = Uri.parse(searchurl);


        Uri.Builder bl = base.buildUpon();

        bl.appendQueryParameter("q", query);
        bl.appendQueryParameter("sortBy", "relevence");
        bl.appendQueryParameter("apiKey", cxt.getResources().getString(R.string.API_KEY));


        return bl.toString();
    }

}
