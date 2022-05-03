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

public class SellingPriceTableAdapter extends RecyclerView.Adapter<SellingPriceTableHolder> {
    private ArrayList<BatteryData> list;
    private Context context;

    SellingPriceTableAdapter(ArrayList<BatteryData> list ) {this.list = list;}

    @NonNull
    @Override
    public SellingPriceTableHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_selling_table, parent, false);

        return new SellingPriceTableHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SellingPriceTableHolder holder, int position) {
        holder.onBind(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class SellingPriceTableHolder extends RecyclerView.ViewHolder {
    TextView batNameView;
    TextView sellingPriceView;

    public SellingPriceTableHolder(@NonNull View itemView) {
        super(itemView);
        batNameView = itemView.findViewById(R.id.batName);
        sellingPriceView = itemView.findViewById(R.id.selling_price);
    }

    void onBind(BatteryData batteryData) {
        DecimalFormat format = new DecimalFormat("#,###");

        batNameView.setText(batteryData.getBatName());
        sellingPriceView.setText(format.format(batteryData.getSellingPrice()));
    }
}