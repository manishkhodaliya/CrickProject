package com.example.blackhat.mlive.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blackhat.mlive.R;
import com.example.blackhat.mlive.fragment.AddScripts;
import com.example.blackhat.mlive.fragment.ViewScripts;
import com.example.blackhat.mlive.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class Portfolio extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView txtProfileName;
    //private ImageView delete;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public Portfolio() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        txtProfileName= (TextView) findViewById(R.id.txtProfileName);
      //  delete= (ImageView) findViewById(R.id.delete);

        //delete.setVisibility(View.INVISIBLE);
        txtProfileName.setText(getIntent().getExtras().getString("profileName"));

        pref = getApplicationContext().getSharedPreferences( AppConstant.PREF_LOGIN, 0 );
        editor = pref.edit();
        editor.putString("profileid",getIntent().getExtras().getString("profileid"));
        editor.putString("profileName",getIntent().getExtras().getString("profileName"));
        editor.commit();


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.portfolioTabLayout);
        tabLayout.setupWithViewPager(viewPager);


    }
    private void setupViewPager(ViewPager viewPager) {


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ViewScripts(), "VIEW SCRIPTS");
        adapter.addFragment(new AddScripts(), "ADD SCRIPTS");

        viewPager.setAdapter(adapter);


    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {



            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
