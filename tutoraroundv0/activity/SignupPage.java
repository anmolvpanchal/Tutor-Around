package com.example.panchal.tutoraroundv0.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.panchal.tutoraroundv0.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupPage extends AppCompatActivity {

    private static String TAG = "SignupPage";


    ArrayList<UniversityModel> spinnerList=new ArrayList<>();

    EditText First_name,Last_name,signup_emai, signup_password, signup_confirm_password, signup_phone;
    Spinner spinner;
    Button signup;
    Switch aSwitch;
    Boolean tutor = false;
    TextView domainname;
    String emaildomain,Enteredemail,Finalemail;
    String universityid;
    int tutororstudent = 1;


    public boolean validation() {
        if (First_name.getText().toString().isEmpty()) {
            First_name.setError("Please Enter Name ");
        } else if (Last_name.getText().toString().isEmpty()){
            Last_name.setError("Please Enter Name ");
        } else if (signup_emai.getText().toString().isEmpty()) {
            signup_emai.setError("Please Enter Email ID");
        } else if (!(Patterns.PHONE.matcher(signup_phone.getText()).matches())) {
            signup_phone.setError("Please Enter Valid Phone Number!!!");
        } else if (signup_phone.length() < 10) {
            signup_phone.setError("enter 10 digits only");
        } else if (signup_phone.length() > 10) {
            signup_phone.setError("enter 10 digits only");
        } else if (signup_password.getText().toString().isEmpty()) {
            signup_password.setError("Please Enter Password");
        } else if (signup_confirm_password.getText().toString().isEmpty()) {
            signup_confirm_password.setError("Please ReEnter Password");
        } else if (!signup_password.getText().toString().equals(signup_confirm_password.getText().toString())) {
            signup_confirm_password.setError("Please ReEnter Same Password");
        } else {
            return true;
        }
        return false;
    }
//
//    else if (!(Patterns.EMAIL_ADDRESS.matcher(signup_emai.getText()).matches())) {
//        signup_emai.setError("Please Enter Valid Email ID !!!");
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        getUniversityList();

        First_name = findViewById(R.id.first_name);
        Last_name = findViewById(R.id.last_name);
        signup_emai = findViewById(R.id.signupemail);
        signup_password = findViewById(R.id.signuppassword);
        signup_confirm_password = findViewById(R.id.signup_confirm_password);
        signup_phone = findViewById(R.id.signupphoneno);
        spinner = findViewById(R.id.signup_selectyear);
        signup = findViewById(R.id.signup_button);
        aSwitch = findViewById(R.id.switch1);
        domainname = findViewById(R.id.signup_domain);






        // setup for switch

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (compoundButton.isChecked()) {
                    tutor = true;
                    tutororstudent = 2;
                } else {
                    tutor = false;
                    tutororstudent = 1;
                }

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                emaildomain = spinnerList.get(spinner.getSelectedItemPosition()).emailDomain;
                domainname.setText(emaildomain);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





        // final button validation


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    if (validation()) {

                        Enteredemail = signup_emai.getText().toString();
                        Finalemail = Enteredemail.concat(emaildomain);
                        Log.e(TAG, "final email to pass: " + Finalemail );

                        register();

                    }
                }

            }
        });


    }


    private void register() {
        universityid= spinnerList.get(spinner.getSelectedItemPosition()).id;
        final ProgressDialog progressDialog = new ProgressDialog(SignupPage.this);
        progressDialog.setTitle("Signing up");
        progressDialog.setMessage("Please Wait !");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);
        Call<ResponseBody> call = apiClient.register(First_name.getText().toString(),Last_name.getText().toString(),Finalemail,"",universityid, String.valueOf(tutororstudent),signup_confirm_password.getText().toString(),"");
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
                    Log.e(TAG, "onResponse: " + rootObj );
                    String status = rootObj.getString("status");
                    Log.e(TAG, "onResponse: " + status );
                    if (status.equals("1")) {
                        Intent intent=new Intent(getApplicationContext(),login.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Log.e("Registration",rootObj.getString("error_msg"));
                        Toast.makeText(getApplicationContext(),  rootObj.getString("error_msg"), Toast.LENGTH_SHORT).show();

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




    private void getUniversityList() {
        final ProgressDialog progressDialog = new ProgressDialog(SignupPage.this);
        progressDialog.setTitle("Loading Content");
        progressDialog.setMessage("Please Wait !");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);
        Call<ResponseBody> call = apiClient.getUniversityList();
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
                    if (status.equals("1")) {
                        JSONArray jsonArray = rootObj.getJSONArray("data");
                        Log.e(TAG, "onResponse: "+ status );
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.e(TAG, "i " + i);
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String id = obj.getString("id");
                            String university_name = obj.getString("university_name");
                            String email_domain = obj.getString("email_domain");
                            spinnerList.add(new UniversityModel(id, university_name, email_domain));
                        }

                        ArrayAdapter<UniversityModel> adapter = new ArrayAdapter<UniversityModel>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, spinnerList);
                        spinner.setAdapter(adapter);

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
