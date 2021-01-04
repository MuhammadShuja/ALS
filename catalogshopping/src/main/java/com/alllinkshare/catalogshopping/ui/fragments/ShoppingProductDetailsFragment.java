package com.alllinkshare.catalogshopping.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alllinkshare.catalogshopping.R;
import com.alllinkshare.catalogshopping.api.API;
import com.alllinkshare.catalogshopping.api.config.Listeners;
import com.alllinkshare.catalogshopping.models.DeliveryMethods;
import com.alllinkshare.catalogshopping.models.Product;
import com.alllinkshare.catalogshopping.models.ProductColor;
import com.alllinkshare.catalogshopping.models.ProductSize;
import com.alllinkshare.catalogshopping.models.SpinnerItem;
import com.alllinkshare.catalogshopping.ui.adapters.SpinnerAdapter;
import com.alllinkshare.core.navigator.Keys;
import com.alllinkshare.core.utils.GlideOptions;
import com.alllinkshare.gallery.models.Image;
import com.alllinkshare.gallery.ui.fragments.GalleryGridFragment;
import com.alllinkshare.reviews.ui.fragments.ReviewsFragment;
import com.alllinkshare.sales.Sales;
import com.alllinkshare.sales.cart.models.Cart;
import com.alllinkshare.sales.cart.models.CartItem;
import com.alllinkshare.sales.cart.ui.fragments.CartBarFragment;
import com.alllinkshare.sales.cart.ui.activities.CheckoutActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShoppingProductDetailsFragment extends Fragment {

    private int productId;

    private ImageView thumbnail, btnCartMinus, btnCartPlus;
    private FrameLayout serviceTypesWrapper, deliveryMethodsWrapper, priceWrapper, usdPriceWrapper;
    private LinearLayout loadingWrapper, contentWrapper, cartWrapper, actionsWrapper;
    private Button btnAddToCart, btnBuyNow, btnComparison, btnAlsMall;
    private EditText cartCounter;
    private TextView name, price, usdPrice;

    private Product shoppingProduct = null;
    private ProductColor selectedColor = null;
    private ProductSize selectedSize = null;

    private View rootView;

    public ShoppingProductDetailsFragment() {
        // Required empty public constructor
    }

    public static ShoppingProductDetailsFragment newInstance(int productId) {
        ShoppingProductDetailsFragment fragment = new ShoppingProductDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(Keys.ITEM_ID, productId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productId = getArguments().getInt(Keys.ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_shopping_product_details, container, false);

        initCartBar();
        initProduct();
        loadProduct();
        initGuide();

        return rootView;
    }

    private void initCartBar(){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.product_cart_bar_host, CartBarFragment.newInstance(Cart.TYPE_SHOPPING))
                .commit();
    }

    private void initProduct(){
        initShare();
        thumbnail = rootView.findViewById(R.id.thumbnail);

        serviceTypesWrapper = rootView.findViewById(R.id.service_tags_wrapper);
        deliveryMethodsWrapper = rootView.findViewById(R.id.delivery_tags_wrapper);
        priceWrapper = rootView.findViewById(R.id.price_wrapper);
        usdPriceWrapper = rootView.findViewById(R.id.usd_price_wrapper);
        loadingWrapper = rootView.findViewById(R.id.loading_wrapper);
        contentWrapper = rootView.findViewById(R.id.content_wrapper);

        name = rootView.findViewById(R.id.name);
        price = rootView.findViewById(R.id.price);
        usdPrice = rootView.findViewById(R.id.usd_price);
    }

    private void loadProduct(){
        API.product(productId, new Listeners.ProductListener() {
            @Override
            public void onSuccess(Product product) {
                if(getContext() != null){
                    shoppingProduct = product;
                    Glide
                            .with(getContext())
                            .load(product.getThumbnail())
                            .thumbnail(0.25f)
                            .apply(GlideOptions.getProductOptions())
                            .into(thumbnail);

                    name.setText(product.getName());
                    price.setText("₩"+product.getPrice());
                    usdPrice.setText("USD"+product.getPrice()/1184);

                    initCart(product.getSiteName(), product.getColors());
                    initBuyNow(product.getSiteName(), product.getClickUrl());
                    initComparison(product.getId(), product.getSiteName());
                    initServiceTypes(product.getServiceType());
                    initDeliveryMethods(product.getDeliveryMethods());
                    initInfoTabs(product);

                    contentWrapper.setVisibility(View.VISIBLE);
                    loadingWrapper.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initCart(final String siteName, final List<ProductColor> colors){
        cartWrapper = rootView.findViewById(R.id.cart_wrapper);
        if(siteName.equals("nshop") || siteName.equals("shopstyle")){
            cartWrapper.setVisibility(View.GONE);
        }
        else {
            cartWrapper.setVisibility(View.VISIBLE);
            btnCartMinus = rootView.findViewById(R.id.btn_minus);
            btnCartPlus = rootView.findViewById(R.id.btn_plus);
            cartCounter = rootView.findViewById(R.id.counter);
            btnAddToCart = rootView.findViewById(R.id.btn_add_to_cart);

            final LinearLayout colorWrapper = rootView.findViewById(R.id.color_wrapper);
            final LinearLayout sizeWrapper = rootView.findViewById(R.id.size_wrapper);

            AppCompatSpinner colorSpinner = rootView.findViewById(R.id.colors);
            final AppCompatSpinner sizeSpinner = rootView.findViewById(R.id.sizes);

            if (colors.size() > 0
                    && !siteName.equals("nshop")
                    && !siteName.equals("shopstyle")) {
                List<SpinnerItem> items = new ArrayList<>();
                for (ProductColor color : colors) {
                    items.add(new SpinnerItem(color.getId(), color.getName(), color.getImage()));
                }

                final SpinnerAdapter colorSpinnerAdapter = new SpinnerAdapter(getContext(), items);
                colorSpinner.setAdapter(colorSpinnerAdapter);
                colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        ProductColor color = colors.get(i);
                        selectedColor = color;
                        if (color.getSizes().size() > 0) {
                            final List<SpinnerItem> sizes = new ArrayList<>();
                            for (ProductSize size : color.getSizes()) {
                                sizes.add(new SpinnerItem(size.getId(), size.getName(), null));
                            }

                            final SpinnerAdapter sizeSpinnerAdapter = new SpinnerAdapter(getContext(), sizes);
                            sizeSpinner.setAdapter(sizeSpinnerAdapter);
                            sizeWrapper.setVisibility(View.VISIBLE);
                            sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    selectedSize = selectedColor.getSizes().get(position);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                            sizeWrapper.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                colorWrapper.setVisibility(View.VISIBLE);
            } else {
                colorWrapper.setVisibility(View.GONE);
            }

            btnCartPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = 0;
                    if (!cartCounter.getText().toString().isEmpty()
                            && TextUtils.isDigitsOnly(cartCounter.getText().toString())) {
                        int t = Integer.parseInt(cartCounter.getText().toString());
                        if (t + 1 > -1) {
                            count = t + 1;
                        }
                    }

                    cartCounter.setText(String.valueOf(count));
                }
            });

            btnCartMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = 0;
                    if (!cartCounter.getText().toString().isEmpty()
                            && TextUtils.isDigitsOnly(cartCounter.getText().toString())) {
                        int t = Integer.parseInt(cartCounter.getText().toString());
                        if (t - 1 > -1) {
                            count = t - 1;
                        }
                    }

                    cartCounter.setText(String.valueOf(count));
                }
            });

            cartCounter.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!TextUtils.isDigitsOnly(s.toString())
                            || s.toString().isEmpty()) {
                        cartCounter.setText("0");
                    }
                }
            });

            btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity = Integer.parseInt(cartCounter.getText().toString());
                    CartItem item = Sales.getShoppingCart().findItem(shoppingProduct.getUuid());
                    if (item == null) {
                        if (quantity > 0) {
                            item = Sales.getShoppingCart().addItem(
                                    shoppingProduct.getUuid(),
                                    shoppingProduct.getListingId(),
                                    shoppingProduct.getId(),
                                    shoppingProduct.getName(),
                                    shoppingProduct.getThumbnail(),
                                    shoppingProduct.getPrice(),
                                    quantity
                            );
                            item.setColor(selectedColor.getName());
                            item.setSize(selectedSize.getName());
                        }
                    } else {
                        item.setQuantity(quantity);
                        item.setColor(selectedColor.getName());
                        item.setSize(selectedSize.getName());
                    }
                }
            });
        }
    }

    private void initBuyNow(final String siteName, final String clickUrl){
        btnBuyNow = rootView.findViewById(R.id.btn_buy_now);
        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(siteName.equals("shopstyle") || siteName.equals("nshop")){
                    if(clickUrl != null){
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(clickUrl));
                        Objects.requireNonNull(getActivity()).startActivity(i);
                    }
                    else{
                        Toast.makeText(getContext(), "Link broken", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    CartItem item = Sales.getShoppingCart().findItem(shoppingProduct.getUuid());
                    if (item == null) {
                        item = Sales.getShoppingCart().addItem(
                                shoppingProduct.getUuid(),
                                shoppingProduct.getListingId(),
                                shoppingProduct.getId(),
                                shoppingProduct.getName(),
                                shoppingProduct.getThumbnail(),
                                shoppingProduct.getPrice(),
                                1
                        );
                        item.setColor(selectedColor.getName());
                        item.setSize(selectedSize.getName());
                    }
                    Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                    intent.putExtra(Keys.TYPE, Cart.TYPE_SHOPPING);
                    if(Sales.getShoppingCart().getTotalPrice() >= 20000){
                        Objects.requireNonNull(getActivity()).startActivity(intent);
                    }
                    else{
                        Toast.makeText(getContext(), "Minimum order: ₩ 20,000", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void initComparison(final int productId, final String sitename){
        btnComparison = rootView.findViewById(R.id.btn_comparison);
        if(sitename.equals("nshop")){
            btnComparison.setVisibility(View.VISIBLE);
            btnComparison.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShoppingProductComparisonFragment.newInstance(productId)
                            .show(getParentFragmentManager(), "dialog");
                }
            });
        }
        else{
            btnComparison.setVisibility(View.GONE);
        }
    }

    private void initShare(){
        rootView.findViewById(R.id.share_sns).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://talksns.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                Objects.requireNonNull(getActivity()).startActivity(i);
            }
        });

        rootView.findViewById(R.id.share_fb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://facebook.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                Objects.requireNonNull(getActivity()).startActivity(i);
            }
        });

        rootView.findViewById(R.id.share_twitter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://twitter.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                Objects.requireNonNull(getActivity()).startActivity(i);
            }
        });

        rootView.findViewById(R.id.share_li).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://linkedin.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                Objects.requireNonNull(getActivity()).startActivity(i);
            }
        });
    }

    private void initServiceTypes(String serviceTypes){
        String[] types = serviceTypes.split(",");

        if(types.length > 0) {
            LinearLayout ll = new LinearLayout(getContext());
            ll.setLayoutParams(new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    Gravity.END
            ));
            ll.setOrientation(LinearLayout.HORIZONTAL);
            serviceTypesWrapper.addView(ll);
            for (String method : types) {
                TextView tv = new TextView(getContext());
                tv.setText(method);
                tv.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                tv.setTextSize(1,12);
                tv.setPadding(4, 4, 4, 4);
                tv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_tag));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(16, 0, 0, 0);

                tv.setLayoutParams(params);
                ll.addView(tv);
            }
            serviceTypesWrapper.setVisibility(View.VISIBLE);
        }
        else{
            serviceTypesWrapper.setVisibility(View.GONE);
        }
    }

    private void initDeliveryMethods(DeliveryMethods deliveryMethods){
        List<String> methods = new ArrayList<>();
        if(deliveryMethods.isALSAvailable()) methods.add("ALS");
        if(deliveryMethods.isO2OAvailable()) methods.add("O2O");
        if(deliveryMethods.isD2DAvailable()) methods.add("D2D");
        if(deliveryMethods.isCODAvailable()) methods.add("COD");

        if(methods.size() > 0) {
            LinearLayout ll = new LinearLayout(getContext());
            ll.setLayoutParams(new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    Gravity.END
            ));
            ll.setOrientation(LinearLayout.HORIZONTAL);
            deliveryMethodsWrapper.addView(ll);
            for (String method : methods) {
                TextView tv = new TextView(getContext());
                tv.setText(method);
                tv.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                tv.setTextSize(1,12);
                tv.setPadding(4, 4, 4, 4);
                tv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_tag));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(16, 0, 0, 0);

                tv.setLayoutParams(params);
                ll.addView(tv);
            }
            deliveryMethodsWrapper.setVisibility(View.VISIBLE);
        }
        else{
            deliveryMethodsWrapper.setVisibility(View.GONE);
        }
    }

    private void initInfoTabs(final Product product){
        TabLayout tabLayout = rootView.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Product information"));
        tabLayout.addTab(tabLayout.newTab().setText("Product images"));
        tabLayout.addTab(tabLayout.newTab().setText("Product reviews"));
//        tabLayout.addTab(tabLayout.newTab().setText("Store home"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                switch (position){
                    case 0:
                        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.product_info_host,
                                        ShoppingProductInformationFragment.newInstance(
                                                product.getManufacturer(),
                                                product.getBrandName(),
                                                product.getModelNumber(),
                                                product.getCountry(),
                                                product.getServiceType(),
                                                product.getDeliveryMethods()
                                        ))
                                .commit();
                        break;

                    case 1:
                        ArrayList<Image> images = new ArrayList<>();
                        images.add(new Image(-1, product.getThumbnail()));

                        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.product_info_host,
                                        GalleryGridFragment.newInstance(images, 1))
                                .commit();
                        break;

                    case 2:
                        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.product_info_host,
                                        ReviewsFragment.newInstance(product.getRating(), product.getReviews()))
                                .commit();
                        break;

//                    case 3:
//                        Coordinator.getCatalogNavigator().navigateToListing(
//                                product.getListingId(), product.getMainCategoryId());
//                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.product_info_host,
                        ShoppingProductInformationFragment.newInstance(
                                product.getManufacturer(),
                                product.getBrandName(),
                                product.getModelNumber(),
                                product.getCountry(),
                                product.getServiceType(),
                                product.getDeliveryMethods()
                        ))
                .commit();
    }

    private void initGuide(){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.shopping_guide, ShoppingGuideFragment.newInstance())
                .commit();
    }
}