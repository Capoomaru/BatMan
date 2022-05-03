package com.example.batman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.batman.DB.BatteryData;
import com.example.batman.DB.StockData;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class StockTableAdapter extends RecyclerView.Adapter<StockTableHolder> {
    private ArrayList<BatteryData> list;
    private Context context;
    private boolean has_minus;

    StockTableAdapter(ArrayList<BatteryData> list, boolean has_minus) {
        this.list = list;
        this.has_minus = has_minus;
    }

    @NonNull
    @Override
    public StockTableHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_stock_table, parent, false);

        if(has_minus)
            view.findViewById(R.id.minus).setVisibility(View.VISIBLE);
        return new StockTableHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockTableHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class StockTableHolder extends RecyclerView.ViewHolder {
    TextView batNameView;
    TextView purchasePriceView;
    TextView countView;

    public StockTableHolder(@NonNull View itemView) {
        super(itemView);
        batNameView = itemView.findViewById(R.id.batName);
        purchasePriceView = itemView.findViewById(R.id.purchase_price);
        countView = itemView.findViewById(R.id.count);
    }

    void onBind(BatteryData batteryData) {
        DecimalFormat format = new DecimalFormat("#,###");

        batNameView.setText(batteryData.getBatName());
        purchasePriceView.setText(format.format(batteryData.getPurchasePrice()));
        countView.setText(format.format(batteryData.getCount()));
    }
}