package com.vavisa.masafah.tap_profile.MyAddresses;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseFragment;
import com.vavisa.masafah.helpers.address_helper.AddressHelper;
import com.vavisa.masafah.helpers.address_helper.MyAddressView;
import com.vavisa.masafah.tap_add.add_address.AddressModel;

import java.util.ArrayList;

public class AddressesFragment extends BaseFragment implements MyAddressView {

    private View view;
    private RecyclerView address_rec;
    private AddressHelper addressHelper;

    public AddressesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_addresses, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        address_rec = view.findViewById(R.id.address_rec);

        addressHelper = new AddressHelper(null,this,address_rec);

        return view;
    }

    @Override
    public void address(ArrayList<AddressModel> addressList) {
        addressHelper.displayAddress(addressList);
    }

    @Override
    public void handleClickAction(AddressModel addressModel) {

    }

    public void handleEditAction(AddressModel addressModel) {

        Toast.makeText(getContext(), "delete" + addressModel.getId(), Toast.LENGTH_SHORT).show();
    }

    public void handleDeleteAction(AddressModel addressModel) {

        Toast.makeText(getContext(), "edit" + addressModel.getId(), Toast.LENGTH_SHORT).show();
    }
}
