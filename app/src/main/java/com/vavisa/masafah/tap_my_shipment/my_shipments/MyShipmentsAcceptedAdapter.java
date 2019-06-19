package com.vavisa.masafah.tap_my_shipment.my_shipments;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import com.vavisa.masafah.R;
import com.vavisa.masafah.tap_my_shipment.shipment_details.ShipmentDetailsFragment;

public class MyShipmentsAcceptedAdapter extends RecyclerView.Adapter<MyShipmentsAcceptedAdapter.ViewHolder> {

    private MyShipmentsFragment activity;

    public MyShipmentsAcceptedAdapter(MyShipmentsFragment activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.my_shipment_accepted_list_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = new ShipmentDetailsFragment();
                        activity.switchFragment(activity.getFragmentManager(), fragment, "shipmentDetails");
                    }
                });
    }

    @Override
    public int getItemCount() {
        return 3;
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
