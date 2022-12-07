package com.studybuddy.pc.brainmate.student;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Take_Test extends AppCompatActivity {

    LinearLayout ObjectiveLayout,ObjectiveplushsubLayout,Fillintheblanklayout;
    ListView Objectivelist,TrueFalse,Fillintheblanklist;
    String [] Quetions={"WWW stands for ?","Which of the following are components of Central Processing Unit (CPU) ?"," Which among following first generation of computers had ?","Where is RAM located ?"," If a computer has more than one processor then it is known as ?"};
    StudentOjectiveAdapter adapter;
    String [] TRUEFALSE={"WWW stands for ____ ?","Which of the following are ____ of Central Processing Unit (CPU) ?"," Which among following first generation of ____ had ?","Where is RAM located ?"," If a computer has more than one ____ then it is known as ?"};
    // ProgressDialog progressDialog;
    ArrayList<HashMap<String, String>> PatientList;
    String UpcomigID;
    EditText inputSearch;
    ArrayList< HashMap < String,String > > searchResults;
    ArrayList< HashMap < String,String > >Arraylist;
    ArrayList< HashMap < String,String > >Arraylist2;
    int displainglist=1;
    StudentTureFalseAdapter trufalseAdapter;
    ArrayList<HashMap<String, String>> turefalesAraylist;

    StudentFillinTheblankApter fillinTheblank;
    ArrayList<HashMap<String, String>> fillinTheblanklist;

    int objectivecount,truefalsecount,fillintyheblanks,Totalcpont;
    Button Next,Generate;
    TextView totalQuestions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take__test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        totalQuestions=(TextView)findViewById(R.id.totalQuestions) ;
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

       // Subjectivelayout=(LinearLayout)findViewById(R.id.Subjectivelayout) ;
        ObjectiveLayout=(LinearLayout)findViewById(R.id.ObjectiveLayout) ;
        ObjectiveplushsubLayout=(LinearLayout)findViewById(R.id.ObjectiveplushsubLayout) ;
        Fillintheblanklayout=(LinearLayout)findViewById(R.id.Fillintheblanklayout);

      //  Subjectivelist=(ListView)findViewById(R.id.Subjectivelist);
        Objectivelist=(ListView)findViewById(R.id.Objectivelist);
        TrueFalse=(ListView)findViewById(R.id.true_false_list);
        Fillintheblanklist=(ListView)findViewById(R.id.Fillintheblanklist);


       /* SubjectiveButton=(Button)findViewById(R.id.SubjectiveButton);
        ObjectiveButton=(Button)findViewById(R.id.ObjectiveButton);
        ObjectiveplushsubButton=(Button)findViewById(R.id.ObjectiveplushsubButton);
        FillintheblankButton=(Button)findViewById(R.id.FillintheblankButton);*/

        Next=(Button)findViewById(R.id.Next);
        Generate=(Button)findViewById(R.id.Generate);
       /* Optionbutton=(Button)findViewById(R.id.fab) ;
        Addbuitton=(Button)findViewById(R.id.add) ;*/
       // check=(Button)findViewById(R.id.check) ;

        Arraylist=new ArrayList<>();
        for(int i=0;i<Quetions.length;i++){
            HashMap<String,String>map1=new HashMap<>();
            map1.put("Quetion",Quetions[i]);
            map1.put("code","1");

            Arraylist.add(map1);

        }
        Arraylist2=new ArrayList<>();
        for(int i=0;i<TRUEFALSE.length;i++){
            HashMap<String,String>map3=new HashMap<>();
            map3.put("Quetion",TRUEFALSE[i]);
            map3.put("code","1");

            Arraylist2.add(map3);

        }
       /* if(displainglist==1)
        {
           // Subjectivelayout.setVisibility(View.VISIBLE);
            ObjectiveLayout.setVisibility(View.GONE);
            ObjectiveplushsubLayout.setVisibility(View.GONE);
            Fillintheblanklayout.setVisibility(View.GONE);
            SectionName.setText("Subjective");
            Sectionnumber.setText("1/4");

        }
        else*/ if(displainglist==1)
        {
           // Subjectivelayout.setVisibility(View.GONE);
            ObjectiveLayout.setVisibility(View.VISIBLE);
            ObjectiveplushsubLayout.setVisibility(View.GONE);
            Fillintheblanklayout.setVisibility(View.GONE);
           /* SectionName.setText("Objective");
            Sectionnumber.setText("2/4");*/

        }
        else if(displainglist==2)
        {
           // Subjectivelayout.setVisibility(View.GONE);
            ObjectiveLayout.setVisibility(View.GONE);
            ObjectiveplushsubLayout.setVisibility(View.VISIBLE);
            Fillintheblanklayout.setVisibility(View.GONE);

           /* SectionName.setText("True/False");

            Sectionnumber.setText("3/4");*/
        }
        else if(displainglist==3)
        {
            //Subjectivelayout.setVisibility(View.GONE);
            ObjectiveLayout.setVisibility(View.GONE);
            ObjectiveplushsubLayout.setVisibility(View.GONE);
            Fillintheblanklayout.setVisibility(View.VISIBLE);
           /* SectionName.setText("Fill In The Blanks");
            Sectionnumber.setText("4/4");*/
        }
        Totalcpont=objectivecount+truefalsecount+fillintyheblanks;
       // subjectiveAraylist = new ArrayList<HashMap<String, String>>();
        turefalesAraylist = new ArrayList<HashMap<String, String>>();

        inputSearch=(EditText)findViewById(R.id.inputSearch);

        trufalseAdapter = new StudentTureFalseAdapter(this, Arraylist);
        TrueFalse.setAdapter(trufalseAdapter);

       /* StudentsubjectiveAdapter = new StudentTureFalseAdapter(Take_Test.this, Arraylist);
        Subjectivelist.setAdapter(StudentsubjectiveAdapter);*/


        fillinTheblank = new StudentFillinTheblankApter(Take_Test.this, Arraylist2);
        Fillintheblanklist.setAdapter(fillinTheblank);

        PatientList = new ArrayList<HashMap<String, String>>();
        adapter = new StudentOjectiveAdapter(Take_Test.this, Arraylist);
        Objectivelist.setAdapter(adapter);
        /*progressDialog = new ProgressDialog(QuetionPaper.this);
        progressDialog.setMessage("Loading..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);*/
       // RequestQueue queue = Volley.newRequestQueue(Take_Test.this);
        RequestQueue queue = Volley.newRequestQueue(Take_Test.this, new HurlStack(null, CertificateClass0S6.getSslSocketFactory(Take_Test.this)));
        String url = "http://techive.in/medical_api/patient_data.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ViewPatient", response);
                        //  progressDialog.dismiss();
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
                            //chapterList.add(PateintDATA);
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
                // progressDialog.dismiss();
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

       /* Subjectivelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(Take_Test.this, "Content:", Toast.LENGTH_LONG);
                toast.show();
                Intent intent=new Intent(Take_Test.this,Students_Class.class);
                startActivity(intent);
            }
        });        //intializing scan object
*/




       /* progressDialog = new ProgressDialog(QuetionPaper.this);
        progressDialog.setMessage("Loading..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);*/
        //RequestQueue queue1 = Volley.newRequestQueue(Take_Test.this);
        RequestQueue queue1 = Volley.newRequestQueue(Take_Test.this, new HurlStack(null,CertificateClass0S6.getSslSocketFactory(Take_Test.this)));
        String url1 = "http://techive.in/medical_api/patient_data.php";
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ViewPatient", response);
                        //  progressDialog.dismiss();
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
                            //chapterList.add(PateintDATA);
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
                // progressDialog.dismiss();
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
        queue1.add(stringRequest1);



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

       /* Objectivelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(Take_Test.this, "Content:", Toast.LENGTH_LONG);
                toast.show();
                Intent intent=new Intent(Take_Test.this,Students_Class.class);
                startActivity(intent);
            }
        });        //intializing scan object

        Addbuitton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Take_Test.this);
                dialog.setContentView(R.layout.adddialog);
                dialog.setTitle("Custom Dialog");
                dialog.show();
                Button Subjective=(Button)dialog.findViewById(R.id.Subjective);
                Subjective.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Take_Test.this,"Questiond Added",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
            }
        });*/

        //  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
     /*   Optionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Take_Test.this);
                dialog.setContentView(R.layout.testgenrate);
                dialog.setTitle("Custom Dialog");
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
                Button Subjective=(Button)dialog.findViewById(R.id.Subjective);
                Button Objective=(Button)dialog.findViewById(R.id.Objective);
                Button SubObj=(Button)dialog.findViewById(R.id.SubObj);
                Button fillintheblank=(Button)dialog.findViewById(R.id.fillintheblank);

               *//* Subjective.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Subjectivelayout.setVisibility(View.VISIBLE);
                        ObjectiveLayout.setVisibility(View.GONE);
                        ObjectiveplushsubLayout.setVisibility(View.GONE);
                        Fillintheblanklayout.setVisibility(View.GONE);
                        dialog.dismiss();
                    }
                });*//*
                Objective.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      //  Subjectivelayout.setVisibility(View.GONE);
                        ObjectiveLayout.setVisibility(View.VISIBLE);
                        ObjectiveplushsubLayout.setVisibility(View.GONE);
                        Fillintheblanklayout.setVisibility(View.GONE);
                        dialog.dismiss();
                    }
                });
                SubObj.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      //  Subjectivelayout.setVisibility(View.GONE);
                        ObjectiveLayout.setVisibility(View.GONE);
                        ObjectiveplushsubLayout.setVisibility(View.VISIBLE);
                        Fillintheblanklayout.setVisibility(View.GONE);
                        dialog.dismiss();
                    }
                });
                fillintheblank.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Subjectivelayout.setVisibility(View.GONE);
                        ObjectiveLayout.setVisibility(View.GONE);
                        ObjectiveplushsubLayout.setVisibility(View.GONE);
                        Fillintheblanklayout.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                    }
                });
            }
        });*/

       /* check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Take_Test.this,PaperConfirmition.class);
                startActivity(intent);
            }
        });*/



        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if(displainglist==1)
                {
                   // Subjectivelayout.setVisibility(View.VISIBLE);
                    ObjectiveLayout.setVisibility(View.GONE);
                    ObjectiveplushsubLayout.setVisibility(View.GONE);
                    Fillintheblanklayout.setVisibility(View.GONE);
                    displainglist=2;
                   *//* SectionName.setText("Subjective");
                    Sectionnumber.setText("1/4");*//*
                }
                else*/
                if(displainglist==1)
                {
                   // Subjectivelayout.setVisibility(View.GONE);
                    ObjectiveLayout.setVisibility(View.VISIBLE);
                    ObjectiveplushsubLayout.setVisibility(View.GONE);
                    Fillintheblanklayout.setVisibility(View.GONE);
                    displainglist=2;
                   /*
                   SectionName.setText("Objective");
                    Sectionnumber.setText("2/4");
                    */
                }
                else if(displainglist==2)
                {
                    //Subjectivelayout.setVisibility(View.GONE);
                    ObjectiveLayout.setVisibility(View.GONE);
                    ObjectiveplushsubLayout.setVisibility(View.VISIBLE);
                    Fillintheblanklayout.setVisibility(View.GONE);
                    displainglist=3;
                   /*
                    SectionName.setText("True/False");
                    Sectionnumber.setText("3/4");

                    */
                }
                else if(displainglist==3)
                {
                   // Subjectivelayout.setVisibility(View.GONE);
                    ObjectiveLayout.setVisibility(View.GONE);
                    ObjectiveplushsubLayout.setVisibility(View.GONE);
                    Fillintheblanklayout.setVisibility(View.VISIBLE);
                    displainglist=4;
                    Next.setText("Complete");
                   /*

                   SectionName.setText("Fill In The Blanks");
                   Sectionnumber.setText("4/4");

                    */
                }
            }
        });
        Totalcpont=truefalsecount+fillintyheblanks+objectivecount;
        totalQuestions.setText(String.valueOf("Total Ques "+Totalcpont));


    }
    public class StudentOjectiveAdapter extends BaseAdapter {
        String code;
        // Declare Variables
        ArrayList<HashMap<String, String>> books3;

        Take_Test mContext;
        LayoutInflater inflater;
        ArrayList<HashMap<String, String>> animalNamesList = new ArrayList<HashMap<String, String>>();
        HashMap<String,String>map;
        String upcomigID;

/*

        public STUDENTDashbroadAdapter(StudentdashBord context, ArrayList<HashMap<String, String>> animalNamesList, String upcomigID) {
            mContext = context;
            this.upcomigID=upcomigID;
            this.animalNamesList = animalNamesList;
            inflater = LayoutInflater.from((Context) mContext);


            Log.d("Sssssssslist", String.valueOf("Adapter"+animalNamesList));

        }
*/

        public StudentOjectiveAdapter(Take_Test studentdashBord, ArrayList<HashMap<String, String>> booksname) {
            this.mContext=studentdashBord;
            this.books3=booksname;
          /*  inflater = LayoutInflater.from((Context) mContext);*/

        }

        public class ViewHolder {
            TextView name,ID,Number,heading,heading2;
            // Button details,Medicalhistory;
            ImageView imgg;
            RadioButton option1,option2,option3,option4;
        }

        @Override
        public int getCount() {
            objectivecount=books3.size();
            return books3.size();
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
            code=books3.get(position).get("code");
            Log.d("BooksCode",code);
            if (view == null) {
                holder = new ViewHolder();
                inflater = LayoutInflater.from((Context) mContext);
                view = inflater.inflate(R.layout.studentobjetivequelist, null);



                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
                // Locate the TextViews in listview_item.xml

                /* holder.imgg = (ImageView) view.findViewById(R.id.imgg);*/
                holder.heading = (TextView) view.findViewById(R.id.heading);
                holder.heading2 = (TextView) view.findViewById(R.id.heading2);

                holder.option1=(RadioButton)view.findViewById(R.id.Option1);
                holder.option2=(RadioButton)view.findViewById(R.id.Option2);
                holder.option3=(RadioButton)view.findViewById(R.id.Option3);
                holder.option4=(RadioButton)view.findViewById(R.id.Option4);

            // Set the results into TextViews
            map=new HashMap<>(position);

            holder.heading.setText(books3.get(position).get("Quetion"));
           holder.option1.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   holder.option1.setChecked(true);
                   holder.option2.setChecked(false);
                   holder.option3.setChecked(false);
                   holder.option4.setChecked(false);
               }
           });
            holder.option2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.option1.setChecked(false);
                    holder.option2.setChecked(true);
                    holder.option3.setChecked(false);
                    holder.option4.setChecked(false);
                }
            });
            holder.option3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.option1.setChecked(false);
                    holder.option2.setChecked(false);
                    holder.option3.setChecked(true);
                    holder.option4.setChecked(false);
                }
            });
            holder.option4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.option1.setChecked(false);
                    holder.option2.setChecked(false);
                    holder.option3.setChecked(false);
                    holder.option4.setChecked(true);
                }
            });
