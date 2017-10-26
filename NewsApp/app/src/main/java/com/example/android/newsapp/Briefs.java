package com.example.android.newsapp;


import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import se.emilsjolander.flipview.FlipView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Briefs extends Fragment implements android.app.LoaderManager.LoaderCallbacks<ArrayList<supplyclass>>{

FlipView fp;


    FlipAdapter fla;
String mainurl = "https://newsapi.org/v1/articles?source=the-times-of-india&sortBy=latest&apiKey=";
    public Briefs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    View vw = inflater.inflate(R.layout.fragment_briefs, container, false);

    fp = (FlipView) vw.findViewById(R.id.flip_view);







        getActivity().getLoaderManager().initLoader(15, null, this).forceLoad();


        // Inflate the layout for this fragment
        return vw;
    }


    @Override
    public Loader<ArrayList<supplyclass>> onCreateLoader(int i, Bundle bundle) {
        return new newsloader(getActivity(),mainurl+getActivity().getResources().getString(R.string.API_KEY),"flipit");
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<supplyclass>> loader, ArrayList<supplyclass> supplyclasses) {

        fla = new FlipAdapter(getActivity(),supplyclasses);


        fp.setAdapter(fla);


    }

    @Override
    public void onLoaderReset(Loader<ArrayList<supplyclass>> loader) {

    }



}

