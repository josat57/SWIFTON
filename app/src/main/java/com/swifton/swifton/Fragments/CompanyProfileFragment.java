package com.swifton.swifton.Fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.swifton.swifton.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyProfileFragment extends Fragment {
    FloatingActionButton editFab;
    TextView id, companyname, companyregno,
            companyaddress, country, companystate, companycity, emailaddress, phone, website, zipcode,
            logo, companycode, approvalstatus, verified,level, created_at;

    String CompanyURL = "http:192.168.43.53/swiftonbe/app/get_designers.php";

    public CompanyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_company_profile, container, false);
    }

    //Load the Top DesignersProfileHolder data
    private void loadTopDesigners(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, CompanyURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for(int i =0; i<= jsonArray.length(); i++){
                                JSONObject designerObject = jsonArray.getJSONObject(i);
                                //get Jsonobjects

                                int id = designerObject.getJSONObject("data").getInt("id");
                                String designerid = designerObject.getJSONObject("data").getString("designerid");

                                String firstname = designerObject.getJSONObject("data").getString("firstname");
                                String lastname = designerObject.getJSONObject("data").getString("lastname");
                                String email = designerObject.getJSONObject("data").getString("email");
                                String password = designerObject.getJSONObject("data").getString("dpassword");
                                String address = designerObject.getJSONObject("data").getString("daddress");
                                String phoneno  = designerObject.getJSONObject("data").getString("phoneno");
                                String dposition = designerObject.getJSONObject("data").getString("dposition");
                                String deviceids = designerObject.getJSONObject("data").getString("deviceids");
                                String created_at = designerObject.getJSONObject("data").getString("created_at");
                                String updated_at = designerObject.getJSONObject("data").getString("updated_at");

                                //DesignersProfile designersProfile =  new DesignersProfile(id, designerid, firstname, lastname, email, password, address, phoneno, dposition, deviceids, created_at, updated_at);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

}
