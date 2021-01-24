package com.alllinkshare.liveTrack.tracking;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.alllinkshare.core.utils.SPM;

import com.alllinkshare.liveTrack.R;
import com.alllinkshare.liveTrack.tracking.Api.Api;
import com.alllinkshare.liveTrack.tracking.Api.AppData;
import com.alllinkshare.liveTrack.tracking.Api.RetrofitInstance;
import com.alllinkshare.liveTrack.tracking.Constants.mConstants;
import com.alllinkshare.liveTrack.tracking.Model.CurrentDateTime;
import com.alllinkshare.liveTrack.tracking.Model.CurrentLocation;
import com.alllinkshare.liveTrack.tracking.Model.Order;
import com.alllinkshare.liveTrack.tracking.Model.Rides;
import com.alllinkshare.liveTrack.tracking.service.TrackerService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.Duration;
import com.google.maps.model.EncodedPolyline;
import com.google.maps.model.TravelMode;
import com.nanotasks.BackgroundWork;
import com.nanotasks.Completion;
import com.nanotasks.Tasks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LOCATION_SERVICE;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;


public class TrackOrderFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final int PERMISSIONS_REQUEST = 1;
    private static final String TAG = "HomeFragment";
    public static String orderStatus = "";
    public static String orderStatus1 = "";
    Button btnConfirmPickUp, btnCancelRide;
    CardView cardFirst, cardSecond;
    String orderId = "";
    String riderId = "";
    MapView mapView;
    TextView tvBudgetFirst, tvBudgetFirstDesc, tvBudgetSec, tvBudgetSecDesc;
    SupportMapFragment supportMapFragment;
    Polyline finalPolyLine;
    /////////////// Danish code
    View sheetView;
    ImageButton btnClose;
    TextView txEstDistance, txEstDuration;
    BottomSheetBehavior bottomSheetBehavior;
    double distancekm, time;
    int round, roundtime = 0;
    // changes
    Bundle bundle;
    String orderid;
    Double liveLong = 0.0;
    Double liveLat = 0.0;
    Location location;
    private OnFragmentInteractionListener mListener;
    private GoogleMap mMap;
    private double longitude;
    private double latitude;
    private GoogleApiClient googleApiClient;
    private double pickUpLat, desOffLat, riderLat;
    private double pickUpLong, desOffLong, riderLong;
    private boolean cameraAnimated = false; // isAlready Animated dont need to animate again unless demand. false is not animated and true for already animated before
    private Marker currentUserMarker, pickUpMarker, dropOffMarker, RiderMarker;
    private RideStatus currentRideStatus = RideStatus.IDEAL;
    private LinearLayout linLayBtn;
    private String rideEstDistance = "null", rideEstDuration = "null";

    public TrackOrderFragment() {
    }

    public static TrackOrderFragment newInstance() {
        TrackOrderFragment fragment = new TrackOrderFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home_track_order, container, false);
//       mapView=rootView.findViewById(R.id.map_home);


        bundle = getArguments();
        orderid = bundle.getString("value");
        orderId = bundle.getString("value");
        Log.d("orderIdshow", orderId);
        //    Toast.makeText(getContext(), "Fragment" + orderid, Toast.LENGTH_SHORT).show();

/*
        Location startPoint = new Location("locationA");
        startPoint.setLatitude(17.372102);
        startPoint.setLongitude(78.484196);

        Location endPoint = new Location("locationA");
        endPoint.setLatitude(17.375775);
        endPoint.setLongitude(78.469218);

        double distance = startPoint.distanceTo(endPoint);

        distancekm = distance / 1000;
        round = (int) Math.round(distancekm);

        time = distancekm * 10;
        roundtime = (int) Math.round(time);
        // Toast.makeText(getContext(), "Distance" + distance + "m", Toast.LENGTH_SHORT).show();
        // Toast.makeText(getContext(), "Distance" + distancekm + "km", Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), "Distance" + round + "Km", Toast.LENGTH_SHORT).show();

        Toast.makeText(getContext(), "Time" + roundtime + "Min", Toast.LENGTH_SHORT).show();
*/


        cardFirst = rootView.findViewById(R.id.cardFirst);
        cardSecond = rootView.findViewById(R.id.cardSecond);
        tvBudgetFirst = rootView.findViewById(R.id.tvBudgetFirst);
        tvBudgetFirstDesc = rootView.findViewById(R.id.tvBudgetFirstDesc);
        tvBudgetSec = rootView.findViewById(R.id.tvBudgetSec);
        tvBudgetSecDesc = rootView.findViewById(R.id.tvBudgetSecDesc);
        linLayBtn = rootView.findViewById(R.id.lin_lay_btn);
        sheetView = rootView.findViewById(R.id.container2);

        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().
                findFragmentById(R.id.map_home);
//        mapView.onCreate(savedInstanceState);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMaps) {
                mMap = mMaps;
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                try {
                    // Customise the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    boolean success = mMap.setMapStyle(
                            MapStyleOptions.loadRawResourceStyle(
                                    getContext(), R.raw.style_json));

                    if (!success) {
                        Log.e("TAG", "Style parsing failed.");
                    }
                } catch (Resources.NotFoundException e) {
                    Log.e("TAG", "Can't find style. Error: ", e);
                }
