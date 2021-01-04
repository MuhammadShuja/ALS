package com.alllinkshare.core.retrofit;

import com.alllinkshare.core.utils.NetworkConnectivity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkConnectionInterceptor implements Interceptor {

    public NetworkConnectionInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if(!NetworkConnectivity.isConnected())
            throw new NoConnectivityException();

        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }

    public static class NoConnectivityException extends IOException {

        @Override
        public String getMessage() {
            return "No network available, please check your WiFi or Data connection";
        }
    }
}