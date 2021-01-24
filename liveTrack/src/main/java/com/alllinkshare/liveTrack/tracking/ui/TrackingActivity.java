package com.alllinkshare.liveTrack.tracking.ui;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alllinkshare.liveTrack.R;
import com.alllinkshare.liveTrack.tracking.TrackOrderFragment;

public class TrackingActivity extends AppCompatActivity implements TrackOrderFragment.OnFragmentInteractionListener {
    private Fragment fragment = null;
    private Class fragmentClass = null;
    private String fragmentTitle = null;
    Bundle bundle;
    private int orderid;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_tracking);
        fragmentClass = TrackOrderFragment.class;
        fragmentTitle = "Track Order";
        bundle = getIntent().getExtras();
        orderid = bundle.getInt("orderid");
        //  Toast.makeText(getApplicationContext(), "............" + orderid, Toast.LENGTH_SHORT).show();

        value = String.valueOf(orderid);


        bundle.putString("value", value);

        //    Toast.makeText(getApplicationContext(),"....Activity.......Order id"+value,Toast.LENGTH_SHORT).show();

        initToolbar();
        loadFragment();


    }

    public int getMyData() {
        return orderid;
    }

    private void initToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorThemeDark));
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("My orders tracking");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadFragment() {

/*

        Bundle bundle = getIntent().getExtras();
        int orderid = bundle.getInt("orderid");
        Toast.makeText(getApplicationContext(), "............" + orderid, Toast.LENGTH_SHORT).show();

        bundle.putInt("value", orderid);
*/

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            fragment.setArguments(bundle);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.mainContainer, fragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        if (fragmentTitle != null) {
            setTitle(fragmentTitle);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void btn_live_tracking(View view) {
    }
}