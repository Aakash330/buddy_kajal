package com.studybuddy.pc.brainmate.teacher;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.mains.Apis;
import com.studybuddy.pc.brainmate.student.CommonMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Expired_book extends AppCompatActivity {

    ArrayList<HashMap<String, String>> Books_By_Accesscode;
    ListView Bookslist;
    ProgressDialog progressDialog;
    String accesscodes, Teachers_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expired_book);
        setTitle("Expired Books");
        Books_By_Accesscode = new ArrayList<>();
        Bookslist = (ListView) findViewById(R.id.Bookslist);
        accesscodes = getIntent().getStringExtra("accesscodes");
        Teachers_ID = getIntent().getStringExtra("Teachers_ID");
        progressDialog = new ProgressDialog(Expired_book.this);
        progressDialog.setMessage("Loading..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        //String url = "http://www.techive.in/studybuddy/api/teacher_book.php";
        String url = Apis.base_url + Apis.teacher_book_url;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("notice123", response);

                        progressDialog.dismiss();
                        try {

                            JSONObject jsonObject1 = null;
                            jsonObject1 = new JSONObject(response);
                            Log.d("object111", jsonObject1.getString("success"));
                            JSONArray heroArray = jsonObject1.getJSONArray("data");
                            for (int j = 0; j < heroArray.length(); j++) {
                                JSONObject c1 = heroArray.getJSONObject(j);
                                if (!c1.getString("status").equals("0")) {
                                    Log.d("imageArray", "ok" + c1.getString("class"));
                                    HashMap<String, String> ObjectiveMap = new HashMap<>();
                                    ObjectiveMap.put("Title", c1.getString("title"));
                                    ObjectiveMap.put("Class", c1.getString("class"));
                                    ObjectiveMap.put("Subject", c1.getString("subject"));
                                    ObjectiveMap.put("book_img", c1.getString("book_img"));
                                    ObjectiveMap.put("ebook", c1.getString("ebook"));
                                    ObjectiveMap.put("manual", c1.getString("manual"));
                                    ObjectiveMap.put("animation", c1.getString("animation"));
                                    ObjectiveMap.put("status", c1.getString("status"));
                                    ObjectiveMap.put("book_Type", "11");

                                    Books_By_Accesscode.add(ObjectiveMap);
                                    Log.d("imageArray1254f", c1.getString("book_img"));
                                }
                            }
                           /* HashMap<String, String> ObjectiveMap = new HashMap<>();
                            ObjectiveMap.put("Title", "Painting");
                            ObjectiveMap.put("Class","1st");
                            ObjectiveMap.put("Subject","Painting");
                            ObjectiveMap.put("book_Type","12");
                            Books_By_Accesscode.add(ObjectiveMap);*/
                            EXBooksAcsCodeAdapter acsCodeAdapter = new EXBooksAcsCodeAdapter(Expired_book.this, Books_By_Accesscode);
                            Bookslist.setAdapter(acsCodeAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Create the AlertDialog object and return it
                        // return builder.create();


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
                return params;
            }
        };
        CommonMethods.callWebserviceForResponse(stringRequest, Expired_book.this);
    }
}
