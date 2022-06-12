package com.example.batman.managemode.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.batman.DB.TransactionPurchaseData;
import com.example.batman.DB.TransactionSellData;
import com.example.batman.R;
import com.example.batman.adapter.TransactionPurchaseTableAdapter;
import com.example.batman.adapter.TransactionSellTableAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class TransactionTableModifyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_transaction_table);

        findViewById(R.id.buttonPanel).setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        boolean isSell = intent.getBooleanExtra("isSell", false);

        ArrayList<TransactionSellData> transactionSellDataList = isSell ? TransactionSellData.cloneList((ArrayList<TransactionSellData>) intent.getSerializableExtra("transactionSellList")) : null;
        ArrayList<TransactionPurchaseData> transactionPurchaseDataList = isSell ? null : TransactionPurchaseData.cloneList((ArrayList<TransactionPurchaseData>) intent.getSerializableExtra("transactionPurchaseList"));

        RecyclerView recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        if (isSell) {
            TransactionSellTableAdapter sellAdapter = new TransactionSellTableAdapter(transactionSellDataList, true);
            recyclerView.setAdapter(sellAdapter);
        } else {
            findViewById(R.id.topPanel2).setVisibility(View.INVISIBLE);
            ((TextView) findViewById(R.id.is_card)).setText("매입\n수량");

            TransactionPurchaseTableAdapter purchaseAdapter = new TransactionPurchaseTableAdapter(transactionPurchaseDataList, true);
            recyclerView.setAdapter(purchaseAdapter);
        }

        ((TextView) findViewById(R.id.modify)).setText("완료");

        findViewById(R.id.modify).setOnClickListener(v -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            AlertDialog.Builder msgBuilder = new AlertDialog.Builder(TransactionTableModifyActivity.this)
                    .setTitle("수정")
                    .setMessage("입력된 수정을 반영하시겠습니까?")
                    .setPositiveButton("네", (dialog, index) -> {
                        ArrayList<TransactionSellData> originSellList;
                        ArrayList<TransactionPurchaseData> originPurchaseList;
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
                                        db.collection("Stock").document(data.getBatName()).update("count", prev + data.getCount());  //수량 복구
                                        db.collection("Customer").document(data.getCarNumber() + data.getPhoneNumber())     //고객의 거래기록 삭제
                                                .collection("TransactionList").document(data.getDate().toString()).delete();
                                        db.collection("Transaction").document(data.getDate().toString()).delete(); //거래 기록 삭제
                                        originSellList.remove(i);                                                          //비교 대상과 인덱스 동기화
                                    }
                                });
                            });

                            for (int i = 0; i < transactionSellDataList.size(); i++) {
                                if (!transactionSellDataList.get(i).equals(originSellList.get(i))) {    //삭제 대상이 아니면서 수정되었으면
                                    TransactionSellData data = transactionSellDataList.get(i);
                                    db.collection("Transaction").document(data.getDate().toString())
                                            .set(transactionSellDataList.get(i));
                                    db.collection("Customer").document(data.getCarNumber() + data.getPhoneNumber())
                                            .collection("TransactionList").document(data.getDate().toString()).set(data);
                                }
                            }
                        } else {
                            originPurchaseList = (ArrayList<TransactionPurchaseData>) intent.getSerializableExtra("transactionPurchaseList");
                            rmList = ((TransactionPurchaseTableAdapter) recyclerView.getAdapter()).getRmList();

                            rmList.forEach(i -> {
                                TransactionPurchaseData data = originPurchaseList.get(i);
                                db.collection("Stock").document(data.getBatName()).get().addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        int prev = Integer.parseInt(document.get("count").toString());
                                        db.collection("Stock").document(data.getBatName()).update("count", prev - data.getCount());  //수량 복구

                                        db.collection("Transaction").document(data.getDate().toString()).delete(); //거래 기록 삭제
                                        originPurchaseList.remove(i);                                                          //비교 대상과 인덱스 동기화
                                    }
                                });
                            });

                            for (int i = 0; i < transactionPurchaseDataList.size(); i++) {
                                if (!transactionPurchaseDataList.get(i).equals(originPurchaseList.get(i))) {    //삭제 대상이 아니면서 수정되었으면
                                    db.collection("Transaction").document(transactionPurchaseDataList.get(i).getDate().toString())
                                            .set(transactionPurchaseDataList.get(i));
                                }
                            }

                        }
                        finish();
                    })
                    .setNegativeButton("아니요", (dialog, which) -> finish());
            msgBuilder.create();
            msgBuilder.show();
        });


    }
}
