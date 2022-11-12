package com.studybuddy.pc.brainmate.teacher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studybuddy.pc.brainmate.R;

import java.util.ArrayList;
import java.util.HashMap;



    public class ChapterAdapter extends BaseAdapter {

        // Declare Variables
        String [] books={};

        TeChapter mContext;
        LayoutInflater inflater;
        ArrayList<HashMap<String, String>> animalNamesList = new ArrayList<HashMap<String, String>>();
        HashMap<String,String>map;
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

        public ChapterAdapter(TeChapter studentdashBord, String[] booksname) {
            this.mContext=studentdashBord;
            this.books=booksname;
            inflater = LayoutInflater.from((Context) mContext);

        }

        public class ViewHolder {
            TextView name,ID,Number,heading;
            // Button details,Medicalhistory;
            LinearLayout linearLayout;
            ImageView imgg;
        }

        @Override
        public int getCount() {
            return books.length;
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
                view = inflater.inflate(R.layout.chapterslisrt, null);
                // Locate the TextViews in listview_item.xml

            //     holder.imgg = (ImageView) view.findViewById(R.id.imggch);
                holder.heading = (TextView) view.findViewById(R.id.headingCh);
                holder.linearLayout=(LinearLayout)view.findViewById(R.id.listclick);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            // Set the results into TextViews
            map=new HashMap<>(position);

            holder.heading.setText(books[position]);
      /*      holder.imgg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                  *//*  Intent intent=new Intent(mContext,LearningElementary.class);
                    mContext.startActivity(intent);*//*

                }
            });*/
            return view;
        }


    }

