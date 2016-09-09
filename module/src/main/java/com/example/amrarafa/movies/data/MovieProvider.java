package com.example.amrarafa.movies.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by amr arafa on 3/22/2016.
 */
public class MovieProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MoviesDbHelper mOpenHelper;

    static final int MOST_POPULAR = 100;
    static final int HIGHEST_RATED = 200;
    static final int FAVOURITE = 300;
    static final int MOST_PUPULAR_WITH_ID=101;
    static final int HIGHEST_RATED_WITH_ID=201;
    static final int FAVOURITE_WITH_ID=301;

    private static final String sMostPopularSelection =
            MovieContract.MostPopular.TABLE_NAME+
                    "." + MovieContract.MostPopular.COLUMN_ID + " = ? ";

    private static final String sHighestRatedSelection =
            MovieContract.HighestRated.TABLE_NAME+
                    "." + MovieContract.HighestRated.COLUMN_ID + " = ? ";
    private static final String sFavouriteSelection =
            MovieContract.Favourite.TABLE_NAME+
                    "." + MovieContract.Favourite.COLUMN_ID + " = ? ";



    private Cursor getMostPopularById(Uri uri, String[] projection) {


        Long id = MovieContract.MostPopular.getIdUri(uri);
        String[] selectionArgs;
        String selection;


            selection = sMostPopularSelection;
            selectionArgs = new String[]{Long.toString(id)};



        return  mOpenHelper.getReadableDatabase().query(
                MovieContract.MostPopular.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

    private Cursor getHighestRatedById(Uri uri, String[] projection, String sortOrder) {

        Long id = MovieContract.HighestRated.getIdUri(uri);
        String[] selectionArgs;
        String selection;

        selection = sHighestRatedSelection;
        selectionArgs = new String[]{Long.toString(id)};



        return  mOpenHelper.getReadableDatabase().query(
                MovieContract.HighestRated.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getFavouriteById(Uri uri, String[] projection, String sortOrder) {


        Long id = MovieContract.Favourite.getIdUri(uri);
        String[] selectionArgs;
        String selection;


        selection = sFavouriteSelection;
        selectionArgs = new String[]{Long.toString(id)};



        return  mOpenHelper.getReadableDatabase().query(
                MovieContract.Favourite.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }


    @Override
    public boolean onCreate() {
        mOpenHelper=new MoviesDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {

            case MOST_POPULAR:
                return MovieContract.MostPopular.CONTENT_TYPE;
            case HIGHEST_RATED:
                return MovieContract.HighestRated.CONTENT_TYPE;
            case FAVOURITE:
                return MovieContract.Favourite.CONTENT_TYPE;
            case MOST_PUPULAR_WITH_ID:
                return MovieContract.MostPopular.CONTENT_ITEM_TYPE;
            case HIGHEST_RATED_WITH_ID:
                return MovieContract.HighestRated.CONTENT_ITEM_TYPE;
            case FAVOURITE_WITH_ID:
                return MovieContract.Favourite.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {

            case MOST_POPULAR: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.MostPopular.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case FAVOURITE: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.Favourite.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case HIGHEST_RATED:{
                retCursor=mOpenHelper.getReadableDatabase().query(
                        MovieContract.HighestRated.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
              break;
            }

            case MOST_PUPULAR_WITH_ID:{
                retCursor = getMostPopularById(uri, projection);
                break;
            }

            case FAVOURITE_WITH_ID:{
                retCursor = getFavouriteById(uri, projection, sortOrder);
                break;
            }

            case HIGHEST_RATED_WITH_ID:{
                retCursor = getHighestRatedById(uri, projection, sortOrder);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case MOST_POPULAR: {
                long _id = db.insert(MovieContract.MostPopular.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MovieContract.MostPopular.buildIdUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case HIGHEST_RATED: {
                long _id = db.insert(MovieContract.HighestRated.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MovieContract.HighestRated.buildIdUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case FAVOURITE: {
                long _id = db.insert(MovieContract.Favourite.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MovieContract.MostPopular.buildIdUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }



            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case MOST_POPULAR:
                rowsDeleted = db.delete(
                        MovieContract.MostPopular.TABLE_NAME, selection, selectionArgs);
                break;
            case HIGHEST_RATED:
                rowsDeleted = db.delete(
                        MovieContract.HighestRated.TABLE_NAME, selection, selectionArgs);
                break;
            case FAVOURITE:
                rowsDeleted=db.delete(MovieContract.Favourite.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }


    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case MOST_POPULAR:
                rowsUpdated = db.update(MovieContract.MostPopular.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case HIGHEST_RATED:
                rowsUpdated = db.update(MovieContract.HighestRated.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case FAVOURITE:
                rowsUpdated=db.update(MovieContract.Favourite.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnCount = 0;

        switch (match) {
            case MOST_POPULAR: {
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insertWithOnConflict(MovieContract.MostPopular.TABLE_NAME,
                                null, value,SQLiteDatabase.CONFLICT_REPLACE);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            }

            case HIGHEST_RATED: {
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(MovieContract.HighestRated.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            }


            default:
                return super.bulkInsert(uri, values);
        }

        return returnCount;
    }






    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, MovieContract.PATH_MOST_POPULAR, MOST_POPULAR);
        matcher.addURI(authority, MovieContract.PATH_HIGHEST_RATED, HIGHEST_RATED);
        matcher.addURI(authority, MovieContract.PATH_FAVOURITE, FAVOURITE);
        matcher.addURI(authority, MovieContract.PATH_MOST_POPULAR + "/#", MOST_PUPULAR_WITH_ID);
        matcher.addURI(authority, MovieContract.PATH_HIGHEST_RATED + "/#", HIGHEST_RATED_WITH_ID);
        matcher.addURI(authority, MovieContract.PATH_FAVOURITE + "/#" , FAVOURITE_WITH_ID);
        return matcher;
    }
}
