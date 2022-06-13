package com.example.batman.managemode.transaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.batman.db.TransactionStockData;
import com.example.batman.db.TransactionSellData;
import com.example.batman.R;
import com.example.batman.share.TransactionStockTableAdapter;
import com.example.batman.share.TransactionSellTableAdapter;
import com.example.batman.selectmode.SelectModeActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class TransactionTableFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<TransactionSellData> transactionSellDataList;
    private ArrayList<TransactionStockData> transactionStockDataList;
    private TransactionSellTableAdapter sellTableAdapter;
    private TransactionStockTableAdapter purchaseTableAdapter;
    private boolean isStockView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_transaction_table, container, false);
        ToggleButton btnSell = v.findViewById(R.id.btn_sell);
        ToggleButton btnStock = v.findViewById(R.id.btn_stock);
        btnSell.setChecked(true);

        transactionSellDataList = new ArrayList<>();
        transactionStockDataList = new ArrayList<>();

        recyclerView = v.findViewById(R.id.rv_list);

        sellTableAdapter = new TransactionSellTableAdapter(transactionSellDataList, false);
        purchaseTableAdapter = new TransactionStockTableAdapter(transactionStockDataList, false);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(sellTableAdapter);

        btnSell.setOnClickListener(v1 -> {
            isStockView = false;

            ((TextView) v.findViewById(R.id.is_card)).setText("결제\n수단");
            v.findViewById(R.id.topPanel2).setVisibility(View.VISIBLE);
            btnSell.setChecked(true);
            btnStock.setChecked(false);
            recyclerView.setAdapter(sellTableAdapter);
        });

        btnStock.setOnClickListener(v1 -> {
            isStockView = true;

            ((TextView) v.findViewById(R.id.is_card)).setText("매입\n수량");
            v.findViewById(R.id.topPanel2).setVisibility(View.INVISIBLE);
            btnSell.setChecked(false);
            btnStock.setChecked(true);
            recyclerView.setAdapter(purchaseTableAdapter);
        });

        v.findViewById(R.id.modify).setOnClickListener(v1 -> {
            Intent intent = getActivity().getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            if (!isStockView) {
                intent.putExtra("transactionSellList", transactionSellDataList);
                intent.putExtra("isSell", true);

            } else {
                intent.putExtra("transactionStockList", transactionStockDataList);
                intent.putExtra("isSell", false);
            }
            intent.setClass(getActivity(), TransactionTableModifyActivity.class);
            startActivity(intent);
        });

        v.findViewById(R.id.back).setOnClickListener(v1 -> {
            Log.i("onclick(): ", "back");
            AlertDialog.Builder msgBuilder = new AlertDialog.Builder(getActivity())
                    .setTitle("모드 선택으로 돌아가기")
                    .setMessage("모드 선택화면으로 돌아가시겠습니까?")
                    .setPositiveButton("네", (dialog, i) -> backToSelectMode(SelectModeActivity.class))
                    .setNegativeButton("아니요", (dialog, which) -> {
                    });
            msgBuilder.create();
            msgBuilder.show();
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        getDataFromDB();

    }

    public void getDataFromDB() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        /* SellData */
        db.collection("Transaction").orderBy("date", Query.Direction.DESCENDING).whereEqualTo("stock", false).addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("snapshot error:", error.getMessage());
                return;
            }

            value.getDocumentChanges().forEach(documentChange -> {
                switch (documentChange.getType()) {
                    case ADDED:
                        if (!transactionSellDataList.contains(documentChange.getDocument().toObject(TransactionSellData.class)))
                            transactionSellDataList.add(documentChange.getDocument().toObject(TransactionSellData.class));
                        break;
                    case MODIFIED:
                        transactionSellDataList.set(documentChange.getOldIndex(), documentChange.getDocument().toObject(TransactionSellData.class));
                        break;
                    case REMOVED:
                        transactionSellDataList.remove(documentChange.getDocument().toObject(TransactionSellData.class));
                        break;
                }
                sellTableAdapter.notifyDataSetChanged();
            });
        });

        /* StockData */
        db.collection("Transaction").orderBy("date", Query.Direction.DESCENDING).whereEqualTo("stock", true).addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("snapshot error:", error.getMessage());
                return;
            }

            value.getDocumentChanges().forEach(documentChange -> {
                switch (documentChange.getType()) {
                    case ADDED:
                        if (!transactionStockDataList.contains(documentChange.getDocument().toObject(TransactionStockData.class)))
                            transactionStockDataList.add(documentChange.getDocument().toObject(TransactionStockData.class));
                        break;
                    case MODIFIED:
                        transactionStockDataList.set(documentChange.getOldIndex(), documentChange.getDocument().toObject(TransactionStockData.class));
                        break;
                    case REMOVED:
                        transactionStockDataList.remove(documentChange.getDocument().toObject(TransactionStockData.class));
                        break;
                }
                purchaseTableAdapter.notifyDataSetChanged();
            });
        });
    }

    public void backToSelectMode(Class c) {
        Intent intent = new Intent(getActivity(), c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}