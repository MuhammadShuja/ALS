package com.alllinkshare.core.forms.validation;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RequiredRule extends ValidationRule{

    private TextView errorView;
    private String errorMessage;

    RequiredRule(TextView errorView, String errorMessage) {
        this.errorView = errorView;
        this.errorMessage = errorMessage;
    }

    public boolean validate(View view){
        boolean isValid = true;
        if(view instanceof EditText){
            String val = ((EditText) view).getText().toString();
            if(TextUtils.isEmpty(val)){
                isValid = false;
            }
        }
        else if(view instanceof TextView){
            String val = ((TextView) view).getText().toString();
            if(TextUtils.isEmpty(val)){
                isValid = false;
            }
        }
        if(isValid){
            errorView.setVisibility(View.GONE);
        }
        else{
            errorView.setText(errorMessage);
            errorView.setVisibility(View.VISIBLE);
        }

        return isValid;
    }
}