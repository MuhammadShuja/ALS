package com.alllinkshare.user.events;

import com.alllinkshare.user.models.User;

public interface ProfileUpdateListener {
    void onUpdate(User user);
}