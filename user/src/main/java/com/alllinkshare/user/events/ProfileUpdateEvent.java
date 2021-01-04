package com.alllinkshare.user.events;

import com.alllinkshare.user.models.User;

import java.util.ArrayList;
import java.util.List;

public class ProfileUpdateEvent implements ProfileUpdateListener {
    public static List<ProfileUpdateListener> listeners = new ArrayList<>();

    public void addOnUpdateListener(ProfileUpdateListener listener){
        listeners.add(listener);
    }

    public void removeOnUpdateListener(ProfileUpdateListener listener){
        listeners.remove(listener);
    }

    public void fire(User user){
        onUpdate(user);
    }

    @Override
    public void onUpdate(User user) {
        for(ProfileUpdateListener listener : listeners){
            listener.onUpdate(user);
        }
    }
}