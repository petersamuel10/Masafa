package com.vavisa.masafah.tap_add.invoice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vavisa.masafah.R;
import com.vavisa.masafah.activities.MainActivity;

public class Invoice extends AppCompatActivity {

    Button confirm_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        confirm_btn = findViewById(R.id.confirm_btn);

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Invoice.this, MainActivity.class));
                finish();
            }
        });
    }
}
