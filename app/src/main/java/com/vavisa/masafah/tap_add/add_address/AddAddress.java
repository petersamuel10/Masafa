package com.vavisa.masafah.tap_add.add_address;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.tap_add.invoice.Invoice;

public class AddAddress extends BaseActivity {

  private Button confirmButton;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_address);

    Toolbar toolbar = findViewById(R.id.add_address_toolbar);
    setSupportActionBar(toolbar);
    setTitle("");
    TextView toolbarTitle = toolbar.findViewById(R.id.add_address_toolbar_title);

    toolbarTitle.setText("Pickup location");

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);

    confirmButton = findViewById(R.id.confirm_button);

    confirmButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          startActivity(new Intent(AddAddress.this, Invoice.class));
        }
    });
  }
}
