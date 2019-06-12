package com.vavisa.masafah.tap_profile.shipment_history;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import com.vavisa.masafah.R;

class MyShipmentsDeliveredAdapter extends RecyclerView.Adapter<MyShipmentsDeliveredAdapter.ViewHolder> {

    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.shipment_history_delivered_list_item, parent, false);

        context = parent.getContext();

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                itemView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
                itemView.setClipToOutline(true);
            }
        }
    }
}
