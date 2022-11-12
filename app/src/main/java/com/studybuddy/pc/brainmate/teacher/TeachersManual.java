package com.studybuddy.pc.brainmate.teacher;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
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
import android.widget.TextView;
import android.widget.Toast;

import com.studybuddy.pc.brainmate.Network_connection.services.NetworkChangeReceiver;
import com.studybuddy.pc.brainmate.Network_connection.utils.NetworkUtil;
import com.studybuddy.pc.brainmate.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.studybuddy.pc.brainmate.Network_connection.data.Constants.CONNECTIVITY_ACTION;


public class TeachersManual extends AppCompatActivity {

    WebView webView;
    ProgressDialog pd;
    String manual;
    IntentFilter intentFilter;
    NetworkChangeReceiver receiver;
    private static TextView log_network;
    private static String log_str;
    String Network_Status;
    String formattedDate;
    String BookTitle;
    Toolbar toolbarHeader;

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
        setContentView(R.layout.activity_teachers_menual);
        toolbarHeader = findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbarHeader);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbarHeader.setTitleTextColor(getResources().getColor(R.color.white));
            //toolbar.setNavigationIcon(R.drawable.ic_toolbar_arrow);
            //getSupportActionBar().setTitle("Techive");
        }
        setTitle("Teacher's Book");
        intentFilter = new IntentFilter();
        intentFilter.addAction(CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        manual = getIntent().getStringExtra("manual");
        BookTitle = getIntent().getStringExtra("Title");
        //Log.d("manual123", "http://techive.in/studybuddy/publisher/" + manual);
        Log.d("manual123", "" + manual);


        if (NetworkUtil.getConnectivityStatus(TeachersManual.this) > 0) {
            System.out.println("Connect");
            Network_Status = "Connect";
            pd = ProgressDialog.show(TeachersManual.this, "", "Please wait...", true);
            webView = (WebView) findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(true); // enable javascript
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);

            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setDisplayZoomControls(false);

            webView.setWebViewClient(new WebViewClient() {
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(TeachersManual.this, description, Toast.LENGTH_SHORT).show();
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
            //webView .loadUrl("http://techive.in/studybuddy/publisher/manual_pdf/1539767241OurNatureAndEarth1_Answers_Compressed.pdf");

            //webView.loadUrl("http://docs.google.com/gview?embedded=true&url=http://techive.in/studybuddy/publisher/" + manual);
            //webView.loadUrl("http://docs.google.com/gview?embedded=true&url=http://techive.in/studybuddy/" + manual);
            webView.loadUrl("http://docs.google.com/gview?embedded=true&url=https://brainmate.co.in/studybuddy/" + manual);
        } else {
            System.out.println("No connection");
            Network_Status = "notConnect";
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TeachersManual.this);
            alertDialogBuilder.setMessage(getString(R.string.no_internet));
            alertDialogBuilder.setPositiveButton("Exit",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                        }
                    });


            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.Download) {
            Date date = Calendar.getInstance().getTime();
            System.out.println("Current time => " + date);
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            formattedDate = df.format(date);
            //String url = "http://techive.in/studybuddy/publisher/" + manual;
            String url = "http://techive.in/studybuddy/" + manual;
            new DownloadFileFromURL().execute(url);

            File direct = new File(Environment.getExternalStorageDirectory()
                    + "/AnhsirkDasarp");

            if (!direct.exists()) {
                direct.mkdirs();
            }

            DownloadManager mgr = (DownloadManager) TeachersManual.this.getSystemService(Context.DOWNLOAD_SERVICE);

            Uri downloadUri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);

            request.setAllowedNetworkTypes(
                    DownloadManager.Request.NETWORK_WIFI
                            | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false).setTitle("Demo")
                    .setDescription("Something useful. No, really.")
                    .setDestinationInExternalFilesDir(TeachersManual.this, "/Studybuddy", BookTitle + "Teacher.pdf");

            mgr.enqueue(request);

            return true;
        }/*else if (id == R.id.wishlist) {
            Intent intent=new Intent(MainActivity.this,Wish_list.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.notification) {
            Intent intent=new Intent(MainActivity.this,Notification.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.Sharch) {
            Intent intent=new Intent(MainActivity.this,Search_Items.class);
            startActivity(intent);
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("Starting download");
            Toast.makeText(TeachersManual.this, "Starting download", Toast.LENGTH_LONG).show();
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                String root = Environment.getExternalStorageDirectory().toString();

                System.out.println("Downloading");
                Toast.makeText(TeachersManual.this, "Downloading", Toast.LENGTH_LONG).show();
                URL url = new URL(f_url[0]);

                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream to write file
                Long tsLong = System.currentTimeMillis() / 1000;
                String ts = tsLong.toString();
                OutputStream output = new FileOutputStream(root + "/" + ts + ".jpg");
                byte data[] = new byte[1024];

                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;

                    // writing data to file
                    output.write(data, 0, count);

                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }


        /**
         * After completing background task
         **/
        @Override
        protected void onPostExecute(String file_url) {
            System.out.println("Downloaded");
            Toast.makeText(TeachersManual.this, "Downloaded", Toast.LENGTH_LONG).show();

        }
    }

}
