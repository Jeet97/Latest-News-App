package com.example.android.newsapp;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Jeet on 04-08-2017.
 */

public class mainloader extends android.content.AsyncTaskLoader<ArrayList<supplyclass>> {
    private ArrayList<supplyclass> arl= new ArrayList<supplyclass>();
    Context cxt;
    String jsonresponse;
    private String arr[] = new String[]{
            "Entertainment",
            "Buissness",
            "New York Times",
            "Tech",
            "Sports",
            "Gadgets",
            "Science",
            "Cricket",
            "Google News",
            "MTV"


    };


    public mainloader(Context cxt)
    {
        super(cxt);
        this.cxt = cxt;

    }

    @Override
    public ArrayList<supplyclass> loadInBackground() {
        geturl gt = new geturl();
        try {

            if (!arl.isEmpty())
            {
               return arl;
            }

            for (int i = 0; i < 10; i++) {
                String url = gt.getpreferences(i, cxt);
                URL urlact = new URL(url);

                jsonresponse = makehttpconnection(urlact);

                JSONObject js = new JSONObject(jsonresponse);

                JSONArray ja = js.optJSONArray("articles");


                for (int j=0;j<1;j++)
                {
                    JSONObject jao = ja.optJSONObject(j);

                    String title = jao.optString("title");



                    String urlimage = jao.optString("urlToImage");




                    arl.add(new supplyclass(arr[i],urlimage,title));
                }

            }
        }

        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        catch (IOException i)
        {
            i.printStackTrace();
        }

        catch (JSONException j)
        {
            j.printStackTrace();
        }

        return arl;
    }

    private String makehttpconnection(URL url) throws IOException
    {
        String realres="";
        HttpURLConnection hulc = null;
        InputStream is = null;

        if (url!=null)
        {

            hulc = (HttpURLConnection) url.openConnection();
            hulc.setRequestMethod("GET");
            hulc.setReadTimeout(10000);
            hulc.setConnectTimeout(15000);
            hulc.connect();

            is = hulc.getInputStream();

            realres = getstring(is);
        }

        if (hulc!=null)
        {
            hulc.disconnect();
        }

        if (is!=null)
        {
            is.close();
        }
        return realres;
    }


    private String getstring(InputStream is) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        if (is!=null) {
            InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8") );

            BufferedReader bf = new BufferedReader(isr);
            String line = bf.readLine();

            while (line!=null)
            {
                sb.append(line);
                line = bf.readLine();
            }
        }
        return sb.toString();
    }
}
