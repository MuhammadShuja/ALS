package com.alllinkshare.user.api.config;

import com.alllinkshare.user.models.City;
import com.alllinkshare.user.models.Country;
import com.alllinkshare.user.models.Coupon;
import com.alllinkshare.user.models.FavouriteItem;
import com.alllinkshare.user.models.Order;
import com.alllinkshare.user.models.State;
import com.alllinkshare.user.models.User;

import java.util.List;

public class Listeners {

    public interface ProfileListener{
        void onSuccess(User userProfile);
        void onFailure(String error);
    }

    public interface ProfilePictureListener{
        void onSuccess(String profilePicture);
        void onFailure(String error);
    }

    public interface CredentialsListener{
        void onSuccess(String message);
        void onFailure(String error);
    }

    public interface FcmTokenListener{
        void onSuccess(String token);
        void onFailure(String error);
    }

    public interface FavouritesListener{
        void onSuccess(List<FavouriteItem> itemList, int currentPageNumber, int lastPageNumber, int totalItemCount);
        void onFailure(String error);
    }

    public interface RemoveFavouriteListener{
        void onSuccess(String message);
        void onFailure(String error);
    }

    public interface CouponsListener{
        void onSuccess(List<Coupon> itemList, int currentPageNumber, int lastPageNumber, int totalItemCount);
        void onFailure(String error);
    }

    public interface CouponListener{
        void onSuccess(Coupon coupon);
        void onFailure(String error);
    }

    public interface OrdersListener{
        void onSuccess(List<Order> itemList, int currentPageNumber, int lastPageNumber, int totalItemCount);
        void onFailure(String error);
    }

    public interface CountriesListener{
        void onSuccess(List<Country> countries);
        void onFailure(String error);
    }

    public interface StatesListener{
        void onSuccess(List<State> states);
        void onFailure(String error);
    }

    public interface CitiesListener {
        void onSuccess(List<City> cities);
        void onFailure(String error);
    }
}