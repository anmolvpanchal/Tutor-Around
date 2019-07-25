package com.example.panchal.tutoraroundv0.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.panchal.tutoraroundv0.R;
import com.example.panchal.tutoraroundv0.modelClasses.LoginModelAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOTP extends AppCompatActivity {

    TextInputEditText checkotp;
    Button check;
    String entered_email;
    String user_id;

    public static String TAG = "CheckOTP";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_otp);

        checkotp = findViewById(R.id.otpTextInputEditText);
        check = findViewById(R.id.generateotp_submit);

        Intent intent = getIntent();

        entered_email= intent.getStringExtra("email");

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validation()){
                    checkotp();
                }

            }
        });

    }

    public boolean validation(){

        if (checkotp.getText().toString().isEmpty()){
            checkotp.setError("please enter otp first");
            checkotp.setFocusable(true);
        }else {
            return true;
        }

        return false;
    }

    public void checkotp(){


        final ProgressDialog progressDialog = new ProgressDialog(CheckOTP.this);
        progressDialog.setTitle("Checking");
        progressDialog.setMessage("Please Wait !");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);

        Call<ResponseBody> call = apiClient.verifyCode(entered_email,checkotp.getText().toString());
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
                        Toast.makeText(getApplicationContext(), rootObj.getString("error_msg"), Toast.LENGTH_LONG).show();

                        JSONArray jsonArray = rootObj.getJSONArray("data");
                        Log.e(TAG, "data of person on check otp: "+ jsonArray );

                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.e(TAG, "i " + i);
                            JSONObject obj = jsonArray.getJSONObject(i);
                            user_id = obj.getString("id");
                        }

                        startActivity(new Intent(getApplicationContext(),ResetPassword.class).putExtra("user_id",user_id));



                    } else {
                        Toast.makeText(getApplicationContext(), rootObj.getString("error_msg"), Toast.LENGTH_LONG).show();
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
