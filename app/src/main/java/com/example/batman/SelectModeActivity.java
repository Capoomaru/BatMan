package com.example.batman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SelectModeActivity extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mode);

        TextView text_sell_mode =findViewById(R.id.sell_mode);
        TextView text_manage_mode =findViewById(R.id.manage_mode);

        text_sell_mode.setOnClickListener(onClickListener);
        text_manage_mode.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.sell_mode:
                    startActivity(SellMainActivity.class);
                    break;
                case R.id.manage_mode:
                    startActivity(ManageMainActivity.class);
                    break;
            }
        }
    };

    private void startActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}