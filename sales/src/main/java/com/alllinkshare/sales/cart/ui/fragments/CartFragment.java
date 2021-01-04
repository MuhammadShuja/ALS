package com.alllinkshare.sales.cart.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alllinkshare.auth.ui.activities.LoginActivity;
import com.alllinkshare.core.navigator.Coordinator;
import com.alllinkshare.core.navigator.Keys;
import com.alllinkshare.core.utils.GifImageView;
import com.alllinkshare.sales.cart.ui.activities.CheckoutActivity;
import com.alllinkshare.sales.R;
import com.alllinkshare.sales.Sales;
import com.alllinkshare.sales.cart.listeners.CartUpdateListener;
import com.alllinkshare.sales.cart.models.Cart;
import com.alllinkshare.sales.cart.models.CartItem;
import com.alllinkshare.sales.cart.ui.adapters.CartAdapter;
import com.alllinkshare.user.api.API;

import java.util.Objects;

public class CartFragment extends Fragment {

    private Cart cart;
    private String type;

    private View rootView;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance(String type) {
        CartFragment fragment = new CartFragment();
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
        rootView = inflater.inflate(R.layout.fragment_cart, container, false);

        initCart();
        initActions();

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

        ((TextView) rootView.findViewById(R.id.cart_counter))
                .setText("Your cart contains "+cart.getQuantity()+" items");

        ((TextView) rootView.findViewById(R.id.total_price)).setText("₩"+cart.getTotalPrice());

        final CartAdapter cartAdapter = new CartAdapter(getContext(), cart.getItems(), new CartAdapter.OnItemClickListener() {
            @Override
            public void onCounterPlus(CartItem cartItem) {

            }

            @Override
            public void onCounterMinus(CartItem cartItem) {

            }

            @Override
            public void onItemRemove(CartItem cartItem) {
                cart.removeItem(cartItem.getUuid());
            }
        });

        final RecyclerView rvItems = rootView.findViewById(R.id.rv_items);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        DividerItemDecoration verticalDivider = new DividerItemDecoration(
                Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);

        verticalDivider.setDrawable(
                Objects.requireNonNull(
                        ContextCompat.getDrawable(getContext(), R.drawable.divider)));
        rvItems.addItemDecoration(verticalDivider);
        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(cartAdapter);

        if(cart.getQuantity() < 1){
            rootView.findViewById(R.id.empty_wrapper).setVisibility(View.VISIBLE);

            GifImageView gifImageView = rootView.findViewById(R.id.gif_image_view);
            gifImageView.setGifImageResource(R.drawable.empty);
        }

        rootView.findViewById(R.id.btn_clear_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.clearCart();
            }
        });

        cart.addOnUpdateListener(new CartUpdateListener() {
            @Override
            public void onCartUpdate(Cart cart) {
                ((TextView) rootView.findViewById(R.id.cart_counter))
                        .setText("Your cart contains "+cart.getQuantity()+" items");

                ((TextView) rootView.findViewById(R.id.total_price)).setText("₩"+cart.getTotalPrice());

                cartAdapter.setData(cart.getItems());

                if(cart.getQuantity() < 1){
                    rootView.findViewById(R.id.empty_wrapper).setVisibility(View.VISIBLE);

                    GifImageView gifImageView = rootView.findViewById(R.id.gif_image_view);
                    gifImageView.setGifImageResource(R.drawable.empty);
                }
            }
        });
    }

    private void initActions(){
        rootView.findViewById(R.id.btn_checkout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(API.isAuthenticated()){
                    if(cart.getTotalPrice() >= 20000){
                        Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                        intent.putExtra(Keys.TYPE, type);
                        getActivity().startActivity(intent);
                    }
                    else{
                        Toast.makeText(getContext(), "Minimum order: ₩ 20,000", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });

        rootView.findViewById(R.id.btn_continue_shopping).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (cart.getType()){
                    case Cart.TYPE_FOOD:
                        Coordinator.getCatalogNavigator().navigateToCategory(58, false);
                        break;
                    case Cart.TYPE_SHOPPING:
                        Coordinator.getCatalogNavigator().navigateToCategory(59, false);
                        break;
                }
            }
        });
    }
}