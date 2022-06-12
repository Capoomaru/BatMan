package com.example.batman.adapter;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.batman.db.TransactionSellData;
import com.example.batman.R;
import com.example.batman.utils.ICallBackTextWatcher;
import com.example.batman.utils.MinusClickListener;
import com.example.batman.utils.NumTextWatcher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TransactionSellTableHolder extends RecyclerView.ViewHolder {
    EditText dateView, batNameView, sellPriceView, isCardView, carNumberView, carCategoryView, phoneNumberView;
    ImageView minusButton;

    public TransactionSellTableHolder(@NonNull View itemView, ArrayList<ICallBackTextWatcher> callbackWatcherList, MinusClickListener minusClickListener) {
        super(itemView);
        dateView = itemView.findViewById(R.id.date);
        batNameView = itemView.findViewById(R.id.batName);
        sellPriceView = itemView.findViewById(R.id.sellingPrice);
        isCardView = itemView.findViewById(R.id.isCard);
        carNumberView = itemView.findViewById(R.id.carNumber);
        carCategoryView = itemView.findViewById(R.id.carCategory);
        phoneNumberView = itemView.findViewById(R.id.phoneNumber);
        minusButton = itemView.findViewById(R.id.minus);

        batNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                callbackWatcherList.get(0).onTextChanged(getAdapterPosition(), s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        sellPriceView.addTextChangedListener(new NumTextWatcher(sellPriceView) {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                callbackWatcherList.get(1).onTextChanged(getAdapterPosition(), s, start, before, count);
            }
        });
        isCardView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                callbackWatcherList.get(2).onTextChanged(getAdapterPosition(), s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        carNumberView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                callbackWatcherList.get(3).onTextChanged(getAdapterPosition(), s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        carCategoryView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                callbackWatcherList.get(4).onTextChanged(getAdapterPosition(), s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        phoneNumberView.addTextChangedListener(new PhoneNumberFormattingTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                callbackWatcherList.get(5).onTextChanged(getAdapterPosition(), s, start, before, count);
            }
        });
        minusButton.setOnClickListener(v -> minusClickListener.onMinusCLick(v, getAdapterPosition()));
    }

    void onBind(TransactionSellData data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd\nHH:mm:ss");

        dateView.setText(dateFormat.format(data.getDate()));
        batNameView.setText(data.getBatName());
        sellPriceView.setText(""+data.getSellPrice());
        isCardView.setText(data.isCard() ? "카드" : "현금");
        carNumberView.setText(data.getCarNumber());
        carCategoryView.setText(data.getCarCategory());
        phoneNumberView.setText(data.getPhoneNumber());
        if(getItemViewType() == 1) {
            minusButton.setVisibility(View.VISIBLE);
            //dateView.setEnabled(true);
            batNameView.setEnabled(true);
            sellPriceView.setEnabled(true);
            isCardView.setEnabled(true);
            carNumberView.setEnabled(true);
            carCategoryView.setEnabled(true);
            phoneNumberView.setEnabled(true);
        }
    }
}
