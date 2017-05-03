package com.example.blackhat.mlive.apiutils;

import android.util.Log;

import com.example.blackhat.mlive.pojo.Portfolio;
import com.example.blackhat.mlive.pojo.Profile;
import com.example.blackhat.mlive.util.ApplicationUtils;
import com.example.blackhat.mlive.util.PortfolioUtility;
import com.example.blackhat.mlive.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by DigitalDesk on 4/14/2017.
 */

public class PortfolioUtils {
    private static final String TAG = "PortfolioUtils";

    public static List<Portfolio> getProfile(String url, String user_id,String profile_id) {

        Log.i(TAG, "getProfile:  ---->  Enter ");

        HttpResponse httpResponse = null;
        String responseBody = "";
        //  User user = null;
        List<Portfolio> profiles = null;
        try {
            List<NameValuePair> pair=new ArrayList<NameValuePair>();

            pair.add(new BasicNameValuePair("userid", user_id));
            pair.add(new BasicNameValuePair("profileid",profile_id));
            httpResponse = PortfolioUtility.postRequest(url, pair);


            responseBody = EntityUtils.toString(httpResponse.getEntity());


            Log.i(TAG, "getProfile:  ---->  Response ->  " + responseBody);
            if (!Utility.isEmpty(responseBody)) {
                profiles = ApplicationUtils.generateObjectFromJSONArray(responseBody, Portfolio.class);

            }

        } catch (IOException e) {
            Log.i(TAG, "getProfile:  ---->  error ->  " + e);
            e.printStackTrace();
        }

        Log.i(TAG, "getProfile:  ---->  Exit ");
        System.out.println("Portfolio="+profiles);
        return profiles;
        //ApplicationUtils.generateObjectFromJSON(
        //ApplicationUtils.generateJSONFromObject(profiles.get(0)),Profile.class);

    }

}
