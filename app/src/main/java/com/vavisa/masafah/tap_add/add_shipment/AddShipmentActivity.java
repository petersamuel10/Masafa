package com.vavisa.masafah.tap_add.add_shipment;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.tap_add.AddShipmentModel;
import com.vavisa.masafah.tap_add.add_address.AddAddressActivity;
import com.vavisa.masafah.tap_add.add_address.AddressModel;
import com.vavisa.masafah.tap_add.add_shipment.model.CategoryModel;
import com.vavisa.masafah.tap_add.add_shipment.model.ShipmentItemModel;
import com.vavisa.masafah.tap_add.companies.CompaniesActivity;
import com.vavisa.masafah.tap_add.invoice.InvoiceActivity;
import com.vavisa.masafah.tap_add.map.MyAddressActivity;
import com.vavisa.masafah.tap_my_shipment.my_shipments.ShipmentModel;
import com.vavisa.masafah.util.BottomSpaceItemDecoration;
import com.vavisa.masafah.util.Connectivity;

import java.util.ArrayList;
import java.util.Calendar;

public class AddShipmentActivity extends BaseActivity implements AddShipmentView {

    AddShipmentModel addShipmentModel;
    private RecyclerView shipmentList_rec;
    private RadioGroup rg;
    private NewShipmentAdapter adapter;
    private ArrayList<ShipmentItemModel> shipmentsList;
    private Button nextButton;
    private AddShipmentPresenter presenter;
    private TextView pickupAddress, pickup_time_from_txt, pickup_time_to_txt;
    private Boolean isToday_picker = true;
    private final int PICKUP_ADDRESS = 4;
    private final int Drop_ADDRESS = 5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_shipment);
        addShipmentModel = new AddShipmentModel();

        initViews();
        if (Connectivity.checkInternetConnection())
            presenter.getCategories();
        else {
            showErrorConnection();
            nextButton.setEnabled(false);
        }

        pickupAddress.setOnClickListener(v -> showPopAddressWay());
        pickup_time_to_txt.setOnClickListener(v -> showPickerTime((TextView) v));
        pickup_time_from_txt.setOnClickListener(v -> showPickerTime((TextView) v));
        nextButton.setOnClickListener(v -> {
            shipmentsList = adapter.getShipmentList();
            if (TextUtils.isEmpty(pickupAddress.getText().toString()))
                Toast.makeText(this, getString(R.string.please_select_pickup_address), Toast.LENGTH_SHORT).show();
            else if (shipmentsList.get(shipmentsList.size() - 1).getCategory_id() == -1)
                Toast.makeText(this, getString(R.string.please_select_drop_address_and_category_name_for_previous_shipment), Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(pickup_time_from_txt.getText().toString()))
                Toast.makeText(this, getString(R.string.please_select_pickup_time_from), Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(pickup_time_to_txt.getText().toString()))
                Toast.makeText(this, getString(R.string.please_select_pickup_time_to), Toast.LENGTH_SHORT).show();
            else
                selectCompanies();
        });
    }

    private void showPopAddressWay() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.select_location));
        String[] address_ways_items = new String[]{getString(R.string.add_new_address), getString(R.string.my_address)};
        builder.setItems(address_ways_items, (dialog, position) -> {
            Intent intent = null;
            if (position == 0)
                intent = new Intent(this, AddAddressActivity.class);
            else
                intent = new Intent(new Intent(this, MyAddressActivity.class));

            startActivityForResult(intent, PICKUP_ADDRESS);
        });

        builder.create().show();
    }

    private void selectCompanies() {

        addShipmentModel.setShipmentList(shipmentsList);
        addShipmentModel.setIs_today(isToday_picker);
        addShipmentModel.setPickup_time_from(pickup_time_from_txt.getText().toString());
        addShipmentModel.setPickup_time_to(pickup_time_to_txt.getText().toString());
        if (getIntent().hasExtra("edit_shipment"))
            startActivity(new Intent(AddShipmentActivity.this, InvoiceActivity.class));
        else {
            Intent companiesIntent = new Intent(AddShipmentActivity.this, CompaniesActivity.class);
            companiesIntent.putExtra("shipmentModel", addShipmentModel);
            startActivity(companiesIntent);
        }
    }

    private void showPickerTime(TextView time_txt) {

        Calendar mCurrentTime = Calendar.getInstance();
        int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mCurrentTime.get(Calendar.MINUTE);

        TimePickerDialog timePicker;
        timePicker = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {

            time_txt.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute1) + ":00");
        }, hour, minute, true);

        timePicker.show();

    }

    private void initViews() {

        Toolbar toolbar = findViewById(R.id.add_shipment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        pickupAddress = findViewById(R.id.pickup_address);
        shipmentList_rec = findViewById(R.id.shipment_items);
        shipmentList_rec.addItemDecoration(new BottomSpaceItemDecoration(50));
        rg = findViewById(R.id.time_rg);
        pickup_time_to_txt = findViewById(R.id.time_to);
        pickup_time_from_txt = findViewById(R.id.time_from);
        nextButton = findViewById(R.id.next_button);
        rg.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.pickup_time_rb)
                isToday_picker = false;
            else
                isToday_picker = true;
        });
        presenter = new AddShipmentPresenter();
        presenter.attachView(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void categories(ArrayList<CategoryModel> categoryList) {

        shipmentsList = new ArrayList<>();
        if (getIntent().hasExtra("edit_shipment")) {
            ShipmentModel shipmentModel = (ShipmentModel) getIntent().getParcelableExtra("edit_shipment");
            shipmentsList = shipmentModel.getItems();
            pickup_time_from_txt.setText(shipmentModel.getPickup_time_from());
            pickup_time_to_txt.setText(shipmentModel.getPickup_time_to());
//            Constants.addShipmentModel.setShipment_id(shipmentModel.getId());
//            Constants.addShipmentModel.setAddress_from_id(shipmentModel.getAddress_from().getId());
//            Constants.addShipmentModel.setPickup_address(shipmentModel.getAddress_from());
//            // Constants.addShipmentModel.setAddress_to_id(shipmentModel.getAddress_to().getId());
//            Constants.addShipmentModel.setDrop_address(shipmentModel.getAddress_to());
        } else
            shipmentsList.add(new ShipmentItemModel(-1, "", 1, -1));

        adapter = new NewShipmentAdapter(categoryList, shipmentsList);
        shipmentList_rec.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK)
            if (requestCode == PICKUP_ADDRESS) {
                AddressModel address = data.getParcelableExtra("selectedAddress");
                pickupAddress.setText(address.getGovernorate().getName().concat(", ").concat(address.getCity().getName()));
                addShipmentModel.setPickup_address(address);
                addShipmentModel.setAddress_from_id(Integer.valueOf(address.getId()));
            } else if (requestCode == Drop_ADDRESS)
                adapter.onActivityResult(requestCode, resultCode, data);
    }
}
