package com.example.amrarafa.movies.data;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

/**
 * Created by amr arafa on 4/16/2016.
 */
public class TestUriMatcher extends AndroidTestCase {

    private static final String LOCATION_QUERY = "London, UK";
    private static final long TEST_DATE = 1419033600L;  // December 20th, 2014
    private static final long TEST_LOCATION_ID = 10L;

    // content://com.example.android.sunshine.app/weather"
    private static final Uri TEST_WEATHER_DIR = MovieContract.MostPopular.CONTENT_URI;
    private static final Uri TEST_WEATHER_WITH_LOCATION_DIR = MovieContract.MostPopular.buildIdUri(TEST_DATE);
    // content://com.example.android.sunshine.app/location"

    /*
        Students: This function tests that your UriMatcher returns the correct integer value
        for each of the Uri types that our ContentProvider can handle.  Uncomment this when you are
        ready to test your UriMatcher.
     */
    public void testUriMatcher() {
        UriMatcher testMatcher = MovieProvider.buildUriMatcher();

        assertEquals("Error: The WEATHER URI was matched incorrectly.",
                testMatcher.match(TEST_WEATHER_DIR), MovieProvider.MOST_POPULAR);
        assertEquals("Error: The WEATHER WITH LOCATION URI was matched incorrectly.",
                testMatcher.match(TEST_WEATHER_WITH_LOCATION_DIR), MovieProvider.MOST_PUPULAR_WITH_ID);

    }
}
