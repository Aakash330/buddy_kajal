package com.studybuddy.pc.brainmate.student;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.studybuddy.pc.brainmate.Network_connection.services.NetworkChangeReceiver;
import com.studybuddy.pc.brainmate.Network_connection.utils.NetworkUtil;
import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.mains.Apis;
import com.studybuddy.pc.brainmate.teacher.Book_Subjects;
import com.studybuddy.pc.brainmate.teacher.E_book;
import com.studybuddy.pc.brainmate.teacher.Main2Activity;

import java.util.ArrayList;

import static com.studybuddy.pc.brainmate.Network_connection.data.Constants.CONNECTIVITY_ACTION;

public class FunWebViewActivity extends AppCompatActivity {

    WebView webView;
    Toolbar toolbarHeader;
    Context context;
    IntentFilter intentFilter;
    NetworkChangeReceiver receiver;
    ProgressDialog pd;
    String Network_Status, url;
    ImageView img_overlay_web;
    private ArrayList<String> urlLists = new ArrayList<>();
    int link_count = 0;

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
        setContentView(R.layout.activity_fun_web_view);
        context = FunWebViewActivity.this;
        toolbarHeader = findViewById(R.id.toolbarHeader);
        img_overlay_web = findViewById(R.id.img_overlay_web);
        setSupportActionBar(toolbarHeader);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbarHeader.setTitleTextColor(getResources().getColor(R.color.white));
            //toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow);
            //getSupportActionBar().setTitle("Techive");
        }
        setTitle("Fun Activity");

        intentFilter = new IntentFilter();
        intentFilter.addAction(CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        url = getIntent().getStringExtra("url");
        link_count = getIntent().getIntExtra("link_count", 0);
        urlLists = getIntent().getStringArrayListExtra("url_list");
        Log.d("getUrl", "" + urlLists);

        callWebView(url);
    }

    private void callWebView(String url) {
        if (NetworkUtil.getConnectivityStatus(context) > 0) {
            System.out.println("Connect");
            Network_Status = "Connect";
            pd = ProgressDialog.show(context, "", "Please wait...", true);
            webView = (WebView) findViewById(R.id.webView);
            if (url != null && !url.equals("")) {
                webView.getSettings().setJavaScriptEnabled(true); // enable javascript
                webView.getSettings().setLoadWithOverviewMode(true);
                webView.getSettings().setUseWideViewPort(true);
                webView.loadUrl(url);
                //webView.loadUrl(getIntent().getStringExtra("url"));
                //webView.loadUrl("http://www.techive.in/studybuddy/upload_img/1543832259images_1.jpg");
                //webView.loadUrl("http:\/\/www.techive.in\/studybuddy\/upload_img\/1543832259images_1.jpg");
                webView.setWebViewClient(new WebViewClient() {
                    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                        Toast.makeText(context, description, Toast.LENGTH_SHORT).show();
                        Log.d("getEbook", "" + description);
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
                //webView.loadUrl("http://brainmate.co.in/BELS/Infonet%20Book3);
            } else {
                pd.dismiss();
                Toast.makeText(context, getString(R.string.no_data), Toast.LENGTH_SHORT).show();
            }
        } else {
            checkNetDialog();
        }
        img_overlay_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethods.showToast(context, "You need to buy it.");
            }
        });
    }

    public void checkNetDialog() {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(getString(R.string.no_internet));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(getString(R.string.retry),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        if (NetworkUtil.getConnectivityStatus(context) > 0) {
                            Toast.makeText(context, "" + getString(R.string.connected), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            checkNetDialog();
                        }
                    }
                });
        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_go_home:

                Log.d("link_count", String.valueOf(link_count) + "\n" + urlLists.size());

                callWebView(urlLists.get(link_count < urlLists.size() ? link_count++ : link_count));

                /*Intent intent = new Intent(context, Main2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("accesscodes", CommonMethods.getAccessCode(context));
                intent.putExtra("Teachers_ID", CommonMethods.getId(context));
                startActivity(intent);*/

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
