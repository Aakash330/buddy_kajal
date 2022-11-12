package com.studybuddy.pc.brainmate.teacher;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import com.studybuddy.pc.brainmate.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Chapters extends AppCompatActivity {
    String [] Quetions={"Book_series 1","Book_series 2","Book_series 3","Book_series 4","Book_series 5"};
    ChapterAdapter adapter;
    ProgressDialog progressDialog;
    ArrayList<HashMap<String, String>> PatientList;
    String UpcomigID;
    EditText inputSearch;
    ArrayList<HashMap<String, String>> searchResults;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);
        setTitle("Chapters");
      /*  inputSearch=(EditText)findViewById(R.id.inputSearch);
        list=(ListView)findViewById(R.id.listview);


        marksList = new ArrayList<HashMap<String, String>>();

        *//*list = (ListView) findViewById(R.id.listview);
        searchBox = (EditText) findViewById(R.id.inputSearch);
        Search = new ArrayList<>();*//*
        adapter = new ChapterAdapter(Chapters.this, Quetions);
        list.setAdapter(adapter);
        progressDialog = new ProgressDialog(Chapters.this);
        progressDialog.setMessage("Loading..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
        RequestQueue queue = Volley.newRequestQueue(Chapters.this);
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
                                marksList.add(contact);
                            }
                            //marksList.add(PateintDATA);
                            searchResults = new ArrayList<>((Collection<? extends HashMap<String, String>>) marksList);
                           *//* adapter = new STUDENTDashbroadAdapter(StudentdashBord.this, searchResults,UpcomigID);
                            list.setAdapter(adapter);*//*


                            Log.d("Sssssssslist", String.valueOf(marksList));
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
                params.put("id","34");

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

                for (int i = 0; i < marksList.size(); i++) {
                    String playerName = marksList.get(i).get("name").toString();
                    String playerID = marksList.get(i).get("id").toString();
                    String playerNumber = marksList.get(i).get("phone").toString();
                    if (textLength < playerName.length()||textLength < playerID.length()||textLength < playerNumber.length()) {
                        //compare the String in EditText with Names in the ArrayList
                        try {


                            if (searchString.equalsIgnoreCase(playerName.substring(0, textLength)) || searchString.equalsIgnoreCase(playerID.substring(0, textLength)) || searchString.equalsIgnoreCase(playerNumber.substring(0, textLength)))
                                searchResults.add(marksList.get(i));
                        }catch (IndexOutOfBoundsException e){

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
                Toast toast = Toast.makeText(Chapters.this, "Content:", Toast.LENGTH_LONG);
                toast.show();
                Intent intent=new Intent(Chapters.this,Students_Class.class);
                startActivity(intent);
            }
        });        //intializing scan object


*/
    }
    }

