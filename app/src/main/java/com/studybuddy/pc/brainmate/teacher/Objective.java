package com.studybuddy.pc.brainmate.teacher;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.studybuddy.pc.brainmate.CertificateClass0S6;
import com.studybuddy.pc.brainmate.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
// TODO : Not in use Remove safely
public class Objective extends AppCompatActivity {
    String[] Quetions = {"WWW stands for ?", "Which of the following are components of Central Processing Unit (CPU) ?", " Which among following first generation of computers had ?", "Where is RAM located ?", " If a computer has more than one processor then it is known as ?"};
    OjectiveAdapter adapter;
    String[] TRUEFALSE = {"WWW stands for ?", "Which of the following are components of Central Processing Unit (CPU) ?", " Which among following first generation of computers had ?", "Where is RAM located ?", " If a computer has more than one processor then it is known as ?"};
    ProgressDialog progressDialog;
    ArrayList<HashMap<String, String>> PatientList;
    String UpcomigID;
    EditText inputSearch;
    ArrayList<HashMap<String, String>> searchResults;
    ListView list, list2;
    ArrayList<HashMap<String, String>> Arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objective);
        Arraylist = new ArrayList<>();
        for (int i = 0; i < Quetions.length; i++) {
            HashMap<String, String> map1 = new HashMap<>();
            map1.put("Quetion", Quetions[i]);
            map1.put("code", "1");

            Arraylist.add(map1);

        }
        for (int i = 0; i < TRUEFALSE.length; i++) {
            HashMap<String, String> map2 = new HashMap<>();
            map2.put("Quetion", TRUEFALSE[i]);
            map2.put("code", "2");
            Arraylist.add(map2);
        }
        Log.d("Arraylist", String.valueOf(Arraylist));

        inputSearch = (EditText) findViewById(R.id.inputSearch);
        list = (ListView) findViewById(R.id.listview);
        list2 = (ListView) findViewById(R.id.listview2);


       /* marksList = new ArrayList<HashMap<String, String>>();
        adapter = new OjectiveAdapter(Objective.this, Arraylist);
        list.setAdapter(adapter);*/
        //   list2.setAdapter(adapter);
       /* setDynamicHeight(list);
        setDynamicHeight(list2);*/
        progressDialog = new ProgressDialog(Objective.this);
        progressDialog.setMessage("Loading..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
       // RequestQueue queue = Volley.newRequestQueue(Objective.this);
        RequestQueue queue =Volley.newRequestQueue(Objective.this, new HurlStack(null, CertificateClass0S6.getSslSocketFactory(Objective.this)));
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


                                // adding contact to contact list

                                Log.d("sarejhaseachha", String.valueOf(contact));
                                PatientList.add(contact);
                            }
                            //marksList.add(PateintDATA);
                            searchResults = new ArrayList<>((Collection<? extends HashMap<String, String>>) PatientList);
                           /* adapter = new STUDENTDashbroadAdapter(StudentdashBord.this, searchResults,UpcomigID);
                            list.setAdapter(adapter);*/


                            Log.d("Sssssssslist", String.valueOf(PatientList));
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
                params.put("id", "34");

                return params;
            }
        };
// Add the request to the RequestQueue.
        queue.add(stringRequest);


        inputSearch.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //get the text in the EditText
                String searchString = inputSearch.getText().toString();
                int textLength = searchString.length();
                searchResults.clear();

                for (int i = 0; i < PatientList.size(); i++) {
                    String playerName = PatientList.get(i).get("name").toString();
                    String playerID = PatientList.get(i).get("id").toString();
                    String playerNumber = PatientList.get(i).get("phone").toString();
                    if (textLength < playerName.length() || textLength < playerID.length() || textLength < playerNumber.length()) {
                        //compare the String in EditText with Names in the ArrayList
                        try {


                            if (searchString.equalsIgnoreCase(playerName.substring(0, textLength)) || searchString.equalsIgnoreCase(playerID.substring(0, textLength)) || searchString.equalsIgnoreCase(playerNumber.substring(0, textLength)))
                                searchResults.add(PatientList.get(i));
                        } catch (IndexOutOfBoundsException e) {

                        }
                    }
                }

                adapter.notifyDataSetChanged();
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            public void afterTextChanged(Editable s) {


            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(Objective.this, "Content:", Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent(Objective.this, Students_Class.class);
                startActivity(intent);
            }
        });        //intializing scan object


    }

    public static void setDynamicHeight(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        //check adapter if null
        if (adapter == null) {
            return;
        }
        int height = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            height += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = height + (listView.getDividerHeight() * (adapter.getCount() - 0));
        listView.setLayoutParams(layoutParams);
        listView.requestLayout();
    }
}
