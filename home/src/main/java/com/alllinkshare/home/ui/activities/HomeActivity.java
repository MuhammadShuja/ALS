package com.alllinkshare.home.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.alllinkshare.auth.events.LogoutEvent;
import com.alllinkshare.auth.events.LogoutListener;
import com.alllinkshare.auth.ui.activities.LoginActivity;
import com.alllinkshare.auth.ui.activities.RegisterActivity;
import com.alllinkshare.cardealing.ui.activities.CarDealingActivity;
import com.alllinkshare.catalog.ui.fragments.CategoriesBaseFragment;
import com.alllinkshare.catalog.ui.fragments.ListingCategoriesFragment;
import com.alllinkshare.catalog.ui.fragments.ListingDetailsFragment;
import com.alllinkshare.catalog.ui.fragments.ListingsFragment;
import com.alllinkshare.catalogshopping.ui.fragments.ShoppingProductDetailsFragment;
import com.alllinkshare.catalogshopping.ui.fragments.ShoppingProductsFragment;
import com.alllinkshare.chat.ChatActivity;
import com.alllinkshare.core.navigator.Coordinator;
import com.alllinkshare.core.navigator.CatalogNavigator;
import com.alllinkshare.core.navigator.CoreNavigator;
import com.alllinkshare.core.navigator.RestaurantNavigator;
import com.alllinkshare.core.navigator.SalesNavigator;
import com.alllinkshare.core.navigator.ShoppingNavigator;
import com.alllinkshare.home.api.API;
import com.alllinkshare.home.api.config.Listeners;
import com.alllinkshare.home.ui.fragments.HomeFragment;
import com.alllinkshare.home.ui.fragments.LandingFragment;
import com.alllinkshare.pushnotifications.PN;
import com.alllinkshare.qrscanner.ui.fragments.ScannerFragment;
import com.alllinkshare.restaurant.ui.fragments.FoodItemFragment;
import com.alllinkshare.restaurant.ui.fragments.FoodMenuFragment;
import com.alllinkshare.sales.cart.ui.fragments.CartFragment;
import com.alllinkshare.user.events.ProfileUpdateEvent;
import com.alllinkshare.user.events.ProfileUpdateListener;
import com.alllinkshare.user.models.User;
import com.alllinkshare.user.repos.UserRepository;
import com.alllinkshare.user.ui.activities.ProfileActivity;
import com.als.home.R;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    private static final float LOCATION_REFRESH_DISTANCE = 1000;
    private static final long LOCATION_REFRESH_TIME = 10000;
    private static final int REQUEST_CODE_PERMISSION = 111;

    public final int HEADER_LANDING = 1;
    public final int HEADER_HOME = 2;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    private BottomNavigationViewEx mBottomNavigationViewEx;

    private LocationManager mLocationManager;

    private LinearLayout headerLayout;

    private TextView notificationCount;
    private static int NOTIFICATION_COUNT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_home);

        initNavigators();
        initPushNotifications();
        initDrawer();
        initToolbar();
        initBottomNav();
//        initLocation();

//        initDemo();

        mBottomNavigationViewEx.setSelectedItemId(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (API.isAuthenticated()) {
            getMenuInflater().inflate(R.menu.home_menu, menu);

            final MenuItem menuItem = menu.findItem(R.id.chat);

            View actionView = menuItem.getActionView();
            notificationCount = actionView.findViewById(R.id.notification_count);

            setupNotificationBadge();

            actionView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onOptionsItemSelected(menuItem);
                }
            });
        }
        return true;
    }

