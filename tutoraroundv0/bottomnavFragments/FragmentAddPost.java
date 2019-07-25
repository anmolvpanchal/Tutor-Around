package com.example.panchal.tutoraroundv0.bottomnavFragments;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.panchal.tutoraroundv0.R;
import com.example.panchal.tutoraroundv0.activity.ApiClient;
import com.example.panchal.tutoraroundv0.activity.BottomNavigation;
import com.example.panchal.tutoraroundv0.activity.ServiceGenerator;
import com.example.panchal.tutoraroundv0.activity.SignupPage;
import com.example.panchal.tutoraroundv0.activity.UniversityModel;
import com.example.panchal.tutoraroundv0.activity.login;
import com.example.panchal.tutoraroundv0.modelClasses.Course_list_API;
import com.example.panchal.tutoraroundv0.modelClasses.LoginModelAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAddPost extends Fragment {


    public FragmentAddPost() {
        // Required empty public constructor
    }

    ArrayList<Course_list_API> course_name_list = new ArrayList<>();

    public static String TAG = "FragmentAddPost";

    Button btnDatePicker, btnTimePicker, btnpost;
    Spinner spinner_subjectname;
    EditText addpost_edtdescription, addpost_code, addpost_Budgetamounttext;
    Spinner addpost_duration;
    public int mYear, mMonth, mDay, mHour, mMinute;
    LoginModelAPI loginModelAPI;
    String user_ID, course_ID, selectedslot, selectedDate, selectedTime;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // shared prefrence data retrival
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //getting userid
        user_ID = preferences.getString("user_id", null);
        Log.e(TAG, "user id madeyo  " + user_ID);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_post, container, false);

        getCourseList();

        addpost_code = view.findViewById(R.id.addpost_codetext);
        addpost_edtdescription = view.findViewById(R.id.descriptiontext);
        btnpost = view.findViewById(R.id.post);
        addpost_Budgetamounttext = view.findViewById(R.id.addpost_Budgetamounttext);
        btnDatePicker = view.findViewById(R.id.btn_date);
        addpost_duration = view.findViewById(R.id.addpost_duration);
        btnTimePicker = view.findViewById(R.id.btn_time);
        spinner_subjectname = view.findViewById(R.id.addpost_subject);


        final List<String> Duration_list = new ArrayList<String>();
        Duration_list.add("00:30");
        Duration_list.add("01:00");
        Duration_list.add("01:30");
        Duration_list.add("02:00");
        Duration_list.add("02:30");
        Duration_list.add("03:00");
        Duration_list.add("03:30");
        Duration_list.add("04:00");
        Duration_list.add("04:30");
        Duration_list.add("05:00");
        Duration_list.add("05:30");
        Duration_list.add("06:00");
        Duration_list.add("06:30");
        Duration_list.add("07:00");
        Duration_list.add("07:30");
        Duration_list.add("08:00");

        ArrayAdapter<String> durationlist_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Duration_list);
        durationlist_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addpost_duration.setAdapter(durationlist_adapter);


        addpost_duration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedslot = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        selectedDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        btnDatePicker.setText(selectedDate);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();


            }
        });

        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {


                        selectedTime = hourOfDay + ":" + minute;
                        btnTimePicker.setText(selectedTime);
                    }
                }, mHour, mMinute, false);
                timePickerDialog.show();


            }
        });

        btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validation()) {
                    addpost();
                }

            }
        });


        spinner_subjectname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                course_ID = course_name_list.get(spinner_subjectname.getSelectedItemPosition()).getId();
                Log.e(TAG, "selected course no id madeyo " + course_ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        return view;
    }


    public boolean validation() {

        if (addpost_code.getText().toString().isEmpty()) {
            addpost_code.setError("Please Enter Code of subject!");
            addpost_code.setFocusable(true);
        } else if (addpost_edtdescription.getText().toString().isEmpty()) {
            addpost_edtdescription.setError("Please Enter some details!");
            addpost_edtdescription.setFocusable(true);
        } else if (addpost_Budgetamounttext.getText().toString().isEmpty()) {
            addpost_Budgetamounttext.setError("Please Enter amount!");
            addpost_Budgetamounttext.setFocusable(true);
        } else if (btnDatePicker.getText().equals("SELECT DATE")) {
            btnDatePicker.setError("please select a Date");
        } else if (btnTimePicker.getText().equals("SELECT TIME")) {
            btnTimePicker.setError("please select time");
        } else {
            return true;
        }
        return false;
    }


    public void getCourseList() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
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
                    Toast.makeText(getActivity(), "Something went Wrong !!" + response.code(), Toast.LENGTH_SHORT).show();
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

                        ArrayAdapter<Course_list_API> adapter = new ArrayAdapter<Course_list_API>(getActivity(), R.layout.support_simple_spinner_dropdown_item, course_name_list);
                        spinner_subjectname.setAdapter(adapter);


                    } else {
                        Log.e("Registration", rootObj.getString("error_msg"));
                        Toast.makeText(getActivity(), rootObj.getString("error_msg"), Toast.LENGTH_SHORT).show();

                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(getActivity(), "Something went Wrong !!" + call, Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void addpost() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Posting request");
        progressDialog.setMessage("Please Wait !");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);
        Call<ResponseBody> call = apiClient.addPost(user_ID, course_ID, addpost_code.getText().toString(), selectedDate, selectedTime, selectedslot, addpost_Budgetamounttext.getText().toString(), addpost_edtdescription.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                progressDialog.dismiss();
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Something went Wrong !!" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                try {

                    String resp = response.body().string();
                    JSONObject rootObj = new JSONObject(resp);
                    Log.e(TAG, "onResponse: " + rootObj);
                    String root_status = rootObj.getString("status");
                    Log.e(TAG, "onResponse: " + root_status);

                    if (root_status.equals("1")) {
                        Toast.makeText(getActivity(), rootObj.getString("error_msg"), Toast.LENGTH_LONG).show();

                        startActivity(new Intent(getActivity(), BottomNavigation.class).putExtra("isFromAddPost", true));

                    } else {

                        Toast.makeText(getActivity(), rootObj.getString("error_msg"), Toast.LENGTH_SHORT).show();

                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went Wrong !!" + call, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
