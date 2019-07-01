package com.vavisa.masafah.tap_add.companies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.tap_add.map.SelectLocationActivity;
import com.vavisa.masafah.tap_my_shipment.company_details.CompanyModel;
import com.vavisa.masafah.util.Constants;

import java.util.ArrayList;

public class CompaniesActivity extends BaseActivity implements CompaniesView {

    private RecyclerView companies_rec;
    private TextView select_all_tag;
    private CompaniesAdapter adapter;
    private Button nextButton;
    private CompaniesPresenter presenter;
    private ArrayList<CompanyModel> companyList;
    private ArrayList<Integer> deliveryCompaniesIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies);
        initView();
        presenter.getCompanies();
        select_all_tag.setOnClickListener(v -> {
            if (select_all_tag.getText().toString().equals(getString(R.string.select_all))) {
                select_all_tag.setText(getString(R.string.unselect_all));
                companies_rec.setAdapter(new CompaniesAdapter(companyList, true));
            } else {
                select_all_tag.setText(getString(R.string.select_all));
                companies_rec.setAdapter(new CompaniesAdapter(companyList, false));
            }
        });
        nextButton.setOnClickListener(v -> {
            deliveryCompaniesIdList = adapter.getDeliveryCompaniesIdList();
            if (deliveryCompaniesIdList.size() == 0)
                Toast.makeText(this, getString(R.string.please_select_a_company_or_more_first), Toast.LENGTH_SHORT).show();
            else {
                Constants.addShipmentModel.setDeliveryCompaniesIdList(deliveryCompaniesIdList);
                startActivity(new Intent(CompaniesActivity.this, SelectLocationActivity.class));
            }
        });

    }

    private void initView() {

        Toolbar toolbar = findViewById(R.id.select_company_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        companies_rec = findViewById(R.id.companies_rec);
        select_all_tag = findViewById(R.id.select_all_tag);
        nextButton = findViewById(R.id.next_button);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(companies_rec.getContext(), DividerItemDecoration.VERTICAL);
        companies_rec.addItemDecoration(dividerItemDecoration);

        presenter = new CompaniesPresenter();
        presenter.attachView(this);

        adapter = new CompaniesAdapter(null, false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void companies(ArrayList<CompanyModel> companyList) {
        this.companyList = companyList;
        companies_rec.setAdapter(new CompaniesAdapter(companyList, false));
    }
}
