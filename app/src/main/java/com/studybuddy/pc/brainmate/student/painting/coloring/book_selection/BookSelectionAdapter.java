package com.studybuddy.pc.brainmate.student.painting.coloring.book_selection;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.student.Books_list;

import java.util.ArrayList;


/**
 * Book selection adapter providing the book images in the coloring book selection grid view.
 */
public class BookSelectionAdapter extends BaseAdapter {
    private AssetManager assetManager;
    private final Context context;
    private final int size; // it's the same for all elements
//    private final Library library = Library.getInstance(); // just for convenience
    private ArrayList<Books_list> Month_One_Array=new ArrayList<>();
    public BookSelectionAdapter(Context context, ArrayList<Books_list> month_One_Array) {
        this.context = context;
        this.Month_One_Array=month_One_Array;
       this.size = context.getResources().getDimensionPixelSize(R.dimen.book_preview_width);
        Log.d("Month_One_Array", String.valueOf(Month_One_Array));
    }

    @Override
    public int getCount() {
        return Month_One_Array.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Month_One_Array.get(position);

        View view;
        if (convertView == null) {
            view = View.inflate(context, R.layout.element_book_selection, null);
            view.setLayoutParams(new GridView.LayoutParams(size, size));
        } else {
            view = convertView;
        }

        // customize book view
        TextView categoryNameView = view.findViewById(R.id.bookNameTextView);
        categoryNameView.setText(Month_One_Array.get(position).getCover("cover"));
        ImageView previewImageView = view.findViewById(R.id.bookPreviewImageView);
        Glide.with(context)
                .load("file:///android_asset/book_background.png")
                .into(previewImageView);

        /*try {
            InputStream is  = assetManager.open("bheem.jpg");
            Bitmap  bitmap = BitmapFactory.decodeStream(is);
            previewImageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

       /* Bitmap bitmap =loadCurrentBookCoverBitmapDownscaled(size, size);
        bitmap = Utils.replaceColorInBitmap(bitmap, Color.WHITE, Color.TRANSPARENT);
        previewImageView.setImageBitmap(bitmap);*/


        return view;
    }
}
