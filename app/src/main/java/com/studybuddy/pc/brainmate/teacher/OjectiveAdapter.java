package com.studybuddy.pc.brainmate.teacher;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.studybuddy.pc.brainmate.R;

import java.util.ArrayList;
import java.util.HashMap;

// TODO : Not in use Remove safely
public class OjectiveAdapter extends BaseAdapter {
    String code;
    // Declare Variables
    ArrayList<HashMap<String, String>> books;

    QuetionPaper mContext;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> animalNamesList = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> map;
    String upcomigID;

/*

        public STUDENTDashbroadAdapter(StudentdashBord context, ArrayList<HashMap<String, String>> scoreDetailsList, String upcomigID) {
            mContext = context;
            this.upcomigID=upcomigID;
            this.scoreDetailsList = scoreDetailsList;
            inflater = LayoutInflater.from((Context) mContext);


            Log.d("Sssssssslist", String.valueOf("Adapter"+scoreDetailsList));

        }
*/

    public OjectiveAdapter(QuetionPaper studentdashBord, ArrayList<HashMap<String, String>> booksname) {
        this.mContext = studentdashBord;
        this.books = booksname;
        inflater = LayoutInflater.from((Context) mContext);

    }

    public class ViewHolder {
        TextView name, ID, Number, heading, heading2;
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
        code = books.get(position).get("code");
        Log.d("BooksCode", code);
        if (view == null) {
            holder = new ViewHolder();
            if (code.equals("1")) {
                view = inflater.inflate(R.layout.objetivequelist, null);
            } else if (code.equals("2")) {

                view = inflater.inflate(R.layout.objectivetruefalse, null);

            }
            // Locate the TextViews in listview_item.xml

            /* holder.imgg = (ImageView) view.findViewById(R.id.imgg);*/
            holder.heading = (TextView) view.findViewById(R.id.heading);
            holder.heading2 = (TextView) view.findViewById(R.id.heading2);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        map = new HashMap<>(position);

        holder.heading.setText(books.get(position).get("Quetion"));
//            holder.heading2.setText(books.get(position).get("Quetion"));
          /*  holder.imgg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,StudebtElimentery.class);
                    mContext.startActivity(intent);

                }
            });*/
        return view;
    }


}

