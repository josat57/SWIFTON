package com.swifton.swifton.Fragments;


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
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.swifton.swifton.ChangeProfileActivity;
import com.swifton.swifton.Helpers.AppSingleton;
import com.swifton.swifton.Helpers.ImageLoader;
import com.swifton.swifton.Helpers.ParseContent;
import com.swifton.swifton.R;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.android.volley.VolleyLog.TAG;
import static com.swifton.swifton.DesignersProfileActivity.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;
import static com.swifton.swifton.Helpers.ImageFilePath.getPath;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment{

    public static final String LoginPREFERENCES = "loginPrefs" ;
    public static final String UserName = "loginnameKey";
    public static final String Phone = "loginidKey";
    public static final String UserEmail = "loginemailKey";
    public static final String DeeviceUId = "deviceuidkey";

    String musername, museremail;

    private static final int PICK_IMAGE_REQUEST =1;

    private ParseContent parseContent;
    private static final String IMAGE_DIRECTORY = "/logoserver";

    private AQuery aQuery;
    private Uri filepath;

    SharedPreferences sharedpreferences;

    TextView vusername, vfirstname, vlastname, vaddress, vcity, vstate, vzipcode, vcountry, vemail, vphone, vbio;

    EditText eusername, efirstname, elastname, eaddress, ecity, estate, ezipcode, ecountry, eemail, ephone, ebio;

    ImageView vprofilepic, eprofilepic;
    FloatingActionButton toggleFab;
    Button updatebtn;

    LinearLayout editprofile, viewprofile;
    int loader = R.drawable.loader;


    String profileURL = "http:192.168.0.109/swiftonbe/app/get_user_profile.php";
    //String profileURL = "http:10.11.32.56/swiftonbe/app/get_user_profile.php";

    String updateprofileURL = "http:192.168.0.109/swiftonbe/app/update_user_profile.php";
    //String updateprofileURL = "http:10.11.32.56/swiftonbe/app/update_user_profile.php";

    ProgressDialog progressDialog;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        sharedpreferences = getActivity().getSharedPreferences(LoginPREFERENCES, Context.MODE_PRIVATE);
        toggleFab = view.findViewById(R.id.toggleFab);
        updatebtn = view.findViewById(R.id.updateBtn);

        viewprofile = view.findViewById(R.id.viewuserprofile);
        editprofile = view.findViewById(R.id.edituserprofile);

        vprofilepic = view.findViewById(R.id.viewuserprofileLogo);
        vusername = view.findViewById(R.id.viewusername);
        vfirstname = view.findViewById(R.id.viewfirstname);
        vlastname = view.findViewById(R.id.viewlastname);
        vaddress = view.findViewById(R.id.viewaddress);
        vcity = view.findViewById(R.id.viewcity);
        vstate = view.findViewById(R.id.viewstate);
        vcountry = view.findViewById(R.id.viewcountry);
        vemail = view.findViewById(R.id.viewemail);
        vphone = view.findViewById(R.id.viewphone);
        vzipcode = view.findViewById(R.id.viewpostalcode);

        eprofilepic = view.findViewById(R.id.edituserprofileLogo);
        eusername = view.findViewById(R.id.editusername);
        efirstname = view.findViewById(R.id.editfirstname);
        elastname = view.findViewById(R.id.editlastname);
        eaddress = view.findViewById(R.id.editaddress);
        ecity = view.findViewById(R.id.editcity);
        estate = view.findViewById(R.id.editstate);
        ezipcode = view.findViewById(R.id.editpostalcode);
        ecountry = view.findViewById(R.id.editcountry);
        ///eemail = view.findViewById(R.id.editemail);
        ephone = view.findViewById(R.id.editphone);


        //final String email = sharedpreferences.getString("loginemailkey", "").trim();
        final Bundle bundle = this.getArguments();
        String email = (String.valueOf(bundle.getString("useremail")));
        Log.d(TAG, email);

        if(!email.isEmpty()){
            Toast.makeText(getActivity(), "deviceUID " + email, Toast.LENGTH_SHORT).show();
            loadUserProfile(email);
        }else{
            Toast.makeText(getActivity(), "the email is empty " + email, Toast.LENGTH_LONG).show();
        }

        toggleFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewprofile.getVisibility() == View.GONE){
                    editprofile.setVisibility(View.GONE);
                    viewprofile.setVisibility(View.VISIBLE);
                }else {
                    viewprofile.setVisibility(View.GONE);
                    editprofile.setVisibility(View.VISIBLE);
                    museremail = vemail.getText().toString();
                    musername = vusername.getText().toString();
                }
            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = (String.valueOf(bundle.getString("useremail")));
                Toast.makeText(getActivity(), "Sending email " +email, Toast.LENGTH_LONG);
                final String username = eusername.getText().toString().trim();
                final String firstname = efirstname.getText().toString().trim();
                final String lastname = elastname.getText().toString().trim();
                final String address = eaddress.getText().toString().trim();
                final String city = ecity.getText().toString().trim();
                final String state = estate.getText().toString().trim();
                final String country = ecountry.getText().toString().trim();
                final String zipcode = ezipcode.getText().toString().trim();
                final String phone = ephone.getText().toString().trim();
                final Bitmap userprofilepic  = ((BitmapDrawable)eprofilepic.getDrawable()).getBitmap();
                //final String request = "logindesigners";
                final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                ///String profileimage = saveImage(userprofilepic); //Convert the image to string format and parse it

                if (firstname.isEmpty() || lastname.isEmpty() || username.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_LONG).show();
                } else if (!email.matches(emailPattern)) {
                    Toast.makeText(getActivity(), "Invalid email address", Toast.LENGTH_SHORT).show();
                } else if (email.matches(emailPattern)) {
                    updateUserProfile(username, firstname, lastname, address, city, state, country, zipcode, phone, email);
                } else {
                    Toast.makeText(getActivity(), "Could not update your profile...", Toast.LENGTH_LONG).show();
                }
            }
        });


        eprofilepic.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
