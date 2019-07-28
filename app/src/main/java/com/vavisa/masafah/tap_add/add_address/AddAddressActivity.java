package com.vavisa.masafah.tap_add.add_address;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.helpers.OTP.CountryModel;
import com.vavisa.masafah.util.Connectivity;
import com.vavisa.masafah.util.Constants;
import com.vavisa.masafah.util.Preferences;

import java.util.ArrayList;
import java.util.List;

public class AddAddressActivity extends BaseActivity implements AddAddressView, AdapterView.OnItemSelectedListener {

    private Button confirmButton;
    private Spinner govern_sp, city_sp;
    private EditText location_name_ed, mobile_ed, block_ed, street_ed, building_ed, details_ed, extra_ed;
    private String location_name, mobile, country_id, city_id, block, govern_id, street, building, details, extra;
    private AddAddressPresenter presenter;
    private String address_id;
    private ArrayList<CountryModel> governorateList, citiesList;
    private ArrayAdapter<String> governAdapter, cityAdapter;
    private boolean isEditORDisplayAddress;
    private AddressModel currentAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        initViews();
        country_id = Preferences.getInstance().getString("country_id");
        presenter.getGovernorate(country_id);
        isEditORDisplayAddress = getIntent().getExtras().containsKey("address_id")? true : false;
        if (isEditORDisplayAddress) {
            if (getIntent().getStringExtra("action").equals("showAddressDetails"))
                disableViewsForDetails();
            if (Connectivity.checkInternetConnection())
                presenter.getAddressById(getIntent().getStringExtra("address_id"));
            else
                showErrorConnection();
            confirmButton.setOnClickListener(v -> {
                if (valid()) {
                    AddressModel addressModel =
                            new AddressModel(address_id, location_name, mobile,
                                    country_id, govern_id, city_id, block, street, building, details, extra);
                    addressModel.setMobile(mobile);
                    if (Connectivity.checkInternetConnection())
                        presenter.editAddress(addressModel);
                    else
                        showErrorConnection();
                }
            });
        } else
            confirmButton.setOnClickListener(v -> {
                if (valid()) {
                    AddressModel addressModel =
                            new AddressModel(location_name, mobile, country_id, govern_id, city_id, block, street, building, details, extra);
                    addressModel.setMobile(mobile);
                    if (Connectivity.checkInternetConnection())
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
        govern_sp.setEnabled(false);
        city_sp.setEnabled(false);
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

      /*  if (TextUtils.isEmpty(area_ed.getText().toString())) {
            showMessage(getString(R.string.please_enter_area));
            return false;
        } else
            area = area_ed.getText().toString();*/

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
        govern_sp = findViewById(R.id.area_spinner);
        city_sp = findViewById(R.id.city_spinner);
        block_ed = findViewById(R.id.block);
        street_ed = findViewById(R.id.street);
        building_ed = findViewById(R.id.building);
        details_ed = findViewById(R.id.details);
        extra_ed = findViewById(R.id.extra);

        govern_sp.setOnItemSelectedListener(this);
        city_sp.setOnItemSelectedListener(this);

        confirmButton = findViewById(R.id.confirm_button);

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

        this.currentAddress = address;
        address_id = address.getId();
        location_name_ed.setText(address.getName());
        mobile_ed.setText(address.getMobile());
        block_ed.setText(address.getBlock());
        street_ed.setText(address.getStreet());
        building_ed.setText(address.getBuilding());
        details_ed.setText(address.getDetails());
        extra_ed.setText(address.getNotes());
        if(isEditORDisplayAddress)
            govern_sp.setSelection(governAdapter.getPosition(address.getGovernorate().getName()));
    }

    @Override
    public void getEditAddress(AddressModel addressModel) {
        finish();
    }

    @Override
    public void displayGovernorate(ArrayList<CountryModel> governorateList) {
        this.governorateList = governorateList;
        List<String> governorateListNames = new ArrayList<>();
        for (CountryModel governorate : governorateList) {
            governorateListNames.add(governorate.getName());
        }
        governAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_dropdown_item, governorateListNames);
        govern_sp.setAdapter(governAdapter);
    }

    @Override
    public void displayCities(ArrayList<CountryModel> citiesList) {
        this.citiesList = citiesList;
        List<String> citiesListNames = new ArrayList<>();
        for (CountryModel city : citiesList) {
            citiesListNames.add(city.getName());
        }
        cityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, citiesListNames);
        city_sp.setAdapter(cityAdapter);
        if(isEditORDisplayAddress)
            city_sp.setSelection(cityAdapter.getPosition(currentAddress.getCity().getName()));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.area_spinner:
                govern_id = governorateList.get(position).getId();
                presenter.getCities(govern_id);
                break;
            case R.id.city_spinner:
                city_id = citiesList.get(position).getId();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
