package com.swifton.swifton.Fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.swifton.swifton.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeasurementFragment extends Fragment {
    Spinner spinUnits,spinLength,spinShoulder,spinChest,spinBust,spinSleeve,spinNeck,spinWrist,spinWaist,spinThigh,spinHips,spinBoot;

    public MeasurementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_measurement, container, false);

        spinUnits = view.findViewById(R.id.measurementUnit);
        spinLength = view.findViewById(R.id.spinLength);
        spinChest = view.findViewById(R.id.spinChest);
        spinShoulder = view.findViewById(R.id.spinShoulder);
        spinBust = view.findViewById(R.id.spinBust);
        spinSleeve = view.findViewById(R.id.spinSleeve);
        spinNeck = view.findViewById(R.id.spinNeck);
        spinWrist = view.findViewById(R.id.spinWrist);
        spinWaist = view.findViewById(R.id.spinWaist);
        spinThigh = view.findViewById(R.id.spinThigh);
        spinHips = view.findViewById(R.id.spinHips);
        spinBoot = view.findViewById(R.id.spinBoot);

        addItemsOnUnitsSpinner();
        generateMeasurementValues();
        return view;
    }


    public void addItemsOnUnitsSpinner() {

        List<String> unitList = new ArrayList<String>();
        unitList.add("Inches inch");
        unitList.add("Metre m");

        ArrayAdapter<String> UnitsAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, unitList);
        UnitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinUnits.setAdapter(UnitsAdapter);


    }
    
    
    @SuppressLint("DefaultLocale")
    private void generateMeasurementValues(){
        ArrayList<Double > valuesList = new ArrayList<Double>();
        double x, values = 0.0;

        for(x=1; x<=60.1; x +=0.1) {

            values = x;
            valuesList.add(Double.valueOf(String.format("%.2f", values)));
        }
        ArrayAdapter<Double> valuesAdapter = new ArrayAdapter<Double>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_item, valuesList);
        valuesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinLength.setAdapter(valuesAdapter);
        spinShoulder.setAdapter(valuesAdapter);
        spinChest.setAdapter(valuesAdapter);
        spinBust.setAdapter(valuesAdapter);
        spinSleeve.setAdapter(valuesAdapter);
        spinNeck.setAdapter(valuesAdapter);
        spinWrist.setAdapter(valuesAdapter);
        spinWaist.setAdapter(valuesAdapter);
        spinThigh.setAdapter(valuesAdapter);
        spinHips.setAdapter(valuesAdapter);
        spinBoot.setAdapter(valuesAdapter);
    }

}
