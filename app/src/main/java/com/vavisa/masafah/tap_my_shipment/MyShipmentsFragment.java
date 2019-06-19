package com.vavisa.masafah.tap_my_shipment;

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
import com.vavisa.masafah.util.BottomSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static com.vavisa.masafah.activities.MainActivity.navigationView;

public class MyShipmentsFragment extends BaseFragment implements View.OnClickListener {

    private View fragment;
    private ConstraintLayout buttonLayout;
    private RecyclerView myShipmentListView;
    private List<String> myShipments = new ArrayList<>();
    private Button pendingButton;
    private Button acceptedButton;

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

        if (fragment == null) {
            fragment = inflater.inflate(R.layout.fragment_my_shipments, container, false);
            Toolbar toolbar = fragment.findViewById(R.id.my_shipments_toolbar);
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            getActivity().setTitle("");

            initViews();
            setupRecycler();

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

    private void setupRecycler() {

        myShipmentListView.setLayoutManager(new LinearLayoutManager(getActivity()));

        myShipments.add("Test");

        myShipmentListView.setAdapter(new MyShipmentsPendingAdapter(this));

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
                myShipmentListView.setAdapter(new MyShipmentsPendingAdapter(this));
                break;

            case R.id.accepted_button:
                acceptedButton.setBackground(
                        getResources().getDrawable(R.drawable.button_rounded_corners_primary_filled));
                pendingButton.setBackground(null);
                acceptedButton.setTextColor(getResources().getColor(android.R.color.white));
                pendingButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                myShipmentListView.setAdapter(new MyShipmentsAcceptedAdapter(this));
                break;
        }
    }

}
