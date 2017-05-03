package com.example.blackhat.mlive.apiutils;

import android.util.Log;

import com.example.blackhat.mlive.pojo.UserProfile;
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
 * Created by DigitalDesk on 4/2/2017.
 */

public class UserProfileUtils {

    private static final String TAG = "UserProfileUtils";

    public static UserProfile getProfile(String url, String username,String password) {

        Log.i(TAG, "getProfile:  ---->  Enter ");

        HttpResponse httpResponse = null;
        String responseBody = "";
        //  User user = null;
        List<UserProfile> profiles = null;
        try {
            List<NameValuePair> pair=new ArrayList<NameValuePair>();


            pair.add(new BasicNameValuePair("username", username));
            pair.add(new BasicNameValuePair("password", password));
            httpResponse = Utility.postRequest(url, pair);

            responseBody = EntityUtils.toString(httpResponse.getEntity());

            Log.i(TAG, "getProfile:  ---->  Response ->  " + responseBody);
            if (!Utility.isEmpty(responseBody)) {
                profiles = ApplicationUtils.generateObjectFromJSONArray(Utility.getUtility().getJsonFromXml(responseBody), UserProfile.class);

            }
        } catch (IOException e) {
            Log.i(TAG, "getProfile:  ---->  error ->  " + e);
            e.printStackTrace();
        }

        Log.i(TAG, "getProfile:  ---->  Exit ");
        return ApplicationUtils.generateObjectFromJSON(ApplicationUtils.generateJSONFromObject(profiles.get(0)),UserProfile.class);

    }

}
