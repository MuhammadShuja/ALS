package com.alllinkshare.auth.events;

import java.util.ArrayList;
import java.util.List;

public class LogoutEvent implements LogoutListener{
    public static List<LogoutListener> listeners = new ArrayList<>();

    public void addOnLogoutListener(LogoutListener listener){
        listeners.add(listener);
    }

    public void removeOnLogoutListener(LogoutListener listener){
        listeners.remove(listener);
    }

    public void fire(){
        onLogout();
    }

    @Override
    public void onLogout() {
        for(LogoutListener listener : listeners){
            listener.onLogout();
        }
    }
}