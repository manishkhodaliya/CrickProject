package com.example.blackhat.mlive.apiutils;

import android.util.Log;

import com.example.blackhat.mlive.pojo.HttpCallResponse;
import com.example.blackhat.mlive.pojo.Profile;
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
 * Created by DigitalDesk on 3/29/2017.
 */

public class ProfileUtils {

    private static final String TAG = "ProfileUtils";

    public static HttpCallResponse getProfile(String url, String user_id) {

        Log.i(TAG, "getProfile:  ---->  Enter ");

        HttpResponse httpResponse = null;
        HttpCallResponse httpCallResponse = null;
        String responseBody = "";
        //  User user = null;
        List<Profile> profiles = null;
        try {
            List<NameValuePair> pair=new ArrayList<NameValuePair>();


            pair.add(new BasicNameValuePair("u", user_id));
            httpResponse = Utility.postRequest(url, pair);


            responseBody = EntityUtils.toString(httpResponse.getEntity());


            Log.i(TAG, "getProfile:  ---->  Response ->  " + responseBody);
            if (!Utility.isEmpty(responseBody)) {
                httpCallResponse = ApplicationUtils.generateObjectFromJSON(responseBody, HttpCallResponse.class);

            }


        } catch (IOException e) {
            Log.i(TAG, "getProfile:  ---->  error ->  " + e);
            e.printStackTrace();
        }

        Log.i(TAG, "getProfile:  ---->  Exit ");
        return httpCallResponse;
                //ApplicationUtils.generateObjectFromJSON(
                //ApplicationUtils.generateJSONFromObject(profiles.get(0)),Profile.class);

    }

}
