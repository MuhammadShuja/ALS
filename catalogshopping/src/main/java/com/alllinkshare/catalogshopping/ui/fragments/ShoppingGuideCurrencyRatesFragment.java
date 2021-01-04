package com.alllinkshare.catalogshopping.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alllinkshare.catalogshopping.R;

public class ShoppingGuideCurrencyRatesFragment extends Fragment {

    private View rootView;

    public ShoppingGuideCurrencyRatesFragment() {
        // Required empty public constructor
    }

    public static ShoppingGuideCurrencyRatesFragment newInstance() {
        ShoppingGuideCurrencyRatesFragment fragment = new ShoppingGuideCurrencyRatesFragment();
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
        rootView = inflater.inflate(R.layout.fragment_shopping_guide_currency_rates, container, false);

        initCurrencyRates();

        return rootView;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initCurrencyRates(){
        WebView webView = rootView.findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadData("<div id='gcw_mainFT8KAebsu' class='gcw_mainFT8KAebsu'></div><script>function reloadFT8KAebsu(){ var sc = document.getElementById('scFT8KAebsu');if (sc) sc.parentNode.removeChild(sc);sc = document.createElement('script');sc.type = 'text/javascript';sc.charset = 'UTF-8';sc.async = true;sc.id='scFT8KAebsu';sc.src = 'https://freecurrencyrates.com/en/widget-vertical-editable?iso=USDKRWGBPJPYCNYEUR&df=2&p=FT8KAebsu&v=fi&source=fcr&width=400&width_title=0&firstrowvalue=1&thm=dddddd,eeeeee,E78F08,F6A828,FFFFFF,cccccc,ffffff,1C94C4,000000&title=Today Currency&tzo=-300';var div = document.getElementById('gcw_mainFT8KAebsu');div.parentNode.insertBefore(sc, div);} reloadFT8KAebsu(); </script>"
                    ,null, null);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                rootView.findViewById(R.id.loading_wrapper).setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });
    }
}