package com.studybuddy.pc.brainmate.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studybuddy.pc.brainmate.R;

import java.util.ArrayList;
import java.util.HashMap;

public class DisplayMColResultAdapter extends RecyclerView.Adapter<DisplayMColResultAdapter.ViewHolder> {

    private ArrayList<HashMap<String, String>> mcolList = new ArrayList<>();
    private Context context;

    public DisplayMColResultAdapter(Context context, ArrayList<HashMap<String, String>> mcolList) {
        this.mcolList = mcolList;
        this.context = context;
    }

    @NonNull
    @Override
    public DisplayMColResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.displaymcolresult, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayMColResultAdapter.ViewHolder holder, int position) {
        holder.txtQnoMCol.setText(mcolList.get(position).get("count"));
        holder.txtTotalMarksMCol.setText("Marks : " + mcolList.get(position).get("each_mcol_mark"));
        holder.txtCol1r1.setText("1.\t" + mcolList.get(position).get("column1r1"));
        holder.txtCol1r2.setText("2.\t" + mcolList.get(position).get("column1r2"));
        holder.txtCol1r3.setText("3.\t" + mcolList.get(position).get("column1r3"));
        holder.txtCol1r4.setText("4.\t" + mcolList.get(position).get("column1r4"));
        holder.txtCol1r5.setText("5.\t" + mcolList.get(position).get("column1r5"));
        holder.txtCol1r6.setText("6.\t" + mcolList.get(position).get("column1r6"));
        holder.txtCol1r7.setText("7.\t" + mcolList.get(position).get("column1r7"));
        holder.txtCol1r8.setText("8.\t" + mcolList.get(position).get("column1r8"));

        holder.txtCol2r1.setText(mcolList.get(position).get("column2r1"));
        holder.txtCol2r2.setText(mcolList.get(position).get("column2r2"));
        holder.txtCol2r3.setText(mcolList.get(position).get("column2r3"));
        holder.txtCol2r4.setText(mcolList.get(position).get("column2r4"));
        holder.txtCol2r5.setText(mcolList.get(position).get("column2r5"));
        holder.txtCol2r6.setText(mcolList.get(position).get("column2r6"));
        holder.txtCol2r7.setText(mcolList.get(position).get("column2r7"));
        holder.txtCol2r8.setText(mcolList.get(position).get("column2r8"));

        holder.llMCol1.setVisibility(!mcolList.get(position).get("column1r1").equals("") ? View.VISIBLE : View.GONE);
        holder.llMCol2.setVisibility(!mcolList.get(position).get("column1r2").equals("") ? View.VISIBLE : View.GONE);
        holder.llMCol3.setVisibility(!mcolList.get(position).get("column1r3").equals("") ? View.VISIBLE : View.GONE);
        holder.llMCol4.setVisibility(!mcolList.get(position).get("column1r4").equals("") ? View.VISIBLE : View.GONE);
        holder.llMCol5.setVisibility(!mcolList.get(position).get("column1r5").equals("") ? View.VISIBLE : View.GONE);
        holder.llMCol6.setVisibility(!mcolList.get(position).get("column1r6").equals("") ? View.VISIBLE : View.GONE);
        holder.llMCol7.setVisibility(!mcolList.get(position).get("column1r7").equals("") ? View.VISIBLE : View.GONE);
        holder.llMCol8.setVisibility(!mcolList.get(position).get("column1r8").equals("") ? View.VISIBLE : View.GONE);

        holder.eTr1.setText(mcolList.get(position).get("answer1"));
        holder.eTr2.setText(mcolList.get(position).get("answer2"));
        holder.eTr3.setText(mcolList.get(position).get("answer3"));
        holder.eTr4.setText(mcolList.get(position).get("answer4"));
        holder.eTr5.setText(mcolList.get(position).get("answer5"));
        holder.eTr6.setText(mcolList.get(position).get("answer6"));
        holder.eTr7.setText(mcolList.get(position).get("answer7"));
        holder.eTr8.setText(mcolList.get(position).get("answer8"));

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {

            holder.eTr1.setBackgroundDrawable(mcolList.get(position).get("answer1").equals(mcolList.get(position).get("user_answer1")) ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.red_card));
            holder.eTr2.setBackgroundDrawable(mcolList.get(position).get("answer2").equals(mcolList.get(position).get("user_answer2")) ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.red_card));
            holder.eTr3.setBackgroundDrawable(mcolList.get(position).get("answer3").equals(mcolList.get(position).get("user_answer3")) ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.red_card));
            holder.eTr4.setBackgroundDrawable(mcolList.get(position).get("answer4").equals(mcolList.get(position).get("user_answer4")) ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.red_card));
            holder.eTr5.setBackgroundDrawable(mcolList.get(position).get("answer5").equals(mcolList.get(position).get("user_answer5")) ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.red_card));
            holder.eTr6.setBackgroundDrawable(mcolList.get(position).get("answer6").equals(mcolList.get(position).get("user_answer6")) ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.red_card));
            holder.eTr7.setBackgroundDrawable(mcolList.get(position).get("answer7").equals(mcolList.get(position).get("user_answer7")) ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.red_card));
            holder.eTr7.setBackgroundDrawable(mcolList.get(position).get("answer7").equals(mcolList.get(position).get("user_answer7")) ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.red_card));
            holder.eTr8.setBackgroundDrawable(mcolList.get(position).get("answer8").equals(mcolList.get(position).get("user_answer8")) ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.red_card));

        } else if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {

            holder.eTr1.setBackground(mcolList.get(position).get("answer1").equals(mcolList.get(position).get("user_answer1")) ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.red_card));
            holder.eTr2.setBackground(mcolList.get(position).get("answer2").equals(mcolList.get(position).get("user_answer2")) ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.red_card));
            holder.eTr3.setBackground(mcolList.get(position).get("answer3").equals(mcolList.get(position).get("user_answer3")) ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.red_card));
            holder.eTr4.setBackground(mcolList.get(position).get("answer4").equals(mcolList.get(position).get("user_answer4")) ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.red_card));
            holder.eTr5.setBackground(mcolList.get(position).get("answer5").equals(mcolList.get(position).get("user_answer5")) ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.red_card));
            holder.eTr6.setBackground(mcolList.get(position).get("answer6").equals(mcolList.get(position).get("user_answer6")) ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.red_card));
            holder.eTr7.setBackground(mcolList.get(position).get("answer7").equals(mcolList.get(position).get("user_answer7")) ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.red_card));
            holder.eTr7.setBackground(mcolList.get(position).get("answer7").equals(mcolList.get(position).get("user_answer7")) ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.red_card));
            holder.eTr8.setBackground(mcolList.get(position).get("answer8").equals(mcolList.get(position).get("user_answer8")) ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.red_card));


        } else {
            holder.eTr1.setBackground(mcolList.get(position).get("answer1").equals(mcolList.get(position).get("user_answer1")) ? ContextCompat.getDrawable(context, R.drawable.green_card) : ContextCompat.getDrawable(context, R.drawable.red_card));
            holder.eTr2.setBackground(mcolList.get(position).get("answer2").equals(mcolList.get(position).get("user_answer2")) ? ContextCompat.getDrawable(context, R.drawable.green_card) : ContextCompat.getDrawable(context, R.drawable.red_card));
            holder.eTr3.setBackground(mcolList.get(position).get("answer3").equals(mcolList.get(position).get("user_answer3")) ? ContextCompat.getDrawable(context, R.drawable.green_card) : ContextCompat.getDrawable(context, R.drawable.red_card));
            holder.eTr4.setBackground(mcolList.get(position).get("answer4").equals(mcolList.get(position).get("user_answer4")) ? ContextCompat.getDrawable(context, R.drawable.green_card) : ContextCompat.getDrawable(context, R.drawable.red_card));
            holder.eTr5.setBackground(mcolList.get(position).get("answer5").equals(mcolList.get(position).get("user_answer5")) ? ContextCompat.getDrawable(context, R.drawable.green_card) : ContextCompat.getDrawable(context, R.drawable.red_card));
            holder.eTr6.setBackground(mcolList.get(position).get("answer6").equals(mcolList.get(position).get("user_answer6")) ? ContextCompat.getDrawable(context, R.drawable.green_card) : ContextCompat.getDrawable(context, R.drawable.red_card));
            holder.eTr7.setBackground(mcolList.get(position).get("answer7").equals(mcolList.get(position).get("user_answer7")) ? ContextCompat.getDrawable(context, R.drawable.green_card) : ContextCompat.getDrawable(context, R.drawable.red_card));
            holder.eTr7.setBackground(mcolList.get(position).get("answer7").equals(mcolList.get(position).get("user_answer7")) ? ContextCompat.getDrawable(context, R.drawable.green_card) : ContextCompat.getDrawable(context, R.drawable.red_card));
            holder.eTr8.setBackground(mcolList.get(position).get("answer8").equals(mcolList.get(position).get("user_answer8")) ? ContextCompat.getDrawable(context, R.drawable.green_card) : ContextCompat.getDrawable(context, R.drawable.red_card));
        }
    }

    @Override
    public int getItemCount() {
        return mcolList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView eTr1, eTr2, eTr3, eTr4, eTr5, eTr6, eTr7, eTr8;
        String answer1 = "";
        String answer2 = "";
        String answer3 = "";
        String answer4 = "";
        String answer5 = "";
        String answer6 = "";
        String answer7 = "";
        String answer8 = "";//1866
        LinearLayout llMCol1, llMCol2, llMCol3, llMCol4, llMCol5, llMCol6, llMCol7, llMCol8;
        TextView txtQnoMCol, txtCol1r1, txtCol1r2, txtCol1r3, txtCol1r4, txtCol1r5, txtCol1r6, txtCol1r7, txtCol1r8, txtCol2r1, txtCol2r2, txtCol2r3, txtCol2r4, txtCol2r5, txtCol2r6, txtCol2r7, txtCol2r8, txtTotalMarksMCol;
        LinearLayout layoutMColumn;

        public ViewHolder(View itemView) {
            super(itemView);
            //region "itemView.findViewById MCol"
            llMCol1 = itemView.findViewById(R.id.llMCol1);
            llMCol2 = itemView.findViewById(R.id.llMCol2);
            llMCol3 = itemView.findViewById(R.id.llMCol3);
            llMCol4 = itemView.findViewById(R.id.llMCol4);
            llMCol5 = itemView.findViewById(R.id.llMCol5);
            llMCol6 = itemView.findViewById(R.id.llMCol6);
            llMCol7 = itemView.findViewById(R.id.llMCol7);
            llMCol8 = itemView.findViewById(R.id.llMCol8);

            txtQnoMCol = itemView.findViewById(R.id.txtQnoMCol);
            txtTotalMarksMCol = itemView.findViewById(R.id.txtTotalMarksMCol);
            txtCol1r1 = itemView.findViewById(R.id.txtCol1r1);
            txtCol1r2 = itemView.findViewById(R.id.txtCol1r2);
            txtCol1r3 = itemView.findViewById(R.id.txtCol1r3);
            txtCol1r5 = itemView.findViewById(R.id.txtCol1r5);
            txtCol1r6 = itemView.findViewById(R.id.txtCol1r6);
            txtCol1r7 = itemView.findViewById(R.id.txtCol1r7);
            txtCol1r8 = itemView.findViewById(R.id.txtCol1r8);
            txtCol1r4 = itemView.findViewById(R.id.txtCol1r4);

            txtCol2r1 = itemView.findViewById(R.id.txtCol2r1);
            txtCol2r2 = itemView.findViewById(R.id.txtCol2r2);
            txtCol2r3 = itemView.findViewById(R.id.txtCol2r3);
            txtCol2r4 = itemView.findViewById(R.id.txtCol2r4);
            txtCol2r5 = itemView.findViewById(R.id.txtCol2r5);
            txtCol2r6 = itemView.findViewById(R.id.txtCol2r6);
            txtCol2r7 = itemView.findViewById(R.id.txtCol2r7);
            txtCol2r8 = itemView.findViewById(R.id.txtCol2r8);
            layoutMColumn = itemView.findViewById(R.id.layoutMColumn);

            eTr1 = itemView.findViewById(R.id.eTr1);
            eTr2 = itemView.findViewById(R.id.eTr2);
            eTr3 = itemView.findViewById(R.id.eTr3);
            eTr4 = itemView.findViewById(R.id.eTr4);
            eTr5 = itemView.findViewById(R.id.eTr5);
            eTr6 = itemView.findViewById(R.id.eTr6);
            eTr7 = itemView.findViewById(R.id.eTr7);
            eTr8 = itemView.findViewById(R.id.eTr8);
            //endregion
        }
    }
}
