package com.studybuddy.pc.brainmate.teacher;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.studybuddy.pc.brainmate.R;

public class TeacherInactiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_inactive);
        getSupportActionBar().setTitle("TeacherInactive");
        AlertDialog dialog=  new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_launcher2)
                .setTitle("Thank you for Registration")
                .setMessage(R.string.thanksmsg)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                })
                .show();
    }
}