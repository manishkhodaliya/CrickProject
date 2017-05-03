package com.example.blackhat.mlive.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blackhat.mlive.R;
import com.example.blackhat.mlive.apiutils.SetDefaultUtils;
import com.example.blackhat.mlive.model.ProfileNameAdapter;
import com.example.blackhat.mlive.pojo.ListComplexObject;
import com.example.blackhat.mlive.pojo.Profile;
import com.example.blackhat.mlive.pojo.ProfileNamePojo;
import com.example.blackhat.mlive.util.AppConstant;
import com.example.blackhat.mlive.util.ComplexPreferences;
import com.example.blackhat.mlive.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileList extends AppCompatActivity {


    private List<ProfileNamePojo> profileNameList = new ArrayList<>();

    RecyclerView recyclerView;
    private ProfileNameAdapter mAdapter;
    private Button btnAddPortfolio,btnDefault,btndelete;
    private EditText editPortfolio;

    ComplexPreferences complexPreferences;
    SharedPreferences preferences;

    ProfileNamePojo profileNamePojo;
    ListComplexObject listComplexObject;
    List<Profile> profileCommo;

    int rem=0;

    public ProfileList() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);

        editPortfolio = (EditText) findViewById(R.id.editPortfolio);
        btnAddPortfolio = (Button) findViewById(R.id.addPortfolio);
        recyclerView = (RecyclerView) findViewById(R.id.profileRecyclerView);
        btnDefault= (Button) findViewById(R.id.btnDefault);
        btndelete= (Button) findViewById(R.id.btnDelete);

        preferences=getApplicationContext().getSharedPreferences("LoginPref", 0);
        complexPreferences = ComplexPreferences.getComplexPreferences(this, "profile", MODE_PRIVATE);
        listComplexObject = complexPreferences.getObject("profileList", ListComplexObject.class);

        System.out.println("list complex object=" + listComplexObject.getProfileList());
        profileCommo = listComplexObject.getProfileList();

        final int user_id=preferences.getInt("User",0);

        for (Profile profile : profileCommo) {

            profileNamePojo = new ProfileNamePojo();

            if(profile.getProfile_type().equalsIgnoreCase("Default"))
            {
                System.out.println("Default="+profile.getProfilename());
                profileNamePojo.setDefault(profile.getProfilename());
            }

            profileNamePojo.setProfileName(profile.getProfilename());
            profileNameList.add(profileNamePojo);
        }

        mAdapter = new ProfileNameAdapter(profileNameList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);




        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                System.out.println(profileCommo.get(position).getProfileid());

                Intent intent=new Intent(ProfileList.this,Portfolio.class);
                intent.putExtra("profileid",profileCommo.get(position).getProfileid());
                startActivity(intent);

            }

            @Override
            public void onLongItemClick(View view, int position) {

                if(btndelete.getVisibility() != View.VISIBLE)
                {
                    btndelete.setVisibility(View.VISIBLE);
                    btnDefault.setVisibility(View.VISIBLE);
                }

                rem=position;
            }
        }));


        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                profileNameList.remove(rem);
                mAdapter.notifyDataSetChanged();

                btndelete.setVisibility(View.INVISIBLE);
                btnDefault.setVisibility(View.INVISIBLE);
            }
        });

        btnDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (Profile profile : profileCommo) {

                    if(profile.getProfilename().equals(profileNameList.get(rem).getProfileName())) {
                        System.out.println("profile id="+ profile.getProfileid() +" Name=" + profile.getProfilename());

                       new SetDefaultTask(""+user_id,profile.getProfileid()).execute();
                    }
                }

                btndelete.setVisibility(View.INVISIBLE);
                btnDefault.setVisibility(View.INVISIBLE);
            }
        });


        btnAddPortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editPortfolio.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_LONG).show();
                } else {

                    if (profileNameList.size() == 0) {
                        profileNamePojo = new ProfileNamePojo();
                        profileNamePojo.setProfileName(editPortfolio.getText().toString());
                        profileNameList.add(profileNamePojo);
                        mAdapter.notifyDataSetChanged();
                    } else {

                        System.out.println("Profile List Size=" + profileNameList.size());

                        for (int i = 0; i < profileNameList.size(); i++) {

                            profileNamePojo = profileNameList.get(i);

                            if (profileNamePojo.getProfileName().equalsIgnoreCase(editPortfolio.getText().toString())) {

                                System.out.println("In For If");
                                Toast.makeText(getApplicationContext(), "Profile Already Exists", Toast.LENGTH_SHORT).show();
                                break;

                            } else {

                                System.out.println("In Else");

                                profileNamePojo = new ProfileNamePojo();
                                profileNamePojo.setProfileName(editPortfolio.getText().toString());
                                profileNameList.add(profileNamePojo);
                                mAdapter.notifyDataSetChanged();
                                break;

                            }

                        }
                    }

                }

            }
        });



    }

    public class SetDefaultTask extends AsyncTask
    {
        String user_id,profile_id;

        public SetDefaultTask(String user_id, String profile_id) {
            this.user_id = user_id;
            this.profile_id = profile_id;
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            int result= SetDefaultUtils.setDefault(AppConstant.SETDEFAULT,user_id,profile_id);
            System.out.println("result="+result);

            return true;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(btndelete.getVisibility() == View.VISIBLE)
        {
            btndelete.setVisibility(View.INVISIBLE);
            btnDefault.setVisibility(View.INVISIBLE);
        }
    }
}
