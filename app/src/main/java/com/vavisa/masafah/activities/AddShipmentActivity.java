package com.vavisa.masafah.activities;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vavisa.masafah.R;
import com.vavisa.masafah.util.BottomSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class AddShipmentActivity extends BaseActivity {

  private RecyclerView shipmentList;
  private List<String> shipments;
  private ShipmentAdapter shipmentAdapter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_new_shipment);

    shipmentList = findViewById(R.id.shipment_items);

    FloatingActionButton addMoreItem = findViewById(R.id.add_more_item);

    addMoreItem.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            shipments.add("Testing");
            shipmentAdapter.notifyDataSetChanged();
          }
        });

    shipmentList.setLayoutManager(new LinearLayoutManager(this));

    shipments = new ArrayList<>();
    shipments.add("Test");

    shipmentList.addItemDecoration(new BottomSpaceItemDecoration(50));

    shipmentAdapter = new ShipmentAdapter();
    shipmentList.setAdapter(shipmentAdapter);
  }

  private class ShipmentViewHolder extends RecyclerView.ViewHolder {

    public ShipmentViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }

  private class ShipmentAdapter extends RecyclerView.Adapter<ShipmentViewHolder> {

    @NonNull
    @Override
    public ShipmentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

      View v =
          LayoutInflater.from(viewGroup.getContext())
              .inflate(R.layout.shipment_list_item, viewGroup, false);
      return new ShipmentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ShipmentViewHolder shipmentViewHolder, int i) {}

    @Override
    public int getItemCount() {
      return shipments.size();
    }

    @Override
    public int getItemViewType(int position) {
      return super.getItemViewType(position);
    }
  }
}
