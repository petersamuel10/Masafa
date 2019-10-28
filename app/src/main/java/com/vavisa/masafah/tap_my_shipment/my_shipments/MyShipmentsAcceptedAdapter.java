package com.vavisa.masafah.tap_my_shipment.my_shipments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vavisa.masafah.R;
import com.vavisa.masafah.tap_add.add_shipment.model.Shipment;
import com.vavisa.masafah.tap_my_shipment.my_shipments.model.ShipmentItems;
import com.vavisa.masafah.tap_my_shipment.my_shipments.model.ShipmentModel;
import com.vavisa.masafah.tap_my_shipment.shipment_details.ShipmentDetailsFragment;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyShipmentsAcceptedAdapter extends RecyclerView.Adapter<MyShipmentsAcceptedAdapter.ViewHolder> {

    private MyShipmentsFragment activity;
    private ArrayList<ShipmentModel> shipmentList = new ArrayList<>();
    private Context context;

    MyShipmentsAcceptedAdapter(MyShipmentsFragment activity, ArrayList<ShipmentModel> acceptList) {
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

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView com_delivery_image;
        TextView shipment_number_txt, shipment_content_txt, pickup_location_txt, drop_location_txt;

        ViewHolder(@NonNull View itemView) {
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

        void bind(ShipmentModel shipmentModel) {

            shipment_number_txt.setText(shipmentModel.getId());
            pickup_location_txt.setText(shipmentModel.getAddress_from().getCity().getName());
            StringBuilder item_str = new StringBuilder();
            StringBuilder drop_address_str = new StringBuilder();
            for (ShipmentItems item : shipmentModel.getItems()) {
                drop_address_str.append("\u25CF").append(item.getAddressTo().getCity().getName()).append("\n");
                for (Shipment shipment : item.getShipmentList()) {

                    item_str.append("\u25CF ").append(item.getAddressTo().getCity().getName()).append("\n")
                            .append("\t\t\t\u25CF").append(shipment.getQuantity()).append(" x   ").append(shipment.getCat_name()).append("\n");
                }
            }

            drop_location_txt.setText(drop_address_str);
            shipment_content_txt.setText(item_str.toString());

            Glide.with(context).load(shipmentModel.getCompany().getImage()).into(com_delivery_image);

        }
    }
}
