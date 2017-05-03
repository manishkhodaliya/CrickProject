package com.example.blackhat.mlive.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.blackhat.mlive.R;
import com.example.blackhat.mlive.model.ProfileNameAdapter;
import com.example.blackhat.mlive.pojo.ProfileNamePojo;

import java.util.ArrayList;
import java.util.List;

public class ProfileName extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_name);


    }

}
