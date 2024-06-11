package com.example.batman.sellmode.sellingprice;

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

import lombok.Getter;

public class SellingPriceTableAdapter extends RecyclerView.Adapter<SellingPriceTableHolder> {
    private ArrayList<BatteryData> list;
    private boolean hasMinus;
    @Getter
    private ArrayList<Integer> removeList;

    public SellingPriceTableAdapter(ArrayList<BatteryData> list, boolean hasMinus) {
        this.list = list;
        this.hasMinus = hasMinus;
        this.removeList = new ArrayList<>();
    }


    @NonNull
    @Override
    public SellingPriceTableHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_selling_table, parent, false);

        /*
         * % 리사이클러뷰에서 EditText의 textWatcher 사용시 유의사항 %
         *   문제점) 리사이클러뷰에서 EditText의 값을 변경하고 스크롤하게 될 경우 변경된 뷰의 값이 실제 데이터(모델)에 업데이트되지 않아서 데이터 유실이 발생함
         *   해결책) 이를 방지하기위해 ITextWatcher interface를 구현하고 이를 넘겨줌으로써 컨트롤러에서 뷰의 값을 실제 데이터(모델)에 업데이트한다.
         *  */
        ICallBackTextWatcher nameWatcher = (position, s, start, before, count) -> list.get(position).setBatName(s.toString());
        ICallBackTextWatcher priceWatcher = (position, s, start, before, count) -> {

            if (s.toString().equals(""))
                list.get(position).setSellingPrice(0);
            else
                list.get(position).setSellingPrice(Integer.parseInt(s.toString().replaceAll(",", "")));
        };

        MinusClickListener minusClickListener = (view1, position) -> {
            removeList.add(position);
            list.remove(position);
            notifyItemRemoved(position);
        };

        return new SellingPriceTableHolder(view, nameWatcher, priceWatcher, minusClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SellingPriceTableHolder holder, int position) {
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