package com.vavisa.masafah.helpers.address_helper;

import android.support.v7.widget.RecyclerView;

import com.vavisa.masafah.tap_add.add_address.AddressModel;
import com.vavisa.masafah.tap_profile.MyAddresses.AddressesFragment;
import com.vavisa.masafah.tap_profile.my_address.MyAddressActivity;

import java.util.ArrayList;

public class AddressHelper  {

    private RecyclerView address_rec;
    private MyAddressView view;

    public AddressHelper(MyAddressActivity activity, AddressesFragment fragment, RecyclerView address_rec) {
        MyAddressPresenter presenter = new MyAddressPresenter();
        view = fragment == null ? activity : fragment;
        presenter.attachView(view);
        presenter.getAddresses();
        this.address_rec = address_rec;
    }

    public void displayAddress(ArrayList<AddressModel> addressList){
        MyAddressAdapter adapter = new MyAddressAdapter(addressList, view);
        address_rec.setAdapter(adapter);
    }
}
