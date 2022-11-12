package com.studybuddy.pc.brainmate.teacher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.student.CommonMethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FillDetailsActivity extends AppCompatActivity {

    EditText etPaperName, LongQM, ShortQM, MultipleQM, TruefalseQM, FillintheblackQM, MatchMakingQM, etExamDurationHr, etExamDurationMin;
    Context context;
    TeChapter.ChapterAdapter adapter;
    private String Class, Subject, displayClassName, ch_names;
    Toolbar toolbarHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fill_marks_layout);
        context = FillDetailsActivity.this;
        toolbarHeader = findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbarHeader);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbarHeader.setTitleTextColor(getResources().getColor(R.color.white));
            //toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow);
            //getSupportActionBar().setTitle("Techive");
        }
        setTitle("Fill The Details");
        etPaperName = (EditText) findViewById(R.id.etPaperName);
        LongQM = (EditText) findViewById(R.id.LongQM);
        ShortQM = (EditText) findViewById(R.id.ShortQM);
        MultipleQM = (EditText) findViewById(R.id.MultipleQM);
        TruefalseQM = (EditText) findViewById(R.id.TruefalseQM);
        FillintheblackQM = (EditText) findViewById(R.id.FillintheblackQM);
        MatchMakingQM = (EditText) findViewById(R.id.MatchMakingQM);
        etExamDurationHr = (EditText) findViewById(R.id.etExamDurationHr);
        etExamDurationMin = (EditText) findViewById(R.id.etExamDurationMin);

        LongQM.setRawInputType(Configuration.KEYBOARD_QWERTY);
        ShortQM.setRawInputType(Configuration.KEYBOARD_QWERTY);
        MultipleQM.setRawInputType(Configuration.KEYBOARD_QWERTY);
        TruefalseQM.setRawInputType(Configuration.KEYBOARD_QWERTY);
        FillintheblackQM.setRawInputType(Configuration.KEYBOARD_QWERTY);
        MatchMakingQM.setRawInputType(Configuration.KEYBOARD_QWERTY);
        Button NextButton = (Button) findViewById(R.id.NextButton);

        Class = getIntent().getStringExtra("Class") != null ? getIntent().getStringExtra("Class") : "";
        displayClassName = getIntent().getStringExtra("displayClassName") != null ? getIntent().getStringExtra("displayClassName") : "";
        Subject = getIntent().getStringExtra("Subject") != null ? getIntent().getStringExtra("Subject") : "";
        ch_names = getIntent().getStringExtra("ch_names") != null ? getIntent().getStringExtra("ch_names") : "";

        //String finalListString = listString;
        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("chList11", "" + ch_names);
                if (!isValid(LongQM.getText().toString().trim())) {
                    LongQM.setError("Please remove special character");
                } else if (!isValid(ShortQM.getText().toString().trim())) {
                    ShortQM.setError("Please remove special character");
                } else if (!isValid(MultipleQM.getText().toString().trim())) {
                    MultipleQM.setError("Please remove special character");
                } else if (!isValid(TruefalseQM.getText().toString().trim())) {
                    TruefalseQM.setError("Please remove special character");
                } else if (!isValid(FillintheblackQM.getText().toString().trim())) {
                    FillintheblackQM.setError("Please remove special character");
                } else if (!isValid(MatchMakingQM.getText().toString().trim())) {
                    MatchMakingQM.setError("Please remove special character");
                } else if (etPaperName.getText().toString().isEmpty()) {
                    etPaperName.setError("Enter paper name.");
                } /*else if (etExamDurationHr.getText().toString().isEmpty()) {
                                etExamDurationHr.setError("Enter Time in Hour.");
                            } else if (etExamDurationMin.getText().toString().isEmpty()) {
                                etExamDurationMin.setError("Enter Time in Min.");
                            }*/ else {
                    if (etExamDurationHr.getText().toString().isEmpty() && etExamDurationMin.getText().toString().isEmpty()) {
                        etExamDurationMin.setText("30");
                    }

                    Intent intent = new Intent(context, QuetionPaper.class);
                    intent.putExtra("Class", Class);
                    intent.putExtra("displayClassName", displayClassName);
                    intent.putExtra("Subject", Subject);
                    //intent.putExtra("Chapters_ID", finalListString);
                    intent.putExtra("Chapters_ID", ch_names);
                    intent.putExtra("LongQM", LongQM.getText().toString());
                    intent.putExtra("ShortQM", ShortQM.getText().toString());
                    intent.putExtra("MultipleQM", MultipleQM.getText().toString());
                    intent.putExtra("TruefalseQM", TruefalseQM.getText().toString());
                    intent.putExtra("FillintheblackQM", FillintheblackQM.getText().toString());
                    intent.putExtra("MatchMakingQM", MatchMakingQM.getText().toString());
                    intent.putExtra("PaperName", etPaperName.getText().toString());
                    intent.putExtra("ExamDuration", (!etExamDurationHr.getText().toString().isEmpty() ? etExamDurationHr.getText().toString() : "0") + " hr " + (!etExamDurationMin.getText().toString().isEmpty() ? etExamDurationMin.getText().toString() : "0") + " min ");
                    startActivity(intent);
                }
            }
        });
    }

    public static boolean isValid(String str) {
        boolean isValid = false;
        String expression = "^[a-z_A-Z0-9 ]*$";
        CharSequence inputStr = str;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_go_home:
                Intent intent = new Intent(context, Main2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("accesscodes", CommonMethods.getAccessCode(context));
                intent.putExtra("Teachers_ID", CommonMethods.getId(context));
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
