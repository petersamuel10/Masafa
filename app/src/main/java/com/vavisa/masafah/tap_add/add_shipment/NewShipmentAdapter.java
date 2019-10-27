package com.vavisa.masafah.tap_add.add_shipment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vavisa.masafah.R;
import com.vavisa.masafah.tap_add.add_address.AddAddressActivity;
import com.vavisa.masafah.tap_add.add_address.AddressModel;
import com.vavisa.masafah.tap_add.add_shipment.model.CategoryModel;
import com.vavisa.masafah.tap_add.add_shipment.model.ShipmentItemModel;
import com.vavisa.masafah.tap_add.map.MyAddressActivity;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class NewShipmentAdapter extends RecyclerView.Adapter<NewShipmentAdapter.ViewHolder> {

    private ArrayList<ShipmentItemModel> shipmentsList;
    private ArrayList<CategoryModel> categoryList;
    private Context context;
    private Integer select_category_pos = 0;
    private Integer selected_item_pos = 0;
    private final int DROP_ADDRESS = 5;


    NewShipmentAdapter(ArrayList<CategoryModel> categoryList, ArrayList<ShipmentItemModel> shipmentsList) {
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
                if (shipmentsList.get(shipmentsList.size() - 1).getAddress_to_id() == -1 || shipmentsList.get(shipmentsList.size() - 1).getCategory_id() == -1) {
                    Toast.makeText(context, context.getString(R.string.please_select_drop_address_and_category_name_for_previous_shipment), Toast.LENGTH_SHORT).show();
                } else {
                    shipmentsList.add(new ShipmentItemModel(-1, "", 1, shipmentsList.get(shipmentsList.size() - 1).getAddress_to_id()));
                    shipmentsList.get(shipmentsList.size() - 1).setDrop_address(shipmentsList.get(shipmentsList.size() - 2).getDrop_address());
                    notifyDataSetChanged();
                }
            });
        } else {
            // bind data after select address or edit shipment
            if (!shipmentsList.get(position).getAddress_to_id().equals(-1))
                holder.bind(shipmentsList.get(position));

            int[] quantity = new int[1];

            holder.drop_address.setOnClickListener(v -> {
                selected_item_pos = position;
                showPopAddressWay();
            });
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
                    holder.category_name_ed.setText(categoryList.get(pos).getName());
                    shipmentsList.get(position).setCategory_id(categoryList.get(pos).getId());
                    shipmentsList.get(position).setCat_name(categoryList.get(pos).getName());

//                    if (categoryExist(categoryList.get(pos).getId()))
//                        Toast.makeText(context, context.getString(R.string.category_already_added), Toast.LENGTH_SHORT).show();
//                    else {
//
//                    }
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

    private void showPopAddressWay() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.select_location));
        String[] address_ways_items = new String[]{context.getString(R.string.add_new_address), context.getString(R.string.my_address)};
        builder.setItems(address_ways_items, (dialog, position) -> {
            Intent intent = null;
            if (position == 0)
                intent = new Intent(context, AddAddressActivity.class);
            else
                intent = new Intent(new Intent(context, MyAddressActivity.class));

            ((Activity) context).startActivityForResult(intent, DROP_ADDRESS);
        });

        builder.create().show();
    }

    void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK)
            if (requestCode == DROP_ADDRESS) {
                AddressModel dropAddress = data.getParcelableExtra("selectedAddress");

                shipmentsList.get(selected_item_pos).setDrop_address(dropAddress);
                shipmentsList.get(selected_item_pos).setAddress_to_id(Integer.valueOf(dropAddress.getId()));
                notifyDataSetChanged();
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

    ArrayList<ShipmentItemModel> getShipmentList() {
        return shipmentsList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        FloatingActionButton addMoreItems, ic_delete, subtract_quantity, add_quantity;
        TextView drop_address, category_name_ed, quantity_txt;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            drop_address = itemView.findViewById(R.id.drop_address);
            category_name_ed = itemView.findViewById(R.id.category_name);
            ic_delete = itemView.findViewById(R.id.ic_del);
            quantity_txt = itemView.findViewById(R.id.quantity);
            addMoreItems = itemView.findViewById(R.id.add_more_item);
            add_quantity = itemView.findViewById(R.id.add_quantity);
            subtract_quantity = itemView.findViewById(R.id.subtract_quantity);
        }

        void bind(ShipmentItemModel shipmentItemModel) {

            drop_address.setText(shipmentItemModel.getDrop_address().getGovernorate().getName().concat(", ").concat(shipmentItemModel.getDrop_address().getCity().getName()));
            category_name_ed.setText(shipmentItemModel.getCat_name());
            quantity_txt.setText(String.valueOf(shipmentItemModel.getQuantity()));
        }
    }
}
