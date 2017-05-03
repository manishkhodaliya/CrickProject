package com.example.blackhat.mlive.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.blackhat.mlive.R;
import com.example.blackhat.mlive.util.AppConstant;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

public class MarketDepth extends AppCompatActivity {

    private WebSocketConnection mConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_depth);

       // start();

    }

    public void start()
    {

        final String wsuri= AppConstant.WS+AppConstant.MARKETDEPTHURI+"1956"+AppConstant.ANDROID_ID+"1"+
                AppConstant.SCRIPTNAME+"BANKNIFTY 27APR2017";
        System.out.println("wsuri="+wsuri);

        try {
            mConnection.connect(wsuri,new WebSocketHandler()
            {
                @Override
                public void onOpen() {
                    Log.d("Connection ", "Status: Connected to " + wsuri);
                }

                @Override
                public void onTextMessage(String message) {
                    Log.d("Connection ", "Message " + message);
                }


                @Override
                public void onClose(int code, String reason) {
                    super.onClose(code, reason);
                }

            });
        } catch (WebSocketException e) {
            e.printStackTrace();
        }


    }


}
