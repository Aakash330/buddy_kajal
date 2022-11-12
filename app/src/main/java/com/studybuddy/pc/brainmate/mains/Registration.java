package com.studybuddy.pc.brainmate.mains;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Pattern;

import static com.studybuddy.pc.brainmate.Network_connection.data.Constants.CONNECTIVITY_ACTION;

public class Registration extends AppCompatActivity {

    //region "Variable"
    RadioButton Student, Teacher;
    Button loginbuttonTecher, Studentlogin;
    int UserStatus = 0;
    LinearLayout TeacherLayout, Studentlayout, student_Fuul_layout, Tchers_full_layout;
    ImageView accessCheck, accessCheckteche;
    EditText AccessCode, AccessCodeTeacher;
    ProgressDialog progressDialog;
    String AccessPass = "12365";
    Button Student_Validate, Teachers_Validate;
    EditText StEmail, stPassword, StName, StContact, StSchoolName;
    EditText TCUsername, TCPassword, TCSchholName, TCName, TCAddress, TCPhonenumber, TCSchholAddress, TCSchholPhoneNumber, TCEmails;
    IntentFilter intentFilter;
    NetworkChangeReceiver receiver;
    String Network_Status;
    TextView txtAccessCodeTeacher, txtUsernameTeacher, txtPasswordTeacher;
    private static TextView log_network;
    private static String log_str;
    TextView txt_share_details;
    LinearLayout llShareDetails;
    Context context;
    //endregion

    //region "Overrided Methods"
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
    //endregion

