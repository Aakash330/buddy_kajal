package com.studybuddy.pc.brainmate.teacher;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.mains.Apis;
import com.studybuddy.pc.brainmate.student.painting.coloring.coloring.ColoringActivity;

import java.util.ArrayList;
import java.util.HashMap;


public class TePaintingChapterAdapter extends BaseAdapter {

    private Bitmap bitmap;
    // Declare Variables
    ArrayList<HashMap<String, String>> books = new ArrayList<>();
    TePaintingChapters mContext;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> animalNamesList = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> map;
    String upcomigID;

    public TePaintingChapterAdapter(TePaintingChapters studentdashBord, ArrayList<HashMap<String, String>> booksname) {
        this.mContext = studentdashBord;
        this.books = booksname;
        inflater = LayoutInflater.from((Context) mContext);
    }

    public class ViewHolder {
        TextView name, ID, Number, heading, chapterNmae;
        LinearLayout linearLayout;
        ImageView Imageview;
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
            view = inflater.inflate(R.layout.paintingchapters, null);
            //Locate the TextViews in listview_item.xml
            //holder.imgg = (ImageView) view.findViewById(R.id.imggch);
            //holder.heading = (TextView) view.findViewById(R.id.headingCh);
            holder.Imageview = (ImageView) view.findViewById(R.id.ImageView);
            holder.chapterNmae = (TextView) view.findViewById(R.id.chaptername);
            holder.linearLayout = (LinearLayout) view.findViewById(R.id.listclick);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        map = new HashMap<>(position);

        holder.chapterNmae.setText(books.get(position).get("ChapterName"));

        //Glide.with(mContext).load("http://www.techive.in/studybuddy/" + books.get(position).get("Iamge"))
        Glide.with(mContext).load(Apis.base_url_img + books.get(position).get("Iamge"))
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder)
                .into(holder.Imageview);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ColoringActivity.class);
                intent.putExtra("PageValue", "2");
                intent.putExtra("Images", Apis.base_url_img + books.get(position).get("Iamge"));
                //intent.putExtra("Images", "http://www.techive.in/studybuddy/" + books.get(position).get("Iamge"));
                mContext.startActivity(intent);
            }
        });
        return view;
    }
}