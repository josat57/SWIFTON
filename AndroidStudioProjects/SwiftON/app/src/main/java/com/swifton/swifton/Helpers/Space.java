package com.swifton.swifton.Helpers;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by amardeep on 11/7/2017.
 */

public class Space extends RecyclerView.ItemDecoration {
    int space;
    int spanCount;

    public Space(int space, int spanCount) {
        this.space = space;
        this.spanCount = spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildLayoutPosition(view) <= spanCount) {
            outRect.top = space;
        }
        outRect.right = space;
        outRect.left = space;
        outRect.bottom = space;
    }
}