//                mMap.clear(); //clear old markers
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);
                mMap.isTrafficEnabled();
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                mMap.getUiSettings().setMapToolbarEnabled(true);
                if (mMap != null) {
                    mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                        @Override
                        public void onMyLocationChange(Location arg0) {
                            animatedUserCam(arg0);
                            longitude = arg0.getLongitude();
                            latitude = arg0.getLatitude();
                            if (currentUserMarker != null) {
                                currentUserMarker.remove();
                            }
                            if (orderId != null) {
                                if (orderStatus1.equals("Active")) {
                                    Log.d("dataSHow", orderStatus1);
                                    Toast.makeText(getContext(), orderStatus1, Toast.LENGTH_SHORT).show();
                                    LiveTrackOrder();
                                } else {
                                    Toast.makeText(getContext(), "Ride  have completed", Toast.LENGTH_SHORT).show();
                                }
                            }
                            setLocationUpdatesSheet(arg0);
//                            currentUserMarker = mMaps.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude())).title("My Location").icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_scooter))));
//                            if (!riderId.isEmpty()) {
//                                sendCurrentLocation(riderId, arg0.getLongitude() + "", arg0.getLatitude() + "");
//                            }
                        }
                    });
                }
            }
        });

        getActivity().setTitle("Home");
        Intent intent = getActivity().getIntent();

        btnConfirmPickUp = rootView.findViewById(R.id.btnConfirm_pickUp);
        btnCancelRide = rootView.findViewById(R.id.btnCancelRide);


        showOrderId();

        /*
        if (getArguments() != null) {
            orderId = getArguments().getString("orderId");

        }
        if (orderId == null) {
            btnConfirmPickUp.setVisibility(View.GONE);

        } else {
            showDialog();
            btnConfirmPickUp.setVisibility(View.VISIBLE);
        }
        try {
            btnConfirmPickUp.setText("Track Order");
            btnConfirmPickUp.setOnClickListener(v -> {
                switch (currentRideStatus) {
                    case IDEAL:
                        showOrderId();
                        getActivity().setTitle("Order Taking Start");
                        break;
                }

            });

            btnCancelRide.setOnClickListener(v -> {
                showRideCancelledDialog();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        */


        return rootView;
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            return;
//        }
//        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
////        sendCurrentLocation();
//        getCurrentLocation();
//        mMap.setMyLocationEnabled(true);
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.684, 133.903), 4));
//
//
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        LocationManager lm = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(getContext(), "Please enable location services", Toast.LENGTH_SHORT).show();
//            finish();
        }

        // Check location permission is granted - if it is, start
        // the service, otherwise request the permission
        int permission = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
//            getCurrentLocation();

        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST);
        }
//        sendCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

