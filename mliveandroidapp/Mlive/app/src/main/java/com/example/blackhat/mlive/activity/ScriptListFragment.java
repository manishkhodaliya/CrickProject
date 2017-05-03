package com.example.blackhat.mlive.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blackhat.mlive.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScriptListFragment extends Fragment {


    public ScriptListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_script_list, container, false);
        return view;
    }

}
