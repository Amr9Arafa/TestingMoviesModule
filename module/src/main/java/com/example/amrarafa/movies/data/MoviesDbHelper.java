package com.example.amrarafa.movies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.amrarafa.movies.data.MovieContract.MostPopular;
import com.example.amrarafa.movies.data.MovieContract.HighestRated;
import com.example.amrarafa.movies.data.MovieContract.Favourite;

/**
 * Created by amr arafa on 3/12/2016.
 */
public class MoviesDbHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "movies.db";

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_MOST_POPULAR_TABLE = "CREATE TABLE " + MostPopular.TABLE_NAME + " (" +
                MostPopular._ID + " INTEGER PRIMARY KEY," +
                MostPopular.COLUMN_ID + " INTEGER UNIQUE NOT NULL, " +
                MostPopular.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MostPopular.COLUMN_POSTER_PATH + "  TEXT NOT NULL, " +
                MostPopular.COLUMN_RELEASE_DATE + "  TEXT NOT NULL, " +
                MostPopular.COLUMN_TITLE + "  TEXT NOT NULL, " +
                MostPopular.COLUMN_VOTE_AVERAGE + "  REAL NOT NULL " +
                " );";

        final String SQL_CREATE_HIGHEST_Rated_TABLE = "CREATE TABLE " + HighestRated.TABLE_NAME + " (" +
                MostPopular._ID + " INTEGER PRIMARY KEY," +
                MostPopular.COLUMN_ID + " INTEGER UNIQUE NOT NULL, " +
                MostPopular.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MostPopular.COLUMN_POSTER_PATH + "  TEXT NOT NULL, " +
                MostPopular.COLUMN_RELEASE_DATE + "  TEXT NOT NULL, " +
                MostPopular.COLUMN_TITLE + "  TEXT NOT NULL, " +
                MostPopular.COLUMN_VOTE_AVERAGE + "  REAL NOT NULL " +
                " );";


        final String SQL_CREATE_Favourite_TABLE = "CREATE TABLE " + Favourite.TABLE_NAME + " (" +
                MostPopular._ID + " INTEGER PRIMARY KEY," +
                MostPopular.COLUMN_ID + " INTEGER UNIQUE NOT NULL, " +
                MostPopular.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MostPopular.COLUMN_POSTER_PATH + "  TEXT NOT NULL, " +
                MostPopular.COLUMN_RELEASE_DATE + "  TEXT NOT NULL, " +
                MostPopular.COLUMN_TITLE + "  TEXT NOT NULL, " +
                MostPopular.COLUMN_VOTE_AVERAGE + "  REAL NOT NULL " +
                " );";

        db.execSQL(SQL_CREATE_MOST_POPULAR_TABLE);
        db.execSQL(SQL_CREATE_HIGHEST_Rated_TABLE);
        db.execSQL(SQL_CREATE_Favourite_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        db.execSQL("DROP TABLE IF EXISTS " + MostPopular.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + HighestRated.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Favourite.TABLE_NAME);
        onCreate(db);
    }
}
