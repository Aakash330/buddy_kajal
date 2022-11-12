package com.studybuddy.pc.brainmate.teacher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.studybuddy.pc.brainmate.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;


class Demo_Adapter extends BaseAdapter {

        private static final int TYPE_ITEM = 0;
        private static final int TYPE_SEPARATOR = 1;

    private ArrayList<HashMap<String,String>> mData = new ArrayList<>();
        private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

        private LayoutInflater mInflater;

        public Demo_Adapter(Context context) {
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        public void addItem(final String item) {
            HashMap<String,String>map=new HashMap<>();
            map.put("item",item);
            mData.add(map);
            notifyDataSetChanged();
        }

        public void addSectionHeaderItem(final String item) {
            HashMap<String,String>map=new HashMap<>();
            map.put("item",item);
            mData.add(map);
            sectionHeader.add(mData.size() - 1);
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public String getItem(int position) {
            return String.valueOf(mData.get(position));
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            int rowType = getItemViewType(position);

            if (convertView == null) {
                holder = new ViewHolder();
                switch (rowType) {
                    case TYPE_ITEM:
                        convertView = mInflater.inflate(R.layout.matchmakinglayout, null);
                        holder.textView = (TextView) convertView.findViewById(R.id.text);
                        break;
                    case TYPE_SEPARATOR:
                        convertView = mInflater.inflate(R.layout.snippet_item2, null);
                        holder.textView = (TextView) convertView.findViewById(R.id.textSeparator);
                        break;
                }
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView.setText(mData.get(position).get("ques"));

            return convertView;
        }

        public static class ViewHolder {
            public TextView textView;
        }

    }

