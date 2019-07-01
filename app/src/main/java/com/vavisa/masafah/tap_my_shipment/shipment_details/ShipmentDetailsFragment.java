package com.vavisa.masafah.tap_my_shipment.shipment_details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseFragment;
import com.vavisa.masafah.tap_my_shipment.company_details.CompanyDetailsActivity;
import com.vavisa.masafah.tap_my_shipment.my_shipments.Items;
import com.vavisa.masafah.tap_my_shipment.my_shipments.ShipmentModel;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.vavisa.masafah.activities.MainActivity.navigationView;

public class ShipmentDetailsFragment extends BaseFragment implements ShipmentDetailsViews {

    private View fragment;
    private RelativeLayout shipment_item_ly;
    private TextView shipment_num, shipment_description,
            pickup_location, drop_location,
            deliveryCompanyName, time_pickup,
            time_drop, total_amount;
    private CircleImageView com_img;
    private ShipmentDetailsPresenter presenter;
    private String com_id;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        if (fragment == null) {
            fragment = inflater.inflate(R.layout.fragment_shipment_details, container, false);
            initViews();

            presenter = new ShipmentDetailsPresenter();
            presenter.attachView(this);
            presenter.getShipmentDetails(getArguments().getString("shipment_id"));

        } else {
            for (int i = 1; i < navigationView.getMenu().size(); i++) {
                navigationView.getMenu().getItem(i).setChecked(false);
            }
            navigationView.getMenu().getItem(0).setChecked(true);
        }
        deliveryCompanyName.setOnClickListener(
                v -> {
                    Intent intent = new Intent(getActivity(), CompanyDetailsActivity.class);
                    intent.putExtra("company_id", com_id);
                    startActivity(intent);
                });

        return fragment;
    }

    private void initViews() {

        Toolbar toolbar = fragment.findViewById(R.id.shipment_details_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        shipment_item_ly = fragment.findViewById(R.id.shipment_item);
        shipment_num = fragment.findViewById(R.id.shipment_number);
        shipment_description = fragment.findViewById(R.id.shipment_description);
        pickup_location = fragment.findViewById(R.id.pickup_location);
        drop_location = fragment.findViewById(R.id.drop_location_area);
        deliveryCompanyName = fragment.findViewById(R.id.delivery_company_name);
        time_pickup = fragment.findViewById(R.id.time_pickup);
        time_drop = fragment.findViewById(R.id.time_drop);
        total_amount = fragment.findViewById(R.id.total_amount);
    }

    @Override
    public void displayShipmentDetails(ShipmentModel shipmentModel) {

        com_id = String.valueOf(shipmentModel.getCompany().getId());
        shipment_num.setText(shipmentModel.getId());
        pickup_location.setText(shipmentModel.getAddress_from().getArea());
        drop_location.setText(shipmentModel.getAddress_to().getArea());
        deliveryCompanyName.setText(shipmentModel.getCompany().getName());
        com_img = fragment.findViewById(R.id.delivery_company_logo);
        time_pickup.setText(shipmentModel.getPickup_time_from());
        time_drop.setText(shipmentModel.getPickup_time_to());
        total_amount.setText(shipmentModel.getPrice() + " " + getString(R.string.kd));

        StringBuilder item_str = new StringBuilder();
        for (Items item : shipmentModel.getItems()) {
            item_str.append("\u25CF ").append(item.getQuantity()).append(" x   ").append(item.getCategory_name()).append("\n");
        }
        shipment_description.setText(item_str.toString());

        Glide.with(getContext()).load(shipmentModel.getCompany().getImage()).into(com_img);

        shipment_item_ly.setVisibility(View.VISIBLE);

    }
}
