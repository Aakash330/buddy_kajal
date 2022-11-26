package com.studybuddy.pc.brainmate.student;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.model.ReferenceMaterialModel;

import java.util.ArrayList;

public class ReferenceMaterial extends AppCompatActivity {

    private Toolbar toolbarHeader;
    private ArrayList<ReferenceMaterialModel> referenceMaterialList;
    private ListView listView;
    private ReferenceMaterialAdaptor materialAdaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference_material);
        listView=findViewById(R.id.RM_List);
        referenceMaterialList=new ArrayList<>();

        //toolbar set back button
        toolbarHeader = findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbarHeader);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbarHeader.setTitleTextColor(getResources().getColor(R.color.white));

        }
        setTitle("Reference Material");

        //Reference Material
        setTitleInList();
    }

    private void setTitleInList() {
        referenceMaterialList.add(new ReferenceMaterialModel(1,"A"));
        referenceMaterialList.add(new ReferenceMaterialModel(2,"B"));
        referenceMaterialList.add(new ReferenceMaterialModel(3,"C"));
        referenceMaterialList.add(new ReferenceMaterialModel(4,"D"));
        referenceMaterialList.add(new ReferenceMaterialModel(5,"E"));
        referenceMaterialList.add(new ReferenceMaterialModel(6,"F"));
        materialAdaptor=new ReferenceMaterialAdaptor(ReferenceMaterial.this,referenceMaterialList);
        listView.setAdapter(materialAdaptor);

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

    public  class ReferenceMaterialAdaptor extends BaseAdapter{
        private Context referenceMaterial;
        private LayoutInflater inflater;
        private ArrayList<ReferenceMaterialModel> referenceMaterialList;
        public ReferenceMaterialAdaptor(Context referenceMaterial, ArrayList<ReferenceMaterialModel> referenceMaterialList) {

            this.referenceMaterial=referenceMaterial;
           this.referenceMaterialList= referenceMaterialList;
        }

        @Override
        public int getCount() {
            return referenceMaterialList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            final ReferenceMaterialAdaptor.ViewHolder holder;

            if (view == null) {
                holder = new ReferenceMaterialAdaptor.ViewHolder();
                view = inflater.inflate(R.layout.reference_material_adptor, null);
                // Locate the TextViews in listview_item.xml
                holder.S_No = (TextView) view.findViewById(R.id.BookTitle);
                holder.Title = (TextView) view.findViewById(R.id.ForClass);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            return null;
        }


        public class ViewHolder {
            TextView S_No,Title;

        }
    }

}