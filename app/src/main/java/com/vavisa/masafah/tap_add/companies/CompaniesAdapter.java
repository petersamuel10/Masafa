package com.vavisa.masafah.tap_add.companies;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vavisa.masafah.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompaniesAdapter extends RecyclerView.Adapter<CompaniesAdapter.ViewHolder> {

    private ArrayList<CompanyModel> companyList;
    private Context context;
    public static ArrayList<Integer> deliveryCompaniesIdList;
    private boolean isSelectAll;

    CompaniesAdapter(ArrayList<CompanyModel> companyList, boolean isSelectAll) {
        this.companyList = companyList;
        this.isSelectAll = isSelectAll;
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

        if (isSelectAll) {
            deliveryCompaniesIdList.clear();
            holder.check_true.setVisibility(View.VISIBLE);
            for (CompanyModel company : companyList) {
                deliveryCompaniesIdList.add(company.getId());
            }
        } else {
            holder.itemView.setOnClickListener(v -> {
                if (holder.check_true.getVisibility() == View.GONE) {
                    holder.check_true.setVisibility(View.VISIBLE);
                    deliveryCompaniesIdList.add(companyList.get(position).getId());
                } else {
                    holder.check_true.setVisibility(View.GONE);
                    deliveryCompaniesIdList.remove(companyList.get(position).getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    public ArrayList<Integer> getDeliveryCompaniesIdList() {
        return deliveryCompaniesIdList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView check_true;
        CircleImageView company_image;
        TextView company_name;
        RatingBar company_rating;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            company_image = itemView.findViewById(R.id.company_image);
            company_name = itemView.findViewById(R.id.company_name);
            company_rating = itemView.findViewById(R.id.com_rating);
            check_true = itemView.findViewById(R.id.ic_check);
        }

        public void bind(CompanyModel companyModel) {

            company_name.setText(companyModel.getName());
            company_rating.setRating(companyModel.getRating());
            Glide.with(context)
                    .load(companyModel.getImage())
                    .centerCrop()
                    .placeholder(new ColorDrawable(Color.rgb(192, 192, 192)))
                    .into(company_image);
        }
    }
}
