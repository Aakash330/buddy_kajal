package com.studybuddy.pc.brainmate.teacher;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.studybuddy.pc.brainmate.Network_connection.utils.NetworkUtil;
import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.student.CommonMethods;
import com.studybuddy.pc.brainmate.student.Students_Chapters;

public class LearningElementary extends AppCompatActivity {

    Button TestGenrator, E_Book, TeachersBook, AnimationVideo, Activities;
    String Class, Subject, displayClassName;
    String ebook;
    String access_code, manual;
    String Title;
    Toolbar toolbarHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_elementary);
        setTitle("Learning Elementary");
        TestGenrator = (Button) findViewById(R.id.TestGenrator);
        AnimationVideo = (Button) findViewById(R.id.AnimationVideo);
        E_Book = (Button) findViewById(R.id.E_Book);
        TeachersBook = (Button) findViewById(R.id.TeacherMenual);
        Activities = (Button) findViewById(R.id.Activities);
        Class = getIntent().getStringExtra("Class");
        displayClassName = getIntent().getStringExtra("displayClassName");
        Subject = getIntent().getStringExtra("SubjectNme");
        ebook = getIntent().getStringExtra("ebook");
        access_code = getIntent().getStringExtra("access_code");
        manual = getIntent().getStringExtra("manual");
        Title = getIntent().getStringExtra("Title");
        //E_Book.setText(Title != null ? Title : "");
        Log.d("manual321", "http://techive.in/studybuddy/publisher/" + manual);

        toolbarHeader = findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbarHeader);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbarHeader.setTitleTextColor(getResources().getColor(R.color.white));
            //toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow);
            //getSupportActionBar().setTitle("Techive");
        }
        setTitle(getIntent().getStringExtra("Title") != null ? getIntent().getStringExtra("Title") : "");

        Activities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtil.getConnectivityStatus(LearningElementary.this) > 0) {
                    Intent intent = new Intent(LearningElementary.this, Students_Chapters.class);
                    intent.putExtra("access_code", access_code);
                    intent.putExtra("displayClassName", displayClassName);
                    intent.putExtra("Subject", Subject);
                    intent.putExtra("from", "t");
                    Log.d("activity_olympiad1",access_code+"\n"+Subject+"\n"+displayClassName);
                    startActivity(intent);
                } else {
                    checkNetDialog();
                }
            }
        });
        E_Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtil.getConnectivityStatus(LearningElementary.this) > 0) {
                    Intent intent = new Intent(LearningElementary.this, E_book.class);
                    intent.putExtra("ebook", ebook);
                    intent.putExtra("Title", Title);
                    startActivity(intent);
                } else {
                    checkNetDialog();
                }
            }
        });
        TestGenrator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtil.getConnectivityStatus(LearningElementary.this) > 0) {
                    Intent intent = new Intent(LearningElementary.this, TeChapter.class);
                    intent.putExtra("Class", Class);
                    intent.putExtra("displayClassName", displayClassName);
                    intent.putExtra("Subject", Subject);
                    intent.putExtra("access_code", access_code);
                    startActivity(intent);
                } else {
                    checkNetDialog();
                }
            }
        });
        TeachersBook.setVisibility(manual != null && !manual.isEmpty() ? View.VISIBLE : View.GONE);
        TeachersBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtil.getConnectivityStatus(LearningElementary.this) > 0) {
                    Log.d("manual", "" + manual);
                    if (manual != null && !manual.isEmpty()) {
                        Intent intent = new Intent(LearningElementary.this, TeachersManual.class);
                        intent.putExtra("manual", manual);
                        intent.putExtra("Title", Title);
                        startActivity(intent);
                    }
                } else {
                    checkNetDialog();
                }
            }
        });
        AnimationVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LearningElementary.this, AnimationView.class);
                startActivity(intent);
            }
        });
    }

    public void checkNetDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LearningElementary.this);
        alertDialogBuilder.setMessage(getString(R.string.no_internet));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(getString(R.string.retry),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        if (NetworkUtil.getConnectivityStatus(LearningElementary.this) > 0) {
                            Toast.makeText(LearningElementary.this, "" + getString(R.string.connected), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            checkNetDialog();
                        }
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_go_home:
                finish();
                Intent intent = new Intent(LearningElementary.this, Main2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("accesscodes", CommonMethods.getAccessCode(LearningElementary.this));
                intent.putExtra("Teachers_ID", CommonMethods.getId(LearningElementary.this));
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //region "Menu"
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
}
