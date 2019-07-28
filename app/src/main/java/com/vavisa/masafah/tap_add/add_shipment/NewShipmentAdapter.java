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
import com.vavisa.masafah.tap_add.add_shipment.model.ShipmentItemModel;

import java.util.ArrayList;

public class NewShipmentAdapter extends RecyclerView.Adapter<NewShipmentAdapter.ViewHolder> {

    private ArrayList<ShipmentItemModel> shipmentsList;
    private ArrayList<CategoryModel> categoryList;
    private Context context;
    private Integer select_category_pos = 0;


    public NewShipmentAdapter(ArrayList<CategoryModel> categoryList, ArrayList<ShipmentItemModel> shipmentsList) {
        this.categoryList = categoryList;
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

        if (position == shipmentsList.size()) {
            holder.addMoreItems.setOnClickListener(v -> {
                if (shipmentsList.get(shipmentsList.size() - 1).getCategory_id() == -1)
                    Toast.makeText(context, context.getString(R.string.please_select_category_name_for_previous_shipment), Toast.LENGTH_SHORT).show();
                else {
                    shipmentsList.add(new ShipmentItemModel("", -1, 1));
                    notifyDataSetChanged();
                }
            });
        } else {
            // bind data if its edit shipment
            if (!shipmentsList.get(0).getCat_name().equals(""))
                holder.bind(shipmentsList.get(position));
            int[] quantity = new int[1];

            holder.category_name_ed.setOnClickListener(v -> {

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle(context.getString(R.string.select_category));
                alert.setCancelable(false);
                String[] categories_name = new String[categoryList.size()];
                for (int i = 0; i < categoryList.size(); i++) {
                    categories_name[i] = categoryList.get(i).getName();
                }

                alert.setSingleChoiceItems(categories_name, select_category_pos, (dialog, pos) -> {
                    dialog.dismiss();
                    select_category_pos = pos;

                    if (categoryExist(categoryList.get(pos).getId()))
                        Toast.makeText(context, context.getString(R.string.category_already_added), Toast.LENGTH_SHORT).show();
                    else {
                        holder.category_name_ed.setText(categoryList.get(pos).getName());
                        shipmentsList.get(position).setCategory_id(categoryList.get(pos).getId());
                        shipmentsList.get(position).setCat_name(categoryList.get(pos).getName());
                    }
                });
                alert.create().show();
            });

            holder.ic_delete.setOnClickListener(v -> {
                if (shipmentsList.size() == 1)
                    Toast.makeText(context, context.getString(R.string.you_must_add_at_least_one_shipment), Toast.LENGTH_SHORT).show();
                else {
                    shipmentsList.remove(position);
                    notifyItemRemoved(position);
                }
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

    private boolean categoryExist(Integer cat_id) {

        for (ShipmentItemModel item : shipmentsList) {
            if (cat_id == item.getCategory_id())
                return true;
        }
        return false;
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

    public ArrayList<ShipmentItemModel> getShipmentList() {
        return shipmentsList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        FloatingActionButton addMoreItems, ic_delete, subtract_quantity, add_quantity;
        TextView quantity_txt, category_name_ed;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            category_name_ed = itemView.findViewById(R.id.category_name);
            ic_delete = itemView.findViewById(R.id.ic_del);
            quantity_txt = itemView.findViewById(R.id.quantity);
            addMoreItems = itemView.findViewById(R.id.add_more_item);
            add_quantity = itemView.findViewById(R.id.add_quantity);
            subtract_quantity = itemView.findViewById(R.id.subtract_quantity);
        }

        public void bind(ShipmentItemModel shipmentItemModel) {
            category_name_ed.setText(shipmentItemModel.getCat_name());
            quantity_txt.setText(String.valueOf(shipmentItemModel.getQuantity()));
        }
    }
}
