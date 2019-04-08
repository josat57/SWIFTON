package com.swifton.swifton;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.icu.util.Calendar;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.swifton.swifton.Helpers.AppSingleton;
import com.swifton.swifton.Helpers.ParseContent;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;
import static com.swifton.swifton.DesignersProfileActivity.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;
import static com.swifton.swifton.Helpers.ImageFilePath.getPath;

public class ChangeProfileActivity extends AppCompatActivity {
    FloatingActionButton GalleryBtn, UpdateBtn;
    ImageView profilepic;
    private static final int PICK_IMAGE_REQUEST =1;

    String profileURL = "http:192.168.0.44/swiftonbe/app/uploadpics.php";
    //String updateprofileURL = "http:10.11.32.56/swiftonbe/app/update_user_profile.php";

    private ProgressDialog mprogressDialog;

    private ParseContent parseContent;
    private static final String IMAGE_DIRECTORY = "/profilepicsFarm";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        mprogressDialog = new ProgressDialog(ChangeProfileActivity.this);
        mprogressDialog.setCancelable(false);

        GalleryBtn = findViewById(R.id.gallery_btn);
        UpdateBtn = findViewById(R.id.update_btn);
        profilepic = findViewById(R.id.ppview);

        Bundle detailsbundle = getIntent().getExtras();
        final String email = detailsbundle.getString("email");
        final String username = detailsbundle.getString("username");
        final String schema = detailsbundle.getString("schema");

        if(!email.isEmpty()){
            Toast.makeText(ChangeProfileActivity.this, "Email " + email + "schema " + schema, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(ChangeProfileActivity.this, "the email is empty " + email, Toast.LENGTH_LONG).show();
        }
        GalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermissionREAD_EXTERNAL_STORAGE(ChangeProfileActivity.this)) {
                    //Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    Intent galleryIntent = new Intent();
                    galleryIntent.setType("image/*");
                    galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(galleryIntent, "Select Company Logo"), PICK_IMAGE_REQUEST);
                }
            }
        });

        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Bitmap userprofilepic  = ((BitmapDrawable)profilepic.getDrawable()).getBitmap();
                //final String request = "logindesigners";
                String profileimg = saveImage(userprofilepic); //Convert the image to string format and parse it
                InputStream inputStream = null;//You can get an inputStream using any IO API
                try {
                    inputStream = new FileInputStream(profileimg);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                byte[] bytes;
                byte[] buffer = new byte[8192];
                int bytesRead;
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                try {
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bytes = output.toByteArray();
                String encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);
                final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if(!encodedString.isEmpty()) {
                    if (!email.isEmpty() && !email.matches(emailPattern)) {
                        Toast.makeText(ChangeProfileActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                    } else if (email.matches(emailPattern)) {
                        Toast.makeText(ChangeProfileActivity.this, "attempting to update " + schema, Toast.LENGTH_SHORT).show();
                        updateUserProfile(username, email, profileimg, schema);
                    } else {
                        Toast.makeText(ChangeProfileActivity.this, "An annonynous profile...", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ChangeProfileActivity.this, "No profile Image Selected", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {

                    Toast.makeText(ChangeProfileActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    String path =  getPath(this,contentURI);
                    Log.i(TAG, "Image Path : " + path);
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    Bitmap conv_bm = getRoundedBitmap(bitmap, 0);
                    profilepic.setImageBitmap(conv_bm);
                    Toast.makeText(ChangeProfileActivity.this, "Image converted successfuly!", Toast.LENGTH_LONG);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ChangeProfileActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog1("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog1(final String msg, final Context context, final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();


    }

    public static Bitmap getRoundedBitmap(Bitmap bitmap, int rpx) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final int px = rpx;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, px, px, paint);

        bitmap.recycle();

        return output;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
                    Toast.makeText(ChangeProfileActivity.this, "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

    private void showDialog() {
        if (!mprogressDialog.isShowing())
            mprogressDialog.show();
    }

    private void hideDialog() {
        if (mprogressDialog.isShowing())
            mprogressDialog.dismiss();
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        byte[] imageBytes = bytes.toByteArray();
        //String encodedImage = Base64.encodeToString(imageBytes, Base64.URL_SAFE);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            }
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(imageBytes);
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void updateUserProfile(final String username, final String email, final String filename, final String schema) {
        // Tag used to cancel the request
        String cancel_req_tag = "Profile";
        mprogressDialog.setMessage("Updating Profile");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, profileURL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("convertstr", "["+response+"]");
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    int result = jObj.getInt("statuscode");
                    Toast.makeText(ChangeProfileActivity.this, "this is profile status code = " + result, Toast.LENGTH_LONG).show();

                    if (result == 1) {
                        String message = jObj.getString("status");
                        Toast.makeText(ChangeProfileActivity.this, "Hi " + message, Toast.LENGTH_LONG).show();
                        Intent dashboardIntent = new Intent(ChangeProfileActivity.this, UserDashboardActivity.class);
                        dashboardIntent.putExtra("username", username);
                        dashboardIntent.putExtra("email", email);
                        //dashboardIntent.putExtra("profilepic", (Parcelable) profilepic);
                        //dashboardIntent.putExtra("deviceid", deviceid);
                        startActivity(dashboardIntent);
                        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                        finish();
                    } else if (result == 2) {
                        String message = jObj.getString("status");
                        Toast.makeText(ChangeProfileActivity.this, "Hi " + message, Toast.LENGTH_LONG).show();
                    } else {
                        String errorMsg = jObj.getString("status");
                        Toast.makeText(ChangeProfileActivity.this, "Sorry " + errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(ChangeProfileActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Profile Error: " + error.getMessage());
                Toast.makeText(ChangeProfileActivity.this, "Profile update failed" + error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("email", email);
                params.put("filename", filename);
                params.put("schema", schema);
                return params;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);

        // Adding request to request queue
        AppSingleton.getInstance(ChangeProfileActivity.this).addToRequestQueue(strReq,cancel_req_tag);
    }
}
