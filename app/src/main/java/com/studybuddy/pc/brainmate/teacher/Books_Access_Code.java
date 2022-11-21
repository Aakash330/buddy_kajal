package com.studybuddy.pc.brainmate.teacher;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.studybuddy.pc.brainmate.student.CommonMethods;
import com.studybuddy.pc.brainmate.student.StudentdashBord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.studybuddy.pc.brainmate.Network_connection.data.Constants.CONNECTIVITY_ACTION;

public class Books_Access_Code extends AppCompatActivity {//implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<HashMap<String, String>> Books_By_Accesscode;
    ListView Bookslist;
    ProgressDialog progressDialog;
    String accesscodes, Teachers_ID;
    Button AddAccessesCodebutton, AddAccesscodeValidate,bookRetryBtn;
    IntentFilter intentFilter;
    NetworkChangeReceiver receiver;
    private static TextView log_network;
    private static String log_str;
    String Network_Status, SubjectNme, ClassName, displayClassName;
    Toolbar toolbarHeader;
    Context context;
    LinearLayout bookNotFoundLty;
    ListView bookListRv;
    BooksAcsCodeAdapter acsCodeAdapter;
    EditText getAccesescode;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books__access__code);
        context = Books_Access_Code.this;
        accesscodes = getIntent().getStringExtra("accesscodes");
        Teachers_ID = getIntent().getStringExtra("Teachers_ID");
        SubjectNme = getIntent().getStringExtra("Subject");
        ClassName = getIntent().getStringExtra("ClassName");
        displayClassName = getIntent().getStringExtra("displayClassName");

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

        intentFilter = new IntentFilter();
        intentFilter.addAction(CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        // Log.d("accesscodes123",Teachers_ID);

        Books_By_Accesscode = new ArrayList<>();
        Bookslist = (ListView) findViewById(R.id.Bookslist);

        if (NetworkUtil.getConnectivityStatus(Books_Access_Code.this) > 0) {
            System.out.println("Connect");
            Network_Status = "Connect";

            Log.d("notice123", "AAAAAAA");
            progressDialog = new ProgressDialog(Books_Access_Code.this);
            progressDialog.setMessage("Loading..."); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);
            String url = Apis.base_url + Apis.teacher_book_url;//webView ebook link come from this api
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("notice12345", response);
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject1 = null;
                                jsonObject1 = new JSONObject(response);
                                Log.d("object111", jsonObject1.getString("success"));
                                JSONArray heroArray = jsonObject1.getJSONArray("data");
                                for (int j = 0; j < heroArray.length(); j++) {
                                    JSONObject c1 = heroArray.getJSONObject(j);
                                    if (c1.getString("status").equals("1")) {
                                        Log.d("imageArray", "ok" + c1.getString("class"));
                                        HashMap<String, String> ObjectiveMap = new HashMap<>();
                                        ObjectiveMap.put("Subject", c1.getString("subject"));
                                        if (SubjectNme.equals(c1.getString("subject"))) {
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
                                                Books_By_Accesscode.add(ObjectiveMap);
                                            }
                                        }
                                        Log.d("imageArray1254f", c1.getString("book_img"));
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

                                if(Books_By_Accesscode.size()==0){
                                    bookListRv.setVisibility(View.GONE);
                                    bookNotFoundLty.setVisibility(View.VISIBLE);

                                }else{
                                    bookListRv.setVisibility(View.VISIBLE);
                                    bookNotFoundLty.setVisibility(View.GONE);
                                    acsCodeAdapter = new BooksAcsCodeAdapter(Books_Access_Code.this, Books_By_Accesscode);
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
                    System.out.println("ResponseRegistration" + error.getMessage());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("accesscodes", accesscodes);
                    params.put("teacher_id", Teachers_ID);
                    Log.d("teacher_id", "" + Teachers_ID);
                    return params;
                }
            };
            // Add the request to the RequestQueue.
            CommonMethods.callWebserviceForResponse(stringRequest, context);
        } else {
            System.out.println("No connection");
            Network_Status = "notConnect";
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Books_Access_Code.this);
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

        Bookslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String book_Type = Books_By_Accesscode.get(position).get("Subject");
                String ebook = Books_By_Accesscode.get(position).get("ebook").trim();
                ebook = ebook.replace(" ", "%20");
                Log.d("ebookebook", book_Type);
                // str = str.replaceAll("[^\\d.]", "");
                String result = Books_By_Accesscode.get(position).get("Class");//.replaceAll("[^\\d.]", "");
                //if (book_Type.equals("Drawing")) {
                if (NetworkUtil.getConnectivityStatus(Books_Access_Code.this) > 0) {
                    //TODO : changes for lkg ukg and nursery
                    if (book_Type.equals("Drawing")) {
                        //finish();
                        Intent intent = new Intent(Books_Access_Code.this, TePaintingChapters.class);
                        intent.putExtra("access_code", Books_By_Accesscode.get(position).get("access_code"));
                        intent.putExtra("class", result);
                        startActivity(intent);
                    } else if (book_Type.equals("Art & Craft")) {
                        //finish();
                        Intent intent = new Intent(Books_Access_Code.this, TePaintingChapters.class);
                        intent.putExtra("access_code", Books_By_Accesscode.get(position).get("access_code"));
                        intent.putExtra("class", result);
                        startActivity(intent);
                    } else {
                        //finish();
                        Intent intent = new Intent(Books_Access_Code.this, LearningElementary.class);
                        intent.putExtra("access_code", Books_By_Accesscode.get(position).get("access_code"));
                        intent.putExtra("ebook", ebook);
                        intent.putExtra("SubjectNme", SubjectNme);
                        intent.putExtra("displayClassName", displayClassName);
                        intent.putExtra("manual", Books_By_Accesscode.get(position).get("manual"));
                        intent.putExtra("Title", Books_By_Accesscode.get(position).get("Title"));
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(Books_Access_Code.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView txtUserName = header.findViewById(R.id.txtUserName);
        TextView txtEmail = header.findViewById(R.id.txtEmail);
        //TODO : Assign Dynamic name email.
        txtUserName.setText(getIntent().getStringExtra("name") != null ? getIntent().getStringExtra("name") : "");
        txtEmail.setText(getIntent().getStringExtra("email") != null ? getIntent().getStringExtra("email") : "");
        navigationView.setNavigationItemSelectedListener(this);*/

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

    /*@SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_slideshow3) {
            Intent intent = new Intent(Books_Access_Code.this, LoginBothActivity.class);
            startActivity(intent);
            SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();

        } else if (id == R.id.QuestionPapers) {
            Intent intent = new Intent(Books_Access_Code.this, ActivityScore.class);
            startActivity(intent);

        } else if (id == R.id.contactus) {
            Intent intent = new Intent(Books_Access_Code.this, Contact_us.class);
            startActivity(intent);

        } else if (id == R.id.Aboutus) {
            Intent intent = new Intent(Books_Access_Code.this, About_us.class);
            startActivity(intent);

        } else if (id == R.id.myprofile) {
            Intent intent = new Intent(Books_Access_Code.this, Teachers_Profile.class);
            startActivity(intent);

        } else if (id == R.id.InviteFriends) {
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            share.putExtra(Intent.EXTRA_SUBJECT, "Sharing Content");
            share.putExtra(Intent.EXTRA_TEXT, "Url of App ");

            startActivity(Intent.createChooser(share, "Share link!"));
        } else if (id == R.id.Costumer_Support) {
            final Dialog dialog = new Dialog(Books_Access_Code.this);
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

        } else if (id == R.id.Expiredbokks) {
            Intent intent = new Intent(Books_Access_Code.this, Expired_book.class);
            intent.putExtra("accesscodes", "2222");
            intent.putExtra("Teachers_ID", Teachers_ID);
            startActivity(intent);
        } else if (id == R.id.Addcode) {

            final Dialog dialog = new Dialog(Books_Access_Code.this);
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
                    if (NetworkUtil.getConnectivityStatus(Books_Access_Code.this) > 0) {
                        System.out.println("Connect");
                        Network_Status = "Connect";

                        progressDialog = new ProgressDialog(Books_Access_Code.this);
                        progressDialog.setMessage("Loading..."); // Setting Title
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                        progressDialog.show(); // Display Progress Dialog
                        progressDialog.setCancelable(false);
                        RequestQueue queue = Volley.newRequestQueue(Books_Access_Code.this);
                        String url = "http://www.techive.in/studybuddy/api/teacher_ac_insert.php";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("books_validator", response);
                                        progressDialog.dismiss();
                                        try {
                                            Log.d("Status123", "3");
                                            JSONObject jsonObject1 = new JSONObject(response);
                                            //  Log.d("login_succes_student", "" + jsonObject1.getString("success"));
                                            String LoginCredential = jsonObject1.getString("success");
                                            Log.d("login_succes_student", "" + LoginCredential);
                                            if (LoginCredential.equals("1")) {
                                                Toast.makeText(Books_Access_Code.this, "Book Added Successfully", Toast.LENGTH_LONG).show();
                                            } else if (LoginCredential.equals("0")) {
                                                Toast.makeText(Books_Access_Code.this, "Something went to wrong", Toast.LENGTH_LONG).show();
                                            } else if (LoginCredential.equals("2")) {
                                                Toast.makeText(Books_Access_Code.this, "you Have already added this book", Toast.LENGTH_LONG).show();
                                                dialog.dismiss();
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
                                params.put("teacher_id", Teachers_ID);
                                return params;
                            }
                        };
                        queue.add(stringRequest);
                    } else {
                        System.out.println("No connection");
                        Network_Status = "notConnect";
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Books_Access_Code.this);
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
                    if (NetworkUtil.getConnectivityStatus(Books_Access_Code.this) > 0) {
                        System.out.println("Connect");
                        Network_Status = "Connect";
                        Log.d("books_validator", "books_validator");
                        progressDialog = new ProgressDialog(Books_Access_Code.this);
                        progressDialog.setMessage("Loading..."); // Setting Title
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                        progressDialog.show(); // Display Progress Dialog
                        progressDialog.setCancelable(false);
                        RequestQueue queue = Volley.newRequestQueue(Books_Access_Code.this);
                        String url = "http://www.techive.in/studybuddy/api/book_validate.php";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("books_validator", response);
                                        progressDialog.dismiss();
                                        try {
                                            Log.d("Status123", "3");
                                            JSONObject jsonObject1 = new JSONObject(response);
                                            //  Log.d("login_succes_student", "" + jsonObject1.getString("success"));
                                            String LoginCredential = jsonObject1.getString("success");
                                            Log.d("login_succes_student", "" + LoginCredential);
                                            if (LoginCredential.equals("1")) {
                                                Log.d("Status123", "1");
                                                AddAccessesCodebutton.setVisibility(View.VISIBLE);
                                                AddAccesscodeValidate.setVisibility(View.GONE);
                                            } else if (LoginCredential.equals("0")) {
                                                Toast.makeText(Books_Access_Code.this, "Access Code Invalid", Toast.LENGTH_LONG).show();
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
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Books_Access_Code.this);
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
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_go_home:
                Intent intent = new Intent(Books_Access_Code.this, Main2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("accesscodes", CommonMethods.getAccessCode(Books_Access_Code.this));
                intent.putExtra("Teachers_ID", CommonMethods.getId(Books_Access_Code.this));
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void getBookWithAccessCode() {

        final Dialog dialog = new Dialog(Books_Access_Code.this);
        dialog.setContentView(R.layout.addaccesscodlay);
        dialog.setTitle("Custom Dialog");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        final ImageView checkAccese = (ImageView) dialog.findViewById(R.id.checkAccese);
        final EditText getAccesescode = (EditText) dialog.findViewById(R.id.getAccesescode);
        AddAccesscodeValidate = (Button) dialog.findViewById(R.id.AddAccesscode);
        AddAccessesCodebutton = (Button) dialog.findViewById(R.id.AddAccessesCodebutton);

       /* AddAccessesCodebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtil.getConnectivityStatus(Books_Access_Code.this) > 0) {
                    System.out.println("Connect");
                    Network_Status = "Connect";

                    progressDialog = new ProgressDialog(Books_Access_Code.this);
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
                                            Toast.makeText(Books_Access_Code.this, "Book added successfully", Toast.LENGTH_LONG).show();
                                        } else if (LoginCredential.equals("0")) {
                                            Toast.makeText(Books_Access_Code.this, "Something went to wrong", Toast.LENGTH_LONG).show();
                                        } else if (LoginCredential.equals("2")) {
                                            Toast.makeText(Books_Access_Code.this, "You have already added this book", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(Books_Access_Code.this, "Internet not Connected", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Books_Access_Code.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }) {
                        @Override
                        protected java.util.Map<String, String> getParams() throws AuthFailureError {
                            java.util.Map<String, String> params = new HashMap<>();
                            params.put("accesscodes", getAccesescode.getText().toString());
                            params.put("student_id", Teachers_ID);
                            Log.d("get_ac_code", params.toString() + "");
                            return params;
                        }
                    };
                    CommonMethods.callWebserviceForResponse(stringRequest, Books_Access_Code.this);
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
                if (NetworkUtil.getConnectivityStatus(Books_Access_Code.this) > 0) {
                    System.out.println("Connect");
                    Network_Status = "Connect";
                    progressDialog = new ProgressDialog(Books_Access_Code.this);
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
                                           /* AddAccessesCodebutton.setVisibility(View.VISIBLE);
                                            AddAccesscodeValidate.setVisibility(View.GONE);*/
                                        } else if (LoginCredential.equals("0")) {
                                            Toast.makeText(Books_Access_Code.this, "Access Code Invalid", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(Books_Access_Code.this, "Internet not Connected", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Books_Access_Code.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
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
                    CommonMethods.callWebserviceForResponse(stringRequest, Books_Access_Code.this);
                } else {
                    System.out.println("No connection");
                    Network_Status = "notConnect";
                    checkNetDialog();
                }
            }
        });
    }
    void let(){
        if (NetworkUtil.getConnectivityStatus(Books_Access_Code.this) > 0) {
            System.out.println("Connect");
            Network_Status = "Connect";

            progressDialog = new ProgressDialog(Books_Access_Code.this);
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
                                    Toast.makeText(Books_Access_Code.this, "Book added successfully", Toast.LENGTH_LONG).show();
                                } else if (LoginCredential.equals("0")) {
                                    Toast.makeText(Books_Access_Code.this, "Something went to wrong", Toast.LENGTH_LONG).show();
                                } else if (LoginCredential.equals("2")) {
                                    Toast.makeText(Books_Access_Code.this, "You have already added this book", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(Books_Access_Code.this, "Internet not Connected", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Books_Access_Code.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
                    }
                }
            }) {
                @Override
                protected java.util.Map<String, String> getParams() throws AuthFailureError {
                    java.util.Map<String, String> params = new HashMap<>();
                    params.put("accesscodes", getAccesescode.getText().toString());
                    params.put("student_id", Teachers_ID);
                    Log.d("get_ac_code", params.toString() + "");
                    return params;
                }
            };
            CommonMethods.callWebserviceForResponse(stringRequest, Books_Access_Code.this);
        } else {
            System.out.println("No connection");
            Network_Status = "notConnect";
            checkNetDialog();
        }
    }
    //region "Menu"
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public void checkNetDialog() {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(Books_Access_Code.this);
        alertDialogBuilder.setMessage(getString(R.string.no_internet));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(getString(R.string.retry),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        if (NetworkUtil.getConnectivityStatus(Books_Access_Code.this) > 0) {
                            Toast.makeText(Books_Access_Code.this, "" + getString(R.string.connected), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            checkNetDialog();
                        }
                    }
                });
        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
