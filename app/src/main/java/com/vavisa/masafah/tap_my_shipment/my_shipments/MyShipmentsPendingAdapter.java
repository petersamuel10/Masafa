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

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.vavisa.masafah.R;
import com.vavisa.masafah.tap_add.add_shipment.model.ShipmentItemModel;
import com.vavisa.masafah.tap_my_shipment.shipment_details.ShipmentDetailsFragment;
import com.vavisa.masafah.util.Constants;

import java.util.ArrayList;

public class MyShipmentsPendingAdapter extends RecyclerView.Adapter<MyShipmentsPendingAdapter.ViewHolder> {

    private Context context;
    private MyShipmentsFragment activity;
    private ArrayList<ShipmentModel> shipmentList;

    public MyShipmentsPendingAdapter(MyShipmentsFragment activity, ArrayList<ShipmentModel> pendingList) {

        this.activity = activity;
        this.shipmentList = pendingList;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bind(shipmentList.get(position));
        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = new ShipmentDetailsFragment();
                        activity.switchFragment(activity.getFragmentManager(), fragment, "shipmentDetails");
                    }
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

    public void deleteShipmentFromRecycler(int index) {

        shipmentList.remove(index);
        notifyItemRemoved(index);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public SwipeRevealLayout sw;
        public TextView edit_txt, delete_txt;
        public TextView shipment_number_txt, shipment_content_txt, pickup_location_txt, drop_location_txt;


        public ViewHolder(@NonNull View itemView) {
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

        public void bind(ShipmentModel shipmentModel) {

            shipment_number_txt.setText(shipmentModel.getId());
            pickup_location_txt.setText(shipmentModel.getAddress_from().getCity().getName());
            drop_location_txt.setText(shipmentModel.getAddress_to().getCity().getName());
            StringBuilder item_str = new StringBuilder();
            for (ShipmentItemModel item : shipmentModel.getItems()) {
                item_str.append("\u25CF ").append(item.getQuantity()).append(" x   ").append(item.getCat_name()).append("\n");
            }

            shipment_content_txt.setText(item_str.toString());

        }
    }
}
