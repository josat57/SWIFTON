package com.swifton.swifton.Adpaters;

import android.widget.Filter;

import com.swifton.swifton.Models.AllDesignItems;

import java.util.ArrayList;
import java.util.List;

public class ItemFilter  extends Filter {
        DemoAdapter adapter;
        List<AllDesignItems> filterList;

        public ItemFilter(List<AllDesignItems> filterList, DemoAdapter adapter){
            this.adapter = adapter;
            this.filterList = filterList;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            if(charSequence != null && charSequence.length() > 0){
                charSequence = charSequence.toString().toUpperCase();

                ArrayList<AllDesignItems> filterDesigns = new ArrayList<>();

                for(int i = 0; i<filterList.size(); i++){
                    String[] Titles = {"Taylor Swift - Look What You Made Me", "Bebe Rexha - Meant to Be", "Andra & Mara - Sweet Dreams", "Sam Smith - Too Good At Goodbyes "};
                    String[] Description = {"By TaylorSwiftVEVO ", "By Bebe Rexha", "BySamSmithWorldVEVO", "SamSmithWorldVEVO "};
                    String[] ImageUrls = {"https://cdn.pixabay.com/photo/2016/01/14/06/09/guitar-1139397_640.jpg", "https://cdn.pixabay.com/photo/2017/10/30/10/35/dance-2902034_640.jpg", "https://cdn.pixabay.com/photo/2017/09/17/11/10/luck-2758147_640.jpg", "https://cdn.pixabay.com/photo/2016/12/17/16/59/guitar-1913836_640.jpg"};
                    if(filterList.get(i).title.toUpperCase().contains(charSequence)){
                        AllDesignItems AllD = new AllDesignItems(Titles[i],Description[i],ImageUrls[i]);
                        filterDesigns.add(filterList.get(i));
                    }
                }

                results.count =  filterList.size();
                results.values = filterDesigns;
            }else {
                results.count = filterList.size();
                results.values = filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            adapter.allDesignItems = (ArrayList<AllDesignItems>) results.values;

            //REFRESH
            adapter.notifyDataSetChanged();
        }
}
