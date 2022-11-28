package com.studybuddy.pc.brainmate.student;

import static com.studybuddy.pc.brainmate.Network_connection.data.Constants.CONNECTIVITY_ACTION;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.studybuddy.pc.brainmate.Network_connection.services.NetworkChangeReceiver;
import com.studybuddy.pc.brainmate.Network_connection.utils.NetworkUtil;
import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.teacher.Main2Activity;

public class ShowReferenceMaterialStudent extends AppCompatActivity {

    private static final String TAG ="ShowReferenceMaterial";
    private String url;
    ProgressDialog pd;
    WebView webView;
    String Network_Status;
    LinearLayout webPageNotFound;
    Toolbar toolbarHeader;
    IntentFilter intentFilter;
    NetworkChangeReceiver receiver;

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reference_material_student);
        url=getIntent().getExtras().getString("url");
       // Toast.makeText(this, "url="+url, Toast.LENGTH_SHORT).show();
        webPageNotFound=findViewById(R.id.pageNotFoundLyt);
        toolbarHeader = findViewById(R.id.toolbarHeader);
        webView = (WebView) findViewById(R.id.webView);
        checkNetDialog();
        //Toolbar
        setSupportActionBar(toolbarHeader);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbarHeader.setTitleTextColor(getResources().getColor(R.color.white));

        }
        intentFilter = new IntentFilter();
        intentFilter.addAction(CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        setTitle("Reference Material");
    }

    private void checkNetDialog() {
        if (NetworkUtil.getConnectivityStatus(ShowReferenceMaterialStudent.this) > 0) {
            System.out.println("Connect");
            Network_Status = "Connect";
            pd = ProgressDialog.show(ShowReferenceMaterialStudent.this, "", "Please wait...", true);


            webView.getSettings().setJavaScriptEnabled(true); // enable javascript
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            //webView.loadUrl("http://brainmate.co.in/BELS/Infonet%20Book3");
            webView.setWebViewClient(new WebViewClient() {
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(ShowReferenceMaterialStudent.this, description, Toast.LENGTH_SHORT).show();
                    Log.d("getEbook", "" + description);
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    pd.show();
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    pd.dismiss();
                }
            });
            Log.w("kk","ebookUrl E_book:"+url);
            if(url==null){
                pd.dismiss();
                webView.setVisibility(View.GONE);
                webPageNotFound.setVisibility(View.VISIBLE);
            }else{
                webView.setVisibility(View.VISIBLE);
                webPageNotFound.setVisibility(View.GONE);
                webView.loadUrl(url);
            }

            //webView.loadUrl("http://brainmate.co.in/BELS/Infonet%20Book3/mobile/index.html");
        } else {
            checkNetDialog();
        }


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
                Intent intent = new Intent(ShowReferenceMaterialStudent.this, Stu_Classes.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("accesscodes", CommonMethods.getAccessCode(ShowReferenceMaterialStudent.this));
                intent.putExtra("Student_ID", CommonMethods.getId(ShowReferenceMaterialStudent.this));
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}