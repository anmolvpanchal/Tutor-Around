package com.example.panchal.tutoraroundv0.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.panchal.tutoraroundv0.ActivityToolbarFragments.MyPosts;
import com.example.panchal.tutoraroundv0.bottomnavFragments.FragmentAddPost;
import com.example.panchal.tutoraroundv0.bottomnavFragments.FragmentActivity;
import com.example.panchal.tutoraroundv0.bottomnavFragments.FragmentEvents;
import com.example.panchal.tutoraroundv0.bottomnavFragments.FragmentNotification;
import com.example.panchal.tutoraroundv0.bottomnavFragments.FragmentProfile;
import com.example.panchal.tutoraroundv0.R;

public class BottomNavigation extends AppCompatActivity implements MyCallBack {

    BottomNavigationView main_nav;
    FrameLayout main_frame;

    FragmentEvents events_fragment;
    FragmentAddPost post_fragment;
    FragmentActivity history_fragment;
    FragmentProfile profile_fragment;


    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String Tutor = String.valueOf(2);
    String TAG = "BottomNavigation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigatiom);

        main_frame = findViewById(R.id.main_frame);
        main_nav = findViewById(R.id.main_nav);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        events_fragment = new FragmentEvents();
        post_fragment = new FragmentAddPost();
        history_fragment = new FragmentActivity();
        profile_fragment = new FragmentProfile();





        main_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.nav_events:
                        main_nav.setItemBackgroundResource(R.color.colorwhite);
                        setFragment(new FragmentEvents());
                        return true;
                    case R.id.nav_post:
                        main_nav.setItemBackgroundResource(R.color.colorwhite);
                        setFragment(new FragmentAddPost());
                        return true;
                    case R.id.nav_history:
                        main_nav.setItemBackgroundResource(R.color.colorwhite);
                        setFragment(new FragmentActivity());
                        return true;
                    case R.id.nav_profile:
                        main_nav.setItemBackgroundResource(R.color.colorwhite);
                        setFragment(new FragmentProfile());
                        return true;
                    case R.id.nav_notifications:
                        main_nav.setItemBackgroundResource(R.color.colorwhite);
                        setFragment(new FragmentNotification());
                        return true;
                    default:
                        return false;
                }

            }
        });

        main_nav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {


            }
        });


        // shared prefrence data retrival
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //tutor or student
        Tutor = preferences.getString("tutorcheck", null);


        if (Tutor.equals(String.valueOf(1))) {
            main_nav.getMenu().removeItem(R.id.nav_events);
            setFragment(history_fragment);
            main_nav.setSelectedItemId(R.id.nav_history);

            loadFragments();

        } else if (Tutor.equals(String.valueOf(2))) {

            main_nav.getMenu().removeItem(R.id.nav_post);
            setFragment(events_fragment);
            main_nav.setSelectedItemId(R.id.nav_events);


        }


    }

    private void loadFragments() {
        Log.e(TAG, (getIntent().hasExtra("isFromAddPost") && getIntent().getBooleanExtra("isFromAddPost", false)) + "");
        if (getIntent().hasExtra("isFromAddPost") && getIntent().getBooleanExtra("isFromAddPost", false)) {
            setFragment(new FragmentActivity());
            main_nav.setSelectedItemId(R.id.nav_history);
        }
    }


    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(BottomNavigation.this)
                .setTitle("Title")
                .setMessage("Do you really want to exit?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.finishAffinity(BottomNavigation.this);
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }


    @Override
    public void onSubmit() {
        Log.e(TAG, "YUPE");
    }

//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.profile_toolbar, menu);
//        return true;
//    }
}
