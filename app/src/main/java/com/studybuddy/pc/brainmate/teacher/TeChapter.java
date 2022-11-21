package com.studybuddy.pc.brainmate.teacher;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.mains.Apis;
import com.studybuddy.pc.brainmate.student.CommonMethods;
import com.studybuddy.pc.brainmate.student.Students_Chapters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeChapter extends AppCompatActivity {

    ChapterAdapter adapter;
    ProgressDialog progressDialog;
    ArrayList<HashMap<String, String>> chapList;
    String UpcomigID;
    EditText inputSearch;
    ArrayList<HashMap<String, String>> searchResults;
    ListView list;
    Button Continue;
    ArrayList<String> Chapters_ID;
    String Class, Subject, displayClassName;
    String access_code;
    Toolbar toolbarHeader;
    LinearLayout chNotFound,tc_chapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_te_chapter);

        toolbarHeader = findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbarHeader);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbarHeader.setTitleTextColor(getResources().getColor(R.color.white));

            chNotFound=findViewById(R.id.chapterNotFoundLyt);
            tc_chapter=findViewById(R.id.te_chapter);
            //toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow);
            //getSupportActionBar().setTitle("Techive");
        }
        setTitle("Chapters");
        Chapters_ID = new ArrayList<String>();
        Class = getIntent().getStringExtra("Class") != null ? getIntent().getStringExtra("Class") : "";
        displayClassName = getIntent().getStringExtra("displayClassName") != null ? getIntent().getStringExtra("displayClassName") : "";
        Subject = getIntent().getStringExtra("Subject");
        access_code = getIntent().getStringExtra("access_code");
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        list = (ListView) findViewById(R.id.listview);
        Continue = (Button) findViewById(R.id.Continue);


        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int isChapter = 0;
                ArrayList<HashMap<String, String>> chapLists1 = ((ChapterAdapter) adapter).getChapterList();
                for (int i = 0; i < chapLists1.size(); i++) {
                    if (chapLists1.get(i).get("isSelected").equals("1")) {
                        isChapter = 1;
                    }
                }
                Log.d("Chapters_ID", "" + isChapter);
                if (isChapter == 0) {
                    Toast.makeText(TeChapter.this, "You have not Selected Any Chapters", Toast.LENGTH_LONG).show();
                } else {
                    String ch_names = "";
                    ArrayList<HashMap<String, String>> chapLists = ((TeChapter.ChapterAdapter) adapter).getChapterList();
                    for (int i = 0; i < chapLists.size(); i++) {
                        if (chapLists.get(i).get("isSelected").equals("1")) {
                            ch_names += chapLists.get(i).get("ChapterID") + ",";
                        }
                    }
                    Intent intent = new Intent(TeChapter.this,FillDetailsActivity.class);
                    intent.putExtra("Class", Class);
                    intent.putExtra("displayClassName", displayClassName);
                    intent.putExtra("Subject", Subject);
                    intent.putExtra("ch_names", ch_names);
                    startActivity(intent);
                }
            }

        });

        chapList = new ArrayList<HashMap<String, String>>();
        progressDialog = new ProgressDialog(TeChapter.this);
        progressDialog.setMessage("Loading..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
        //String url = "http://www.techive.in/studybuddy/api/chapter.php";
        String url = Apis.base_url+Apis.chapter_url;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ViewPatient12320", response);
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            Log.d("ChapterStatus", "" + jsonObject1.getString("success"));
                            String LoginCredential = jsonObject1.getString("success");
                            JSONArray heroArray = jsonObject1.getJSONArray("data");

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
                                chapList.add(contact);
                            }

                            ArrayList<HashMap<String, String>> arrayList = chapList;
                            Collections.sort(arrayList, new Comparator<HashMap<String, String>>() {
                                @Override
                                public int compare(HashMap<String, String> lhs, HashMap<String, String> rhs) {
                                    int id_1 = Integer.parseInt(lhs.get("ChapterNo") != null && !lhs.get("ChapterNo").isEmpty() ? lhs.get("ChapterNo") : "0");
                                    int id_2 = Integer.parseInt(rhs.get("ChapterNo") != null && !rhs.get("ChapterNo").isEmpty() ? rhs.get("ChapterNo") : "0");
                                    /*For ascending order*/
                                    return id_1 - id_2;
                                }
                            });

                            if(chapList.size()==0){
                                tc_chapter.setVisibility(View.GONE);
                                chNotFound.setVisibility(View.VISIBLE);

                            }else{
                                tc_chapter.setVisibility(View.VISIBLE);
                                chNotFound.setVisibility(View.GONE);
                                adapter = new ChapterAdapter(TeChapter.this, chapList);
                                list.setAdapter(adapter);
                            }




                            //marksList.add(PateintDATA);
                            searchResults = new ArrayList<>((Collection<? extends HashMap<String, String>>) chapList);
                           /* adapter = new STUDENTDashbroadAdapter(StudentdashBord.this, searchResults,UpcomigID);
                            list.setAdapter(adapter);*/

                            Log.d("Sssssssslist", String.valueOf(chapList));
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
                params.put("accesscodes", access_code);

                return params;
            }
        };
        //queue.add(stringRequest);
        CommonMethods.callWebserviceForResponse(stringRequest,TeChapter.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_go_home:
                Intent intent = new Intent(TeChapter.this, Main2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("accesscodes", CommonMethods.getAccessCode(TeChapter.this));
                intent.putExtra("Teachers_ID", CommonMethods.getId(TeChapter.this));
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

    public static boolean isValid(String str) {
        boolean isValid = false;
        String expression = "^[a-z_A-Z0-9 ]*$";
        CharSequence inputStr = str;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    //ArrayList<HashMap<String, String>> chapList;


    public class ChapterAdapter extends BaseAdapter {

        // Declare Variables
        ArrayList<HashMap<String, String>> books = new ArrayList<>();

        TeChapter mContext;
        LayoutInflater inflater;
        ArrayList<HashMap<String, String>> animalNamesList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        String upcomigID;


        public ChapterAdapter(TeChapter studentdashBord, ArrayList<HashMap<String, String>> booksname) {
            this.mContext = studentdashBord;
            this.books = booksname;
            inflater = LayoutInflater.from((Context) mContext);

        }

        public class ViewHolder {
            TextView name, ID, Number, heading, chapterNmae;
            // Button details,Medicalhistory;
            LinearLayout linearLayout;
            CheckBox checkboxChapter;
            ImageView imgg;
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
                // Locate the TextViews in listview_item.xml
                //     holder.imgg = (ImageView) view.findViewById(R.id.imggch);
                holder.heading = (TextView) view.findViewById(R.id.headingCh);
                holder.chapterNmae = (TextView) view.findViewById(R.id.chaptername);
                holder.checkboxChapter = (CheckBox) view.findViewById(R.id.checkboxChapter);
                holder.linearLayout = (LinearLayout) view.findViewById(R.id.listclick);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            // Set the results into TextViews
            map = new HashMap<>(position);
            holder.heading.setText("Chapter No.  " + books.get(position).get("ChapterNo"));
            holder.chapterNmae.setText(books.get(position).get("ChapterName"));
            holder.checkboxChapter.setChecked(!books.get(position).get("isSelected").equals("0"));
            holder.checkboxChapter.setTag(books.get(position));
            holder.checkboxChapter.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //Toast.makeText(v.getContext(), "Clicked on Checkbox: " + position + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();
                    //region "Checked Unchecked"
                    CheckBox cb = (CheckBox) v;
                    map = (HashMap<String, String>) cb.getTag();
                    map.put("isSelected", cb.isChecked() ? "1" : "0");
                    books.get(position).put("isSelected", cb.isChecked() ? "1" : "0");
                    if (cb.isChecked()) {
                        Chapters_ID.add(books.get(position).get("ChapterID"));
                    } else if (!cb.isChecked()) {
                        // Chapters_ID.remove(position);
                        if (Chapters_ID.size() > position) {
                            Chapters_ID.remove(position);
                        }

                    }//endregion
                }

            });
            return view;
        }

        public ArrayList<HashMap<String, String>> getChapterList() {
            return books;
        }
    }

}
