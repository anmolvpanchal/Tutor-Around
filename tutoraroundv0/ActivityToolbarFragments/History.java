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

import com.example.panchal.tutoraroundv0.EventsToolbarFragments.HistoryList_API;
import com.example.panchal.tutoraroundv0.R;
import com.example.panchal.tutoraroundv0.activity.ApiClient;
import com.example.panchal.tutoraroundv0.activity.BottomNavigation;
import com.example.panchal.tutoraroundv0.activity.ServiceGenerator;
import com.example.panchal.tutoraroundv0.bottomnavFragments.AdapterHistory;
import com.example.panchal.tutoraroundv0.modelClasses.MyPostList_API;

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

public class History extends Fragment {

    SharedPreferences preferences;

    public History() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    private static String TAG = "History";
    ArrayList<HistoryList_API> my_history_list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.history_fragment_toolbar, container, false);
        recyclerView = view.findViewById(R.id.historyrecyclerview);
        swipeRefreshLayout = view.findViewById(R.id.history_refresh);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHistory();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_blue_bright);


        getHistory();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) ;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

        }
    }

    void getHistory() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Getting List");
        progressDialog.setMessage("Please Wait !");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);
        Call<ResponseBody> call;
        if (preferences.getString("tutorcheck", "").equals("1"))
            call = apiClient.getHistoryPost(preferences.getString("user_id", ""));
        else
            call = apiClient.getHistoryTutoringSchedule(preferences.getString("user_id", ""));

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

                        my_history_list.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.e(TAG, "i " + i);
                            JSONObject obj = jsonArray.getJSONObject(i);

                            String id = obj.getString("id");
                            String date = obj.getString("date");
                            String time = obj.getString("time");
                            String description = obj.getString("description");
                            String course_name = obj.getString("course_name");
                            String fname = obj.getString("fname");
                            String duration = obj.getString("duration");
                            String price = obj.getString("price");
                            String rating = obj.getString("rating");
                            String ccode = obj.getString("ccode");
                            my_history_list.add(new HistoryList_API(course_name,fname,id,date,time,duration,price,description,rating,ccode));

                            Log.e(TAG, "details made posts ne" + id + date + time);

                            //   my_post_list.add(new Course_list_API(id, course_name, status));

                        }


                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        AdapterHistory adapterHistory = new AdapterHistory(getActivity(), my_history_list);
                        recyclerView.setAdapter(adapterHistory);


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
}
