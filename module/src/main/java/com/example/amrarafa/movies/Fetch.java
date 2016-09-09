package com.example.amrarafa.movies;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by amr arafa on 9/7/2016.
 */
public class Fetch {

   private static Context mContext;

    public static void fetchUrl(final String movieListType,Context context){
        mContext =context;

        Log.d("asdasd",movieListType);

        String url="https://api.themoviedb.org/3/discover/movie?api_key=19dfd5ebe589153dc9d6788c7c9f347b&sort_by="
                +"popularity"+".desc";

        if (movieListType.equals("Highest Rated")) {


            url="https://api.themoviedb.org/3/discover/movie?api_key=19dfd5ebe589153dc9d6788c7c9f347b&sort_by="
                    +"top_rated"+".desc";
        }

        RequestQueue requestQueue;

        requestQueue= Volley.newRequestQueue(mContext);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        ParsingTask ps =new ParsingTask(mContext,movieListType);
                        ps.execute(response);

//                        (OnFinishedFetching) mContext).onFinishedFeching(mContext,movieListType,response);
                        Log.w("Testing",response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                    }
                });

        Volley.newRequestQueue(mContext).add(jsObjRequest);

    }

    public interface OnFinishedFetching {

        void onFinishedFeching(Context context,String movieListType,JSONObject response);
    }
}
