package com.alllinkshare.core.forms;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alllinkshare.core.forms.validation.CharactersInLengthRule;
import com.alllinkshare.core.forms.validation.NumberInRangeRule;
import com.alllinkshare.core.forms.validation.RequiredRule;
import com.alllinkshare.core.forms.validation.ValidationRule;

import java.util.List;

class FormField<T> {
    final T inputControl;
    final List<ValidationRule> validationRules;
    private final View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if(!view.hasFocus()){
                for(ValidationRule rule : validationRules){
                    if(rule instanceof RequiredRule) {
                        if(!((RequiredRule) rule).validate((View) inputControl))
                            break;
                    }
                    else if(rule instanceof NumberInRangeRule) {
                        if(!((NumberInRangeRule) rule).validate((View) inputControl))
                            break;
                    }

                    else if(rule instanceof CharactersInLengthRule) {
                        if(!((CharactersInLengthRule) rule).validate((View) inputControl))
                            break;
                    }
                }
            }
        }
    };

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            for(ValidationRule rule : validationRules){
                if(rule instanceof RequiredRule) {
                    if(!((RequiredRule) rule).validate((View) inputControl))
                        break;
                }
                else if(rule instanceof NumberInRangeRule) {
                    if(!((NumberInRangeRule) rule).validate((View) inputControl))
                        break;
                }

                else if(rule instanceof CharactersInLengthRule) {
                    if(!((CharactersInLengthRule) rule).validate((View) inputControl))
                        break;
                }
            }
        }
    };

    FormField(T inputControl, List<ValidationRule> validationRules) {
        this.inputControl = inputControl;
        this.validationRules = validationRules;

        setValidator();
    }

    private void setValidator(){
        if(inputControl instanceof EditText){
            ((EditText) inputControl).setOnFocusChangeListener(onFocusChangeListener);
        }
        else if(inputControl instanceof TextView){
            ((TextView) inputControl).setOnFocusChangeListener(onFocusChangeListener);
            ((TextView) inputControl).addTextChangedListener(textWatcher);
        }
    }
}