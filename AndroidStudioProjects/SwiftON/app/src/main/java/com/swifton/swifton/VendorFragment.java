package com.swifton.swifton;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.simple.parser.JSONParser;


/**
 * A simple {@link Fragment} subclass.
 */
public class VendorFragment extends Fragment {
    ProgressDialog progressDialog;
    Button cmd_Token;
    EditText txtToken;

    protected String enteredUsername;

    String URL= "http:192.168.43.53/swiftonbe/app/login.php";
    private static final String TAG = "RegisterActivity";

    private static final String TAG_SUCCESS = "success";

    JSONParser jsonParser=new JSONParser();

    public VendorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vendor, container, false);

        cmd_Token = view.findViewById(R.id.cmdToken);
        txtToken = view.findViewById(R.id.txtToken);

        cmd_Token.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent dashboardIntent = new Intent(getActivity(), VendorDashboardActivity.class);
                startActivity(dashboardIntent);
                getActivity().overridePendingTransition(R.anim.righttranslate, R.anim.lefttrslate);
            }
        });

        return view;
    }

}
