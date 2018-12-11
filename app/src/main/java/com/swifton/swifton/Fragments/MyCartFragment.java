package com.swifton.swifton.Fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.swifton.swifton.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCartFragment extends Fragment {
    ListView mcartList;
    CheckBox mcartCheck;

    int[] images ={
            R.drawable.album1,
            R.drawable.album2,
            R.drawable.album3,
            R.drawable.album4,
            R.drawable.album5,
            R.drawable.album6,
            R.drawable.album7,
            R.drawable.album8,
            R.drawable.album9,
            R.drawable.album10,
            R.drawable.album11
    };

    String[] title = {
            "SwiftON Apple Pinky",
            "SwiftON Orange Pinky",
            "SwiftON Avocado Pinky",
            "SwiftON Blackberry Pinky",
            "SwiftON Carrot Pinky",
            "SwiftON Grape Pinky",
            "SwiftON Banana Pinky",
            "SwiftON Cherry Pinky",
            "SwiftON Pineapple Pinky",
            "SwiftON Mango Pinky",
            "SwiftON Pumpkin Pinky",
    };

    String[] desc = {
            "Apple Fruit",
            "Orange Fruit",
            "Avocado Fruit",
            "Blackberry Fruit",
            "Carrot Fruit",
            "Grape Fruit",
            "Banana Fruit",
            "Cherry Fruit",
            "Pineapple Fruit",
            "Mango Fruit",
            "Pumpkin Fruit"
    };

    String[] prizes = {
            "15000",
            "25000",
            "10000",
            "5000",
            "30000",
            "12000",
            "7000",
            "21000",
            "14000",
            "6500",
            "19999"
    };

    public MyCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        mcartList = view.findViewById(R.id.cartList);
        mcartCheck = view.findViewById(R.id.cartcheck);

        final FloatingActionButton mcartfab =  view.findViewById(R.id.cartfab);


        customCartAdapter cartAdapter = new customCartAdapter();

        mcartList.setAdapter(cartAdapter);

        mcartfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Thank you for petronizing us!", Snackbar.LENGTH_LONG)
                        .setAction("Buy", null).show();
            }
        });


        return view;
    }


    class customCartAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View layoutView = getLayoutInflater().inflate(R.layout.cartlist_layout, null);

            ImageView imageView = layoutView.findViewById(R.id.cartddi);
            TextView  txtTitles = layoutView.findViewById(R.id.carttitle);
            TextView txtDesc = layoutView.findViewById(R.id.cartdesc);
            TextView txtPrizes = layoutView.findViewById(R.id.cartprize);

            imageView.setImageResource(images[position]);
            txtTitles.setText(title[position]);
            txtDesc.setText(desc[position]);
            txtPrizes.setText(prizes[position]);
            return layoutView;
        }
    }


}
