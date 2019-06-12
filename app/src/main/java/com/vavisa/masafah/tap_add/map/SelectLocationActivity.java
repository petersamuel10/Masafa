package com.vavisa.masafah.tap_add.map;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseActivity;

public class SelectLocationActivity extends BaseActivity implements View.OnClickListener {

  private ConstraintLayout pickupLayout;
  private ConstraintLayout dropLayout;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_select_location);

    Toolbar toolbar = findViewById(R.id.select_location_toolbar);
    setSupportActionBar(toolbar);
    setTitle("");

    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    pickupLayout = findViewById(R.id.pickup_location_layout);
    dropLayout = findViewById(R.id.drop_location_layout);

    pickupLayout.setOnClickListener(this);
    dropLayout.setOnClickListener(this);
  }

  @Override
  public boolean onSupportNavigateUp() {
    onBackPressed();
    return super.onSupportNavigateUp();
  }

  @Override
  public void onClick(View v) {

    switch (v.getId()) {
      case R.id.pickup_location_layout:
        startActivity(new Intent(SelectLocationActivity.this, MapActivity.class));
        break;

      case R.id.drop_location_layout:
        startActivity(new Intent(SelectLocationActivity.this, MapActivity.class));
        break;
    }
  }
}
