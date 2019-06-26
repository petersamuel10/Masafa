package com.vavisa.masafah.tap_add.map;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.tap_add.add_address.AddressModel;
import com.vavisa.masafah.tap_add.invoice.Invoice;
import com.vavisa.masafah.tap_profile.my_address.MyAddressActivity;
import com.vavisa.masafah.util.Constants;

public class SelectLocationActivity extends BaseActivity implements View.OnClickListener {

    private ConstraintLayout pickupLayout;
    private ConstraintLayout dropLayout;
    private ImageView pick_ic_done, drop_ic_done;
    private Button next_btn;
    private TextView pickup_address_txt, drop_address_txt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        initViews();

    }

    private void initViews() {

        Toolbar toolbar = findViewById(R.id.select_location_toolbar);
        setSupportActionBar(toolbar);
        setTitle("");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pickupLayout = findViewById(R.id.pickup_location_layout);
        pick_ic_done = findViewById(R.id.pick_ic_done);
        dropLayout = findViewById(R.id.drop_location_layout);
        drop_ic_done = findViewById(R.id.drop_ic_done);
        pickup_address_txt = findViewById(R.id.pickup_address);
        drop_address_txt = findViewById(R.id.drop_address);
        next_btn = findViewById(R.id.next_button);

        pickupLayout.setOnClickListener(this);
        dropLayout.setOnClickListener(this);
        next_btn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!TextUtils.isEmpty(Constants.addShipmentModel.getAddress_from_id())) {
            pick_ic_done.setVisibility(View.VISIBLE);
            pickup_address_txt.setText(Address_to_string(Constants.addShipmentModel.getPickup_address()));
        }

        if (!TextUtils.isEmpty(Constants.addShipmentModel.getAddress_to_id())) {
            drop_ic_done.setVisibility(View.VISIBLE);
            drop_address_txt.setText(Address_to_string(Constants.addShipmentModel.getDrop_address()));
        }

    }

    private String Address_to_string(AddressModel address) {
        return address.getName() + ", " + address.getArea() + ", " + address.getBlock() + ", " + address.getStreet() + ", " + address.getBuilding();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.pickup_location_layout:
                showPopAddressWay();
                break;

            case R.id.drop_location_layout:
                showPopAddressWay();
                break;
            case R.id.next_button:
                if (valid())
                    start(Invoice.class);
                break;
        }
    }

    private boolean valid() {
        if (TextUtils.isEmpty(Constants.addShipmentModel.getAddress_from_id())) {
            showMessage(getString(R.string.please_select_pickup_address));
            return false;
        }
        if (TextUtils.isEmpty(Constants.addShipmentModel.getAddress_to_id())) {
            showMessage(getString(R.string.please_select_drop_address));
            return false;
        }
        return true;
    }

    private void showPopAddressWay() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.select_address_from));

        String[] address_ways_items = new String[]{getString(R.string.my_address), getString(R.string.map)};

        builder.setItems(address_ways_items, (dialog, position) -> {
            Intent intent = null;
            if (position == 0) {
                intent = new Intent(new Intent(SelectLocationActivity.this, MyAddressActivity.class));
                intent.putExtra("tag", "pickup_tag");

            } else {
                intent = new Intent(SelectLocationActivity.this, MapActivity.class);
                intent.putExtra("tag", "drop_tag");
            }

            startActivity(intent);
        });

        builder.create().show();
    }
}
