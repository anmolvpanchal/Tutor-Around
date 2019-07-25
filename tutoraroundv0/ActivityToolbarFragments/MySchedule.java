package com.example.panchal.tutoraroundv0.ActivityToolbarFragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.panchal.tutoraroundv0.R;
import com.example.panchal.tutoraroundv0.activity.ApiClient;
import com.example.panchal.tutoraroundv0.activity.ServiceGenerator;
import com.example.panchal.tutoraroundv0.bottomnavFragments.AdapterHistory;
import com.example.panchal.tutoraroundv0.modelClasses.Available_opportunities_API;
import com.example.panchal.tutoraroundv0.modelClasses.Schedule_list_API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MySchedule extends Fragment {


    public MySchedule() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;

    SwipeRefreshLayout swipeRefreshLayout;
    String user_ID;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    ArrayList<Schedule_list_API> schedule_list = new ArrayList<>();

    private static String TAG ="MySchedule";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // shared prefrence data retrival
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //getting userid
        user_ID = preferences.getString("user_id", null);
        Log.e(TAG, "user id madeyo  " + user_ID);

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.my_schedule_fragment_toolbar, container, false);

        recyclerView = view.findViewById(R.id.myschedulerecyclerview);
        swipeRefreshLayout = view.findViewById(R.id.myschedule_refresh);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MySchedule();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_blue_bright);


        MySchedule();

        return view;

    }


    public void MySchedule(){

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Getting List");
        progressDialog.setMessage("Please Wait !");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);


        Call<ResponseBody> call = apiClient.getActiveTutoringSchedule(user_ID);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {



                progressDialog.dismiss();
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Something went Wrong !!" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }


                try {

                    String resp = response.body().string();
                    JSONObject rootObj = new JSONObject(resp);
                    Log.e(TAG, "onResponse: " + rootObj);
                    String root_status = rootObj.getString("status");
                    Log.e(TAG, "onResponse: " + root_status);
                    if (root_status.equals("1")) {

                        JSONArray jsonArray = rootObj.getJSONArray("data");
                        Log.e(TAG, "onResponse: " + root_status);

                        schedule_list.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.e(TAG, "i " + i);
                            JSONObject obj = jsonArray.getJSONObject(i);

                            String id = obj.getString("id");
                            String date = obj.getString("date");
                            String time = obj.getString("time");
                            String course_name = obj.getString("course_name");
                            String fname = obj.getString("fname");
                            String rating = obj.getString("rating");

                            schedule_list.add(new Schedule_list_API(course_name,date,time,fname,rating,id));

                            Log.e(TAG, "details made posts ne" + id + date + time);


                        }

                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        AdapterMySchedule adapterMySchedule = new AdapterMySchedule(getActivity(), schedule_list);
                        recyclerView.setAdapter(adapterMySchedule);


                    } else {
                        Log.e("Registration", rootObj.getString("error_msg"));
                        Toast.makeText(getActivity(), rootObj.getString("error_msg"), Toast.LENGTH_SHORT).show();

                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }




            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {



            }
        });






    }


    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) findViews();
    }

    private void findViews() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            findViews();
        }
    }

}
