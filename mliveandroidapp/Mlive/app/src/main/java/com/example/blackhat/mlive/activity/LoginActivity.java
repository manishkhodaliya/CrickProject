package com.example.blackhat.mlive.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.blackhat.mlive.R;
import com.example.blackhat.mlive.apiutils.ProfileUtils;
import com.example.blackhat.mlive.apiutils.UserUtils;
import com.example.blackhat.mlive.pojo.HttpCallResponse;
import com.example.blackhat.mlive.pojo.ListComplexObject;
import com.example.blackhat.mlive.pojo.Profile;
import com.example.blackhat.mlive.pojo.User;
import com.example.blackhat.mlive.util.AppConstant;
import com.example.blackhat.mlive.util.ApplicationUtils;
import com.example.blackhat.mlive.util.ComplexPreferences;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG ="LoginActivity" ;
    
    List< Profile > profileCommo ;
    private static Map< String, String > userIndexMap = new HashMap< String, String >();
    
   public String Server1 = "://mlive.bcommo.in";
 //public String Server1 = "://112.133.247.131";
    public String Server2 = "://d1.user11.com";

    //Android Controlls 
    private String server="";
    EditText editUsername, editPassword;
    CheckBox check;
    Button btnLogin;
    ProgressDialog mProgress;
    ProgressDialog mProgressProfile;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    ComplexPreferences complexPreferences;
    ComplexPreferences preferencesForUser;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        editUsername = ( EditText ) findViewById( R.id.editUserName );
        editPassword = ( EditText ) findViewById( R.id.editPassword );
        check = ( CheckBox ) findViewById( R.id.checkRememberLogin );
        //spinner = ( Spinner ) findViewById( R.id.spinner );
        btnLogin = ( Button ) findViewById( R.id.btnLogin );
        //mProgress= ( ProgressBar ) findViewById( R.id.progressbar );
        pref = getApplicationContext().getSharedPreferences( AppConstant.PREF_LOGIN, 0 ); // 0 - for private mode
        editor = pref.edit();

        complexPreferences = ComplexPreferences.getComplexPreferences(this, AppConstant.PREF_PROFILE, MODE_PRIVATE);
        preferencesForUser =ComplexPreferences.getComplexPreferences(this,AppConstant.PREF_USER,MODE_PRIVATE );

        if( pref.getAll().size()!=0 ) {

            editUsername.setText( pref.getString( AppConstant.PREF_LOGIN_USER_NAME,null ) );
            editPassword.setText(pref.getString(AppConstant.PREF_LOGIN_PASSWORD, null));

            server = pref.getString( AppConstant.PREF_SERVER, null);
            check.setChecked( Boolean.TRUE );

//            new UserLoginTask( Server1,editUsername.getText().toString(), editPassword.getText().toString() ).execute();
/*
            if( server.equals( "Server1" ) )
            {
                spinner.setSelection( 0 );
            }
            else {
                spinner.setSelection( 1 );
            }*/
            System.out.println( "server="+server );

        }else
        {
            System.out.println( "Preference Not Found" );
        }

        mProgress = new ProgressDialog( this );
        mProgress.setCancelable( Boolean.TRUE );

        mProgressProfile = new ProgressDialog( this );
        mProgress.setCancelable( Boolean.FALSE );

        btnLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {

                if ( !editUsername.getText().toString().equals( "" ) ) {

                    if( check.isChecked() ) {
                        
                        editor.putString( AppConstant.PREF_LOGIN_USER_NAME, editUsername.getText().toString() );
                        editor.putString( AppConstant.PREF_LOGIN_PASSWORD, editPassword.getText().toString() );
                        editor.putString( AppConstant.PREF_SERVER, Server1 );
                        editor.commit();
                        
                        System.out.println( "Preference Created" );
                    }
                    else {
                        pref.edit().clear().commit();
                        System.out.println( "Preference Cleared" );
                    }
                    new UserLoginTask( Server1,editUsername.getText().toString(), editPassword.getText().toString() ).execute();

                } else {

                    Toast.makeText( LoginActivity.this, AppConstant.MESSAGE_BLANK_USERNAME_PASSWORD, Toast.LENGTH_LONG ).show();
                }
            }
        } );


    }
    public class UserLoginTask extends AsyncTask< Void, Void, Boolean > {

        private final String mEmail;
        private final String mPassword;
        private final String server;

        SharedPreferences preferences;
        User user;

        UserLoginTask( String Server,String email, String password ) {

            this.mEmail = email;
            this.mPassword = password;
            this.server = Server;
            this.preferences = getApplicationContext().getSharedPreferences( AppConstant.PREF_LOGIN, 0 );

        }

        @Override
        protected Boolean doInBackground( Void... params ) {

            HttpCallResponse  httpCallResponse = UserUtils.doLogin(server + AppConstant.HTTP_JAVA_PORT + AppConstant.LOGIN_MAPPING, mEmail, mPassword);
            if ( httpCallResponse !=null ){
                if ( httpCallResponse.getCode() == 1){
                    user = ApplicationUtils.generateObjectFromJSON( ApplicationUtils.generateJSONFromObject( httpCallResponse.getData().get( AppConstant.OBJ_U )),User.class );
                    if ( user !=null ){
                        
                        final SharedPreferences.Editor editor = preferences.edit();

                        if( user.getUserid() == 0 )
                        {
                            preferences.edit().clear().commit();

                            System.out.println( "Preference Cleared" );

                        }

                        else{


                            preferencesForUser.putObject( AppConstant.PREF_USER, user );
                            preferencesForUser.commit();
                        }

                    }else {
                        mProgress.dismiss();
                        Toast.makeText( LoginActivity.this,AppConstant.MESSAGE_SERVER_CONNECTION_PROBLEM,Toast.LENGTH_LONG ).show();
                    }
                }else {
                    mProgress.dismiss();
                    Toast.makeText( LoginActivity.this,httpCallResponse.getMessages(),Toast.LENGTH_LONG ).show();
                }

            }

        return true;
        }

        @Override
        protected void onPostExecute( final Boolean success ) {

            mProgress.dismiss();

            if( preferences.getAll().size() !=0 ) {
                //mProgress.setTitle( "User Authanticated" );
                //Toast.makeText( LoginActivity.this,"User Authanticated",Toast.LENGTH_SHORT ).show();

                mProgressProfile.setMessage( AppConstant.MESSAGE_GETTING_PROFILES );
                mProgressProfile.show();

                new ProfileWatchTask( "" + user.getUserid() ).execute();

            }
            else {
              //  mProgress.setTitle( "User Not Found" );
                Toast.makeText( LoginActivity.this, AppConstant.MESSAGE_USER_NOT_FOUND,Toast.LENGTH_LONG ).show();
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress.setMessage( AppConstant.MESSAGE_AUTHENTICATING );
            mProgress.show();
        }


        @Override
        protected void onCancelled() {
        }

        public class ProfileWatchTask extends AsyncTask< Void, Void, Boolean > {
            final int progress = 0;
            private final String user_id;

            public ProfileWatchTask( String user_id ) {
                this.user_id = user_id;
            }

            @Override
            protected Boolean doInBackground( Void... voids ) {

                System.out.println( "In Profile Watch task" );

                HttpCallResponse httpCallResponse = ProfileUtils.getProfile( server + AppConstant.HTTP_JAVA_PORT
                        + AppConstant.PROFILE_URI_MAPPING, user_id );

                if ( httpCallResponse != null ){

                    if ( httpCallResponse.getCode() == 1 ){

                        List profile = ( List )httpCallResponse.getData().get( ( AppConstant.OBJ_PROFILE ) );

                        System.out.println( "profile size  from api =>>" + profile.size() );
                        profileCommo = new ArrayList<>();
                        for ( int i = 0; i < profile.size(); i++ ) {

                            Profile profile1 = ApplicationUtils.generateObjectFromJSON(
                                    ApplicationUtils.generateJSONFromObject( profile.get( i ) ), Profile.class );

                            profileCommo.add( profile1 );
                            System.out.println( "profile_name="+profile1.getProfilename() );
                            System.out.println( "profile_type="+profile1.getProfile_type() );
                            /*
                                if ( profile1.getProfile_type().equals( "Default" ) ) {
                                    System.out.println( "in id" );
                                    profile_id = profile1.getProfileid();
                                    System.out.println( "Default Profile ID=" + profile_id );*/

                              /*  editor.putString( "commoProfile",profile1.getProfile_type() );
                                editor.commit();
                                }*/

                        }
                        if ( profile.size() != 0 ) {
                            System.out.println("profile commo size=" + profileCommo.size());

                            ListComplexObject complexObject = new ListComplexObject();
                            complexObject.setProfileList( profileCommo );

                            complexPreferences.putObject( AppConstant.OBJ_PROFILE_LIST, complexObject );
                            complexPreferences.commit();

                            if( preferences.getAll().size()!=0 ) {

                                Intent intent = new Intent( LoginActivity.this, MainMenuActivity.class );
                                startActivity( intent );
                            }

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
            }

            @Override
            protected void onPostExecute( Boolean aBoolean ) {
                super.onPostExecute( aBoolean );

                try {
                    Thread.sleep( 1000 );
                } catch ( InterruptedException e ) {
                    e.printStackTrace();
                }

                mProgressProfile.dismiss();

            }
        }

    }

}