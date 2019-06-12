package com.vavisa.masafah.tap_add.companies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vavisa.masafah.R;
import com.vavisa.masafah.tap_add.map.SelectLocationActivity;

public class Companies extends AppCompatActivity {

    private RecyclerView companies_rec;
    private TextView select_all_tag;
    private CompaniesAdapter adapter;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies);

        companies_rec = findViewById(R.id.companies_rec);
        select_all_tag = findViewById(R.id.select_all_tag);
        nextButton = findViewById(R.id.next_button);

        // companies_rec.addItemDecoration();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(companies_rec.getContext(), DividerItemDecoration.VERTICAL);
        companies_rec.addItemDecoration(dividerItemDecoration);
        companies_rec.setAdapter(new CompaniesAdapter(false));

        select_all_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companies_rec.setAdapter(new CompaniesAdapter(true));
            }
        });
        nextButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Companies.this, SelectLocationActivity.class));
                    }
                });

    }
}
