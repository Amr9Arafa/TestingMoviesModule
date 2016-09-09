package com.example.amrarafa.movies.data;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

/**
 * Created by amr arafa on 4/16/2016.
 */
public class TestMovieProvider extends AndroidTestCase {

    public void deleteAllRecordsFromProvider() {
        mContext.getContentResolver().delete(
                MovieContract.MostPopular.CONTENT_URI,
                null,
                null
        );
        mContext.getContentResolver().delete(
                MovieContract.HighestRated.CONTENT_URI,
                null,
                null
        );

        Cursor cursor = mContext.getContentResolver().query(
                MovieContract.Favourite.CONTENT_URI,
                null,
                null,
                null,
                null
        );
//        assertEquals("Error: Records not deleted from Weather table during delete", 0, cursor.getCount());
        cursor.close();



    }

    /*
        Student: Refactor this function to use the deleteAllRecordsFromProvider functionality once
        you have implemented delete functionality there.
     */
    public void deleteAllRecords() {
        deleteAllRecordsFromProvider();
    }

    // Since we want each test to start with a clean slate, run deleteAllRecords
    // in setUp (called by the test runner before each test).
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecords();
    }

    public void testProviderRegistry() {
        PackageManager pm = mContext.getPackageManager();

        // We define the component name based on the package name from the context and the
        // WeatherProvider class.
        ComponentName componentName = new ComponentName(mContext.getPackageName(),
                MovieProvider.class.getName());
        try {
            // Fetch the provider info using the component name from the PackageManager
            // This throws an exception if the provider isn't registered.
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            // Make sure that the registered authority matches the authority from the Contract.
            assertEquals("Error: WeatherProvider registered with authority: " + providerInfo.authority +
                            " instead of authority: " + MovieContract.CONTENT_AUTHORITY,
                    providerInfo.authority, MovieContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            // I guess the provider isn't registered correctly.
            assertTrue("Error: WeatherProvider not registered at " + mContext.getPackageName(),
                    false);
        }

    }


    public void testGetType() {
        // content://com.example.android.sunshine.app/weather/
        String type = mContext.getContentResolver().getType(MovieContract.MostPopular.CONTENT_URI);
        // vnd.android.cursor.dir/com.example.android.sunshine.app/weather
        assertEquals("Error: the WeatherEntry CONTENT_URI should return WeatherEntry.CONTENT_TYPE",
                MovieContract.MostPopular.CONTENT_TYPE, type);

        Long id = 94074L;
        // content://com.example.android.sunshine.app/weather/94074
        type = mContext.getContentResolver().getType(
                MovieContract.MostPopular.buildIdUri(id));
        // vnd.android.cursor.dir/com.example.android.sunshine.app/weather
        assertEquals("Error: the WeatherEntry CONTENT_URI with location should return WeatherEntry.CONTENT_TYPE",
                MovieContract.MostPopular.CONTENT_ITEM_TYPE, type);
//
//        long testDate = 1419120000L; // December 21st, 2014
//        // content://com.example.android.sunshine.app/weather/94074/20140612
//        type = mContext.getContentResolver().getType(
//                WeatherEntry.buildWeatherLocationWithDate(testLocation, testDate));
//        // vnd.android.cursor.item/com.example.android.sunshine.app/weather/1419120000
//        assertEquals("Error: the WeatherEntry CONTENT_URI with location and date should return WeatherEntry.CONTENT_ITEM_TYPE",
//                WeatherEntry.CONTENT_ITEM_TYPE, type);
//
//        // content://com.example.android.sunshine.app/location/
//        type = mContext.getContentResolver().getType(LocationEntry.CONTENT_URI);
//        // vnd.android.cursor.dir/com.example.android.sunshine.app/location
//        assertEquals("Error: the LocationEntry CONTENT_URI should return LocationEntry.CONTENT_TYPE",
//                LocationEntry.CONTENT_TYPE, type);
    }

    public void testBasicWeatherQuery() {
        // insert our test records into the database
        MoviesDbHelper dbHelper = new MoviesDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValues = TestUtilities.createMostPopularValues();
        Long locationRowId = db.insert(MovieContract.MostPopular.TABLE_NAME, null, testValues);

        assertTrue("Unable to Insert WeatherEntry into the Database", locationRowId != -1);

        db.close();

        // Test the basic content provider query
        Cursor cursor= mContext.getContentResolver().query(
                MovieContract.MostPopular.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        assertTrue( "Error: No Records returned from Most popular query", cursor.moveToFirst() );


        // Make sure we get the correct cursor out of the database
        // TestUtilities.validateCursor("testBasicWeatherQuery", weatherCursor, weatherValues);
    }

    public void testUpdateLocation() {
        // Create a new map of values, where column names are the keys
        ContentValues values = TestUtilities
                .createMostPopularValues();

        Uri locationUri = mContext.getContentResolver().
                insert(MovieContract.MostPopular.CONTENT_URI, values);
        long locationRowId = ContentUris.parseId(locationUri);

        // Verify we got a row back.
        assertTrue(locationRowId != -1);
        Log.d("lalalala", "New row id: " + locationRowId);

        ContentValues updatedValues = new ContentValues(values);


        // Create a cursor with observer to make sure that the content provider is notifying
        // the observers as expected
        Cursor locationCursor = mContext.getContentResolver().query(MovieContract.MostPopular.CONTENT_URI, null, null, null, null);

        TestUtilities.TestContentObserver tco = TestUtilities.getTestContentObserver();
        locationCursor.registerContentObserver(tco);

//        int count = mContext.getContentResolver().update(
//                LocationEntry.CONTENT_URI, updatedValues, LocationEntry._ID + "= ?",
//                new String[] { Long.toString(locationRowId)});
//        assertEquals(count, 1);

        // Test to make sure our observer is called.  If not, we throw an assertion.
        //
        // Students: If your code is failing here, it means that your content provider
        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
//        tco.waitForNotificationOrFail();

        locationCursor.unregisterContentObserver(tco);
        locationCursor.close();

//        // A cursor is your primary interface to the query results.
//        Cursor cursor = mContext.getContentResolver().query(
//                LocationEntry.CONTENT_URI,
//                null,   // projection
//                LocationEntry._ID + " = " + locationRowId,
//                null,   // Values for the "where" clause
//                null    // sort order
//        );

//        TestUtilities.validateCursor("testUpdateLocation.  Error validating location entry update.",
//                cursor, updatedValues);
//
//        cursor.close();
    }
}
