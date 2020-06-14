package com.example.mobdev;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;



public class Main3Activity<viewPager> extends AppCompatActivity {

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
    }




    public void goToFirstActivity (View view){
            finish();
        }

        // Add Fragments to Tabs
        private void setupViewPager (ViewPager viewPager){
            Adapter adapter = new Adapter(getSupportFragmentManager());
            ProfileFragment profile = new ProfileFragment();
            SettingsFragment settings = new SettingsFragment();
            adapter.addFragment(profile, "Profile");
            Intent intent = getIntent();
            Bundle b = intent.getExtras();
            profile.setArguments(b);
            settings.setArguments(b);
            adapter.addFragment(new MatchesFragment(), "Matches");
            adapter.addFragment(settings, "Settings");
            viewPager.setAdapter(adapter);
        }

//    public void displayToast(View view) {
//        Toast.makeText(this, "You Liked" + MatchesFragment.ViewHolder.class.getName(), Toast.LENGTH_LONG).show();
//        return;
//    }

    static class Adapter extends FragmentPagerAdapter {
            private final List<Fragment> mFragmentList = new ArrayList<>();
            private final List<String> mFragmentTitleList = new ArrayList<>();

            public Adapter(FragmentManager manager) {
                super(manager);
            }

            @NonNull
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

