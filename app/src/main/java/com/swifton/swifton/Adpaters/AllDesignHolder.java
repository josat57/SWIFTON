package com.swifton.swifton.Adpaters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.swifton.swifton.Helpers.ItemClickListener;
import com.swifton.swifton.R;

public class AllDesignHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ItemClickListener itemClickListener;
    ImageView Thumbnail;
    TextView Title, Description;
    android.support.v7.widget.AppCompatImageView loveBtn;

        public AllDesignHolder(View itemView) {
            super(itemView);
            Thumbnail = itemView.findViewById(R.id.imageViewThumbnail);
            Title = itemView.findViewById(R.id.textViewTitle);
            Description = itemView.findViewById(R.id.textViewDes);
            loveBtn = itemView.findViewById(R.id.loveButton);
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
