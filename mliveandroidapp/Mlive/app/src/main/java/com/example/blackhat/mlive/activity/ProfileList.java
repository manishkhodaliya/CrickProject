package com.example.blackhat.mlive.activity;

import android.app.ProgressDialog;
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
import com.example.blackhat.mlive.apiutils.AddProfileUtils;
import com.example.blackhat.mlive.apiutils.ProfileUtils;
import com.example.blackhat.mlive.apiutils.SetDefaultUtils;
import com.example.blackhat.mlive.model.ProfileNameAdapter;
import com.example.blackhat.mlive.pojo.HttpCallResponse;
import com.example.blackhat.mlive.pojo.ListComplexObject;
import com.example.blackhat.mlive.pojo.Profile;
import com.example.blackhat.mlive.pojo.ProfileNamePojo;
import com.example.blackhat.mlive.util.AppConstant;
import com.example.blackhat.mlive.util.ApplicationUtils;
import com.example.blackhat.mlive.util.ComplexPreferences;
import com.example.blackhat.mlive.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileList extends AppCompatActivity {


    private List<ProfileNamePojo> profileNameList = new ArrayList<>();

    RecyclerView recyclerView;
    private ProfileNameAdapter mAdapter;
    private Button btnAddPortfolio, btnDefault, btndelete;
    public EditText editPortfolio;

    ComplexPreferences complexPreferences;
    SharedPreferences preferences;

    ProfileNamePojo profileNamePojo;
    ListComplexObject listComplexObject;
    List<Profile> profileCommo;

    String server;

    int rem = 0;

    public ProfileList() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);

        editPortfolio = (EditText) findViewById(R.id.editPortfolio);
        btnAddPortfolio = (Button) findViewById(R.id.addPortfolio);
        recyclerView = (RecyclerView) findViewById(R.id.profileRecyclerView);
        btnDefault = (Button) findViewById(R.id.btnDefault);
        btndelete = (Button) findViewById(R.id.btnDelete);


        preferences = getApplicationContext().getSharedPreferences("LoginPref", 0);
        server = preferences.getString(AppConstant.PREF_SERVER, null);
        System.out.println("server=" + server);
        complexPreferences = ComplexPreferences.getComplexPreferences(this, AppConstant.PREF_PROFILE, MODE_PRIVATE);


        final int user_id = preferences.getInt("User", 0);

        profileSet();

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(ProfileList.this, Portfolio.class);
                intent.putExtra("profileid", profileCommo.get(position).getProfileid());
                intent.putExtra("profileName", profileCommo.get(position).getProfilename());
                startActivity(intent);

            }

            @Override
            public void onLongItemClick(View view, int position) {

                if (btndelete.getVisibility() != View.VISIBLE) {
                    btndelete.setVisibility(View.VISIBLE);
                    btnDefault.setVisibility(View.VISIBLE);
                }

                rem = position;
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

                    if (profile.getProfilename().equals(profileNameList.get(rem).getProfileName())) {
                        System.out.println("profile id=" + profile.getProfileid() + " Name=" + profile.getProfilename());

                        new SetDefaultTask("" + user_id, profile.getProfileid()).execute();
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

                                new AddProfileTask(""+user_id,editPortfolio.getText().toString()).execute();

                               /* profileNamePojo = new ProfileNamePojo();
                                profileNamePojo.setProfileName(editPortfolio.getText().toString());
                                profileNameList.add(profileNamePojo);
                                mAdapter.notifyDataSetChanged();*/
                                break;

                            }

                        }
                    }

                }



            }
        });
    }

    public void profileSet() {

        listComplexObject = complexPreferences.getObject("profileList", ListComplexObject.class);

        System.out.println("list complex object=" + listComplexObject.getProfileList());
        profileCommo = listComplexObject.getProfileList();


        for (Profile profile : profileCommo) {

            profileNamePojo = new ProfileNamePojo();

            if (profile.getProfile_type().equalsIgnoreCase("Default")) {
                System.out.println("Default=" + profile.getProfilename());
                profileNamePojo.setDefault(profile.getProfilename());
            }

            profileNamePojo.setProfileName(profile.getProfilename());
            profileNameList.add(profileNamePojo);
        }

        System.out.println("profileName List = "+profileNameList.size());

        mAdapter = new ProfileNameAdapter(profileNameList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }


    public class SetDefaultTask extends AsyncTask {
        String user_id, profile_id;

        public SetDefaultTask(String user_id, String profile_id) {
            this.user_id = user_id;
            this.profile_id = profile_id;
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            int result = SetDefaultUtils.setDefault(AppConstant.SETDEFAULT, user_id, profile_id);
            System.out.println("result=" + result);

            return true;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            mAdapter.notifyDataSetChanged();

        }
    }


    public class ProfileWatchTask extends AsyncTask<Void, Void, Boolean> {
        final int progress = 0;
        private final String user_id;

        public ProfileWatchTask(String user_id) {
            this.user_id = user_id;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            System.out.println("In Profile Watch task");

            HttpCallResponse httpCallResponse = ProfileUtils.getProfile(server + AppConstant.HTTP_JAVA_PORT
                    + AppConstant.PROFILE_URI_MAPPING, user_id);

            if (httpCallResponse != null) {

                if (httpCallResponse.getCode() == 1) {

                    List profile = (List) httpCallResponse.getData().get((AppConstant.OBJ_PROFILE));

                    System.out.println("profile size  from api =>>" + profile.size());
                    profileCommo = new ArrayList<>();
                    for (int i = 0; i < profile.size(); i++) {

                        Profile profile1 = ApplicationUtils.generateObjectFromJSON(
                                ApplicationUtils.generateJSONFromObject(profile.get(i)), Profile.class);

                        profileCommo.add(profile1);
                        System.out.println("profile_name=" + profile1.getProfilename());
                        System.out.println("profile_type=" + profile1.getProfile_type());
                            /*
                                if ( profile1.getProfile_type().equals( "Default" ) ) {
                                    System.out.println( "in id" );
                                    profile_id = profile1.getProfileid();
                                    System.out.println( "Default Profile ID=" + profile_id );*/

                              /*  editor.putString( "commoProfile",profile1.getProfile_type() );
                                editor.commit();
                                }*/

                    }
                    if (profile.size() != 0) {
                        System.out.println("profile commo size=" + profileCommo.size());

                        ListComplexObject complexObject = new ListComplexObject();
                        complexObject.setProfileList(profileCommo);

                        complexPreferences.putObject(AppConstant.OBJ_PROFILE_LIST, complexObject);
                        complexPreferences.commit();


                    }
                }

            }


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /*     mProgressProfile.setMessage( "Getting Profiles...." );
                mProgressProfile.show();*/
            //progressBar.setVisibility( View.GONE );

            if (profileNameList.size() != 0)
            {
                profileNameList.clear();
                System.out.println("Profile Name List Cleared");
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           profileSet();

        }
    }

    public class AddProfileTask extends AsyncTask<Void, Void, Boolean> {

        private final String user_id;
        private final String profileName;

        public AddProfileTask(String user_id,String profileName) {
            this.user_id = user_id;
            this.profileName=profileName;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            System.out.println("In Profile Add task");

            HttpCallResponse httpCallResponse = AddProfileUtils.addProfile(AppConstant.ADDPROFILE, profileName, user_id);

            if (httpCallResponse != null) {

                if (httpCallResponse.getCode() == 1) {

                    List profile = (List) httpCallResponse.getData().get((AppConstant.OBJ_PROFILE));

                    System.out.println("profile size  from api =>>" + profile.size());
                    profileCommo = new ArrayList<>();
                    for (int i = 0; i < profile.size(); i++) {

                        Profile profile1 = ApplicationUtils.generateObjectFromJSON(
                                ApplicationUtils.generateJSONFromObject(profile.get(i)), Profile.class);

                        profileCommo.add(profile1);
                        System.out.println("profile_name=" + profile1.getProfilename());
                        System.out.println("profile_type=" + profile1.getProfile_type());
                            /*
                                if ( profile1.getProfile_type().equals( "Default" ) ) {
                                    System.out.println( "in id" );
                                    profile_id = profile1.getProfileid();
                                    System.out.println( "Default Profile ID=" + profile_id );*/

                              /*  editor.putString( "commoProfile",profile1.getProfile_type() );
                                editor.commit();
                                }*/

                    }
                    if (profile.size() != 0) {
                        System.out.println("profile commo size=" + profileCommo.size());

                        ListComplexObject complexObject = new ListComplexObject();
                        complexObject.setProfileList(profileCommo);

                        complexPreferences.putObject(AppConstant.OBJ_PROFILE_LIST, complexObject);
                        complexPreferences.commit();


                    }
                }

            }


            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /*     mProgressProfile.setMessage( "Getting Profiles...." );
                mProgressProfile.show();*/
            //progressBar.setVisibility( View.GONE );

            if (profileNameList.size() != 0)
            {
                profileNameList.clear();
                System.out.println("Profile Name List Cleared");
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new ProfileWatchTask("" + user_id).execute();

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (btndelete.getVisibility() == View.VISIBLE) {
            btndelete.setVisibility(View.INVISIBLE);
            btnDefault.setVisibility(View.INVISIBLE);
        }
    }


}
