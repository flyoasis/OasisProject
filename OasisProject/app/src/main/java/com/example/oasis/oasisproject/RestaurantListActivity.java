package com.example.oasis.oasisproject;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class RestaurantListActivity extends AppCompatActivity implements RestaurantGridFragment.OnItemSelectListener,RestaurantListFragment.OnItemSelectListener
{
    RestaurantListFragment listFragment;
    RestaurantGridFragment gridFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);
        Log.e("Life cycle test", "We are at onCreate()");

        //setContentView(R.layout.activity_restaurant_list);
        // Get ListView object from xml.
       // ListView restaurantListView = (ListView) findViewById(R.id.restaurant_list);
        // Initialize an adapter.
       // RestaurantAdapter adapter = new RestaurantAdapter(this);

        // Assign adapter to ListView.
       // restaurantListView.setAdapter(adapter);

        // Show different fragments based on screen size.
        //  if (findViewById(R.id.fragment_container) != null) {
        //   Fragment fragment = isTablet() ?
        //           new RestaurantGridFragment() : new RestaurantListFragment();
        //     getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
        //  }
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                YelpApi yelp = new YelpApi();
                yelp.searchForBusinessesByLocation("dinner", "San Francisco, CA", 20);
                return null;
            }
        }.execute();


        if (findViewById(R.id.fragment_container) != null) {
            Intent intent = getIntent();
            if (intent.getExtras() != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_list_container, new BackendListFragment()).commit();
            } else {
                listFragment = new RestaurantListFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_list_container, listFragment).commit();
            }
        }

        //add list view
       // if (isTablet()) {
           // listFragment = new RestaurantListFragment();
            //getSupportFragmentManager().beginTransaction().add(R.id.fragment_list_container, listFragment).commit();
        ///}

        //add Gridview
       // gridFragment = new RestaurantGridFragment();
        //getSupportFragmentManager().beginTransaction().add(R.id.fragment_grid_container, gridFragment).commit();
    }

    private boolean isTablet() {
        return (getApplicationContext().getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    @Override
    public void onItemSelected(int position) {
        gridFragment.onItemSelectedGrid(position);
    }
    public void onItemSelectedGrid(int position) {
        listFragment.onItemSelected(position);
    }
}
