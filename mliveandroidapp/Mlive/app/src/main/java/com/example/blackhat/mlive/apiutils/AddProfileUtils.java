package com.example.blackhat.mlive.apiutils;

import android.util.Log;

import com.example.blackhat.mlive.pojo.HttpCallResponse;
import com.example.blackhat.mlive.pojo.ProfileAdd;
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
 * Created by OM on 5/5/2017.
 */

public class AddProfileUtils {

    private static final String TAG = "AddProfileUtils";

    public static HttpCallResponse addProfile(String url, String user_id,String profileName) {

        Log.i(TAG, "getProfile:  ---->  Enter ");

        HttpResponse httpResponse = null;
        HttpCallResponse httpCallResponse = null;
        String responseBody = "";
        List<ProfileAdd> profiles = null;
        try {
            List<NameValuePair> pair=new ArrayList<NameValuePair>();

            pair.add(new BasicNameValuePair("userid", user_id));
            pair.add(new BasicNameValuePair("profilename", profileName));

            httpResponse = PortfolioUtility.postRequest(url, pair);

            System.out.println("http Response= "+httpResponse.getEntity());

            responseBody = EntityUtils.toString(httpResponse.getEntity());


            Log.i(TAG, "addProfile:  ---->  Response ->  " + responseBody);
            if (!Utility.isEmpty(responseBody)) {
                httpCallResponse = ApplicationUtils.generateObjectFromJSON(responseBody, HttpCallResponse.class);

            }


        } catch (IOException e) {
            Log.i(TAG, "addProfile:  ---->  error ->  " + e);
            e.printStackTrace();
        }

        Log.i(TAG, "addProfile:  ---->  Exit ");
        return httpCallResponse;
        //ApplicationUtils.generateObjectFromJSON(
        //ApplicationUtils.generateJSONFromObject(profiles.get(0)),Profile.class);

    }

}
