package com.example.amrarafa.movies;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.amrarafa.movies.data.MovieContract;

import android.support.v4.app.LoaderManager;
import android.widget.ProgressBar;

import org.json.JSONObject;


public class MovieFragment extends android.support.v4.app.Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MOVIE_LOADER = 1;

    MovieCursorAdapter mMovieAdapter;



    public MovieFragment() {
        // Required empty public constructor
    }

    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(Uri movieUri);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.moive_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.movies_type) {

            startActivity(new Intent(getActivity(), SettingsActivity.class));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        getLoaderManager().restartLoader(MOVIE_LOADER, null, this);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String movieType= prefs.getString(getActivity().getString(R.string.pref_movies_key),
                getActivity().getString(R.string.pref_movies_most_popular));

        Log.d("yalla testing hoppa",movieType);
//        fetchUrl(movieType);
        Fetch.fetchUrl(movieType,getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);






//        Uri uri = MovieContract.MostPopular.CONTENT_URI;
//
//        Cursor cursor= getActivity().getContentResolver().query(uri,null,null,null,null);

        mMovieAdapter = new MovieCursorAdapter(getActivity(),null,0);
        GridView gridView=(GridView) rootView.findViewById(R.id.gridView);
        gridView.setAdapter(mMovieAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int position, long id) {

                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);

                if (cursor != null) {

                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    String movieType= prefs.getString(getActivity().getString(R.string.pref_movies_key),
                            getActivity().getString(R.string.pref_movies_most_popular));


                    Uri uri = MovieContract.MostPopular.buildIdUri(cursor.getLong(cursor.getColumnIndex(
                            MovieContract.MostPopular.COLUMN_ID
                    )));

                    if (movieType.equals("Highest Rated")){

                         uri = MovieContract.HighestRated.buildIdUri(cursor.getLong(cursor.getColumnIndex(
                                MovieContract.HighestRated.COLUMN_ID
                        )));
                    }

                    else if(movieType.equals("Favourite")){

                        uri = MovieContract.Favourite.buildIdUri(cursor.getLong(cursor.getColumnIndex(
                                MovieContract.HighestRated.COLUMN_ID
                        )));
                    }

                    ((Callback) getActivity()).onItemSelected(uri);

                }

            }
        });



        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String movieType= prefs.getString(getActivity().getString(R.string.pref_movies_key),
                getActivity().getString(R.string.pref_movies_default));

        Uri uri = MovieContract.MostPopular.CONTENT_URI;
        if (movieType.equals("Highest Rated")) {
            uri = MovieContract.HighestRated.CONTENT_URI;

        }
        else if(movieType.equals("Favourite")){
            uri=MovieContract.Favourite.CONTENT_URI;
        }
        return new CursorLoader(getActivity(),
                uri,
                null,
                null,
                null,
                null);
    }



    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

        mMovieAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mMovieAdapter.swapCursor(null);
    }


    void fetchUrl(final String movieListType){

        Log.d("asdasd",movieListType);

        String url="https://api.themoviedb.org/3/discover/movie?api_key=19dfd5ebe589153dc9d6788c7c9f347b&sort_by="
                +"popularity"+".desc";

        if (movieListType.equals("Highest Rated")) {

            Log.d("yalla testing","7asal Hooo");

            url="https://api.themoviedb.org/3/discover/movie?api_key=19dfd5ebe589153dc9d6788c7c9f347b&sort_by="
                    +"top_rated"+".desc";
        }

        RequestQueue requestQueue;

        requestQueue= Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        ParsingTask ps =new ParsingTask(getActivity(),movieListType);
                        ps.execute(response);


                        Log.w("Testing",response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.v("myApp","yallaa"+error.toString());
                    }
                });

        Volley.newRequestQueue(getActivity()).add(jsObjRequest);

    }





}
