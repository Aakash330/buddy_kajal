package com.studybuddy.pc.brainmate.teacher;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
import com.studybuddy.pc.brainmate.mains.Apis;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
//TODO Note
public class Books_Details extends AppCompatActivity {

    ListView list;
    BooksdetailsAdapter adapter;
    ProgressDialog progressDialog;
    ArrayList<HashMap<String, String>> PatientList;
    String UpcomigID;
    EditText inputSearch;
    ArrayList<HashMap<String, String>> searchResults;
    ArrayList<HashMap<String,String>>Arraylist;
    String [] TRUEFALSE ={"Webex","Infonet","EZone"};
    String [] nursery = {"Play","LKG","UKG"};
    String CLASS,Subject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books__details);
        setTitle("Books");
        inputSearch=(EditText)findViewById(R.id.inputSearch);
        list=(ListView)findViewById(R.id.listview12);
        PatientList = new ArrayList<HashMap<String, String>>();
        CLASS=getIntent().getStringExtra("Class");
        Subject=getIntent().getStringExtra("Subject");
        Arraylist=new ArrayList<>();
        for(int i=0;i<TRUEFALSE.length;i++){
            HashMap<String,String>map1=new HashMap<>();
            map1.put("Books",TRUEFALSE[i]);
            map1.put("code","1");

            Arraylist.add(map1);
        }
        adapter = new BooksdetailsAdapter(Books_Details.this,Arraylist);
        list.setAdapter(adapter);
        progressDialog = new ProgressDialog(Books_Details.this);
        progressDialog.setMessage("Loading..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
       // RequestQueue queue = Volley.newRequestQueue(Books_Details.this);
        RequestQueue queue =Volley.newRequestQueue(Books_Details.this, new HurlStack(null, CertificateClass0S6.getSslSocketFactory(Books_Details.this)));
        String url = "http://techive.in/medical_api/patient_data.php";
        //String url = Apis.base_url+Apis. "http://techive.in/medical_api/patient_data.php";
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


                                // adding contact to contact list

                                Log.d("sarejhaseachha", String.valueOf(contact));
                                PatientList.add(contact);
                            }
                            //marksList.add(PateintDATA);
                            searchResults = new ArrayList<>((Collection<? extends HashMap<String, String>>) PatientList);

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
                params.put("id","34");

                return params;
            }
        };

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
                    if (textLength < playerName.length()||textLength < playerID.length()||textLength < playerNumber.length()) {
                        //compare the String in EditText with Names in the ArrayList
                        try {


                            if (searchString.equalsIgnoreCase(playerName.substring(0, textLength)) || searchString.equalsIgnoreCase(playerID.substring(0, textLength)) || searchString.equalsIgnoreCase(playerNumber.substring(0, textLength)))
                                searchResults.add(PatientList.get(i));
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


    }
    public class BooksdetailsAdapter extends BaseAdapter {

        // Declare Variables

        Books_Details mContext;
        LayoutInflater inflater;
        ArrayList<HashMap<String, String>> animalNamesList = new ArrayList<HashMap<String, String>>();
        HashMap<String,String>map;
        String upcomigID;


        public BooksdetailsAdapter(Books_Details context, ArrayList<HashMap<String, String>> animalNamesList) {
            mContext = context;
            this.upcomigID=upcomigID;
            this.animalNamesList = animalNamesList;
            inflater = LayoutInflater.from((Context) mContext);
            Log.d("Sssssssslist", String.valueOf("Adapter"+animalNamesList));
        }

        public class ViewHolder {
            TextView name,ID,Number;
            // Button details,Medicalhistory;
            LinearLayout linearLayout;
            ImageView shope;
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
                view = inflater.inflate(R.layout.booksdetailslist, null);
                // Locate the TextViews in listview_item.xml
                holder.name = (TextView) view.findViewById(R.id.heading);
                holder.ID = (TextView) view.findViewById(R.id.nameLabel);
                holder.Number = (TextView) view.findViewById(R.id.PhonenumberLa);
                holder.shope = (ImageView) view.findViewById(R.id.Shop);
                holder.linearLayout=(LinearLayout)view.findViewById(R.id.listclick);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            // Set the results into TextViews
            map=new HashMap<>(position);
            holder.name.setText(animalNamesList.get(position).get("Books"));
           /* holder.ID.setText(scoreDetailsList.get(position).get("id"));
            holder.Number.setText(scoreDetailsList.get(position).get("phone"));*/
            holder.shope.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,LearningElementary.class);
                    intent.putExtra("Class",CLASS);
                    intent.putExtra("Subject",Subject);
                    mContext.startActivity(intent);
                }
            });
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,LearningElementary.class);
                    intent.putExtra("Class",CLASS);
                    intent.putExtra("Subject",Subject);
                    mContext.startActivity(intent);
                }
            });
            return view;
        }
    }
}
