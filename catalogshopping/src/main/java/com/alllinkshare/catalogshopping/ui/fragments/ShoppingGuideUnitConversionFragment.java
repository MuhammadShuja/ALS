package com.alllinkshare.catalogshopping.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.alllinkshare.catalogshopping.R;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class ShoppingGuideUnitConversionFragment extends Fragment {

    private View rootView;

    public ShoppingGuideUnitConversionFragment() {
        // Required empty public constructor
    }

    public static ShoppingGuideUnitConversionFragment newInstance() {
        ShoppingGuideUnitConversionFragment fragment = new ShoppingGuideUnitConversionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_shopping_guide_unit_conversion, container, false);

        initConverters();

        return rootView;
    }

    private void initConverters(){
        initInchToCm();
        initLbToKg();
        initMtoPy();
    }

    private void initInchToCm(){
        final EditText inputInch, inputCm;
        inputInch = rootView.findViewById(R.id.input_inch);
        inputCm = rootView.findViewById(R.id.input_cm);

        inputInch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint("DefaultLocale")
            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s) && inputInch.hasFocus())
                    inputCm.setText(
                            getRoundedString(Double.parseDouble(s.toString()) * 2.54)
                    );
            }
        });

        inputCm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint("DefaultLocale")
            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s) && inputCm.hasFocus())
                    inputInch.setText(
                            getRoundedString(Double.parseDouble(s.toString()) / 2.54)
                    );
            }
        });
    }

    private void initLbToKg(){
        final EditText inputLb, inputKg;
        inputLb = rootView.findViewById(R.id.input_lb);
        inputKg = rootView.findViewById(R.id.input_kg);

        inputLb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s) && inputLb.hasFocus())
                    inputKg.setText(
                            getRoundedString(Double.parseDouble(s.toString()) / 2.205)
                    );
            }
        });

        inputKg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s) && inputKg.hasFocus())
                    inputLb.setText(
                            getRoundedString(Double.parseDouble(s.toString()) * 2.205)
                    );
            }
        });
    }

    private void initMtoPy(){
        final EditText inputM, inputPy;
        inputM = rootView.findViewById(R.id.input_m);
        inputPy = rootView.findViewById(R.id.input_py);

        inputM.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s) && inputM.hasFocus())
                    inputPy.setText(
                            getRoundedString(Double.parseDouble(s.toString()) / 3.306)
                    );
            }
        });

        inputPy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s) && inputPy.hasFocus())
                    inputM.setText(
                            getRoundedString(Double.parseDouble(s.toString()) * 3.306)
                    );
            }
        });
    }

    private String getRoundedString(Double d){
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        return String.valueOf(df.format(d));
    }
}