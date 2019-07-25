package com.example.panchal.tutoraroundv0.bottomnavFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.panchal.tutoraroundv0.ActivityToolbarFragments.History;
import com.example.panchal.tutoraroundv0.ActivityToolbarFragments.MyPosts;
import com.example.panchal.tutoraroundv0.ActivityToolbarFragments.MySchedule;

class MyAdaptertablayout extends FragmentPagerAdapter {
    private Context activity;
    int totalTabs;
    private static final String TAG = "MyAdaptertablayout";
    String Tutor;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    public MyAdaptertablayout(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        activity = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {


        // shared prefrence data retrival
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);


        Tutor = preferences.getString("tutorcheck", null);
        Log.e(TAG, "user Type madeyo  " + Tutor);

        if (Tutor.equals("2")) {
            switch (position) {
                case 0:
                    MySchedule mySchedule = new MySchedule();
                    return mySchedule;

                case 1:
                    History history = new History();
                    return history;

                default:
                    return null;
            }
        } else if (Tutor.equals("1")) {
            switch (position) {
                case 0:
                    MyPosts myPosts = new MyPosts();
                    return myPosts;

                case 1:
                    History history = new History();
                    return history;
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
