package com.example.batman;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.batman.DB.BatteryData;
import com.example.batman.adapter.NumTextWatcher;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class AddStockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        EditText batNameView = findViewById(R.id.batName);
        EditText purchasePriceView =  findViewById(R.id.purchasePrice);
        EditText sellingPriceView = findViewById(R.id.sellingPrice);
        purchasePriceView.addTextChangedListener(new NumTextWatcher(purchasePriceView));
        sellingPriceView.addTextChangedListener(new NumTextWatcher(sellingPriceView));


        findViewById(R.id.complete).setOnClickListener(v -> {
            String batName = batNameView.getText().toString();
            int purchasePrice = Integer.parseInt(purchasePriceView.getText().toString().replaceAll(",",""));
            int sellingPrice = Integer.parseInt(sellingPriceView.getText().toString().replaceAll(",",""));

            BatteryData newStock = new BatteryData(batName, purchasePrice, sellingPrice, 0);
            db.collection("Stock").document(batName).set(newStock);
            db.collection("Stock").document(batName).update("LastUpdate", new Date(System.currentTimeMillis()));

            finish();

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}