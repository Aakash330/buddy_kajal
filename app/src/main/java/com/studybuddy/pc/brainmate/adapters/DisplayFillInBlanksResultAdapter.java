package com.studybuddy.pc.brainmate.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.studybuddy.pc.brainmate.R;

import java.util.ArrayList;
import java.util.HashMap;

public class DisplayFillInBlanksResultAdapter extends RecyclerView.Adapter<DisplayFillInBlanksResultAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HashMap<String, String>> fillList = new ArrayList<>();

    public DisplayFillInBlanksResultAdapter(Context context, ArrayList<HashMap<String, String>> fillList) {
        this.context = context;
        this.fillList = fillList;
    }

    @NonNull
    @Override
    public DisplayFillInBlanksResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.displayfillresult, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayFillInBlanksResultAdapter.ViewHolder holder, int position) {

        holder.txtMarksFB.setText("Marks : " + fillList.get(position).get("each_fill_mark"));
        holder.txtFBQuestion.setText(fillList.get(position).get("ques"));
        holder.txtFBAnswer.setText(fillList.get(position).get("Answer"));
        holder.txtQuestionNoFB.setText(fillList.get(position).get("count"));
        holder.txtFBYourAnswer.setText(fillList.get(position).get("MarkedANS"));
        holder.txtYourAnsA.setTextColor(fillList.get(position).get("Answer").equalsIgnoreCase(fillList.get(position).get("MarkedANS")) ? context.getResources().getColor(R.color.green) : context.getResources().getColor(R.color.red));
        //holder.txtFBYourAnswer.set
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            if (!fillList.get(position).get("Answer").equals("") && fillList.get(position).get("Answer").equals(fillList.get(position).get("MarkedANS"))) {
                holder.txtFBYourAnswer.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.green_card));
            }
            if (!fillList.get(position).get("Answer").equals(fillList.get(position).get("MarkedANS"))) {
                holder.txtFBYourAnswer.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.red_card));
            }
            //------------------------------------------------------------------------------------------------------------
        } else if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!fillList.get(position).get("Answer").equals("") && fillList.get(position).get("Answer").equals(fillList.get(position).get("MarkedANS"))) {
                holder.txtFBYourAnswer.setBackground(context.getResources().getDrawable(R.drawable.green_card));
            }
            if (!fillList.get(position).get("Answer").equals(fillList.get(position).get("MarkedANS"))) {
                holder.txtFBYourAnswer.setBackground(context.getResources().getDrawable(R.drawable.red_card));
            }
            //------------------------------------------------------------------------------------------------------------
        } else {
            if (!fillList.get(position).get("Answer").equals("") && fillList.get(position).get("Answer").equals(fillList.get(position).get("MarkedANS"))) {
                holder.txtFBYourAnswer.setBackground(ContextCompat.getDrawable(context, R.drawable.green_card));
            }
            if (!fillList.get(position).get("Answer").equals(fillList.get(position).get("MarkedANS"))) {
                holder.txtFBYourAnswer.setBackground(ContextCompat.getDrawable(context, R.drawable.red_card));
            }
            //------------------------------------------------------------------------------------------------------------
        }
        //holder.txtYourAnsA.setTextColor();
    }

    @Override
    public int getItemCount() {
        return fillList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMarksFB, txtFBQuestion, txtFBAnswer, txtQuestionNoFB, txtCorrectAnsA, txtYourAnsA, txtFBYourAnswer;

        public ViewHolder(View itemView) {
            super(itemView);
            txtMarksFB = itemView.findViewById(R.id.txtMarksFB);
            txtFBQuestion = itemView.findViewById(R.id.txtFBQuestion);
            txtFBAnswer = itemView.findViewById(R.id.txtFBAnswer);
            txtQuestionNoFB = itemView.findViewById(R.id.txtQuestionNoFB);

            txtCorrectAnsA = itemView.findViewById(R.id.txtCorrectAnsA);
            txtYourAnsA = itemView.findViewById(R.id.txtYourAnsA);
            txtFBYourAnswer = itemView.findViewById(R.id.txtFBYourAnswer);
        }
    }
}
