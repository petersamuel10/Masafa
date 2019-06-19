package com.vavisa.masafah.tap_add;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.tap_add.companies.Companies;
import com.vavisa.masafah.util.BottomSpaceItemDecoration;

import java.util.ArrayList;

public class AddShipmentActivity extends BaseActivity {

  private RecyclerView shipmentList_rec;
  private ArrayList<NewShipmentModel> shipmentList;
  private NewShipmentAdapter adapter;
  private Button nextButton;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_new_shipment);

    shipmentList_rec = findViewById(R.id.shipment_items);
    nextButton = findViewById(R.id.next_button);

    shipmentList_rec.setLayoutManager(new LinearLayoutManager(this));

    shipmentList = new ArrayList<>();
    shipmentList.add(new NewShipmentModel("",1));

    shipmentList_rec.addItemDecoration(new BottomSpaceItemDecoration(50));

    adapter = new NewShipmentAdapter(shipmentList);
    shipmentList_rec.setAdapter(adapter);

    nextButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            startActivity(new Intent(AddShipmentActivity.this, Companies.class));
          }
        });
  }
}
