package com.example.batman;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.batman.DB.BatteryData;
import com.example.batman.adapter.SellingPriceTableAdapter;

import java.util.ArrayList;

public class StockTableModifyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_stock_table);

        Intent intent = getIntent();
        ArrayList<BatteryData> batteryList = (ArrayList<BatteryData>) intent.getSerializableExtra("batteryList");

        RecyclerView recyclerView = findViewById(R.id.rv_list);
        StockTableAdapter sellingPriceTableAdapter = new StockTableAdapter(batteryList, true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(sellingPriceTableAdapter);

        ((TextView)findViewById(R.id.modify)).setText("완료");

        findViewById(R.id.modify).setOnClickListener(v -> {

        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onclick(): ","back");
                AlertDialog.Builder msgBuilder = new AlertDialog.Builder(StockTableModifyActivity.this)
                        .setTitle("모드 선택으로 돌아가기")
                        .setMessage("모드 선택화면으로 돌아가시겠습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                onBackPressed();
                            }
                        })
                        .setNegativeButton("아니요", new DialogInterface.OnClickListener(){@Override public void onClick(DialogInterface dialog, int which) { }});
                msgBuilder.create();
                msgBuilder.show();
            }
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