//    private void showRideDetails(String riderId, String message) {
//
//        Api service = RetrofitInstance.getRetrofitInstance().create(Api.class);
//        String bear = "Bearer";
//        String authorized = bear + " " + AppData.ACCESS_TOKEN;
//        String val = "application/json";
//
//        Call<Object> call = service.showRideDetails(riderId, val, authorized);
//        call.enqueue(new Callback<Object>() {
//
//            @Override
//            public void onResponse(Call<Object> call, Response<Object> response) {
//
//                Log.d("ddd", response.toString());
//                try {
//                    if (response.isSuccessful()) {
//
//                        String json1 = new Gson().toJson(response.body());
//                        JSONObject json = new JSONObject(json1);
//                        Log.e("RideDetails", json.toString());
//                        //                            Geocoder geocoder = null;
////                            List<Address> addresses;
////                            List<Address> addresses1;
////                            String destination_latitude = "";
////                            String destination_longitude = "";
////                            String pickup_latitude = json.getString("pickup_latitude");
////                            String pickup_longitude = json.getString("pickup_longitude");
//
//                        /****
//                         * for me
//                         * First Pickup Location
//                         * Second DropOff location
//                         */
//                        // setting pickupLocation
//
////                        if (!json.isNull("destination_latitude")) {
////                                destination_latitude = json.getString("destination_latitude");
////                                destination_longitude = json.getString("destination_longitude");
//                        if (json.isNull("destination_latitude") || json.isNull("destination_longitude")) {
//                            getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Invalid Ride Details!", Toast.LENGTH_SHORT).show());
//                            Paper.book(mConstants.rideTimeRecordKeyBook).write(riderId, 0);
//                            btnConfirmPickUp.setVisibility(View.GONE);
//                        } else {
//                            cardSecond.setVisibility(View.VISIBLE);
//                            cardFirst.setVisibility(View.VISIBLE);
//                            pickUpLat = json.getDouble("pickup_latitude");
//                            pickUpLong = json.getDouble("pickup_longitude");
//                            Log.e("pickup_Details ", pickUpLat + " _ " + pickUpLong);
//
//                            try {
//                                tvBudgetFirstDesc.setText(getAddress(pickUpLat, pickUpLong));
//                            } catch (Exception e) {
//                                tvBudgetFirstDesc.setText("Location not available");
//                                e.printStackTrace();
//                            }
//
//                            desOffLat = json.getDouble("destination_latitude");
//                            desOffLong = json.getDouble("destination_longitude");
//                            Log.e("destination ", desOffLat + " _ " + desOffLong);
//                            try {
//                                tvBudgetSecDesc.setText(getAddress(desOffLat, desOffLong));
//                            } catch (Exception e) {
//                                tvBudgetSecDesc.setText("Location not available");
//                                e.printStackTrace();
//                            }
//                            Log.e("RideStatus_STARTED", "Shift_to_Start");
//                            changeRideStatus(RideStatus.STARTED);
//                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
//                        }
////                        }
//
////                            geocoder = new Geocoder(getContext(), Locale.getDefault());
////                            addresses = geocoder.getFromLocation(Double.parseDouble(pickup_latitude), Double.parseDouble(pickup_longitude), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
////                            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
////                            tvBudgetFirstDesc.setText(address);
////                            tvBudgetFirst.setText("Pickup Location");
////                            tvBudgetSec.setText("Drop Off Location");
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
////                historyDetails = HistoryDetails.createOrderList(30);
//            }
//
//            @Override
//            public void onFailure(Call<Object> call, Throwable t) {
//                Log.d("ddddddd", t.getMessage());
//            }
//        });
//    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void startRider() {
        // To store rider current location and time

        Api service = RetrofitInstance.getRetrofitInstance().create(Api.class);
        String bear = "Bearer";
        String authorized = bear + " " + AppData.ACCESS_TOKEN;
        String val = "application/json";

        Order order = new Order(orderId);
        Call<Object> call = service.startRides(order, val, authorized);
        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                Log.d("ddd", response.toString());
                try {
                    if (response.isSuccessful()) {
                        String json1 = new Gson().toJson(response.body());
                        JSONObject json = new JSONObject(json1);
                        String message = json.getString("message");
                        riderId = json.getString("ride_id");
                        CurrentDateTime obj = new CurrentDateTime(riderId, String.valueOf(longitude), String.valueOf(latitude), new Date(), null);
                        Paper.book(mConstants.rideTimeRecordKeyBook).write(riderId, obj);
//                        showRideDetails(riderId, message);
//                        CurrentDateTime temp=Paper.book(mConstants.rideTimeRecordKeyBook).read(riderId);
//                        Log.e("DATA_STORED",temp.ride_id + " _ " + temp.getStartDateTime());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//   historyDetails = HistoryDetails.createOrderList(30);

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("ddddddd", t.getMessage());
            }
        });
    }

    private void completeRide(String rider_id) {
        Api service = RetrofitInstance.getRetrofitInstance().create(Api.class);
        String bear = "Bearer";
        String authorized = bear + " " + AppData.ACCESS_TOKEN;
        String val = "application/json";

        Rides rides = new Rides(rider_id);
        Call<Object> call = service.completeRide(rides, val, authorized);
        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                Log.d("ddd", response.toString());
                try {
                    if (response.isSuccessful()) {
                        String json1 = new Gson().toJson(response.body());
                        JSONObject json = new JSONObject(json1);
                        String message = json.getString("message");
//                        String riderId = json.getString("ride_id");
//                        showRideDetails(riderId);
                        clearUpViews(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                historyDetails = HistoryDetails.createOrderList(30);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("ddddddd", t.getMessage());
            }
        });
    }

    private void clearUpViews(String message) {
        btnConfirmPickUp.setVisibility(View.GONE);
        cardFirst.setVisibility(View.GONE);
        cardSecond.setVisibility(View.GONE);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        btnConfirmPickUp.setVisibility(View.GONE);
        btnCancelRide.setVisibility(View.GONE);
        btnConfirmPickUp.setText("Start Ride");
        cameraAnimated = false;
        getActivity().runOnUiThread(() -> {
            mMap.clear();
        });
    }

    private void cancelRider(String rider_id) {
        Api service = RetrofitInstance.getRetrofitInstance().create(Api.class);
        String bear = "Bearer";
        String authorized = bear + " " + AppData.ACCESS_TOKEN;
        String val = "application/json";

        Rides rides = new Rides(rider_id);
        Call<Object> call = service.cancelRide(rides, val, authorized);
        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                Log.d("ddd", response.toString());
                try {
                    if (response.isSuccessful()) {
                        String json1 = new Gson().toJson(response.body());
                        JSONObject json = new JSONObject(json1);
                        String message = json.getString("message");
//                        String riderId = json.getString("ride_id");
//                        showRideDetails(riderId);
                        clearUpViews(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                historyDetails = HistoryDetails.createOrderList(30);

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("ddddddd", t.getMessage());
            }
        });
    }

    private void sendCurrentLocation(String riderId, String longitude, String latitude) {

        Api service = RetrofitInstance.getRetrofitInstance().create(Api.class);
        String bear = "Bearer";
        String authorized = bear + " " + AppData.ACCESS_TOKEN;
        String val = "application/json";

        CurrentLocation move = new CurrentLocation(riderId, longitude, latitude);
        Call<Object> call = service.sendCurrentLocation(move, val, authorized);
        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                Log.d("ddd", response.toString());
//                try {
                if (response.isSuccessful()) {
//                        String json1 = new Gson().toJson(response.body());
//                        JSONObject json = new JSONObject(json1);
//                        String message = json.getString("message");
//                        String  riderId = json.getString("ride_id");
//                        Toast.makeText(getContext(), message+"rider:"+riderId, Toast.LENGTH_SHORT).show();
                }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

//                historyDetails = HistoryDetails.createOrderList(30);

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("ddddddd", t.getMessage());
            }
        });
    }

    private void showOrderTrackDetails(String orderId) {

        Api service = RetrofitInstance.getRetrofitInstance().create(Api.class);
/*
        String bear = "Bearer";
        String authorized = bear + " " + AppData.ACCESS_TOKEN;
        String val = "application/json";*/

        String acceptHeader = "application/json";
        String authorizationHeader = "Bearer " + SPM.getInstance().get(SPM.ACCESS_TOKEN, null);


        Call<Object> call = service.showOrderTrack(orderId, acceptHeader, authorizationHeader);
        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                Log.d("ddd", response.toString());
                try {
                    if (response.isSuccessful() && response.code() == 200) {

                        String json1 = new Gson().toJson(response.body());
                        JSONObject json = new JSONObject(json1).optJSONObject("ride");

                        Log.e("RideDetails", json.toString());
                        //                            Geocoder geocoder = null;
//                            List<Address> addresses;
//                            List<Address> addresses1;
//                            String destination_latitude = "";
//                            String destination_longitude = "";
//                            String pickup_latitude = json.getString("pickup_latitude");
//                            String pickup_longitude = json.getString("pickup_longitude");

                        /****
                         * for me
                         * First Pickup Location
                         * Second DropOff location
                         */
                        // setting pickupLocation

//                        if (!json.isNull("destination_latitude")) {
//                                destination_latitude = json.getString("destination_latitude");
//                                destination_longitude = json.getString("destination_longitude");
//                        orderStatus = new JSONObject(json1).getString("status");
                        Log.d("orderStatus", orderStatus);
//                        if (orderStatus.equals("Active")) {
                        if (json.isNull("destination_latitude") || json.isNull("destination_longitude")) {
                            getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Invalid Ride Details!", Toast.LENGTH_SHORT).show());
                            Paper.book(mConstants.rideTimeRecordKeyBook).write(riderId, 0);
                            btnConfirmPickUp.setVisibility(View.GONE);
                        } else {
                            cardSecond.setVisibility(View.VISIBLE);
                            cardFirst.setVisibility(View.VISIBLE);
                            pickUpLat = json.getDouble("pickup_latitude");
                            pickUpLong = json.getDouble("pickup_longitude");
                            Log.e("pickup_Details ", pickUpLat + " _ " + pickUpLong);

                            try {
                                tvBudgetFirstDesc.setText(getAddress(pickUpLat, pickUpLong));
                            } catch (Exception e) {
                                tvBudgetFirstDesc.setText("Location not available");
                                e.printStackTrace();
                            }

                            desOffLat = json.getDouble("destination_latitude");
                            desOffLong = json.getDouble("destination_longitude");
                            Log.e("destination ", desOffLat + " _ " + desOffLong);
                            try {
                                tvBudgetSecDesc.setText(getAddress(desOffLat, desOffLong));
                            } catch (Exception e) {
                                tvBudgetSecDesc.setText("Location not available");
                                e.printStackTrace();
                            }
//                                calculateDistane(pickUpLat, pickUpLong, desOffLat, desOffLong);
                            Log.e("RideStatus_STARTED", "Shift_to_Start");
                            changeRideStatus(RideStatus.STARTED);
//                            Toast.makeText(getContext(), "message", Toast.LENGTH_SHORT).show();
                            if (!orderId.equals("")) {
//                                    if (orderStatus.equals("Active")) {
                                LiveTrackOrder();
//                                    } else {
////                                        LiveTrackOrder("Active");
//                                        Toast.makeText(getContext(), "order already completed", Toast.LENGTH_SHORT).show();
//                                    }

                            }
                        }
//                        } else {
//                            Toast.makeText(getContext(), "order already completed", Toast.LENGTH_SHORT).show();
//
//                        }

//                            geocoder = new Geocoder(getContext(), Locale.getDefault());
//                            addresses = geocoder.getFromLocation(Double.parseDouble(pickup_latitude), Double.parseDouble(pickup_longitude), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//                            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//                            tvBudgetFirstDesc.setText(address);
//                            tvBudgetFirst.setText("Pickup Location");
//                            tvBudgetSec.setText("Drop Off Location");

                    } else {
                        Toast.makeText(getContext(), "Ride details are not available", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                historyDetails = HistoryDetails.createOrderList(30);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("ddddddd", t.getMessage());
            }
        });
    }

    private void changeRideStatus(RideStatus status) {
        currentRideStatus = status;
        Log.e("RideStatus", status.name());
        switch (currentRideStatus) {
            case IDEAL:

            case STARTED:
//                setUpDropOffMarker();
                setUpPickUpMarker();
                //   setUpRiderMarker();
                setUpShortestPath(currentRideStatus == RideStatus.IDEAL); // True to get trail from current location to pickUp location
                btnConfirmPickUp.setText("Confirm Pickup");
                btnConfirmPickUp.setVisibility(View.GONE);
                break;
            case PICKED_UP:
                setUpDropOffMarker();
                setUpShortestPath(currentRideStatus == RideStatus.IDEAL); // True to get trail from current location to pickUp location
                btnConfirmPickUp.setText("Complete Ride");
                btnCancelRide.setVisibility(View.VISIBLE);
                break;
            case COMPLETED:
//                completeRide(riderId);
//                showRideSummary(riderId);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                setBtnHolderConstraint(0.9f);
                break;
            case CANCELLED:
//                cancelRider(riderId);
//                showRideSummary(riderId);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                setBtnHolderConstraint(0.9f);
                break;
        }
    }

    private void showRideSummary(String riderId) {
        showRideSummaryDialog(riderId);
    }

    @Override
    public void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    private void getCurrentLocation() {
//        mMap.clear();
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        } else {
            // Write you code here if permission already given.
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (location != null) {
                //Getting longitude and latitude
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title("Rider Location")
                        .snippet("My"));
//

                //                LatLng latLng = new LatLng(latitude, longitude);
//                mMap.addMarker(new MarkerOptions()
//                        .position(latLng)
//                        .draggable(true)
//                        .title("Rider Location"));
//
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
//                mMap.getUiSettings().setZoomControlsEnabled(true);
//                Toast.makeText(getContext(), latitude + "", Toast.LENGTH_SHORT).show();
                //moving the map to location

            }
        }


    }

    void sendCurrentLocation() {
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                LatLng latLng = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .draggable(true)
                        .title("Rider Location"));

                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                mMap.getUiSettings().setZoomControlsEnabled(true);
