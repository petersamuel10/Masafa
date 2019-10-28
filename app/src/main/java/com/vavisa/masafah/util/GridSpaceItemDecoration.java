package com.vavisa.masafah.util;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    //private static int count = 0;

    public GridSpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(
            @NonNull Rect outRect,
            @NonNull View view,
            @NonNull RecyclerView parent,
            @NonNull RecyclerView.State state) {
        outRect.bottom = space;
        outRect.right = space;
        outRect.left = space;
    }
}
