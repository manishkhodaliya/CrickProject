package com.example.blackhat.mlive.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blackhat.mlive.R;
import com.example.blackhat.mlive.activity.LoginActivity;
import com.example.blackhat.mlive.activity.MainMenuActivity;
import com.example.blackhat.mlive.apiutils.PortfolioUtils;
import com.example.blackhat.mlive.apiutils.ProfileUtils;
import com.example.blackhat.mlive.model.PortfolioAdapter;
import com.example.blackhat.mlive.pojo.ListComplexObject;
import com.example.blackhat.mlive.pojo.Portfolio;
import com.example.blackhat.mlive.pojo.Profile;
import com.example.blackhat.mlive.util.AppConstant;
import com.example.blackhat.mlive.util.ApplicationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewScripts extends Fragment {


    PortfolioAdapter adapter;
    RecyclerView recyclerView;
    private static List<Portfolio> demoData;
    ProgressDialog mProgressProfile;
    List<Portfolio> scriptList;

    public ViewScripts()
    {

    }

    public ViewScripts(String profileid) {
        new ViewScriptTask("1956",profileid).execute();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_view_scripts, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        recyclerView = (RecyclerView) getView().findViewById(R.id.viewScriptRecylerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        //System.out.println("Demodata="+demoData.size());

    }


    public class ViewScriptTask extends AsyncTask<Void, Void, Boolean> {

        String user_id, profile_id;
        Portfolio portfolio;

        public ViewScriptTask(String user_id, String profile_id) {

            System.out.println("In View Script Task");
            this.user_id = user_id;
            this.profile_id = profile_id;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            scriptList=new ArrayList<>();
            demoData=new ArrayList<>();
            demoData = PortfolioUtils.getProfile(AppConstant.GETSCRIPT, user_id, profile_id);
            System.out.println("size=" + demoData.size());
            for (int i = 0; i < demoData.size(); i++) {
                Portfolio profile1 = ApplicationUtils.generateObjectFromJSON(
                        ApplicationUtils.generateJSONFromObject(demoData.get(i)), Portfolio.class);

                System.out.println("profile_name=" +profile1.getScriptname() );

                scriptList.add(profile1);
            }


            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            adapter = new PortfolioAdapter(scriptList);
            recyclerView.setAdapter(adapter);
        }
    }


}