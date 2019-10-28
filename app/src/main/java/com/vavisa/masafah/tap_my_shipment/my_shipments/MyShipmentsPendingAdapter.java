package com.vavisa.masafah.tap_my_shipment.my_shipments;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.vavisa.masafah.R;
import com.vavisa.masafah.tap_add.add_shipment.model.Shipment;
import com.vavisa.masafah.tap_my_shipment.my_shipments.model.ShipmentItems;
import com.vavisa.masafah.tap_my_shipment.my_shipments.model.ShipmentModel;
import com.vavisa.masafah.tap_my_shipment.shipment_details.ShipmentDetailsFragment;
import com.vavisa.masafah.util.Constants;

import java.util.ArrayList;

public class MyShipmentsPendingAdapter extends RecyclerView.Adapter<MyShipmentsPendingAdapter.ViewHolder> {

    private MyShipmentsFragment activity;
    private ArrayList<ShipmentModel> shipmentList;

    MyShipmentsPendingAdapter(MyShipmentsFragment activity, ArrayList<ShipmentModel> pendingList) {

        this.activity = activity;
        this.shipmentList = pendingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.my_shipment_pending_list_item, viewGroup, false);

        Context context = viewGroup.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bind(shipmentList.get(position));
        holder.itemView.setOnClickListener(
                v -> {
                    Fragment fragment = new ShipmentDetailsFragment();
                    activity.switchFragment(activity.getFragmentManager(), fragment, "shipmentDetails");
                });

        holder.edit_txt.setOnClickListener(v -> {
            holder.sw.close(true);
            (activity).editShipment(shipmentList.get(position));
        });
        holder.delete_txt.setOnClickListener(v -> (activity).deleteShipmentById(position, shipmentList.get(position).getId()));

    }

    @Override
    public int getItemCount() {
        return shipmentList.size();
    }

    void deleteShipmentFromRecycler(int index) {

        shipmentList.remove(index);
        notifyItemRemoved(index);

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        SwipeRevealLayout sw;
        TextView edit_txt, delete_txt;
        TextView shipment_number_txt, shipment_content_txt, pickup_location_txt, drop_location_txt;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            sw = itemView.findViewById(R.id.sw);
            if (Constants.LANGUAGE == "en")
                sw.setDragEdge(2);
            else
                sw.setDragEdge(1);
            edit_txt = itemView.findViewById(R.id.tv_edit);
            delete_txt = itemView.findViewById(R.id.tv_delete);
            shipment_number_txt = itemView.findViewById(R.id.shipment_number);
            shipment_content_txt = itemView.findViewById(R.id.shipment_description);
            pickup_location_txt = itemView.findViewById(R.id.pickup_location);
            drop_location_txt = itemView.findViewById(R.id.drop_location_area);


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
                item_str.append("\n\u25CF ").append(item.getAddressTo().getCity().getName()).append("\n");
                for (Shipment shipment : item.getShipmentList()) {
                    item_str.append("\t\t\t\u25CF").append(shipment.getQuantity()).append(" x   ").append(shipment.getCat_name()).append("\n");
                }
            }

            drop_location_txt.setText(drop_address_str);
            shipment_content_txt.setText(item_str.toString());

        }
    }
}
