package com.vavisa.masafah.tap_add.map;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.tap_profile.my_address.MyAddressActivity;
import com.vavisa.masafah.util.Constants;

public class SelectLocationActivity extends BaseActivity implements View.OnClickListener {

    private ConstraintLayout pickupLayout;
    private ConstraintLayout dropLayout;
    private ImageView pick_ic_done, drop_ic_done;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);

        Toolbar toolbar = findViewById(R.id.select_location_toolbar);
        setSupportActionBar(toolbar);
        setTitle("");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pickupLayout = findViewById(R.id.pickup_location_layout);
        pick_ic_done = findViewById(R.id.pick_ic_done);
        dropLayout = findViewById(R.id.drop_location_layout);
        drop_ic_done = findViewById(R.id.drop_ic_done);

        pickupLayout.setOnClickListener(this);
        dropLayout.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!TextUtils.isEmpty(Constants.addShipmentModel.getAddress_from_id()))
            pick_ic_done.setVisibility(View.VISIBLE);

        if (!TextUtils.isEmpty(Constants.addShipmentModel.getAddress_to_id()))
            drop_ic_done.setVisibility(View.VISIBLE);

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
        }
    }

    private void showPopAddressWay() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.select_address_from));

        String[] address_ways_items = new String[]{getString(R.string.my_address), getString(R.string.map)};

        builder.setItems(address_ways_items, (dialog, position) -> {
            if (position == 0)
                startActivity(new Intent(SelectLocationActivity.this, MyAddressActivity.class));
            else
                startActivity(new Intent(SelectLocationActivity.this, MapActivity.class));
        });

        builder.create().show();
    }
}
