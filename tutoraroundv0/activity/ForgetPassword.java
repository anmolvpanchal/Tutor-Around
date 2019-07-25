package com.example.panchal.tutoraroundv0.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.panchal.tutoraroundv0.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassword extends AppCompatActivity {

    TextInputEditText emailtocheck;
    Button generateotp;

    public static String TAG = "ForgetPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);


        emailtocheck = findViewById(R.id.forgetuserIDTextInputEditText);
        generateotp = findViewById(R.id.generateotp);

        generateotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validation()){
                    forgetpassword();
                }

            }
        });


    }

    public boolean validation(){
        if (emailtocheck.getText().toString().isEmpty()) {
            emailtocheck.setError("Please Enter Email !!!");
            emailtocheck.setFocusable(true);
        } else if (!(Patterns.EMAIL_ADDRESS.matcher(emailtocheck.getText()).matches())) {
            emailtocheck.setError("Please Enter Valid Email !!!");
            emailtocheck.setFocusable(true);
        } else {
            return true;
        }
        return false;
    }

    public void forgetpassword() {

        final ProgressDialog progressDialog = new ProgressDialog(ForgetPassword.this);
        progressDialog.setTitle("Processing Request");
        progressDialog.setMessage("Please Wait !");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);

        Call<ResponseBody> call = apiClient.forgotPassword(emailtocheck.getText().toString());
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
                        startActivity(new Intent(getApplicationContext(),CheckOTP.class).putExtra("email",emailtocheck.getText().toString()));
                    } else {
                        Toast.makeText(getApplicationContext(), rootObj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "some error "+call, Toast.LENGTH_LONG).show();


            }


        });

    }


}


