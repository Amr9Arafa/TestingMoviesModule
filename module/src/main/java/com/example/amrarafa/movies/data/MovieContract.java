package com.example.amrarafa.movies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by amr arafa on 3/12/2016.
 */
public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.example.amrarafa.movies.app";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_MOST_POPULAR = "most_popular";
    public static final String PATH_HIGHEST_RATED = "highest_rated";
    public static final String PATH_FAVOURITE="favourite";


    public static final class MostPopular implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOST_POPULAR).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOST_POPULAR;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOST_POPULAR;

        public static final String TABLE_NAME = "most_popular";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_POSTER_PATH = "poster_path";

        public static final String COLUMN_RELEASE_DATE = "release_date";

        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

        public static final String COLUMN_ID = "id";

        public static final String COLUMN_OVERVIEW = "overview";

        public static Uri buildIdUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Long getIdUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }



    }

    public static final class HighestRated implements BaseColumns {


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_HIGHEST_RATED).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HIGHEST_RATED;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HIGHEST_RATED;


        public static final String TABLE_NAME = "highest_rated";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_POSTER_PATH = "poster_path";

        public static final String COLUMN_RELEASE_DATE = "release_date";

        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

        public static final String COLUMN_ID = "id";

        public static final String COLUMN_OVERVIEW = "overview";

        public static Uri buildIdUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Long getIdUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));        }

    }

    public static final class Favourite implements BaseColumns {


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVOURITE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVOURITE;


        public static final String TABLE_NAME = "favourite";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_POSTER_PATH = "poster_path";

        public static final String COLUMN_RELEASE_DATE = "release_date";

        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

        public static final String COLUMN_ID = "id";

        public static final String COLUMN_OVERVIEW = "overview";

        public static Uri buildIdUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static Long getIdUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));        }



    }
}
