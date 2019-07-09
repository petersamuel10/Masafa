package com.vavisa.masafah.tap_add.add_shipment;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.tap_add.Select_location.SelectLocationActivity;
import com.vavisa.masafah.tap_add.add_shipment.model.CategoryModel;
import com.vavisa.masafah.tap_add.add_shipment.model.ShipmentItemModel;
import com.vavisa.masafah.tap_add.companies.CompaniesActivity;
import com.vavisa.masafah.tap_my_shipment.my_shipments.ShipmentModel;
import com.vavisa.masafah.util.BottomSpaceItemDecoration;
import com.vavisa.masafah.util.Connectivity;
import com.vavisa.masafah.util.Constants;

import java.util.ArrayList;
import java.util.Calendar;

public class AddShipmentActivity extends BaseActivity implements AddShipmentView {

    private RecyclerView shipmentList_rec;
    private RadioGroup rg;
    private NewShipmentAdapter adapter;
    private ArrayList<ShipmentItemModel> shipmentsList;
    private Button nextButton;
    private AddShipmentPresenter presenter;
    private TextView pickup_time_from_txt, pickup_time_to_txt;
    private Boolean isToday_picker = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_shipment);
        initViews();
        if(Connectivity.checkInternetConnection())
            presenter.getCategories();
        else{
            showErrorConnection();
            nextButton.setEnabled(false);
        }

        pickup_time_to_txt.setOnClickListener(v -> showPickerTime((TextView) v));

        pickup_time_from_txt.setOnClickListener(v -> showPickerTime((TextView) v));

        nextButton.setOnClickListener(v -> {
            shipmentsList = adapter.getShipmentList();
            if (shipmentsList.get(shipmentsList.size() - 1).getCategory_id() == -1)
                Toast.makeText(this, getString(R.string.please_select_category_name_for_previous_shipment), Toast.LENGTH_SHORT).show();
            else if (!isToday_picker) {
                if (TextUtils.isEmpty(pickup_time_from_txt.getText().toString()))
                    Toast.makeText(this, getString(R.string.please_select_pickup_time_from), Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(pickup_time_to_txt.getText().toString()))
                    Toast.makeText(this, getString(R.string.please_select_pickup_time_to), Toast.LENGTH_SHORT).show();
                else
                    selectCompanies();
            } else
                selectCompanies();
        });
    }

    private void selectCompanies() {

        Constants.addShipmentModel.setShipmentList(shipmentsList);
        Constants.addShipmentModel.setIs_today(isToday_picker);
        Constants.addShipmentModel.setPickup_time_from(pickup_time_from_txt.getText().toString());
        Constants.addShipmentModel.setPickup_time_to(pickup_time_to_txt.getText().toString());
        if (getIntent().hasExtra("edit_shipment"))
            startActivity(new Intent(AddShipmentActivity.this, SelectLocationActivity.class));
        else
            startActivity(new Intent(AddShipmentActivity.this, CompaniesActivity.class));
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

        shipmentList_rec = findViewById(R.id.shipment_items);
        shipmentList_rec.addItemDecoration(new BottomSpaceItemDecoration(50));
        rg = findViewById(R.id.time_rg);
        pickup_time_to_txt = findViewById(R.id.time_to);
        pickup_time_from_txt = findViewById(R.id.time_from);
        nextButton = findViewById(R.id.next_button);

        rg.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.pickup_time_rb) {
                isToday_picker = false;

                pickup_time_from_txt.setVisibility(View.VISIBLE);
                pickup_time_to_txt.setVisibility(View.VISIBLE);
            } else {
                isToday_picker = true;
                pickup_time_from_txt.setVisibility(View.GONE);
                pickup_time_to_txt.setVisibility(View.GONE);
            }
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
            Constants.addShipmentModel.setShipment_id(shipmentModel.getId());
            Constants.addShipmentModel.setAddress_from_id(shipmentModel.getAddress_from().getId());
            Constants.addShipmentModel.setPickup_address(shipmentModel.getAddress_from());
            Constants.addShipmentModel.setAddress_to_id(shipmentModel.getAddress_to().getId());
            Constants.addShipmentModel.setDrop_address(shipmentModel.getAddress_to());
        } else
            shipmentsList.add(new ShipmentItemModel("", -1, 1));

        adapter = new NewShipmentAdapter(categoryList, shipmentsList);
        shipmentList_rec.setAdapter(adapter);
    }
}
