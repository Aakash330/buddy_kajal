package com.studybuddy.pc.brainmate.adapters;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.studybuddy.pc.brainmate.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DisplayMcqResultAdapter extends RecyclerView.Adapter<DisplayMcqResultAdapter.ViewHolder> {

    private ArrayList<HashMap<String, String>> mcqList = new ArrayList<>();
    //private ArrayList<McqReportList> mcqList = new ArrayList<>();//mcqReportLists
    private Context context;

    public DisplayMcqResultAdapter(Context context, ArrayList<HashMap<String, String>> mcqList) {
        this.mcqList = mcqList;
        this.context = context;
    }

    @Override
    public DisplayMcqResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.display_mcq_result, parent, false));
    }

    @Override
    public void onBindViewHolder(DisplayMcqResultAdapter.ViewHolder holder, int position) {
        Log.d("sizeodmcq", String.valueOf(mcqList.size()));

        holder.Option_textA.setText(mcqList.get(position).get("option_a"));
        holder.Option_textB.setText(mcqList.get(position).get("option_b"));
        holder.Option_textC.setText(mcqList.get(position).get("option_c"));
        holder.Option_textD.setText(mcqList.get(position).get("option_d"));
        holder.QuestionnumberMCQ.setText(mcqList.get(position).get("count"));
        holder.MCQQuestion.setText(mcqList.get(position).get("ques"));
        holder.marksMCQ.setText("Marks : " + mcqList.get(position).get("each_mcq_mark"));

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {

            if (mcqList.get(position).get("Answer").equals("a") && mcqList.get(position).get("MarkedANS").equals("a")) {
                holder.llmcqA.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.green_card));
            } else if (mcqList.get(position).get("Answer").equals("a") && !mcqList.get(position).get("MarkedANS").equals("a")) {
                holder.llmcqA.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.red_card));
            } else if (mcqList.get(position).get("Answer").equals("b") && mcqList.get(position).get("MarkedANS").equals("b")) {
                holder.llmcqB.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.green_card));
            } else if (mcqList.get(position).get("Answer").equals("b") && !mcqList.get(position).get("MarkedANS").equals("b")) {
                holder.llmcqB.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.red_card));
            } else if (mcqList.get(position).get("Answer").equals("c") && mcqList.get(position).get("MarkedANS").equals("c")) {
                holder.llmcqC.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.green_card));
            } else if (mcqList.get(position).get("Answer").equals("c") && !mcqList.get(position).get("MarkedANS").equals("c")) {
                holder.llmcqC.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.red_card));
            } else if (mcqList.get(position).get("Answer").equals("d") && mcqList.get(position).get("MarkedANS").equals("d")) {
                holder.llmcqD.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.green_card));
            } else if (mcqList.get(position).get("Answer").equals("d") && !mcqList.get(position).get("MarkedANS").equals("d")) {
                holder.llmcqD.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.red_card));
            }



            /*holder.llmcqA.setBackgroundDrawable(mcqList.get(position).get("Answer").equals("a") ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.listcard));
            holder.llmcqB.setBackgroundDrawable(mcqList.get(position).get("Answer").equals("b") ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.listcard));
            holder.llmcqC.setBackgroundDrawable(mcqList.get(position).get("Answer").equals("c") ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.listcard));
            holder.llmcqD.setBackgroundDrawable(mcqList.get(position).get("Answer").equals("d") ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.listcard));*/

        } else if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (mcqList.get(position).get("Answer").equals("a") && mcqList.get(position).get("MarkedANS").equals("a")) {
                holder.llmcqA.setBackground(context.getResources().getDrawable(R.drawable.green_card));
                //holder.llmcqA.setBackground(mcqList.get(position).get("Answer").equals("a") ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.listcard));
            } else if (mcqList.get(position).get("Answer").equals("a") && !mcqList.get(position).get("MarkedANS").equals("a")) {
                holder.llmcqA.setBackground(context.getResources().getDrawable(R.drawable.red_card));
                //holder.llmcqA.setBackground(mcqList.get(position).get("Answer").equals("a") ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.listcard));
            } else if (mcqList.get(position).get("Answer").equals("b") && mcqList.get(position).get("MarkedANS").equals("b")) {
                holder.llmcqB.setBackground(context.getResources().getDrawable(R.drawable.green_card));
                //holder.llmcqB.setBackground(mcqList.get(position).get("Answer").equals("b") ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.listcard));
            } else if (mcqList.get(position).get("Answer").equals("b") && !mcqList.get(position).get("MarkedANS").equals("b")) {
                holder.llmcqB.setBackground(context.getResources().getDrawable(R.drawable.red_card));
                //holder.llmcqB.setBackground(mcqList.get(position).get("Answer").equals("b") ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.listcard));
            } else if (mcqList.get(position).get("Answer").equals("c") && mcqList.get(position).get("MarkedANS").equals("c")) {
                holder.llmcqC.setBackground(context.getResources().getDrawable(R.drawable.green_card));
                //holder.llmcqC.setBackground(mcqList.get(position).get("Answer").equals("c") ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.listcard));
            } else if (mcqList.get(position).get("Answer").equals("c") && !mcqList.get(position).get("MarkedANS").equals("c")) {
                holder.llmcqC.setBackground(context.getResources().getDrawable(R.drawable.red_card));
                //holder.llmcqC.setBackground(mcqList.get(position).get("Answer").equals("c") ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.listcard));
            } else if (mcqList.get(position).get("Answer").equals("d") && mcqList.get(position).get("MarkedANS").equals("d")) {
                holder.llmcqD.setBackground(context.getResources().getDrawable(R.drawable.green_card));
                //holder.llmcqD.setBackground(mcqList.get(position).get("Answer").equals("d") ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.listcard));
            } else if (mcqList.get(position).get("Answer").equals("d") && !mcqList.get(position).get("MarkedANS").equals("d")) {
                holder.llmcqD.setBackground(context.getResources().getDrawable(R.drawable.red_card));
                //holder.llmcqD.setBackground(mcqList.get(position).get("Answer").equals("d") ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.listcard));
            }



            /*holder.llmcqA.setBackground(mcqList.get(position).get("Answer").equals("a") ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.listcard));
            holder.llmcqB.setBackground(mcqList.get(position).get("Answer").equals("b") ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.listcard));
            holder.llmcqC.setBackground(mcqList.get(position).get("Answer").equals("c") ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.listcard));
            holder.llmcqD.setBackground(mcqList.get(position).get("Answer").equals("d") ? context.getResources().getDrawable(R.drawable.green_card) : context.getResources().getDrawable(R.drawable.listcard));*/
        } else {
            if (mcqList.get(position).get("Answer").equals("a")) {
                holder.llmcqA.setBackground(ContextCompat.getDrawable(context, R.drawable.green_card));
            }
            if (mcqList.get(position).get("Answer").equals("b")) {
                holder.llmcqB.setBackground(ContextCompat.getDrawable(context, R.drawable.green_card));
            }
            if (mcqList.get(position).get("Answer").equals("c")) {
                holder.llmcqC.setBackground(ContextCompat.getDrawable(context, R.drawable.green_card));
            }
            if (mcqList.get(position).get("Answer").equals("d")) {
                holder.llmcqD.setBackground(ContextCompat.getDrawable(context, R.drawable.green_card));
            }
            //---------------------------------------------------------------------------------------------------------------

            if (mcqList.get(position).get("MarkedANS").equals("a")) {
                holder.llmcqA.setBackground(ContextCompat.getDrawable(context, R.drawable.red_card));
            }
            if (mcqList.get(position).get("MarkedANS").equals("b")) {
                holder.llmcqB.setBackground(ContextCompat.getDrawable(context, R.drawable.red_card));
            }
            if (mcqList.get(position).get("MarkedANS").equals("c")) {
                holder.llmcqC.setBackground(ContextCompat.getDrawable(context, R.drawable.red_card));
            }
            if (mcqList.get(position).get("MarkedANS").equals("d")) {
                holder.llmcqD.setBackground(ContextCompat.getDrawable(context, R.drawable.red_card));
            }
            //------------------------------------------------------------------------------------------------------------
            if (mcqList.get(position).get("Answer").equals("a") && mcqList.get(position).get("MarkedANS").equals("a")) {
                holder.llmcqA.setBackground(ContextCompat.getDrawable(context, R.drawable.green_card));
            }
            if (mcqList.get(position).get("Answer").equals("b") && mcqList.get(position).get("MarkedANS").equals("b")) {
                holder.llmcqB.setBackground(ContextCompat.getDrawable(context, R.drawable.green_card));
            }
            if (mcqList.get(position).get("Answer").equals("c") && mcqList.get(position).get("MarkedANS").equals("c")) {
                holder.llmcqC.setBackground(ContextCompat.getDrawable(context, R.drawable.green_card));
            }
            if (mcqList.get(position).get("Answer").equals("d") && mcqList.get(position).get("MarkedANS").equals("d")) {
                holder.llmcqD.setBackground(ContextCompat.getDrawable(context, R.drawable.green_card));
            }



            /*holder.llmcqA.setBackground(ContextCompat.getDrawable(context, mcqList.get(position).get("Answer").equals("a") ? R.drawable.green_card : R.drawable.listcard));
            holder.llmcqB.setBackground(ContextCompat.getDrawable(context, mcqList.get(position).get("Answer").equals("b") ? R.drawable.green_card : R.drawable.listcard));
            holder.llmcqC.setBackground(ContextCompat.getDrawable(context, mcqList.get(position).get("Answer").equals("c") ? R.drawable.green_card : R.drawable.listcard));
            holder.llmcqD.setBackground(ContextCompat.getDrawable(context, mcqList.get(position).get("Answer").equals("d") ? R.drawable.green_card : R.drawable.listcard));
            holder.llmcqA.setBackground(ContextCompat.getDrawable(context, mcqList.get(position).get("MarkedANS").equals("a") ? R.drawable.green_card : R.drawable.listcard));
            holder.llmcqB.setBackground(ContextCompat.getDrawable(context, mcqList.get(position).get("MarkedANS").equals("b") ? R.drawable.green_card : R.drawable.listcard));
            holder.llmcqC.setBackground(ContextCompat.getDrawable(context, mcqList.get(position).get("MarkedANS").equals("c") ? R.drawable.green_card : R.drawable.listcard));
            holder.llmcqD.setBackground(ContextCompat.getDrawable(context, mcqList.get(position).get("MarkedANS").equals("d") ? R.drawable.green_card : R.drawable.listcard));*/
        }

        holder.txtCorrectAnsA.setVisibility(mcqList.get(position).get("Answer").equals("a") ? View.VISIBLE : View.GONE);
        holder.txtCorrectAnsB.setVisibility(mcqList.get(position).get("Answer").equals("b") ? View.VISIBLE : View.GONE);
        holder.txtCorrectAnsC.setVisibility(mcqList.get(position).get("Answer").equals("c") ? View.VISIBLE : View.GONE);
        holder.txtCorrectAnsD.setVisibility(mcqList.get(position).get("Answer").equals("d") ? View.VISIBLE : View.GONE);

        holder.txtYourAnsA.setVisibility(mcqList.get(position).get("MarkedANS").equals("a") ? View.VISIBLE : View.GONE);
        holder.txtYourAnsB.setVisibility(mcqList.get(position).get("MarkedANS").equals("b") ? View.VISIBLE : View.GONE);
        holder.txtYourAnsC.setVisibility(mcqList.get(position).get("MarkedANS").equals("c") ? View.VISIBLE : View.GONE);
        holder.txtYourAnsD.setVisibility(mcqList.get(position).get("MarkedANS").equals("d") ? View.VISIBLE : View.GONE);

        holder.txtYourAnsA.setTextColor(mcqList.get(position).get("Answer").equals("a") && mcqList.get(position).get("MarkedANS").equals("a") ? context.getResources().getColor(R.color.green) : context.getResources().getColor(R.color.red));
        holder.txtYourAnsB.setTextColor(mcqList.get(position).get("Answer").equals("b") && mcqList.get(position).get("MarkedANS").equals("b") ? context.getResources().getColor(R.color.green) : context.getResources().getColor(R.color.red));
        holder.txtYourAnsC.setTextColor(mcqList.get(position).get("Answer").equals("c") && mcqList.get(position).get("MarkedANS").equals("c") ? context.getResources().getColor(R.color.green) : context.getResources().getColor(R.color.red));
        holder.txtYourAnsD.setTextColor(mcqList.get(position).get("Answer").equals("d") && mcqList.get(position).get("MarkedANS").equals("d") ? context.getResources().getColor(R.color.green) : context.getResources().getColor(R.color.red));

        final String OptionA = mcqList.get(position).get("option_a");
        Log.d("OptionA", OptionA);
        String OPImage1 = "";
        List<String> elephantList1 = Arrays.asList(OptionA.split("-"));
        Log.d("elephantList1", String.valueOf(elephantList1.size()));
        if (elephantList1.size() == 1) {
            String url = OptionA.replace("-", "");
            Log.d("13654063", "" + url);
            String str1 = url != null && url.length() > 3 ? url.substring(url.length() - 3, url.length()) : "";
            if (str1.equals("jpg") || str1.equals("png")) {
                holder.Option_textA.setVisibility(View.GONE);
                holder.Option_Aiamge.setVisibility(View.VISIBLE);
                OPImage1 = "http://techive.in/studybuddy/question_img/" + url;
                Log.d("136540", OPImage1);
                Glide.with(context).load(OPImage1)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.Option_Aiamge);
            } else {
                holder.Option_textA.setVisibility(View.VISIBLE);
                holder.Option_Aiamge.setVisibility(View.GONE);
                holder.Option_textA.setText(OptionA);
            }
        } else {

            elephantList1 = Arrays.asList(OptionA.split("-"));
            String str1 = OptionA != null && OptionA.length() > 3 ? OptionA.substring(OptionA.length() - 3, OptionA.length()) : "";
            Log.d("elephantList2", String.valueOf(elephantList1.size()));
            if (str1.equals("jpg") || str1.equals("png")) {
                holder.Option_textA.setVisibility(View.GONE);
                holder.Option_Aiamge.setVisibility(View.VISIBLE);
                OPImage1 = "http://techive.in/studybuddy/question_img/" + elephantList1.get(1);
                Glide.with(context).load(OPImage1)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.Option_Aiamge);
            } else {
                holder.Option_textA.setVisibility(View.VISIBLE);
                holder.Option_Aiamge.setVisibility(View.GONE);
                holder.Option_textA.setText(OptionA);
            }
        }

    }

    @Override
    public int getItemCount() {
        return mcqList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCorrectAnsA, txtCorrectAnsB, txtCorrectAnsC, txtCorrectAnsD, txtYourAnsA, txtYourAnsB, txtYourAnsC, txtYourAnsD;
        ImageView Option_Aiamge, Option_Biamge, Option_Ciamge, Option_Diamge;
        TextView Option_textA, Option_textB, Option_textC, Option_textD;
        TextView QuestionnumberMCQ, marksMCQ, MCQQuestion;
        LinearLayout llmcqA, llmcqB, llmcqC, llmcqD;

        public ViewHolder(View itemView) {
            super(itemView);

            txtCorrectAnsA = itemView.findViewById(R.id.txtCorrectAnsA);
            txtCorrectAnsB = itemView.findViewById(R.id.txtCorrectAnsB);
            txtCorrectAnsC = itemView.findViewById(R.id.txtCorrectAnsC);
            txtCorrectAnsD = itemView.findViewById(R.id.txtCorrectAnsD);
            txtYourAnsA = itemView.findViewById(R.id.txtYourAnsA);
            txtYourAnsB = itemView.findViewById(R.id.txtYourAnsB);
            txtYourAnsC = itemView.findViewById(R.id.txtYourAnsC);
            txtYourAnsD = itemView.findViewById(R.id.txtYourAnsD);

            QuestionnumberMCQ = itemView.findViewById(R.id.QuestionnumberMCQ);
            marksMCQ = itemView.findViewById(R.id.marksMCQ);
            MCQQuestion = itemView.findViewById(R.id.MCQQuestion);

            llmcqA = itemView.findViewById(R.id.llmcqA);
            llmcqB = itemView.findViewById(R.id.llmcqB);
            llmcqC = itemView.findViewById(R.id.llmcqC);
            llmcqD = itemView.findViewById(R.id.llmcqD);

            Option_Aiamge = itemView.findViewById(R.id.Option_Aiamge);
            Option_Biamge = itemView.findViewById(R.id.Option_Biamge);
            Option_Ciamge = itemView.findViewById(R.id.Option_Ciamge);
            Option_Diamge = itemView.findViewById(R.id.Option_Diamge);

            Option_textA = itemView.findViewById(R.id.Option_textA);
            Option_textB = itemView.findViewById(R.id.Option_textB);
            Option_textC = itemView.findViewById(R.id.Option_textC);
            Option_textD = itemView.findViewById(R.id.Option_textD);
        }
    }
}
