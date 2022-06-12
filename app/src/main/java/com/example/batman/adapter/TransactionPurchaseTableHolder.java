package com.example.batman.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.batman.db.TransactionStockData;
import com.example.batman.R;
import com.example.batman.utils.ICallBackTextWatcher;
import com.example.batman.utils.MinusClickListener;
import com.example.batman.utils.NumTextWatcher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TransactionPurchaseTableHolder extends RecyclerView.ViewHolder {
    EditText dateView, batNameView, purchasePriceView, countView;
    ImageView minusButton;

    public TransactionPurchaseTableHolder(@NonNull View itemView, ArrayList<ICallBackTextWatcher> callbackWatcherList, MinusClickListener minusClickListener) {
        super(itemView);
        dateView = itemView.findViewById(R.id.date);
        batNameView = itemView.findViewById(R.id.batName);
        purchasePriceView = itemView.findViewById(R.id.purchasePrice);
        countView = itemView.findViewById(R.id.count);
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
        purchasePriceView.addTextChangedListener(new NumTextWatcher(purchasePriceView) {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                callbackWatcherList.get(1).onTextChanged(getAdapterPosition(), s, start, before, count);
            }
        });
        countView.addTextChangedListener(new NumTextWatcher(countView) {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                callbackWatcherList.get(2).onTextChanged(getAdapterPosition(), s, start, before, count);
            }
        });
        minusButton.setOnClickListener(v -> {minusClickListener.onMinusCLick(itemView, getAdapterPosition());});
    }

    void onBind(TransactionStockData data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd\nHH:mm:ss");

        dateView.setText(dateFormat.format(data.getDate()));
        batNameView.setText(data.getBatName());
        purchasePriceView.setText(""+data.getPurchasePrice());
        countView.setText(""+data.getCount());
        if(getItemViewType() == 1) {
            minusButton.setVisibility(View.VISIBLE);
            //dateView.setEnabled(true);
            batNameView.setEnabled(true);
            purchasePriceView.setEnabled(true);
            countView.setEnabled(true);
        }
    }
}
