package com.example.n8tech.taskcan;

import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.n8tech.taskcan.Services.SyncService;

// https://www.mkyong.com/android/android-progress-bar-example/
public class SyncDialogActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Handler handler;
    private int progressBarStatus;
    private int incrementer;
    private int numOfSyncs;
    private final int MAX_PROGRESS = 100;
    private TextView syncMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_dialog);
        this.setFinishOnTouchOutside(false);
        this.progressBar = findViewById(R.id.progressBar);
        this.loadExtras();
        this.resetProgressBarWidth();
        this.progressAction();
    }

    public void loadExtras() {
        this.numOfSyncs = getIntent().getExtras().getInt(SyncService.SYNC_SERVICE_NUM_OF_SYNC);
        this.incrementer = MAX_PROGRESS / this.numOfSyncs;
    }

    private void progressAction() {
        this.progressBarStatus = 0;
        this.progressBar.setProgress(progressBarStatus);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (progressBarStatus < 100) {
                    // update one item
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                this.updateProgressBar();
                if (progressBarStatus >= 100) {
                    syncMessage.setText("Sync Compleeted ...");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finishActivity(-1);
                }
            }

            private void updateProgressBar() {
                progressBarStatus += incrementer;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setProgress(progressBarStatus);
                    }
                });
            }
        });
    }

    private void resetProgressBarWidth() {
        this.syncMessage = findViewById(R.id.syncMessage);
        this.progressBar.setLayoutParams(new ConstraintLayout.LayoutParams(this.syncMessage.getWidth(), ConstraintLayout.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void onBackPressed() { }

}
