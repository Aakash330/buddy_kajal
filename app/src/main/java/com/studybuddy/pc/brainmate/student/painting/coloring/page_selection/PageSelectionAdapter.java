package com.studybuddy.pc.brainmate.student.painting.coloring.page_selection;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.studybuddy.pc.brainmate.R;

/**
 * Page selection adapter providing the coloring pages from the current coloring book in a grid view.
 */
public class PageSelectionAdapter extends BaseAdapter {

    private final Context context;
    private final int size; // it's the same same for all elements
//    private final Library library = Library.getInstance(); // just convenience
int[] Arraylist={};
    public PageSelectionAdapter(Context context, int[] paintImage) {
        this.context = context;
        this.size = context.getResources().getDimensionPixelSize(R.dimen.page_preview_width);
        this.Arraylist = paintImage;
    }

    @Override
    public int getCount() {
        return Arraylist.length;
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
        // returns a single page preview

        // update page in library
      //  library.setCurrentPage(position);

        View view;
        if (convertView == null) {
            view = View.inflate(context, R.layout.element_page_selection, null);
            view.setLayoutParams(new GridView.LayoutParams(size, size));
        } else {
            view = convertView;
        }

        // customize page view
        TextView categoryNameView;
        categoryNameView = view.findViewById(R.id.pageNameTextView);
      //  categoryNameView.setText(library.getStringFromCurrentPage("name") + "\nEasy");

        ImageView previewImageView = view.findViewById(R.id.pagePreviewImageView);
        previewImageView.setImageResource(Arraylist[position]);
       /* Bitmap bitmap = library.loadCurrentPageBitmapDownscaled(size, size);
        bitmap = Utils.replaceColorInBitmap(bitmap, Color.WHITE, Color.TRANSPARENT);
        previewImageView.setImageBitmap(bitmap);*/

        return view;
    }
}
