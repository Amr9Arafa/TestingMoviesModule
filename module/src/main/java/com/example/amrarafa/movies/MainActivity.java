package com.example.amrarafa.movies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements MovieFragment.Callback {

    boolean mTwopane;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(findViewById(R.id.movies_detail_container)!=null){


            Toast.makeText(this,"e7an fe el two pane",Toast.LENGTH_SHORT).show();
            mTwopane=true;

            if (savedInstanceState==null){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movies_detail_container, new DetailFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }

        }else {
            mTwopane=false;
        }

    }



    @Override
    public void onItemSelected(Uri contentUri) {
        if (mTwopane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle args = new Bundle();
            args.putParcelable(DetailFragment.DETAIL_URI, contentUri);

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movies_detail_container, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class)
                    .setData(contentUri);
            startActivity(intent);
        }
    }



}
