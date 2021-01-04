package com.alllinkshare.core.forms;

import android.view.View;
import android.widget.EditText;

import com.alllinkshare.core.forms.validation.CharactersInLengthRule;
import com.alllinkshare.core.forms.validation.ListNotEmptyRule;
import com.alllinkshare.core.forms.validation.NumberInRangeRule;
import com.alllinkshare.core.forms.validation.RequiredRule;
import com.alllinkshare.core.forms.validation.ValidationRule;

import java.util.ArrayList;
import java.util.List;

public class Form<T> {

    private List<FormField<T>> formFields;

    public Form() {
        formFields = new ArrayList<>();
    }

    public void addField(T inputControl, List<ValidationRule> validationRules){
        formFields.add(new FormField<T>(inputControl, validationRules));
    }

    public boolean isValid(){
        boolean isFormValid = true;

        for(FormField field : formFields){
            for(Object rule : field.validationRules){
                if(rule instanceof RequiredRule){
                    boolean isFieldValid = ((RequiredRule) rule).validate((View) field.inputControl);

                    if(!isFieldValid) {
                        isFormValid = false;
                        break;
                    }
                }
                else if(rule instanceof NumberInRangeRule){
                    boolean isFieldValid = ((NumberInRangeRule) rule).validate((View) field.inputControl);

                    if(!isFieldValid) {
                        isFormValid = false;
                        break;
                    }
                }

                else if(rule instanceof CharactersInLengthRule){
                    boolean isFieldValid = ((CharactersInLengthRule) rule).validate((View) field.inputControl);

                    if(!isFieldValid) {
                        isFormValid = false;
                        break;
                    }
                }

                else if(rule instanceof ListNotEmptyRule){
                    boolean isFieldValid = ((ListNotEmptyRule) rule).validate((ArrayList) field.inputControl);

                    if(!isFieldValid) {
                        isFormValid = false;
                        break;
                    }
                }
            }
        }

        return isFormValid;
    }

    public void reset(){
        for(FormField field : formFields){
            if(field.inputControl instanceof EditText){
                ((EditText) field.inputControl).setText("");
            }
        }
    }
}