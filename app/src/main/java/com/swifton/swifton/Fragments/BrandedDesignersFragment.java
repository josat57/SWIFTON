package com.swifton.swifton.Fragments;


import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.swifton.swifton.Adpaters.BrandedDesignersAdapter;
import com.swifton.swifton.Helpers.Space;
import com.swifton.swifton.Models.BrandedDesigners;
import com.swifton.swifton.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
@TargetApi(Build.VERSION_CODES.M)
public class BrandedDesignersFragment extends Fragment implements RecyclerView.OnScrollChangeListener {

    BrandedDesignersAdapter brandedDesignersAdapter;
    ArrayList<BrandedDesigners> brandeddesigners_list;

    private int reqcount = 1;

    RequestQueue requestQueue;

    RecyclerView recyclerViewbd;
    SearchView brandeddesignersSearch;

    ProgressDialog progressDialog;

    //Local testing server url
    String brandedDesignersURL = "http:192.168.43.53/swiftonbe/app/get_designers.php";

    //Live testing server url
    //String brandedDesignersURL = "https://swiftontest.000webhostapp.com/swiftonbe/app/get_designers.php";

    public static BrandedDesignersFragment newInstance() {
        BrandedDesignersFragment fragment = new BrandedDesignersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    public BrandedDesignersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_branded_designers, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        brandeddesignersSearch = view.findViewById(R.id.search_Designers);
        recyclerViewbd = view.findViewById(R.id.recyclerViewDesigners);
        recyclerViewbd.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewbd.addItemDecoration(new Space(20, 1));
        recyclerViewbd.setHasFixedSize(true);

        brandeddesigners_list = new ArrayList<>();
        brandeddesignersSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                loadBrandedDesigners("2", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS USER TYPES

                brandedDesignersAdapter.getFilter().filter(query);
                loadBrandedDesigners("2", query);
                return false;
            }
        });
        loadBrandedDesigners("2", null);
        recyclerViewbd.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    private void loadBrandedDesigners(final String level, final String search) {

        String cancel_req_tag = "Branded Designers";
        brandeddesigners_list =  new ArrayList<BrandedDesigners>();
        progressDialog.setMessage("Fetching Branded Designers...");
        //showDialog();

       final StringRequest stringRequest = new StringRequest(Request.Method.GET, brandedDesignersURL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.i("tagconvertstr", response);
                        hideDialog();
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for(int i =0; i<= jsonArray.length(); i++){
                                JSONObject designerObject = jsonArray.getJSONObject(i);
                                //get Jsonobjects

                                int id = designerObject.getInt("id");
                                int approvalstatus = designerObject.getInt("approvalstatus");
                                int level = designerObject.getInt("level");
                                String zipcode = designerObject.getString("zipcode");
                                String companyname = designerObject.getString("companyname");
                                String companyregno = designerObject.getString("companyregno");
                                String companyaddress = designerObject.getString("companyaddress");
                                String country = designerObject.getString("country");
                                String companystate = designerObject.getString("companystate");
                                String companycity = designerObject.getString("companycity");
                                String emailaddress = designerObject.getString("emailaddress");
                                String phone  = designerObject.getString("phone");
                                String website = designerObject.getString("website");
                                String companycode = designerObject.getString("companycode");
                                String verified = designerObject.getString("verified");
                                String created_at = designerObject.getString("created_at");
                                String updated_at = designerObject.getString("updated_at");
                                String logo = designerObject.getString("logo");


                                BrandedDesigners brandeddesigners =  new BrandedDesigners(id, approvalstatus, level, zipcode,
                                        companyname, companyregno, companyaddress, country, companystate,
                                        companycity, emailaddress, phone, website, companycode,
                                        verified, created_at, updated_at, logo);

                                brandeddesigners_list.add(brandeddesigners);
                            }
                            brandedDesignersAdapter = new BrandedDesignersAdapter(brandeddesigners_list, getActivity());
                            recyclerViewbd.setAdapter(brandedDesignersAdapter);
                            brandedDesignersAdapter.notifyDataSetChanged();

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
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("search", search);
                params.put("level", level);
                return params;
            }
        };

        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

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
