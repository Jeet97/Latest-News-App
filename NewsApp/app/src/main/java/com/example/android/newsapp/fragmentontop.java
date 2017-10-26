
package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class fragmentontop extends android.app.Fragment implements LoaderManager.LoaderCallbacks<ArrayList<supplyclass>>,SwipeRefreshLayout.OnRefreshListener{
    private static String mainurl;

    private RecyclerView rv;
    private ProgressBar pb;
    ConnectivityManager cm;
    private TextView empty;
    String activity = "main";
    NetworkInfo ni;
    SwipeRefreshLayout swipit;
    private newsadapter madapter;
    private RecyclerView.LayoutManager lm;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((Toolbar)getActivity().findViewById(R.id.toolbar)).setTitle(R.string.app_name);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vw = inflater.inflate(R.layout.fragtop,container,false);

        swipit = (SwipeRefreshLayout) vw.findViewById(R.id.swiperef);
        empty = (TextView) vw.findViewById(R.id.emptyview);
        pb = (ProgressBar) vw.findViewById(R.id.pb);
        lm = new LinearLayoutManager(getActivity());
        rv = (RecyclerView) vw.findViewById(R.id.recycler);
        swipit.setOnRefreshListener(this);
        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        ni = cm.getActiveNetworkInfo();

        if (ni!=null&&ni.isConnectedOrConnecting()) {

            if (getActivity().getLoaderManager().getLoader(2)!=null) {
                getActivity().getLoaderManager().restartLoader(2, null, this).forceLoad();
            }

            else
            {
                getActivity().getLoaderManager().initLoader(2, null, this).forceLoad();
            }

        }
        else
        {
            pb.setVisibility(View.INVISIBLE);
            empty.setVisibility(View.VISIBLE);
            empty.setText("No Internet Connection");
        }


return vw;
    }




    @Override
    public Loader<ArrayList<supplyclass>> onCreateLoader(int i, Bundle bundle) {
        return new newsloader(getActivity(),mainurl);
    }

    public static void seturl(String seturl)
    {
        mainurl = seturl;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<supplyclass>> loader, ArrayList<supplyclass> supplyclasses) {
        pb.setVisibility(View.INVISIBLE);


        if (supplyclasses.isEmpty()) {
            rv.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
        else {

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
            if (empty.getVisibility()==View.VISIBLE)
                pb.setVisibility(View.VISIBLE);

            getActivity().getLoaderManager().restartLoader(2, null, this).forceLoad();

        }

        else
        {
            Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_SHORT).show();

            if (swipit.isRefreshing())
                swipit.setRefreshing(false);

        }

    }
}
