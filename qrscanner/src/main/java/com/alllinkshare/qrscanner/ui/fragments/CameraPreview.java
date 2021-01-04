package com.alllinkshare.qrscanner.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import java.io.IOException;

import static android.content.ContentValues.TAG;
import static android.content.Context.WINDOW_SERVICE;

public class CameraPreview extends SurfaceView{
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private boolean isPreviewRunning = true;

    public CameraPreview(final Context context, Camera camera) {
        super(context);
        mCamera = camera;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                // The Surface has been created, now tell the camera where to draw the preview.
                try {
                    mCamera.setPreviewDisplay(surfaceHolder);
                    mCamera.startPreview();
                } catch (IOException e) {
                    Log.d(TAG, "Error setting camera preview: " + e.getMessage());
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                // If your preview can change or rotate, take care of those events here.
                // Make sure to stop the preview before resizing or reformatting it.

                if (mHolder.getSurface() == null){
                    // preview surface does not exist
                    return;
                }

                // stop preview before making changes
                if(isPreviewRunning){
                    try {
                        mCamera.stopPreview();
                    } catch (Exception e){
                        // ignore: tried to stop a non-existent preview
                    }
                }

                // set preview size and make any resize, rotate or
                // reformatting changes here

                Camera.Parameters parameters = mCamera.getParameters();
                Display display = ((WindowManager) context.getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

                if(display.getRotation() == Surface.ROTATION_0) {
                    mCamera.setDisplayOrientation(90);
                }
                if(display.getRotation() == Surface.ROTATION_270) {
                    mCamera.setDisplayOrientation(180);
                }
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                mCamera.setParameters(parameters);

                // start preview with new settings
                try {
                    mCamera.setPreviewDisplay(mHolder);
                    mCamera.startPreview();
                    isPreviewRunning = true;

                } catch (Exception e){
                    Log.d(TAG, "Error starting camera preview: " + e.getMessage());
                }
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                // empty. Take care of releasing the Camera preview in your activity.
            }
        });
    }
}