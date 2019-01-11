package com.swifton.swifton.Filters;

import android.widget.Filter;

import com.swifton.swifton.Adpaters.DesignerprofileAdapter;
import com.swifton.swifton.Models.DesignersProfile;

import java.util.ArrayList;
import java.util.List;

public class DesignerProfileFilter extends Filter {

    DesignerprofileAdapter designerProfileAdapter;
    List<DesignersProfile> designersList;

    public DesignerProfileFilter(DesignerprofileAdapter designerprofileAdapter, List<DesignersProfile> designersProfileList){
        this.designerProfileAdapter = designerprofileAdapter;
        this.designersList = designersProfileList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if(constraint != null && constraint.length() > 0){
            constraint = constraint.toString().toUpperCase();

            ArrayList<DesignersProfile> filterDesigners = new ArrayList<>();

            for(int i = 0; i<designersList.size(); i++){
                Integer id = designersList.get(i).getId();
                String username = designersList.get(i).getUsername();
                String designerid = designersList.get(i).getDesignerid();
                String firstname = designersList.get(i).getFirstname();
                String lastname = designersList.get(i).getLastname();
                String email = designersList.get(i).getEmail();
                String dpassword = designersList.get(i).getDpassword();
                String daddress = designersList.get(i).getDaddress();
                String phoneno = designersList.get(i).getPhoneno();
                String dposition = designersList.get(i).getDposition();
                String deviceids = designersList.get(i).getDeviceids();
                String created_at = designersList.get(i).getCreated_at();
                String updated_at = designersList.get(i).getUpdated_at();

//                String[] Titles = {"Taylor Swift - Look What You Made Me", "Bebe Rexha - Meant to Be", "Andra & Mara - Sweet Dreams", "Sam Smith - Too Good At Goodbyes "};
//                String[] Description = {"By TaylorSwiftVEVO ", "By Bebe Rexha", "BySamSmithWorldVEVO", "SamSmithWorldVEVO "};
//                String[] ImageUrls = {"https://cdn.pixabay.com/photo/2016/01/14/06/09/guitar-1139397_640.jpg", "https://cdn.pixabay.com/photo/2017/10/30/10/35/dance-2902034_640.jpg", "https://cdn.pixabay.com/photo/2017/09/17/11/10/luck-2758147_640.jpg", "https://cdn.pixabay.com/photo/2016/12/17/16/59/guitar-1913836_640.jpg"};
//
                if(designersList.get(i).getUsername().toUpperCase().contains(constraint)){
                    DesignersProfile natives = new DesignersProfile(id, username, designerid, firstname, lastname, email, dpassword, daddress, phoneno, dposition, deviceids, created_at, updated_at);
                    filterDesigners.add(designersList.get(i));
                }
            }

            results.count =  designersList.size();
            results.values = filterDesigners;
        }else {
            results.count = designersList.size();
            results.values = designersList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        designerProfileAdapter.Designers = (ArrayList<DesignersProfile>) results.values;

        //Refresh Adapter
        designerProfileAdapter.notify();
    }

}
