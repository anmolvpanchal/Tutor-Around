package com.example.panchal.tutoraroundv0.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.panchal.tutoraroundv0.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassword extends AppCompatActivity {


    TextInputEditText password,confirm_password;
    Button changepassword;
    String User_ID;

    public static String TAG = "ResetPassword";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        password = findViewById(R.id.reset_password_password_TextInputEditText);
        confirm_password = findViewById(R.id.reset_password_confirmpassword_TextInputEditText);
        changepassword = findViewById(R.id.reset_password_submit);


        Intent intent = getIntent();
        User_ID = intent.getStringExtra("user_id");

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validation()){

                    resetpassword();
                }
            }
        });

    }

    public void resetpassword(){



        final ProgressDialog progressDialog = new ProgressDialog(ResetPassword.this);
        progressDialog.setTitle("Resetting..");
        progressDialog.setMessage("Please Wait !");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);

        Call<ResponseBody> call = apiClient.resetPassword(User_ID,confirm_password.getText().toString());

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

                        startActivity(new Intent(getApplicationContext(),login.class));

                    } else {
                        Toast.makeText(getApplicationContext(), rootObj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "error in parsing calling " + call, Toast.LENGTH_LONG).show();
            }
        });




    }



    public boolean validation(){

        if (password.getText().toString().isEmpty()){
            password.setError("Enter new password");
            password.setFocusable(true);
        } else if (confirm_password.getText().toString().isEmpty()){
            confirm_password.setError("enter new pasword");
            confirm_password.setFocusable(true);
        } else if (!confirm_password.getText().toString().equals(password.getText().toString())){
            confirm_password.setError("please ReEnter same password");
            confirm_password.setFocusable(true);
        } else {
            return true;
        }


        return false;
    }
}
