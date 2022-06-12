package com.example.batman.sellmode.sellingprice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.batman.AddStockActivity;
import com.example.batman.db.BatteryData;
import com.example.batman.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;

public class SellingPriceModifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_selling_price_table);

        Intent intent = getIntent();
        ArrayList<BatteryData> batteryList = BatteryData.cloneList((ArrayList<BatteryData>) intent.getSerializableExtra("batteryList"));

        RecyclerView recyclerView = findViewById(R.id.rv_list);
        SellingPriceTableAdapter sellingPriceTableAdapter = new SellingPriceTableAdapter(batteryList, 1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(sellingPriceTableAdapter);

        ((TextView) findViewById(R.id.modify)).setText("완료");

        findViewById(R.id.modify).setOnClickListener(v -> {
            ArrayList<BatteryData> originList = (ArrayList<BatteryData>) intent.getSerializableExtra("batteryList");
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            AlertDialog.Builder msgBuilder = new AlertDialog.Builder(SellingPriceModifyActivity.this)
                    .setTitle("수정")
                    .setMessage("입력된 수정을 반영하시겠습니까?")
                    .setPositiveButton("네", (dialog, index) -> {
                        for(int i=0;i<batteryList.size();i++) {
                            if(!batteryList.get(i).equals(originList.get(i))) {
                                Log.i("modify : ", "not match" + i);
                                db.collection("Stock").document(batteryList.get(i).getBatName())
                                        .set(batteryList.get(i));
                                db.collection("Stock").document(batteryList.get(i).getBatName())
                                        .update("lastUpdate", new Date(System.currentTimeMillis()));
                            }
                        }
                        finish();
                    })
                    .setNegativeButton("아니요", (dialog, which) -> finish());
            msgBuilder.create();
            msgBuilder.show();
        });

        findViewById(R.id.back).setOnClickListener(v -> {
            Log.i("onclick(): ","back");
            AlertDialog.Builder msgBuilder = new AlertDialog.Builder(SellingPriceModifyActivity.this)
                    .setTitle("수정 취소")
                    .setMessage("수정을 취소하시겠습니까?")
                    .setPositiveButton("네", (dialog, i) -> onBackPressed())
                    .setNegativeButton("아니요", (dialog, which) -> { });
            msgBuilder.create();
            msgBuilder.show();
        });

        findViewById(R.id.add_button).setOnClickListener(view -> {
            startActivity(AddStockActivity.class);
        });
    }

    private void startActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}