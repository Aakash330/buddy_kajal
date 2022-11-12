package com.studybuddy.pc.brainmate.teacher;


import android.app.ListActivity;
import android.os.Bundle;

public class SectionListView extends ListActivity {

    private Demo_Adapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new Demo_Adapter(SectionListView.this);
        for (int i = 1; i < 30; i++) {
            mAdapter.addItem("Row Item #" + i);
            if (i % 4 == 0) {
                mAdapter.addSectionHeaderItem("Section #" + i);
            }
        }
        setListAdapter(mAdapter);
    }

}