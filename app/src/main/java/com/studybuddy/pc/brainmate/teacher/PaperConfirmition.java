package com.studybuddy.pc.brainmate.teacher;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.studybuddy.pc.brainmate.R;

import java.util.ArrayList;
import java.util.HashMap;

public class PaperConfirmition extends AppCompatActivity {

    LinearLayout Subjectivelayout, ObjectiveLayout, ObjectiveplushsubLayout, Fillintheblanklayout;
    ListView Subjectivelist, Objectivelist, TrueFalse, Fillintheblanklist;
    Button Next, Generate;
    int subtive = 1, objtive = 2, turefalse = 3, fillintheblanks = 4;
    int displainglist = 1;


    Button Addbuitton, Optionbutton, check;
    String[] Quetions = {"WWW stands for ?", "Which of the following are components of Central Processing Unit (CPU) ?", " Which among following first generation of computers had ?", "Where is RAM located ?", " If a computer has more than one processor then it is known as ?"};
    ConfiOjectiveAdapter adapter;
    String[] TRUEFALSE = {"WWW stands for ____ ?", "Which of the following are ____ of Central Processing Unit (CPU) ?", " Which among following first generation of ____ had ?", "Where is RAM located ?", " If a computer has more than one ____ then it is known as ?"};
    // ProgressDialog progressDialog;
    ArrayList<HashMap<String, String>> PatientList;
    String UpcomigID;
    EditText inputSearch;
    ArrayList<HashMap<String, String>> searchResults;
    ArrayList<HashMap<String, String>> Arraylist;
    ArrayList<HashMap<String, String>> Arraylist2;


    ConfiSubjectiveAdapter subjectiveAdapter;
    ArrayList<HashMap<String, String>> subjectiveAraylist;
    ArrayList<HashMap<String, String>> turefalesAraylist;
    ArrayList<HashMap<String, String>> fillinTheblanklist;
    ArrayList<HashMap<String, String>> Objective;

    ConfiTureFalseAdapter trufalseAdapter;


    ConfiFillinTheblankApter fillinTheblank;


    TextView SectionName, Sectionnumber;

    String SubjectveMarks, ObjectiveMarks, truefalseMarks, fillintheblacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_confirmition);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Next = (Button) findViewById(R.id.Next);
        Generate = (Button) findViewById(R.id.Generate);

        Subjectivelayout = (LinearLayout) findViewById(R.id.longQAlayout);
        ObjectiveLayout = (LinearLayout) findViewById(R.id.ObjectiveLayout);
        ObjectiveplushsubLayout = (LinearLayout) findViewById(R.id.ObjectiveplushsubLayout);
        Fillintheblanklayout = (LinearLayout) findViewById(R.id.Fillintheblanklayout);

        Subjectivelist = (ListView) findViewById(R.id.Subjectivelist);
        Objectivelist = (ListView) findViewById(R.id.Objectivelist);
        TrueFalse = (ListView) findViewById(R.id.true_false_list);
        Fillintheblanklist = (ListView) findViewById(R.id.Fillintheblanklist);
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        SectionName = (TextView) findViewById(R.id.SectionNAne);
        Sectionnumber = (TextView) findViewById(R.id.SectionNumber);


        subjectiveAraylist = new ArrayList<>();
        turefalesAraylist = new ArrayList<>();
        fillinTheblanklist = new ArrayList<>();
        Objective = new ArrayList<>();

        subjectiveAraylist = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("LongQuestionAdapter");
        turefalesAraylist = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("trufalseAdapterlist");
        fillinTheblanklist = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("fillinTheblanklistt");
        Objective = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("shortQuestionlist");


        SubjectveMarks = getIntent().getStringExtra("SubjectiveMarks");
        ObjectiveMarks = getIntent().getStringExtra("ObjectiveMarks");
        truefalseMarks = getIntent().getStringExtra("turefalseMarks");
        fillintheblacks = getIntent().getStringExtra("Fillintheblanks");
