package com.studybuddy.pc.brainmate.student;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.studybuddy.pc.brainmate.Network_connection.utils.NetworkUtil;
import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.mains.Apis;
import com.studybuddy.pc.brainmate.teacher.E_book;
import com.studybuddy.pc.brainmate.teacher.LearningElementary;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StudebtElimentery extends AppCompatActivity {

    Button TakeTest, E_Book, AnimationVideo, Reference_Material;
    String access_code, ebook, Subject,Title,ClassName;
    Toolbar toolbarHeader;
    ProgressDialog progressDialog;
    String Network_Status,sno;
    LinearLayout LtyE_book,LtyActivity,LtyReferenceMaterial,noBookAvailable,BookAvailable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_studebt_factivty);
        access_code = getIntent().getStringExtra("access_code");
        ebook = getIntent().getStringExtra("ebook");
        Subject = getIntent().getStringExtra("Subject");
        ClassName = getIntent().getStringExtra("ClassName");
        sno = getIntent().getStringExtra("sno");
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
        Reference_Material = (Button) findViewById(R.id.Reference_Material);
        LtyE_book = (LinearLayout) findViewById(R.id.e_bookLty);
        LtyActivity = (LinearLayout) findViewById(R.id.activityO);
        LtyReferenceMaterial = (LinearLayout) findViewById(R.id.reference_material);
        noBookAvailable = (LinearLayout) findViewById(R.id.nobookLty);
        BookAvailable = (LinearLayout) findViewById(R.id.bookLty);
        //E_Book.setText(Title != null ? Title : "");

        if (NetworkUtil.getConnectivityStatus(StudebtElimentery.this) > 0) {
            System.out.println("Connect");
            Network_Status = "Connect";

            progressDialog = new ProgressDialog(StudebtElimentery.this);
            progressDialog.setMessage("Loading..."); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);
            String url = Apis.base_url1+Apis.checkStatus_url;//webView ebook link come from this api
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("checkStatus", response);
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject1 = null;
                                jsonObject1 = new JSONObject(response);
                                Log.w("check","ebook_status"+jsonObject1.getString("ebook_status"));
                                Log.w("check","rehere"+jsonObject1.getString("reference_material_status"));
                               String rf= jsonObject1.getString("reference_material_status");

                                if(jsonObject1.getString("ebook_status").equals("1")){
                                    LtyE_book.setVisibility(View.VISIBLE);
                                }
                                if(rf.equals("1"))
                                {

                                    LtyReferenceMaterial.setVisibility(View.VISIBLE);
                                }
                                if(jsonObject1.getString("ebook_status").equals("0")&&
                                        jsonObject1.getString("test_generator_status").equals("0")&&
                                        jsonObject1.getString("test_manual_status").equals("0")&&
                                        jsonObject1.getString("reference_material_status").equals("0")){

                                    noBookAvailable.setVisibility(View.VISIBLE);
                                    BookAvailable.setVisibility(View.GONE);
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
                    params.put("sno",sno);
                    return params;
                }
            };
            // Add the request to the RequestQueue.
            CommonMethods.callWebserviceForResponse(stringRequest,StudebtElimentery.this);
        } else {
            System.out.println("No connection");
            Network_Status = "notConnect";
            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(StudebtElimentery.this);
            alertDialogBuilder.setMessage(getString(R.string.no_internet));
            alertDialogBuilder.setPositiveButton("Exit",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                        }
                    });
            android.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

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
              /*  Intent intent = new Intent(StudebtElimentery.this, StudentEbook.class);
                intent.putExtra("ebook", ebook);
                intent.putExtra("Title", Title != null ? Title : "E-Book");
                startActivity(intent);
*/
                getWebViewUrl();

            }
        });

        AnimationVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudebtElimentery.this, StudentAnimition.class);
                startActivity(intent);
            }
        });

        Reference_Material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudebtElimentery.this, ReferenceMaterial.class);
                intent.putExtra("ClassName",ClassName);
                intent.putExtra("Subject",Subject);
                intent.putExtra("Title",Title);
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
    private void getWebViewUrl() {

        if (NetworkUtil.getConnectivityStatus(StudebtElimentery.this) > 0) {
            System.out.println("Connect");
            Network_Status = "Connect";

            Log.d("notice123", "AAAAAAA");
            progressDialog = new ProgressDialog(StudebtElimentery.this);
            progressDialog.setMessage("Loading..."); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);
            String url = Apis.base_url2+Apis.read_url;//webView ebook link come from this api
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("notice12345", response);
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject1 = null;
                                jsonObject1 = new JSONObject(response);
                                Log.d("object111","code:"+jsonObject1.getString("status"));
                                Log.d("object111"," Api StudentElimentery url:"+ jsonObject1.getString("data"));
                                Log.d("object111", access_code);
                                Log.w("kk","ebookUrl E_book:"+jsonObject1.getString("data"));
                                String heroArray = jsonObject1.getString("data");
                                Intent intent = new Intent(StudebtElimentery.this, StudentEbook.class);
                                intent.putExtra("ebook", heroArray);
                                intent.putExtra("Title", Title != null ? Title : "E-Book");
                                startActivity(intent);

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
                    params.put("user_type","student");
                    params.put("email",CommonMethods.getEmailId(StudebtElimentery.this));
                    params.put("accesscode",access_code);


                    return params;
                }
            };
            // Add the request to the RequestQueue.
            CommonMethods.callWebserviceForResponse(stringRequest,StudebtElimentery.this);
        } else {
            System.out.println("No connection");
            Network_Status = "notConnect";
            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(StudebtElimentery.this);
            alertDialogBuilder.setMessage(getString(R.string.no_internet));
            alertDialogBuilder.setPositiveButton("Exit",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                        }
                    });
            android.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
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
