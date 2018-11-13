package com.swifton.swifton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    TextView loginLabel, deviceUDID;
    Button cmd_signupbtn;
    EditText txtFullName,txtSignupEmail,txtSignupPassword;
    ProgressDialog progressDialog;
    String UDIDS;

    protected String enteredUsername;

    String URL= "http:192.168.43.53/swiftonbe/app/signup.php";
    private static final String TAG = "RegisterActivity";

    private static final String TAG_SUCCESS = "success";

    JSONParser jsonParser=new JSONParser();

    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        loginLabel=findViewById(R.id.loginlabel);
        cmd_signupbtn = findViewById(R.id.signupbtn);
        //txtFullName = findViewById(R.id.txtsignupname);
        txtSignupEmail = findViewById(R.id.txtsignupemail);
        txtSignupPassword = findViewById(R.id.txtsignuppswrd);
        deviceUDID = findViewById(R.id.udid);

        // Progress dialog
        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setCancelable(false);

        UDIDS = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        deviceUDID.setText(UDIDS);

        loginLabel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent loginIntent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                overridePendingTransition(R.anim.righttranslate, R.anim.lefttrslate);
            }
        });

        cmd_signupbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String email = txtSignupEmail.getText().toString().trim();
                final String password = txtSignupPassword.getText().toString().trim();
                final String userdevice = deviceUDID.getText().toString().trim();
                final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (email.isEmpty() || password.isEmpty() || userdevice.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_LONG).show();
                } else if (!email.matches(emailPattern)) {
                    Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                } else if (email.matches(emailPattern) && password.length() >= 6) {
                    signupUsers(email, password, userdevice);
                    Toast.makeText(getApplicationContext(), "hello checking signup error "+ userdevice, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Could not signup...", Toast.LENGTH_LONG).show();
                }
            }
        });
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
                    Integer result = jObj.getInt("result");

                    if(result == 1){
                        String message = "Your account has been created successfuly!";//jObj.getString("message");
                        //String user = jObj.getString("email");
                        Toast.makeText(getApplicationContext(), "Hi " +message, Toast.LENGTH_LONG).show();
                        Intent mainIntent = new Intent(SignupActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }else if(result == 2){
                        String message = "This user already exists!";//jObj.getString("message");
                        //String user = jObj.getString("email");
                        Toast.makeText(getApplicationContext(), "Hi " +message, Toast.LENGTH_LONG).show();
                    }else{
                        //String errorMsg = jObj.getString("error");
                        //Toast.makeText(getApplicationContext(), name+ " "+ email, Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "Sorry registration failed! ", Toast.LENGTH_LONG).show();
                    }
                } catch (final JSONException e){
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(SignupActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),"Sorry registration failed! 2"+error.getMessage(), Toast.LENGTH_LONG).show();
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
                params.put("deviceuid", UDIDS);
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
