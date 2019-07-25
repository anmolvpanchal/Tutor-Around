package com.example.panchal.tutoraroundv0.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.panchal.tutoraroundv0.R;
import com.example.panchal.tutoraroundv0.modelClasses.Mytutorslist;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class AdapterMyPostsTutorslist extends RecyclerView.Adapter<AdapterMyPostsTutorslist.MyViewHolder> {

    Context activity;

    Mytutorslist obj;
    ArrayList<Mytutorslist> mytutorslists;
    TextView tutorsname, tutorsrating, amount;
    Button accept, reject;
    String fname, lname, final_name;
    String postid, Tutors_id, post_assigned_no;

    String Tutor;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    private static String TAG = "AdapterMyPostsTutorslist";
    private String user_ID;

    public AdapterMyPostsTutorslist(Context activity, ArrayList<Mytutorslist> mytutorslists, String api_id, String api_assign) {
        this.activity = activity;
        this.mytutorslists = mytutorslists;
        this.postid = api_id;
        this.post_assigned_no = api_assign;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View view) {
            super(view);

            tutorsname = view.findViewById(R.id.tutor_list_tutor_name);
            tutorsrating = view.findViewById(R.id.tutor_list_tutor_rating);
            amount = view.findViewById(R.id.tutor_list_actual_amount);
            accept = view.findViewById(R.id.tutor_list_accept);
            reject = view.findViewById(R.id.tutor_list_reject);

        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tutor_list_cell, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {

        obj = mytutorslists.get(position);


        fname = obj.getFname();
        lname = obj.getLname();
        final_name = fname.concat(" " + lname);
        tutorsname.setText(final_name);
        tutorsrating.setText(obj.getRating());
        amount.setText(obj.getOffer_price());

        Log.e(TAG, " tutor ne detais for my post " + obj.getOffer_price() + obj.getRating());


        // shared prefrence data retrival
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);

        //getting userid
        user_ID = preferences.getString("user_id", null);
        Tutor = preferences.getString("tutorcheck", null);
        Log.e("Tutor", "" + Tutor);
        Log.e(TAG, "user id madeyo  " + user_ID);


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Tutors_id = mytutorslists.get(position).getUser_fk();

                AcceptTutor();

            }
        });


        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  Tutors_id = mytutorslists.get(position).getUser_fk();

                RejectTutor();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mytutorslists.size();
    }

    public void RejectTutor() {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Rejecting offer");
        progressDialog.setMessage("Please Wait !");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);

        Call<ResponseBody> call = apiClient.declineOfferCall(postid, user_ID, Tutors_id);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                progressDialog.dismiss();
                if (!response.isSuccessful()) {
                    Toast.makeText(activity, "Something went Wrong !!" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                try {

                    String resp = response.body().string();
                    JSONObject rootObj = new JSONObject(resp);
                    Log.e(TAG, "onResponse: " + rootObj);
                    String root_status = rootObj.getString("status");
                    Log.e(TAG, "onResponse: " + root_status);
                    if (root_status.equals("1")) {
                        Toast.makeText(activity, rootObj.getString("error_msg"), Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(activity, rootObj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(activity, "error in parsing calling " + call, Toast.LENGTH_LONG).show();
            }
        });


    }


    public void AcceptTutor() {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Accepting");
        progressDialog.setMessage("Please Wait !");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);

        Call<ResponseBody> call = apiClient.acceptOfferCall(postid, user_ID, Tutors_id);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                progressDialog.dismiss();
                if (!response.isSuccessful()) {
                    Toast.makeText(activity, "Something went Wrong !!" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                try {

                    String resp = response.body().string();
                    JSONObject rootObj = new JSONObject(resp);
                    Log.e(TAG, "onResponse: " + rootObj);
                    String root_status = rootObj.getString("status");
                    Log.e(TAG, "onResponse: " + root_status);
                    if (root_status.equals("1")) {
                        Toast.makeText(activity, rootObj.getString("error_msg"), Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(activity, rootObj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(activity, "error in parsing calling " + call, Toast.LENGTH_LONG).show();
            }
        });


    }


}
