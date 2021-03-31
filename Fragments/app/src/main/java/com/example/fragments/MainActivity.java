package com.example.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
TabLayout tab;
ViewPager viewpage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tab=findViewById(R.id.tv);
        viewpage=findViewById(R.id.myviewpager);
        viewpage.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
        tab.setupWithViewPager(viewpage);
    }
    public class MyFragmentPagerAdapter extends FragmentPagerAdapter{
String tabtitles[]={"Chats","Status","Calls"};
        public MyFragmentPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (position==0) {
                return new BlankFragment();
            }
            if (position==1){
                return new StatusFragment();
            }
            if (position==2){
                return new CallFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return tabtitles.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
           return tabtitles[position];
        }
    }
}