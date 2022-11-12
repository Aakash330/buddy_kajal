package com.studybuddy.pc.brainmate.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.teacher.ManageClickInterface;

import java.util.ArrayList;
import java.util.HashMap;

public class ColorGridImgAdapter extends RecyclerView.Adapter<ColorGridImgAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HashMap<String, String>> grid_imgList = new ArrayList<>();
    private ManageClickInterface manageClickInterface;

    public ColorGridImgAdapter(Context context, ArrayList<HashMap<String, String>> grid_imgList, ManageClickInterface manageClickInterface) {
        this.context = context;
        this.grid_imgList = grid_imgList;
        this.manageClickInterface = manageClickInterface;
    }

    @NonNull
    @Override
    public ColorGridImgAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.paintingchapters, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ColorGridImgAdapter.ViewHolder holder, int position) {
        holder.chapterNmae.setText(grid_imgList.get(position).get("ChapterName"));

        Glide.with(context).load("http://www.techive.in/studybuddy/" + grid_imgList.get(position).get("Image"))
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder)
                .into(holder.Imageview);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageClickInterface.onManageClick(holder.getAdapterPosition());
                //  "Images", "http://www.techive.in/studybuddy/" + books.get(position).get("Iamge"));

            }
        });
    }

    @Override
    public int getItemCount() {
        return grid_imgList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView chapterNmae;
        LinearLayout linearLayout;
        ImageView Imageview;

        public ViewHolder(View itemView) {
            super(itemView);
            Imageview = itemView.findViewById(R.id.ImageView);
            chapterNmae = itemView.findViewById(R.id.chaptername);
            linearLayout = itemView.findViewById(R.id.listclick);
        }
    }
}
