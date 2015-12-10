package com.romil93.weatherforecast;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by romil93 on 09/12/15.
 */

public class MapActivity extends ActionBarActivity {
    JSONObject json = null;
    String lat = null;
    String lng =  null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        try {
            json = new JSONObject((String) bd.get("json"));
            lat = json.getString("latitude");
            lng = json.getString("longitude");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("lat", lat);
        Log.d("lng", lng);



        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MapFragment fragment = new MapFragment();

        Bundle bundle = new Bundle();
        bundle.putString("lat", lat);
        bundle.putString("lng", lng);

        fragment.setArguments(bundle);

        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}