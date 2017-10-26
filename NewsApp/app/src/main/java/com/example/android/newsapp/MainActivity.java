package com.example.android.newsapp;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    Toolbar tb;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        tb = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(tb);





        fragadapter fg = new fragadapter(getSupportFragmentManager());

        ViewPager vp = (ViewPager) findViewById(R.id.viewpager);

        vp.setAdapter(fg);


        TabLayout tb = (TabLayout) findViewById(R.id.tabs);

        tb.setupWithViewPager(vp);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);


        SearchManager sc = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView shc= (SearchView) menu.findItem(R.id.searchit).getActionView();

        shc.setSearchableInfo(sc.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }






}
