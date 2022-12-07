package com.studybuddy.pc.brainmate.teacher;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

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
import com.studybuddy.pc.brainmate.student.freehandpainting.FreehandActivityMain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TePaintingChapters extends AppCompatActivity {

    TePaintingChapterAdapter adapter;
    ProgressDialog progressDialog;
    ArrayList<HashMap<String, String>> PatientList;
    String UpcomigID;
    EditText inputSearch;
    ArrayList<HashMap<String, String>> searchResults;
    GridView list;
    Button Continue;
    ArrayList<String> chaptercount;
    String Class, Subject, className;
    String access_code;
    ImageView DrwingInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_te_painting_chapters);
        access_code = getIntent().getStringExtra("access_code");
        chaptercount = new ArrayList<>();
        Class = getIntent().getStringExtra("Class");
        Subject = getIntent().getStringExtra("Subject");
        className = getIntent().getStringExtra("class");
        Log.d("AAAAAAAAAAAAAA1", className);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        list = (GridView) findViewById(R.id.listviewTec);
        DrwingInterface = (ImageView) findViewById(R.id.DrwingInterface);
        DrwingInterface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TePaintingChapters.this, FreehandActivityMain.class);
                intent.putExtra("PageValue", "2");
                intent.putExtra("className", className);
                startActivity(intent);
            }
        });
        PatientList = new ArrayList<HashMap<String, String>>();


        progressDialog = new ProgressDialog(TePaintingChapters.this);
        progressDialog.setMessage("Loading..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
       // RequestQueue queue1 = Volley.newRequestQueue(TePaintingChapters.this);
        RequestQueue queue1 = Volley.newRequestQueue(TePaintingChapters.this, new HurlStack(null, CertificateClass0S6.getSslSocketFactory(TePaintingChapters.this)));
        //String url1 = "http://www.techive.in/studybuddy/api/painting_images.php";
        String url1 = Apis.base_url + Apis.painting_images_url;
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Painti9ng", "" + response);
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            Log.d("login_succes", "" + jsonObject1.getString("success"));
                            String LoginCredential = jsonObject1.getString("success");
                            JSONArray PaintImages = jsonObject1.getJSONArray("images");
                            for (int j = 0; j < PaintImages.length(); j++) {
                                JSONObject c = PaintImages.getJSONObject(j);
                                Log.d("login_succes", "" + c.getString("image"));
                                HashMap<String, String> ObjectiveMap = new HashMap<>();
                                ObjectiveMap.put("Iamge", c.getString("image"));
                                ObjectiveMap.put("Bitmap", c.getString("bitmap"));
                                PatientList.add(ObjectiveMap);
                            }
                            adapter = new TePaintingChapterAdapter(TePaintingChapters.this, PatientList);
                            list.setAdapter(adapter);
                            Log.d("imageArray1254f", String.valueOf(PatientList));
                            //marksList.add(PateintDATA);
                            // searchResults = new ArrayList<>((Collection<? extends HashMap<String, String>>) marksList);
                            // Log.d("Sssssssslist", String.valueOf(marksList));
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
                //TODO make changes
                params.put("class", className);
                Log.d("className", "" + params.toString());
                return params;
            }
        };
        queue1.add(stringRequest1);

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
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        super.onBackPressed();
    }
}
