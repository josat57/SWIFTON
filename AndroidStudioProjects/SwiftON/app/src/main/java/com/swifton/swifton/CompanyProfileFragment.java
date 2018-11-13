package com.swifton.swifton;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyProfileFragment extends Fragment {
    EditText companyname, companyregno, companyaddress,comcountry, comstate, comcity,comemail,
             comphone, comwebsite, comzipcode, comlogo, vendorid , vendortoken, approvalstatus;

    Button cmdSubmit;
    ProgressDialog progressDialog;
    TextView SignupLabel,ForgotLabel;
    Button cmd_LoginBtn;
    EditText txtloginEmail,txtloginPassword;

    protected String enteredUsername;

    String URL= "http:192.168.43.53/swiftonbe/app/login.php";
    private static final String TAG = "RegisterActivity";

    private static final String TAG_SUCCESS = "success";

    JSONParser jsonParser=new JSONParser();

    public CompanyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_company_profile, container, false);

        cmdSubmit = view.findViewById(R.id.cmdsubmit);

        cmdSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                VendorFragment vendorFragment = new VendorFragment();
                FragmentManager vendormanager = getActivity().getSupportFragmentManager();
                vendormanager.beginTransaction().replace(R.id.linearLayout_for_fragment, vendorFragment, vendorFragment.getTag()).commit();
                getActivity().overridePendingTransition(R.anim.lefttrslate, R.anim.lefttrslate);
            }
        });
        return view;
    }

    private void loginUser( final String email, final String password) {
        // Tag used to cancel the request
        String cancel_req_tag = "login";
        progressDialog.setMessage("Logging you in...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.i("tagconvertstr", "["+response+"]");
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    Integer error = jObj.getInt("error");

                    if (error == 1) {
                        //String user = jObj.getJSONObject("response").getString("email");
                        String user = jObj.getString("email");
                        // Launch User activity
                        //Intent homeIntent = new Intent(getActivity(), dashboardActivity.class);
                        //homeIntent.putExtra("USERNAME", enteredUsername);
                        //homeIntent.putExtra("MESSAGE", "Login Successful!");
                        //startActivity(homeIntent);
                        getActivity().overridePendingTransition(R.anim.righttranslate, R.anim.lefttrslate);
                        //homeIntent.putExtra("email", user);
                        getActivity().finish();
                    } else {

                        String errorMsg = jObj.getString("error");
                        Toast.makeText(getActivity(), "Unable to login at this time " + errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            //Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq,cancel_req_tag);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }
    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

}
