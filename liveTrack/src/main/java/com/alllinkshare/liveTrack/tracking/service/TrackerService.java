package com.alllinkshare.liveTrack.tracking.service;

import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class TrackerService extends Service {

    private static final String TAG = TrackerService.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        buildNotification();
        requestLocationUpdates();

    }

//    private void buildNotification() {
//        String stop = "stop";
//        registerReceiver(stopReceiver, new IntentFilter(stop));
//        PendingIntent broadcastIntent = PendingIntent.getBroadcast(
//                this, 0, new Intent(stop), PendingIntent.FLAG_UPDATE_CURRENT);
//        // Create the persistent notification
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                .setContentTitle(getString(R.string.app_name))
//                .setContentText(getString("data"))
//                .setOngoing(true)
//                .setContentIntent(broadcastIntent)
//                .setSmallIcon(R.drawable.ic_baseline_directions_bus_24);
//        startForeground(1, builder.build());
//    }

    protected BroadcastReceiver stopReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "received stop broadcast");
            // Stop the service when the notification is tapped
            unregisterReceiver(stopReceiver);
            stopSelf();
        }
    };

//    private void loginToFirebase() {
//        // Authenticate with Firebase, and request location updates
//        String email = getString(R.string.firebase_email);
//        String password = getString(R.string.firebase_password);
//        FirebaseAuth.getInstance().signInWithEmailAndPassword(
//                email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
//            @Override
//            public void onComplete(Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    Log.d(TAG, "firebase auth success");
//                    requestLocationUpdates();
//                } else {
//                    Log.d(TAG, "firebase auth failed");
//                }
//            }
//        });
//    }

    private void requestLocationUpdates() {
        LocationRequest request = new LocationRequest();
        request.setInterval(1000);
        request.setFastestInterval(1000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
//        final String path = getString(R.string.firebase_path) + "/";
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            // Request location updates and when an update is
            // received, store the location in Firebase
            client.requestLocationUpdates(request, new LocationCallback() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    Log.d("locationUpdate:", locationResult.getLastLocation().getLatitude() + "");

                    //                    locationUpdation(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude(), locationResult.getLastLocation().getSpeed());
//                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path).push();
//                    Location location = locationResult.getLastLocation();
//                    if (location != null) {
//                        Log.d(TAG, "location update " + location);
//                        ref.setValue(location);
//                    }
                }
            }, null);
        }
    }

//    private void locationUpdation(double lat, double lng, double speed) {
//
//        ParentDataModel firebMessage = new ParentDataModel(AppData.parentDataModels1.get(0).name,
//                lat + "",
//                lng + "",
//                AppData.parentDataModels1.get(0).username,
//                AppData.parentDataModels1.get(0).password,
//                speed + "");
//        FirebaseDatabase.getInstance().getReference("Users").child("Driver").child(AppData.userId).setValue(firebMessage, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                if (databaseError != null) {
//                    Log.e(TAG, "Data Not saved");
//
//                } else {
//                    Log.e(TAG, "Data saved successfully");
//                }
//            }
//        });
//    }

}