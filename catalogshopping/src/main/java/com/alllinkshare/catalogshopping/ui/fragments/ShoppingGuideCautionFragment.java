package com.alllinkshare.catalogshopping.ui.fragments;

import android.os.Bundle;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alllinkshare.catalogshopping.R;

public class ShoppingGuideCautionFragment extends Fragment {

    private View rootView;

    public ShoppingGuideCautionFragment() {
        // Required empty public constructor
    }

    public static ShoppingGuideCautionFragment newInstance() {
        ShoppingGuideCautionFragment fragment = new ShoppingGuideCautionFragment();
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
        rootView = inflater.inflate(R.layout.fragment_shopping_guide_caution, container, false);

        initGuide();

        return rootView;
    }

    private void initGuide(){
        ((TextView) rootView.findViewById(R.id.content)).setText(
                HtmlCompat.fromHtml(getString(R.string.content_shopping_guide_caution),
                        HtmlCompat.FROM_HTML_MODE_LEGACY)
        );
    }
}