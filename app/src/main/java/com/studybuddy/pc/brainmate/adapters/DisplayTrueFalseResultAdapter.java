package com.studybuddy.pc.brainmate.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.studybuddy.pc.brainmate.R;

import java.util.ArrayList;
import java.util.HashMap;

public class DisplayTrueFalseResultAdapter extends RecyclerView.Adapter<DisplayTrueFalseResultAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HashMap<String, String>> tfList = new ArrayList<>();

    public DisplayTrueFalseResultAdapter(Context context, ArrayList<HashMap<String, String>> tfList) {
        this.context = context;
        this.tfList = tfList;
    }

    @NonNull
    @Override
    public DisplayTrueFalseResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.displaytfresult, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayTrueFalseResultAdapter.ViewHolder holder, int position) {

        holder.txtMarksTF.setText("Marks : " + tfList.get(position).get("each_tf_mark"));
        holder.txtTFQno.setText(tfList.get(position).get("count"));
        holder.txtTFQuestion.setText(tfList.get(position).get("ques"));

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            if (tfList.get(position).get("ans").equals("1")) {
                holder.rbTrue.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.green_card));
            }
            if (tfList.get(position).get("ans").equals("0")) {
                holder.rbFalse.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.green_card));
            }
            //---------------------------------------------------------------------------------------------------------------
            if (tfList.get(position).get("ans").equals("0") && tfList.get(position).get("MarkedANS").equals("1")) {
                holder.rbTrue.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.red_card));
            }
            if (tfList.get(position).get("ans").equals("1") && tfList.get(position).get("MarkedANS").equals("0")) {
                holder.rbFalse.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.red_card));
            }
            //------------------------------------------------------------------------------------------------------------
            if (tfList.get(position).get("ans").equals("1") && tfList.get(position).get("MarkedANS").equals("1")) {
                holder.rbTrue.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.green_card));
            }
            if (tfList.get(position).get("ans").equals("0") && tfList.get(position).get("MarkedANS").equals("0")) {
                holder.rbFalse.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.green_card));
            }
        } else if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (tfList.get(position).get("ans").equals("1")) {
                holder.rbTrue.setBackground(context.getResources().getDrawable(R.drawable.green_card));
            }
            if (tfList.get(position).get("ans").equals("0")) {
                holder.rbFalse.setBackground(context.getResources().getDrawable(R.drawable.green_card));
            }
            //---------------------------------------------------------------------------------------------------------------
            if (tfList.get(position).get("ans").equals("0") && tfList.get(position).get("MarkedANS").equals("1")) {
                holder.rbTrue.setBackground(context.getResources().getDrawable(R.drawable.red_card));
            }
            if (tfList.get(position).get("ans").equals("1") && tfList.get(position).get("MarkedANS").equals("0")) {
                holder.rbFalse.setBackground(context.getResources().getDrawable(R.drawable.red_card));
            }
            //------------------------------------------------------------------------------------------------------------
            if (tfList.get(position).get("ans").equals("1") && tfList.get(position).get("MarkedANS").equals("1")) {
                holder.rbTrue.setBackground(context.getResources().getDrawable(R.drawable.green_card));
            }
            if (tfList.get(position).get("ans").equals("0") && tfList.get(position).get("MarkedANS").equals("0")) {
                holder.rbFalse.setBackground(context.getResources().getDrawable(R.drawable.green_card));
            }
        } else {
            if (tfList.get(position).get("ans").equals("1")) {
                holder.rbTrue.setBackground(ContextCompat.getDrawable(context, R.drawable.green_card));
            }
            if (tfList.get(position).get("ans").equals("0")) {
                holder.rbFalse.setBackground(ContextCompat.getDrawable(context, R.drawable.green_card));
            }
            //---------------------------------------------------------------------------------------------------------------
            if (tfList.get(position).get("ans").equals("0") && tfList.get(position).get("MarkedANS").equals("1")) {
                holder.rbTrue.setBackground(ContextCompat.getDrawable(context, R.drawable.red_card));
            }
            if (tfList.get(position).get("ans").equals("1") && tfList.get(position).get("MarkedANS").equals("0")) {
                holder.rbFalse.setBackground(ContextCompat.getDrawable(context, R.drawable.red_card));
            }
            //------------------------------------------------------------------------------------------------------------
            if (tfList.get(position).get("ans").equals("1") && tfList.get(position).get("MarkedANS").equals("1")) {
                holder.rbTrue.setBackground(ContextCompat.getDrawable(context, R.drawable.green_card));
            }
            if (tfList.get(position).get("ans").equals("0") && tfList.get(position).get("MarkedANS").equals("0")) {
                holder.rbFalse.setBackground(ContextCompat.getDrawable(context, R.drawable.green_card));
            }
        }

        holder.txtCorrectAnsA.setVisibility(tfList.get(position).get("ans").equals("1") ? View.VISIBLE : View.GONE);
        holder.txtCorrectAnsB.setVisibility(tfList.get(position).get("ans").equals("0") ? View.VISIBLE : View.GONE);

        holder.txtYourAnsA.setVisibility(tfList.get(position).get("MarkedANS").equals("1") ? View.VISIBLE : View.GONE);
        holder.txtYourAnsB.setVisibility(tfList.get(position).get("MarkedANS").equals("0") ? View.VISIBLE : View.GONE);

        holder.txtYourAnsA.setTextColor(tfList.get(position).get("ans").equals("1") && tfList.get(position).get("ans").equals("1") ? context.getResources().getColor(R.color.green) : context.getResources().getColor(R.color.red));
        holder.txtYourAnsB.setTextColor(tfList.get(position).get("ans").equals("0") && tfList.get(position).get("ans").equals("0") ? context.getResources().getColor(R.color.green) : context.getResources().getColor(R.color.red));

    }

    @Override
    public int getItemCount() {
        return tfList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMarksTF, txtTFQno, txtTFQuestion, txtCorrectAnsA, txtCorrectAnsB, txtYourAnsA, txtYourAnsB;
        RadioButton rbTrue, rbFalse;

        public ViewHolder(View itemView) {
            super(itemView);
            txtCorrectAnsA = itemView.findViewById(R.id.txtCorrectAnsA);
            txtCorrectAnsB = itemView.findViewById(R.id.txtCorrectAnsB);
            txtYourAnsA = itemView.findViewById(R.id.txtYourAnsA);
            txtYourAnsB = itemView.findViewById(R.id.txtYourAnsB);

            txtMarksTF = itemView.findViewById(R.id.txtMarksTF);
            txtTFQno = itemView.findViewById(R.id.txtTFQno);
            txtTFQuestion = itemView.findViewById(R.id.txtTFQuestion);
            rbTrue = itemView.findViewById(R.id.rbTrue);
            rbFalse = itemView.findViewById(R.id.rbFalse);
        }
    }
}