package com.example.android.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DetailsActivity extends AppCompatActivity {
WebView wb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Loading...");
        Intent in = getIntent();


       String q = in.getStringExtra("url");
wb = (WebView) findViewById(R.id.webview);
wb.getSettings().setJavaScriptEnabled(true);
        wb.setWebViewClient(new WebViewClient()

{

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        getSupportActionBar().setTitle("News Details");

    }
}
);
        wb.loadUrl(q);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wb.destroy();
    }


}
