package com.studybuddy.pc.brainmate.mains;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.studybuddy.pc.brainmate.Network_connection.services.NetworkChangeReceiver;
import com.studybuddy.pc.brainmate.Network_connection.utils.NetworkUtil;
import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.REgVerifyAccessCode;
import com.studybuddy.pc.brainmate.RegistrationModeView;
import com.studybuddy.pc.brainmate.student.CommonMethods;
import com.studybuddy.pc.brainmate.student.Stu_Classes;
import com.studybuddy.pc.brainmate.teacher.Main2Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import static com.studybuddy.pc.brainmate.Network_connection.data.Constants.CONNECTIVITY_ACTION;

public class Registration extends AppCompatActivity implements REgVerifyAccessCode {

    private static final String TAG = "Registration";
    private static String SMS_OTP;
    private static  long OTP_VALID_TILL;
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
    EditText StEmail, stPassword, StName, StContact, StSchoolName,StAddress;
    EditText TCUsername, TCPassword, TCSchholName, TCName, TCAddress, TCPhonenumber, TCSchholAddress, TCSchholPhoneNumber, TCEmails,TCSubject;
    IntentFilter intentFilter;
    NetworkChangeReceiver receiver;
    String Network_Status;
    TextView txtAccessCodeTeacher, txtUsernameTeacher, txtPasswordTeacher;
    private static TextView log_network;
    private static String log_str;
    TextView txt_share_details;
    LinearLayout llShareDetails;
    Context context=Registration.this;
    int statePos;
    REgVerifyAccessCode registrationVerify;
    private RegistrationModeView model;

    //endregion

    //new
    private final Executor mExecutor= Executors.newSingleThreadExecutor();
    private ArrayList<String> STATE_LIST,CITY_LIST;


    //CLASS LIST
    private boolean[] ST_SELECT_CLASS,TC_SELECT_CLASS;
    private ArrayList<Integer> ST_CLS_LIST,TC_CLS_LIST;
    private final String[] CLS_ARRAY = {"PLAY","NURSERY","LKG","UKG","kG","1st","2nd","3rd","4th","5th","6th","7th","8th","9th","10th","11th","12th"};

    //Student
    //SPINNER
    private Spinner ST_STATE,ST_CITY;
    //TEXTVIEW
    private TextView ST_CLASS,ST_SEND_OTP,ST_DIS_OTP,tvCity,PassInvalidTv;
    private LinearLayout TextBox;
    //EDITTEXT
    private EditText ST_ENTER_OTP;
    private boolean ST_OTP_CLICKED;
    private String ST_ACCESS_CODE;
    String ST_Id;

    //Teacher
    //SPINNER
    private Spinner TC_STATE,TC_CITY;
    //TEXTVIEW
    private TextView TC_CLASS,TC_SEND_OTP,TC_DIS_OTP,tvTcity,PassInvalidTCTv;
    //EDITTEXT
    private EditText TC_ENTER_OTP;
    //STRING
    private String TOTAL_STATE,TC_ACCESS_CODE,CheckAccessCode;
    private  boolean TC_OTP_CLICKED;
    boolean check;

    public SharedPreferences preferences,getPreference,pref;
    private boolean IsEmailValid;
    ArrayAdapter<String> arrayAdapterstate,arrayAdapter1;
    private String stateId;
     Observer<String> nameObserver;

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


        pref = context.getSharedPreferences("StudyBuddyPreference", context.MODE_PRIVATE);
        llShareDetails = findViewById(R.id.llShareDetails);
        txt_share_details = findViewById(R.id.txt_share_details);
        SpannableString content = new SpannableString(txt_share_details.getText().toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        txt_share_details.setText(content);
        loginbuttonTecher = (Button) findViewById(R.id.Teacherbutton);
        Studentlogin = (Button) findViewById(R.id.loginbuttonStudent);
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
        TCPassword = (EditText) findViewById(R.id.TCPassword);
        TCSchholName = (EditText) findViewById(R.id.TCSchholName);
        TCName = (EditText) findViewById(R.id.TCName);
        TCAddress = (EditText) findViewById(R.id.TCAddress);
        TCEmails = (EditText) findViewById(R.id.TCEmail);
        TCSubject = (EditText) findViewById(R.id.TCSubject);
        TCPhonenumber = (EditText) findViewById(R.id.TCPhonenumber);
        TCSchholAddress = (EditText) findViewById(R.id.TCSchholAddress);
        TCSchholPhoneNumber = (EditText) findViewById(R.id.TCSchholPhoneNumber);
        // ASSIGN VARIABLE
        //STUDENT
        //TEXTVIEW
        ST_CLASS= findViewById(R.id.textView);
        ST_SEND_OTP= findViewById(R.id.btnSendOtp);
        ST_DIS_OTP =findViewById(R.id.sendOtpBtnDis);
        TextBox=findViewById(R.id.textBox);
        PassInvalidTv=findViewById(R.id.PassInvalidTv);

        //SPINNER
        ST_STATE=findViewById(R.id.SpState);
        ST_CITY=findViewById(R.id.SpCity);
        //EDITTEXT
        ST_ENTER_OTP=(EditText) findViewById(R.id.edSendOtp);
        StAddress=(EditText) findViewById(R.id.StAddress);

        //TEACHER
        //TEXTVIEW
        TC_CLASS= findViewById(R.id.textView2);
        TC_SEND_OTP=findViewById(R.id.TCSendOtp);
        TC_DIS_OTP=findViewById(R.id.TCSendOtpDis);
        PassInvalidTCTv=findViewById(R.id.PassInvalidTCTv);
        //SPINNER
        TC_STATE = (Spinner) findViewById(R.id.TCState);
        TC_CITY = (Spinner) findViewById(R.id.TCCity);
        //EDITTEXT
        TC_ENTER_OTP=(EditText) findViewById(R.id.edTCSendOtp);
        tvCity=findViewById(R.id.tvCity);
        tvTcity=findViewById(R.id.tvTCity);
        ST_CITY.setVisibility(View.GONE);
        TC_CITY.setVisibility(View.GONE);
        tvTcity.setVisibility(View.VISIBLE);

        // INITIALIZE SELECTED CLASS ARRAY
        ST_CLS_LIST=new ArrayList<>();
        TC_CLS_LIST=new ArrayList<>();
        ST_SELECT_CLASS = new boolean[CLS_ARRAY.length];
        TC_SELECT_CLASS = new boolean[CLS_ARRAY.length];

        // INITIALIZE SELECTED CLASS ARRAY
        STATE_LIST=new ArrayList<>();
        CITY_LIST=new ArrayList<>();



        //sharedPreference
        preferences = context.getSharedPreferences("STUDENT_DETAILS", MODE_PRIVATE);
        getPreference = context.getSharedPreferences("TEACHER_DETAILS", MODE_PRIVATE);


        ST_CLASS.setOnClickListener(v -> showStudentClassList());
        TC_CLASS.setOnClickListener(v -> showTeacherClassList());

        STATE_LIST.add(0,"Select State");
        CITY_LIST.add(0,"Select City");

        STSelectState();
        // Get the ViewModel.
        if (NetworkUtil.getConnectivityStatus(Registration.this) > 0) {
            System.out.println("Connect");
            Network_Status = "Connect";

            Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
            tvCity.setOnClickListener(v -> {
                Toast.makeText(context, "Please Select state", Toast.LENGTH_SHORT).show();
            });
            tvTcity.setOnClickListener(v -> {
                Toast.makeText(context, "Please Select state", Toast.LENGTH_SHORT).show();
            });

            InitView();


        }
        else {
            dialogShowNoInternet();
        }

    }

