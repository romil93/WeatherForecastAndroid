package com.romil93.weatherforecast;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamweather.aeris.communication.AerisCallback;
import com.hamweather.aeris.communication.AerisEngine;
import com.hamweather.aeris.communication.EndpointType;
import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.MapViewFragment;
import com.hamweather.aeris.maps.interfaces.OnAerisMapLongClickListener;
import com.hamweather.aeris.model.AerisResponse;
import com.hamweather.aeris.tiles.AerisPointData;
import com.hamweather.aeris.tiles.AerisTile;

/**
 * Created by romil93 on 09/12/15.
 */

public class MapFragment extends MapViewFragment implements OnAerisMapLongClickListener, AerisCallback {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        AerisEngine.initWithKeys(this.getString(R.string.aeris_client_id), this.getString(R.string.aeris_client_secret), "com.romil93.weatherforecast");

        View view = inflater.inflate(R.layout.fragment_interactive_maps, container, false);
        mapView = (AerisMapView) view.findViewById(R.id.aerisfragment_map);
        mapView.init(savedInstanceState, AerisMapView.AerisMapType.GOOGLE);

        Bundle bundle = getArguments();
        String lat = bundle.getString("lat");
        String lng = bundle.getString("lng");

        Location location = new Location("");
        location.setLatitude(Double.valueOf(lat));
        location.setLongitude(Double.valueOf(lng));

        mapView.moveToLocation(location, 10.0F);
        mapView.addLayer(AerisTile.RADSAT);

        mapView.setOnAerisMapLongClickListener(this);

        mapView.setZoomControlsEnabled(true);
        mapView.setMyLocationEnabled(true);

        return view;
    }

    @Override
    public void onMapLongClick(double lat, double longitude) {
        // code to handle map long press. i.e. Fetch current conditions?
        // see demo app MapFragment.java
    }

    @Override
    public void onResult(EndpointType type, AerisResponse response) {

    }}