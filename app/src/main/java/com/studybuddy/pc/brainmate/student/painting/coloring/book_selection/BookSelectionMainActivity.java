package com.studybuddy.pc.brainmate.student.painting.coloring.book_selection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.student.Books_list;

import java.util.ArrayList;
import java.util.HashMap;

public class BookSelectionMainActivity extends AppCompatActivity {
    private ArrayList<Books_list> Month_One_Array;
    ArrayList<HashMap<String, String>> Arraylist;

    //int[] BooksImage = {R.drawable.book_background, R.drawable.book_background, R.drawable.book_background, R.drawable.book_background, R.drawable.book_background};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_selection_main);
        Arraylist = new ArrayList<>();

  /*      Month_One_Array = new ArrayList<Books_list>();
        String response = CommonMethods.getJson(BookSelectionMainActivity.this, "ArtsBooks");
        Log.d("QuestionsJSON", response);


        Books_Data data = new Gson().fromJson(response, Books_Data.class);
        for (Book_data book_data : data.getBook_data()) {
            Books_list list = new Books_list();
            list.setCover(book_data.getCover());
            list.setFolder(book_data.getFolder());
            list.setName(book_data.getName());
            Month_One_Array.add(list);
            *//*for (Pages month_one : book_data.getPages()) {

            }*//*
        }

        File errorLog = getFileStreamPath(getString(R.string.error_log_file));
        if (errorLog.exists()) {
            Intent intent = new Intent(this, SendLogActivity.class);
            startActivity(intent);
            finish();
            return;
        }


        for (int i = 0; i < BooksImage.length; i++) {
            HashMap<String, String> Map = new HashMap<>();
            Map.put("Books_Name", String.valueOf(BooksImage[i]));
            Arraylist.add(Map);
        }

        // exit button action: going back which basically means finish
      *//*  ImageButton imageButton = findViewById(R.id.exitButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });*//*

        // about button action: show about activity
      *//* // imageButton = findViewById(R.id.aboutButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BookSelectionMainActivity.this, AboutActivity.class);
                startActivity(intent);

            }
        });
*//*

        GridView gridView = findViewById(R.id.bookSelectionGridView);
        gridView.setAdapter(new BookSelectionAdapter(BookSelectionMainActivity.this, Month_One_Array));

        // on item click listener for grid view, start page selection
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              //  Library.getInstance().setCurrentBook(position);
                String potion= String.valueOf(Month_One_Array.get(position));
                Intent intent = new Intent(BookSelectionMainActivity.this, PageSelectionActivity.class);
                intent.putExtra("PageValue","2");
                startActivity(intent);
            }
        });
*/

    }
}
