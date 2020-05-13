package com.example.mobdev;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;

import androidx.appcompat.widget.Toolbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;


public class Main3Activity<viewPager> extends AppCompatActivity {

    private String name = "";
    private String email = "";
    private String age = "";
    private String description = "";
    private String occupation = "";
    private String dateOfBirth = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        // Adding Toolbar to Main screen
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("Profile"));
        tabs.addTab(tabs.newTab().setText("Matches"));
        tabs.addTab(tabs.newTab().setText("Settings"));
        tabs.setupWithViewPager(viewPager);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();


        if (b != null) {
            if (b.containsKey(Constants.KEY_NAME)) {
                name = b.getString(Constants.KEY_NAME);
            }

            if (b.containsKey(Constants.KEY_EMAIL)) {
                email = b.getString(Constants.KEY_EMAIL);
            }

            if (b.containsKey(Constants.KEY_AGE)) {
                age = b.getString(Constants.KEY_AGE);
            }

            if (b.containsKey(Constants.KEY_DESCRIPTION)) {
                description = b.getString(Constants.KEY_DESCRIPTION);
            }

            if (b.containsKey(Constants.KEY_OCCUPATION)) {
                occupation = b.getString(Constants.KEY_OCCUPATION);
            }

            if (b.containsKey(Constants.KEY_DATE_OF_BIRTH)) {
                dateOfBirth = b.getString(Constants.KEY_DATE_OF_BIRTH);
            }


        }
    }

        public void goToFirstActivity (View view){
            finish();
        }

        // Add Fragments to Tabs
        private void setupViewPager (ViewPager viewPager){
            Adapter adapter = new Adapter(getSupportFragmentManager());
            adapter.addFragment(new ProfileFragment(), "Profile");
            adapter.addFragment(new MatchesFragment(), "Matches");
            adapter.addFragment(new SettingsFragment(), "Settings");
            viewPager.setAdapter(adapter);
        }

        static class Adapter extends FragmentPagerAdapter {
            private final List<Fragment> mFragmentList = new ArrayList<>();
            private final List<String> mFragmentTitleList = new ArrayList<>();

            public Adapter(FragmentManager manager) {
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

