package belev.org.warface_app;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.work.WorkInfo;
import androidx.work.WorkManager;

public class MyApplication extends Application {

    private static AppOpenManager appOpenManager;

    @Override
    public void onCreate() {
        super.onCreate();

        // On app start ad
        MyApplicationContext myApplicationContext = new MyApplicationContext(this);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        // appOpenManager = new AppOpenManager(this);

        // Work manager
        Log.d("MyTag", "my application start class --->");
        WorkManager workManager = WorkManager.getInstance(this);
        ListenableFuture<List<WorkInfo>> statuses = workManager.getWorkInfosByTag("task_worker5");

        try {
            List<WorkInfo> workInfoList = statuses.get();

            if (workInfoList.size() <= 0) {
                PeriodicWorkCreator periodicWorkCreator = new PeriodicWorkCreator((Application) getApplicationContext());
                periodicWorkCreator.create();
            }
        } catch(ExecutionException | InterruptedException e) {
            Log.d("MyTag", e.getMessage());
        }
    }
}
