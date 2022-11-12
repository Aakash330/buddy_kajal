package com.studybuddy.pc.brainmate.teacher;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.studybuddy.pc.brainmate.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Students_Class extends AppCompatActivity {

    private Button barcodeScanner;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    ListView list;
    ProgressDialog progressDialog;
    ArrayList<HashMap<String, String>> PatientList;
    String UpcomigID;
    EditText inputSearch;
    ArrayList<HashMap<String, String>> searchResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students__class);
        setTitle("Classes");
        list=(ListView)findViewById(R.id.listview12);

        inputSearch=(EditText)findViewById(R.id.inputSearch);

        PatientList = new ArrayList<HashMap<String, String>>();


        barcodeScanner = (Button) findViewById(R.id.barcodeScanner);
        barcodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    //start the scanning activity from the com.google.zxing.client.android.SCAN intent
                    Intent intent = new Intent(ACTION_SCAN);
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, 0);
                }
                catch (ActivityNotFoundException anfe)
                {
                    //on catch, show the download dialog
                    showDialog(Students_Class.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
                }
            }
        });



    }
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo)
    {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try
                {
                    act.startActivity(intent);
                }
                catch (ActivityNotFoundException anfe)
                {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialogInterface, int i)
            {
            }
        });
        return downloadDialog.show();
    }

    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if (requestCode == 0)
        {
            if (resultCode == RESULT_OK)
            {
                //get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                Intent intent1=new Intent(Students_Class.this,TeChapter.class);
                startActivity(intent1);
                toast.show();
                inputSearch.setText(contents);
                Log.d("Upcomingcode",contents);
            }
        }}
   /* public static class StudentClassAdapter extends BaseAdapter {

        // Declare Variables

        Students_Class mContext;
        LayoutInflater inflater;
        ArrayList<HashMap<String, String>> scoreDetailsList = new ArrayList<HashMap<String, String>>();
        HashMap<String,String>map;
        String upcomigID;


        public StudentClassAdapter(Main2Activity context, ArrayList<HashMap<String, String>> scoreDetailsList) {
            mContext = context;
          //  this.upcomigID=upcomigID;
            this.scoreDetailsList = scoreDetailsList;
            inflater = LayoutInflater.from((Context) mContext);


            Log.d("Sssssssslist", String.valueOf("Adapter"+scoreDetailsList));

        }

        public class ViewHolder {
            TextView name,ID,Number;
            // Button details,Medicalhistory;
            LinearLayout linearLayout;
            ImageView imgg;
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
                view = inflater.inflate(R.layout.studentclasslist, null);
                // Locate the TextViews in listview_item.xml
                holder.name = (TextView) view.findViewById(R.id.heading);
                holder.ID = (TextView) view.findViewById(R.id.nameLabel);
                holder.Number = (TextView) view.findViewById(R.id.PhonenumberLa);
                holder.imgg = (ImageView) view.findViewById(R.id.imgg12);
                holder.linearLayout=(LinearLayout)view.findViewById(R.id.listclick);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            // Set the results into TextViews
            map=new HashMap<>(position);
            holder.name.setText(scoreDetailsList.get(position).get("Class"));
            *//*holder.ID.setText(scoreDetailsList.get(position).get("id"));
            holder.Number.setText(scoreDetailsList.get(position).get("phone"));*//*
            holder.imgg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,MainActivity.class);
                    mContext.startActivity(intent);

                }
            });
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,MainActivity.class);
                    mContext.startActivity(intent);
                }
            });
            return view;
        }


    }*/
    //Getting the scan results


}
