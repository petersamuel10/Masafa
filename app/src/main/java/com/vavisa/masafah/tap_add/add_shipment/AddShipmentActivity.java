package com.vavisa.masafah.tap_add.add_shipment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.Toast;

import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.tap_add.add_shipment.model.CategoryModel;
import com.vavisa.masafah.tap_add.add_shipment.model.NewShipmentModel;
import com.vavisa.masafah.tap_add.companies.CompaniesActivity;
import com.vavisa.masafah.util.BottomSpaceItemDecoration;
import com.vavisa.masafah.util.Connectivity;
import com.vavisa.masafah.util.Constants;

import java.util.ArrayList;

public class AddShipmentActivity extends BaseActivity implements AddShipmentView {

    private RecyclerView shipmentList_rec;
    private NewShipmentAdapter adapter;
    private ArrayList<NewShipmentModel> shipmentsList;
    private Button nextButton;
    private AddShipmentPresenter presenter;
    private ArrayList<CategoryModel> categoryList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_shipment);
        initViews();
        if (Connectivity.checkInternetConnection())
            presenter.getCategories();
        else
            showErrorConnection();

        nextButton.setOnClickListener(v -> {
            shipmentsList = adapter.getShipmentList();
            if (shipmentsList.get(shipmentsList.size() - 1).getCategory_id() == -1)
                Toast.makeText(this, getString(R.string.please_select_category_name_for_previous_shipment), Toast.LENGTH_SHORT).show();
            else {
                Constants.addShipmentModel.setShipmentList(shipmentsList);
                startActivity(new Intent(AddShipmentActivity.this, CompaniesActivity.class));
            }
        });
    }

    private void initViews() {

        Toolbar toolbar = findViewById(R.id.add_shipment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        shipmentList_rec = findViewById(R.id.shipment_items);
        shipmentList_rec.addItemDecoration(new BottomSpaceItemDecoration(50));
        nextButton = findViewById(R.id.next_button);

        presenter = new AddShipmentPresenter();
        presenter.attachView(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void categories(ArrayList<CategoryModel> categoryList) {

        this.categoryList = categoryList;

        adapter = new NewShipmentAdapter(categoryList);
        shipmentList_rec.setAdapter(adapter);
    }
}
