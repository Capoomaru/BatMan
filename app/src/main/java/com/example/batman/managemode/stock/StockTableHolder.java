package com.example.batman.managemode.stock;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.batman.db.BatteryData;
import com.example.batman.R;
import com.example.batman.share.utils.ICallBackTextWatcher;
import com.example.batman.share.utils.MinusClickListener;
import com.example.batman.share.utils.NumTextWatcher;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class StockTableHolder extends RecyclerView.ViewHolder {
    protected EditText batNameView;
    protected EditText purchasePriceView;
    protected EditText countView;
    protected ImageView minusButton;
    protected ArrayList<Integer> rmList;

    public StockTableHolder(@NonNull View itemView, ICallBackTextWatcher nameWatcher, ICallBackTextWatcher priceWatcher, ICallBackTextWatcher countWatcher, MinusClickListener minusClickListener) {
        super(itemView);
        batNameView = itemView.findViewById(R.id.batName);
        purchasePriceView = itemView.findViewById(R.id.purchase_price);
        countView = itemView.findViewById(R.id.count);
        minusButton = itemView.findViewById(R.id.minus);

        rmList = new ArrayList<>();
        minusButton.setOnClickListener(v -> {
            rmList.add(getAdapterPosition());
        });

        batNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nameWatcher.onTextChanged(getAdapterPosition(), s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        purchasePriceView.addTextChangedListener(new NumTextWatcher(purchasePriceView) {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                priceWatcher.onTextChanged(getAdapterPosition(), s, start, before, count);
            }
        });
        countView.addTextChangedListener(new NumTextWatcher(countView) {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                countWatcher.onTextChanged(getAdapterPosition(), s, start, before, count);
            }
        });
        minusButton.setOnClickListener(v -> minusClickListener.onMinusCLick(v, getAdapterPosition()));
    }

    void onBind(BatteryData batteryData) {
        DecimalFormat format = new DecimalFormat("#,###");

        batNameView.setText(batteryData.getBatName());
        purchasePriceView.setText(format.format(batteryData.getPurchasePrice()));
        countView.setText(format.format(batteryData.getCount()));
        if (getItemViewType() == 1) {
            minusButton.setVisibility(View.VISIBLE);
            batNameView.setEnabled(true);
            purchasePriceView.setEnabled(true);
            countView.setEnabled(true);
        }
    }
}
