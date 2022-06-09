package com.example.batman.selectMode;

import android.view.View;

import com.example.batman.ManageMainActivity;
import com.example.batman.R;
import com.example.batman.SellMainActivity;

public class ModeClickListener implements View.OnClickListener {
    private SelectModeActivity activity;

    ModeClickListener(SelectModeActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sell_mode:
                activity.startActivity(SellMainActivity.class);
                break;
            case R.id.manage_mode:
                activity.startActivity(ManageMainActivity.class);
                break;
        }
    }
}
