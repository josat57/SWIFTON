package com.swifton.swifton;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class VendorProfileFragment extends Fragment {
    EditText email, password, firstname, lastname, address, country, state, city, phone, position;
    Button cmdContinue;

    public VendorProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.fragment_vendor_profile, container, false);

        cmdContinue = view.findViewById(R.id.cmdcontinue);

        cmdContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompanyProfileFragment companyprofileFragment = new CompanyProfileFragment();
                FragmentManager managerhm = getActivity().getSupportFragmentManager();
                managerhm.beginTransaction().replace(R.id.linearLayout_for_fragment, companyprofileFragment, companyprofileFragment.getTag()).commit();
                getActivity().overridePendingTransition(R.anim.lefttrslate, R.anim.lefttrslate);
            }
        });

        return view;
    }

}
