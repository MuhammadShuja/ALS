package com.alllinkshare.user.ui.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alllinkshare.auth.ui.activities.LoginActivity;
import com.alllinkshare.core.forms.Form;
import com.alllinkshare.core.forms.validation.ValidationRule;
import com.alllinkshare.user.R;
import com.alllinkshare.user.api.API;
import com.alllinkshare.user.events.ProfileUpdateEvent;
import com.alllinkshare.user.events.ProfileUpdateListener;
import com.alllinkshare.user.models.User;
import com.alllinkshare.user.repos.UserRepository;
import com.alllinkshare.user.ui.fragments.ChatFragment;
import com.alllinkshare.user.ui.fragments.CouponFragment;
import com.alllinkshare.user.ui.fragments.CredentialsFragment;
import com.alllinkshare.user.ui.fragments.DashboardFragment;
import com.alllinkshare.user.ui.fragments.FavouritesFragment;
import com.alllinkshare.user.ui.fragments.ProfileFragment;
import com.alllinkshare.user.ui.fragments.UploadFragment;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_profile);

        if(!API.isAuthenticated()){
            finish();
        }

        initDrawer();
        initToolbar();
        initFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_profile_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
        }
        else if(id == R.id.nav){
            drawerLayout.openDrawer(GravityCompat.END);
        }

        return super.onOptionsItemSelected(item);
    }

    private void initDrawer(){
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.user_navigation_drawer_open, R.string.user_navigation_drawer_close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        View headerView =  navigationView.getHeaderView(0);

        final LinearLayout navAction = headerView.findViewById(R.id.navigation_header_container);
        final TextView navActionTitle = headerView.findViewById(R.id.action);
        final CircleImageView navProfilePicture = headerView.findViewById(R.id.profile_picture);

        if(API.isAuthenticated()){
            UserRepository.getInstance().getProfile(new UserRepository.ProfileReadyListener() {
                @Override
                public void onProfileReady(User profile) {
                    Glide
                            .with(ProfileActivity.this)
                            .load(profile.getProfilePicture())
                            .into(navProfilePicture);
                    navActionTitle.setText(profile.getFullName());
                }

                @Override
                public void onFailure(String error) {

                }
            });
            navAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadFragment("ProfileFragment");
                    drawerLayout.closeDrawer(GravityCompat.END);
                }
            });

            new ProfileUpdateEvent().addOnUpdateListener(new ProfileUpdateListener() {
                @Override
                public void onUpdate(User profile) {
                    Glide
                            .with(ProfileActivity.this)
                            .load(profile.getProfilePicture())
                            .into(navProfilePicture);
                    navActionTitle.setText(profile.getFullName());
                }
            });
        }
        else{
            Glide
                    .with(this)
                    .load(getResources().getIdentifier("profile_picture_placeholder", "drawable", this.getPackageName()))
                    .into(navProfilePicture);
            navActionTitle.setText("Login");
            navAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                }
            });
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int position = item.getItemId();

                if(position == R.id.nav_dashboard){
                    loadFragment("DashboardFragment");
                }
                else if(position == R.id.nav_credentials){
                    loadFragment("CredentialsFragment");
                }
                else if(position == R.id.nav_favourites){
                    loadFragment("FavouritesFragment");
                }
                else if(position == R.id.nav_coupon){
                    loadFragment("CouponFragment");
                }
                else if(position == R.id.nav_chat_box){
                    loadFragment("ChatFragment");
                }
                else if(position == R.id.nav_upload){
                    loadFragment("UploadFragment");
                }
                drawerLayout.closeDrawer(GravityCompat.END);

                return false;
            }
        });
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
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorThemeDark));
        }
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initFragment(){
        String fragmentName = "ProfileFragment";

        if(getIntent().hasExtra("fragment")){
            fragmentName = getIntent().getExtras().getString("fragment");
        }

        loadFragment(fragmentName);
    }

    private void loadFragment(String fragmentName){
        Fragment fragment;
        String title;

        if("ProfileFragment".equals(fragmentName)){
            fragment = ProfileFragment.newInstance();
            title = getString(R.string.user_fragment_dashboard_my_profile);
        }
        else if("CredentialsFragment".equals(fragmentName)){
            fragment = CredentialsFragment.newInstance();
            title = getString(R.string.user_fragment_dashboard_credentials);
        }
        else if("FavouritesFragment".equals(fragmentName)){
            fragment = FavouritesFragment.newInstance();
            title = getString(R.string.user_fragment_dashboard_my_favourites);
        }
        else if("CouponFragment".equals(fragmentName)){
            fragment = CouponFragment.newInstance();
            title = getString(R.string.user_fragment_dashboard_my_coupons);
        }
        else if("ChatFragment".equals(fragmentName)){
            fragment = ChatFragment.newInstance(null, null);
            title = getString(R.string.user_fragment_dashboard_chat_box);
        }
        else if("UploadFragment".equals(fragmentName)){
            fragment = UploadFragment.newInstance(null, null);
            title = getString(R.string.user_fragment_dashboard_image_video_upload);
        }
        else{
            fragment = DashboardFragment.newInstance();
            title = getString(R.string.user_fragment_dashboard);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .commit();
        setTitle(title);
    }

    public void setTitle(String title){
        ((TextView) toolbar.findViewById(R.id.title)).setText(title);
    }
}