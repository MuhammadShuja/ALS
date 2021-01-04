package com.alllinkshare.user.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.alllinkshare.user.R;
import com.alllinkshare.user.models.Order;
import com.alllinkshare.user.ui.adapters.OrdersAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    private OrdersAdapter ordersAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private int currentPage = 0;
    private int lastPage = 1;
    private int totalItems = 0;
    private boolean isLoading = false;

    private ProgressBar progressBar;
    private LinearLayout sectionEmpty, sectionData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_orders);

        initToolbar();
        initSwipeRefreshLayout();
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
//        mSwipeRefreshLayout = findViewById(R.id.refresh_layout);
//        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.white);
//        int indicatorColorArray[] = {R.color.emerald, R.color.gold};
//        mSwipeRefreshLayout.setColorSchemeResources(indicatorColorArray);
//
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                currentPage = 0;
//                lastPage = 1;
//                totalItems = 0;
//                isLoading = false;
//                ordersAdapter.clearData();
//                loadData();
//            }
//        });
    }

    private void initOrders(){
        List<Order> items = new ArrayList<>();
        items.add(new Order(1, "99145", "Pending", 4000, "27th March, 2020"));
        items.add(new Order(1, "99475", "Confirmed", 8950, "23rd Feb, 2020"));
        items.add(new Order(1, "99503", "Delivered", 6750, "19th Dec, 2019"));
        items.add(new Order(1, "99871", "Delivered", 3500, "15th Nov, 2019"));
        items.add(new Order(1, "99145", "Pending", 4000, "27th March, 2020"));
        items.add(new Order(1, "99475", "Confirmed", 8950, "23rd Feb, 2020"));
        items.add(new Order(1, "99503", "Delivered", 6750, "19th Dec, 2019"));
        items.add(new Order(1, "99871", "Delivered", 3500, "15th Nov, 2019"));
        items.add(new Order(1, "99145", "Pending", 4000, "27th March, 2020"));
        items.add(new Order(1, "99475", "Confirmed", 8950, "23rd Feb, 2020"));
        items.add(new Order(1, "99503", "Delivered", 6750, "19th Dec, 2019"));
        items.add(new Order(1, "99871", "Delivered", 3500, "15th Nov, 2019"));
        ordersAdapter = new OrdersAdapter(this, items, new OrdersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Order order) {
            }
        });

        final RecyclerView rvOrders = findViewById(R.id.rv_items);
        final GridLayoutManager layoutManager = new GridLayoutManager(OrdersActivity.this, 1);
        rvOrders.setLayoutManager(layoutManager);
        rvOrders.setAdapter(ordersAdapter);

        progressBar = findViewById(R.id.progress_bar);
        sectionEmpty = findViewById(R.id.section_empty);
        sectionData = findViewById(R.id.data_wrapper);
    }

    private void loadData() {
    }
}