package com.example.amrarafa.movies.data;

import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

/**
 * Created by amr arafa on 4/4/2016.
 */
public class TestMovieContract extends AndroidTestCase {

    // intentionally includes a slash to make sure Uri is getting quoted correctly
    private static final String TEST_WEATHER_LOCATION = "/North Pole";
    private static final long TEST_WEATHER_DATE = 1419033600L;  // December 20th, 2014

    /*
        Students: Uncomment this out to test your weather location function.
     */
    public void testBuildIdUri() {
        Uri MovieUri = MovieContract.MostPopular.buildIdUri(TEST_WEATHER_DATE);
        assertNotNull("Error: Null Uri returned.  You must fill-in buildWeatherLocation in " +
                        "WeatherContract.",
                MovieUri);
        assertEquals("Error: Weather location not properly appended to the end of the Uri",
                Long.toString(TEST_WEATHER_DATE), MovieUri.getLastPathSegment());
        Log.d("hello Testing",MovieUri.toString());
//        assertEquals("Error: Weather location Uri doesn't match our expected result",
//                MovieUri.toString(),
//                "content://com.example.android.sunshine.app/weather/%2FNorth%20Pole");
    }
}
