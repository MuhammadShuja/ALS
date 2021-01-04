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

public class ResetPasswordActivity extends AppCompatActivity {

    private TextInputEditText inputPassword, inputPasswordConfirmation;

    private Form<Object> form = new Form<>();

    private SweetAlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_reset_password);

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
        initResetPassword();
    }

    private void initFormValidation(){
        inputPassword = findViewById(R.id.password);
        inputPasswordConfirmation = findViewById(R.id.password_confirmation);

        List<ValidationRule> passwordValidationRules = new ArrayList<>();
        passwordValidationRules.add(ValidationRule.Required((TextView) findViewById(R.id.password_error),"Password is required"));
        passwordValidationRules.add(ValidationRule.CharactersInLength(5, 50, (TextView) findViewById(R.id.password_error),"Password should be between 5 and 50 characters"));
        form.addField(inputPassword, passwordValidationRules);

        List<ValidationRule> passwordConfirmationValidationRules = new ArrayList<>();
        passwordConfirmationValidationRules.add(ValidationRule.Required((TextView) findViewById(R.id.password_confirmation_error),"Password confirmation is required"));
        passwordConfirmationValidationRules.add(ValidationRule.CharactersInLength(5, 50, (TextView) findViewById(R.id.password_confirmation_error),"Password should be between 5 and 50 characters"));
        form.addField(inputPasswordConfirmation, passwordConfirmationValidationRules);
    }

    private void initResetPassword(){
        findViewById(R.id.btn_reset_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(form.isValid()){
                    showProgressDialog();
                    API.resetPassword(
                            inputPassword.getText().toString().trim(),
                            inputPasswordConfirmation.getText().toString().trim(),
                            new Listeners.AuthListener() {
                                @Override
                                public void onSuccess(String message) {
                                    startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                                    finish();
                                    dialog.dismiss();
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