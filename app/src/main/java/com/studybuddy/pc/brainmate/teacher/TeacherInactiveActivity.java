package com.studybuddy.pc.brainmate.teacher;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.studybuddy.pc.brainmate.Network_connection.utils.NetworkUtil;
import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.mains.Apis;
import com.studybuddy.pc.brainmate.mains.LoginBothActivity;
import com.studybuddy.pc.brainmate.student.CommonMethods;
import com.studybuddy.pc.brainmate.student.Stu_Classes;
import com.studybuddy.pc.brainmate.student.Stu_Subjects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class TeacherInactiveActivity extends AppCompatActivity {

    private Button refresh;
    private  SwipeRefreshLayout swipeRefreshLayout;
    private String msg,loginEmail,loginPassword,regEmail,regPassword,Email,Password;
    private String TAG="TeacherInactiveActivity";
    private SharedPreferences sharedPreferences;
    private static final String strSharedPrefName = "StudyBuddyPreference";
    private TextView thankMsgTv;
    private String Teachers_ID;
    public SharedPreferences getPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_inactive);
        getSupportActionBar().setTitle("Teacher Inactive");

        thankMsgTv=findViewById(R.id.textView4);
        getPreference = getSharedPreferences("Inactive",MODE_PRIVATE);


        try{

            msg=getIntent().getExtras().getString("msg")!= null ? getIntent().getExtras().getString("msg") : CommonMethods.getMsg(this);
            Email=getIntent().getExtras().getString("email")!= null ? getIntent().getExtras().getString("email") : CommonMethods.getEmailId(this);
            Password=getIntent().getExtras().getString("pass")!= null ? getIntent().getExtras().getString("pass") : CommonMethods.getUsername(this);
            Log.w(TAG,"msg="+msg);
            Log.w(TAG,"email="+Email);
            Log.w(TAG,"pass="+Password);
            Log.w(TAG," Sp msg="+ CommonMethods.getMsg(this));



            thankMsgTv.setText(msg);
           /* AlertDialog dialog=  new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_launcher2)
                    .setTitle("Thank you for Registration")
                    .setMessage(msg)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }

                    })
                    .show();*/
        }catch (Exception ee){}



        initView();
        initOnClick();


    }


    private void initView() {
        refresh=findViewById(R.id.refresh);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        sharedPreferences=getSharedPreferences(strSharedPrefName,MODE_PRIVATE);



    }
    private void initOnClick() {
        swipeRefreshLayout.setOnRefreshListener(() -> IsTeacherActive());
        refresh.setOnClickListener(v -> IsTeacherActive());


    }

    private void IsTeacherActive() {
        String network_Status;
        if (NetworkUtil.getConnectivityStatus(TeacherInactiveActivity.this) > 0) {
            System.out.println("Connect");
            network_Status = "Connect";
            if (Email.equals(null) && Password.equals(null)) {
                Toast.makeText(this, "Please Enter email and password first..", Toast.LENGTH_SHORT).show();
            }else if (Email.isEmpty()) {
                Toast.makeText(this, "Please Enter email first..", Toast.LENGTH_SHORT).show();

            } else if (Password.isEmpty()) {
                Toast.makeText(this, "Please Enter  password first..", Toast.LENGTH_SHORT).show();
            } else {

                ProgressDialog progressDialog = new ProgressDialog(TeacherInactiveActivity.this);
                    progressDialog.setMessage("Loading..."); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    String url = Apis.base_url1+Apis.teacher_login_url;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @SuppressLint("ResourceAsColor")
                                @Override
                                public void onResponse(String response) {
                                    Log.d("teacher_login", response);
                                    progressDialog.dismiss();
                                    try {
                                        Log.d("Status123", "3");

                                        JSONObject jsonObject1 = new JSONObject(response);

                                        if (jsonObject1.getString("success").equals("0")) {
                                            Toast.makeText(TeacherInactiveActivity.this, getString(R.string.register_first), Toast.LENGTH_LONG).show();
                                        }
                                        if(jsonObject1.getString("success").equals("2")){
                                            Toast.makeText(TeacherInactiveActivity.this, "Teacher Inactive", Toast.LENGTH_SHORT).show();
                                        }

                                        JSONArray heroArray = jsonObject1.getJSONArray("data");
                                        JSONObject c = heroArray.getJSONObject(0);
                                        String accesscodes = c.getString("accesscodes");
                                        String Active_Status = c.getString("status");
                                        String email = c.getString("email");
                                        String name = c.getString("name");
                                        Teachers_ID = c.getString("t_id");

                                        Toast.makeText(TeacherInactiveActivity.this, "tid"+Teachers_ID, Toast.LENGTH_SHORT).show();

                                        Log.d("teachers_ID", "Teachers " + Teachers_ID + "Acc " + accesscodes + "Status" + Active_Status);

                                        /*  if (Active_Status.equals("1")) *///@kajal 11_23_22
                                        if (jsonObject1.getString("success").equals("1")) {
                                            Log.d("Status123", "1");
                                   /*         SharedPreferences.Editor editor1= pref.edit();
                                            editor1.putInt("IsLogin",2);
                                            editor1.apply();*/

                                            /* Intent intent = new Intent(LoginBothActivity.this, Books_Access_Code.class);*/
                                            Intent intent = new Intent(TeacherInactiveActivity.this, Main2Activity.class);
                                            intent.putExtra("name", "" + name);
                                            intent.putExtra("email", "" + email);
                                            intent.putExtra("Teachers_ID",Teachers_ID);
                                            intent.putExtra("accesscodes", accesscodes);
                                            CommonMethods.saveEmailId(TeacherInactiveActivity.this, email);
                                            CommonMethods.saveId(TeacherInactiveActivity.this, Teachers_ID);
                                            CommonMethods.saveType(TeacherInactiveActivity.this, "t");
                                            CommonMethods.saveUsername(TeacherInactiveActivity.this,Password);
                                            CommonMethods.saveIsLogin(TeacherInactiveActivity.this, 2 );//Teacher==2
                                            CommonMethods.saveAccessCode(TeacherInactiveActivity.this, accesscodes);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);

                                        } else {

                                            final Dialog dialog = new Dialog(TeacherInactiveActivity.this);
                                            dialog.setContentView(R.layout.dialog_layout);
                                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                            dialog.show();
                                            ImageView imgCross = dialog.findViewById(R.id.imgCross);
                                            imgCross.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dialog.dismiss();
                                                    //finish();
                                                }
                                            });

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
                            Log.d("ResponseRegistration", "" + error.getMessage());
                        }
                    }) {

                        @Override
                        protected java.util.Map<String, String> getParams() throws AuthFailureError {
                            java.util.Map<String, String> params = new HashMap<>();
                            //  params.put("username", StEmail.getText().toString());
                            params.put("email",Email);
                            params.put("password",Password);
                            Log.d("params_detail", params.toString());
                            return params;
                        }
                    };
                    CommonMethods.callWebserviceForResponse(stringRequest, TeacherInactiveActivity.this);

            }


        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TeacherInactiveActivity.this);
            alertDialogBuilder.setMessage(getString(R.string.no_internet));
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton(getString(R.string.retry),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int arg1) {
                            if (NetworkUtil.getConnectivityStatus(TeacherInactiveActivity.this) > 0) {
                                Toast.makeText(TeacherInactiveActivity.this, "" + getString(R.string.connected), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                IsTeacherActive();
                            }
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            //System.out.println("No connection");
            network_Status = "notConnect";
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.remove("IsLogin");
                editor.apply();
                startActivity(new Intent(TeacherInactiveActivity.this, LoginBothActivity.class));
                finishAffinity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing StudyBuddy")
                .setMessage("Are you sure you want to exit StudyBuddy?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}