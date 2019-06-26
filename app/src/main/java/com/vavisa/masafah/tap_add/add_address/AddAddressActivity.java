package com.vavisa.masafah.tap_add.add_address;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.util.Constants;

public class AddAddressActivity extends BaseActivity implements AddAddressView {

    private Button confirmButton;
    private EditText location_name_ed, block_ed, area_ed, street_ed, building_ed, details_ed, extra_ed;
    private String location_name, city, block, area, street, building, details, extra;
    private AddAddressPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        initViews();

        confirmButton.setOnClickListener(v -> {
            if (valid())
                presenter.AddAddress(new AddressModel(location_name, area, block, street, building, details, extra));
        });
    }

    private boolean valid() {
        if (TextUtils.isEmpty(location_name_ed.getText().toString())) {
            showMessage(getString(R.string.please_enter_location_name));
            return false;
        } else
            location_name = location_name_ed.getText().toString();

        if (TextUtils.isEmpty(area_ed.getText().toString())) {
            showMessage(getString(R.string.please_enter_area));
            return false;
        } else
            area = area_ed.getText().toString();

        if (TextUtils.isEmpty(block_ed.getText().toString())) {
            showMessage(getString(R.string.please_enter_block));
            return false;
        } else
            block = block_ed.getText().toString();

        if (TextUtils.isEmpty(street_ed.getText().toString())) {
            showMessage(getString(R.string.please_enter_street));
            return false;
        } else
            street = street_ed.getText().toString();

        if (TextUtils.isEmpty(building_ed.getText().toString())) {
            showMessage(getString(R.string.please_enter_building));
            return false;
        } else
            building = building_ed.getText().toString();

        details = details_ed.getText().toString();
        extra = extra_ed.getText().toString();
        return true;
    }


    private void initViews() {

        Toolbar toolbar = findViewById(R.id.add_address_toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        TextView toolbarTitle = toolbar.findViewById(R.id.add_address_toolbar_title);
        toolbarTitle.setText("Pickup location");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        location_name_ed = findViewById(R.id.location_name);
        area_ed = findViewById(R.id.area);
        block_ed = findViewById(R.id.block);
        street_ed = findViewById(R.id.street);
        building_ed = findViewById(R.id.building);
        details_ed = findViewById(R.id.details);
        extra_ed = findViewById(R.id.extra);

        confirmButton = findViewById(R.id.confirm_button);

        area = getIntent().getExtras().getString("area");
        street = getIntent().getExtras().getString("street");
        building = getIntent().getExtras().getString("building");
        city = getIntent().getExtras().getString("city");

        area_ed.setText((area != null) ? area : "");
        street_ed.setText((street != null) ? street : "");
        building_ed.setText((building != null) ? building : "");
        details_ed.setText((city != null) ? city : "");

        presenter = new AddAddressPresenter();
        presenter.attachView(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void Address_id(String address_id) {

        Constants.addShipmentModel.setAddress_from_id(address_id);
    }
}
