package com.studybuddy.pc.brainmate.student;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.mains.Apis;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Student_Profile extends AppCompatActivity {

    TextView STName, STEmail, Accessecode, STContact, STSchoolAddress;
    ProgressDialog progressDialog;
    String Student_ID;
    Toolbar toolbarHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__profile);
        STName = (TextView) findViewById(R.id.STName);
        STEmail = (TextView) findViewById(R.id.STEmail);
        Accessecode = (TextView) findViewById(R.id.Accessecode);
        STContact = (TextView) findViewById(R.id.STContact);
        STSchoolAddress = (TextView) findViewById(R.id.STSchoolAddress);
        Student_ID = getIntent().getStringExtra("Student_ID");
        toolbarHeader = findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbarHeader);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbarHeader.setTitleTextColor(getResources().getColor(R.color.white));
            //toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow);
            //getSupportActionBar().setTitle("Techive");
        }
        setTitle("Profile");

        progressDialog = new ProgressDialog(Student_Profile.this);
        progressDialog.setMessage("Loading..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
        //RequestQueue queue = Volley.newRequestQueue(Student_Profile.this);
        //String url = "http://www.techive.in/studybuddy/api/student_profile.php";
        String url = Apis.base_url + Apis.student_profile_url;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ViewPatient", response);
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            String LoginCredential = jsonObject1.getString("success");
                            JSONArray StudentProfile = jsonObject1.getJSONArray("data");
                            for (int j = 0; j < StudentProfile.length(); j++) {
                                JSONObject c = StudentProfile.getJSONObject(j);
                                Log.d("School_name", c.getString("name"));
                                STName.setText(c.getString("name"));
                                STEmail.setText(c.getString("email"));
                                Accessecode.setText(c.getString("accesscode"));
                                STContact.setText(c.getString("ph_no"));
                                STSchoolAddress.setText(c.getString("school"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    Toast.makeText(Student_Profile.this, "Internet not Connected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Student_Profile.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                }
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }
        }) {

            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                java.util.Map<String, String> params = new HashMap<>();
                params.put("student_id", Student_ID);

                return params;
            }
        };
        CommonMethods.callWebserviceForResponse(stringRequest, Student_Profile.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_go_home:
                Intent intent = new Intent(Student_Profile.this, Stu_Classes.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("accesscodes", CommonMethods.getAccessCode(Student_Profile.this));
                intent.putExtra("Student_ID", CommonMethods.getId(Student_Profile.this));
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
