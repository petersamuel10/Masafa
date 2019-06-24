package com.vavisa.masafah.tap_my_shipment.my_shipments;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.vavisa.masafah.R;
import com.vavisa.masafah.tap_my_shipment.shipment_details.ShipmentDetailsFragment;
import com.vavisa.masafah.util.Constants;

public class MyShipmentsPendingAdapter extends RecyclerView.Adapter<MyShipmentsPendingAdapter.ViewHolder> {

    private Context context;
    private MyShipmentsFragment activity;

    public MyShipmentsPendingAdapter(MyShipmentsFragment activity) {

        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.my_shipment_pending_list_item, viewGroup, false);

        context = viewGroup.getContext();
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

        public SwipeRevealLayout sw;
        public TextView edit_txt, delete_txt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sw = itemView.findViewById(R.id.sw);
            if(Constants.LANGUAGE == "en")
                sw.setDragEdge(2);
            else
                sw.setDragEdge(1);
            edit_txt = itemView.findViewById(R.id.tv_edit);
            delete_txt = itemView.findViewById(R.id.tv_delete);

            edit_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "edit", Toast.LENGTH_SHORT).show();
                }
            });

            delete_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
                }
            });


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                itemView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
                itemView.setClipToOutline(true);
            }
        }
    }
}
