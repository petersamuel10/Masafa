package com.vavisa.masafah.tap_add.add_address;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.util.Connectivity;
import com.vavisa.masafah.util.Constants;

public class AddAddressActivity extends BaseActivity implements AddAddressView {

    private Button confirmButton;
    private EditText location_name_ed, mobile_ed, block_ed, area_ed, street_ed, building_ed, details_ed, extra_ed;
    private String location_name, mobile, city, block, area, street, building, details, extra;
    private AddAddressPresenter presenter;
    private String address_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        initViews();

        if (getIntent().getExtras().containsKey("address_id")) {
            if (getIntent().getStringExtra("action").equals("showAddressDetails"))
                disableViewsForDetails();
            if(Connectivity.checkInternetConnection())
                presenter.getAddressById(getIntent().getStringExtra("address_id"));
            else
                showErrorConnection();
            confirmButton.setOnClickListener(v -> {
                if (valid()) {
                    AddressModel addressModel = new AddressModel(address_id, location_name, area, block, street, building, details, extra);
                    addressModel.setMobile(mobile);
                    if(Connectivity.checkInternetConnection())
                        presenter.editAddress(addressModel);
                    else
                        showErrorConnection();
                }
            });
        } else
            confirmButton.setOnClickListener(v -> {
                if (valid()) {
                    AddressModel addressModel = new AddressModel(location_name, area, block, street, building, details, extra);
                    addressModel.setMobile(mobile);
                    if(Connectivity.checkInternetConnection())
                        presenter.AddAddress(addressModel);
                    else
                        showErrorConnection();
                }
            });
    }

    private void disableViewsForDetails() {

        location_name_ed.setEnabled(false);
        mobile_ed.setEnabled(false);
        block_ed.setEnabled(false);
        area_ed.setEnabled(false);
        street_ed.setEnabled(false);
        building_ed.setEnabled(false);
        details_ed.setEnabled(false);
        extra_ed.setEnabled(false);

        confirmButton.setVisibility(View.GONE);
    }

    private boolean valid() {
        if (TextUtils.isEmpty(location_name_ed.getText().toString())) {
            showMessage(getString(R.string.please_enter_location_name));
            return false;
        } else
            location_name = location_name_ed.getText().toString();

        if (TextUtils.isEmpty(mobile_ed.getText().toString())) {
            showMessage(getString(R.string.please_enter_mobile_number));
            return false;
        } else
            mobile = mobile_ed.getText().toString();

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
        mobile_ed = findViewById(R.id.mobile);
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
    public void addNewAddress(AddressModel addressModel) {
        if (getIntent().getExtras().getString("tag").equals("pickup_tag")) {
            Constants.addShipmentModel.setPickup_address(addressModel);
            Constants.addShipmentModel.setAddress_from_id(addressModel.getId());
        } else if (getIntent().getExtras().getString("tag").equals("drop_tag")) {
            Constants.addShipmentModel.setDrop_address(addressModel);
            Constants.addShipmentModel.setAddress_to_id(addressModel.getId());
        }
        finish();
    }

    @Override
    public void getAddressDetails(AddressModel address) {

        address_id = address.getId();
        location_name_ed.setText(address.getName());
        mobile_ed.setText(address.getMobile());
        block_ed.setText(address.getBlock());
        area_ed.setText(address.getArea());
        street_ed.setText(address.getStreet());
        building_ed.setText(address.getBuilding());
        details_ed.setText(address.getDetails());
        extra_ed.setText(address.getNotes());

    }

    @Override
    public void getEditAddress(AddressModel addressModel) {
        finish();
    }
}
