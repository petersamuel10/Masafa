package com.vavisa.masafah.helpers.address_helper;

import androidx.recyclerview.widget.RecyclerView;

import com.vavisa.masafah.tap_add.add_address.AddressModel;
import com.vavisa.masafah.tap_add.myAddress.MyAddressActivity;
import com.vavisa.masafah.tap_profile.myAddresses.AddressesFragment;
import com.vavisa.masafah.util.Connectivity;

import java.util.ArrayList;

public class AddressHelper {

    private RecyclerView address_rec;
    private MyAddressView view;
    private MyAddressAdapter adapter;

    public AddressHelper(MyAddressActivity activity, AddressesFragment fragment, RecyclerView address_rec) {
        MyAddressPresenter presenter = new MyAddressPresenter();
        view = fragment == null ? activity : fragment;
        presenter.attachView(view);
        if (Connectivity.checkInternetConnection())
            presenter.getAddresses();
        else
            view.showErrorConnection();
        this.address_rec = address_rec;
    }

    public void displayAddress(ArrayList<AddressModel> addressList) {
        adapter = new MyAddressAdapter(addressList, view);
        address_rec.setAdapter(adapter);
    }

    public void deleteAddressFromRecycler(int position) {
        adapter.deleteAddress(position);
    }
}
