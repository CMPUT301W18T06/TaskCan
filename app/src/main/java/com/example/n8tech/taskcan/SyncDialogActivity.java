package com.example.n8tech.taskcan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SyncDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_dialog);
        this.setFinishOnTouchOutside(false);
    }
}
