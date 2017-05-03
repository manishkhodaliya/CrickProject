package com.example.blackhat.mlive.apiutils;

import android.util.Log;

import com.example.blackhat.mlive.pojo.HttpCallResponse;
import com.example.blackhat.mlive.pojo.User;
import com.example.blackhat.mlive.util.ApplicationUtils;
import com.example.blackhat.mlive.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by DigitalDesk on 3/26/2017.
 */

public class UserUtils {
    private static final String TAG = "UserUtils";

    public static HttpCallResponse doLogin(String url,String username,String password) {

        Log.i(TAG, "doLogin:  ---->  Enter ");

        HttpResponse httpResponse = null;
        String responseBody = "";
        HttpCallResponse httpCallResponse = null;
      //  User user = null;
        List<User> users = null;
        try {
            List<NameValuePair> pair=new ArrayList<NameValuePair>();

            pair.add(new BasicNameValuePair("u", username));
            pair.add(new BasicNameValuePair("p",password));

            httpResponse = Utility.postRequest(url, pair);

            responseBody = EntityUtils.toString(httpResponse.getEntity());
            System.out.println( "doLogin"+responseBody );
            Log.i(TAG, "doLogin:  ---->  Response ->  " + responseBody);
            if (!Utility.isEmpty(responseBody)) {
                httpCallResponse  = ApplicationUtils.generateObjectFromJSON(responseBody, HttpCallResponse.class);
            }


        } catch (Exception e) {
            Log.i(TAG, "doLogin:  ---->  error ->  " + e);
            e.printStackTrace();
        }

        Log.i(TAG, "doLogin:  ---->  Exit ");
        return httpCallResponse;

    }
}
