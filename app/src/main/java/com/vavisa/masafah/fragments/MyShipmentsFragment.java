package com.vavisa.masafah.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Button;

import com.vavisa.masafah.R;
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
      Toolbar toolbar = fragment.findViewById(R.id.add_shipment_toolbar);
      ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

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
              buttonLayout.setTranslationY(-(height / 2));
            }
          });
    } else {

      // ((ViewGroup) fragment).removeView(fragment);

      for (int i = 1; i < navigationView.getMenu().size(); i++) {
        navigationView.getMenu().getItem(i).setChecked(false);
      }
      navigationView.getMenu().getItem(0).setChecked(true);
    }

    myShipments.add("Test");

    myShipmentListView.setAdapter(new MyShipmentsPendingAdapter());

    myShipmentListView.addItemDecoration(new BottomSpaceItemDecoration(50));

    return fragment;
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
        myShipmentListView.setAdapter(new MyShipmentsPendingAdapter());
        break;

      case R.id.accepted_button:
        acceptedButton.setBackground(
            getResources().getDrawable(R.drawable.button_rounded_corners_primary_filled));
        pendingButton.setBackground(null);
        acceptedButton.setTextColor(getResources().getColor(android.R.color.white));
        pendingButton.setTextColor(getResources().getColor(R.color.colorPrimary));
        myShipmentListView.setAdapter(new MyShipmentsAcceptedAdapter());
        break;
    }
  }

  private class MyShipmentsPendingViewHolder extends RecyclerView.ViewHolder {

    public MyShipmentsPendingViewHolder(@NonNull View itemView) {
      super(itemView);

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        itemView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        itemView.setClipToOutline(true);
      }
    }
  }

  private class MyShipmentsAcceptedViewHolder extends RecyclerView.ViewHolder {

    public MyShipmentsAcceptedViewHolder(@NonNull View itemView) {
      super(itemView);

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        itemView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        itemView.setClipToOutline(true);
      }
    }
  }

  private class MyShipmentsPendingAdapter
      extends RecyclerView.Adapter<MyShipmentsPendingViewHolder> {

    @NonNull
    @Override
    public MyShipmentsPendingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View v =
          LayoutInflater.from(viewGroup.getContext())
              .inflate(R.layout.my_shipment_pending_list_item, viewGroup, false);

      return new MyShipmentsPendingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(
        @NonNull MyShipmentsPendingViewHolder myShipmentsViewHolder, int i) {

      myShipmentsViewHolder.itemView.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Fragment fragment = new ShipmentDetailsFragment();
              switchFragment(fragment);
            }
          });
    }

    @Override
    public int getItemCount() {
      return 3;
    }
  }

  private class MyShipmentsAcceptedAdapter
      extends RecyclerView.Adapter<MyShipmentsAcceptedViewHolder> {

    @NonNull
    @Override
    public MyShipmentsAcceptedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View v =
          LayoutInflater.from(viewGroup.getContext())
              .inflate(R.layout.my_shipment_accepted_list_item, viewGroup, false);

      return new MyShipmentsAcceptedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(
        @NonNull MyShipmentsAcceptedViewHolder myShipmentsViewHolder, int i) {

      myShipmentsViewHolder.itemView.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Fragment fragment = new ShipmentDetailsFragment();
              switchFragment(fragment);
            }
          });
    }

    @Override
    public int getItemCount() {
      return 3;
    }
  }

  private void switchFragment(Fragment fragment) {
    FragmentTransaction fragmentTransaction =
        getActivity().getSupportFragmentManager().beginTransaction();
    fragmentTransaction.replace(R.id.frame_layout, fragment);
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();
  }
}
