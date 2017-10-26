package com.example.android.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Jeet on 04-03-2017.
 */

public class FlipAdapter extends ArrayAdapter {
Context cxt;
    ArrayList<supplyclass> arl;


public FlipAdapter(Context cxt, ArrayList<supplyclass> arl)
{
    super(cxt,R.layout.flipnews,arl);

    this.arl = arl;
    this.cxt = cxt;
}


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vw = convertView;

        vw = LayoutInflater.from(cxt).inflate(R.layout.flipnews,parent,false);

        supplyclass sp = arl.get(position);

        TextView head = (TextView) vw.findViewById(R.id.fliphead);
        TextView desc = (TextView) vw.findViewById(R.id.flipdes);
        ImageView img = (ImageView) vw.findViewById(R.id.flipimage);

head.setText(sp.getHeadline());
        desc.setText(sp.getBrief());
        Picasso.with(cxt).load(sp.getUrl()).placeholder(R.drawable.placeholder).fit().into(img);

        vw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ConnectivityManager cm1 = (ConnectivityManager) cxt.getSystemService(Context.CONNECTIVITY_SERVICE);
           NetworkInfo ni = cm1.getActiveNetworkInfo();



                if (ni!=null&&ni.isConnectedOrConnecting()) {
                    Intent in = new Intent(cxt, DetailsActivity.class);
                    in.putExtra("url",arl.get(position).getUrlart() );
                    cxt.startActivity(in);
                }

                else
                {
                    Toast.makeText(cxt,"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return vw;

    }
}
