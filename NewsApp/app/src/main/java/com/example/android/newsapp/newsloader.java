package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
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
 * Created by Jeet on 03-01-2017.
 */

public class newsloader extends AsyncTaskLoader<ArrayList<supplyclass>> {
private Context cxt;
   private ArrayList<supplyclass> arl = new ArrayList<supplyclass>();
    private String mainurl ;
private String jsonresponse;

    String token;
    public newsloader(Context cxt, String mainurl)
    {
super(cxt);
        this.cxt = cxt;
        this.mainurl = mainurl;
    }

    public newsloader(Context cxt, String mainurl,String token)
    {
        super(cxt);
        this.cxt = cxt;
        this.mainurl = mainurl;
    this.token = token;


    }


    @Override
    public ArrayList<supplyclass> loadInBackground() {


        try {

            URL url = new URL(mainurl);

            jsonresponse = makehttpconnection(url);


            JSONObject js = new JSONObject(jsonresponse);

            JSONArray ja = js.optJSONArray("articles");

            if (!arl.isEmpty())
            {
                arl.clear();
            }

            for (int i=0;i<ja.length();i++)
            {
                JSONObject jao = ja.optJSONObject(i);

                String title = jao.optString("title");



                String urlimage = jao.optString("urlToImage");

String urlart = jao.optString("url");

String description;

                if (token!=null) {
                    description = jao.optString("description");
                    arl.add(new supplyclass(title,urlimage,urlart,description));

                }
else
arl.add(new supplyclass(title,urlimage,urlart));
            }


        }

        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (JSONException e)
        {

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
