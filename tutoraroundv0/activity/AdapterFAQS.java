package com.example.panchal.tutoraroundv0.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.panchal.tutoraroundv0.R;
import com.example.panchal.tutoraroundv0.modelClasses.FAQS_API;
import com.example.panchal.tutoraroundv0.modelClasses.MyPostList_API;

import java.util.ArrayList;

public class AdapterFAQS extends RecyclerView.Adapter<AdapterFAQS.MyViewHolder> {


    private Context activity;
    ArrayList<FAQS_API> faqs_list;
    private static String TAG = "AdapterFAQS";

    public AdapterFAQS(Context context, ArrayList<FAQS_API> faqs_list) {
        this.activity = context;
        this.faqs_list = faqs_list;

    }

    TextView questions, answers;


    public class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(View view) {
            super(view);
            this.setIsRecyclable(false);
            questions = view.findViewById(R.id.faqs_questions);
            answers = view.findViewById(R.id.faqs_answers);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.faqs_cell, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        FAQS_API obj = faqs_list.get(position);

        questions.setText(obj.getQuestion());
        answers.setText(Html.fromHtml(obj.getAnswer()));
//
//        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return faqs_list.size();
    }
}
