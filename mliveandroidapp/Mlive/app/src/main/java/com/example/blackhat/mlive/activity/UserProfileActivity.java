package com.example.blackhat.mlive.activity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blackhat.mlive.R;
import com.example.blackhat.mlive.apiutils.UserProfileUtils;
import com.example.blackhat.mlive.pojo.UserProfile;
import com.example.blackhat.mlive.util.AppConstant;



public class UserProfileActivity extends AppCompatActivity {

    TextView txtFirstName,txtLastName,txtMobileNo,txtEmailID,txtCity,txtExpiryDate;
    UserProfile user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        txtFirstName= (TextView) findViewById(R.id.txtFirstName);
        txtLastName= (TextView) findViewById(R.id.txtLastName);
        txtMobileNo= (TextView) findViewById(R.id.txtMobileNo);
        txtEmailID= (TextView) findViewById(R.id.txtMobileNo);
        txtCity= (TextView) findViewById(R.id.txtCity);
        txtExpiryDate= (TextView) findViewById(R.id.txtExpiryDate);

        final SharedPreferences pref= getApplicationContext().getSharedPreferences("LoginPref", 0); // 0 - for private mode
        new UserProfileTask(pref.getString("LoginUserName",null),pref.getString("LoginPassword",null)).execute();

        if(!user.getUserid().equals(""))
        {
            txtFirstName.setText(user.getFirstname());
            txtLastName.setText(user.getLastname());
            txtMobileNo.setText(user.getContactno());
            txtEmailID.setText(user.getEmailid());
            txtCity.setText(user.getCity());
            txtExpiryDate.setText(user.getExpiryDate());
        }

    }

    public class UserProfileTask extends AsyncTask<Void, Void, Boolean> {

        private final String musername;
        private final String mPassword;
        //SharedPreferences preferences;


        UserProfileTask(String username, String password) {
            musername = username;
            mPassword = password;
            //preferences = getApplicationContext().getSharedPreferences("LoginPref", 0);
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            //  Log.i(TAG, "-->  UserLoginTask  -->  doInBackground:  ---->  Enter ");

            user = UserProfileUtils.getProfile(AppConstant.GETUSERPROFILE, musername, mPassword);

          //  final SharedPreferences.Editor editor = preferences.edit();

        return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

/*
            if(preferences.getAll().size()!=0) {
                //mProgress.setTitle("User Authanticated");
                //Toast.makeText(UserProfileActivity.this,"User Authanticated",Toast.LENGTH_SHORT).show();

            }
            else {
                //  mProgress.setTitle("User Not Found");
                Toast.makeText(LoginActivity.this,"User Not Found",Toast.LENGTH_LONG).show();
            }*/
            //mProgress.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //    mProgress.show();
        }


        @Override
        protected void onCancelled() {

        }

    }

}