//            holder.heading2.setText(books.get(position).get("Quetion"));
          /*  holder.imgg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,StudebtElimentery.class);
                    mContext.startActivity(intent);

                }
            });*/
            return view;
        }


    }
    public class StudentTureFalseAdapter extends BaseAdapter {

        // Declare Variables
        ArrayList<HashMap<String, String>> books2;

        Take_Test mContext;
        LayoutInflater inflater;
        ArrayList<HashMap<String, String>> animalNamesList = new ArrayList<HashMap<String, String>>();
        HashMap<String,String>map;
        String upcomigID;

/*

        public STUDENTDashbroadAdapter(StudentdashBord context, ArrayList<HashMap<String, String>> animalNamesList, String upcomigID) {
            mContext = context;
            this.upcomigID=upcomigID;
            this.animalNamesList = animalNamesList;
            inflater = LayoutInflater.from((Context) mContext);


            Log.d("Sssssssslist", String.valueOf("Adapter"+animalNamesList));

        }
*/

        public StudentTureFalseAdapter(Take_Test studentdashBord, ArrayList<HashMap<String, String>> booksname) {
            this.mContext=studentdashBord;
            this.books2=booksname;
            inflater = LayoutInflater.from((Context) mContext);

        }

        public class ViewHolder {
            TextView name,ID,Number,heading;
            // Button details,Medicalhistory;
            ImageView imgg;
            CheckBox checkbox;
            RadioButton trueee,Falsee;
        }

        @Override
        public int getCount()
        {
            truefalsecount=books2.size();
            return books2.size();

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
                view = inflater.inflate(R.layout.studenttruefalselist, null);
                // Locate the TextViews in listview_item.xml

                /* holder.imgg = (ImageView) view.findViewById(R.id.imgg);*/
                holder.heading = (TextView) view.findViewById(R.id.heading);
                holder.checkbox = (CheckBox) view.findViewById(R.id.checkbox);
                holder.trueee=(RadioButton)view.findViewById(R.id.truee);
                holder.Falsee=(RadioButton)view.findViewById(R.id.falses);


                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            // Set the results into TextViews
            map=new HashMap<>(position);

            holder.heading.setText(books2.get(position).get("Quetion"));
            holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(holder.checkbox.isChecked()==true){

                    }
                }
            });
            holder.trueee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.trueee.setChecked(true);
                    holder.Falsee.setChecked(false);
                }
            });

            holder.Falsee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.trueee.setChecked(false);
                    holder.Falsee.setChecked(true);
                }
            });
          /*  holder.imgg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,StudebtElimentery.class);
                    mContext.startActivity(intent);

                }
            });*/
            return view;
        }


    }
    public class StudentFillinTheblankApter extends BaseAdapter {

        // Declare Variables
        ArrayList<HashMap<String, String>> books1;

        Take_Test mContext;
        LayoutInflater inflater;
        ArrayList<HashMap<String, String>> animalNamesList = new ArrayList<HashMap<String, String>>();
        HashMap<String,String>map;
        String upcomigID;

/*

        public STUDENTDashbroadAdapter(StudentdashBord context, ArrayList<HashMap<String, String>> animalNamesList, String upcomigID) {
            mContext = context;
            this.upcomigID=upcomigID;
            this.animalNamesList = animalNamesList;
            inflater = LayoutInflater.from((Context) mContext);


            Log.d("Sssssssslist", String.valueOf("Adapter"+animalNamesList));

        }
*/

        public StudentFillinTheblankApter(Take_Test studentdashBord, ArrayList<HashMap<String, String>> booksname) {
            this.mContext=studentdashBord;
            this.books1=booksname;
            inflater = LayoutInflater.from((Context) mContext);

        }

        public class ViewHolder {
            TextView name,ID,Number,heading;
            // Button details,Medicalhistory;
            ImageView imgg;
        }

        @Override
        public int getCount() {
            fillintyheblanks=books1.size();
            return books1.size();
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
                view = inflater.inflate(R.layout.studentfileintheblanck, null);
                // Locate the TextViews in listview_item.xml

                /* holder.imgg = (ImageView) view.findViewById(R.id.imgg);*/
                holder.heading = (TextView) view.findViewById(R.id.heading);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            // Set the results into TextViews
            map=new HashMap<>(position);

            holder.heading.setText(books1.get(position).get("Quetion"));
          /*  holder.imgg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,StudebtElimentery.class);
                    mContext.startActivity(intent);

                }
            });*/
            return view;
        }














    }
















}
