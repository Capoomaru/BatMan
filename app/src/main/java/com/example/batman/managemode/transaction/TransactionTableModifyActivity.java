package com.example.batman.managemode.transaction;

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

import com.example.batman.db.TransactionStockData;
import com.example.batman.db.TransactionSellData;
import com.example.batman.R;
import com.example.batman.sellmode.sellingprice.SellingPriceTableModifyActivity;
import com.example.batman.share.TransactionStockTableAdapter;
import com.example.batman.share.TransactionSellTableAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class TransactionTableModifyActivity extends AppCompatActivity {
    private boolean isSell;
    private Intent intent;
    private RecyclerView recyclerView;

    private ArrayList<TransactionSellData> transactionSellDataList;
    private ArrayList<TransactionStockData> transactionStockDataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_transaction_table);

        findViewById(R.id.buttonPanel).setVisibility(View.INVISIBLE);

        intent = getIntent();
        isSell = intent.getBooleanExtra("isSell", false);

        transactionSellDataList = isSell ? TransactionSellData.cloneList((ArrayList<TransactionSellData>) intent.getSerializableExtra("transactionSellList")) : null;
        transactionStockDataList = isSell ? null : TransactionStockData.cloneList((ArrayList<TransactionStockData>) intent.getSerializableExtra("transactionStockList"));

        recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        if (isSell) {
            TransactionSellTableAdapter sellAdapter = new TransactionSellTableAdapter(transactionSellDataList, true);
            recyclerView.setAdapter(sellAdapter);
        } else {
            findViewById(R.id.topPanel2).setVisibility(View.INVISIBLE);
            ((TextView) findViewById(R.id.is_card)).setText("??????\n??????");

            TransactionStockTableAdapter purchaseAdapter = new TransactionStockTableAdapter(transactionStockDataList, true);
            recyclerView.setAdapter(purchaseAdapter);
        }

        ((TextView) findViewById(R.id.modify)).setText("??????");

        findViewById(R.id.back).setOnClickListener(v -> {
            Log.i("onclick(): ", "back");
            AlertDialog.Builder msgBuilder = new AlertDialog.Builder(TransactionTableModifyActivity.this)
                    .setTitle("?????? ??????")
                    .setMessage("????????? ?????????????????????????")
                    .setPositiveButton("???", (dialog, i) -> onBackPressed())
                    .setNegativeButton("?????????", (dialog, which) -> {
                    });
            msgBuilder.create();
            msgBuilder.show();
        });

        findViewById(R.id.modify).setOnClickListener(v -> {
            AlertDialog.Builder msgBuilder = new AlertDialog.Builder(TransactionTableModifyActivity.this)
                    .setTitle("??????")
                    .setMessage("????????? ????????? ?????????????????????????")
                    .setPositiveButton("???", (dialog, index) -> {
                        saveToDB();
                        finish();
                    })
                    .setNegativeButton("?????????", (dialog, which) -> finish());
            msgBuilder.create();
            msgBuilder.show();
        });

    }

    public void saveToDB() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        ArrayList<TransactionSellData> originSellList;
        ArrayList<TransactionStockData> originStockList;
        ArrayList<Integer> rmList;
        if (isSell) {
            originSellList = (ArrayList<TransactionSellData>) intent.getSerializableExtra("transactionSellList");
            rmList = ((TransactionSellTableAdapter) recyclerView.getAdapter()).getRmList();

            rmList.forEach(i -> {
                TransactionSellData data = originSellList.get(i);
                db.collection("Stock").document(data.getBatName()).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        int prev = Integer.parseInt(document.get("count").toString());
                        db.collection("Stock").document(data.getBatName()).update("count", prev + data.getCount());  //?????? ??????
                        db.collection("Customer").document(data.getCarNumber() + data.getPhoneNumber())     //????????? ???????????? ??????
                                .collection("TransactionList").document(data.getDate().toString()).delete();
                        db.collection("Transaction").document(data.getDate().toString()).delete(); //?????? ?????? ??????
                        originSellList.remove(i);                                                          //?????? ????????? ????????? ?????????
                    }
                });
            });

            for (int i = 0; i < transactionSellDataList.size(); i++) {
                if (!transactionSellDataList.get(i).equals(originSellList.get(i))) {    //?????? ????????? ???????????? ??????????????????
                    TransactionSellData data = transactionSellDataList.get(i);
                    TransactionSellData originData = originSellList.get(i);
                    data.setFindString();
                    db.collection("Transaction").document(data.getDate().toString())
                            .set(transactionSellDataList.get(i));
                    db.collection("Customer").document(data.getCarNumber() + data.getPhoneNumber())
                            .collection("TransactionList").document(data.getDate().toString()).set(data);
                    if (!data.equalCustomer(originData)) {
                        db.collection("Customer").document(originData.getCarNumber() + originData.getPhoneNumber())
                                .collection("TransactionList").document(originData.getDate().toString()).delete();
                        db.collection("Customer").document(data.getCarNumber() + data.getPhoneNumber())
                                .set(data);

                    }
                }
            }
        } else {
            originStockList = (ArrayList<TransactionStockData>) intent.getSerializableExtra("transactionStockList");
            rmList = ((TransactionStockTableAdapter) recyclerView.getAdapter()).getRmList();

            rmList.forEach(i -> {
                TransactionStockData data = originStockList.get(i);
                db.collection("Stock").document(data.getBatName()).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        int prev = Integer.parseInt(document.get("count").toString());
                        db.collection("Stock").document(data.getBatName()).update("count", prev - data.getCount());  //?????? ??????

                        db.collection("Transaction").document(data.getDate().toString()).delete(); //?????? ?????? ??????
                        originStockList.remove(i);                                                          //?????? ????????? ????????? ?????????
                    }
                });
            });

            for (int i = 0; i < transactionStockDataList.size(); i++) {
                if (!transactionStockDataList.get(i).equals(originStockList.get(i))) {    //?????? ????????? ???????????? ??????????????????
                    db.collection("Transaction").document(transactionStockDataList.get(i).getDate().toString())
                            .set(transactionStockDataList.get(i));
                }
            }

        }
    }
}