//                double speed;
                Toast.makeText(getContext(), "location fechting", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                Log.d("dataLocation", s);
            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        LocationManager locationManager = (LocationManager) getContext().getApplicationContext().getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Start the service when the permission is granted
//            getCurrentLocation();
        }
    }

    void startTracking() {
        LocationManager lm = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(getContext(), "Please enable location services", Toast.LENGTH_SHORT).show();
            ((Activity) getContext()).finish();
        }

        // Check location permission is granted - if it is, start
        // the service, otherwise request the permission
        int permission = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            startTrackerService();
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST);
        }


    }

    private void startTrackerService() {
        getContext().startService(new Intent(getContext(), TrackerService.class));
        ((Activity) getContext()).finish();
    }

    void showDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        View itemView = inflater.inflate(R.layout.dialog_order_show, null);
        TextView tv_orderId = itemView.findViewById(R.id.tv_orderId);

        TextView tv_status = itemView.findViewById(R.id.tv_status);
        TextView tv_type = itemView.findViewById(R.id.tv_type);
//       TextView tv_pr_price = itemView.findViewById(R.id.tv_price);
        TextView tv_price = itemView.findViewById(R.id.tv_pay_price);
        TextView tv_pr_name = itemView.findViewById(R.id.tv_oderName);
//       TextView tv_pr_qt = itemView.findViewById(R.id.tv_quantity);
        TextView tv_cus_name = itemView.findViewById(R.id.tv_name);
        Button btnConfirm = itemView.findViewById(R.id.btnConfirm);
        Button btnCancel = itemView.findViewById(R.id.btnCancel);
        if (getArguments() != null) {
            tv_orderId.setText("Order Id #" + getArguments().getString("orderId"));
            tv_cus_name.setText(getArguments().getString("customerName"));
            tv_type.setText(getArguments().getString("type"));
            tv_status.setText(getArguments().getString("status"));
            tv_price.setText(getArguments().getString("price"));
            tv_pr_name.setText(getArguments().getString("productName"));
            dialogBuilder.setView(itemView);
            AlertDialog alertDialog = dialogBuilder.create();
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();

                }
            });
            alertDialog.show();
        }
    }

    ////////////////// Danish Code /////////////////
    private void animatedUserCam(Location location) {
        if (!cameraAnimated) {
            CameraPosition googlePlex = CameraPosition.builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                    .zoom(30)
                    .bearing(0)
                    .tilt(60)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 1000, null);
            cameraAnimated = true;
        }
    }

    String getAddress(double latitude, double longitude) throws IOException {

        if (Geocoder.isPresent()) {

            Geocoder myLocation = new Geocoder(getContext(), Locale.getDefault());
            List<Address> myList = myLocation.getFromLocation(latitude, longitude, 1);
            Address address = (Address) myList.get(0);
            String addressStr = "";
            addressStr += address.getAddressLine(0) + ", ";
            addressStr += address.getAddressLine(1) + ", ";
            addressStr += address.getAddressLine(2);
            return address.getAddressLine(0);
        } else {
            return "geoCoder not present";
        }

    }

    void setUpPickUpMarker() {
        if (pickUpMarker != null)
            pickUpMarker.remove();
        if (dropOffMarker != null)
            dropOffMarker.remove();
        Log.e("SettingPickUpMarker", "Setting");
        pickUpMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(pickUpLat, pickUpLong))
                .title("Pickup Location")
                .snippet(tvBudgetFirstDesc.getText().toString())
                .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_food_package))));
        dropOffMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(desOffLat, desOffLong))
                .title("Destination Location")
                .snippet(tvBudgetSecDesc.getText().toString())
                .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_house))));
        Log.e("PickupMarkerSet", "Done");
        animateFitCam(pickUpMarker, dropOffMarker);
    }

    void setUpDropOffMarker() {
        if (dropOffMarker != null)
            dropOffMarker.remove();
        dropOffMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(desOffLat, desOffLong))
                .title("Destination Location")
                .snippet(tvBudgetSecDesc.getText().toString())
                .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_house))));
        animateFitCam(pickUpMarker, dropOffMarker);
    }

    void setUpRiderMarker() {
        if (RiderMarker != null)
            RiderMarker.remove();
        RiderMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(riderLat, riderLong))
                .title("Rider Location")
                .snippet("Rider")
                .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_scooter))));
        //   animateFitCam(pickUpMarker, RiderMarker);
    }

