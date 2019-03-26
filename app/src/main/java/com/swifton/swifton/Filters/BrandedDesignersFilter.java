package com.swifton.swifton.Filters;

import android.widget.Filter;

import com.swifton.swifton.Adpaters.BrandedDesignersAdapter;
import com.swifton.swifton.Models.BrandedDesigners;

import java.util.ArrayList;

public class BrandedDesignersFilter extends Filter {
    BrandedDesignersAdapter Adapter;
    ArrayList<BrandedDesigners> filterList;

    public BrandedDesignersFilter(ArrayList<BrandedDesigners> filterList, BrandedDesignersAdapter adapter){
        this.Adapter = adapter;
        this.filterList = filterList;

    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results = new FilterResults();
        if(charSequence != null && charSequence.length() > 0){
            charSequence = charSequence.toString().toUpperCase();

            ArrayList<BrandedDesigners> filterbrandeddesigners = new ArrayList<>();

            for(int i = 0; i<filterList.size(); i++){
                if(filterList.get(i).getCompanyname().toUpperCase().contains(charSequence)){
                    //BrandedDesigners BDs = new BrandedDesigners(i);
                    filterbrandeddesigners.add(filterList.get(i));
                }
            }

            results.count =  filterList.size();
            results.values = filterbrandeddesigners;
        }else {
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults results) {
        Adapter.brandedDesigners = (ArrayList<BrandedDesigners>) results.values;

        //REFRESH
        Adapter.notifyDataSetChanged();
    }
}
