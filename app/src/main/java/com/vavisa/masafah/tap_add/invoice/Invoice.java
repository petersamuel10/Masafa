package com.vavisa.masafah.tap_add.invoice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.vavisa.masafah.R;
import com.vavisa.masafah.activities.MainActivity;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.tap_add.AddShipmentModel;
import com.vavisa.masafah.tap_add.add_shipment.model.ShipmentItemModel;
import com.vavisa.masafah.util.Constants;

public class Invoice extends BaseActivity implements com.vavisa.masafah.tap_add.invoice.View {

    private TextView shipment_description,
            pickup_location, pickup_address,
            drop_location, drop_address,
            time_from_txt, time_to_txt,
            total_txt;
    private Button confirm_btn;
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        initViews();

        presenter = new Presenter();
        presenter.attachView(this);

        bindData();

        confirm_btn.setOnClickListener(v -> {
            if (Constants.isEditShipment)
                presenter.editShipment();
            else
                presenter.addShipment();
        });
    }

    private void initViews() {

        confirm_btn = findViewById(R.id.confirm_btn);
        shipment_description = findViewById(R.id.shipment_description);
        pickup_location = findViewById(R.id.pickup_location);
        pickup_address = findViewById(R.id.pickup_address);
        drop_location = findViewById(R.id.drop_location);
        drop_address = findViewById(R.id.drop_address);
        time_from_txt = findViewById(R.id.time_from);
        time_to_txt = findViewById(R.id.time_to);
        total_txt = findViewById(R.id.total_amount);
    }

    private void bindData() {

        StringBuilder item_str = new StringBuilder();
        for (ShipmentItemModel item : Constants.addShipmentModel.getShipmentList()) {
            item_str.append("\u25CF ").append(item.getQuantity()).append(" x   ").append(item.getCat_name()).append("\n");
        }

        shipment_description.setText(item_str.toString());
        pickup_location.setText(Constants.addShipmentModel.getPickup_address().getArea());
        String pickup_address_str = Constants.addShipmentModel.getPickup_address().getBlock() + " - "
                + Constants.addShipmentModel.getPickup_address().getStreet() + "\n"
                + Constants.addShipmentModel.getPickup_address().getBuilding() + " - "
                + Constants.addShipmentModel.getPickup_address().getMobile();

        pickup_address.setText(pickup_address_str);
        drop_location.setText(Constants.addShipmentModel.getDrop_address().getArea());
        String drop_address_str = Constants.addShipmentModel.getDrop_address().getBlock() + " - "
                + Constants.addShipmentModel.getDrop_address().getStreet() + "\n"
                + Constants.addShipmentModel.getDrop_address().getBuilding() + " - "
                + Constants.addShipmentModel.getDrop_address().getMobile();
        drop_address.setText(drop_address_str);
        if (Constants.addShipmentModel.getIs_today()) {
            time_from_txt.setVisibility(android.view.View.GONE);
            time_to_txt.setVisibility(android.view.View.GONE);
        } else {
            time_from_txt.setText(Constants.addShipmentModel.getPickup_time_from());
            time_to_txt.setText(Constants.addShipmentModel.getPickup_time_to());
        }
        total_txt.setText(Constants.addShipmentModel.getPrice());

    }

    @Override
    public void handleAddShipment(String msg) {
        Constants.isEditShipment = false;
        Constants.addShipmentModel = new AddShipmentModel();
        showMessage(msg);
        startActivity(new Intent(Invoice.this, MainActivity.class));
        finish();
    }
}
