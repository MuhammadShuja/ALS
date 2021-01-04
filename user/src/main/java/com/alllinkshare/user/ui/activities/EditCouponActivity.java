package com.alllinkshare.user.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alllinkshare.core.forms.Form;
import com.alllinkshare.core.forms.validation.ValidationRule;
import com.alllinkshare.core.utils.Swal;
import com.alllinkshare.user.R;
import com.alllinkshare.user.api.API;
import com.alllinkshare.user.api.config.Listeners;
import com.alllinkshare.user.models.Coupon;
import com.alllinkshare.user.models.SpinnerItem;
import com.alllinkshare.user.ui.adapters.SpinnerAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EditCouponActivity extends AppCompatActivity {
    private int COUPON_ID;

    private TextInputEditText name, code, percentOff, discount;
    private TextView nameError, codeError, typeError, percentOffError, discountError;
    private Spinner typeSpinner;
    private SpinnerAdapter spinnerAdapter;
    private String SELECTED_TYPE = null;
    private boolean isTypeSpinnerValid = false;

    private Form<Object> form = new Form<>();
    private Swal swal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_edit_coupon);

        initToolbar();
        initData();
        initForm();
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
        ((TextView) toolbar.findViewById(R.id.title)).setText("Edit coupon");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    private void initData(){
        swal = new Swal(this);
        if(!getIntent().hasExtra("coupon_id")){
            swal.error("Invalid request", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    finish();
                }
            });
        }
        COUPON_ID = getIntent().getExtras().getInt("coupon_id");
    }

    private void initForm(){
        initFormValidation();
        initUpdateCoupon();
    }

    private void initFormValidation(){
        name = findViewById(R.id.name);
        code = findViewById(R.id.code);
        percentOff = findViewById(R.id.percent_off);
        discount = findViewById(R.id.discount);

        nameError = findViewById(R.id.name_error);
        codeError = findViewById(R.id.code_error);
        typeError = findViewById(R.id.type_error);
        percentOffError = findViewById(R.id.percent_off_error);
        discountError = findViewById(R.id.discount_error);

        List<ValidationRule> nameValidationRules = new ArrayList<>();
        nameValidationRules.add(ValidationRule.Required(nameError,"Coupon name is required"));
        form.addField(name, nameValidationRules);

        List<ValidationRule> codeValidationRules = new ArrayList<>();
        codeValidationRules.add(ValidationRule.Required(codeError,"Coupon code is required"));
        form.addField(code, codeValidationRules);

        List<ValidationRule> percentOffValidationRules = new ArrayList<>();
        percentOffValidationRules.add(ValidationRule.Required(percentOffError,"Percentage off is required"));
        form.addField(percentOff, percentOffValidationRules);

        List<ValidationRule> discountValidationRules = new ArrayList<>();
        discountValidationRules.add(ValidationRule.Required(discountError,"Discount is required"));
        form.addField(discount, discountValidationRules);

        initTypeSpinner();

        populateForm();
    }

    private void populateForm(){
        swal.progress("Loading coupon");
        API.coupon(COUPON_ID, new Listeners.CouponListener() {
            @Override
            public void onSuccess(Coupon coupon) {
                name.setText(coupon.getName());
                code.setText(coupon.getCode());
                percentOff.setText(coupon.getDiscount());
                discount.setText(coupon.getAmount());

                if(coupon.getType().equals("fixed")){
                    typeSpinner.setSelection(1);
                }
                else if(coupon.getType().equals("percent")){
                    typeSpinner.setSelection(2);
                }
                swal.dismiss();
            }

            @Override
            public void onFailure(String error) {
                swal.error(error);
            }
        });
    }

    private void initTypeSpinner(){
        List<SpinnerItem> spinnerItems = new ArrayList<>();
        spinnerItems.add(new SpinnerItem(-1, "select"));
        spinnerItems.add(new SpinnerItem(1, "fixed"));
        spinnerItems.add(new SpinnerItem(2, "percent"));

        spinnerAdapter = new SpinnerAdapter(this, spinnerItems);

        typeSpinner = findViewById(R.id.type_spinner);
        typeSpinner.setAdapter(spinnerAdapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i > 0){
                    SELECTED_TYPE = spinnerAdapter.getItem(i).getName();
                    typeError.setVisibility(View.GONE);
                    isTypeSpinnerValid = true;
                }
                else{
                    typeError.setVisibility(View.VISIBLE);
                    isTypeSpinnerValid = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initUpdateCoupon(){
        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(form.isValid() && isTypeSpinnerValid){
                    swal.progress("Updating");
                    Coupon coupon = new Coupon(
                            COUPON_ID,
                            null,
                            null,
                            null,
                            name.getText().toString().trim(),
                            code.getText().toString().trim(),
                            SELECTED_TYPE,
                            percentOff.getText().toString().trim(),
                            discount.getText().toString().trim(),
                            null
                    );
                    API.updateCoupon(coupon, new Listeners.CouponListener() {
                        @Override
                        public void onSuccess(Coupon coupon) {
                            swal.success("Coupon has been updated successfully.");
                        }

                        @Override
                        public void onFailure(String error) {
                            swal.error(error);
                        }
                    });
                }
            }
        });
    }
}