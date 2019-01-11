package com.swifton.swifton.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.swifton.swifton.Adpaters.TopDesignersAdapter;
import com.swifton.swifton.Helpers.Space;
import com.swifton.swifton.Models.DesignersProfile;
import com.swifton.swifton.Models.TopDesigners;
import com.swifton.swifton.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.swifton.swifton.R.id.recyclerViewTopDesigners;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopDesignersFragment extends Fragment {

    TopDesignersAdapter topDesignersAdapter;
    ArrayList<TopDesigners> topdesigners_list;

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


        topdesigners_list = new ArrayList<>();
        topDesignersAdapter = new TopDesignersAdapter(topdesigners_list, getActivity());

        final SearchView topdesignersSearch = view.findViewById(R.id.search_topDesigners);
        final RecyclerView recyclerViewtopdesigns = view.findViewById(recyclerViewTopDesigners);
        recyclerViewtopdesigns.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewtopdesigns.addItemDecoration(new Space(20, 1));
        recyclerViewtopdesigns.setAdapter(new TopDesignersAdapter(gettopdesigners(), getContext()));

        topDesignersAdapter = new TopDesignersAdapter(gettopdesigners(),getContext());

        topdesignersSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS USER TYPES

                topDesignersAdapter.getFilter().filter(query);
                return false;
            }
        });

        recyclerViewtopdesigns.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    //ADD tdsPUSES TO ARRAYLIST
    private ArrayList<TopDesigners> gettopdesigners(){
        ArrayList<TopDesigners> topdesigners = new ArrayList<>();
        TopDesigners tds = new TopDesigners();
        tds.setCompanyname("University of Lagos");
        tds.setCompanyaddress("Fedraly");
        tds.setCompanycity("University");
        tds.setCompanystate("Prof. Godfrey Palmer");
        tds.setCountry("Nigeria");
        tds.setLogo("https://cdn.pixabay.com/photo/2016/01/14/06/09/guitar-1139397_640.jpg");
        topdesigners.add(tds);

        tds = new TopDesigners();
        tds.setCompanyname("University of Port Harcourt");
        tds.setCompanyaddress("Fedral");
        tds.setCompanycity("University");
        tds.setCompanystate("Kaduna");
        tds.setCountry("Nigeria");
        tds.setLogo("https://cdn.pixabay.com/photo/2017/10/30/10/35/dance-2902034_640.jpg");
        topdesigners.add(tds);

        tds = new TopDesigners();
        tds.setCompanyname("University of Nigeria");
        tds.setCompanyaddress("Fedral");
        tds.setCompanycity("University");
        tds.setCompanystate("Prof. Slidetown Richman");
        tds.setCountry("Nigeria");
        tds.setLogo( "https://cdn.pixabay.com/photo/2017/09/17/11/10/luck-2758147_640.jpg");
        tds.setWebsite("www.universitywebsite.gov.edu");
        topdesigners.add(tds);

        tds = new TopDesigners();
        tds.setCompanyname("University of Ibadan");
        tds.setCompanyaddress("Fedral");
        tds.setCompanycity("University");
        tds.setCompanystate("Prof. Slidetown Richman");
        tds.setCountry("Nigeria");
        tds.setLogo("https://cdn.pixabay.com/photo/2016/12/17/16/59/guitar-1913836_640.jpg");
        tds.setWebsite("www.universitywebsite.gov.edu");
        topdesigners.add(tds);

//        tds = new tdspuses();
//        tds.setSchoolName("University of Ilorin");
//        tds.setDesc("Fedral");
//        tds.setSchlType("University");
//        tds.setVCName("Prof. Slidetown Richman");
//        tds.setVCPhoto(R.drawable.godfrey_palmer);
//        tds.setSchoolLogo(R.drawable.uc_app_logo);
//        tds.setWebsite("www.universitywebsite.gov.edu");
//        tdspuses.add(tds);
//
//        tds = new tdspuses();
//        tds.setSchoolName("University of Maiduguri");
//        tds.setDesc("Fedral");
//        tds.setSchlType("University");
//        tds.setVCName("Prof. Slidetown Richman");
//        tds.setVCPhoto(R.drawable.godfrey_palmer);
//        tds.setSchoolLogo(R.drawable.uc_app_logo);
//        tds.setWebsite("www.universitywebsite.gov.edu");
//        tdspuses.add(tds);
//
//        tds = new tdspuses();
//        tds.setSchoolName("University of Science and Technology");
//        tds.setDesc("State");
//        tds.setSchlType("University");
//        tds.setVCName("Prof. Slidetown Richman");
//        tds.setVCPhoto(R.drawable.godfrey_palmer);
//        tds.setSchoolLogo(R.drawable.uc_app_logo);
//        tds.setWebsite("www.universitywebsite.gov.edu");
//        tdspuses.add(tds);

        tds = new TopDesigners();
        tds.setCompanyname("");
        tds.setCompanyaddress("");
        tds.setCompanycity("");
        tds.setCompanystate("");
        tds.setCountry("");
        topdesigners.add(tds);

        return topdesigners;
    }

    //Load the Top DesignersProfileHolder data
    private void loadTopDesigners(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, TopDesignersURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for(int i =0; i<= jsonArray.length(); i++){
                                JSONObject designerObject = jsonArray.getJSONObject(i);
                                //get Jsonobjects

                                int id = designerObject.getJSONObject("data").getInt("id");
                                String username = designerObject.getJSONObject("data").getString("username");
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

                                DesignersProfile designersProfile =  new DesignersProfile(id, username, designerid, firstname, lastname, email, password, address, phoneno, dposition, deviceids, created_at, updated_at);
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