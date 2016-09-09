package com.example.amrarafa.movies.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

import java.util.HashSet;

/**
 * Created by amr arafa on 3/14/2016.
 */
public class TestDb extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();

    void deleteTheDatabase() {
        mContext.deleteDatabase(MoviesDbHelper.DATABASE_NAME);
    }


    public void setUp() {
        deleteTheDatabase();
    }


    public void testCreateDb() throws Throwable {
        // build a HashSet of all of the table names we wish to look for
        // Note that there will be another table in the DB that stores the
        // Android metadata (db version information)
        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(MovieContract.MostPopular.TABLE_NAME);
        tableNameHashSet.add(MovieContract.HighestRated.TABLE_NAME);
        tableNameHashSet.add(MovieContract.Favourite.TABLE_NAME);
        mContext.deleteDatabase(MoviesDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new MoviesDbHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        // have we created the tables we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());

        // verify that the tables have been created
        do {
            tableNameHashSet.remove(c.getString(0));
        } while( c.moveToNext() );

        // if this fails, it means that your database doesn't contain both the location entry
        // and weather entry tables
        assertTrue("Error: Your database was created without both the location entry and weather entry tables",
                tableNameHashSet.isEmpty());

        // now, do our tables contain the correct columns?
//        c = db.rawQuery("PRAGMA table_info(" + MovieContract.MostPopular.TABLE_NAME + ")",
//                null);

        c = db.rawQuery("PRAGMA table_info(" + MovieContract.HighestRated.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> locationColumnHashSet = new HashSet<String>();
        locationColumnHashSet.add(MovieContract.Favourite._ID);
        locationColumnHashSet.add(MovieContract.Favourite.COLUMN_OVERVIEW);
        locationColumnHashSet.add(MovieContract.Favourite.COLUMN_POSTER_PATH);
        locationColumnHashSet.add(MovieContract.Favourite.COLUMN_RELEASE_DATE);
        locationColumnHashSet.add(MovieContract.Favourite.COLUMN_TITLE);
        locationColumnHashSet.add(MovieContract.Favourite.COLUMN_VOTE_AVERAGE);
        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            locationColumnHashSet.remove(columnName);
        } while(c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required location entry columns",
              locationColumnHashSet.isEmpty());
        db.close();
    }

    public void testMostPopularTable(){


        MoviesDbHelper dbHelper = new MoviesDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Second Step: Create ContentValues of what you want to insert
        // (you can use the createNorthPoleLocationValues if you wish)
        ContentValues testValues = TestUtilities.createMostPopularValues();

        // Third Step: Insert ContentValues into database and get a row ID back
        long locationRowId;
        locationRowId = db.insert(MovieContract.Favourite.TABLE_NAME, null, testValues);

        // Verify we got a row back.
        assertTrue(locationRowId != -1);

        Cursor cursor = db.query(
                MovieContract.Favourite.TABLE_NAME,  // Table to Query
                null, // all columns
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        // Move the cursor to a valid database row and check to see if we got any records back
        // from the query
       assertTrue("Error: No Records returned from Most popular query", cursor.moveToFirst());

        cursor.close();









        ContentValues realTestValues = TestUtilities.createRealMostPopularValues();

        // Third Step: Insert ContentValues into database and get a row ID back
        long locationRowId1;
        locationRowId1 = db.insert(MovieContract.MostPopular.TABLE_NAME, null, realTestValues);

        assertTrue(locationRowId1 != -1);

        Uri uri= MovieContract.MostPopular.buildIdUri(209112L);

        Cursor providerCursor= mContext.getContentResolver().query(uri, null, null, null, null);

        assertTrue("Error: No Records returned from Most popular query", (providerCursor.moveToFirst())
                && (providerCursor.getCount() >= 1)
                && (providerCursor!= null));

        String id=providerCursor.getString(providerCursor.getColumnIndex(MovieContract.MostPopular
                .COLUMN_POSTER_PATH));

        Log.d("hello yalla testing", id);

//        String[] selectionArgs;
//
//         String sMostPopularSelection =
//                MovieContract.MostPopular.TABLE_NAME +
//                        "." + MovieContract.MostPopular.COLUMN_ID + " = ? ";
//
//        selectionArgs = new String[]{Long.toString(209112L)};
//
//        Cursor realCursor = db.query(
//                MovieContract.MostPopular.TABLE_NAME,
//                null,
//                sMostPopularSelection,
//                selectionArgs,
//                null,
//                null,
//                null);
//
//
//        realCursor.moveToNext();
//       int id1=realCursor.getInt (realCursor.getColumnIndex(MovieContract.MostPopular
//                .COLUMN_ID));
//
//        Log.d("hello yalla testing",String.valueOf(id1));
//
//
//        assertTrue("Error: No Records returned from Most popular query", (realCursor.moveToFirst())
//                && (realCursor.getCount() >= 1)
//                && (realCursor != null));
//        realCursor.close();
        providerCursor.close();
        db.close();
    }


//    public void testHighestRatedTable(){
//
//
//        MoviesDbHelper dbHelper = new MoviesDbHelper(mContext);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // Second Step: Create ContentValues of what you want to insert
//        // (you can use the createNorthPoleLocationValues if you wish)
//        ContentValues testValues = TestUtilities.createRealMostPopularValues();
//
//        // Third Step: Insert ContentValues into database and get a row ID back
//        long locationRowId;
//        locationRowId = db.insert(MovieContract.HighestRated.TABLE_NAME, null, testValues);
//
//        // Verify we got a row back.
//        assertTrue(locationRowId != -1);
//
//        Cursor cursor = db.query(
//                MovieContract.HighestRated.TABLE_NAME,  // Table to Query
//                null, // all columns
//                null, // Columns for the "where" clause
//                null, // Values for the "where" clause
//                null, // columns to group by
//                null, // columns to filter by row groups
//                null // sort order
//        );
//
//        // Move the cursor to a valid database row and check to see if we got any records back
//        // from the query
//        assertTrue("Error: No Records returned from Most popular query", cursor.moveToFirst());
//
//        cursor.close();
//
//
//
//
//
//
//
//
//
//        ContentValues realTestValues = TestUtilities.createRealMostPopularValues();
//
//        // Third Step: Insert ContentValues into database and get a row ID back
//        long locationRowId1;
//        locationRowId1 = db.insert(MovieContract.MostPopular.TABLE_NAME, null, realTestValues);
//
//        assertTrue(locationRowId1 != -1);
//
//        Uri uri= MovieContract.MostPopular.buildIdUri(209112L);
//
//        Cursor providerCursor= mContext.getContentResolver().query(uri, null, null, null, null);
//
//        assertTrue("Error: No Records returned from Most popular query", (providerCursor.moveToFirst())
//                && (providerCursor.getCount() >= 1)
//                && (providerCursor!= null));
//
//        String id=providerCursor.getString(providerCursor.getColumnIndex(MovieContract.MostPopular
//                .COLUMN_POSTER_PATH));
//
//        Log.d("testing aho mn elTestDb", id);
//
////        String[] selectionArgs;
////
////         String sMostPopularSelection =
////                MovieContract.MostPopular.TABLE_NAME +
////                        "." + MovieContract.MostPopular.COLUMN_ID + " = ? ";
////
////        selectionArgs = new String[]{Long.toString(209112L)};
////
////        Cursor realCursor = db.query(
////                MovieContract.MostPopular.TABLE_NAME,
////                null,
////                sMostPopularSelection,
////                selectionArgs,
////                null,
////                null,
////                null);
////
////
////        realCursor.moveToNext();
////       int id1=realCursor.getInt (realCursor.getColumnIndex(MovieContract.MostPopular
////                .COLUMN_ID));
////
////        Log.d("hello yalla testing",String.valueOf(id1));
////
////
////        assertTrue("Error: No Records returned from Most popular query", (realCursor.moveToFirst())
////                && (realCursor.getCount() >= 1)
////                && (realCursor != null));
////        realCursor.close();
//        providerCursor.close();
//        db.close();
//    }


}


