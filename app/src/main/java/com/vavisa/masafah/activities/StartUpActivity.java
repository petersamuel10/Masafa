package com.vavisa.masafah.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.login.LoginActivity;

public class StartUpActivity extends BaseActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_startup);

    Thread timerThread =
        new Thread() {
          public void run() {
            try {
              sleep(1);
            } catch (InterruptedException e) {
            } finally {
              startActivity(new Intent(StartUpActivity.this, LoginActivity.class));
            }
          }
        };
    timerThread.start();
  }
}