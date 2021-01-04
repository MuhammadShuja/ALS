package com.alllinkshare.cardealing.ui.activities;

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
import android.widget.TextView;
import android.widget.Toast;

import com.alllinkshare.auth.events.LogoutEvent;
import com.alllinkshare.auth.events.LogoutListener;
import com.alllinkshare.auth.ui.activities.LoginActivity;
import com.alllinkshare.cardealing.R;
import com.alllinkshare.cardealing.api.API;
import com.alllinkshare.cardealing.ui.fragments.CarDealingHomeFragment;
import com.alllinkshare.cardealing.ui.fragments.CarDealingListingFragment;
import com.alllinkshare.user.events.ProfileUpdateEvent;
import com.alllinkshare.user.models.User;
import com.alllinkshare.user.repos.UserRepository;
import com.alllinkshare.user.ui.activities.ProfileActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class CarDealingActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_car_dealing);

        initDrawer();
        initToolbar();

        loadFragment("HomeFragment");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.car_dealing_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.car_nav){
            drawerLayout.openDrawer(GravityCompat.END);
        }

        return super.onOptionsItemSelected(item);
    }

    private void initDrawer(){
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView = findViewById(R.id.car_dealer_nav_view);
        View headerView =  navigationView.getHeaderView(0);

        final LinearLayout navAction = headerView.findViewById(R.id.navigation_header_container);
        final TextView navActionTitle = headerView.findViewById(R.id.action);
        final CircleImageView navProfilePicture = (CircleImageView) headerView.findViewById(R.id.profile_picture);

        if(API.isAuthenticated()){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.car_dealing_nav_drawer_authenticated);
            UserRepository.getInstance().getProfile(new UserRepository.ProfileReadyListener() {
                @Override
                public void onProfileReady(User profile) {
                    Glide
                            .with(CarDealingActivity.this)
                            .load(profile.getProfilePicture())
                            .into(navProfilePicture);
                    navActionTitle.setText(profile.getFullName());
                }

                @Override
                public void onFailure(String error) {

                }
            });
            navAction.setOnClickListener(view -> startActivity(new Intent(CarDealingActivity.this, ProfileActivity.class)));

            new ProfileUpdateEvent().addOnUpdateListener(profile -> {
                Glide
                        .with(CarDealingActivity.this)
                        .load(profile.getProfilePicture())
                        .into(navProfilePicture);
                navActionTitle.setText(profile.getFullName());
            });

            new LogoutEvent().addOnLogoutListener(new LogoutListener() {
                @Override
                public void onLogout() {
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.car_dealing_nav_drawer);

                    Glide
                            .with(CarDealingActivity.this)
                            .load(getResources().getIdentifier("profile_picture_placeholder", "drawable", CarDealingActivity.this.getPackageName()))
                            .into(navProfilePicture);
                    navActionTitle.setText("Login");
                    navAction.setOnClickListener(view -> startActivity(new Intent(CarDealingActivity.this, LoginActivity.class)));
                }
            });
        }
        else{
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.car_dealing_nav_drawer);

            Glide
                    .with(this)
                    .load(getResources().getIdentifier("profile_picture_placeholder", "drawable", this.getPackageName()))
                    .into(navProfilePicture);
            navActionTitle.setText("Login");
            navAction.setOnClickListener(view -> startActivity(new Intent(CarDealingActivity.this, LoginActivity.class)));
        }

        navigationView.setNavigationItemSelectedListener(item -> {
            int position = item.getItemId();

            if(position == R.id.nav_car_home){
                loadFragment("HomeFragment");
            }
            else if(position == R.id.nav_car_login){
                startActivity(new Intent(CarDealingActivity.this, LoginActivity.class));
            }
            else if(position == R.id.nav_car_listing){
                loadFragment("ListingsFragment");
            }
            else if(position == R.id.nav_car_logout){
                com.alllinkshare.auth.api.API.logout(new com.alllinkshare.auth.api.config.Listeners.AuthListener() {
                    @Override
                    public void onSuccess(String message) {
                        navigationView.getMenu().clear();
                        navigationView.inflateMenu(R.menu.car_dealing_nav_drawer);
                        startActivity(new Intent(CarDealingActivity.this, LoginActivity.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                    }
                });
            }
            drawerLayout.closeDrawer(GravityCompat.END);
            return false;
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
            getWindow().setStatusBarColor(getResources().getColor(R.color.bg));
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void loadFragment(String fragmentName){
        Fragment fragment;

        if("HomeFragment".equals(fragmentName)){
            fragment = CarDealingHomeFragment.newInstance();
        }
        else if("ListingsFragment".equals(fragmentName)){
            fragment = CarDealingListingFragment.newInstance();
        }
        else{
            fragment = CarDealingHomeFragment.newInstance();
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.car_nav_host, fragment)
                .addToBackStack(null)
                .commit();
    }
}