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
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.panchal.tutoraroundv0.R;
import com.example.panchal.tutoraroundv0.activity.ApiClient;
import com.example.panchal.tutoraroundv0.activity.ServiceGenerator;
import com.example.panchal.tutoraroundv0.activity.UniversityModel;
import com.example.panchal.tutoraroundv0.bottomnavFragments.AdapterHistory;
import com.example.panchal.tutoraroundv0.modelClasses.Course_list_API;
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
public class MyPosts extends Fragment {


    public MyPosts() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;

    SwipeRefreshLayout swipeRefreshLayout;
    String user_ID;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ArrayList<MyPostList_API> my_post_list = new ArrayList<>();
    public static String TAG = "MyPosts";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // shared prefrence data retrival
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //getting userid
        user_ID = preferences.getString("user_id", null);
        Log.e(TAG, "user id madeyo  " + user_ID);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.mypost_fragment_toolbar, container, false);
        recyclerView = view.findViewById(R.id.mypostrecyclerview);
        swipeRefreshLayout = view.findViewById(R.id.mypost_refresh);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mypost_list();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_blue_bright);


        mypost_list();

        return view;
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


    public void mypost_list() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Getting List");
        progressDialog.setMessage("Please Wait !");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);

        Call<ResponseBody> call = apiClient.getActivePost(user_ID);
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

                        my_post_list.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.e(TAG, "i " + i);
                            JSONObject obj = jsonArray.getJSONObject(i);

                            String id = obj.getString("id");
                            String date = obj.getString("date");
                            String time = obj.getString("time");
                            String description = obj.getString("description");
                            String course_name = obj.getString("course_name");

                            my_post_list.add(new MyPostList_API(id, date, time, description, course_name));

                            Log.e(TAG, "details made posts ne" + id + date + time);

                            //   my_post_list.add(new Course_list_API(id, course_name, status));

                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        AdapterMyPosts adapterMyPosts = new AdapterMyPosts(getActivity(), my_post_list);
                        recyclerView.setAdapter(adapterMyPosts);


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
