package com.vavisa.masafah.tap_my_shipment.company_details;

import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.vavisa.masafah.R;
import com.vavisa.masafah.base.BaseActivity;
import com.vavisa.masafah.util.Connectivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompanyDetailsActivity extends BaseActivity implements View.OnClickListener, CompanyDetailsViews {

    private ConstraintLayout profileLayout;
    private ConstraintLayout contactUsLayout;
    private ConstraintLayout descriptionLayout;
    private TextView callTag, emailTag, rateTag;
    private CircleImageView company_image;
    private TextView company_name, company_email, company_reviews, description;
    private RatingBar company_rating;
    private CompanyDetailsPresenter presenter;
    private Float rating_value;
    private DialogPlus dialogPlus;
    private CompanyModel companyModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);
        initViews();

        presenter = new CompanyDetailsPresenter();
        presenter.attachView(this);
        if (Connectivity.checkInternetConnection())
            presenter.getCompanyDetails(getIntent().getStringExtra("company_id"));
        else
            showErrorConnection();
    }

    private void initViews() {

        Toolbar toolbar = findViewById(R.id.company_details_toolbar);
        setTitle("");

        profileLayout = findViewById(R.id.profile_layout);
        contactUsLayout = findViewById(R.id.contact_us_layout);
        descriptionLayout = findViewById(R.id.description_layout);
        company_name = findViewById(R.id.company_name);
        company_email = findViewById(R.id.company_email);
        company_reviews = findViewById(R.id.reviews);
        company_rating = findViewById(R.id.com_rating);
        company_image = findViewById(R.id.company_image);
        description = findViewById(R.id.description);
        callTag = findViewById(R.id.call_us_tag);
        emailTag = findViewById(R.id.email_tag);
        rateTag = findViewById(R.id.rating_tag);

        callTag.setOnClickListener(this);
        emailTag.setOnClickListener(this);
        rateTag.setOnClickListener(this);

        profileLayout.post(
                () -> {
                    int height = profileLayout.getHeight();
                    profileLayout.setTranslationY(-(height / 3));
                    contactUsLayout.setTranslationY(-(height / 3));
                    descriptionLayout.setTranslationY(-(height / 3));
                });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.call_us_tag:

                AlertDialog.Builder callAlert = new AlertDialog.Builder(this);
                callAlert.setTitle(R.string.call);
                callAlert.setMessage(getString(R.string.are_you_want_to_call) + " " + companyModel.getMobile() + " " +
                        getString(R.string.question_mark));

                callAlert.setPositiveButton(R.string.call, (dialog, which) -> {
                    boolean call = getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
                    if (call) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + companyModel.getMobile()));
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

                callAlert.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());

                callAlert.create().show();
                break;

            case R.id.email_tag:

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{companyModel.getEmail()});
                try {
                    startActivity(i);
                } catch (android.content.ActivityNotFoundException ex) {
                }
                break;

            case R.id.rating_tag:
                dialogPlus =
                        DialogPlus.newDialog(CompanyDetailsActivity.this)
                                .setGravity(Gravity.BOTTOM)
                                .setContentHolder(new ViewHolder(R.layout.rating_pop_up))
                                .setContentBackgroundResource(R.drawable.rounded_corners_white_filled)
                                .create();

                ImageView close_btn = (ImageView) dialogPlus.findViewById(R.id.close_button);
                Button rating_btn = (Button) dialogPlus.findViewById(R.id.rating_btn);
                RatingBar ratingBar = (RatingBar) dialogPlus.findViewById(R.id.rating_bar);
                ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
                    rating_value = rating;
                });
                close_btn.setOnClickListener(v1 -> dialogPlus.dismiss());
                rating_btn.setOnClickListener(v2 -> {
                    if (Connectivity.checkInternetConnection())
                        presenter.rateCompanyDetails(new RatingModel(getIntent().getStringExtra("company_id"), rating_value));
                    else
                        showErrorConnection();
                });
                dialogPlus.show();
                break;
        }
    }

    @Override
    public void displayCompanyDetails(CompanyModel companyModel) {

        this.companyModel = companyModel;
        company_name.setText(companyModel.getName());
        company_email.setText(companyModel.getEmail());
        company_rating.setRating(companyModel.getRating());
        description.setText(companyModel.getDescription());

        Glide.with(this)
                .load(companyModel.getImage())
                .centerCrop()
                .placeholder(R.drawable.ic_account_circle)
                .error(R.drawable.ic_account_circle)
                .into(company_image);
    }

    @Override
    public void rationResponse() {

        dialogPlus.dismiss();
    }
}
