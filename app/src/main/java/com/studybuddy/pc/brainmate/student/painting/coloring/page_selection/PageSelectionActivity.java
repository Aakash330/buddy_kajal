package com.studybuddy.pc.brainmate.student.painting.coloring.page_selection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.student.painting.coloring.coloring.ColoringActivity;

/**
 * Once a coloring book has been chosen, select a page here. Also a way to show the gallery for this book.
 */
public class PageSelectionActivity extends AppCompatActivity {
    int[] PaintImage={R.drawable.balloons,R.drawable.balloons,R.drawable.balloons,R.drawable.balloons,R.drawable.balloons};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_selection);

        // back button action: go back
        /*ImageButton imageButton = findViewById(R.id.backButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
*/
        // picture gallery button action: start picture gallery activity
      /*  imageButton = findViewById(R.id.galleryButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PageSelectionActivity.this, PictureGalleryActivity.class);
                startActivity(intent);
            }
        });
*/
        // populate the grid view using a PageSelectionAdapter
        final GridView gridView = findViewById(R.id.pageSelectionGridView);
        gridView.setAdapter(new PageSelectionAdapter(this,PaintImage));

        // on item click listener for grid view, start coloring
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // current page in library
              //  Library.getInstance().setCurrentPage(position);

                // start coloring activity
                Intent intent = new Intent(PageSelectionActivity.this, ColoringActivity.class);
                intent.putExtra("PageValue","2");
                startActivity(intent);
            }
        });
    }
}