//    private void showStartRideDialog() {
//        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
//        builder1.setTitle("Are You Sure!");
//        builder1.setMessage("Want to Start Ride");
//        builder1.setCancelable(true);
//        builder1.setPositiveButton(
//                "Yes",
//                (dialog, id) -> {
////                    showOrderTrackDetails(etOrderId.getText().toString());
////                    startRider();
//                });
//        builder1.setNegativeButton(
//                "Cancel",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
////                                    cancelRider(riderId);
//                    }
//                });
//        builder1.setNegativeButton(
//                "No",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                    }
//                });
//        AlertDialog alert11 = builder1.create();
//        alert11.show();
//    }
//
//    private void showConfirmPickUpDialog() {
//        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
//        builder1.setTitle("Confirm Pickup");
//        builder1.setMessage("Are you sure to confirm pickup?");
//        builder1.setCancelable(true);
//        builder1.setPositiveButton(
//                "Yes",
//                (dialog, id) -> {
//                    changeRideStatus(RideStatus.PICKED_UP);
//                });
//        builder1.setNegativeButton(
//                "Cancel",
//                (dialog, id) -> {
//                });
//        builder1.setNegativeButton(
//                "No",
//                (dialog, id) -> {
//                });
//        AlertDialog alert11 = builder1.create();
//        alert11.show();
//    }
//
//    private void showRideCompleteDialog() {
//        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
//        builder1.setTitle("Ride Complete");
//        builder1.setMessage("Are you sure to complete the ride?");
//        builder1.setCancelable(true);
//        builder1.setPositiveButton(
//                "Yes",
//                (dialog, id) -> {
//                    changeRideStatus(RideStatus.COMPLETED);
//                });
//        builder1.setNegativeButton(
//                "Cancel",
//                (dialog, id) -> {
//                });
//        builder1.setNegativeButton(
//                "No",
//                (dialog, id) -> {
//                });
//        AlertDialog alert11 = builder1.create();
//        alert11.show();
//    }
//
//    private void showRideCancelledDialog() {
//        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
//        builder1.setTitle("Cancel Ride");
//        builder1.setMessage("Are you sure to cancel the ride?");
//        builder1.setCancelable(true);
//        builder1.setPositiveButton(
//                "Yes",
//                (dialog, id) -> {
//                    changeRideStatus(RideStatus.CANCELLED);
//                });
//        builder1.setNegativeButton(
//                "Cancel",
//                (dialog, id) -> {
//                });
//        builder1.setNegativeButton(
//                "No",
//                (dialog, id) -> {
//                });
//        AlertDialog alert11 = builder1.create();
//        alert11.show();
//    }

    private void animateFitCam(Marker firstMarker, Marker secondMarker) {
        Log.e("AnimationMarker", "Start");
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(firstMarker.getPosition());
        builder.include(secondMarker.getPosition());
        LatLngBounds bounds = builder.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                350,
                getResources().getDisplayMetrics()
        );
        int padding = (int) (width * 0.19); // offset from edges of the map 10% of screen
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
        mMap.animateCamera(cu);
        Log.e("AnimationMarker", "Done");
    }

    private void animateFitCamRider(Marker firstMarker) {
        Log.e("AnimationMarker", "Start");
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(firstMarker.getPosition());
//        builder.include(secondMarker.getPosition());
        LatLngBounds bounds = builder.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                350,
                getResources().getDisplayMetrics()
        );
        int padding = (int) (width * 0.19); // offset from edges of the map 10% of screen
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
        mMap.animateCamera(cu);
        Log.e("AnimationMarker", "Done");
    }

    private void setUpShortestPath(boolean isCurrentLocToPickup) {
        Tasks.executeInBackground(getContext(), new BackgroundWork<List<LatLng>>() {
            @Override
            public List<LatLng> doInBackground() throws Exception {
                List<LatLng> shortestRoute = getShortestRoute(isCurrentLocToPickup);
                return shortestRoute;
            }
        }, new Completion<List<LatLng>>() {
            @Override
            public void onSuccess(Context context, List<LatLng> path) {
                getActivity().runOnUiThread(() -> {
                    if (finalPolyLine != null) {
                        Log.e("Removing_", "Polyline");
                        finalPolyLine.remove();
                    }
                    if (path.size() > 0) {
                        PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.BLUE).width(10);
                        finalPolyLine = mMap.addPolyline(opts);
                    }
                });
            }

            @Override
            public void onError(Context context, Exception e) {
            }
        });
    }

    private List<LatLng> getShortestRoute(boolean isCurrentLocToPickup) {
        List<LatLng> path = new ArrayList();

        getActivity().runOnUiThread(() -> {

            GeoApiContext context = new GeoApiContext.Builder()
//                    .apiKey(getActivity().getResources().getString(R.string.map_key))
                    .apiKey("AIzaSyCBt_NpnieCbgy_rFuhh-DAYarQwlYBf2Y")
                    .build();
            LatLng sourceLatLng = null, desLatLng = null;
//            if (isCurrentLocToPickup) {
////                sourceLatLng = new LatLng(currentUserMarker.getPosition().latitude, currentUserMarker.getPosition().longitude);
////                desLatLng = new LatLng(pickUpLat, pickUpLong);
//            } else {
            sourceLatLng = new LatLng(pickUpLat, pickUpLong);
//                       sourceLatLng = new LatLng(currentUserMarker.getPosition().latitude, currentUserMarker.getPosition().longitude);
            desLatLng = new LatLng(desOffLat, desOffLong);
//            }
            DirectionsApiRequest req = DirectionsApi.getDirections(context, sourceLatLng.latitude + "," + sourceLatLng.longitude, desLatLng.latitude + "," + desLatLng.longitude);
            try {
                DirectionsResult res = req.await();
                if (res.routes != null && res.routes.length > 0) {
                    DirectionsRoute route = res.routes[0];

                    if (route.legs != null) {
                        for (int i = 0; i < route.legs.length; i++) {
                            DirectionsLeg leg = route.legs[i];
                            if (leg.steps != null) {
                                for (int j = 0; j < leg.steps.length; j++) {
                                    DirectionsStep step = leg.steps[j];
                                    if (step.steps != null && step.steps.length > 0) {
                                        for (int k = 0; k < step.steps.length; k++) {
                                            DirectionsStep step1 = step.steps[k];
                                            EncodedPolyline points1 = step1.polyline;
                                            if (points1 != null) {
                                                List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                                for (com.google.maps.model.LatLng coord1 : coords1) {
                                                    path.add(new LatLng(coord1.lat, coord1.lng));
                                                }
                                            }
                                        }
                                    } else {
                                        EncodedPolyline points = step.polyline;
                                        if (points != null) {
                                            List<com.google.maps.model.LatLng> coords = points.decodePath();
                                            for (com.google.maps.model.LatLng coord : coords) {
                                                path.add(new LatLng(coord.lat, coord.lng));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                Log.e(TAG, ex.getLocalizedMessage());
            }
        });
        return path;
    }

    private void showRideSummaryDialog(String riderId) {
        Log.e("RIDEE_ID", riderId);
        CurrentDateTime obj = Paper.book(mConstants.rideTimeRecordKeyBook).read(riderId);
//        obj.setEndDateTime(new Date());
        Log.e("StartDateTime", obj.getStartDateTime().toString());
//        parseDateToddMMyyyy(obj.getStartDateTime().toString());
        View view = getLayoutInflater().inflate(R.layout.dialog_ride_summary, null, false);
        AlertDialog dialog;
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setView(view);
        dialog = builder1.create();

//        String stringDate = DateFormat.getDateTimeInstance().format(date);
        ((TextView) view.findViewById(R.id.tx_time_duration_2)).setText(printDuration(obj.getStartDateTime(), new Date()));
        ((TextView) view.findViewById(R.id.tx_id)).setText("Ride Id : " + obj.ride_id);
        ((TextView) view.findViewById(R.id.tx_time_start)).setText(DateFormat.getDateTimeInstance().format(obj.getStartDateTime()));
        ((TextView) view.findViewById(R.id.tx_time_end)).setText(DateFormat.getDateTimeInstance().format(new Date()));
        try {
            ((TextView) view.findViewById(R.id.tx_loc_start)).setText(getAddress(Double.parseDouble(obj.latitude), Double.parseDouble(obj.longitude)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ((TextView) view.findViewById(R.id.tx_loc_end)).setText(getAddress(desOffLat, desOffLong));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        ((TextView)view.findViewById(R.id.tx_time_duration)).setText(calculationByDistance(new LatLng(Double.parseDouble(obj.latitude),Double.parseDouble(obj.longitude)),new LatLng(desOffLat,desOffLat)));
//        ((TextView)view.findViewById(R.id.tx_time_duration)).setText(""+SphericalUtil.computeDistanceBetween(new LatLng(pickUpLat,pickUpLong),new LatLng(desOffLat,desOffLat)));
        float[] results = new float[2];
        Location.distanceBetween(pickUpLat, pickUpLong,
                desOffLat, desOffLong,
                results);
        Log.e("results", "" + results[0] + " _ " + results[1]);
        double kilometers = results[0] * 0.001;
        ((TextView) view.findViewById(R.id.tx_time_duration)).setText(Math.round(kilometers) + " KM");

        AlertDialog finalDialog = dialog;
        ((Button) view.findViewById(R.id.btn_close)).setOnClickListener((View.OnClickListener) v -> {
            finalDialog.dismiss();
        });
        ((ImageButton) view.findViewById(R.id.bt_close)).setOnClickListener((View.OnClickListener) v -> {
            finalDialog.dismiss();
        });
        dialog.show();
    }

    private Bitmap getMarkerBitmapFromView(@DrawableRes int resId) {

        View customMarkerView = Objects.requireNonNull(getActivity()).getLayoutInflater().inflate(R.layout.view_custom_marker, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.profile_image);
        markerImageView.setImageResource(resId);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);

        return returnedBitmap;

    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd-MMM-yyyy h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public String getDurationForRoute(com.google.maps.model.LatLng origin, com.google.maps.model.LatLng destination) {
        GeoApiContext geoApiContext = new GeoApiContext.Builder()
                .apiKey(getResources().getString(R.string.working_api))
                .build();

        // - Perform the actual request
        DirectionsResult directionsResult = null;
        try {
            directionsResult = DirectionsApi.newRequest(geoApiContext)
                    .mode(TravelMode.DRIVING)
                    .origin(origin)
                    .destination(destination)
                    .await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DirectionsRoute route = directionsResult.routes[0];
        DirectionsLeg leg = route.legs[0];
        Duration duration = leg.duration;
        return duration.humanReadable;
    }

    // duration fuction
    public String printDuration(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
        return elapsedDays + " days " + elapsedHours + " hours " + elapsedMinutes + " minutes" + elapsedSeconds + " seconds";
    }

    @SuppressLint("DefaultLocale")
    private void setLocationUpdatesSheet(Location arg0) {
        if (currentRideStatus == RideStatus.STARTED) {
            showBottomSheet();
            if (riderLong != 0 || riderLat != 0) {
//                getDistance(riderLat, riderLong, desOffLat, desOffLong);
//                DecimalFormat newFormat = new DecimalFormat("####");
//                rideEstDistance = newFormat.format(Double.valueOf(CalculationByDistance(new LatLng(riderLat, riderLong), new LatLng(desOffLat, desOffLong))));
//                rideEstDistance= String.valueOf(CalculationByDistance(new LatLng(riderLat, riderLong),new LatLng(desOffLat, desOffLong)));
//                rideEstDuration = getDurationForRoute(new com.google.maps.model.LatLng(
//                        riderLat, riderLong), new com.google.maps.model.LatLng(
//                        desOffLat, desOffLong));
//                rideEstDuration = String.valueOf((Double.parseDouble(rideEstDistance) * 10.0));
//                rideEstDistance= String.valueOf(Math.round(CalculationByDistance(new LatLng(riderLat, riderLong), new LatLng(desOffLat, desOffLong))));
                rideEstDistance = String.format("%.2f", CalculationByDistance(new LatLng(riderLat, riderLong), new LatLng(desOffLat, desOffLong)));
                rideEstDuration = String.valueOf(Math.round(Double.parseDouble(rideEstDistance) * 10));
                Log.e("EstimatedDuration", rideEstDuration);
                if (txEstDistance != null) {
                    if (Double.parseDouble(rideEstDistance) < 1.0) {
                        rideEstDistance = String.valueOf(Double.parseDouble(Double.toString(Double.parseDouble(rideEstDistance)).replace(".", "")));
                        txEstDistance.setText(rideEstDistance + " m");
                    } else {

                        txEstDistance.setText(rideEstDistance + " Km");
                    }
                    txEstDuration.setText(rideEstDuration + " min");
                }
            }
        } else if (currentRideStatus == RideStatus.PICKED_UP) {
            showBottomSheet();
            if (riderLat != 0 || riderLong != 0) {
//                getDistance(riderLat, riderLong, desOffLat, desOffLong);
//
//                rideEstDuration = getDurationForRoute(new com.google.maps.model.LatLng(
//                        riderLat, riderLong), new com.google.maps.model.LatLng(
//                        desOffLat, desOffLong));

//                new code for manual distance calculation
//                rideEstDistance= String.valueOf(CalculationByDistance(new LatLng(riderLat, riderLong),new LatLng(desOffLat, desOffLong)));
//                rideEstDuration = getDurationForRoute(new com.google.maps.model.LatLng(
//                        riderLat, riderLong), new com.google.maps.model.LatLng(
//                        desOffLat, desOffLong));
//                rideEstDistance= String.valueOf(Math.round(CalculationByDistance(new LatLng(riderLat, riderLong), new LatLng(desOffLat, desOffLong))));
//                rideEstDuration = String.valueOf((Integer.parseInt(rideEstDistance) * 10));
//
                rideEstDistance = String.format("%.2f", CalculationByDistance(new LatLng(riderLat, riderLong), new LatLng(desOffLat, desOffLong)));
                rideEstDuration = String.valueOf(Math.round(Double.parseDouble(rideEstDistance) * 10));

            }
            Log.e("EstimatedDuration", rideEstDuration);
            if (txEstDistance != null) {
                if (Double.parseDouble(rideEstDistance) < 1.0) {
                    rideEstDistance = String.valueOf(Double.parseDouble(Double.toString(Double.parseDouble(rideEstDistance)).replace(".", "")));
                    txEstDistance.setText(rideEstDistance + " m");

                } else {

                    txEstDistance.setText(rideEstDistance + " Km");
                }
                txEstDuration.setText(rideEstDuration + " min");
            }
        }

    }

    public void getDistance(final double lat1, final double lon1, final double lat2, final double lon2) {
        String apiKey = getResources().getString(R.string.working_api);
        Tasks.executeInBackground(getContext(), () -> {
            return getDistanceApi(apiKey, lat1, lon1, lat2, lon2);
        }, new Completion<String>() {
            @Override
            public void onSuccess(Context context, String result) {
                rideEstDistance = result;
            }

            @Override
            public void onError(Context context, Exception e) {
            }
        });
    }

    String getDistanceApi(String apiKey, double lat1, double lon1, double lat2, double lon2) throws IOException, JSONException {
        URL url = new URL("https://maps.googleapis.com/maps/api/directions/json?origin=" + lat1 + "," + lon1 + "&destination=" + lat2 + "," + lon2 + "&sensor=false&units=metric&mode=driving&key=" + apiKey);
        Log.e("FINE:_UEL", url.getPath() + "");
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        InputStream in = new BufferedInputStream(conn.getInputStream());
        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        StringBuilder total = new StringBuilder();
        for (String line; (line = r.readLine()) != null; ) {
            total.append(line).append('\n');
        }
//                Log.e("RESPONSEEE",String.valueOf(total));
        String response = String.valueOf(total);

        JSONObject jsonObject = new JSONObject(response);
        JSONArray array = jsonObject.getJSONArray("routes");
        JSONObject routes = array.getJSONObject(0);
        JSONArray legs = routes.getJSONArray("legs");
        JSONObject steps = legs.getJSONObject(0);
        JSONObject distance = steps.getJSONObject("distance");
        return distance.get("text").toString();
    }

//    public void calculateDistane(double lata, double langa, double latb, double langb) {
//
//
//        Location startPoint = new Location("locationA");
//        startPoint.setLatitude(lata);
//        startPoint.setLongitude(langa);
//
//        Location endPoint = new Location("locationA");
//        endPoint.setLatitude(latb);
//        endPoint.setLongitude(langb);
//
//        double distance = startPoint.distanceTo(endPoint);
//        int a = 0;
//        distancekm = distance / 1000;
//        round = (int) Math.round(distancekm);
//
//        time = distancekm * 10;
//        roundtime = (int) Math.round(time);
//
//    }

    void showBottomSheet() {
        if (bottomSheetBehavior == null) {
            Log.e("NULL", "SHETTTT");
//            bottomSheetFragment = new RideBottomList();
            bottomSheetBehavior = BottomSheetBehavior.from(sheetView);
            bottomSheetBehavior.setState(STATE_EXPANDED);
            sheetView.setVisibility(View.VISIBLE);
            btnClose = sheetView.findViewById(R.id.btn_close);
            txEstDistance = sheetView.findViewById(R.id.tx_est_distance);
            txEstDuration = sheetView.findViewById(R.id.tx_est_duration);
            txEstDistance.setText(String.valueOf(round + "     km"));
            txEstDuration.setText(String.valueOf(roundtime + "     min"));
            setBtnHolderConstraint(0.7f);

        }
//        else if(RideBottomList.bottomSheetBehavior== null ){
//            Log.e("!!STATE_EXPANDED","STATE_EXPANDED");
//            bottomSheetFragment.show(getChildFragmentManager(), "btm_sheet");
////            setBtnHolderConstraint(0.7f);
//        }else if(RideBottomList.bottomSheetBehavior.getState() != STATE_EXPANDED){
//            Log.e("bottomSheetBehavior","NOT_EXPANDED");
//            RideBottomList.bottomSheetBehavior.setState(STATE_EXPANDED);
//        }

//        if(RideBottomList.bottomSheetBehavior!=null) {
//            Log.e("bottomSheetBehavior : ", RideBottomList.bottomSheetBehavior.getState() + "");
//        }
//        else{
//            RideBottomList.bottomSheetDialog
//                    .getWindow()
//                    .findViewById(R.id.design_bottom_sheet)
//                    .setBackgroundResource(android.R.color.transparent);
//        }
//        else if(RideBottomList.bottomSheetBehavior.getState() != STATE_EXPANDED){
//            RideBottomList.bottomSheetBehavior.setState(STATE_EXPANDED);
//        }
    }

    void setBtnHolderConstraint(float value) {
        Log.e("Setting_value", "" + value);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) linLayBtn.getLayoutParams();
        params.verticalBias = value;
        linLayBtn.setLayoutParams(params);
    }

    void showOrderId() {

        showOrderTrackDetails(orderId);
/*


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        View itemView = inflater.inflate(R.layout.dialog_order_id, null);
        EditText etOrder    Id = itemView.findViewById(R.id.etOrderId);
//   TextView tv_cus_name = itemView.findViewById(R.id.tv_name);
        Button btnConfirm = itemView.findViewById(R.id.btnConfirm);
        Button btnCancel = itemView.findViewById(R.id.btnCancel);

        dialogBuilder.setView(itemView);
        AlertDialog alertDialog = dialogBuilder.create();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                alertDialog.dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etOrderId.getText().toString().isEmpty()) {
                    orderId=etOrderId.getText().toString();
                    showOrderTrackDetails(etOrderId.getText().toString());
                }
                alertDialog.dismiss();

            }
        });
        alertDialog.show();
*/
    }

    private void LiveTrackOrder() {
//        orderStatus = "Active";
        Api service = RetrofitInstance.getRetrofitInstance().create(Api.class);
//        String bear = "Bearer";
//        String authorized = bear + " " + SPM.ACCESS_TOKEN;
//        String acceptHeader = "application/json";
        String authorizationHeader = "Bearer " + SPM.getInstance().get(SPM.ACCESS_TOKEN, null);

        String val = "application/json";
        Call<Object> call = service.LiveOrderTrack(orderId, val, authorizationHeader);
        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                Log.d("ddd", response.toString());
                try {
                    if (response.isSuccessful()) {

                        String json1 = new Gson().toJson(response.body());
                        JSONObject json = new JSONObject(json1);
                        orderStatus1 = json.getString("status");
                        Log.d("orderStatusss", response.body().toString());

                        if (json.isNull("current_position_latitude") || json.isNull("current_position_longitude")) {
                            getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Invalid Ride Details!", Toast.LENGTH_SHORT).show());
                        } else {
                            if (orderStatus1.equals("Active")) {
                                cardSecond.setVisibility(View.VISIBLE);
                                cardFirst.setVisibility(View.VISIBLE);
                                riderLat = json.getDouble("current_position_latitude");
                                riderLong = json.getDouble("current_position_longitude");
                                String riderAddress = getAddress(riderLat, riderLong);
                                if (RiderMarker != null)
                                    RiderMarker.remove();
                                RiderMarker = mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(riderLat, riderLong))
                                        .title("Rider Current Location")
                                        .snippet(riderAddress)
                                        .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_scooter))));
//                                 animateFitCam(RiderMarker,dropOffMarker);
//

                            } else {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                                builder1.setTitle("Order Tracking status");
                                builder1.setMessage("Order Already completed,Go Back");
                                builder1.setCancelable(true);
                                builder1.setPositiveButton(
                                        "OK",
                                        (dialog, id) -> {
                                            try {
                                                startActivity(new Intent(getContext(),
                                                        Class.forName("com.alllinkshare.user.ui.activities.OrdersActivity")));
                                            } catch (ClassNotFoundException e) {
                                                e.printStackTrace();
                                            }
                                            ((Activity) getContext()).finish();
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                                Log.d("show dialog", "completed dialog");
                            }
                        }
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
//                historyDetails = HistoryDetails.createOrderList(30);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("ddddddd", t.getMessage());
            }
        });

    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);


        return Radius * c;
    }

    enum RideStatus {
        IDEAL, // Currently null
        STARTED, // Ride has been started
        PICKED_UP, // Ride has confirmed pickup
        COMPLETED,  //Ride is completed
        CANCELLED   //Ride is cancelled
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}