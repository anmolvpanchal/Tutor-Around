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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.panchal.tutoraroundv0.R;
import com.example.panchal.tutoraroundv0.bottomnavFragments.FragmentProfile;
import com.example.panchal.tutoraroundv0.modelClasses.Course_list_API;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCourseForTutor extends AppCompatActivity {

    private static String TAG = "AddCourseForTutor";

    Spinner spinner;
    TextInputEditText code_edittext;
    Button button_add;
    SharedPreferences preferences;
    String user_ID, course_ID, course_name;

    ArrayList<Course_list_API> course_name_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_for_tutor);

        spinner = findViewById(R.id.addsubjects_spinner);
        code_edittext = findViewById(R.id.addsubjects_TextInputEditText);
        button_add = findViewById(R.id.addsubjects_add_button);

        // shared prefrence data retrival
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //getting userid
        user_ID = preferences.getString("user_id", null);

        getCourseList();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                course_ID = course_name_list.get(spinner.getSelectedItemPosition()).getId();
                course_name = course_name_list.get(spinner.getSelectedItemPosition()).getCourse_name();
                Log.e(TAG, "selected course no id madeyo " + course_ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Model> modelList = new ArrayList<>();
                modelList.add(new Model(code_edittext.getText().toString(), course_ID, course_name));
                addSkillCall(new Gson().toJson(modelList));
            }
        });

    }

    private void addSkillCall(String jsonData) {


        final ProgressDialog progressDialog = new ProgressDialog(AddCourseForTutor.this);
        progressDialog.setTitle("Getting subjects");
        progressDialog.setMessage("Please Wait !");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);


        Call<ResponseBody> call = apiClient.addCourseDetails(user_ID, jsonData);

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
                        Log.e(TAG, "course added" + rootObj.getString("error_msg") );

                    } else {

                        Toast.makeText(getApplicationContext(), rootObj.getString("error_msg"), Toast.LENGTH_SHORT).show();

                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Something went Wrong !!" + call, Toast.LENGTH_SHORT).show();
            }
        });


    }

    class Model {
        String ccode, course_id, subjectName;

        public String getCcode() {
            return ccode;
        }

        public void setCcode(String ccode) {
            this.ccode = ccode;
        }

        public String getCourse_id() {
            return course_id;
        }

        public void setCourse_id(String course_id) {
            this.course_id = course_id;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public Model(String ccode, String course_id, String subjectName) {
            this.ccode = ccode;
            this.course_id = course_id;
            this.subjectName = subjectName;
        }
    }


    public void getCourseList() {


        final ProgressDialog progressDialog = new ProgressDialog(AddCourseForTutor.this);
        progressDialog.setTitle("Getting subjects");
        progressDialog.setMessage("Please Wait !");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);


        Call<ResponseBody> call = apiClient.getCourseList(user_ID);
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


                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.e(TAG, "i " + i);
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String id = obj.getString("id");
                            String course_name = obj.getString("course_name");
                            String status = obj.getString("status");
                            course_name_list.add(new Course_list_API(id, course_name, status));

                        }

                        ArrayAdapter<Course_list_API> adapter = new ArrayAdapter<Course_list_API>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, course_name_list);
                        spinner.setAdapter(adapter);


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

                Toast.makeText(getApplicationContext(), "Something went Wrong !!" + call, Toast.LENGTH_SHORT).show();
            }
        });


    }

}
