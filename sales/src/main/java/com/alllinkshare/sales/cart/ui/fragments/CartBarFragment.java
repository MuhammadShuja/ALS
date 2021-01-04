package com.alllinkshare.sales.cart.ui.fragments;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alllinkshare.core.navigator.Coordinator;
import com.alllinkshare.core.navigator.Keys;
import com.alllinkshare.sales.R;
import com.alllinkshare.sales.Sales;
import com.alllinkshare.sales.cart.listeners.CartUpdateListener;
import com.alllinkshare.sales.cart.models.Cart;

public class CartBarFragment extends Fragment {

    private String type;
    private Cart cart;

    private ConstraintLayout shoppingCartWrapper;
    private TextView shoppingCartCounter, shoppingCartTotal;

    private View rootView;

    public CartBarFragment() {
        // Required empty public constructor
    }

    public static CartBarFragment newInstance(String type) {
        CartBarFragment fragment = new CartBarFragment();
        Bundle args = new Bundle();
        args.putString(Keys.TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            type = getArguments().getString(Keys.TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cart_bar, container, false);

        initCart();
        initBar();

        return rootView;
    }

    private void initCart(){
        switch (type){
            case Cart.TYPE_FOOD:
                cart = Sales.getFoodCart();
                break;
            case Cart.TYPE_SHOPPING:
                cart = Sales.getShoppingCart();
            break;
        }
    }

    private void initBar(){
        shoppingCartWrapper = rootView.findViewById(R.id.shopping_cart_wrapper);
        shoppingCartCounter = rootView.findViewById(R.id.shopping_cart_counter);
        shoppingCartTotal = rootView.findViewById(R.id.shopping_cart_total);

        shoppingCartCounter.setText(String.valueOf(cart.getQuantity()));

        double total = cart.getTotalPrice();
        if(total > 0){
            shoppingCartTotal.setText("₩"+total);
        }
        else{
            shoppingCartTotal.setText("");
        }


        cart.addOnUpdateListener(new CartUpdateListener() {
            @Override
            public void onCartUpdate(Cart cart) {
                shoppingCartCounter.setText(String.valueOf(cart.getQuantity()));

                if(cart.getTotalPrice() > 0){
                    shoppingCartTotal.setText("₩"+cart.getTotalPrice());
                }
                else{
                    shoppingCartTotal.setText("");
                }
            }
        });

        shoppingCartWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Coordinator.getSalesNavigator().navigateToCart(type);
            }
        });
    }
}