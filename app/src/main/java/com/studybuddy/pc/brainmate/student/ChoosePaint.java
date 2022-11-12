package com.studybuddy.pc.brainmate.student;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.student.freehandpainting.FreehandActivityMain;
import com.studybuddy.pc.brainmate.student.painting.coloring.book_selection.BookSelectionMainActivity;
import com.studybuddy.pc.brainmate.student.painting.coloring.coloring.ColoringActivity;

public class ChoosePaint extends AppCompatActivity {
    ImageView freehand, Shape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_paint);
        freehand = (ImageView) findViewById(R.id.freehand);
        Shape = (ImageView) findViewById(R.id.Shape);
        freehand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent=new Intent(ChoosePaint.this,FreehandActivityMain.class);
                startActivity(intent);*/
                final Dialog dialog = new Dialog(ChoosePaint.this);
                dialog.setContentView(R.layout.choosedialogtwo);
                dialog.setTitle("Custom Dialog");
                dialog.show();
                LinearLayout FreefromGallery = dialog.findViewById(R.id.fromGallery);
                LinearLayout FreeFromFolder = dialog.findViewById(R.id.FromFolder);
                FreefromGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ChoosePaint.this, FreehandActivityMain.class);
                        intent.putExtra("PageValue", "1");
                        startActivity(intent);
                    }
                });
                FreeFromFolder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ChoosePaint.this, FreehandActivityMain.class);
                        intent.putExtra("PageValue", "2");
                        startActivity(intent);
                    }
                });
            }
        });
        Shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ChoosePaint.this);
                dialog.setContentView(R.layout.choosedialog);
                dialog.setTitle("Custom Dialog");
                dialog.show();
                LinearLayout fromGallery = dialog.findViewById(R.id.fromGallery);
                LinearLayout FromFolder = dialog.findViewById(R.id.FromFolder);
                fromGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ChoosePaint.this, ColoringActivity.class);
                        intent.putExtra("PageValue", "1");
                        startActivity(intent);
                    }
                });
                FromFolder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ChoosePaint.this, BookSelectionMainActivity.class);
                        intent.putExtra("PageValue", "2");
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
