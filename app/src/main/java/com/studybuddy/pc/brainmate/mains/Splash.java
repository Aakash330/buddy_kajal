package com.studybuddy.pc.brainmate.mains;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.studybuddy.pc.brainmate.student.CommonMethods;
import com.studybuddy.pc.brainmate.student.Stu_Classes;
import com.studybuddy.pc.brainmate.student.StudentdashBord;
import com.studybuddy.pc.brainmate.teacher.Books_Access_Code;
import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.teacher.Main2Activity;
import com.studybuddy.pc.brainmate.teacher.TeacherInactiveActivity;

public class Splash extends Activity {

    private static int SPLASH_TIME_OUT = 1000;
    String Status = "0";
    String Teachers_ID, Access_code, Password, Email;
    Context context;
    private String TAG="Splash";

    /*int a = 0;
    int b = 1;
    int c = 0;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = Splash.this;
        SharedPreferences prefs = getSharedPreferences("LoginDetails", MODE_PRIVATE);
        Email = prefs.getString("Email", "0");
        Password = prefs.getString("Password", "0");
        Access_code = prefs.getString("Access_code", "0");
        Teachers_ID = prefs.getString("Teachers_ID", "0");

        Status = prefs.getString("Status", "0");

       // Toast.makeText(context, "Login type:"+CommonMethods.getIsLogin(this), Toast.LENGTH_SHORT).show();
        /*for (int i = 0; i < 10; i++) {
            int temp;
            c = a + b;
            if (i == 0) {
                Log.d("val_fab", "0");
            }
            if (i == 1) {
                Log.d("val_fab", "1");
            }
            Log.d("val_fab", String.valueOf(c));
            //Log.d("val_fab","0");
            temp = a;
            a = b;
            b = c;
        }*/

        /*boolean flag = false;
        for (int i = 1; i < 4; i++) {
            System.out.print(String.valueOf(i));
            for (int j = 0; j < i; j++) {
                System.out.print("*");
            }
            System.out.println("");
        }*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (CommonMethods.getIsLogin(context) == 0) {
                    Intent i = new Intent(context, LoginBothActivity.class);
                    startActivity(i);
                    finish();
                }if (CommonMethods.getIsLogin(context) == 3) {
                    Intent i = new Intent(context, TeacherInactiveActivity.class);
                    i.putExtra("email", "" + CommonMethods.getEmailId(context));
                    i.putExtra("pass", "" +CommonMethods.getUsername(context));
                    startActivity(i);
                    finish();
                } else if (CommonMethods.getIsLogin(context) == 1) {

                    CommonMethods.getEmailId(context);
                    CommonMethods.getId(context);
                    CommonMethods.getUsername(context);
                    CommonMethods.getType(context);
                    CommonMethods.getIsLogin(context);//Teachers==2
                    CommonMethods.getAccessCode(context);

                    Intent intent = new Intent(context, Stu_Classes.class);
                    intent.putExtra("name", "" + CommonMethods.getUsername(context));
                    intent.putExtra("email", "" + CommonMethods.getEmailId(context));
                    intent.putExtra("accesscodes", "" +CommonMethods.getAccessCode(context));
                    intent.putExtra("Student_ID", CommonMethods.getId(context));
                    Log.w(TAG,"AC:"+ CommonMethods.getAccessCode(context));
                    Log.w(TAG,"Email:"+CommonMethods.getEmailId(context));
                    Log.w(TAG,"UN:"+CommonMethods.getUsername(context));

                    startActivity(intent);

                    finish();
                } else if (CommonMethods.getIsLogin(context) == 2) {

                    CommonMethods.getEmailId(context);
                    CommonMethods.getId(context);
                    CommonMethods.getUsername(context);
                    CommonMethods.getType(context);
                    CommonMethods.getIsLogin(context);//Student==2
                    CommonMethods.getAccessCode(context);

                    Intent intent = new Intent(context,Main2Activity.class);
                    intent.putExtra("name", "" + CommonMethods.getUsername(context));
                    intent.putExtra("email", "" + CommonMethods.getEmailId(context));
                    intent.putExtra("accesscodes", "" + CommonMethods.getAccessCode(context));
                    intent.putExtra("Teachers_ID", CommonMethods.getId(context));
                    Log.w(TAG,"AC:"+ CommonMethods.getAccessCode(context));
                    Log.w(TAG,"Email:"+CommonMethods.getEmailId(context));
                    Log.w(TAG,"UN:"+CommonMethods.getUsername(context));

                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);


        //region "Commented"
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Status.equals("0")) {
                    Intent i = new Intent(context, LoginBothActivity.class);
                    startActivity(i);
                    finish();
                } else if (Status.equals("1")) {
                    Intent i = new Intent(context, StudentdashBord.class);
                    startActivity(i);
                    finish();
                } else if (Status.equals("2")) {
                    Intent i = new Intent(context, Books_Access_Code.class);
                    i.putExtra("accesscodes", "2222");
                    i.putExtra("Teachers_ID", Teachers_ID);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);*/
        //endregion
    }
}
