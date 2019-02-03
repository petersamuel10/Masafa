package com.vavisa.masafah.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vavisa.masafah.R;

import java.util.ArrayList;
import java.util.List;

import static com.vavisa.masafah.activities.MainActivity.navigationView;

public class ShipmentDetailsFragment extends BaseFragment {

  private View fragment;
  private RecyclerView imagesListView;
  private List<Integer> images = new ArrayList<>();

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    if (fragment == null) {
      fragment = inflater.inflate(R.layout.fragment_shipment_details, container, false);
      imagesListView = fragment.findViewById(R.id.images_list);

      RecyclerView.LayoutManager layoutManager =
          new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

      imagesListView.setLayoutManager(layoutManager);
    } else {
      for (int i = 1; i < navigationView.getMenu().size(); i++) {
        navigationView.getMenu().getItem(i).setChecked(false);
      }
      navigationView.getMenu().getItem(0).setChecked(true);
    }

    images.add(R.drawable.sofa_single);
    images.add(R.drawable.flooar_mat);
    images.add(R.drawable.fridge);

    imagesListView.setAdapter(new ImagesAdapter());

    return fragment;
  }

  private class ImagesViewHolder extends RecyclerView.ViewHolder {

    private ImageView itemImage;

    public ImagesViewHolder(@NonNull View itemView) {
      super(itemView);

      itemImage = itemView.findViewById(R.id.item_image);
    }

    public void bindData(int imageSource) {
      itemImage.setImageResource(imageSource);
    }
  }

  private class ImagesAdapter extends RecyclerView.Adapter<ImagesViewHolder> {

    @NonNull
    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View v =
          LayoutInflater.from(viewGroup.getContext())
              .inflate(R.layout.shipment_details_images_list_item, viewGroup, false);

      return new ImagesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesViewHolder imagesViewHolder, int i) {
      imagesViewHolder.bindData(images.get(i));
    }

    @Override
    public int getItemCount() {
      return images.size();
    }
  }
}
