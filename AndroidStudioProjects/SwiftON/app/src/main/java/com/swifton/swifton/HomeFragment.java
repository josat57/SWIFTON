package com.swifton.swifton;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.util.Locale.getDefault;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    Button findEvents, viewTickets, AdminMode, HelpFAQ, findEventHalls;
    TextView homeDate, homeTime;

    BroadcastReceiver _broadcastReceiver;
    private final SimpleDateFormat _sdfWatchTime = new SimpleDateFormat("hh:mm", Locale.getDefault());

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);


        homeDate = (TextView) view.findViewById(R.id.home_date);

        Date date = new Date();  // to get the date
        SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy", getDefault()); // getting date in this format
        String formattedDate = df.format(date.getTime());
        homeDate.setText(formattedDate);




//        AdminMode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent AdminIntent = new Intent(getActivity(), MainActivity.class);
//                startActivity(AdminIntent);
//                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//            }
//        });
//
//        findEventHalls.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent eventhallsIntent = new Intent (getActivity(), SignupActivity.class);
//                startActivity(eventhallsIntent);
//                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//            }
//        });

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        _broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctx, Intent intent) {
                if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0);
                    homeTime.setText(_sdfWatchTime.format(new Date()));
            }
        };

        getActivity().registerReceiver(_broadcastReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }

    @Override
    public void onStop() {
        super.onStop();
        if (_broadcastReceiver != null)
            getActivity().unregisterReceiver(_broadcastReceiver);
    }

}
