package com.example.android.newsapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Jeet on 04-01-2017.
 */

public class fragadapter extends FragmentPagerAdapter {
    public CharSequence names[] = {"India","World","MTV","Others"};





    public fragadapter (FragmentManager fm)
    {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        if (position==0)
            return new Latest();

        else if(position==1)
            return  new Top();

        else if (position==2)
            return  new Briefs();

        else
            return new Others();
    }



    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return names[position];
    }
}
