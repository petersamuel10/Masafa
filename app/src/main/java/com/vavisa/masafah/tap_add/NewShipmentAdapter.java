package com.vavisa.masafah.tap_add;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vavisa.masafah.R;

import java.util.ArrayList;

public class NewShipmentAdapter extends RecyclerView.Adapter<NewShipmentAdapter.ViewHolder> {

    private ArrayList<NewShipmentModel> shipmentsList;
    private Context context;


    public NewShipmentAdapter(ArrayList<NewShipmentModel> shipmentsList) {
        this.shipmentsList = shipmentsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView;
        context = parent.getContext();

        if (i == R.layout.add_shipment_list_item) {
            itemView =
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.add_shipment_list_item, parent, false);
        } else {
            itemView =
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.add_new_item, parent, false);
        }

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        if (position == shipmentsList.size())
            holder.addMoreItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shipmentsList.add(new NewShipmentModel("", 1));
                    notifyDataSetChanged();
                }
            });
        else {

            holder.bind(shipmentsList.get(position));
            final int[] quantity = new int[1];

            holder.add_quantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quantity[0] = shipmentsList.get(position).getQuantity() + 1;
                    holder.quantity_txt.setText(String.valueOf(quantity[0]));
                    shipmentsList.get(position).setQuantity(quantity[0]);
                }
            });

            holder.subtract_quantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (quantity[0] > 0) {
                        quantity[0] = shipmentsList.get(position).getQuantity() - 1;
                        holder.quantity_txt.setText(String.valueOf(quantity[0]));
                        shipmentsList.get(position).setQuantity(quantity[0]);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return shipmentsList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == shipmentsList.size()) ?
                R.layout.add_new_item :
                R.layout.add_shipment_list_item;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        FloatingActionButton addMoreItems, subtract_quantity, add_quantity;
        TextView quantity_txt;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            quantity_txt = itemView.findViewById(R.id.quantity);
            addMoreItems = itemView.findViewById(R.id.add_more_item);
            add_quantity = itemView.findViewById(R.id.add_quantity);
            subtract_quantity = itemView.findViewById(R.id.subtract_quantity);
        }

        public void bind(NewShipmentModel shipment) {

            quantity_txt.setText(String.valueOf(shipment.getQuantity()));
        }
    }
}
