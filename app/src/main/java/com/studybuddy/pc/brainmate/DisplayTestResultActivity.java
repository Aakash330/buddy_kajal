package com.studybuddy.pc.brainmate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.studybuddy.pc.brainmate.adapters.DisplayFillInBlanksResultAdapter;
import com.studybuddy.pc.brainmate.adapters.DisplayMColResultAdapter;
import com.studybuddy.pc.brainmate.adapters.DisplayMcqResultAdapter;
import com.studybuddy.pc.brainmate.adapters.DisplayTrueFalseResultAdapter;
import com.studybuddy.pc.brainmate.mains.Apis;
import com.studybuddy.pc.brainmate.student.CommonMethods;
import com.studybuddy.pc.brainmate.student.StudentTest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class DisplayTestResultActivity extends AppCompatActivity {

    RecyclerView rvListMcq, rvListTf, rvListFillInBlanks, rvListMCol;
    Context context;
    ArrayList<HashMap<String, String>> fillinTheblanklistt = new ArrayList<>();
    ArrayList<HashMap<String, String>> MCQLIST = new ArrayList<>();
    ArrayList<HashMap<String, String>> truefalseList = new ArrayList<>();
    ArrayList<HashMap<String, String>> mcolList = new ArrayList<>();
    DisplayMcqResultAdapter mcqAdapter;
    DisplayFillInBlanksResultAdapter displayFillInBlanksResultAdapter;
    DisplayTrueFalseResultAdapter displayTrueFalseResultAdapter;
    DisplayMColResultAdapter displayMColResultAdapter;
    Activity activity;
    String ChapterID;
    int a_counter = 0;
    int b_counter = 0;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_test_result);
        context = DisplayTestResultActivity.this;
        activity = (Activity) context;
        rvListMcq = findViewById(R.id.rvListMcq);
        rvListTf = findViewById(R.id.rvListTf);
        rvListFillInBlanks = findViewById(R.id.rvListFillInBlanks);
        rvListMCol = findViewById(R.id.rvListMCol);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        ChapterID = getIntent().getStringExtra("ch_id");
        Log.d("ChapterID111", "" + ChapterID);
        //error
        /*for (String str : getUserMcolArrayList("user_response_mcol")) {
            Log.d("getUserMcolArrayList", str);
        }*/
        //getUserMcolArrayList
        getMcqData();
        ViewCompat.setNestedScrollingEnabled(rvListMcq, false);
        ViewCompat.setNestedScrollingEnabled(rvListTf, false);
        ViewCompat.setNestedScrollingEnabled(rvListFillInBlanks, false);
        ViewCompat.setNestedScrollingEnabled(rvListMCol, false);
    }

    private void lockNestedScrolling() {
        rvListMcq.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                rvListMcq.dispatchNestedFling(velocityX, velocityY, false);
                return false;
            }
        });
        rvListTf.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                rvListTf.dispatchNestedFling(velocityX, velocityY, false);
                return false;
            }
        });
        rvListFillInBlanks.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                rvListFillInBlanks.dispatchNestedFling(velocityX, velocityY, false);
                return false;
            }
        });
        rvListMCol.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                rvListMCol.dispatchNestedFling(velocityX, velocityY, false);
                return false;
            }
        });
    }

    public ArrayList<String> getMcqArrayList(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public ArrayList<String> getTFArrayList(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public ArrayList<String> getFillArrayList(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public ArrayList<String> getMcolArrayList(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public ArrayList<String> getUserMcolArrayList(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    private void getMcqData() {
        String url5 = Apis.base_url + Apis.random_question_url;
        StringRequest stringRequest5 = new StringRequest(Request.Method.POST, url5,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d("MCResponse", "" + response);
                        JSONObject jsonObject1 = null;
                        try {
                            jsonObject1 = new JSONObject(response);
                            JSONArray mcolArray = jsonObject1.getJSONArray("match_column");
                            JSONArray true_false = jsonObject1.getJSONArray("true_false");
                            JSONArray MCQArray = jsonObject1.getJSONArray("mcq");
                            JSONArray fill_in_blank = jsonObject1.getJSONArray("fill_in_blank");

                            Log.d("FillArrayMcol", "" + String.valueOf(mcolArray));
                            Log.d("MCQQQ", "" + String.valueOf(MCQArray.length()));

                            if (MCQArray.length() > 0) {
                                for (int i = 0; i < MCQArray.length(); i++) {
                                    JSONObject c1 = MCQArray.getJSONObject(i);
                                    /*String ques_no = c.getString("ques_no");
                                    String ques = c.getString("ques");
                                    String option_a = c.getString("a");
                                    String option_b = c.getString("b");
                                    String option_c = c.getString("c");
                                    String option_d = c.getString("d");
                                    String Answer = c.getString("ans");*/

                                    HashMap<String, String> contact = new HashMap<>();
                                    contact.put("ques_no", c1.getString("ques_no"));
                                    contact.put("ques", c1.getString("ques"));
                                    contact.put("count", c1.getString("count"));
                                    contact.put("option_a", c1.getString("a"));
                                    contact.put("option_b", c1.getString("b"));
                                    contact.put("option_c", c1.getString("c"));
                                    contact.put("option_d", c1.getString("d"));
                                    contact.put("Answer", c1.getString("ans"));
                                    contact.put("each_mcq_mark", String.valueOf(getIntent().getIntExtra("each_mcq_mark", 0)));
                                    contact.put("MarkedANS", getMcqArrayList("mcq_report").get(i));
                                    contact.put("sno", c1.getString("sno"));
                                    Log.d("mcqData", "" + String.valueOf(contact));
                                    MCQLIST.add(contact);

                                }
                                Log.d("MCQLIOST111", "" + String.valueOf(MCQLIST));
                                rvListMcq.setLayoutManager(new LinearLayoutManager(context));
                                mcqAdapter = new DisplayMcqResultAdapter(context, MCQLIST);
                                rvListMcq.setAdapter(mcqAdapter);
                            }
                            if (fill_in_blank.length() > 0) {
                                for (int i = 0; i < fill_in_blank.length(); i++) {
                                    /*"count": "9",
                                    "sno": "1",
                                    "ques_no": "4",
                                    "ques": "In sleep mode, computer consumes ......",
                                    "ans": "less power"*/

                                    JSONObject c = fill_in_blank.getJSONObject(i);
                                    String ques_no = c.getString("ques_no");
                                    String ques = c.getString("ques");
                                    String Answer = c.getString("ans");

                                    HashMap<String, String> contact4 = new HashMap<>();
                                    contact4 = new HashMap<>();
                                    contact4.put("ques_no", ques_no);
                                    contact4.put("ques", ques);
                                    contact4.put("sno", c.getString("sno"));
                                    contact4.put("count", c.getString("count"));
                                    contact4.put("Answer", Answer);
                                    contact4.put("each_fill_mark", String.valueOf(getIntent().getIntExtra("each_fill_mark", 0)));
                                    contact4.put("MarkedANS", getFillArrayList("fill_report").get(i));
                                    Log.d("fill_report_here", "" + String.valueOf(contact4));
                                    fillinTheblanklistt.add(contact4);
                                }

                                Log.d("fillinTheblanklistLog", "" + String.valueOf(fillinTheblanklistt));
                                rvListFillInBlanks.setLayoutManager(new LinearLayoutManager(context));
                                displayFillInBlanksResultAdapter = new DisplayFillInBlanksResultAdapter(context, fillinTheblanklistt);
                                rvListFillInBlanks.setAdapter(displayFillInBlanksResultAdapter);
                            }

                            if (true_false.length() > 0) {
                                for (int j = 0; j < true_false.length(); j++) {
                                    JSONObject c = true_false.getJSONObject(j);
                                    Log.d("126644565", c.getString("ques") + "  Size " + true_false.length());

                                    HashMap<String, String> TrueFalsemap = new HashMap<>();
                                    TrueFalsemap.put("ques_no", c.getString("ques_no"));
                                    TrueFalsemap.put("ques", c.getString("ques"));
                                    TrueFalsemap.put("ans", c.getString("ans"));
                                    TrueFalsemap.put("count", c.getString("count"));
                                    TrueFalsemap.put("sno", c.getString("sno"));
                                    TrueFalsemap.put("each_tf_mark", String.valueOf(getIntent().getIntExtra("each_tf_mark", 0)));
                                    TrueFalsemap.put("Marks", "1");
                                    TrueFalsemap.put("MarkedANS", getTFArrayList("trueFalse_report").get(j));
                                    truefalseList.add(TrueFalsemap);
                                }

                                Log.d("TrueFalseArray11", "" + String.valueOf(truefalseList));
                                rvListTf.setLayoutManager(new LinearLayoutManager(context));
                                displayTrueFalseResultAdapter = new DisplayTrueFalseResultAdapter(context, truefalseList);
                                rvListTf.setAdapter(displayTrueFalseResultAdapter);
                            }


                            if (mcolArray.length() > 0) {
                                Log.d("MColMap999", "" + getMcolArrayList("response_mcol").toString());
                                for (int i = 0; i < mcolArray.length(); i++) {
                                    JSONObject c = mcolArray.getJSONObject(i);

                                    HashMap<String, String> MColMap = new HashMap<>();
                                    MColMap.put("count", c.getString("count"));
                                    //MColMap.put("mc_id", c.getString("mc_id"));
                                    //MColMap.put("ch_name", c.getString("ch_name"));

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
                                    //MColMap.put("ch_id", c.getString("ch_id"));

                                    MColMap.put("ans1", c.getString("ans1"));
                                    MColMap.put("ans2", c.getString("ans2"));
                                    MColMap.put("ans3", c.getString("ans3"));
                                    MColMap.put("ans4", c.getString("ans4"));
                                    MColMap.put("ans5", c.getString("ans5"));
                                    MColMap.put("ans6", c.getString("ans6"));
                                    MColMap.put("ans7", c.getString("ans7"));
                                    MColMap.put("ans8", c.getString("ans8"));


                                    MColMap.put("each_mcol_mark", String.valueOf(getIntent().getIntExtra("each_mcol_mark", 0)));
                                    int count = 0;
                                    for (String s : getMcolArrayList("response_mcol").get(0).split(",")) {
                                        if (!s.isEmpty()) {
                                            count++;
                                        }
                                    }
                                    Log.d("coutns", String.valueOf(count));
                                    Log.d("coutns", String.valueOf(i));

                                    MColMap.put("answer1", getMcolArrayList("response_mcol").size() > 0 && !getMcolArrayList("response_mcol").get(0).split(",")[a_counter].equals("N") ? getMcolArrayList("response_mcol").get(0).split(",")[a_counter] : getMcolArrayList("response_mcol").get(0).split(",")[a_counter] + "");
                                    MColMap.put("answer2", getMcolArrayList("response_mcol").size() > 0 && !getMcolArrayList("response_mcol").get(0).split(",")[a_counter++].equals("N") ? getMcolArrayList("response_mcol").get(0).split(",")[a_counter] : getMcolArrayList("response_mcol").get(0).split(",")[a_counter] + "");
                                    MColMap.put("answer3", getMcolArrayList("response_mcol").size() > 0 && !getMcolArrayList("response_mcol").get(0).split(",")[a_counter++].equals("N") ? getMcolArrayList("response_mcol").get(0).split(",")[a_counter] : getMcolArrayList("response_mcol").get(0).split(",")[a_counter] + "");
                                    MColMap.put("answer4", getMcolArrayList("response_mcol").size() > 0 && !getMcolArrayList("response_mcol").get(0).split(",")[a_counter++].equals("N") ? getMcolArrayList("response_mcol").get(0).split(",")[a_counter] : getMcolArrayList("response_mcol").get(0).split(",")[a_counter] + "");
                                    MColMap.put("answer5", getMcolArrayList("response_mcol").size() > 0 && !getMcolArrayList("response_mcol").get(0).split(",")[a_counter++].equals("N") ? getMcolArrayList("response_mcol").get(0).split(",")[a_counter] : getMcolArrayList("response_mcol").get(0).split(",")[a_counter] + "");
                                    MColMap.put("answer6", getMcolArrayList("response_mcol").size() > 0 && !getMcolArrayList("response_mcol").get(0).split(",")[a_counter++].equals("N") ? getMcolArrayList("response_mcol").get(0).split(",")[a_counter] : getMcolArrayList("response_mcol").get(0).split(",")[a_counter] + "");
                                    MColMap.put("answer7", getMcolArrayList("response_mcol").size() > 0 && !getMcolArrayList("response_mcol").get(0).split(",")[a_counter++].equals("N") ? getMcolArrayList("response_mcol").get(0).split(",")[a_counter] : getMcolArrayList("response_mcol").get(0).split(",")[a_counter] + "");
                                    MColMap.put("answer8", getMcolArrayList("response_mcol").size() > 0 && !getMcolArrayList("response_mcol").get(0).split(",")[a_counter++].equals("N") ? getMcolArrayList("response_mcol").get(0).split(",")[a_counter] : getMcolArrayList("response_mcol").get(0).split(",")[a_counter] + "");

                                    MColMap.put("user_answer1", getUserMcolArrayList("user_response_mcol").size() > 0 && !getUserMcolArrayList("user_response_mcol").get(0).split(",")[b_counter].equals("N") ? getMcolArrayList("user_response_mcol").get(0).split(",")[b_counter] : getUserMcolArrayList("user_response_mcol").get(0).split(",")[b_counter] + "");
                                    MColMap.put("user_answer2", getUserMcolArrayList("user_response_mcol").size() > 0 && !getUserMcolArrayList("user_response_mcol").get(0).split(",")[b_counter++].equals("N") ? getMcolArrayList("user_response_mcol").get(0).split(",")[b_counter] : getUserMcolArrayList("user_response_mcol").get(0).split(",")[b_counter] + "");
                                    MColMap.put("user_answer3", getUserMcolArrayList("user_response_mcol").size() > 0 && !getUserMcolArrayList("user_response_mcol").get(0).split(",")[b_counter++].equals("N") ? getMcolArrayList("user_response_mcol").get(0).split(",")[b_counter] : getUserMcolArrayList("user_response_mcol").get(0).split(",")[b_counter] + "");
                                    MColMap.put("user_answer4", getUserMcolArrayList("user_response_mcol").size() > 0 && !getUserMcolArrayList("user_response_mcol").get(0).split(",")[b_counter++].equals("N") ? getMcolArrayList("user_response_mcol").get(0).split(",")[b_counter] : getUserMcolArrayList("user_response_mcol").get(0).split(",")[b_counter] + "");
                                    MColMap.put("user_answer5", getUserMcolArrayList("user_response_mcol").size() > 0 && !getUserMcolArrayList("user_response_mcol").get(0).split(",")[b_counter++].equals("N") ? getMcolArrayList("user_response_mcol").get(0).split(",")[b_counter] : getUserMcolArrayList("user_response_mcol").get(0).split(",")[b_counter] + "");
                                    MColMap.put("user_answer6", getUserMcolArrayList("user_response_mcol").size() > 0 && !getUserMcolArrayList("user_response_mcol").get(0).split(",")[b_counter++].equals("N") ? getMcolArrayList("user_response_mcol").get(0).split(",")[b_counter] : getUserMcolArrayList("user_response_mcol").get(0).split(",")[b_counter] + "");
                                    MColMap.put("user_answer7", getUserMcolArrayList("user_response_mcol").size() > 0 && !getUserMcolArrayList("user_response_mcol").get(0).split(",")[b_counter++].equals("N") ? getMcolArrayList("user_response_mcol").get(0).split(",")[b_counter] : getUserMcolArrayList("user_response_mcol").get(0).split(",")[b_counter] + "");
                                    MColMap.put("user_answer8", getUserMcolArrayList("user_response_mcol").size() > 0 && !getUserMcolArrayList("user_response_mcol").get(0).split(",")[b_counter++].equals("N") ? getMcolArrayList("user_response_mcol").get(0).split(",")[b_counter] : getUserMcolArrayList("user_response_mcol").get(0).split(",")[b_counter] + "");
                                    a_counter++;
                                    b_counter++;
                                    Log.d("MColMap", "" + getMcolArrayList("response_mcol").toString());
                                    Log.d("getUserMcolArrayList", "" + getUserMcolArrayList("user_response_mcol").toString());
                                    MColMap.put("isSelected", "0");
                                    MColMap.put("MarkedANS", "");
                                    MColMap.put("Marks", "1");
                                    Log.d("MColMap", "" + String.valueOf(MColMap));
                                    mcolList.add(MColMap);
                                }
                                Log.d("Matchmakingselected", "" + mcolList.toString());
                                rvListMCol.setLayoutManager(new LinearLayoutManager(context));
                                displayMColResultAdapter = new DisplayMColResultAdapter(context, mcolList);
                                rvListMCol.setAdapter(displayMColResultAdapter);
                                lockNestedScrolling();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("str_error", e.getMessage());
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // progressDialog.dismiss();
                System.out.println("ResponseRegistration" + error.getMessage());
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                java.util.Map<String, String> params = new HashMap<>();
                params.put("ch_id", ChapterID);//Chapters_ID);
                return params;
            }
        };
        CommonMethods.callWebserviceForResponse(stringRequest5, context);
    }
}
