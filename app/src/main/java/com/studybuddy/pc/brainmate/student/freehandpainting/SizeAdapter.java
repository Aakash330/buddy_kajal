package com.studybuddy.pc.brainmate.student.freehandpainting;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.studybuddy.pc.brainmate.R;

class SizeAdapter extends BaseAdapter {
        private Context mContext;

        int[] Itemsicon ;

        String [] Dpostion,DAddress,Dmobile,DEmails;

        public SizeAdapter(Context c, int[] image) {
            mContext = c;

            this.Itemsicon=image;
            Log.d("IMVNHMJH", String.valueOf(Itemsicon));




        }


        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return Itemsicon.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View grid;
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {

                grid = new View(mContext);
                grid = inflater.inflate(R.layout.sizeadapter, null);


            } else {
                grid = (View) convertView;
            }

            ImageView Size=(ImageView)grid.findViewById(R.id.small_brush);
            Size.setImageResource(Itemsicon[position]);



            return grid;
        }
    }

