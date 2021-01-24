package com.alllinkshare.user.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alllinkshare.core.utils.GifImageView;
import com.alllinkshare.core.utils.SPM;
import com.alllinkshare.liveTrack.tracking.Api.Api;
import com.alllinkshare.liveTrack.tracking.Api.RetrofitInstance;
import com.alllinkshare.user.R;
import com.alllinkshare.user.api.API;
import com.alllinkshare.user.api.config.Listeners;
import com.alllinkshare.user.models.Order;
import com.alllinkshare.user.ui.adapters.OrdersAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersActivity extends AppCompatActivity {

    private OrdersAdapter ordersAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private int currentPage = 0;
    private int lastPage = 1;
    private int totalItems = 0;
    private boolean isLoading = false;

    private String filter = "";

    private ProgressBar progressBar;

    private LinearLayout loadingWrapper, emptyWrapper, contentWrapper;

    public static String orderStatus = "";
    String orderId = "";
    String riderId = "";
    int orderid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_orders);

        initToolbar();
        initSwipeRefreshLayout();
        initTabs();
        initOrders();
        loadData();
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
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        }
        else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorThemeDark));
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("My orders");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    private void initSwipeRefreshLayout(){
        mSwipeRefreshLayout = findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.white);
        int[] indicatorColorArray = {R.color.emerald, R.color.gold};
        mSwipeRefreshLayout.setColorSchemeResources(indicatorColorArray);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadData();
            }
        });
    }

    private void initTabs(){
        TabLayout tabLayout = findViewById(R.id.tabs);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                switch (position){
                    case 0:
                        if(!filter.equals("")){
                            filter = "";
                            reloadData();
                        }
                        break;

                    case 1:
                        if(!filter.equals("Pending")){
                            filter = "Pending";
                            reloadData();
                        }
                        break;

                    case 2:
                        if(!filter.equals("Active")){
                            filter = "Active";
                            reloadData();
                        }
                        break;

                    case 3:
                        if(!filter.equals("Completed")){
                            filter = "Completed";
                            reloadData();
                        }
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initOrders(){
        ordersAdapter = new OrdersAdapter(this, new ArrayList<Order>(), new OrdersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Order order) {
                orderid = order.getId();

                showOrderTrackDetails(String.valueOf(order.getId()));
            }
        });

        final RecyclerView rvItems = findViewById(R.id.rv_items);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        DividerItemDecoration verticalDivider = new DividerItemDecoration(
                Objects.requireNonNull(this), DividerItemDecoration.VERTICAL);

        verticalDivider.setDrawable(
                Objects.requireNonNull(
                        ContextCompat.getDrawable(this, R.drawable.divider)));
        rvItems.addItemDecoration(verticalDivider);
        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(ordersAdapter);
        rvItems.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    loadData();
                }
            }
        });

        loadingWrapper = findViewById(R.id.loading_wrapper);
        emptyWrapper = findViewById(R.id.empty_wrapper);
        contentWrapper = findViewById(R.id.content_wrapper);

        GifImageView gifImageView = findViewById(R.id.gif_image_view);
        gifImageView.setGifImageResource(R.drawable.empty);
    }

    private void loadData() {
        if(isLoading) return;

        currentPage++;
        if(currentPage > lastPage) return;

        isLoading = true;
        loadingWrapper.setVisibility(View.VISIBLE);

        API.orders(currentPage, filter, new Listeners.OrdersListener() {
            @Override
            public void onSuccess(List<Order> orders, int currentPageNumber, int lastPageNumber, int totalProducts) {
                currentPage = currentPageNumber;
                lastPage = lastPageNumber;
                totalItems = totalProducts;

                ordersAdapter.addData(orders);

                loadingWrapper.setVisibility(View.GONE);
                isLoading = false;
                if(mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(false);

                if(totalItems < 1){
                    contentWrapper.setVisibility(View.GONE);
                    emptyWrapper.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(String error) {
                loadingWrapper.setVisibility(View.GONE);
                isLoading = false;
                Toast.makeText(getApplicationContext(), "Error: "+error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void reloadData(){
        currentPage = 0;
        lastPage = 1;
        totalItems = 0;
        isLoading = false;
        ordersAdapter.clearData();
        loadData();
    }

    private void showOrderTrackDetails(String orderId) {

        Api service = RetrofitInstance.getRetrofitInstance().create(Api.class);
        String bear = "Bearer";

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

                        orderStatus = new JSONObject(json1).getString("status");
//                        hasssan changes here
                        if (orderStatus.equals("Active")) {

                            Intent intent = null;
                            try {
                                intent = new Intent(getApplicationContext(),
                                        Class.forName("com.alllinkshare.liveTrack.tracking.ui.TrackingActivity"));
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            intent.putExtra("orderid", orderid);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "order already completed", Toast.LENGTH_SHORT).show();

                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Your order has not been picked up yet", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("ddddddd", t.getMessage());
            }
        });
    }
}