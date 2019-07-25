package com.example.panchal.tutoraroundv0.bottomnavFragments;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.panchal.tutoraroundv0.R;

import com.example.panchal.tutoraroundv0.activity.AddCourseForTutor;
import com.example.panchal.tutoraroundv0.activity.ApiClient;
import com.example.panchal.tutoraroundv0.activity.FAQS;
import com.example.panchal.tutoraroundv0.activity.ServiceGenerator;
import com.example.panchal.tutoraroundv0.activity.UpdatePassword;
import com.example.panchal.tutoraroundv0.activity.login;
import com.example.panchal.tutoraroundv0.modelClasses.TutorsSubjectList;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfile extends Fragment {


    public FragmentProfile() {
        // Required empty public constructor
    }

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    CircleImageView profileimage;
    Integer REQUEST_GET_SINGLE_FILE = 1;
    String user_ID, Tutor;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ImageView toolbarimage;
    TextView persons_name, persons_email, profile_subjects_known_text;
    Button add_skills;
    RecyclerView recyclerView;

    String fname,lname,id,email,course_id,course_code,course_name;

    ArrayList<TutorsSubjectList> tutorsSubjectList = new ArrayList<>();

    private static String TAG = "FragmentProfile";


    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_profile, container, false);
        navigationView = view.findViewById(R.id.navigation_drawer);
        drawerLayout = view.findViewById(R.id.drawer_layout);
        toolbar = view.findViewById(R.id.profile_toolbar);
        toolbarimage = toolbar.findViewById(R.id.toolbarimg);
        profileimage = view.findViewById(R.id.profile_image);
        persons_name = view.findViewById(R.id.name_text_profile);
        persons_email = view.findViewById(R.id.profile_email);
        add_skills = view.findViewById(R.id.profile_addskills);
        profile_subjects_known_text = view.findViewById(R.id.profile_subjects_known_text);
        recyclerView = view.findViewById(R.id.profile_subject_list);


        // shared prefrence data retrival
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //getting userid
        user_ID = preferences.getString("user_id", null);
        Tutor = preferences.getString("tutorcheck", null);
        Log.e(TAG, "user id madeyo  " + user_ID);


        if (Tutor.equals(String.valueOf(1))) {
            add_skills.setVisibility(View.GONE);
            profile_subjects_known_text.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        }



        ProfileDetails();

        //hamburger icon opening

        toolbarimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.END);
            }
        });

        add_skills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Objects.requireNonNull(getActivity()).startActivity(new Intent(getActivity(), AddCourseForTutor.class));
            }
        });


        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GET_SINGLE_FILE);
            }
        });


        //setHasOptionsMenu(true);

        // navigation drawer working
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.Security:
                        startActivity(new Intent(getActivity(), UpdatePassword.class));
                        return true;
                    case R.id.share:
                        Share();
                        return true;
                    case R.id.logout:

                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(getActivity());
                        }
                        builder.setTitle("Logout")
                                .setMessage("Are you sure you want to logout ?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        logout();
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                        return true;

                    case R.id.FAQS:
                        startActivity(new Intent(getActivity(), FAQS.class));
                        return true;

                    case R.id.switch_account_type:
                        builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Dialog_Alert);
                        builder.setTitle("Switch User Type")
                                .setMessage("Are you sure ?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Switch();
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .show();

                        return true;

                    default:
                        return false;
                }

            }
        });


        return view;
    }


    public void Share() {


        final String appPackageName = getContext().getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out the App at: https://play.google.com/store/apps/details?id=" + appPackageName);
        sendIntent.setType("text/plain");
        getContext().startActivity(sendIntent);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_GET_SINGLE_FILE) {
                    Uri selectedImageUri = data.getData();
                    // Get the path from the Uri
                    final String path = getPathFromURI(selectedImageUri);
                    if (path != null) {
                        File f = new File(path);
                        selectedImageUri = Uri.fromFile(f);
                    }
                    // Set the image in ImageView
                    profileimage.setImageURI(selectedImageUri);

                }
            }
        } catch (Exception e) {
            Log.e("FileSelectorActivity", "File select error", e);
        }
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }


    public void ProfileDetails() {


        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Getting Details");
        progressDialog.setMessage("Please Wait !");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);

        Call<ResponseBody> call = apiClient.viewProfile(user_ID);

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
                        Log.e(TAG, " " + jsonArray);


                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.e(TAG, "main data details " + i);
                            JSONObject details_object = jsonArray.getJSONObject(i);

                            JSONArray basic_detail = details_object.getJSONArray("basic_detail");
                            JSONArray course_details = details_object.getJSONArray("course_details");


                            Log.e(TAG, "persons basci details " + basic_detail + " | SOMEthing");


                            for (int j = 0; j < basic_detail.length(); j++) {

                                JSONObject basic_detail_object = basic_detail.getJSONObject(j);
                                Log.e(TAG, "basic_detail_object    " + new Gson().toJson(basic_detail_object));


                                fname = basic_detail_object.getString("fname");
                                lname = basic_detail_object.getString("lname");
                                id = basic_detail_object.getString("id");
                                email = basic_detail_object.getString("email");

                                Log.e(TAG, "post detail madi gay  " + fname + lname);

                                String final_name = fname.concat(lname);
                                persons_name.setText(final_name);
                                persons_email.setText(email);



                            }
                            

                            for (int x = 0; x < course_details.length(); x++) {
                                JSONObject course_details_object = course_details.getJSONObject(x);
                                Log.e(TAG, "tutor list of cources " + course_details_object + "| NULL");

                                course_id = course_details_object.getString("course_id");
                                course_code = course_details_object.getString("course_code");
                                course_name = course_details_object.getString("course_name");

                                tutorsSubjectList.add(new TutorsSubjectList(course_id,course_code,course_name));
                            }

                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                            AdapterTutorsSubjectList adapterTutorsSubjectList = new AdapterTutorsSubjectList(getActivity(),tutorsSubjectList);
                            recyclerView.setAdapter(adapterTutorsSubjectList);


                        }


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

                Toast.makeText(getActivity(), "Error " + call, Toast.LENGTH_LONG).show();
            }
        });


    }


    public void Switch() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Switching Type");
        progressDialog.setMessage("Please Wait !");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);

        Call<ResponseBody> call = apiClient.switchUser(user_ID);

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
                        Intent intent = new Intent(getActivity(), login.class);
                        startActivity(intent);

                    } else {

                        Toast.makeText(getActivity(), rootObj.getString("error_msg"), Toast.LENGTH_LONG).show();

                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(getActivity(), "Error " + call, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void logout() {


        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Logging Out");
        progressDialog.setMessage("Please Wait !");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);


        Call<ResponseBody> call = apiClient.logoutCall(user_ID, "");
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
                        Intent intent = new Intent(getActivity(), login.class);
                        startActivity(intent);

                    } else {

                        Toast.makeText(getActivity(), rootObj.getString("error_msg"), Toast.LENGTH_LONG).show();

                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(getActivity(), "Error " + call, Toast.LENGTH_LONG).show();
            }
        });


    }


}

