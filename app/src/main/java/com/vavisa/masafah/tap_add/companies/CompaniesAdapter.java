package com.vavisa.masafah.tap_add.companies;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vavisa.masafah.R;
import com.vavisa.masafah.tap_my_shipment.company_details.CompanyModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompaniesAdapter extends RecyclerView.Adapter<CompaniesAdapter.ViewHolder> {

    private ArrayList<CompanyModel> companyList;
    private Context context;
    public static ArrayList<Integer> deliveryCompaniesIdList;

    CompaniesAdapter(ArrayList<CompanyModel> companyList) {
        this.companyList = companyList;
        deliveryCompaniesIdList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company, parent, false);
        context = parent.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.bind(companyList.get(position));
        holder.itemView.setOnClickListener(v -> {
            if (companyList.get(position).isSelected()) {
                companyList.get(position).setSelected(false);
                deliveryCompaniesIdList.remove(getIndexOf(companyList.get(position).getId()));
                if (deliveryCompaniesIdList.size() == 0)
                    CompaniesActivity.select_all_tag.setText(context.getString(R.string.select_all));
            } else {
                companyList.get(position).setSelected(true);
                deliveryCompaniesIdList.add(companyList.get(position).getId());
            }
            notifyDataSetChanged();
        });
    }

    private int getIndexOf(Integer companyId) {
        int index = 0;
        for (int i = 0; i < deliveryCompaniesIdList.size(); i++) {
            if (companyId.equals(deliveryCompaniesIdList.get(i)))
                return i;
        }
        return index;
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    ArrayList<Integer> getDeliveryCompaniesIdList() {
        return deliveryCompaniesIdList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView check_true;
        CircleImageView company_image;
        TextView company_name;
        RatingBar company_rating;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            company_image = itemView.findViewById(R.id.company_image);
            company_name = itemView.findViewById(R.id.company_name);
            company_rating = itemView.findViewById(R.id.com_rating);
            check_true = itemView.findViewById(R.id.ic_check);
        }

        void bind(CompanyModel companyModel) {
            if (companyModel.isSelected())
                check_true.setVisibility(View.VISIBLE);
            else
                check_true.setVisibility(View.GONE);

            company_name.setText(companyModel.getName());
            company_rating.setRating(companyModel.getRating());
            Glide.with(context)
                    .load(companyModel.getImage())
                    .centerCrop()
                    .placeholder(new ColorDrawable(Color.rgb(192, 192, 192)))
                    .into(company_image);
        }
    }

    void changeSelectionState(Boolean selected) {
        deliveryCompaniesIdList.clear();
        for (int i=0; i<companyList.size(); i++) {
            companyList.get(i).setSelected(selected);
            if (selected)
                deliveryCompaniesIdList.add(companyList.get(i).getId());
        }
        notifyDataSetChanged();
    }
}
