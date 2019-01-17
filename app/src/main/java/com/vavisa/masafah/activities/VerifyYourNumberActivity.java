package com.vavisa.masafah.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.vavisa.masafah.R;

public class VerifyYourNumberActivity extends BaseActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_verify_phone_number);

    Toolbar toolbar = findViewById(R.id.verify_number_toolbar);
    setSupportActionBar(toolbar);
    setTitle("");

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);

  }
}
