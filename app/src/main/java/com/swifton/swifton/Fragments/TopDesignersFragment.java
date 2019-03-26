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
import com.swifton.swifton.Adpaters.TopDesignersAdapter;
import com.swifton.swifton.Helpers.Space;
import com.swifton.swifton.Models.TopDesigners;
import com.swifton.swifton.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
@TargetApi(Build.VERSION_CODES.M)
public class TopDesignersFragment extends Fragment implements RecyclerView.OnScrollChangeListener {

    TopDesignersAdapter topDesignersAdapter;
    ArrayList<TopDesigners> topdesigners_list;

    private int reqcount = 1;

    RequestQueue requestQueue;

    RecyclerView recyclerViewtd;
    SearchView topdesignersSearch;

    ProgressDialog progressDialog;

    //Local testing server url
    String TopDesignersURL = "http:192.168.43.53/swiftonbe/app/get_designers.php";

    //Live testing server url
    //String TopDesignersURL = "https://swiftontest.000webhostapp.com/swiftonbe/app/get_designers.php";

    public static TopDesignersFragment newInstance() {
        TopDesignersFragment fragment = new TopDesignersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public TopDesignersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_designers, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        topdesignersSearch = view.findViewById(R.id.search_Designers);
        recyclerViewtd = view.findViewById(R.id.recyclerViewDesigners);
        recyclerViewtd.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewtd.addItemDecoration(new Space(20, 1));

        topdesigners_list = new ArrayList<>();
        topdesignersSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadTopDesigners("1", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS USER TYPES

                topDesignersAdapter.getFilter().filter(query);
                loadTopDesigners("1", query);
                return false;
            }
        });
        loadTopDesigners("1", null);
        recyclerViewtd.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    //Load the Top DesignersProfileHolder data
    private void loadTopDesigners(final String level, final String search){

        String cancel_req_tag = "Top Designers";
        topdesigners_list =  new ArrayList<TopDesigners>();
        progressDialog.setMessage("Fetching Top Designers...");
        //showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, TopDesignersURL,
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


                                TopDesigners topdesigners =  new TopDesigners(id, approvalstatus, level, zipcode,
                                        companyname, companyregno, companyaddress, country, companystate,
                                        companycity, emailaddress, phone, website, companycode,
                                        verified, created_at, updated_at, logo);

                                topdesigners_list.add(topdesigners);
                            }
                            topDesignersAdapter = new TopDesignersAdapter(topdesigners_list, getActivity());
                            recyclerViewtd.setAdapter(topDesignersAdapter);
                            topDesignersAdapter.notifyDataSetChanged();

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
                }){
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("search", search);
                params.put("level", "1");
                return params;
            }
        };

        Volley.newRequestQueue(Objects.requireNonNull(getActivity())).add(stringRequest);
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