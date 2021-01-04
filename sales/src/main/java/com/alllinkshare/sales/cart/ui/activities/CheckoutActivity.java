package com.alllinkshare.sales.cart.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alllinkshare.core.forms.Form;
import com.alllinkshare.core.forms.validation.ValidationRule;
import com.alllinkshare.core.navigator.Keys;
import com.alllinkshare.core.utils.Swal;
import com.alllinkshare.core.utils.Utils;
import com.alllinkshare.core.widgets.DatePickerWidget;
import com.alllinkshare.core.widgets.TimePickerWidget;
import com.alllinkshare.sales.R;
import com.alllinkshare.sales.Sales;
import com.alllinkshare.sales.cart.api.API;
import com.alllinkshare.sales.cart.api.config.Listeners;
import com.alllinkshare.sales.cart.api.retrofit.responses.PaymentTokenResponse;
import com.alllinkshare.sales.cart.models.Cart;
import com.alllinkshare.sales.cart.models.CartItem;
import com.alllinkshare.sales.cart.models.Coupon;
import com.alllinkshare.sales.cart.models.Order;
import com.alllinkshare.sales.cart.models.OrderDiscount;
import com.alllinkshare.sales.cart.ui.adapters.CartSummaryAdapter;
import com.alllinkshare.sales.cart.ui.adapters.CouponsAdapter;
import com.alllinkshare.user.models.User;
import com.alllinkshare.user.repos.UserRepository;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CheckoutActivity extends AppCompatActivity {

    private String type;
    private Cart cart;
    private User user;

    private TextView pickupTime, pickupDate;
    private EditText accountName, accountMobile, deliveryAddress, detailedAddress,
                    sellerInstructions, riderInstructions;
    private String googleId, address, latitude, longitude, deliveryType = "cod", paymentType="cod";

    private OrderDiscount orderDiscount = new OrderDiscount();

    private Form<Object> codForm = new Form<>();
    private Form<Object> o2oForm = new Form<>();

    private Swal swal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_checkout);

        initToolbar();
        initDialog();
        initAccountInformation();
        initCartSummary();
        initCoupons();
        initDeliveryMethods();
        initPaymentMethods();
        initCheckout();
        initPlacePicker();
        initForms();
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
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.bg));
        }
        else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorThemeDark));
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setTitle("Checkout");
    }

    private void initDialog(){
        swal = new Swal(CheckoutActivity.this);
    }

    private void initAccountInformation(){
        UserRepository.getInstance().getProfile(new UserRepository.ProfileReadyListener() {
            @Override
            public void onProfileReady(User profile) {
                user = profile;
                accountName =  findViewById(R.id.account_name);
                accountName.setText(profile.getFullName());

                accountMobile = findViewById(R.id.account_mobile_number);
                accountMobile.setText(profile.getMobileNumber());

                detailedAddress = findViewById(R.id.detailed_address);
                detailedAddress.setText(profile.getAddress());
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void initCartSummary(){
        getCart();

        final CartSummaryAdapter cartAdapter = new CartSummaryAdapter(CheckoutActivity.this, cart.getItems(), new CartSummaryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CartItem cartItem) {

            }
        });

        final RecyclerView rvItems = findViewById(R.id.rv_items);
        final GridLayoutManager layoutManager = new GridLayoutManager(CheckoutActivity.this, 1);
        DividerItemDecoration verticalDivider = new DividerItemDecoration(
                CheckoutActivity.this , DividerItemDecoration.VERTICAL);

        verticalDivider.setDrawable(
                Objects.requireNonNull(
                        ContextCompat.getDrawable(CheckoutActivity.this, R.drawable.divider)));
        rvItems.addItemDecoration(verticalDivider);
        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(cartAdapter);

        setTotals(cart.getTotalPrice(), 0.0, cart.getTotalPrice());
    }

    private void initCoupons(){
        final LinearLayout couponsWrapper = findViewById(R.id.coupons_wrapper);
        final LinearLayout couponsLoadingWrapper = findViewById(R.id.coupons_loading_wrapper);

        final CouponsAdapter couponAdapter = new CouponsAdapter(CheckoutActivity.this, new ArrayList<Coupon>(), new CouponsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Coupon coupon) {
                applyCoupon(coupon);
            }
        });

        final RecyclerView rvItems = findViewById(R.id.rv_coupons);
        final GridLayoutManager layoutManager = new GridLayoutManager(CheckoutActivity.this, 1);
        DividerItemDecoration verticalDivider = new DividerItemDecoration(
                CheckoutActivity.this , DividerItemDecoration.VERTICAL);

        verticalDivider.setDrawable(
                Objects.requireNonNull(
                        ContextCompat.getDrawable(CheckoutActivity.this, R.drawable.divider)));
        rvItems.addItemDecoration(verticalDivider);
        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(couponAdapter);

        API.coupons(cart.getListingIds(), new Listeners.CouponsListener() {
            @Override
            public void onSuccess(List<Coupon> couponList) {
                if(couponList.size() > 0){
                    couponsWrapper.setVisibility(View.VISIBLE);
                    couponAdapter.setData(couponList);
                }
                else{
                    couponsWrapper.setVisibility(View.GONE);
                }
                couponsLoadingWrapper.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                couponsLoadingWrapper.setVisibility(View.GONE);
            }
        });
    }

    private void initDeliveryMethods(){
        final LinearLayout deliveryPickupWrapper = findViewById(R.id.delivery_pickup_wrapper);
        sellerInstructions = findViewById(R.id.seller_instructions);
        riderInstructions = findViewById(R.id.rider_instructions);

        RadioGroup radioGroup = findViewById(R.id.radio_group_delivery);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = radioGroup.findViewById(i);
                String tag = radioButton.getTag().toString();

                if(tag.equals("delivery_online")){
                    deliveryPickupWrapper.setVisibility(View.GONE);
                    if(paymentType.equals("cod")){
                        deliveryType = "cod";
                    }
                    else if(paymentType.equals("card")){
                        deliveryType = "d2d";
                    }
                }
                else if(tag.equals("delivery_pickup")){
                    deliveryPickupWrapper.setVisibility(View.VISIBLE);
                    deliveryType = "o2o";
                }
            }
        });

        pickupTime = findViewById(R.id.pickup_time);
        new TimePickerWidget(pickupTime, this);

        pickupDate = findViewById(R.id.pickup_date);
        new DatePickerWidget(pickupDate, this);

    }

    private void initPaymentMethods(){
        RadioGroup radioGroup = findViewById(R.id.radio_group_payment);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = radioGroup.findViewById(i);
                String tag = radioButton.getTag().toString();

                if(tag.equals("payment_cod")){
                    paymentType = "cod";
                    if(deliveryType.equals("d2d")){
                        deliveryType = "cod";
                    }
                }
                else if(tag.equals("payment_card")){
                    paymentType = "card";
                    if(deliveryType.equals("cod")){
                        deliveryType = "d2d";
                    }
                }
            }
        });
    }

    private void initCheckout(){
        findViewById(R.id.btn_place_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order order = null;
                if((deliveryType.equals("cod") || deliveryType.equals("d2d")) && codForm.isValid()){
                    order = createOrder();
                }
                else if(deliveryType.equals("o2o") && o2oForm.isValid()){
                    order = createOrder();
                }
                if(order != null){
                    String orderJson = new Gson().toJson(order);
                    Log.d("TAG", "---------------------- Order JSON: "+orderJson);

                    Sales.activeOrder = order;
                    if(paymentType.equals("cod")){
                        swal.progress("Processing");
                        API.checkout(orderJson, new Listeners.CheckoutListener() {
                            @Override
                            public void onSuccess(String message) {
                                swal.dismiss();
                                cart.clearCart();

                                startActivity(new Intent(CheckoutActivity.this, SuccessActivity.class));
                                finish();
                            }

                            @Override
                            public void onFailure(String error) {
                                swal.error(error);
                            }
                        });
                    }
                    else{
                        Intent intent = new Intent(CheckoutActivity.this, PaymentActivity.class);
                        intent.putExtra(Keys.TYPE, type);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    private void getCart(){
        if(getIntent().hasExtra(Keys.TYPE)){
            type = Objects.requireNonNull(getIntent().getExtras()).getString(Keys.TYPE);
            if(type != null){
                switch (type){
                    case Cart.TYPE_FOOD:
                        cart = Sales.getFoodCart();
                        break;
                    case Cart.TYPE_SHOPPING:
                        cart = Sales.getShoppingCart();
                        break;
                }
            }
            else{
                finish();
            }
        }
        else{
            finish();
        }
    }

    private void initPlacePicker(){
        if (!Places.isInitialized()) {
            String gApiKey = getString(R.string.google_places_api_key);
            Places.initialize(getApplicationContext(), gApiKey);
        }

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        deliveryAddress = (EditText) autocompleteFragment.getView().findViewById(R.id.places_autocomplete_search_input);
        deliveryAddress.setTextSize(16.0f);

        int pix = (int) Utils.dpToPx(this, 18);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(pix, pix);

        autocompleteFragment.getView().findViewById(R.id.places_autocomplete_search_button).setLayoutParams(layoutParams);
        autocompleteFragment.getView().findViewById(R.id.places_autocomplete_clear_button).setLayoutParams(layoutParams);

        // Specify the types of place data to return.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);
        autocompleteFragment.setPlaceFields(fields);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                googleId = place.getId();
                address = place.getName();
                latitude = String.valueOf(Objects.requireNonNull(place.getLatLng()).latitude);
                longitude = String.valueOf(Objects.requireNonNull(place.getLatLng()).longitude);
            }

            @Override
            public void onError(@NonNull Status status) {
            }
        });
    }

    private void initForms(){
        List<ValidationRule> nameValidationRules = new ArrayList<>();
        nameValidationRules.add(ValidationRule.Required((TextView)findViewById(R.id.account_name_error), "Customer name is required"));

        List<ValidationRule> mobileNumberValidationRules = new ArrayList<>();
        mobileNumberValidationRules.add(ValidationRule.Required((TextView)findViewById(R.id.account_mobile_number_error), "Customer mobile number is required"));

        List<ValidationRule> addressValidationRules = new ArrayList<>();
        addressValidationRules.add(ValidationRule.Required((TextView)findViewById(R.id.address_error), "Delivery address is required"));

        List<ValidationRule> pickupTimeValidationRules = new ArrayList<>();
        pickupTimeValidationRules.add(ValidationRule.Required((TextView)findViewById(R.id.pickup_time_error), "Pickup time is required"));

        List<ValidationRule> pickupDateValidationRules = new ArrayList<>();
        pickupDateValidationRules.add(ValidationRule.Required((TextView)findViewById(R.id.pickup_date_error), "Pickup date is required"));

        codForm.addField(accountName, nameValidationRules);
        codForm.addField(accountMobile, mobileNumberValidationRules);
        codForm.addField(deliveryAddress, addressValidationRules);

        o2oForm.addField(accountName, nameValidationRules);
        o2oForm.addField(accountMobile, mobileNumberValidationRules);
        o2oForm.addField(deliveryAddress, addressValidationRules);
        o2oForm.addField(pickupTime, pickupTimeValidationRules);
        o2oForm.addField(pickupDate, pickupDateValidationRules);
    }

    private void applyCoupon(Coupon coupon){
        double subTotal = cart.getTotalPrice();
        double discount;
        double total;
        if(coupon.getType().equals("fixed")){
            discount = coupon.getDiscount();
        }
        else{
            discount = subTotal * coupon.getDiscount() / 100;
        }
        total = subTotal - discount;
        setTotals(subTotal, discount, total);

        orderDiscount.setListingId(coupon.getListingId());
        orderDiscount.setTotal(total);
        orderDiscount.setDiscount(discount);
    }

    @SuppressLint("SetTextI18n")
    private void setTotals(double subTotal, double discount, double total){
        ((TextView) findViewById(R.id.order_subtotal)).setText(getString(R.string.currency_symbol)+subTotal);
        ((TextView) findViewById(R.id.order_discount)).setText("- "+getString(R.string.currency_symbol)+discount);
        ((TextView) findViewById(R.id.order_total)).setText(getString(R.string.currency_symbol)+total);
    }

    private Order createOrder(){
        Order order = cart.createOrder();
        order.setCustomerDetails(
                user.getFullName(),
                address,
                user.getAddress(),
                latitude,
                longitude,
                user.getEmail(),
                user.getMobileNumber()
        );

        String instructions = "Seller instructions: "+sellerInstructions.getText().toString()
                +" - Rider instructions: "+ riderInstructions.getText().toString();

        order.setDeliveryDetails(
                instructions,
                pickupDate.getText().toString(),
                pickupTime.getText().toString(),
                deliveryType
        );

        order.setDiscount(orderDiscount);
        order.setPaymentStatus("pending");

        return order;
    }
}