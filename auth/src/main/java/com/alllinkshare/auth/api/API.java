package com.alllinkshare.auth.api;

import android.util.Log;

import com.alllinkshare.auth.api.config.Listeners;
import com.alllinkshare.auth.api.controllers.LoginController;
import com.alllinkshare.auth.api.controllers.LogoutController;
import com.alllinkshare.auth.api.controllers.PasswordResetController;
import com.alllinkshare.auth.api.controllers.RegisterController;
import com.alllinkshare.auth.api.controllers.ResendCodeController;
import com.alllinkshare.auth.api.controllers.VerifyCodeController;
import com.alllinkshare.auth.api.controllers.VerifyEmailController;
import com.alllinkshare.auth.api.controllers.VerifyTokenController;
import com.alllinkshare.auth.models.User;
import com.alllinkshare.core.utils.SPM;

public class API {
    private static final String TAG = "API/Auth";

    public static boolean isAuthenticated(){
        String token = SPM.getInstance().get(SPM.ACCESS_TOKEN, null);
        int userStatus = SPM.getInstance().get(SPM.USER_STATUS, 0);

        return token != null && userStatus == 1;
    }

    public static void login(String userType, String username, String password, Listeners.AuthListener listener){
        Log.d(TAG, "Calling login endpoint...");
        LoginController.login(userType, username, password, listener);
    }

    public static void register(User user, Listeners.AuthListener listener){
        Log.d(TAG, "Calling register endpoint...");
        RegisterController.register(user, listener);
    }

    public static void verifyEmail(String email,  Listeners.AuthListener listener) {
        Log.d(TAG, "Calling verify email endpoint...");
        VerifyEmailController.verify(email,  listener);
    }

    public static void verifyCode(String code, Listeners.AuthListener listener){
        Log.d(TAG, "Calling verify code endpoint...");
        VerifyCodeController.verify(code, listener);
    }

    public static void resendCode(Listeners.AuthListener listener){
        Log.d(TAG, "Calling resend code endpoint...");
        ResendCodeController.resend(listener);
    }

    public static void verifyToken(String token, Listeners.AuthListener listener){
        Log.d(TAG, "Calling verify token endpoint...");
        VerifyTokenController.verify(token, listener);
    }

    public static void resetPassword(String password, String passwordConfirmation, Listeners.AuthListener listener){
        Log.d(TAG, "Calling password reset endpoint...");
        PasswordResetController.reset(password, passwordConfirmation, listener);
    }

    public static void logout(Listeners.AuthListener listener){
        Log.d(TAG, "Calling logout endpoint...");
        LogoutController.logout(listener);
    }
}