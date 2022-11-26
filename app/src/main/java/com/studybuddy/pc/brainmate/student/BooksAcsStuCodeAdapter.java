package com.studybuddy.pc.brainmate.student;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.mains.Apis;

import java.util.ArrayList;
import java.util.HashMap;

class BooksAcsStuCodeAdapter extends BaseAdapter {

    StudentdashBord mContext;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> Books_by_accessCode = new ArrayList<HashMap<String, String>>();
    HashMap<String,String>map;
    String upcomigID,Image_url;


    public BooksAcsStuCodeAdapter(StudentdashBord context, ArrayList<HashMap<String, String>> BooksArray) {
        mContext = context;
        this.upcomigID=upcomigID;
        this.Books_by_accessCode = BooksArray;
        inflater = LayoutInflater.from((Context) mContext);
        Log.d("Sssssssslist", String.valueOf("Adapter"+Books_by_accessCode));

    }

    public class ViewHolder {
        TextView BookTitle,ForClass,BookSubject,expire_tv;
        // Button details,Medicalhistory;
        LinearLayout linearLayout;
        ImageView Book_Image;
    }

    @Override
    public int getCount() {
        return Books_by_accessCode.size();
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
            view = inflater.inflate(R.layout.buymedicenilist, null);
            // Locate the TextViews in listview_item.xml
            holder.BookTitle = (TextView) view.findViewById(R.id.BookTitle);
            holder.ForClass = (TextView) view.findViewById(R.id.ForClass);
            holder.BookSubject = (TextView) view.findViewById(R.id.BookSubject);
            holder.Book_Image=(ImageView)view.findViewById(R.id.Book_Image) ;
            holder.expire_tv=(TextView) view.findViewById(R.id.inactive_tv) ;
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        map=new HashMap<>(position);
        holder.BookTitle.setText(Books_by_accessCode.get(position).get("Title"));
        holder.ForClass.setText(Books_by_accessCode.get(position).get("Class"));
        holder.BookSubject.setText(Books_by_accessCode.get(position).get("Subject"));
       String Image_Name=Books_by_accessCode.get(position).get("book_img");
       String expireStatus=Books_by_accessCode.get(position).get("expire_status");
        //Image_url ="http://techive.in/studybuddy/"+Image_Name;
        Image_url =Apis.base_url_img +Image_Name;
        Log.d("Image_url",""+Image_url);

        if(Books_by_accessCode.get(position).get("expire_status").equals("1")){

            holder.expire_tv.setVisibility(View.VISIBLE);
            Toast.makeText(mContext, "expire", Toast.LENGTH_SHORT).show();
        }

        Glide.with(mContext).load(Image_url)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder)
                .into(holder.Book_Image);

        return view;
    }

}
