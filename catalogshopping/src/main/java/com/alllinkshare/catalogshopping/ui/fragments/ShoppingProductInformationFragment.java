package com.alllinkshare.catalogshopping.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alllinkshare.catalogshopping.R;
import com.alllinkshare.catalogshopping.models.DeliveryMethods;
import com.cunoraz.tagview.Tag;
import com.cunoraz.tagview.TagView;

import java.util.ArrayList;
import java.util.List;

public class ShoppingProductInformationFragment extends Fragment {

    private static final String MANUFACTURER = "manufacturer";
    private static final String BRAND = "brand";
    private static final String MODEL = "model";
    private static final String COUNTRY = "country";
    private static final String SERVICE = "service";
    private static final String DELIVERY = "delivery";

    private String manufacturer;
    private String model;
    private String brand;
    private String country;
    private String service;
    private DeliveryMethods deliveryMethods;

    private View rootView;

    public ShoppingProductInformationFragment() {
        // Required empty public constructor
    }

    public static ShoppingProductInformationFragment newInstance(
            String manufacturer, String brand, String model, String country,
            String service, DeliveryMethods deliveryMethods) {
        ShoppingProductInformationFragment fragment = new ShoppingProductInformationFragment();
        Bundle args = new Bundle();
        args.putString(MANUFACTURER, manufacturer);
        args.putString(BRAND, brand);
        args.putString(MODEL, model);
        args.putString(COUNTRY, country);
        args.putString(SERVICE, service);
        args.putParcelable(DELIVERY, deliveryMethods);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            manufacturer = getArguments().getString(MANUFACTURER);
            brand = getArguments().getString(BRAND);
            model = getArguments().getString(MODEL);
            country = getArguments().getString(COUNTRY);
            service = getArguments().getString(SERVICE);
            deliveryMethods = getArguments().getParcelable(DELIVERY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_shopping_product_information, container, false);

        initInfo();

        return rootView;
    }

    private void initInfo(){
        ((TextView) rootView.findViewById(R.id.manufacturer)).setText(manufacturer);
        ((TextView) rootView.findViewById(R.id.brand_name)).setText(brand);
        ((TextView) rootView.findViewById(R.id.model_number)).setText(model);
        ((TextView) rootView.findViewById(R.id.origin_country)).setText(country);
        ((TextView) rootView.findViewById(R.id.service_type)).setText(service);
        ((TextView) rootView.findViewById(R.id.delivery_type)).setText(service);
    }
}