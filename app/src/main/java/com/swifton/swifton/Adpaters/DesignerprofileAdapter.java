package com.swifton.swifton.Adpaters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swifton.swifton.Filters.DesignerProfileFilter;
import com.swifton.swifton.Helpers.ItemClickListener;
import com.swifton.swifton.Holders.DesignersProfileHolder;
import com.swifton.swifton.Models.DesignersProfile;
import com.swifton.swifton.R;
import com.swifton.swifton.ToolBars.ToolbarAndFab;

import java.util.List;

public class DesignerprofileAdapter extends RecyclerView.Adapter<DesignersProfileHolder> {

    Context mContext;
    public List<DesignersProfile> Designers, filterList;
    public DesignerProfileFilter filter;

    public DesignerprofileAdapter(List<DesignersProfile> designers, Context context){
        this.mContext = context;
        this.Designers= designers;
        this.filterList = designers;
    }

    @Override
    public DesignersProfileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.designers_content_layout, null);

        DesignersProfileHolder holder = new DesignersProfileHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DesignersProfileHolder holder, int position) {
        DesignersProfile designersItem = Designers.get(position);
        holder.id.setText(designersItem.getId());
        holder.username.setText(designersItem.getUsername());
        holder.designerid.setText(designersItem.getDesignerid());
        holder.firstname.setText(designersItem.getFirstname());
        holder.lastname.setText(designersItem.getLastname());
        holder.email.setText(designersItem.getEmail());
        holder.dpassword.setText(designersItem.getDpassword());
        holder.phoneno.setText(designersItem.getPhoneno());
        holder.daddress.setText(designersItem.getDaddress());
        holder.deviceids.setText(designersItem.getDeviceids());
        holder.dposition.setText(designersItem.getDposition());
        holder.updated_at.setText(designersItem.getUpdated_at());
        holder.created_at.setText(designersItem.getCreated_at());


        // Get image with bellow options

//        Glide.with(mContext)
//                .load(designersItem.getImage())
//                .Into(holder.image);  OR

        //DisplayMetrics displayMetrics = new DisplayMetrics();
        //((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //Picasso.with(mContext).load(designersItem.getLogo()).placeholder(R.drawable.ic_image_black_24dp).centerCrop().resize(displayMetrics.widthPixels, displayMetrics.heightPixels / 3).into(holder.imgLogos);

        //IMPLEMENT CLICKLISTENER

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int desc) {
                Snackbar.make(v, Designers.get(desc).getUsername(), Snackbar.LENGTH_SHORT).show();
                confirmChoice(v, desc);
            }
        });
    }


    public void confirmChoice(View v, int desc) {
        final CharSequence[] options = { "Continue", "Not Sure!" };
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(mContext);
        }
        builder.setTitle("You selected " + Designers.get(desc).getUsername() );
        builder.setItems(options,new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                if(options[which].equals("Continue"))
                {
                    Intent roomIntent = new Intent(mContext, ToolbarAndFab.class);
                    mContext.startActivity(roomIntent);
                }
                else if(options[which].equals("Not Sure!"))
                {
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }


    @Override
    public int getItemCount() {
        return Designers.size();
    }

    //RETURN FILTER BJECT
    //@Override
//    public Filter getFilter() {
//
//        if(filter == null){
//            filter = new DesignerProfileFilter(filterList, this);
//        }
//        return filter;
//    }


}
