package com.example.batman.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.batman.db.TransactionSellData;
import com.example.batman.R;
import com.example.batman.utils.ICallBackTextWatcher;
import com.example.batman.utils.MinusClickListener;

import java.util.ArrayList;

public class TransactionSellTableAdapter extends RecyclerView.Adapter<TransactionSellTableHolder> {
    protected ArrayList<TransactionSellData> list;
    private boolean has_minus;
    private ArrayList<Integer> rmList;

    public TransactionSellTableAdapter(ArrayList<TransactionSellData> list, boolean has_minus) {
        this.list = list;
        this.has_minus=has_minus;
        this.rmList = new ArrayList<>();
    }

    @NonNull
    @Override
    public TransactionSellTableHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_transaction_sell_table, parent, false);

        ArrayList<ICallBackTextWatcher> callbackWatcherList = new ArrayList<>();
        callbackWatcherList.add((position, s, start, before, count) -> list.get(position).setBatName(s.toString()));
        callbackWatcherList.add((position, s, start, before, count) -> {
            if(s.toString().equals(""))
                list.get(position).setSellPrice(0);
            else
                list.get(position).setSellPrice(Integer.parseInt(s.toString().replaceAll(",","")));
        });
        callbackWatcherList.add((position, s, start, before, count) -> list.get(position).setCard(s.toString().equals("카드")));
        callbackWatcherList.add((position, s, start, before, count) -> list.get(position).setCarNumber(s.toString()));
        callbackWatcherList.add((position, s, start, before, count) -> list.get(position).setCarCategory(s.toString()));
        callbackWatcherList.add((position, s, start, before, count) -> list.get(position).setPhoneNumber(s.toString()));

        MinusClickListener minusClickListener = (view1, position) -> {
            rmList.add(position);
            list.remove(position);
            notifyItemRemoved(position);
        };

        return new TransactionSellTableHolder(view, callbackWatcherList, minusClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionSellTableHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return has_minus ? 1 : 0;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<TransactionSellData> list) { this.list = list; }
    public ArrayList<Integer> getRmList() { return rmList; }
}
