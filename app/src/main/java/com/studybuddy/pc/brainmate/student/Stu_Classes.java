package com.studybuddy.pc.brainmate.student;

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
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.studybuddy.pc.brainmate.teacher.About_us;
import com.studybuddy.pc.brainmate.teacher.ActivityScore;
import com.studybuddy.pc.brainmate.teacher.Books_Details;
import com.studybuddy.pc.brainmate.teacher.Contact_us;
import com.studybuddy.pc.brainmate.teacher.ImageslistAdapter;
import com.studybuddy.pc.brainmate.teacher.Main2Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Stu_Classes extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Button barcodeScanner;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    // EditText inputSearch;
    int count = 1;
    String Network_Status;
    String accesscodes, Student_ID;
    ListView list;
    StudentClassAdapter adapter;
    ProgressDialog progressDialog;
    ArrayList<HashMap<String, String>> PatientList;
    String UpcomigID;
    EditText inputSearch;
    //ArrayList<HashMap<String, String>> searchResults;
    ArrayList<HashMap<String, String>> Arraylist;
    ArrayList<HashMap<String, String>> CatalogArray;
    RecyclerView Imageslist;
    ImageslistAdapter imageslistAdapter;
    private LinearLayoutManager horizontalLayoutManager;
    Button AddAccessesCodebutton, AddAccesscodeValidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu__classes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Classes");

        accesscodes = getIntent().getStringExtra("accesscodes");
        Student_ID = getIntent().getStringExtra("Student_ID");

        Log.d("ASSSASSAS", "126364GH  " + accesscodes + "id " + Student_ID);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        list = (ListView) findViewById(R.id.listview12);
        Imageslist = (RecyclerView) findViewById(R.id.Imageslist);
        PatientList = new ArrayList<HashMap<String, String>>();
        CatalogArray = new ArrayList<>();
        //barcodeScanner = (Button) findViewById(R.id.barcodeScanner);
        autoScrollAnother();
        /*barcodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //start the scanning activity from the com.google.zxing.client.android.SCAN intent
                    Intent intent = new Intent(ACTION_SCAN);
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, 0);
                } catch (ActivityNotFoundException anfe) {
                    //on catch, show the download dialog
                    //showDialog(Stu_Classes.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
                }
            }
        });*/

        Log.d("ViewClass", "1263654789");
        progressDialog = new ProgressDialog(Stu_Classes.this);
        progressDialog.setMessage("Loading..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        RequestQueue queue = Volley.newRequestQueue(Stu_Classes.this);
        //String url = "http://www.techive.in/studybuddy/api/student_book.php";
        String url = Apis.base_url + Apis.student_book_url;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ViewClass123654", response);
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
                            Log.d("AAAAAAA", String.valueOf(Arraylist));

                            adapter = new StudentClassAdapter(Stu_Classes.this, Arraylist);
                            list.setAdapter(adapter);

                            Log.d("Sssssssslist", String.valueOf(PatientList));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("getParamsDatas11", "" + error.getMessage());
                if (error instanceof NoConnectionError) {
                    Toast.makeText(Stu_Classes.this, "Internet not Connected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Stu_Classes.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        }) {

            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //TODO : changes made below
                //params.put("accesscodes", accesscodes);
                params.put("student_id", Student_ID);
                return params;
            }
        };
        queue.add(stringRequest);

        horizontalLayoutManager = new LinearLayoutManager(Stu_Classes.this, LinearLayoutManager.HORIZONTAL, false);
        Imageslist.setLayoutManager(horizontalLayoutManager);
        horizontalLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RequestQueue Recqueue = Volley.newRequestQueue(Stu_Classes.this);
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
                                ObjectiveMap.put("book_img", c1.getString("book_img"));
                                CatalogArray.add(ObjectiveMap);
                            }
                            Log.d("Sssssssslist", String.valueOf(PatientList));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        scrollImg();
                        // Create the AlertDialog object and return it
                        /* return builder.create();*/
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                Log.d("getParamsDatas11", "" + volleyError.getMessage());
                if (volleyError instanceof NoConnectionError) {
                    Toast.makeText(Stu_Classes.this, "Internet not Connected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Stu_Classes.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        }) {

            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("accesscodes", accesscodes);
                params.put("student_id", Student_ID);
                return params;
            }
        };
        // Add the request to the RequestQueue.
        Recqueue.add(RecstringRequest);


        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // new Handler().post(Update);
            }
        }, 3000, 3000);


        Arraylist = new ArrayList<>();

        /*inputSearch.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //get the text in the EditText
                String searchString = inputSearch.getText().toString();
                int textLength = searchString.length();
                //searchResults.clear();
                for (int i = 0; i < chapterList.size(); i++) {
                    String playerName = chapterList.get(i).get("name").toString();
                    String playerID = chapterList.get(i).get("id").toString();
                    String playerNumber = chapterList.get(i).get("phone").toString();
                    if (textLength < playerName.length() || textLength < playerID.length() || textLength < playerNumber.length()) {
                        //compare the String in EditText with Names in the ArrayList
                        try {
                            if (searchString.equalsIgnoreCase(playerName.substring(0, textLength)) || searchString.equalsIgnoreCase(playerID.substring(0, textLength)) || searchString.equalsIgnoreCase(playerNumber.substring(0, textLength)))
                                searchResults.add(chapterList.get(i));
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


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Intent intent = new Intent(Stu_Classes.this, Books_Details.class);
                startActivity(intent);*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView txtUserName = header.findViewById(R.id.txtStuName);
        TextView txtEmail = header.findViewById(R.id.textViewEmail);
        txtUserName.setText(CommonMethods.getUsername(Stu_Classes.this) != null ? CommonMethods.getUsername(Stu_Classes.this) : "");
        txtEmail.setText(CommonMethods.getEmailId(Stu_Classes.this) != null ? CommonMethods.getEmailId(Stu_Classes.this) : "");
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //region "OptionsMenu"
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.stu__classes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_go_home) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Profile) {
            Intent intent = new Intent(Stu_Classes.this, Student_Profile.class);
            intent.putExtra("Student_ID", Student_ID);
            startActivity(intent);
        } else if (id == R.id.ScoreCard) {
            //Intent intent = new Intent(Stu_Classes.this, ScoreCard.class);
            Intent intent = new Intent(Stu_Classes.this, ActivityScore.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {
            CommonMethods.saveEmailId(Stu_Classes.this, null);
            CommonMethods.saveId(Stu_Classes.this, null);
            CommonMethods.saveUsername(Stu_Classes.this, null);
            CommonMethods.saveType(Stu_Classes.this, null);
            CommonMethods.saveIsLogin(Stu_Classes.this, 0);
            CommonMethods.saveAccessCode(Stu_Classes.this, null);
        } else if (id == R.id.AddCode) {
            final Dialog dialog = new Dialog(Stu_Classes.this);
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
                    if (NetworkUtil.getConnectivityStatus(Stu_Classes.this) > 0) {
                        System.out.println("Connect");
                        Network_Status = "Connect";

                        progressDialog = new ProgressDialog(Stu_Classes.this);
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
                                                Toast.makeText(Stu_Classes.this, "Book added successfully", Toast.LENGTH_LONG).show();
                                            } else if (LoginCredential.equals("0")) {
                                                Toast.makeText(Stu_Classes.this, "Something went to wrong", Toast.LENGTH_LONG).show();
                                            } else if (LoginCredential.equals("2")) {
                                                Toast.makeText(Stu_Classes.this, "You have already added this book", Toast.LENGTH_LONG).show();
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
                                    Toast.makeText(Stu_Classes.this, "Internet not Connected", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Stu_Classes.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
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
                        CommonMethods.callWebserviceForResponse(stringRequest, Stu_Classes.this);
                    } else {
                        System.out.println("No connection");
                        Network_Status = "notConnect";
                        checkNetDialog();
                    }
                }
            });
            AddAccesscodeValidate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetworkUtil.getConnectivityStatus(Stu_Classes.this) > 0) {
                        System.out.println("Connect");
                        Network_Status = "Connect";
                        progressDialog = new ProgressDialog(Stu_Classes.this);
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
                                                AddAccessesCodebutton.setVisibility(View.VISIBLE);
                                                AddAccesscodeValidate.setVisibility(View.GONE);
                                            } else if (LoginCredential.equals("0")) {
                                                Toast.makeText(Stu_Classes.this, "Access Code Invalid", Toast.LENGTH_LONG).show();
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
                                    Toast.makeText(Stu_Classes.this, "Internet not Connected", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Stu_Classes.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
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
                        CommonMethods.callWebserviceForResponse(stringRequest, Stu_Classes.this);
                    } else {
                        System.out.println("No connection");
                        Network_Status = "notConnect";
                        checkNetDialog();
                    }
                }
            });

        } else if (id == R.id.nav_change_pwd) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Stu_Classes.this);
            alertDialogBuilder.setMessage("To change password please login to the website");
            alertDialogBuilder.setPositiveButton(getString(R.string.go_to_website),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent intent = new Intent(Stu_Classes.this, LoginBothActivity.class);
                            intent.putExtra("canChangePwd", true);
                            intent.putExtra("student_id", CommonMethods.getId(Stu_Classes.this));
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            CommonMethods.saveEmailId(Stu_Classes.this, null);
                            CommonMethods.saveId(Stu_Classes.this, null);
                            CommonMethods.saveUsername(Stu_Classes.this, null);
                            CommonMethods.saveType(Stu_Classes.this, null);
                            CommonMethods.saveIsLogin(Stu_Classes.this, 0);
                            CommonMethods.saveAccessCode(Stu_Classes.this, null);
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
        } else if (id == R.id.SContactUS) {
            Intent intent = new Intent(Stu_Classes.this, Contact_us.class);
            intent.putExtra("getType", "s");
            startActivity(intent);
        } else if (id == R.id.nav_log_version) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.techive.in/"));
            startActivity(browserIntent);
        } else if (id == R.id.SCustomerSupport) {
            /*final Dialog dialog = new Dialog(Stu_Classes.this);
            dialog.setContentView(R.layout.costumer_support);
            dialog.setTitle("Custom Dialog");
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.show();
            Button Call_US = (Button) dialog.findViewById(R.id.Call_US);
            Button Email_Us = (Button) dialog.findViewById(R.id.Email_Us);
            Call_US.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    *//*String phone = "+34666777888";
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    startActivity(intent);*//*
                }
            });
            Email_Us.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    *//*String mailto = "mailto:bob@example.org" +
                            "&subject=" + Uri.encode("Subject") +
                            "&body=" + Uri.encode("Bodytext");

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(Uri.parse(mailto));

                    try {
                        startActivity(emailIntent);
                    } catch (ActivityNotFoundException e) {
                        //TODO: Handle case where no email app is available
                    }*//*
                }
            });*/
        } else if (id == R.id.SShare) {
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            share.putExtra(Intent.EXTRA_SUBJECT, "Sharing Content");
            share.putExtra(Intent.EXTRA_TEXT, "Url of App ");
            startActivity(Intent.createChooser(share, "Share link!"));
        } else if (id == R.id.SAboutus) {
            Intent intent = new Intent(Stu_Classes.this, About_us.class);
            startActivity(intent);
        } else if (id == R.id.SLogout) {
            Intent intent = new Intent(Stu_Classes.this, LoginBothActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            CommonMethods.saveEmailId(Stu_Classes.this, null);
            CommonMethods.saveId(Stu_Classes.this, null);
            CommonMethods.saveUsername(Stu_Classes.this, null);
            CommonMethods.saveType(Stu_Classes.this, null);
            CommonMethods.saveIsLogin(Stu_Classes.this, 0);
            CommonMethods.saveAccessCode(Stu_Classes.this, null);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }//endregion

    public void checkNetDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Stu_Classes.this);
        alertDialogBuilder.setMessage(getString(R.string.no_internet));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(getString(R.string.retry),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        if (NetworkUtil.getConnectivityStatus(Stu_Classes.this) > 0) {
                            Toast.makeText(Stu_Classes.this, "" + getString(R.string.connected), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            checkNetDialog();
                        }
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    //region "StudentClassAdapter"
    public class StudentClassAdapter extends BaseAdapter {

        // Declare Variables

        Stu_Classes mContext;
        LayoutInflater inflater;
        ArrayList<HashMap<String, String>> animalNamesList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        String upcomigID;


        public StudentClassAdapter(Stu_Classes context, ArrayList<HashMap<String, String>> animalNamesList) {
            mContext = context;
            this.animalNamesList = animalNamesList;
            inflater = LayoutInflater.from((Context) mContext);
        }

        public class ViewHolder {
            TextView name, ID, Number;
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
            /*holder.ID.setText(animalNamesList.get(position).get("id"));
            holder.Number.setText(animalNamesList.get(position).get("phone"));*/
           /* accesscodes = getIntent().getStringExtra("accesscodes");
            Teachers_ID = getIntent().getStringExtra("Teachers_ID");*/
            holder.imgg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, Stu_Subjects.class);
                    intent.putExtra("Class", animalNamesList.get(position).get("Class"));
                    intent.putExtra("accesscodes", accesscodes);
                    intent.putExtra("Student_ID", Student_ID);
                    intent.putExtra("ClassName", animalNamesList.get(position).get("Class"));
                    mContext.startActivity(intent);

                }
            });
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, Stu_Subjects.class);
                    intent.putExtra("Class", animalNamesList.get(position).get("Class"));
                    intent.putExtra("accesscodes", accesscodes);
                    intent.putExtra("Student_ID", Student_ID);
                    intent.putExtra("ClassName", animalNamesList.get(position).get("Class"));
                    mContext.startActivity(intent);
                }
            });
            return view;
        }
    }//endregion

    //region "Img Scroll"
    public void autoScrollAnother() {
        final int speedScroll = 1200;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (count == imageslistAdapter.getItemCount()) {
                        imageslistAdapter.load();
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
            imageslistAdapter = new ImageslistAdapter(CatalogArray, Stu_Classes.this) {
                @Override
                public void load() {
                    // CatalogArray.addAll(CatalogArray);
                }
            };
            Imageslist.setAdapter(imageslistAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(Stu_Classes.this) {
                @Override
                public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                    LinearSmoothScroller smoothScroller = new LinearSmoothScroller(Stu_Classes.this) {
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
    }//endregion
}
