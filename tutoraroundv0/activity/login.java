package com.example.panchal.tutoraroundv0.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class login extends AppCompatActivity {

    private static String TAG = "login";

    Button loginbutton;
    TextView signup, forgot_password;
    TextInputEditText Email, Password;


    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.userIDTextInputEditText);
        Password = findViewById(R.id.PasswordTextInputEditText);
        loginbutton = findViewById(R.id.loginbutton);
        signup = findViewById(R.id.signuptext);
        forgot_password = findViewById(R.id.forgottext);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validation()){
                    login();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SignupPage.class);
                startActivity(intent);

            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // new to get new password and create activity for that
                Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(login.this)
                .setTitle("Title")
                .setMessage("Do you really want to exit?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.finishAffinity(login.this);
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    public boolean validation(){

        if (Email.getText().toString().isEmpty()) {
            Email.setError("Please Enter Email !!!");
            Email.setFocusable(true);
        } else if (!(Patterns.EMAIL_ADDRESS.matcher(Email.getText()).matches())) {
            Email.setError("Please Enter Valid Email !!!");
            Email.setFocusable(true);
        } else if (Password.getText().toString().isEmpty()) {
            Password.setError("Please Enter Valid Password !!!");
            Password.setFocusable(true);
        } else {
            return true;
        }
        return false;
    }

    private void login(){
        final ProgressDialog progressDialog = new ProgressDialog(login.this);
        progressDialog.setTitle("Logging IN");
        progressDialog.setMessage("Please Wait !");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        Integer tutor = 2;


        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);
        Call<ResponseBody> call =  apiClient.login(Email.getText().toString(),Password.getText().toString(),"",tutor.toString());
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
                    String status = rootObj.getString("status");
                    Log.e(TAG, "onResponse: "+ status );

                    if (!status.equals("0")) {
                        JSONArray jsonArray = rootObj.getJSONArray("data");
                        Log.e(TAG, "onResponse: "+ jsonArray );

                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.e(TAG, "i " + i);
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String id = obj.getString("id");
                            String fname = obj.getString("fname");
                            String lname = obj.getString("lname");
                            String email = obj.getString("email");
                            String type = obj.getString("type");

                            new LoginModelAPI(id,fname,lname,email,type);


                            preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            editor = preferences.edit();
                            editor.putString("user_id",id);
                            editor.putString("tutorcheck",type);
                            editor.commit();


                        }

                            startActivity(new Intent(getApplicationContext(),BottomNavigation.class));
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "" + rootObj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Error " + t,Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });

    }

}
