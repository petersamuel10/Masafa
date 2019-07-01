package com.vavisa.masafah.tap_add.add_shipment;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.tap_add.add_shipment.model.CategoryModel;
import com.vavisa.masafah.tap_add.add_shipment.model.NewShipmentModel;
import com.vavisa.masafah.tap_add.companies.CompaniesActivity;
import com.vavisa.masafah.util.BottomSpaceItemDecoration;
import com.vavisa.masafah.util.Connectivity;
import com.vavisa.masafah.util.Constants;

import java.util.ArrayList;
import java.util.Calendar;

public class AddShipmentActivity extends BaseActivity implements AddShipmentView {

    private RecyclerView shipmentList_rec;
    private NewShipmentAdapter adapter;
    private ArrayList<NewShipmentModel> shipmentsList;
    private Button nextButton;
    private AddShipmentPresenter presenter;
    private TextView pickup_time_from_txt, pickup_time_to_txt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_shipment);
        initViews();
        if (Connectivity.checkInternetConnection())
            presenter.getCategories();
        else
            showErrorConnection();

        pickup_time_to_txt.setOnClickListener(v -> showPickerTime((TextView) v));

        pickup_time_from_txt.setOnClickListener(v -> showPickerTime((TextView) v));

        nextButton.setOnClickListener(v -> {
            shipmentsList = adapter.getShipmentList();
            if (shipmentsList.get(shipmentsList.size() - 1).getCategory_id() == -1)
                Toast.makeText(this, getString(R.string.please_select_category_name_for_previous_shipment), Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(pickup_time_from_txt.getText().toString()))
                Toast.makeText(this, getString(R.string.please_select_pickup_time_from), Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(pickup_time_to_txt.getText().toString()))
                Toast.makeText(this, getString(R.string.please_select_pickup_time_to), Toast.LENGTH_SHORT).show();
            else {
                Constants.addShipmentModel.setShipmentList(shipmentsList);
                Constants.addShipmentModel.setPickup_time_from(pickup_time_from_txt.getText().toString());
                Constants.addShipmentModel.setPickup_time_to(pickup_time_to_txt.getText().toString());
                startActivity(new Intent(AddShipmentActivity.this, CompaniesActivity.class));
            }
        });
    }

    private void showPickerTime(TextView time_txt) {

        Calendar mCurrentTime = Calendar.getInstance();
        int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mCurrentTime.get(Calendar.MINUTE);

        TimePickerDialog timePicker;
        timePicker = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {

            time_txt.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute1)+":00");
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
        pickup_time_to_txt = findViewById(R.id.time_to);
        pickup_time_from_txt = findViewById(R.id.time_from);
        nextButton = findViewById(R.id.next_button);


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

        adapter = new NewShipmentAdapter(categoryList);
        shipmentList_rec.setAdapter(adapter);
    }
}
