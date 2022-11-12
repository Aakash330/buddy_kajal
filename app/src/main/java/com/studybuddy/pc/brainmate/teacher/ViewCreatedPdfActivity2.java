package com.studybuddy.pc.brainmate.teacher;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.mains.Apis;
import com.studybuddy.pc.brainmate.student.CommonMethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewCreatedPdfActivity2 extends AppCompatActivity {

    ArrayList<HashMap<String, String>> hashMapArrayList = new ArrayList<>();
    RecyclerView rVTeacherPdfList;
    Context context;
    Toolbar toolbarHeader;
    String Teachers_ID;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_created_pdf2);
        rVTeacherPdfList = findViewById(R.id.rVTeacherPdfList);
        context = ViewCreatedPdfActivity2.this;
        toolbarHeader = findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbarHeader);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbarHeader.setTitleTextColor(getResources().getColor(R.color.white));
            //toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow);
            //getSupportActionBar().setTitle("Techive");
        }
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..."); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        Teachers_ID = getIntent().getStringExtra("Teachers_ID") != null ? getIntent().getStringExtra("Teachers_ID") : "";
        setTitle("View Pdf");
        reqToSubmit(Apis.base_url + Apis.teacher_pdf_data, Teachers_ID);
    }

    //region "Submit Request"
    private void reqToSubmit(String feedUrl, final String teacher_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, feedUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("validateResponse222", response);
                        parsePdfJson(response);
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        Log.d("getParamsDatas222", "" + volleyError.getMessage());
                        if (volleyError instanceof NoConnectionError) {
                            Toast.makeText(context, "Internet not Connected", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Some Error Occured", Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //region "teacher_id"
                params.put("teacher_id", "" + teacher_id);
                //endregion
                Log.d("getParamsDatas", params.toString());
                return params;
            }
        };
        CommonMethods.callWebserviceForResponse(stringRequest, context);
    }//endregion

    private void parsePdfJson(String response) {
        try {
            JSONObject object = new JSONObject(response);
            JSONArray jsonArray = object.getJSONArray("data");
            if (jsonArray.length() > 0) {
                for (int i = jsonArray.length() - 1; i >= 0; i--) {
                    JSONObject object1 = jsonArray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<>();
                    map.put("ids", object1.getString("id"));
                    map.put("teacher_id", object1.getString("teacher_id"));
                    map.put("title", object1.getString("title"));
                    map.put("paper_pdf", object1.getString("paper_pdf"));
                    map.put("date", object1.getString("date"));
                    hashMapArrayList.add(map);
                    Log.d("parsePdfJson", "" + hashMapArrayList.toString());
                }
                ViewPdfListAdapter viewPdfListAdapter = new ViewPdfListAdapter(hashMapArrayList, context);
                rVTeacherPdfList.setLayoutManager(new LinearLayoutManager(context));
                rVTeacherPdfList.setAdapter(viewPdfListAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getIntent().getBooleanExtra("isFromQuestionPaper", false)) {
            Intent intent = new Intent(ViewCreatedPdfActivity2.this, Main2Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("accesscodes", CommonMethods.getAccessCode(ViewCreatedPdfActivity2.this));
            intent.putExtra("Teachers_ID", CommonMethods.getId(ViewCreatedPdfActivity2.this));
            startActivity(intent);
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (getIntent().getBooleanExtra("isFromQuestionPaper", false)) {
                Intent intent = new Intent(ViewCreatedPdfActivity2.this, Main2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("accesscodes", CommonMethods.getAccessCode(ViewCreatedPdfActivity2.this));
                intent.putExtra("Teachers_ID", CommonMethods.getId(ViewCreatedPdfActivity2.this));
                startActivity(intent);
            } else {
                finish();
            }
            return true;
        } else if (id == R.id.action_go_home) {
            Intent intent = new Intent(ViewCreatedPdfActivity2.this, Main2Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("accesscodes", CommonMethods.getAccessCode(ViewCreatedPdfActivity2.this));
            intent.putExtra("Teachers_ID", CommonMethods.getId(ViewCreatedPdfActivity2.this));
            startActivity(intent);
            return true;
        }
        return true;
    }

    public class ViewPdfListAdapter extends RecyclerView.Adapter<ViewPdfListAdapter.ViewHolder> {

        private ArrayList<HashMap<String, String>> pdf_list = new ArrayList<>();
        private Context context;

        public ViewPdfListAdapter(ArrayList<HashMap<String, String>> pdf_list, Context context) {
            this.pdf_list = pdf_list;
            this.context = context;
        }


        @Override
        public ViewPdfListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.viewpdflist, parent, false));
        }

        @Override
        public void onBindViewHolder(final ViewPdfListAdapter.ViewHolder holder, int position) {
            holder.txtTitleViewPdf.setText(pdf_list.get(position).get("title"));
            holder.txtDateViewPdf.setText(pdf_list.get(position).get("date"));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ViewCreatedPdfActivity1.class);
                    intent.putExtra("paper_pdf", pdf_list.get(holder.getAdapterPosition()).get("paper_pdf"));
                    intent.putExtra("pdf_title",pdf_list.get(holder.getAdapterPosition()).get("title"));
                    startActivity(intent);
                }
            });
            holder.llDeletePdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reqToDelete(Apis.base_url + Apis.teacher_generate_paper_delete, pdf_list.get(holder.getAdapterPosition()).get("ids"), holder.getAdapterPosition());
                }
            });
        }

        //region "Submit Request"
        private void reqToDelete(String feedUrl, final String paper_id, final int position) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, feedUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("validateResponse222", response);
                            try {
                                JSONObject object = new JSONObject(response);
                                String success = object.getString("success");
                                if (success != null && !success.isEmpty() && success.equals("1")) {
                                    pdf_list.remove(position);
                                    notifyItemRemoved(position);
                                    CommonMethods.showToast(context, getString(R.string.deleted));

                                } else {
                                    CommonMethods.showToast(context, getString(R.string.some_error_occurred));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            if (progressDialog != null) {
                                progressDialog.dismiss();
                            }
                            Log.d("getParamsDatas222", "" + volleyError.getMessage());
                            if (volleyError instanceof NoConnectionError) {
                                Toast.makeText(context, "Internet not Connected", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Some Error Occured", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    //region "teacher_id"
                    params.put("paper_id", "" + paper_id);
                    //endregion
                    Log.d("getParamsDatas", params.toString());
                    return params;
                }
            };
            CommonMethods.callWebserviceForResponse(stringRequest, context);
        }//endregion

        @Override
        public int getItemCount() {
            return pdf_list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView txtTitleViewPdf, txtDateViewPdf;
            LinearLayout llDeletePdf;

            public ViewHolder(View itemView) {
                super(itemView);
                txtTitleViewPdf = itemView.findViewById(R.id.txtTitleViewPdf);
                txtDateViewPdf = itemView.findViewById(R.id.txtDateViewPdf);
                llDeletePdf = itemView.findViewById(R.id.llDeletePdf);
                llDeletePdf.setVisibility(View.VISIBLE);
            }
        }
    }
}
