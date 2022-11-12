package com.studybuddy.pc.brainmate.student;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.studybuddy.pc.brainmate.DisplayTestResultActivity;
import com.studybuddy.pc.brainmate.mains.Apis;
import com.studybuddy.pc.brainmate.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class StudentTest extends AppCompatActivity {

    ///////////////////headerview////////////////////
    //region "Match Column"
    int mTotal = 0;
    int mColTotal = 0;
    int eachQuestionMarks = 1;
    static int main_question_count = 0;
    int mcol_question_count = 0;
    int mColValue = 0;//TODO : Find in 1814
    int nextMColCount, backMColCount;
    Button NextButtonMCol, backButtonMCol;
    int marks_mcq = 2, marks_tf = 2, marks_fill = 2, marks_mCol = 2;
    ArrayList<String> mcol_User_ans = new ArrayList<>();
    ArrayList<String> mcq_listToSend = new ArrayList<>();
    EditText eTr1, eTr2, eTr3, eTr4, eTr5, eTr6, eTr7, eTr8;
    ArrayList<String> list1 = new ArrayList<>();

    ArrayList<String> list_mcol_mrks = new ArrayList<>();


    int totalTestMarks = 0;
    String answer1 = "";
    String answer2 = "";
    String answer3 = "";
    String answer4 = "";
    String answer5 = "";
    String answer6 = "";
    String answer7 = "";
    String answer8 = "";//2873
    //1863
    int match_count = 0;
    TextView txtQnoMCol, txtCol1r1, txtCol1r2, txtCol1r3, txtCol1r4, txtCol1r5, txtCol1r6, txtCol1r7, txtCol1r8, txtCol2r1, txtCol2r2, txtCol2r3, txtCol2r4, txtCol2r5, txtCol2r6, txtCol2r7, txtCol2r8, txtTotalMarksMCol;
    LinearLayout layoutMColumn;
    //endregion
    //region "Variables"
    int dispMarks = 0;
    int QuestionCount = 1;
    int mCol = 0;
    ArrayList<HashMap<String, String>> MCQArraySelected, TruefalseSelected, FillinthSelected;
    String MCQUPcomingANS = "";
    TextView PaperName, TotalQuestions, totalmarks;
    LinearLayout llMCol1, llMCol2, llMCol3, llMCol4, llMCol5, llMCol6, llMCol7, llMCol8;
    LinearLayout objective, TureFasle, FillInTheBlancks, MQCTestLayout;
    Button NextButtonTruefalse, NextButtonobjective, BACKButtonTruefalse, NextButtonfillintheblank, backButtonobjective, BackButtonfillintheblank;
    int MCQListCount = 0;
    TextView FBAnswerBox;
    int TF = 0, FB = 0;
    TextView MarksTF;
    int Quecount = 1, Quecountnumber = 0;
    int MCQNumber = 0, TRUEFalseNumber = 0, FillintheNumber = 0;
    int TFQuesCount = 0, truefalseCount = 0, FillinbCount = 0;
    TextView OBJQuestion, TFQuestion, FBQuestion, marksOBJ, QuestionnumberOBJ, QuestionCountFB, TotalMarksFB, TIMEPASS, Timer;
    RadioButton Option1, Option2, Option3, Option4, TRUEBton, FALSEBTN;
    int CountObj, CountTruefalse, CountFillIntheblnks;
    String OOMarks, TTFMarks, FITBMarks;
    int mcq_size = 0, tf_size = 0, fill_in_blank_size = 0, match_col_size = 0;

    String OBJRightAnswer, TFRightAswer, FBrightAnswer, OBJMarked_Answe, NextOBJMarked_Answe, TrueMarked_Answe, NextTrueMarked_Answe, NextMarked_Answe;
    int OBJMarksCount = 0, TRUEFALSEmarksCount = 0, FillIntheblancksmarkscount = 0;
    ArrayList<HashMap<String, String>> Questionobjective, TUREFALSE, FILLINTHEBLANKS, QuestiojnMCQ, QuestionMCol;


    ArrayList<String> ObjectiveRightAnswersID, TruefalseRightAnswersID, FillintheblanksRightAnswersID;
    RadioGroup rg, rg1;
    String objective_Answer;
    ProgressDialog progressDialog;


    TextView Option_textA, Option_textB, Option_textC, Option_textD, marksMCQ, QuestionnumberMCQ;
    ImageView Option_Aiamge, Option_Biamge, Option_Ciamge, Option_Diamge, MQCQuestionImag;
    CheckBox Option_A, Option_B, Option_C, Option_D;
    int QuestionSCount = 1, TrQuestionNumber = 1, FBQuestionSCount = 1;
    Button NextButtonMCQ, backButtonMCQ;
    TextView MCQQuestion;
    String ImageURL, OPImage1, OPImage2, OPImage3, OPImage4;
    List<String> elephantList;
    Context context;
    Activity activity;
    String ChapterID, Subject = "";//endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_test);
        startCountDown(this);
        context = StudentTest.this;
        activity = (Activity) context;
        Questionobjective = new ArrayList<>();
        FILLINTHEBLANKS = new ArrayList<>();
        QuestiojnMCQ = new ArrayList<>();
        MCQArraySelected = new ArrayList<>();
        TruefalseSelected = new ArrayList<>();
        FillinthSelected = new ArrayList<>();
        QuestionMCol = new ArrayList<>();

        ChapterID = getIntent().getStringExtra("ChapterID");
        Subject = getIntent().getStringExtra("Subject");

        Log.d("ChapterID", ChapterID);
        //region "findViewById"
        objective = (LinearLayout) findViewById(R.id.objectivelayout);
        TureFasle = (LinearLayout) findViewById(R.id.TRUEFALSE);
        FillInTheBlancks = (LinearLayout) findViewById(R.id.FillintheBlancks);
        BackButtonfillintheblank = (Button) findViewById(R.id.BackButtonfillintheblank);
        TRUEBton = (RadioButton) findViewById(R.id.TRUE11);
        FALSEBTN = (RadioButton) findViewById(R.id.FALSE11);

        PaperName = (TextView) findViewById(R.id.PaperName);
        TotalQuestions = (TextView) findViewById(R.id.TotalQuestions);
        Timer = (TextView) findViewById(R.id.Timer);

        totalmarks = (TextView) findViewById(R.id.totalmarks);
        TIMEPASS = (TextView) findViewById(R.id.TIMEPASS);
        BACKButtonTruefalse = (Button) findViewById(R.id.BACKButtonTruefalse);

        NextButtonTruefalse = (Button) findViewById(R.id.NextButtonTruefalse);
        NextButtonfillintheblank = (Button) findViewById(R.id.NextButtonfillintheblank);

        //region "FindViewById MCol"
        llMCol1 = findViewById(R.id.llMCol1);
        llMCol2 = findViewById(R.id.llMCol2);
        llMCol3 = findViewById(R.id.llMCol3);
        llMCol4 = findViewById(R.id.llMCol4);
        llMCol5 = findViewById(R.id.llMCol5);
        llMCol6 = findViewById(R.id.llMCol6);
        llMCol7 = findViewById(R.id.llMCol7);
        llMCol8 = findViewById(R.id.llMCol8);

        txtQnoMCol = findViewById(R.id.txtQnoMCol);
        txtTotalMarksMCol = findViewById(R.id.txtTotalMarksMCol);
        txtCol1r1 = findViewById(R.id.txtCol1r1);
        txtCol1r2 = findViewById(R.id.txtCol1r2);
        txtCol1r3 = findViewById(R.id.txtCol1r3);
        txtCol1r5 = findViewById(R.id.txtCol1r5);
        txtCol1r6 = findViewById(R.id.txtCol1r6);
        txtCol1r7 = findViewById(R.id.txtCol1r7);
        txtCol1r8 = findViewById(R.id.txtCol1r8);
        txtCol1r4 = findViewById(R.id.txtCol1r4);

        txtCol2r1 = findViewById(R.id.txtCol2r1);
        txtCol2r2 = findViewById(R.id.txtCol2r2);
        txtCol2r3 = findViewById(R.id.txtCol2r3);
        txtCol2r4 = findViewById(R.id.txtCol2r4);
        txtCol2r5 = findViewById(R.id.txtCol2r5);
        txtCol2r6 = findViewById(R.id.txtCol2r6);
        txtCol2r7 = findViewById(R.id.txtCol2r7);
        txtCol2r8 = findViewById(R.id.txtCol2r8);
        NextButtonMCol = findViewById(R.id.NextButtonMCol);
        backButtonMCol = findViewById(R.id.backButtonMCol);
        layoutMColumn = findViewById(R.id.layoutMColumn);

        eTr1 = findViewById(R.id.eTr1);
        eTr2 = findViewById(R.id.eTr2);
        eTr3 = findViewById(R.id.eTr3);
        eTr4 = findViewById(R.id.eTr4);
        eTr5 = findViewById(R.id.eTr5);
        eTr6 = findViewById(R.id.eTr6);
        eTr7 = findViewById(R.id.eTr7);
        eTr8 = findViewById(R.id.eTr8);
        //endregion

        TruefalseRightAnswersID = new ArrayList<>();
        FillintheblanksRightAnswersID = new ArrayList<>();
        TUREFALSE = new ArrayList<>();

        //todo MCQ Questionary//////////////////////////////////////////

        Option_textA = (TextView) findViewById(R.id.Option_textA);
        Option_textB = (TextView) findViewById(R.id.Option_textB);
        Option_textC = (TextView) findViewById(R.id.Option_textC);
        Option_textD = (TextView) findViewById(R.id.Option_textD);

        Option_Aiamge = (ImageView) findViewById(R.id.Option_Aiamge);
        Option_Biamge = (ImageView) findViewById(R.id.Option_Biamge);
        Option_Ciamge = (ImageView) findViewById(R.id.Option_Ciamge);
        Option_Diamge = (ImageView) findViewById(R.id.Option_Diamge);

        Option_A = (CheckBox) findViewById(R.id.Option_A);
        Option_B = (CheckBox) findViewById(R.id.Option_B);
        Option_C = (CheckBox) findViewById(R.id.Option_C);
        Option_D = (CheckBox) findViewById(R.id.Option_D);
        Option_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Option_A.setChecked(true);
                Option_B.setChecked(false);
                Option_C.setChecked(false);
                Option_D.setChecked(false);
                MCQUPcomingANS = "a";
            }
        });
        Option_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MCQUPcomingANS = "b";
                Option_A.setChecked(false);
                Option_B.setChecked(true);
                Option_C.setChecked(false);
                Option_D.setChecked(false);
            }
        });
        Option_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MCQUPcomingANS = "c";
                Option_A.setChecked(false);
                Option_B.setChecked(false);
                Option_C.setChecked(true);
                Option_D.setChecked(false);
            }
        });
        Option_D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MCQUPcomingANS = "d";
                Option_A.setChecked(false);
                Option_B.setChecked(false);
                Option_C.setChecked(false);
                Option_D.setChecked(true);
            }
        });


        marksMCQ = (TextView) findViewById(R.id.marksMCQ);
        NextButtonMCQ = (Button) findViewById(R.id.NextButtonMCQ);
        backButtonMCQ = (Button) findViewById(R.id.backButtonMCQ);
        MQCTestLayout = (LinearLayout) findViewById(R.id.MQCTestLayout);
        QuestionnumberMCQ = (TextView) findViewById(R.id.QuestionnumberMCQ);
        MCQQuestion = (TextView) findViewById(R.id.MCQQuestion);
        MQCQuestionImag = (ImageView) findViewById(R.id.MQCQuestionImag);
        //endregion
        //String url = "http://www.techive.in/studybuddy/api/random_question.php";
        String url = Apis.base_url + Apis.random_question_url;
        //reqToCheckList(url);

        progressDialog = new ProgressDialog(StudentTest.this);
        progressDialog.setMessage("Loading..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
        RequestQueue queue = Volley.newRequestQueue(StudentTest.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("UpcomiongRendomQuestion", response);
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            JSONArray heroArray = jsonObject1.getJSONArray("mcq");
                            //region "To show Dynamic Marks Subject Question"
                            JSONArray fill_in_blankArr = jsonObject1.getJSONArray("fill_in_blank");
                            JSONArray true_falseArr = jsonObject1.getJSONArray("true_false");
                            JSONArray match_columnArr = jsonObject1.getJSONArray("match_column");
                            if (match_columnArr != null && match_columnArr.length() > 0) {
                                for (int i = 0; i < match_columnArr.length(); i++) {
                                    JSONObject object = match_columnArr.getJSONObject(i);
                                    String q1 = object.getString("column1r1");
                                    String q2 = object.getString("column1r2");
                                    String q3 = object.getString("column1r3");
                                    String q4 = object.getString("column1r4");
                                    String q5 = object.getString("column1r5");
                                    String q6 = object.getString("column1r6");
                                    String q7 = object.getString("column1r7");
                                    String q8 = object.getString("column1r8");
                                    dispMarks = q1 != null && !q1.equals("") ? dispMarks + marks_mCol : dispMarks;
                                    dispMarks = q2 != null && !q2.equals("") ? dispMarks + marks_mCol : dispMarks;
                                    dispMarks = q3 != null && !q3.equals("") ? dispMarks + marks_mCol : dispMarks;
                                    dispMarks = q4 != null && !q4.equals("") ? dispMarks + marks_mCol : dispMarks;
                                    dispMarks = q5 != null && !q5.equals("") ? dispMarks + marks_mCol : dispMarks;
                                    dispMarks = q6 != null && !q6.equals("") ? dispMarks + marks_mCol : dispMarks;
                                    dispMarks = q7 != null && !q7.equals("") ? dispMarks + marks_mCol : dispMarks;
                                    dispMarks = q8 != null && !q8.equals("") ? dispMarks + marks_mCol : dispMarks;
                                }
                            }
                            if (heroArray.length() > 0) {
                                MQCTestLayout.setVisibility(View.VISIBLE);
                                TureFasle.setVisibility(View.GONE);
                                FillInTheBlancks.setVisibility(View.GONE);
                                layoutMColumn.setVisibility(View.GONE);
                            } else if (true_falseArr.length() > 0) {
                                MQCTestLayout.setVisibility(View.GONE);
                                TureFasle.setVisibility(View.VISIBLE);
                                FillInTheBlancks.setVisibility(View.GONE);
                                layoutMColumn.setVisibility(View.GONE);
                            } else if (fill_in_blankArr.length() > 0) {
                                MQCTestLayout.setVisibility(View.GONE);
                                TureFasle.setVisibility(View.GONE);
                                FillInTheBlancks.setVisibility(View.VISIBLE);
                                layoutMColumn.setVisibility(View.GONE);
                            } else if (match_columnArr.length() > 0) {
                                MQCTestLayout.setVisibility(View.GONE);
                                TureFasle.setVisibility(View.GONE);
                                FillInTheBlancks.setVisibility(View.GONE);
                                layoutMColumn.setVisibility(View.VISIBLE);
                            }
                            totalTestMarks = heroArray.length() * marks_mcq + fill_in_blankArr.length() * marks_fill + true_falseArr.length() * marks_tf + dispMarks;
                            mTotal = totalTestMarks;
                            mcq_size = heroArray.length();
                            tf_size = true_falseArr.length();
                            fill_in_blank_size = fill_in_blankArr.length();
                            match_col_size = match_columnArr != null ? match_columnArr.length() : 0;
                            TotalQuestions.setText("Total Questions : " + String.valueOf(heroArray.length() + fill_in_blankArr.length() + true_falseArr.length() + (dispMarks / marks_mCol)));
                            PaperName.setText("Subject : " + Subject);
                            totalmarks.setText("Total Marks : " + String.valueOf(totalTestMarks));
                            TIMEPASS.setText("Time   : " + "30 Minutes");
                            //endregion
                            //TODO
                            if (heroArray.length() > 0) {
                                for (int i = 0; i < heroArray.length(); i++) {
                                    JSONObject c = heroArray.getJSONObject(i);
                                    HashMap<String, String> MCQmap = new HashMap<>();
                                    //MCQmap.put("ques_no", c.getString(String.valueOf(i)));
                                    //MCQmap.put("ques_no", c.getString("ques_no"));
                                    MCQmap.put("ques", c.getString("ques"));
                                    MCQmap.put("a", c.getString("a"));
                                    MCQmap.put("b", c.getString("b"));
                                    MCQmap.put("c", c.getString("c"));
                                    MCQmap.put("d", c.getString("d"));
                                    MCQmap.put("sno", c.getString("sno"));
                                    MCQmap.put("ans", c.getString("ans"));
                                    MCQmap.put("count", c.getString("count"));
                                    MCQmap.put("MarkedANS", "");
                                    //MCQmap.put("Marks", "1");
                                    MCQmap.put("Marks", String.valueOf(marks_mcq));
                                    QuestiojnMCQ.add(MCQmap);

                                }
                                Log.d("mcq_response222", QuestiojnMCQ.toString());
                                MCQListCount = QuestiojnMCQ.size();
                                String Questiomn = QuestiojnMCQ.get(MCQNumber).get("ques");
                                marksMCQ.setText("Marks\t:\t" + marks_mcq);
                                QuestionnumberMCQ.setText(String.valueOf(QuestionSCount));
                                List<String> elephantList23 = Arrays.asList(Questiomn.split("-"));
                                int indexSize = elephantList23.size();
                                // Log.d("headheadimageurl", elephantList23.size()+"......."+elephantList23 + "========" + elephantList23.get(1)+"index0"+elephantList23.get(0));
                                Log.d("headheadimageurl", String.valueOf(elephantList23.size()));
                                if (indexSize == 1) {
                                    Questiomn.replace("-", "");
                                } else {
                                    elephantList = Arrays.asList(Questiomn.split("-"));
                                }
                                Log.d("headheadimageurl", Questiomn);
                                //Log.d("headheadimageurl", elephantList.get(0) + "========" + elephantList.get(1));
                                //String str = Questiomn.substring(Questiomn.length() - 3, Questiomn.length());
                                String str = Questiomn != null && Questiomn.length() > 3 ? Questiomn.substring(Questiomn.length() - 3, Questiomn.length()) : "";
                                Log.d("headstr", str);
                                if (str.equals("jpg") || str.equals("png")) {
                                    MQCQuestionImag.setVisibility(View.VISIBLE);
                                    MCQQuestion.setVisibility(View.GONE);
                                    ImageURL = "http://techive.in/studybuddy/question_img/" + elephantList.get(1);
                                    Glide.with(StudentTest.this).load(ImageURL)
                                            .crossFade()
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .into(MQCQuestionImag);
                                } else {
                                    MQCQuestionImag.setVisibility(View.GONE);
                                    MCQQuestion.setVisibility(View.VISIBLE);
                                    MCQQuestion.setText(Questiomn);

                                }
                                //region "Fill Options"
                                final String OptionA = QuestiojnMCQ.get(MCQNumber).get("a");
                                Log.d("OptionA", OptionA);

                                List<String> elephantList1 = Arrays.asList(OptionA.split("-"));
                                Log.d("elephantList1", String.valueOf(elephantList1.size()));
                                if (elephantList1.size() == 1) {
                                    String url = OptionA.replace("-", "");
                                    Log.d("13654063", "" + url);
                                    String str1 = url != null && url.length() > 3 ? url.substring(url.length() - 3, url.length()) : "";
                                    if (str1.equals("jpg") || str1.equals("png")) {
                                        Option_textA.setVisibility(View.GONE);
                                        Option_Aiamge.setVisibility(View.VISIBLE);
                                        OPImage1 = "http://techive.in/studybuddy/question_img/" + url;
                                        Log.d("136540", OPImage1);
                                        Glide.with(StudentTest.this).load(OPImage1)
                                                .crossFade()
                                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                .into(Option_Aiamge);
                                    } else {
                                        Option_textA.setVisibility(View.VISIBLE);
                                        Option_Aiamge.setVisibility(View.GONE);
                                        Option_textA.setText(OptionA);
                                    }
                                } else {

                                    elephantList1 = Arrays.asList(OptionA.split("-"));
                                    String str1 = OptionA != null && OptionA.length() > 3 ? OptionA.substring(OptionA.length() - 3, OptionA.length()) : "";
                                    Log.d("elephantList2", String.valueOf(elephantList1.size()));
                                    if (str1.equals("jpg") || str1.equals("png")) {
                                        Option_textA.setVisibility(View.GONE);
                                        Option_Aiamge.setVisibility(View.VISIBLE);
                                        OPImage1 = "http://techive.in/studybuddy/question_img/" + elephantList1.get(1);
                                        Glide.with(StudentTest.this).load(OPImage1)
                                                .crossFade()
                                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                .into(Option_Aiamge);
                                    } else {
                                        Option_textA.setVisibility(View.VISIBLE);
                                        Option_Aiamge.setVisibility(View.GONE);
                                        Option_textA.setText(OptionA);
                                    }
                                }
                                //Log.d("headheadimageurl", elephantList1.get(0) + "========" + elephantList1.get(1));

                                String OptionB = QuestiojnMCQ.get(MCQNumber).get("b");
                                Log.d("OptionB", "" + OptionB);
                                List<String> elephantList2 = Arrays.asList(OptionB.split("-"));
                                Log.d("elephantList2", String.valueOf(elephantList1.size()));
                                if (elephantList2.size() == 1) {
                                    String url2 = OptionB.replace("-", "");
                                    Log.d("13654063B", "" + url2);
                                    String str2 = OptionB != null && OptionB.length() > 3 ? OptionB.substring(OptionB.length() - 3, OptionB.length()) : "";
                                    if (str2.equals("jpg") || str2.equals("png")) {
                                        Option_textB.setVisibility(View.GONE);
                                        Option_Biamge.setVisibility(View.VISIBLE);
                                        OPImage2 = "http://techive.in/studybuddy/question_img/" + url2;
                                        Log.d("136540B", OPImage2);
                                        Glide.with(StudentTest.this).load(OPImage2)
                                                .crossFade()
                                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                .into(Option_Biamge);
                                    } else {
                                        Option_textB.setVisibility(View.VISIBLE);
                                        Option_Biamge.setVisibility(View.GONE);
                                        Option_textB.setText(OptionB);
                                    }
                                } else {

                                    Log.d("headheadimageurl", elephantList2.get(0) + "========" + elephantList2.get(1));
                                    String str2 = OptionB != null && OptionB.length() > 3 ? OptionB.substring(OptionB.length() - 3, OptionB.length()) : "";
                                    //String str2 = OptionB.substring(OptionB.length() - 3, OptionB.length());
                                    if (str2.equals("jpg") || str2.equals("png")) {
                                        Option_textB.setVisibility(View.GONE);
                                        Option_Biamge.setVisibility(View.VISIBLE);
                                        OPImage2 = "http://techive.in/studybuddy/question_img/" + elephantList2.get(1);
                                        Glide.with(StudentTest.this).load(OPImage2)
                                                .crossFade()
                                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                .into(Option_Biamge);
                                    } else {
                                        Option_textB.setText(OptionB);
                                        Option_textB.setVisibility(View.VISIBLE);
                                        Option_Biamge.setVisibility(View.GONE);
                                    }
                                }

                                String OptionC = QuestiojnMCQ.get(MCQNumber).get("c");
                                List<String> elephantList3 = Arrays.asList(OptionC.split("-"));
                                if (elephantList3.size() == 1) {
                                    String url3 = OptionC.replace("-", "");
                                    Log.d("13654063C", "" + url3);
                                    //String str3 = OptionC.substring(OptionC.length() - 3, OptionC.length());
                                    String str3 = OptionC != null && OptionC.length() > 3 ? OptionC.substring(OptionC.length() - 3, OptionC.length()) : "";
                                    if (str3.equals("jpg") || str3.equals("png")) {
                                        Option_textC.setVisibility(View.GONE);
                                        Option_Ciamge.setVisibility(View.VISIBLE);
                                        OPImage3 = "http://techive.in/studybuddy/question_img/" + url3;
                                        Log.d("136540B", OPImage3);
                                        Glide.with(StudentTest.this).load(OPImage3)
                                                .crossFade()
                                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                .into(Option_Ciamge);
                                    } else {
                                        Option_textC.setVisibility(View.VISIBLE);
                                        Option_Ciamge.setVisibility(View.GONE);
                                        Option_textC.setText(OptionC);
                                    }
                                } else {

                                    Log.d("headheadimageurl", elephantList3.get(0) + "========" + elephantList3.get(1));
                                    //String str3 = OptionC.substring(OptionC.length() - 3, OptionC.length());
                                    String str3 = OptionC != null && OptionC.length() > 3 ? OptionC.substring(OptionC.length() - 3, OptionC.length()) : "";
                                    if (str3.equals("jpg") || str3.equals("png")) {
                                        Option_textC.setVisibility(View.GONE);
                                        Option_Ciamge.setVisibility(View.VISIBLE);
                                        OPImage3 = "http://techive.in/studybuddy/question_img/" + elephantList3.get(1);
                                        Glide.with(StudentTest.this).load(OPImage3)
                                                .crossFade()
                                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                .into(Option_Ciamge);
                                    } else {
                                        Option_textC.setVisibility(View.VISIBLE);
                                        Option_Ciamge.setVisibility(View.GONE);
                                        Option_textC.setText(OptionC);

                                    }
                                }

                                String OptionD = QuestiojnMCQ.get(MCQNumber).get("d");
                                List<String> elephantList4 = Arrays.asList(OptionD.split("-"));
                                if (elephantList4.size() == 1) {
                                    String url4 = OptionD.replace("-", "");
                                    Log.d("13654063D", "" + url4);
                                    String str4 = OptionD != null && OptionD.length() > 3 ? OptionD.substring(OptionD.length() - 3, OptionD.length()) : "";
                                    //String str4 = OptionD.substring(OptionD.length() - 3, OptionD.length());
                                    if (str4.equals("jpg") || str4.equals("png")) {
                                        Option_textD.setVisibility(View.GONE);
                                        Option_Diamge.setVisibility(View.VISIBLE);
                                        OPImage4 = "http://techive.in/studybuddy/question_img/" + url4;
                                        Log.d("136540D", OPImage4);
                                        Glide.with(StudentTest.this).load(OPImage4)
                                                .crossFade()
                                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                .into(Option_Diamge);
                                    } else {
                                        Option_textD.setVisibility(View.VISIBLE);
                                        Option_Diamge.setVisibility(View.GONE);
                                        Option_textD.setText(OptionD);


                                    }
                                } else {

                                    Log.d("headheadimageurl", elephantList4.get(0) + "========" + elephantList4.get(1));
                                    //String str4 = OptionD.substring(OptionD.length() - 3, OptionD.length());
                                    String str4 = OptionD != null && OptionD.length() > 3 ? OptionD.substring(OptionD.length() - 3, OptionD.length()) : "";
                                    if (str4.equals("jpg") || str4.equals("png")) {
                                        Option_textD.setVisibility(View.GONE);
                                        Option_Diamge.setVisibility(View.VISIBLE);
                                        OPImage4 = "http://techive.in/studybuddy/question_img/" + elephantList4.get(1);
                                        Glide.with(StudentTest.this).load(OPImage4)
                                                .crossFade()
                                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                .into(Option_Diamge);
                                    } else {
                                        Option_textD.setVisibility(View.VISIBLE);
                                        Option_Diamge.setVisibility(View.GONE);
                                        Option_textD.setText(OptionD);

                                    }
                                }//endregion

                                //TODO : Work here
                                NextButtonMCQ.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (Option_A.isChecked()) {
                                            MCQUPcomingANS = "a";
                                        } else if (Option_B.isChecked()) {
                                            MCQUPcomingANS = "b";
                                        } else if (Option_C.isChecked()) {
                                            MCQUPcomingANS = "c";
                                        } else if (Option_D.isChecked()) {
                                            MCQUPcomingANS = "d";
                                        } else {
                                            MCQUPcomingANS = "N";
                                        }
                                        Log.d("MCQNumber", "" + String.valueOf(MCQNumber));
                                        QuestiojnMCQ.get(MCQNumber).put("MarkedANS", MCQUPcomingANS);
                                        if (QuestiojnMCQ != null && QuestiojnMCQ.size() > 0) {
                                            for (int i = 0; i < QuestiojnMCQ.size(); i++) {
                                                Log.d("getClickedValue", QuestiojnMCQ.get(i).get("MarkedANS"));
                                            }
                                        }
                                        Log.d("TotalArray", String.valueOf(QuestiojnMCQ));

                                        if (MCQListCount - 1 == MCQNumber) {
                                            //region "Visible Invisible"
                                            if (tf_size > 0) {
                                                MQCTestLayout.setVisibility(View.GONE);
                                                TureFasle.setVisibility(View.VISIBLE);
                                                FillInTheBlancks.setVisibility(View.GONE);
                                                layoutMColumn.setVisibility(View.GONE);
                                            } else if (tf_size == 0 && fill_in_blank_size > 0) {
                                                MQCTestLayout.setVisibility(View.GONE);
                                                TureFasle.setVisibility(View.GONE);
                                                FillInTheBlancks.setVisibility(View.VISIBLE);
                                                layoutMColumn.setVisibility(View.GONE);
                                            } else if (tf_size == 0 && fill_in_blank_size == 0 && match_col_size > 0) {
                                                MQCTestLayout.setVisibility(View.GONE);
                                                TureFasle.setVisibility(View.GONE);
                                                FillInTheBlancks.setVisibility(View.GONE);
                                                layoutMColumn.setVisibility(View.VISIBLE);
                                            }//TODO : Note : showTotalMarks when only mcq is filled
                                            else if (tf_size == 0 && fill_in_blank_size == 0 && match_col_size == 0) {
                                                showTotalMarks();
                                            }//endregion
                                        } else {
                                            QuestionSCount = QuestionSCount + 1;
                                            MCQNumber = MCQNumber + 1;
                                            main_question_count = main_question_count + 1;
                                            //String value = MCQUPcomingANS;

                                            /*Option_A.setChecked(false);
                                            Option_B.setChecked(false);
                                            Option_C.setChecked(false);
                                            Option_D.setChecked(false);*/
                                            /**/
                                            String MarkerANS = (QuestiojnMCQ != null && QuestiojnMCQ.size() > MCQNumber) ? QuestiojnMCQ.get(MCQNumber).get("MarkedANS") : "";
                                            Log.d("MarkerANS12", "\t" + MarkerANS);
                                            if (MarkerANS.equals("a")) {
                                                Option_A.setChecked(true);
                                                Option_B.setChecked(false);
                                                Option_C.setChecked(false);
                                                Option_D.setChecked(false);
                                            } else if (MarkerANS.equals("b")) {
                                                Option_A.setChecked(false);
                                                Option_B.setChecked(true);
                                                Option_C.setChecked(false);
                                                Option_D.setChecked(false);
                                            } else if (MarkerANS.equals("c")) {
                                                Option_A.setChecked(false);
                                                Option_B.setChecked(false);
                                                Option_C.setChecked(true);
                                                Option_D.setChecked(false);
                                            } else if (MarkerANS.equals("d")) {
                                                Option_A.setChecked(false);
                                                Option_B.setChecked(false);
                                                Option_C.setChecked(false);
                                                Option_D.setChecked(true);
                                            } else {
                                                Option_A.setChecked(false);
                                                Option_B.setChecked(false);
                                                Option_C.setChecked(false);
                                                Option_D.setChecked(false);
                                            }
                                            /**/

                                            //region "Set Question and Options"
                                            //QuestionnumberMCQ.setText(String.valueOf(QuestionSCount));
                                            //QuestionnumberMCQ.setText(String.valueOf(main_question_count));
                                            //QuestionnumberMCQ.setText(String.valueOf(QuestiojnMCQ.get(MCQNumber).get("sno")));
                                            QuestionnumberMCQ.setText(String.valueOf(QuestiojnMCQ.get(MCQNumber).get("count")));
                                            String Questiomn = QuestiojnMCQ.get(MCQNumber).get("ques");
                                            List<String> elephantList23 = Arrays.asList(Questiomn.split("-"));
                                            int indexSize = elephantList23.size();
                                            // Log.d("headheadimageurl", elephantList23.size()+"......."+elephantList23 + "========" + elephantList23.get(1)+"index0"+elephantList23.get(0));
                                            Log.d("headheadimageurl", String.valueOf(elephantList23.size()));
                                            if (indexSize == 1) {
                                                Questiomn.replace("-", "");
                                            } else {
                                                elephantList = Arrays.asList(Questiomn.split("-"));
                                            }
                                            Log.d("headheadimageurl", Questiomn);
                                            String str = Questiomn.substring(Questiomn.length() - 3, Questiomn.length());
                                            Log.d("headstr", str);
                                            if (str.equals("jpg") || str.equals("png")) {
                                                MQCQuestionImag.setVisibility(View.VISIBLE);
                                                MCQQuestion.setVisibility(View.GONE);
                                                ImageURL = "http://techive.in/studybuddy/question_img/" + elephantList.get(1);
                                                Glide.with(StudentTest.this).load(ImageURL)
                                                        .crossFade()
                                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                        .into(MQCQuestionImag);
                                            } else {
                                                MQCQuestionImag.setVisibility(View.GONE);
                                                MCQQuestion.setVisibility(View.VISIBLE);
                                                MCQQuestion.setText(Questiomn);

                                            }
                                            String OptionA = QuestiojnMCQ.get(MCQNumber).get("a");
                                            Log.d("OptionA", OptionA);
                                            List<String> elephantList1 = Arrays.asList(OptionA.split("-"));
                                            Log.d("elephantList1", String.valueOf(elephantList1.size()));
                                            if (elephantList1.size() == 1) {
                                                String url = OptionA.replace("-", "");
                                                Log.d("13654063", url);
                                                //String str1 = url.substring(url.length() - 3, url.length());
                                                String str1 = url != null && url.length() > 3 ? url.substring(url.length() - 3, url.length()) : "";
                                                if (str1.equals("jpg") || str1.equals("png")) {
                                                    Option_textA.setVisibility(View.GONE);
                                                    Option_Aiamge.setVisibility(View.VISIBLE);
                                                    OPImage1 = "http://techive.in/studybuddy/question_img/" + url;
                                                    Log.d("136540", OPImage1);
                                                    Glide.with(StudentTest.this).load(OPImage1)
                                                            .crossFade()
                                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                            .into(Option_Aiamge);
                                                } else {
                                                    Option_textA.setVisibility(View.VISIBLE);
                                                    Option_Aiamge.setVisibility(View.GONE);
                                                    Option_textA.setText(OptionA);
                                                }
                                            } else {

                                                elephantList1 = Arrays.asList(OptionA.split("-"));
                                                //String str1 = OptionA.substring(OptionA.length() - 3, OptionA.length());
                                                String str1 = OptionA != null && OptionA.length() > 3 ? OptionA.substring(OptionA.length() - 3, OptionA.length()) : "";
                                                Log.d("elephantList2", String.valueOf(elephantList1.size()));
                                                if (str1.equals("jpg") || str1.equals("png")) {
                                                    Option_textA.setVisibility(View.GONE);
                                                    Option_Aiamge.setVisibility(View.VISIBLE);
                                                    OPImage1 = "http://techive.in/studybuddy/question_img/" + elephantList1.get(1);
                                                    Glide.with(StudentTest.this).load(OPImage1)
                                                            .crossFade()
                                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                            .into(Option_Aiamge);
                                                } else {
                                                    Option_textA.setVisibility(View.VISIBLE);
                                                    Option_Aiamge.setVisibility(View.GONE);
                                                    Option_textA.setText(OptionA);
                                                }
                                            }
                                            String OptionB = QuestiojnMCQ.get(MCQNumber).get("b");
                                            Log.d("OptionB", OptionB);
                                            List<String> elephantList2 = Arrays.asList(OptionB.split("-"));
                                            Log.d("elephantList2", String.valueOf(elephantList1.size()));
                                            if (elephantList2.size() == 1) {
                                                String url2 = OptionB.replace("-", "");
                                                Log.d("13654063B", url2);
                                                //String str2 = OptionB.substring(OptionB.length() - 3, OptionB.length());
                                                String str2 = OptionB != null && OptionB.length() > 3 ? OptionB.substring(OptionB.length() - 3, OptionB.length()) : "";
                                                if (str2.equals("jpg") || str2.equals("png")) {
                                                    Option_textB.setVisibility(View.GONE);
                                                    Option_Biamge.setVisibility(View.VISIBLE);
                                                    OPImage2 = "http://techive.in/studybuddy/question_img/" + url2;
                                                    Log.d("136540B", OPImage2);
                                                    Glide.with(StudentTest.this).load(OPImage2)
                                                            .crossFade()
                                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                            .into(Option_Biamge);
                                                } else {
                                                    Option_textB.setVisibility(View.VISIBLE);
                                                    Option_Biamge.setVisibility(View.GONE);
                                                    Option_textB.setText(OptionB);
                                                }
                                            } else {
                                                Log.d("headheadimageurl", elephantList2.get(0) + "========" + elephantList2.get(1));
                                                String str2 = OptionB != null && OptionB.length() > 3 ? OptionB.substring(OptionB.length() - 3, OptionB.length()) : "";
                                                if (str2.equals("jpg") || str2.equals("png")) {
                                                    Option_textB.setVisibility(View.GONE);
                                                    Option_Biamge.setVisibility(View.VISIBLE);
                                                    OPImage2 = "http://techive.in/studybuddy/question_img/" + elephantList2.get(1);
                                                    Glide.with(StudentTest.this).load(OPImage2)
                                                            .crossFade()
                                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                            .into(Option_Biamge);
                                                } else {
                                                    Option_textB.setText(OptionB);
                                                    Option_textB.setVisibility(View.VISIBLE);
                                                    Option_Biamge.setVisibility(View.GONE);
                                                }
                                            }
                                            String OptionC = QuestiojnMCQ.get(MCQNumber).get("c");
                                            List<String> elephantList3 = Arrays.asList(OptionC.split("-"));
                                            if (elephantList3.size() == 1) {
                                                String url3 = OptionC.replace("-", "");
                                                Log.d("13654063C", url3);
                                                //String str3 = OptionC.substring(OptionC.length() - 3, OptionC.length());
                                                String str3 = OptionC != null && OptionC.length() > 3 ? OptionC.substring(OptionC.length() - 3, OptionC.length()) : "";
                                                if (str3.equals("jpg") || str3.equals("png")) {
                                                    Option_textC.setVisibility(View.GONE);
                                                    Option_Ciamge.setVisibility(View.VISIBLE);
                                                    OPImage3 = "http://techive.in/studybuddy/question_img/" + url3;
                                                    Log.d("136540B", OPImage3);
                                                    Glide.with(StudentTest.this).load(OPImage3)
                                                            .crossFade()
                                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                            .into(Option_Ciamge);
                                                } else {
                                                    Option_textC.setVisibility(View.VISIBLE);
                                                    Option_Ciamge.setVisibility(View.GONE);
                                                    Option_textC.setText(OptionC);
                                                }
                                            } else {
                                                Log.d("headheadimageurl", elephantList3.get(0) + "========" + elephantList3.get(1));
                                                String str3 = OptionC != null && OptionC.length() > 3 ? OptionC.substring(OptionC.length() - 3, OptionC.length()) : "";
                                                if (str3.equals("jpg") || str3.equals("png")) {
                                                    Option_textC.setVisibility(View.GONE);
                                                    Option_Ciamge.setVisibility(View.VISIBLE);
                                                    OPImage3 = "http://techive.in/studybuddy/question_img/" + elephantList3.get(1);
                                                    Glide.with(StudentTest.this).load(OPImage3)
                                                            .crossFade()
                                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                            .into(Option_Ciamge);
                                                } else {
                                                    Option_textC.setVisibility(View.VISIBLE);
                                                    Option_Ciamge.setVisibility(View.GONE);
                                                    Option_textC.setText(OptionC);

                                                }
                                            }
                                            String OptionD = QuestiojnMCQ.get(MCQNumber).get("d");
                                            List<String> elephantList4 = Arrays.asList(OptionD.split("-"));
                                            if (elephantList4.size() == 1) {
                                                String url4 = OptionD.replace("-", "");
                                                Log.d("13654063D", url4);
                                                String str4 = OptionD != null && OptionD.length() > 3 ? OptionD.substring(OptionD.length() - 3, OptionD.length()) : "";
                                                if (str4.equals("jpg") || str4.equals("png")) {
                                                    Option_textD.setVisibility(View.GONE);
                                                    Option_Diamge.setVisibility(View.VISIBLE);
                                                    OPImage4 = "http://techive.in/studybuddy/question_img/" + url4;
                                                    Log.d("136540D", OPImage4);
                                                    Glide.with(StudentTest.this).load(OPImage4)
                                                            .crossFade()
                                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                            .into(Option_Diamge);
                                                } else {
                                                    Option_textD.setVisibility(View.VISIBLE);
                                                    Option_Diamge.setVisibility(View.GONE);
                                                    Option_textD.setText(OptionD);
                                                }
                                            } else {
                                                Log.d("headheadimageurl", elephantList4.get(0) + "========" + elephantList4.get(1));
                                                String str4 = OptionD != null && OptionD.length() > 3 ? OptionD.substring(OptionD.length() - 3, OptionD.length()) : "";
                                                if (str4.equals("jpg") || str4.equals("png")) {
                                                    Option_textD.setVisibility(View.GONE);
                                                    Option_Diamge.setVisibility(View.VISIBLE);
                                                    OPImage4 = "http://techive.in/studybuddy/question_img/" + elephantList4.get(1);
                                                    Glide.with(StudentTest.this).load(OPImage4)
                                                            .crossFade()
                                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                            .into(Option_Diamge);
                                                } else {
                                                    Option_textD.setVisibility(View.VISIBLE);
                                                    Option_Diamge.setVisibility(View.GONE);
                                                    Option_textD.setText(OptionD);

                                                }
                                            }//endregion
                                        }
                                    }
                                });
                                backButtonMCQ.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d("MCQNumber", String.valueOf(MCQNumber));

                                        if (MCQNumber == 0) {
                                            MQCTestLayout.setVisibility(View.VISIBLE);
                                            TureFasle.setVisibility(View.GONE);
                                        } else {
                                            Log.d("MCQNumber111", "" + MCQNumber);
                                            main_question_count = main_question_count - 1;

                                            MCQNumber = MCQNumber - 1;
                                            //QuestionnumberMCQ.setText(String.valueOf(QuestiojnMCQ.get(MCQNumber).get("sno")));
                                            QuestionnumberMCQ.setText(String.valueOf(QuestiojnMCQ.get(MCQNumber).get("count")));
                                            String MarkerANS = QuestiojnMCQ.get(MCQNumber).get("MarkedANS");
                                            for (int i = 0; i < QuestiojnMCQ.size(); i++) {
                                                Log.d("getClickedValue", QuestiojnMCQ.get(i).get("MarkedANS"));
                                            }
                                            Log.d("MarkerANS12", "1234545" + MarkerANS);
                                            if (MarkerANS.equals("a")) {
                                                Option_A.setChecked(true);
                                                Option_B.setChecked(false);
                                                Option_C.setChecked(false);
                                                Option_D.setChecked(false);
                                            } else if (MarkerANS.equals("b")) {
                                                Option_A.setChecked(false);
                                                Option_B.setChecked(true);
                                                Option_C.setChecked(false);
                                                Option_D.setChecked(false);
                                            } else if (MarkerANS.equals("c")) {
                                                Option_A.setChecked(false);
                                                Option_B.setChecked(false);
                                                Option_C.setChecked(true);
                                                Option_D.setChecked(false);
                                            } else if (MarkerANS.equals("d")) {
                                                Option_A.setChecked(false);
                                                Option_B.setChecked(false);
                                                Option_C.setChecked(false);
                                                Option_D.setChecked(true);
                                            } else {
                                                Option_A.setChecked(false);
                                                Option_B.setChecked(false);
                                                Option_C.setChecked(false);
                                                Option_D.setChecked(false);
                                            }

                                            //region "Set Question and Options"
                                            String Questiomn = QuestiojnMCQ.get(MCQNumber).get("ques");
                                            List<String> elephantList23 = Arrays.asList(Questiomn.split("-"));
                                            int indexSize = elephantList23.size();
                                            // Log.d("headheadimageurl", elephantList23.size()+"......."+elephantList23 + "========" + elephantList23.get(1)+"index0"+elephantList23.get(0));
                                            Log.d("headheadimageurl", String.valueOf(elephantList23.size()));
                                            if (indexSize == 1) {
                                                Questiomn.replace("-", "");
                                            } else {
                                                elephantList = Arrays.asList(Questiomn.split("-"));
                                            }
                                            Log.d("headheadimageurl", Questiomn);
                                            String str = Questiomn != null && Questiomn.length() > 3 ? Questiomn.substring(Questiomn.length() - 3, Questiomn.length()) : "";
                                            Log.d("headstr", str);
                                            if (str.equals("jpg") || str.equals("png")) {
                                                MQCQuestionImag.setVisibility(View.VISIBLE);
                                                MCQQuestion.setVisibility(View.GONE);
                                                ImageURL = "http://techive.in/studybuddy/question_img/" + elephantList.get(1);
                                                Glide.with(StudentTest.this).load(ImageURL)
                                                        .crossFade()
                                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                        .into(MQCQuestionImag);
                                            } else {
                                                MQCQuestionImag.setVisibility(View.GONE);
                                                MCQQuestion.setVisibility(View.VISIBLE);
                                                MCQQuestion.setText(Questiomn);

                                            }
                                            String OptionA = QuestiojnMCQ.get(MCQNumber).get("a");
                                            Log.d("OptionA", OptionA);
                                            List<String> elephantList1 = Arrays.asList(OptionA.split("-"));
                                            Log.d("elephantList1", String.valueOf(elephantList1.size()));
                                            if (elephantList1.size() == 1) {
                                                String url = OptionA.replace("-", "");
                                                Log.d("13654063", url);
                                                String str1 = url != null && url.length() > 3 ? url.substring(url.length() - 3, url.length()) : "";
                                                if (str1.equals("jpg") || str1.equals("png")) {
                                                    Option_textA.setVisibility(View.GONE);
                                                    Option_Aiamge.setVisibility(View.VISIBLE);
                                                    OPImage1 = "http://techive.in/studybuddy/question_img/" + url;
                                                    Log.d("136540", OPImage1);
                                                    Glide.with(StudentTest.this).load(OPImage1)
                                                            .crossFade()
                                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                            .into(Option_Aiamge);
                                                } else {
                                                    Option_textA.setVisibility(View.VISIBLE);
                                                    Option_Aiamge.setVisibility(View.GONE);
                                                    Option_textA.setText(OptionA);
                                                }
                                            } else {
                                                elephantList1 = Arrays.asList(OptionA.split("-"));
                                                String str1 = OptionA != null && OptionA.length() > 3 ? OptionA.substring(OptionA.length() - 3, OptionA.length()) : "";
                                                Log.d("elephantList2", String.valueOf(elephantList1.size()));
                                                if (str1.equals("jpg") || str1.equals("png")) {
                                                    Option_textA.setVisibility(View.GONE);
                                                    Option_Aiamge.setVisibility(View.VISIBLE);
                                                    OPImage1 = "http://techive.in/studybuddy/question_img/" + elephantList1.get(1);
                                                    Glide.with(StudentTest.this).load(OPImage1)
                                                            .crossFade()
                                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                            .into(Option_Aiamge);
                                                } else {
                                                    Option_textA.setVisibility(View.VISIBLE);
                                                    Option_Aiamge.setVisibility(View.GONE);
                                                    Option_textA.setText(OptionA);

                                                }
                                            }
                                            String OptionB = QuestiojnMCQ.get(MCQNumber).get("b");
                                            Log.d("OptionB", OptionB);
                                            List<String> elephantList2 = Arrays.asList(OptionB.split("-"));
                                            Log.d("elephantList2", String.valueOf(elephantList1.size()));
                                            if (elephantList2.size() == 1) {
                                                String url2 = OptionB.replace("-", "");
                                                Log.d("13654063B", "" + url2);
                                                String str2 = OptionB != null && OptionB.length() > 3 ? OptionB.substring(OptionB.length() - 3, OptionB.length()) : "";
                                                if (str2.equals("jpg") || str2.equals("png")) {
                                                    Option_textB.setVisibility(View.GONE);
                                                    Option_Biamge.setVisibility(View.VISIBLE);
                                                    OPImage2 = "http://techive.in/studybuddy/question_img/" + url2;
                                                    Log.d("136540B", OPImage2);
                                                    Glide.with(StudentTest.this).load(OPImage2)
                                                            .crossFade()
                                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                            .into(Option_Biamge);
                                                } else {
                                                    Option_textB.setVisibility(View.VISIBLE);
                                                    Option_Biamge.setVisibility(View.GONE);
                                                    Option_textB.setText(OptionB);
                                                }
                                            } else {
                                                Log.d("headheadimageurl", elephantList2.get(0) + "========" + elephantList2.get(1));
                                                String str2 = OptionB != null && OptionB.length() > 3 ? OptionB.substring(OptionB.length() - 3, OptionB.length()) : "";
                                                if (str2.equals("jpg") || str2.equals("png")) {
                                                    Option_textB.setVisibility(View.GONE);
                                                    Option_Biamge.setVisibility(View.VISIBLE);
                                                    OPImage2 = "http://techive.in/studybuddy/question_img/" + elephantList2.get(1);
                                                    Glide.with(StudentTest.this).load(OPImage2)
                                                            .crossFade()
                                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                            .into(Option_Biamge);
                                                } else {
                                                    Option_textB.setText(OptionB);
                                                    Option_textB.setVisibility(View.VISIBLE);
                                                    Option_Biamge.setVisibility(View.GONE);
                                                }
                                            }
                                            String OptionC = QuestiojnMCQ.get(MCQNumber).get("c");
                                            List<String> elephantList3 = Arrays.asList(OptionC.split("-"));
                                            if (elephantList3.size() == 1) {
                                                String url3 = OptionC.replace("-", "");
                                                Log.d("13654063C", "" + url3);
                                                String str3 = OptionC != null && OptionC.length() > 3 ? OptionC.substring(OptionC.length() - 3, OptionC.length()) : "";
                                                if (str3.equals("jpg") || str3.equals("png")) {
                                                    Option_textC.setVisibility(View.GONE);
                                                    Option_Ciamge.setVisibility(View.VISIBLE);
                                                    OPImage3 = "http://techive.in/studybuddy/question_img/" + url3;
                                                    Log.d("136540B", OPImage3);
                                                    Glide.with(StudentTest.this).load(OPImage3)
                                                            .crossFade()
                                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                            .into(Option_Ciamge);
                                                } else {
                                                    Option_textC.setVisibility(View.VISIBLE);
                                                    Option_Ciamge.setVisibility(View.GONE);
                                                    Option_textC.setText(OptionC);
                                                }
                                            } else {
                                                Log.d("headheadimageurl", elephantList3.get(0) + "========" + elephantList3.get(1));
                                                String str3 = OptionC != null && OptionC.length() > 3 ? OptionC.substring(OptionC.length() - 3, OptionC.length()) : "";
                                                if (str3.equals("jpg") || str3.equals("png")) {
                                                    Option_textC.setVisibility(View.GONE);
                                                    Option_Ciamge.setVisibility(View.VISIBLE);
                                                    OPImage3 = "http://techive.in/studybuddy/question_img/" + elephantList3.get(1);
                                                    Glide.with(StudentTest.this).load(OPImage3)
                                                            .crossFade()
                                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                            .into(Option_Ciamge);
                                                } else {
                                                    Option_textC.setVisibility(View.VISIBLE);
                                                    Option_Ciamge.setVisibility(View.GONE);
                                                    Option_textC.setText(OptionC);
                                                }
                                            }
                                            String OptionD = QuestiojnMCQ.get(MCQNumber).get("d");
                                            List<String> elephantList4 = Arrays.asList(OptionD.split("-"));
                                            if (elephantList4.size() == 1) {
                                                String url4 = OptionD.replace("-", "");
                                                Log.d("13654063D", url4);
                                                String str4 = OptionD != null && OptionD.length() > 3 ? OptionD.substring(OptionD.length() - 3, OptionD.length()) : "";
                                                if (str4.equals("jpg") || str4.equals("png")) {
                                                    Option_textD.setVisibility(View.GONE);
                                                    Option_Diamge.setVisibility(View.VISIBLE);
                                                    OPImage4 = "http://techive.in/studybuddy/question_img/" + url4;
                                                    Log.d("136540D", OPImage4);
                                                    Glide.with(StudentTest.this).load(OPImage4)
                                                            .crossFade()
                                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                            .into(Option_Diamge);
                                                } else {
                                                    Option_textD.setVisibility(View.VISIBLE);
                                                    Option_Diamge.setVisibility(View.GONE);
                                                    Option_textD.setText(OptionD);
                                                }
                                            } else {
                                                Log.d("headheadimageurl", elephantList4.get(0) + "========" + elephantList4.get(1));
                                                String str4 = OptionD != null && OptionD.length() > 3 ? OptionD.substring(OptionD.length() - 3, OptionD.length()) : "";
                                                if (str4.equals("jpg") || str4.equals("png")) {
                                                    Option_textD.setVisibility(View.GONE);
                                                    Option_Diamge.setVisibility(View.VISIBLE);
                                                    OPImage4 = "http://techive.in/studybuddy/question_img/" + elephantList4.get(1);
                                                    Glide.with(StudentTest.this).load(OPImage4)
                                                            .crossFade()
                                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                            .into(Option_Diamge);
                                                } else {
                                                    Option_textD.setVisibility(View.VISIBLE);
                                                    Option_Diamge.setVisibility(View.GONE);
                                                    Option_textD.setText(OptionD);
                                                }
                                            }//endregion
                                        }
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            Log.d("excep_response", "" + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                System.out.println("ResponseRegistration" + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("accesscodes", "0300081516");
                params.put("ch_id", ChapterID);
                Log.d("getParamsRandom", params.toString());
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


        //TODO : ///////////////////////////////////............2nd True False Starts.............//////////////////////////////////////////////////////////////////////

        TFQuestion = (TextView) findViewById(R.id.TFQuestion);
        rg1 = (RadioGroup) findViewById(R.id.rg1);
        MarksTF = (TextView) findViewById(R.id.MarksTF);
        final TextView TFQuestionNo = (TextView) findViewById(R.id.TFQuestionNo);
        RequestQueue queue3 = Volley.newRequestQueue(StudentTest.this);
        //String url3 = "http://www.techive.in/studybuddy/api/random_question.php";
        String url3 = Apis.base_url + Apis.random_question_url;
        StringRequest stringRequest3 = new StringRequest(Request.Method.POST, url3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TrueFalse", response);
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            JSONArray heroArray = jsonObject1.getJSONArray("true_false");
                            JSONArray TrueFalseArray = jsonObject1.getJSONArray("true_false");
                            //TODO
                            if (TrueFalseArray.length() > 0) {
                                Log.d("TrueFalseArray", String.valueOf(heroArray));
                                for (int j = 0; j < TrueFalseArray.length(); j++) {
                                    JSONObject c = TrueFalseArray.getJSONObject(j);
                                    Log.d("126644565", c.getString("ques") + "  Size " + TrueFalseArray.length());
                                    HashMap<String, String> TrueFalsemap = new HashMap<>();

                                    //TrueFalsemap.put("ques_no", c.getString(String.valueOf(QuestiojnMCQ.size() + j)));
                                    TrueFalsemap.put("ques_no", c.getString("ques_no"));
                                    TrueFalsemap.put("ques", c.getString("ques"));
                                    TrueFalsemap.put("ans", c.getString("ans"));
                                    TrueFalsemap.put("sno", c.getString("sno"));
                                    TrueFalsemap.put("count", c.getString("count"));
                                    TrueFalsemap.put("Marks", String.valueOf(marks_tf));
                                    TrueFalsemap.put("MarkedANS", "");
                                    TUREFALSE.add(TrueFalsemap);


                                }
                                Log.d("tfvalue", "" + TUREFALSE.toString());
                                String Question = TUREFALSE.get(TF).get("ques");
                                TRUEFalseNumber = TUREFALSE.size();
                                TFQuestion.setText(TUREFALSE.get(TF).get("ques"));
                                TrQuestionNumber = MCQListCount + TrQuestionNumber;

                                //TFQuestionNo.setText(String.valueOf(main_question_count));
                                //TFQuestionNo.setText(String.valueOf(TUREFALSE.get(TF).get("sno")));
                                TFQuestionNo.setText(String.valueOf(TUREFALSE.get(TF).get("count")));
                                Log.d("TrQuestionSCount", String.valueOf(TrQuestionNumber + "MCQ" + MCQListCount));
                                MarksTF.setText("Marks\t:\t" + marks_tf);
                                //region "NextButtonTruefalse"
                                NextButtonTruefalse.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        int selectedRadioButtonID1 = rg1.getCheckedRadioButtonId();
                                        if (selectedRadioButtonID1 != -1) {
                                            RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonID1);
                                            String selectedRadioButtonText1 = selectedRadioButton.getText().toString();
                                            try {
                                                if (selectedRadioButtonText1.equals("True")) {
                                                    TUREFALSE.get(TF).put("MarkedANS", "1");
                                                } else {
                                                    TUREFALSE.get(TF).put("MarkedANS", "0");
                                                }
                                            } catch (ArrayIndexOutOfBoundsException Ar) {
                                            }
                                        } else {
                                            TUREFALSE.get(TF).put("MarkedANS", "N");
                                        }
                                        try {
                                            if (TF < TUREFALSE.size()) {
                                                TF = TF + 1;
                                                Quecount = Quecount + 1;
                                            }
                                            try {
                                                if (TF == TUREFALSE.size()) {
                                                    //if (TF == TRUEFalseNumber - 1) {
                                                    objective.setVisibility(View.GONE);
                                                    TureFasle.setVisibility(View.GONE);
                                                    if (fill_in_blank_size > 0) {
                                                        FillInTheBlancks.setVisibility(View.VISIBLE);
                                                    } else if (match_col_size > 0) {
                                                        layoutMColumn.setVisibility(View.VISIBLE);
                                                    }
                                                    //TODO : Note : showTotalMarks when only mcq is filled
                                                    if (fill_in_blank_size == 0 && match_col_size == 0) {
                                                        TureFasle.setVisibility(View.VISIBLE);
                                                        showTotalMarks();
                                                    }
                                                    //TODO
                                                    TF = TF - 1;
                                                    Quecount = Quecount - 1;
                                                } else if (TF < TUREFALSE.size()) {
                                                    rg1.clearCheck();
                                                    String MarkedAns = TUREFALSE.get(TF).get("MarkedANS");
                                                    Log.d("MarkedAnsTF", MarkedAns);
                                                    if (MarkedAns.equals("1")) {
                                                        TRUEBton.setChecked(true);
                                                        FALSEBTN.setChecked(false);
                                                    } else if (MarkedAns.equals("0")) {
                                                        TRUEBton.setChecked(false);
                                                        FALSEBTN.setChecked(true);
                                                    } else {
                                                        TRUEBton.setChecked(false);
                                                        FALSEBTN.setChecked(false);
                                                    }

                                                    TrQuestionNumber = MCQListCount + TrQuestionNumber + 1;
                                                    Log.d("Countt12", String.valueOf(TrQuestionNumber + "MCQ" + MCQListCount));
                                                    main_question_count = main_question_count + 1;
                                                    //TFQuestionNo.setText(String.valueOf(main_question_count));
                                                    //TFQuestionNo.setText(String.valueOf(TUREFALSE.get(TF).get("sno")));
                                                    TFQuestionNo.setText(String.valueOf(TUREFALSE.get(TF).get("count")));
                                                    TFQuestion.setText(TUREFALSE.get(TF).get("ques"));
                                                    // rg1.clearCheck();

                                                    if (NextTrueMarked_Answe.isEmpty()) {

                                                        TRUEBton.setSelected(false);
                                                        FALSEBTN.setSelected(false);
                                                        ((RadioButton) rg1.getChildAt(0)).setChecked(false);
                                                        ((RadioButton) rg1.getChildAt(1)).setChecked(false);

                                                    } else {
                                                        if (NextTrueMarked_Answe.equals("True")) {
                                                            TRUEBton.setSelected(true);
                                                            ((RadioButton) rg1.getChildAt(0)).setChecked(true);
                                                        } else if (NextTrueMarked_Answe.equals("False")) {
                                                            FALSEBTN.setSelected(false);
                                                            ((RadioButton) rg1.getChildAt(1)).setChecked(true);
                                                        }
                                                    }
                                                }
                                            } catch (NullPointerException Nu) {
                                            }
                                        } catch (ArrayIndexOutOfBoundsException ex) {
                                            Log.d("ex", String.valueOf(ex));
                                        }
                                        Log.d("TFValue", "" + TF);
                                    }
                                });//endregion
                                //region "BACKButtonTruefalse"
                                //chapterList.add(PateintDATA);
                                BACKButtonTruefalse.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        int selectedRadioButtonID1 = rg1.getCheckedRadioButtonId();

                                        if (selectedRadioButtonID1 != -1) {
                                            RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonID1);
                                            String selectedRadioButtonText1 = selectedRadioButton.getText().toString();
                                        }
                                        try {
                                            TF = TF - 1;
                                            Log.d("Countttt", String.valueOf("Minus  " + TF));
                                            Quecount = Quecount - 1;

                                            if (TrQuestionNumber > 1) {
                                                TrQuestionNumber = MCQListCount + TrQuestionNumber - 1;
                                            }
                                            Log.d("Countt12", String.valueOf(TrQuestionNumber + "MCQ" + MCQListCount));
                                            MarksTF.setText("Marks\t:\t" + marks_tf);
                                            if (TF < 0) {
                                                MQCTestLayout.setVisibility(View.VISIBLE);
                                                objective.setVisibility(View.GONE);
                                                TureFasle.setVisibility(View.GONE);
                                                FillInTheBlancks.setVisibility(View.GONE);

                                                if (QuestiojnMCQ != null && QuestiojnMCQ.size() == 0) {
                                                    MQCTestLayout.setVisibility(View.GONE);
                                                    TureFasle.setVisibility(View.VISIBLE);
                                                } else {
                                                    MQCTestLayout.setVisibility(View.VISIBLE);
                                                    TureFasle.setVisibility(View.GONE);
                                                }
                                                TF = TF + 1;
                                                Quecount = Quecount + 1;

                                            } else {
                                                MarksTF.setText("Marks\t:\t" + marks_tf);
                                                TFQuestion.setText(TUREFALSE.get(TF).get("ques"));
                                                main_question_count = main_question_count - 1;
                                                //TFQuestionNo.setText(String.valueOf(main_question_count));
                                                //TFQuestionNo.setText(String.valueOf(TUREFALSE.get(TF).get("sno")));
                                                TFQuestionNo.setText(String.valueOf(TUREFALSE.get(TF).get("count")));
                                                String MarkedAns = TUREFALSE.get(TF).get("MarkedANS");
                                                Log.d("MarkedAnsTF", MarkedAns);
                                                if (MarkedAns.equals("1")) {
                                                    TRUEBton.setChecked(true);
                                                    FALSEBTN.setChecked(false);
                                                } else if (MarkedAns.equals("0")) {
                                                    TRUEBton.setChecked(false);
                                                    FALSEBTN.setChecked(true);
                                                } else {
                                                    TRUEBton.setChecked(false);
                                                    FALSEBTN.setChecked(false);
                                                }
                                                try {
                                                    // NextTrueMarked_Answe = TUREFALSE.get(TF).get("Marked_Answers");
                                                    if (NextTrueMarked_Answe.isEmpty()) {

                                                        TRUEBton.setSelected(false);
                                                        FALSEBTN.setSelected(false);
                                                        ((RadioButton) rg1.getChildAt(0)).setChecked(false);
                                                        ((RadioButton) rg1.getChildAt(1)).setChecked(false);

                                                    } else {
                                                        if (NextTrueMarked_Answe.equals("True")) {
                                                            TRUEBton.setSelected(true);
                                                            ((RadioButton) rg1.getChildAt(0)).setChecked(true);
                                                        } else if (NextTrueMarked_Answe.equals("False")) {
                                                            FALSEBTN.setSelected(false);
                                                            ((RadioButton) rg1.getChildAt(1)).setChecked(false);
                                                        }
                                                    }

                                                } catch (NullPointerException Nu) {

                                                }
                                            }
                                        } catch (ArrayIndexOutOfBoundsException ex) {
                                            Log.d("ex", String.valueOf(ex));
                                        }
                                        Log.d("TFValue", "" + TF);
                                    }
                                });//endregion
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                System.out.println("ResponseRegistration" + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("accesscodes", "0300081516");
                params.put("ch_id", ChapterID);

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue3.add(stringRequest3);


        //todo...........Fill In The Blacks...........//////////////////////////////////////////////////////////////

        FBQuestion = (TextView) findViewById(R.id.FBQuestion);
        FBAnswerBox = findViewById(R.id.FBAnswerBox);

        final TextView txtFB1 = (TextView) findViewById(R.id.txtFB1);
        final TextView txtFB2 = (TextView) findViewById(R.id.txtFB2);
        final TextView txtFB3 = (TextView) findViewById(R.id.txtFB3);
        final TextView txtFB4 = (TextView) findViewById(R.id.txtFB4);
        final TextView txtFB5 = (TextView) findViewById(R.id.txtFB5);
        final TextView txtFbtitle = (TextView) findViewById(R.id.txtFbtitle);

        RequestQueue queue2 = Volley.newRequestQueue(StudentTest.this);
        //String url2 = "http://www.techive.in/studybuddy/api/random_question.php";
        String url2 = Apis.base_url + Apis.random_question_url;
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Fillintheblank", response);
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            JSONArray heroArray = jsonObject1.getJSONArray("fill_in_blank");
                            Log.d("FillArray", "" + String.valueOf(heroArray));
                            if (heroArray.length() > 0) {
                                for (int i = 0; i < heroArray.length(); i++) {
                                    JSONObject c = heroArray.getJSONObject(i);
                                    Log.d("FillArrayQQQ", c.getString("ques"));
                                    HashMap<String, String> FIBMAP = new HashMap<>();
                                    //FIBMAP.put("ques_no", c.getString(String.valueOf(QuestiojnMCQ.size() + TUREFALSE.size() + i)));
                                    FIBMAP.put("ques_no", c.getString("ques_no"));
                                    FIBMAP.put("ques", c.getString("ques"));
                                    FIBMAP.put("ans", c.getString("ans"));
                                    FIBMAP.put("sno", c.getString("sno"));
                                    FIBMAP.put("count", c.getString("count"));
                                    FIBMAP.put("MarkedANS", "");
                                    FIBMAP.put("Marks", String.valueOf(marks_fill));
                                    Log.d("FillArrayQQQ", String.valueOf(FIBMAP));
                                    FILLINTHEBLANKS.add(FIBMAP);
                                }

                                txtFB1.setText((FILLINTHEBLANKS != null && FILLINTHEBLANKS.size() > 0) ? FILLINTHEBLANKS.get(0).get("ans") : "");
                                txtFB2.setText((FILLINTHEBLANKS != null && FILLINTHEBLANKS.size() > 1) ? FILLINTHEBLANKS.get(1).get("ans") : "");
                                txtFB3.setText((FILLINTHEBLANKS != null && FILLINTHEBLANKS.size() > 2) ? FILLINTHEBLANKS.get(2).get("ans") : "");
                                txtFB4.setText((FILLINTHEBLANKS != null && FILLINTHEBLANKS.size() > 3) ? FILLINTHEBLANKS.get(3).get("ans") : "");
                                txtFB5.setText((FILLINTHEBLANKS != null && FILLINTHEBLANKS.size() > 4) ? FILLINTHEBLANKS.get(4).get("ans") : "");
                                txtFbtitle.setVisibility((FILLINTHEBLANKS != null && FILLINTHEBLANKS.size() > 0) && !FILLINTHEBLANKS.get(0).get("ans").equals("") ? View.VISIBLE : View.GONE);
                                txtFB1.setVisibility((FILLINTHEBLANKS != null && FILLINTHEBLANKS.size() > 0) && !FILLINTHEBLANKS.get(0).get("ans").equals("") ? View.VISIBLE : View.GONE);
                                txtFB2.setVisibility((FILLINTHEBLANKS != null && FILLINTHEBLANKS.size() > 1) && !FILLINTHEBLANKS.get(1).get("ans").equals("") ? View.VISIBLE : View.GONE);
                                txtFB3.setVisibility((FILLINTHEBLANKS != null && FILLINTHEBLANKS.size() > 2) && !FILLINTHEBLANKS.get(2).get("ans").equals("") ? View.VISIBLE : View.GONE);
                                txtFB4.setVisibility((FILLINTHEBLANKS != null && FILLINTHEBLANKS.size() > 3) && !FILLINTHEBLANKS.get(3).get("ans").equals("") ? View.VISIBLE : View.GONE);
                                txtFB5.setVisibility((FILLINTHEBLANKS != null && FILLINTHEBLANKS.size() > 4) && !FILLINTHEBLANKS.get(4).get("ans").equals("") ? View.VISIBLE : View.GONE);

                                txtFB1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        FBAnswerBox.setText(txtFB1.getText().toString());

                                        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                            txtFB1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.selected_card));
                                            txtFB2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB3.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB4.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB5.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.buttoncard));

                                        } else if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
                                            txtFB1.setBackground(context.getResources().getDrawable(R.drawable.selected_card));
                                            txtFB2.setBackground(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB3.setBackground(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB3.setBackground(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB4.setBackground(context.getResources().getDrawable(R.drawable.buttoncard));
                                        } else {
                                            txtFB1.setBackground(ContextCompat.getDrawable(context, R.drawable.selected_card));
                                            txtFB2.setBackground(ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                            txtFB3.setBackground(ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                            txtFB4.setBackground(ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                            txtFB5.setBackground(ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                        }
                                    }
                                });
                                txtFB2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        FBAnswerBox.setText(txtFB2.getText().toString());
                                        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                            txtFB1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.selected_card));
                                            txtFB3.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB4.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB5.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.buttoncard));

                                        } else if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
                                            txtFB1.setBackground(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB2.setBackground(context.getResources().getDrawable(R.drawable.selected_card));
                                            txtFB3.setBackground(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB4.setBackground(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB5.setBackground(context.getResources().getDrawable(R.drawable.buttoncard));
                                        } else {
                                            txtFB1.setBackground(ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                            txtFB2.setBackground(ContextCompat.getDrawable(context, R.drawable.selected_card));
                                            txtFB3.setBackground(ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                            txtFB4.setBackground(ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                            txtFB5.setBackground(ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                        }
                                    }
                                });
                                txtFB3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        FBAnswerBox.setText(txtFB3.getText().toString());
                                        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                            txtFB1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB3.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.selected_card));
                                            txtFB4.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB5.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.buttoncard));

                                        } else if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
                                            txtFB1.setBackground(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB2.setBackground(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB3.setBackground(context.getResources().getDrawable(R.drawable.selected_card));
                                            txtFB4.setBackground(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB5.setBackground(context.getResources().getDrawable(R.drawable.buttoncard));
                                        } else {
                                            txtFB1.setBackground(ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                            txtFB2.setBackground(ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                            txtFB3.setBackground(ContextCompat.getDrawable(context, R.drawable.selected_card));
                                            txtFB4.setBackground(ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                            txtFB5.setBackground(ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                        }
                                    }
                                });
                                txtFB4.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        FBAnswerBox.setText(txtFB4.getText().toString());
                                        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                            txtFB1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB3.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB4.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.selected_card));
                                            txtFB5.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.buttoncard));

                                        } else if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
                                            txtFB1.setBackground(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB2.setBackground(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB3.setBackground(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB4.setBackground(context.getResources().getDrawable(R.drawable.selected_card));
                                            txtFB5.setBackground(context.getResources().getDrawable(R.drawable.buttoncard));
                                        } else {
                                            txtFB1.setBackground(ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                            txtFB2.setBackground(ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                            txtFB3.setBackground(ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                            txtFB4.setBackground(ContextCompat.getDrawable(context, R.drawable.selected_card));
                                            txtFB5.setBackground(ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                        }
                                    }
                                });
                                txtFB5.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        FBAnswerBox.setText(txtFB5.getText().toString());
                                        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                            txtFB1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB3.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB4.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB5.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.selected_card));

                                        } else if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
                                            txtFB1.setBackground(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB2.setBackground(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB3.setBackground(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB4.setBackground(context.getResources().getDrawable(R.drawable.buttoncard));
                                            txtFB5.setBackground(context.getResources().getDrawable(R.drawable.selected_card));
                                        } else {
                                            txtFB1.setBackground(ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                            txtFB2.setBackground(ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                            txtFB3.setBackground(ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                            txtFB4.setBackground(ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                            txtFB5.setBackground(ContextCompat.getDrawable(context, R.drawable.selected_card));
                                        }
                                    }
                                });
                                Log.d("FILLINTHEBLANKS", String.valueOf(FILLINTHEBLANKS));
                                //chapterList.add(PateintDATA);
                                FBQuestionSCount = TRUEFalseNumber + MCQListCount + FBQuestionSCount;
                                //QuestionCountFB.setText(String.valueOf(main_question_count));
                                //QuestionCountFB.setText(String.valueOf(FILLINTHEBLANKS.get(FB).get("sno")));
                                QuestionCountFB.setText(String.valueOf(FILLINTHEBLANKS.get(FB).get("count")));
                                FBQuestion.setText(FILLINTHEBLANKS.get(FB).get("ques"));
                                FBrightAnswer = FILLINTHEBLANKS.get(FB).get("ans");
                                //FITBMarks = FILLINTHEBLANKS.get(FB).get("Marks");
                                FillintheNumber = FILLINTHEBLANKS.size();
                                //region "NextButtonfillintheblank"
                                NextButtonfillintheblank.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {
                                            String StudentAnswer = FBAnswerBox.getText().toString();
                                            FILLINTHEBLANKS.get(FB).put("MarkedANS", StudentAnswer);
                                            if (FB < FILLINTHEBLANKS.size()) {
                                                FB = FB + 1;
                                            }
                                            if (FB == FillintheNumber) {
                                                //todo FillIntheblacks
                                                if (match_col_size > 0) {
                                                    layoutMColumn.setVisibility(View.VISIBLE);
                                                    FillInTheBlancks.setVisibility(View.GONE);
                                                } else {
                                                    FillInTheBlancks.setVisibility(View.VISIBLE);
                                                    layoutMColumn.setVisibility(View.VISIBLE);
                                                }
                                                //TODO : Note : showTotalMarks when till FillintheBlanks is filled
                                                if (match_col_size == 0) {
                                                    showTotalMarks();
                                                }
                                                FB = FB - 1;
                                            } else {
                                                try {
                                                    FBQuestionSCount = TRUEFalseNumber + MCQListCount + FBQuestionSCount + 1;
                                                    main_question_count = main_question_count + 1;
                                                    //QuestionCountFB.setText(String.valueOf(main_question_count));
                                                    //QuestionCountFB.setText(String.valueOf(FILLINTHEBLANKS.get(FB).get("sno")));
                                                    QuestionCountFB.setText(String.valueOf(FILLINTHEBLANKS.get(FB).get("count")));
                                                    FBQuestion.setText(FILLINTHEBLANKS.get(FB).get("ques"));
                                                    FBAnswerBox.setText((FILLINTHEBLANKS != null && FILLINTHEBLANKS.size() > FB) ? FILLINTHEBLANKS.get(FB).get("MarkedANS") : "");

                                                    if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                                        txtFB1.setBackgroundDrawable(FBAnswerBox.getText().toString().equals(txtFB1.getText().toString()) && !txtFB1.getText().toString().isEmpty() ? context.getResources().getDrawable(R.drawable.selected_card) : context.getResources().getDrawable(R.drawable.buttoncard));
                                                        txtFB2.setBackgroundDrawable(FBAnswerBox.getText().toString().equals(txtFB2.getText().toString()) && !txtFB2.getText().toString().isEmpty() ? context.getResources().getDrawable(R.drawable.selected_card) : context.getResources().getDrawable(R.drawable.buttoncard));
                                                        txtFB3.setBackgroundDrawable(FBAnswerBox.getText().toString().equals(txtFB3.getText().toString()) && !txtFB3.getText().toString().isEmpty() ? context.getResources().getDrawable(R.drawable.selected_card) : context.getResources().getDrawable(R.drawable.buttoncard));
                                                        txtFB4.setBackgroundDrawable(FBAnswerBox.getText().toString().equals(txtFB4.getText().toString()) && !txtFB4.getText().toString().isEmpty() ? context.getResources().getDrawable(R.drawable.selected_card) : context.getResources().getDrawable(R.drawable.buttoncard));
                                                        txtFB5.setBackgroundDrawable(FBAnswerBox.getText().toString().equals(txtFB5.getText().toString()) && !txtFB5.getText().toString().isEmpty() ? context.getResources().getDrawable(R.drawable.selected_card) : context.getResources().getDrawable(R.drawable.buttoncard));

                                                    } else if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
                                                        txtFB1.setBackground(FBAnswerBox.getText().toString().equals(txtFB1.getText().toString()) && !txtFB1.getText().toString().isEmpty() ? context.getResources().getDrawable(R.drawable.selected_card) : context.getResources().getDrawable(R.drawable.buttoncard));
                                                        txtFB2.setBackground(FBAnswerBox.getText().toString().equals(txtFB2.getText().toString()) && !txtFB2.getText().toString().isEmpty() ? context.getResources().getDrawable(R.drawable.selected_card) : context.getResources().getDrawable(R.drawable.buttoncard));
                                                        txtFB3.setBackground(FBAnswerBox.getText().toString().equals(txtFB3.getText().toString()) && !txtFB3.getText().toString().isEmpty() ? context.getResources().getDrawable(R.drawable.selected_card) : context.getResources().getDrawable(R.drawable.buttoncard));
                                                        txtFB4.setBackground(FBAnswerBox.getText().toString().equals(txtFB4.getText().toString()) && !txtFB4.getText().toString().isEmpty() ? context.getResources().getDrawable(R.drawable.selected_card) : context.getResources().getDrawable(R.drawable.buttoncard));
                                                        txtFB5.setBackground(FBAnswerBox.getText().toString().equals(txtFB5.getText().toString()) && !txtFB5.getText().toString().isEmpty() ? context.getResources().getDrawable(R.drawable.selected_card) : context.getResources().getDrawable(R.drawable.buttoncard));
                                                    } else {
                                                        txtFB1.setBackground(FBAnswerBox.getText().toString().equals(txtFB1.getText().toString()) && !txtFB1.getText().toString().isEmpty() ? ContextCompat.getDrawable(context, R.drawable.selected_card) : ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                                        txtFB2.setBackground(FBAnswerBox.getText().toString().equals(txtFB2.getText().toString()) && !txtFB2.getText().toString().isEmpty() ? ContextCompat.getDrawable(context, R.drawable.selected_card) : ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                                        txtFB3.setBackground(FBAnswerBox.getText().toString().equals(txtFB3.getText().toString()) && !txtFB3.getText().toString().isEmpty() ? ContextCompat.getDrawable(context, R.drawable.selected_card) : ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                                        txtFB4.setBackground(FBAnswerBox.getText().toString().equals(txtFB4.getText().toString()) && !txtFB4.getText().toString().isEmpty() ? ContextCompat.getDrawable(context, R.drawable.selected_card) : ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                                        txtFB5.setBackground(FBAnswerBox.getText().toString().equals(txtFB5.getText().toString()) && !txtFB5.getText().toString().isEmpty() ? ContextCompat.getDrawable(context, R.drawable.selected_card) : ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                                    }

                                                } catch (ArrayIndexOutOfBoundsException e) {

                                                } catch (NullPointerException Nu) {

                                                }
                                            }
                                        } catch (ArrayIndexOutOfBoundsException ex) {
                                            Log.d("ex", String.valueOf(ex));
                                        }
                                    }
                                });//endregion
                                //region "BackButtonfillintheblank"
                                BackButtonfillintheblank.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {
                                            //Quecount = Quecount - 1;
                                            FB = FB - 1;

                                            if (FB < 0) {
                                                objective.setVisibility(View.GONE);
                                                if (tf_size != 0) {
                                                    TureFasle.setVisibility(View.VISIBLE);
                                                    FillInTheBlancks.setVisibility(View.GONE);
                                                } else if (tf_size == 0 && mcq_size != 0) {
                                                    TureFasle.setVisibility(View.GONE);
                                                    FillInTheBlancks.setVisibility(View.GONE);
                                                    MQCTestLayout.setVisibility(View.VISIBLE);
                                                } else if (tf_size == 0 && mcq_size == 0) {
                                                    TureFasle.setVisibility(View.GONE);
                                                    FillInTheBlancks.setVisibility(View.VISIBLE);
                                                    MQCTestLayout.setVisibility(View.GONE);
                                                }
                                                FB = FB + 1;
                                                //Quecount = Quecount + 1;
                                            } else {

                                                FBQuestionSCount = TRUEFalseNumber + MCQListCount + FBQuestionSCount - 1;
                                                main_question_count = main_question_count - 1;
                                                //QuestionCountFB.setText(String.valueOf(main_question_count));
                                                //QuestionCountFB.setText(String.valueOf(FILLINTHEBLANKS.get(FB).get("sno")));
                                                QuestionCountFB.setText(String.valueOf(FILLINTHEBLANKS.get(FB).get("count")));
                                                TotalMarksFB.setText("Marks\t:\t" + marks_fill);
                                                FBQuestion.setText(FILLINTHEBLANKS.get(FB).get("ques"));
                                                //TFQuestionNo.setText("Question  " + String.valueOf(Quecount));
                                                FBAnswerBox.setText(FILLINTHEBLANKS.get(FB).get("MarkedANS"));
                                                Log.d("MarkedAnsFLL", FILLINTHEBLANKS.get(FB).get("MarkedANS"));

                                                if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                                    txtFB1.setBackgroundDrawable(FBAnswerBox.getText().toString().equals(txtFB1.getText().toString()) && !txtFB1.getText().toString().isEmpty() ? context.getResources().getDrawable(R.drawable.selected_card) : context.getResources().getDrawable(R.drawable.buttoncard));
                                                    txtFB2.setBackgroundDrawable(FBAnswerBox.getText().toString().equals(txtFB2.getText().toString()) && !txtFB2.getText().toString().isEmpty() ? context.getResources().getDrawable(R.drawable.selected_card) : context.getResources().getDrawable(R.drawable.buttoncard));
                                                    txtFB3.setBackgroundDrawable(FBAnswerBox.getText().toString().equals(txtFB3.getText().toString()) && !txtFB3.getText().toString().isEmpty() ? context.getResources().getDrawable(R.drawable.selected_card) : context.getResources().getDrawable(R.drawable.buttoncard));
                                                    txtFB4.setBackgroundDrawable(FBAnswerBox.getText().toString().equals(txtFB4.getText().toString()) && !txtFB4.getText().toString().isEmpty() ? context.getResources().getDrawable(R.drawable.selected_card) : context.getResources().getDrawable(R.drawable.buttoncard));
                                                    txtFB5.setBackgroundDrawable(FBAnswerBox.getText().toString().equals(txtFB5.getText().toString()) && !txtFB5.getText().toString().isEmpty() ? context.getResources().getDrawable(R.drawable.selected_card) : context.getResources().getDrawable(R.drawable.buttoncard));

                                                } else if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
                                                    txtFB1.setBackground(FBAnswerBox.getText().toString().equals(txtFB1.getText().toString()) && !txtFB1.getText().toString().isEmpty() ? context.getResources().getDrawable(R.drawable.selected_card) : context.getResources().getDrawable(R.drawable.buttoncard));
                                                    txtFB2.setBackground(FBAnswerBox.getText().toString().equals(txtFB2.getText().toString()) && !txtFB2.getText().toString().isEmpty() ? context.getResources().getDrawable(R.drawable.selected_card) : context.getResources().getDrawable(R.drawable.buttoncard));
                                                    txtFB3.setBackground(FBAnswerBox.getText().toString().equals(txtFB3.getText().toString()) && !txtFB3.getText().toString().isEmpty() ? context.getResources().getDrawable(R.drawable.selected_card) : context.getResources().getDrawable(R.drawable.buttoncard));
                                                    txtFB4.setBackground(FBAnswerBox.getText().toString().equals(txtFB4.getText().toString()) && !txtFB4.getText().toString().isEmpty() ? context.getResources().getDrawable(R.drawable.selected_card) : context.getResources().getDrawable(R.drawable.buttoncard));
                                                    txtFB5.setBackground(FBAnswerBox.getText().toString().equals(txtFB5.getText().toString()) && !txtFB5.getText().toString().isEmpty() ? context.getResources().getDrawable(R.drawable.selected_card) : context.getResources().getDrawable(R.drawable.buttoncard));
                                                } else {
                                                    txtFB1.setBackground(FBAnswerBox.getText().toString().equals(txtFB1.getText().toString()) && !txtFB1.getText().toString().isEmpty() ? ContextCompat.getDrawable(context, R.drawable.selected_card) : ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                                    txtFB2.setBackground(FBAnswerBox.getText().toString().equals(txtFB2.getText().toString()) && !txtFB2.getText().toString().isEmpty() ? ContextCompat.getDrawable(context, R.drawable.selected_card) : ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                                    txtFB3.setBackground(FBAnswerBox.getText().toString().equals(txtFB3.getText().toString()) && !txtFB3.getText().toString().isEmpty() ? ContextCompat.getDrawable(context, R.drawable.selected_card) : ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                                    txtFB4.setBackground(FBAnswerBox.getText().toString().equals(txtFB4.getText().toString()) && !txtFB4.getText().toString().isEmpty() ? ContextCompat.getDrawable(context, R.drawable.selected_card) : ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                                    txtFB5.setBackground(FBAnswerBox.getText().toString().equals(txtFB5.getText().toString()) && !txtFB5.getText().toString().isEmpty() ? ContextCompat.getDrawable(context, R.drawable.selected_card) : ContextCompat.getDrawable(context, R.drawable.buttoncard));
                                                }
                                            }
                                        } catch (ArrayIndexOutOfBoundsException ex) {
                                            Log.d("ex", String.valueOf(ex));
                                        }
                                    }
                                });//endregion

                                Log.d("Sssssssslist", String.valueOf(heroArray));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                System.out.println("ResponseRegistration" + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("accesscodes", "0300081516");
                params.put("ch_id", ChapterID);

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue2.add(stringRequest2);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////


        ///////////////////////////////................3.....................///////////////////////////////////////////////////////////////////


        Quecountnumber = Questionobjective.size() + TUREFALSE.size() + FILLINTHEBLANKS.size();

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        CountObj = Questionobjective.size();
        CountTruefalse = TUREFALSE.size();
        CountFillIntheblnks = FILLINTHEBLANKS.size();


        //////////////////////////////// FILLINTHEBLANKS VIEW //////////////////////////////////////////
        SharedPreferences spsFI = getSharedPreferences("mysharedpref", MODE_PRIVATE);
        String QuestionCounterFI = spsFI.getString("TFQuesCount", "0");
        Quecount = Quecount + 1;
        int FIllB = Integer.parseInt(QuestionCounterFI);
        // = Integer.parseInt(TFQuestionNo.getText().toString());
        FillinbCount = FIllB + 1;
        TotalMarksFB = (TextView) findViewById(R.id.TotalMarksFB);
        TotalMarksFB.setText("Marks\t:\t" + marks_fill);
        QuestionCountFB = (TextView) findViewById(R.id.QuestionCountFB);
        QuestionCountFB.setText(String.valueOf(FillinbCount));

        //region "Fill MColumn List"

        RequestQueue queueMCol = Volley.newRequestQueue(StudentTest.this);
        //String urlMCol = "http://www.techive.in/studybuddy/api/random_question.php";
        String urlMCol = Apis.base_url + Apis.random_question_url;
        StringRequest stringRequestMCol = new StringRequest(Request.Method.POST, urlMCol,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseMCol", response);
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        try {
                            String result = CommonMethods.getJson(context, "mcolumn");
                            //JSONObject jsonObject1 = new JSONObject(result);
                            JSONObject jsonObject1 = new JSONObject(response);
                            JSONArray mcolArray = jsonObject1.getJSONArray("match_column");
                            Log.d("FillArrayMcol", "" + String.valueOf(mcolArray));
                            if (mcolArray.length() > 0) {

                                for (int i = 0; i < mcolArray.length(); i++) {
                                    JSONObject c = mcolArray.getJSONObject(i);
                                    //Log.d("MColArrayQQQ", c.getString("ques"));

                                    HashMap<String, String> MColMap = new HashMap<>();

                                    //FIBMAP.put("ques_no", c.getString(String.valueOf(QuestiojnMCQ.size() + TUREFALSE.size() + i)));
                                    MColMap.put("ques_no", c.getString("q_no"));
                                    MColMap.put("mc_id", c.getString("mc_id"));
                                    MColMap.put("sno", c.getString("sno"));
                                    MColMap.put("count", c.getString("count"));
                                    MColMap.put("MarkedANS1", c.getString("column1r1") == null || c.getString("column1r1").isEmpty() ? "E" : "");
                                    MColMap.put("MarkedANS2", c.getString("column1r2") == null || c.getString("column1r2").isEmpty() ? "E" : "");
                                    MColMap.put("MarkedANS3", c.getString("column1r3") == null || c.getString("column1r3").isEmpty() ? "E" : "");
                                    MColMap.put("MarkedANS4", c.getString("column1r4") == null || c.getString("column1r4").isEmpty() ? "E" : "");
                                    MColMap.put("MarkedANS5", c.getString("column1r5") == null || c.getString("column1r5").isEmpty() ? "E" : "");
                                    MColMap.put("MarkedANS6", c.getString("column1r6") == null || c.getString("column1r6").isEmpty() ? "E" : "");
                                    MColMap.put("MarkedANS7", c.getString("column1r7") == null || c.getString("column1r7").isEmpty() ? "E" : "");
                                    MColMap.put("MarkedANS8", c.getString("column1r8") == null || c.getString("column1r8").isEmpty() ? "E" : "");

                                    MColMap.put("column1r1", c.getString("column1r1"));
                                    MColMap.put("column1r2", c.getString("column1r2"));
                                    MColMap.put("column1r3", c.getString("column1r3"));
                                    MColMap.put("column1r4", c.getString("column1r4"));
                                    MColMap.put("column1r5", c.getString("column1r5"));
                                    MColMap.put("column1r6", c.getString("column1r6"));
                                    MColMap.put("column1r7", c.getString("column1r7"));
                                    MColMap.put("column1r8", c.getString("column1r8"));

                                    MColMap.put("column2r1", c.getString("column2r1"));
                                    MColMap.put("column2r2", c.getString("column2r2"));
                                    MColMap.put("column2r3", c.getString("column2r3"));
                                    MColMap.put("column2r4", c.getString("column2r4"));
                                    MColMap.put("column2r5", c.getString("column2r5"));
                                    MColMap.put("column2r6", c.getString("column2r6"));
                                    MColMap.put("column2r7", c.getString("column2r7"));
                                    MColMap.put("column2r8", c.getString("column2r8"));

                                    MColMap.put("ans1", c.getString("ans1"));
                                    MColMap.put("ans2", c.getString("ans2"));
                                    MColMap.put("ans3", c.getString("ans3"));
                                    MColMap.put("ans4", c.getString("ans4"));
                                    MColMap.put("ans5", c.getString("ans5"));
                                    MColMap.put("ans6", c.getString("ans6"));
                                    MColMap.put("ans7", c.getString("ans7"));
                                    MColMap.put("ans8", c.getString("ans8"));

                                    //region check answer
                                    String r1 = c.getString("column2r1");
                                    String r2 = c.getString("column2r2");
                                    String r3 = c.getString("column2r3");
                                    String r4 = c.getString("column2r4");
                                    String r5 = c.getString("column2r5");
                                    String r6 = c.getString("column2r6");
                                    String r7 = c.getString("column2r7");
                                    String r8 = c.getString("column2r8");

                                    String a1 = c.getString("ans1");
                                    String a2 = c.getString("ans2");
                                    String a3 = c.getString("ans3");
                                    String a4 = c.getString("ans4");
                                    String a5 = c.getString("ans5");
                                    String a6 = c.getString("ans6");
                                    String a7 = c.getString("ans7");
                                    String a8 = c.getString("ans8");
                                    String answers1 = "";
                                    String answers2 = "";
                                    String answers3 = "";
                                    String answers4 = "";
                                    String answers5 = "";
                                    String answers6 = "";
                                    String answers7 = "";
                                    String answers8 = "";

                                    //region "check a"
                                    if (!r1.equals("") && !a1.equals("") && a1.equals(r1)) {
                                        answers1 = "a";
                                    }
                                    if (!r2.equals("") && !a1.equals("") && a1.equals(r2)) {
                                        answers1 = "b";
                                    }
                                    if (!r3.equals("") && !a1.equals("") && a1.equals(r3)) {
                                        answers1 = "c";
                                    }
                                    if (!r4.equals("") && !a1.equals("") && a1.equals(r4)) {
                                        answers1 = "d";
                                    }
                                    if (!r5.equals("") && !a1.equals("") && a1.equals(r5)) {
                                        answers1 = "e";
                                    }
                                    if (!r6.equals("") && !a1.equals("") && a1.equals(r6)) {
                                        answers1 = "f";
                                    }
                                    if (!r7.equals("") && !a1.equals("") && a1.equals(r7)) {
                                        answers1 = "g";
                                    }
                                    if (!r8.equals("") && !a1.equals("") && a1.equals(r8)) {
                                        answers1 = "h";
                                    }
                                    //endregion
                                    //region "check b"
                                    if (!r1.equals("") && !a2.equals("") && a2.equals(r1)) {
                                        answers2 = "a";
                                    }
                                    if (!r2.equals("") && !a2.equals("") && a2.equals(r2)) {
                                        answers2 = "b";
                                    }
                                    if (!r3.equals("") && !a2.equals("") && a2.equals(r3)) {
                                        answers2 = "c";
                                    }
                                    if (!r4.equals("") && !a2.equals("") && a2.equals(r4)) {
                                        answers2 = "d";
                                    }
                                    if (!r5.equals("") && !a2.equals("") && a2.equals(r5)) {
                                        answers2 = "e";
                                    }
                                    if (!r6.equals("") && !a2.equals("") && a2.equals(r6)) {
                                        answers2 = "f";
                                    }
                                    if (!r7.equals("") && !a2.equals("") && a2.equals(r7)) {
                                        answers2 = "g";
                                    }
                                    if (!r8.equals("") && !a2.equals("") && a2.equals(r8)) {
                                        answers2 = "h";
                                    }
                                    //endregion
                                    //region "check c"
                                    if (!r1.equals("") && !a3.equals("") && a3.equals(r1)) {
                                        answers3 = "a";
                                    }
                                    if (!r2.equals("") && !a3.equals("") && a3.equals(r2)) {
                                        answers3 = "b";
                                    }
                                    if (!r3.equals("") && !a3.equals("") && a3.equals(r3)) {
                                        answers3 = "c";
                                    }
                                    if (!r4.equals("") && !a3.equals("") && a3.equals(r4)) {
                                        answers3 = "d";
                                    }
                                    if (!r5.equals("") && !a3.equals("") && a3.equals(r5)) {
                                        answers3 = "e";
                                    }
                                    if (!r6.equals("") && !a3.equals("") && a3.equals(r6)) {
                                        answers3 = "f";
                                    }
                                    if (!r7.equals("") && !a3.equals("") && a3.equals(r7)) {
                                        answers3 = "g";
                                    }
                                    if (!r8.equals("") && !a3.equals("") && a3.equals(r8)) {
                                        answers3 = "h";
                                    }//endregion
                                    //region "check d"
                                    if (!r1.equals("") && !a4.equals("") && a4.equals(r1)) {
                                        answers4 = "a";
                                    }
                                    if (!r2.equals("") && !a4.equals("") && a4.equals(r2)) {
                                        answers4 = "b";
                                    }
                                    if (!r3.equals("") && !a4.equals("") && a4.equals(r3)) {
                                        answers4 = "c";
                                    }
                                    if (!r4.equals("") && !a4.equals("") && a4.equals(r4)) {
                                        answers4 = "d";
                                    }
                                    if (!r5.equals("") && !a4.equals("") && a4.equals(r5)) {
                                        answers4 = "e";
                                    }
                                    if (!r6.equals("") && !a4.equals("") && a4.equals(r6)) {
                                        answers4 = "f";
                                    }
                                    if (!r7.equals("") && !a4.equals("") && a4.equals(r7)) {
                                        answers4 = "g";
                                    }
                                    if (!r8.equals("") && !a4.equals("") && a4.equals(r8)) {
                                        answers4 = "h";
                                    }//endregion
                                    //region "check e"
                                    if (!r1.equals("") && !a5.equals("") && a5.equals(r1)) {
                                        answers5 = "a";
                                    }
                                    if (!r2.equals("") && !a5.equals("") && a5.equals(r2)) {
                                        answers5 = "b";
                                    }
                                    if (!r3.equals("") && !a5.equals("") && a5.equals(r3)) {
                                        answers5 = "c";
                                    }
                                    if (!r4.equals("") && !a5.equals("") && a5.equals(r4)) {
                                        answers5 = "d";
                                    }
                                    if (!r5.equals("") && !a5.equals("") && a5.equals(r5)) {
                                        answers5 = "e";
                                    }
                                    if (!r6.equals("") && !a5.equals("") && a5.equals(r6)) {
                                        answers5 = "f";
                                    }
                                    if (!r7.equals("") && !a5.equals("") && a5.equals(r7)) {
                                        answers5 = "g";
                                    }
                                    if (!r8.equals("") && !a5.equals("") && a5.equals(r8)) {
                                        answers5 = "h";
                                    }//endregion
                                    //region "check f"
                                    if (!r1.equals("") && !a6.equals("") && a6.equals(r1)) {
                                        answers6 = "a";
                                    }
                                    if (!r2.equals("") && !a6.equals("") && a6.equals(r2)) {
                                        answers6 = "b";
                                    }
                                    if (!r3.equals("") && !a6.equals("") && a6.equals(r3)) {
                                        answers6 = "c";
                                    }
                                    if (!r4.equals("") && !a6.equals("") && a6.equals(r4)) {
                                        answers6 = "d";
                                    }
                                    if (!r5.equals("") && !a6.equals("") && a6.equals(r5)) {
                                        answers6 = "e";
                                    }
                                    if (!r6.equals("") && !a6.equals("") && a6.equals(r6)) {
                                        answers6 = "f";
                                    }
                                    if (!r7.equals("") && !a6.equals("") && a6.equals(r7)) {
                                        answers6 = "g";
                                    }
                                    if (!r8.equals("") && !a6.equals("") && a6.equals(r8)) {
                                        answers6 = "h";
                                    }///endregion
                                    //region "check f"
                                    if (!r1.equals("") && !a7.equals("") && a7.equals(r1)) {
                                        answers7 = "a";
                                    }
                                    if (!r2.equals("") && !a7.equals("") && a7.equals(r2)) {
                                        answers7 = "b";
                                    }
                                    if (!r3.equals("") && !a7.equals("") && a7.equals(r3)) {
                                        answers7 = "c";
                                    }
                                    if (!r4.equals("") && !a7.equals("") && a7.equals(r4)) {
                                        answers7 = "d";
                                    }
                                    if (!r5.equals("") && !a7.equals("") && a7.equals(r5)) {
                                        answers7 = "e";
                                    }
                                    if (!r6.equals("") && !a7.equals("") && a7.equals(r6)) {
                                        answers7 = "f";
                                    }
                                    if (!r7.equals("") && !a7.equals("") && a7.equals(r7)) {
                                        answers7 = "g";
                                    }
                                    if (!r8.equals("") && !a7.equals("") && a7.equals(r8)) {
                                        answers7 = "h";
                                    }///endregion
                                    //region "check g"
                                    if (!r1.equals("") && !a8.equals("") && a8.equals(r1)) {
                                        answers8 = "a";
                                    }
                                    if (!r2.equals("") && !a8.equals("") && a8.equals(r2)) {
                                        answers8 = "b";
                                    }
                                    if (!r3.equals("") && !a8.equals("") && a8.equals(r3)) {
                                        answers8 = "c";
                                    }
                                    if (!r4.equals("") && !a8.equals("") && a8.equals(r4)) {
                                        answers8 = "d";
                                    }
                                    if (!r5.equals("") && !a8.equals("") && a8.equals(r5)) {
                                        answers8 = "e";
                                    }
                                    if (!r6.equals("") && !a8.equals("") && a8.equals(r6)) {
                                        answers8 = "f";
                                    }
                                    if (!r7.equals("") && !a8.equals("") && a8.equals(r7)) {
                                        answers8 = "g";
                                    }
                                    if (!r8.equals("") && !a8.equals("") && a8.equals(r8)) {
                                        answers8 = "h";
                                    }//endregion
                                    //endregion
                                    int mrks_cnt = 0;
                                    if (!answers1.isEmpty()) {
                                        mrks_cnt++;
                                    }
                                    if (!answers2.isEmpty()) {
                                        mrks_cnt++;
                                    }
                                    if (!answers3.isEmpty()) {
                                        mrks_cnt++;
                                    }
                                    if (!answers4.isEmpty()) {
                                        mrks_cnt++;
                                    }
                                    if (!answers5.isEmpty()) {
                                        mrks_cnt++;
                                    }
                                    if (!answers6.isEmpty()) {
                                        mrks_cnt++;
                                    }
                                    if (!answers7.isEmpty()) {
                                        mrks_cnt++;
                                    }
                                    if (!answers8.isEmpty()) {
                                        mrks_cnt++;
                                    }
                                    MColMap.put("mrks_cnt", String.valueOf(mrks_cnt));

                                    //Log.d("answers111", answers1 + "\n" + answers2 + "\n" + answers3 + "\n" + answers4 + "\n" + answers5 + "\n" + answers6 + "\n" + answers7 + "\n" + answers8 + "\n");
                                    MColMap.put("m_col_ans1", answers1 == null || answers1.isEmpty() ? "E" : answers1);
                                    MColMap.put("m_col_ans2", answers2 == null || answers2.isEmpty() ? "E" : answers2);
                                    MColMap.put("m_col_ans3", answers3 == null || answers3.isEmpty() ? "E" : answers3);
                                    MColMap.put("m_col_ans4", answers4 == null || answers4.isEmpty() ? "E" : answers4);
                                    MColMap.put("m_col_ans5", answers5 == null || answers5.isEmpty() ? "E" : answers5);
                                    MColMap.put("m_col_ans6", answers6 == null || answers6.isEmpty() ? "E" : answers6);
                                    MColMap.put("m_col_ans7", answers7 == null || answers7.isEmpty() ? "E" : answers7);
                                    MColMap.put("m_col_ans8", answers8 == null || answers8.isEmpty() ? "E" : answers8);

                                    MColMap.put("MarkedANS", "");
                                    MColMap.put("Marks", String.valueOf(marks_mCol));

                                    QuestionMCol.add(MColMap);

                                }
                                Log.d("MCol111", QuestionMCol.toString());

                                HashMap<String, String> map = new HashMap<>();
                                StringBuilder stringBuilder = new StringBuilder();
                                StringBuilder stringBuilder_mrks = new StringBuilder();

                                for (int i = 0; i < QuestionMCol.size(); i++) {

                                    stringBuilder_mrks.append(QuestionMCol.get(i).get("mrks_cnt") + ",");
                                    stringBuilder.append(QuestionMCol.get(i).get("m_col_ans1") != null && !QuestionMCol.get(i).get("m_col_ans1").isEmpty() ? QuestionMCol.get(i).get("m_col_ans1") + "," : "N" + ",");
                                    stringBuilder.append(QuestionMCol.get(i).get("m_col_ans2") != null && !QuestionMCol.get(i).get("m_col_ans2").isEmpty() ? QuestionMCol.get(i).get("m_col_ans2") + "," : "N" + ",");
                                    stringBuilder.append(QuestionMCol.get(i).get("m_col_ans3") != null && !QuestionMCol.get(i).get("m_col_ans3").isEmpty() ? QuestionMCol.get(i).get("m_col_ans3") + "," : "N" + ",");
                                    stringBuilder.append(QuestionMCol.get(i).get("m_col_ans4") != null && !QuestionMCol.get(i).get("m_col_ans4").isEmpty() ? QuestionMCol.get(i).get("m_col_ans4") + "," : "N" + ",");
                                    stringBuilder.append(QuestionMCol.get(i).get("m_col_ans5") != null && !QuestionMCol.get(i).get("m_col_ans5").isEmpty() ? QuestionMCol.get(i).get("m_col_ans5") + "," : "N" + ",");
                                    stringBuilder.append(QuestionMCol.get(i).get("m_col_ans6") != null && !QuestionMCol.get(i).get("m_col_ans6").isEmpty() ? QuestionMCol.get(i).get("m_col_ans6") + "," : "N" + ",");
                                    stringBuilder.append(QuestionMCol.get(i).get("m_col_ans7") != null && !QuestionMCol.get(i).get("m_col_ans7").isEmpty() ? QuestionMCol.get(i).get("m_col_ans7") + "," : "N" + ",");
                                    stringBuilder.append(QuestionMCol.get(i).get("m_col_ans8") != null && !QuestionMCol.get(i).get("m_col_ans8").isEmpty() ? QuestionMCol.get(i).get("m_col_ans8") + "," : "N" + ",");
                                    //map.put("m_answers", String.valueOf(stringBuilder));
                                }

                                Log.d("string_builder", "" + stringBuilder);
                                if (stringBuilder.length() > 0 && stringBuilder.subSequence(stringBuilder.length() - 1, stringBuilder.length()).equals(",")) {
                                    stringBuilder.subSequence(0, stringBuilder.length() - 1);
                                }
                                //TODO Add Marks 2120
                                //list_mcol_mrks.add(String.valueOf(stringBuilder_mrks));
                                list1.add(String.valueOf(stringBuilder));
                                saveArrayMColList(list1, "response_mcol");
                                //saveArrayUserMColList(list1, "user_response_mcol");
                                Log.d("string_builder444", "" + list1.toString());

                                //TODO : TextView set text below txtQnoMCol
                                //View First Set Questions
                                txtCol1r1.setText("1.\t" + QuestionMCol.get(mColValue).get("column1r1"));
                                txtCol1r2.setText("2.\t" + QuestionMCol.get(mColValue).get("column1r2"));
                                txtCol1r3.setText("3.\t" + QuestionMCol.get(mColValue).get("column1r3"));
                                txtCol1r4.setText("4.\t" + QuestionMCol.get(mColValue).get("column1r4"));
                                txtCol1r5.setText("5.\t" + QuestionMCol.get(mColValue).get("column1r5"));
                                txtCol1r6.setText("6.\t" + QuestionMCol.get(mColValue).get("column1r6"));
                                txtCol1r7.setText("7.\t" + QuestionMCol.get(mColValue).get("column1r7"));
                                txtCol1r8.setText("8.\t" + QuestionMCol.get(mColValue).get("column1r8"));

                                txtCol2r1.setText(QuestionMCol.get(mColValue).get("column2r1"));
                                txtCol2r2.setText(QuestionMCol.get(mColValue).get("column2r2"));
                                txtCol2r3.setText(QuestionMCol.get(mColValue).get("column2r3"));
                                txtCol2r4.setText(QuestionMCol.get(mColValue).get("column2r4"));
                                txtCol2r5.setText(QuestionMCol.get(mColValue).get("column2r5"));
                                txtCol2r6.setText(QuestionMCol.get(mColValue).get("column2r6"));
                                txtCol2r7.setText(QuestionMCol.get(mColValue).get("column2r7"));
                                txtCol2r8.setText(QuestionMCol.get(mColValue).get("column2r8"));

                                llMCol1.setVisibility(!QuestionMCol.get(mColValue).get("column1r1").equals("") ? View.VISIBLE : View.GONE);
                                llMCol2.setVisibility(!QuestionMCol.get(mColValue).get("column1r2").equals("") ? View.VISIBLE : View.GONE);
                                llMCol3.setVisibility(!QuestionMCol.get(mColValue).get("column1r3").equals("") ? View.VISIBLE : View.GONE);
                                llMCol4.setVisibility(!QuestionMCol.get(mColValue).get("column1r4").equals("") ? View.VISIBLE : View.GONE);
                                llMCol5.setVisibility(!QuestionMCol.get(mColValue).get("column1r5").equals("") ? View.VISIBLE : View.GONE);
                                llMCol6.setVisibility(!QuestionMCol.get(mColValue).get("column1r6").equals("") ? View.VISIBLE : View.GONE);
                                llMCol7.setVisibility(!QuestionMCol.get(mColValue).get("column1r7").equals("") ? View.VISIBLE : View.GONE);
                                llMCol8.setVisibility(!QuestionMCol.get(mColValue).get("column1r8").equals("") ? View.VISIBLE : View.GONE);
                                //endregion
                                disp_mcol_mrks();
                                txtQnoMCol.setText(String.valueOf(QuestionMCol.get(mColValue).get("sno")));
                                /*FBQuestionSCount = TRUEFalseNumber + MCQListCount + FBQuestionSCount;
                                QuestionCountFB.setText("Question  " + String.valueOf(FBQuestionSCount));
                                FBQuestion.setText(FILLINTHEBLANKS.get(FB).get("ques"));
                                FBrightAnswer = FILLINTHEBLANKS.get(FB).get("ans");
                                //FITBMarks = FILLINTHEBLANKS.get(FB).get("Marks");
                                FillintheNumber = FILLINTHEBLANKS.size();*/
                                //endregion

                                //region "Next Back Buttonfillintheblank"
                                mCol = QuestionMCol != null ? QuestionMCol.size() : 0;
                                //region "Next Btn"
                                NextButtonMCol.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        QuestionMCol.get(mColValue).put("MarkedANS1", eTr1.getText().toString());
                                        QuestionMCol.get(mColValue).put("MarkedANS2", eTr2.getText().toString());
                                        QuestionMCol.get(mColValue).put("MarkedANS3", eTr3.getText().toString());
                                        QuestionMCol.get(mColValue).put("MarkedANS4", eTr4.getText().toString());
                                        QuestionMCol.get(mColValue).put("MarkedANS5", eTr5.getText().toString());
                                        QuestionMCol.get(mColValue).put("MarkedANS6", eTr6.getText().toString());
                                        QuestionMCol.get(mColValue).put("MarkedANS7", eTr7.getText().toString());
                                        QuestionMCol.get(mColValue).put("MarkedANS8", eTr8.getText().toString());

                                        Log.d("QuestionMCol_val", "" + QuestionMCol.size());
                                        Log.d("QuestionMCol_val", "" + mColValue);

                                        Log.d("QuestionMCol_vcnt", QuestionMCol.size() + "\n" + mColValue);

                                        if (QuestionMCol.size() > 0 && mColValue < QuestionMCol.size()) {
                                            mColValue = mColValue + 1;
                                        }
                                        if (mColValue < QuestionMCol.size()) {
                                            disp_mcol_mrks();
                                        }
                                        if (mColValue == QuestionMCol.size()) {

                                            HashMap<String, String> map = new HashMap<>();
                                            StringBuilder stringBuilder = new StringBuilder();
                                            for (int i = 0; i < QuestionMCol.size(); i++) {
                                                stringBuilder.append(!QuestionMCol.get(i).get("MarkedANS1").isEmpty() ? QuestionMCol.get(i).get("MarkedANS1") + "," : "N" + ",");
                                                stringBuilder.append(!QuestionMCol.get(i).get("MarkedANS2").isEmpty() ? QuestionMCol.get(i).get("MarkedANS2") + "," : "N" + ",");
                                                stringBuilder.append(!QuestionMCol.get(i).get("MarkedANS3").isEmpty() ? QuestionMCol.get(i).get("MarkedANS3") + "," : "N" + ",");
                                                stringBuilder.append(!QuestionMCol.get(i).get("MarkedANS4").isEmpty() ? QuestionMCol.get(i).get("MarkedANS4") + "," : "N" + ",");
                                                stringBuilder.append(!QuestionMCol.get(i).get("MarkedANS5").isEmpty() ? QuestionMCol.get(i).get("MarkedANS5") + "," : "N" + ",");
                                                stringBuilder.append(!QuestionMCol.get(i).get("MarkedANS6").isEmpty() ? QuestionMCol.get(i).get("MarkedANS6") + "," : "N" + ",");
                                                stringBuilder.append(!QuestionMCol.get(i).get("MarkedANS7").isEmpty() ? QuestionMCol.get(i).get("MarkedANS7") + "," : "N" + ",");
                                                stringBuilder.append(!QuestionMCol.get(i).get("MarkedANS8").isEmpty() ? QuestionMCol.get(i).get("MarkedANS8") + "," : "N" + ",");
                                                map.put("m_answers", String.valueOf(stringBuilder));
                                            }
                                            Log.d("string_builder", "" + stringBuilder);
                                            if (stringBuilder.length() > 0 && stringBuilder.subSequence(stringBuilder.length() - 1, stringBuilder.length()).equals(",")) {
                                                stringBuilder.subSequence(0, stringBuilder.length() - 1);
                                            }
                                            mcol_User_ans.add(String.valueOf(stringBuilder));
                                            Log.d("string_builder444", "" + mcol_User_ans.toString());
                                            saveArrayUserMColList(mcol_User_ans, "user_response_mcol");
                                            //showTotalMarks();
                                            //mColValue = mColValue - 1;
                                            /*===========================================================================================================================================*/

                                            try {
                                                int MCQToatal = 0, FilTotal = 0, TRtotal = 0, ALLtotal = 0;
                                                int MatchColMarks = 0;
                                                int cnt = 0;
                                                //region "Count Match Column Marks"
                                                if (mcol_User_ans.size() > 0) {
                                                    for (String s : list1.get(0).toString().split(",")) {
                                                        if (!s.equals("E") && !s.equals("N") && s.equals(mcol_User_ans.get(0).split(",")[cnt])) {
                                                            MatchColMarks++;
                                                        }
                                                        cnt++;
                                                    }
                                                }
                                                //endregion
                                                mColTotal = MatchColMarks;
                                                Log.d("counts_mcol", String.valueOf(MatchColMarks));
                                                Log.d("counts_mcol11", String.valueOf(cnt));
                                                ArrayList<String> fillInTheBlanksMarkedAns = new ArrayList<>();
                                                //todo FillIntheblacks
                                                for (int i = 0; i < FILLINTHEBLANKS.size(); i++) {

                                                    fillInTheBlanksMarkedAns.add(FILLINTHEBLANKS.get(i).get("MarkedANS"));

                                                    if (FILLINTHEBLANKS.get(i).get("ans").equals(FILLINTHEBLANKS.get(i).get("MarkedANS")) && !FILLINTHEBLANKS.get(i).get("ans").equals("")) {
                                                        FilTotal = FilTotal + marks_fill;
                                                    }
                                                }
                                                saveArrayFILLList(fillInTheBlanksMarkedAns, "fill_report");
                                                Log.d("fillInMarkedAns", String.valueOf(fillInTheBlanksMarkedAns));
                                                //todo True False
                                                ArrayList<String> trueFalseMarkedAns = new ArrayList<>();
                                                for (int i = 0; i < TUREFALSE.size(); i++) {

                                                    trueFalseMarkedAns.add(TUREFALSE.get(i).get("MarkedANS"));

                                                    if (TUREFALSE.get(i).get("ans").equals(TUREFALSE.get(i).get("MarkedANS")) && !TUREFALSE.get(i).get("ans").equals("")) {
                                                        TRtotal = TRtotal + marks_tf;
                                                    }
                                                }
                                                saveArrayTFList(trueFalseMarkedAns, "trueFalse_report");
                                                Log.d("trueFalseMarkedAns", String.valueOf(trueFalseMarkedAns));

                                                //todo MCQ
                                                ArrayList<String> mcqMarkedAns = new ArrayList<>();
                                                for (int i = 0; i < QuestiojnMCQ.size(); i++) {
                                                    mcqMarkedAns.add(QuestiojnMCQ.get(i).get("MarkedANS"));
                                                    if (QuestiojnMCQ.get(i).get("ans").equals(QuestiojnMCQ.get(i).get("MarkedANS")) && !QuestiojnMCQ.get(i).get("ans").equals("")) {
                                                        MCQToatal = MCQToatal + marks_mcq;
                                                    }
                                                } //todo MCQ
                                                saveArrayMCQList(mcqMarkedAns, "mcq_report");
                                                //Log.d("mcqMarkedAns", String.valueOf(mcqMarkedAns));
                                                /*for (int i = 0; i < QuestionMCol.size(); i++) {
                                                    if (QuestionMCol.get(i).get("ans").equals(QuestionMCol.get(i).get("MarkedANS"))) {
                                                        MCQToatal = MCQToatal + 1;
                                                    } else {
                                                        MCQToatal = MCQToatal + 0;
                                                    }
                                                }*/
                                                //MatchColMarks
                                                isShowCalled = 1;
                                                Log.d("ansvalue", QuestionMCol.get(0).get("ans1") + "");
                                                Log.d("eTr1value", eTr1.getText().toString() + "");

                                                Log.d("Arrays", "FLI " + FilTotal + "MCQ  " + MCQToatal + "TRF " + TRtotal);
                                                final Dialog dialog = new Dialog(StudentTest.this);
                                                dialog.setContentView(R.layout.complatedialog);
                                                dialog.setTitle("Custom Dialog");
                                                dialog.setCancelable(false);
                                                dialog.show();
                                                TextView Test_Score_message = (TextView) dialog.findViewById(R.id.Test_Score_message);
                                                if (ALLtotal == 20) {
                                                    Test_Score_message.setText("");
                                                } else if (ALLtotal == 15) {
                                                    Test_Score_message.setText("");
                                                } else if (ALLtotal < 10) {

                                                }
                                                ALLtotal = MCQToatal + TRtotal + FilTotal + MatchColMarks * 2;
                                                Log.d("TotalMarks", String.valueOf(ALLtotal));
                                                //int tot = FILLINTHEBLANKS.size() + TUREFALSE.size() + QuestiojnMCQ.size() + match_col_size;
                                                //TODO : Here dispMarks is Match Column Size
                                                final int tot = mcq_size + fill_in_blank_size + tf_size + dispMarks;
                                                //int TotalCount = OBJMarksCount + TRUEFALSEmarksCount + FillIntheblancksmarkscount;
                                                TextView TotalmArks = (TextView) dialog.findViewById(R.id.TotalMarks);
                                                TextView gotMarks = (TextView) dialog.findViewById(R.id.gottmarks);
                                                TextView Papername = (TextView) dialog.findViewById(R.id.Papername);
                                                gotMarks.setText("" + String.valueOf(ALLtotal));
                                                Papername.setText("" + Subject);
                                                TotalmArks.setText("" + String.valueOf(totalTestMarks));
                                                Button submitbutton = (Button) dialog.findViewById(R.id.submitbutton);
                                                Button cancelbutton = (Button) dialog.findViewById(R.id.cancelbutton);
                                                final int finalALLtotal = ALLtotal;
                                                submitbutton.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        progressDialog = new ProgressDialog(StudentTest.this);
                                                        progressDialog.setMessage("Loading..."); // Setting Title
                                                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                                        progressDialog.show();
                                                        progressDialog.setCancelable(false);
                                                        /*ArrayList<String> user_list = new ArrayList<>();
                                                        user_list.add(eTr1.getText().toString().isEmpty() ? "N" : eTr1.getText().toString());
                                                        user_list.add(eTr2.getText().toString().isEmpty() ? "N" : eTr2.getText().toString());
                                                        user_list.add(eTr3.getText().toString().isEmpty() ? "N" : eTr3.getText().toString());
                                                        user_list.add(eTr4.getText().toString().isEmpty() ? "N" : eTr4.getText().toString());
                                                        user_list.add(eTr5.getText().toString().isEmpty() ? "N" : eTr5.getText().toString());
                                                        user_list.add(eTr6.getText().toString().isEmpty() ? "N" : eTr6.getText().toString());
                                                        user_list.add(eTr7.getText().toString().isEmpty() ? "N" : eTr7.getText().toString());
                                                        user_list.add(eTr8.getText().toString().isEmpty() ? "N" : eTr8.getText().toString());
                                                        saveArrayUserMColList(user_list, "user_response_mcol");*/
                                                        reqToSubmitTest(CommonMethods.getId(StudentTest.this), "chapter", String.valueOf(totalTestMarks), String.valueOf(finalALLtotal), dialog);
                                                    }
                                                });
                                                cancelbutton.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        dialog.dismiss();
                                                        finish();
                                                    }
                                                });
                                            } catch (ArrayIndexOutOfBoundsException ex) {
                                                Log.d("ex", String.valueOf(ex));
                                            }

                                            /*===========================================================================================================================================*/

                                        } else if (mColValue < QuestionMCol.size()) {

                                            txtQnoMCol.setText(String.valueOf(QuestionMCol.get(mColValue).get("sno")));
                                            eTr1.setText(QuestionMCol.size() >= mColValue ? QuestionMCol.get(mColValue).get("MarkedANS1") : "");
                                            eTr2.setText(QuestionMCol.size() >= mColValue ? QuestionMCol.get(mColValue).get("MarkedANS2") : "");
                                            eTr3.setText(QuestionMCol.size() >= mColValue ? QuestionMCol.get(mColValue).get("MarkedANS3") : "");
                                            eTr4.setText(QuestionMCol.size() >= mColValue ? QuestionMCol.get(mColValue).get("MarkedANS4") : "");
                                            eTr5.setText(QuestionMCol.size() >= mColValue ? QuestionMCol.get(mColValue).get("MarkedANS5") : "");
                                            eTr6.setText(QuestionMCol.size() >= mColValue ? QuestionMCol.get(mColValue).get("MarkedANS6") : "");
                                            eTr7.setText(QuestionMCol.size() >= mColValue ? QuestionMCol.get(mColValue).get("MarkedANS7") : "");
                                            eTr8.setText(QuestionMCol.size() >= mColValue ? QuestionMCol.get(mColValue).get("MarkedANS8") : "");
                                            Log.d("QuestionMCol_val1", QuestionMCol.toString());

                                            /*QuestionMCol.get(mColValue).put("MarkedANS1", eTr1.getText().toString());
                                            QuestionMCol.get(mColValue).put("MarkedANS2", eTr2.getText().toString());
                                            QuestionMCol.get(mColValue).put("MarkedANS3", eTr3.getText().toString());
                                            QuestionMCol.get(mColValue).put("MarkedANS4", eTr4.getText().toString());
                                            QuestionMCol.get(mColValue).put("MarkedANS5", eTr5.getText().toString());
                                            QuestionMCol.get(mColValue).put("MarkedANS6", eTr6.getText().toString());
                                            QuestionMCol.get(mColValue).put("MarkedANS7", eTr7.getText().toString());
                                            QuestionMCol.get(mColValue).put("MarkedANS8", eTr8.getText().toString());

                                            Log.d("QuestionMCol_val", QuestionMCol.toString());
                                            Log.d("QuestionMCol_vcnt11", QuestionMCol.size() + "\n" + mColValue);*/

                                            /*-------------------------------------------------------------------------------------------------*/
                                            //region check answer
                                            String r1 = QuestionMCol.get(mColValue).get("column2r1");
                                            String r2 = QuestionMCol.get(mColValue).get("column2r2");
                                            String r3 = QuestionMCol.get(mColValue).get("column2r3");
                                            String r4 = QuestionMCol.get(mColValue).get("column2r4");
                                            String r5 = QuestionMCol.get(mColValue).get("column2r5");
                                            String r6 = QuestionMCol.get(mColValue).get("column2r6");
                                            String r7 = QuestionMCol.get(mColValue).get("column2r7");
                                            String r8 = QuestionMCol.get(mColValue).get("column2r8");

                                            String a1 = QuestionMCol.get(mColValue).get("ans1");
                                            String a2 = QuestionMCol.get(mColValue).get("ans2");
                                            String a3 = QuestionMCol.get(mColValue).get("ans3");
                                            String a4 = QuestionMCol.get(mColValue).get("ans4");
                                            String a5 = QuestionMCol.get(mColValue).get("ans5");
                                            String a6 = QuestionMCol.get(mColValue).get("ans6");
                                            String a7 = QuestionMCol.get(mColValue).get("ans7");
                                            String a8 = QuestionMCol.get(mColValue).get("ans8");

                                            Log.d("qmcol", "" + QuestiojnMCQ.toString());
                                            if (QuestionMCol != null && QuestionMCol.size() > 0) {
                                                //region "check a"
                                                if (!r1.equals("") && !a1.equals("") && a1.equals(r1)) {
                                                    answer1 = "a";
                                                }
                                                if (!r2.equals("") && !a1.equals("") && a1.equals(r2)) {
                                                    answer1 = "b";
                                                }
                                                if (!r3.equals("") && !a1.equals("") && a1.equals(r3)) {
                                                    answer1 = "c";
                                                }
                                                if (!r4.equals("") && !a1.equals("") && a1.equals(r4)) {
                                                    answer1 = "d";
                                                }
                                                if (!r5.equals("") && !a1.equals("") && a1.equals(r5)) {
                                                    answer1 = "e";
                                                }
                                                if (!r6.equals("") && !a1.equals("") && a1.equals(r6)) {
                                                    answer1 = "f";
                                                }
                                                if (!r7.equals("") && !a1.equals("") && a1.equals(r7)) {
                                                    answer1 = "g";
                                                }
                                                if (!r8.equals("") && !a1.equals("") && a1.equals(r8)) {
                                                    answer1 = "h";
                                                }
                                                //endregion
                                                //region "check b"
                                                if (!r1.equals("") && !a2.equals("") && a2.equals(r1)) {
                                                    answer2 = "a";
                                                }
                                                if (!r2.equals("") && !a2.equals("") && a2.equals(r2)) {
                                                    answer2 = "b";
                                                }
                                                if (!r3.equals("") && !a2.equals("") && a2.equals(r3)) {
                                                    answer2 = "c";
                                                }
                                                if (!r4.equals("") && !a2.equals("") && a2.equals(r4)) {
                                                    answer2 = "d";
                                                }
                                                if (!r5.equals("") && !a2.equals("") && a2.equals(r5)) {
                                                    answer2 = "e";
                                                }
                                                if (!r6.equals("") && !a2.equals("") && a2.equals(r6)) {
                                                    answer2 = "f";
                                                }
                                                if (!r7.equals("") && !a2.equals("") && a2.equals(r7)) {
                                                    answer2 = "g";
                                                }
                                                if (!r8.equals("") && !a2.equals("") && a2.equals(r8)) {
                                                    answer2 = "h";
                                                }
                                                //endregion
                                                //region "check c"
                                                if (!r1.equals("") && !a3.equals("") && a3.equals(r1)) {
                                                    answer3 = "a";
                                                }
                                                if (!r2.equals("") && !a3.equals("") && a3.equals(r2)) {
                                                    answer3 = "b";
                                                }
                                                if (!r3.equals("") && !a3.equals("") && a3.equals(r3)) {
                                                    answer3 = "c";
                                                }
                                                if (!r4.equals("") && !a3.equals("") && a3.equals(r4)) {
                                                    answer3 = "d";
                                                }
                                                if (!r5.equals("") && !a3.equals("") && a3.equals(r5)) {
                                                    answer3 = "e";
                                                }
                                                if (!r6.equals("") && !a3.equals("") && a3.equals(r6)) {
                                                    answer3 = "f";
                                                }
                                                if (!r7.equals("") && !a3.equals("") && a3.equals(r7)) {
                                                    answer3 = "g";
                                                }
                                                if (!r8.equals("") && !a3.equals("") && a3.equals(r8)) {
                                                    answer3 = "h";
                                                }//endregion
                                                //region "check d"
                                                if (!r1.equals("") && !a4.equals("") && a4.equals(r1)) {
                                                    answer4 = "a";
                                                }
                                                if (!r2.equals("") && !a4.equals("") && a4.equals(r2)) {
                                                    answer4 = "b";
                                                }
                                                if (!r3.equals("") && !a4.equals("") && a4.equals(r3)) {
                                                    answer4 = "c";
                                                }
                                                if (!r4.equals("") && !a4.equals("") && a4.equals(r4)) {
                                                    answer4 = "d";
                                                }
                                                if (!r5.equals("") && !a4.equals("") && a4.equals(r5)) {
                                                    answer4 = "e";
                                                }
                                                if (!r6.equals("") && !a4.equals("") && a4.equals(r6)) {
                                                    answer4 = "f";
                                                }
                                                if (!r7.equals("") && !a4.equals("") && a4.equals(r7)) {
                                                    answer4 = "g";
                                                }
                                                if (!r8.equals("") && !a4.equals("") && a4.equals(r8)) {
                                                    answer4 = "h";
                                                }//endregion
                                                //region "check e"
                                                if (!r1.equals("") && !a5.equals("") && a5.equals(r1)) {
                                                    answer5 = "a";
                                                }
                                                if (!r2.equals("") && !a5.equals("") && a5.equals(r2)) {
                                                    answer5 = "b";
                                                }
                                                if (!r3.equals("") && !a5.equals("") && a5.equals(r3)) {
                                                    answer5 = "c";
                                                }
                                                if (!r4.equals("") && !a5.equals("") && a5.equals(r4)) {
                                                    answer5 = "d";
                                                }
                                                if (!r5.equals("") && !a5.equals("") && a5.equals(r5)) {
                                                    answer5 = "e";
                                                }
                                                if (!r6.equals("") && !a5.equals("") && a5.equals(r6)) {
                                                    answer5 = "f";
                                                }
                                                if (!r7.equals("") && !a5.equals("") && a5.equals(r7)) {
                                                    answer5 = "g";
                                                }
                                                if (!r8.equals("") && !a5.equals("") && a5.equals(r8)) {
                                                    answer5 = "h";
                                                }//endregion
                                                //region "check f"
                                                if (!r1.equals("") && !a6.equals("") && a6.equals(r1)) {
                                                    answer6 = "a";
                                                }
                                                if (!r2.equals("") && !a6.equals("") && a6.equals(r2)) {
                                                    answer6 = "b";
                                                }
                                                if (!r3.equals("") && !a6.equals("") && a6.equals(r3)) {
                                                    answer6 = "c";
                                                }
                                                if (!r4.equals("") && !a6.equals("") && a6.equals(r4)) {
                                                    answer6 = "d";
                                                }
                                                if (!r5.equals("") && !a6.equals("") && a6.equals(r5)) {
                                                    answer6 = "e";
                                                }
                                                if (!r6.equals("") && !a6.equals("") && a6.equals(r6)) {
                                                    answer6 = "f";
                                                }
                                                if (!r7.equals("") && !a6.equals("") && a6.equals(r7)) {
                                                    answer6 = "g";
                                                }
                                                if (!r8.equals("") && !a6.equals("") && a6.equals(r8)) {
                                                    answer6 = "h";
                                                }///endregion
                                                //region "check f"
                                                if (!r1.equals("") && !a7.equals("") && a7.equals(r1)) {
                                                    answer7 = "a";
                                                }
                                                if (!r2.equals("") && !a7.equals("") && a7.equals(r2)) {
                                                    answer7 = "b";
                                                }
                                                if (!r3.equals("") && !a7.equals("") && a7.equals(r3)) {
                                                    answer7 = "c";
                                                }
                                                if (!r4.equals("") && !a7.equals("") && a7.equals(r4)) {
                                                    answer7 = "d";
                                                }
                                                if (!r5.equals("") && !a7.equals("") && a7.equals(r5)) {
                                                    answer7 = "e";
                                                }
                                                if (!r6.equals("") && !a7.equals("") && a7.equals(r6)) {
                                                    answer7 = "f";
                                                }
                                                if (!r7.equals("") && !a7.equals("") && a7.equals(r7)) {
                                                    answer7 = "g";
                                                }
                                                if (!r8.equals("") && !a7.equals("") && a7.equals(r8)) {
                                                    answer7 = "h";
                                                }///endregion
                                                //region "check g"
                                                if (!r1.equals("") && !a8.equals("") && a8.equals(r1)) {
                                                    answer8 = "a";
                                                }
                                                if (!r2.equals("") && !a8.equals("") && a8.equals(r2)) {
                                                    answer8 = "b";
                                                }
                                                if (!r3.equals("") && !a8.equals("") && a8.equals(r3)) {
                                                    answer8 = "c";
                                                }
                                                if (!r4.equals("") && !a8.equals("") && a8.equals(r4)) {
                                                    answer8 = "d";
                                                }
                                                if (!r5.equals("") && !a8.equals("") && a8.equals(r5)) {
                                                    answer8 = "e";
                                                }
                                                if (!r6.equals("") && !a8.equals("") && a8.equals(r6)) {
                                                    answer8 = "f";
                                                }
                                                if (!r7.equals("") && !a8.equals("") && a8.equals(r7)) {
                                                    answer8 = "g";
                                                }
                                                if (!r8.equals("") && !a8.equals("") && a8.equals(r8)) {
                                                    answer8 = "h";
                                                }//endregion
                                            }//endregion
                                            ArrayList<String> list = new ArrayList<>();
                                            list.add(answer1);
                                            list.add(answer2);
                                            list.add(answer3);
                                            list.add(answer4);
                                            list.add(answer5);
                                            list.add(answer6);
                                            list.add(answer7);
                                            list.add(answer8);

                                            Log.d("mc_response", answer1 +
                                                    "\n" + answer2 +
                                                    "\n" + answer3 +
                                                    "\n" + answer4 +
                                                    "\n" + answer5 +
                                                    "\n" + answer6 +
                                                    "\n" + answer7 +
                                                    "\n" + answer8);
                                            //region "SetText MCol"
                                            //TODO : TextView set text below txtQnoMCol

                                            txtCol1r1.setText("1.\t" + QuestionMCol.get(mColValue).get("column1r1"));
                                            txtCol1r2.setText("2.\t" + QuestionMCol.get(mColValue).get("column1r2"));
                                            txtCol1r3.setText("3.\t" + QuestionMCol.get(mColValue).get("column1r3"));
                                            txtCol1r4.setText("4.\t" + QuestionMCol.get(mColValue).get("column1r4"));
                                            txtCol1r5.setText("5.\t" + QuestionMCol.get(mColValue).get("column1r5"));
                                            txtCol1r6.setText("6.\t" + QuestionMCol.get(mColValue).get("column1r6"));
                                            txtCol1r7.setText("7.\t" + QuestionMCol.get(mColValue).get("column1r7"));
                                            txtCol1r8.setText("8.\t" + QuestionMCol.get(mColValue).get("column1r8"));

                                            txtCol2r1.setText(QuestionMCol.get(mColValue).get("column2r1"));
                                            txtCol2r2.setText(QuestionMCol.get(mColValue).get("column2r2"));
                                            txtCol2r3.setText(QuestionMCol.get(mColValue).get("column2r3"));
                                            txtCol2r4.setText(QuestionMCol.get(mColValue).get("column2r4"));
                                            txtCol2r5.setText(QuestionMCol.get(mColValue).get("column2r5"));
                                            txtCol2r6.setText(QuestionMCol.get(mColValue).get("column2r6"));
                                            txtCol2r7.setText(QuestionMCol.get(mColValue).get("column2r7"));
                                            txtCol2r8.setText(QuestionMCol.get(mColValue).get("column2r8"));

                                            llMCol1.setVisibility(!QuestionMCol.get(mColValue).get("column1r1").equals("") ? View.VISIBLE : View.GONE);
                                            llMCol2.setVisibility(!QuestionMCol.get(mColValue).get("column1r2").equals("") ? View.VISIBLE : View.GONE);
                                            llMCol3.setVisibility(!QuestionMCol.get(mColValue).get("column1r3").equals("") ? View.VISIBLE : View.GONE);
                                            llMCol4.setVisibility(!QuestionMCol.get(mColValue).get("column1r4").equals("") ? View.VISIBLE : View.GONE);
                                            llMCol5.setVisibility(!QuestionMCol.get(mColValue).get("column1r5").equals("") ? View.VISIBLE : View.GONE);
                                            llMCol6.setVisibility(!QuestionMCol.get(mColValue).get("column1r6").equals("") ? View.VISIBLE : View.GONE);
                                            llMCol7.setVisibility(!QuestionMCol.get(mColValue).get("column1r7").equals("") ? View.VISIBLE : View.GONE);
                                            llMCol8.setVisibility(!QuestionMCol.get(mColValue).get("column1r8").equals("") ? View.VISIBLE : View.GONE);
                                            //endregion
                                            //txtQnoMCol.setText(String.valueOf(QuestionMCol.get(mColValue).get("sno")));
                                            /*-------------------------------------------------------------------------------------------------*/

                                        }
                                    }
                                });//endregion
                                //region "BackButtonfillintheblank"
                                //TODO work
                                backButtonMCol.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        /*----------------------------------------------------------------------------------------------------------*/

                                        try {
                                            //mCol = mCol - 1;
                                            if (mColValue > 0) {

                                                QuestionMCol.get(mColValue).put("MarkedANS1", eTr1.getText().toString());
                                                QuestionMCol.get(mColValue).put("MarkedANS2", eTr2.getText().toString());
                                                QuestionMCol.get(mColValue).put("MarkedANS3", eTr3.getText().toString());
                                                QuestionMCol.get(mColValue).put("MarkedANS4", eTr4.getText().toString());
                                                QuestionMCol.get(mColValue).put("MarkedANS5", eTr5.getText().toString());
                                                QuestionMCol.get(mColValue).put("MarkedANS6", eTr6.getText().toString());
                                                QuestionMCol.get(mColValue).put("MarkedANS7", eTr7.getText().toString());
                                                QuestionMCol.get(mColValue).put("MarkedANS8", eTr8.getText().toString());

                                                mColValue = mColValue - 1;
                                                eTr1.setText(QuestionMCol.size() >= mColValue ? QuestionMCol.get(mColValue).get("MarkedANS1") : "");
                                                eTr2.setText(QuestionMCol.size() >= mColValue ? QuestionMCol.get(mColValue).get("MarkedANS2") : "");
                                                eTr3.setText(QuestionMCol.size() >= mColValue ? QuestionMCol.get(mColValue).get("MarkedANS3") : "");
                                                eTr4.setText(QuestionMCol.size() >= mColValue ? QuestionMCol.get(mColValue).get("MarkedANS4") : "");
                                                eTr5.setText(QuestionMCol.size() >= mColValue ? QuestionMCol.get(mColValue).get("MarkedANS5") : "");
                                                eTr6.setText(QuestionMCol.size() >= mColValue ? QuestionMCol.get(mColValue).get("MarkedANS6") : "");
                                                eTr7.setText(QuestionMCol.size() >= mColValue ? QuestionMCol.get(mColValue).get("MarkedANS7") : "");
                                                eTr8.setText(QuestionMCol.size() >= mColValue ? QuestionMCol.get(mColValue).get("MarkedANS8") : "");
                                                Log.d("QuestionMCol_val15", QuestionMCol.toString());

                                                //TODO : Add Dynamic data
                                                //region check answer
                                                String r1 = QuestionMCol.get(mColValue).get("column2r1");
                                                String r2 = QuestionMCol.get(mColValue).get("column2r2");
                                                String r3 = QuestionMCol.get(mColValue).get("column2r3");
                                                String r4 = QuestionMCol.get(mColValue).get("column2r4");
                                                String r5 = QuestionMCol.get(mColValue).get("column2r5");
                                                String r6 = QuestionMCol.get(mColValue).get("column2r6");
                                                String r7 = QuestionMCol.get(mColValue).get("column2r7");
                                                String r8 = QuestionMCol.get(mColValue).get("column2r8");

                                                String a1 = QuestionMCol.get(mColValue).get("ans1");
                                                String a2 = QuestionMCol.get(mColValue).get("ans2");
                                                String a3 = QuestionMCol.get(mColValue).get("ans3");
                                                String a4 = QuestionMCol.get(mColValue).get("ans4");
                                                String a5 = QuestionMCol.get(mColValue).get("ans5");
                                                String a6 = QuestionMCol.get(mColValue).get("ans6");
                                                String a7 = QuestionMCol.get(mColValue).get("ans7");
                                                String a8 = QuestionMCol.get(mColValue).get("ans8");
                                                /*//region "display marks"
                                                int match_mark = 0;
                                                if (r1 != null && !r1.isEmpty()) {
                                                    match_mark += marks_mCol;
                                                }
                                                if (r2 != null && !r2.isEmpty()) {
                                                    match_mark += marks_mCol;
                                                }
                                                if (r3 != null && !r3.isEmpty()) {
                                                    match_mark += marks_mCol;
                                                }
                                                if (r4 != null && !r4.isEmpty()) {
                                                    match_mark += marks_mCol;
                                                }
                                                if (r5 != null && !r5.isEmpty()) {
                                                    match_mark += marks_mCol;
                                                }
                                                if (r6 != null && !r6.isEmpty()) {
                                                    match_mark += marks_mCol;
                                                }
                                                if (r7 != null && !r7.isEmpty()) {
                                                    match_mark += marks_mCol;
                                                }
                                                if (r8 != null && !r8.isEmpty()) {
                                                    match_mark += marks_mCol;
                                                }//endregion
                                                txtTotalMarksMCol.setText("Marks : " + match_mark);*/
                                                Log.d("qmcol", "" + QuestiojnMCQ.toString());
                                                if (QuestionMCol != null && QuestionMCol.size() > 0) {
                                                    //region "check a"
                                                    if (!r1.equals("") && !a1.equals("") && a1.equals(r1)) {
                                                        answer1 = "a";
                                                    }
                                                    if (!r2.equals("") && !a1.equals("") && a1.equals(r2)) {
                                                        answer1 = "b";
                                                    }
                                                    if (!r3.equals("") && !a1.equals("") && a1.equals(r3)) {
                                                        answer1 = "c";
                                                    }
                                                    if (!r4.equals("") && !a1.equals("") && a1.equals(r4)) {
                                                        answer1 = "d";
                                                    }
                                                    if (!r5.equals("") && !a1.equals("") && a1.equals(r5)) {
                                                        answer1 = "e";
                                                    }
                                                    if (!r6.equals("") && !a1.equals("") && a1.equals(r6)) {
                                                        answer1 = "f";
                                                    }
                                                    if (!r7.equals("") && !a1.equals("") && a1.equals(r7)) {
                                                        answer1 = "g";
                                                    }
                                                    if (!r8.equals("") && !a1.equals("") && a1.equals(r8)) {
                                                        answer1 = "h";
                                                    }
                                                    //endregion
                                                    //region "check b"
                                                    if (!r1.equals("") && !a2.equals("") && a2.equals(r1)) {
                                                        answer2 = "a";
                                                    }
                                                    if (!r2.equals("") && !a2.equals("") && a2.equals(r2)) {
                                                        answer2 = "b";
                                                    }
                                                    if (!r3.equals("") && !a2.equals("") && a2.equals(r3)) {
                                                        answer2 = "c";
                                                    }
                                                    if (!r4.equals("") && !a2.equals("") && a2.equals(r4)) {
                                                        answer2 = "d";
                                                    }
                                                    if (!r5.equals("") && !a2.equals("") && a2.equals(r5)) {
                                                        answer2 = "e";
                                                    }
                                                    if (!r6.equals("") && !a2.equals("") && a2.equals(r6)) {
                                                        answer2 = "f";
                                                    }
                                                    if (!r7.equals("") && !a2.equals("") && a2.equals(r7)) {
                                                        answer2 = "g";
                                                    }
                                                    if (!r8.equals("") && !a2.equals("") && a2.equals(r8)) {
                                                        answer2 = "h";
                                                    }
                                                    //endregion
                                                    //region "check c"
                                                    if (!r1.equals("") && !a3.equals("") && a3.equals(r1)) {
                                                        answer3 = "a";
                                                    }
                                                    if (!r2.equals("") && !a3.equals("") && a3.equals(r2)) {
                                                        answer3 = "b";
                                                    }
                                                    if (!r3.equals("") && !a3.equals("") && a3.equals(r3)) {
                                                        answer3 = "c";
                                                    }
                                                    if (!r4.equals("") && !a3.equals("") && a3.equals(r4)) {
                                                        answer3 = "d";
                                                    }
                                                    if (!r5.equals("") && !a3.equals("") && a3.equals(r5)) {
                                                        answer3 = "e";
                                                    }
                                                    if (!r6.equals("") && !a3.equals("") && a3.equals(r6)) {
                                                        answer3 = "f";
                                                    }
                                                    if (!r7.equals("") && !a3.equals("") && a3.equals(r7)) {
                                                        answer3 = "g";
                                                    }
                                                    if (!r8.equals("") && !a3.equals("") && a3.equals(r8)) {
                                                        answer3 = "h";
                                                    }//endregion
                                                    //region "check d"
                                                    if (!r1.equals("") && !a4.equals("") && a4.equals(r1)) {
                                                        answer4 = "a";
                                                    }
                                                    if (!r2.equals("") && !a4.equals("") && a4.equals(r2)) {
                                                        answer4 = "b";
                                                    }
                                                    if (!r3.equals("") && !a4.equals("") && a4.equals(r3)) {
                                                        answer4 = "c";
                                                    }
                                                    if (!r4.equals("") && !a4.equals("") && a4.equals(r4)) {
                                                        answer4 = "d";
                                                    }
                                                    if (!r5.equals("") && !a4.equals("") && a4.equals(r5)) {
                                                        answer4 = "e";
                                                    }
                                                    if (!r6.equals("") && !a4.equals("") && a4.equals(r6)) {
                                                        answer4 = "f";
                                                    }
                                                    if (!r7.equals("") && !a4.equals("") && a4.equals(r7)) {
                                                        answer4 = "g";
                                                    }
                                                    if (!r8.equals("") && !a4.equals("") && a4.equals(r8)) {
                                                        answer4 = "h";
                                                    }//endregion
                                                    //region "check e"
                                                    if (!r1.equals("") && !a5.equals("") && a5.equals(r1)) {
                                                        answer5 = "a";
                                                    }
                                                    if (!r2.equals("") && !a5.equals("") && a5.equals(r2)) {
                                                        answer5 = "b";
                                                    }
                                                    if (!r3.equals("") && !a5.equals("") && a5.equals(r3)) {
                                                        answer5 = "c";
                                                    }
                                                    if (!r4.equals("") && !a5.equals("") && a5.equals(r4)) {
                                                        answer5 = "d";
                                                    }
                                                    if (!r5.equals("") && !a5.equals("") && a5.equals(r5)) {
                                                        answer5 = "e";
                                                    }
                                                    if (!r6.equals("") && !a5.equals("") && a5.equals(r6)) {
                                                        answer5 = "f";
                                                    }
                                                    if (!r7.equals("") && !a5.equals("") && a5.equals(r7)) {
                                                        answer5 = "g";
                                                    }
                                                    if (!r8.equals("") && !a5.equals("") && a5.equals(r8)) {
                                                        answer5 = "h";
                                                    }//endregion
                                                    //region "check f"
                                                    if (!r1.equals("") && !a6.equals("") && a6.equals(r1)) {
                                                        answer6 = "a";
                                                    }
                                                    if (!r2.equals("") && !a6.equals("") && a6.equals(r2)) {
                                                        answer6 = "b";
                                                    }
                                                    if (!r3.equals("") && !a6.equals("") && a6.equals(r3)) {
                                                        answer6 = "c";
                                                    }
                                                    if (!r4.equals("") && !a6.equals("") && a6.equals(r4)) {
                                                        answer6 = "d";
                                                    }
                                                    if (!r5.equals("") && !a6.equals("") && a6.equals(r5)) {
                                                        answer6 = "e";
                                                    }
                                                    if (!r6.equals("") && !a6.equals("") && a6.equals(r6)) {
                                                        answer6 = "f";
                                                    }
                                                    if (!r7.equals("") && !a6.equals("") && a6.equals(r7)) {
                                                        answer6 = "g";
                                                    }
                                                    if (!r8.equals("") && !a6.equals("") && a6.equals(r8)) {
                                                        answer6 = "h";
                                                    }///endregion
                                                    //region "check f"
                                                    if (!r1.equals("") && !a7.equals("") && a7.equals(r1)) {
                                                        answer7 = "a";
                                                    }
                                                    if (!r2.equals("") && !a7.equals("") && a7.equals(r2)) {
                                                        answer7 = "b";
                                                    }
                                                    if (!r3.equals("") && !a7.equals("") && a7.equals(r3)) {
                                                        answer7 = "c";
                                                    }
                                                    if (!r4.equals("") && !a7.equals("") && a7.equals(r4)) {
                                                        answer7 = "d";
                                                    }
                                                    if (!r5.equals("") && !a7.equals("") && a7.equals(r5)) {
                                                        answer7 = "e";
                                                    }
                                                    if (!r6.equals("") && !a7.equals("") && a7.equals(r6)) {
                                                        answer7 = "f";
                                                    }
                                                    if (!r7.equals("") && !a7.equals("") && a7.equals(r7)) {
                                                        answer7 = "g";
                                                    }
                                                    if (!r8.equals("") && !a7.equals("") && a7.equals(r8)) {
                                                        answer7 = "h";
                                                    }///endregion
                                                    //region "check g"
                                                    if (!r1.equals("") && !a8.equals("") && a8.equals(r1)) {
                                                        answer8 = "a";
                                                    }
                                                    if (!r2.equals("") && !a8.equals("") && a8.equals(r2)) {
                                                        answer8 = "b";
                                                    }
                                                    if (!r3.equals("") && !a8.equals("") && a8.equals(r3)) {
                                                        answer8 = "c";
                                                    }
                                                    if (!r4.equals("") && !a8.equals("") && a8.equals(r4)) {
                                                        answer8 = "d";
                                                    }
                                                    if (!r5.equals("") && !a8.equals("") && a8.equals(r5)) {
                                                        answer8 = "e";
                                                    }
                                                    if (!r6.equals("") && !a8.equals("") && a8.equals(r6)) {
                                                        answer8 = "f";
                                                    }
                                                    if (!r7.equals("") && !a8.equals("") && a8.equals(r7)) {
                                                        answer8 = "g";
                                                    }
                                                    if (!r8.equals("") && !a8.equals("") && a8.equals(r8)) {
                                                        answer8 = "h";
                                                    }//endregion
                                                }//endregion
                                                ArrayList<String> list = new ArrayList<>();
                                                list.add(answer1);
                                                list.add(answer2);
                                                list.add(answer3);
                                                list.add(answer4);
                                                list.add(answer5);
                                                list.add(answer6);
                                                list.add(answer7);
                                                list.add(answer8);

                                                HashMap<String, String> map = new HashMap<>();
                                                StringBuilder stringBuilder = new StringBuilder();
                                                for (int i = 0; i < list.size(); i++) {
                                                    if (!list.get(i).isEmpty()) {
                                                        stringBuilder.append(list.get(i) + ",");
                                                    }
                                                }
                                                Log.d("string_builder", "" + stringBuilder);
                                                if (stringBuilder.length() > 0 && stringBuilder.subSequence(stringBuilder.length() - 1, stringBuilder.length()).equals(",")) {
                                                    stringBuilder.subSequence(0, stringBuilder.length() - 1);
                                                }
                                                map.put("m_answers", String.valueOf(stringBuilder));

                                                //map.put("m_answers", String.valueOf(stringBuilder));

                                                for (String str : stringBuilder.toString().split(",")) {
                                                    Log.d("m_answers", "" + str);
                                                }


                                                //   saveArrayMColList(list, "response_mcol");

                                                Log.d("mc_response", answer1 +
                                                        "\n" + answer2 +
                                                        "\n" + answer3 +
                                                        "\n" + answer4 +
                                                        "\n" + answer5 +
                                                        "\n" + answer6 +
                                                        "\n" + answer7 +
                                                        "\n" + answer8);


                                                //region "SetText MCol"
                                                //TODO : TextView set text below txtQnoMCol
                                                txtCol1r1.setText("1.\t" + QuestionMCol.get(mColValue).get("column1r1"));
                                                txtCol1r2.setText("2.\t" + QuestionMCol.get(mColValue).get("column1r2"));
                                                txtCol1r3.setText("3.\t" + QuestionMCol.get(mColValue).get("column1r3"));
                                                txtCol1r4.setText("4.\t" + QuestionMCol.get(mColValue).get("column1r4"));
                                                txtCol1r5.setText("5.\t" + QuestionMCol.get(mColValue).get("column1r5"));
                                                txtCol1r6.setText("6.\t" + QuestionMCol.get(mColValue).get("column1r6"));
                                                txtCol1r7.setText("7.\t" + QuestionMCol.get(mColValue).get("column1r7"));
                                                txtCol1r8.setText("8.\t" + QuestionMCol.get(mColValue).get("column1r8"));

                                                txtCol2r1.setText(QuestionMCol.get(mColValue).get("column2r1"));
                                                txtCol2r2.setText(QuestionMCol.get(mColValue).get("column2r2"));
                                                txtCol2r3.setText(QuestionMCol.get(mColValue).get("column2r3"));
                                                txtCol2r4.setText(QuestionMCol.get(mColValue).get("column2r4"));
                                                txtCol2r5.setText(QuestionMCol.get(mColValue).get("column2r5"));
                                                txtCol2r6.setText(QuestionMCol.get(mColValue).get("column2r6"));
                                                txtCol2r7.setText(QuestionMCol.get(mColValue).get("column2r7"));
                                                txtCol2r8.setText(QuestionMCol.get(mColValue).get("column2r8"));

                                                llMCol1.setVisibility(!QuestionMCol.get(mColValue).get("column1r1").equals("") ? View.VISIBLE : View.GONE);
                                                llMCol2.setVisibility(!QuestionMCol.get(mColValue).get("column1r2").equals("") ? View.VISIBLE : View.GONE);
                                                llMCol3.setVisibility(!QuestionMCol.get(mColValue).get("column1r3").equals("") ? View.VISIBLE : View.GONE);
                                                llMCol4.setVisibility(!QuestionMCol.get(mColValue).get("column1r4").equals("") ? View.VISIBLE : View.GONE);
                                                llMCol5.setVisibility(!QuestionMCol.get(mColValue).get("column1r5").equals("") ? View.VISIBLE : View.GONE);
                                                llMCol6.setVisibility(!QuestionMCol.get(mColValue).get("column1r6").equals("") ? View.VISIBLE : View.GONE);
                                                llMCol7.setVisibility(!QuestionMCol.get(mColValue).get("column1r7").equals("") ? View.VISIBLE : View.GONE);
                                                llMCol8.setVisibility(!QuestionMCol.get(mColValue).get("column1r8").equals("") ? View.VISIBLE : View.GONE);
                                                //endregion
                                                disp_mcol_mrks();
                                                txtQnoMCol.setText(String.valueOf(QuestionMCol.get(mColValue).get("sno")));
                                                /*----------------------------------------------------------------------------------------------------------*/
                                            } else {
                                                objective.setVisibility(View.GONE);
                                                if (fill_in_blank_size != 0) {
                                                    MQCTestLayout.setVisibility(View.GONE);
                                                    TureFasle.setVisibility(View.GONE);
                                                    FillInTheBlancks.setVisibility(View.VISIBLE);
                                                    layoutMColumn.setVisibility(View.GONE);
                                                } else if (fill_in_blank_size == 0 && tf_size != 0) {
                                                    MQCTestLayout.setVisibility(View.GONE);
                                                    TureFasle.setVisibility(View.VISIBLE);
                                                    FillInTheBlancks.setVisibility(View.GONE);
                                                    layoutMColumn.setVisibility(View.GONE);
                                                } else if (fill_in_blank_size == 0 && tf_size == 0 && mcq_size != 0) {
                                                    MQCTestLayout.setVisibility(View.VISIBLE);
                                                    TureFasle.setVisibility(View.GONE);
                                                    FillInTheBlancks.setVisibility(View.GONE);
                                                    layoutMColumn.setVisibility(View.GONE);
                                                } else if (fill_in_blank_size == 0 && tf_size == 0 && mcq_size == 0) {
                                                    MQCTestLayout.setVisibility(View.GONE);
                                                    TureFasle.setVisibility(View.GONE);
                                                    FillInTheBlancks.setVisibility(View.GONE);
                                                    layoutMColumn.setVisibility(View.VISIBLE);
                                                }
                                                main_question_count = main_question_count - 1;
                                                //txtQnoMCol.setText(String.valueOf(main_question_count));
                                            }
                                        } catch (ArrayIndexOutOfBoundsException ex) {
                                            Log.d("ex", String.valueOf(ex));
                                        }
                                    }
                                });//endregion
                                //Log.d("Sssssssslist", String.valueOf(heroArray));//endregion
                                //TODO "Make changes in above in btn"
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("ResponseRegistration", "" + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("accesscodes", "0300081516");
                params.put("ch_id", ChapterID);
                Log.d("getQuestionParams", params.toString());
                return params;
            }
        };
        queueMCol.add(stringRequestMCol);
        //endregion
    }

    private void disp_mcol_mrks() {
        int disp_mcol_mrks = 0;
        if (!QuestionMCol.get(mColValue).get("column1r1").equals("")) {
            disp_mcol_mrks = disp_mcol_mrks + 1;
        }
        if (!QuestionMCol.get(mColValue).get("column1r2").equals("")) {
            disp_mcol_mrks = disp_mcol_mrks + 1;
        }
        if (!QuestionMCol.get(mColValue).get("column1r3").equals("")) {
            disp_mcol_mrks = disp_mcol_mrks + 1;
        }
        if (!QuestionMCol.get(mColValue).get("column1r4").equals("")) {
            disp_mcol_mrks = disp_mcol_mrks + 1;
        }
        if (!QuestionMCol.get(mColValue).get("column1r5").equals("")) {
            disp_mcol_mrks = disp_mcol_mrks + 1;
        }
        if (!QuestionMCol.get(mColValue).get("column1r6").equals("")) {
            disp_mcol_mrks = disp_mcol_mrks + 1;
        }
        if (!QuestionMCol.get(mColValue).get("column1r7").equals("")) {
            disp_mcol_mrks = disp_mcol_mrks + 1;
        }
        if (!QuestionMCol.get(mColValue).get("column1r8").equals("")) {
            disp_mcol_mrks = disp_mcol_mrks + 1;
        }
        txtTotalMarksMCol.setText("Marks :" + String.valueOf(disp_mcol_mrks * 2));
    }

    public void saveArrayMCQList(ArrayList<String> list, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public void saveArrayFILLList(ArrayList<String> list, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public void saveArrayTFList(ArrayList<String> list, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    //region "For right answer"
    public void saveArrayMColList(ArrayList<String> list, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }//endregion

    //region "user answer"
    public void saveArrayUserMColList(ArrayList<String> list, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }//endregion

    private void reqToSubmitTest(final String id, String chapter, final String TotalMarksOfQuestions, final String marksObtained, final Dialog dialog) {
        String url = "";
        if (CommonMethods.getType(StudentTest.this).equals("s")) {
            url = Apis.base_url + Apis.student_test_submit;
        } else if (CommonMethods.getType(StudentTest.this).equals("t")) {
            url = Apis.base_url + Apis.teacher_test_submit;
        }
        //TODO
        Log.d("getType", CommonMethods.getType(StudentTest.this));
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                            dialog.dismiss();
                        }
                        Log.d("validateResponse", response);
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getString("success").equals("1")) {
                                Toast.makeText(StudentTest.this, "Test Completed", Toast.LENGTH_SHORT).show();
                                if (progressDialog != null) {
                                    progressDialog.dismiss();
                                    dialog.dismiss();
                                    finish();
                                }
                                Intent intent = new Intent(StudentTest.this, DisplayTestResultActivity.class);
                                intent.putExtra("ch_id", ChapterID);
                                intent.putExtra("each_mcq_mark", marks_mcq);
                                intent.putExtra("each_tf_mark", marks_tf);
                                intent.putExtra("each_fill_mark", marks_fill);
                                intent.putExtra("each_mcol_mark", dispMarks);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(StudentTest.this, "Something went wrong. Try Again After Sometimes", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (progressDialog != null) {
                                progressDialog.dismiss();
                                dialog.dismiss();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //dialog.dismiss();
                        Log.d("getParamsDatas11", "" + volleyError.getMessage());
                        if (volleyError instanceof NoConnectionError) {
                            Toast.makeText(StudentTest.this, "Internet not Connected", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(StudentTest.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                        }
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                            dialog.dismiss();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("accesscodes", "0300081516");
                if (CommonMethods.getType(StudentTest.this).equals("s")) {
                    params.put("student_id", id);
                } else if (CommonMethods.getType(StudentTest.this).equals("t")) {
                    params.put("teacher_id", id);
                }
                params.put("subject", Subject);
                params.put("chapter", ChapterID);
                params.put("total_marks", TotalMarksOfQuestions);
                params.put("marks", marksObtained);
                params.put("chapter_name", getIntent().getStringExtra("Ch_name") != null ? getIntent().getStringExtra("Ch_name") : "");
                params.put("date", "" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

                Log.d("getParamsDatas", params.toString());
                return params;
            }
        };
        CommonMethods.callWebserviceForResponse(stringRequest, StudentTest.this);
    }//endregion


    public void startCountDown(StudentTest view) {
        //new CountDownTimer(20000, 1000) {
        new CountDownTimer(900000, 1000) {
            //new CountDownTimer(100000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                /*            converting the milliseconds into days, hours, minutes and seconds and displaying it in textviews             */

                String day = TimeUnit.HOURS.toDays(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)) + "";

                String hours = (TimeUnit.MILLISECONDS.toHours(millisUntilFinished) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisUntilFinished))) + "";

                String Minuts = (TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished))) + "";

                Timer.setBackgroundColor(getResources().getColor(R.color.Blackcolor));

                String Seconds = (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))) + "";
                Timer.setText(hours + ":" + Minuts + ":" + "" + Seconds);
                Log.d("Samaya", "hours  " + hours + " Minuts " + Minuts + " Seconds " + Seconds);
            }

            @Override
            public void onFinish() {
                /*            clearing all fields and displaying countdown finished message             */

                Timer.setText("Time Up !");
                Timer.setBackgroundColor(getResources().getColor(R.color.color1));
                if (isActivity && isShowCalled == 0) {
                    showTotalMarks();
                }
                /*final Dialog dialog = new Dialog(StudentTest.this);
                dialog.setContentView(R.layout.complatedialog);
                dialog.setTitle("Custom Dialog");
                try {
                    dialog.show();
                } catch (WindowManager.BadTokenException e) {
                    //use a log message
                }
                int TotalCount = OBJMarksCount + TRUEFALSEmarksCount + FillIntheblancksmarkscount;
                TextView TotalmArks = (TextView) dialog.findViewById(R.id.TotalMarks);
                TotalmArks.setText("you get marks        -   " + String.valueOf(TotalCount));
                Button submitbutton = (Button) dialog.findViewById(R.id.submitbutton);
                submitbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });*/
               /* hours.setText("");
                mins.setText("");
                seconds.setText("");*/
            }
        }.start();
    }

    void showTotalMarks() {
        isShowCalled = 1;
        try {
            int MCQToatal = 0, FilTotal = 0, TRtotal = 0, ALLtotal = 0;
            int MatchColMarks = 0;

            ArrayList<String> fillInTheBlanksMarkedAns = new ArrayList<>();
            if (fill_in_blank_size != 0) {
                for (int i = 0; i < FILLINTHEBLANKS.size(); i++) {
                    fillInTheBlanksMarkedAns.add(FILLINTHEBLANKS.get(i).get("MarkedANS"));
                    if (FILLINTHEBLANKS.get(i).get("ans").equals(FILLINTHEBLANKS.get(i).get("MarkedANS")) && !FILLINTHEBLANKS.get(i).get("ans").equals("")) {
                        FilTotal = FilTotal + marks_fill;
                    }
                }
            }
            saveArrayFILLList(fillInTheBlanksMarkedAns, "fill_report");
            Log.d("fillInMarkedAns1", String.valueOf(fillInTheBlanksMarkedAns));
            //todo True False
            ArrayList<String> trueFalseMarkedAns = new ArrayList<>();
            if (tf_size != 0) {
                for (int i = 0; i < TUREFALSE.size(); i++) {
                    trueFalseMarkedAns.add(TUREFALSE.get(i).get("MarkedANS"));
                    if (TUREFALSE.get(i).get("ans").equals(TUREFALSE.get(i).get("MarkedANS")) && !TUREFALSE.get(i).get("ans").equals("")) {
                        TRtotal = TRtotal + marks_tf;
                    }
                }
            }
            saveArrayTFList(trueFalseMarkedAns, "trueFalse_report");
            Log.d("trueFalseMarkedAns1", String.valueOf(trueFalseMarkedAns));
            //todo MCQ
            ArrayList<String> mcqMarkedAns = new ArrayList<>();
            if (mcq_size != 0) {
                for (int i = 0; i < QuestiojnMCQ.size(); i++) {
                    mcqMarkedAns.add(QuestiojnMCQ.get(i).get("MarkedANS"));
                    if (QuestiojnMCQ.get(i).get("ans").equals(QuestiojnMCQ.get(i).get("MarkedANS")) && !QuestiojnMCQ.get(i).get("ans").equals("")) {
                        MCQToatal = MCQToatal + marks_mcq;
                    }
                }
            }//todo MCQ
            saveArrayMCQList(mcqMarkedAns, "mcq_report");
            Log.d("mcqMarkedAns1", String.valueOf(mcqMarkedAns));

            Log.d("TotalMarks", String.valueOf(ALLtotal));
            Log.d("Arrays", "FLI " + FilTotal + "MCQ  " + MCQToatal + "TRF " + TRtotal);
            final Dialog dialog = new Dialog(StudentTest.this);
            dialog.setContentView(R.layout.complatedialog);
            dialog.setTitle("Custom Dialog");
            dialog.setCancelable(false);
            dialog.show();
            //TextView Test_Score_message = (TextView) dialog.findViewById(R.id.Test_Score_message);

            ALLtotal = MCQToatal + TRtotal + FilTotal + mColTotal;
            /*if (ALLtotal == 20) {
                Test_Score_message.setText("");
            } else if (ALLtotal == 15) {
                Test_Score_message.setText("");
            } else if (ALLtotal < 10) {

            }*/
            final int tot = FILLINTHEBLANKS.size() + TUREFALSE.size() + QuestiojnMCQ.size() + QuestionMCol.size();
            int TotalCount = OBJMarksCount + TRUEFALSEmarksCount + FillIntheblancksmarkscount;
            TextView TotalmArks = (TextView) dialog.findViewById(R.id.TotalMarks);
            TextView gotMarks = (TextView) dialog.findViewById(R.id.gottmarks);
            TextView Papername = (TextView) dialog.findViewById(R.id.Papername);
            gotMarks.setText("" + String.valueOf(ALLtotal));
            Papername.setText("" + Subject);
            TotalmArks.setText("" + String.valueOf(QuestiojnMCQ.size() * marks_mcq + FILLINTHEBLANKS.size() * marks_fill + TUREFALSE.size() * marks_tf + dispMarks));
            //TotalmArks.setText("" + String.valueOf(total));
            Button submitbutton = (Button) dialog.findViewById(R.id.submitbutton);
            Button cancelbutton = (Button) dialog.findViewById(R.id.cancelbutton);
            final int finalALLtotal = ALLtotal;
            submitbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog = new ProgressDialog(StudentTest.this);
                    progressDialog.setMessage("Loading..."); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    reqToSubmitTest(CommonMethods.getId(StudentTest.this), "chapter", String.valueOf(mTotal), String.valueOf(finalALLtotal), dialog);
                }
            });
            cancelbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    finish();
                }
            });
        } catch (ArrayIndexOutOfBoundsException ex) {
            Log.d("ex", String.valueOf(ex));
        }
    }

    boolean isActivity = false;
    int isShowCalled = 0;

    @Override
    protected void onStart() {
        super.onStart();
        isActivity = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isActivity = false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isActivity = false;
    }
}
