package com.example.batman.adapter;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.batman.DB.BatteryData;
import com.example.batman.R;

import java.text.DecimalFormat;

public class SellingPriceTableHolder extends RecyclerView.ViewHolder {
    EditText batNameView;
    EditText sellingPriceView;
    ImageView minusButton;
    MinusClickListener minusClickListener;
    ITextWatcher nameWatcher, priceWatcher;

    public interface ITextWatcher {
        String name = "";
        // you can add/remove methods as you please, maybe you dont need this much
        void beforeTextChanged(int position, CharSequence s, int start, int count, int after);

        void onTextChanged(int position, CharSequence s, int start, int before, int count);

        void afterTextChanged(int position, Editable s);
    }

    public SellingPriceTableHolder(@NonNull View itemView, ITextWatcher nameWatcher, ITextWatcher priceWatcher) {
        super(itemView);
        batNameView = itemView.findViewById(R.id.batName);
        sellingPriceView = itemView.findViewById(R.id.selling_price);
        minusButton = itemView.findViewById(R.id.minus);
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("onclick(minus) :",getAdapterPosition()+"minus");

            }
        });

        this.nameWatcher = nameWatcher;
        this.priceWatcher = priceWatcher;
        TextWatcher nameTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                nameWatcher.beforeTextChanged(getAdapterPosition(), s, start, count, after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nameWatcher.onTextChanged(getAdapterPosition(), s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                nameWatcher.afterTextChanged(getAdapterPosition(), s);
            }
        };

        TextWatcher priceTextWatcher = new TextWatcher() {
            String result="";
            private DecimalFormat decimalFormat = new DecimalFormat("#,###");
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                priceWatcher.beforeTextChanged(getAdapterPosition(), s, start, count, after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                priceWatcher.onTextChanged(getAdapterPosition(), s, start, before, count);
                if(!TextUtils.isEmpty(s.toString()) && !s.toString().equals(result)){
                    result = decimalFormat.format(Double.parseDouble(s.toString().replaceAll(",","")));
                    sellingPriceView.setText(result);
                    sellingPriceView.setSelection(result.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                priceWatcher.afterTextChanged(getAdapterPosition(), s);
                if(s.toString().equals(""))
                    sellingPriceView.setText("0");
            }
        };
        batNameView.addTextChangedListener(nameTextWatcher);
        sellingPriceView.addTextChangedListener(priceTextWatcher);

    }

    void onBind(BatteryData batteryData) {
        DecimalFormat format = new DecimalFormat("#,###");

        batNameView.setText(batteryData.getBatName());
        //sellingPriceView.setText(Integer.toString(batteryData.getSellingPrice()));
        sellingPriceView.setText(format.format(batteryData.getSellingPrice()));
        if(getItemViewType() == 1) {
            minusButton.setVisibility(View.VISIBLE);
            batNameView.setEnabled(true);
            sellingPriceView.setEnabled(true);
        }

    }

    void setMinusClickListener(MinusClickListener minusClickListener) {
        this.minusClickListener = minusClickListener;
    }
}
