package com.vavisa.masafah.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.vavisa.masafah.R;
import com.vavisa.masafah.util.PopUpActivity;

public class CompanyDetailsActivity extends BaseActivity implements View.OnClickListener {

  private ConstraintLayout profileLayout;
  private ConstraintLayout contactUsLayout;
  private ConstraintLayout descriptionLayout;
  private ImageView rateIcon;
  private TextView rateTag;
  private ImageView callIcon;
  private TextView calltag;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_company_details);

    Toolbar toolbar = findViewById(R.id.company_details_toolbar);
    setTitle("");

    profileLayout = findViewById(R.id.profile_layout);
    contactUsLayout = findViewById(R.id.contact_us_layout);
    descriptionLayout = findViewById(R.id.description_layout);
    rateIcon = findViewById(R.id.rating_icon);
    rateTag = findViewById(R.id.rating_tag);
    callIcon = findViewById(R.id.call_icon);
    calltag = findViewById(R.id.call_us_tag);

    rateIcon.setOnClickListener(this);
    rateTag.setOnClickListener(this);
    callIcon.setOnClickListener(this);
    calltag.setOnClickListener(this);

    profileLayout.post(
        new Runnable() {
          @Override
          public void run() {
            int height = profileLayout.getHeight();
            profileLayout.setTranslationY(-(height / 3));
            contactUsLayout.setTranslationY(-(height / 3));
            descriptionLayout.setTranslationY(-(height / 3));
          }
        });
  }

  @Override
  public void onClick(View v) {

    switch (v.getId()) {
      case R.id.rating_icon:
      case R.id.rating_tag:
        DialogPlus dialogPlus =
            DialogPlus.newDialog(CompanyDetailsActivity.this)
                .setGravity(Gravity.BOTTOM)
                .setContentHolder(new ViewHolder(R.layout.rating_pop_up))
                .setContentBackgroundResource(R.drawable.rounded_corners_white_filled)
                .create();

        dialogPlus.show();
        break;

      case R.id.call_icon:
      case R.id.call_us_tag:
        startActivity(new Intent(CompanyDetailsActivity.this, PopUpActivity.class));
        break;
    }
  }
}
