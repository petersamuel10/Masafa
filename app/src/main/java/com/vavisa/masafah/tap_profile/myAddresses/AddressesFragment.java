package com.vavisa.masafah.tap_profile.myAddresses;

import android.content.Intent;
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
import com.vavisa.masafah.helpers.address_helper.MyAddressPresenter;
import com.vavisa.masafah.helpers.address_helper.MyAddressView;
import com.vavisa.masafah.tap_add.add_address.AddAddressActivity;
import com.vavisa.masafah.tap_add.add_address.AddressModel;
import com.vavisa.masafah.tap_my_shipment.my_shipments.MyShipmentPresenter;

import java.util.ArrayList;

public class AddressesFragment extends BaseFragment implements MyAddressView {

    private View view;
    private RecyclerView address_rec;
    private AddressHelper addressHelper;
    private ArrayList<AddressModel> addressList;
    private MyAddressPresenter presenter;

    public AddressesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_addresses, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        address_rec = view.findViewById(R.id.address_rec);

        presenter = new MyAddressPresenter();
        presenter.attachView(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        addressHelper = new AddressHelper(null, this, address_rec);
    }

    @Override
    public void address(ArrayList<AddressModel> addressList) {
        this.addressList = addressList;
        addressHelper.displayAddress(addressList);
    }

    @Override
    public void handleClickAction(AddressModel addressModel) {
        Intent intent = new Intent(getActivity(), AddAddressActivity.class);
        intent.putExtra("address_id", addressModel.getId());
        intent.putExtra("action", "showAddressDetails");
        startActivity(intent);
    }

    public void handleEditAction(AddressModel addressModel) {
        Intent intent = new Intent(getActivity(), AddAddressActivity.class);
        intent.putExtra("address_id", addressModel.getId());
        intent.putExtra("action", "editAddress");
        startActivity(intent);
    }

    public void handleDeleteAction(int position,AddressModel addressModel) {

        presenter.deleteAddresses(this,addressModel.getId());

    }

}
