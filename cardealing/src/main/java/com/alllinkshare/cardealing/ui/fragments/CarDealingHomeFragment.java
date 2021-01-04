package com.alllinkshare.cardealing.ui.fragments;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.alllinkshare.cardealing.R;
import com.alllinkshare.cardealing.api.API;
import com.alllinkshare.cardealing.api.config.Listeners;
import com.alllinkshare.cardealing.models.Car;
import com.alllinkshare.cardealing.ui.adapters.FeaturedCarsAdapter;
import com.alllinkshare.cardealing.ui.adapters.SpinnerAdapter;
import com.alllinkshare.core.utils.GlideOptions;
import com.alllinkshare.user.models.SpinnerItem;
import com.bumptech.glide.Glide;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CarDealingHomeFragment extends Fragment {

    private String[] makeArray = {"Make", "BMW", "Honda", "Hyundai", "Nissan", "Mercedes Benz"};
    private String[] modelArray = {"Model", "3-Series", "Carrera", "GT-R", "Cayenne", "Mazda6", "Macan"};
    private String[] yearArray = {"Year", "2010", "2011", "2012", "2013", "2014", "2015", "2016"};
    private String[] bodyArray = {"Body style", "2dr car", "4dr car", "Convertible", "Sedan", "Sports utility"};
    private String[] statusArray = {"Vehicle status", "All conditions", "Brand new", "Slightly used", "Used"};

    private float priceMinimum = 0.0f, priceMaximum = 10000000.0f;

    private FeaturedCarsAdapter featuredCarsAdapter;

    private static int currentPage = 0;
    private int NUM_PAGES = 0;

    private View rootView;

    public CarDealingHomeFragment() {
        // Required empty public constructor
    }

    public static CarDealingHomeFragment newInstance() {
        CarDealingHomeFragment fragment = new CarDealingHomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_car_dealing_home, container, false);

        initSlider();
        initSearchForm();
        initFeaturedCars();

        return rootView;
    }

    private void initSlider(){
        Glide
                .with(getContext())
                .load("https://alllinkshare.com/frontend-assets/autoMobileSite/revolution/assets/3176d-road-bg.jpg")
                .thumbnail(0.25f)
                .apply(GlideOptions.getDefault())
                .into((ImageView) rootView.findViewById(R.id.slider));

        Glide
                .with(getContext())
                .load("https://alllinkshare.com/frontend-assets/autoMobileSite/images/bg/02.jpg")
                .thumbnail(0.25f)
                .apply(GlideOptions.getDefault())
                .into((ImageView) rootView.findViewById(R.id.background));
    }

    private void initSearchForm(){
        AppCompatSpinner modelSpinner = rootView.findViewById(R.id.model);
        AppCompatSpinner makeSpinner = rootView.findViewById(R.id.make);
        AppCompatSpinner yearSpinner = rootView.findViewById(R.id.year);
        AppCompatSpinner bodySpinner = rootView.findViewById(R.id.body_style);
        AppCompatSpinner statusSpinner = rootView.findViewById(R.id.status);

        modelSpinner.setAdapter(getSpinnerAdapter(modelArray));
        makeSpinner.setAdapter(getSpinnerAdapter(makeArray));
        yearSpinner.setAdapter(getSpinnerAdapter(yearArray));
        bodySpinner.setAdapter(getSpinnerAdapter(bodyArray));
        statusSpinner.setAdapter(getSpinnerAdapter(statusArray));

        addDropdownWidthListener(modelSpinner, modelSpinner);
        addDropdownWidthListener(makeSpinner, makeSpinner);
        addDropdownWidthListener(yearSpinner, yearSpinner);
        addDropdownWidthListener(bodySpinner, bodySpinner);
        addDropdownWidthListener(statusSpinner, statusSpinner);

        RangeSlider priceRange = rootView.findViewById(R.id.price_range);
        TextView priceRangeMinimum = rootView.findViewById(R.id.price_range_minimum);
        TextView priceRangeMaximum = rootView.findViewById(R.id.price_range_maximum);

        priceRange.setValueFrom(priceMinimum);
        priceRange.setValueTo(priceMaximum);
        priceRange.setValues(priceMinimum, priceMaximum);
        priceRange.setStepSize(100.0f);
        priceRange.setLabelBehavior(LabelFormatter.LABEL_GONE);
        priceRangeMinimum.setText(getFormattedAmount(priceMinimum));
        priceRangeMaximum.setText(getFormattedAmount(priceMaximum));

        priceRange.setLabelFormatter(value -> getString(R.string.currency_symbol)+value);
        priceRange.addOnChangeListener((slider, value, fromUser) -> {
            List<Float> values = slider.getValues();
            priceMinimum = Collections.min(values);
            priceMaximum = Collections.max(values);
            priceRangeMinimum.setText(getFormattedAmount(priceMinimum));
            priceRangeMaximum.setText(getFormattedAmount(priceMaximum));
        });

        rootView.findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initFeaturedCars(){
        featuredCarsAdapter = new FeaturedCarsAdapter(getContext(), new ArrayList<Car>(), car -> {
            Toast.makeText(getContext(), car.getName(), Toast.LENGTH_LONG).show();
        });

        RecyclerView rvItems = rootView.findViewById(R.id.rv_items);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(featuredCarsAdapter);
        new PagerSnapHelper().attachToRecyclerView(rvItems);
        rvItems.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                currentPage = layoutManager.findFirstVisibleItemPosition();
            }
        });

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                rvItems.post(() -> rvItems.smoothScrollToPosition(currentPage++));
            }
        }, 3000, 3000);

        API.featured(new Listeners.FeaturedCarsListener() {
            @Override
            public void onSuccess(List<Car> cars) {
                rootView.findViewById(R.id.loading_wrapper).setVisibility(View.GONE);
                featuredCarsAdapter.setData(cars);
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private SpinnerAdapter getSpinnerAdapter(String[] data){
        List<SpinnerItem> items = new ArrayList<>();
        for(String item : data){
            items.add(new SpinnerItem(0, item));
        }
        return new SpinnerAdapter(getContext(), items);
    }

    private String getFormattedAmount(Float amount){
        NumberFormat format = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol(getString(R.string.currency_symbol));
        decimalFormatSymbols.setGroupingSeparator(',');
        decimalFormatSymbols.setMonetaryDecimalSeparator('.');
        ((DecimalFormat) format).setDecimalFormatSymbols(decimalFormatSymbols);
        return format.format(amount);
    }

    private void addDropdownWidthListener(View view, AppCompatSpinner spinner){
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                spinner.setDropDownWidth(view.getWidth());
            }
        });
    }
}