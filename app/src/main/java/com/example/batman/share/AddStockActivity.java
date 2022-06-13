package com.example.batman.share;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.batman.R;
import com.example.batman.db.BatteryData;
import com.example.batman.share.utils.NumTextWatcher;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class AddStockActivity extends AppCompatActivity {
    private EditText batNameView, purchasePriceView, sellingPriceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);

        batNameView = findViewById(R.id.batName);
        purchasePriceView =  findViewById(R.id.purchasePrice);
        sellingPriceView = findViewById(R.id.sellingPrice);
        purchasePriceView.addTextChangedListener(new NumTextWatcher(purchasePriceView));
        sellingPriceView.addTextChangedListener(new NumTextWatcher(sellingPriceView));


        findViewById(R.id.complete).setOnClickListener(v -> {
            saveToDB();
            finish();

        });
    }

    void saveToDB() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String batName = batNameView.getText().toString();
        int purchasePrice = Integer.parseInt(purchasePriceView.getText().toString().replaceAll(",",""));
        int sellingPrice = Integer.parseInt(sellingPriceView.getText().toString().replaceAll(",",""));

        BatteryData newStock = new BatteryData(batName, purchasePrice, sellingPrice, 0);
        db.collection("Stock").document(batName).set(newStock);
        db.collection("Stock").document(batName).update("lastUpdate", new Date(System.currentTimeMillis()));
    }

}