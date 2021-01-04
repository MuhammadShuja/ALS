package com.alllinkshare.chat;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.Objects;

public class ChatActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSION = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_chat);

        initToolbar();
        initChat();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {// If request is cancelled, the result arrays are empty.
            initChat();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initToolbar(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.bg));
        }
        else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setTitle("Chat");
    }

    private void initChat(){
        if(!hasPermissions()) requestPermissions();

        WebView webView = findViewById(R.id.chat_view);
        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        webView.loadUrl("https://www.jobcallme.com:22000/#!/dash?userToken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVmNWI1MmY5ZjYzMTkyNzFmMTVkZWRhNSIsIm5hbWUiOiJXYXFhcyIsImVtYWlsIjoid2FxYXNAZ21haWwuY29tIiwiaWF0IjoxNjA4MDY2MjU4fQ.-yeRlKUG5zzM7EZEQRTbPU7F152pGIe30ggE3UjM5eA&userId=5f5b52f9f6319271f15deda5&projectId=5d4c07fb030f5d0600bf5c04");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                findViewById(R.id.loading_wrapper).setVisibility(View.GONE);
                findViewById(R.id.data_wrapper).setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Toast.makeText(getApplicationContext(), request.getUrl().toString(), Toast.LENGTH_LONG).show();
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
    }

    private boolean hasPermissions() {
        return ActivityCompat.checkSelfPermission(
                ChatActivity.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                        ChatActivity.this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions(){
        String[] PERMISSIONS = {
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
        };

        ActivityCompat.requestPermissions(ChatActivity.this, PERMISSIONS, REQUEST_CODE_PERMISSION);
    }
}