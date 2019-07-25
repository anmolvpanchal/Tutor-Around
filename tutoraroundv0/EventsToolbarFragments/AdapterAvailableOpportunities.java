package com.example.panchal.tutoraroundv0.EventsToolbarFragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.panchal.tutoraroundv0.R;
import com.example.panchal.tutoraroundv0.activity.ApiClient;
import com.example.panchal.tutoraroundv0.activity.BottomNavigation;
import com.example.panchal.tutoraroundv0.activity.MyCallBack;
import com.example.panchal.tutoraroundv0.activity.PerticularPostDetails;
import com.example.panchal.tutoraroundv0.activity.ServiceGenerator;
import com.example.panchal.tutoraroundv0.activity.login;
import com.example.panchal.tutoraroundv0.modelClasses.Available_opportunities_API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class AdapterAvailableOpportunities extends RecyclerView.Adapter<AdapterAvailableOpportunities.MyViewHolder> {
    String myid,forieg;


    private Activity activity;
    ArrayList<Available_opportunities_API> available_opportunities;
    private static String TAG = "AdapterAvailableOpportunities";
    String counterofferbytutor;
    String user_ID;
    SharedPreferences preferences;
    Available_opportunities_API obj;


    public AdapterAvailableOpportunities(Activity activity, ArrayList<Available_opportunities_API> subjects) {
        this.activity = activity;
        this.available_opportunities = subjects;
    }

    public TextView Subjectname, Date, Time, Subjectdescription, actualdate, actualtime, amount;
    Button counter_offer;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View view) {
            super(view);

            amount = view.findViewById(R.id.available_opp_amount);
            counter_offer = view.findViewById(R.id.counteroffer);
            actualtime = view.findViewById(R.id.available_opp_actual_time);
            actualdate = view.findViewById(R.id.available_opp_actual_date);
            Subjectname = view.findViewById(R.id.available_opp_subjectname);
            Date = view.findViewById(R.id.available_opp_date);
            Time = view.findViewById(R.id.available_opp_time);
            Subjectdescription = view.findViewById(R.id.available_opp_description);

        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.available_opportunities_cell, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {


        // shared prefrence data retrival
        preferences = PreferenceManager.getDefaultSharedPreferences(activity);

        //getting userid
        user_ID = preferences.getString("user_id", null);
        Log.e(TAG, "user id madeyo  " + user_ID);



        obj = available_opportunities.get(position);
        final String perticular_postid = obj.getId();
        actualdate.setText(obj.getDate());
        actualtime.setText(obj.getTime());
        Subjectdescription.setText(obj.getDescription());
        Subjectname.setText(obj.getCourse_name());
        amount.setText(obj.getPrice());

        counter_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myid = available_opportunities.get(position).getId();
                forieg= available_opportunities.get(position).getCourse_fk();

                openDialog();

            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activity.startActivity(new Intent(activity, PerticularPostDetails.class).putExtra("id",perticular_postid));
                Log.e(TAG, "my id in available opp" + perticular_postid );

            }
        });


    }


    @Override
    public int getItemCount() {
        return available_opportunities.size();
    }

    public void openDialog() {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.layout_dialod);
        final EditText edtCounterOffer = dialog.findViewById(R.id.edtCounterOffer);
        Button submit = dialog.findViewById(R.id.layout_dialog_Submit);

        dialog.show();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counterofferbytutor = edtCounterOffer.getText().toString();
                if (edtCounterOffer.getText().toString().isEmpty()) {
                    edtCounterOffer.setError(" Enter some Ammount");
                } else {
                      counteroffer();
                    dialog.dismiss();
                }

            }
        });

    }


    public void counteroffer() {


        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Counteroffering");
        progressDialog.setMessage("Please Wait !");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);


        Call<ResponseBody> call = apiClient.counterOfferCall(myid, forieg, user_ID, counterofferbytutor);
        Log.e("user_ID", "" + user_ID);
        Log.e("obj.getId()", "" + obj.getId());
        Log.e("obj.courseFk", "" + obj.getCourse_fk());

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
                    Log.e(TAG, "jsonobj" + rootObj);
                    String root_status = rootObj.getString("status");
                    Log.e(TAG, "root_status" + root_status);
                    if (root_status.equals("1")) {

                        Toast.makeText(activity, rootObj.getString("error_msg"), Toast.LENGTH_LONG).show();
                        notifyDataSetChanged();

                    } else {
                        Toast.makeText(activity, rootObj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(activity, " Some error in processing " + call, Toast.LENGTH_LONG).show();

            }
        });

    }


}
