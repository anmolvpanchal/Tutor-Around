package com.example.panchal.tutoraroundv0.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.panchal.tutoraroundv0.R;
import com.example.panchal.tutoraroundv0.modelClasses.MyPostList_API;
import com.example.panchal.tutoraroundv0.modelClasses.Mytutorslist;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerticularPostDetails extends AppCompatActivity {

    String user_ID, Post_id;
    String Tutor ;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String Api_price,Api_assign, Api_id, Api_date, Api_time, Api_courseid, Api_duration, Api_description, Api_course_name, Api_personsname;
    String fname,lname,offer_price,rating,id;


    ArrayList<Mytutorslist> mytutorslists = new ArrayList<>();
    public static String TAG = "PerticularPostDetails";

    RecyclerView recyclerView;

    TextView subjectname, code, description, actualdate, actualtime, actualduration, actualcost, personsname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perticular_post_details);


        Intent intent = getIntent();
        Post_id = intent.getStringExtra("id");

        // shared prefrence data retrival
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //getting userid
        user_ID = preferences.getString("user_id", null);
        Tutor = preferences.getString("tutorcheck", null);
        Log.e("Tutor",""+Tutor);
        Log.e(TAG, "user id madeyo  " + user_ID);


        recyclerView = findViewById(R.id.perticular_post_recyclerview);
        subjectname = findViewById(R.id.perticular_post_subjectname);
        code = findViewById(R.id.perticular_post_subjectcode);
        description = findViewById(R.id.perticular_post_description);
        actualdate = findViewById(R.id.perticular_post_actualdate);
        actualtime = findViewById(R.id.perticular_post_actualtime);
        actualduration = findViewById(R.id.perticular_post_actualduration);
        actualcost = findViewById(R.id.perticular_post_actualcost);
        personsname = findViewById(R.id.perticular_post_tutorsname);


        if (Tutor.equals("2")){
            recyclerView.setVisibility(View.GONE);
        }

//        else if (Api_assign.equals("1")){
//
//        }


        detailsofpost();




    }

    public void settext(){

        subjectname.setText(Api_course_name);
        code.setText(Api_courseid);
        description.setText(Api_description);
        actualdate.setText(Api_date);
        actualtime.setText(Api_time);
        actualduration.setText(Api_duration);
        actualcost.setText(Api_price);
        personsname.setText(Api_personsname);

    }

    public void detailsofpost() {

        final ProgressDialog progressDialog = new ProgressDialog(PerticularPostDetails.this);
        progressDialog.setTitle("Getting Details");
        progressDialog.setMessage("Please Wait !");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);

        Call<ResponseBody> call = apiClient.getPostDetailTutorList(Post_id, user_ID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                progressDialog.dismiss();
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Something went Wrong !!" + response.code(), Toast.LENGTH_SHORT).show();
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
                        Log.e(TAG, " " + jsonArray);


                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.e(TAG, "main data details " + i);
                            JSONObject details_object = jsonArray.getJSONObject(i);

                            JSONArray dataarray = details_object.getJSONArray("Post_Details");
                            JSONArray tutordataarray = details_object.getJSONArray("tour_details");
                            String tutor_bid = details_object.getString("tutor_bid");


                            Log.e(TAG, "only Post_Details " + dataarray + " | SOMEthing");
                            Log.e(TAG, "only tutor_bid " + tutor_bid + " here zero is the value of string ");


                            for (int j = 0; j < dataarray.length(); j++) {

                                JSONObject postdetails_object = dataarray.getJSONObject(j);
                                Log.e(TAG, "postdetails_object    " + new Gson().toJson(postdetails_object));


                                Api_id = postdetails_object.getString("id");
                                Api_assign = postdetails_object.getString("assign");
                                Api_date = postdetails_object.getString("date");
                                Api_time = postdetails_object.getString("time");
                                Api_duration = postdetails_object.getString("duration");
                                Api_description = postdetails_object.getString("description");
                                Api_course_name = postdetails_object.getString("course_name");
                                Api_price = postdetails_object.getString("price");
                                Api_personsname = postdetails_object.getString("fname");
                                Api_courseid = postdetails_object.getString("ccode");

                                Log.e(TAG, "post detail madi gay  " + Api_date  + Api_course_name + Api_personsname);

                            }

                            settext();

                            for (int x = 0 ; x < tutordataarray.length() ; x++){
                                JSONObject tour_details = tutordataarray.getJSONObject(x);
                                Log.e(TAG, "tutor details " + tutordataarray + "| NULL");

                                fname = tour_details.getString("fname");
                                lname = tour_details.getString("lname");
                                id = tour_details.getString("id");
                                offer_price = tour_details.getString("offer_price");
                                rating = tour_details.getString("rating");
                                String user_fk = tour_details.getString("user_fk");


                                mytutorslists.add(new Mytutorslist(fname,lname,offer_price,rating,id,user_fk));
                                Log.e(TAG, "post detail madi gay  " + fname  + lname + offer_price);
                            }

                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                            AdapterMyPostsTutorslist adapterMyPostsTutorslist = new AdapterMyPostsTutorslist(PerticularPostDetails.this,mytutorslists,Api_id,Api_assign);
                            recyclerView.setAdapter(adapterMyPostsTutorslist);



                        }


                    } else {
                        Log.e("Registration", rootObj.getString("error_msg"));
                        Toast.makeText(getApplicationContext(), rootObj.getString("error_msg"), Toast.LENGTH_SHORT).show();

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
