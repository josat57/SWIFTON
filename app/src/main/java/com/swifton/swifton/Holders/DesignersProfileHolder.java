package com.swifton.swifton.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.swifton.swifton.Helpers.ItemClickListener;
import com.swifton.swifton.R;

public class DesignersProfileHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

     private ItemClickListener itemClickListener;

     public TextView id,username, designerid, firstname, lastname, email, dpassword, daddress, phoneno, dposition, deviceids, created_at, updated_at;



    public DesignersProfileHolder(View itemView){
        super(itemView);

        this.id = itemView.findViewById(R.id.dsnid);
        this.username = itemView.findViewById(R.id.dsnusername);
        this.designerid = itemView.findViewById(R.id.designerID);
        this.firstname = itemView.findViewById(R.id.dsnfirstname);
        this.lastname = itemView.findViewById(R.id.dsnlastname);
        this.email = itemView.findViewById(R.id.dsnemail);
        this.dpassword = itemView.findViewById(R.id.dsnpassword);
        this.daddress = itemView.findViewById(R.id.dsnaddress);
        this.phoneno = itemView.findViewById(R.id.dsnphone);
        this.dposition = itemView.findViewById(R.id.dsnposition);
        this.deviceids = itemView.findViewById(R.id.dsndeviceid);
        this.created_at = itemView.findViewById(R.id.dsncreated);
        this.updated_at = itemView.findViewById(R.id.dsnupdated);


        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClick(view, getLayoutPosition());

    }

    public void  setItemClickListener(ItemClickListener icl){
        this.itemClickListener = icl;
    }
}
