package com.example.batman;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionTableFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TransactionTableFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
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

        btnSell.setOnClickListener(v1 -> {
            btnStock.setChecked(false);
        });

        btnStock.setOnClickListener(v1 -> {
            btnSell.setChecked(false);
        });
        // Inflate the layout for this fragment
        return v;
    }
}