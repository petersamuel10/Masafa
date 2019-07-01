package com.vavisa.masafah.tap_add.map;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.helpers.address_helper.AddressHelper;
import com.vavisa.masafah.helpers.address_helper.MyAddressView;
import com.vavisa.masafah.tap_add.add_address.AddressModel;
import com.vavisa.masafah.util.Constants;

import java.util.ArrayList;

public class MyAddressActivity extends BaseActivity implements MyAddressView {

    private RecyclerView address_rec;
    private AddressHelper addressHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        initViews();
        addressHelper = new AddressHelper(this, null, address_rec);
    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        address_rec = findViewById(R.id.address_rec);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void address(ArrayList<AddressModel> addressList) {
        addressHelper.displayAddress(addressList);
    }

    @Override
    public void handleClickAction(AddressModel addressModel) {

        if (getIntent().getExtras().getString("tag").equals("pickup_tag")) {
            Constants.addShipmentModel.setPickup_address(addressModel);
            Constants.addShipmentModel.setAddress_from_id(addressModel.getId());
        } else if (getIntent().getExtras().getString("tag").equals("drop_tag")) {
            Constants.addShipmentModel.setDrop_address(addressModel);
            Constants.addShipmentModel.setAddress_to_id(addressModel.getId());
        }
        finish();

    }
}
