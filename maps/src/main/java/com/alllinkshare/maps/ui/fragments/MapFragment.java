package com.alllinkshare.maps.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alllinkshare.maps.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private View rootView;

    private MapView mapView;
    private GoogleMap map;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_map, container, false);

//        mapView = rootView.findViewById(R.id.mapView);
        return  rootView;
    }


    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        //LatLng class is google provided class to get latiude and longitude of location.
        //GpsTracker is helper class to get the details for current location latitude and longitude.
        LatLng location = new LatLng(37.532600, 127.024612);
        map = googleMap;
        map.addMarker(new MarkerOptions().position(location).title("Marker position"));
        map.moveCamera(CameraUpdateFactory.newLatLng(location));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
}