package com.example.batman.managemode.stock;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.batman.share.AddStockActivity;
import com.example.batman.db.BatteryData;
import com.example.batman.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;

public class StockTableModifyActivity extends AppCompatActivity {
    private ArrayList<BatteryData> batteryList;
    private Intent intent;
    private StockTableAdapter sellingPriceTableAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_stock_table);

        intent = getIntent();
        batteryList = BatteryData.cloneList((ArrayList<BatteryData>) intent.getSerializableExtra("batteryList"));

        RecyclerView recyclerView = findViewById(R.id.rv_list);
        sellingPriceTableAdapter = new StockTableAdapter(batteryList, true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(sellingPriceTableAdapter);

        ((TextView)findViewById(R.id.modify)).setText("완료");

        findViewById(R.id.modify).setOnClickListener(v -> saveToDB());

        findViewById(R.id.back).setOnClickListener(v -> {
            Log.i("onclick(): ","back");
            AlertDialog.Builder msgBuilder = new AlertDialog.Builder(StockTableModifyActivity.this)
                    .setTitle("수정 취소")
                    .setMessage("수정을 취소하시겠습니까?")
                    .setPositiveButton("네", (dialog, i) -> onBackPressed())
                    .setNegativeButton("아니요", (dialog, which) -> { });
            msgBuilder.create();
            msgBuilder.show();
        });

        findViewById(R.id.add_button).setOnClickListener(view -> startAddStock(AddStockActivity.class));
    }

    public void startAddStock(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void saveToDB() {
        ArrayList<BatteryData> originList = (ArrayList<BatteryData>) intent.getSerializableExtra("batteryList");
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(StockTableModifyActivity.this)
                .setTitle("수정")
                .setMessage("입력된 수정을 반영하시겠습니까?")
                .setPositiveButton("네", (dialog, index) -> {
                    ArrayList<Integer> rmList = sellingPriceTableAdapter.getRmList();

                    rmList.forEach(i -> {
                        db.collection("Stock").document(originList.get(i).getBatName()).delete(); //삭제 하고
                        originList.remove(i);                                                                 //비교 대상과 인덱스 동기화화
                    });

                    for(int i=0;i<batteryList.size();i++) {
                        if(!batteryList.get(i).equals(originList.get(i))) {    //삭제 대상이 아니면서 수정되었으면
                            db.collection("Stock").document(batteryList.get(i).getBatName())
                                    .set(batteryList.get(i));
                            db.collection("Stock").document(batteryList.get(i).getBatName())
                                    .update("LastUpdate", new Date(System.currentTimeMillis()));
                        }
                    }
                    finish();
                })
                .setNegativeButton("아니요", (dialog, which) -> finish());
        msgBuilder.create();
        msgBuilder.show();
    }
}
