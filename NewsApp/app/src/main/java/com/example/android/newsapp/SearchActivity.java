package com.example.android.newsapp;

import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,LoaderManager.LoaderCallbacks<ArrayList<supplyclass>>{

    private RecyclerView rv;
    private ProgressBar pb;
    private TextView empty;
    private boolean isNewsFeedsVisible;
    ConnectivityManager cm;
    String activity = "search";
    SwipeRefreshLayout swipit;
    NetworkInfo ni;
    private newsadapter madapter;
    private RecyclerView.LayoutManager lm;
    private String query = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        Intent in = getIntent();
        query = in.getStringExtra(SearchManager.QUERY);




        swipit = (SwipeRefreshLayout) findViewById(R.id.swiperef);
        empty = (TextView) findViewById(R.id.emptyview);
        pb = (ProgressBar) findViewById(R.id.pb);
        lm = new LinearLayoutManager(this);
        rv = (RecyclerView) findViewById(R.id.recycler);


        swipit.setOnRefreshListener(this);

        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        ni = cm.getActiveNetworkInfo();

        if (ni!=null&&ni.isConnectedOrConnecting()) {

            getLoaderManager().initLoader(1, null, this).forceLoad();
        }
        else
        {
            isNewsFeedsVisible = false;
            pb.setVisibility(View.INVISIBLE);
            empty.setVisibility(View.VISIBLE);
            empty.setText(R.string.No_data);
        }
        // Inflate the layout for this fragment
    }
    @Override
    public Loader<ArrayList<supplyclass>> onCreateLoader(int i, Bundle bundle) {
        geturl gt = new geturl();

       Log.v("this is url",gt.getsearchpreferences(query,this)) ;
        return new newsloader(this,gt.getsearchpreferences(query,this),activity);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<supplyclass>> loader, ArrayList<supplyclass> supplyclasses) {


        pb.setVisibility(View.INVISIBLE);


        if (supplyclasses.isEmpty()) {
            isNewsFeedsVisible = false;
            rv.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
        else {
            isNewsFeedsVisible = true;
            madapter = new newsadapter(supplyclasses,(AppCompatActivity) this,activity);
            rv.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
            rv.setAdapter(madapter);
            rv.setHasFixedSize(true);
            rv.setLayoutManager(lm);


        }

        if (swipit.isRefreshing())
            swipit.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<supplyclass>> loader) {

    }
    @Override
    public void onRefresh() {

        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        ni = cm.getActiveNetworkInfo();
        if (ni!=null&&ni.isConnectedOrConnecting())

        {
            empty.setVisibility(View.INVISIBLE);


            if (!isNewsFeedsVisible)
                pb.setVisibility(View.VISIBLE);

            getLoaderManager().restartLoader(1, null, this).forceLoad();

        }

        else
        {

            Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show();

            if (swipit.isRefreshing())
                swipit.setRefreshing(false);


        }
    }
}
