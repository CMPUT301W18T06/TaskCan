package com.example.n8tech.taskcan;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SyncDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_dialog);
        this.setFinishOnTouchOutside(false);

        this.resetProgressBarWidth();
    }

    private void resetProgressBarWidth() {
        ProgressBar pb = findViewById(R.id.progressBar);
        TextView tv = findViewById(R.id.syncMessage);
        pb.setLayoutParams(new ConstraintLayout.LayoutParams(tv.getWidth(), ConstraintLayout.LayoutParams.WRAP_CONTENT));
    }
}
