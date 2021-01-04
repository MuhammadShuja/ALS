package com.alllinkshare.restaurant.ui.fragments;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alllinkshare.core.navigator.Keys;
import com.alllinkshare.core.utils.GlideOptions;
import com.alllinkshare.gallery.models.Image;
import com.alllinkshare.gallery.ui.fragments.GalleryGridFragment;
import com.alllinkshare.restaurant.R;
import com.alllinkshare.restaurant.api.API;
import com.alllinkshare.restaurant.api.config.Listeners;
import com.alllinkshare.restaurant.models.FoodFreeGift;
import com.alllinkshare.restaurant.models.FoodMenuItem;
import com.alllinkshare.restaurant.models.FoodTopping;
import com.alllinkshare.restaurant.ui.adapters.FoodGiftAdapter;
import com.alllinkshare.restaurant.ui.adapters.FoodToppingsAdapter;
import com.alllinkshare.reviews.ui.fragments.ReviewsFragment;
import com.alllinkshare.sales.Sales;
import com.alllinkshare.sales.cart.models.Cart;
import com.alllinkshare.sales.cart.models.CartItem;
import com.alllinkshare.sales.cart.ui.fragments.CartBarFragment;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Objects;

public class FoodItemFragment extends Fragment {

    private int itemId;

    private ImageView thumbnail, btnCartMinus, btnCartPlus;
    private TextView name, price, deliveryFee, options;
    private LinearLayout loadingWrapper, contentWrapper, cartWrapper;
    private Button btnAddToCart;
    private EditText cartCounter;
    private WebView description;

    private FoodMenuItem foodMenuItem = null;

    private RecyclerView rvToppings;
    private FoodToppingsAdapter foodToppingsAdapter;
    private FoodGiftAdapter foodGiftAdapter;

    private View rootView;

    public FoodItemFragment() {
        // Required empty public constructor
    }

    public static FoodItemFragment newInstance(int itemId) {
        FoodItemFragment fragment = new FoodItemFragment();
        Bundle args = new Bundle();
        args.putInt(Keys.ITEM_ID, itemId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            itemId = getArguments().getInt(Keys.ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_food_item, container, false);

        initCartBar();
        initItem();
        loadItem();

        return rootView;
    }

    private void initCartBar(){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.cart_bar_host, CartBarFragment.newInstance(Cart.TYPE_FOOD))
                .commit();
    }

