package com.studybuddy.pc.brainmate.mains;

import static com.studybuddy.pc.brainmate.Network_connection.data.Constants.CONNECTIVITY_ACTION;

import android.app.ProgressDialog;
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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.studybuddy.pc.brainmate.Network_connection.services.NetworkChangeReceiver;
import com.studybuddy.pc.brainmate.Network_connection.utils.NetworkUtil;
import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.student.CommonMethods;
import com.studybuddy.pc.brainmate.student.Stu_Classes;

public class BuyEbooks extends AppCompatActivity {
    WebView webView;
    ProgressDialog pd;
    IntentFilter intentFilter;
    NetworkChangeReceiver receiver;
    private static TextView log_network;
    private static String log_str;
    String Network_Status;
    Toolbar toolbarHeader;
    LinearLayout webPageNotFound;

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
        setContentView(R.layout.activity_buy_ebooks);
        toolbarHeader = findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbarHeader);
        webPageNotFound = findViewById(R.id.pageNotFoundLyt);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbarHeader.setTitleTextColor(getResources().getColor(R.color.white));
            //toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow);
            //getSupportActionBar().setTitle("Techive");
        }
        setTitle("Buy E-books");

        intentFilter = new IntentFilter();
        intentFilter.addAction(CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();

        if (NetworkUtil.getConnectivityStatus(BuyEbooks.this) > 0) {
            System.out.println("Connect");
            Network_Status = "Connect";
            pd = ProgressDialog.show(BuyEbooks.this, "", "Please wait...", true);

            webView = (WebView) findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(true); // enable javascript
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            //webView.loadUrl("http://brainmate.co.in/BELS/Infonet%20Book3");
            webView.setWebViewClient(new WebViewClient() {
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(BuyEbooks.this, description, Toast.LENGTH_SHORT).show();
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

            webView.loadUrl("ebookselibrary.com");


        }else{
            checkNetDialog();
        }
    }

    public void checkNetDialog() {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(BuyEbooks.this);
        alertDialogBuilder.setMessage(getString(R.string.no_internet));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(getString(R.string.retry),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        if (NetworkUtil.getConnectivityStatus(BuyEbooks.this) > 0) {
                            Toast.makeText(BuyEbooks.this, "" + getString(R.string.connected), Toast.LENGTH_SHORT).show();
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_go_home:
                Intent intent = new Intent(BuyEbooks.this, Stu_Classes.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("accesscodes", CommonMethods.getAccessCode(BuyEbooks.this));
                intent.putExtra("Teachers_ID", CommonMethods.getId(BuyEbooks.this));
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