//                if(checkPermissionREAD_EXTERNAL_STORAGE(getActivity())) {
//                    //Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    Intent galleryIntent = new Intent();
//                    galleryIntent.setType("image/*");
//                    galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(Intent.createChooser(galleryIntent, "Select Company Logo"), PICK_IMAGE_REQUEST);
//                }

                Intent ppviewIntent =  new Intent(getActivity(), ChangeProfileActivity.class);
                String mschema = "appusers";

                ppviewIntent.putExtra("username", musername);
                ppviewIntent.putExtra("email", museremail);
                ppviewIntent.putExtra("deviceuid", mschema);
                Toast.makeText(getActivity(), "hey " + musername +" "+ museremail + " " +mschema, Toast.LENGTH_LONG).show();
                startActivity(ppviewIntent);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                getActivity().finish();

            }
        });


        return view;
    }

    //Load the Top DesignersProfileHolder data
    private void loadUserProfile(final String email) {
        // Tag used to cancel the request
        String cancel_req_tag = "Profile";
        progressDialog.setMessage("Loading Profile...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, profileURL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("tagconvertstr", "["+response+"]");
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    int statuscode = jObj.getInt("statuscode");
                    Toast.makeText(getActivity(), "this is company status code = " + statuscode, Toast.LENGTH_LONG).show();

                    if (statuscode == 1) {
                        //int id = jObj.getInt("id");
                        String username = jObj.getString("username"),
                                firstname = jObj.getString("firstname"),
                                lastname = jObj.getString("lastname"),
                                address = jObj.getString("address"),
                                city = jObj.getString("city"),
                                state = jObj.getString("state"),
                                country = jObj.getString("country"),
                                email = jObj.getString("email"),
                                phone = jObj.getString("phone"),
                                zipcode = jObj.getString("zipcode"),
                                //profilepic = jObj.getString("profilepic");
                                profilepic = "http:192.168.0.114/swiftonbe/profilepicsFarm/mindblown.jpg";

                        //deviceuid = jObj.getString("deviceuid"),
                        // created_at = jObj.getString("created_at"),
                        //updated_at = jObj.getString("updated_at");
                        //convert the image string int bytes like this
                        ImageLoader imgLoader = new ImageLoader(getActivity());

//                        byte[] decodedString = Base64.decode(jObj.getString("profilepic"), Base64.DEFAULT);
//                        Bitmap imgBitMap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                        //mid.setText(id);
                        vusername.setText(username);
                        vfirstname.setText(firstname);
                        vlastname.setText(lastname);
                        vaddress.setText(address);
                        vcity.setText(city);
                        vstate.setText(state);
                        vcountry.setText(country);
                        vemail.setText(email);
                        vphone.setText(phone);
                        vzipcode.setText(zipcode);
                        imgLoader.DisplayImage(profilepic, loader, vprofilepic);
                        //vprofilepic.setImageBitmap(imgBitMap);
                        //deviceuid.setText(deviceuid);
                        //vcreated_at.setText(created_at);
                        //vupdated_at.setText(updated_at);
                        Toast.makeText(getActivity(), "see the value " + profilepic, Toast.LENGTH_LONG).show();
                        String errorMsg = jObj.getString("status");
                        Toast.makeText(getActivity(), "Profile " + firstname + errorMsg, Toast.LENGTH_LONG).show();
                        //finish();
                    } else if(statuscode == 0) {
                        String status = jObj.getString("status");
                        Toast.makeText(getActivity(), "Unable to login at this time " + status, Toast.LENGTH_LONG).show();
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                    Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Profile Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<>();
                //params.put("email", email);
                params.put("email", email);
                //params.put("request", request);
                return params;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(strReq);

        // Adding request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq,cancel_req_tag);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {

                    Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
                    String path =  getPath(getActivity(),contentURI);
                    Log.i(TAG, "Image Path : " + path);
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    Bitmap conv_bm = getRoundedBitmap(bitmap, 0);
                    eprofilepic.setImageBitmap(conv_bm);
                    Toast.makeText(getActivity(), "Image converted successfuly!", Toast.LENGTH_LONG);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getActivity(), "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
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
            MediaScannerConnection.scanFile(getActivity(),
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

    private void updateUserProfile(final String username, final String firstname, final String lastname, final String address, final String city, final String state, final String country, final String zipcode, final String phone, final String email) {
        // Tag used to cancel the request
        String cancel_req_tag = "Profile";
        progressDialog.setMessage("Updating Profile...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, updateprofileURL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("convertstr", "["+response+"]");
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    int result = jObj.getInt("statuscode");
                    Toast.makeText(getActivity(), "this is profile status code = " + result, Toast.LENGTH_LONG).show();

                    if (result == 1) {
                        String message = jObj.getString("status");
                        //String user = jObj.getString("email");
                        Toast.makeText(getActivity(), "Hi " + message, Toast.LENGTH_LONG).show();
//                        Intent mainIntent = new Intent(getActivity(), DesignersDashboardActivity.class);
//                        startActivity(mainIntent);
//                        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                        getActivity().finish();
                        if(viewprofile.getVisibility() == View.GONE){
                            editprofile.setVisibility(View.GONE);
                            viewprofile.setVisibility(View.VISIBLE);
                        }else {
                            viewprofile.setVisibility(View.GONE);
                            editprofile.setVisibility(View.VISIBLE);
                        }
                    } else if (result == 2) {
                        String message = jObj.getString("status");
                        //String user = jObj.getString("email");
                        Toast.makeText(getActivity(), "Hi " + message, Toast.LENGTH_LONG).show();
                    } else {
                        String errorMsg = jObj.getString("status");
                        //Toast.makeText(getApplicationContext(), name+ " "+ email, Toast.LENGTH_LONG).show();
                        Toast.makeText(getActivity(), "Sorry " + errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                    Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Profile Error: " + error.getMessage());
                Toast.makeText(getActivity(), "Profile update failed" + error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("firstname", firstname);
                params.put("lastname", lastname);
                params.put("useraddress", address);
                params.put("city", city);
                params.put("userstate", state);
                params.put("usercountry", country);
                params.put("phone", phone);
                params.put("zipcode", zipcode);
                params.put("email", email);
                return params;
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(strReq);

        // Adding request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq,cancel_req_tag);
    }
}
