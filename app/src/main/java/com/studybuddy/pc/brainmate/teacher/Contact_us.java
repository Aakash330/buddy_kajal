package com.studybuddy.pc.brainmate.teacher;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.mains.Apis;
import com.studybuddy.pc.brainmate.student.CommonMethods;
import com.studybuddy.pc.brainmate.student.Stu_Classes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Contact_us extends AppCompatActivity {

    EditText emailSubject, emailMessage;//fromEmailID,
    ProgressDialog progressDialog;
    Button SendButton;
    Context context;
    Toolbar toolbarHeader;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        context = Contact_us.this;
        //fromEmailID = (EditText) findViewById(R.id.fromEmailID);
        emailSubject = (EditText) findViewById(R.id.EmailSubject);
        emailMessage = (EditText) findViewById(R.id.EmailMessage);
        SendButton = (Button) findViewById(R.id.SendButton);
        toolbarHeader = findViewById(R.id.toolbarHeader);
        type = getIntent().getStringExtra("getType") != null ? getIntent().getStringExtra("getType") : "";

        setSupportActionBar(toolbarHeader);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbarHeader.setTitleTextColor(getResources().getColor(R.color.white));
            //toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow);
            //getSupportActionBar().setTitle("Techive");
        }
        setTitle("Contact Us");
        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String fromEmail = "deep59613@gmail.com";
                String fromPassword = "DEEPAK1996";
                String toEmails = "deep59613@gmail.com";
                List toEmailList = Arrays.asList(toEmails.split("\\s*,\\s*"));
                Log.i("SendMailActivity", "To List: " + toEmailList);
                String emailSubject = "My studyBuddy :" + emailSubject.getText().toString();
                String emailBody = "User_Email" + fromEmailID + "\n" + emailMessage.getText().toString();
                new SendMailTask(Contact_us.this).execute(fromEmail, fromPassword, toEmailList, emailSubject, emailBody);*/
                if (emailMessage.getText().toString().isEmpty()) {
                    emailMessage.setError("Enter your message.");
                } else if (emailSubject.getText().toString().isEmpty()) {
                    emailSubject.setError("Enter the subject.");
                } else {
                    progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage("Loading..."); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);
                    reqToSubmit(Apis.base_url + Apis.contact_us, CommonMethods.getId(context), CommonMethods.getType(context), emailSubject.getText().toString(), emailMessage.getText().toString());
                }
            }
        });
    }

    //region "Submit Request"
    private void reqToSubmit(String feedUrl, final String id, final String person_type, final String subject, final String message) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, feedUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("validateResponse", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String mail = jsonObject.getString("mail");
                            if (success != null && mail != null && success.equals("1") && mail.equals("1")) {
                                Toast.makeText(context, "Mail sent successfully.", Toast.LENGTH_LONG).show();
                            } else if (success != null && mail != null && success.equals("1") && mail.equals("0")) {
                                Toast.makeText(context, "Unable to send mail.", Toast.LENGTH_LONG).show();
                            } else if (success != null && mail != null && success.equals("0") && mail.equals("0")) {
                                Toast.makeText(context, "User not found.", Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //dialog.dismiss();
                        Log.d("getParamsDatas11", "" + volleyError.getMessage());
                        if (volleyError instanceof NoConnectionError) {
                            Toast.makeText(context, "Internet not Connected", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Some Error Occured", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //region "ch_id"
                params.put("id", "" + id);
                params.put("person_type", "" + person_type);
                params.put("subject", "" + subject);
                params.put("message", "" + message);
                //endregion

                Log.d("getParamsDatas", params.toString());
                return params;
            }
        };
        CommonMethods.callWebserviceForResponse(stringRequest, context);
    }//endregion

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_go_home:
                if (type.equals("t")) {
                    Intent intent = new Intent(Contact_us.this, Main2Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("accesscodes", CommonMethods.getAccessCode(Contact_us.this));
                    intent.putExtra("Teachers_ID", CommonMethods.getId(Contact_us.this));
                    startActivity(intent);
                } else if (type.equals("s")) {
                    Intent intent = new Intent(Contact_us.this, Stu_Classes.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("accesscodes", CommonMethods.getAccessCode(Contact_us.this));
                    intent.putExtra("Student_ID", CommonMethods.getId(Contact_us.this));
                    startActivity(intent);
                }
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