//    @Override
//    public void onBackPressed() {
//        if(getSupportFragmentManager().getBackStackEntryCount() > 0)
//            getSupportFragmentManager().popBackStack();
//        else
//            loadFragment(HomeFragment.newInstance("ALSFragment"));
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION) {
            initLocation();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void setupNotificationBadge() {
        if (notificationCount != null) {
            if (NOTIFICATION_COUNT == 0) {
                if (notificationCount.getVisibility() != View.GONE) {
                    notificationCount.setVisibility(View.GONE);
                }
            } else {
                if (NOTIFICATION_COUNT > 99) {
                    notificationCount.setText("99+");
                } else {
                    notificationCount.setText(String.valueOf(NOTIFICATION_COUNT));
                }

                if (notificationCount.getVisibility() != View.VISIBLE) {
                    notificationCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START);
            else drawerLayout.openDrawer(GravityCompat.START);
        } else if (id == R.id.chat) {
            if (API.isAuthenticated()) {
                startActivity(new Intent(HomeActivity.this, ChatActivity.class));
            } else {
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void initDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        final LinearLayout navAction = headerView.findViewById(R.id.navigation_header_container);
        final TextView navActionTitle = headerView.findViewById(R.id.action);
        final CircleImageView navProfilePicture = (CircleImageView) headerView.findViewById(R.id.profile_picture);

        if (API.isAuthenticated()) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_nav_drawer_authenticated);
            UserRepository.getInstance().getProfile(new UserRepository.ProfileReadyListener() {
                @Override
                public void onProfileReady(User profile) {
                    Glide
                            .with(HomeActivity.this)
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
                    startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                }
            });

            new ProfileUpdateEvent().addOnUpdateListener(new ProfileUpdateListener() {
                @Override
                public void onUpdate(User profile) {
                    Glide
                            .with(HomeActivity.this)
                            .load(profile.getProfilePicture())
                            .into(navProfilePicture);
                    navActionTitle.setText(profile.getFullName());
                }
            });

            new LogoutEvent().addOnLogoutListener(new LogoutListener() {
                @Override
                public void onLogout() {
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.activity_nav_drawer);

                    Glide
                            .with(HomeActivity.this)
                            .load(getResources().getIdentifier("profile_picture_placeholder", "drawable", HomeActivity.this.getPackageName()))
                            .into(navProfilePicture);
                    navActionTitle.setText("Login");
                    navAction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        }
                    });
                }
            });
        } else {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_nav_drawer);

            Glide
                    .with(this)
                    .load(getResources().getIdentifier("profile_picture_placeholder", "drawable", this.getPackageName()))
                    .into(navProfilePicture);
            navActionTitle.setText("Login");
            navAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                }
            });
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int position = item.getItemId();

                if (position == R.id.nav_login) {
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                } else if (position == R.id.nav_register) {
                    startActivity(new Intent(HomeActivity.this, RegisterActivity.class));
                } else if (position == R.id.nav_logout) {
                    API.logout(new Listeners.LogoutListener() {
                        @Override
                        public void onSuccess(boolean success) {
                            navigationView.getMenu().clear();
                            navigationView.inflateMenu(R.menu.activity_nav_drawer);
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        }

                        @Override
                        public void onFailure(String error) {
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                        }
                    });
                } else if (position == R.id.nav_dashboard) {
                    Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                    intent.putExtra("fragment", "DashboardFragment");
                    startActivity(intent);
                } else if (position == R.id.nav_favourites) {
                    Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                    intent.putExtra("fragment", "FavouritesFragment");
                    startActivity(intent);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void initToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.bg));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorThemeDark));
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_hamburger);

        headerLayout = findViewById(R.id.header);
    }

    private void initBottomNav() {
        mBottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavigation);
        mBottomNavigationViewEx.enableAnimation(false);
        mBottomNavigationViewEx.enableShiftingMode(false);
        mBottomNavigationViewEx.enableItemShiftingMode(false);
        mBottomNavigationViewEx.setTextSize(12);

        mBottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                int position;
                if (id == R.id.bnav_home) {
                    position = 0;
                    Coordinator.getCoreNavigator().navigateToHome("ALSFragment");
                } else if (id == R.id.bnav_social) {
                    position = 1;
                } else if (id == R.id.bnav_scan) {
                    position = 2;
                    Coordinator.getCoreNavigator().navigateToScan();
                } else if (id == R.id.bnav_voice) {
                    position = 3;
                } else {
                    position = 0;
                }

                mBottomNavigationViewEx.setItemIconTintList(getResources().getColorStateList(R.color.grey));
                mBottomNavigationViewEx.setItemTextAppearanceInactive(getResources().getIdentifier("gold", "color", getPackageName()));
                mBottomNavigationViewEx.setIconTintList(position, getResources().getColorStateList(R.color.gold));
                mBottomNavigationViewEx.setTextTintList(position, getResources().getColorStateList(R.color.gold));
                return true;
            }
        });

        mBottomNavigationViewEx.setSelectedItemId(R.id.bnav_home);
    }

    public void updateHeader(int type) {
        switch (type) {
            case HEADER_HOME:
                headerLayout.findViewById(R.id.thumbnail).setVisibility(View.VISIBLE);
                headerLayout.findViewById(R.id.search_bar).setVisibility(View.VISIBLE);
                break;
            case HEADER_LANDING:
                headerLayout.findViewById(R.id.thumbnail).setVisibility(View.GONE);
                headerLayout.findViewById(R.id.search_bar).setVisibility(View.GONE);
                break;
        }
    }

    private void initNavigators() {
        initCoreNavigator();
        initCatalogNavigator();
        initShoppingNavigator();
        initRestaurantNavigator();
        initSalesNavigator();
    }

    private void initPushNotifications() {
        PN.init(new PN.OnTokenReadyListener() {
            @Override
            public void onTokenReady(String token) {
                if (API.isAuthenticated()) {
                    com.alllinkshare.user.api.API.updateFcmToken(token, new com.alllinkshare.user.api.config.Listeners.FcmTokenListener() {
                        @Override
                        public void onSuccess(String token) {
                            UserRepository.getInstance().setFcmToken(token);
                        }

                        @Override
                        public void onFailure(String error) {

                        }
                    });
                }
            }
        });
    }

    private void initCoreNavigator() {
        CoreNavigator coreNavigator = new CoreNavigator() {
            @Override
            public void navigateToHome(String fragmentToLoad) {
                Fragment fragment;
                if (API.isAuthenticated()) {
                    fragment = HomeFragment.newInstance("ALSFragment");
                } else {
                    updateHeader(HEADER_LANDING);
                    fragment = LandingFragment.newInstance();
                }
                loadCoreFragment(fragment);
            }

            @Override
            public void navigateToSocial() {

            }

            @Override
            public void navigateToScan() {
                ScannerFragment fragment = ScannerFragment.newInstance(R.id.nav_host_fragment);
                loadFragment(fragment);
            }

            @Override
            public void navigateToVoice() {

            }
        };
        Coordinator.setCoreNavigator(coreNavigator);
    }

    private void initCatalogNavigator() {
        CatalogNavigator catalogNavigator = new CatalogNavigator() {
            @Override
            public void navigateToCategory(int categoryId, boolean loadAll) {
                CategoriesBaseFragment fragment = CategoriesBaseFragment.newInstance(R.id.nav_host_fragment, categoryId, loadAll);
                loadFragment(fragment);
            }

            @Override
            public void navigateToListings(int categoryId) {
                ListingsFragment fragment = ListingsFragment.newInstance(categoryId);
                loadFragment(fragment);
            }

            @Override
            public void navigateToListing(int listingId, int categoryId) {
                ListingDetailsFragment fragment = ListingDetailsFragment.newInstance(listingId, categoryId);
                loadFragment(fragment);
            }

            @Override
            public void navigateToListingCategories(String type, int listingId, int categoryId) {
                ListingCategoriesFragment fragment = ListingCategoriesFragment.newInstance(type, listingId, categoryId);
                loadFragment(fragment);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {

            }
        };
        Coordinator.setCatalogNavigator(catalogNavigator);
    }

    private void initShoppingNavigator() {
        ShoppingNavigator shoppingNavigator = new ShoppingNavigator() {
            @Override
            public void navigateToProducts(int listingId, int shoppingCategoryId, int mainCategoryId) {
                ShoppingProductsFragment fragment = ShoppingProductsFragment.newInstance(listingId, shoppingCategoryId, mainCategoryId);
                loadFragment(fragment);
            }

            @Override
            public void navigateToProduct(int productId) {
                ShoppingProductDetailsFragment fragment = ShoppingProductDetailsFragment.newInstance(productId);
                loadFragment(fragment);
            }
        };
        Coordinator.setShoppingNavigator(shoppingNavigator);
    }

    private void initRestaurantNavigator() {
        RestaurantNavigator restaurantNavigator = new RestaurantNavigator() {
            @Override
            public void navigateToMenu(int listingId) {
                FoodMenuFragment fragment = FoodMenuFragment.newInstance(listingId);
                loadFragment(fragment);
            }

            @Override
            public void navigateToItem(int menuItemId) {
                FoodItemFragment fragment = FoodItemFragment.newInstance(menuItemId);
                loadFragment(fragment);
            }
        };
        Coordinator.setRestaurantNavigator(restaurantNavigator);
    }

    private void initSalesNavigator() {
        SalesNavigator salesNavigator = new SalesNavigator() {
            @Override
            public void navigateToCart(String type) {
                CartFragment fragment = CartFragment.newInstance(type);
                loadFragment(fragment);
            }

            @Override
            public void navigateToCheckout() {

            }
        };
        Coordinator.setSalesNavigator(salesNavigator);
    }

    private void initLocation() {
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] PERMISSIONS = {
                    Manifest.permission.ACCESS_FINE_LOCATION
            };
            ActivityCompat.requestPermissions(HomeActivity.this, PERMISSIONS, REQUEST_CODE_PERMISSION);
            return;
        }
//        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
//                LOCATION_REFRESH_DISTANCE, new LocationListener() {
//                    @Override
//                    public void onLocationChanged(final Location location) {
////                        location.get
//                    }
//                });
    }

    private void initDemo(){
//        ShoppingProductsFragment fragment = ShoppingProductsFragment.newInstance(87, 14, 4591);
//        ShoppingProductDetailsFragment fragment = ShoppingProductDetailsFragment.newInstance(6008967);
//        ShoppingProductDetailsFragment fragment = ShoppingProductDetailsFragment.newInstance(3798060);
//        ShoppingProductDetailsFragment fragment = ShoppingProductDetailsFragment.newInstance(63805244);
        FoodMenuFragment fragment = FoodMenuFragment.newInstance(87);
        loadFragment(fragment);

//        startActivity(new Intent(HomeActivity.this, CarDealingActivity.class));
    }

    private void loadCoreFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment,
                        fragment)
                .commit();
    }

    private void loadFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.nav_host_fragment,
                        fragment)
                .addToBackStack(null)
                .commit();
    }
}