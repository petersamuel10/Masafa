package com.vavisa.masafah.tap_add.invoice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vavisa.masafah.R;
import com.vavisa.masafah.activities.MainActivity;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.tap_add.AddShipmentModel;
import com.vavisa.masafah.tap_add.add_shipment.model.NewShipmentModel;
import com.vavisa.masafah.util.Constants;

public class Invoice extends BaseActivity implements com.vavisa.masafah.tap_add.invoice.View {

    private TextView shipment_description, pickup_location, drop_location, time_from_txt, time_to_txt, total_txt;
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

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.addShipment();

            }
        });
    }

    private void initViews() {

        confirm_btn = findViewById(R.id.confirm_btn);
        shipment_description = findViewById(R.id.shipment_description);
        pickup_location = findViewById(R.id.pickup_location);
        drop_location = findViewById(R.id.drop_location);
        time_from_txt = findViewById(R.id.time_from);
        time_to_txt = findViewById(R.id.time_to);
    }

    private void bindData() {

        StringBuilder item_str = new StringBuilder();
        for (NewShipmentModel item : Constants.addShipmentModel.getShipmentList()) {
            item_str.append("\u25CF ").append(item.getQuantity()).append(" x   ").append(item.getCat_name()).append("\n");
        }

        shipment_description.setText(item_str.toString());

        pickup_location.setText(Constants.addShipmentModel.getPickup_address().getArea());
        drop_location.setText(Constants.addShipmentModel.getDrop_address().getArea());
        time_from_txt.setText(Constants.addShipmentModel.getPickup_time_from());
        time_to_txt.setText(Constants.addShipmentModel.getPickup_time_to());

    }

    @Override
    public void handleAddShipment() {

        Constants.addShipmentModel = new AddShipmentModel();
        startActivity(new Intent(Invoice.this, MainActivity.class));
        finish();

    }
}
