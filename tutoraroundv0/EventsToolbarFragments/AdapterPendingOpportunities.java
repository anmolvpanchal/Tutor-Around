package com.example.panchal.tutoraroundv0.EventsToolbarFragments;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.panchal.tutoraroundv0.R;
import com.example.panchal.tutoraroundv0.activity.PerticularPostDetails;
import com.example.panchal.tutoraroundv0.modelClasses.Pending_opportunities_API;

import java.util.ArrayList;

class AdapterPendingOpportunities extends RecyclerView.Adapter <AdapterPendingOpportunities.MyViewHolder>{


    private Context activity;
    ArrayList<Pending_opportunities_API> pending_opportunities;

    public AdapterPendingOpportunities(Context activity, ArrayList<Pending_opportunities_API> subjects) {
        this.activity = activity;
        this.pending_opportunities = subjects;
    }

    public TextView Subjectname,Date,Time,Subjectdescription,actual_date,actual_time,offeredprice;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View view) {
            super(view);

            actual_time = view.findViewById(R.id.pending_opp_actual_time);
            actual_date = view.findViewById(R.id.pending_opp_actual_date);
            Subjectname = view.findViewById(R.id.pending_opp_subjectname);
            Date = view.findViewById(R.id.pending_opp_date);
            Time = view.findViewById(R.id.pending_opp_time);
            Subjectdescription = view.findViewById(R.id.pending_opp_description);
            offeredprice = view.findViewById(R.id.pending_opp_actual_offeredprice);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_opportunties_cell, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {


        Pending_opportunities_API obj = pending_opportunities.get(position);
        actual_date.setText(obj.getDate());
        actual_time.setText(obj.getTime());
        Subjectdescription.setText(obj.getDescription());
        Subjectname.setText(obj.getCourse_name());
        offeredprice.setText(obj.getOffer_price());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activity.startActivity(new Intent(activity, PerticularPostDetails.class).putExtra("id", pending_opportunities.get(position).getId()));

            }
        });




    }

    @Override
    public int getItemCount() {
        return pending_opportunities.size();
    }



}
