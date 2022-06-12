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

import com.example.batman.DB.TransactionPurchaseData;
import com.example.batman.DB.TransactionSellData;
import com.example.batman.R;
import com.example.batman.adapter.TransactionPurchaseTableAdapter;
import com.example.batman.adapter.TransactionSellTableAdapter;
import com.example.batman.selectmode.SelectModeActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class TransactionTableFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private ArrayList<TransactionSellData> transactionSellDataList;
    private ArrayList<TransactionPurchaseData> transactionPurchaseDataList;
    private TransactionSellTableAdapter sellTableAdapter;
    private TransactionPurchaseTableAdapter purchaseTableAdapter;

    public TransactionTableFragment() {
        // Required empty public constructor
    }

    public static TransactionTableFragment newInstance(String param1, String param2) {
        TransactionTableFragment fragment = new TransactionTableFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_transaction_table, container, false);
        ToggleButton btnSell = v.findViewById(R.id.btn_sell);
        ToggleButton btnStock = v.findViewById(R.id.btn_stock);
        btnSell.setChecked(true);

        transactionSellDataList = new ArrayList<>();
        transactionPurchaseDataList = new ArrayList<>();

        recyclerView = v.findViewById(R.id.rv_list);

        sellTableAdapter = new TransactionSellTableAdapter(transactionSellDataList, false);
        purchaseTableAdapter = new TransactionPurchaseTableAdapter(transactionPurchaseDataList, false);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(sellTableAdapter);

        btnSell.setOnClickListener(v1 -> {
            ((TextView)v.findViewById(R.id.is_card)).setText("결제\n수단");
            v.findViewById(R.id.topPanel2).setVisibility(View.VISIBLE);
            btnSell.setChecked(true);
            btnStock.setChecked(false);
            recyclerView.setAdapter(sellTableAdapter);
        });

        btnStock.setOnClickListener(v1 -> {
            ((TextView)v.findViewById(R.id.is_card)).setText("매입\n수량");
            v.findViewById(R.id.topPanel2).setVisibility(View.INVISIBLE);
            btnSell.setChecked(false);
            btnStock.setChecked(true);
            recyclerView.setAdapter(purchaseTableAdapter);
        });

        v.findViewById(R.id.modify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getActivity().getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if(btnSell.isChecked()) {
                    intent.putExtra("transactionSellList", transactionSellDataList);
                    intent.putExtra("isSell", true);

                }
                else {
                    intent.putExtra("transactionPurchaseList", transactionPurchaseDataList);
                    intent.putExtra("isSell", false);
                }
                intent.setClass(getActivity(), TransactionTableModifyActivity.class);
                startActivity(intent);
            }
        });

        v.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onclick(): ","back");
                AlertDialog.Builder msgBuilder = new AlertDialog.Builder(getActivity())
                        .setTitle("모드 선택으로 돌아가기")
                        .setMessage("모드 선택화면으로 돌아가시겠습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                startActivity(SelectModeActivity.class);
                            }
                        })
                        .setNegativeButton("아니요", new DialogInterface.OnClickListener(){@Override public void onClick(DialogInterface dialog, int which) { }});
                msgBuilder.create();
                msgBuilder.show();
            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Transaction").orderBy("date", Query.Direction.DESCENDING).whereEqualTo("stock", false).addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("snapshot error:", error.getMessage());
                return;
            }

            value.getDocumentChanges().forEach(documentChange -> {
                switch (documentChange.getType()) {
                    case ADDED:
                        if(!transactionSellDataList.contains(documentChange.getDocument().toObject(TransactionSellData.class)))
                            transactionSellDataList.add(documentChange.getDocument().toObject(TransactionSellData.class));
                        break;
                    case MODIFIED:
                        transactionSellDataList.set(documentChange.getOldIndex(), documentChange.getDocument().toObject(TransactionSellData.class));
                        break;
                    case REMOVED:
                        transactionSellDataList.remove(documentChange.getDocument().toObject(TransactionSellData.class));
                        break;
                }
                //documentChange.getDocument().toObject(TransactionSellData.class);
                sellTableAdapter.notifyDataSetChanged();
            });
        });
        db.collection("Transaction").orderBy("date", Query.Direction.DESCENDING).whereEqualTo("stock", true).addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("snapshot error:", error.getMessage());
                return;
            }

            value.getDocumentChanges().forEach(documentChange -> {
                switch (documentChange.getType()) {
                    case ADDED:
                        if(!transactionPurchaseDataList.contains(documentChange.getDocument().toObject(TransactionPurchaseData.class)))
                            transactionPurchaseDataList.add(documentChange.getDocument().toObject(TransactionPurchaseData.class));
                        break;
                    case MODIFIED:
                        transactionPurchaseDataList.set(documentChange.getOldIndex(), documentChange.getDocument().toObject(TransactionPurchaseData.class));
                        break;
                    case REMOVED:
                        transactionPurchaseDataList.remove(documentChange.getDocument().toObject(TransactionPurchaseData.class));
                        break;
                }
                //documentChange.getDocument().toObject(TransactionPurchaseData.class);
                purchaseTableAdapter.notifyDataSetChanged();
            });
        });
    }

    private void startActivity(Class c) {
        Intent intent = new Intent(getActivity(), c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}