package com.studybuddy.pc.brainmate.teacher;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
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
import com.studybuddy.pc.brainmate.mains.Apis;
import com.studybuddy.pc.brainmate.mains.LoginBothActivity;
import com.studybuddy.pc.brainmate.Network_connection.utils.NetworkUtil;
import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.student.CommonMethods;
import com.studybuddy.pc.brainmate.student.Stu_Classes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /*private Button barcodeScanner;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";*/
    // EditText inputSearch;
    int count = 1;
    String Network_Status, SubjectNme;
    String accesscodes, Teachers_ID;
    ListView list;
    StudentClassAdapter adapter;
    ProgressDialog progressDialog;
    ArrayList<HashMap<String, String>> PatientList;
    String UpcomigID;
    EditText inputSearch;
    ArrayList<HashMap<String, String>> searchResults;
    ArrayList<HashMap<String, String>> Arraylist;
    ArrayList<HashMap<String, String>> CatalogArray;
    Activity activity;
    RecyclerView Imageslist;
    ImageslistAdapter imageslistAdapter;
    private LinearLayoutManager horizontalLayoutManager;
    Context context;
    ArrayList<HashMap<String, String>> Books_By_Accesscode;
    ListView Bookslist;

    Button AddAccessesCodebutton, AddAccesscodeValidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        activity = (Activity) Main2Activity.this;
        context = Main2Activity.this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        accesscodes = getIntent().getStringExtra("accesscodes") != null ? getIntent().getStringExtra("accesscodes") : CommonMethods.getAccessCode(Main2Activity.this);
        Teachers_ID = getIntent().getStringExtra("Teachers_ID") != null ? getIntent().getStringExtra("Teachers_ID") : CommonMethods.getId(Main2Activity.this);

        inputSearch = (EditText) findViewById(R.id.inputSearch);
        list = (ListView) findViewById(R.id.listview12);
        Imageslist = (RecyclerView) findViewById(R.id.Imageslist);
        PatientList = new ArrayList<HashMap<String, String>>();
        CatalogArray = new ArrayList<>();
        autoScrollAnother();
        horizontalLayoutManager = new LinearLayoutManager(Main2Activity.this, LinearLayoutManager.HORIZONTAL, false);
        Imageslist.setLayoutManager(horizontalLayoutManager);
        horizontalLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //RequestQueue Recqueue = Volley.newRequestQueue(Main2Activity.this);
        //String Recurl = "http://www.techive.in/studybuddy/api/book_images.php";
        String Recurl = Apis.base_url + Apis.book_images_url;
        StringRequest RecstringRequest = new StringRequest(Request.Method.POST, Recurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("catalogImagsrs", response);
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject1 = null;
                            jsonObject1 = new JSONObject(response);
                            Log.d("object111", jsonObject1.getString("success"));
                            JSONArray heroArray = jsonObject1.getJSONArray("images");
                            for (int j = 0; j < heroArray.length(); j++) {
                                JSONObject c1 = heroArray.getJSONObject(j);
                                HashMap<String, String> ObjectiveMap = new HashMap<>();
                                if (!c1.getString("book_img").equals("")) {
                                    ObjectiveMap.put("book_img", c1.getString("book_img"));
                                    CatalogArray.add(ObjectiveMap);
                                }
                            }
                            scrollImg();
                            Log.d("Sssssssslist", String.valueOf(PatientList));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Create the AlertDialog object and return it
                        /* return builder.create();*/
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                error.printStackTrace();
                if (error instanceof NoConnectionError) {
                    Toast.makeText(Main2Activity.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Main2Activity.this, R.string.some_error_occurred, Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("accesscodes", accesscodes);
                params.put("teacher_id", Teachers_ID);
                return params;
            }
        };
        //Recqueue.add(RecstringRequest);
        CommonMethods.callWebserviceForResponse(RecstringRequest, context);

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // new Handler().post(Update);
            }
        }, 3000, 3000);


        Arraylist = new ArrayList<>();
       /* for(int i=0;i<backlogPNG.length;i++){
            HashMap<String,String>map1=new HashMap<>();
            map1.put("Images", String.valueOf(backlogPNG[i]));
            map1.put("code","1");

            CatalogArray.add(map1);

        }
*/

        progressDialog = new ProgressDialog(Main2Activity.this);
        progressDialog.setMessage("Loading..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        //RequestQueue queue = Volley.newRequestQueue(Main2Activity.this);
        //String url = "http://www.techive.in/studybuddy/api/teacher_book.php";
        String url = Apis.base_url + Apis.teacher_book_url;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ViewClass", response);
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject1 = null;
                            jsonObject1 = new JSONObject(response);
                            Log.d("object111", jsonObject1.getString("success"));
                            JSONArray heroArray = jsonObject1.getJSONArray("data");
                            for (int j = 0; j < heroArray.length(); j++) {
                                JSONObject c1 = heroArray.getJSONObject(j);
                                if (c1.getString("class").equals("Nursery")) {
                                    HashMap<String, String> ObjectiveMap = new HashMap<>();
                                    ObjectiveMap.put("Class", c1.getString("class"));
                                    Arraylist.add(ObjectiveMap);
                                }
                            }
                            for (int j = 0; j < heroArray.length(); j++) {
                                JSONObject c1 = heroArray.getJSONObject(j);
                                if (c1.getString("class").equals("LKG")) {
                                    HashMap<String, String> ObjectiveMap = new HashMap<>();
                                    ObjectiveMap.put("Class", c1.getString("class"));
                                    Arraylist.add(ObjectiveMap);
                                }
                            }
                            for (int j = 0; j < heroArray.length(); j++) {
                                JSONObject c1 = heroArray.getJSONObject(j);
                                if (c1.getString("class").equals("UKG")) {
                                    HashMap<String, String> ObjectiveMap = new HashMap<>();
                                    ObjectiveMap.put("Class", c1.getString("class"));
                                    Arraylist.add(ObjectiveMap);
                                }
                            }
                            for (int j = 0; j < heroArray.length(); j++) {
                                JSONObject c1 = heroArray.getJSONObject(j);
                                if (c1.getString("status").equals("1")) {
                                    Log.d("imageArray", "ok" + c1.getString("class"));
                                    HashMap<String, String> ObjectiveMap = new HashMap<>();
                                    // ObjectiveMap.put("Title", c1.getString("title"));
                                    ObjectiveMap.put("Class", c1.getString("class"));
                                    // ObjectiveMap.put("Subject", c1.getString("subject"));
                                    //ObjectiveMap.put("book_img", c1.getString("book_img"));
                                    // ObjectiveMap.put("ebook", c1.getString("ebook"));
                                    // ObjectiveMap.put("manual", c1.getString("manual"));
                                    // ObjectiveMap.put("animation", c1.getString("animation"));
                                    // ObjectiveMap.put("status", c1.getString("status"));
                                    // ObjectiveMap.put("book_Type", "11");
                                    // ObjectiveMap.put("access_code", c1.getString("access_code"));

                                    //   Books_By_Accesscode.add(ObjectiveMap);
                                    Arraylist.add(ObjectiveMap);
                                    Log.d("imageArray1254f", c1.getString("book_img"));
                                }
                            }
                            for (int i = 0; i < Arraylist.size(); i++) {

                                for (int j = i + 1; j < Arraylist.size(); j++) {
                                    if (Arraylist.get(i).equals(Arraylist.get(j))) {
                                        Arraylist.remove(j);
                                        j--;
                                    }
                                }
                            }
                            adapter = new StudentClassAdapter(Main2Activity.this, Arraylist);
                            list.setAdapter(adapter);

                            Log.d("Sssssssslist", String.valueOf(PatientList));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Create the AlertDialog object and return it
                        /* return builder.create();*/
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                if (error instanceof NoConnectionError) {
                    Toast.makeText(Main2Activity.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Main2Activity.this, R.string.some_error_occurred, Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("accesscodes", accesscodes);
                params.put("teacher_id", Teachers_ID);
                return params;
            }
        };
        // Add the request to the RequestQueue.
        //queue.add(stringRequest);
        CommonMethods.callWebserviceForResponse(stringRequest, context);

        /*inputSearch.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //get the text in the EditText
                String searchString = inputSearch.getText().toString();
                int textLength = searchString.length();
                //searchResults.clear();

                for (int i = 0; i < marksList.size(); i++) {
                    String playerName = marksList.get(i).get("name").toString();
                    String playerID = marksList.get(i).get("id").toString();
                    String playerNumber = marksList.get(i).get("phone").toString();
                    if (textLength < playerName.length() || textLength < playerID.length() || textLength < playerNumber.length()) {
                        //compare the String in EditText with Names in the ArrayList
                        try {
                            if (searchString.equalsIgnoreCase(playerName.substring(0, textLength)) || searchString.equalsIgnoreCase(playerID.substring(0, textLength)) || searchString.equalsIgnoreCase(playerNumber.substring(0, textLength)))
                                searchResults.add(marksList.get(i));
                        } catch (IndexOutOfBoundsException e) {

                        }
                    }
                }
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void afterTextChanged(Editable s) {
            }
        });*/


        /*list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (NetworkUtil.getConnectivityStatus(Main2Activity.this) > 0) {
                    *//*Toast toast = Toast.makeText(Main2Activity.this, "Content:", Toast.LENGTH_LONG);
                    toast.show();*//*
                    Intent intent = new Intent(Main2Activity.this, Books_Details.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Main2Activity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });*/


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView txtUserName = header.findViewById(R.id.txtUserName);
        TextView txtEmail = header.findViewById(R.id.txtEmail);
        //TODO : Assign Dynamic name email.
        /*txtUserName.setText(getIntent().getStringExtra("name") != null ? getIntent().getStringExtra("name") : "");
        txtEmail.setText(getIntent().getStringExtra("email") != null ? getIntent().getStringExtra("email") : "");*/

        txtUserName.setText(CommonMethods.getUsername(Main2Activity.this) != null ? CommonMethods.getUsername(Main2Activity.this) : "");
        txtEmail.setText(CommonMethods.getEmailId(Main2Activity.this) != null ? CommonMethods.getEmailId(Main2Activity.this) : "");
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // super.onBackPressed();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_refresh) {
            if (NetworkUtil.getConnectivityStatus(Main2Activity.this) > 0) {
                System.out.println("Connect");
                Network_Status = "Connect";
                reloadActivity(activity);
            } else {
                System.out.println("No connection");
                Network_Status = "notConnect";
                getConnectedStatus();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void reloadActivity(Activity activity) {
        activity.finish();
        activity.startActivity(activity.getIntent());
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_logout) {
            Intent intent = new Intent(Main2Activity.this, LoginBothActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();

            CommonMethods.saveEmailId(Main2Activity.this, null);
            CommonMethods.saveId(Main2Activity.this, null);
            CommonMethods.saveUsername(Main2Activity.this, null);
            CommonMethods.saveType(Main2Activity.this, null);
            CommonMethods.saveIsLogin(Main2Activity.this, 0);
            CommonMethods.saveAccessCode(Main2Activity.this, null);

        } /*else if (id == R.id.QuestionPapers) {
         *//*Intent intent = new Intent(Main2Activity.this, ActivityScore.class);
            startActivity(intent);*//*
           }*/ else if (id == R.id.PfdPaper) {
            final Dialog dialog = new Dialog(Main2Activity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.history_layout);
            //dialog.setTitle("Custom Dialog");
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.show();

            Button btnActivityScore = (Button) dialog.findViewById(R.id.btnActivityScore);
            Button btnGeneratedPaper = (Button) dialog.findViewById(R.id.btnGeneratedPaper);

            btnActivityScore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetworkUtil.getConnectivityStatus(Main2Activity.this) > 0) {
                        System.out.println("Connect");
                        Network_Status = "Connect";
                        Intent intent = new Intent(Main2Activity.this, ActivityScore.class);
                        intent.putExtra("Teachers_ID", Teachers_ID);
                        startActivity(intent);
                    } else {
                        System.out.println("No connection");
                        Network_Status = "notConnect";
                        getConnectedStatus();
                    }
                }
            });
            btnGeneratedPaper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetworkUtil.getConnectivityStatus(Main2Activity.this) > 0) {
                        System.out.println("Connect");
                        Network_Status = "Connect";
                        Intent intent = new Intent(Main2Activity.this, ViewCreatedPdfActivity2.class);
                        intent.putExtra("Teachers_ID", Teachers_ID);
                        startActivity(intent);
                    } else {
                        System.out.println("No connection");
                        Network_Status = "notConnect";
                        getConnectedStatus();
                    }
                }
            });

        } else if (id == R.id.contactus) {
            Intent intent = new Intent(Main2Activity.this, Contact_us.class);
            intent.putExtra("getType", "t");
            startActivity(intent);

        } /*else if (id == R.id.nav_home) {
         *//*Intent intent = new Intent(Main2Activity.this, Main2Activity.class);
            startActivity(intent);*//*

        }*/ else if (id == R.id.nav_change_pwd) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main2Activity.this);
            alertDialogBuilder.setMessage("To change password please login to the website");
            alertDialogBuilder.setPositiveButton(getString(R.string.go_to_website),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                            Intent intent = new Intent(Main2Activity.this, LoginBothActivity.class);
                            intent.putExtra("canChangePwd", true);
                            intent.putExtra("teacher_id", CommonMethods.getId(Main2Activity.this));
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            CommonMethods.saveEmailId(Main2Activity.this, null);
                            CommonMethods.saveId(Main2Activity.this, null);
                            CommonMethods.saveUsername(Main2Activity.this, null);
                            CommonMethods.saveType(Main2Activity.this, null);
                            CommonMethods.saveIsLogin(Main2Activity.this, 0);
                            CommonMethods.saveAccessCode(Main2Activity.this, null);
                            /*Intent intent1 = new Intent(Main2Activity.this, LoginBothActivity.class);
                            startActivity(intent1);
                            SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.clear();
                            editor.apply();*/
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        } else if (id == R.id.Aboutus) {
            Intent intent = new Intent(Main2Activity.this, About_us.class);
            startActivity(intent);

        } else if (id == R.id.myprofile) {
            Intent intent = new Intent(Main2Activity.this, Teachers_Profile.class);
            intent.putExtra("Teachers_ID", Teachers_ID);
            startActivity(intent);

        } else if (id == R.id.InviteFriends) {
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            share.putExtra(Intent.EXTRA_SUBJECT, "Sharing Content");
            share.putExtra(Intent.EXTRA_TEXT, "" + Apis.app_url);
            startActivity(Intent.createChooser(share, "Share link!"));
        } else if (id == R.id.Costumer_Support) {
            /*final Dialog dialog = new Dialog(Main2Activity.this);
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
            });*/

        } else if (id == R.id.Expiredbokks) {
            Intent intent = new Intent(Main2Activity.this, Expired_book.class);
            intent.putExtra("accesscodes", "2222");
            intent.putExtra("Teachers_ID", Teachers_ID);
            startActivity(intent);
        } else if (id == R.id.nav_log_version) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.techive.in/"));
            startActivity(browserIntent);
        } else if (id == R.id.Addcode) {
            final Dialog dialog = new Dialog(Main2Activity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.addaccesscodlay);
            //dialog.setTitle("Study Buddy");
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.show();

            final ImageView checkAccese = (ImageView) dialog.findViewById(R.id.checkAccese);
            final EditText getAccesescode = (EditText) dialog.findViewById(R.id.getAccesescode);
            AddAccesscodeValidate = (Button) dialog.findViewById(R.id.AddAccesscode);
            AddAccessesCodebutton = (Button) dialog.findViewById(R.id.AddAccessesCodebutton);

            AddAccessesCodebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetworkUtil.getConnectivityStatus(Main2Activity.this) > 0) {
                        System.out.println("Connect");
                        Network_Status = "Connect";

                        progressDialog = new ProgressDialog(Main2Activity.this);
                        progressDialog.setMessage("Loading..."); // Setting Title
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                        progressDialog.show(); // Display Progress Dialog
                        progressDialog.setCancelable(false);
                        //RequestQueue queue = Volley.newRequestQueue(Main2Activity.this);
                        //String url = "http://www.techive.in/studybuddy/api/teacher_ac_insert.php";
                        String url = Apis.base_url + Apis.teacher_ac_insert_url;
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
                                                Toast.makeText(Main2Activity.this, "Book Added Successfully", Toast.LENGTH_LONG).show();
                                            } else if (LoginCredential.equals("0")) {
                                                Toast.makeText(Main2Activity.this, "Something went to wrong", Toast.LENGTH_LONG).show();
                                            } else if (LoginCredential.equals("2")) {
                                                Toast.makeText(Main2Activity.this, "you have already added this book", Toast.LENGTH_LONG).show();
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
                                if (error instanceof NoConnectionError) {
                                    getConnectedStatus();
                                    Toast.makeText(Main2Activity.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Main2Activity.this, R.string.some_error_occurred, Toast.LENGTH_SHORT).show();
                                }
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
                        //queue.add(stringRequest);
                        CommonMethods.callWebserviceForResponse(stringRequest, context);
                    } else {
                        System.out.println("No connection");
                        Network_Status = "notConnect";
                        getConnectedStatus();
                    }

                }
            });
            AddAccesscodeValidate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetworkUtil.getConnectivityStatus(Main2Activity.this) > 0) {
                        System.out.println("Connect");
                        Network_Status = "Connect";
                        Log.d("books_validator", "books_validator");
                        progressDialog = new ProgressDialog(Main2Activity.this);
                        progressDialog.setMessage("Loading..."); // Setting Title
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                        progressDialog.show(); // Display Progress Dialog
                        progressDialog.setCancelable(false);
                        //RequestQueue queue = Volley.newRequestQueue(Main2Activity.this);
                        //String url = "http://www.techive.in/studybuddy/api/book_validate.php";
                        String url = Apis.base_url + Apis.book_validate_url;
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
                                                Toast.makeText(Main2Activity.this, "Access Code Invalid", Toast.LENGTH_LONG).show();

                                            }


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                if (error instanceof NoConnectionError) {
                                    getConnectedStatus();
                                    Toast.makeText(Main2Activity.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Main2Activity.this, R.string.some_error_occurred, Toast.LENGTH_SHORT).show();
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
                        CommonMethods.callWebserviceForResponse(stringRequest, context);
                    } else {
                        System.out.println("No connection");
                        Network_Status = "notConnect";
                        getConnectedStatus();
                    }

                }
            });

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {
                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    /*//on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                toast.show();
                inputSearch.setText(contents);
                Log.d("Upcomingcode", contents);
            }
        }
    }*/

    public class StudentClassAdapter extends BaseAdapter {

        // Declare Variables
        Main2Activity mContext;
        LayoutInflater inflater;
        ArrayList<HashMap<String, String>> animalNamesList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        String upcomigID;


        public StudentClassAdapter(Main2Activity context, ArrayList<HashMap<String, String>> animalNamesList) {
            mContext = context;
            //  this.upcomigID=upcomigID;
            this.animalNamesList = animalNamesList;
            inflater = LayoutInflater.from((Context) mContext);
            Log.d("Sssssssslist", String.valueOf("Adapter" + animalNamesList));
        }

        public class ViewHolder {
            TextView name, ID, Number;
            // Button details,Medicalhistory;
            LinearLayout linearLayout;
            ImageView imgg;
        }

        @Override
        public int getCount() {
            return animalNamesList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View view, ViewGroup parent) {
            final ViewHolder holder;

            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.studentclasslist, null);
                // Locate the TextViews in listview_item.xml
                holder.name = (TextView) view.findViewById(R.id.heading);
                holder.ID = (TextView) view.findViewById(R.id.nameLabel);
                holder.Number = (TextView) view.findViewById(R.id.PhonenumberLa);
                holder.imgg = (ImageView) view.findViewById(R.id.imgg12);
                holder.linearLayout = (LinearLayout) view.findViewById(R.id.listclick);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            // Set the results into TextViews
            map = new HashMap<>(position);
            holder.name.setText(animalNamesList.get(position).get("Class"));
            /*holder.ID.setText(scoreDetailsList.get(position).get("id"));
            holder.Number.setText(scoreDetailsList.get(position).get("phone"));*/
           /* accesscodes = getIntent().getStringExtra("accesscodes");
            Teachers_ID = getIntent().getStringExtra("Teachers_ID");*/
            holder.imgg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetworkUtil.getConnectivityStatus(Main2Activity.this) > 0) {
                        //finish();
                        Intent intent = new Intent(mContext, Book_Subjects.class);
                        intent.putExtra("Class", animalNamesList.get(position).get("Class"));
                        intent.putExtra("accesscodes", accesscodes);
                        intent.putExtra("Teachers_ID", Teachers_ID);
                        intent.putExtra("ClassName", animalNamesList.get(position).get("Class"));
                        mContext.startActivity(intent);
                    } else {
                        Toast.makeText(Main2Activity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetworkUtil.getConnectivityStatus(Main2Activity.this) > 0) {
                        //finish();
                        Intent intent = new Intent(mContext, Book_Subjects.class);
                        intent.putExtra("Class", animalNamesList.get(position).get("Class"));
                        intent.putExtra("accesscodes", accesscodes);
                        intent.putExtra("Teachers_ID", Teachers_ID);
                        intent.putExtra("ClassName", animalNamesList.get(position).get("Class"));
                        mContext.startActivity(intent);
                    } else {
                        Toast.makeText(Main2Activity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return view;
        }
    }

    public void autoScrollAnother() {
        final int speedScroll = 5000;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (count == imageslistAdapter.getItemCount()) {
                        // noticeAdapter.load();
                        imageslistAdapter.notifyDataSetChanged();
                    }
                    Imageslist.smoothScrollToPosition((count++));
                    handler.postDelayed(this, speedScroll);
                } catch (NullPointerException N) {

                }
            }
        };
        handler.postDelayed(runnable, speedScroll);
    }

    @Override
    protected void onResume() {
        super.onResume();
        scrollImg();
    }

    public void scrollImg() {
        if (CatalogArray != null && CatalogArray.size() > 0) {
            imageslistAdapter = new ImageslistAdapter(CatalogArray, Main2Activity.this) {
                @Override
                public void load() {
                    //CatalogArray.addAll(CatalogArray);
                }
            };
            Imageslist.setAdapter(imageslistAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(Main2Activity.this) {
                @Override
                public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                    LinearSmoothScroller smoothScroller = new LinearSmoothScroller(Main2Activity.this) {
                        private static final float SPEED = 5000f;// Change this value (default=25f)

                        @Override
                        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                            return SPEED / displayMetrics.densityDpi;
                        }
                    };
                    smoothScroller.setTargetPosition(position);
                    startSmoothScroll(smoothScroller);
                }
            };
            autoScrollAnother();
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            Imageslist.setLayoutManager(layoutManager);
            Imageslist.setHasFixedSize(true);
            Imageslist.setItemViewCacheSize(10);
            Imageslist.setDrawingCacheEnabled(true);
            Imageslist.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
            Imageslist.setAdapter(imageslistAdapter);
        }
    }

    public void getConnectedStatus() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main2Activity.this);
        alertDialogBuilder.setMessage(getString(R.string.no_internet));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(getString(R.string.retry),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        if (NetworkUtil.getConnectivityStatus(Main2Activity.this) > 0) {
                            Toast.makeText(Main2Activity.this, "" + getString(R.string.connected), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            getConnectedStatus();
                        }
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
