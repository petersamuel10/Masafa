package com.vavisa.masafah.tap_profile.my_address;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.tap_add.add_address.AddressModel;

import java.util.ArrayList;

public class MyAddressActivity extends BaseActivity implements MyAddressView {

    RecyclerView address_rec;
    MyAddressPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        initViews();
    }

    private void initViews() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        address_rec = findViewById(R.id.address_rec);

        presenter = new MyAddressPresenter();
        presenter.attachView(this);
        presenter.getAddresses();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void address(ArrayList<AddressModel> addressList) {

    }
}
