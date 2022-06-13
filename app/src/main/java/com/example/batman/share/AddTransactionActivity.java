package com.example.batman.share;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.batman.R;
import com.example.batman.db.BatteryData;
import com.example.batman.db.TransactionStockData;
import com.example.batman.db.TransactionSellData;
import com.example.batman.share.utils.DateUtils;
import com.example.batman.share.utils.NumTextWatcher;
import com.example.batman.share.utils.TransactionNumTextWatcher;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddTransactionActivity extends AppCompatActivity {
    private EditText inventoryCountText, priceText, countText, totalText, carNumberText, carCategoryText, phoneNumberText;
    private Spinner nameSpinner, yearSpinner, monthSpinner, daySpinner;
    private RadioGroup isStockRadio, isCardRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        Intent intent = getIntent();
        ArrayList<BatteryData> batteryList = (ArrayList<BatteryData>) intent.getSerializableExtra("batteryList");

        nameSpinner = findViewById(R.id.batNameSpinner);
        inventoryCountText = findViewById(R.id.inventoryCount);
        isStockRadio = findViewById(R.id.isStock);
        isCardRadio = findViewById(R.id.isCredit);
        yearSpinner = findViewById(R.id.yearSpinner);
        monthSpinner = findViewById(R.id.monthSpinner);
        daySpinner = findViewById(R.id.daySpinner);
        priceText = findViewById(R.id.price);
        countText = findViewById(R.id.count);
        totalText = findViewById(R.id.priceTotal);
        carNumberText = findViewById(R.id.carNumber);
        carCategoryText = findViewById(R.id.carCategory);
        phoneNumberText = findViewById(R.id.phoneNumber);

        ArrayAdapter<BatteryData> nameAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, batteryList);
        nameSpinner.setAdapter(nameAdapter);
        nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                inventoryCountText.setText("" + batteryList.get(position).getCount());
                priceText.setText("" + (isStockRadio.getCheckedRadioButtonId() == R.id.radio_stock ?
                        batteryList.get(position).getPurchasePrice() :
                        batteryList.get(position).getSellingPrice()));
                if (((RadioButton) findViewById(R.id.radio_sell)).isChecked() && batteryList.get(position).getCount() <= 0) {
                    findViewById(R.id.complete).setClickable(false);
                    ((TextView) findViewById(R.id.complete)).setTextColor(Color.GRAY);
                } else {
                    findViewById(R.id.complete).setClickable(true);
                    ((TextView) findViewById(R.id.complete)).setTextColor(Color.BLACK);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Calendar today = Calendar.getInstance();

        int todayYear = today.get(Calendar.YEAR);
        int todayMonth = today.get(Calendar.MONTH) + 1; // MONTH는 0부터 시작
        int todayDay = today.get(Calendar.DATE);
        ArrayList<Integer> yearList = new ArrayList<Integer>();
        for (int i = 2; i >= 0; i--)
            yearList.add(todayYear - i);
        ArrayList<Integer> monthList = new ArrayList<Integer>();
        for (int i = 1; i <= 12; i++)
            monthList.add(i);
        ArrayList<Integer> dayList = new ArrayList<Integer>();
        for (int i = 1; i <= YearMonth.now().lengthOfMonth(); i++)
            dayList.add(i);

        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, yearList);
        ArrayAdapter<Integer> monthAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, monthList);
        ArrayAdapter<Integer> dayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, dayList);

        AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dayList.clear();
                int pickYear = (int) yearSpinner.getSelectedItem();
                int pickMonth = (int) monthSpinner.getSelectedItem();
                int maxDay = YearMonth.of(pickYear, pickMonth).lengthOfMonth();
                for (int i = 1; i <= maxDay; i++)
                    dayList.add(i);
                if (pickYear != todayYear || pickMonth != todayMonth)
                    daySpinner.setSelection(maxDay - 1);
                else
                    daySpinner.setSelection(todayDay - 1);
                dayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        yearSpinner.setAdapter(yearAdapter);
        monthSpinner.setAdapter(monthAdapter);
        daySpinner.setAdapter(dayAdapter);

        yearSpinner.setOnItemSelectedListener(spinnerListener);
        monthSpinner.setOnItemSelectedListener(spinnerListener);

        yearSpinner.setSelection(yearList.size() - 1);
        monthSpinner.setSelection(todayMonth - 1);
        daySpinner.setSelection(todayDay - 1);

        inventoryCountText.addTextChangedListener(new NumTextWatcher(inventoryCountText));
        priceText.addTextChangedListener(new TransactionNumTextWatcher(priceText, countText, totalText));
        countText.addTextChangedListener(new TransactionNumTextWatcher(countText, priceText, totalText));
        totalText.addTextChangedListener(new NumTextWatcher(totalText));
        phoneNumberText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        isStockRadio.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radio_stock:
                    findViewById(R.id.carNumberLine).setVisibility(View.INVISIBLE);
                    findViewById(R.id.carCategoryLine).setVisibility(View.INVISIBLE);
                    findViewById(R.id.phoneNumberLine).setVisibility(View.INVISIBLE);

                    //재고수량 없더라도 활성화
                    findViewById(R.id.complete).setClickable(true);
                    ((TextView) findViewById(R.id.complete)).setTextColor(Color.BLACK);

                    break;
                case R.id.radio_sell:
                    findViewById(R.id.carNumberLine).setVisibility(View.VISIBLE);
                    findViewById(R.id.carCategoryLine).setVisibility(View.VISIBLE);
                    findViewById(R.id.phoneNumberLine).setVisibility(View.VISIBLE);

                    //재고수량 없을 시 완료 비활성화
                    if (Integer.parseInt(inventoryCountText.getText().toString().replaceAll(",", "")) <= 0) {
                        findViewById(R.id.complete).setClickable(false);
                        ((TextView) findViewById(R.id.complete)).setTextColor(Color.GRAY);
                    }
                    break;
            }
        });

        /* 완료 버튼을 눌렀을 때*/
        findViewById(R.id.complete).setOnClickListener(v -> {
            saveToDB();
            finish();
        });
    }

    void saveToDB() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String batName = nameSpinner.getSelectedItem().toString();
        boolean isStock = false, isCard = true;
        switch (isStockRadio.getCheckedRadioButtonId()) {
            case R.id.radio_stock:
                isStock = true;
                break;
            case R.id.radio_sell:
                isStock = false;
                break;
        }
        switch (isCardRadio.getCheckedRadioButtonId()) {
            case R.id.radio_cash:
                isCard = false;
                break;

            case R.id.radio_card:
                isCard = true;
                break;
        }

        DateUtils dateUtils = new DateUtils();
        int year = (int) yearSpinner.getSelectedItem();
        int month = (int) monthSpinner.getSelectedItem();
        int day = (int) daySpinner.getSelectedItem();
        Calendar date = new Calendar.Builder().setDate(year, month, day).build();
        if (date.equals(dateUtils.getToday()))
            date = Calendar.getInstance();  //오늘 날짜면, 현재 시간까지 입력

        int price = Integer.parseInt(priceText.getText().toString().replaceAll(",", ""));
        int count = Integer.parseInt(countText.getText().toString().replaceAll(",", ""));
        int priceTotal = Integer.parseInt(totalText.getText().toString().replaceAll(",", ""));


        if (isStock) {
            TransactionStockData newTransaction = new TransactionStockData(batName, isCard, isStock, date.getTime(), price, count, priceTotal);

            db.collection("Transaction").document(newTransaction.getDate().toString()).set(newTransaction);
            db.collection("Stock").document(batName).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    int prev = Integer.parseInt(document.get("count").toString());
                    db.collection("Stock").document(batName).update("count", prev + newTransaction.getCount());
                }
            });
        } else {
            String carNumber = carNumberText.getText().toString().trim();
            String carCategory = carCategoryText.getText().toString().trim();
            String phoneNumber = phoneNumberText.getText().toString().trim();

            Map customerInfo = new HashMap();
            customerInfo.put("carNumber", carNumber);
            customerInfo.put("carCategory", carCategory);
            customerInfo.put("phoneNumber", phoneNumber);
            TransactionSellData newTransaction = new TransactionSellData(batName, isCard, isStock, date.getTime(), price, count, priceTotal, carNumber, carCategory, phoneNumber);

            db.collection("Transaction").document(newTransaction.getDate().toString()).set(newTransaction);
            db.collection("Stock").document(batName).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    int prev = Integer.parseInt(document.get("count").toString());
                    db.collection("Stock").document(batName).update("count", prev - newTransaction.getCount());
                    db.collection("Customer").document(carNumber + phoneNumber).set(customerInfo);
                    db.collection("Customer").document(carNumber + phoneNumber).collection("TransactionList").document(newTransaction.getDate().toString()).set(newTransaction);
                }
            });

        }
    }
}