    private void initItem(){
        initCart();
        thumbnail = rootView.findViewById(R.id.thumbnail);

        name = rootView.findViewById(R.id.name);
        price = rootView.findViewById(R.id.price);
        deliveryFee = rootView.findViewById(R.id.delivery_fee);
        options = rootView.findViewById(R.id.options);
        description = rootView.findViewById(R.id.description);

        foodToppingsAdapter = new FoodToppingsAdapter(getContext(), new ArrayList<FoodTopping>(), new FoodToppingsAdapter.OnItemClickListener() {
            @Override
            public void onCounterPlus(FoodTopping topping) {
                CartItem cartItem = Sales.getFoodCart().findItem(topping.getUuid());
                if(cartItem == null){
                    Sales.getFoodCart().addItem(
                            topping.getUuid(),
                            foodMenuItem.getListingId(),
                            foodMenuItem.getId(),
                            topping.getName(),
                            topping.getThumbnail(),
                            Double.parseDouble(topping.getPrice()),
                            1
                    );
                }
                else{
                    cartItem.increaseQuantity(1);
                }
            }

            @Override
            public void onCounterMinus(FoodTopping topping) {
                CartItem cartItem = Sales.getFoodCart().findItem(topping.getUuid());
                if(cartItem != null){
                    cartItem.decreaseQuantity(1);
                }
            }
        });
        foodGiftAdapter = new FoodGiftAdapter(getContext(), new ArrayList<FoodFreeGift>(), new FoodGiftAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FoodFreeGift gift) {
                if(Sales.getFoodCart().findItem(foodMenuItem.getUuid()) != null)
                    Sales.getFoodCart().findItem(foodMenuItem.getUuid()).setFreeGift(gift.getName());
            }
        });

        DividerItemDecoration verticalDivider = new DividerItemDecoration(
                Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);

        verticalDivider.setDrawable(
                Objects.requireNonNull(
                        ContextCompat.getDrawable(getContext(), R.drawable.divider)));

        rvToppings = rootView.findViewById(R.id.rv_toppings);
        rvToppings.addItemDecoration(verticalDivider);
        rvToppings.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        rvToppings.setAdapter(foodToppingsAdapter);

        final RecyclerView rvGifts = rootView.findViewById(R.id.rv_gifts);
        rvGifts.addItemDecoration(verticalDivider);
        rvGifts.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        rvGifts.setAdapter(foodGiftAdapter);

        loadingWrapper = rootView.findViewById(R.id.loading_wrapper);
        contentWrapper = rootView.findViewById(R.id.content_wrapper);
    }

    private void loadItem(){
        API.item(itemId, new Listeners.ItemListener() {
            @Override
            public void onSuccess(FoodMenuItem foodItem) {
                foodMenuItem = foodItem;
                if(getContext() != null){
                    Glide
                            .with(getContext())
                            .load(foodItem.getThumbnail())
                            .thumbnail(0.25f)
                            .apply(GlideOptions.getProductOptions())
                            .into(thumbnail);

                    name.setText(foodItem.getName());
                    price.setText("KRW "+foodItem.getPrice());
                    deliveryFee.setText(foodItem.getDeliveryFee());
                    options.setText(HtmlCompat.fromHtml(foodItem.getOptions(), HtmlCompat.FROM_HTML_MODE_LEGACY));
                    description.loadData(foodItem.getDescription()
                            ,null, null);

                    foodToppingsAdapter.setData(foodItem.getToppings());
                    foodGiftAdapter.setData(foodItem.getFreeGifts());
                    foodToppingsAdapter.setItemUuid(foodItem.getUuid());

                    initInfoTabs(foodItem);

                    CartItem itemInCart = Sales.getFoodCart().findItem(foodMenuItem.getUuid());
                    if(itemInCart != null){
                        cartCounter.setText(String.valueOf(itemInCart.getQuantity()));
                    }

                    contentWrapper.setVisibility(View.VISIBLE);
                    loadingWrapper.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initCart(){
        cartWrapper = rootView.findViewById(R.id.cart_wrapper);
        btnCartMinus = rootView.findViewById(R.id.btn_minus);
        btnCartPlus = rootView.findViewById(R.id.btn_plus);
        cartCounter = rootView.findViewById(R.id.counter);
        btnAddToCart = rootView.findViewById(R.id.btn_add_to_cart);

        btnCartPlus.setOnClickListener(v -> {
            int count = 0;
            if (!cartCounter.getText().toString().isEmpty()
                    && TextUtils.isDigitsOnly(cartCounter.getText().toString())) {
                int t = Integer.parseInt(cartCounter.getText().toString());
                if (t + 1 > -1) {
                    count = t + 1;
                }
            }

            cartCounter.setText(String.valueOf(count));
        });

        btnCartMinus.setOnClickListener(v -> {
            int count = 0;
            if (!cartCounter.getText().toString().isEmpty()
                    && TextUtils.isDigitsOnly(cartCounter.getText().toString())) {
                int t = Integer.parseInt(cartCounter.getText().toString());
                if (t - 1 > -1) {
                    count = t - 1;
                }
            }

            cartCounter.setText(String.valueOf(count));
        });

        cartCounter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isDigitsOnly(s.toString())
                        || s.toString().isEmpty()) {
                    cartCounter.setText("0");
                }
            }
        });

        btnAddToCart.setOnClickListener(v -> {
            int quantity = Integer.parseInt(cartCounter.getText().toString());
            CartItem item = Sales.getFoodCart().findItem(foodMenuItem.getUuid());
            if(item == null){
                if(quantity > 0){
                    Sales.getFoodCart().addItem(
                            foodMenuItem.getUuid(),
                            foodMenuItem.getListingId(),
                            foodMenuItem.getId(),
                            foodMenuItem.getName(),
                            foodMenuItem.getThumbnail(),
                            Double.parseDouble(foodMenuItem.getPrice()),
                            quantity
                    );
                }
            }
            else{
                if(quantity == 0){
                    int index = 0;
                    for(FoodTopping topping : foodMenuItem.getToppings()){
                        Sales.getFoodCart().removeItem(topping.getUuid());
                        foodToppingsAdapter.clearCartSelections(
                                (FoodToppingsAdapter.ViewHolder) rvToppings.findViewHolderForAdapterPosition(index));
                        index++;
                    }
                }
                item.setQuantity(quantity);
            }
        });
    }

    private void initInfoTabs(final FoodMenuItem item){
        TabLayout tabLayout = rootView.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Product information"));
        tabLayout.addTab(tabLayout.newTab().setText("Product images"));
        tabLayout.addTab(tabLayout.newTab().setText("Product reviews"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                switch (position){
                    case 0:
                        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_info_host,
                                        FoodItemInformationFragment.newInstance(
                                                item.getCategoryName(),
                                                item.getName()
                                        ))
                                .commit();
                        break;

                    case 1:
                        ArrayList<Image> images = new ArrayList<>();
                        images.add(new Image(-1, item.getThumbnail()));

                        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_info_host,
                                        GalleryGridFragment.newInstance(images, 1))
                                .commit();
                        break;

                    case 2:
                        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_info_host,
                                        ReviewsFragment.newInstance(item.getRating(), item.getReviews()))
                                .commit();
                        break;

//                    case 3:
//                        Coordinator.getCatalogNavigator().navigateToListing(
//                                product.getListingId(), product.getMainCategoryId());
//                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.item_info_host,
                        FoodItemInformationFragment.newInstance(
                                item.getCategoryName(),
                                item.getName()
                        ))
                .commit();
    }
}