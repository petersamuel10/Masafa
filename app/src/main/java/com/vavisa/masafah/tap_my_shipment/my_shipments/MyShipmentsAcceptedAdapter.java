package com.vavisa.masafah.tap_my_shipment.my_shipments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vavisa.masafah.R;
import com.vavisa.masafah.tap_my_shipment.shipment_details.ShipmentDetailsFragment;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyShipmentsAcceptedAdapter extends RecyclerView.Adapter<MyShipmentsAcceptedAdapter.ViewHolder> {

    private MyShipmentsFragment activity;
    private ArrayList<ShipmentModel> shipmentList;
    private Context context;

    public MyShipmentsAcceptedAdapter(MyShipmentsFragment activity, ArrayList<ShipmentModel> acceptList) {
        this.activity = activity;
        this.shipmentList = acceptList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.my_shipment_accepted_list_item, viewGroup, false);
        context = viewGroup.getContext();

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        holder.bind(shipmentList.get(i));
        holder.itemView.setOnClickListener(
                v -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("shipment_id", shipmentList.get(i).getId());
                    Fragment fragment = new ShipmentDetailsFragment();
                    fragment.setArguments(bundle);
                    activity.switchFragment(activity.getFragmentManager(), fragment, "shipmentDetails");
                });
    }

    @Override
    public int getItemCount() {
        return shipmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView com_delivery_image;
        public TextView shipment_number_txt, shipment_content_txt, pickup_location_txt, drop_location_txt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            shipment_content_txt = itemView.findViewById(R.id.shipment_description);
            shipment_number_txt = itemView.findViewById(R.id.shipment_number);
            pickup_location_txt = itemView.findViewById(R.id.pickup_location);
            drop_location_txt = itemView.findViewById(R.id.drop_location_area);
            com_delivery_image = itemView.findViewById(R.id.delivery_company_image);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                itemView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
                itemView.setClipToOutline(true);
            }
        }

        public void bind(ShipmentModel shipmentModel) {

            shipment_number_txt.setText(shipmentModel.getId());
            pickup_location_txt.setText(shipmentModel.getAddress_from().getArea());
            drop_location_txt.setText(shipmentModel.getAddress_to().getArea());

            StringBuilder item_str = new StringBuilder();
            for (Items item : shipmentModel.getItems()) {
                item_str.append("\u25CF ").append(item.getQuantity()).append(" x   ").append(item.getCategory_name()).append("\n");
            }

            shipment_content_txt.setText(item_str.toString());

            Glide.with(context).load(shipmentModel.getCompany().getImage()).into(com_delivery_image);

        }
    }
}
