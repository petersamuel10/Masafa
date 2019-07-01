package com.vavisa.masafah.tap_add.add_shipment;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vavisa.masafah.R;
import com.vavisa.masafah.tap_add.add_shipment.model.CategoryModel;
import com.vavisa.masafah.tap_add.add_shipment.model.NewShipmentModel;

import java.util.ArrayList;

public class NewShipmentAdapter extends RecyclerView.Adapter<NewShipmentAdapter.ViewHolder> {

    private ArrayList<NewShipmentModel> shipmentsList;
    private ArrayList<CategoryModel> categoryList;
    private Context context;
    private Integer select_category_pos = 0;


    public NewShipmentAdapter(ArrayList<CategoryModel> categoryList) {
        this.categoryList = categoryList;

        this.shipmentsList = new ArrayList<>();
        this.shipmentsList.add(new NewShipmentModel("",-1, 1));
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
                    if (shipmentsList.get(shipmentsList.size() - 1).getCategory_id() == -1)
                        Toast.makeText(context, context.getString(R.string.please_select_category_name_for_previous_shipment), Toast.LENGTH_SHORT).show();
                    else {
                        shipmentsList.add(new NewShipmentModel("",-1, 1));
                        notifyDataSetChanged();
                    }
                }
            });
        else {

            int[] quantity = new int[1];

            holder.category_name_ed.setOnClickListener(v -> {

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle(context.getString(R.string.select_category));
                alert.setCancelable(false);
                String[] countries_name = new String[categoryList.size()];
                for (int i = 0; i < categoryList.size(); i++) {
                    countries_name[i] = categoryList.get(i).getName();
                }

                alert.setSingleChoiceItems(countries_name, select_category_pos, (dialog, pos) -> {
                    dialog.dismiss();
                    select_category_pos = pos;
                    holder.category_name_ed.setText(categoryList.get(pos).getName());
                    shipmentsList.get(position).setCategory_id(categoryList.get(pos).getId());
                    shipmentsList.get(position).setCat_name(categoryList.get(pos).getName());
                    // country_id = countriesList.get(position).getId();
                });
                alert.create().show();
            });

            holder.add_quantity.setOnClickListener(v -> {

                quantity[0] = shipmentsList.get(position).getQuantity() + 1;
                holder.quantity_txt.setText(String.valueOf(quantity[0]));
                shipmentsList.get(position).setQuantity(quantity[0]);
            });
            holder.subtract_quantity.setOnClickListener(v -> {
                if (quantity[0] > 1) {
                    quantity[0] = shipmentsList.get(position).getQuantity() - 1;
                    holder.quantity_txt.setText(String.valueOf(quantity[0]));
                    shipmentsList.get(position).setQuantity(quantity[0]);
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

    public ArrayList<NewShipmentModel> getShipmentList() {
        return shipmentsList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        FloatingActionButton addMoreItems, subtract_quantity, add_quantity;
        TextView quantity_txt, category_name_ed;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            category_name_ed = itemView.findViewById(R.id.category_name);
            quantity_txt = itemView.findViewById(R.id.quantity);
            addMoreItems = itemView.findViewById(R.id.add_more_item);
            add_quantity = itemView.findViewById(R.id.add_quantity);
            subtract_quantity = itemView.findViewById(R.id.subtract_quantity);
        }
    }
}
