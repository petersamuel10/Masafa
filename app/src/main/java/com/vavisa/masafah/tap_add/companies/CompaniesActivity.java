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
import com.vavisa.masafah.tap_add.AddShipmentModel;
import com.vavisa.masafah.tap_add.invoice.InvoiceActivity;
import com.vavisa.masafah.tap_my_shipment.company_details.CompanyModel;
import com.vavisa.masafah.util.Connectivity;

import java.util.ArrayList;

public class CompaniesActivity extends BaseActivity implements CompaniesView {

    private RecyclerView companies_rec;
    public static TextView select_all_tag;
    private CompaniesAdapter adapter;
    private Button nextButton;
    private CompaniesPresenter presenter;
    private ArrayList<CompanyModel> companyList;
    private ArrayList<Integer> deliveryCompaniesIdList;
    private AddShipmentModel addShipmentModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies);
        initView();

        addShipmentModel = getIntent().getParcelableExtra("shipmentModel");
        if (Connectivity.checkInternetConnection())
            presenter.getCompanies();
        else {
            showErrorConnection();
            nextButton.setEnabled(false);
            select_all_tag.setEnabled(false);
        }
        select_all_tag.setOnClickListener(v -> {
            if (select_all_tag.getText().toString().equals(getString(R.string.select_all))) {
                select_all_tag.setText(getString(R.string.unselect_all));
                adapter.changeSelectionState(true);
            } else {
                select_all_tag.setText(getString(R.string.select_all));
                adapter.changeSelectionState(false);
            }
        });
        nextButton.setOnClickListener(v -> {
            deliveryCompaniesIdList = adapter.getDeliveryCompaniesIdList();
            if (deliveryCompaniesIdList.size() == 0)
                Toast.makeText(this, getString(R.string.please_select_a_company_or_more_first), Toast.LENGTH_SHORT).show();
            else {
                addShipmentModel.setDeliveryCompaniesIdList(deliveryCompaniesIdList);
                Intent invoiceIntent = new Intent(CompaniesActivity.this, InvoiceActivity.class);
                invoiceIntent.putExtra("shipmentModel", addShipmentModel);
                startActivity(invoiceIntent);
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

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void companies(ArrayList<CompanyModel> companyList) {
        this.companyList = companyList;
        adapter = new CompaniesAdapter(companyList);
        adapter.changeSelectionState(true);
        companies_rec.setAdapter(adapter);
    }
}
