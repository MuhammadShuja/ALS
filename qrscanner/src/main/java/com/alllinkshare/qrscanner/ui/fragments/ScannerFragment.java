package com.alllinkshare.qrscanner.ui.fragments;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

import com.alllinkshare.core.navigator.Coordinator;
import com.alllinkshare.qrscanner.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Objects;

public class ScannerFragment extends Fragment {
    private static final String TAG = "QRScanner";
    private static final int REQUEST_CODE_PERMISSION = 111;
    private static final String FRAGMENT_HOST = "fragment_host";

    private int fragmentHost;

    private Camera mCamera;
    private CameraPreview mPreview;

    private BarcodeScanner scanner;

    private boolean isScanning = false;
    private boolean qrScanned = false;

    private View rootView;

    public ScannerFragment() {
    }

    public static ScannerFragment newInstance(int fragmentHost) {
        ScannerFragment fragment = new ScannerFragment();
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_HOST, fragmentHost);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragmentHost = getArguments().getInt(FRAGMENT_HOST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_scanner, container, false);

        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION) {// If request is cancelled, the result arrays are empty.
            initCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mCamera != null)
        {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        initCamera();
        initScannerAnimation();
        initScanner();
    }

    private void initCamera(){
        if(!hasCameraPermission()) requestCameraPermission();
        else{
            // Create an instance of Camera
            mCamera = getCameraInstance();
            mCamera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] bytes, Camera camera) {
                    if(!isScanning && !qrScanned){
                        Camera.Size previewSize = mCamera.getParameters().getPreviewSize();
                        YuvImage yuvImage = new YuvImage(bytes, ImageFormat.NV21, previewSize.width, previewSize.height, null);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        yuvImage.compressToJpeg(new Rect(0,0, previewSize.width, previewSize.height),80, baos);
                        byte[] byteArray = baos.toByteArray();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray,0, byteArray.length);
                        new Rect(100, 100, 500, 500);

                        scanBarcode(bitmap);

                        mCamera.addCallbackBuffer(bytes);
                    }
                }
            });

            // Create our Preview view and set it as the content of our activity.
            mPreview = new CameraPreview(getContext(), mCamera);
            FrameLayout preview = (FrameLayout) rootView.findViewById(R.id.camera_preview);
            preview.addView(mPreview);
        }
    }

    private void initScannerAnimation(){
        final View scannerLayout = (FrameLayout) rootView.findViewById(R.id.camera_preview);
        final View scannerBar = rootView.findViewById(R.id.scannerBar);

        ViewTreeObserver vto = scannerLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scannerLayout.getViewTreeObserver().
                        removeOnGlobalLayoutListener(this);

                float destination = (float)(scannerLayout.getY() +
                        scannerLayout.getHeight());

                ObjectAnimator animator = ObjectAnimator.ofFloat(scannerBar, "translationY",
                        scannerLayout.getY(),
                        destination);

                animator.setRepeatMode(ValueAnimator.REVERSE);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(1500);
                animator.start();
            }
        });
    }

    private void initScanner(){
        BarcodeScannerOptions options =
        new BarcodeScannerOptions.Builder()
        .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE)
        .build();
        scanner = BarcodeScanning.getClient(options);
    }

    private void scanBarcode(Bitmap bitmap){
        isScanning = true;

        InputImage image = InputImage.fromBitmap(bitmap, 0);
        scanner.process(image)
                .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                    @Override
                    public void onSuccess(List<Barcode> barcodes) {
                        for (Barcode barcode: barcodes) {
                            String rawValue = barcode.getRawValue();

                            if(rawValue != null){
                                Coordinator.getCatalogNavigator()
                                        .navigateToListing(Integer.parseInt(rawValue), -1);

                                qrScanned = true;
                            }
                        }
                        isScanning = false;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        isScanning = false;
                    }
                });
    }

    private static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    private boolean hasCameraPermission() {
        return ActivityCompat.checkSelfPermission(
                Objects.requireNonNull(getContext()), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission(){
        String[] PERMISSIONS = {
                Manifest.permission.CAMERA
        };
        requestPermissions(PERMISSIONS, REQUEST_CODE_PERMISSION);
    }
}