package com.alllinkshare.core.forms.validation;

import android.widget.TextView;

public class ValidationRule {

    public static RequiredRule Required(TextView errorView, String errorMessage){
        return new RequiredRule(errorView, errorMessage);
    }

    public static NumberInRangeRule NumberInRange(double min, double max, TextView errorView, String errorMessage){
        return new NumberInRangeRule(min, max, errorView, errorMessage);
    }

    public static CharactersInLengthRule CharactersInLength(int minLength, int maxLength, TextView errorView, String errorMessage){
        return new CharactersInLengthRule(minLength, maxLength, errorView, errorMessage);
    }

    public static ListNotEmptyRule ListNotEmpty(TextView errorView, String errorMessage){
        return new ListNotEmptyRule(errorView, errorMessage);
    }
}