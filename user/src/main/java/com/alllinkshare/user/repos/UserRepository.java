package com.alllinkshare.user.repos;

import android.util.Log;

import com.alllinkshare.auth.events.LogoutEvent;
import com.alllinkshare.auth.events.LogoutListener;
import com.alllinkshare.pushnotifications.PN;
import com.alllinkshare.user.api.API;
import com.alllinkshare.user.api.config.Listeners;
import com.alllinkshare.user.events.ProfileUpdateEvent;
import com.alllinkshare.user.events.ProfileUpdateListener;
import com.alllinkshare.user.models.User;

public class UserRepository {
    private static final String TAG = "Repo/User";

    private static User profile = null;

    private static UserRepository instance = null;

    public static synchronized UserRepository getInstance(){
        if(instance == null){
            instance = new UserRepository();
        }
        return instance;
    }

    private UserRepository(){
        new LogoutEvent().addOnLogoutListener(new LogoutListener() {
            @Override
            public void onLogout() {
                clearRepository();
            }
        });
        Log.d(TAG, "New instance created...");
    }

    public void getProfile(final ProfileReadyListener listener){
        if(profile != null){
            listener.onProfileReady(profile);
        }
        else{
            API.profile(new Listeners.ProfileListener() {
                @Override
                public void onSuccess(final User userProfile) {
                    profile = userProfile;
                    listener.onProfileReady(userProfile);

                    if(userProfile.getFcmToken() == null){
                        PN.getToken();
                    }
                }

                @Override
                public void onFailure(String error) {
                    listener.onFailure(error);
                }
            });
        }
        Log.d(TAG, "Get user profile completed...");

    }

    public void updateProfile(final User user, final ProfileReadyListener listener){
        API.updateProfile(user, new Listeners.ProfileListener() {
            @Override
            public void onSuccess(User userProfile) {
                profile = userProfile;
                listener.onProfileReady(userProfile);
                new ProfileUpdateEvent().fire(userProfile);
            }

            @Override
            public void onFailure(String error) {
                listener.onFailure(error);
            }
        });

        Log.d(TAG, "Update user profile completed...");
    }

    public void setFcmToken(String fcmToken){
        if(profile != null){
            profile.setFcmToken(fcmToken);
        }
    }

    private void clearRepository(){
        profile = null;
        Log.d(TAG, "Repository cleared...");
    }

    public interface ProfileReadyListener{
        void onProfileReady(User profile);
        void onFailure(String error);
    }
}