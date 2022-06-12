package com.example.batman.sellmode.sellingprice;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.batman.AddTransactionActivity;
import com.example.batman.DB.BatteryDB;
import com.example.batman.DB.BatteryData;
import com.example.batman.R;
import com.example.batman.selectmode.SelectModeActivity;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SellingPriceTableFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private ArrayList<BatteryData> batteryList;

    public SellingPriceTableFragment() {
        // Required empty public constructor
    }

    public static SellingPriceTableFragment newInstance(String param1, String param2) {
        SellingPriceTableFragment fragment = new SellingPriceTableFragment();
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
        View v = inflater.inflate(R.layout.fragment_selling_price_table, container, false);

        batteryList = new ArrayList<>();

        recyclerView = v.findViewById(R.id.rv_list);
        SellingPriceTableAdapter sellingPriceTableAdapter = new SellingPriceTableAdapter(batteryList, 0);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(sellingPriceTableAdapter);

        v.findViewById(R.id.modify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getActivity().getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("batteryList", batteryList);
                intent.setClass(getActivity(), SellingPriceModifyActivity.class);
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

        v.findViewById(R.id.add_button).setOnClickListener(view -> {
            Log.i("onclick(): ","add");
            Intent intent = getActivity().getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("batteryList", batteryList);
            intent.putExtra("isStock", false);
            intent.setClass(getActivity(), AddTransactionActivity.class);
            startActivity(intent);
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v("onstart : ","start");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Stock").orderBy("batName").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("snapshot error:", error.getMessage());
                    return;
                }
                value.getDocumentChanges().forEach(documentChange -> {
                   switch (documentChange.getType()) {
                       case ADDED:
                           if(!batteryList.contains((BatteryData)documentChange.getDocument().toObject(BatteryDB.class)))
                                batteryList.add((BatteryData) documentChange.getDocument().toObject(BatteryDB.class));
                           break;
                       case MODIFIED:
                           batteryList.set(documentChange.getOldIndex(),  (BatteryData)documentChange.getDocument().toObject(BatteryDB.class));
                           break;
                       case REMOVED:
                           batteryList.remove((BatteryData) documentChange.getDocument().toObject(BatteryDB.class));
                           break;
                   }
                   documentChange.getDocument().toObject(BatteryDB.class);
                   recyclerView.getAdapter().notifyDataSetChanged();
                });


            }
        });
    }

    private void startActivity(Class c) {
        Intent intent = new Intent(getActivity(), c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}