package com.studybuddy.pc.brainmate.student;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.io.InputStream;


public class CommonMethods {

    private static final String strSharedPrefName = "StudyBuddyPreference";

   /* public static void showBigImage(String img_url, String title, Context context) {
        final Dialog fullscreenDialog = new Dialog(context, R.style.DialogFullscreen);
        fullscreenDialog.setContentView(R.layout.dialog_fullscreen);
        ImageView img_full_screen_dialog = fullscreenDialog.findViewById(R.id.img_full_screen_dialog);
        TextView txtDialogTitle = fullscreenDialog.findViewById(R.id.txtDialogTitle);
        txtDialogTitle.setText(title);
        Picasso.get().load(img_url).into(img_full_screen_dialog);
        ImageView img_dialog_fullscreen_close = fullscreenDialog.findViewById(R.id.img_dialog_fullscreen_close);
        img_dialog_fullscreen_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fullscreenDialog.dismiss();
            }
        });
        fullscreenDialog.show();
    }*/

    public static String getJson(Context context, String json) {
        return parseFileToString(context, json);
    }

    public static String parseFileToString(Context context, String filename) {
        try {
            InputStream stream = context.getAssets().open(filename);
            int size = stream.available();

            byte[] bytes = new byte[size];
            stream.read(bytes);
            stream.close();

            return new String(bytes);

        } catch (IOException e) {
            Log.i("GuiFormData", "IOException: " + e.getMessage());
        }
        return null;
    }

    public static void callWebserviceForResponse(StringRequest stringRequest, Context context) {
        int socketTimeout = 30000;//10 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    //TODO : Assign rest
    public static String getEmailId(Context context) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        return pref.getString("email", "");
    }

    public static void saveEmailId(Context context, String value) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("email", value);
        editor.apply();
    }

    public static String getId(Context context) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        return pref.getString("id", "");
    }

    public static void saveId(Context context, String value) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("id", value);
        editor.apply();
    }

    /**/
    public static String getUsername(Context context) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        return pref.getString("user_name", "");
    }

    public static void saveUsername(Context context, String value) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("user_name", value);
        editor.apply();
    }

    public static String getType(Context context) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        return pref.getString("type", "");
    }

    public static void saveType(Context context, String value) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("type", value);
        editor.apply();
    }

    /**/
    public static String getAccessCode(Context context) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        return pref.getString("AccessCode", "");
    }

    public static void saveAccessCode(Context context, String value) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("AccessCode", value);
        editor.apply();
    }

    //here 0 is logout ,1 is teacher login ,2 is student login.
    public static int getIsLogin(Context context) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        return pref.getInt("IsLogin", 0);
    }
 public static String getMsg(Context context) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        return pref.getString("msg", "0");
    }

    public static void saveIsLogin(Context context, int value) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("IsLogin", value);
        editor.apply();
    }
    public static void saveMsg(Context context, String value) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("msg", value);
        editor.apply();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
    }
}
