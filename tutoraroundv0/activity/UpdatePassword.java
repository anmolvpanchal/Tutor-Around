package com.example.panchal.tutoraroundv0.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class UpdatePassword extends AppCompatActivity {

    Button submit;
    TextInputEditText oldPassword,newPassword;
    String User_ID;
    public static String TAG = "UpdatePassword";
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);


        // shared prefrence data retrival
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //getting userid
        User_ID = preferences.getString("user_id", null);
        Log.e(TAG, "user id madeyo  " + User_ID);


        oldPassword = findViewById(R.id.update_Password_old_TextInputEditText);
        newPassword = findViewById(R.id.update_Password_new_TextInputEditText);
        submit = findViewById(R.id.update_Password_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()){
                    changePassword();
                }
            }
        });

    }

    public void changePassword(){



        final ProgressDialog progressDialog = new ProgressDialog(UpdatePassword.this);
        progressDialog.setTitle("Resetting..");
        progressDialog.setMessage("Please Wait !");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);

        Call<ResponseBody> call = apiClient.changePassword(User_ID,oldPassword.getText().toString(),newPassword.getText().toString());

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

        if (oldPassword.getText().toString().isEmpty()){
            oldPassword.setError("please enter old password");
        }else if (newPassword.getText().toString().isEmpty()){
            newPassword.setError("please enter new password");
        }else {
            return true;
        }
        return false;
    }
}
