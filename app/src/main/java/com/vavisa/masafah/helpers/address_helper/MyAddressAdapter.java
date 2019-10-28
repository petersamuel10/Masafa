package com.vavisa.masafah.helpers.address_helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.vavisa.masafah.R;
import com.vavisa.masafah.tap_add.add_address.AddressModel;
import com.vavisa.masafah.tap_add.myAddress.MyAddressActivity;
import com.vavisa.masafah.tap_profile.myAddresses.AddressesFragment;
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
            holder.delete_txt.setOnClickListener(v -> ((AddressesFragment) view).handleDeleteAction(position, addressList.get(position)));
        }

    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    void deleteAddress(int position) {

        addressList.remove(position);
        notifyItemRemoved(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        SwipeRevealLayout sw;
        private TextView address_name, address_area,
                address_block_street, address_building,
                address_details, address_notes;
        private TextView edit_txt, delete_txt;
        private ConstraintLayout item;

        ViewHolder(@NonNull View itemView) {

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

        void bind(AddressModel addressModel) {

            address_name.setText(addressModel.getName());
            address_area.setText(addressModel.getGovernorate().getName().concat(" - ").concat(addressModel.getCity().getName()));
            address_block_street.setText(addressModel.getBlock().concat("  -  ").concat(addressModel.getStreet()));
            address_building.setText(addressModel.getBuilding());
            address_details.setText(addressModel.getDetails());
            address_notes.setText(addressModel.getNotes());
        }
    }
}
