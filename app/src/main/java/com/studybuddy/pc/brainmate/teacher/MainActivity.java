package com.studybuddy.pc.brainmate.teacher;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private Button barcodeScanner;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    // EditText inputSearch;
    String CLASS;
    ListView list;
    ArrayList<HashMap<String, String>> Arraylist;
  //  String[] TRUEFALSE = {"Computer science", "Art & Activity", "G.K", "Hindi", "EVS"};
//    String[] nursery = {"Play", "LKG", "UKG"};


    BuyMedicineAdapter adapter;
    ProgressDialog progressDialog;
    ArrayList<HashMap<String, String>> PatientList;
    String UpcomigID;
    EditText inputSearch;
    ArrayList<HashMap<String, String>> searchResults;

    String accesscodes, Teachers_ID;
    String ClassName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Subjects");
        accesscodes = getIntent().getStringExtra("accesscodes");
        Teachers_ID = getIntent().getStringExtra("Teachers_ID");
        ClassName = getIntent().getStringExtra("ClassName");
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        Arraylist = new ArrayList<>();
        CLASS = getIntent().getStringExtra("Class");
       /* for(int i=0;i<TRUEFALSE.length;i++)
        {
            HashMap<String,String>map1=new HashMap<>();
            map1.put("Subjects",TRUEFALSE[i]);
            map1.put("code","1");
            Arraylist.add(map1);

        }
*/

        list = (ListView) findViewById(R.id.listview25);

        barcodeScanner = (Button) findViewById(R.id.barcodeScanner);
        barcodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //start the scanning activity from the com.google.zxing.client.android.SCAN intent
                    Intent intent = new Intent(ACTION_SCAN);
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, 0);
                } catch (ActivityNotFoundException anfe) {
                    //on catch, show the download dialog
                    showDialog(MainActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
                }
            }
        });
        PatientList = new ArrayList<HashMap<String, String>>();

        /*list = (ListView) findViewById(R.id.listview);
        searchBox = (EditText) findViewById(R.id.inputSearch);
        Search = new ArrayList<>();*/


        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
       // RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this, new HurlStack(null, CertificateClass0S6.getSslSocketFactory(MainActivity.this)));
        String url = "http://www.techive.in/studybuddy/api/teacher_book.php";
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
                                if (c1.getString("status").equals("Inactive")) {
                                    Log.d("imageArray", "ok" + c1.getString("class"));
                                    HashMap<String, String> ObjectiveMap = new HashMap<>();
                                    // ObjectiveMap.put("Title", c1.getString("title"));
                                    ObjectiveMap.put("Class", c1.getString("class"));
                                    if (c1.getString("class").equals(ClassName)) {
                                        ObjectiveMap.put("Subject", c1.getString("subject"));
                                        Arraylist.add(ObjectiveMap);
                                    }
                                    // ObjectiveMap.put("Subject", c1.getString("subject"));
                                    //ObjectiveMap.put("book_img", c1.getString("book_img"));
                                    // ObjectiveMap.put("ebook", c1.getString("ebook"));
                                    // ObjectiveMap.put("manual", c1.getString("manual"));
                                    // ObjectiveMap.put("animation", c1.getString("animation"));
                                    // ObjectiveMap.put("status", c1.getString("status"));
                                    // ObjectiveMap.put("book_Type", "11");
                                    // ObjectiveMap.put("access_code", c1.getString("access_code"));

                                    //   Books_By_Accesscode.add(ObjectiveMap);

                                    Log.d("imageArray1254f", c1.getString("book_img"));
                                }
                            }

                            adapter = new BuyMedicineAdapter(MainActivity.this, Arraylist);
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
                progressDialog.dismiss();
                System.out.println("ResponseRegistration" + error.getMessage());
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
        queue.add(stringRequest);


        inputSearch.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //get the text in the EditText
                String searchString = inputSearch.getText().toString();
                int textLength = searchString.length();
//                searchResults.clear();

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
                Toast toast = Toast.makeText(MainActivity.this, "Content:", Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent(MainActivity.this, Students_Class.class);
                startActivity(intent);
            }
        });        //intializing scan object


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


    //on ActivityResult method
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
    }


    public class BuyMedicineAdapter extends BaseAdapter {

        // Declare Variables

        MainActivity mContext;
        LayoutInflater inflater;
        ArrayList<HashMap<String, String>> animalNamesList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        String upcomigID;


        public BuyMedicineAdapter(MainActivity context, ArrayList<HashMap<String, String>> animalNamesList) {
            mContext = context;
            this.upcomigID = upcomigID;
            this.animalNamesList = animalNamesList;
            inflater = LayoutInflater.from((Context) mContext);
            Log.d("Sssssssslist", String.valueOf("Adapter" + animalNamesList));

        }

        public class ViewHolder {
            TextView name, ID, Number;
            // Button details,Medicalhistory;
            LinearLayout linearLayout;
            //  ImageView imgg;
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
                view = inflater.inflate(R.layout.sujectslayout, null);
                // Locate the TextViews in listview_item.xml
                holder.name = (TextView) view.findViewById(R.id.SubjectsName);
               /* holder.ID = (TextView) view.findViewById(R.id.nameLabel);
                holder.Number = (TextView) view.findViewById(R.id.PhonenumberLa);
*/
                holder.linearLayout = (LinearLayout) view.findViewById(R.id.listclick);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            // Set the results into TextViews
            map = new HashMap<>(position);
            holder.name.setText(animalNamesList.get(position).get("Subject"));
           /* holder.ID.setText(scoreDetailsList.get(position).get("id"));
            holder.Number.setText(scoreDetailsList.get(position).get("phone"));*/


            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, Books_Access_Code.class);
                    intent.putExtra("Class", CLASS);
                    intent.putExtra("Subject", animalNamesList.get(position).get("Subjects"));
                    intent.putExtra("accesscodes", accesscodes);
                    intent.putExtra("Teachers_ID", Teachers_ID);
                    intent.putExtra("Subject", animalNamesList.get(position).get("Subject"));
                    mContext.startActivity(intent);

                }
            });
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, Books_Access_Code.class);
                    intent.putExtra("Class", CLASS);
                    intent.putExtra("Subject", animalNamesList.get(position).get("Subjects"));
                    intent.putExtra("accesscodes", accesscodes);
                    intent.putExtra("Teachers_ID", Teachers_ID);
                    intent.putExtra("Subject", animalNamesList.get(position).get("Subject"));
                    mContext.startActivity(intent);
                }
            });
            return view;
        }


    }
    //Getting the scan results


}
