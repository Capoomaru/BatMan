package com.example.batman.share;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.batman.R;
import com.example.batman.db.TransactionStockData;
import com.example.batman.share.utils.ICallBackTextWatcher;
import com.example.batman.share.utils.MinusClickListener;

import java.util.ArrayList;

import lombok.Getter;

public class TransactionStockTableAdapter extends RecyclerView.Adapter<TransactionStockTableHolder> {
    protected ArrayList<TransactionStockData> list;
    private boolean hasMinus;
    @Getter
    private ArrayList<Integer> removeList;

    public TransactionStockTableAdapter(ArrayList<TransactionStockData> list, boolean hasMinus) {
        this.list = list;
        this.hasMinus = hasMinus;
        this.removeList = new ArrayList<>();
    }

    @NonNull
    @Override
    public TransactionStockTableHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_transaction_purchase_table, parent, false);

        ArrayList<ICallBackTextWatcher> callbackWatcherList = new ArrayList<>();
        callbackWatcherList.add((position, s, start, before, count) -> list.get(position).setBatName(s.toString()));
        callbackWatcherList.add((position, s, start, before, count) -> {
            if (s.toString().equals(""))
                list.get(position).setPurchasePrice(0);
            else
                list.get(position).setPurchasePrice(Integer.parseInt(s.toString().replaceAll(",", "")));
        });
        callbackWatcherList.add((position, s, start, before, count) -> {
            if (s.toString().equals(""))
                list.get(position).setCount(0);
            else
                list.get(position).setCount(Integer.parseInt(s.toString().replaceAll(",", "")));
        });
        MinusClickListener minusClickListener = (view1, position) -> {
            removeList.add(position);
            list.remove(position);
            notifyItemRemoved(position);
        };

        return new TransactionStockTableHolder(view, callbackWatcherList, minusClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionStockTableHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return hasMinus ? 1 : 0;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
