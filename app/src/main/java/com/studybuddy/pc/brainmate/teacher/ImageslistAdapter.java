package com.studybuddy.pc.brainmate.teacher;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.mains.Apis;

import java.util.ArrayList;
import java.util.HashMap;


public abstract class ImageslistAdapter extends RecyclerView.Adapter<ImageslistAdapter.ViewHolder> {

    private ArrayList<HashMap<String, String>> data_list = new ArrayList<>();
    private Context context;
    String[] CategoryName;
    String URL;
    int[] Icons;

    public ImageslistAdapter(ArrayList<HashMap<String, String>> data_list, Context context) {
        this.data_list = data_list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false));
    }

    public abstract void load();

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ViewHolder messageViewHolder = (ViewHolder) holder;
        //  messageViewHolder.nameT.setText(data_list.get(position).get("Images"));
        URL = data_list.get(position).get("book_img");
        //  holder.CatalogImage.setImageResource(Integer.parseInt(data_list.get(position).get("Images")));
       /* messageViewHolder.nameT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog3 = new Dialog(context);
                dialog3.setContentView(R.layout.imagelayout);
                dialog3.setTitle("Custom Dialog");
                dialog3.show();
                Button Close=(Button)dialog3.findViewById(R.id.Close);
                ImageView imageView=(ImageView)dialog3.findViewById(R.id.image);
                Close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog3.dismiss();
                    }
                });
                String Image_url ="http://techive.in/studybuddy/publisher/"+"";
                Log.d("Image_url",""+Image_url);

            }
        });*/
        //Glide.with(context.getApplicationContext()).load("http://techive.in/studybuddy/publisher/" + URL)
        Glide.with(context.getApplicationContext()).load(Apis.base_url_img + URL)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder)
                .into(holder.CatalogImage);
        holder.CatalogImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("getpos", holder.getAdapterPosition() + "");
                final Dialog dialog3 = new Dialog(context);
                dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog3.setContentView(R.layout.imagelayout);
                //dialog3.setTitle("Custom Dialog");
                dialog3.show();
                ImageView Imagevioew = (ImageView) dialog3.findViewById(R.id.Imagevioew);
                Glide.with(context).load(Apis.base_url_img + data_list.get(holder.getAdapterPosition()).get("book_img"))
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.placeholder)
                        .into(Imagevioew);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameT;
        ImageView CatalogImage;

        public ViewHolder(View itemView) {
            super(itemView);
            /* nameT = (TextView) itemView.findViewById(R.id.textview);*/
            CatalogImage = (ImageView) itemView.findViewById(R.id.CatalogImage);
        }
    }
}



