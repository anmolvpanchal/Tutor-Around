package com.example.panchal.tutoraroundv0.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.panchal.tutoraroundv0.R;
import com.example.panchal.tutoraroundv0.modelClasses.FAQS_API;
import com.example.panchal.tutoraroundv0.modelClasses.MyPostList_API;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FAQS extends AppCompatActivity {

    RecyclerView recyclerView;
    private static String TAG = "FAQS";

    String user_type;
    SharedPreferences preferences;
    ArrayList<FAQS_API> faqs_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);

        recyclerView = findViewById(R.id.faqs_recyclerview);

        // shared prefrence data retrival
        preferences = PreferenceManager.getDefaultSharedPreferences(FAQS.this);

        //getting userid
        user_type = preferences.getString("tutorcheck", null);
        Log.e(TAG, "user id madeyo  " + user_type);


        faqs();
    }

    public void faqs() {

        final ProgressDialog progressDialog = new ProgressDialog(FAQS.this);
        progressDialog.setTitle("Getting List");
        progressDialog.setMessage("Please Wait !");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);

        Call<ResponseBody> call = apiClient.faqList(user_type);
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
                        Log.e(TAG, "onResponse: " + root_status);

                        faqs_list.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.e(TAG, "i " + i);
                            JSONObject obj = jsonArray.getJSONObject(i);

                            String id = obj.getString("id");
                            String question = obj.getString("question");
                            String answer = obj.getString("answer");
                            String type = obj.getString("type");

                            faqs_list.add(new FAQS_API(id, question, answer, type));


                            //   my_post_list.add(new Course_list_API(id, course_name, status));

                        }
                        Log.e(TAG, "details made posts ne" + new Gson().toJson(faqs_list));
                        recyclerView.setLayoutManager(new LinearLayoutManager(FAQS.this));
                        AdapterFAQS adapterFAQS = new AdapterFAQS(FAQS.this, faqs_list);

                        recyclerView.setAdapter(adapterFAQS);


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