    //region "OnCreate"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Register");
        context = Registration.this;
        intentFilter = new IntentFilter();
        intentFilter.addAction(CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();

        txtAccessCodeTeacher = findViewById(R.id.txtAccessCodeTeacher);
        llShareDetails = findViewById(R.id.llShareDetails);
        txtUsernameTeacher = findViewById(R.id.txtUsernameTeacher);
        txtPasswordTeacher = findViewById(R.id.txtPasswordTeacher);
        txt_share_details = findViewById(R.id.txt_share_details);
        SpannableString content = new SpannableString(txt_share_details.getText().toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        txt_share_details.setText(content);
        Student_Validate = (Button) findViewById(R.id.Student_Validate);
        Teachers_Validate = (Button) findViewById(R.id.Teachers_Validate);
        loginbuttonTecher = (Button) findViewById(R.id.Teacherbutton);
        Studentlogin = (Button) findViewById(R.id.loginbuttonStudent);
        accessCheck = (ImageView) findViewById(R.id.accessCheck);
        accessCheckteche = (ImageView) findViewById(R.id.accessCheckteche);
        Student = (RadioButton) findViewById(R.id.doct);
        Teacher = (RadioButton) findViewById(R.id.Pateintt);

        Student.setSelected(true);
        AccessCode = (EditText) findViewById(R.id.AccessCode);
        AccessCodeTeacher = (EditText) findViewById(R.id.AccessCodeTeacher);

        TeacherLayout = (LinearLayout) findViewById(R.id.TeacherLayout);
        Studentlayout = (LinearLayout) findViewById(R.id.StudentlAyout);
        student_Fuul_layout = (LinearLayout) findViewById(R.id.student_Fuul_layout);
        Tchers_full_layout = (LinearLayout) findViewById(R.id.Tchers_full_layout);

        StEmail = (EditText) findViewById(R.id.StEmail);
        stPassword = (EditText) findViewById(R.id.stPassword);
        StName = (EditText) findViewById(R.id.StName);
        StContact = (EditText) findViewById(R.id.StContact);
        StSchoolName = (EditText) findViewById(R.id.StSchoolName);


        // TCAccesscode=(EditText)findViewById(R.id.TCAccesscode);
        TCUsername = (EditText) findViewById(R.id.TCUsername);
        TCPassword = (EditText) findViewById(R.id.TCPassword);
        TCSchholName = (EditText) findViewById(R.id.TCSchholName);
        TCName = (EditText) findViewById(R.id.TCName);
        TCAddress = (EditText) findViewById(R.id.TCAddress);
        TCEmails = (EditText) findViewById(R.id.TCEmails);

        TCPhonenumber = (EditText) findViewById(R.id.TCPhonenumber);
        TCSchholAddress = (EditText) findViewById(R.id.TCSchholAddress);
        TCSchholPhoneNumber = (EditText) findViewById(R.id.TCSchholPhoneNumber);


        if (Student.isChecked()) {
            student_Fuul_layout.setVisibility(View.VISIBLE);
        } else if (!Student.isChecked()) {
            student_Fuul_layout.setVisibility(View.GONE);
        }
        llShareDetails.setVisibility(Teacher.isChecked() ? View.VISIBLE : View.GONE);

        Student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student.setChecked(true);
                Teacher.setChecked(false);
                student_Fuul_layout.setVisibility(View.VISIBLE);
                Tchers_full_layout.setVisibility(View.GONE);
                llShareDetails.setVisibility(Teacher.isChecked() ? View.VISIBLE : View.GONE);
            }

        });

        //region "student_access"
        Student_Validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student_ValidateOnClick();
            }
        });//endregion

        //region "Teacher"
        Teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student.setChecked(false);
                Teacher.setChecked(true);
                Tchers_full_layout.setVisibility(View.VISIBLE);
                student_Fuul_layout.setVisibility(View.GONE);
                llShareDetails.setVisibility(Teacher.isChecked() ? View.VISIBLE : View.GONE);
            }
        });//endregion

        //region "teacher_access"
        Teachers_Validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Teachers_ValidateOnClick();
            }
        });//endregion

        //region "teacher_register"
        loginbuttonTecher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginbuttonTecherOnClick();
            }
        });//endregion

        //region "student_register"
        Studentlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentloginOnClick();
            }
        });//endregion
        txt_share_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registration.this, ShareDetailsActivity.class);
                startActivity(intent);
            }
        });
    }//endregion

    private void Teachers_ValidateOnClick() {
        if (NetworkUtil.getConnectivityStatus(Registration.this) > 0) {
            System.out.println("Connect");
            Network_Status = "Connect";

            if (AccessCodeTeacher.getText().toString().isEmpty()) {
                Student.setChecked(false);
                Teacher.setChecked(false);
                AccessCode.setError("Please Fill Accesses Code");
                Toast.makeText(Registration.this, "Please fill Access Code", Toast.LENGTH_LONG).show();
            } else {
                progressDialog = new ProgressDialog(Registration.this);
                progressDialog.setMessage("Loading..."); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                progressDialog.setCancelable(false);
                //TODO
                //String url = "http://www.techive.in/studybuddy/api/teacher_access.php";
                String url = Apis.base_url + Apis.teacher_access_url;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @SuppressLint("ResourceAsColor")
                            @Override
                            public void onResponse(String response) {
                                Log.d("Login_response_teacher", response);
                                progressDialog.dismiss();
                                try
                                //TODO : success = 2 Invalid username or password correct access code
                                {
                                    JSONObject jsonObject1 = new JSONObject(response);
                                    String LoginCredential = jsonObject1.getString("success");
                                    Log.d("login_succe_tt", "" + LoginCredential);
                                    if (LoginCredential.equals("1")) {
                                        Student.setChecked(false);
                                        UserStatus = 2;
                                        Teacher.setChecked(true);
                                        Studentlayout.setVisibility(View.GONE);
                                        TeacherLayout.setVisibility(View.VISIBLE);
                                        AccessCode.setEnabled(false);
                                        AccessCode.setKeyListener(null);
                                        accessCheckteche.setBackgroundResource(R.drawable.checkedright);

                                        /**/
                                        txtAccessCodeTeacher.setVisibility(View.VISIBLE);
                                        txtUsernameTeacher.setVisibility(View.VISIBLE);
                                        txtPasswordTeacher.setVisibility(View.VISIBLE);

                                        txtAccessCodeTeacher.setText(AccessCodeTeacher.getText().toString());
                                        txtUsernameTeacher.setText(TCUsername.getText().toString());
                                        txtPasswordTeacher.setText(TCPassword.getText().toString());

                                        TCPassword.setVisibility(View.GONE);
                                        TCUsername.setVisibility(View.GONE);
                                        AccessCodeTeacher.setVisibility(View.GONE);
                                        /**/

                                           /* Teachers_Validate.setBackgroundColor(R.color.green);
                                            Teachers_Validate.setText("Valid");*/

                                        Teachers_Validate.setVisibility(View.GONE);
                                    } else if (LoginCredential.equals("0")) {
                                        Toast.makeText(Registration.this, "Access Code Invalid", Toast.LENGTH_LONG).show();
                                        accessCheckteche.setBackgroundResource(R.drawable.wrong);
                                           /* Student.setChecked(false);
                                            Teacher.setChecked(false);*/
                                        Studentlayout.setVisibility(View.GONE);
                                        TeacherLayout.setVisibility(View.GONE);
                                        Teachers_Validate.setBackgroundColor(R.color.red);
                                        Teachers_Validate.setText("Retry");
                                    }
                                    if (LoginCredential.equals("2")) {
                                        //Toast.makeText(Registration.this, "" + getString(R.string.valid_access_code_invalid_email_pass), Toast.LENGTH_SHORT).show();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                                        builder.setMessage(getString(R.string.valid_access_code_invalid_email_pass))
                                                .setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                        //Creating dialog box
                                        AlertDialog alert = builder.create();
                                        //Setting the title manually
                                        //alert.setTitle("Login Expired");
                                        alert.show();
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
                        params.put("accesscodes", AccessCodeTeacher.getText().toString());
                        params.put("username", TCUsername.getText().toString());
                        //params.put("email", TCUsername.getText().toString());
                        params.put("password", TCPassword.getText().toString());
                        Log.d("accesscodes", AccessCodeTeacher.getText().toString());

                        return params;
                    }
                };
                CommonMethods.callWebserviceForResponse(stringRequest, context);
            }
        } else {
            System.out.println("No connection");
            Network_Status = "notConnect";
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Registration.this);
            alertDialogBuilder.setMessage(getString(R.string.no_internet));
            alertDialogBuilder.setPositiveButton(getString(R.string.retry),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int arg1) {
                            if (NetworkUtil.getConnectivityStatus(Registration.this) > 0) {
                                Toast.makeText(Registration.this, "" + getString(R.string.connected), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Teachers_ValidateOnClick();
                            }
                        }
                    });


            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    private void loginbuttonTecherOnClick() {
        if (NetworkUtil.getConnectivityStatus(Registration.this) > 0) {
            System.out.println("Connect");
            Network_Status = "Connect";

            if (TCUsername.getText().toString().equals("")) {
                TCUsername.setError("Please Fill Name");
            } else if (TCPassword.getText().toString().equals("")) {
                TCPassword.setError("Please Fill Password");
            } else if (TCName.getText().toString().equals("")) {
                TCName.setError("Please Fill Name");
            } else if (TCPhonenumber.getText().toString().equals("")) {
                TCPhonenumber.setError("Please Fill Number");
            } else if (!TCPhonenumber.getText().toString().isEmpty() && TCPhonenumber.getText().toString().length() < 10) {
                TCPhonenumber.setError("Please Fill valid Mobile Number");
            } else if (!isValidEmail(TCEmails.getText().toString())) {
                TCEmails.setError("Please Fill Valid Email");
            } else if (TCEmails.getText().toString().equals("")) {
                TCEmails.setError("Please Fill Email");
            } else if (TCAddress.getText().toString().equals("")) {
                TCAddress.setError("Please Fill Address");
            } else if (TCSchholName.getText().toString().equals("")) {
                TCSchholName.setError("Please Fill School Name");
            } else if (TCSchholAddress.getText().toString().equals("")) {
                TCSchholAddress.setError("Please Fill School address");
            } else if (TCSchholPhoneNumber.getText().toString().equals("")) {
                TCSchholPhoneNumber.setError("Please Fill School Number");
            }/*else if (!isValidMobile(TCSchholPhoneNumber.getText().toString())) {
                TCSchholPhoneNumber.setError("Please Fill Valid School Number");
            }*/ else if (!isValidMobile(TCSchholPhoneNumber.getText().toString())) {
                TCSchholPhoneNumber.setError("Please Fill valid Phone Number");
            } else {
                Log.d("Login_response_techers", "Click");
                progressDialog = new ProgressDialog(Registration.this);
                progressDialog.setMessage("Loading..."); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                progressDialog.setCancelable(false);
                //String url = "http://www.techive.in/studybuddy/api/teacher_register.php";
                String url = Apis.base_url + Apis.teacher_register_url;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Login_techers11100", response);
                                progressDialog.dismiss();
                                try {
                                    Log.d("Status123", "3");
                                    JSONObject jsonObject1 = new JSONObject(response);
                                    //  Log.d("login_succes_student", "" + jsonObject1.getString("success"));
                                    String SuccessValue = jsonObject1.getString("success");
                                    String Accesssescoderesp = jsonObject1.getString("accesscodes_flag");
                                    String alerady_registered = jsonObject1.getString("already_registered");
                                    String teacher_id = jsonObject1.getString("teacher_id");

                                    Log.d("login_succes_student", "" + SuccessValue);
                                    Log.d("Accesssescoderesp", "" + Accesssescoderesp);

                                    if (alerady_registered != null && alerady_registered.equals("1")) {
                                        Toast.makeText(Registration.this, "Email Already registered. Please login Again.", Toast.LENGTH_SHORT).show();

                                    } else if (SuccessValue.equals("1")) {
                                        //TODO :
                                        Intent intent = new Intent(Registration.this, LoginBothActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        //Intent intent = new Intent(Registration.this, Stu_Classes.class);
                                        intent.putExtra("accesscodes", AccessCode.getText().toString());
                                        intent.putExtra("Teachers_ID", "" + teacher_id);
                                        startActivity(intent);
                                        //CommonMethods.saveIsLogin(Registration.this, 1);//teacher==1
                                    } else if (SuccessValue.equals("0")) {
                                        Toast.makeText(Registration.this, "" + getString(R.string.some_error_occurred), Toast.LENGTH_SHORT).show();
                                    }
                                            /*if (Accesssescoderesp.equals("1")) {
                                                Toast.makeText(Registration.this, "Email Already registered. Please login Again.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                if (SuccessValue.equals("0"))
                                                {
                                                    Toast.makeText(Registration.this, "Something Went wrong", Toast.LENGTH_LONG).show();
                                                }else {
                                                    if (SuccessValue.equals("1"))
                                                    {
                                                        Intent intent = new Intent(Registration.this, Books_Access_Code.class);
                                                        intent.putExtra("accesscodes", AccessCode.getText().toString());
                                                        startActivity(intent);
                                                    }
                                                }
                                            }
                                            if (SuccessValue.equals("1"))
                                            {
                                                Intent intent = new Intent(Registration.this, Books_Access_Code.class);
                                                intent.putExtra("accesscodes", AccessCode.getText().toString());
                                                startActivity(intent);
                                            } else if (SuccessValue.equals("0"))
                                            {
                                                Toast.makeText(Registration.this, "Something Went wrong", Toast.LENGTH_LONG).show();
                                            } else if (SuccessValue.equals("2")) {
                                                Toast.makeText(Registration.this, "Email Already registered. Please login Again", Toast.LENGTH_LONG).show();
                                            }*/

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
                        params.put("username", TCUsername.getText().toString());
                        params.put("password", TCPassword.getText().toString());
                        params.put("name", TCName.getText().toString());
                        params.put("ph_no", TCPhonenumber.getText().toString());
                        params.put("email", TCEmails.getText().toString());
                        params.put("accesscodes", AccessCodeTeacher.getText().toString());
                        params.put("school", TCSchholName.getText().toString());
                        params.put("address", TCAddress.getText().toString());
                        params.put("schl_addr", TCSchholAddress.getText().toString());
                        params.put("status", "1");
                        params.put("schl_ph_no", TCSchholPhoneNumber.getText().toString());
                        Log.d("teacher_reg_params", "" + params.toString());
                        return params;
                    }
                };
                CommonMethods.callWebserviceForResponse(stringRequest, context);
            }
        } else {
            System.out.println("No connection");
            Network_Status = "notConnect";
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Registration.this);
            alertDialogBuilder.setMessage(getString(R.string.no_internet));
            alertDialogBuilder.setPositiveButton(getString(R.string.retry),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int arg1) {
                            if (NetworkUtil.getConnectivityStatus(Registration.this) > 0) {
                                Toast.makeText(Registration.this, "" + getString(R.string.connected), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                loginbuttonTecherOnClick();
                            }
                        }
                    });


            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    private void StudentloginOnClick() {
        if (NetworkUtil.getConnectivityStatus(Registration.this) > 0) {
            System.out.println("Connect");
            Network_Status = "Connect";

            if (StEmail.getText().toString().equals("")) {
                StEmail.setError("Please Fill Email");
            } else if (stPassword.getText().toString().equals("")) {
                stPassword.setError("Please Fill Password");
            } else if (StName.getText().toString().equals("")) {
                StName.setError("Please Fill Name");
            } else if (StContact.getText().toString().equals("")) {
                StContact.setError("Please Fill Contact");
            } else if (!isValidEmail(StEmail.getText().toString())) {
                StEmail.setError("Please Fill Valid Email");
            } else if (StSchoolName.getText().toString().equals("")) {
                StSchoolName.setError("Please Fill School Name");
            } else if (AccessCode.getText().toString().equals("")) {
                AccessCode.setError("Please Fill AccessCode");
            } else {


                progressDialog = new ProgressDialog(Registration.this);
                progressDialog.setMessage("Loading..."); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                progressDialog.setCancelable(false);
                //String url = "http://www.techive.in/studybuddy/api/student_register.php";
                String url = Apis.base_url + Apis.student_register_url;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Login_resp_student111", response);
                                progressDialog.dismiss();

                                // Here for already registered user success value is 2
                                try {
                                    Log.d("Status123", "3");
                                    JSONObject jsonObject1 = new JSONObject(response);
                                    //  Log.d("login_succes_student", "" + jsonObject1.getString("success"));
                                    String LoginCredential = jsonObject1.getString("success");
                                    Log.d("Login_resp_student111", "" + LoginCredential);

                                    if (LoginCredential != null && LoginCredential.equals("1")) {
                                        //response : {"accesscodes":"1","success":"1","student_id":"12"}
                                        //TODO : Changes Made below
                                                /*Intent intent = new Intent(Registration.this, StudentdashBord.class);
                                                intent.putExtra("accesscodes", AccessCode.getText().toString());
                                                startActivity(intent);*/
                                        CommonMethods.showToast(Registration.this, "Login with username and password");
                                        String student_id = jsonObject1.getString("student_id");
                                        Intent intent = new Intent(Registration.this, LoginBothActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        //intent.putExtra("accesscodes", accesscodes);
                                        intent.putExtra("Student_ID", student_id);
                                        startActivity(intent);

                                    } else if (LoginCredential != null && LoginCredential.equals("0")) {
                                        Toast.makeText(Registration.this, "Something Went wrong", Toast.LENGTH_LONG).show();
                                    }
                                    //Here success = 2 is same as email_already = 1
                                    if (jsonObject1.getString("email_already") != null && jsonObject1.getString("email_already").equals("1")) {
                                        //Toast.makeText(Registration.this, "Email Already Registered", Toast.LENGTH_LONG).show();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                                        builder.setMessage(getString(R.string.already_register_user))
                                                .setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                        //Creating dialog box
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                    }
                                } catch (JSONException e) {
                                    Log.d("getDetailshere", "" + e.getMessage());
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
                        params.put("name", StName.getText().toString());
                        params.put("ph_no", StContact.getText().toString());
                        params.put("school", StSchoolName.getText().toString());
                        params.put("accesscodes", AccessCode.getText().toString());
                        params.put("status", "1");

                        return params;
                    }
                };
                CommonMethods.callWebserviceForResponse(stringRequest, context);
            }
        } else {
            System.out.println("No connection");
            Network_Status = "notConnect";
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Registration.this);
            alertDialogBuilder.setMessage(getString(R.string.no_internet));
            alertDialogBuilder.setPositiveButton(getString(R.string.retry),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int arg1) {
                            if (NetworkUtil.getConnectivityStatus(Registration.this) > 0) {
                                Toast.makeText(Registration.this, "" + getString(R.string.connected), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                StudentloginOnClick();
                            }
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean isValidMobile(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() < 6 || phone.length() > 13) {

                check = false;

            } else {
                check = true;
            }
        } else {
            check = false;
        }
        return check;
    }

    private void Student_ValidateOnClick() {
        if (NetworkUtil.getConnectivityStatus(Registration.this) > 0) {
            System.out.println("Connect");
            Network_Status = "Connect";

            if (AccessCode.getText().toString().isEmpty()) {
                Student.setChecked(false);
                Teacher.setChecked(false);
                AccessCode.setError("Please Fill Accesses Code");
                Toast.makeText(Registration.this, "Please fill Access Code", Toast.LENGTH_LONG).show();
            } else {
                //TODO : success = 1 valid access code
                progressDialog = new ProgressDialog(Registration.this);
                progressDialog.setMessage("Loading..."); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                progressDialog.setCancelable(false);
                //String url = "http://www.techive.in/studybuddy/api/student_access.php";
                String url = Apis.base_url + Apis.student_access_url;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @SuppressLint("ResourceAsColor")
                            @Override
                            public void onResponse(String response) {
                                Log.d("Login_response", response);
                                progressDialog.dismiss();
                                try {
                                    Log.d("Status123", "3");
                                    JSONObject jsonObject1 = new JSONObject(response);
                                    //  Log.d("login_succes_student", "" + jsonObject1.getString("success"));
                                    String LoginCredential = jsonObject1.getString("success");
                                    Log.d("login_succes_student", "" + LoginCredential);
                                    if (LoginCredential.equals("1")) {
                                        Log.d("Status123", "1");
                                        Student.setChecked(true);
                                        UserStatus = 1;
                                        Teacher.setChecked(false);
                                        Studentlayout.setVisibility(View.VISIBLE);
                                        TeacherLayout.setVisibility(View.GONE);
                                        AccessCode.setEnabled(false);
                                        AccessCode.setKeyListener(null);
                                        accessCheck.setBackgroundResource(R.drawable.checkedright);
                                          /*  Student_Validate.setBackgroundColor(R.color.green);
                                            Student_Validate.setText("Valid");*/
                                        Student_Validate.setVisibility(View.GONE);

                                    } else if (LoginCredential.equals("0")) {
                                        Toast.makeText(Registration.this, "Access Code Invalid", Toast.LENGTH_LONG).show();
                                        accessCheck.setBackgroundResource(R.drawable.wrong);
                                        Log.d("Status12345", "2");
                                                /*Student.setChecked(false);
                                                Teacher.setChecked(false);*/
                                        Studentlayout.setVisibility(View.GONE);
                                        TeacherLayout.setVisibility(View.GONE);
                                        Student_Validate.setBackgroundColor(R.color.red);
                                        Student_Validate.setText("Retry");
                                        //accessCheck.setVisibility(View.VISIBLE);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                                        builder.setMessage(getString(R.string.access_code_exp))
                                                .setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                        //Creating dialog box
                                        AlertDialog alert = builder.create();
                                        //Setting the title manually
                                        alert.setTitle("Login Expired");
                                        alert.show();
                                    }
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
                            Toast.makeText(Registration.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Registration.this, R.string.some_error_occurred, Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {
                    @Override
                    protected java.util.Map<String, String> getParams() throws AuthFailureError {
                        java.util.Map<String, String> params = new HashMap<>();
                        params.put("accesscodes", AccessCode.getText().toString());
                        return params;
                    }
                };
                //.add(stringRequest);
                CommonMethods.callWebserviceForResponse(stringRequest, context);
            }
        } else {
            System.out.println("No connection");
            Network_Status = "notConnect";
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Registration.this);
            alertDialogBuilder.setMessage(getString(R.string.no_internet));
            alertDialogBuilder.setPositiveButton(getString(R.string.retry),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int arg1) {
                            if (NetworkUtil.getConnectivityStatus(Registration.this) > 0) {
                                Toast.makeText(Registration.this, "" + getString(R.string.connected), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Student_ValidateOnClick();
                            }
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
}
