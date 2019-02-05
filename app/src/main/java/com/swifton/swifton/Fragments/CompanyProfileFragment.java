package com.swifton.swifton.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.swifton.swifton.DesignersProfileActivity;
import com.swifton.swifton.Helpers.AppSingleton;
import com.swifton.swifton.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.android.volley.VolleyLog.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyProfileFragment extends Fragment {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";
    public static final String Designerid = "designeridkey";

    SharedPreferences sharedpreferences;

    FloatingActionButton createProfileFab;
    TextView mid, mcompanyname, mcompanyregno, mdesignerid, mrating,
            mcompanyaddress, mcountry, mcompanystate, mcompanycity, memailaddress, mphone, mwebsite, mzipcode,
            mcompanycode, mapprovalstatus, mlevel, mcreated_at, mupdated_at, Exit, content;

    ImageView  mverified, mlogo;
    RelativeLayout relativeLayout;



    //String CompanyURL = "http:192.168.43.53/swiftonbe/app/get_company_profile.php";
    String CompanyURL = "http:10.11.32.56/swiftonbe/app/get_company_profile.php";

    ProgressDialog progressDialog;

    public CompanyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_company_profile, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        createProfileFab = view.findViewById(R.id.compProfileFAB);

        mid = view.findViewById(R.id.comid);
        mdesignerid = view.findViewById(R.id.comdesignerid);
        mcompanyname = view.findViewById(R.id.comname);
        mcompanyregno = view.findViewById(R.id.comregno);
        mcompanyaddress = view.findViewById(R.id.comaddress);
        mcompanycity = view.findViewById(R.id.comcity);
        mcompanystate = view.findViewById(R.id.comstate);
        mcountry = view.findViewById(R.id.comcountry);
        mzipcode = view.findViewById(R.id.comzipcode);
        mcompanystate = view.findViewById(R.id.comstate);
        memailaddress = view.findViewById(R.id.comemail);
        mphone = view.findViewById(R.id.comphone);
        mwebsite = view.findViewById(R.id.comwebsite);
        mcompanycode = view.findViewById(R.id.comcode);
        mapprovalstatus = view.findViewById(R.id.comstatus);
        mverified = view.findViewById(R.id.verified);
        mcreated_at = view.findViewById(R.id.comcreated);
        mlevel = view.findViewById(R.id.comlevel);
        mupdated_at = view.findViewById(R.id.comupdated);
        mrating = view.findViewById(R.id.comrating);
        mlogo = view.findViewById(R.id.cprofileLogo);

        content = view.findViewById(R.id.textexists);
        Exit = view.findViewById(R.id.textExit);
        relativeLayout = view.findViewById(R.id.profileexists);
        //mdesignerid.setText(70423694c3);

        final String designID = sharedpreferences.getString("designeridkey", "").toString().trim();
        Log.d(TAG, designID);

        if(!designID.isEmpty()){
            Toast.makeText(getActivity(), "designerID " + designID, Toast.LENGTH_SHORT).show();
            loadCompanyProfile(designID);
        }

        createProfileFab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent editprofileIntent = new Intent(getActivity(), DesignersProfileActivity.class);
                startActivity(editprofileIntent);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
            }
        });

        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout.setVisibility(View.GONE);
                //relativeLayout.setScaleX((float) -10.5);
                //relativeLayout.setScaleY((float) -10.5);

            }
        });

        return view;
    }

    //Load the Top DesignersProfileHolder data
    private void loadCompanyProfile(final String designerid) {
        // Tag used to cancel the request
        String cancel_req_tag = "Profile";
        progressDialog.setMessage("Loading Profile...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, CompanyURL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.i("tagconvertstr", "["+response+"]");
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    int statuscode = jObj.getInt("statuscode");
                    Toast.makeText(getActivity(), "this is company status code = " + statuscode, Toast.LENGTH_LONG).show();

                    if (statuscode == 1) {
                        setLayoutInvisible();
                        int id = jObj.getInt("id");
                        String designerid = jObj.getString("Designerid"),
                                companyname = jObj.getString("CompanyName"),
                                companyregno = jObj.getString("CompanyReg"),
                                companyaddress = jObj.getString("CompanyAddress"),
                                companycity = jObj.getString("CompanyCity"),
                                companystate = jObj.getString("CompanyState"),
                                companycountry = jObj.getString("Country"),
                                zipcode = jObj.getString("ZipCode"),
                                emailaddress = jObj.getString("EmailAddress"),
                                phone = jObj.getString("Phone"),
                                website = jObj.getString("Website"),
                                logo = jObj.getString("Designerid"),
                                companycode = jObj.getString("CompanyCode"),
                                approvalstatus = jObj.getString("ApprovalStatus"),
                                verified = jObj.getString("Verified"),
                                rating = jObj.getString("Rating"),
                                created_at = jObj.getString("Created_at"),
                                updated_at = jObj.getString("Updated_at");

                        //mid.setText(id);
                        mdesignerid.setText(designerid);
                        mcompanyname.setText(companyname);
                        mcompanyregno.setText(companyregno);
                        mcompanyaddress.setText(companyaddress);
                        mcompanycity.setText(companycity);
                        mcompanystate.setText(companystate);
                        mcountry.setText(companycountry);
                        mzipcode.setText(zipcode);
                        memailaddress.setText(emailaddress);
                        mphone.setText(phone);
                        mwebsite.setText(website);
                        mcompanycode.setText(companycode);
                        mapprovalstatus.setText(approvalstatus);
                        //mverified.setText(verified);
                        mrating.setText(rating);
                        mcreated_at.setText(created_at);
                        mupdated_at.setText(updated_at);

                        String errorMsg = jObj.getString("status");
                        Toast.makeText(getActivity(), "Profile " + companyname + errorMsg, Toast.LENGTH_LONG).show();
                        //finish();
                    } else if(statuscode == 0) {
                        String status = jObj.getString("status");
                        setLayoutVisible();
                        content.setText(status);

                        Toast.makeText(getActivity(), "Unable to login at this time " + status, Toast.LENGTH_LONG).show();
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                    //Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                        //public void run() {
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                        //}
                    //});
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
                params.put("designerid", designerid);
                //params.put("request", request);
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

    public void setLayoutInvisible() {
        if (relativeLayout.getVisibility() == View.VISIBLE) {
            relativeLayout.setVisibility(View.GONE);
        }
    }
    public void setLayoutVisible() {
        if (relativeLayout.getVisibility() == View.GONE) {
            relativeLayout.setVisibility(View.VISIBLE);
        }
    }

}
