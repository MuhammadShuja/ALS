package com.alllinkshare.core.forms.validation;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.TextView;

public class CharactersInLengthRule extends ValidationRule{

    private int minLength;
    private int maxLength;

    private TextView errorView;
    private String errorMessage;

    CharactersInLengthRule(int minLength, int maxLength, TextView errorView, String errorMessage) {
        this.minLength = minLength;
        this.maxLength = maxLength;
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
            else{
                isValid = isLengthValid(val);
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

    private boolean isLengthValid(String value){
        try{
            int characters = value.length();
            return characters >= minLength && characters <= maxLength;
        }
        catch (NumberFormatException e){
            return false;
        }
    }
}