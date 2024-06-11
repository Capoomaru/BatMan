package com.example.batman.sellmode.sellingprice;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.batman.R;
import com.example.batman.db.BatteryData;
import com.example.batman.share.utils.ICallBackTextWatcher;
import com.example.batman.share.utils.MinusClickListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SellingPriceTableHolder extends RecyclerView.ViewHolder {
    protected EditText batNameView;
    protected EditText sellingPriceView;
    protected ImageView minusButton;
    protected ICallBackTextWatcher nameWatcher, priceWatcher;
    protected ArrayList<Integer> removeList;

    public SellingPriceTableHolder(@NonNull View itemView, ICallBackTextWatcher nameWatcher, ICallBackTextWatcher priceWatcher, MinusClickListener minusClickListener) {
        super(itemView);
        batNameView = itemView.findViewById(R.id.batName);
        sellingPriceView = itemView.findViewById(R.id.selling_price);
        minusButton = itemView.findViewById(R.id.minus);
        minusButton.setOnClickListener(view -> Log.w("onclick(minus) :", getAdapterPosition() + "minus"));

        removeList = new ArrayList<>();
        minusButton.setOnClickListener(v -> {
            removeList.add(getAdapterPosition());
        });

        this.nameWatcher = nameWatcher;
        this.priceWatcher = priceWatcher;
        TextWatcher nameTextWatcher = new TextWatcher() {
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
        };

        TextWatcher priceTextWatcher = new TextWatcher() {
            String result = "";
            private DecimalFormat decimalFormat = new DecimalFormat("#,###");

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                priceWatcher.onTextChanged(getAdapterPosition(), s, start, before, count);
                if (!TextUtils.isEmpty(s.toString()) && !s.toString().equals(result)) {
                    result = decimalFormat.format(Double.parseDouble(s.toString().replaceAll(",", "")));
                    sellingPriceView.setText(result);
                    sellingPriceView.setSelection(result.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(""))
                    sellingPriceView.setText("0");
            }
        };
        batNameView.addTextChangedListener(nameTextWatcher);
        sellingPriceView.addTextChangedListener(priceTextWatcher);
        minusButton.setOnClickListener(v -> minusClickListener.onMinusCLick(v, getAdapterPosition()));

    }

    void onBind(BatteryData batteryData) {
        DecimalFormat format = new DecimalFormat("#,###");

        batNameView.setText(batteryData.getBatName());
        sellingPriceView.setText(format.format(batteryData.getSellingPrice()));
        if (getItemViewType() == 1) {
            minusButton.setVisibility(View.VISIBLE);
            batNameView.setEnabled(true);
            sellingPriceView.setEnabled(true);
        }
    }
}
