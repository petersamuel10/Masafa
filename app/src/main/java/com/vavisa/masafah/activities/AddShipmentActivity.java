package com.vavisa.masafah.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vavisa.masafah.R;
import com.vavisa.masafah.util.BottomSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class AddShipmentActivity extends BaseActivity {

  private RecyclerView shipmentList;
  private List<String> shipments;
  private ShipmentAdapter shipmentAdapter;
  private Button nextButton;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_new_shipment);

    shipmentList = findViewById(R.id.shipment_items);
    nextButton = findViewById(R.id.next_button);

    shipmentList.setLayoutManager(new LinearLayoutManager(this));

    shipments = new ArrayList<>();
    shipments.add("Test");

    shipmentList.addItemDecoration(new BottomSpaceItemDecoration(50));

    shipmentAdapter = new ShipmentAdapter();
    shipmentList.setAdapter(shipmentAdapter);

    nextButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            startActivity(new Intent(AddShipmentActivity.this, SelectLocationActivity.class));
          }
        });
  }

  private class ShipmentViewHolder extends RecyclerView.ViewHolder {

    FloatingActionButton addMoreItems;

    public ShipmentViewHolder(@NonNull View itemView) {
      super(itemView);

      // if (getItemViewType() == R.layout.add_new_item) {
      addMoreItems = itemView.findViewById(R.id.add_more_item);
      // }
    }
  }

  private class ShipmentAdapter extends RecyclerView.Adapter<ShipmentViewHolder> {

    @NonNull
    @Override
    public ShipmentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

      View itemView;

      if (i == R.layout.add_shipment_list_item) {
        itemView =
            LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.add_shipment_list_item, viewGroup, false);
      } else {
        itemView =
            LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.add_new_item, viewGroup, false);
      }

      return new ShipmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShipmentViewHolder shipmentViewHolder, int i) {

      if (i == shipments.size()) {
        shipmentViewHolder.addMoreItems.setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                shipments.add("Testing");
                notifyDataSetChanged();
              }
            });
      }
    }

    @Override
    public int getItemCount() {
      return shipments.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
      return (position == shipments.size())
          ? R.layout.add_new_item
          : R.layout.add_shipment_list_item;
    }
  }
}
