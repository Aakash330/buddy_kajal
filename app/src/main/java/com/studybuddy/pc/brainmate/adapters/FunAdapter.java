package com.studybuddy.pc.brainmate.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.mains.Apis;
import com.studybuddy.pc.brainmate.model.fun_data_list.FunForList;
import com.studybuddy.pc.brainmate.student.CommonMethods;
import com.studybuddy.pc.brainmate.student.FunWebViewActivity;
import com.studybuddy.pc.brainmate.student.freehandpainting.FreehandActivityMain;
import com.studybuddy.pc.brainmate.student.painting.coloring.coloring.ColoringActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class FunAdapter extends RecyclerView.Adapter<FunAdapter.ViewHolder> {

    //private ArrayList<HashMap<String, String>> funList = new ArrayList<>();
    private ArrayList<FunForList> funForLists = new ArrayList<>();
    private ArrayList<String> urlLists = new ArrayList<>();
    private Context context;
    private int drawing = 0;
    private int coloring = 0;
    private int web_view = 0;
    private int web_view007 = 0;
    private int link_count = 0;
    boolean isFirst = true;

    public FunAdapter(ArrayList<FunForList> funForLists, Context context) {
        this.funForLists = funForLists;
        this.context = context;
    }

    @NonNull
    @Override
    public FunAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.fun_title, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final FunAdapter.ViewHolder holder, int position) {
        holder.txtTitleName.setText(funForLists.get(position).getTitle());
        String str = funForLists.get(position).getType();
        if (str.equals("0")) {
            holder.txtTitleFunActivity.setVisibility(position != 0 ? View.GONE : View.VISIBLE);
            holder.txtTitleFunActivity.setText("WebView");
            web_view++;
        } else if (str.equals("1")) {
            holder.txtTitleFunActivity.setVisibility(drawing != 0 ? View.GONE : View.VISIBLE);
            holder.txtTitleFunActivity.setText("Drawing");
            drawing++;
        } else if (str.equals("2")) {
            holder.txtTitleFunActivity.setVisibility(coloring != 0 ? View.GONE : View.VISIBLE);
            holder.txtTitleFunActivity.setText("Coloring");
            coloring++;
        }
        if (isFirst) {
            for (int i = 0; i < funForLists.size(); i++) {
                if (funForLists.get(position).getType().equals("0")) {
                    web_view007++;
                }
            }
            for (int i = 0; i < web_view007; i++) {
                urlLists.add(funForLists.get(holder.getAdapterPosition()).getUrl());
            }
            Log.d("web_view007", String.valueOf(web_view007));
            isFirst = false;
        }
        holder.img_overlay.setVisibility(funForLists.get(holder.getAdapterPosition()).getIsFree() != null && funForLists.get(holder.getAdapterPosition()).getIsFree().equals("1") ? View.GONE : View.VISIBLE);
        holder.llfunLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (funForLists.get(holder.getAdapterPosition()).getType()) {
                    case "0": {
                        if (funForLists.get(holder.getAdapterPosition()).getIsFree().equals("1")) {
                            for (int i = 0; i < web_view007; i++) {
                                if (urlLists.get(i).equals(funForLists.get(holder.getAdapterPosition()).getUrl())) {
                                    link_count = i;
                                }
                            }
                            Intent intent = new Intent(context, FunWebViewActivity.class);
                            intent.putExtra("url", funForLists.get(holder.getAdapterPosition()).getUrl());
                            intent.putExtra("link_count", link_count);
                            intent.putStringArrayListExtra("url_list", urlLists);
                            context.startActivity(intent);
                        } else {
                            CommonMethods.showToast(context, "You Need to buy this book.");
                        }
                        /*if (!funForLists.get(holder.getAdapterPosition()).getTitle().equals("Write alphabet")) {
                            Intent intent = new Intent(context, FunWebViewActivity.class);
                            intent.putExtra("url", funForLists.get(holder.getAdapterPosition()).getUrl());
                            context.startActivity(intent);
                        }else {
                            Uri uri = Uri.parse("http://www.techive.in/studybuddy/fun-activity/url/Write_the_alphabet_that_comes_between_lkg&nursery/"); // missing 'http://' will cause crashed
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            context.startActivity(intent);
                        }*/
                        break;
                    }
                    case "1": {
                        if (funForLists.get(holder.getAdapterPosition()).getIsFree().equals("1")) {
                            Intent intent = new Intent(context, FreehandActivityMain.class);
                            intent.putExtra("url", funForLists.get(holder.getAdapterPosition()).getUrl());
                            context.startActivity(intent);
                        } else {
                            CommonMethods.showToast(context, "You Need to buy this book.");
                        }
                        break;
                    }
                    case "2": {
                        if (funForLists.get(holder.getAdapterPosition()).getIsFree().equals("1")) {
                            Intent intent = new Intent(context, ColoringActivity.class);
                            intent.putExtra("url", funForLists.get(holder.getAdapterPosition()).getUrl());
                            Log.d("img_url", "" + funForLists.get(holder.getAdapterPosition()).getUrl());
                            context.startActivity(intent);
                        } else {
                            CommonMethods.showToast(context, "You Need to buy this book.");
                        }
                        break;
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return funForLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitleName, txtTitleFunActivity;
        LinearLayout llfunLayout;
        ImageView img_overlay;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitleName = itemView.findViewById(R.id.txtTitleName);
            txtTitleFunActivity = itemView.findViewById(R.id.txtTitleFunActivity);
            llfunLayout = itemView.findViewById(R.id.llfunLayout);
            img_overlay = itemView.findViewById(R.id.img_overlay);
        }
    }
}
