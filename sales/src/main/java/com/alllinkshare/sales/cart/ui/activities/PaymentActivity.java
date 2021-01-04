package com.alllinkshare.sales.cart.ui.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.alllinkshare.core.navigator.Keys;
import com.alllinkshare.core.utils.Swal;
import com.alllinkshare.core.utils.Utils;
import com.alllinkshare.sales.R;
import com.alllinkshare.sales.Sales;
import com.alllinkshare.sales.cart.api.API;
import com.alllinkshare.sales.cart.api.config.Listeners;
import com.alllinkshare.sales.cart.api.retrofit.responses.PaymentResponse;
import com.alllinkshare.sales.cart.api.retrofit.responses.PaymentTokenResponse;
import com.alllinkshare.sales.cart.models.Cart;
import com.alllinkshare.sales.cart.models.Order;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.google.gson.Gson;

import java.util.Objects;

public class PaymentActivity extends AppCompatActivity {

    private static final int DROP_IN_REQUEST = 111;

    private String type;
    private Cart cart;

    private Order order;
    private String amount;

    private Swal swal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_payment);

        initToolbar();
        initDialog();
        initOrder();
        Utils.makeMeShake(findViewById(R.id.shake_view), 100, 20);

        initPay();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DROP_IN_REQUEST) {
            if (resultCode == RESULT_OK) {
                swal.progress("Processing");

                DropInResult dropInResult = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce paymentMethodNonce = dropInResult.getPaymentMethodNonce();
                String nonce = paymentMethodNonce.getNonce();

                API.pay(amount, nonce, new Listeners.PayListener() {
                    @Override
                    public void onSuccess(String message) {
                        order.setPaymentStatus("paid");
                        String orderJson = new Gson().toJson(order);

                        API.checkout(orderJson, new Listeners.CheckoutListener() {
                            @Override
                            public void onSuccess(String message) {
                                swal.dismiss();
                                cart.clearCart();
                                swal.dismiss();

                                startActivity(new Intent(PaymentActivity.this, SuccessActivity.class));
                                finish();
                            }

                            @Override
                            public void onFailure(String error) {
                                swal.error(error);
                            }
                        });
                    }

                    @Override
                    public void onFailure(String error) {
                        swal.error(error);
                    }
                });
            }
            else if(resultCode == RESULT_CANCELED){
                Toast.makeText(PaymentActivity.this, "Action cancelled!", Toast.LENGTH_SHORT).show();
            }
            else{
                Exception exception = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.d("TAG", "----------------------- " + exception.getMessage());
            }
        }
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
        setTitle("Pay");
    }

    private void initDialog(){
        swal = new Swal(PaymentActivity.this);
    }

    private void initOrder(){
        getCart();

        order = Sales.activeOrder;

        if(order.getDiscount().getTotal() > 0)
            amount = String.valueOf(order.getDiscount().getTotal());
        else
            amount = String.valueOf(order.getPrice());

        ((TextView) findViewById(R.id.amount)).setText(amount);
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

    private void initPay(){
        findViewById(R.id.btn_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swal.progress("Processing");
                API.token(new Listeners.TokenListener() {
                    @Override
                    public void onSuccess(PaymentTokenResponse response) {
                        DropInRequest dropInRequest = new DropInRequest().clientToken(response.getToken());
                        startActivityForResult(dropInRequest.getIntent(PaymentActivity.this), DROP_IN_REQUEST);
                        swal.dismiss();
                    }

                    @Override
                    public void onFailure(String error) {
                        swal.error(error);
                    }
                });
            }
        });
    }
}