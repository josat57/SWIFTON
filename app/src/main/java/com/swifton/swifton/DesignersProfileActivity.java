package com.swifton.swifton;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.androidquery.AQuery;
import com.swifton.swifton.Helpers.AndyUtils;
import com.swifton.swifton.Helpers.AppSingleton;
import com.swifton.swifton.Helpers.AsyncTaskCompleteListener;
import com.swifton.swifton.Helpers.ParseContent;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class DesignersProfileActivity extends AppCompatActivity implements AsyncTaskCompleteListener {
    private static final int PICK_IMAGE_REQUEST =1;
    public static final String UPLOAD_KEY = "image";
    private Uri filepath;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";
    public static final String Designerid = "designeridkey";

    private ParseContent parseContent;
    private static final String IMAGE_DIRECTORY = "/logoserver";

    private AQuery aQuery;

    SharedPreferences sharedpreferences;

    EditText companyname, companyregno, companyaddress, country, companystate, companycity, emailaddress, companyphone, website, companyzipcode;
    Button cmdDone;
    ImageView companyLogo;

    ProgressDialog progressDialog;

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 2;

    String URL= "http:192.168.0.112/swiftonbe/app/create_company.php";

    //Live testing server url
    //String URL = "https://swiftontest.000webhostapp.com/swiftonbe/app/create_company.php";

    private static final String TAG = "CreateCompanyProfile";

    JSONParser jsonParser = new JSONParser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designers_profile);

        // Progress dialog
        progressDialog = new ProgressDialog(DesignersProfileActivity.this);
        progressDialog.setCancelable(false);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        companyname = findViewById(R.id.editcomname);
        companyregno = findViewById(R.id.editcomregno);
        emailaddress = findViewById(R.id.editcomemail);
        companyphone = findViewById(R.id.editcomphone);
        companyzipcode = findViewById(R.id.editcomzipcode);
        companyaddress = findViewById(R.id.editcomaddress);
        companycity = findViewById(R.id.editcomcity);
        companystate = findViewById(R.id.editcomstate);
        country = findViewById(R.id.editcomcountry);
        website = findViewById(R.id.editcomwebsite);
        companyLogo = findViewById(R.id.comprofileLogo);
        cmdDone = findViewById(R.id.editprofileBTN);


        cmdDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String compname = companyname.getText().toString().trim();
                final String compregno = companyregno.getText().toString().trim();
                final String compemail = emailaddress.getText().toString().trim();
                final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                final String compphone = companyphone.getText().toString().trim();
                final String comopzip = companyzipcode.getText().toString().trim();
                final String compaddress = companyaddress.getText().toString().trim();
                final String compcity = companycity.getText().toString().trim();
                final String compstate = companystate.getText().toString().trim();
                final String compcountry = country.getText().toString().trim();
                final String compwebsite = website.getText().toString().trim();
                final Bitmap complogo  = ((BitmapDrawable)companyLogo.getDrawable()).getBitmap();
                final String designID = sharedpreferences.getString("designeridkey", "");

                if(compname.isEmpty() || compemail.isEmpty() || compphone.isEmpty() || compaddress.isEmpty() || !compemail.matches(emailPattern)){
                    Toast.makeText(getApplicationContext(), "Please make sure you enter all fields correctly!", Toast.LENGTH_LONG).show();
                }else if(!compemail.isEmpty() && !compaddress.isEmpty() && !compphone.isEmpty() && compemail.matches(emailPattern)){

                    //Call the profile creation method
                    //Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(complogo);
                    updateprofile(compname, compregno, compaddress, compcity, compstate,
                            compcountry, comopzip, compemail, compphone, compwebsite, designID, path);

                    Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Cannot create the company profile at the moment", Toast.LENGTH_LONG).show();
                }
            }
        });

        companyLogo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(checkPermissionREAD_EXTERNAL_STORAGE(DesignersProfileActivity.this)) {
                    //Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    Intent galleryIntent = new Intent();
                    galleryIntent.setType("image/*");
                    galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(galleryIntent, "Select Company Logo"), PICK_IMAGE_REQUEST);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {

                    Toast.makeText(DesignersProfileActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI);
                    String  path = getStringImage(bitmap);
                    Log.i(TAG, "Image Path : " + path);
                     Bitmap btmap = BitmapFactory.decodeFile(path);
                    Bitmap conv_bm = getRoundedBitmap(btmap, 0);
                    companyLogo.setImageBitmap(conv_bm);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(DesignersProfileActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
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
                    showDialog("External storage", context,
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

    public void showDialog(final String msg, final Context context, final String permission) {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
                    Toast.makeText(this, "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

//    private void createCompanyProfile(final String compname, final String compregno, final String compaddress, final String compcity, final String compstate,
//                              final String compcountry, final String comopzip, final String compemail, final String compphone, final String compwebsite, final String designerid, final String encodedlogo) {
//        //Tag used to cancel the request
//        String cancel_req_tag = "signup";
//        progressDialog.setMessage("Creating Profile now!...");
//        showDialog();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                hideDialog();
//                Log.i("tagconvertstr", "["+response+"]");
//                try {
//                    JSONObject jObj = new JSONObject(response);
//                    int result = jObj.getInt("statuscode");
//
//                    if(result == 1){
//                        String message = jObj.getString("status");
//                        //String user = jObj.getString("email");
//                        Toast.makeText(getApplicationContext(), "Hi " +message, Toast.LENGTH_LONG).show();
//                        Intent mainIntent = new Intent(DesignersProfileActivity.this, DesignersDashboardActivity.class);
//                        startActivity(mainIntent);
//                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                        finish();
//                    }else if(result == 2){
//                        String message = jObj.getString("status");
//                        //String user = jObj.getString("email");
//                        Toast.makeText(getApplicationContext(), "Hi " +message, Toast.LENGTH_LONG).show();
//                    }else{
//                        String errorMsg = jObj.getString("status");
//                        //Toast.makeText(getApplicationContext(), name+ " "+ email, Toast.LENGTH_LONG).show();
//                        Toast.makeText(getApplicationContext(), "Sorry " + errorMsg, Toast.LENGTH_LONG).show();
//                    }
//                } catch (final JSONException e){
//                    e.printStackTrace();
//                    runOnUiThread(new Runnable() {
//                        public void run() {
//                            Toast.makeText(DesignersProfileActivity.this, e.toString(), Toast.LENGTH_LONG).show();
//                        }
//                    });
//                }
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Registration Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),"Sorry profile creation failed! "+error.getMessage(), Toast.LENGTH_LONG).show();
//                hideDialog();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                // Posting params to register url
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("compname", compname);
//                params.put("compregno", compregno);
//                params.put("compaddress", compaddress);
//                params.put("compcity", compcity);
//                params.put("compstate", compstate);
//                params.put("compcountry", compcountry);
//                params.put("compzip", comopzip);
//                params.put("compemail", compemail);
//                params.put("compphone", compphone);
//                params.put("compwebsite", compwebsite);
//                params.put("designerid", designerid);
//                params.put("complogo", encodedlogo);
//                return params;
//            }
//        };
//        // Adding request to request queue
//        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest, cancel_req_tag);
//    }

    private void showDialog(){
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }



    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        AndyUtils.removeSimpleProgressDialog();
        Log.d("res", response.toString());
        switch (serviceCode) {

            case PICK_IMAGE_REQUEST:
                if (parseContent.isSuccess(response)) {
                    String url = parseContent.getURL(response);
                    aQuery.id(companyLogo).image(url);
                }
        }
    }


    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        byte[] imageBytes = bytes.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.URL_SAFE);
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
            fo.write(Integer.parseInt(encodedImage));
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

    private void updateprofile( final String compname, final String compregno, final String compaddress, final String compcity, final String compstate,
                            final String compcountry, final String comopzip, final String compemail, final String compphone, final String compwebsite,
                            final String designerid, final String encodedlogo) {

        //Tag used to cancel the request
        String cancel_req_tag = "Update Profile";
        progressDialog.setMessage("Creating Profile now!...");
        showDialog();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("convertstr", "["+response+"]");
                        Log.d("uploade", response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            int result = jObj.getInt("statuscode");
                            if (result == 1) {
                                String message = jObj.getString("status");
                                //String user = jObj.getString("email");
                                Toast.makeText(getApplicationContext(), "Hi " + message, Toast.LENGTH_LONG).show();
                                Intent mainIntent = new Intent(DesignersProfileActivity.this, DesignersDashboardActivity.class);
                                startActivity(mainIntent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                finish();
                            } else if (result == 2) {
                                String message = jObj.getString("status");
                                //String user = jObj.getString("email");
                                Toast.makeText(getApplicationContext(), "Hi " + message, Toast.LENGTH_LONG).show();
                            } else {
                                String errorMsg = jObj.getString("status");
                                //Toast.makeText(getApplicationContext(), name+ " "+ email, Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(), "Sorry " + errorMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Profile Update Error: \t" + error.getMessage());
                Log.e(TAG, "Error StackTrace: \t" + Arrays.toString(error.getStackTrace()));
                Toast.makeText(DesignersProfileActivity.this,"Update failed! "+error.getMessage(), Toast.LENGTH_LONG).show();
                try {
                    byte[] htmlBodyBytes = error.networkResponse.data;
                    Log.e(TAG, new String(htmlBodyBytes), error);
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                if(error.getMessage() == null){
                    hideDialog();
                }
                Toast.makeText(getApplicationContext(), "Sorry profile update failed! " +
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("compname", compname);
                params.put("compregno", compregno);
                params.put("compaddress", compaddress);
                params.put("compcity", compcity);
                params.put("compstate", compstate);
                params.put("compcountry", compcountry);
                params.put("compzip", comopzip);
                params.put("compemail", compemail);
                params.put("compphone", compphone);
                params.put("compwebsite", compwebsite);
                params.put("designerid", designerid);
                params.put("filename", encodedlogo);
                return params;
            }
        };
        {
            int socketTimeout = 30000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

            //Adding request to request queue
            AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest, cancel_req_tag);
        }

    }
}
