package com.vavisa.masafah.tap_profile.my_address;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.helpers.address_helper.AddressHelper;
import com.vavisa.masafah.helpers.address_helper.MyAddressAdapter;
import com.vavisa.masafah.helpers.address_helper.MyAddressPresenter;
import com.vavisa.masafah.helpers.address_helper.MyAddressView;
import com.vavisa.masafah.tap_add.add_address.AddressModel;

import java.util.ArrayList;

public class MyAddressActivity extends BaseActivity implements MyAddressView {

    private RecyclerView address_rec;
    private AddressHelper addressHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        initViews();
        addressHelper = new AddressHelper(this,null, address_rec);
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
        Toast.makeText(this, ""+addressModel.getId(), Toast.LENGTH_SHORT).show();
    }
}
