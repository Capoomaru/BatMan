package com.example.batman.sellmode.customer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.example.batman.db.TransactionSellData;
import com.example.batman.R;
import com.example.batman.utils.DateUtils;
import com.example.batman.adapter.TransactionSellTableAdapter;
import com.example.batman.selectmode.SelectModeActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private int prevId;

    private RecyclerView recyclerView;
    private ArrayList<TransactionSellData> originList;
    private ArrayList<TransactionSellData> dataList;
    private HashMap<Integer, ToggleButton> btnMap;

    private View.OnClickListener onClickListener;
    private DateUtils dateUtils;


    public CustomerListFragment() {
        // Required empty public constructor
    }

    public static CustomerListFragment newInstance(String param1, String param2) {
        CustomerListFragment fragment = new CustomerListFragment();
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
        dateUtils = new DateUtils();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_list, container, false);

        originList = new ArrayList<>();
        dataList = new ArrayList<>();
        recyclerView = v.findViewById(R.id.rv_list);
        TransactionSellTableAdapter sellTableAdapter = new TransactionSellTableAdapter(dataList, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(sellTableAdapter);

        btnMap = new HashMap();
        ToggleButton btnToday, btnWeek, btnMonth, btnSearch;
        btnToday = v.findViewById(R.id.btn_today);
        btnWeek = v.findViewById(R.id.btn_week);
        btnMonth = v.findViewById(R.id.btn_month);
        btnSearch = v.findViewById(R.id.btn_search);
        btnMap.put(R.id.btn_today, btnToday);
        btnMap.put(R.id.btn_week, btnWeek);
        btnMap.put(R.id.btn_month, btnMonth);
        btnMap.put(R.id.btn_search, btnSearch);

        EditText searchView = v.findViewById(R.id.searchView);
        ConstraintLayout searchPanel = v.findViewById(R.id.searchPanel);

        prevId = R.id.btn_today;

        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMap.get(v.getId()).setChecked(true);
                if (v.getId() != prevId) {
                    btnMap.get(prevId).setChecked(false);
                    switch (v.getId()) {
                        case R.id.btn_today:
                            searchPanel.setVisibility(View.INVISIBLE);
                            dataList.clear();
                            for(TransactionSellData data : originList) {
                                if(data.getDate().after(dateUtils.getToday()) && data.getDate().before(dateUtils.getTomorrow()))
                                    dataList.add(data);
                            }
                            recyclerView.getAdapter().notifyDataSetChanged();

                            break;
                        case R.id.btn_week:
                            searchPanel.setVisibility(View.INVISIBLE);
                            dataList.clear();
                            Log.w("week", dateUtils.getWeekStart().toString() + dateUtils.getWeekEnd().toString());
                            for(TransactionSellData data : originList) {
                                if(data.getDate().after(dateUtils.getWeekStart()) && data.getDate().before(dateUtils.getWeekEnd()))
                                    dataList.add(data);
                            }
                            recyclerView.getAdapter().notifyDataSetChanged();
                            break;
                        case R.id.btn_month:
                            searchPanel.setVisibility(View.INVISIBLE);
                            dataList.clear();
                            for(TransactionSellData data : originList) {
                                if(data.getDate().after(dateUtils.getMonthStart()) && data.getDate().before(dateUtils.getMonthEnd()))
                                    dataList.add(data);
                            }
                            recyclerView.getAdapter().notifyDataSetChanged();
                            break;
                        case R.id.btn_search:
                            searchPanel.setVisibility(View.VISIBLE);
                            dataList.clear();
                            recyclerView.getAdapter().notifyDataSetChanged();
                            searchView.setOnKeyListener((v1, keyCode, event) -> {
                                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                String searchData;
                                switch (keyCode) {
                                    case KeyEvent.KEYCODE_ENTER: //엔터키 눌렸을 때
                                        if (searchView.getText().length() == 0) {
                                            break;
                                        }
                                        //입력글자 깨짐현상 - google 입력기에서 문제생김 //https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=wnwogh88&logNo=220462603648

                                        searchData = searchView.getText().toString().trim();//검색한거 스트링으로 저장

                                        searchView.clearFocus();//엔터키 누르면 커서 제거
                                        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0); //키보드 내려줌.

                                        searching(searchData);
                                        recyclerView.getAdapter().notifyDataSetChanged();

                                        break;
                                    default:
                                        return false;
                                }
                                return true;
                            });
                            break;
                    }
                    prevId = v.getId();
                }
            }
        };

        btnMap.forEach((integer, toggleButton) -> {
            toggleButton.setOnClickListener(onClickListener);
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

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collectionGroup("TransactionList").orderBy("date", Query.Direction.DESCENDING)
                .whereGreaterThanOrEqualTo("date",dateUtils.getYearStart())
                .whereLessThanOrEqualTo("date",dateUtils.getYearEnd())
                .addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("snapshot error:", error.getMessage());
                return;
            }

            value.getDocumentChanges().forEach(documentChange -> {
                switch (documentChange.getType()) {
                    case ADDED:
                        if(!originList.contains(documentChange.getDocument().toObject(TransactionSellData.class)))
                            originList.add(documentChange.getDocument().toObject(TransactionSellData.class));
                        break;
                    case MODIFIED:
                        originList.set(documentChange.getOldIndex(), documentChange.getDocument().toObject(TransactionSellData.class));
                        break;
                    case REMOVED:
                        originList.remove(documentChange.getDocument().toObject(TransactionSellData.class));
                        break;
                }
            });

            for (TransactionSellData data : originList) {
                if (data.getDate().after(dateUtils.getToday()) && data.getDate().before(dateUtils.getTomorrow()))
                    dataList.add(data);
            }
            recyclerView.getAdapter().notifyDataSetChanged();
        });


    }

    private void startActivity(Class c) {
        Intent intent = new Intent(getActivity(), c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void searching(String search) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        dataList.clear();
        for (TransactionSellData data : originList) {
            if (data.getCarNumber().matches(".*" + search + ".*") && data.getCarCategory().matches(".*" + search + ".*")
                    && data.getPhoneNumber().replaceAll("-", "").matches(".*" + search + ".*"))
                dataList.add(data);
        }
    }
}