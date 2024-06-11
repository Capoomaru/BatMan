package com.example.batman.managemode.stock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.batman.R;
import com.example.batman.db.BatteryData;
import com.example.batman.share.utils.ICallBackTextWatcher;
import com.example.batman.share.utils.MinusClickListener;

import java.util.ArrayList;

public class StockTableAdapter extends RecyclerView.Adapter<StockTableHolder> {
    protected ArrayList<BatteryData> list;
    private boolean hasMinus;
    private ArrayList<Integer> removeList;

    public StockTableAdapter(ArrayList<BatteryData> list, boolean hasMinus) {
        this.list = list;
        this.hasMinus = hasMinus;
        this.removeList = new ArrayList<>();
    }

    @NonNull
    @Override
    public StockTableHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_stock_table, parent, false);

        ICallBackTextWatcher nameWatcher = (position, s, start, before, count) -> list.get(position).setBatName(s.toString());
        ICallBackTextWatcher priceWatcher = (position, s, start, before, count) -> {
            if (s.toString().equals(""))
                list.get(position).setPurchasePrice(0);
            else
                list.get(position).setPurchasePrice(Integer.parseInt(s.toString().replaceAll(",", "")));
        };
        ICallBackTextWatcher countWatcher = (position, s, start, before, count) -> {
            if (s.toString().equals(""))
                list.get(position).setCount(0);
            else
                list.get(position).setCount(Integer.parseInt(s.toString().replaceAll(",", "")));
        };
        MinusClickListener minusClickListener = (view1, position) -> {
            removeList.add(position);
            list.remove(position);
            notifyItemRemoved(position);
        };

        if (hasMinus)
            view.findViewById(R.id.minus).setVisibility(View.VISIBLE);
        return new StockTableHolder(view, nameWatcher, priceWatcher, countWatcher, minusClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StockTableHolder holder, int position) {
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

    public ArrayList<Integer> getRemoveList() {
        return removeList;
    }
}