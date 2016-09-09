package com.example.amrarafa.movies;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.example.amrarafa.movies.data.MovieContract;
import com.squareup.picasso.Picasso;

/**
 * Created by amr arafa on 3/26/2016.
 */
public class MovieCursorAdapter extends CursorAdapter {


    public MovieCursorAdapter(Context context, Cursor c, int flags)
    {
        super(context, c, flags);
    }

    private String convertCursorRowToUXFormat(Cursor cursor) {
        // get row indices for our cursor

        String poster_path=cursor.getString(cursor.getColumnIndex(MovieContract.
                MostPopular.COLUMN_POSTER_PATH));

        return poster_path;

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view= LayoutInflater.from(context).inflate(R.layout.grid_view_poster,parent,false);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {


        ImageView myImageView=(ImageView)view;
        Picasso.with(context)
                .load(convertCursorRowToUXFormat(cursor))
                .fit()
                .into(myImageView);

    }
}
