package com.vavisa.masafah.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vavisa.masafah.R;
import com.vavisa.masafah.util.BottomSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MyShipmentsFragment extends BaseFragment {

  private View fragment;
  private ConstraintLayout buttonLayout;
  private RecyclerView myShipmentListView;
  private List<String> myShipments = new ArrayList<>();

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    fragment = inflater.inflate(R.layout.fragment_my_shipments, container, false);

    Toolbar toolbar = fragment.findViewById(R.id.add_shipment_toolbar);
    ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

    buttonLayout = fragment.findViewById(R.id.buttons_layout);
    myShipmentListView = fragment.findViewById(R.id.my_shipment_list);

    myShipmentListView.setLayoutManager(new LinearLayoutManager(getActivity()));

    myShipments.add("Test");

    myShipmentListView.setAdapter(new MyShipmentsAdapter());

    myShipmentListView.addItemDecoration(new BottomSpaceItemDecoration(50));

    buttonLayout.post(
        new Runnable() {
          @Override
          public void run() {
            int height = buttonLayout.getHeight();
            buttonLayout.setTranslationY(-(height / 2));
          }
        });
    return fragment;
  }

  private class MyShipmentsViewHolder extends RecyclerView.ViewHolder {

    public MyShipmentsViewHolder(@NonNull View itemView) {
      super(itemView);
    }
  }

  private class MyShipmentsAdapter extends RecyclerView.Adapter<MyShipmentsViewHolder> {

    @NonNull
    @Override
    public MyShipmentsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View v =
          LayoutInflater.from(viewGroup.getContext())
              .inflate(R.layout.my_shipment_list_item, viewGroup, false);

      return new MyShipmentsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyShipmentsViewHolder myShipmentsViewHolder, int i) {}

    @Override
    public int getItemCount() {
      return 2;
    }
  }
}
