package com.alllinkshare.auth.events;

import java.util.ArrayList;
import java.util.List;

public class LoginEvent implements LoginListener{
    public static List<LoginListener> listeners = new ArrayList<>();

    public void addOnLoginListener(LoginListener listener){
        listeners.add(listener);
    }

    public void removeOnLoginListener(LoginListener listener){
        listeners.remove(listener);
    }

    public void fire(){
        onLogin();
    }

    @Override
    public void onLogin() {
        for(LoginListener listener : listeners){
            listener.onLogin();
        }
    }
}