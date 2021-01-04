package com.alllinkshare.user.api;

import android.graphics.Bitmap;
import android.util.Log;

import com.alllinkshare.user.api.config.Listeners;
import com.alllinkshare.user.api.controllers.CouponsController;
import com.alllinkshare.user.api.controllers.CredentialsController;
import com.alllinkshare.user.api.controllers.FavouritesController;
import com.alllinkshare.user.api.controllers.FcmController;
import com.alllinkshare.user.api.controllers.LocationController;
import com.alllinkshare.user.api.controllers.ProfileController;
import com.alllinkshare.user.models.Coupon;
import com.alllinkshare.user.models.User;

import java.io.File;

public class API {
    private static final String TAG = "API/User";

    public static boolean isAuthenticated(){
        return com.alllinkshare.auth.api.API.isAuthenticated();
    }

    public static void countries(Listeners.CountriesListener listener){
        Log.d(TAG, "Calling countries endpoint...");
        LocationController.countries(listener);
    }

    public static void states(int countryId, Listeners.StatesListener listener){
        Log.d(TAG, "Calling states endpoint...");
        LocationController.states(countryId, listener);
    }

    public static void cities(int stateId, Listeners.CitiesListener listener){
        Log.d(TAG, "Calling cities endpoint...");
        LocationController.cities(stateId, listener);
    }

    public static void profile(Listeners.ProfileListener listener){
        Log.d(TAG, "Calling get profile endpoint...");
        ProfileController.get(listener);
    }

    public static void updateProfile(User user, Listeners.ProfileListener listener){
        Log.d(TAG, "Calling update profile endpoint...");
        ProfileController.update(user, listener);
    }

    public static void updateProfilePicture(File file, Listeners.ProfilePictureListener listener){
        Log.d(TAG, "Calling update profile picture endpoint...");
        ProfileController.profilePicture(file, listener);
    }

    public static void updateMobileNumberRequest(final String currentMobileNumber, final String newMobileNumber, Listeners.CredentialsListener listener){
        Log.d(TAG, "Calling update mobile request endpoint...");
        CredentialsController.updateMobileNumberRequest(currentMobileNumber, newMobileNumber, listener);
    }

    public static void updateMobileNumber(final String currentMobileNumber, final String newMobileNumber, final String verificationCode, Listeners.CredentialsListener listener){
        Log.d(TAG, "Calling update mobile endpoint...");
        CredentialsController.updateMobileNumber(currentMobileNumber, newMobileNumber, verificationCode, listener);
    }

    public static void updateEmailRequest(final String currentEmail, final String newEmail, Listeners.CredentialsListener listener){
        Log.d(TAG, "Calling update email request endpoint...");
        CredentialsController.updateEmailRequest(currentEmail, newEmail, listener);
    }

    public static void updateEmail(final String currentEmail, final String newEmail, final String verificationCode, Listeners.CredentialsListener listener){
        Log.d(TAG, "Calling update email endpoint...");
        CredentialsController.updateEmail(currentEmail, newEmail, verificationCode, listener);
    }

    public static void updatePassword(final String currentPassword, final String newPassword, Listeners.CredentialsListener listener){
        Log.d(TAG, "Calling update password endpoint...");
        CredentialsController.updatePassword(currentPassword, newPassword, listener);
    }

    public static void updateFcmToken(final String token, Listeners.FcmTokenListener listener){
        Log.d(TAG, "Calling update fcm token endpoint...");
        FcmController.updateFcmToken(token, listener);
    }

    public static void favourites(int pageNumber, Listeners.FavouritesListener listener){
        Log.d(TAG, "Calling favourites endpoint...");
        FavouritesController.favourites(pageNumber, listener);
    }

    public static void removeFavourite(int itemId, Listeners.RemoveFavouriteListener listener){
        Log.d(TAG, "Calling remove favourite endpoint...");
        FavouritesController.remove(itemId, listener);
    }

    public static void coupons(int pageNumber, Listeners.CouponsListener listener){
        Log.d(TAG, "Calling coupons endpoint...");
        CouponsController.coupons(pageNumber, listener);
    }

    public static void coupon(int couponId, Listeners.CouponListener listener){
        Log.d(TAG, "Calling get coupon endpoint...");
        CouponsController.coupon(couponId, listener);
    }

    public static void updateCoupon(Coupon coupon, Listeners.CouponListener listener){
        Log.d(TAG, "Calling update coupon endpoint...");
        CouponsController.update(coupon, listener);
    }
}