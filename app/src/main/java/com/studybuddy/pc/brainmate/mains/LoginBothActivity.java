package com.studybuddy.pc.brainmate.mains;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.ContactsContract;
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
import com.studybuddy.pc.brainmate.Network_connection.services.NetworkChangeReceiver;
import com.studybuddy.pc.brainmate.Network_connection.utils.NetworkUtil;
import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.student.CommonMethods;
import com.studybuddy.pc.brainmate.student.Stu_Classes;
import com.studybuddy.pc.brainmate.teacher.Main2Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.studybuddy.pc.brainmate.Network_connection.data.Constants.CONNECTIVITY_ACTION;

public class LoginBothActivity extends AppCompatActivity {

    private static final String TAG ="LoginBothActivity" ;
    RadioButton Student, Teacher, TeacherF, StudentF;
    int Status = 0;
    EditText StEmail, stPassword;
    Button loginbuttonStudent;
    ProgressDialog progressDialog;
    TextView register;
    CheckBox keeplogin;
    String Teachers_ID;
    String access_code = "",AccessCode,regEmail,regPass,type;
    IntentFilter intentFilter;
    NetworkChangeReceiver receiver;
    private static TextView log_network;
    private static String log_str;
    Context context;
    public SharedPreferences preferences,getPreference,pref;

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    String Network_Status;
    AlertDialog.Builder builder;
    TextView Forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loingboth);
        context = LoginBothActivity.this;
        builder = new AlertDialog.Builder(this);
        intentFilter = new IntentFilter();
        intentFilter.addAction(CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        Forget = (TextView) findViewById(R.id.Forget);
        Student = (RadioButton) findViewById(R.id.Student);
        Teacher = (RadioButton) findViewById(R.id.Teacher);
        StEmail = (EditText) findViewById(R.id.StEmail);
        stPassword = (EditText) findViewById(R.id.stPassword);
        loginbuttonStudent = (Button) findViewById(R.id.loginbuttonStudent);
        register = (TextView) findViewById(R.id.register);
        keeplogin = (CheckBox) findViewById(R.id.keeplogin);
        pref = context.getSharedPreferences("StudyBuddyPreference", context.MODE_PRIVATE);
        preferences = context.getSharedPreferences("STUDENT_DETAILS",MODE_PRIVATE);
        getPreference= context.getSharedPreferences("TEACHER_DETAILS",MODE_PRIVATE);


        Log.w(TAG,"onCreate LoginBothActivity");
        try {
            type = getIntent().getExtras().getString("Type");
            regEmail = getIntent().getExtras().getString("Email");
            regPass = getIntent().getExtras().getString("Password");

            if (!preferences.getString("TAccessCode", "0").equals(0)) {
                access_code = preferences.getString("TAccessCode", "0");
                Toast.makeText(context, "teacher access code="+access_code, Toast.LENGTH_SHORT).show();
            } else {
                access_code = "0";
                Toast.makeText(context, "teacher access code="+access_code, Toast.LENGTH_SHORT).show();

            }

            if (!preferences.getString("AccessCode", "0").equals(0)) {
                Toast.makeText(context, "student access code="+access_code, Toast.LENGTH_SHORT).show();
                access_code = getPreference.getString("AccessCode", "0");
            } else {
                Toast.makeText(context, "student access code="+access_code, Toast.LENGTH_SHORT).show();
                access_code = "0";
            }

            Log.w(TAG, "onCreate  LoginBothActivity type=" + type);

            if (type.equals("Teacher")) {
                Student.setChecked(false);
                Teacher.setChecked(true);
                StEmail.setText(regEmail);
                stPassword.setText(regPass);
                Toast.makeText(context, "teacher: ac="+access_code+"; email"+StEmail+"; pass"+stPassword, Toast.LENGTH_SHORT).show();

            } else if (type.equals("Student")) {
                Student.setChecked(true);
                Teacher.setChecked(false);
                StEmail.setText(regEmail);
                stPassword.setText(regPass);
                Toast.makeText(context, "student: ac="+access_code+"; email"+StEmail+"; pass"+stPassword, Toast.LENGTH_SHORT).show();

            }


        }catch (Exception ee){}
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginBothActivity.this, Registration.class);
                startActivity(intent);
            }
        });
        if (getIntent().getBooleanExtra("canChangePwd", false)) {
            if (getIntent().getStringExtra("student_id") != null) {
                //http://brainmate.co.in/brainmate/teacher-myaccount.php?teacher_id=5
                Uri uri = Uri.parse(Apis.student_change_pwd + "student_id=" + getIntent().getStringExtra("student_id")); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            } else if (getIntent().getStringExtra("teacher_id") != null) {
                Uri uri = Uri.parse(Apis.teacher_change_pwd + "teacher_id=" + getIntent().getStringExtra("teacher_id"));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        }
        Student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student.setSelected(true);
                Teacher.setChecked(false);
                Status = 1;
                /*StEmail.setText("deep59613@gmail.com");
                stPassword.setText("123456");*/
            }
        });

        Teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Student.setSelected(false);

                Teacher.setChecked(true);
                Student.setChecked(false);
                Status = 2;
                /*StEmail.setText("ravi@gmail.com");
                stPassword.setText("123456");*/
                /*StEmail.setText("demo3@gmail.com");
                stPassword.setText("123456");*/

            }
        });

        loginbuttonStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Teacher.isChecked()) {
                    Teacher.setChecked(true);
                    Student.setChecked(false);
                    Status = 2;
                    /*StEmail.setText("ravi@gmail.com");
                    stPassword.setText("123456");*/
                    /*StEmail.setText("demo3@gmail.com");
                    stPassword.setText("123456");*/
                }
                studentLoginClick();
            }
        });

        /*keeplogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = LoginBothActivity.this.getSharedPreferences("LoginDetails", LoginBothActivity.this.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Email", StEmail.getText().toString());
                editor.putString("Password", stPassword.getText().toString());
                editor.putString("Status", String.valueOf(Status));
                editor.putString("Access_code", "2222");
                editor.putString("Teachers_ID", Teachers_ID);
                editor.apply();
            }
        });*/

        //region "Forget Credentials"
        Forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(LoginBothActivity.this);
                dialog.setContentView(R.layout.forgetpassword);
                dialog.setTitle(getString(R.string.app_name));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
                TeacherF = (RadioButton) dialog.findViewById(R.id.TeacherF);
                StudentF = (RadioButton) dialog.findViewById(R.id.StudentF);
                final EditText EmailIDS = (EditText) dialog.findViewById(R.id.EmailID);
                final EditText EmailIDT = (EditText) dialog.findViewById(R.id.EmailIDT);
                Button Submit = (Button) dialog.findViewById(R.id.Submit);
                Button SubmitT = (Button) dialog.findViewById(R.id.SubmitT);
                final LinearLayout studentslay = (LinearLayout) dialog.findViewById(R.id.studentslay);

                final LinearLayout Techerslay = (LinearLayout) dialog.findViewById(R.id.Techerslay);
                TeacherF.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TeacherF.setChecked(true);
                        StudentF.setChecked(false);
                        studentslay.setVisibility(View.GONE);
                        Techerslay.setVisibility(View.VISIBLE);
                    }
                });
                StudentF.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TeacherF.setChecked(false);
                        StudentF.setChecked(true);
                        studentslay.setVisibility(View.VISIBLE);
                        Techerslay.setVisibility(View.GONE);

                    }
                });
                Submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog = new ProgressDialog(LoginBothActivity.this);
                        progressDialog.setMessage("Loading..."); // Setting Title
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                        progressDialog.show(); // Display Progress Dialog
                        progressDialog.setCancelable(false);
                        String url = Apis.base_url + Apis.forget_pwd;
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("Forgetstudent", response);
                                        progressDialog.dismiss();
                                        try {
                                            JSONObject jsonObject1 = new JSONObject(response);
                                            Log.d("login_succes", "" + jsonObject1.getString("success"));
                                            String LoginCredential = jsonObject1.getString("success");
                                            if (LoginCredential.equals("1")) {
                                                Toast.makeText(LoginBothActivity.this, "Please check your mail.", Toast.LENGTH_SHORT).show();
                                            } else if (LoginCredential.equals("0")) {
                                                Toast.makeText(LoginBothActivity.this, "Not a valid email", Toast.LENGTH_SHORT).show();
                                            }
                                            //  Log.d("Sssssssslist", String.valueOf(PatientList));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                progressDialog.dismiss();
                                System.out.println("ResponseRegistration" + volleyError.getMessage());
                                if (volleyError instanceof NoConnectionError) {
                                    Toast.makeText(LoginBothActivity.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LoginBothActivity.this, R.string.some_error_occurred, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }) {
                            @Override
                            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                                java.util.Map<String, String> params = new HashMap<>();
                                params.put("email", EmailIDS.getText().toString());
                                Log.d("email11",params.toString());
                                return params;
                            }
                        };
                        CommonMethods.callWebserviceForResponse(stringRequest, context);
                    }
                });

                SubmitT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog = new ProgressDialog(LoginBothActivity.this);
                        progressDialog.setMessage("Loading..."); // Setting Title
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                        progressDialog.show(); // Display Progress Dialog
                        progressDialog.setCancelable(false);
                        //String url = "http://www.techive.in/studybuddy/api/teacher_reset_password.php";
                        String url = Apis.base_url+Apis.teacher_reset_pwd;
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("ForgetTeacher", response);
                                        progressDialog.dismiss();
                                        try {
                                            JSONObject jsonObject1 = new JSONObject(response);
                                            Log.d("login_succes", "" + jsonObject1.getString("success"));
                                            String LoginCredential = jsonObject1.getString("success");
                                            if (LoginCredential.equals("1")) {
                                                Toast.makeText(LoginBothActivity.this, "Please check your mail.", Toast.LENGTH_SHORT).show();
                                            } else if (LoginCredential.equals("0")) {
                                                Toast.makeText(LoginBothActivity.this, "Not a valid email", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                progressDialog.dismiss();
                                Log.d("getParamsDatas11", "" + volleyError.getMessage());
                                if (volleyError instanceof NoConnectionError) {
                                    Toast.makeText(LoginBothActivity.this, "Internet not Connected", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LoginBothActivity.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }) {
                            @Override
                            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                                java.util.Map<String, String> params = new HashMap<>();
                                params.put("email", EmailIDT.getText().toString());
                                Log.d("email",params.toString());
                                return params;
                            }
                        };
                        CommonMethods.callWebserviceForResponse(stringRequest, context);
                    }
                });
            }
        });//endregion
    }


    private void studentLoginClick() {
        if (NetworkUtil.getConnectivityStatus(LoginBothActivity.this) > 0) {
            System.out.println("Connect");
            Network_Status = "Connect";
            if (StEmail.getText().toString().isEmpty() && stPassword.getText().toString().isEmpty()) {
                StEmail.setError("Please fill Email");
                stPassword.setError("Please fill Password");
            } else if (StEmail.getText().toString().isEmpty()) {
                StEmail.setError("Please fill Email");
            } else if (stPassword.getText().toString().isEmpty()) {
                stPassword.setError("Please fill Password");
            } else {

                Log.w(TAG,"onCreate LoginBothActivity status="+Status);
                /*    Intent intent=new Intent(LoginBothActivity.this,StudentdashBord.class);
                    startActivity(intent);*/
                if (Status == 0) {
                    Toast.makeText(LoginBothActivity.this, "Please Select teacher or Student", Toast.LENGTH_SHORT).show();
                }
                Log.d("LOOOOOOOO", "FFFFFFFFFFFFFFFFF" + Status);
                if (Status == 1) {


                    Log.d("LOOOOOOOO", "FFFFFFFFFFFFFFFFF" + Status);
                    progressDialog = new ProgressDialog(LoginBothActivity.this);
                    progressDialog.setMessage("Loading..."); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    progressDialog.setCancelable(false);

                    String url = Apis.base_url1 + Apis.student_login_url;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @SuppressLint("ResourceAsColor")
                                @Override
                                public void onResponse(String response) {
                                    Log.d("Looocococococ123", response);
                                    progressDialog.dismiss();
                                    try {
                                        Log.d("Status123", "3");
                                        JSONObject jsonObject1 = new JSONObject(response);
                                        String LoginCredential = jsonObject1.getString("success");
                                        if (LoginCredential.equals("0")) {
                                            Toast.makeText(LoginBothActivity.this, "" + getString(R.string.invalid_email_password), Toast.LENGTH_LONG).show();
                                        }
                                        JSONArray heroArray = jsonObject1.getJSONArray("data");
                                        JSONObject c = heroArray.getJSONObject(0);
                                        String accesscodes = c.getString("accesscodes");
                                        String Student_ID = c.getString("s_id");
                                        Log.d("login_succes_student", "" + LoginCredential);
                                        String Active_Status = c.getString("status");
                                        String email = c.getString("email");

                                        if (LoginCredential.equals("1")) {
                                            //student//////////////!!!!!!!!!!!!
                                            Log.w(TAG,"onCreate LoginBothActivity Login");
                                            Log.d("Status123", "1");
                                            Intent intent = new Intent(LoginBothActivity.this, Stu_Classes.class);
                                            intent.putExtra("accesscodes", accesscodes);
                                            intent.putExtra("Student_ID",Student_ID);
                                            SharedPreferences.Editor editor1= pref.edit();


                                            Log.w(TAG,"onCreate LoginBothActivity LoginCredential Ac:"+accesscodes);
                                            Log.w(TAG,"onCreate LoginBothActivity LoginCredential SI:"+Student_ID);
                                            Log.w(TAG,"onCreate LoginBothActivity LoginCredential IsLogin:"+pref.getInt("IsLogin",0));
                                            Log.w(TAG,"onCreate LoginBothActivity LoginCredential Em:"+email);
                                            Log.w(TAG,"onCreate LoginBothActivity LoginCredential Nm:"+c.getString("name"));

                                            if(pref.getInt("IsLogin",0)!=0){
                                                editor1.remove("IsLogin");
                                                editor1.putInt("IsLogin",1);
                                                editor1.apply();
                                            }else {
                                                editor1.putInt("IsLogin",1);

                                            }
                                            keeplogin.setChecked(true);

                                            CommonMethods.saveEmailId(LoginBothActivity.this, email);
                                            CommonMethods.saveId(LoginBothActivity.this, Student_ID);
                                            CommonMethods.saveUsername(LoginBothActivity.this, c.getString("name"));
                                            CommonMethods.saveType(LoginBothActivity.this, "s");
                                            CommonMethods.saveIsLogin(LoginBothActivity.this, keeplogin.isChecked() ? 1 : 0);//Student==1
                                            CommonMethods.saveAccessCode(LoginBothActivity.this, accesscodes);

                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        } else if (LoginCredential.equals("0")) {
                                            Toast.makeText(LoginBothActivity.this, "Access Code Invalid", Toast.LENGTH_LONG).show();
                                        } else {
                                            final Dialog dialog = new Dialog(LoginBothActivity.this);
                                            dialog.setContentView(R.layout.dialog_layout);
                                            //dialog.setTitle("Custom Dialog");
                                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                            dialog.show();
                                            ImageView imgCross = dialog.findViewById(R.id.imgCross);
                                            imgCross.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dialog.dismiss();
                                                    finish();
                                                }
                                            });
                                                    /*builder.setMessage("Please contact Publisher for further details.")
                                                            .setCancelable(false)
                                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    finish();
                                                                }
                                                            });
                                                    //Creating dialog box
                                                    AlertDialog alert = builder.create();
                                                    //Setting the title manually
                                                    alert.setTitle("Login Expired");
                                                    alert.show();*/
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
                            params.put("email", StEmail.getText().toString());
                            params.put("password", stPassword.getText().toString());
                        /*    params.put("email","yugal.k236@gmail.com");
                            params.put("password","Yugal@1234");*/
                            Log.d("stu_params", "" + params.toString());
                            return params;
                        }
                    };
                    CommonMethods.callWebserviceForResponse(stringRequest, LoginBothActivity.this);

                } else if (Status == 2) {
                     /*   Intent intent32 = new Intent(LoginBothActivity.this, Books_Access_Code.class);
                        startActivity(intent32);*/
                    Log.d("LOOOOOOOO", "FFFFFFFFFFFFFFFFF" + Status);
                    progressDialog = new ProgressDialog(LoginBothActivity.this);
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
                                        //  Log.d("login_succes_student", "" + jsonObject1.getString("success"));
                                        String LoginCredential = jsonObject1.getString("success");
                                        String email_field = jsonObject1.getString("email_field");

                                        if (LoginCredential.equals("0") && email_field.equals("0")) {
                                            Toast.makeText(LoginBothActivity.this, getString(R.string.invalid_email_password), Toast.LENGTH_LONG).show();
                                        } else if (LoginCredential.equals("1") && email_field.equals("0")) {
                                            Toast.makeText(LoginBothActivity.this, getString(R.string.register_first), Toast.LENGTH_LONG).show();
                                        }
                                        Log.d("login_succes_student", "" + LoginCredential);

                                        JSONArray heroArray = jsonObject1.getJSONArray("data");
                                        JSONObject c = heroArray.getJSONObject(0);
                                        String accesscodes = c.getString("accesscodes");
                                        String Active_Status = c.getString("status");
                                        String email = c.getString("email");
                                        String name = c.getString("name");
                                        Teachers_ID = c.getString("t_id");

                                        Log.d("teachers_ID", "Teachers " + Teachers_ID + "Acc " + accesscodes + "Status" + Active_Status);

                                        if (Active_Status.equals("1")) {
                                            if (LoginCredential.equals("1")) {
                                                Log.d("Status123", "1");
                                                SharedPreferences.Editor editor1= pref.edit();
                                                editor1.putInt("IsLogin",2);
                                                editor1.apply();
                                                keeplogin.setChecked(true);
                                                /* Intent intent = new Intent(LoginBothActivity.this, Books_Access_Code.class);*/
                                                Intent intent = new Intent(LoginBothActivity.this, Main2Activity.class);
                                                intent.putExtra("name", "" + name);
                                                intent.putExtra("email", "" + email);
                                                intent.putExtra("Teachers_ID",Teachers_ID);
                                                intent.putExtra("accesscodes", accesscodes);
                                                CommonMethods.saveEmailId(LoginBothActivity.this, email);
                                                CommonMethods.saveId(LoginBothActivity.this, Teachers_ID);
                                                CommonMethods.saveType(LoginBothActivity.this, "t");
                                                CommonMethods.saveUsername(LoginBothActivity.this, c.getString("name"));
                                                CommonMethods.saveIsLogin(LoginBothActivity.this, keeplogin.isChecked() ? 2 : 0);//Teacher==2
                                                CommonMethods.saveAccessCode(LoginBothActivity.this, accesscodes);
                                                intent.putExtra("Teachers_ID", Teachers_ID);
                                                intent.putExtra("EntryType", "Log");
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            } else if (LoginCredential.equals("0")) {
                                                Toast.makeText(LoginBothActivity.this, "", Toast.LENGTH_LONG).show();
                                            }
                                        } else {

                                            final Dialog dialog = new Dialog(LoginBothActivity.this);
                                            dialog.setContentView(R.layout.dialog_layout);
                                            //dialog.setTitle("Custom Dialog");
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
                                                    /*builder.setMessage("Please contact Publisher for further details.")
                                                            .setCancelable(false)
                                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    finish();
                                                                }
                                                            });
                                                    //Creating dialog box
                                                    AlertDialog alert = builder.create();
                                                    //Setting the title manually
                                                    alert.setTitle("Login Expired");
                                                    alert.show();*/
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
                            params.put("email", StEmail.getText().toString());
                            params.put("password", stPassword.getText().toString());
                            Log.d("params_detail", params.toString());
                            return params;
                        }
                    };
                    CommonMethods.callWebserviceForResponse(stringRequest, LoginBothActivity.this);
                }
            }


        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginBothActivity.this);
            alertDialogBuilder.setMessage(getString(R.string.no_internet));
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton(getString(R.string.retry),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int arg1) {
                            if (NetworkUtil.getConnectivityStatus(LoginBothActivity.this) > 0) {
                                Toast.makeText(LoginBothActivity.this, "" + getString(R.string.connected), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                studentLoginClick();
                            }
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            //System.out.println("No connection");
            Network_Status = "notConnect";
        }
    }
}
