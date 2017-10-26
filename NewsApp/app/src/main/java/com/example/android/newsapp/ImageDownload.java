package com.example.android.newsapp;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class ImageDownload extends AppCompatActivity {
ImageView img;
    Toolbar tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_download);
img = (ImageView) findViewById(R.id.ddit);
        tb = (Toolbar) findViewById(R.id.toolbardownload);
        setSupportActionBar(tb);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        img.setImageBitmap(floatActivity.sp.get(0));
        if (Build.VERSION.SDK_INT >= 21) {
            img.setTransitionName("cover");
            // Add a listener to get noticed when the transition ends to animate the fab button
            getWindow().getSharedElementEnterTransition().addListener(new CustomTransitionListener() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    super.onTransitionEnd(transition);
                }
            });
        } else {
            Utils.showViewByScale(img).setDuration(450).start();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_download,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.downloadit)
        {
            SaveWallpaperAsync sac = new SaveWallpaperAsync(this);
            sac.execute(getIntent().getStringExtra("url"));
        }




        return super.onOptionsItemSelected(item);
    }
}
