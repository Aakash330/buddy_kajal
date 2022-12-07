package com.studybuddy.pc.brainmate.student.freehandpainting;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.studybuddy.pc.brainmate.CertificateClass0S6;
import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.adapters.ColorGridImgAdapter;
import com.studybuddy.pc.brainmate.save.PermissionUtils;
import com.studybuddy.pc.brainmate.student.CommonMethods;
import com.studybuddy.pc.brainmate.student.Utility;
import com.studybuddy.pc.brainmate.teacher.ManageClickInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class FreehandActivityMain extends AppCompatActivity implements View.OnClickListener,ActivityCompat.OnRequestPermissionsResultCallback, PermissionUtils.PermissionResultCallback {

    private DrawingView mDrawingView;
    private ImageButton currPaint, drawButton, eraseButton, newButton, saveButton, Morecolors, buttonGetImg;
    private float smallBrush_omne, smallBrush, mediumBrush_one, mediumBrush, largeBrush_one, largeBrush;
    ImageView extraImageview;
    private ArrayList<HashMap<String, String>> grid_imgList = new ArrayList<>();
    private int SELECT_FILE = 2;
    String PageValue, class_name;
    // list of permissions
    ArrayList<String> permissions = new ArrayList<>();
    PermissionUtils permissionUtils;
    boolean isPermissionGranted;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freehand_acitiviy_main);
        context = FreehandActivityMain.this;
        class_name = getIntent().getStringExtra("className") != null ? getIntent().getStringExtra("className") : "";
        PageValue = getIntent().getStringExtra("PageValue") != null ? getIntent().getStringExtra("PageValue") : "2";
        //Log.d("PageValue", PageValue);
        /*if (PageValue.equals("1")) {
            selectImage();
        } else {

        }*/
        permissionUtils = new PermissionUtils(context);
        if (!isPermissionGranted) {
            permissionUtils = new PermissionUtils(context);
            //permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            permissionUtils.check_permission(permissions, "Need storage permission to save image.", 1);
        }


        mDrawingView = (DrawingView) findViewById(R.id.drawing);
        // Getting the initial paint color.
        LinearLayout paintLayout = (LinearLayout) findViewById(R.id.paint_colors);
        // selectImage();
        // 0th child is white color, so selecting first child to give black as initial color.
        currPaint = (ImageButton) paintLayout.getChildAt(1);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.pallet_pressed));
        drawButton = (ImageButton) findViewById(R.id.buttonBrush);
        drawButton.setOnClickListener(this);
        eraseButton = (ImageButton) findViewById(R.id.buttonErase);
        eraseButton.setOnClickListener(this);
        newButton = (ImageButton) findViewById(R.id.buttonNew);
        newButton.setOnClickListener(this);
        saveButton = (ImageButton) findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(this);
        buttonGetImg = (ImageButton) findViewById(R.id.buttonGetImg);
        buttonGetImg.setOnClickListener(this);

        smallBrush = getResources().getInteger(R.integer.small_size);
        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);
        smallBrush_omne = getResources().getInteger(R.integer.small_size_one);
        mediumBrush_one = getResources().getInteger(R.integer.medium_size_one);
        largeBrush_one = getResources().getInteger(R.integer.large_size_one);

        Morecolors = (ImageButton) findViewById(R.id.Morecolors);
        // Set the initial brush size
        extraImageview = (ImageView) findViewById(R.id.extraImageview);
        mDrawingView.setBrushSize(mediumBrush);
        Morecolors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(FreehandActivityMain.this);
                dialog.setContentView(R.layout.layoutcolors);
                dialog.setTitle("Choose Color");
                // set the custom dialog components - text, image and button
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });
        Log.d("urlImg", "http://www.techive.in/studybuddy/" + getIntent().getStringExtra("url"));
        if (getIntent().getStringExtra("url") != null && !getIntent().getStringExtra("url").equals("")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                Glide.with(getApplicationContext())
                        //.load(getIntent().getStringExtra("Images"))
                        //.load("http://www.techive.in/studybuddy/painting_img/1543054251rose.png")
                        //.load("http://www.techive.in/studybuddy/painting_img/1547033808draw_img.jpg")
                        .load("http://www.techive.in/studybuddy/" + getIntent().getStringExtra("url"))
                        .asBitmap()
                        //.into(new SimpleTarget<Bitmap>(500, 500) {
                        .into(new SimpleTarget<Bitmap>(1000, 800) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                if (resource != null) {
                                    mDrawingView.setBackground(new BitmapDrawable(getResources(), resource));
                                    mDrawingView.invalidate();
                                } else {
                                    Toast.makeText(FreehandActivityMain.this, "Try Again.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    }

    /**
     * Method is called when color is clicked from pallet.
     *
     * @param view ImageButton on which click took place.
     */
    public void paintClicked(View view) {
        if (view != currPaint) {
            // Update the color
            ImageButton imageButton = (ImageButton) view;
            String colorTag = imageButton.getTag().toString();
            mDrawingView.setColor(colorTag);
            // Swap the backgrounds for last active and currently active image button.
            imageButton.setImageDrawable(getResources().getDrawable(R.drawable.pallet_pressed));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.pallet));
            currPaint = (ImageButton) view;
            mDrawingView.setErase(false);
            mDrawingView.setBrushSize(mDrawingView.getLastBrushSize());
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.buttonBrush:
                // Show brush size chooser dialog
                showBrushSizeChooserDialog();
                break;
            case R.id.buttonErase:
                // Show eraser size chooser dialog
                showEraserSizeChooserDialog();
                break;
            case R.id.buttonNew:
                // Show new painting alert dialog
                showNewPaintingAlertDialog();
                break;
            case R.id.buttonSave:
                // Show save painting confirmation dialog.
                if (!isPermissionGranted) {
                    permissionUtils = new PermissionUtils(context);
                    //permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                    permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    permissionUtils.check_permission(permissions, "Need storage permission to save image.", 1);
                }else {
                    showSavePaintingConfirmationDialog();
                }
                break;
            case R.id.buttonGetImg:
                // Show save painting confirmation dialog.
                show_freehand_dialog();
                break;
        }
    }

    ProgressDialog progressDialog;

    private void show_freehand_dialog() {
        final Dialog dialog = new Dialog(FreehandActivityMain.this);
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.coloring_grid);
        dialog.show();
        final RecyclerView rVShowImg = dialog.findViewById(R.id.rVShowImg);

        progressDialog = new ProgressDialog(FreehandActivityMain.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
       // RequestQueue queue1 = Volley.newRequestQueue(FreehandActivityMain.this);
        RequestQueue queue1 =Volley.newRequestQueue(context, new HurlStack(null, CertificateClass0S6.getSslSocketFactory(context)));
        String url1 = "http://www.techive.in/studybuddy/api/painting_images.php";
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Painti9ng", "" + response);
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        try {
                            grid_imgList.clear();
                            JSONObject jsonObject1 = new JSONObject(response);
                            Log.d("login_succes", "" + jsonObject1.getString("success"));
                            String LoginCredential = jsonObject1.getString("success");
                            JSONArray PaintImages = jsonObject1.getJSONArray("images");
                            for (int j = 0; j < PaintImages.length(); j++) {
                                JSONObject c = PaintImages.getJSONObject(j);
                                Log.d("login_succes", "" + c.getString("image"));
                                HashMap<String, String> ObjectiveMap = new HashMap<>();
                                ObjectiveMap.put("Image", c.getString("image"));
                                ObjectiveMap.put("Bitmap", c.getString("bitmap"));
                                grid_imgList.add(ObjectiveMap);
                                ColorGridImgAdapter colorGridImgAdapter = new ColorGridImgAdapter(FreehandActivityMain.this, grid_imgList, new ManageClickInterface() {
                                    @Override
                                    public void onManageClick(int position) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                            Glide.with(getApplicationContext())
                                                    //.load(getIntent().getStringExtra("Images"))
                                                    //.load("http://www.techive.in/studybuddy/painting_img/1543054251rose.png")
                                                    //.load("http://www.techive.in/studybuddy/painting_img/1547033808draw_img.jpg")
                                                    .load("http://www.techive.in/studybuddy/" + grid_imgList.get(position).get("Image"))
                                                    .asBitmap()
                                                    //.into(new SimpleTarget<Bitmap>(500, 500) {
                                                    .into(new SimpleTarget<Bitmap>(1000, 800) {
                                                        @Override
                                                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                                            if (resource != null) {
                                                                mDrawingView.setBackground(new BitmapDrawable(getResources(), resource)); // Possibly runOnUiThread()
                                                                mDrawingView.invalidate();
                                                            } else {
                                                                Toast.makeText(FreehandActivityMain.this, "Try Again.", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                            //mDrawingView.setBackgroundResource(R.drawable.ic_launcher);
                                        }
                                        dialog.dismiss();
                                    }
                                });
                                rVShowImg.setLayoutManager(new GridLayoutManager(FreehandActivityMain.this, 3));
                                rVShowImg.setAdapter(colorGridImgAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                System.out.println("ResponseRegistration" + error.getMessage());
            }
        }) {
            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                java.util.Map<String, String> params = new HashMap<>();
                //TODO make changes
                params.put("class", class_name);
                Log.d("className", "" + params.toString());
                return params;
            }
        };
        queue1.add(stringRequest1);
    }

    private void showBrushSizeChooserDialog() {
        final Dialog brushDialog = new Dialog(this);
        brushDialog.setContentView(R.layout.dialog_brush_size);
        brushDialog.setTitle("Brush size:");

        ImageButton smallBtn = (ImageButton) brushDialog.findViewById(R.id.small_brush);
        smallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.setBrushSize(smallBrush_omne);
                mDrawingView.setLastBrushSize(smallBrush_omne);
                brushDialog.dismiss();
            }
        });
        ImageButton smallBtn1 = (ImageButton) brushDialog.findViewById(R.id.small_brush_one);
        smallBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.setBrushSize(smallBrush);
                mDrawingView.setLastBrushSize(smallBrush);
                brushDialog.dismiss();
            }
        });
        ImageButton mediumBtn = (ImageButton) brushDialog.findViewById(R.id.medium_brush);
        mediumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.setBrushSize(mediumBrush_one);
                mDrawingView.setLastBrushSize(mediumBrush_one);
                brushDialog.dismiss();
            }
        });
        ImageButton mediumBtn1 = (ImageButton) brushDialog.findViewById(R.id.medium_brush_one);
        mediumBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.setBrushSize(mediumBrush);
                mDrawingView.setLastBrushSize(mediumBrush);
                brushDialog.dismiss();
            }
        });

        ImageButton largeBtn = (ImageButton) brushDialog.findViewById(R.id.large_brush);
        largeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.setBrushSize(largeBrush_one);
                mDrawingView.setLastBrushSize(largeBrush_one);
                brushDialog.dismiss();
            }
        });
        ImageButton largeBtn1 = (ImageButton) brushDialog.findViewById(R.id.large_brush_one);
        largeBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.setBrushSize(largeBrush);
                mDrawingView.setLastBrushSize(largeBrush);
                brushDialog.dismiss();
            }
        });
        mDrawingView.setErase(false);
        brushDialog.show();
    }

    private void showEraserSizeChooserDialog() {
        final Dialog brushDialog = new Dialog(this);
        brushDialog.setTitle("Eraser size:");
        brushDialog.setContentView(R.layout.dialog_brush_size);
        ImageButton smallBtn = (ImageButton) brushDialog.findViewById(R.id.small_brush);
        smallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.setErase(true);
                mDrawingView.setLastBrushSize(smallBrush_omne);
                brushDialog.dismiss();
            }
        });
        ImageButton smallBtn1 = (ImageButton) brushDialog.findViewById(R.id.small_brush_one);
        smallBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.setErase(true);
                mDrawingView.setLastBrushSize(smallBrush);
                brushDialog.dismiss();
            }
        });
        ImageButton mediumBtn = (ImageButton) brushDialog.findViewById(R.id.medium_brush);
        mediumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.setErase(true);
                mDrawingView.setLastBrushSize(mediumBrush_one);
                brushDialog.dismiss();
            }
        });
        ImageButton mediumBtn1 = (ImageButton) brushDialog.findViewById(R.id.medium_brush_one);
        mediumBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.setErase(true);
                mDrawingView.setLastBrushSize(mediumBrush);
                brushDialog.dismiss();
            }
        });

        ImageButton largeBtn = (ImageButton) brushDialog.findViewById(R.id.large_brush);
        largeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.setErase(true);
                mDrawingView.setLastBrushSize(largeBrush_one);
                brushDialog.dismiss();
            }
        });
        ImageButton largeBtn1 = (ImageButton) brushDialog.findViewById(R.id.large_brush_one);
        largeBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.setErase(true);
                mDrawingView.setLastBrushSize(largeBrush);
                brushDialog.dismiss();
            }
        });
      /*  ImageButton smallBtn = (ImageButton)brushDialog.findViewById(R.id.small_brush);
        smallBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mDrawingView.setErase(true);
                mDrawingView.setBrushSize(smallBrush);
                brushDialog.dismiss();
            }
        });
        ImageButton mediumBtn = (ImageButton)brushDialog.findViewById(R.id.medium_brush);
        mediumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.setErase(true);
                mDrawingView.setBrushSize(mediumBrush);
                brushDialog.dismiss();
            }
        });
        ImageButton largeBtn = (ImageButton)brushDialog.findViewById(R.id.large_brush);
        largeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.setErase(true);
                mDrawingView.setBrushSize(largeBrush);
                brushDialog.dismiss();
            }
        });*/
        brushDialog.show();
    }

    private void showNewPaintingAlertDialog() {
        AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
        newDialog.setTitle("New drawing");
        newDialog.setMessage("Start new drawing (you will lose the current drawing)?");
        newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mDrawingView.startNew();
                dialog.dismiss();
            }
        });
        newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        newDialog.show();
    }

    private void showSavePaintingConfirmationDialog() {
        AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
        saveDialog.setTitle("Save drawing");
        saveDialog.setMessage("Save drawing to device Gallery?");
        saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Bitmap bitmap = Bitmap.createBitmap(mDrawingView.getWidth(),
                        mDrawingView.getHeight(),
                        Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(bitmap);
                mDrawingView.draw(c);

                svaeImage(bitmap);
            }
        });
        saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        saveDialog.show();
    }

    // Permission check functions


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // redirects to utils
        permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void PermissionGranted(int request_code) {
        Log.i("PERMISSION", "GRANTED");
        isPermissionGranted = true;
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
        Log.i("PERMISSION PARTIALLY", "GRANTED");
    }

    @Override
    public void PermissionDenied(int request_code) {
        Log.i("PERMISSION", "DENIED");
    }

    @Override
    public void NeverAskAgain(int request_code) {
        Log.i("PERMISSION", "NEVER ASK AGAIN");
    }


    private void saveImageToExternalStorage(Bitmap finalBitmap) {
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/saved_images_1");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Tell the media scanner about the new file so that it is
        // immediately available to the user.
        MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
    }

    public void svaeImage(Bitmap Paintbitmap) {
        //Bitmap b = Paintbitmap;
        //create directory if not exist
        //File dir = new File("/sdcard/tempfolder/");
        //File externalStorageDir = FreehandActivityMain.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File dir = new File(root, "/study_buddy");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();
        File output = new File(dir, "FreehandPainting" + ts + ".jpg");
        try {
            FileOutputStream out = new FileOutputStream(output);
            Paintbitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*try {
            FileOutputStream fileOutputStream = new FileOutputStream(output);
            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

            // choose another format if PNG doesn't suit you
            Bitmap bitmap1 = Bitmap.createBitmap(1280, 1280, Bitmap.Config.ARGB_8888);
            Canvas c1 = new Canvas(bitmap1);
            mDrawingView.draw(c1);
            bitmap1.compress(Bitmap.CompressFormat.PNG, 50, bos);

            bos.flush();
            bos.close();
            Log.d("getBitmap", "" + bitmap1);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        MediaScannerConnection.scanFile(FreehandActivityMain.this, new String[]{output.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                        CommonMethods.showToast(context, uri != null ? "Image Saved Successfully" : "Unable to save images.");
                    }
                });
    }

    public void displayImgInGallery(File file) {
        MediaScannerConnection.scanFile(FreehandActivityMain.this, new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
    }

    private void selectImage() {
        boolean result = Utility.checkPermission(FreehandActivityMain.this);
        if (result) {
            galleryIntent();
        }

    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == SELECT_FILE) {
                    onSelectFromGalleryResult(data);
                }
           /* else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) throws IOException {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                Log.d("ImageRowww", String.valueOf(bm));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
        }
        //ivImage.setImageBitmap(bm);
        Bitmap workingBitmap = bm;
        Bitmap mutableBitmap = bm.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        Log.d("canvascanvas", String.valueOf(canvas));
        mDrawingView.draw(canvas);
    }
}
