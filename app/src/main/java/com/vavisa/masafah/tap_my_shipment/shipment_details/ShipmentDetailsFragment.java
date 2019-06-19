package com.vavisa.masafah.tap_my_shipment.shipment_details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vavisa.masafah.R;
import com.vavisa.masafah.tap_my_shipment.company_details.CompanyDetailsActivity;
import com.vavisa.masafah.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import static com.vavisa.masafah.activities.MainActivity.navigationView;

public class ShipmentDetailsFragment extends BaseFragment {

  private View fragment;
  private List<Integer> images = new ArrayList<>();
  private TextView deliveryCompanyName;

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    if (fragment == null) {
      fragment = inflater.inflate(R.layout.fragment_shipment_details, container, false);

      deliveryCompanyName = fragment.findViewById(R.id.delivery_company_name);

      RecyclerView.LayoutManager layoutManager =
          new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

    } else {
      for (int i = 1; i < navigationView.getMenu().size(); i++) {
        navigationView.getMenu().getItem(i).setChecked(false);
      }
      navigationView.getMenu().getItem(0).setChecked(true);
    }

    images.add(R.drawable.sofa_single);
    images.add(R.drawable.flooar_mat);
    images.add(R.drawable.fridge);


    deliveryCompanyName.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            startActivity(new Intent(getActivity(), CompanyDetailsActivity.class));
          }
        });

    return fragment;
  }

}
