package com.alllinkshare.sales.cart.listeners;

import com.alllinkshare.sales.cart.models.CartItem;

public interface CartItemListener {
    void onItemUpdate(CartItem item);
}