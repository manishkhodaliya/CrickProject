package com.example.blackhat.mlive.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.blackhat.mlive.util.ComplexPreferences;
import com.example.blackhat.mlive.util.RecyclerItemClickListener;

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

    SharedPreferences preferences;
    ProgressDialog mProgress;

    public ViewScripts()
    {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_view_scripts, container, false);


        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mProgress = new ProgressDialog(getContext());
        mProgress.setCancelable(Boolean.TRUE);
        mProgress.setMessage(AppConstant.MESSAGE_GETTING_DATA);
        mProgress.show();

        preferences = getContext().getSharedPreferences(AppConstant.PREF_LOGIN, 0);
        System.out.println("User id " + preferences.getInt("User", 0));
        System.out.println("Profile id " + preferences.getString("profileid", null));


        new ViewScriptTask("" + preferences.getInt("User", 0), preferences.getString("profileid", null)).execute();

        recyclerView = (RecyclerView) getView().findViewById(R.id.viewScriptRecylerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        //recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() );

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                System.out.println("selected script="+scriptList.get(position).getScriptname());

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
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

               // System.out.println("profile_name=" +profile1.getScriptname() );

                scriptList.add(profile1);
            }


            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            adapter = new PortfolioAdapter(scriptList);
            recyclerView.setAdapter(adapter);

            mProgress.dismiss();
        }
    }


}