package com.vavisa.masafah.tap_add.companies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vavisa.masafah.R;

public class CompaniesAdapter extends RecyclerView.Adapter<CompaniesAdapter.ViewHolder> {

    private Context context;
    private int pos = -1;
    private boolean isSelectAll;

    public CompaniesAdapter(boolean isSelectAll) {
        this.isSelectAll = isSelectAll;
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

        if(isSelectAll)
            holder.check_true.setVisibility(View.VISIBLE);
        else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos = position;
                    notifyDataSetChanged();
                }
            });

            if (position == pos)
                holder.check_true.setVisibility(View.VISIBLE);
            else
                holder.check_true.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView check_true;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            check_true = itemView.findViewById(R.id.ic_check);
        }
    }
}
