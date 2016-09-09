package com.example.amrarafa.movies;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amrarafa.movies.data.MovieContract;

public class DetailActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toast.makeText(this,"e7na fe el detail activity",Toast.LENGTH_SHORT).show();



        Intent intent=this.getIntent();

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            Bundle arguments = new Bundle();
            arguments.putParcelable(DetailFragment.DETAIL_URI, intent.getData());

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movies_detail_container, fragment)
                    .commit();
        }


//
//        if (intent!=null){
//
//            Cursor cursor=this.getContentResolver().query(intent.getData(),null,null,null,null);
//
//            cursor.moveToNext();
//            String data =cursor.getString(cursor.getColumnIndex(MovieContract.MostPopular.COLUMN_POSTER_PATH));
//            tv.setText(data);
//
//        }


     //   String data = getIntent().getDataString();
       // tv.setText(data);
    }



}
