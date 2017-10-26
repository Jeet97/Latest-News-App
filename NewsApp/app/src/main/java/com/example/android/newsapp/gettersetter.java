package com.example.android.newsapp;

import java.util.ArrayList;

/**
 * Created by Jeet on 24-01-2017.
 */

public class gettersetter {
   static ArrayList<String> arl = new ArrayList<String>();

   public gettersetter(ArrayList<String> arl)
   {
      this. arl = arl;
   }

    public gettersetter()
    {

    }


    public ArrayList<String> getArl() {
        return arl;
    }

    public void deleteit()
    {
        arl.clear();
    }
}
