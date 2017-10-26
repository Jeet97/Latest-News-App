package com.example.android.newsapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import static com.example.android.newsapp.newsadapter.sopu;

public class floatActivity extends AppCompatActivity {
ImageView floatimage;
    RelativeLayout rl;
    public static SparseArray<Bitmap> sp = new SparseArray<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float);

        floatimage = (ImageView) findViewById(R.id.floatimage);

rl = (RelativeLayout) findViewById(R.id.floatback);

        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        floatimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(floatActivity.this, (ImageView) view.findViewById(R.id.floatimage), "cover");

                Intent in = new Intent(floatActivity.this,ImageDownload.class);
                // in.putExtra("url", arl.get(position).getUrl());
                Bitmap bm=((BitmapDrawable)floatimage.getDrawable()).getBitmap();
                sp.put(0,bm);
                in.putExtra("url",getIntent().getStringExtra("url"));
                floatActivity.this.startActivity(in, options.toBundle());
                finish();
            }
        });
        floatimage.setImageBitmap(sopu.get(0));


        if (Build.VERSION.SDK_INT >= 21) {
            floatimage.setTransitionName("cover");
            // Add a listener to get noticed when the transition ends to animate the fab button
            getWindow().getSharedElementEnterTransition().addListener(new CustomTransitionListener() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    super.onTransitionEnd(transition);
                }
            });
        } else {
            Utils.showViewByScale(floatimage).setDuration(450).start();
        }
    }
}
