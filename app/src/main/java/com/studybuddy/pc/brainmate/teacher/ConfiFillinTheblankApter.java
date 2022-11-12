package com.studybuddy.pc.brainmate.teacher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.studybuddy.pc.brainmate.R;

import java.util.ArrayList;
import java.util.HashMap;


public class ConfiFillinTheblankApter extends BaseAdapter {

    // Declare Variables
    ArrayList<HashMap<String, String>> books;

    PaperConfirmition mContext;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> animalNamesList = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> map;
    String upcomigID;

    public ConfiFillinTheblankApter(PaperConfirmition studentdashBord, ArrayList<HashMap<String, String>> booksname) {
        this.mContext = studentdashBord;
        this.books = booksname;
        inflater = LayoutInflater.from((Context) mContext);

    }

    public class ViewHolder {
        TextView name, ID, Number, heading;
        // Button details,Medicalhistory;
        ImageView imgg;
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
            view = inflater.inflate(R.layout.confifileintheblanck, null);
            // Locate the TextViews in listview_item.xml

            /* holder.imgg = (ImageView) view.findViewById(R.id.imgg);*/
            holder.heading = (TextView) view.findViewById(R.id.heading);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        map = new HashMap<>(position);

        holder.heading.setText(books.get(position).get("Confirmation"));

        return view;
    }


}

