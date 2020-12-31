package belev.org.warface_app;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import belev.org.warface_app.data.DataDbHelper;

public class BootBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        WorkManager workManager = WorkManager.getInstance(context);
        ListenableFuture<List<WorkInfo>> statuses = workManager.getWorkInfosByTag("task_worker");

        try {
            List<WorkInfo> workInfoList = statuses.get();

            if (workInfoList.size() <= 0) {
                Log.e("CustomLogTag", "Empty work manager");
                PeriodicWorkCreator periodicWorkCreator = new PeriodicWorkCreator((Application) context.getApplicationContext());
                periodicWorkCreator.create();
            }
        } catch(ExecutionException | InterruptedException e) {
            Log.e("CustomLogTag", e.getMessage());
        }
    }
}
