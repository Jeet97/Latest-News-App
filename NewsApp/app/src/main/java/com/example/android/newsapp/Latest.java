package com.example.android.newsapp;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Latest extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<supplyclass>>,SwipeRefreshLayout.OnRefreshListener{
    private String mainurl = "https://newsapi.org/v1/articles?source=the-times-of-india&sortBy=latest&apiKey=";

    private RecyclerView rv;
    private ProgressBar pb;
private boolean isNewsFeedsVisible;
    private TextView empty;
    ConnectivityManager cm;
    NetworkInfo ni;
    String activity = "main";
    SwipeRefreshLayout swipit;
    private newsadapter madapter;
    private RecyclerView.LayoutManager lm;

    public Latest() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
View vw = inflater.inflate(R.layout.activity_main,container,false);



        swipit = (SwipeRefreshLayout) vw.findViewById(R.id.swiperef);
        empty = (TextView) vw.findViewById(R.id.emptyview);
        pb = (ProgressBar) vw.findViewById(R.id.pb);
        lm = new LinearLayoutManager(getActivity());
        rv = (RecyclerView) vw.findViewById(R.id.recycler);


        swipit.setOnRefreshListener(this);

         cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

         ni = cm.getActiveNetworkInfo();

        if (ni!=null&&ni.isConnectedOrConnecting()) {

            getActivity().getLoaderManager().initLoader(0, null, this).forceLoad();
        }
        else
        {
            isNewsFeedsVisible = false;
            pb.setVisibility(View.INVISIBLE);
            empty.setVisibility(View.VISIBLE);
            empty.setText(R.string.No_data);
        }
        // Inflate the layout for this fragment
        return vw;
    }


    @Override
    public Loader<ArrayList<supplyclass>> onCreateLoader(int i, Bundle bundle) {
        return new newsloader(getActivity(),mainurl+getActivity().getResources().getString(R.string.API_KEY));
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
            madapter = new newsadapter(supplyclasses,(AppCompatActivity) getActivity(),activity);
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
        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        ni = cm.getActiveNetworkInfo();

        if (ni!=null&&ni.isConnectedOrConnecting())

        {
            empty.setVisibility(View.INVISIBLE);

            if (!isNewsFeedsVisible)
                pb.setVisibility(View.VISIBLE);



            getActivity().getLoaderManager().restartLoader(0, null, this).forceLoad();

        }

        else
        {



            Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_SHORT).show();

            if (swipit.isRefreshing())
                swipit.setRefreshing(false);

        }
    }

}
