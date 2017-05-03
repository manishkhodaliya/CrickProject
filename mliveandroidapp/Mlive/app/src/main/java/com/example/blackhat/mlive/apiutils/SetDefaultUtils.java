package com.example.blackhat.mlive.apiutils;

import android.util.Log;

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
 * Created by DigitalDesk on 19/04/2017.
 */

public class SetDefaultUtils {
    private static final String TAG = "SetDefaultUtils";

    public static int setDefault(String url, String user_id, String profile_id) {

        Log.i(TAG, "doLogin:  ---->  Enter ");

        HttpResponse httpResponse = null;
        int responseBody =0;
        //  User user = null;
        List<User> users = null;
        try {
            List<NameValuePair> pair=new ArrayList<NameValuePair>();


            pair.add(new BasicNameValuePair("userid", user_id));
            pair.add(new BasicNameValuePair("profileid",profile_id));
            httpResponse = Utility.postRequest(url, pair);

            responseBody =Integer.parseInt(EntityUtils.toString(httpResponse.getEntity()));

        } catch (IOException e) {
            Log.i(TAG, "doLogin:  ---->  error ->  " + e);
            e.printStackTrace();
        }

        Log.i(TAG, "doLogin:  ---->  Exit ");
        return responseBody;

    }
}
