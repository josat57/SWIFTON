package com.swifton.swifton.Adpaters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.swifton.swifton.BuyActivity;
import com.swifton.swifton.Helpers.ItemClickListener;
import com.swifton.swifton.JustTest;
import com.swifton.swifton.Models.AllDesignItems;
import com.swifton.swifton.R;
import com.swifton.swifton.ToolBars.ToolbarAndFab;

import java.util.List;

/**
 * Created by amardeep on 11/7/2017.
 */

public class DemoAdapter extends RecyclerView.Adapter implements Filterable {
    Bundle Title, Description, Backdrop;

    List<AllDesignItems> allDesignItems, filterList;
    Context context;
    ItemFilter itemsFilter;
    int Desc;
    private ImageView loveBtn;

    public DemoAdapter(List<AllDesignItems> DesignItems, Context context) {
        this.allDesignItems = DesignItems;
        this.context = context;
        this.filterList = DesignItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_row_demo, parent, false);


       AllDesignHolder allholder = new AllDesignHolder(view);

        loveBtn = allholder.loveBtn;
        return  allholder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final AllDesignItems currentAllDesignItems = allDesignItems.get(position);
        AllDesignHolder designHolder = (AllDesignHolder) holder;
        designHolder.Title.setText(currentAllDesignItems.title);
        designHolder.Description.setText(currentAllDesignItems.Description);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Picasso.with(context).load(currentAllDesignItems.imageUrl).placeholder(R.drawable.ic_image_black_24dp).centerCrop().resize(displayMetrics.widthPixels, displayMetrics.heightPixels / 3).into(designHolder.Thumbnail);

        designHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int desc) {
                Snackbar.make(v, allDesignItems.get(desc).title, Snackbar.LENGTH_SHORT).show();
                confirmChoice(v, desc);
            }
        });

        loveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(context, "Hello you like me?", Toast.LENGTH_LONG).show();
                //ImageViewCompat.setImageTintList(loveBtn, ColorStateList.valueOf(ContextCompat.getColor(context,R.color.colorPrimaryDark)));
                loveBtn.setColorFilter(Color.RED);
                Intent testIntent = new Intent(context, JustTest.class);
                context.startActivity(testIntent);
                ((Activity) context).finish();

            }
        });


    }


    public void confirmChoice(View view, final int desc) {
        final CharSequence[] options = { "Add to Cart","View Size","Buy","Visit Designer Website", "Not Sure" };
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle("What do I do now " + allDesignItems.get(desc).title + " ?" );
        builder.setItems(options,new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(options[which].equals("Add to Cart"))
                {
                    Intent DetailsIntent = new Intent(context, ToolbarAndFab.class);
                    Title= new Bundle(); Description =  new Bundle(); Backdrop = new Bundle();
                    Title.putString("title", allDesignItems.get(desc).title);
                    Description.putString("venue", allDesignItems.get(desc).Description);
                    Backdrop.putString("date", allDesignItems.get(desc).imageUrl);
                    //Hbanner.putInt("banner", halls.get(desc).getBanner());

                        DetailsIntent.putExtras(Title);
                        DetailsIntent.putExtras(Description);
                        //DetailsIntent.putExtras(HLocation);
                        DetailsIntent.putExtras(Backdrop);
                        context.startActivity(DetailsIntent);

                } else if (options[which].equals("View Size")) {

                    dialog.dismiss();

                }else if (options[which].equals("Buy")) {
                    final Intent buyIntent = new Intent(context, BuyActivity.class);
                    Title= new Bundle(); Description =  new Bundle(); Backdrop = new Bundle();
                    Title.putString("title", allDesignItems.get(desc).title);
                    Description.putString("venue", allDesignItems.get(desc).Description);
                    Backdrop.putString("date", allDesignItems.get(desc).imageUrl);
                    //Hbanner.putInt("banner", halls.get(desc).getBanner());

                    buyIntent.putExtras(Title);
                    buyIntent.putExtras(Description);
                    //DetailsIntent.putExtras(HLocation);
                    buyIntent.putExtras(Backdrop);
                    context.startActivity(buyIntent);
                    ((Activity) context).overridePendingTransition(R.anim.lefttrslate, R.anim.righttranslate);
                    ((Activity) context).finish();
                    dialog.dismiss();

                } else if (options[which].equals("Visit Designer Website")) {

                    Bundle url = new Bundle();
                    url.putString("URL", allDesignItems.get(desc).imageUrl);
                    String site = allDesignItems.get(desc).imageUrl;
                    final Intent weburlIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(site));
                    weburlIntent.putExtras(url);
                    context.startActivity(weburlIntent);
                    dialog.dismiss();

                } else if (options[which].equals("Not Sure")) {
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }

    @Override
    public int getItemCount() {
        return allDesignItems.size();
    }

    //RETURN FILTER BJECT
    @Override
    public Filter getFilter() {

        if(itemsFilter == null){
            itemsFilter = new ItemFilter(filterList, this);
        }
        return itemsFilter;
    }

}
