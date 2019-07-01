package com.vavisa.masafah.tap_my_shipment.my_shipments;

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
import android.widget.RelativeLayout;

import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;

import static com.vavisa.masafah.activities.MainActivity.navigationView;

public class MyShipmentsFragment extends BaseFragment implements MyShipmentsView, View.OnClickListener {

    private View fragment;
    private ConstraintLayout buttonLayout;
    private RecyclerView myShipmentListView;
    private ArrayList<ShipmentModel> pendingList, acceptList;
    private Button pendingButton;
    private Button acceptedButton;
    private MyShipmentPresenter presenter;
    private MyShipmentsPendingAdapter adapter;

    @Override
    public void onResume() {
        super.onResume();
    }


    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (fragment == null) {
            fragment = inflater.inflate(R.layout.fragment_my_shipments, container, false);
            Toolbar toolbar = fragment.findViewById(R.id.my_shipments_toolbar);
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            getActivity().setTitle("");
            initViews();

            presenter = new MyShipmentPresenter();
            presenter.attachView(this);
            presenter.getShipment();


        } else {
            for (int i = 1; i < navigationView.getMenu().size(); i++) {
                navigationView.getMenu().getItem(i).setChecked(false);
            }
            navigationView.getMenu().getItem(0).setChecked(true);
        }

        return fragment;
    }

    private void initViews() {
        buttonLayout = fragment.findViewById(R.id.profile_layout);
        myShipmentListView = fragment.findViewById(R.id.my_shipment_list);

        pendingButton = buttonLayout.findViewById(R.id.pending_button);
        acceptedButton = buttonLayout.findViewById(R.id.accepted_button);

        pendingButton.setOnClickListener(this);
        acceptedButton.setOnClickListener(this);

        myShipmentListView.setLayoutManager(new LinearLayoutManager(getActivity()));

        buttonLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        int height = buttonLayout.getHeight();
                        RelativeLayout.LayoutParams layoutParams =
                                (RelativeLayout.LayoutParams) buttonLayout.getLayoutParams();
                        layoutParams.topMargin = -(height / 2);
                        buttonLayout.setLayoutParams(layoutParams);
                    }
                });
    }

    public void deleteShipmentById(int position, String shipment_id) {

        presenter.deleteShipment(position, shipment_id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pending_button:
                pendingButton.setBackground(
                        getResources().getDrawable(R.drawable.button_rounded_corners_primary_filled));
                pendingButton.setTextColor(getResources().getColor(android.R.color.white));
                acceptedButton.setBackground(null);
                acceptedButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                myShipmentListView.setAdapter(new MyShipmentsPendingAdapter(this, pendingList));
                break;

            case R.id.accepted_button:
                acceptedButton.setBackground(
                        getResources().getDrawable(R.drawable.button_rounded_corners_primary_filled));
                pendingButton.setBackground(null);
                acceptedButton.setTextColor(getResources().getColor(android.R.color.white));
                pendingButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                myShipmentListView.setAdapter(new MyShipmentsAcceptedAdapter(this, acceptList));
                break;
        }
    }

    @Override
    public void displayShipments(HashMap<String, ArrayList<ShipmentModel>> shipmentList) {
        pendingList = shipmentList.get("pending");
        acceptList = shipmentList.get("accepted");
        adapter = new MyShipmentsPendingAdapter(this, pendingList);
        myShipmentListView.setAdapter(adapter);

    }

    @Override
    public void deleteShipmentRes(int position) {
        adapter.deleteShipmentFromRecycler(position);

    }

}
