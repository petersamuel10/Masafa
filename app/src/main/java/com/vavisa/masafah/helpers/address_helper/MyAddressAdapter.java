package com.vavisa.masafah.helpers.address_helper;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.vavisa.masafah.R;
import com.vavisa.masafah.tap_add.add_address.AddressModel;
import com.vavisa.masafah.tap_profile.MyAddresses.AddressesFragment;
import com.vavisa.masafah.tap_profile.my_address.MyAddressActivity;
import com.vavisa.masafah.util.Constants;

import java.util.ArrayList;

public class MyAddressAdapter extends RecyclerView.Adapter<MyAddressAdapter.ViewHolder> {

    private ArrayList<AddressModel> addressList;
    MyAddressView view;

    MyAddressAdapter(ArrayList<AddressModel> addressList, MyAddressView view) {
        this.addressList = addressList;
        this.view = view;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(addressList.get(position));
        holder.item.setOnClickListener(v ->
                view.handleClickAction(addressList.get(position)));

        if (view instanceof MyAddressActivity)
            holder.sw.setLockDrag(true);
        else {
            if (Constants.LANGUAGE.equals("en"))
                holder.sw.setDragEdge(2);
            else
                holder.sw.setDragEdge(1);
            holder.sw.setLockDrag(false);
            holder.edit_txt.setOnClickListener(v -> ((AddressesFragment) view).handleEditAction(addressList.get(position)));
            holder.delete_txt.setOnClickListener(v -> ((AddressesFragment) view).handleDeleteAction(addressList.get(position)));
        }

    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public SwipeRevealLayout sw;
        private TextView address_name, address_area,
                address_block_street, address_building,
                address_details, address_notes;
        private TextView edit_txt, delete_txt;
        private ConstraintLayout item;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            sw = itemView.findViewById(R.id.sw);
            item = itemView.findViewById(R.id.foreground);
            edit_txt = itemView.findViewById(R.id.tv_edit);
            delete_txt = itemView.findViewById(R.id.tv_delete);
            address_name = itemView.findViewById(R.id.address_name);
            address_area = itemView.findViewById(R.id.address_area);
            address_block_street = itemView.findViewById(R.id.address_block_street);
            address_building = itemView.findViewById(R.id.address_building);
            address_details = itemView.findViewById(R.id.address_details);
            address_notes = itemView.findViewById(R.id.address_extra);

        }

        public void bind(AddressModel addressModel) {

            address_name.setText(addressModel.getName());
            address_area.setText(addressModel.getArea());
            address_block_street.setText(addressModel.getBlock() + "  -  " + addressModel.getStreet());
            address_building.setText(addressModel.getBuilding());
            address_details.setText(addressModel.getDetails());
            address_notes.setText(addressModel.getNotes());
        }
    }
}
