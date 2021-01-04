package com.alllinkshare.sales.cart.models;

import com.alllinkshare.sales.cart.listeners.CartItemListener;
import com.alllinkshare.sales.cart.listeners.CartUpdateListener;

import java.util.ArrayList;
import java.util.List;

public class Cart implements CartUpdateListener {
    public static final String TYPE_SHOPPING = "shopping";
    public static final String TYPE_FOOD = "restaurant";

    private final List<CartItem> items = new ArrayList<>();
    private final List<CartItem> temporaryItems = new ArrayList<>();
    private int quantity = 0;
    private double totalPrice = 0;
    private final String type;

    public List<CartUpdateListener> listeners = new ArrayList<>();

    public void addOnUpdateListener(CartUpdateListener listener){
        listeners.add(listener);
    }

    public void removeOnUpdateListener(CartUpdateListener listener){
        listeners.remove(listener);
    }

    private void fire(Cart cart){
        onCartUpdate(cart);
    }

    @Override
    public void onCartUpdate(Cart cart) {
        for(CartUpdateListener listener : listeners){
            listener.onCartUpdate(cart);
        }
    }

    private CartItemListener cartItemListener = new CartItemListener() {
        @Override
        public void onItemUpdate(CartItem item) {
            if (item.getQuantity() == 0) {
                items.remove(item);
                temporaryItems.add(item);
            } else {
                if (temporaryItems.contains(item)) {
                    temporaryItems.remove(item);
                    items.add(item);
                }
            }
            Cart.this.updateCart();
        }
    };

    public Cart(String type) {
        this.type = type;
    }

    public CartItem addItem(String uuid, int listingId, int id, String name, String thumbnail, double price, int quantity){
        CartItem item = findItem(uuid);
        if(item == null){
            item = new CartItem(uuid, listingId, id, name, thumbnail, price, quantity, cartItemListener);
            items.add(item);
        }
        updateCart();

        return item;
    }

    public CartItem findItem(String uuid){
        for(CartItem item : items){
            if(item.getUuid().equals(uuid))
                return item;
        }
        return null;
    }

    public void removeItem(String uuid){
        int index = 0;
        for(CartItem item : items){
            if(item.getUuid().equals(uuid)){
                items.remove(index);
                break;
            }
            index++;
        }
        updateCart();
    }

    public int getItemCount() {
        return items.size();
    }

    public String getType() {
        return type;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void clearCart(){
        items.clear();
        updateCart();
    }

    private void updateCart(){
        int q = 0;
        double p = 0;
        for(CartItem item : items){
            q += item.getQuantity();
            p += item.getSubTotal();
        }
        this.quantity = q;
        this.totalPrice = p;

        fire(this);
    }

    public String getListingIds(){
        StringBuilder listingIds = new StringBuilder();
        for(CartItem item : items){
            listingIds.append(item.getListingId()).append(",");
        }
        return listingIds.toString();
    }

    public Order createOrder(){
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem item : items){
            orderItems.add(
                new OrderItem(
                    item.getListingId(),
                    item.getId(),
                    item.getName(),
                    item.getThumbnail(),
                    item.getQuantity(),
                    item.getPrice(),
                    item.getSubTotal(),
                    item.getColor(),
                    item.getSize(),
                    item.getFreeGift()
                )
            );
        }

        return new Order(
                getType(),
                getQuantity(),
                getTotalPrice(),
                orderItems
        );
    }
}