package com.example.batman;

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

import com.example.batman.DB.BatteryData;

import java.util.ArrayList;

public class StockTableFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public StockTableFragment() {
        // Required empty public constructor
    }

    public static StockTableFragment newInstance(String param1, String param2) {
        StockTableFragment fragment = new StockTableFragment();
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
        View v = inflater.inflate(R.layout.fragment_stock_table, container, false);

        ArrayList<BatteryData> batteryList = new ArrayList<>();

        for(int i=0;i<30;i++)
            batteryList.add(new BatteryData("batNmame",50000,100000,"batID",10));

        RecyclerView recyclerView = v.findViewById(R.id.rv_list);
        StockTableAdapter stockTableAdapter = new StockTableAdapter(batteryList, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(stockTableAdapter);


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

    private void startActivity(Class c) {
        Intent intent = new Intent(getActivity(), c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}