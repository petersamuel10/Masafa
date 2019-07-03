package com.vavisa.masafah.tap_profile.shipment_history;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseFragment;
import com.vavisa.masafah.tap_my_shipment.my_shipments.ShipmentModel;
import com.vavisa.masafah.util.BottomSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static com.vavisa.masafah.activities.MainActivity.navigationView;

public class ShipmentHistoryFragment extends BaseFragment implements ShipmentHistoryView {

    private View fragment;
  //  private ConstraintLayout buttonLayout;
    private RecyclerView myShipmentListView;
    private ImageView noShipmentsImage;
    private TextView noShipmentsMessage;
   /* private Button deliveredButton;
    private Button canceledButton;*/
    private ShipmentHistoryPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        if (fragment == null) {
            fragment = inflater.inflate(R.layout.fragment_shipment_history, container, false);
            initViews();

            presenter = new ShipmentHistoryPresenter();
            presenter.attachView(this);
            presenter.getShipments();

        } else {
            for (int i = 1; i < navigationView.getMenu().size(); i++) {
                navigationView.getMenu().getItem(i).setChecked(false);
            }
            navigationView.getMenu().getItem(1).setChecked(true);
        }

        return fragment;
    }

    private void initViews() {

        Toolbar toolbar = fragment.findViewById(R.id.shipment_history_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

      //  buttonLayout = fragment.findViewById(R.id.profile_layout);
        myShipmentListView = fragment.findViewById(R.id.my_shipments_list);
        noShipmentsImage = fragment.findViewById(R.id.no_shipments_image);
        noShipmentsMessage = fragment.findViewById(R.id.no_shipments_message);

    //    deliveredButton = buttonLayout.findViewById(R.id.delivered_button);
   //     canceledButton = buttonLayout.findViewById(R.id.canceled_button);

   //     deliveredButton.setOnClickListener(this);
   //     canceledButton.setOnClickListener(this);

        myShipmentListView.setLayoutManager(new LinearLayoutManager(getActivity()));

        myShipmentListView.addItemDecoration(new BottomSpaceItemDecoration(50));

   /*     buttonLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        int height = buttonLayout.getHeight();
                        buttonLayout.setTranslationY(-(height / 2));
                    }
                });*/

    }

   /* @Override
    public void onClick(View v) {
     *//*   switch (v.getId()) {
            case R.id.delivered_button:

                deliveredButton.setBackground(
                        getResources().getDrawable(R.drawable.button_rounded_corners_primary_filled));
                deliveredButton.setTextColor(getResources().getColor(android.R.color.white));
                canceledButton.setBackground(null);
                canceledButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                myShipmentListView.setAdapter(new MyShipmentsDeliveredAdapter());
                break;

            case R.id.canceled_button:
                canceledButton.setBackground(
                        getResources().getDrawable(R.drawable.button_rounded_corners_primary_filled));
                deliveredButton.setBackground(null);
                canceledButton.setTextColor(getResources().getColor(android.R.color.white));
                deliveredButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                myShipmentListView.setAdapter(new MyShipmentsCanceledAdapter());
                break;
        }*//*
    }*/

    @Override
    public void displayShipment(ArrayList<ShipmentModel> shipmentList) {

        if (shipmentList.size() == 0) {
            noShipmentsMessage.setVisibility(View.VISIBLE);
            noShipmentsImage.setVisibility(View.VISIBLE);
        }else
            myShipmentListView.setAdapter(new MyShipmentsDeliveredAdapter(shipmentList));

    }
}
