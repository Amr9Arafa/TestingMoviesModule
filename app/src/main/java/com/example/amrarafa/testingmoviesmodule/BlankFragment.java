package com.example.amrarafa.testingmoviesmodule;

import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.amrarafa.movies.DataFragment;


public class BlankFragment extends DataFragment implements DataFragment.Callback {


    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fetch();
        return inflater.inflate(R.layout.fragment_movie, container, false);

    }


    @Override
    public String getParams() {
        return "Highest Rated";

    }

    void fetch(){
        super.fetchUrl();
    }

    @Override
    public void onDataFetched(Cursor cursor) {

        Log.d("Test","onDataFetched is invoked");

    }

    @Override
    public void onError() {

        //would never happen because it is invoked if the cursor is empty
//        and he cursor will never be empty because by default fetch brings most popular movies if highest rated is not
//        selected
        // will be invoked if we didn't call fetch method so there are no data in database so cursor will be empty

        Log.d("Test","onError is invoked");


    }
}