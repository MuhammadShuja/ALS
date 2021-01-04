package com.alllinkshare.sales;

import com.alllinkshare.sales.cart.models.Cart;
import com.alllinkshare.sales.cart.models.Order;

public class Sales {
    private static final String TAG = "API/Sales";

    private static final Cart foodCart = new Cart(Cart.TYPE_FOOD);
    private static final Cart shoppingCart = new Cart(Cart.TYPE_SHOPPING);

    public static Order activeOrder = null;

    public static Cart getFoodCart() {
        return foodCart;
    }

    public static Cart getShoppingCart() {
        return shoppingCart;
    }
}