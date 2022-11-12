package com.studybuddy.pc.brainmate.student.painting.coloring.coloring;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.studybuddy.pc.brainmate.R;
import com.studybuddy.pc.brainmate.mains.Apis;
import com.studybuddy.pc.brainmate.save.PermissionUtils;
import com.studybuddy.pc.brainmate.student.CommonMethods;
import com.studybuddy.pc.brainmate.student.Zoom;
import com.studybuddy.pc.brainmate.student.freehandpainting.FreehandActivityMain;
import com.studybuddy.pc.brainmate.student.painting.coloring.ColoringUtils;
import com.studybuddy.pc.brainmate.student.painting.coloring.color_picker.ColorPickerActivity;
import com.studybuddy.pc.brainmate.student.painting.util.Utils;
import com.studybuddy.pc.brainmate.student.Utility;


import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This is the main activity of the app, controlling the coloring of a picture (page in a book).
 */
public class ColoringActivity extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback, PermissionUtils.PermissionResultCallback{

    private int selectedColor = Color.BLUE;
    private int SELECT_FILE = 2;
    String PageValue;
    ColoringView coloringView;
    String Images = "";
    Context context;
    ArrayList<String> permissions = new ArrayList<>();
    PermissionUtils permissionUtils;
    boolean isPermissionGranted;
    /**/
    float[] lastEvent = null;
    float d = 0f;
    float newRot = 0f;
    private boolean isZoomAndRotate;
    private boolean isOutSide;
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    private PointF start = new PointF();
    private PointF mid = new PointF();
    float oldDist = 1f;
    private float xCoOrdinate, yCoOrdinate;
    /**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coloring);
        //setContentView(R.layout.activity_coloring);
        context = ColoringActivity.this;
        PageValue = getIntent().getStringExtra("PageValue") != null ? getIntent().getStringExtra("PageValue") : "1";
        Images = getIntent().getStringExtra("Bitmap");
        //Log.d("PageValue", PageValue);
        Bitmap bmp = null;
        String filename = getIntent().getStringExtra("image");
        try {
            FileInputStream is = this.openFileInput(filename);
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //TODO
        permissionUtils = new PermissionUtils(context);
        if (!isPermissionGranted) {
            permissionUtils = new PermissionUtils(context);
            //permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            permissionUtils.check_permission(permissions, "Need storage permission to save image.", 1);
        }
        /*if (PageValue.equals("2")) {
            selectImage();
        } else {
        }*/
        // color picker button action: show color picker activity
        View colorPickerButton = findViewById(R.id.colorPickerButton);
        colorPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ColoringActivity.this, ColorPickerActivity.class);
                startActivityForResult(intent, ColorPickerActivity.PICK_COLOR_REQUEST);
            }
        });

        // coloring view
        coloringView = findViewById(R.id.coloringView);
        ViewTreeObserver vto = coloringView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Utils.removeOnGlobalLayoutListener(coloringView, this);

                // load and set the coloring page bitmap after the ColoringView has been laid out and knows its size
                // Bitmap bitmap = Library.getInstance().loadCurrentPageBitmap();
                Bitmap bitmap = null;
                // coloringView.setBitmap(bitmap);

                /*try {
                    URL url = new URL("file:///assets/bheem.jpg");
                    InputStream ims = getAssets().open("bheem.jpg");
                    URLConnection connection =  url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap myBitmap = BitmapFactory.decodeStream(ims);
                    coloringView.setBitmap(myBitmap);
                    coloringView.invalidate();
                } catch (IOException e) {
                    // Log exception

                }
*/
               /* try {

                    Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
*/


                    /*coloringView.setBitmap(myBitmap);
                    coloringView.invalidate();*/

                    /*Glide.with(ColoringActivity.this).load("https://cdn.pixabay.com/photo/2017/08/30/12/45/girl-2696947_960_720.jpg")
                            .thumbnail(0.5f)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.placeholder)
                            .into(imgColoring);*/
                if (getIntent().getStringExtra("Images") != null) {
                    Log.d("ImagesImages", "" + getIntent().getStringExtra("Images"));
                    Glide.with(getApplicationContext())
                            .load(getIntent().getStringExtra("Images"))
                            //.load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTgb9Pn7Cw2XntCBLoM8PpGxrIugta3pRZnsdysd7_aZm15CqfZ")
                            .asBitmap()
                            //.into(new SimpleTarget<Bitmap>(500, 500) {
                            .into(new SimpleTarget<Bitmap>(1000, 800) {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                    if (resource != null) {
                                        //new Zoom(context,resource);
                                        coloringView.setBitmap(resource); // Possibly runOnUiThread()
                                        coloringView.invalidate();
                                    } else {
                                        Toast.makeText(ColoringActivity.this, "Try Again.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else if (getIntent().getStringExtra("url") != null && !getIntent().getStringExtra("url").equals("")) {
                    Log.d("ImagesImages", "" + getIntent().getStringExtra("url"));
                    Glide.with(getApplicationContext()).load(Apis.base_url1 + getIntent().getStringExtra("url"))
                            //.load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTgb9Pn7Cw2XntCBLoM8PpGxrIugta3pRZnsdysd7_aZm15CqfZ")
                            .asBitmap()
                            .into(new SimpleTarget<Bitmap>(1000, 800) {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                    if (resource != null) {
                                        //new Zoom(context,resource);
                                        coloringView.setBitmap(resource); // Possibly runOnUiThread()
                                        coloringView.invalidate();
                                    } else {
                                        Toast.makeText(ColoringActivity.this, "Try Again.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

               /* try {
                    URL url = new URL(Images);
                  //  Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    Bitmap myBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    Log.d("myBitmap", String.valueOf(myBitmap));
                    coloringView.setBitmap(myBitmap);
                    coloringView.invalidate();
                } catch(IOException e) {
                    System.out.println(e);
                }*/
              /*  Bitmap b =getBitmapFromURL(Images);
                Log.d("myBitmap", ""+String.valueOf(b));*/
            }
        });

       /* Glide.with(this).load(Images)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder)
                .into(coloringView);*/
        // back button action: check if modified (if so, ask for desired action)

        final ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!coloringView.isModified()) {
                    // not modified just finish
                    finish();
                } else {
                    final Context context = ColoringActivity.this;

                    boolean externalStorageAvailable = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
                    if (!externalStorageAvailable) {
                        Toast toast = Toast.makeText(context, getString(R.string.toast_external_storage_unavailable), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    DialogInterface.OnClickListener finishListener = new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    };
                    DialogInterface.OnClickListener saveImageListener = new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (!isPermissionGranted) {
                                permissionUtils = new PermissionUtils(context);
                                //permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                permissionUtils.check_permission(permissions, "Need storage permission to save image.", 1);
                            } else {
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
                                    //BufferedOutputStream bos = new BufferedOutputStream(out);
                                    //Bitmap bitmap1 = Bitmap.createBitmap(1000, 800, Bitmap.Config.ARGB_8888);
                                    Bitmap bitmap1 = Bitmap.createBitmap(coloringView.getWidth(), coloringView.getHeight(), Bitmap.Config.ARGB_8888);
                                    Canvas c1 = new Canvas(bitmap1);
                                    coloringView.draw(c1);
                                    //bitmap1.compress(Bitmap.CompressFormat.PNG, 50, bos);
                                    bitmap1.compress(Bitmap.CompressFormat.JPEG, 90, out);
                                    CommonMethods.showToast(context, bitmap1 != null ? "Image Saved Successfully" : "Unable to save images.");
                                    out.flush();
                                    out.close();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                MediaScannerConnection.scanFile(context, new String[]{output.toString()}, null,
                                        new MediaScannerConnection.OnScanCompletedListener() {
                                            public void onScanCompleted(String path, Uri uri) {
                                                Log.i("ExternalStorage", "Scanned " + path + ":");
                                                Log.i("ExternalStorage", "-> uri=" + uri);
                                                /*CommonMethods.showToast(context, uri != null ? "Image Saved Successfully" : "Unable to save images.");
                                                Toast.makeText(context, "Image Saved Successfully", Toast.LENGTH_SHORT).show();*/
                                            }
                                        });
                                finish();
                            }
                        }
                    };

                    // show alert dialog asking for "Finish?"
                    AlertDialog.Builder builder = new AlertDialog.Builder(ColoringActivity.this);
                    builder.setTitle(R.string.coloring_end_dialog_title);

                    // negative answer is cancel, which just doesn't do anything
                    builder.setNegativeButton(R.string.dialog_cancel, null);

                    if (externalStorageAvailable) {
                        builder.setNeutralButton(R.string.coloring_end_dialog_neutral, finishListener);
                        builder.setPositiveButton(R.string.coloring_end_dialog_positive, saveImageListener);
                    } else {
                        builder.setPositiveButton(R.string.coloring_end_dialog_neutral, finishListener);
                    }
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        // update color picker button with current active color
        //updateColorOfColorPickerButton();
    }



    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
        //Bitmap.createScaledBitmap(bitmap, 320, 480, false);
        Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50 / 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }


    public void svaeImage(Bitmap Paintbitmap) {
        Bitmap b = Paintbitmap;

        //create directory if not exist
        //File dir = new File("/sdcard/tempfolder/");
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myAppDir/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();
        File output = new File(dir, "ColoringImages" + ts + ".jpg");
        OutputStream os = null;
        try {
            os = new FileOutputStream(output);
            b.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
            //this code will scan the image so that it will appear in your gallery when you open next time
            MediaScannerConnection.scanFile(this, new String[]{output.toString()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.d("appname", "image is saved in gallery and gallery is refreshed.");
                        }
                    }
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the color of the color picker selected button with the actual color (a gradient from it).
     */
    private void updateColorOfColorPickerButton() {
        View view = findViewById(R.id.colorPickerButton);

        // takes the actually selected color
        int color = getSelectedColor();
        //int color =Color.BLUE;
        int[] gradientColors = ColoringUtils.colorSelectionButtonBackgroundGradient(color);

        if (Build.VERSION.SDK_INT < 16) {
            GradientDrawable newGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, gradientColors);
            newGradientDrawable.setStroke(1, Color.parseColor("#bbbbbb"));
            newGradientDrawable.setCornerRadius(ColoringActivity.this.getResources().getDimension(R.dimen.color_selection_button_corner_radius));
            //noinspection deprecation
            view.setBackgroundDrawable(newGradientDrawable);
        } else {
            GradientDrawable drawable = (GradientDrawable) view.getBackground();
            drawable.mutate();
            drawable.setColors(gradientColors);
        }
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public void setSelectedColor(int color) {
        Log.d("Coloclcoclco", "STR " + String.valueOf(color));
        selectedColor = color;
        SharedPreferences sharedPreferences = this.getSharedPreferences("Colors", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selectedColor", String.valueOf(selectedColor));
        editor.commit();

        SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
        editor1.putString("selectedColor", String.valueOf(selectedColor));
        editor1.apply();

    }

    public int getSelectedColor() {
        Log.d("Coloclcoclco", "SRT1  " + String.valueOf(selectedColor));
        int selectedcolor = selectedColor;
        return selectedColor;
    }

    /*   @Override
       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
           // handling of color picker activity result
           if (requestCode == ColorPickerActivity.PICK_COLOR_REQUEST && resultCode == RESULT_OK) {
               int color = data.getIntExtra("color", 0);
               // set color in library
               setSelectedColor(color);
               // updaste color of button
               updateColorOfColorPickerButton();
           }
       }*/


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        /*switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    galleryIntent();

                   *//* if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                 *//*   //code for deny

                }
                break;
        }*/
    }

    private void selectImage() {
        boolean result = Utility.checkPermission(ColoringActivity.this);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ColorPickerActivity.PICK_COLOR_REQUEST && resultCode == RESULT_OK) {
            int color = data.getIntExtra("color", 0);
            // set color in library
            setSelectedColor(color);
            // update color of button
            updateColorOfColorPickerButton();
        }
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
        coloringView.setBitmap(bm);
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
}
