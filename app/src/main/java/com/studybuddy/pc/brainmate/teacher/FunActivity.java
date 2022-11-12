package com.studybuddy.pc.brainmate.teacher;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.adapters.FunAdapter;
import com.studybuddy.pc.brainmate.mains.Apis;
import com.studybuddy.pc.brainmate.model.fun_data_list.Data;
import com.studybuddy.pc.brainmate.model.fun_data_list.FunDataList;
import com.studybuddy.pc.brainmate.model.fun_data_list.FunForList;
import com.studybuddy.pc.brainmate.student.CommonMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class FunActivity extends AppCompatActivity {

    private ArrayList<HashMap<String, String>> funList = new ArrayList<>();
    private ArrayList<FunForList> funForLists = new ArrayList<>();
    private Context context;
    RecyclerView recyclerViewFun;
    Toolbar toolbarHeader;
    ProgressDialog progressDialog;
    String displayClassName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun);
        context = FunActivity.this;
        recyclerViewFun = findViewById(R.id.recyclerViewFun);
        toolbarHeader = findViewById(R.id.toolbarHeader);
        displayClassName = getIntent().getStringExtra("displayClassName") != null ? getIntent().getStringExtra("displayClassName") : "";
        setSupportActionBar(toolbarHeader);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbarHeader.setTitleTextColor(getResources().getColor(R.color.white));
            //toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow);
            //getSupportActionBar().setTitle("Techive");
        }
        setTitle("Fun Activity");
        //getJsonData();
        getFunData();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void getFunData() {
        //final Dialog dialog = new Dialog(context);
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        String url1 = Apis.base_url + Apis.fun_activity;
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Painti5644ng", "" + response);
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equals("1")) {
                                FunDataList dataList = new Gson().fromJson(response, FunDataList.class);
                                int p = 0;
                                for (Data list : dataList.getData()) {
                                    FunForList forList = new FunForList();
                                    forList.setId(list.getId());
                                    forList.setPublisher_id(list.getPublisher_id());
                                    forList.setClass1(list.getClass1());
                                    forList.setSubject(list.getSubject());
                                    forList.setSeries(list.getSeries());
                                    forList.setTitle(list.getTitle());
                                    forList.setType(list.getType());
                                    forList.setUrl(list.getUrl());
                                    forList.setDate(list.getDate());
                                    //forList.setIsFree(p % 2 == 0 ? "0" : "1");
                                    forList.setIsFree("1");
                                    funForLists.add(forList);
                                    p++;
                                }
                                if (funForLists.size() > 0) {
                                    Collections.sort(funForLists, FunForList.getByType);
                                }
                                for (int i = 0; i < funForLists.size(); i++) {
                                    Log.d("funForLists", funForLists.get(i).getType());
                                }
                                /*JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    *//*HashMap<String, String> list = new HashMap<>();
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    list.put("id", object.getString("id"));
                                    list.put("publisher_id", object.getString("publisher_id"));
                                    list.put("class", object.getString("class"));
                                    list.put("subject", object.getString("subject"));
                                    list.put("series", object.getString("series"));
                                    list.put("title", object.getString("title"));
                                    list.put("type", object.getString("type"));
                                    list.put("url", object.getString("url"));
                                    list.put("date", object.getString("date"));
                                    funList.add(list);*//*
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    FunForList forList = new FunForList();
                                    forList.setId(object.getString("id"));
                                    forList.setPublisher_id(object.getString("publisher_id"));
                                    forList.setClass1(object.getString("class"));
                                    forList.setSubject(object.getString("subject"));
                                    forList.setSeries(object.getString("series"));
                                    forList.setTitle(object.getString("title"));
                                    forList.setType(object.getString("type"));
                                    forList.setUrl(object.getString("url"));
                                    forList.setDate(object.getString("date"));
                                    funForLists.add(forList);
                                }*/

                                recyclerViewFun.setLayoutManager(new LinearLayoutManager(context));
                                FunAdapter funAdapter = new FunAdapter(funForLists, context);
                                recyclerViewFun.setAdapter(funAdapter);
                            } else if (jsonObject.getString("success").equals("0")) {
                                Toast.makeText(context, "No Data Found.", Toast.LENGTH_SHORT).show();
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
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                java.util.Map<String, String> params = new HashMap<>();
                //TODO make changes
                params.put("class", displayClassName);
                Log.d("className", "" + params.toString());
                return params;
            }
        };
        CommonMethods.callWebserviceForResponse(stringRequest1,context);
    }

    private void getJsonData() {
        String result = CommonMethods.getJson(context, "funlist");
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                HashMap<String, String> list = new HashMap<>();
                JSONObject object = jsonArray.getJSONObject(i);
                list.put("id", object.getString("id"));
                list.put("publisher_id", object.getString("publisher_id"));
                list.put("class", object.getString("class"));
                list.put("subject", object.getString("subject"));
                list.put("series", object.getString("series"));
                list.put("title", object.getString("title"));
                list.put("type", object.getString("type"));
                list.put("url", object.getString("url"));
                list.put("date", object.getString("date"));
                funList.add(list);
            }
            recyclerViewFun.setLayoutManager(new LinearLayoutManager(context));
            FunAdapter funAdapter = new FunAdapter(funForLists, context);
            recyclerViewFun.setAdapter(funAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("result", result);
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
