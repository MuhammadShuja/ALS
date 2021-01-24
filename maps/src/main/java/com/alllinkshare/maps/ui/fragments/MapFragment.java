package com.alllinkshare.maps.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alllinkshare.maps.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

public class MapFragment extends Fragment implements OnMapReadyCallback{

    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";

    private String latitude;
    private String longitude;

    private View rootView;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance(String latitude, String longitude) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(LATITUDE, latitude);
        args.putString(LONGITUDE, longitude);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            latitude = getArguments().getString(LATITUDE);
            longitude = getArguments().getString(LONGITUDE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return  rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        //LatLng class is google provided class to get latitude and longitude of location.
        //GpsTracker is helper class to get the details for current location latitude and longitude.
        LatLng location = new LatLng(37.532600, 127.024612);

        if(!TextUtils.isEmpty(latitude) && !TextUtils.isEmpty(longitude)){
            location = new LatLng(
                    Double.parseDouble(latitude),
                    Double.parseDouble(longitude)
            );
        }
//        Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        googleMap.addMarker(new MarkerOptions().position(location).title("Marker position"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12.0f));
    }
}