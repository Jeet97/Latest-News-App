package com.example.android.newsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Jeet on 02-01-2017.
 */

public  class newsadapter extends RecyclerView.Adapter<newsadapter.myviewholder>  {

    private ArrayList<supplyclass> arl;
    public static SparseArray<Bitmap> sopu = new SparseArray<>();
    private static AppCompatActivity cxt;
    supplyclass sp;
    ConnectivityManager cm1;
    NetworkInfo ni;


    String getactivity;
    private static final int TYPE1 = 0,TYPE2 = 1,TYPE3 = 2;




    public static class myviewholder extends RecyclerView.ViewHolder
    {
        TextView headline,customheadline,categories;
       ImageView headimagev;
                ImageView customimage;


       public  myviewholder(View vw,int type)
        {
super(vw);
            if (type==TYPE1) {

               customheadline = (TextView) vw.findViewById(R.id.customheadtextview);
                customimage = (ImageView) vw.findViewById(R.id.customheadimage);

            }

            else if (type==TYPE3)
            {

                customheadline = (TextView) vw.findViewById(R.id.customheadtextview);
                customimage = (ImageView) vw.findViewById(R.id.customheadimage);
               categories = (TextView) vw.findViewById(R.id.cat);
            }
        else
            {
                headimagev = (ImageView) vw.findViewById(R.id.headimage);
                headline = (TextView) vw.findViewById(R.id.headtextview);

            }

//vw.setOnClickListener(view);
        }




    }

    public newsadapter(ArrayList<supplyclass> arl, AppCompatActivity cxt,String getact) {
        this.arl = arl;
        this.cxt = cxt;

getactivity = getact;
    }




    @Override
    public myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        final newsadapter.myviewholder viewholder;


        LayoutInflater li = LayoutInflater.from(parent.getContext());

        switch (viewType) {


            case TYPE1:
            View v1 = li.inflate(R.layout.customlayout, parent, false);
            viewholder = new myviewholder(v1,viewType);
                break;

            case TYPE3:
                View v3 = li.inflate(R.layout.custommain, parent, false);
                viewholder = new myviewholder(v3,viewType);
                break;


            default:
                View v2 = li.inflate(R.layout.listrow,parent,false);
                viewholder = new myviewholder(v2,viewType);
        }
        return viewholder;
    }

    @Override
    public void onBindViewHolder(final myviewholder holder, final int position) {
       sp = arl.get(position);
int type = getItemViewType(position);

        switch (type)
        {
            case TYPE1:
                holder.customheadline.setText(sp.getHeadline());
                Picasso.with(cxt).load(sp.getUrl()).placeholder(R.drawable.placeholder).fit().into(holder.customimage);


                break;

            case TYPE3:
                holder.customheadline.setText(sp.getUrlart());
                Picasso.with(cxt).load(sp.getUrl()).placeholder(R.drawable.placeholder).fit().into(holder.customimage);

                holder.categories.setText(sp.getHeadline());

                break;

                default:
                    holder.headline.setText(sp.getHeadline());

                    try {
                        Picasso.with(cxt).load(sp.getUrl()).placeholder(R.drawable.placeholder).fit().into(holder.headimagev);
                    }

                    catch (IllegalArgumentException i)
                    {
                        i.printStackTrace();
                    }


        }


        if (type!=TYPE3) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cm1 = (ConnectivityManager) cxt.getSystemService(Context.CONNECTIVITY_SERVICE);
                    ni = cm1.getActiveNetworkInfo();


                    if (ni != null && ni.isConnectedOrConnecting()) {
                        Intent in = new Intent(cxt, DetailsActivity.class);
                        in.putExtra("url", arl.get(position).getUrlart());
                        cxt.startActivity(in);
                    } else {
                        Toast.makeText(cxt, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }


        if (type==TYPE3) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    fragmentontop.seturl(new geturl().getpreferences(position,cxt));
cxt.getSupportActionBar().setTitle(geturl.newsnames[position]);
                    cm1 = (ConnectivityManager) cxt.getSystemService(Context.CONNECTIVITY_SERVICE);
                    ni = cm1.getActiveNetworkInfo();


                    if (ni != null && ni.isConnectedOrConnecting()) {
                        android.app.FragmentManager fm = cxt.getFragmentManager() ;
                        android.app.FragmentTransaction ft = fm.beginTransaction();


                        android.app.Fragment frag = null;

                        try {
                            frag = fragmentontop.class.newInstance();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        ft.replace(R.id.included , frag);
                        ft.addToBackStack(null);
                        ft.commit();
                    } else {
                        Toast.makeText(cxt, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }
        if (getItemViewType(position)==TYPE2) {

            holder.headimagev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) cxt, (ImageView) view.findViewById(R.id.headimage), "cover");

                    Intent in = new Intent(cxt, floatActivity.class);
                   // in.putExtra("url", arl.get(position).getUrl());
                    Bitmap bm=((BitmapDrawable)holder.headimagev.getDrawable()).getBitmap();
                    sopu.put(0,bm);
                    in.putExtra("url",arl.get(position).getUrl());
                    cxt.startActivity(in, options.toBundle());



                }
            });

        }

    }


    @Override
    public int getItemViewType(int position) {

        if (getactivity=="others")
            return TYPE3;

        if (position==0&&getactivity.equals("main"))
        {
            return TYPE1;
        }

        else
            return TYPE2;
    }

    @Override
    public int getItemCount() {
        return arl.size();
    }


}
