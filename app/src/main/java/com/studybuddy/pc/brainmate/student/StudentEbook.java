package com.studybuddy.pc.brainmate.student;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.studybuddy.pc.brainmate.R;

public class StudentEbook extends AppCompatActivity {

    WebView webView;
    ProgressDialog pd;
    String ebook;
    Toolbar toolbarHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_ebook);
        //setTitle(getIntent().getStringExtra("Title") != null ? getIntent().getStringExtra("Title") : "");
        ebook = getIntent().getStringExtra("ebook");
        pd = ProgressDialog.show(StudentEbook.this, "", "Please wait...", true);

        toolbarHeader = findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbarHeader);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbarHeader.setTitleTextColor(getResources().getColor(R.color.white));
            //toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow);
            //getSupportActionBar().setTitle("Techive");
        }
        setTitle(getIntent().getStringExtra("Title") != null ? getIntent().getStringExtra("Title") : "E-Book");

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);


        webView.setWebViewClient(new WebViewClient()

        {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(StudentEbook.this, description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                pd.dismiss();
                String webUrl = webView.getUrl();
            }
        });


        webView.loadUrl(ebook);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_go_home:
                Intent intent = new Intent(StudentEbook.this, Stu_Classes.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("accesscodes", CommonMethods.getAccessCode(StudentEbook.this));
                intent.putExtra("Student_ID", CommonMethods.getId(StudentEbook.this));
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
}
