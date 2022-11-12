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
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.studybuddy.pc.brainmate.Network_connection.utils.NetworkUtil;
import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.mains.Apis;
import com.studybuddy.pc.brainmate.student.CommonMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Book_Subjects extends AppCompatActivity {

    //private Button barcodeScanner;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    // EditText inputSearch;
    String CLASS;
    ListView list;
    ArrayList<HashMap<String, String>> Arraylist;
    BookSubjectAdapter adapter;
    ProgressDialog progressDialog;
    ArrayList<HashMap<String, String>> PatientList;
    String UpcomigID;
    //EditText inputSearch;
    ArrayList<HashMap<String, String>> searchResults;
    String accesscodes, Teachers_ID;
    String ClassName, displayClassName;
    RecyclerView Audiolist, Imageslist, PDFlist;
    ImageslistAdapter imageslistAdapter;
    private LinearLayoutManager horizontalLayoutManager;
    ArrayList<HashMap<String, String>> CatalogArray;
    int count = 1;
    int inn = 0;
    Toolbar toolbarHeader;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book__subjects);
        context = Book_Subjects.this;
        toolbarHeader = findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbarHeader);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbarHeader.setTitleTextColor(getResources().getColor(R.color.white));
            //toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow);
            //getSupportActionBar().setTitle("Techive");
        }
        setTitle("Subjects");
        accesscodes = getIntent().getStringExtra("accesscodes");
        Teachers_ID = getIntent().getStringExtra("Teachers_ID");
        ClassName = getIntent().getStringExtra("ClassName");
        displayClassName = getIntent().getStringExtra("Class");
        Arraylist = new ArrayList<>();
        CatalogArray = new ArrayList<>();
        CLASS = getIntent().getStringExtra("Class");
        Imageslist = (RecyclerView) findViewById(R.id.Imageslist1);
        autoScrollAnother();
        list = (ListView) findViewById(R.id.listview25);
        PatientList = new ArrayList<HashMap<String, String>>();
        progressDialog = new ProgressDialog(Book_Subjects.this);
        progressDialog.setMessage("Loading..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        //RequestQueue queue = Volley.newRequestQueue(Book_Subjects.this);
        String url = Apis.base_url + Apis.teacher_book_url;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("1400019211", response);
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
                                    String str = c1.getString("class");
                                    if (str.equalsIgnoreCase("LKG")) {
                                        Log.d("lkg111", "" + String.valueOf(++inn));
                                    }
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
                            for (int i = 0; i < Arraylist.size(); i++) {

                                for (int j = i + 1; j < Arraylist.size(); j++) {
                                    if (Arraylist.get(i).equals(Arraylist.get(j))) {
                                        Arraylist.remove(j);
                                        j--;
                                    }
                                }
                            }
                            if (heroArray != null && heroArray.length() > 0 && ClassName.equals("Nursery")) {
                                HashMap<String, String> ObjectiveMap1 = new HashMap<>();
                                ObjectiveMap1.put("Subject", "Fun Activities");
                                Arraylist.add(ObjectiveMap1);
                            }
                            if (heroArray != null && heroArray.length() > 0 && ClassName.equals("LKG")) {
                                HashMap<String, String> ObjectiveMap1 = new HashMap<>();
                                ObjectiveMap1.put("Subject", "Fun Activities");
                                Arraylist.add(ObjectiveMap1);
                            }
                            if (heroArray != null && heroArray.length() > 0 && ClassName.equals("UKG")) {
                                HashMap<String, String> ObjectiveMap1 = new HashMap<>();
                                ObjectiveMap1.put("Subject", "Fun Activities");
                                Arraylist.add(ObjectiveMap1);
                            }
                            adapter = new BookSubjectAdapter(Book_Subjects.this, Arraylist);
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
                //params.put("accesscodes", accesscodes);
                params.put("teacher_id", Teachers_ID);
                //params.put("teacher_id", "5");
                Log.d("classname", ClassName + "\n" + Teachers_ID);
                return params;
            }
        };
        CommonMethods.callWebserviceForResponse(stringRequest, context);

        horizontalLayoutManager = new LinearLayoutManager(Book_Subjects.this, LinearLayoutManager.HORIZONTAL, false);
        Imageslist.setLayoutManager(horizontalLayoutManager);
        horizontalLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //RequestQueue Recqueue = Volley.newRequestQueue(Book_Subjects.this);
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
        //Recqueue.add(RecstringRequest);
        CommonMethods.callWebserviceForResponse(RecstringRequest, context);

        /*inputSearch.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //get the text in the EditText
                String searchString = inputSearch.getText().toString();
                int textLength = searchString.length();
//                searchResults.clear();

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

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            public void afterTextChanged(Editable s) {


            }
        });*/


        /*list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (NetworkUtil.getConnectivityStatus(Book_Subjects.this) > 0) {
                    Intent intent = new Intent(Book_Subjects.this, Students_Class.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Book_Subjects.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }

            }
        });*/
    }


   /* private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
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
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_go_home:
                //finish();
                Intent intent = new Intent(Book_Subjects.this, Main2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("accesscodes", CommonMethods.getAccessCode(Book_Subjects.this));
                intent.putExtra("Teachers_ID", CommonMethods.getId(Book_Subjects.this));
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //region "Menu"
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //region "BookSubjectAdapter"
    public class BookSubjectAdapter extends BaseAdapter {

        Book_Subjects mContext;
        LayoutInflater inflater;
        ArrayList<HashMap<String, String>> animalNamesList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        String upcomigID;


        public BookSubjectAdapter(Book_Subjects context, ArrayList<HashMap<String, String>> animalNamesList) {
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
                holder.Number = (TextView) view.findViewById(R.id.PhonenumberLa);*/
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
                    if (animalNamesList.get(position).get("Subject").equals("Fun Activities")) {
                        Intent intent = new Intent(Book_Subjects.this, FunActivity.class);
                        intent.putExtra("displayClassName", displayClassName);
                        startActivity(intent);
                    } else if (!animalNamesList.get(position).get("Subject").equals("Fun Activities") && NetworkUtil.getConnectivityStatus(Book_Subjects.this) > 0) {
                        Intent intent = new Intent(mContext, Books_Access_Code.class);
                        intent.putExtra("ClassName", ClassName);
                        intent.putExtra("displayClassName", displayClassName);
                        intent.putExtra("Subject", animalNamesList.get(position).get("Subjects"));
                        intent.putExtra("accesscodes", accesscodes);
                        intent.putExtra("Teachers_ID", Teachers_ID);
                        intent.putExtra("Subject", animalNamesList.get(position).get("Subject"));
                        mContext.startActivity(intent);
                    } else {
                        Toast.makeText(Book_Subjects.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                    }
                    //0CommonMethods.showToast(Book_Subjects.this,""+displayClassName);
                }
            });
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (animalNamesList.get(position).get("Subject").equals("Fun Activities")) {
                        Intent intent = new Intent(Book_Subjects.this, FunActivity.class);
                        intent.putExtra("displayClassName", displayClassName);
                        startActivity(intent);
                    } else if (!animalNamesList.get(position).get("Subject").equals("Fun Activities") && NetworkUtil.getConnectivityStatus(Book_Subjects.this) > 0) {
                        Intent intent = new Intent(mContext, Books_Access_Code.class);
                        intent.putExtra("ClassName", ClassName);
                        intent.putExtra("Subject", animalNamesList.get(position).get("Subjects"));
                        intent.putExtra("displayClassName", displayClassName);
                        intent.putExtra("accesscodes", accesscodes);
                        intent.putExtra("Teachers_ID", Teachers_ID);
                        intent.putExtra("Subject", animalNamesList.get(position).get("Subject"));
                        mContext.startActivity(intent);
                    } else {
                        Toast.makeText(Book_Subjects.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                    }
                    //CommonMethods.showToast(Book_Subjects.this,""+displayClassName);
                }
            });
            return view;
        }
    }

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
            imageslistAdapter = new ImageslistAdapter(CatalogArray, Book_Subjects.this) {
                @Override
                public void load() {
                    //CatalogArray.addAll(CatalogArray);
                }
            };
            Imageslist.setAdapter(imageslistAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(Book_Subjects.this) {
                @Override
                public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                    LinearSmoothScroller smoothScroller = new LinearSmoothScroller(Book_Subjects.this) {
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
