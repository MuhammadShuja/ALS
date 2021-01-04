package com.alllinkshare.core.forms.validation;

import android.view.View;
import android.widget.TextView;

import java.util.List;

public class ListNotEmptyRule extends ValidationRule{

    private TextView errorView;
    private String errorMessage;

    ListNotEmptyRule(TextView errorView, String errorMessage) {
        this.errorView = errorView;
        this.errorMessage = errorMessage;
    }

    public boolean validate(List list){
        boolean isValid = !list.isEmpty();

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