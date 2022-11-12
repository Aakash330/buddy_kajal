package com.studybuddy.pc.brainmate.teacher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.studybuddy.pc.brainmate.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MatchMakingAdapter extends BaseAdapter {

    // Declare Variables
    ArrayList<HashMap<String, String>> books;

    QuetionPaper mContext;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> animalNamesList = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> map;
    String upcomigID;


    public MatchMakingAdapter(QuetionPaper studentdashBord, ArrayList<HashMap<String, String>> booksname) {
        this.mContext = studentdashBord;
        this.books = booksname;
        inflater = LayoutInflater.from((Context) mContext);

    }

    public class ViewHolder {
        TextView name, ID, Number, heading;
        // Button details,Medicalhistory;
        ImageView imgg;
        CheckBox checkbox10;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.short_answer_layout, null);
            holder.heading = (TextView) view.findViewById(R.id.heading);
            holder.checkbox10 = (CheckBox) view.findViewById(R.id.checkbox10);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        map = new HashMap<>(position);

        holder.heading.setText(books.get(position).get("Quetion"));
        holder.checkbox10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                /*
                 *//*   int Marks=0;
                    if(Marksfor_ShortQuestions.getText().toString().isEmpty()==true)
                    {
                        Toast.makeText(mContext,"Please Enter Marks for each question First",Toast.LENGTH_LONG).show();
                        holder.checkbox10.setChecked(false);
                    }*//*
                    else {
                        if(holder.checkbox10.isChecked()==true)
                        {



                           *//* ToatlMarksLOgnd =ToatlMarksLOgnd+Integer.parseInt(Marksfor_ShortQuestions.getText().toString());
                            Log.d("MarksMarksMarks", String.valueOf(ToatlMarks));
                            ShortAnswer.setText(String.valueOf(ToatlMarksLOgnd));
                            HashMap<String,String>confimition=new HashMap<>();
                            confimition.put("Confirmation",books.get(position).get("Quetion"));
                            confimition.put("Marks", String.valueOf(Marks));
                            SubjectiveAdaptershoet.add(confimition);
                            int LongAnswerT= Integer.parseInt(LongAnswer.getText().toString());
                            int ShortAnswerT=Integer.parseInt(ShortAnswer.getText().toString());
                            int MultipleAnswerT=Integer.parseInt(MultipleAnswer.getText().toString());
                            int fillinblacksT=Integer.parseInt(fillinblacks.getText().toString());
                            int truefalseT=Integer.parseInt(truefalse.getText().toString());
                            finalTotalmarks=LongAnswerT+ShortAnswerT+MultipleAnswerT+fillinblacksT+truefalseT;
                            TotalMarksbutton.setText(String.valueOf(finalTotalmarks));*//*
                        }
                        else if(holder.checkbox10.isChecked()==false) {


*//*
                            ToatlMarksLOgnd = ToatlMarksLOgnd - Integer.parseInt(Marksfor_ShortQuestions.getText().toString());
                            Log.d("MarksMarksMarks", String.valueOf(ToatlMarks));
                            ShortAnswer.setText(String.valueOf(ToatlMarksLOgnd));
                            int LongAnswerT = Integer.parseInt(LongAnswer.getText().toString());
                            int ShortAnswerT = Integer.parseInt(ShortAnswer.getText().toString());
                            int MultipleAnswerT = Integer.parseInt(MultipleAnswer.getText().toString());
                            int fillinblacksT = Integer.parseInt(fillinblacks.getText().toString());
                            int truefalseT = Integer.parseInt(truefalse.getText().toString());
                            finalTotalmarks = LongAnswerT + ShortAnswerT + MultipleAnswerT + fillinblacksT + truefalseT;
                            TotalMarksbutton.setText(String.valueOf(finalTotalmarks));
                            SubjectiveAdaptershoet.remove(position).get("Confirmation");*//*
                        }}*/
            }
        });

        return view;
    }


}


