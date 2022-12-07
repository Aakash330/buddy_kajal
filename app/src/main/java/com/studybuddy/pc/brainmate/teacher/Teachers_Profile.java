package com.studybuddy.pc.brainmate.teacher;

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
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.studybuddy.pc.brainmate.CertificateClass0S6;
import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.mains.Apis;
import com.studybuddy.pc.brainmate.student.CommonMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Teachers_Profile extends AppCompatActivity {

    TextView TeacheName, TeacherEmail, TeacherAccesses, TeacherNumber, TeacherAddress, TeacherSchoolname, TeacherShclAddress, Teacherschlnumber;
    ProgressDialog progressDialog;
    String Teachers_ID;
    Toolbar toolbarHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers__profile);
        setTitle("My Profile");
        Teachers_ID = getIntent().getStringExtra("Teachers_ID");
        TeacheName = (TextView) findViewById(R.id.TeacheName);
        TeacherEmail = (TextView) findViewById(R.id.TeacherEmail);
        TeacherAccesses = (TextView) findViewById(R.id.TeacherAccesses);
        TeacherNumber = (TextView) findViewById(R.id.TeacherNumber);
        TeacherAddress = (TextView) findViewById(R.id.TeacherAddress);
        TeacherSchoolname = (TextView) findViewById(R.id.TeacherSchoolname);
        TeacherShclAddress = (TextView) findViewById(R.id.TeacherShclAddress);
        Teacherschlnumber = (TextView) findViewById(R.id.Teacherschlnumber);

        toolbarHeader = findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbarHeader);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbarHeader.setTitleTextColor(getResources().getColor(R.color.white));
            //toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow);
            //getSupportActionBar().setTitle("Techive");
        }

        progressDialog = new ProgressDialog(Teachers_Profile.this);
        progressDialog.setMessage("Loading..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
       // RequestQueue queue = Volley.newRequestQueue(Teachers_Profile.this);
        RequestQueue queue =Volley.newRequestQueue(Teachers_Profile.this, new HurlStack(null, CertificateClass0S6.getSslSocketFactory(Teachers_Profile.this)));
        //String url = "http://www.techive.in/studybuddy/api/teacher_profile.php";
        String url = Apis.base_url + Apis.teacher_profile_url;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ViewPatient", response);
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            JSONArray teachersArray = jsonObject1.getJSONArray("data");
                            Log.d("teachersArray", String.valueOf(teachersArray));
                            for (int j = 0; j < teachersArray.length(); j++) {
                                JSONObject c = teachersArray.getJSONObject(j);
                                Log.d("teachersArrayname", c.getString("name"));
                                TeacherAccesses.setText(c.getString("accesscodes"));

                                TeacheName.setText(c.getString("name"));
                                TeacherEmail.setText(c.getString("email"));
                                TeacherNumber.setText(c.getString("ph_no"));
                                TeacherAddress.setText(c.getString("address"));
                                TeacherSchoolname.setText(c.getString("school"));
                                TeacherShclAddress.setText(c.getString("schl_addr"));
                                Teacherschlnumber.setText(c.getString("schl_ph_no"));
                            }
                            for (int i = 0; i < teachersArray.length(); i++) {
                                JSONObject c = teachersArray.getJSONObject(i);
                                String id = c.getString("id");
                                //  Log.d("teachersArrayname", c.getString("name"));
                                String name = c.getString("name");
                                String email = c.getString("email");
                                String P_hone = c.getString("ph_no");
                                String addres = c.getString("address");
                                String School_name = c.getString("school");
                                String School_Address = c.getString("schl_addr");
                                String School_Number = c.getString("schl_ph_no");
                                // adding contact to contact list
                            }
                            // Log.d("Sssssssslist", String.valueOf(marksList));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    Toast.makeText(Teachers_Profile.this, "Internet not Connected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Teachers_Profile.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                }
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }
        }) {

            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                java.util.Map<String, String> params = new HashMap<>();
                params.put("teacher_id", Teachers_ID);

                return params;
            }
        };
        //queue.add(stringRequest);
        CommonMethods.callWebserviceForResponse(stringRequest,Teachers_Profile.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_go_home:
                Intent intent = new Intent(Teachers_Profile.this, Main2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("accesscodes", CommonMethods.getAccessCode(Teachers_Profile.this));
                intent.putExtra("Teachers_ID", CommonMethods.getId(Teachers_Profile.this));
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
