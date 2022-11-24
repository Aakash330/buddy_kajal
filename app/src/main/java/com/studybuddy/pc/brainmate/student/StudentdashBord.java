package com.studybuddy.pc.brainmate.student;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.studybuddy.pc.brainmate.mains.Apis;
import com.studybuddy.pc.brainmate.teacher.TePaintingChapters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.studybuddy.pc.brainmate.Network_connection.data.Constants.CONNECTIVITY_ACTION;

public class StudentdashBord extends AppCompatActivity {// implements NavigationView.OnNavigationItemSelectedListener {

    Button AddAccessesCodebutton, AddAccesscodeValidate,bookRetryBtn;
    String TAG="StudentdashBord";
    ArrayList<HashMap<String, String>> Books_By_Accesscode;
    ListView Bookslist;
    ProgressDialog progressDialog;
    String accesscodes;
    Toolbar toolbarHeader;
    IntentFilter intentFilter;
    NetworkChangeReceiver receiver;
    LinearLayout bookNotFoundLty;
    private static TextView log_network;
    private static String log_str;
     EditText getAccesescode;
    BooksAcsStuCodeAdapter acsCodeAdapter;
    ListView bookListRv;

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
    String Subject, Student_ID, ClassName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentdash_bord);

        toolbarHeader = findViewById(R.id.toolbarHeader);

        //BookNotFound
         bookNotFoundLty=findViewById(R.id.bookNotFoundLyt);
         bookRetryBtn=findViewById(R.id.book_retry);
         bookListRv=findViewById(R.id.Bookslist);

         bookRetryBtn.setOnClickListener(v ->getBookWithAccessCode());


        setSupportActionBar(toolbarHeader);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbarHeader.setTitleTextColor(getResources().getColor(R.color.white));
            //toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow);
            //getSupportActionBar().setTitle("Techive");
        }
        setTitle("Books");
        accesscodes = getIntent().getStringExtra("accesscodes");
        Subject = getIntent().getStringExtra("Subject");
        Student_ID = getIntent().getStringExtra("Student_ID");
        ClassName = getIntent().getStringExtra("ClassName");
        Books_By_Accesscode = new ArrayList<>();
        Bookslist = (ListView) findViewById(R.id.Bookslist);


        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/
        intentFilter = new IntentFilter();
        intentFilter.addAction(CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();

        studentCheckForUpdateBook();

        Bookslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String book_Type = Books_By_Accesscode.get(position).get("Subject");
                String ebook = Books_By_Accesscode.get(position).get("ebook").trim();
                String expireStatus = Books_By_Accesscode.get(position).get("expire_status");
                ebook = ebook.replace(" ", "%20");
                Log.d("ebookebook", book_Type);
                String result = Books_By_Accesscode.get(position).get("Class");//.replaceAll("[^\\d.]", "");
                if (book_Type.equals("Art & Craft")) {
                    if(expireStatus.equals("0")){
                    Intent intent = new Intent(StudentdashBord.this, TePaintingChapters.class);
                    intent.putExtra("access_code", Books_By_Accesscode.get(position).get("access_code"));
                    intent.putExtra("class", result);
                    intent.putExtra("Subject", Subject);
                    startActivity(intent);}
                    else{
                        getBookWithAccessCode();
                    }
                } else {
                    if(expireStatus.equals("0")){
                        Intent intent = new Intent(StudentdashBord.this, StudebtElimentery.class);
                        intent.putExtra("access_code", Books_By_Accesscode.get(position).get("access_code"));
                        intent.putExtra("ebook", Books_By_Accesscode.get(position).get("ebook"));

                        intent.putExtra("Title", Books_By_Accesscode.get(position).get("Title"));
                        intent.putExtra("animation", Books_By_Accesscode.get(position).get("animation"));
                        intent.putExtra("Subject", Subject);
                        startActivity(intent);
                    }else{
                     getBookWithAccessCode();

                    }

                }
            }
        });
    }

    private void studentCheckForUpdateBook() {

        if (NetworkUtil.getConnectivityStatus(StudentdashBord.this) > 0) {
            System.out.println("Connect");
            Network_Status = "Connect";


            progressDialog = new ProgressDialog(StudentdashBord.this);
            progressDialog.setMessage("Loading..."); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);
            RequestQueue queue = Volley.newRequestQueue(StudentdashBord.this);
            //String url = "http://www.techive.in/studybuddy/api/student_book.php";
            String url = Apis.base_url+Apis.student_book_url;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Studentbooks", response);
                            Log.d("Studentbooks","@kajal testing books page");


                            progressDialog.dismiss();
                            try {

                                JSONObject jsonObject1 = null;
                                jsonObject1 = new JSONObject(response);

                                Log.d("object111", jsonObject1.getString("success"));

                                JSONArray heroArray = jsonObject1.getJSONArray("data");

//                            Log.d("notice123", String.valueOf();

                                for (int j = 0; j < heroArray.length(); j++) {
                                    JSONObject c1 = heroArray.getJSONObject(j);
                                    if (c1.getString("status").equals("1")) {
                                        Log.d("imageArray", "ok" + c1.getString("class"));
                                       /* HashMap<String, String> ObjectiveMap = new HashMap<>();
                                        ObjectiveMap.put("Title", c1.getString("title"));
                                        ObjectiveMap.put("Class", c1.getString("class"));
                                        ObjectiveMap.put("Subject",c1.getString("subject"));
                                        ObjectiveMap.put("book_img", c1.getString("book_img"));
                                        ObjectiveMap.put("ebook", c1.getString("ebook"));
                                        ObjectiveMap.put("manual", c1.getString("manual"));
                                        ObjectiveMap.put("animation", c1.getString("animation"));
                                        ObjectiveMap.put("status", c1.getString("status"));
                                        ObjectiveMap.put("access_code", c1.getString("access_code"));
                                        ObjectiveMap.put("book_Type", "11");

                                        Books_By_Accesscode.add(ObjectiveMap);
                                        Log.d("imageArray1254f", String.valueOf(Books_By_Accesscode));*/

                                        HashMap<String, String> ObjectiveMap = new HashMap<>();
                                        ObjectiveMap.put("Subject", c1.getString("subject"));
                                        //Check expire book @kajal 11_23_22
                                        //     if (c1.getString("expire_status").equals("0")) {

                                        if (Subject.equals(c1.getString("subject"))) {

                                            if (ClassName.equals(c1.getString("class"))) {
                                                ObjectiveMap.put("Title", c1.getString("title"));
                                                ObjectiveMap.put("Class", c1.getString("class"));
                                                ObjectiveMap.put("Subject", c1.getString("subject"));
                                                ObjectiveMap.put("book_img", c1.getString("book_img"));
                                                ObjectiveMap.put("ebook", c1.getString("ebook"));
                                                ObjectiveMap.put("manual", c1.getString("manual"));
                                                ObjectiveMap.put("animation", c1.getString("animation"));
                                                ObjectiveMap.put("status", c1.getString("status"));
                                                ObjectiveMap.put("book_Type", "11");
                                                ObjectiveMap.put("access_code", c1.getString("access_code"));
                                                ObjectiveMap.put("expire_status", c1.getString("expire_status"));
                                                Books_By_Accesscode.add(ObjectiveMap);
                                            }
                                        }
                              /*      }else{
                                            getBookWithAccessCode();
                                        }*/
                                    }
                                }
                                for (int i = 0; i < Books_By_Accesscode.size(); i++) {

                                    for (int j = i + 1; j < Books_By_Accesscode.size(); j++) {
                                        if (Books_By_Accesscode.get(i).equals(Books_By_Accesscode.get(j))) {
                                            Books_By_Accesscode.remove(j);
                                            j--;
                                        }
                                    }

                                }
                                Log.d("Studentbooks","@kajal testing books page");
                                Log.d("Studentbooks","@kajal testing books page array size:"+Books_By_Accesscode.size());

                                if(Books_By_Accesscode.size()==0){
                                    bookListRv.setVisibility(View.GONE);
                                    bookNotFoundLty.setVisibility(View.VISIBLE);

                                }else{
                                    bookListRv.setVisibility(View.VISIBLE);
                                    bookNotFoundLty.setVisibility(View.GONE);
                                    acsCodeAdapter = new BooksAcsStuCodeAdapter(StudentdashBord.this, Books_By_Accesscode);
                                    Bookslist.setAdapter(acsCodeAdapter);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("student_id", Student_ID);
                    Log.d("getStudentId", "" + params.toString());
                    return params;
                }
            };
            queue.add(stringRequest);
        } else {
            System.out.println("No connection");
            Network_Status = "notConnect";
            checkNetDialog();
        }
    }

    private void getBookWithAccessCode() {

        final Dialog dialog = new Dialog(StudentdashBord.this);
        dialog.setContentView(R.layout.addaccesscodlay_experi);
        dialog.setTitle("Custom Dialog");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        final ImageView checkAccese = (ImageView) dialog.findViewById(R.id.checkAccese);
        getAccesescode = (EditText) dialog.findViewById(R.id.getAccesescode);
        AddAccesscodeValidate = (Button) dialog.findViewById(R.id.AddAccesscode);
        AddAccessesCodebutton = (Button) dialog.findViewById(R.id.AddAccessesCodebutton);

   /*     AddAccessesCodebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtil.getConnectivityStatus(StudentdashBord.this) > 0) {
                    System.out.println("Connect");
                    Network_Status = "Connect";

                    progressDialog = new ProgressDialog(StudentdashBord.this);
                    progressDialog.setMessage("Loading..."); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);
                    //RequestQueue queue = Volley.newRequestQueue(Stu_Classes.this);
                    //String url = "http://www.techive.in/studybuddy/api/student_ac_insert.php";
                    String url = Apis.base_url + Apis.student_ac_insert_url;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("books_validator009", response);
                                    progressDialog.dismiss();
                                    try {
                                        Log.d("Status123", "3");
                                        JSONObject jsonObject1 = new JSONObject(response);

                                        String LoginCredential = jsonObject1.getString("success");

                                        Log.d("login_succes_student", "" + LoginCredential);

                                        if (LoginCredential.equals("1")) {
                                            Toast.makeText(StudentdashBord.this, "Book added successfully", Toast.LENGTH_LONG).show();
                                        } else if (LoginCredential.equals("0")) {
                                            Toast.makeText(StudentdashBord.this, "Something went to wrong", Toast.LENGTH_LONG).show();
                                        } else if (LoginCredential.equals("2")) {
                                            Toast.makeText(StudentdashBord.this, "You have already added this book", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Log.d("login_succes_student", "" + e.getMessage());
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            progressDialog.dismiss();
                            Log.d("getParamsDatas11", "" + volleyError.getMessage());
                            if (volleyError instanceof NoConnectionError) {
                                Toast.makeText(StudentdashBord.this, "Internet not Connected", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(StudentdashBord.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }) {
                        @Override
                        protected java.util.Map<String, String> getParams() throws AuthFailureError {
                            java.util.Map<String, String> params = new HashMap<>();
                            params.put("accesscodes", getAccesescode.getText().toString());
                            params.put("student_id", Student_ID);
                            Log.d("get_ac_code", params.toString() + "");
                            return params;
                        }
                    };
                    CommonMethods.callWebserviceForResponse(stringRequest, StudentdashBord.this);
                } else {
                    System.out.println("No connection");
                    Network_Status = "notConnect";
                    checkNetDialog();
                }
            }
        });*/
        AddAccesscodeValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtil.getConnectivityStatus(StudentdashBord.this) > 0) {
                    System.out.println("Connect");
                    Network_Status = "Connect";
                    progressDialog = new ProgressDialog(StudentdashBord.this);
                    progressDialog.setMessage("Loading..."); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);
                    //RequestQueue queue = Volley.newRequestQueue(Stu_Classes.this);
                    //String url = "http://www.techive.in/studybuddy/api/book_validate.php";
                    String url = Apis.base_url + Apis.book_validate_url;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("books_validator", "response " + response);
                                    progressDialog.dismiss();
                                    try {
                                        Log.d("Status123", "3");
                                        JSONObject jsonObject1 = new JSONObject(response);
                                        //  Log.d("login_succes_student", "" + jsonObject1.getString("success"));
                                        String LoginCredential = jsonObject1.getString("success");
                                        Log.d("login_succes_student", "" + LoginCredential);
                                        if (LoginCredential.equals("1")) {
                                            Log.d("Status123", "1");
                                            let();
                                          } else if (LoginCredential.equals("0")) {
                                            Toast.makeText(StudentdashBord.this, "Access Code Invalid", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(StudentdashBord.this, "Internet not Connected", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(StudentdashBord.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }) {
                        @Override
                        protected java.util.Map<String, String> getParams() throws AuthFailureError {
                            java.util.Map<String, String> params = new HashMap<>();
                            params.put("accesscodes", getAccesescode.getText().toString());
                            return params;
                        }
                    };
                    //queue.add(stringRequest);
                    CommonMethods.callWebserviceForResponse(stringRequest, StudentdashBord.this);
                } else {
                    System.out.println("No connection");
                    Network_Status = "notConnect";
                    checkNetDialog();
                }
            }
        });
    }
    void let(){
        if (NetworkUtil.getConnectivityStatus(StudentdashBord.this) > 0) {
            System.out.println("Connect");
            Network_Status = "Connect";

            progressDialog = new ProgressDialog(StudentdashBord.this);
            progressDialog.setMessage("Loading..."); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);
            //RequestQueue queue = Volley.newRequestQueue(Stu_Classes.this);
            //String url = "http://www.techive.in/studybuddy/api/student_ac_insert.php";
            String url = Apis.base_url + Apis.student_ac_insert_url;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("books_validator009", response);
                            progressDialog.dismiss();
                            try {
                                Log.d("Status123", "3");
                                JSONObject jsonObject1 = new JSONObject(response);

                                String LoginCredential = jsonObject1.getString("success");

                                Log.d("login_succes_student", "" + LoginCredential);

                                if (LoginCredential.equals("1")) {
                                    Toast.makeText(StudentdashBord.this, "Book added successfully", Toast.LENGTH_LONG).show();
                                    Books_By_Accesscode.clear();
                                    studentCheckForUpdateBook();
                                } else if (LoginCredential.equals("3")) {
                                    Toast.makeText(StudentdashBord.this, "Something went to wrong", Toast.LENGTH_LONG).show();
                                } else if (LoginCredential.equals("0")) {
                                    Toast.makeText(StudentdashBord.this, "You have already added this book", Toast.LENGTH_LONG).show();
                                }else if (LoginCredential.equals("2")) {
                                    Toast.makeText(StudentdashBord.this, "Book updated successfully", Toast.LENGTH_LONG).show();
                                    Books_By_Accesscode.clear();
                                    studentCheckForUpdateBook();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("login_succes_student", "" + e.getMessage());
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    progressDialog.dismiss();
                    Log.d("getParamsDatas11", "" + volleyError.getMessage());
                    if (volleyError instanceof NoConnectionError) {
                        Toast.makeText(StudentdashBord.this, "Internet not Connected", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(StudentdashBord.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
                    }
                }
            }) {
                @Override
                protected java.util.Map<String, String> getParams() throws AuthFailureError {
                    java.util.Map<String, String> params = new HashMap<>();
                    params.put("accesscodes", getAccesescode.getText().toString());
                    params.put("student_id", Student_ID);
                    Log.d("get_ac_code", params.toString() + "");
                    return params;
                }
            };
            CommonMethods.callWebserviceForResponse(stringRequest, StudentdashBord.this);
        } else {
            System.out.println("No connection");
            Network_Status = "notConnect";
            checkNetDialog();
        }
    }

    /*@Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
          /*getMenuInflater().inflate(R.menu.studentdash_bord, menu);
          return true;*/
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_go_home:
                Intent intent = new Intent(StudentdashBord.this, Stu_Classes.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("accesscodes", CommonMethods.getAccessCode(StudentdashBord.this));
                intent.putExtra("Student_ID", CommonMethods.getId(StudentdashBord.this));
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void checkNetDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StudentdashBord.this);
        alertDialogBuilder.setMessage(getString(R.string.no_internet));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(getString(R.string.retry),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        if (NetworkUtil.getConnectivityStatus(StudentdashBord.this) > 0) {
                            Toast.makeText(StudentdashBord.this, "" + getString(R.string.connected), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            checkNetDialog();
                        }
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /*@SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Profile) {
            Intent intent = new Intent(StudentdashBord.this, Student_Profile.class);
            startActivity(intent);
        } else if (id == R.id.ScoreCard) {
            Intent intent = new Intent(StudentdashBord.this, ScoreCard.class);
            startActivity(intent);
        }
        *//*else if (id == R.id.nav_slideshow) {

        }*//* *//*else if (id == R.id.TakeTest)
        {
            *//**//*Intent intent=new Intent(StudentdashBord.this,StudentTest.class);
            startActivity(intent);*//**//*
        }*//*
        else if (id == R.id.AddCode) {
            final Dialog dialog = new Dialog(StudentdashBord.this);
            dialog.setContentView(R.layout.addaccesscodlay);
            dialog.setTitle("Custom Dialog");
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.show();

            final ImageView checkAccese = (ImageView) dialog.findViewById(R.id.checkAccese);
            final EditText getAccesescode = (EditText) dialog.findViewById(R.id.getAccesescode);
            AddAccesscodeValidate = (Button) dialog.findViewById(R.id.AddAccesscode);
            AddAccessesCodebutton = (Button) dialog.findViewById(R.id.AddAccessesCodebutton);

            AddAccessesCodebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetworkUtil.getConnectivityStatus(StudentdashBord.this) > 0) {
                        System.out.println("Connect");
                        Network_Status = "Connect";


                        progressDialog = new ProgressDialog(StudentdashBord.this);
                        progressDialog.setMessage("Loading..."); // Setting Title
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                        progressDialog.show(); // Display Progress Dialog
                        progressDialog.setCancelable(false);
                        RequestQueue queue = Volley.newRequestQueue(StudentdashBord.this);
                        String url = "http://www.techive.in/studybuddy/api/student_ac_insert.php";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("books_validator", response);
                                        progressDialog.dismiss();
                                        try

                                        {
                                            Log.d("Status123", "3");
                                            JSONObject jsonObject1 = new JSONObject(response);
                                            //  Log.d("login_succes_student", "" + jsonObject1.getString("success"));
                                            String LoginCredential = jsonObject1.getString("success");
                                            String AlreadyAdded = jsonObject1.getString("access_already");
                                            Log.d("login_succes_student", "" + LoginCredential);

                                            if (LoginCredential.equals("1"))

                                            {

                                                Toast.makeText(StudentdashBord.this, "Book Added Successfully", Toast.LENGTH_LONG).show();

                                            } else if (LoginCredential.equals("0"))

                                            {
                                                Toast.makeText(StudentdashBord.this, "Something went to wrong", Toast.LENGTH_LONG).show();

                                            } else if (LoginCredential.equals("2")) {
                                                Toast.makeText(StudentdashBord.this, "you Have already added this book", Toast.LENGTH_LONG).show();
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
                                params.put("accesscodes", getAccesescode.getText().toString());
                                params.put("student_id", Student_ID);

                                return params;
                            }
                        };
                        queue.add(stringRequest);
                    } else {
                        System.out.println("No connection");
                        Network_Status = "notConnect";
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StudentdashBord.this);
                        alertDialogBuilder.setMessage(getString(R.string.no_internet));
                        alertDialogBuilder.setPositiveButton("Exit",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        finish();
                                    }
                                });


                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                }
            });
            AddAccesscodeValidate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetworkUtil.getConnectivityStatus(StudentdashBord.this) > 0) {
                        System.out.println("Connect");
                        Network_Status = "Connect";

                        Log.d("books_validator", "books_validator");
                        progressDialog = new ProgressDialog(StudentdashBord.this);
                        progressDialog.setMessage("Loading..."); // Setting Title
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                        progressDialog.show(); // Display Progress Dialog
                        progressDialog.setCancelable(false);
                        RequestQueue queue = Volley.newRequestQueue(StudentdashBord.this);
                        String url = "http://www.techive.in/studybuddy/api/book_validate.php";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("books_validator", response);
                                        progressDialog.dismiss();
                                        try

                                        {
                                            Log.d("Status123", "3");
                                            JSONObject jsonObject1 = new JSONObject(response);
                                            //  Log.d("login_succes_student", "" + jsonObject1.getString("success"));
                                            String LoginCredential = jsonObject1.getString("success");
                                            Log.d("login_succes_student", "" + LoginCredential);

                                            if (LoginCredential.equals("1"))

                                            {

                                                Log.d("Status123", "1");

                                                AddAccessesCodebutton.setVisibility(View.VISIBLE);
                                                AddAccesscodeValidate.setVisibility(View.GONE);

                                            } else if (LoginCredential.equals("0"))

                                            {
                                                Toast.makeText(StudentdashBord.this, "Access Code Invalid", Toast.LENGTH_LONG).show();

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
                                params.put("accesscodes", getAccesescode.getText().toString());

                                return params;
                            }
                        };
                        queue.add(stringRequest);
                    } else {
                        System.out.println("No connection");
                        Network_Status = "notConnect";
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StudentdashBord.this);
                        alertDialogBuilder.setMessage(getString(R.string.no_internet));
                        alertDialogBuilder.setPositiveButton("Exit",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        finish();
                                    }
                                });


                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }

                }
            });

        } else if (id == R.id.nav_change_pwd) {
            Intent intent = new Intent(StudentdashBord.this, ChoosePaint.class);
            startActivity(intent);
        } else if (id == R.id.SContactUS) {
            Intent intent = new Intent(StudentdashBord.this, Contact_us.class);
            startActivity(intent);
        } else if (id == R.id.SCustomerSupport) {
            final Dialog dialog = new Dialog(StudentdashBord.this);
            dialog.setContentView(R.layout.costumer_support);
            dialog.setTitle("Custom Dialog");
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.show();
            Button Call_US = (Button) dialog.findViewById(R.id.Call_US);
            Button Email_Us = (Button) dialog.findViewById(R.id.Email_Us);
            Call_US.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String phone = "+34666777888";
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    startActivity(intent);
                }
            });
            Email_Us.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mailto = "mailto:bob@example.org" +
                            "&subject=" + Uri.encode("Subject") +
                            "&body=" + Uri.encode("Bodytext");

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(Uri.parse(mailto));

                    try {
                        startActivity(emailIntent);
                    } catch (ActivityNotFoundException e) {
                        //TODO: Handle case where no email app is available
                    }


                }
            });
        } else if (id == R.id.SShare) {
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            share.putExtra(Intent.EXTRA_SUBJECT, "Sharing Content");
            share.putExtra(Intent.EXTRA_TEXT, "Url of App ");

            startActivity(Intent.createChooser(share, "Share link!"));
        } else if (id == R.id.SAboutus) {
            Intent intent = new Intent(StudentdashBord.this, About_us.class);
            startActivity(intent);
        } else if (id == R.id.SLogout) {
            Intent intent = new Intent(StudentdashBord.this, LoginBothActivity.class);
            startActivity(intent);
            SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/

}
