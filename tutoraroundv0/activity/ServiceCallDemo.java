package com.example.panchal.tutoraroundv0.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.panchal.tutoraroundv0.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceCallDemo extends AppCompatActivity {

    String TAG = "ServiceCallDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_call_demo);
        getUniList();
    }

    void getUniList() {
        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);
        Call<ResponseBody> call = apiClient.getUniversityList();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Response Failed : " + response.code() + "|" + response.message());
                    return;
                }
                try {
                    String myResponse = response.body().string();
                    JSONObject obj = new JSONObject(myResponse);
                    String status = obj.getString("status");
                    JSONArray jsonArray = obj.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj0 = jsonArray.getJSONObject(i);
                        String uni = obj0.getString("university_name");
                        Log.e(TAG, new Gson().toJson(obj) + "\nstatus : " + status + "\n data : " + new Gson().toJson(jsonArray) + "\nUniversity : " + uni);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
