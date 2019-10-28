package com.vavisa.masafah.util;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BottomSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public BottomSpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(
            @NonNull Rect outRect,
            @NonNull View view,
            @NonNull RecyclerView parent,
            @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.bottom = space;
    }
}
