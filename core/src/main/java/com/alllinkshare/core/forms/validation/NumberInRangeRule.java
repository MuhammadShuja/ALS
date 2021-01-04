package com.alllinkshare.core.forms.validation;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NumberInRangeRule extends ValidationRule{

    private double min;
    private double max;

    private TextView errorView;
    private String errorMessage;

    NumberInRangeRule(double min, double max, TextView errorView, String errorMessage) {
        this.min = min;
        this.max = max;
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
                isValid = inRange(val);
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

    private boolean inRange(String value){
        try{
            double number = Double.parseDouble(value);
            return number >= min && number <= max;
        }
        catch (NumberFormatException e){
            return false;
        }
    }
}