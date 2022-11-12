package com.studybuddy.pc.brainmate.student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.studybuddy.pc.brainmate.R;

public class StudebtElimentery extends AppCompatActivity {

    Button TakeTest, E_Book, AnimationVideo;
    String access_code, ebook, Subject,Title;
    Toolbar toolbarHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_studebt_factivty);
        access_code = getIntent().getStringExtra("access_code");
        ebook = getIntent().getStringExtra("ebook");
        Subject = getIntent().getStringExtra("Subject");
        Title = getIntent().getStringExtra("Title");

        toolbarHeader = findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbarHeader);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbarHeader.setTitleTextColor(getResources().getColor(R.color.white));
            //toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow);
            //getSupportActionBar().setTitle("Techive");
        }
        setTitle("Learning Elementary");
        //setTitle(getIntent().getStringExtra("Title") != null ? getIntent().getStringExtra("Title") : "E-Book");


        TakeTest = (Button) findViewById(R.id.ActivityOlympiad);
        E_Book = (Button) findViewById(R.id.E_Book);
        AnimationVideo = (Button) findViewById(R.id.AnimationVideo);
        //E_Book.setText(Title != null ? Title : "");

        TakeTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudebtElimentery.this, Students_Chapters.class);
                intent.putExtra("access_code", access_code);
                intent.putExtra("Subject", Subject);
                intent.putExtra("from", "s");
                Log.d("activity_olympiad",access_code+"\n"+Subject);
                startActivity(intent);

            }
        });

        E_Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudebtElimentery.this, StudentEbook.class);
                intent.putExtra("ebook", ebook);
                intent.putExtra("Title", Title != null ? Title : "E-Book");
                startActivity(intent);

            }
        });

        AnimationVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudebtElimentery.this, StudentAnimition.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
          /*getMenuInflater().inflate(R.menu.studentdash_bord, menu);
          return true;*/
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_go_home:
                Intent intent = new Intent(StudebtElimentery.this, Stu_Classes.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("accesscodes", CommonMethods.getAccessCode(StudebtElimentery.this));
                intent.putExtra("Student_ID", CommonMethods.getId(StudebtElimentery.this));
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
