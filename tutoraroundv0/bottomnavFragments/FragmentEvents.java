package com.example.panchal.tutoraroundv0.bottomnavFragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.example.panchal.tutoraroundv0.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEvents extends Fragment {


    public FragmentEvents() {
        // Required empty public constructor
    }

    TabLayout tabLayout;
    ViewPager viewPager;
    private static String TAG = "FragmentEvents";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_events, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.eventstabLayout);
        viewPager = view.findViewById(R.id.eventsviewPager);


        tabLayout.addTab(tabLayout.newTab().setText("Available Opportunities"));
        tabLayout.addTab(tabLayout.newTab().setText("Pending Opportunities"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        Log.e(TAG, "IN");
        MyAdaptertablayoutEvents adapter = new MyAdaptertablayoutEvents(getActivity(), getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

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

    }

}
