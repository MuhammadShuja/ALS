package com.alllinkshare.shopping.ui.activities;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.alllinkshare.shopping.R;
import com.alllinkshare.shopping.ui.fragments.CategoryFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class ShoppingActivity extends AppCompatActivity {

    private BottomNavigationViewEx mBottomNavigationViewEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_shopping);

        initToolbar();
        initBottomNav();
//        loadFragment();
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
//            getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.midnight_blue));
        }
        else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.midnight_blue));
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initBottomNav(){
        mBottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavigation);
        mBottomNavigationViewEx.enableAnimation(false);
        mBottomNavigationViewEx.enableShiftingMode(false);
        mBottomNavigationViewEx.enableItemShiftingMode(false);
        mBottomNavigationViewEx.setTextSize(12);

        mBottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                int position = 0;
                if (id == R.id.bnav_home) {
                    position = 0;
//                    fragmentClass = HomeFragment.class;
//                    fragmentTitle = "Home";
                } else if (id == R.id.bnav_category) {
                    position = 1;
//                    fragmentClass = CategoriesFragment.class;
//                    fragmentTitle = "Catalog";
                } else if (id == R.id.bnav_cart) {
                    position = 2;
//                    fragmentClass = CartFragment.class;
//                    fragmentTitle = "My Cart (" + Cart.count() + ")";
                } else if (id == R.id.bnav_account) {
                    position = 3;
//                    fragmentClass = AccountFragment.class;
//                    fragmentTitle = "Account";
                }
//                loadFragment();

                mBottomNavigationViewEx.setItemIconTintList(getResources().getColorStateList(R.color.grey));
                mBottomNavigationViewEx.setItemTextAppearanceInactive(getResources().getIdentifier("gold", "color", getPackageName()));
                mBottomNavigationViewEx.setIconTintList(position, getResources().getColorStateList(R.color.gold));
                mBottomNavigationViewEx.setTextTintList(position, getResources().getColorStateList(R.color.gold));
                return true;
            }
        });

        mBottomNavigationViewEx.setSelectedItemId(R.id.bnav_home);
    }

    private void loadFragment(){
        int productID = getIntent().getExtras().getInt("product_id");
        boolean fullDemo = false;
        CategoryFragment categoryFragment = CategoryFragment.newInstance(productID, false
        );
        getSupportFragmentManager().beginTransaction()
                .add(R.id.nav_host_fragment, categoryFragment)
                .commit();
    }
}