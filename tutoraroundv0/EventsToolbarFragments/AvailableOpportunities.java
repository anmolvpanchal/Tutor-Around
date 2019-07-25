package com.example.panchal.tutoraroundv0.EventsToolbarFragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.example.panchal.tutoraroundv0.activity.BottomNavigation;
import com.example.panchal.tutoraroundv0.activity.ServiceGenerator;
import com.example.panchal.tutoraroundv0.modelClasses.Available_opportunities_API;

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
public class AvailableOpportunities extends Fragment {

    private static String TAG = "AvailableOpportunities";

    String user_ID;
    String id, course_fk;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ArrayList<Available_opportunities_API> available_opportunities_list = new ArrayList<>();
    BottomNavigation activity;


    //    FragmentManager fragManager = getActivity().getSupportFragmentManager();
    public AvailableOpportunities() {
        // Required empty public constructor
    }


    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // shared prefrence data retrival
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        activity = (BottomNavigation) getActivity();
        //getting userid
        user_ID = preferences.getString("user_id", null);
        Log.e(TAG, "user id madeyo  " + user_ID);


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_available_opportunities, container, false);
        recyclerView = view.findViewById(R.id.available_opportunities_recycleview);
        swipeRefreshLayout = view.findViewById(R.id.available_opportunities_refresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                available_opportunities();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_blue_bright);



        available_opportunities();


        return view;
    }

    public void available_opportunities() {


        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Getting List");
        progressDialog.setMessage("Please Wait !");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);


        Call<ResponseBody> call = apiClient.getAvailablePost(user_ID);

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

                        available_opportunities_list.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.e(TAG, "i " + i);
                            JSONObject obj = jsonArray.getJSONObject(i);

                            id = obj.getString("id");
                            course_fk = obj.getString("course_fk");
                            Log.e("course_fk",""+course_fk);
                            String date = obj.getString("date");
                            String time = obj.getString("time");
                            String description = obj.getString("description");
                            String course_name = obj.getString("course_name");
                            String price = obj.getString("price");
                            available_opportunities_list.add(new Available_opportunities_API(id, date, time, description, course_name, price, course_fk));

                            Log.e(TAG, "details made posts ne" + id + date + time);


                        }


                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        AdapterAvailableOpportunities adapterAvailableOpportunities = new AdapterAvailableOpportunities(getActivity(), available_opportunities_list);
                        recyclerView.setAdapter(adapterAvailableOpportunities);


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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "in");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.e(TAG, "ONE" + isVisibleToUser);
        if (isVisibleToUser) {

        }
        super.setUserVisibleHint(isVisibleToUser);
    }


}
