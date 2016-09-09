package com.example.amrarafa.movies;

import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.os.Bundle;

import com.example.amrarafa.movies.data.MovieContract;

public abstract class DataFragment extends android.support.v4.app.Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MOVIE_LOADER = 1;
    Cursor mCursor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }



    protected void fetchUrl(){

        String type=getParams();
        Fetch.fetchUrl(type,getActivity());
        getLoaderManager().restartLoader(MOVIE_LOADER, null,this);



    }

    public abstract String getParams();



    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {



        Uri uri = MovieContract.MostPopular.CONTENT_URI;
        if (getParams().equals("Highest Rated")) {
            uri = MovieContract.HighestRated.CONTENT_URI;

        }
        else if(getParams().equals("Favourite")){
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

        mCursor=cursor;
        if (!(mCursor.moveToFirst()) || mCursor.getCount() ==0){
            //cursor is empty
            ((Callback) this).onError();

        }
        else {


            ((Callback) this).onDataFetched(mCursor);
        }
    }

    @Override
    public void onLoaderReset(
            Loader<Cursor> cursorLoader) {
        mCursor=null;
        ((Callback) this).onDataFetched(mCursor);

    }


    public interface Callback {

        void onDataFetched(Cursor cursor);
        void onError();
    }

}