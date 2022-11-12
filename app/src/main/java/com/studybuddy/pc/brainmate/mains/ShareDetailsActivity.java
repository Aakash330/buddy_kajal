package com.studybuddy.pc.brainmate.mains;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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
import com.studybuddy.pc.brainmate.student.CommonMethods;
import com.studybuddy.pc.brainmate.teacher.Main2Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.studybuddy.pc.brainmate.mains.Registration.isValidEmail;
import static com.studybuddy.pc.brainmate.mains.Registration.isValidMobile;

public class ShareDetailsActivity extends AppCompatActivity {

    EditText etRegTeacherName, etRegAccessCode, etRegEmail, etRegSchoolName, etRegSchoolAddress, etRegPhoneNo;
    Button btnRegSendDetails;
    Context context;
    ProgressDialog progressDialog;
    Toolbar toolbarHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_details);
        context = ShareDetailsActivity.this;
        etRegTeacherName = findViewById(R.id.etRegTeacherName);
        etRegAccessCode = findViewById(R.id.etRegAccessCode);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegSchoolName = findViewById(R.id.etRegSchoolName);
        etRegSchoolAddress = findViewById(R.id.etRegSchoolAddress);
        etRegPhoneNo = findViewById(R.id.etRegPhoneNo);
        toolbarHeader = findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbarHeader);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbarHeader.setTitleTextColor(getResources().getColor(R.color.white));
            //toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow);
            //getSupportActionBar().setTitle("Techive");
        }
        setTitle(getString(R.string.app_name));
        btnRegSendDetails = findViewById(R.id.btnRegSendDetails);
        btnRegSendDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etRegTeacherName.getText().toString().isEmpty()) {
                    etRegTeacherName.setError("Enter Teacher Name");
                } else if (etRegAccessCode.getText().toString().isEmpty()) {
                    etRegAccessCode.setError("Enter Access Code");
                } else if (etRegEmail.getText().toString().isEmpty()) {
                    etRegEmail.setError("Enter Email");
                } else if (etRegSchoolName.getText().toString().isEmpty()) {
                    etRegSchoolName.setError("Enter School Name");
                } else if (etRegSchoolAddress.getText().toString().isEmpty()) {
                    etRegSchoolAddress.setError("Enter School Address");
                } else if (etRegPhoneNo.getText().toString().isEmpty()) {
                    etRegPhoneNo.setError("Enter Mobile Number");
                } else if (!isValidEmail(etRegEmail.getText().toString())) {
                    etRegEmail.setError("Enter Valid Email");
                } else if (!isValidMobile(etRegPhoneNo.getText().toString())) {
                    etRegPhoneNo.setError("Enter Valid Mobile Number");
                } else {
                    progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage("Loading..."); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);
                    reqToSubmit(Apis.base_url + Apis.teacher_new_register_mail);
                }
            }
        });
    }

    //region "Submit Request"
    private void reqToSubmit(String feedUrl) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, feedUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("validateRegResponse", response);
                        //CommonMethods.showToast(context, "" + response);
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            //String success = jsonObject.getString("success");
                            String mail = jsonObject.getString("mail_success");
                            if (mail != null && mail.equals("1")) {
                                Toast.makeText(context, "Mail sent successfully.", Toast.LENGTH_LONG).show();
                            } else if (mail != null && mail.equals("0")) {
                                Toast.makeText(context, "Unable to send mail. Try Again Later", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "Unable to send mail. Try Again Later", Toast.LENGTH_LONG).show();
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
                params.put("teacher_name", etRegTeacherName.getText().toString());
                params.put("email", etRegEmail.getText().toString());
                params.put("number", etRegPhoneNo.getText().toString());
                params.put("accesscode", etRegAccessCode.getText().toString());
                params.put("school", etRegSchoolName.getText().toString());
                params.put("address", etRegSchoolAddress.getText().toString());
                //Log.d("getRegParamsData", params.toString());
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
            /*case R.id.action_go_home:
                Intent intent = new Intent(context, Main2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("accesscodes", CommonMethods.getAccessCode(context));
                intent.putExtra("Teachers_ID", CommonMethods.getId(context));
                startActivity(intent);
                return true;*/
        }
        return super.onOptionsItemSelected(item);
    }

    //region "Menu"
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.stu__classes, menu);
        return true;
    }
}
