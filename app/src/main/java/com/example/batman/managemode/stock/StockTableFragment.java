package com.example.batman.managemode.stock;

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

import com.example.batman.AddTransactionActivity;
import com.example.batman.db.BatteryDB;
import com.example.batman.db.BatteryData;
import com.example.batman.R;
import com.example.batman.selectmode.SelectModeActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class StockTableFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<BatteryData> batteryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stock_table, container, false);

        batteryList = new ArrayList<>();

        recyclerView = v.findViewById(R.id.rv_list);
        StockTableAdapter stockTableAdapter = new StockTableAdapter(batteryList, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(stockTableAdapter);

        /* 수정 액티비티 실행 */
        v.findViewById(R.id.modify).setOnClickListener(v1 -> {
            Intent intent = getActivity().getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("batteryList", batteryList);
            intent.setClass(getActivity(), StockTableModifyActivity.class);
            startActivity(intent);
        });

        v.findViewById(R.id.back).setOnClickListener(v1 -> {
            Log.i("onclick(): ","back");
            AlertDialog.Builder msgBuilder = new AlertDialog.Builder(getActivity())
                    .setTitle("모드 선택으로 돌아가기")
                    .setMessage("모드 선택화면으로 돌아가시겠습니까?")
                    .setPositiveButton("네", (dialog, i) -> startActivity(SelectModeActivity.class))
                    .setNegativeButton("아니요", (dialog, which) -> { });
            msgBuilder.create();
            msgBuilder.show();
        });

        v.findViewById(R.id.add_button).setOnClickListener(view -> {
            Log.i("onclick(): ","add");
            Intent intent = getActivity().getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("batteryList", batteryList);
            intent.putExtra("isStock", true);
            intent.setClass(getActivity(), AddTransactionActivity.class);
            startActivity(intent);
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Stock").orderBy("batName").addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("snapshot error:", error.getMessage());
                return;
            }
            value.getDocumentChanges().forEach(documentChange -> {
                switch (documentChange.getType()) {
                    case ADDED:
                        if(!batteryList.contains(documentChange.getDocument().toObject(BatteryDB.class)))
                            batteryList.add(documentChange.getDocument().toObject(BatteryDB.class));
                        break;
                    case MODIFIED:
                        batteryList.set(documentChange.getOldIndex(), documentChange.getDocument().toObject(BatteryDB.class));
                        break;
                    case REMOVED:
                        batteryList.remove(documentChange.getDocument().toObject(BatteryDB.class));
                        break;
                }
                documentChange.getDocument().toObject(BatteryDB.class);
                recyclerView.getAdapter().notifyDataSetChanged();
            });


        });
    }

    private void startActivity(Class c) {
        Intent intent = new Intent(getActivity(), c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}