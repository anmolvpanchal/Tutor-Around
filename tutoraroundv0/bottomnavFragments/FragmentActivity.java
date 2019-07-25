package com.example.panchal.tutoraroundv0.bottomnavFragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.panchal.tutoraroundv0.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentActivity extends Fragment {


    public FragmentActivity() {
        // Required empty public constructor
    }

    TabLayout tabLayout;
    ViewPager viewPager;
    String Tutor;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private static String TAG = "FragmentActivity";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_activity, container, false);

        // shared prefrence data retrival
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());


        Tutor = preferences.getString("tutorcheck", null);
        Log.e(TAG, "user Type madeyo  " + Tutor);

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);


        if (Tutor.equals("2")){
            tabLayout.addTab(tabLayout.newTab().setText("My Schedule"));
            tabLayout.addTab(tabLayout.newTab().setText("History"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        }else {
            tabLayout.addTab(tabLayout.newTab().setText("My Posts"));
            tabLayout.addTab(tabLayout.newTab().setText("History"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        }

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        findViews();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // shared prefrence data retrival
        //tutor or student


        return view;
    }

    void findViews() {
        MyAdaptertablayout adapter = new MyAdaptertablayout(getActivity(), getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (getUserVisibleHint()) {
//            findViews();
//        }
//    }
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            findViews();
//        }
//    }
}
