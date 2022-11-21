package com.studybuddy.pc.brainmate.teacher;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.studybuddy.pc.brainmate.mains.Apis;
import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.student.CommonMethods;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuetionPaper extends AppCompatActivity {

    /**/
    HashMap<String, String> contact1;
    HashMap<String, String> contact2;
    HashMap<String, String> contact;
    HashMap<String, String> contact3;
    HashMap<String, String> contact4;
    HashMap<String, String> contact5;
    int show_keyboard = 0;
    LinearLayout Subjectivelayout, ObjectiveLayout, ObjectiveplushsubLayout, Fillintheblanklayout, ShortAnswers, MatchMaking, MCQLayout;
    ListView Subjectivelist, Objectivelist, TrueFalse, Fillintheblanklist, ShortAnswerslist, MatchMakinglist, MCQuestionlist;
    Button SubjectiveButton, ObjectiveButton, ObjectiveplushsubButton, FillintheblankButton;
    int ToatlMarks = 0, ToatlMarksLOgnd = 0, TotalMarksObjective = 0, TotalMarksTruefalse = 0, TotalMarksFilblancks = 0, TotalMatchmaikg = 0, TotalMarksMCQ = 0;
    int finalTotalmarks = 0;
    LinearLayout Addbuitton, Optionbutton, check;
    TextView TotalMarksbutton;
    ArrayList<HashMap<String, String>> PatientList;
    ArrayList<HashMap<String, String>> MCQLIST;
    String UpcomigID;
    ArrayList<HashMap<String, String>> longQuesAdapterLog;
    ArrayList<HashMap<String, String>> SubjectiveAdaptershoet;
    ArrayList<HashMap<String, String>> trufalseAdapterlist;
    ArrayList<HashMap<String, String>> fillinTheblanklistt;
    ArrayList<HashMap<String, String>> shortQuestionlist;
    ArrayList<HashMap<String, String>> Matchmakingselected;

    ArrayList<String> LongQuestionID;
    ArrayList<String> ShortQuestionID;
    ArrayList<String> TruefalseQuestionID;
    ArrayList<String> FillQuestionID;
    ArrayList<String> MCQQuestionID;
    ArrayList<String> MatchQuestionID;

    LongQuestionAdapter longQuestionAdapter;
    ShortAnswerAdapter shortAnswerAdapter;
    MatchMakingAdapter matchMakingAdapter;
    MCQAdapter mcqAdapter;

    TureFalseAdapter trufalseAdapter;
    ArrayList<HashMap<String, String>> turefalesAraylist;

    FillinTheblankApter fillinTheblank;
    ArrayList<HashMap<String, String>> fillinTheblanklist;
    TextView LongAnswer, ShortAnswer, MultipleAnswer, fillinblacks, truefalse, MatchMakingT, MCQ;

    String CLASS, Subject, Chapterscount, TotalQuestion, TotalMarks, displayClassName;
    String Chapters_ID, UPComingChapters_ID;

    String LongQM, ShortQM, MultipleQM, TruefalseQM, FillintheblackQM, MatchMakingQM, PaperName, ExamDuration;
    EditText MarksMatchMaking, inputSearch, Marksfor_LongQuestions, Marksfor_ShortQuestions, MarksForQbjective, MarksTurefalse, Marksfillinblacks, MarksMCQ;

    ArrayList<HashMap<String, String>> ch_ques_id = new ArrayList<>();
    ArrayList<String> chapter_id_list = new ArrayList<>();

    ArrayList<String> long_question_id_list = new ArrayList<>();
    ArrayList<String> short_question_id_list = new ArrayList<>();
    ArrayList<String> mcq_question_id_list = new ArrayList<>();
    ArrayList<String> tf_question_id_list = new ArrayList<>();
    ArrayList<String> fill_question_id_list = new ArrayList<>();
    ArrayList<String> match_question_id_list = new ArrayList<>();

    ArrayList<String> long_ch_id_list = new ArrayList<>();
    ArrayList<String> short_ch_id_list = new ArrayList<>();
    ArrayList<String> mcq_ch_id_list = new ArrayList<>();
    ArrayList<String> tf_ch_id_list = new ArrayList<>();
    ArrayList<String> fill_ch_id_list = new ArrayList<>();
    Context context;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quetion_paper);
        context = QuetionPaper.this;
        setTitle("Generate Question Paper");

        UPComingChapters_ID = getIntent().getStringExtra("Chapters_ID");
        Log.d("Chapters_ID", UPComingChapters_ID);

        CLASS = getIntent().getStringExtra("Class");
        displayClassName = getIntent().getStringExtra("displayClassName");
        Subject = getIntent().getStringExtra("Subject");

        //todo upcoming Marks From Chapters Page

        LongQM = getIntent().getStringExtra("LongQM");
        ShortQM = getIntent().getStringExtra("ShortQM");
        MultipleQM = getIntent().getStringExtra("MultipleQM");
        TruefalseQM = getIntent().getStringExtra("TruefalseQM");
        FillintheblackQM = getIntent().getStringExtra("FillintheblackQM");
        MatchMakingQM = getIntent().getStringExtra("MatchMakingQM");
        PaperName = getIntent().getStringExtra("PaperName");
        ExamDuration = getIntent().getStringExtra("ExamDuration");

        Marksfor_LongQuestions = (EditText) findViewById(R.id.Marksfor_LongQuestions);
        Marksfor_ShortQuestions = (EditText) findViewById(R.id.Marksfor_ShortQuestion);
        MarksForQbjective = (EditText) findViewById(R.id.MarksMCQ);
        MarksTurefalse = (EditText) findViewById(R.id.MarksTurefalse);
        Marksfillinblacks = (EditText) findViewById(R.id.Marksfillinblacks);
        MarksMatchMaking = (EditText) findViewById(R.id.MarksMatchMaking);
        editTextFocusChanged();
        LongQuestionID = new ArrayList<>();
        ShortQuestionID = new ArrayList<>();
        TruefalseQuestionID = new ArrayList<>();
        FillQuestionID = new ArrayList<>();
        MCQQuestionID = new ArrayList<>();
        MatchQuestionID = new ArrayList<>();

        // todo setting upcoming Marks in textview
        if (LongQM.isEmpty()) {
            Marksfor_LongQuestions.setText("");
        } else {
            Marksfor_LongQuestions.setText(LongQM);
            show_keyboard = show_keyboard + 1;
        }
        if (ShortQM.isEmpty()) {
            Marksfor_ShortQuestions.setText("");
        } else {
            Marksfor_ShortQuestions.setText(ShortQM);
            show_keyboard = show_keyboard + 1;
        }
        if (MultipleQM.isEmpty()) {
            MarksForQbjective.setText("");
        } else {
            MarksForQbjective.setText(MultipleQM);
            show_keyboard = show_keyboard + 1;
        }
        if (TruefalseQM.isEmpty()) {
            MarksTurefalse.setText("");
        } else {
            MarksTurefalse.setText(TruefalseQM);
            show_keyboard = show_keyboard + 1;
        }
        if (FillintheblackQM.isEmpty()) {
            Marksfillinblacks.setText("");
        } else {
            Marksfillinblacks.setText(FillintheblackQM);
            show_keyboard = show_keyboard + 1;
        }
        if (MatchMakingQM.isEmpty()) {
            MarksMatchMaking.setText("");
        } else {
            MarksMatchMaking.setText(MatchMakingQM);
            show_keyboard = show_keyboard + 1;
        }
        //TODO : Note Hide keyboard programmatically
        if (show_keyboard != 0) {
            hideMyKeyboard(QuetionPaper.this, 1);
        }
        /////////////////////////////////////////////////////////////////////////////
        if (UPComingChapters_ID.endsWith(",")) {
            Chapters_ID = UPComingChapters_ID.substring(0, UPComingChapters_ID.length() - 1);
        }
        //Chapterscount=getIntent().getStringExtra("ChaptersCount");
        Chapters_ID = Chapters_ID != null ? Chapters_ID.replace(" ", "") : "";
        Log.d("Chapterscount1365", "" + Chapters_ID);

        LongAnswer = (TextView) findViewById(R.id.LongAnswer);
        ShortAnswer = (TextView) findViewById(R.id.ShortAnswer);
        MultipleAnswer = (TextView) findViewById(R.id.MultipleAnswer);
        fillinblacks = (TextView) findViewById(R.id.fillinblacks);
        truefalse = (TextView) findViewById(R.id.truefalse);
        MatchMakingT = (TextView) findViewById(R.id.Matchmaking);


        Subjectivelayout = (LinearLayout) findViewById(R.id.longQAlayout);
        ObjectiveLayout = (LinearLayout) findViewById(R.id.ObjectiveLayout);
        ShortAnswers = (LinearLayout) findViewById(R.id.ShortAnswers);
        ObjectiveplushsubLayout = (LinearLayout) findViewById(R.id.ObjectiveplushsubLayout);
        Fillintheblanklayout = (LinearLayout) findViewById(R.id.Fillintheblanklayout);
        MatchMaking = (LinearLayout) findViewById(R.id.MatchMaking);
        MCQLayout = (LinearLayout) findViewById(R.id.MCQLayout);

        Subjectivelist = (ListView) findViewById(R.id.Subjectivelist);
        ShortAnswerslist = (ListView) findViewById(R.id.ShortAnswerslist);
        Objectivelist = (ListView) findViewById(R.id.Objectivelist);
        TrueFalse = (ListView) findViewById(R.id.true_false_list);
        Fillintheblanklist = (ListView) findViewById(R.id.Fillintheblanklist);
        MatchMakinglist = (ListView) findViewById(R.id.MatchMakinglist);
        MCQuestionlist = (ListView) findViewById(R.id.MCQuestionlist);

        //todo setting empty layout on listivew
        /*Subjectivelist.setEmptyView(findViewById(R.id.emtylayout));
        ShortAnswerslist.setEmptyView(findViewById(R.id.emtylayout));
        Objectivelist.setEmptyView(findViewById(R.id.emtylayout));
        TrueFalse.setEmptyView(findViewById(R.id.emtylayout));
        Fillintheblanklist.setEmptyView(findViewById(R.id.emtylayout));
        MatchMakinglist.setEmptyView(findViewById(R.id.emtylayout));
        MCQuestionlist.setEmptyView(findViewById(R.id.emtylayout));*/
        ////////////////////////////////////////////////////////////


        ObjectiveButton = (Button) findViewById(R.id.ObjectiveButton);
        ObjectiveplushsubButton = (Button) findViewById(R.id.ObjectiveplushsubButton);
        FillintheblankButton = (Button) findViewById(R.id.FillintheblankButton);
        Optionbutton = (LinearLayout) findViewById(R.id.fab);
        check = (LinearLayout) findViewById(R.id.check);
        TotalMarksbutton = (TextView) findViewById(R.id.TotalMarks);

        // MarksMCQ = (EditText) findViewById(R.id.MarksMCQ);
        Marksfor_LongQuestions.setRawInputType(Configuration.KEYBOARD_QWERTY);
        Marksfor_ShortQuestions.setRawInputType(Configuration.KEYBOARD_QWERTY);
        MarksForQbjective.setRawInputType(Configuration.KEYBOARD_QWERTY);
        MarksTurefalse.setRawInputType(Configuration.KEYBOARD_QWERTY);
        Marksfillinblacks.setRawInputType(Configuration.KEYBOARD_QWERTY);
        MarksMatchMaking.setRawInputType(Configuration.KEYBOARD_QWERTY);
        //MarksMCQ.setRawInputType(Configuration.KEYBOARD_QWERTY);
        LongAnswer.setText("00");
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        fillinTheblanklistt = new ArrayList<>();
        shortQuestionlist = new ArrayList<>();
        Matchmakingselected = new ArrayList<>();
        MCQLIST = new ArrayList<>();
        turefalesAraylist = new ArrayList<HashMap<String, String>>();

        longQuesAdapterLog = new ArrayList<>();
        trufalseAdapterlist = new ArrayList<>();
        SubjectiveAdaptershoet = new ArrayList<>();


        //todo Method for MatchMaking
        progressDialog = new ProgressDialog(QuetionPaper.this);
        progressDialog.setMessage("Loading..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
        String url5 = Apis.base_url + Apis.test_generator_url;
        StringRequest stringRequest5 = new StringRequest(Request.Method.POST, url5,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("MCResponse", "" + response);
                        JSONObject jsonObject1 = null;
                        try {
                            jsonObject1 = new JSONObject(response);
                            JSONArray mcolArray = jsonObject1.getJSONArray("match_column");
                            Log.d("FillArrayMcol", "" + String.valueOf(mcolArray));
                            String mcq1 = jsonObject1.getString("mcq_response");
                            String fill_in_blank = jsonObject1.getString("fill_in_blank_response");
                            String true_false = jsonObject1.getString("true_false_response");
                            String match_column = jsonObject1.getString("match_column_response");
                            String long_ques = jsonObject1.getString("long_ques_response");
                            JSONArray sq_arr = jsonObject1.getJSONArray("short_ques");
                            String shortQ = sq_arr.length() > 0 ? "1" : "0";

                            if (mcolArray.length() > 0) {

                                for (int i = 0; i < mcolArray.length(); i++) {
                                    JSONObject c = mcolArray.getJSONObject(i);
                                    //Log.d("MColArrayQQQ", c.getString("ques"));

                                    HashMap<String, String> MColMap = new HashMap<>();

                                    //FIBMAP.put("ques_no", c.getString(String.valueOf(QuestiojnMCQ.size() + TUREFALSE.size() + i)));
                                    MColMap.put("q_no", c.getString("q_no"));
                                    MColMap.put("mc_id", c.getString("mc_id"));
                                    MColMap.put("ch_name", c.getString("ch_name"));

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
                                    MColMap.put("ch_id", c.getString("ch_id"));

                                    MColMap.put("ans1", c.getString("ans1"));
                                    MColMap.put("ans2", c.getString("ans2"));
                                    MColMap.put("ans3", c.getString("ans3"));
                                    MColMap.put("ans4", c.getString("ans4"));
                                    MColMap.put("ans5", c.getString("ans5"));
                                    MColMap.put("ans6", c.getString("ans6"));
                                    MColMap.put("ans7", c.getString("ans7"));
                                    MColMap.put("ans8", c.getString("ans8"));
                                    MColMap.put("isSelected", "0");
                                    MColMap.put("MarkedANS", "");
                                    MColMap.put("Marks", "1");
                                    Log.d("MCol", String.valueOf(MColMap));
                                    Matchmakingselected.add(MColMap);
                                }
                                Log.d("Matchmakingselected", "" + Matchmakingselected.toString());
                                matchMakingAdapter = new MatchMakingAdapter(QuetionPaper.this, Matchmakingselected);
                                MatchMakinglist.setAdapter(matchMakingAdapter);
                            }
                            manageView(mcq1, fill_in_blank, true_false, match_column, long_ques, shortQ);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                System.out.println("ResponseRegistration" + error.getMessage());
            }
        }) {

            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                java.util.Map<String, String> params = new HashMap<>();
                params.put("ch_id", Chapters_ID);
                Log.d("ch_id", "" + Chapters_ID);
                return params;
            }
        };
        CommonMethods.callWebserviceForResponse(stringRequest5, context);

        //todo Method for fillinblanks
        String url4 = Apis.base_url + Apis.test_generator_url;
        StringRequest stringRequest4 = new StringRequest(Request.Method.POST, url4,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Ucoming_Questions", response);
                        //  progressDialog.dismiss();
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            JSONArray true_false = jsonObject1.getJSONArray("true_false");
                            JSONArray short_ques = jsonObject1.getJSONArray("short_ques");
                            JSONArray MCQArray = jsonObject1.getJSONArray("mcq");
                            JSONArray long_ques = jsonObject1.getJSONArray("long_ques");
                            JSONArray fill_in_blank = jsonObject1.getJSONArray("fill_in_blank");
                            Log.d("MCQQQ", String.valueOf(fill_in_blank));

                            // Toast.makeText(QuetionPaper.this, "hi from outside", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < fill_in_blank.length(); i++) {
                                JSONObject c = fill_in_blank.getJSONObject(i);
                                String ques_no = c.getString("ques_no");
                                String ques = c.getString("ques");
                                String Answer = c.getString("ans");

                                contact4 = new HashMap<>();
                                contact4.put("ques_no", ques_no);
                                contact4.put("ques", ques);
                                contact4.put("fq_id", c.getString("fq_id"));
                                contact4.put("ch_id", c.getString("ch_id"));
                                contact4.put("ch_name", c.getString("ch_name"));
                                contact4.put("Answer", Answer);
                                contact4.put("isSelected", "0");
                                Log.d("sarejhaseachha", String.valueOf(contact4));
                                fillinTheblanklistt.add(contact4);
                            }

                            Log.d("longQuesAdapterLog", String.valueOf(fillinTheblanklistt));
                            fillinTheblank = new FillinTheblankApter(QuetionPaper.this, fillinTheblanklistt);
                            Fillintheblanklist.setAdapter(fillinTheblank);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // progressDialog.dismiss();
                System.out.println("ResponseRegistration" + error.getMessage());
            }
        }) {

            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                java.util.Map<String, String> params = new HashMap<>();
                params.put("ch_id", Chapters_ID);

                return params;
            }
        };
        CommonMethods.callWebserviceForResponse(stringRequest4, context);

        //todo Method for Long
        String url3 = Apis.base_url + Apis.test_generator_url;
        StringRequest stringRequest3 = new StringRequest(Request.Method.POST, url3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Ucoming_QuestionsLL", response);
                        //  progressDialog.dismiss();
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            JSONArray long_ques = jsonObject1.getJSONArray("long_ques");
                            Log.d("MCQQQ", String.valueOf(long_ques));
                            for (int i = 0; i < long_ques.length(); i++) {
                                JSONObject c = long_ques.getJSONObject(i);
                                contact3 = new HashMap<>();
                                contact3.put("ques_no", c.getString("ques_no"));
                                contact3.put("ques", c.getString("ques"));
                                contact3.put("Answer", c.getString("ans_hint"));
                                contact3.put("lq_id", c.getString("lq_id"));
                                contact3.put("ch_id", c.getString("ch_id"));
                                contact3.put("ch_name", c.getString("ch_name"));
                                contact3.put("isSelected", "0");
                                Log.d("sarejhaseachha123", String.valueOf(contact3));
                                Log.d("long_ques", c.getString("ques"));
                                longQuesAdapterLog.add(contact3);
                            }
                            longQuestionAdapter = new LongQuestionAdapter(QuetionPaper.this, longQuesAdapterLog);
                            Subjectivelist.setAdapter(longQuestionAdapter);
                            Log.d("Looooooooooogggg", String.valueOf(longQuesAdapterLog));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // progressDialog.dismiss();
                System.out.println("ResponseRegistration" + error.getMessage());
            }
        }) {
            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                java.util.Map<String, String> params = new HashMap<>();
                params.put("ch_id", Chapters_ID);
                return params;
            }
        };
        CommonMethods.callWebserviceForResponse(stringRequest3, context);

        //todo Method for Ture False
        String url = Apis.base_url + Apis.test_generator_url;// ChapterId come from this url
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Ucoming_Questions", response);
                        //  progressDialog.dismiss();
                        try {

                            JSONObject jsonObject1 = new JSONObject(response);
                            JSONArray true_false = jsonObject1.getJSONArray("true_false");
                            JSONArray short_ques = jsonObject1.getJSONArray("short_ques");
                            JSONArray MCQArray = jsonObject1.getJSONArray("mcq");
                            Log.d("MCQQQ", String.valueOf(true_false));


                            // Toast.makeText(QuetionPaper.this, "hi from outside", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < true_false.length(); i++) {
                                JSONObject c = true_false.getJSONObject(i);
                                String ques_no = c.getString("ques_no");
                                String ques = c.getString("ques");
                                String Answer = c.getString("ans");
                                // Toast.makeText(QuetionPaper.this, "hi from inside", Toast.LENGTH_SHORT).show();

                                contact1 = new HashMap<>();
                                contact1.put("ques_no", ques_no);
                                contact1.put("ques", ques);
                                contact1.put("Answer", Answer);
                                contact1.put("tf_id", c.getString("tf_id"));
                                contact1.put("ch_id", c.getString("ch_id"));

                                contact1.put("ch_name", c.getString("ch_name"));
                                contact1.put("isSelected", "0");
                                Log.d("sarejhaseachha", String.valueOf(contact1));
                                trufalseAdapterlist.add(contact1);
                            }

                            Log.d("trufalseAdapterlist", String.valueOf(trufalseAdapterlist));
                            trufalseAdapter = new TureFalseAdapter(QuetionPaper.this, trufalseAdapterlist);
                            TrueFalse.setAdapter(trufalseAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // progressDialog.dismiss();
                System.out.println("ResponseRegistration" + error.getMessage());
            }
        }) {

            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                java.util.Map<String, String> params = new HashMap<>();
                params.put("ch_id", Chapters_ID);

                return params;
            }
        };
        CommonMethods.callWebserviceForResponse(stringRequest, context);

        //todo MCQ Method
        String url1 = Apis.base_url + Apis.test_generator_url;
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Ucoming_Questions", response);
                        //  progressDialog.dismiss();
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            JSONArray true_false = jsonObject1.getJSONArray("true_false");
                            JSONArray short_ques = jsonObject1.getJSONArray("short_ques");
                            JSONArray MCQArray = jsonObject1.getJSONArray("mcq");
                            Log.d("MCQQQ", String.valueOf(true_false));

                            for (int i = 0; i < MCQArray.length(); i++) {
                                JSONObject c = MCQArray.getJSONObject(i);
                                String ques_no = c.getString("ques_no");
                                String ques = c.getString("ques");
                                String option_a = c.getString("a");
                                String option_b = c.getString("b");
                                String option_c = c.getString("c");
                                String option_d = c.getString("d");
                                String Answer = c.getString("ans");

                                contact = new HashMap<>();

                                contact.put("ques_no", ques_no);
                                contact.put("ques", ques);
                                contact.put("ch_id", c.getString("ch_id"));
                                contact.put("option_a", option_a);
                                contact.put("option_b", option_b);
                                contact.put("option_c", option_c);
                                contact.put("option_d", option_d);
                                contact.put("mcq_id", c.getString("mcq_id"));
                                contact.put("Answer", Answer);
                                contact.put("isSelected", "0");
                                contact.put("ch_name", c.getString("ch_name"));
                                Log.d("sarejhaseachha", String.valueOf(contact));
                                MCQLIST.add(contact);
                            }

                            Log.d("MCQLIOST", String.valueOf(MCQLIST.size()));
                            mcqAdapter = new MCQAdapter(QuetionPaper.this, MCQLIST);
                            MCQuestionlist.setAdapter(mcqAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // progressDialog.dismiss();
                System.out.println("ResponseRegistration" + error.getMessage());
            }
        }) {

            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                java.util.Map<String, String> params = new HashMap<>();
                params.put("ch_id", Chapters_ID);

                return params;
            }
        };
        CommonMethods.callWebserviceForResponse(stringRequest1, context);

        //todo Short Question
        String url2 = Apis.base_url + Apis.test_generator_url;
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Ucoming_Questions", response);
                        //  progressDialog.dismiss();
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            JSONArray true_false = jsonObject1.getJSONArray("true_false");
                            JSONArray short_ques = jsonObject1.getJSONArray("short_ques");
                            JSONArray MCQArray = jsonObject1.getJSONArray("mcq");
                            Log.d("MCQQQ", String.valueOf(true_false));

                            for (int i = 0; i < short_ques.length(); i++) {
                                JSONObject c = short_ques.getJSONObject(i);
                                String ques_no = c.getString("ques_no");
                                String ques = c.getString("ques");


                                contact2 = new HashMap<>();
                                contact2.put("ques_no", ques_no);
                                contact2.put("ques", ques);
                                contact2.put("sq_id", c.getString("sq_id"));
                                contact2.put("ch_id", c.getString("ch_id"));
                                contact2.put("ch_name", c.getString("ch_name"));
                                contact2.put("isSelected", "0");
                                Log.d("sarejhaseachha", String.valueOf(contact2));
                                shortQuestionlist.add(contact2);
                            }
                            shortAnswerAdapter = new ShortAnswerAdapter(QuetionPaper.this, shortQuestionlist);
                            ShortAnswerslist.setAdapter(shortAnswerAdapter);
                            Log.d("Sssssssslist", String.valueOf(PatientList));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // progressDialog.dismiss();
                System.out.println("ResponseRegistration" + error.getMessage());
            }
        }) {

            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                java.util.Map<String, String> params = new HashMap<>();
                params.put("ch_id", Chapters_ID);

                return params;
            }
        };
        CommonMethods.callWebserviceForResponse(stringRequest2, context);

        Subjectivelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                /*Toast toast = Toast.makeText(QuetionPaper.this, "Content:", Toast.LENGTH_LONG);
                toast.show();*/
                Intent intent = new Intent(QuetionPaper.this, Students_Class.class);
                startActivity(intent);
            }
        });

        Objectivelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Toast toast = Toast.makeText(QuetionPaper.this, "Content:", Toast.LENGTH_LONG);
                toast.show();*/
                Intent intent = new Intent(QuetionPaper.this, Students_Class.class);
                startActivity(intent);
            }
        });
        Optionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (longQuesAdapterLog.size() == 0 && shortQuestionlist.size() == 0 && MCQLIST.size() == 0 && trufalseAdapterlist.size() == 0 && fillinTheblanklistt.size() == 0 && Matchmakingselected.size() == 0) {
                    Toast.makeText(QuetionPaper.this, getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                } else {

                    final Dialog dialog = new Dialog(QuetionPaper.this);
                    dialog.setContentView(R.layout.testgenrate);
                    dialog.setTitle("Custom Dialog");
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.show();

                    Button Long_Answers = (Button) dialog.findViewById(R.id.longAnswer);
                    Button Shor_Answers = (Button) dialog.findViewById(R.id.shortanswers);
                    Button mcq_btn = (Button) dialog.findViewById(R.id.mcqs);
                    Button trueFalseBtn = (Button) dialog.findViewById(R.id.truenfalse);
                    Button fillintheblank = (Button) dialog.findViewById(R.id.fillintheblank);
                    Button MatchMakingButton = (Button) dialog.findViewById(R.id.MatchMakingButton);

                    Long_Answers.setVisibility(longQuesAdapterLog.size() > 0 ? View.VISIBLE : View.GONE);
                    Shor_Answers.setVisibility(shortQuestionlist.size() > 0 ? View.VISIBLE : View.GONE);
                    mcq_btn.setVisibility(MCQLIST.size() > 0 ? View.VISIBLE : View.GONE);
                    trueFalseBtn.setVisibility(trufalseAdapterlist.size() > 0 ? View.VISIBLE : View.GONE);
                    fillintheblank.setVisibility(fillinTheblanklistt.size() > 0 ? View.VISIBLE : View.GONE);
                    MatchMakingButton.setVisibility(Matchmakingselected.size() > 0 ? View.VISIBLE : View.GONE);

                    // Button MCQButton = (Button) dialog.findViewById(R.id.MCQButton);
                /*  MCQButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MCQLayout.setVisibility(View.VISIBLE);
                        MatchMaking.setVisibility(View.GONE);
                        ShortAnswers.setVisibility(View.GONE);
                        Subjectivelayout.setVisibility(View.GONE);
                        ObjectiveLayout.setVisibility(View.GONE);
                        ObjectiveplushsubLayout.setVisibility(View.GONE);
                        Fillintheblanklayout.setVisibility(View.GONE);
                        dialog.dismiss();
                    }
                });*/
                    MatchMakingButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MCQLayout.setVisibility(View.GONE);
                            MatchMaking.setVisibility(View.VISIBLE);
                            ShortAnswers.setVisibility(View.GONE);
                            Subjectivelayout.setVisibility(View.GONE);
                            ObjectiveLayout.setVisibility(View.GONE);
                            ObjectiveplushsubLayout.setVisibility(View.GONE);
                            Fillintheblanklayout.setVisibility(View.GONE);
                            dialog.dismiss();
                            if (MatchMakingQM.isEmpty()) {
                                hideMyKeyboard(QuetionPaper.this, 0);
                            }
                        }
                    });
                    Shor_Answers.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MCQLayout.setVisibility(View.GONE);
                            MatchMaking.setVisibility(View.GONE);
                            ShortAnswers.setVisibility(View.VISIBLE);
                            Subjectivelayout.setVisibility(View.GONE);
                            ObjectiveLayout.setVisibility(View.GONE);
                            ObjectiveplushsubLayout.setVisibility(View.GONE);
                            Fillintheblanklayout.setVisibility(View.GONE);
                            dialog.dismiss();
                            if (ShortQM.isEmpty()) {
                                hideMyKeyboard(QuetionPaper.this, 0);
                            }
                        }
                    });

                    Long_Answers.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MCQLayout.setVisibility(View.GONE);
                            MatchMaking.setVisibility(View.GONE);
                            ShortAnswers.setVisibility(View.GONE);
                            Subjectivelayout.setVisibility(View.VISIBLE);
                            ObjectiveLayout.setVisibility(View.GONE);
                            ObjectiveplushsubLayout.setVisibility(View.GONE);
                            Fillintheblanklayout.setVisibility(View.GONE);
                            dialog.dismiss();
                            if (LongQM.isEmpty()) {
                                hideMyKeyboard(QuetionPaper.this, 0);
                            }
                        }
                    });
                    mcq_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MCQLayout.setVisibility(View.VISIBLE);
                            MatchMaking.setVisibility(View.GONE);
                            ShortAnswers.setVisibility(View.GONE);
                            Subjectivelayout.setVisibility(View.GONE);
                            ObjectiveLayout.setVisibility(View.GONE);
                            ObjectiveplushsubLayout.setVisibility(View.GONE);
                            Fillintheblanklayout.setVisibility(View.GONE);
                            dialog.dismiss();
                            if (MultipleQM.isEmpty()) {
                                hideMyKeyboard(QuetionPaper.this, 0);
                            }
                        }
                    });
                    trueFalseBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MCQLayout.setVisibility(View.GONE);
                            MatchMaking.setVisibility(View.GONE);
                            ShortAnswers.setVisibility(View.GONE);
                            Subjectivelayout.setVisibility(View.GONE);
                            ObjectiveLayout.setVisibility(View.GONE);
                            ObjectiveplushsubLayout.setVisibility(View.VISIBLE);
                            Fillintheblanklayout.setVisibility(View.GONE);
                            dialog.dismiss();
                            if (TruefalseQM.isEmpty()) {
                                hideMyKeyboard(QuetionPaper.this, 0);
                            }
                        }
                    });
                    fillintheblank.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MCQLayout.setVisibility(View.GONE);
                            MatchMaking.setVisibility(View.GONE);
                            ShortAnswers.setVisibility(View.GONE);
                            Subjectivelayout.setVisibility(View.GONE);
                            ObjectiveLayout.setVisibility(View.GONE);
                            ObjectiveplushsubLayout.setVisibility(View.GONE);
                            Fillintheblanklayout.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                            if (FillintheblackQM.isEmpty()) {
                                hideMyKeyboard(QuetionPaper.this, 0);
                            }
                        }
                    });
                }
            }
        });
        //region "TextChange"
        Marksfor_ShortQuestions.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ShortAnswer.setText(String.valueOf(getSCount()));
                TotalMarksbutton.setText(String.valueOf(finalCount()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Marksfor_LongQuestions.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LongAnswer.setText(String.valueOf(getLCount()));
                TotalMarksbutton.setText(String.valueOf(finalCount()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        MarksForQbjective.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MultipleAnswer.setText(String.valueOf(getMCQCount()));
                TotalMarksbutton.setText(String.valueOf(finalCount()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        MarksTurefalse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                truefalse.setText(String.valueOf(getTrueFalseCount()));
                TotalMarksbutton.setText(String.valueOf(finalCount()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Marksfillinblacks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fillinblacks.setText(String.valueOf(getFillInBlankCount()));
                TotalMarksbutton.setText(String.valueOf(finalCount()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        MarksMatchMaking.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MatchMakingT.setText(String.valueOf(getMatchMakingCount()));
                TotalMarksbutton.setText(String.valueOf(finalCount()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });//endregion

        //region "Check"
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //if (longQuesAdapterLog.isEmpty() && trufalseAdapterlist.isEmpty() && fillinTheblanklistt.isEmpty() && shortQuestionlist.isEmpty()) {
                    if (Marksfor_LongQuestions.getText().toString().isEmpty() && Marksfor_ShortQuestions.getText().toString().isEmpty() && MarksForQbjective.getText().toString().isEmpty() && MarksTurefalse.getText().toString().isEmpty() && Marksfillinblacks.getText().toString().isEmpty() && MarksMatchMaking.getText().toString().isEmpty()) {
                        Toast.makeText(QuetionPaper.this, "you have not selected any questions", Toast.LENGTH_LONG).show();
                    } else {
                        Log.d("D123654", String.valueOf("LongQuestionID " + LongQuestionID + "ShortQuestionID  " + ShortQuestionID + "TruefalseQuestionID  " + TruefalseQuestionID + "FillQuestionID  " + FillQuestionID + "MCQQuestionID  " + MCQQuestionID + "MatchQuestionID  " + MatchQuestionID));
                        int selected_quest = 0;//LongQuestionID.size() + ShortQuestionID.size() + TruefalseQuestionID.size() + FillQuestionID.size() + MCQQuestionID.size() + MatchQuestionID.size();
                        if (LongQuestionID != null) {
                            selected_quest += LongQuestionID.size();
                        }
                        if (ShortQuestionID != null) {
                            selected_quest += ShortQuestionID.size();
                        }
                        if (TruefalseQuestionID != null) {
                            selected_quest += TruefalseQuestionID.size();
                        }
                        if (FillQuestionID != null) {
                            selected_quest += FillQuestionID.size();
                        }
                        if (MCQQuestionID != null) {
                            selected_quest += MCQQuestionID.size();
                        }
                        if (MatchQuestionID != null) {
                            selected_quest += MatchQuestionID.size();
                        }
                        final Dialog dialog = new Dialog(QuetionPaper.this);
                        dialog.setContentView(R.layout.confirmitiondailog);
                        dialog.setTitle("Custom Dialog");
                        dialog.show();
                        Button yes = (Button) dialog.findViewById(R.id.yes);
                        Button No = (Button) dialog.findViewById(R.id.No);
                        TextView Class = (TextView) dialog.findViewById(R.id.Class);
                        TextView txtEmail = (TextView) dialog.findViewById(R.id.txtEmail);
                        TextView txtPaperName = (TextView) dialog.findViewById(R.id.txtPaperName);

                        TextView TotalQuestion = (TextView) dialog.findViewById(R.id.TotalQuestion);
                        TextView TotalMarks = (TextView) dialog.findViewById(R.id.TotalMarksD);
                        TextView SubjectT = (TextView) dialog.findViewById(R.id.Subject);
                        TextView TotalDuration = (TextView) dialog.findViewById(R.id.TotalDuration);

                        int TotalQuestions = longQuesAdapterLog.size() + SubjectiveAdaptershoet.size() + shortQuestionlist.size() + trufalseAdapterlist.size() + fillinTheblanklistt.size();
                        Class.setText("" + displayClassName);
                        TotalDuration.setText("" + ExamDuration);
                        txtPaperName.setText("" + PaperName);
                        txtEmail.setText("" + CommonMethods.getEmailId(QuetionPaper.this));
                        TotalMarks.setText("" + String.valueOf(TotalMarksbutton.getText().toString()));
                        SubjectT.setText("" + Subject);
                        /**/
                        String strLongChId = "";
                        String strLongQuesId = "";
                        String strShortChId = "";
                        String strShortQuesId = "";
                        String strMcqChId = "";
                        String strMcqQuesId = "";
                        String strTfChId = "";
                        String strTfQuesId = "";
                        String strFillChId = "";
                        String strFillQuesId = "";
                        String strMatchColChId = "";
                        String strMatchColQuesId = "";
                        int total_ques = 0;
                        //1
                        if (longQuesAdapterLog != null && longQuesAdapterLog.size() > 0) {
                            ArrayList<HashMap<String, String>> lng_list = ((LongQuestionAdapter) longQuestionAdapter).getLongQuestionList();
                            for (int i = 0; i < lng_list.size(); i++) {
                                if (lng_list.get(i).get("isSelected").equals("1")) {
                                    strLongChId = strLongChId + "," + lng_list.get(i).get("ch_id");
                                    strLongQuesId = strLongQuesId + "," + lng_list.get(i).get("lq_id");
                                    total_ques = total_ques + 1;
                                }
                            }
                        }
                        //2
                        if (shortQuestionlist != null && shortQuestionlist.size() > 0) {
                            ArrayList<HashMap<String, String>> short_list = ((ShortAnswerAdapter) shortAnswerAdapter).getShortQuestionList();
                            for (int i = 0; i < short_list.size(); i++) {
                                if (short_list.get(i).get("isSelected").equals("1")) {
                                    strShortChId = strShortChId + "," + short_list.get(i).get("ch_id");
                                    strShortQuesId = strShortQuesId + "," + short_list.get(i).get("sq_id");
                                    total_ques = total_ques + 1;
                                }
                            }
                        }
                        //3
                        if (MCQLIST != null && MCQLIST.size() > 0) {
                            ArrayList<HashMap<String, String>> mcq_list = ((MCQAdapter) mcqAdapter).getMcqQuestionList();
                            for (int i = 0; i < mcq_list.size(); i++) {
                                if (mcq_list.get(i).get("isSelected").equals("1")) {
                                    strMcqChId = strMcqChId + "," + mcq_list.get(i).get("ch_id");
                                    strMcqQuesId = strMcqQuesId + "," + mcq_list.get(i).get("mcq_id");
                                    total_ques = total_ques + 1;
                                }
                            }
                        }
                        //4
                        if (trufalseAdapterlist != null && trufalseAdapterlist.size() > 0) {
                            ArrayList<HashMap<String, String>> tf_list = ((TureFalseAdapter) trufalseAdapter).getTFQuestionList();
                            for (int i = 0; i < tf_list.size(); i++) {
                                if (tf_list.get(i).get("isSelected").equals("1")) {
                                    strTfChId = strTfChId + "," + tf_list.get(i).get("ch_id");
                                    strTfQuesId = strTfQuesId + "," + tf_list.get(i).get("tf_id");
                                    total_ques = total_ques + 1;
                                }
                            }
                        }
                        //5
                        if (fillinTheblanklistt != null && fillinTheblanklistt.size() > 0) {
                            ArrayList<HashMap<String, String>> fill_list = ((FillinTheblankApter) fillinTheblank).getFillInBlanksQuestionList();
                            for (int i = 0; i < fill_list.size(); i++) {
                                if (fill_list.get(i).get("isSelected").equals("1")) {
                                    strFillChId = strFillChId + "," + fill_list.get(i).get("ch_id");
                                    strFillQuesId = strFillQuesId + "," + fill_list.get(i).get("fq_id");
                                    total_ques = total_ques + 1;
                                }
                            }
                        }
                        //6
                        if (Matchmakingselected != null && Matchmakingselected.size() > 0) {
                            ArrayList<HashMap<String, String>> mm_list = ((MatchMakingAdapter) matchMakingAdapter).getMatchMakingQuestionList();
                            for (int i = 0; i < mm_list.size(); i++) {
                                if (mm_list.get(i).get("isSelected").equals("1")) {
                                    strMatchColChId = strMatchColChId + "," + mm_list.get(i).get("ch_id");
                                    strMatchColQuesId = strMatchColQuesId + "," + mm_list.get(i).get("mc_id");
                                    total_ques = total_ques + 1;
                                }
                            }
                        }
                        TotalQuestion.setText("" + String.valueOf(total_ques));
                        /**/

                        final String finalStrLongQuesId = strLongQuesId;
                        final String finalStrShortQuesId = strShortQuesId;
                        final String finalStrMcqQuesId = strMcqQuesId;
                        final String finalStrTfQuesId = strTfQuesId;
                        final String finalStrFillQuesId = strFillQuesId;
                        final String finalStrMatchColQuesId = strMatchColQuesId;
                        final String finalStrLongChId = strLongChId;
                        final String finalStrShortChId = strShortChId;
                        final String finalStrMcqChId = strMcqChId;
                        final String finalStrTfChId = strTfChId;
                        final String finalStrFillChId = strFillChId;
                        final String finalStrMatchColChId = strMatchColChId;
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                /**/
                                Log.d("chljabhai", finalStrLongQuesId);
                                Log.d("chljabhai", finalStrShortQuesId);
                                Log.d("chljabhai", finalStrMcqQuesId);
                                Log.d("chljabhai", finalStrTfQuesId);
                                Log.d("chljabhai", finalStrFillQuesId);
                                Log.d("chljabhai", finalStrMatchColQuesId);

                                Log.d("chljabhai", finalStrLongChId);
                                Log.d("chljabhai", finalStrShortChId);
                                Log.d("chljabhai", finalStrMcqChId);
                                Log.d("chljabhai", finalStrTfChId);
                                Log.d("chljabhai", finalStrFillChId);
                                Log.d("chljabhai", finalStrMatchColChId);

                                String strLongQuesId1 = !finalStrLongQuesId.equals("") && finalStrLongQuesId.substring(0, 1).equals(",") ? finalStrLongQuesId.substring(1, finalStrLongQuesId.length()) : finalStrLongQuesId;
                                String strShortQuesId1 = !finalStrShortQuesId.equals("") && finalStrShortQuesId.substring(0, 1).equals(",") ? finalStrShortQuesId.substring(1, finalStrShortQuesId.length()) : finalStrShortQuesId;
                                String strMcqQuesId1 = !finalStrMcqQuesId.equals("") && finalStrMcqQuesId.substring(0, 1).equals(",") ? finalStrMcqQuesId.substring(1, finalStrMcqQuesId.length()) : finalStrMcqQuesId;
                                String strTfQuesId1 = !finalStrTfQuesId.equals("") && finalStrTfQuesId.substring(0, 1).equals(",") ? finalStrTfQuesId.substring(1, finalStrTfQuesId.length()) : finalStrTfQuesId;
                                String strFillQuesId1 = !finalStrFillQuesId.equals("") && finalStrFillQuesId.substring(0, 1).equals(",") ? finalStrFillQuesId.substring(1, finalStrFillQuesId.length()) : finalStrFillQuesId;
                                String strMatchColQuesId1 = !finalStrMatchColQuesId.equals("") && finalStrMatchColQuesId.substring(0, 1).equals(",") ? finalStrMatchColQuesId.substring(1, finalStrMatchColQuesId.length()) : finalStrMatchColQuesId;

                                String strLongChId1 = !finalStrLongChId.equals("") && finalStrLongChId.substring(0, 1).equals(",") ? finalStrLongChId.substring(1, finalStrLongChId.length()) : finalStrLongChId;
                                String strShortChId1 = !finalStrShortChId.equals("") && finalStrShortChId.substring(0, 1).equals(",") ? finalStrShortChId.substring(1, finalStrShortChId.length()) : finalStrShortChId;
                                String strMcqChId1 = !finalStrMcqChId.equals("") && finalStrMcqChId.substring(0, 1).equals(",") ? finalStrMcqChId.substring(1, finalStrMcqChId.length()) : finalStrMcqChId;
                                String strTfChId1 = !finalStrTfChId.equals("") && finalStrTfChId.substring(0, 1).equals(",") ? finalStrTfChId.substring(1, finalStrTfChId.length()) : finalStrTfChId;
                                String strFillChId1 = !finalStrFillChId.equals("") && finalStrFillChId.substring(0, 1).equals(",") ? finalStrFillChId.substring(1, finalStrFillChId.length()) : finalStrFillChId;
                                String strMatchColChId1 = !finalStrMatchColChId.equals("") && finalStrMatchColChId.substring(0, 1).equals(",") ? finalStrMatchColChId.substring(1, finalStrMatchColChId.length()) : finalStrMatchColChId;

                                if (!strLongQuesId1.equals("") || !strShortQuesId1.equals("") || !strMcqQuesId1.equals("") || !strTfQuesId1.equals("") || !strFillQuesId1.equals("") || !strMatchColQuesId1.equals("") ||
                                        !strLongChId1.equals("") || !strShortChId1.equals("") || !strMcqChId1.equals("") || !strTfChId1.equals("") || !strFillChId1.equals("") || !strMatchColChId1.equals("")) {
                                    progressDialog = new ProgressDialog(QuetionPaper.this);
                                    progressDialog.setMessage("Loading..."); // Setting Title
                                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                    progressDialog.show();
                                    progressDialog.setCancelable(false);
                                    reqToSubmit("" + Apis.test_generator_submit,
                                            strLongQuesId1, strShortQuesId1, strMcqQuesId1, strTfQuesId1, strFillQuesId1, strMatchColQuesId1,
                                            strLongChId1, strShortChId1, strMcqChId1, strTfChId1, strFillChId1, strMatchColChId1,
                                            ch_ques_id.toString(), CommonMethods.getEmailId(QuetionPaper.this),
                                            CommonMethods.getId(QuetionPaper.this), PaperName, displayClassName, TotalMarksbutton.getText().toString(), new SimpleDateFormat("yyyy-MM-dd").format(new Date()), ExamDuration);
                                } else {
                                    Toast.makeText(QuetionPaper.this, "Nothing Selected.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        No.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                    }
                } catch (NullPointerException ex) {

                }
            }
        });
    }//endregion

    void manageView(String mcq, String fill_in_blank, String true_false, String match_column, String long_ques, String shortQ) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        if (long_ques.equals("1")) {
            Subjectivelayout.setVisibility(View.VISIBLE);
            ObjectiveLayout.setVisibility(View.GONE);
            ObjectiveplushsubLayout.setVisibility(View.GONE);
            Fillintheblanklayout.setVisibility(View.GONE);
            ShortAnswers.setVisibility(View.GONE);
            MatchMaking.setVisibility(View.GONE);
            MCQLayout.setVisibility(View.GONE);
        } else if (long_ques.equals("0") && shortQ.equals("1")) {
            Subjectivelayout.setVisibility(View.GONE);
            ObjectiveLayout.setVisibility(View.GONE);
            ObjectiveplushsubLayout.setVisibility(View.GONE);
            Fillintheblanklayout.setVisibility(View.GONE);
            ShortAnswers.setVisibility(View.VISIBLE);
            MatchMaking.setVisibility(View.GONE);
            MCQLayout.setVisibility(View.GONE);
        } else if (long_ques.equals("0") && shortQ.equals("0") && mcq.equals("1")) {
            Subjectivelayout.setVisibility(View.GONE);
            ObjectiveLayout.setVisibility(View.GONE);
            ObjectiveplushsubLayout.setVisibility(View.GONE);
            Fillintheblanklayout.setVisibility(View.GONE);
            ShortAnswers.setVisibility(View.GONE);
            MatchMaking.setVisibility(View.GONE);
            MCQLayout.setVisibility(View.VISIBLE);
        } else if (long_ques.equals("0") && shortQ.equals("0") && mcq.equals("0") && true_false.equals("1")) {
            Subjectivelayout.setVisibility(View.GONE);
            ObjectiveLayout.setVisibility(View.GONE);
            ObjectiveplushsubLayout.setVisibility(View.VISIBLE);
            Fillintheblanklayout.setVisibility(View.GONE);
            ShortAnswers.setVisibility(View.GONE);
            MatchMaking.setVisibility(View.GONE);
            MCQLayout.setVisibility(View.GONE);
        } else if (long_ques.equals("0") && shortQ.equals("0") && mcq.equals("0") && true_false.equals("0") && fill_in_blank.equals("1")) {
            Subjectivelayout.setVisibility(View.GONE);
            ObjectiveLayout.setVisibility(View.GONE);
            ObjectiveplushsubLayout.setVisibility(View.GONE);
            Fillintheblanklayout.setVisibility(View.VISIBLE);
            ShortAnswers.setVisibility(View.GONE);
            MatchMaking.setVisibility(View.GONE);
            MCQLayout.setVisibility(View.GONE);
        } else if (long_ques.equals("0") && shortQ.equals("0") && mcq.equals("0") && true_false.equals("0") && fill_in_blank.equals("0") && match_column.equals("1")) {
            Subjectivelayout.setVisibility(View.GONE);
            ObjectiveLayout.setVisibility(View.GONE);
            ObjectiveplushsubLayout.setVisibility(View.GONE);
            Fillintheblanklayout.setVisibility(View.GONE);
            ShortAnswers.setVisibility(View.GONE);
            MatchMaking.setVisibility(View.VISIBLE);
            MCQLayout.setVisibility(View.GONE);
        } else if (long_ques.equals("0") && shortQ.equals("0") && mcq.equals("0") && true_false.equals("0") && fill_in_blank.equals("0") && match_column.equals("0")) {
            Subjectivelayout.setVisibility(View.GONE);
            ObjectiveLayout.setVisibility(View.GONE);
            ObjectiveplushsubLayout.setVisibility(View.GONE);
            Fillintheblanklayout.setVisibility(View.GONE);
            ShortAnswers.setVisibility(View.GONE);
            MatchMaking.setVisibility(View.GONE);
            MCQLayout.setVisibility(View.GONE);
            CommonMethods.showToast(QuetionPaper.this, "No data found.");
        }
    }

    private void editTextFocusChanged() {
        Marksfor_LongQuestions.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        Marksfor_ShortQuestions.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        MarksForQbjective.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        MarksTurefalse.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        Marksfillinblacks.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        MarksMatchMaking.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    //endregion
    //region "ManageCount"
    public int getLCount() {
        int updated_lng_chk_count = 0;
        if (longQuesAdapterLog != null && longQuesAdapterLog.size() > 0) {
            ArrayList<HashMap<String, String>> stList = ((LongQuestionAdapter) longQuestionAdapter).getLongQuestionList();
            for (int i = 0; i < stList.size(); i++) {
                if (stList.get(i).get("isSelected").equals("1")) {
                    updated_lng_chk_count = updated_lng_chk_count + 1;
                }
            }
            return updated_lng_chk_count * Integer.parseInt(Marksfor_LongQuestions.getText().toString().contains(" ") || Marksfor_LongQuestions.getText().toString().isEmpty() || !isValid(Marksfor_LongQuestions.getText().toString()) ? "0" : Marksfor_LongQuestions.getText().toString().trim().replaceAll("\\s", ""));
        }
        return updated_lng_chk_count;
    }

    public int getSCount() {
        int updated_lng_chk_count = 0;
        if (shortQuestionlist != null && shortQuestionlist.size() > 0) {
            ArrayList<HashMap<String, String>> short_list = ((ShortAnswerAdapter) shortAnswerAdapter).getShortQuestionList();
            for (int i = 0; i < short_list.size(); i++) {
                if (short_list.get(i).get("isSelected").equals("1")) {
                    updated_lng_chk_count = updated_lng_chk_count + 1;
                }
            }
            return updated_lng_chk_count * Integer.parseInt(Marksfor_ShortQuestions.getText().toString().contains(" ") || Marksfor_ShortQuestions.getText().toString().isEmpty() || !isValid(Marksfor_ShortQuestions.getText().toString().trim()) ? "0" : Marksfor_ShortQuestions.getText().toString().trim().replaceAll("\\s", ""));
        }
        return updated_lng_chk_count;
    }

    public int getMCQCount() {
        int updated_lng_chk_count = 0;
        if (MCQLIST != null && MCQLIST.size() > 0) {
            ArrayList<HashMap<String, String>> mcq_list = ((MCQAdapter) mcqAdapter).getMcqQuestionList();
            for (int i = 0; i < mcq_list.size(); i++) {
                if (mcq_list.get(i).get("isSelected").equals("1")) {
                    updated_lng_chk_count = updated_lng_chk_count + 1;
                }
            }
            return updated_lng_chk_count * Integer.parseInt(MarksForQbjective.getText().toString().contains(" ") || MarksForQbjective.getText().toString().isEmpty() || !isValid(MarksForQbjective.getText().toString()) ? "0" : MarksForQbjective.getText().toString().trim().replaceAll("\\s", ""));
        }
        return updated_lng_chk_count;
    }

    public int getTrueFalseCount() {
        int updated_lng_chk_count = 0;
        if (trufalseAdapterlist != null && trufalseAdapterlist.size() > 0) {
            ArrayList<HashMap<String, String>> tf_list = ((TureFalseAdapter) trufalseAdapter).getTFQuestionList();
            for (int i = 0; i < tf_list.size(); i++) {
                if (tf_list.get(i).get("isSelected").equals("1")) {
                    updated_lng_chk_count = updated_lng_chk_count + 1;
                }
            }
            return updated_lng_chk_count * Integer.parseInt(MarksTurefalse.getText().toString().contains(" ") || MarksTurefalse.getText().toString().isEmpty() || !isValid(MarksTurefalse.getText().toString()) ? "0" : MarksTurefalse.getText().toString().trim().replaceAll("\\s", ""));
        }
        return updated_lng_chk_count;
    }

    public int getFillInBlankCount() {
        int updated_lng_chk_count = 0;
        if (fillinTheblanklistt != null && fillinTheblanklistt.size() > 0) {
            ArrayList<HashMap<String, String>> fill_list = ((FillinTheblankApter) fillinTheblank).getFillInBlanksQuestionList();
            for (int i = 0; i < fill_list.size(); i++) {
                if (fill_list.get(i).get("isSelected").equals("1")) {
                    updated_lng_chk_count = updated_lng_chk_count + 1;
                }
            }
            return updated_lng_chk_count * Integer.parseInt(Marksfillinblacks.getText().toString().contains(" ") || Marksfillinblacks.getText().toString().isEmpty() || !isValid(Marksfillinblacks.getText().toString()) ? "0" : Marksfillinblacks.getText().toString().trim().replaceAll("\\s", ""));
        }
        return updated_lng_chk_count;
    }

    public int getMatchMakingCount() {
        int updated_lng_chk_count = 0;
        if (Matchmakingselected != null && Matchmakingselected.size() > 0) {
            ArrayList<HashMap<String, String>> mm_list = ((MatchMakingAdapter) matchMakingAdapter).getMatchMakingQuestionList();
            for (int i = 0; i < mm_list.size(); i++) {
                if (mm_list.get(i).get("isSelected").equals("1")) {
                    updated_lng_chk_count = updated_lng_chk_count + 1;
                }
            }
            return updated_lng_chk_count * Integer.parseInt(MarksMatchMaking.getText().toString().contains(" ") || MarksMatchMaking.getText().toString().isEmpty() || !isValid(MarksMatchMaking.getText().toString()) ? "0" : MarksMatchMaking.getText().toString().trim().replaceAll("\\s", ""));
        }
        return updated_lng_chk_count;
    }


    public int finalCount() {
        return getLCount() + getSCount() + getMCQCount() + getTrueFalseCount() + getFillInBlankCount() + getMatchMakingCount();
    }

    private void hideMyKeyboard(QuetionPaper quetionPaper, int show_hide_status) {
        //quetionPaper.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (show_hide_status == 1) {
            quetionPaper.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        } else if (show_hide_status == 0) {
            quetionPaper.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
    }

    ////////subjective///////////////////////////////////////
    public class LongQuestionAdapter extends BaseAdapter {

        // Declare Variables
        ArrayList<HashMap<String, String>> books;

        QuetionPaper mContext;
        LayoutInflater inflater;
        ArrayList<HashMap<String, String>> animalNamesList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        String upcomigID;


        public LongQuestionAdapter(QuetionPaper studentdashBord, ArrayList<HashMap<String, String>> booksname) {
            this.mContext = studentdashBord;
            this.books = booksname;
            inflater = LayoutInflater.from((Context) mContext);

        }

        public ArrayList<HashMap<String, String>> getLongQuestionList() {
            return books;
        }

        public class ViewHolder {
            TextView name, ID, Number, heading, Questionnumber, txtShortAnsTitle;
            // Button details,Medicalhistory;
            ImageView imgg;
            CheckBox checkbox10;
        }

        @Override
        public int getCount() {
            return books.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View view, ViewGroup parent) {
            final ViewHolder holder;

            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.short_answer_layout, null);
                holder.heading = (TextView) view.findViewById(R.id.heading);
                holder.Questionnumber = (TextView) view.findViewById(R.id.Questionnumber);
                holder.checkbox10 = (CheckBox) view.findViewById(R.id.checkbox10);
                holder.txtShortAnsTitle = (TextView) view.findViewById(R.id.txtShortAnsTitle);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }


            // Set the results into TextViews
            map = new HashMap<>(position);


            holder.heading.setText(books.get(position).get("ques"));
            holder.Questionnumber.setText("Q ." + books.get(position).get("ques_no"));
            holder.txtShortAnsTitle.setVisibility(books != null && books.get(position).get("ques_no").equals("1") ? View.VISIBLE : View.GONE);
            holder.txtShortAnsTitle.setText(books != null ? books.get(position).get("ch_name") : "");
            holder.txtShortAnsTitle.setBackgroundColor(getResources().getColor(R.color.color1));
            holder.checkbox10.setChecked(books.get(position).get("isSelected").equals("0") ? false : true);
            holder.checkbox10.setTag(books.get(position));
            holder.checkbox10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int Marks = 0;
                    if (Marksfor_LongQuestions.getText().toString().isEmpty() == true) {
                        Toast.makeText(mContext, "Please Enter Marks for each question First", Toast.LENGTH_LONG).show();
                        holder.checkbox10.setChecked(false);
                    } else if (!isValid(Marksfor_LongQuestions.getText().toString())) {
                        Marksfor_LongQuestions.setError("Please remove special character");
                    } else {

                        CheckBox cb = (CheckBox) v;
                        map = (HashMap<String, String>) cb.getTag();
                        map.put("isSelected", cb.isChecked() ? "1" : "0");
                        books.get(position).put("isSelected", cb.isChecked() ? "1" : "0");
                        if (cb.isChecked()) {

                            //TODO : map_ch_q_id replace fq_id with dynamic
                            HashMap<String, String> map_ch_q_id = new HashMap<>();
                            //String string = books.get(position).get("ch_id") + "\t" + books.get(position).get("fq_id");
                            //String string = "ch_id " + books.get(position).get("ch_id") + "\tlq_id" + books.get(position).get("lq_id") + "\tmarks" + Marksfor_LongQuestions.getText().toString();
                            String string = " " + books.get(position).get("ch_id");
                            String q_id = "" + books.get(position).get("lq_id");
                            String marks = Marksfor_LongQuestions.getText().toString();

                            /*map_ch_q_id.put("ch_ques_id", string);
                            map_ch_q_id.put("q_id", q_id);
                            map_ch_q_id.put("marks_id", marks);
                            ch_ques_id.add(map_ch_q_id);*/

                            if (books.get(position).get("isSelected").equals("1")) {
                                long_question_id_list.add(q_id);
                                long_ch_id_list.add(string);
                            }


                            Log.d("long_questionery", long_question_id_list.toString());


                            //ToatlMarks = ToatlMarks + Integer.parseInt(Marksfor_LongQuestions.getText().toString());
                            Log.d("MarksMarksMarks", String.valueOf(ToatlMarks));
                            LongAnswer.setText(String.valueOf(getLCount()));//(String.valueOf(ToatlMarks));

                            HashMap<String, String> confimition = new HashMap<>();
                            confimition.put("Confirmation", books.get(position).get("lq_id"));
                            LongQuestionID.add(books.get(position).get("lq_id"));
                            Log.d("213654", books.get(position).get("lq_id"));
                            /*int LongAnswerT = Integer.parseInt(LongAnswer.getText().toString());
                            int ShortAnswerT = Integer.parseInt(ShortAnswer.getText().toString());
                            int MultipleAnswerT = Integer.parseInt(MultipleAnswer.getText().toString());
                            int fillinblacksT = Integer.parseInt(fillinblacks.getText().toString());
                            int truefalseT = Integer.parseInt(truefalse.getText().toString());
                            int MatchT = Integer.parseInt(MatchMakingT.getText().toString());
                            finalTotalmarks = LongAnswerT + ShortAnswerT + MultipleAnswerT + fillinblacksT + truefalseT + MatchT;*/
                            //TotalMarksbutton.setText(String.valueOf(finalTotalmarks));
                            TotalMarksbutton.setText(String.valueOf(finalCount()));

                        } else if (!cb.isChecked()) {


                            //TODO : map_ch_q_id uncomment when done dynamically
                            /*if (ch_ques_id.size() > position) {
                                ch_ques_id.remove(position);
                            }*/
                            if (long_question_id_list.size() > position) {
                                long_question_id_list.remove(position);
                            }
                            Log.d("long_questionery", long_question_id_list.toString());
                            //long_question_id_list.remove(position);

                            if (long_ch_id_list.size() > position) {
                                long_ch_id_list.remove(position);
                            }


                            //ToatlMarks = ToatlMarks - Integer.parseInt(Marksfor_LongQuestions.getText().toString());
                            Log.d("MarksMarksMarks", String.valueOf(ToatlMarks));
                            LongAnswer.setText(String.valueOf(getLCount()));//(String.valueOf(ToatlMarks));
                            /*int LongAnswerT = Integer.parseInt(LongAnswer.getText().toString());
                            int ShortAnswerT = Integer.parseInt(ShortAnswer.getText().toString());
                            int MultipleAnswerT = Integer.parseInt(MultipleAnswer.getText().toString());
                            int fillinblacksT = Integer.parseInt(fillinblacks.getText().toString());
                            int truefalseT = Integer.parseInt(truefalse.getText().toString());
                            int MatchT = Integer.parseInt(MatchMakingT.getText().toString());
                            finalTotalmarks = LongAnswerT + ShortAnswerT + MultipleAnswerT + fillinblacksT + truefalseT + MatchT;*/
                            //TotalMarksbutton.setText(String.valueOf(finalTotalmarks));
                            TotalMarksbutton.setText(String.valueOf(finalCount()));
                            if (position < LongQuestionID.size()) {
                                LongQuestionID.remove(position);
                            }
                        }
                    }
                }
            });
          /*  holder.checkbox10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                }
            });*/
            return view;
        }
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

    public class ShortAnswerAdapter extends BaseAdapter {

        // Declare Variables
        ArrayList<HashMap<String, String>> books;
        QuetionPaper mContext;
        LayoutInflater inflater;
        ArrayList<HashMap<String, String>> animalNamesList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        String upcomigID;


        public ShortAnswerAdapter(QuetionPaper studentdashBord, ArrayList<HashMap<String, String>> booksname) {
            this.mContext = studentdashBord;
            this.books = booksname;
            inflater = LayoutInflater.from((Context) mContext);

        }

        public class ViewHolder {
            TextView name, ID, Number, heading, Questionnumber, txtShortAnsTitle;
            // Button details,Medicalhistory;
            ImageView imgg;
            CheckBox checkbox10;
        }

        @Override
        public int getCount() {
            return books.size();
        }

        public ArrayList<HashMap<String, String>> getShortQuestionList() {
            return books;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View view, ViewGroup parent) {
            final ViewHolder holder;

            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.short_answer_layout, null);
                holder.heading = (TextView) view.findViewById(R.id.heading);
                holder.Questionnumber = (TextView) view.findViewById(R.id.Questionnumber);
                holder.checkbox10 = (CheckBox) view.findViewById(R.id.checkbox10);
                holder.txtShortAnsTitle = (TextView) view.findViewById(R.id.txtShortAnsTitle);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            // Set the results into TextViews
            map = new HashMap<>(position);

            holder.heading.setText(books.get(position).get("ques"));
            holder.Questionnumber.setText("Q ." + books.get(position).get("ques_no"));
            holder.txtShortAnsTitle.setVisibility(books != null && books.get(position).get("ques_no").equals("1") ? View.VISIBLE : View.GONE);
            holder.txtShortAnsTitle.setText(books != null ? books.get(position).get("ch_name") : "");
            holder.txtShortAnsTitle.setBackgroundColor(getResources().getColor(R.color.color2));
            holder.checkbox10.setChecked(books.get(position).get("isSelected").equals("0") ? false : true);
            holder.checkbox10.setTag(books.get(position));
            holder.checkbox10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int Marks = 0;
                    if (Marksfor_ShortQuestions.getText().toString().isEmpty() == true) {
                        Toast.makeText(mContext, "Please Enter Marks for each question First", Toast.LENGTH_LONG).show();
                        holder.checkbox10.setChecked(false);
                    } else if (!isValid(Marksfor_ShortQuestions.getText().toString())) {
                        Marksfor_ShortQuestions.setError("Please remove special character");
                    } else {
                        CheckBox cb = (CheckBox) v;
                        map = (HashMap<String, String>) cb.getTag();
                        map.put("isSelected", cb.isChecked() ? "1" : "0");
                        books.get(position).put("isSelected", cb.isChecked() ? "1" : "0");

                        if (cb.isChecked()) {

                            //TODO : map_ch_q_id replace fq_id with dynamic
                            HashMap<String, String> map_ch_q_id = new HashMap<>();
                            //String string = books.get(position).get("ch_id") + "\t" + books.get(position).get("tf_id");
                            //String string = "ch_id " + books.get(position).get("ch_id") + "\tsq_id" + books.get(position).get("sq_id") + "\tmarks" + Marksfor_ShortQuestions.getText().toString();
                            String string = "ch_id " + books.get(position).get("ch_id");
                            String q_id = "\tlq_id" + books.get(position).get("sq_id");
                            String marks = Marksfor_ShortQuestions.getText().toString();

                            map_ch_q_id.put("ch_ques_id", string);
                            map_ch_q_id.put("q_id", q_id);
                            map_ch_q_id.put("marks_id", marks);
                            ch_ques_id.add(map_ch_q_id);

                            short_question_id_list.add(q_id);
                            short_ch_id_list.add(string);


                            ToatlMarksLOgnd = ToatlMarksLOgnd + Integer.parseInt(Marksfor_ShortQuestions.getText().toString().trim().replaceAll("\\s", ""));
                            Log.d("MarksMarksMarks", String.valueOf(ToatlMarks));
                            //ShortAnswer.setText(String.valueOf(ToatlMarksLOgnd));
                            ShortAnswer.setText(String.valueOf(getSCount()));
                            HashMap<String, String> confimition = new HashMap<>();
                            confimition.put("Confirmation", books.get(position).get("Quetion"));
                            confimition.put("Marks", String.valueOf(Marks));
                            ShortQuestionID.add(books.get(position).get("sq_id"));
                            /*int LongAnswerT = Integer.parseInt(LongAnswer.getText().toString());
                            int ShortAnswerT = Integer.parseInt(ShortAnswer.getText().toString());
                            int MultipleAnswerT = Integer.parseInt(MultipleAnswer.getText().toString());
                            int fillinblacksT = Integer.parseInt(fillinblacks.getText().toString());
                            int truefalseT = Integer.parseInt(truefalse.getText().toString());
                            int MatchT = Integer.parseInt(MatchMakingT.getText().toString());
                            finalTotalmarks = LongAnswerT + ShortAnswerT + MultipleAnswerT + fillinblacksT + truefalseT + MatchT;*/
                            //TotalMarksbutton.setText(String.valueOf(finalTotalmarks));
                            TotalMarksbutton.setText(String.valueOf(finalCount()));
                        } else if (!cb.isChecked()) {

                            //TODO : map_ch_q_id uncomment when done dynamically
                            /*if (ch_ques_id.size() > position) {
                                ch_ques_id.remove(position);
                            }*/
                            if (short_question_id_list.size() > position) {
                                short_question_id_list.remove(position);
                            }
                            if (short_ch_id_list.size() > position) {
                                short_ch_id_list.remove(position);
                            }

                            ToatlMarksLOgnd = ToatlMarksLOgnd - Integer.parseInt(Marksfor_ShortQuestions.getText().toString().trim().replaceAll("\\s", ""));
                            Log.d("MarksMarksMarks", String.valueOf(ToatlMarks));
                            //ShortAnswer.setText(String.valueOf(ToatlMarksLOgnd));
                            ShortAnswer.setText(String.valueOf(getSCount()));

                            TotalMarksbutton.setText(String.valueOf(finalCount()));
                            if (position < ShortQuestionID.size()) {
                                ShortQuestionID.remove(position);
                            }
                        }
                    }
                }
            });
            return view;
        }
    }


    public class TureFalseAdapter extends BaseAdapter {

        // Declare Variables
        ArrayList<HashMap<String, String>> books;
        QuetionPaper mContext;
        LayoutInflater inflater;
        ArrayList<HashMap<String, String>> animalNamesList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        String upcomigID;


        public TureFalseAdapter(QuetionPaper studentdashBord, ArrayList<HashMap<String, String>> booksname) {
            this.mContext = studentdashBord;
            this.books = booksname;
            inflater = LayoutInflater.from((Context) mContext);

        }

        public ArrayList<HashMap<String, String>> getTFQuestionList() {
            return books;
        }

        public class ViewHolder {
            TextView name, ID, Number, heading, Questionnumber, txtTrueFalseTitle, trueQuestion, falseQuestion;
            // Button details,Medicalhistory;
            ImageView imgg;
            CheckBox checkbox104;
        }

        @Override
        public int getCount() {
            return books.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View view1, ViewGroup parent) {
            final ViewHolder holder;

            if (view1 == null) {
                holder = new ViewHolder();
                view1 = inflater.inflate(R.layout.truefalselist, null);
                holder.trueQuestion = view1.findViewById(R.id.trueQuestion);
                holder.falseQuestion = view1.findViewById(R.id.falseQuestion);
                holder.heading = (TextView) view1.findViewById(R.id.heading);
                holder.checkbox104 = (CheckBox) view1.findViewById(R.id.checkboxturefalse);
                holder.Questionnumber = (TextView) view1.findViewById(R.id.Questionnumber);
                holder.txtTrueFalseTitle = (TextView) view1.findViewById(R.id.txtTrueFalseTitle);

                view1.setTag(holder);
            } else {
                holder = (ViewHolder) view1.getTag();
            }
            // Set the results into TextViews
            map = new HashMap<>(position);
            holder.trueQuestion.setClickable(false);
            holder.falseQuestion.setClickable(false);
            holder.heading.setText(books.get(position).get("ques"));
            holder.Questionnumber.setText("Q ." + books.get(position).get("ques_no"));
            holder.checkbox104.setChecked(books.get(position).get("isSelected").equals("0") ? false : true);
            holder.txtTrueFalseTitle.setVisibility((books != null && books.get(position).get("ques_no").equals("1") ? View.VISIBLE : View.GONE));
            holder.txtTrueFalseTitle.setText((books != null ? books.get(position).get("ch_name") : ""));
            holder.checkbox104.setTag(books.get(position));
            holder.checkbox104.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int Marks = 0;
                    if (MarksTurefalse.getText().toString().isEmpty() == true) {
                        Toast.makeText(mContext, "Please Enter Marks for each question First", Toast.LENGTH_LONG).show();
                        holder.checkbox104.setChecked(false);
                    } else if (!isValid(MarksTurefalse.getText().toString())) {
                        MarksTurefalse.setError("Please remove special character");
                    } else {
                        CheckBox cb = (CheckBox) v;
                        map = (HashMap<String, String>) cb.getTag();
                        map.put("isSelected", cb.isChecked() ? "1" : "0");
                        books.get(position).put("isSelected", cb.isChecked() ? "1" : "0");
                        if (cb.isChecked()) {

                            //TODO : map_ch_q_id
                            HashMap<String, String> map_ch_q_id = new HashMap<>();
                            //String string = books.get(position).get("ch_id") + "\t" + books.get(position).get("tf_id");
                            //String string = "ch_id " + books.get(position).get("ch_id") + "\ttf_id" + books.get(position).get("tf_id") + "\tmarks" + MarksTurefalse.getText().toString();
                            String string = "ch_id " + books.get(position).get("ch_id");
                            String q_id = "\tlq_id" + books.get(position).get("tf_id");
                            String marks = MarksTurefalse.getText().toString();

                            map_ch_q_id.put("ch_ques_id", string);
                            map_ch_q_id.put("q_id", q_id);
                            map_ch_q_id.put("marks_id", marks);
                            ch_ques_id.add(map_ch_q_id);

                            tf_question_id_list.add(q_id);
                            tf_ch_id_list.add(string);

                            TotalMarksTruefalse = TotalMarksTruefalse + Integer.parseInt(MarksTurefalse.getText().toString().trim().replaceAll("\\s", ""));
                            Log.d("MarksMarksMarks", String.valueOf(TotalMarksTruefalse));
                            truefalse.setText(String.valueOf(getTrueFalseCount()));
                            //truefalse.setText(String.valueOf(TotalMarksTruefalse));
                            HashMap<String, String> confimition = new HashMap<>();
                            confimition.put("Confirmation", books.get(position).get("Quetion"));
                            confimition.put("Marks", String.valueOf(Marks));
                            confimition.put("Option_1", "Option_1");
                            confimition.put("Option_2", "Option_2");
                            confimition.put("Option_3", "Option_3");
                            confimition.put("Option_4", "Option_4");
                            TruefalseQuestionID.add(books.get(position).get("tf_id"));

                            TotalMarksbutton.setText(String.valueOf(finalCount()));

                        } else if (!cb.isChecked()) {

                            if (ch_ques_id.size() > position) {
                                ch_ques_id.remove(position);
                            }
                            if (tf_question_id_list.size() > position) {
                                tf_question_id_list.remove(position);
                            }
                            if (tf_ch_id_list.size() > position) {
                                tf_ch_id_list.remove(position);
                            }

                            TotalMarksTruefalse = TotalMarksTruefalse - Integer.parseInt(MarksTurefalse.getText().toString().trim().replaceAll("\\s", ""));
                            Log.d("MarksMarksMarks", String.valueOf(ToatlMarks));
                            truefalse.setText(String.valueOf(getTrueFalseCount()));
                            //truefalse.setText(String.valueOf(TotalMarksTruefalse));

                            TotalMarksbutton.setText(String.valueOf(finalCount()));
                            if (position < TruefalseQuestionID.size()) {
                                TruefalseQuestionID.remove(position);
                            }
                        }
                    }
                }
            });
            return view1;
        }
    }

    public class FillinTheblankApter extends BaseAdapter {

        // Declare Variables
        ArrayList<HashMap<String, String>> books;
        QuetionPaper mContext;
        LayoutInflater inflater;
        ArrayList<HashMap<String, String>> animalNamesList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        String upcomigID;


        public FillinTheblankApter(QuetionPaper studentdashBord, ArrayList<HashMap<String, String>> booksname) {
            this.mContext = studentdashBord;
            this.books = booksname;
            inflater = LayoutInflater.from((Context) mContext);
        }

        public class ViewHolder {
            TextView name, ID, Number, heading, Questionnumber, txtFillintheBlanksTitle;
            // Button details,Medicalhistory;
            ImageView imgg;
            CheckBox checkbox102;
        }

        public ArrayList<HashMap<String, String>> getFillInBlanksQuestionList() {
            return books;
        }

        @Override
        public int getCount() {
            return books.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View view, ViewGroup parent) {
            final ViewHolder holder;

            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.fileintheblanck, null);
                holder.heading = (TextView) view.findViewById(R.id.heading);
                holder.checkbox102 = (CheckBox) view.findViewById(R.id.checkboxfillintheblanks);
                holder.Questionnumber = (TextView) view.findViewById(R.id.Questionnumber);
                holder.txtFillintheBlanksTitle = (TextView) view.findViewById(R.id.txtFillintheBlanksTitle);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            // Set the results into TextViews
            map = new HashMap<>(position);

            holder.txtFillintheBlanksTitle.setVisibility(books != null && books.get(position).get("ques_no").equals("1") ? View.VISIBLE : View.GONE);
            holder.txtFillintheBlanksTitle.setText(books != null ? books.get(position).get("ch_name") : "");
            holder.heading.setText(books.get(position).get("ques"));
            holder.Questionnumber.setText("Q ." + books.get(position).get("ques_no"));
            holder.checkbox102.setChecked(books.get(position).get("isSelected").equals("0") ? false : true);

            holder.checkbox102.setTag(books.get(position));
            holder.checkbox102.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int Marks = 0;
                    if (Marksfillinblacks.getText().toString().isEmpty() == true) {
                        Toast.makeText(mContext, "Please Enter Marks for each question First", Toast.LENGTH_LONG).show();
                        holder.checkbox102.setChecked(false);
                    } else if (!isValid(Marksfillinblacks.getText().toString())) {
                        Marksfillinblacks.setError("Please remove special character");
                    } else {

                        CheckBox cb = (CheckBox) v;
                        map = (HashMap<String, String>) cb.getTag();
                        map.put("isSelected", cb.isChecked() ? "1" : "0");
                        books.get(position).put("isSelected", cb.isChecked() ? "1" : "0");
                        if (cb.isChecked()) {

                            //TODO : map_ch_q_id
                            HashMap<String, String> map_ch_q_id = new HashMap<>();
                            //String string = books.get(position).get("ch_id") + "\t" + books.get(position).get("fq_id");
                            //String string = "ch_id " + books.get(position).get("ch_id") + "\tfq_id" + books.get(position).get("fq_id") + "\tmarks" + Marksfillinblacks.getText().toString();

                            String string = "ch_id " + books.get(position).get("ch_id");
                            String q_id = "\tfq_id" + books.get(position).get("fq_id");
                            String marks = Marksfillinblacks.getText().toString();

                            map_ch_q_id.put("ch_ques_id", string);
                            map_ch_q_id.put("q_id", q_id);
                            map_ch_q_id.put("marks_id", marks);
                            ch_ques_id.add(map_ch_q_id);

                            fill_question_id_list.add(q_id);
                            fill_ch_id_list.add(string);

                            TotalMarksFilblancks = TotalMarksFilblancks + Integer.parseInt(Marksfillinblacks.getText().toString().trim().replaceAll("\\s", ""));
                            Log.d("MarksMarksMarks", String.valueOf(ToatlMarks));
                            fillinblacks.setText(String.valueOf(getFillInBlankCount()));
                            //fillinblacks.setText(String.valueOf(TotalMarksFilblancks));
                            HashMap<String, String> confimition = new HashMap<>();
                            confimition.put("Confirmation", books.get(position).get("Quetion"));
                            FillQuestionID.add(books.get(position).get("fq_id"));
                            //TotalMarksbutton.setText(String.valueOf(finalTotalmarks));
                            TotalMarksbutton.setText(String.valueOf(finalCount()));

                        } else if (!cb.isChecked()) {

                            if (ch_ques_id.size() > position) {
                                ch_ques_id.remove(position);
                            }
                            if (fill_ch_id_list.size() > position) {
                                fill_ch_id_list.remove(position);
                            }
                            if (fill_question_id_list.size() > position) {
                                fill_question_id_list.remove(position);
                            }

                            TotalMarksFilblancks = TotalMarksFilblancks - Integer.parseInt(Marksfillinblacks.getText().toString().trim().replaceAll("\\s", ""));
                            Log.d("MarksMarksMarks", String.valueOf(ToatlMarks));
                            fillinblacks.setText(String.valueOf(getFillInBlankCount()));

                            TotalMarksbutton.setText(String.valueOf(finalCount()));
                            if (position < FillQuestionID.size()) {
                                FillQuestionID.remove(position);
                            }
                        }
                    }
                }
            });
            return view;
        }
    }

    //TODO : Match the column
    public class MatchMakingAdapter extends BaseAdapter {

        // Declare Variables
        ArrayList<HashMap<String, String>> books;
        String imageurl;
        QuetionPaper mContext;
        LayoutInflater inflater;
        ArrayList<HashMap<String, String>> animalNamesList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        String upcomigID;
        String Option1, Option2, Option3, Option4, QUESTION;


        public MatchMakingAdapter(QuetionPaper studentdashBord, ArrayList<HashMap<String, String>> booksname) {
            this.mContext = studentdashBord;
            this.books = booksname;
            inflater = LayoutInflater.from((Context) mContext);
        }

        public ArrayList<HashMap<String, String>> getMatchMakingQuestionList() {
            return books;
        }

        public class ViewHolder {
            TextView txtQnoMColTG, txtCol1r1TG, txtCol1r2TG, txtCol1r3TG, txtCol1r4TG, txtCol1r5TG, txtCol1r6TG, txtCol1r7TG, txtCol1r8TG,
                    txtCol2r1TG, txtCol2r2TG, txtCol2r3TG, txtCol2r4TG, txtCol2r5TG, txtCol2r6TG, txtCol2r7TG, txtCol2r8TG;
            CheckBox ChkMCTG;
            // Button details,Medicalhistory;
            ImageView imgg, QuestionImage, Imageoption1, Imageoption2, Imageoption3, Imageoption4;
            CheckBox checkbox10;
        }

        @Override
        public int getCount() {
            return books.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View view, ViewGroup parent) {
            final ViewHolder holder;

            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.matchqlay, null);
                holder.txtQnoMColTG = (TextView) view.findViewById(R.id.txtQnoMColTG);
                holder.txtCol1r1TG = (TextView) view.findViewById(R.id.txtCol1r1TG);
                holder.txtCol1r2TG = (TextView) view.findViewById(R.id.txtCol1r2TG);
                holder.txtCol1r3TG = (TextView) view.findViewById(R.id.txtCol1r3TG);
                holder.txtCol1r4TG = (TextView) view.findViewById(R.id.txtCol1r4TG);
                holder.txtCol1r5TG = (TextView) view.findViewById(R.id.txtCol1r5TG);
                holder.txtCol1r6TG = (TextView) view.findViewById(R.id.txtCol1r6TG);
                holder.txtCol1r7TG = (TextView) view.findViewById(R.id.txtCol1r7TG);
                holder.txtCol1r8TG = (TextView) view.findViewById(R.id.txtCol1r8TG);

                holder.txtCol2r1TG = (TextView) view.findViewById(R.id.txtCol2r1TG);
                holder.txtCol2r2TG = (TextView) view.findViewById(R.id.txtCol2r2TG);
                holder.txtCol2r3TG = (TextView) view.findViewById(R.id.txtCol2r3TG);
                holder.txtCol2r4TG = (TextView) view.findViewById(R.id.txtCol2r4TG);
                holder.txtCol2r5TG = (TextView) view.findViewById(R.id.txtCol2r5TG);
                holder.txtCol2r6TG = (TextView) view.findViewById(R.id.txtCol2r6TG);
                holder.txtCol2r7TG = (TextView) view.findViewById(R.id.txtCol2r7TG);
                holder.txtCol2r8TG = (TextView) view.findViewById(R.id.txtCol2r8TG);

                holder.ChkMCTG = (CheckBox) view.findViewById(R.id.ChkMCTG);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            // Set the results into TextViews
            map = new HashMap<>(position);

            //region "FindViewById MCol"
            //holder.txtQnoMColTG.setText("Chapter\t:\t" + books.get(position).get("q_no"));
            holder.txtQnoMColTG.setText("Chapter\t:\t" + books.get(position).get("ch_name"));
            holder.txtCol1r1TG.setText("1.\t" + books.get(position).get("column1r1"));
            holder.txtCol1r2TG.setText("2.\t" + books.get(position).get("column1r2"));
            holder.txtCol1r3TG.setText("3.\t" + books.get(position).get("column1r3"));
            holder.txtCol1r4TG.setText("4.\t" + books.get(position).get("column1r4"));
            holder.txtCol1r5TG.setText("5.\t" + books.get(position).get("column1r5"));
            holder.txtCol1r6TG.setText("6.\t" + books.get(position).get("column1r6"));
            holder.txtCol1r7TG.setText("7.\t" + books.get(position).get("column1r7"));
            holder.txtCol1r8TG.setText("8.\t" + books.get(position).get("column1r8"));


            holder.txtCol2r1TG.setText(books.get(position).get("column2r1"));
            holder.txtCol2r2TG.setText(books.get(position).get("column2r2"));
            holder.txtCol2r3TG.setText(books.get(position).get("column2r3"));
            holder.txtCol2r4TG.setText(books.get(position).get("column2r4"));
            holder.txtCol2r5TG.setText(books.get(position).get("column2r5"));
            holder.txtCol2r6TG.setText(books.get(position).get("column2r6"));
            holder.txtCol2r7TG.setText(books.get(position).get("column2r7"));
            holder.txtCol2r8TG.setText(books.get(position).get("column2r8"));
            //endregion


            holder.txtCol1r1TG.setVisibility(!books.get(position).get("column1r1").equals("") ? View.VISIBLE : View.GONE);
            holder.txtCol1r2TG.setVisibility(!books.get(position).get("column1r2").equals("") ? View.VISIBLE : View.GONE);
            holder.txtCol1r3TG.setVisibility(!books.get(position).get("column1r3").equals("") ? View.VISIBLE : View.GONE);
            holder.txtCol1r4TG.setVisibility(!books.get(position).get("column1r4").equals("") ? View.VISIBLE : View.GONE);
            holder.txtCol1r5TG.setVisibility(!books.get(position).get("column1r5").equals("") ? View.VISIBLE : View.GONE);
            holder.txtCol1r6TG.setVisibility(!books.get(position).get("column1r6").equals("") ? View.VISIBLE : View.GONE);
            holder.txtCol1r7TG.setVisibility(!books.get(position).get("column1r7").equals("") ? View.VISIBLE : View.GONE);
            holder.txtCol1r8TG.setVisibility(!books.get(position).get("column1r8").equals("") ? View.VISIBLE : View.GONE);

            holder.txtCol2r1TG.setVisibility(!books.get(position).get("column2r1").equals("") ? View.VISIBLE : View.GONE);
            holder.txtCol2r2TG.setVisibility(!books.get(position).get("column2r2").equals("") ? View.VISIBLE : View.GONE);
            holder.txtCol2r3TG.setVisibility(!books.get(position).get("column2r3").equals("") ? View.VISIBLE : View.GONE);
            holder.txtCol2r4TG.setVisibility(!books.get(position).get("column2r4").equals("") ? View.VISIBLE : View.GONE);
            holder.txtCol2r5TG.setVisibility(!books.get(position).get("column2r5").equals("") ? View.VISIBLE : View.GONE);
            holder.txtCol2r6TG.setVisibility(!books.get(position).get("column2r6").equals("") ? View.VISIBLE : View.GONE);
            holder.txtCol2r7TG.setVisibility(!books.get(position).get("column2r7").equals("") ? View.VISIBLE : View.GONE);
            holder.txtCol2r8TG.setVisibility(!books.get(position).get("column2r8").equals("") ? View.VISIBLE : View.GONE);

            holder.ChkMCTG.setChecked(books.get(position).get("isSelected") != null && !books.get(position).get("isSelected").equals("0"));
            holder.ChkMCTG.setTag(books.get(position));
            holder.ChkMCTG.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (MarksMatchMaking.getText().toString().isEmpty()) {
                        Toast.makeText(mContext, "Please Enter Marks for each question First", Toast.LENGTH_LONG).show();
                        holder.ChkMCTG.setChecked(false);
                    } else if (!isValid(MarksMatchMaking.getText().toString())) {
                        MarksMatchMaking.setError("Please remove special character");
                    } else {
                        //region "Checked Unchecked"
                        CheckBox cb = (CheckBox) v;
                        map = (HashMap<String, String>) cb.getTag();
                        map.put("isSelected", cb.isChecked() ? "1" : "0");
                        books.get(position).put("isSelected", cb.isChecked() ? "1" : "0");
                        if (cb.isChecked()) {

                            //TODO : map_ch_q_id
                            HashMap<String, String> map_ch_q_id = new HashMap<>();
                            //String string = "ch_id " + books.get(position).get("ch_id") + "\tmc_id" + books.get(position).get("mc_id") + "\tmarks" + MarksMatchMaking.getText().toString();

                            /*String string = "ch_id " + books.get(position).get("ch_id");
                            String q_id = "\tmc_id" + books.get(position).get("mc_id");
                            String marks = MarksMatchMaking.getText().toString();

                            map_ch_q_id.put("ch_ques_id", string);
                            map_ch_q_id.put("q_id", q_id);
                            map_ch_q_id.put("marks_id", marks);
                            ch_ques_id.add(map_ch_q_id);

                            match_question_id_list.add(q_id);
                            match_ch_id_list.add(string);*/

                            TotalMatchmaikg = TotalMatchmaikg + Integer.parseInt(MarksMatchMaking.getText().toString().trim().replaceAll("\\s", ""));
                            Log.d("TotalMatchmaikg", String.valueOf(TotalMatchmaikg));
                            MatchMakingT.setText(String.valueOf(getMatchMakingCount()));
                            HashMap<String, String> confimition = new HashMap<>();
                            confimition.put("QuestionID", books.get(position).get("id"));
                            MatchQuestionID.add(books.get(position).get("mc_id"));

                            TotalMarksbutton.setText(String.valueOf(finalCount()));
                        } else if (!cb.isChecked()) {

                            TotalMatchmaikg = TotalMatchmaikg - Integer.parseInt(MarksMatchMaking.getText().toString().trim().replaceAll("\\s", ""));
                            Log.d("MarksMarksMarks", String.valueOf(TotalMatchmaikg));
                            MatchMakingT.setText(String.valueOf(getMatchMakingCount()));

                            TotalMarksbutton.setText(String.valueOf(finalCount()));
                            if (position < MatchQuestionID.size()) {
                                MatchQuestionID.remove(position);
                            }
                        }//endregion
                    }
                }
            });
            return view;
        }
    }

    //TODO : Note : MCQ Adapter
    public class MCQAdapter extends BaseAdapter {

        // Declare Variables
        ArrayList<HashMap<String, String>> books;
        String imageurl;
        QuetionPaper mContext;
        LayoutInflater inflater;
        ArrayList<HashMap<String, String>> animalNamesList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        String upcomigID;
        String Option1, Option2, Option3, Option4, QUESTION;

        public MCQAdapter(QuetionPaper studentdashBord, ArrayList<HashMap<String, String>> booksname) {
            this.mContext = studentdashBord;
            this.books = booksname;
            inflater = LayoutInflater.from((Context) mContext);
        }

        public ArrayList<HashMap<String, String>> getMcqQuestionList() {
            return books;
        }

        public class ViewHolder {
            TextView name, ID, Number, heading, Option1, Option2, Option3, Option4, Questionnumber, txtMCQTitle;
            // Button details,Medicalhistory;
            ImageView imgg, QuestionImage, Imageoption1, Imageoption2, Imageoption3, Imageoption4;
            CheckBox checkbox10;
        }

        @Override
        public int getCount() {
            Log.d("ListSize", String.valueOf(books.size()));
            return books.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View view, ViewGroup parent) {
            final ViewHolder holder;

            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.matchmakinglayout, null);

                holder.txtMCQTitle = (TextView) view.findViewById(R.id.txtMCQTitle);
                holder.heading = (TextView) view.findViewById(R.id.heading);
                holder.Option1 = (TextView) view.findViewById(R.id.Option1);
                holder.Option2 = (TextView) view.findViewById(R.id.Option2);
                holder.Option3 = (TextView) view.findViewById(R.id.Option3);
                holder.Option4 = (TextView) view.findViewById(R.id.Option4);
                holder.Questionnumber = (TextView) view.findViewById(R.id.Questionnumber);

                holder.checkbox10 = (CheckBox) view.findViewById(R.id.MatchCheck);

                holder.QuestionImage = (ImageView) view.findViewById(R.id.QuestionImage);
                holder.Imageoption1 = (ImageView) view.findViewById(R.id.Imageoption1);
                holder.Imageoption2 = (ImageView) view.findViewById(R.id.Imageoption2);
                holder.Imageoption3 = (ImageView) view.findViewById(R.id.Imageoption3);
                holder.Imageoption4 = (ImageView) view.findViewById(R.id.Imageoption4);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            // Set the results into TextViews


            map = new HashMap<>(position);
            String head = books.get(position).get("ques");
            String option1 = books.get(position).get("option_a");
            String option2 = books.get(position).get("option_b");
            String option3 = books.get(position).get("option_c");
            String option4 = books.get(position).get("option_d");
            String Answer = books.get(position).get("Answer");
            // String imageurl = books.get(position).get("ques");
//            Log.d("headheadimageurl", head);
            /*try {*/

            if (head.contains("-")) {
                List<String> elephantList = Arrays.asList(head.split("-"));
                Log.d("headheadimageurl", elephantList.get(0) + "========" + elephantList.get(1));
                QUESTION = elephantList.get(0);
                imageurl = "https://brainmate.co.in/studybuddy/question_img/" + elephantList.get(1);
                Log.w("QPImage","image:   MCQAdaptor="+imageurl);
            }


            String str = head.substring(head.length() - 3, head.length());


            if (str.equals("jpg") || str.equals("png")) {
                Log.w("QPImage","Question is in image");
                Log.w("QPImage","ImageType MCQAdaptor:"+str);
                holder.QuestionImage.setVisibility(View.VISIBLE);
                //   holder.heading.setVisibility(View.GONE);
                holder.heading.setVisibility(View.VISIBLE);
                holder.heading.setText(QUESTION);
                Log.d("imageurl Ques:", str);

                Glide.with(mContext).load(imageurl)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.QuestionImage);

            } else {
                Log.w("QPImage","Question is in Text");
                holder.QuestionImage.setVisibility(View.GONE);
                holder.heading.setVisibility(View.VISIBLE);
                holder.heading.setText(books.get(position).get("ques"));
                Log.w("QPImage","Ques:"+books.get(position).get("option_a"));
            }
            if (option1.contains("-")) {
                Log.w("QPImage","Option2 is contain -");
                List<String> elephantList = Arrays.asList(option1.split("-"));
                Log.d("headheadimageurl", elephantList.get(0) + "========" + elephantList.get(1));
                Option1 = "https://brainmate.co.in/studybuddy/question_img/" + elephantList.get(1);
                Log.w("QPImage","imgUrl Option4:"+option2);
            }
            String str1 = "";
            if (option1.length() > 3) {
                str1 = option1.substring(option1.length() - 3, option1.length());
            }
            if (str1.equals("jpg") || str1.equals("png")) {
                Log.w("QPImage","Option1 is image");
                holder.Imageoption1.setVisibility(View.VISIBLE);
                holder.Option1.setVisibility(View.GONE);
                // Option1= books.get(position).get("option_a");
//                Log.d("imageurl", Option1);
                if (option1 != null) {
                    Glide.with(mContext).load(Option1)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(holder.Imageoption1);
                    Log.w("QPImage","img Option4:"+option2);
                }


            } else {
                Log.w("QPImage","Option1 is Text");
                holder.Imageoption1.setVisibility(View.GONE);
                holder.Option1.setVisibility(View.VISIBLE);
                holder.Option1.setText(books.get(position).get("option_a"));
            }

            if (option2.contains("-")) {
                List<String> elephantList = Arrays.asList(option2.split("-"));
                Log.d("headheadimageurl", elephantList.get(0) + "========" + elephantList.get(1));
                Option2 = "https://brainmate.co.in/studybuddy/question_img/" + elephantList.get(1);
                Log.w("QPImage","imgUrl Option2:"+option2);

            }
            String str2 = "";
            if (option2.length() > 3) {
                str2 = option2.substring(option2.length() - 3, option2.length());
            }
            //str2 = option2.substring(option2.length() - 3, option2.length());
            if (str2.equals("jpg") || str2.equals("png")) {
                Log.w("QPImage","Option2 is image");
                holder.Imageoption2.setVisibility(View.VISIBLE);
                holder.Option2.setVisibility(View.GONE);
                // String Option2 = books.get(position).get("option_b");
                // Log.d("imageurl", Option2);
                if (option2 != null) {
                    Glide.with(mContext).load(Option2)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(holder.Imageoption2);
                    Log.w("QPImage","img Option2:"+option2);
                }

            } else {
                Log.w("QPImage","Option2 is text");
                holder.Imageoption2.setVisibility(View.GONE);
                holder.Option2.setVisibility(View.VISIBLE);
                holder.Option2.setText(books.get(position).get("option_b"));
                Log.w("QPImage","Option2:"+books.get(position).get("option_b"));

            }

            if (option3.contains("-")) {
                List<String> elephantList = Arrays.asList(option3.split("-"));
                Log.d("headheadimageurl", elephantList.get(0) + "========" + elephantList.get(1));
                Option3 = "https://brainmate.co.in/studybuddy/question_img/" + elephantList.get(1);
                Log.w("QPImage","imgUrl Option4:"+option3);
            }
            String str3 = "";
            if (option3.length() > 3) {
                str3 = option3.substring(option3.length() - 3, option3.length());
            }
            // String str3 = option3.substring(option3.length() - 3, option3.length());
            if (str3.equals("jpg") || str3.equals("png")) {
                Log.w("QPImage","Option3 is image");
                holder.Imageoption3.setVisibility(View.VISIBLE);
                holder.Option3.setVisibility(View.GONE);
                // String Option3 = books.get(position).get("option_c");
                // Log.d("imageurl", Option3);

                if (Option3 != null) {
                    Glide.with(mContext).load(Option3)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(holder.Imageoption3);
                    Log.w("QPImage","img Option3:"+option3);
                }

            } else {
                Log.w("QPImage","Option3 is Text");
                holder.Imageoption3.setVisibility(View.GONE);
                holder.Option3.setVisibility(View.VISIBLE);
                holder.Option3.setText(books.get(position).get("option_c"));
                Log.w("QPImage","Option3:"+books.get(position).get("option_c"));
            }

            if (option4.contains("-")) {
                List<String> elephantList = Arrays.asList(option4.split("-"));
                Log.d("headheadimageurl", elephantList.get(0) + "========" + elephantList.get(1));
                Option4 = "https://brainmate.co.in/studybuddy/question_img/" + elephantList.get(1);
                Log.w("QPImage","imgUrl Option4:"+option4);
            }
            String str4 = "";
            if (option4.length() > 3) {
                str4 = option4.substring(option4.length() - 3, option4.length());
            }
            //String str4 = option4.substring(option4.length() - 3, option4.length());
            if (str4.equals("jpg") || str4.equals("png") || str4.equals("jpeg") || str4.equals("JPEG")) {
                holder.Imageoption4.setVisibility(View.VISIBLE);
                holder.Option4.setVisibility(View.GONE);
                // String Option4 = books.get(position).get("option_d");
                //  Log.d("imageurl", Option4);

                if (Option4 != null) {
                    Log.w("QPImage","Option4 is image");
                    Glide.with(mContext).load(Option4)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(holder.Imageoption4);
                    Log.w("QPImage","img Option4:"+option4);

                }

            } else {
                Log.w("QPImage","Option4 is text");
                holder.Imageoption4.setVisibility(View.GONE);
                holder.Option4.setVisibility(View.VISIBLE);
                holder.Option4.setText(books.get(position).get("option_d"));
                Log.w("QPImage","img Option4:"+option4);
                Log.w("QPImage","Option4:"+books.get(position).get("option_d"));
            }

            holder.Questionnumber.setText("Q " + books.get(position).get("ques_no"));
            /**/
            holder.checkbox10.setChecked(books.get(position).get("isSelected").equals("0") ? false : true);
            holder.txtMCQTitle.setVisibility(books != null && books.get(position).get("ques_no").equals("1") ? View.VISIBLE : View.GONE);
            holder.txtMCQTitle.setText(books != null ? books.get(position).get("ch_name") : "");
            holder.checkbox10.setTag(books.get(position));


            holder.checkbox10.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //Toast.makeText(v.getContext(), "Clicked on Checkbox: " + position + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();
                    if (MarksForQbjective.getText().toString().isEmpty() == true) {
                        Toast.makeText(mContext, "Please Enter Marks for each question First", Toast.LENGTH_LONG).show();
                        holder.checkbox10.setChecked(false);
                    } else if (!isValid(MarksForQbjective.getText().toString())) {
                        MarksForQbjective.setError("Please remove special character");
                    } else {
                        //region "Checked Unchecked"
                        CheckBox cb = (CheckBox) v;
                        map = (HashMap<String, String>) cb.getTag();
                        map.put("isSelected", cb.isChecked() ? "1" : "0");
                        books.get(position).put("isSelected", cb.isChecked() ? "1" : "0");
                        if (cb.isChecked()) {

                            //TODO : map_ch_q_id
                            HashMap<String, String> map_ch_q_id = new HashMap<>();
                            //String string = books.get(position).get("ch_id") + "\t" + books.get(position).get("mcq_id");
                            //String string = "ch_id " + books.get(position).get("ch_id") + "\tmcq_id" + books.get(position).get("mcq_id") + "\tmarks" + MarksForQbjective.getText().toString();

                            String string = "ch_id " + books.get(position).get("ch_id");
                            String q_id = "\tmcq_id" + books.get(position).get("mcq_id");
                            String marks = MarksForQbjective.getText().toString();

                            map_ch_q_id.put("ch_ques_id", string);
                            map_ch_q_id.put("q_id", q_id);
                            map_ch_q_id.put("marks_id", marks);
                            ch_ques_id.add(map_ch_q_id);

                            mcq_question_id_list.add(q_id);
                            mcq_ch_id_list.add(string);

                            TotalMarksObjective = TotalMarksObjective + Integer.parseInt(MarksForQbjective.getText().toString().trim().replaceAll("\\s", ""));
                            Log.d("MarksMarksMarks", String.valueOf(TotalMarksObjective));
                            MultipleAnswer.setText(String.valueOf(getMCQCount()));
                            //MultipleAnswer.setText(String.valueOf(TotalMarksObjective));
                            HashMap<String, String> confimition = new HashMap<>();
                            confimition.put("QuestionID", books.get(position).get("id"));
                            MCQQuestionID.add(books.get(position).get("mcq_id"));

                            TotalMarksbutton.setText(String.valueOf(finalCount()));
                        } else if (!cb.isChecked()) {

                            if (ch_ques_id.size() > position) {
                                ch_ques_id.remove(position);
                            }
                            if (mcq_question_id_list.size() > position) {
                                mcq_question_id_list.remove(position);
                            }
                            if (mcq_ch_id_list.size() > position) {
                                mcq_ch_id_list.remove(position);
                            }

                            TotalMarksObjective = TotalMarksObjective - Integer.parseInt(MarksForQbjective.getText().toString().trim().replaceAll("\\s", ""));
                            Log.d("MarksMarksMarks", String.valueOf(TotalMarksObjective));
                            MultipleAnswer.setText(String.valueOf(getMCQCount()));
                            //MultipleAnswer.setText(String.valueOf(TotalMarksObjective) != null ? String.valueOf(TotalMarksObjective) : "");

                            TotalMarksbutton.setText(String.valueOf(finalCount()));
                            if (position < MCQQuestionID.size()) {
                                MCQQuestionID.remove(position);
                            }

                        }//endregion
                    }
                }
            });
            /**/

            return view;
        }

        // method to access in activity after updating selection
        public ArrayList<HashMap<String, String>> getStudentist() {
            return books;
        }
    }

    //region "Submit Request"
    private void reqToSubmit(String feedUrl, final String strLongQuesId, final String strShortQuesId, final String strMcqQuesId, final String strTfQuesId, final String strFillQuesId, final String strMatchColQuesId,
                             final String strLongChId, final String strShortChId, final String strMcqChId, final String strTfChId, final String strFillChId, final String strMatchColChId, final String ch_ques_id,
                             final String email, final String id, final String title, final String classes, final String fullMarks, final String testDate, final String testTime) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, feedUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("validateResponse111", response);
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        try {
                            JSONObject object = new JSONObject(response);
                            String success = object.getString("success");
                            if (success.equals("1")) {
                                Toast.makeText(QuetionPaper.this, "Question Paper Generated.", Toast.LENGTH_LONG).show();
                                //ViewCreatedPdfActivity2
                                Intent intent = new Intent(QuetionPaper.this, ViewCreatedPdfActivity2.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("accesscodes", CommonMethods.getAccessCode(QuetionPaper.this));
                                intent.putExtra("Teachers_ID", CommonMethods.getId(QuetionPaper.this));
                                intent.putExtra("isFromQuestionPaper", true);
                                startActivity(intent);
                                //finish();
                            } else {
                                Toast.makeText(QuetionPaper.this, getString(R.string.some_error_occurred), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        Log.d("getParamsDatas11", "" + volleyError.getMessage());
                        if (volleyError instanceof NoConnectionError) {
                            Toast.makeText(QuetionPaper.this, "Internet not Connected"+volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(QuetionPaper.this, "Some Error Occurred"+volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //region "ques id"
                params.put("LongQuesId", "" + strLongQuesId);
                params.put("ShortQuesId", "" + strShortQuesId);
                params.put("McqQuesId", "" + strMcqQuesId);
                params.put("TfQuesId", "" + strTfQuesId);
                params.put("FillQuesId", "" + strFillQuesId);
                params.put("MatchColQuesId", "" + strMatchColQuesId);
                //endregion
                //region "ch_id"
                params.put("LongChId", "" + strLongChId);
                params.put("ShortChId", "" + strShortChId);
                params.put("McqChId", "" + strMcqChId);
                params.put("TfChId", "" + strTfChId);
                params.put("FillChId", "" + strFillChId);
                params.put("MatchColChId", "" + strMatchColChId);

                params.put("long_ques_marks", "" + Marksfor_LongQuestions.getText().toString());
                params.put("short_ques_marks", "" + Marksfor_ShortQuestions.getText().toString());
                params.put("mcq_ques_marks", "" + MarksForQbjective.getText().toString());
                params.put("tf_ques_marks", "" + MarksTurefalse.getText().toString());
                params.put("fib_ques_marks", "" + Marksfillinblacks.getText().toString());
                    params.put("multiple_ques_marks", "" + MarksMatchMaking.getText().toString());

                params.put("title", "" + title);
                params.put("subject", "" + Subject);
                params.put("class", "" + classes);
                params.put("fullMarks", "" + fullMarks);
                params.put("testDate", "" + testDate);
                params.put("testTime", "" + testTime);

                //endregion
                params.put("email", email);
                params.put("id", id);
                //TODO
                //String strMarks = "long_marks" + Marksfor_LongQuestions.getText().toString() + "short_marks" + Marksfor_ShortQuestions + "mcq_marks" + MarksForQbjective.getText().toString() + "tf_marks" + MarksTurefalse.getText().toString() + "fill_marks" + Marksfillinblacks.getText().toString() + "mcol_marks" + MarksMatchMaking.getText().toString();
                //params.put("marks", "" + strMarks);
                Log.d("getParamsDatas", params.toString());
                return params;
            }
        };
        CommonMethods.callWebserviceForResponse(stringRequest, QuetionPaper.this);
    }//endregion

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