    private void InitClicks() {


    }


    private void dialogShowNoInternet() {

 new AlertDialog.Builder(this)
                .setIcon(R.drawable.no_internet)
                .setTitle("No Internet")
                .setMessage("Please on your mobile data or wifi")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
            startActivity(new Intent(Registration.this,Registration.class));
                        Toast.makeText(context, "No connection", Toast.LENGTH_SHORT).show();
                    }

                })
                .show();
    }

    private void InitView() {


        //BY DEFAULT STUDENT RADIO WILL BE CHECKED
        Student.setChecked(true);


        llShareDetails.setVisibility(Teacher.isChecked() ? View.VISIBLE : View.GONE);

        Student.setOnClickListener(v -> {
            Student.setChecked(true);
            Teacher.setChecked(false);
            clearTeacherField();
            student_Fuul_layout.setVisibility(View.VISIBLE);
            Tchers_full_layout.setVisibility(View.GONE);
            TC_OTP_CLICKED=false;
            ST_ENTER_OTP.setVisibility(View.GONE);
            ST_DIS_OTP.setVisibility(View.GONE);
            ST_SEND_OTP.setVisibility(View.VISIBLE);

            llShareDetails.setVisibility(Teacher.isChecked() ? View.VISIBLE : View.GONE);
        });

        //region "Teacher"
        Teacher.setOnClickListener(v -> {
            Student.setChecked(false);
            Teacher.setChecked(true);
            clearStudentField();
            Tchers_full_layout.setVisibility(View.VISIBLE);
            student_Fuul_layout.setVisibility(View.GONE);
            TC_OTP_CLICKED=false;
            TC_ENTER_OTP.getText().clear();
            TC_ENTER_OTP.setVisibility(View.GONE);
            TC_SEND_OTP.setVisibility(View.VISIBLE);
            TC_DIS_OTP.setVisibility(View.GONE);
            llShareDetails.setVisibility(Teacher.isChecked() ? View.VISIBLE : View.GONE);
        });//endregion


        //region "teacher_register"
        loginbuttonTecher.setOnClickListener(v ->{// loginbuttonTecherOnClick(); //OLD CODE @KAJAL
//           NEW CODE
            Teacher_ValidateOnClick();
        });
//      endregion

        //region "student_register"
        Studentlogin.setOnClickListener(v ->{// StudentloginOnClick()); //OLD CODE @KAJAL
//         NEW CODE
            Student_ValidateOnClick();

// endregion


        });
        txt_share_details.setOnClickListener(view -> {
            Intent intent = new Intent(Registration.this, ShareDetailsActivity.class);
            startActivity(intent);
        });

        //SET STATE AND CITY LIST ON OTHER THREAD
        executeTask();
        ST_SEND_OTP.setOnClickListener(v -> {
            if(StEmail.getText().toString().isEmpty()){
                ST_OTP_CLICKED=false;
                Toast.makeText(context, "Enter Email ", Toast.LENGTH_SHORT).show();

                //StEmail.setBackground(getResources().getDrawable(R.drawable.roundloginbutton_redboarder));
            }else if(!isValidEmail(StEmail.getText().toString())) {
                ST_OTP_CLICKED=false;
             //   StEmail.setError("Incorrect Format of Email");
                Toast.makeText(context, "Invalid Email", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "invalid mail");

            }else {
                //EmailValidationCheck();

                sendOtpToStudent();
                ST_OTP_CLICKED=true;

            }

        });
        //The button will remain disable until the OTP is not verified
        ST_DIS_OTP.setOnClickListener(v -> sendOtpToStudent());
        //TC Send Verify OTP
        TC_SEND_OTP.setOnClickListener(v -> {
            if(TCEmails.getText().toString().isEmpty()){
                TC_OTP_CLICKED=false;
                //TCEmails.setError("Please Fill Valid Email");
                Toast.makeText(context, "Enter Email First", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "invalid mail");
            }else if(!isValidEmail(TCEmails.getText().toString())) {
               // TCEmails.setError("Please Fill Valid Email");
                Toast.makeText(context, "Enter Invalid Email", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "invalid mail");
                TC_OTP_CLICKED=false;
            }
            else {
                TC_OTP_CLICKED=true;
                TC_ENTER_OTP.setVisibility(View.VISIBLE);
                TC_DIS_OTP.setVisibility(View.VISIBLE);
                TC_SEND_OTP.setVisibility(View.GONE);
                sendOtpToTeacher();
            }});

        TC_DIS_OTP.setOnClickListener(v -> sendOtpToTeacher());
        AccessCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.length()>0&&s.length()<10){
                    AccessCode.setError("invalide Access code");
                }

            }
        });

        AccessCodeTeacher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.length()>0&&s.length()<10){
                    AccessCodeTeacher.setError("invalide Access code");
                }
            }
        });

        StContact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()<10&&s.length()>0){
                    StContact.setError("Please Enter 10 Digit Mobile");
                }
            }
        });
        TCPhonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()<10&&s.length()>0){
                    TCPhonenumber.setError("Please Enter 10 Digit Mobile");

                }
            }
        });

        StEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(StEmail.getText().toString().length()>0){
                    // Toast.makeText(context, "email length"+StEmail.getText().toString().length(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
               if(!isValidEmail(StEmail.getText().toString())&&StEmail.getText().toString().length()>0){
                   StEmail.setError("invalid email");
               }else{

               }
            }
        });
        TCEmails.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TCEmails.getText().toString().length()>0){
                    // Toast.makeText(context, "email length"+StEmail.getText().toString().length(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
               if(!isValidEmail(TCEmails.getText().toString())&&TCEmails.getText().toString().length()>0){
                   TCEmails.setError("invalid email");
               }
            }
        });

        TCPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0) {
                    if (!isValidPassword(TCPassword.getText().toString())) {
                        PassInvalidTCTv.setVisibility(View.VISIBLE);
                    } else {
                        PassInvalidTCTv.setVisibility(View.GONE);
                    }
                }else{

                    PassInvalidTCTv.setVisibility(View.GONE);
                }
            }
        });

        stPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0) {
                    if(!isValidPassword(stPassword.getText().toString())){
                        PassInvalidTv.setVisibility(View.VISIBLE);
                    }else{
                        PassInvalidTv.setVisibility(View.GONE);
                    }
                }else{

                    PassInvalidTv.setVisibility(View.GONE);
                }

            }
        });
    }

    private void studentReg() {

        Log.w(TAG,"CurrentTime: "+System.currentTimeMillis());
        try {
            Log.w(TAG, "OTP:" +SMS_OTP + " EdOTP:" + ST_ENTER_OTP.getText().toString());

            if (NetworkUtil.getConnectivityStatus(Registration.this) > 0) {
                System.out.println("Connect");
                Network_Status = "Connect";

               // Student_ValidateOnClick(registrationVerify);


                if(StEmail.getText().toString().equals("")&&stPassword.getText().toString().equals("")&&StName.getText().toString().equals("")&&
                        StContact.getText().toString().equals("") || StContact.getText().length() < 10&&StSchoolName.getText().toString().equals("")&&
                        ST_STATE.getSelectedItem().toString().equals("")&&ST_CITY.getSelectedItem().toString().isEmpty()){

                    Toast.makeText(context, "Please Enter Details... First", Toast.LENGTH_SHORT).show();
                    // TextBox.setVisibility(View.VISIBLE);
                }

                else {
                    Log.w(TAG,"AC="+ST_ACCESS_CODE);
                    if (System.currentTimeMillis() >OTP_VALID_TILL&&ST_OTP_CLICKED) {

                        Toast.makeText(context, "TimeOut! Resend OTP", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "TimeOut");

                        //layout Gone
                        ST_OTP_CLICKED=false;
                        ST_ENTER_OTP.setVisibility(View.GONE);
                        ST_DIS_OTP.setVisibility(View.GONE);
                        ST_SEND_OTP.setVisibility(View.VISIBLE);

                    }
                     if (StName.getText().toString().equals("")) {
                        StName.setError("Please Fill Name");
                        Toast.makeText(context, "Enter Name", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "Empty Name");

                    }else if (StContact.getText().toString().equals("") || StContact.getText().length() < 10) {
                    StContact.setError("10 digit mobile");
                    Toast.makeText(context, "Please fill contact detail", Toast.LENGTH_SHORT).show();
                    Log.w(TAG, "Empty Contact");

                    }
                    else if (stPassword.getText().toString().equals("")) {
                         stPassword.setError("Please Fill Password");
                         Toast.makeText(context, "Enter Password", Toast.LENGTH_SHORT).show();
                         Log.w(TAG, "Empty password");
                     }
                     else if (StSchoolName.getText().toString().equals("")) {
                         StSchoolName.setError("Please Fill School Name");
                         Toast.makeText(context, "Enter School Name", Toast.LENGTH_SHORT).show();
                         Log.w(TAG, "Empty SchoolName");

                     } else if(ST_STATE.getSelectedItem().toString().equals(STATE_LIST.get(0))){
                        Toast.makeText(context, "Select State", Toast.LENGTH_SHORT).show();
                    }
                    else if(ST_CITY.getSelectedItem().toString().equals(CITY_LIST.get(0))){
                        Toast.makeText(context, "Select city", Toast.LENGTH_SHORT).show();
                    }else if(ST_CLASS.getText().toString().isEmpty()){
                        Toast.makeText(context, "Please Select City", Toast.LENGTH_SHORT).show();
                    }else if (StEmail.getText().toString().isEmpty()) {
                        //StEmail.setError("Please Fill Email");
                        Toast.makeText(context, "Please Fill Email", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "Empty email");
                    }
                     else if (!isValidEmail(StEmail.getText().toString())) {
                         // StEmail.setError("InValid Email");
                         Toast.makeText(context, " Invalid Email", Toast.LENGTH_SHORT).show();
                         Log.w(TAG, "invalid mail");
                         ST_OTP_CLICKED=false;
                         ST_ENTER_OTP.setVisibility(View.GONE);
                         ST_DIS_OTP.setVisibility(View.GONE);
                         ST_SEND_OTP.setVisibility(View.VISIBLE);

                     } else if(!ST_OTP_CLICKED){
                        Toast.makeText(context, "Verify Email first", Toast.LENGTH_SHORT).show();
                    } else if(ST_ENTER_OTP.getText().toString().isEmpty()){
                        Toast.makeText(context, "Please Fill OTP", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "Enter OTP");

                    }
                    else {
                        if(SMS_OTP.equals(ST_ENTER_OTP.getText().toString())) {


                            //Set Otp button enable to resend OTP
                            TC_OTP_CLICKED=false;
                            ST_ENTER_OTP.setVisibility(View.GONE);
                            ST_DIS_OTP.setVisibility(View.GONE);
                            ST_SEND_OTP.setVisibility(View.VISIBLE);

                            Log.w(TAG, "OTP:" +SMS_OTP+ " EdOTP:" +ST_SEND_OTP.getText().toString());


                            //Store Student Details in preference
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("Name", StName.getText().toString());
                           // Toast.makeText(context, "StName" + StName, Toast.LENGTH_SHORT).show();


                            if(AccessCode.getText().toString().isEmpty()){
                                editor.putString("AccessCode","0");
                            }else{
                                editor.putString("AccessCode",AccessCode.getText().toString());
                            }
                            editor.putString("Email", StEmail.getText().toString());
                            editor.putString("Password", stPassword.getText().toString());
                            editor.putString("Mobile", StContact.getText().toString());
                            editor.putString("Address", StAddress.getText().toString());
                            editor.putString("School_name", StSchoolName.getText().toString());
                            editor.putString("State",ST_STATE.getSelectedItem().toString());
                            editor.putString("City", ST_CITY.getSelectedItem().toString());
                            editor.putString("Class",ST_CLASS.getText().toString());
                            editor.apply();

                            Toast.makeText(context, ""+check, Toast.LENGTH_SHORT).show();

                            if(AccessCode.getText().toString().isEmpty()){
                                Toast.makeText(context, "Empty Access Code", Toast.LENGTH_SHORT).show();
                                //   ST_ACCESS_CODE="0";
                                AccessCode.getText().clear();
                                ST_ACCESS_CODE=AccessCode.getText().toString();
                            }
                            progressDialog = new ProgressDialog(Registration.this);
                            progressDialog.setMessage("Loading..."); // Setting Title
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressDialog.show();
                            progressDialog.setCancelable(false);
                            //String url = "https://test.brainmate.co.in/studybuddy/api_ver_2/register.php"; //change @kajal
                            String url = Apis.new_base_url + Apis.register_url;
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            progressDialog.dismiss();
                                            Log.w(TAG, "St Reg response->"+response);

                                            // Here for already registered user success value is 2
                                            try {
                                              //  Toast.makeText(context, "sccussful"+response, Toast.LENGTH_SHORT).show();


                                                JSONObject jsonObject1 = new JSONObject(response);
                                                String msg= jsonObject1.getString("msg");
                                                String RESPONSE_CODE = jsonObject1.getString("error");

                                                Log.w(TAG, " msg:" +msg+ " TC code:" +RESPONSE_CODE);

                                                if (RESPONSE_CODE.equals(400)) {
                                                    Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();

                                                }else {
                                                    ST_Id= jsonObject1.getString("student_id");

                                                    Intent intent = new Intent(Registration.this, Stu_Classes.class);
                                                    if(AccessCode.getText().toString().isEmpty()){
                                                        intent.putExtra("accesscodes","123");
                                                        CommonMethods.saveAccessCode(Registration.this,"123");

                                                    }else{
                                                        intent.putExtra("accesscodes", ST_ACCESS_CODE);
                                                        CommonMethods.saveAccessCode(Registration.this,AccessCode.getText().toString());

                                                    }

                                                    intent.putExtra("Student_ID", ST_Id);

                                                    Toast.makeText(context, msg+"student id="+ST_Id, Toast.LENGTH_SHORT).show();
                                                    clearTeacherField();
                                                    clearStudentField();
                                                    SharedPreferences.Editor editor1 = pref.edit();
                                                    editor1.putInt("IsLogin", 1);
                                                    editor1.apply();
                                                    CommonMethods.saveEmailId(Registration.this, StEmail.getText().toString());
                                                    CommonMethods.saveId(Registration.this, ST_Id);
                                                    Toast.makeText(context, "Thank you for registering with us!", Toast.LENGTH_SHORT).show();
                                                    startActivity(intent);
                                                    finishAffinity();

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
                                    Log.w(TAG, "TeacherLogin Registration error:" + error.getMessage());
                                    System.out.println("ResponseRegistration" + error.getMessage());
                                    Toast.makeText(context, "failed" + error.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }) {

                                @Override
                                protected java.util.Map<String, String> getParams() throws AuthFailureError {
                                    java.util.Map<String, String> params = new HashMap<>();

                                   params.put("email", StEmail.getText().toString());
                                    Log.w(TAG," ST Email:"+StEmail.getText().toString());
                                    Log.w(TAG," ST p:"+stPassword.getText().toString());
                                    Log.w(TAG," ST N:"+ StName.getText().toString());
                                    Log.w(TAG," ST Contact:"+ StContact.getText().toString());
                                    Log.w(TAG," ST SN:"+StSchoolName.getText().toString());
                                    Log.w(TAG," ST SAC:"+ST_ACCESS_CODE);
                                    Log.w(TAG," ST State:"+ST_STATE.getSelectedItem().toString());
                                    Log.w(TAG," ST Address:"+StAddress.getText().toString());
                                    Log.w(TAG," ST city:"+ST_CITY.getSelectedItem().toString());
                                    Log.w(TAG," ST class:"+ST_CLASS.getText().toString());
                                   params.put("password", stPassword.getText().toString());
                                   params.put("name", StName.getText().toString());
                                   params.put("mobile", StContact.getText().toString());
                                   params.put("school_name", StSchoolName.getText().toString());
                                   params.put("school_addess","0");

                                   params.put("school_phone","0");
                                   params.put("accesscode",ST_ACCESS_CODE);
                                   params.put("address",StAddress.getText().toString());

                                   params.put("state_id",ST_STATE.getSelectedItem().toString());
                                   params.put("city_id",ST_CITY.getSelectedItem().toString());
                                   params.put("classes",ST_CLASS.getText().toString());

                                   params.put("subject","0");
                                   params.put("type", "1");

                                   return params;
                                }
                            };
                            CommonMethods.callWebserviceForResponse(stringRequest, context);


                        }
                        else {
                            Log.w(TAG, "invalid Otp");
                            ST_OTP_CLICKED=true;
                            ST_ENTER_OTP.requestFocus();
                            Toast.makeText(context, "InValid OTP", Toast.LENGTH_SHORT).show();
                        }//@kajal
                    }
                }}else {
                System.out.println("No connection");
                Network_Status = "notConnect";
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Registration.this);
                alertDialogBuilder.setMessage(getString(R.string.no_internet));
                alertDialogBuilder.setPositiveButton(getString(R.string.retry),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int arg1) {
                                if (NetworkUtil.getConnectivityStatus(Registration.this) > 0) {
                                    Toast.makeText(Registration.this, "No Connection", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    studentReg();
                                }
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

        }catch (Exception ee){}



    }



    private void teacherReg() {

        try{
            if (NetworkUtil.getConnectivityStatus(Registration.this) > 0) {
                System.out.println("Connect");
                Network_Status = "Connect";

                if(TCPassword.getText().toString().equals("")&&TCName.getText().toString().equals("")&& TCPhonenumber.getText().toString().isEmpty() && TCPhonenumber.getText().toString().length() < 10&& TCEmails.getText().toString().equals("")&&TCSchholName.getText().toString().equals("")&& TC_ENTER_OTP.getText().toString().isEmpty()&&TCSubject.getText().toString().isEmpty()){
                    Toast.makeText(context, "Please Enter Details... First", Toast.LENGTH_SHORT).show();
                }else{
                    if(System.currentTimeMillis()>OTP_VALID_TILL&&TC_OTP_CLICKED){
                        TC_OTP_CLICKED=false;
                        TC_ENTER_OTP.getText().clear();
                        TC_ENTER_OTP.setVisibility(View.GONE);
                        TC_SEND_OTP.setVisibility(View.VISIBLE);
                        TC_DIS_OTP.setVisibility(View.GONE);
                        Log.w(TAG, " TC TimeOut");

                        Toast.makeText(context, "TimeOut! Resend OTP", Toast.LENGTH_SHORT).show();
                    }
                    else if (TCName.getText().toString().equals("")) {
                        TCName.setError("Please Enter Name");
                        Log.w(TAG, "Empty TC Name");
                        Toast.makeText(context, "Enter name", Toast.LENGTH_SHORT).show();

                    }
                    else if (TCPhonenumber.getText().toString().isEmpty() && TCPhonenumber.getText().toString().length() < 10) {
                        TCPhonenumber.setError("Please Fill valid Mobile Number");
                        Log.w(TAG, "Enter TC 10 digit number");
                        Toast.makeText(context, "Enter phone", Toast.LENGTH_SHORT).show();

                    }
                    else if (TCPassword.getText().toString().equals("")) {
                        TCPassword.setError("Please Enter Password");
                        Log.w(TAG, "Empty TC Password");
                        Toast.makeText(context, "Enter password", Toast.LENGTH_SHORT).show();


                    }else if(TC_CLASS.getText().toString().equals("Select Class")){
                        Toast.makeText(context, "Please Select City", Toast.LENGTH_SHORT).show();
                    }

                    else if (TCSchholName.getText().toString().equals(""))
                    {
                        Toast.makeText(context, "Enter SchoolName", Toast.LENGTH_SHORT).show();
                        TCSchholName.setError("Please Fill School Name");
                        Log.w(TAG, "Empty TC SchoolName");
                    }
                    else if (TCSchholAddress.getText().toString().equals(""))
                    {
                        Toast.makeText(context, "Enter school address", Toast.LENGTH_SHORT).show();
                        TCSchholAddress.setError("Please Fill School address");
                        Log.w(TAG, "Empty  Tc School Address");
                    }
                    else if (TCSchholPhoneNumber.getText().toString().equals(""))
                    {
                        Toast.makeText(context, "Enter school phone", Toast.LENGTH_SHORT).show();

                        TCSchholPhoneNumber.setError("Please Fill School Number");
                        Log.w(TAG, "Empty TC PHone");
                    }
                    else if (!isValidMobile(TCSchholPhoneNumber.getText().toString()))
                    {
                        Toast.makeText(context, "invalid phone", Toast.LENGTH_SHORT).show();

                        TCSchholPhoneNumber.setError("Please Fill valid Phone Number");
                        Log.w(TAG, "Invalid TC Phone");
                    }
                    else if(TC_STATE.getSelectedItem().toString().equals(STATE_LIST.get(0))){
                        Toast.makeText(context, "Select State", Toast.LENGTH_SHORT).show();
                    }
                    else if(TC_CITY.getSelectedItem().toString().equals(CITY_LIST.get(0))){
                        Toast.makeText(context, "Select city", Toast.LENGTH_SHORT).show();
                    }else if(TC_CLASS.getText().toString().isEmpty()){
                        Toast.makeText(context, "Select class", Toast.LENGTH_SHORT).show();
                    }else if(TCSubject.getText().toString().isEmpty())
                    {
                        Toast.makeText(context, "Enter Subject", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "Empty TC Subject");
                        TCSubject.setError("Enter  TC Subject");
                    }
                    else if(TCEmails.getText().toString().isEmpty()){
                        // TCEmails.setError("Please Fill Valid Email");
                        Toast.makeText(context, "Enter Email First", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "Enter mail");
                    }else if(!isValidEmail(TCEmails.getText().toString())) {
                        // TCEmails.setError("Please Fill Valid Email");
                        Toast.makeText(context, "Enter Invalid Email", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "invalid mail");

                    }else if(!TC_OTP_CLICKED){
                        Toast.makeText(context, "Verify Email First(SendOTP)", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if(TC_ENTER_OTP.getText().toString().isEmpty()){
                            Toast.makeText(context, "ENTER OTP FIRST...", Toast.LENGTH_SHORT).show();
                            TC_ENTER_OTP.setError("Enter OTP");
                            Log.w(TAG, "Empty TC OTP Text Box"); }
                        else{
                            if(SMS_OTP.equals(TC_ENTER_OTP.getText().toString())) {

                                Teacher_ValidateOnClick();
                                //VERIFY OTP SUCCESSFULLY GONE OTP LAYOUT
                                TC_OTP_CLICKED=false;
                                TC_ENTER_OTP.getText().clear();
                                TC_ENTER_OTP.setVisibility(View.GONE);
                                TC_SEND_OTP.setVisibility(View.VISIBLE);
                                TC_DIS_OTP.setVisibility(View.GONE);

                                //Store Teacher Details in preference
                                SharedPreferences.Editor editor = getPreference.edit();
                                editor.putString("TName", TCName.getText().toString());

                                editor.putString("TEmail", TCEmails.getText().toString());
                                editor.putString("TPassword", TCPassword.getText().toString());
                                editor.putString("TMobile", TCPhonenumber.getText().toString());
                                editor.putString("TAddress", TCAddress.getText().toString());
                                editor.putString("TSchool_name", TCSchholName.getText().toString());
                                editor.putString("TState", TC_STATE.getSelectedItem().toString());
                                editor.putString("TCity", TC_CITY.getSelectedItem().toString());
                                editor.putString("TClass", TCName.getText().toString());
                                editor.putString("TSubject", TCSubject.getText().toString());
                                editor.putString("TSchool_ph", TCSchholPhoneNumber.getText().toString());
                                editor.putString("TSchool_address", TCSchholAddress.getText().toString());
                                editor.apply();

                                progressDialog = new ProgressDialog(Registration.this);
                                progressDialog.setMessage("Loading..."); // Setting Title
                                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                progressDialog.show();
                                progressDialog.setCancelable(false);
                                //String url = "https://test.brainmate.co.in/studybuddy/api_ver_2/register.php"; //change @kajal
                                 String url = Apis.new_base_url + Apis.register_url;
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                progressDialog.dismiss();

                                                // Here for already registered user success value is 2
                                                try {
                                                    Log.w(TAG, "response"+response);
                                                    Log.w(TAG, " TC OTP:" +SMS_OTP+ " TC EdOTP:" +TC_ENTER_OTP.getText().toString());

                                                    JSONObject jsonObject1 = new JSONObject(response);
                                                    String msg= jsonObject1.getString("msg");
                                                    String RESPONSE_CODE = jsonObject1.getString("error");
                                                    String TC_Id = jsonObject1.getString("teacher_id");
                                                    Log.w(TAG, " msg:" +msg+ " TC code:" +RESPONSE_CODE);

                                                    if (RESPONSE_CODE.equals(400)) {
                                                        Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();

                                                    }
                                                   else{

                                                        Intent intent = new Intent(Registration.this,Main2Activity.class);
                                                        intent.putExtra("Type", "2");
                                                        intent.putExtra("Id", TC_Id);
                                                        intent.putExtra("EntryType", "Reg");
                                                        intent.putExtra("accesscodes", TC_ACCESS_CODE);
                                                        intent.putExtra("Email",TCEmails.getText().toString());
                                                        intent.putExtra("Password",TCPassword.getText().toString());
                                                        startActivity(intent);
                                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                                        clearTeacherField();
                                                        clearStudentField();
                                                        SharedPreferences.Editor editor1= pref.edit();
                                                        editor1.putInt("IsLogin",2);
                                                        editor1.apply();
                                                        CommonMethods.saveEmailId(Registration.this,TCEmails.getText().toString());
                                                        CommonMethods.saveId(Registration.this,TC_Id);
                                                        CommonMethods.saveAccessCode(Registration.this,TC_ACCESS_CODE);



                                                        //finish();
                                                        finishAffinity();

                                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                                                        clearTeacherField();
                                                        clearStudentField();
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
                                        params.put("email", TCEmails.getText().toString());
                                        params.put("password",TCPassword.getText().toString());
                                        params.put("name", TCName.getText().toString());

                                        params.put("mobile",TCPhonenumber.getText().toString());
                                        params.put("school_name",TCSchholName.getText().toString());
                                        params.put("school_addess",TCSchholAddress.getText().toString());

                                        params.put("school_phone",TCSchholPhoneNumber.getText().toString());
                                        params.put("accesscode",AccessCodeTeacher.getText().toString());
                                        params.put("address",TCAddress.getText().toString());

                                        params.put("state_id",TC_STATE.getSelectedItem().toString());
                                        params.put("city_id",TC_CITY.getSelectedItem().toString());
                                        params.put("classes",TC_CLASS.getText().toString());

                                        params.put("subject",TCSubject.getText().toString());
                                        params.put("type", "2");
                                        Log.w(TAG,"map para:"+params);



                                        return params;
                                    }
                                };
                                CommonMethods.callWebserviceForResponse(stringRequest, context);

                            }
                            else {
                                Log.w(TAG, "invalid  TC Otp");
                                Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show();
                                   }

                        }
                    }
                }}
            else {
                System.out.println("No connection");
                Network_Status = "notConnect";
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Registration.this);
                alertDialogBuilder.setMessage(getString(R.string.no_internet));
                alertDialogBuilder.setPositiveButton(getString(R.string.retry),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int arg1) {
                                if (NetworkUtil.getConnectivityStatus(Registration.this) > 0) {
                                    Toast.makeText(Registration.this, "No Connection", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    teacherReg();
                                }
                            }
                        });


                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

        }catch (Exception ee){
        }


    }

    void clearStudentField(){

        StEmail.getText().clear();
        stPassword.getText().clear();
        StName.getText().clear();
        AccessCode.getText().clear();
        StContact.getText().clear();
       // StAddress.getText().clear();
        StSchoolName.getText().clear();
        ST_ENTER_OTP.getText().clear();
        ST_CLASS.setText("Select Class");
        STSelectState();


    }
    void clearTeacherField(){
        TCName.getText().clear();
        AccessCodeTeacher.getText().clear();
        TCEmails.getText().clear();
        TCPhonenumber.getText().clear();
        TCPassword.getText().clear();
        TCAddress.getText().clear();
        TCSchholAddress.getText().clear();
        TCSubject.getText().clear();
        TCSchholName.getText().clear();
        TCSchholPhoneNumber.getText().clear();
        TC_ENTER_OTP.getText().clear();
        TC_CLASS.setText("Select Class");
        STSelectState();
    }
    private void showStudentClassList() {
        // INITIALIZE ALERT DIALOG
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //SET TITLE
        builder.setTitle("Select Class");

        // SET DIALOG NON CANCELABLE
        builder.setCancelable(false);

        builder.setMultiChoiceItems(CLS_ARRAY, ST_SELECT_CLASS, (dialogInterface, i, b) -> {
            // CHECK CONDITION
            if (b) {
                // WHEN CHECKBOX SELECTED
                // ADD POSITION IN CLASS LIST
                ST_CLS_LIST.add(i);
                //Log.w(TAG, "MultiChoiceItem Registration ST_List :Add=" + ST_CLS_LIST.add(i));
                // Sort array list
                Collections.sort(ST_CLS_LIST);
            } else {
                // WHEN CHECKBOX UNSELECTED
                //REMOVE POSITION FROM CLASS LIST
                ST_CLS_LIST.remove(Integer.valueOf(i));
                Log.w(TAG, "MultiChoiceItem Registration  ST_List :Remove=" + ST_CLS_LIST.remove(Integer.valueOf(i)));

            }
        });

        builder.setPositiveButton("OK", (dialogInterface, i) -> {

            // INITIALIZE STRING BUILDER
            StringBuilder stringBuilder = new StringBuilder();
            // USE FOR LOOP
            for (int j = 0; j < ST_CLS_LIST.size(); j++) {
                // concat array value
                stringBuilder.append(CLS_ARRAY[ST_CLS_LIST.get(j)]);
               // Log.w(TAG, "MultiChoiceItem Registration  ST_List: position=" + ST_CLS_LIST.get(j) + " ; value:" + CLS_ARRAY[ST_CLS_LIST.get(j)]);

                // check condition
                if (j != ST_CLS_LIST.size() - 1) {
                    // WHEN J VALUE NOT EQUAL
                    // TO LANG LIST SIZE -1
                    // ADD COMMA
                    stringBuilder.append(",");
                    Log.w(TAG, "MultiChoiceItem Registration  ADD COMMA");

                }
            }
            // SET TEXT ON TEXT VIEW
            ST_CLASS.setText(stringBuilder.toString());
        });
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
            // DISMISS DIALOG
            dialogInterface.dismiss();
        });
        builder.setNeutralButton("Clear All", (dialogInterface, i) -> {
            // UDE FOR LOOP
            for (int j = 0; j < ST_SELECT_CLASS.length; j++) {
                //REMOVE ALL SELECTION
                ST_SELECT_CLASS[j] = false;
                // CLEAR CLASS LIST
                ST_CLS_LIST.clear();
                //CLEAR TEXT VIEW
                ST_CLASS.setText("");
            }
        });
        //SHOW DIALOG
        builder.show();



    }


    private void showTeacherClassList() {

        // INITIALIZE ALERT DIALOG
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //SET TITLE
        builder.setTitle("Select Language");

        // SET DIALOG NON CANCELABLE
        builder.setCancelable(false);

        builder.setMultiChoiceItems(CLS_ARRAY, TC_SELECT_CLASS, (dialogInterface, i, b) -> {
            // CHECK CONDITION
            if (b) {
                // WHEN CHECKBOX SELECTED
                // ADD POSITION IN CLASS LIST
                TC_CLS_LIST.add(i);
                //Log.w(TAG, "MultiChoiceItem Registration ST_List :Add=" + TC_CLS_LIST.add(i));
                // Sort array list
                Collections.sort(TC_CLS_LIST);
            } else {
                // WHEN CHECKBOX UNSELECTED
                //REMOVE POSITION FROM CLASS LIST
                TC_CLS_LIST.remove(Integer.valueOf(i));
                Log.w(TAG, "MultiChoiceItem Registration  ST_List :Remove=" + TC_CLS_LIST.remove(Integer.valueOf(i)));

            }
        });

        builder.setPositiveButton("OK", (dialogInterface, i) -> {

            // INITIALIZE STRING BUILDER
            StringBuilder stringBuilder = new StringBuilder();
            // USE FOR LOOP
            for (int j = 0; j < TC_CLS_LIST.size(); j++) {
                // concat array value
                stringBuilder.append(CLS_ARRAY[TC_CLS_LIST.get(j)]);
                Log.w(TAG, "MultiChoiceItem Registration  ST_List: position=" + TC_CLS_LIST.get(j) + " ; value:" + CLS_ARRAY[TC_CLS_LIST.get(j)]);

                // check condition
                if (j != TC_CLS_LIST.size() - 1) {
                    // WHEN J VALUE NOT EQUAL
                    // TO LANG LIST SIZE -1
                    // ADD COMMA
                    stringBuilder.append(",");
                    Log.w(TAG, "MultiChoiceItem Registration  ADD COMMA");

                }
            }
            // SET TEXT ON TEXT VIEW
            TC_CLASS.setText(stringBuilder.toString());
        });
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
            // DISMISS DIALOG
            dialogInterface.dismiss();
        });
        builder.setNeutralButton("Clear All", (dialogInterface, i) -> {
            // UDE FOR LOOP
            for (int j = 0; j < TC_SELECT_CLASS.length; j++) {
                //REMOVE ALL SELECTION
                TC_SELECT_CLASS[j] = false;
                // CLEAR CLASS LIST
                TC_CLS_LIST.clear();
                //CLEAR TEXT VIEW
                TC_CLASS.setText("");
            }
        });
        //SHOW DIALOG
        builder.show();

    }
    private void executeTask() {
        mExecutor.execute(() -> {


            //spinner set City

        });

    }
    private void sendOtpToStudent() {
        if (NetworkUtil.getConnectivityStatus(Registration.this) > 0) {
            System.out.println("Connect");
            Network_Status = "Connect";

            OTP_VALID_TILL = System.currentTimeMillis() + (15*60*1000);

            Log.w(TAG, "Time Start: " + System.currentTimeMillis());
            Log.w(TAG, "OTp valid till: " + OTP_VALID_TILL);
            progressDialog = new ProgressDialog(Registration.this);
            progressDialog.setMessage("Loading..."); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            progressDialog.setCancelable(false);

            //String url = "http://www.techive.in/studybuddy/api/teacher_access.php";
            String url = Apis.new_base_url + Apis.send_otp;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onResponse(String response) {
                            Log.d("SendOtp response:", response);
                            progressDialog.dismiss();
                            try
                            //TODO : success = 2 Invalid username or password correct access code
                            {
                                JSONObject jsonObject1 = new JSONObject(response);
                                try{

                                    String msg = jsonObject1.getString("msg");
                                    String code = jsonObject1.getString("error");
                                    if(Integer.parseInt(code)==400){
                                        Toast.makeText(context,  msg, Toast.LENGTH_SHORT).show();

                                            ST_ENTER_OTP.setVisibility(View.GONE);
                                            ST_DIS_OTP.setVisibility(View.GONE);
                                            ST_SEND_OTP.setVisibility(View.VISIBLE);
                                            ST_ENTER_OTP.requestFocus();

                                    }else{
                                        Toast.makeText(context,  msg, Toast.LENGTH_SHORT).show();

                                            ST_ENTER_OTP.setVisibility(View.VISIBLE);
                                            ST_DIS_OTP.setVisibility(View.VISIBLE);
                                            ST_SEND_OTP.setVisibility(View.GONE);
                                            ST_ENTER_OTP.requestFocus();
                                        SMS_OTP = jsonObject1.getString("otp");
                                        Log.w(TAG,"OTP:"+SMS_OTP);
                                        Toast.makeText(context, "otp"+SMS_OTP, Toast.LENGTH_SHORT).show();



                                    }

                                }catch (Exception ee){}




                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    System.out.println("ResponseRegistration" + error.getMessage());
                    Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected java.util.Map<String, String> getParams() throws AuthFailureError {
                    java.util.Map<String, String> params = new HashMap<>();
                    params.put("email",StEmail.getText().toString());
                    return params;
                }
            };

            CommonMethods.callWebserviceForResponse(stringRequest, context);
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
                                Toast.makeText(Registration.this, "No Connection", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                              sendOtpToStudent();
                            }
                        }
                    });


            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

    }
  private void sendOtpToTeacher() {
        if (NetworkUtil.getConnectivityStatus(Registration.this) > 0) {
            System.out.println("Connect");
            Network_Status = "Connect";

            OTP_VALID_TILL = System.currentTimeMillis() + (15*60*1000);

            Log.w(TAG, "Time Start: " + System.currentTimeMillis());
            Log.w(TAG, "OTp valid till: " + OTP_VALID_TILL);
            progressDialog = new ProgressDialog(Registration.this);
            progressDialog.setMessage("Loading..."); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            progressDialog.setCancelable(false);

            //String url = "http://www.techive.in/studybuddy/api/teacher_access.php";
            String url = Apis.new_base_url + Apis.send_otp;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onResponse(String response) {
                            Log.d("SendOtp response:", response);
                            progressDialog.dismiss();
                            try
                            //TODO : success = 2 Invalid username or password correct access code
                            {
                                JSONObject jsonObject1 = new JSONObject(response);
                                try{

                                    String msg = jsonObject1.getString("msg");
                                    String code = jsonObject1.getString("error");
                                    if(Integer.parseInt(code)==400){
                                        Toast.makeText(context,  msg, Toast.LENGTH_SHORT).show();

                                            TC_ENTER_OTP.setVisibility(View.GONE);
                                            TC_DIS_OTP.setVisibility(View.GONE);
                                            TC_SEND_OTP.setVisibility(View.VISIBLE);
                                            TC_ENTER_OTP.requestFocus();

                                    }else{
                                        Toast.makeText(context,  msg, Toast.LENGTH_SHORT).show();

                                            TC_ENTER_OTP.setVisibility(View.VISIBLE);
                                            TC_DIS_OTP.setVisibility(View.VISIBLE);
                                            TC_SEND_OTP.setVisibility(View.GONE);
                                            TC_ENTER_OTP.requestFocus();
                                        SMS_OTP = jsonObject1.getString("otp");
                                        Log.w(TAG,"OTP:"+SMS_OTP);
                                        Toast.makeText(context, "otp"+SMS_OTP, Toast.LENGTH_SHORT).show();

                                    }

                                }catch (Exception ee){}




                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    System.out.println("ResponseRegistration" + error.getMessage());
                    Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected java.util.Map<String, String> getParams() throws AuthFailureError {
                    java.util.Map<String, String> params = new HashMap<>();
                    params.put("email",TCEmails.getText().toString());
                    return params;
                }
            };

            CommonMethods.callWebserviceForResponse(stringRequest, context);
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
                                Toast.makeText(Registration.this, "No internet Connection", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                              sendOtpToStudent();
                            }
                        }
                    });


            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

    }

    private void STSelectState() {
        if (NetworkUtil.getConnectivityStatus(Registration.this) > 0) {
            System.out.println("Connect");
            Network_Status = "Connect";

            //TODO
            //  String url = "https://test.brainmate.co.in/studybuddy/api_ver_2/allstate.php";
            String url = Apis.new_base_url + Apis.state_url;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onResponse(String response) {
                            Log.d("Login_response_teacher", response);
                            // progressDialog.dismiss();
                            Log.w(TAG, "StateName: Registration fun() API");

                            try
                            //TODO : success = 2 Invalid username or password correct access code
                            {
                                JSONObject jsonObject1 = new JSONObject(response);
                                JSONArray jsonArray = jsonObject1.getJSONArray("states");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String stateName = object.getString("name");

                                    stateId = object.getString("id");
                                    STATE_LIST.add(stateName);

                                    if (STATE_LIST.size() == jsonArray.length()) {

                                        arrayAdapterstate = new ArrayAdapter<>(Registration.this, R.layout.color_spinner_layout,STATE_LIST);
                                        arrayAdapterstate.setDropDownViewResource(R.layout.spinner_style);
                                        ST_STATE.setAdapter(arrayAdapterstate);
                                        TC_STATE.setAdapter(arrayAdapterstate);
                                    }

                                    ST_STATE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            statePos=position;
                                            STSelectCity(statePos); }
                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) { }
                                    });

                                    //State Selected id
                                    TC_STATE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            statePos=position;
                                            STSelectCity(statePos); }
                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) { }
                                    });

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("ResponseRegistration" + error.getMessage());
                }
            });
            CommonMethods.callWebserviceForResponse(stringRequest, context); } else {
            System.out.println("No connection");
            Network_Status = "notConnect";
           // Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
            /*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Registration.this);
            alertDialogBuilder.setMessage(getString(R.string.no_internet));
            alertDialogBuilder.setPositiveButton(getString(R.string.retry),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int arg1) {
                            if (NetworkUtil.getConnectivityStatus(Registration.this) > 0) {
                                Toast.makeText(Registration.this, "" + getString(R.string.connected), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                STSelectState();
                            }
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();*/
        }
    }

    private void STSelectCity(int sId) {

        Log.w(TAG," StateId="+ this.stateId);
        if (NetworkUtil.getConnectivityStatus(Registration.this) > 0) {
            System.out.println("Connect");
            Network_Status = "Connect";

                 if(sId==0){
                     ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(Registration.this, R.layout.color_spinner_layout,CITY_LIST);
                     arrayAdapter1.setDropDownViewResource(R.layout.spinner_style);
                     ST_CITY.setAdapter(arrayAdapter1);
                     TC_CITY.setAdapter(arrayAdapter1);
                     tvCity.setVisibility(View.VISIBLE);
                     tvTcity.setVisibility(View.VISIBLE);
                     ST_CITY.setVisibility(View.GONE);
                     TC_CITY.setVisibility(View.GONE);

                 }

                //TODO
                //String url = "https://test.brainmate.co.in/studybuddy/api_ver_2/allstate.php";
                String url = Apis.new_base_url + Apis.city_url;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @SuppressLint("ResourceAsColor")
                            @Override
                            public void onResponse(String response) {
                                Log.d("F", response);
                                //progressDialog.dismiss();
                                Log.w(TAG, "StateName: Registration fun() API");

                                try
                                //TODO : success = 2 Invalid username or password correct access code
                                {

                                    JSONObject jsonObject1 = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject1.getJSONArray("city_list");

                                    ArrayList<String>  CITY_LIST=new ArrayList<>();
                                    CITY_LIST.add(0,"select city");

                                    tvCity.setVisibility(View.GONE);
                                    ST_CITY.setVisibility(View.VISIBLE);
                                    tvTcity.setVisibility(View.GONE);
                                    TC_CITY.setVisibility(View.VISIBLE);

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        String cityId = object.getString("id");
                                        String cityName = object.getString("name");
                                        CITY_LIST.add(cityName);
                                        Log.w(TAG,"thread1"+i);
                                    }
                                    Log.w(TAG,"thread2");
                                    ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(Registration.this, R.layout.color_spinner_layout,CITY_LIST);
                                    arrayAdapter1.setDropDownViewResource(R.layout.spinner_style);
                                    ST_CITY.setAdapter(arrayAdapter1);
                                    TC_CITY.setAdapter(arrayAdapter1);

                                    Log.w(TAG, "StateName: Registration ");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("ResponseRegistration" + error.getMessage());
                    }
                }) {

                    @Override
                    protected java.util.Map<String, String> getParams() throws AuthFailureError {
                        java.util.Map<String, String> params = new HashMap<>();
                        params.put("state_id",String.valueOf(sId));
                        return params;
                    }
                };

                CommonMethods.callWebserviceForResponse(stringRequest, context);
            }


        else {
            System.out.println("No connection");
            Network_Status = "notConnect";
         /*   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Registration.this);
            alertDialogBuilder.setMessage(getString(R.string.no_internet));
            alertDialogBuilder.setPositiveButton(getString(R.string.retry),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int arg1) {
                            if (NetworkUtil.getConnectivityStatus(Registration.this) > 0) {
                                Toast.makeText(Registration.this, "" + getString(R.string.connected), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                STSelectCity();
                            }
                        }
                    });


            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();*/
        }

    }


    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    public static boolean isValidPassword(String pas) {
        boolean check = false;
        if (Pattern.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",pas)) {
            check=true;
        } else {
            check = false;
        }
        return check;
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
            Log.w(TAG," i am in student validation");

       /*//TODO : success = 1 valid access code
                progressDialog = new ProgressDialog(Registration.this);
                progressDialog.setMessage("Loading..."); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                progressDialog.setCancelable(false);
                //String url = "http://www.techive.in/studybuddy/api/student_access.php";*/
                String url = Apis.base_url + Apis.student_access_url;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @SuppressLint("ResourceAsColor")
                            @Override
                            public void onResponse(String response) {
                                Log.d("StudentAccess response", response);
                                //  progressDialog.dismiss();
                                try {
                                    Log.d("Status123", "3");
                                    JSONObject jsonObject1 = new JSONObject(response);
                                    String LoginCredential = jsonObject1.getString("success");

                                    if (LoginCredential.equals("0")) {

                                        Toast.makeText(context, "Invalid", Toast.LENGTH_SHORT).show();
                                        AccessCode.getText().clear();
                                        ST_ACCESS_CODE=AccessCode.getText().toString();
                                        studentReg();
                                        return;
                                    }
                                    ST_ACCESS_CODE=AccessCode.getText().toString();
                                    Toast.makeText(context, "valid AC", Toast.LENGTH_SHORT).show();
                                    studentReg();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //  progressDialog.dismiss();
                        System.out.println("ResponseRegistration" + volleyError.getMessage());
                     /*   if (volleyError instanceof NoConnectionError) {
                            Toast.makeText(Registration.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Registration.this, R.string.some_error_occurred, Toast.LENGTH_SHORT).show();
                        }*/
                    }
                }) {
                    @Override
                    protected java.util.Map<String, String> getParams() throws AuthFailureError {
                        java.util.Map<String, String> params = new HashMap<>();
                        Log.w(TAG,"Edit value"+AccessCode.getText().toString());
                        params.put("accesscodes",AccessCode.getText().toString());
                        return params;
                    }
                };

            Log.w(TAG,"out check="+check);
                //.add(stringRequest);
                CommonMethods.callWebserviceForResponse(stringRequest, context);
          //  }

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
                                Toast.makeText(Registration.this, "No Connection", Toast.LENGTH_SHORT).show();
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
    private void Teacher_ValidateOnClick() {
        if (NetworkUtil.getConnectivityStatus(Registration.this) > 0) {
            System.out.println("Connect");
            Network_Status = "Connect";

            if (AccessCodeTeacher.getText().toString().isEmpty()) {
               TC_ACCESS_CODE="0";
               // Toast.makeText(Registration.this, "Please fill Access Code", Toast.LENGTH_LONG).show();
            }
               /* //TODO : success = 1 valid access code
                progressDialog = new ProgressDialog(Registration.this);
                progressDialog.setMessage("Loading..."); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                progressDialog.setCancelable(false);*/
                //String url = "http://www.techive.in/studybuddy/api/student_access.php";
                String url = Apis.base_url + Apis.student_access_url;
                StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                        new Response.Listener<String>() {
                            @SuppressLint("ResourceAsColor")
                            @Override
                            public void onResponse(String response) {
                                Log.d("StudentAccess response", response);
                              //  progressDialog.dismiss();
                                try {
                                    Log.d("Status123", "3");
                                    JSONObject jsonObject1 = new JSONObject(response);
                                    String LoginCredential = jsonObject1.getString("success");

                                    if(LoginCredential.equals("0")){
                                        AccessCodeTeacher.getText().clear();
                                        TC_ACCESS_CODE=AccessCodeTeacher.getText().toString();
                                        teacherReg();
                                        Toast.makeText(context, "invalid AC", Toast.LENGTH_SHORT).show();

                                        return;
                                        }
                                    Toast.makeText(context, "valid AC", Toast.LENGTH_SHORT).show();
                                    TC_ACCESS_CODE=AccessCodeTeacher.getText().toString();
                                    teacherReg();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                      //  progressDialog.dismiss();
                        System.out.println("ResponseRegistration" + volleyError.getMessage());
                     /*   if (volleyError instanceof NoConnectionError) {
                            Toast.makeText(Registration.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Registration.this, R.string.some_error_occurred, Toast.LENGTH_SHORT).show();
                        }*/
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
                                Toast.makeText(Registration.this, "No Connection", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {

                                Teacher_ValidateOnClick();                            }
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    @Override
    public void IsInValidAccessCode(boolean value) {
        Toast.makeText(context, "CheckValideAccessCode:"+value, Toast.LENGTH_SHORT).show();
    }
    //Access code mandatory code @kajal 11-12-2022
 /*   private void Teachers_ValidateOnClick() {
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

                                        *//**//*
                                        txtAccessCodeTeacher.setVisibility(View.VISIBLE);
                                        txtUsernameTeacher.setVisibility(View.VISIBLE);
                                        txtPasswordTeacher.setVisibility(View.VISIBLE);

                                        txtAccessCodeTeacher.setText(AccessCodeTeacher.getText().toString());
                                        txtUsernameTeacher.setText(TCUsername.getText().toString());
                                        txtPasswordTeacher.setText(TCPassword.getText().toString());

                                        TCPassword.setVisibility(View.GONE);
                                        TCUsername.setVisibility(View.GONE);
                                        AccessCodeTeacher.setVisibility(View.GONE);
                                        *//**//*

                                           *//* Teachers_Validate.setBackgroundColor(R.color.green);
                                            Teachers_Validate.setText("Valid");*//*

                                        Teachers_Validate.setVisibility(View.GONE);
                                    } else if (LoginCredential.equals("0")) {
                                        Toast.makeText(Registration.this, "Access Code Invalid", Toast.LENGTH_LONG).show();
                                        accessCheckteche.setBackgroundResource(R.drawable.wrong);
                                           *//* Student.setChecked(false);
                                            Teacher.setChecked(false);*//*
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
            }*//*else if (!isValidMobile(TCSchholPhoneNumber.getText().toString())) {
                TCSchholPhoneNumber.setError("Please Fill Valid School Number");
            }*//* else if (!isValidMobile(TCSchholPhoneNumber.getText().toString())) {
                TCSchholPhoneNumber.setError("Please Fill valid Phone Number");
            } else {
                Log.d("Login_response_techers", "Click");
                progressDialog = new ProgressDialog(Registration.this);
                progressDialog.setMessage("Loading..."); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                progressDialog.setCancelable(false);
                //String url = "http://www.techive.in/studybuddy/api/teacher_register.php";
                String url = Apis.new_base_url + Apis.teacher_register_url;
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
                                            *//*if (Accesssescoderesp.equals("1")) {
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
                                            }*//*

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
                String url = Apis.new_base_url + Apis.student_register_url;
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
                                                *//*Intent intent = new Intent(Registration.this, StudentdashBord.class);
                                                intent.putExtra("accesscodes", AccessCode.getText().toString());
                                                startActivity(intent);*//*
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
                                          *//*  Student_Validate.setBackgroundColor(R.color.green);
                                            Student_Validate.setText("Valid");*//*
                                        Student_Validate.setVisibility(View.GONE);

                                    } else if (LoginCredential.equals("0")) {
                                        Toast.makeText(Registration.this, "Access Code Invalid", Toast.LENGTH_LONG).show();
                                        accessCheck.setBackgroundResource(R.drawable.wrong);
                                        Log.d("Status12345", "2");
                                                *//*Student.setChecked(false);
                                                Teacher.setChecked(false);*//*
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
    }*/
}
