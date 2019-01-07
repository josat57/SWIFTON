package com.swifton.swifton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.swifton.swifton.Helpers.AppSingleton;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;
import java.util.Map;

public class DesignersProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST =234;
    private Uri filepath;
    EditText companyname, companyregno, companyaddress, country, companystate, companycity, emailaddress, companyphone, website, companyzipcode;
    Button cmdDone;
    ImageView companyLogo;

    ProgressDialog progressDialog;

    String URL= "http:192.168.43.53/swiftonbe/app/create_company.php";
    private static final String TAG = "RegisterActivity";

    private static final String TAG_SUCCESS = "success";

    JSONParser jsonParser=new JSONParser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designers_profile);

        // Progress dialog
        progressDialog = new ProgressDialog(DesignersProfileActivity.this);
        progressDialog.setCancelable(false);

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
                final String email = emailaddress.getText().toString().trim();
                final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                final String compphone = companyphone.getText().toString().trim();
                final String comopzip = companyzipcode.getText().toString().trim();
                final String compaddress = companyaddress.getText().toString().trim();
                final String compcity = companycity.getText().toString().trim();
                final String compstate = companystate.getText().toString().trim();
                final String compcountry = country.getText().toString().trim();
                final String compwebsite = website.getText().toString().trim();
            }
        });

        companyLogo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null){
            Uri binarydata = data.getData();
            companyLogo.setImageURI(binarydata);
        }
    }

    private void signupUsers(final String email, final String password, final String userdevice) {
        //Tag used to cancel the request
        String cancel_req_tag = "signup";
        progressDialog.setMessage("Registering you...");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                hideDialog();
                Log.i("tagconvertstr", "["+response+"]");
                try {
                    JSONObject jObj = new JSONObject(response);
                    Integer result = jObj.getInt("statuscode");

                    if(result == 1){
                        String message = jObj.getString("status");
                        //String user = jObj.getString("email");
                        Toast.makeText(getApplicationContext(), "Hi " +message, Toast.LENGTH_LONG).show();
                        Intent mainIntent = new Intent(DesignersProfileActivity.this, DesignersDashboardActivity.class);
                        startActivity(mainIntent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }else if(result == 2){
                        String message = jObj.getString("status");
                        //String user = jObj.getString("email");
                        Toast.makeText(getApplicationContext(), "Hi " +message, Toast.LENGTH_LONG).show();
                    }else{
                        String errorMsg = jObj.getString("status");
                        //Toast.makeText(getApplicationContext(), name+ " "+ email, Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "Sorry " + errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (final JSONException e){
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(DesignersProfileActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),"Sorry registration failed! "+error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                //params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("deviceuid", userdevice);
                //params.put("phone", phone);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest, cancel_req_tag);
    }

    private void showDialog(){
        if (!progressDialog.isShowing())
            progressDialog.show();
    }
    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
