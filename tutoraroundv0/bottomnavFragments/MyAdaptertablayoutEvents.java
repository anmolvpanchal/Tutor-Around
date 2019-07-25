package com.example.panchal.tutoraroundv0.bottomnavFragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.panchal.tutoraroundv0.EventsToolbarFragments.AvailableOpportunities;
import com.example.panchal.tutoraroundv0.EventsToolbarFragments.PendingOpportunities;

class MyAdaptertablayoutEvents extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;

    public MyAdaptertablayoutEvents(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        Log.e("position", "getItem: "+position );
        switch (position) {
            case 0:
                AvailableOpportunities availableOpportunities = new AvailableOpportunities();
                return availableOpportunities;


            case 1:
                PendingOpportunities pendingOpportunities = new PendingOpportunities();
                return pendingOpportunities;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
