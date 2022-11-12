package com.studybuddy.pc.brainmate.teacher;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
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
import com.studybuddy.pc.brainmate.mains.LoginBothActivity;
import com.studybuddy.pc.brainmate.student.CommonMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityScore extends AppCompatActivity {

    CreatepapersAdapter adapter;
    ProgressDialog progressDialog;
    ArrayList<HashMap<String, String>> marksList;
    String UpcomigID;
    //EditText inputSearch;
    ListView list;
    String type = "";
    String url = "";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        context = ActivityScore.this;
        //inputSearch = (EditText) findViewById(R.id.inputSearch);
        list = (ListView) findViewById(R.id.listview25);
        setTitle("Activity Score");

        if (CommonMethods.getType(ActivityScore.this).equals("s")) {
            type = "s";
            url = Apis.base_url + Apis.show_student_marks;
        } else if (CommonMethods.getType(ActivityScore.this).equals("t")) {
            type = "t";
            url = Apis.base_url + Apis.show_teacher_marks;
        }
        marksList = new ArrayList<HashMap<String, String>>();
        progressDialog = new ProgressDialog(ActivityScore.this);
        progressDialog.setMessage("Loading..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        //RequestQueue queue = Volley.newRequestQueue(ActivityScore.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("show_teacher_marks", response);
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            Log.d("login_succes", "" + jsonObject1.getString("success"));
                            String LoginCredential = jsonObject1.getString("success");
                            JSONArray heroArray = jsonObject1.getJSONArray("data");

                            for (int i = heroArray.length()-1; i >= 0; i--) {
                                JSONObject c = heroArray.getJSONObject(i);
                                String id = c.getString("id");
                                String name = "";
                                String time = "";
                                if (type.equals("t")) {
                                    name = c.getString("teacher_id");
                                    time = c.getString("time");
                                } else if (type.equals("s")) {
                                    name = c.getString("student_id");
                                }
                                String subject = c.getString("subject");
                                String chapter = c.getString("chapter");
                                String total_marks = c.getString("total_marks");
                                String marks = c.getString("marks");
                                String date = c.getString("date");

                                HashMap<String, String> contact = new HashMap<>();
                                contact.put("id", id);
                                contact.put("name", name);
                                contact.put("subject", subject);
                                contact.put("chapter", chapter);
                                contact.put("total_marks", total_marks);
                                contact.put("marks", marks);
                                contact.put("time", time);
                                contact.put("date", date);

                                Log.d("sarejhaseachha", String.valueOf(contact));
                                marksList.add(contact);
                            }
                            adapter = new CreatepapersAdapter(ActivityScore.this, marksList);
                            list.setAdapter(adapter);

                            Log.d("Sssssssslist", String.valueOf(marksList));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                Log.d("getParamsDatas11", "" + volleyError.getMessage());
                if (volleyError instanceof NoConnectionError) {
                    Toast.makeText(ActivityScore.this, "Internet not Connected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ActivityScore.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                java.util.Map<String, String> params = new HashMap<>();
                if (type.equals("t")) {
                    params.put("teacher_id", CommonMethods.getId(ActivityScore.this));
                } else if (type.equals("s")) {
                    params.put("student_id", CommonMethods.getId(ActivityScore.this));
                }
                return params;
            }
        };
        //queue.add(stringRequest);
        CommonMethods.callWebserviceForResponse(stringRequest, context);


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

                adapter.notifyDataSetChanged();
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            public void afterTextChanged(Editable s) {


            }
        });*/

       /* list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(ActivityScore.this, "Content:", Toast.LENGTH_LONG);
                toast.show();
                Intent intent=new Intent(ActivityScore.this,Students_Class.class);
                startActivity(intent);
            }
        });    */    //intializing scan object


    }

    public class CreatepapersAdapter extends BaseAdapter {

        ActivityScore mContext;
        LayoutInflater inflater;
        ArrayList<HashMap<String, String>> scoreDetailsList = new ArrayList<HashMap<String, String>>();


        public CreatepapersAdapter(ActivityScore context, ArrayList<HashMap<String, String>> scoreDetailsList) {
            mContext = context;
            this.scoreDetailsList = scoreDetailsList;
            inflater = LayoutInflater.from((Context) mContext);
        }

        public class ViewHolder {
            TextView Subject, Class, txtShowDate, txtShowChapter, txtShowMarksObtained, txtShowTotalMarks, txtShowTime, txtTimeTitle;
        }

        @Override
        public int getCount() {
            return scoreDetailsList.size();
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
                view = inflater.inflate(R.layout.created_papers_list, null);
                holder.Subject = (TextView) view.findViewById(R.id.SubjectName);

                holder.txtShowDate = (TextView) view.findViewById(R.id.txtShowDate);
                holder.txtShowChapter = (TextView) view.findViewById(R.id.txtShowChapter);
                holder.txtShowMarksObtained = (TextView) view.findViewById(R.id.txtShowMarksObtained);
                holder.txtShowTotalMarks = (TextView) view.findViewById(R.id.txtShowTotalMarks);
                holder.txtShowTime = (TextView) view.findViewById(R.id.txtShowTime);
                holder.txtTimeTitle = (TextView) view.findViewById(R.id.txtTimeTitle);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            // Set the results into TextViews
            //map = new HashMap<>(position);
            holder.Subject.setText(scoreDetailsList.get(position).get("subject"));
            holder.txtShowDate.setText(scoreDetailsList.get(position).get("date"));
            holder.txtShowChapter.setText(scoreDetailsList.get(position).get("chapter"));
            holder.txtShowMarksObtained.setText(scoreDetailsList.get(position).get("marks"));
            holder.txtShowTotalMarks.setText(scoreDetailsList.get(position).get("total_marks"));
            holder.txtShowTime.setText(scoreDetailsList.get(position).get("time"));
            holder.txtTimeTitle.setVisibility(!scoreDetailsList.get(position).get("time").equals("") ? View.VISIBLE : View.GONE);
            holder.txtShowTime.setVisibility(!scoreDetailsList.get(position).get("time").equals("") ? View.VISIBLE : View.GONE);
            return view;
        }
    }
}
