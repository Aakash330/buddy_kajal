package com.studybuddy.pc.brainmate.student;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.studybuddy.pc.brainmate.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/*public class ScoreCard extends AppCompatActivity {

    ArrayList<HashMap<String, String>> Arraylist;
    String[] TRUEFALSE = {"Computer science", "Arts & Activity", "G.K", "Hindi", "EVS"};
    String[] nursery = {"Play", "LKG", "UKG"};

    Button image;
    ScoreCardAdapter adapter;
    ProgressDialog progressDialog;
    ArrayList<HashMap<String, String>> PatientList;
    String UpcomigID;
    EditText inputSearch;
    ListView list;
    ArrayList<HashMap<String, String>> searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Score Card");
        setContentView(R.layout.activity_score_card);

        inputSearch = (EditText) findViewById(R.id.inputSearch);
        list = (ListView) findViewById(R.id.listview25);
        // image=(Button)findViewById(R.id.image) ;


        Arraylist = new ArrayList<>();
        for (int i = 0; i < TRUEFALSE.length; i++) {
            HashMap<String, String> map1 = new HashMap<>();
            map1.put("Subjects", TRUEFALSE[i]);
            map1.put("code", "1");

            Arraylist.add(map1);

        }
        adapter = new ScoreCardAdapter(ScoreCard.this, Arraylist);
        list.setAdapter(adapter);
        PatientList = new ArrayList<HashMap<String, String>>();


        progressDialog = new ProgressDialog(ScoreCard.this);
        progressDialog.setMessage("Loading..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        RequestQueue queue = Volley.newRequestQueue(ScoreCard.this);
        String url = "http://techive.in/medical_api/patient_data.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ViewPatient", response);
                        progressDialog.dismiss();
                        try
                        {
                            JSONObject jsonObject1 = new JSONObject(response);
                            Log.d("login_succes", "" + jsonObject1.getString("success"));
                            String LoginCredential = jsonObject1.getString("success");
                            JSONArray heroArray = jsonObject1.getJSONArray("patientData");

                            for (int i = 0; i < heroArray.length(); i++) {
                                JSONObject c = heroArray.getJSONObject(i);
                                String id = c.getString("id");
                                String name = c.getString("name");
                                String email = c.getString("email");
                                String address = c.getString("address");
                                String gender = c.getString("gender");
                                String father_name = c.getString("father_name");
                                String mother_name = c.getString("mother_name");
                                String phone = c.getString("phone");
                                String bood_group = c.getString("bood_group");
                                String weight = c.getString("weight");
                                String height = c.getString("height");
                                String company = c.getString("company");
                                String designation = c.getString("designation");
                                String allergy = c.getString("allergy");
                                String food_allergy = c.getString("food_allergy");
                                String medical_allergy = c.getString("medical_allergy");
                                String any_health_issue = c.getString("any_health_issue");
                                String other_notes = c.getString("other_notes");

                                HashMap<String, String> contact = new HashMap<>();

                                // adding each child node to HashMap key => value
                                contact.put("id", id);
                                contact.put("name", name);
                                contact.put("email", email);
                                contact.put("address", address);
                                contact.put("gender", gender);
                                contact.put("father_name", father_name);
                                contact.put("mother_name", mother_name);
                                contact.put("phone", phone);
                                contact.put("bood_group", bood_group);
                                contact.put("weight", weight);
                                contact.put("height", height);
                                contact.put("company", company);
                                contact.put("designation", designation);
                                contact.put("allergy", allergy);
                                contact.put("food_allergy", food_allergy);
                                contact.put("medical_allergy", medical_allergy);
                                contact.put("any_health_issue", any_health_issue);
                                contact.put("other_notes", other_notes);


                                Log.d("sarejhaseachha", String.valueOf(contact));
                                PatientList.add(contact);
                            }
                            //chapterList.add(PateintDATA);
                            searchResults = new ArrayList<>((Collection<? extends HashMap<String, String>>) PatientList);
                          *//*  adapter = new BookSubjectAdapter(MainActivity.this, searchResults,UpcomigID);
                            list.setAdapter(adapter);*//*
                            Log.d("Sssssssslist", String.valueOf(PatientList));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Create the AlertDialog object and return it
                        *//* return builder.create();*//*
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
                params.put("id", "34");
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public class ScoreCardAdapter extends BaseAdapter {

        // Declare Variables

        ScoreCard mContext;
        LayoutInflater inflater;
        ArrayList<HashMap<String, String>> animalNamesList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        String upcomigID;


        public ScoreCardAdapter(ScoreCard context, ArrayList<HashMap<String, String>> animalNamesList) {
            mContext = context;
            this.upcomigID = upcomigID;
            this.animalNamesList = animalNamesList;
            inflater = LayoutInflater.from((Context) mContext);


            Log.d("Sssssssslist", String.valueOf("Adapter" + animalNamesList));

        }

        public class ViewHolder {
            TextView Subject, Date, Class, Markers, NumQuestion, result2, result;
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
                view = inflater.inflate(R.layout.scorecardlist, null);
                // Locate the TextViews in listview_item.xml
                holder.Subject = (TextView) view.findViewById(R.id.SubjectName);
                holder.result2 = (TextView) view.findViewById(R.id.SubjectName);
                holder.result = (TextView) view.findViewById(R.id.SubjectName);
               *//* holder.Date = (TextView) view.findViewById(R.id.nameLabel);
                holder.Class = (TextView) view.findViewById(R.id.PhonenumberLa);
                holder.imgg = (ImageView) view.findViewById(R.id.imgg);*//*
                //  holder.linearLayout=(LinearLayout)view.findViewById(R.id.listclick);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            // Set the results into TextViews
            map = new HashMap<>(position);
            holder.Subject.setText(animalNamesList.get(position).get("Subjects"));

           *//* holder.ID.setText(animalNamesList.get(position).get("id"));
            holder.Number.setText(animalNamesList.get(position).get("phone"));*//*


           *//* holder.imgg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,Books_Details.class);
                    mContext.startActivity(intent);

                }
            });
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,Books_Details.class);
                    mContext.startActivity(intent);
                }
            });*//*
            return view;
        }
    }
}*/
