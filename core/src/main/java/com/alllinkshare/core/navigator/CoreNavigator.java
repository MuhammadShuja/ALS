package com.alllinkshare.core.navigator;

public interface CoreNavigator {
    void navigateToSearch(String query);
    void navigateToHome(String fragmentToLoad);
    void navigateToSocial();
    void navigateToScan();
    void navigateToVoice();
    void navigateToMap(String latitude, String longitude);
}