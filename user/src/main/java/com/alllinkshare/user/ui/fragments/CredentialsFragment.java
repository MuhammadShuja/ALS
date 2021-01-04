package com.alllinkshare.user.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alllinkshare.core.forms.Form;
import com.alllinkshare.core.forms.validation.ValidationRule;
import com.alllinkshare.core.utils.Swal;
import com.alllinkshare.user.R;
import com.alllinkshare.user.api.API;
import com.alllinkshare.user.api.config.Listeners;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CredentialsFragment extends Fragment {

    private TextInputEditText currentMobileNumber, newMobileNumber,
            currentEmail, newEmail, currentPassword, newPassword,
            mobileVerificationCode, emailVerificationCode;

    private LinearLayout updateMobileWrapper, mobileVerificationWrapper, updateEmailWrapper, emailVerificationWrapper;

    private Form<Object> mobileForm = new Form<>();
    private Form<Object> emailForm = new Form<>();
    private Form<Object> passwordForm = new Form<>();

    private Swal swal;

    private View rootView;

    public CredentialsFragment() {
    }

    public static CredentialsFragment newInstance() {
        return new CredentialsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_credentials, container, false);

        initDialog();
        initForm();

        return rootView;
    }

    private void initDialog(){
        swal = new Swal(getContext());
    }

    private void initForm(){
        initFormValidation();
        initUpdateMobileNumber();
        initUpdateEmail();
        initUpdatePassword();
    }

    private void initFormValidation(){
        currentMobileNumber = rootView.findViewById(R.id.current_mobile_number);
        newMobileNumber = rootView.findViewById(R.id.new_mobile_number);
        currentEmail = rootView.findViewById(R.id.current_email);
        newEmail = rootView.findViewById(R.id.new_email);
        currentPassword = rootView.findViewById(R.id.current_password);
        newPassword = rootView.findViewById(R.id.new_password);

        TextView currentMobileNumberError = rootView.findViewById(R.id.current_mobile_number_error);
        TextView newMobileNumberError = rootView.findViewById(R.id.new_mobile_number_error);
        TextView currentEmailError = rootView.findViewById(R.id.current_email_error);
        TextView newEmailError = rootView.findViewById(R.id.new_email_error);
        TextView currentPasswordError = rootView.findViewById(R.id.current_password_error);
        TextView newPasswordError = rootView.findViewById(R.id.new_password_error);

        List<ValidationRule> currentMobileNumberValidationRules = new ArrayList<>();
        currentMobileNumberValidationRules.add(ValidationRule.Required(currentMobileNumberError,"Current mobile number is required"));
        mobileForm.addField(currentMobileNumber, currentMobileNumberValidationRules);

        List<ValidationRule> newMobileNumberValidationRules = new ArrayList<>();
        newMobileNumberValidationRules.add(ValidationRule.Required(newMobileNumberError,"New mobile number is required"));
        mobileForm.addField(newMobileNumber, newMobileNumberValidationRules);

        List<ValidationRule> currentEmailValidationRules = new ArrayList<>();
        currentEmailValidationRules.add(ValidationRule.Required(currentEmailError,"Current email is required"));
        emailForm.addField(currentEmail, currentEmailValidationRules);

        List<ValidationRule> newEmailValidationRules = new ArrayList<>();
        newEmailValidationRules.add(ValidationRule.Required(newEmailError,"New email is required"));
        emailForm.addField(newEmail, newEmailValidationRules);

        List<ValidationRule> currentPasswordValidationRules = new ArrayList<>();
        currentPasswordValidationRules.add(ValidationRule.Required(currentPasswordError,"Current password is required"));
        currentPasswordValidationRules.add(ValidationRule.CharactersInLength(5, 50, currentPasswordError,"Password should be between 5 and 50 characters"));
        passwordForm.addField(currentPassword, currentPasswordValidationRules);

        List<ValidationRule> newPasswordValidationRules = new ArrayList<>();
        newPasswordValidationRules.add(ValidationRule.Required(newPasswordError,"New password is required"));
        newPasswordValidationRules.add(ValidationRule.CharactersInLength(5, 50, newPasswordError,"New password should be between 5 and 50 characters"));
        passwordForm.addField(newPassword, newPasswordValidationRules);
    }

    private void initUpdateMobileNumber(){
        updateMobileWrapper = rootView.findViewById(R.id.mobile_update_wrapper);
        mobileVerificationWrapper = rootView.findViewById(R.id.mobile_verification_code_wrapper);
        rootView.findViewById(R.id.btn_update_mobile_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mobileForm.isValid()){
                    swal.progress("Verifying");
                    API.updateMobileNumberRequest(Objects.requireNonNull(currentMobileNumber.getText()).toString().trim(),
                            Objects.requireNonNull(newMobileNumber.getText()).toString().trim(),
                            new Listeners.CredentialsListener() {
                                @Override
                                public void onSuccess(String message) {
                                    TextView mobileVerificationCodeError = rootView.findViewById(R.id.mobile_verification_code_error);
                                    mobileVerificationCode = rootView.findViewById(R.id.mobile_verification_code);

                                    List<ValidationRule> verificationCodeValidationRules = new ArrayList<>();
                                    verificationCodeValidationRules.add(ValidationRule.Required(mobileVerificationCodeError, "Verification code is required"));
                                    mobileForm.addField(mobileVerificationCode, verificationCodeValidationRules);

                                    updateMobileWrapper.setVisibility(View.GONE);
                                    mobileVerificationWrapper.setVisibility(View.VISIBLE);
                                    swal.success(message);
                                }

                                @Override
                                public void onFailure(String error) {
                                    swal.error(error);
                                }
                            });
                }
            }
        });

        rootView.findViewById(R.id.btn_update_mobile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mobileForm.isValid()){
                    swal.progress("Updating");
                    API.updateMobileNumber(Objects.requireNonNull(currentMobileNumber.getText()).toString().trim(),
                            Objects.requireNonNull(newMobileNumber.getText()).toString().trim(),
                            Objects.requireNonNull(mobileVerificationCode.getText()).toString().trim(),
                            new Listeners.CredentialsListener() {

                                @Override
                                public void onSuccess(String message) {
                                    mobileForm.reset();

                                    updateMobileWrapper.setVisibility(View.VISIBLE);
                                    mobileVerificationWrapper.setVisibility(View.GONE);
                                    swal.success(message);
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

    private void initUpdateEmail(){
        updateEmailWrapper = rootView.findViewById(R.id.email_update_wrapper);
        emailVerificationWrapper = rootView.findViewById(R.id.email_verification_code_wrapper);
        rootView.findViewById(R.id.btn_update_email_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailForm.isValid()){
                    swal.progress("Verifying");
                    API.updateEmailRequest(Objects.requireNonNull(currentEmail.getText()).toString().trim(),
                            Objects.requireNonNull(newEmail.getText()).toString().trim(),
                            new Listeners.CredentialsListener() {
                                @Override
                                public void onSuccess(String message) {
                                    TextView emailVerificationCodeError = rootView.findViewById(R.id.email_verification_code_error);
                                    emailVerificationCode = rootView.findViewById(R.id.email_verification_code);

                                    List<ValidationRule> verificationCodeValidationRules = new ArrayList<>();
                                    verificationCodeValidationRules.add(ValidationRule.Required(emailVerificationCodeError, "Verification code is required"));
                                    emailForm.addField(emailVerificationCode, verificationCodeValidationRules);

                                    updateEmailWrapper.setVisibility(View.GONE);
                                    emailVerificationWrapper.setVisibility(View.VISIBLE);
                                    swal.success(message);
                                }

                                @Override
                                public void onFailure(String error) {
                                    swal.error(error);
                                }
                            });
                }
            }
        });

        rootView.findViewById(R.id.btn_update_email).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailForm.isValid()){
                    swal.progress("Updating");
                    API.updateEmail(Objects.requireNonNull(currentEmail.getText()).toString().trim(),
                            Objects.requireNonNull(newEmail.getText()).toString().trim(),
                            Objects.requireNonNull(emailVerificationCode.getText()).toString().trim(),
                            new Listeners.CredentialsListener() {
                                @Override
                                public void onSuccess(String message) {
                                    emailForm.reset();
                                    
                                    updateEmailWrapper.setVisibility(View.VISIBLE);
                                    emailVerificationWrapper.setVisibility(View.GONE);
                                    swal.success(message);
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

    private void initUpdatePassword(){
        rootView.findViewById(R.id.btn_update_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passwordForm.isValid()){
                    swal.progress("Updating");
                    API.updatePassword(Objects.requireNonNull(currentPassword.getText()).toString().trim(),
                            Objects.requireNonNull(newPassword.getText()).toString().trim(), new Listeners.CredentialsListener() {
                                @Override
                                public void onSuccess(String message) {
                                    swal.success(message);
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