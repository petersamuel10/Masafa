package com.vavisa.masafah.tap_add.invoice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.vavisa.masafah.R;
import com.vavisa.masafah.activities.MainActivity;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.tap_add.AddShipmentModel;
import com.vavisa.masafah.tap_add.add_shipment.model.Shipment;
import com.vavisa.masafah.util.Connectivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InvoiceActivity extends BaseActivity implements InvoiceView {

    private TextView shipment_description,
            pickup_location, pickup_address, drop_address,
            time_from_txt, time_to_txt,
            total_txt;
    private Button confirm_btn;
    private InvoicePresenter presenter;
    private AddShipmentModel addShipmentModel;
    private String drop_address_str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        initViews();
        presenter = new InvoicePresenter();
        presenter.attachView(this);

        addShipmentModel = getIntent().getParcelableExtra("shipmentModel");
        getPrice();
        bindData();
        confirm_btn.setOnClickListener(v -> {
            if (Connectivity.checkInternetConnection()) {
                if (getIntent().hasExtra("isEdit"))
                    presenter.editShipment(addShipmentModel);
                else
                    presenter.addShipment(addShipmentModel);
            } else
                showErrorConnection();
        });
    }

    private void getPrice() {

        List<Integer> addressToList = new ArrayList<>();
        for (Shipment shipment : addShipmentModel.getShipmentList()) {
            addressToList.add(shipment.getAddress_to_id());
        }

        Set<Integer> set = new HashSet<>(addressToList);
        addressToList.clear();
        addressToList.addAll(set);

        AddressIDs addressIds = new AddressIDs(addShipmentModel.getAddress_from_id(), addressToList);
        presenter.getPrice(addressIds);
    }

    private void bindData() {

        StringBuilder item_str = new StringBuilder();

        Shipment item;
        for (int i = 0; i < addShipmentModel.getShipmentList().size(); i++) {

            item = addShipmentModel.getShipmentList().get(i);
            if (!item.getVisited()) {
                item_str.append("\u25CF ").append(item.getDrop_address().getGovernorate().getName()).append(", ").append(item.getDrop_address().getCity().getName()).append("\n");
                drop_address_str += ("\u25CF ").concat(item.getDrop_address().getGovernorate().getName()).concat(", ").concat(item.getDrop_address().getCity().getName()).concat("\n");

                for (int x = 0; x < addShipmentModel.getShipmentList().size(); x++) {
                    Shipment item1 = addShipmentModel.getShipmentList().get(x);
                    if (item.getAddress_to_id().equals(item1.getAddress_to_id())) {
                        item_str.append("\t\t\t\u25CF ").append(item1.getQuantity()).append(" x   ").append(item1.getCat_name()).append("\n");
                        item1.setVisited(true);
                    }
                }
            }
        }

        shipment_description.setText(item_str.toString());
        pickup_location.setText(addShipmentModel.getPickup_address().getCity().getName());
        String pickup_address_str = addShipmentModel.getPickup_address().getBlock() + " - "
                + addShipmentModel.getPickup_address().getStreet() + "\n"
                + addShipmentModel.getPickup_address().getBuilding() + " - "
                + addShipmentModel.getPickup_address().getMobile();

        pickup_address.setText(pickup_address_str);
        drop_address.setText(drop_address_str);
        time_from_txt.setText(addShipmentModel.getPickup_time_from());
        time_to_txt.setText(addShipmentModel.getPickup_time_to());
    }

    private void initViews() {

        confirm_btn = findViewById(R.id.confirm_btn);
        shipment_description = findViewById(R.id.shipment_description);
        pickup_location = findViewById(R.id.pickup_location);
        pickup_address = findViewById(R.id.pickup_address);
        drop_address = findViewById(R.id.drop_address);
        time_from_txt = findViewById(R.id.time_from);
        time_to_txt = findViewById(R.id.time_to);
        total_txt = findViewById(R.id.total_amount);
    }

    @Override
    public void handleAddShipment(String msg) {
        showMessage(msg);
        Intent i = new Intent(InvoiceActivity.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    @Override
    public void displayPrice(String price) {

        total_txt.setText(price.concat(getString(R.string.kd)));
    }
}