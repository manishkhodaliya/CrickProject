package com.example.blackhat.mlive.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blackhat.mlive.R;
import com.example.blackhat.mlive.pojo.User;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgMarketWatch,imgProfile;
    int user;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        imgMarketWatch = (ImageView) findViewById(R.id.imgMarketWatch);
        imgProfile = (ImageView) findViewById(R.id.imgProfile);

        preferences = getApplicationContext().getSharedPreferences("LoginPref", 0);
        editor = preferences.edit();
        //SharedPreferences preferences=getApplicationContext().getSharedPreferences("LoginPref", 0);
        //final SharedPreferences.Editor editor = preferences.edit();

        user=preferences.getInt("User",0);
        System.out.println(user);

        imgMarketWatch.setOnClickListener(this);
        imgProfile.setOnClickListener(this);
        TextView scroolingTextView = (TextView)findViewById(R.id.scrollingtext);
        scroolingTextView.setSelected( Boolean.TRUE );

    }
    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.imgMarketWatch:
                intent=new Intent(MainMenuActivity.this,MainActivity.class);
                //intent.putExtra("User",user);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.imgProfile:
                intent=new Intent(MainMenuActivity.this,ProfileList.class);
                //intent.putExtra("User",user);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }

    }
}
