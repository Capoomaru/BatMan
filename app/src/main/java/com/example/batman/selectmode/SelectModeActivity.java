package com.example.batman.selectmode;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.batman.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

/* View */
public class SelectModeActivity extends AppCompatActivity {
    private ModeClickListener modeClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mode);

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(settings);

        TextView sellModeTextView = findViewById(R.id.sell_mode);
        TextView manageModeTextView = findViewById(R.id.manage_mode);

        modeClickListener = new ModeClickListener(this);

        sellModeTextView.setOnClickListener(modeClickListener);
        manageModeTextView.setOnClickListener(modeClickListener);
    }

    public void startActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}