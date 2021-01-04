package com.alllinkshare.auth.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alllinkshare.auth.R;
import com.alllinkshare.auth.api.API;
import com.alllinkshare.auth.api.config.Listeners;
import com.alllinkshare.auth.models.User;
import com.alllinkshare.core.forms.Form;
import com.alllinkshare.core.forms.validation.ValidationRule;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText inputFirstName, inputLastName, inputEmail, inputMobile,
            inputPassword, inputPasswordConfirmation;

    private CountryCodePicker countryCodePicker;

    private String userType = "buyer"
            , countryCode;

    private Form<Object> form = new Form<>();

    private SweetAlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_register);

        initToolbar();
        initDialog();
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
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initDialog(){
        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(getResources().getColor(R.color.gold));
    }

    private void initForm(){
        inputFirstName = findViewById(R.id.first_name);
        inputLastName = findViewById(R.id.last_name);
        inputEmail = findViewById(R.id.email);
        inputMobile = findViewById(R.id.mobile);
        inputPassword = findViewById(R.id.password);
        inputPasswordConfirmation = findViewById(R.id.password_confirmation);

        initUserType();
        initCountryCodePicker();
        initFormValidation();
        initLogin();
        initRegister();
    }

    private void initUserType(){
        RadioGroup radioGroup = findViewById(R.id.radio_group);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = radioGroup.findViewById(i);
                String tag = radioButton.getTag().toString();

                if(tag.equals("buyer")){
                    userType = "buyer";
                }
                else if(tag.equals("seller")){
                    userType = "seller";
                }
            }
        });
    }

    private void initCountryCodePicker(){
        countryCodePicker = findViewById(R.id.country_code);
        countryCode = countryCodePicker.getSelectedCountryCode();
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryCode = countryCodePicker.getSelectedCountryCode();
            }
        });
    }

    private void initFormValidation(){
        List<ValidationRule> firstNameValidationRules = new ArrayList<>();
        firstNameValidationRules.add(ValidationRule.Required((TextView) findViewById(R.id.first_name_error),"First name is required"));
        form.addField(inputFirstName, firstNameValidationRules);

        List<ValidationRule> lastNameValidationRules = new ArrayList<>();
        lastNameValidationRules.add(ValidationRule.Required((TextView) findViewById(R.id.last_name_error),"Last name is required"));
        form.addField(inputLastName, lastNameValidationRules);

        List<ValidationRule> emailValidationRules = new ArrayList<>();
        emailValidationRules.add(ValidationRule.Required((TextView) findViewById(R.id.email_error),"Email is required"));
        form.addField(inputEmail, emailValidationRules);

        List<ValidationRule> mobileValidationRules = new ArrayList<>();
        mobileValidationRules.add(ValidationRule.Required((TextView) findViewById(R.id.mobile_error),"Mobile number is required"));
        form.addField(inputMobile, mobileValidationRules);

        List<ValidationRule> passwordValidationRules = new ArrayList<>();
        passwordValidationRules.add(ValidationRule.Required((TextView) findViewById(R.id.password_error),"Password is required"));
        passwordValidationRules.add(ValidationRule.CharactersInLength(5, 50, (TextView) findViewById(R.id.password_error),"Password should be between 5 and 50 characters"));
        form.addField(inputPassword, passwordValidationRules);

        List<ValidationRule> passwordConfirmationValidationRules = new ArrayList<>();
        passwordConfirmationValidationRules.add(ValidationRule.Required((TextView) findViewById(R.id.password_confirmation_error),"Password confirmation is required"));
        passwordConfirmationValidationRules.add(ValidationRule.CharactersInLength(5, 50, (TextView) findViewById(R.id.password_confirmation_error),"Password should be between 5 and 50 characters"));
        form.addField(inputPasswordConfirmation, passwordConfirmationValidationRules);
    }

    private void initLogin(){
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void initRegister(){
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(form.isValid()){
                    showProgressDialog();

                    User user = new User(
                            userType,
                            inputFirstName.getText().toString().trim(),
                            inputLastName.getText().toString().trim(),
                            countryCode,
                            inputEmail.getText().toString().trim(),
                            inputMobile.getText().toString().trim(),
                            inputPassword.getText().toString().trim(),
                            inputPasswordConfirmation.getText().toString().trim()
                    );

                    API.register(user, new Listeners.AuthListener() {
                        @Override
                        public void onSuccess(String message) {
                            startActivity(new Intent(RegisterActivity.this, VerifyCodeActivity.class));
                            finish();
                        }

                        @Override
                        public void onFailure(String error) {
                            showErrorDialog(error);
                        }
                    });
                }
            }
        });
    }

    private void showProgressDialog() {
        dialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitleText("Processing");
        dialog.setContentText("");
        dialog.setCancelable(false);
        dialog.show();
    }

    private void showErrorDialog(String errorMessage) {
        dialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
        dialog.setTitleText("Error");
        dialog.setContentText(errorMessage);
        dialog.setCancelable(false);
        dialog.setConfirmButton("Close", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialog.dismiss();
            }
        });
        dialog.setConfirmButtonBackgroundColor(getResources().getColor(R.color.white_six));
    }
}