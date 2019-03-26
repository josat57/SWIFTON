package com.swifton.swifton.Fragments;


import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.swifton.swifton.Adpaters.LatestDesignersAdapter;
import com.swifton.swifton.Helpers.Space;
import com.swifton.swifton.Models.LatestDesigners;
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
public class LatestDesignersFragment extends Fragment implements RecyclerView.OnScrollChangeListener {

    LatestDesignersAdapter latestDesignersAdapter;
    ArrayList<LatestDesigners> latestdesigners_list;

    private int reqcount = 1;

    RequestQueue requestQueue;

    RecyclerView recyclerViewld;
    SearchView latestdesignersSearch;

    ProgressDialog progressDialog;

    //Local testing server url
    String latestDesignersURL = "http:192.168.43.53/swiftonbe/app/get_designers.php";

    //Live testing server url
    //String latestDesignersURL = "https://swiftontest.000webhostapp.com/swiftonbe/app/get_designers.php";

    public static LatestDesignersFragment newInstance() {
        LatestDesignersFragment fragment = new LatestDesignersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    public LatestDesignersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_latest_designers, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        latestdesignersSearch = view.findViewById(R.id.search_Designers);
        recyclerViewld = view.findViewById(R.id.recyclerViewDesigners);
        recyclerViewld.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewld.addItemDecoration(new Space(20, 1));

        latestdesigners_list = new ArrayList<>();
        latestdesignersSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadLatestDesigners(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS USER TYPES

                latestDesignersAdapter.getFilter().filter(query);
                loadLatestDesigners(query);
                return false;
            }
        });
        loadLatestDesigners(null);
        recyclerViewld.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    private void loadLatestDesigners(final String search) {
        String cancel_req_tag = "Latest Designers";
        latestdesigners_list =  new ArrayList<LatestDesigners>();
        progressDialog.setMessage("Fetching Latest Designers...");
        //showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, latestDesignersURL,
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


                                LatestDesigners latestdesigners =  new LatestDesigners(id, approvalstatus, level, zipcode,
                                        companyname, companyregno, companyaddress, country, companystate,
                                        companycity, emailaddress, phone, website, companycode,
                                        verified, created_at, updated_at, logo);

                                latestdesigners_list.add(latestdesigners);
                            }
                            latestDesignersAdapter = new LatestDesignersAdapter(latestdesigners_list, getActivity());
                            recyclerViewld.setAdapter(latestDesignersAdapter);
                            latestDesignersAdapter.notifyDataSetChanged();

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
