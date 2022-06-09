package com.example.batman.selectMode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.batman.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class SelectModeActivity extends AppCompatActivity {
    ModeClickListener modeClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mode);

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(settings);

        TextView text_sell_mode = findViewById(R.id.sell_mode);
        TextView text_manage_mode = findViewById(R.id.manage_mode);

        modeClickListener = new ModeClickListener(this);

        text_sell_mode.setOnClickListener(modeClickListener);
        text_manage_mode.setOnClickListener(modeClickListener);
    }

    public void startActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}