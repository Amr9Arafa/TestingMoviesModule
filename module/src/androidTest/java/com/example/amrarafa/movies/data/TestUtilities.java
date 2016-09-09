package com.example.amrarafa.movies.data;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.test.AndroidTestCase;

/**
 * Created by amr arafa on 4/4/2016.
 */
public class TestUtilities extends AndroidTestCase {

    static ContentValues createMostPopularValues() {
        // Create a new map of values, where column names are the keys
        ContentValues testValues = new ContentValues();
        testValues.put(MovieContract.HighestRated.COLUMN_ID, "12345");
        testValues.put(MovieContract.HighestRated.COLUMN_OVERVIEW, "North Pole");
        testValues.put(MovieContract.HighestRated.COLUMN_POSTER_PATH, "64.7488");
        testValues.put(MovieContract.HighestRated.COLUMN_RELEASE_DATE, "SDsawd dwadasdw");
        testValues.put(MovieContract.HighestRated.COLUMN_TITLE, "SDsawd dwadasdw");
        testValues.put(MovieContract.HighestRated.COLUMN_VOTE_AVERAGE, "SDsawd dwadasdw");
        return testValues;
    }

    static ContentValues createRealMostPopularValues() {
        // Create a new map of values, where column names are the keys
        ContentValues testValues = new ContentValues();
        testValues.put(MovieContract.MostPopular.COLUMN_ID, "209112");
        testValues.put(MovieContract.MostPopular.COLUMN_OVERVIEW, "Fearing the actions of a god-like Super Hero left unchecked," +
                " Gotham City’s own formidable," +
                " forceful vigilante takes on Metropolis’s most revered," +
                " modern-day savior, while the world wrestles with what sort of hero it really needs." +
                " And with Batman and Superman at war with one another, a new threat quickly arises," +
                " putting mankind in greater danger than it’s ever known before.");
        testValues.put(MovieContract.MostPopular.COLUMN_POSTER_PATH, "/6bCplVkhowCjTHXWv49UjRPn0eK.jpg");
        testValues.put(MovieContract.MostPopular.COLUMN_RELEASE_DATE, "2016-03-23");
        testValues.put(MovieContract.MostPopular.COLUMN_TITLE, "Batman v Superman: Dawn of Justice");
        testValues.put(MovieContract.MostPopular.COLUMN_VOTE_AVERAGE, " 5.75");
        return testValues;
    }


    static class TestContentObserver extends ContentObserver {
        final HandlerThread mHT;
        boolean mContentChanged;

        static TestContentObserver getTestContentObserver() {
            HandlerThread ht = new HandlerThread("ContentObserverThread");
            ht.start();
            return new TestContentObserver(ht);
        }

        private TestContentObserver(HandlerThread ht) {
            super(new Handler(ht.getLooper()));
            mHT = ht;

        }

        // On earlier versions of Android, this onChange method is called
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            mContentChanged = true;
        }



    }
    static TestContentObserver getTestContentObserver() {
        return TestContentObserver.getTestContentObserver();
    }

    }
