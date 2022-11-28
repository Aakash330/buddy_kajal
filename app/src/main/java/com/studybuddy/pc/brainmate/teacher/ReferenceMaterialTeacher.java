package com.studybuddy.pc.brainmate.student;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
import com.studybuddy.pc.brainmate.model.ReferenceMaterialModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReferenceMaterial extends AppCompatActivity {

    private Toolbar toolbarHeader;
    private ArrayList<ReferenceMaterialModel> referenceMaterialList;
    private ListView listView;
    private ReferenceMaterialModel model;
    private ReferenceMaterialAdaptor materialAdaptor;
    private String Network_Status;
    private ProgressDialog progressDialog;
    private String S_id,Subject,ClassName,Title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference_material);
        listView=findViewById(R.id.RM_List);
        toolbarHeader = findViewById(R.id.toolbarHeader);
        referenceMaterialList=new ArrayList<>();
        //Toolbar
        setSupportActionBar(toolbarHeader);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbarHeader.setTitleTextColor(getResources().getColor(R.color.white));

        }
        setTitle("Reference Material");

        try{
            Subject = getIntent().getStringExtra("Subject");
            ClassName = getIntent().getStringExtra("ClassName");
            Title = getIntent().getStringExtra("Title");
        }catch (Exception ee){}
        //toolbar set back button

        S_id=CommonMethods.getId(ReferenceMaterial.this);

        //Reference Material
        setTitleInList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
          /*getMenuInflater().inflate(R.menu.studentdash_bord, menu);
          return true;*/
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_go_home:
                Intent intent = new Intent(ReferenceMaterial.this, Stu_Classes.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("accesscodes", CommonMethods.getAccessCode(ReferenceMaterial.this));
                intent.putExtra("Student_ID", CommonMethods.getId(ReferenceMaterial.this));
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setTitleInList() {

        if (NetworkUtil.getConnectivityStatus(ReferenceMaterial.this) > 0) {
            System.out.println("Connect");
            Network_Status = "Connect";


            progressDialog = new ProgressDialog(ReferenceMaterial.this);
            progressDialog.setMessage("Loading..."); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);
            RequestQueue queue = Volley.newRequestQueue(ReferenceMaterial.this);
            //String url = "http://www.techive.in/studybuddy/api/student_book.php";
            String url = Apis.base_url+Apis.student_book_url;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Studentbooks", response);
                            Log.d("Studentbooks","@kajal testing books page");


                            progressDialog.dismiss();
                            try {

                                JSONObject jsonObject1 = null;
                                jsonObject1 = new JSONObject(response);

                                Log.d("object111", jsonObject1.getString("success"));

                                JSONArray heroArray = jsonObject1.getJSONArray("data");

                                for (int j = 0; j < heroArray.length(); j++) {
                                    JSONObject c1 = heroArray.getJSONObject(j);


                                    if (Subject.equals(c1.getString("subject"))) {

                                        if (ClassName.equals(c1.getString("class"))) {
                                            if (ClassName.equals(c1.getString("Title"))) {
                                                JSONArray array = c1.getJSONArray("book_referal");
                                                for (int n = 0; n < array.length(); n++) {
                                                    JSONObject object = array.getJSONObject(n);

                                                    referenceMaterialList.add(new ReferenceMaterialModel(n, object.getString("title"), object.getString("url")));

                                                }
                                            }
                                        }
                                    }
                                }

                                materialAdaptor=new ReferenceMaterialAdaptor(ReferenceMaterial.this,referenceMaterialList);
                                listView.setAdapter(materialAdaptor);




                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("student_id",S_id);
                    Log.d("getStudentId", "" + params.toString());
                    return params;
                }
            };
            queue.add(stringRequest);
        } else {
            System.out.println("No connection");
            Network_Status = "notConnect";
            setTitleInList();
        }
    }



    public  class ReferenceMaterialAdaptor extends BaseAdapter{
        private Context referenceMaterial;
        private LayoutInflater inflater;
        private ArrayList<ReferenceMaterialModel> referenceMaterialList;
        public ReferenceMaterialAdaptor(Context referenceMaterial, ArrayList<ReferenceMaterialModel> referenceMaterialList) {

            this.referenceMaterial=referenceMaterial;
           this.referenceMaterialList= referenceMaterialList;
            inflater = LayoutInflater.from((Context) referenceMaterial);


        }

        @Override
        public int getCount() {
            return referenceMaterialList.size();
        }

        @Override
        public Object getItem(int position) {
             return referenceMaterialList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View rview, ViewGroup parent) {

            model= referenceMaterialList.get(position);
            if (rview == null) {
                rview = inflater.inflate(R.layout.reference_material_adptor, parent,false);
            }
            TextView S_No = (TextView) rview.findViewById(R.id.textView5);
            TextView Title = (TextView) rview.findViewById(R.id.textView6);

            S_No.setText(""+(position+1));
            Title.setText(model.getTitle());
            return rview;
        }


    }

}