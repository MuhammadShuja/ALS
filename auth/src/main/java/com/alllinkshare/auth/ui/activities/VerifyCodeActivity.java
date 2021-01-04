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
import com.alllinkshare.core.forms.Form;
import com.alllinkshare.core.forms.validation.ValidationRule;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class VerifyCodeActivity extends AppCompatActivity {

    private TextInputEditText inputCode;

    private Form<Object> form = new Form<>();

    private SweetAlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_verify_code);

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
        initFormValidation();
        initResend();
        initVerify();
    }

    private void initFormValidation(){
        inputCode = findViewById(R.id.code);

        List<ValidationRule> codeValidationRules = new ArrayList<>();
        codeValidationRules.add(ValidationRule.Required((TextView) findViewById(R.id.code_error),"Code is required"));
        form.addField(inputCode, codeValidationRules);
    }

    private void initResend(){
        findViewById(R.id.btn_resend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                API.resendCode(
                        new Listeners.AuthListener() {
                            @Override
                            public void onSuccess(String message) {
                                showSuccessDialog(message);
                            }

                            @Override
                            public void onFailure(String error) {
                                showErrorDialog(error);
                            }
                        }
                );
            }
        });
    }

    private void initVerify(){
        findViewById(R.id.btn_verify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(form.isValid()){
                    showProgressDialog();
                    API.verifyCode(
                            inputCode.getText().toString().trim(),
                            new Listeners.AuthListener() {
                                @Override
                                public void onSuccess(String message) {
                                    try {
                                        Intent intent = new Intent(VerifyCodeActivity.this,
                                                Class.forName("com.alllinkshare.home.ui.activities.HomeActivity"));
                                        intent.putExtra("fragment", "HomeFragment");
                                        dialog.dismiss();
                                        startActivity(intent);
                                        finish();
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(String error) {
                                    showErrorDialog(error);
                                }
                            }
                    );
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

    private void showSuccessDialog(String successMessage) {
        dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        dialog.setTitleText("Success");
        dialog.setConfirmText("Done");
        dialog.setContentText(successMessage);
        dialog.setCancelable(false);
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialog.dismiss();
            }
        });
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