//        Log.d("IntenteArraylist", String.valueOf(subjectiveAraylist.get(1).get("Confirmation")));


        Arraylist = new ArrayList<>();
        for (int i = 0; i < Quetions.length; i++) {
            HashMap<String, String> map1 = new HashMap<>();
            map1.put("Quetion", Quetions[i]);
            map1.put("code", "1");

            Arraylist.add(map1);

        }
        Arraylist2 = new ArrayList<>();
        for (int i = 0; i < TRUEFALSE.length; i++) {
            HashMap<String, String> map3 = new HashMap<>();
            map3.put("Quetion", TRUEFALSE[i]);
            map3.put("code", "1");

            Arraylist2.add(map3);

        }

        if (displainglist == 1) {
            Subjectivelayout.setVisibility(View.VISIBLE);
            ObjectiveLayout.setVisibility(View.GONE);
            ObjectiveplushsubLayout.setVisibility(View.GONE);
            Fillintheblanklayout.setVisibility(View.GONE);
            SectionName.setText("Subjective");
            Sectionnumber.setText("1/4");

        } else if (displainglist == 2) {
            Subjectivelayout.setVisibility(View.GONE);
            ObjectiveLayout.setVisibility(View.VISIBLE);
            ObjectiveplushsubLayout.setVisibility(View.GONE);
            Fillintheblanklayout.setVisibility(View.GONE);
            SectionName.setText("Objective");
            Sectionnumber.setText("2/4");

        } else if (displainglist == 3) {
            Subjectivelayout.setVisibility(View.GONE);
            ObjectiveLayout.setVisibility(View.GONE);
            ObjectiveplushsubLayout.setVisibility(View.VISIBLE);
            Fillintheblanklayout.setVisibility(View.GONE);
            SectionName.setText("True/False");
            Sectionnumber.setText("3/4");
        } else if (displainglist == 4) {
            Subjectivelayout.setVisibility(View.GONE);
            ObjectiveLayout.setVisibility(View.GONE);
            ObjectiveplushsubLayout.setVisibility(View.GONE);
            Fillintheblanklayout.setVisibility(View.VISIBLE);
            SectionName.setText("Fill In The Blanks");
            Sectionnumber.setText("4/4");
        }
        adapter = new ConfiOjectiveAdapter(this, Objective);
        Objectivelist.setAdapter(adapter);

        trufalseAdapter = new ConfiTureFalseAdapter(this, turefalesAraylist);
        TrueFalse.setAdapter(trufalseAdapter);

        subjectiveAdapter = new ConfiSubjectiveAdapter(this, subjectiveAraylist);
        Subjectivelist.setAdapter(subjectiveAdapter);


        fillinTheblank = new ConfiFillinTheblankApter(this, fillinTheblanklist);
        Fillintheblanklist.setAdapter(fillinTheblank);

        Generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(PaperConfirmition.this);
                dialog.setContentView(R.layout.confirmitiondailog);
                dialog.setTitle("Custom Dialog");
                dialog.show();
                Button yes = (Button) dialog.findViewById(R.id.yes);
                Button No = (Button) dialog.findViewById(R.id.No);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(PaperConfirmition.this, "Question Paper Generated", Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                    }
                });
                No.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }

        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (displainglist == 1) {
                    Subjectivelayout.setVisibility(View.VISIBLE);
                    ObjectiveLayout.setVisibility(View.GONE);
                    ObjectiveplushsubLayout.setVisibility(View.GONE);
                    Fillintheblanklayout.setVisibility(View.GONE);
                    displainglist = 2;
                    SectionName.setText("Subjective");
                    Sectionnumber.setText("1/4");
                } else if (displainglist == 2) {
                    Subjectivelayout.setVisibility(View.GONE);
                    ObjectiveLayout.setVisibility(View.VISIBLE);
                    ObjectiveplushsubLayout.setVisibility(View.GONE);
                    Fillintheblanklayout.setVisibility(View.GONE);
                    displainglist = 3;
                    SectionName.setText("Objective");
                    Sectionnumber.setText("2/4");
                } else if (displainglist == 3) {
                    Subjectivelayout.setVisibility(View.GONE);
                    ObjectiveLayout.setVisibility(View.GONE);
                    ObjectiveplushsubLayout.setVisibility(View.VISIBLE);
                    Fillintheblanklayout.setVisibility(View.GONE);
                    displainglist = 4;
                    SectionName.setText("True/False");
                    Sectionnumber.setText("3/4");
                } else if (displainglist == 4) {
                    Subjectivelayout.setVisibility(View.GONE);
                    ObjectiveLayout.setVisibility(View.GONE);
                    ObjectiveplushsubLayout.setVisibility(View.GONE);
                    Fillintheblanklayout.setVisibility(View.VISIBLE);
                    displainglist = 1;
                    SectionName.setText("Fill In The Blanks");
                    Sectionnumber.setText("4/4");
                }
            }
        });

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }

}
