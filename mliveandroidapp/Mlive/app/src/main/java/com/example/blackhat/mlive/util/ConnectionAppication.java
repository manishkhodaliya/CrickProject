package com.example.blackhat.mlive.util;

import android.app.Application;

/**
 * Created by BlackHat on 29/03/2017.
 */
public class ConnectionAppication extends Application {
    private static ConnectionAppication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized ConnectionAppication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
