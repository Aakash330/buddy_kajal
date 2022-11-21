package com.studybuddy.pc.brainmate.student;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import android.widget.CheckBox;
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
import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.mains.Apis;
import com.studybuddy.pc.brainmate.teacher.Main2Activity;
import com.studybuddy.pc.brainmate.teacher.Students_Class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Students_Chapters extends AppCompatActivity {

    StudentChapterAdapter adapter;
    ProgressDialog progressDialog;
    ArrayList<HashMap<String, String>> chapterList;
    String UpcomigID;
    EditText inputSearch;
    ArrayList<HashMap<String, String>> searchResults;
    ListView list;
    Button Continue;
    String choosen_ID = "";
    String access_code = "", Subject = "";
    Toolbar toolbarHeader;
    String from = "";
    String Ch_name = "";

    LinearLayout chapterLNotFoundLty,linearList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students__chapters);
        toolbarHeader = findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbarHeader);

        chapterLNotFoundLty=findViewById(R.id.chapterNotFoundLyt);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbarHeader.setTitleTextColor(getResources().getColor(R.color.white));
            //toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow);
            //getSupportActionBar().setTitle("Techive");
        }
        setTitle("Chapters");
        from = getIntent().getStringExtra("from") != null ? getIntent().getStringExtra("from") : "";
        access_code = getIntent().getStringExtra("access_code") != null ? getIntent().getStringExtra("access_code") : "";
        Subject = getIntent().getStringExtra("Subject") != null ? getIntent().getStringExtra("Subject") : "";
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        list = (ListView) findViewById(R.id.listview);
        Continue = (Button) findViewById(R.id.Continue);
        linearList=findViewById(R.id.listviewLty);
        chapterList = new ArrayList<HashMap<String, String>>();

        progressDialog = new ProgressDialog(Students_Chapters.this);
        progressDialog.setMessage("Loading..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
        //RequestQueue queue = Volley.newRequestQueue(Students_Chapters.this);
        //String url = "http://www.techive.in/studybuddy/api/chapter.php";
        String url = Apis.base_url + Apis.chapter_url;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ViewPatient1254", response);
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            Log.d("ChapterStatus", "" + jsonObject1.getString("success"));
                            String LoginCredential = jsonObject1.getString("success");
                            JSONArray heroArray = jsonObject1.getJSONArray("data");
                            if (heroArray.length() > 0) {
                                for (int i = 0; i < heroArray.length(); i++) {
                                    JSONObject c = heroArray.getJSONObject(i);
                                    String ChapterID = c.getString("ch_id");
                                    String ChapterNo = c.getString("ch_no");
                                    String ChapterName = c.getString("ch_name");
                                    Log.d("datag", ChapterID + "   " + ChapterNo + "  " + ChapterName);
                                    HashMap<String, String> contact = new HashMap<>();
                                    contact.put("ChapterID", ChapterID);
                                    contact.put("ChapterNo", ChapterNo);
                                    contact.put("ChapterName", ChapterName);
                                    contact.put("isSelected", "0");
                                    // adding contact to contact list
                                    Log.d("sarejhaseachha", String.valueOf(contact));
                                    chapterList.add(contact);
                                }
                                //chapterList.add(PateintDATA);
                                searchResults = new ArrayList<>((Collection<? extends HashMap<String, String>>) chapterList);
                                //NOTE : Code for sorting arraylist of hashmap type
                                ArrayList<HashMap<String, String>> arrayList = chapterList;
                                Collections.sort(arrayList, new Comparator<HashMap<String, String>>() {
                                    @Override
                                    public int compare(HashMap<String, String> lhs, HashMap<String, String> rhs) {
                                        int id_1 = Integer.parseInt(lhs.get("ChapterNo") != null && !lhs.get("ChapterNo").isEmpty() ? lhs.get("ChapterNo") : "0");
                                        int id_2 = Integer.parseInt(rhs.get("ChapterNo") != null && !rhs.get("ChapterNo").isEmpty() ? rhs.get("ChapterNo") : "0");
                                        /*For ascending order*/
                                        return id_1 - id_2;
                                    }
                                });
                                //
                                if(chapterList.size()==0){
                                    linearList.setVisibility(View.GONE);
                                    chapterLNotFoundLty.setVisibility(View.VISIBLE);

                                }else{
                                    linearList.setVisibility(View.VISIBLE);
                                    chapterLNotFoundLty.setVisibility(View.GONE);
                                    adapter = new StudentChapterAdapter(Students_Chapters.this, chapterList);
                                    list.setAdapter(adapter);
                                }


                            } else {
                                Toast.makeText(Students_Chapters.this, getString(R.string.no_chapter), Toast.LENGTH_SHORT).show();
                            }
                            Log.d("Sssssssslist", String.valueOf(chapterList));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                if (volleyError instanceof NoConnectionError) {
                    Toast.makeText(Students_Chapters.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Students_Chapters.this, R.string.some_error_occurred, Toast.LENGTH_SHORT).show();
                }
            }
        }) {

            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                java.util.Map<String, String> params = new HashMap<>();
                params.put("accesscodes", access_code);

                return params;
            }
        };
        // Add the request to the RequestQueue.
        CommonMethods.callWebserviceForResponse(stringRequest, Students_Chapters.this);


        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choosen_ID != null && !choosen_ID.equals("")) {
                    Intent intent = new Intent(Students_Chapters.this, StudentTest.class);
                    intent.putExtra("ChapterID", choosen_ID);
                    intent.putExtra("Subject", Subject);
                    intent.putExtra("Ch_name", Ch_name);
                    intent.putExtra("from", from);
                    startActivity(intent);
                    Log.d("chname", Ch_name);
                } else {
                    Toast.makeText(Students_Chapters.this, "Nothing Selected.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(Students_Chapters.this, "Content:", Toast.LENGTH_LONG);
                toast.show();
                Intent intent = new Intent(Students_Chapters.this, Students_Class.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_go_home:
                if (!from.equals("") && from.equals("s")) {
                    Intent intent = new Intent(Students_Chapters.this, Stu_Classes.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("accesscodes", CommonMethods.getAccessCode(Students_Chapters.this));
                    intent.putExtra("Student_ID", CommonMethods.getId(Students_Chapters.this));
                    startActivity(intent);
                } else if (!from.equals("") && from.equals("t")) {

                    Intent intent = new Intent(Students_Chapters.this, Main2Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("accesscodes", CommonMethods.getAccessCode(Students_Chapters.this));
                    intent.putExtra("Teachers_ID", CommonMethods.getId(Students_Chapters.this));
                    startActivity(intent);
                }
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

    public class StudentChapterAdapter extends BaseAdapter {

        // Declare Variables
        ArrayList<HashMap<String, String>> books;
        Students_Chapters mContext;
        LayoutInflater inflater;
        ArrayList<HashMap<String, String>> animalNamesList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        String upcomigID;
        int selected_position = -1;


        public StudentChapterAdapter(Students_Chapters studentdashBord, ArrayList<HashMap<String, String>> booksname) {
            this.mContext = studentdashBord;
            this.books = booksname;
            inflater = LayoutInflater.from((Context) mContext);
        }

        public class ViewHolder {
            TextView name, ID, Number, heading, chaptername;
            // Button details,Medicalhistory;
            LinearLayout linearLayout;
            ImageView imgg;
            CheckBox checkboxChapter;
        }

        @Override
        public int getCount() {
            return books.size();
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
                view = inflater.inflate(R.layout.chapterslisrt, null);
                holder.checkboxChapter = (CheckBox) view.findViewById(R.id.checkboxChapter);
                holder.heading = (TextView) view.findViewById(R.id.headingCh);
                holder.chaptername = (TextView) view.findViewById(R.id.chaptername);
                holder.linearLayout = (LinearLayout) view.findViewById(R.id.listclick);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            // Set the results into TextViews
            map = new HashMap<>(position);

            if (selected_position == position) {
                holder.checkboxChapter.setChecked(true);
            } else {
                holder.checkboxChapter.setChecked(false);
            }
            holder.checkboxChapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (((CheckBox) view).isChecked()) {
                        selected_position = position;
                        choosen_ID = books.get(selected_position).get("ChapterID");
                        Ch_name = books.get(selected_position).get("ChapterName");
                    } else {
                        selected_position = -1;
                    }
                    notifyDataSetChanged();
                }
            });
            holder.heading.setText("Chapter No. " + books.get(position).get("ChapterNo"));
            holder.chaptername.setText(books.get(position).get("ChapterName"));
            return view;
        }
    }
}
